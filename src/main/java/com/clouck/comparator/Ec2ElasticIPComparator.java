package com.clouck.comparator;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.amazonaws.services.ec2.model.Address;
import com.clouck.model.Event;
import com.clouck.model.EventType;
import com.clouck.model.aws.ec2.Ec2ElasticIP;

@Component
public class Ec2ElasticIPComparator extends AbstractEc2Comparator<Ec2ElasticIP> {
    private static final Logger log = LoggerFactory.getLogger(Ec2ElasticIPComparator.class);

    @Override
    public Event firstScan() {
        return createFirstScanEvent(EventType.Ec2_Elastic_Ip_First_Scan);
    }

    @Override
    public Event initialise(Ec2ElasticIP newResource) {
        return createEvent(null, newResource, EventType.Ec2_Elastic_Ip_Found);
    }

    @Override
    public Event add(Ec2ElasticIP newResource) {
        return createEvent(null, newResource, EventType.Ec2_Elastic_Ip_Allocated);
    }

    @Override
    protected void update(List<Event> result, Ec2ElasticIP oldResource, Ec2ElasticIP newResource) {
        Address oldAddress = oldResource.getResource();
        Address newAddress = newResource.getResource();

        if (oldAddress.getDomain().equals("vpc")) {
            if (notEqual(oldAddress.getInstanceId(), newAddress.getInstanceId())) {
                if (StringUtils.isEmpty(oldAddress.getInstanceId()) && StringUtils.isNotEmpty(newAddress.getInstanceId())) {
                    result.add(createEvent(oldResource, newResource, EventType.Ec2_Elastic_Ip_Associated, newAddress.getInstanceId(), newAddress.getNetworkInterfaceId(), newAddress.getPrivateIpAddress()));
                } else if (StringUtils.isNotEmpty(oldAddress.getInstanceId()) && StringUtils.isEmpty(newAddress.getInstanceId())) {
                    result.add(createEvent(oldResource, newResource, EventType.Ec2_Elastic_Ip_Disassociated, oldAddress.getInstanceId(), oldAddress.getNetworkInterfaceId(), oldAddress.getPrivateIpAddress()));
                } else {
                    result.add(createEvent(oldResource, newResource, EventType.Ec2_Elastic_Ip_Disassociated, oldAddress.getInstanceId(), oldAddress.getNetworkInterfaceId(), oldAddress.getPrivateIpAddress()));
                    result.add(createEvent(oldResource, newResource, EventType.Ec2_Elastic_Ip_Associated, newAddress.getInstanceId(), newAddress.getNetworkInterfaceId(), newAddress.getPrivateIpAddress()));
                }
            } else {
                if (notEqual(oldAddress.getPrivateIpAddress(), newAddress.getPrivateIpAddress())) {
                    result.add(createEvent(oldResource, newResource, EventType.Ec2_Elastic_Ip_Disassociated, oldAddress.getInstanceId(), oldAddress.getNetworkInterfaceId(), oldAddress.getPrivateIpAddress()));
                    result.add(createEvent(oldResource, newResource, EventType.Ec2_Elastic_Ip_Associated, newAddress.getInstanceId(), newAddress.getNetworkInterfaceId(), newAddress.getPrivateIpAddress()));
                }
            }
        } else if (oldAddress.getDomain().equals("standard")) {
            if (notEqual(oldAddress.getInstanceId(), newAddress.getInstanceId())) {
                if (StringUtils.isEmpty(oldAddress.getInstanceId()) && StringUtils.isNotEmpty(newAddress.getInstanceId())) {
                    result.add(createEvent(oldResource, newResource, EventType.Ec2_Elastic_Ip_Associated, newAddress.getInstanceId()));
                } else if (StringUtils.isNotEmpty(oldAddress.getInstanceId()) && StringUtils.isEmpty(newAddress.getInstanceId())) {
                    result.add(createEvent(oldResource, newResource, EventType.Ec2_Elastic_Ip_Disassociated, oldAddress.getInstanceId()));
                } else {
                    result.add(createEvent(oldResource, newResource, EventType.Ec2_Elastic_Ip_Disassociated, oldAddress.getInstanceId()));
                    result.add(createEvent(oldResource, newResource, EventType.Ec2_Elastic_Ip_Associated, newAddress.getInstanceId()));
                }
            }
        } else {
            log.error("unknown domain:{}", oldAddress.getDomain());
            result.add(createEvent(oldResource, newResource, EventType.Unknown));
        }
    }

    @Override
    public Event delete(Ec2ElasticIP oldResource) {
        return createEvent(oldResource, null, EventType.Ec2_Elastic_Ip_Released);
    }
}
