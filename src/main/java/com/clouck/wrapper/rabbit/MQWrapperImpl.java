package com.clouck.wrapper.rabbit;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.clouck.application.Ec2Constants;
import com.clouck.model.Region;
import com.clouck.model.ResourceType;
import com.clouck.mq.GenerateEventMessage;
import com.clouck.mq.ScanResourceMessage;

@Component
public class MQWrapperImpl implements MQWrapper {

    @Autowired
    private AmqpTemplate template;

    @Override
    public void sendScanResourceMessage(String accountId, ResourceType resourceType, Region region) {
        ScanResourceMessage message = new ScanResourceMessage();
        message.setAccountId(accountId);
        message.setRegion(region);
        message.setResourceType(resourceType);
        template.convertAndSend(Ec2Constants.Scan_Resource_Exchange_Name, null, message);
    }

    @Override
    public void sendScanResourceMessage(String accountId, ResourceType resourceType) {
        ScanResourceMessage message = new ScanResourceMessage();
        message.setAccountId(accountId);
        message.setResourceType(resourceType);
        template.convertAndSend(Ec2Constants.Scan_Resource_Exchange_Name, null, message);
    }

    @Override
    public void sendGenerateEventMessage(String accountId, ResourceType resourceType, Region region) {
        GenerateEventMessage message = new GenerateEventMessage();
        message.setAccountId(accountId);
        message.setRegion(region);
        message.setResourceType(resourceType);
        template.convertAndSend(Ec2Constants.Generate_Event_Exchange_Name, null, message);
    }

    @Override
    public void sendGenerateEventMessage(String accountId, ResourceType resourceType) {
        GenerateEventMessage message = new GenerateEventMessage();
        message.setAccountId(accountId);
        message.setResourceType(resourceType);
        template.convertAndSend(Ec2Constants.Generate_Event_Exchange_Name, null, message);
    }
}
