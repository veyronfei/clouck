package com.clouck.mq;

import java.util.List;

import org.apache.commons.lang3.Validate;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.clouck.application.Ec2Constants;
import com.clouck.model.EventConfig;
import com.clouck.model.Region;
import com.clouck.model.ResourceType;
import com.clouck.model.aws.ec2.Ec2Version;
import com.clouck.model.aws.ec2.Ec2VersionMeta;
import com.clouck.service.AwsService;
import com.clouck.service.BaseService;
import com.clouck.service.ConfService;
import com.clouck.service.EventService;
import com.google.common.base.Optional;

@Component
public class GenerateEventWorker {
    private static final Logger log = LoggerFactory.getLogger(GenerateEventWorker.class);

    @Autowired
    private ConfService confService;
    @Autowired
    private AwsService awsService;
    @Autowired
    private EventService eventService;
    @Autowired
    private BaseService baseService;

    public void receive(GenerateEventMessage message) {
        Validate.noNullElements(new Object[]{message.getAccountId(), message.getResourceType(), message.getRegion()});

        String accountId = message.getAccountId();
        ResourceType resourceType = message.getResourceType();
        Region region = message.getRegion();

        //prevent receiving too many "same" messages at the same time in case of worker failure.
        Optional<EventConfig> oEventConf = confService.findEventConf(accountId, resourceType, region);
        if (oEventConf.isPresent()) {
            DateTime now = DateTime.now();
            EventConfig ec = oEventConf.get();
            DateTime dateTime = new DateTime(ec.getLastProcessTime());
            if (dateTime.plusSeconds(Ec2Constants.Event_Generating_Mininum_Waiting_Seconds).isAfter(now)) {
                return;
            } else {
                ec.setLastProcessTime(now.toDate());
                confService.save(ec);
            }
        } else {
            confService.createNewEventConf(accountId, resourceType, region);
        }

        Optional<Ec2VersionMeta> oLatestEc2VersionMeta = awsService.findLatestEc2VersionMeta(accountId, resourceType, region);
        if (oLatestEc2VersionMeta.isPresent()) {
            DateTime dt = new DateTime(oLatestEc2VersionMeta.get().getTimeDetected());
            List<Ec2Version> ec2Versions = awsService.findEc2VersionsFromIncludeOrderByTimeDetected(accountId, resourceType, region, dt);
            List<Ec2VersionMeta> eventMetas = eventService.generateEvents(ec2Versions);
            baseService.insertAll(eventMetas);
        } else {
            Optional<Ec2Version> oFirstEc2Version = awsService.findFirstEc2Version(accountId, region, resourceType);
            if (oFirstEc2Version.isPresent()) {
                Ec2VersionMeta eventMeta = eventService.generateEvents(oFirstEc2Version.get());
                baseService.save(eventMeta);
            }
        }
    }
}
