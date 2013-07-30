package com.clouck.mq;

import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.clouck.application.Ec2Constants;
import com.clouck.model.Account;
import com.clouck.model.Region;
import com.clouck.model.ResourceType;
import com.clouck.model.ScanConfig;
import com.clouck.model.aws.AbstractResource;
import com.clouck.repository.AccountDao;
import com.clouck.repository.AwsRepository;
import com.clouck.repository.BaseRepository;
import com.clouck.service.ConfService;
import com.clouck.service.ResourceService;
import com.clouck.validator.Ec2ResourceValidator;
import com.clouck.wrapper.aws.AsWrapper;
import com.clouck.wrapper.aws.Ec2Wrapper;
import com.clouck.wrapper.aws.IamWrapper;
import com.google.common.base.Optional;

@Component
public class ScanResourceWorker {
    private static final Logger log = LoggerFactory.getLogger(ScanResourceWorker.class);

    @Autowired
    private Ec2Wrapper ec2;
    @Autowired
    private BaseRepository baseDao;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private AwsRepository awsDao;
    @Autowired
    private AsWrapper as;
    @Autowired
    private IamWrapper iam;
    @Autowired
    private Ec2ResourceValidator validator;
    @Autowired
    private AmqpTemplate template;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private ConfService confService;

    public void receive(ScanResourceMessage message) {
        String accountId = message.getAccountId();
        Account account = accountDao.findOne(accountId);
        ResourceType resourceType = message.getResourceType();
        Region region = message.getRegion();

        //prevent receiving too many "same" messages at the same time in case of worker failure.
        DateTime now = DateTime.now();
        Optional<ScanConfig> oScanConf = confService.findScanConf(accountId, resourceType, region);
        if (oScanConf.isPresent()) {
            ScanConfig sc = oScanConf.get();
            DateTime dateTime = new DateTime(oScanConf.get().getLastScanTime());
            if (dateTime.plusSeconds(Ec2Constants.Resource_Scaning_Mininum_Waiting_Seconds).isAfter(now)) {
                return;
            } else {
                sc.setLastScanTime(now.toDate());
                confService.save(sc);
            }
        } else {
            confService.createNewScanConf(accountId, resourceType, region);
        }

        //TODO: put this line into resource service
        List<AbstractResource<?>> newResources = resourceService.findNewResources(account, region, resourceType, now);

        resourceService.addNewResources(account, region, resourceType, newResources, now);
    }
}
