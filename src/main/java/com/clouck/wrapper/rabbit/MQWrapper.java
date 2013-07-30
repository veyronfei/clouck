package com.clouck.wrapper.rabbit;

import com.clouck.model.Region;
import com.clouck.model.ResourceType;

public interface MQWrapper {

    void sendScanResourceMessage(String accountId, ResourceType resourceType, Region region);

    void sendScanResourceMessage(String accountId, ResourceType resourceType);

    void sendGenerateEventMessage(String accountId, ResourceType resourceType, Region region);

    void sendGenerateEventMessage(String accountId, ResourceType resourceType);

}
