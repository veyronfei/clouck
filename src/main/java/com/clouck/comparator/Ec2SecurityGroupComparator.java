package com.clouck.comparator;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.ec2.model.IpPermission;
import com.amazonaws.services.ec2.model.SecurityGroup;
import com.clouck.model.Event;
import com.clouck.model.EventType;
import com.clouck.model.aws.ec2.Ec2SecurityGroup;
import com.clouck.util.ResourceUtil;

@Component
public class Ec2SecurityGroupComparator extends AbstractEc2Comparator<Ec2SecurityGroup> {
    private static final Logger log = LoggerFactory.getLogger(Ec2SecurityGroupComparator.class);

    @Autowired
    private ResourceUtil resourceUtil;

    @Override
    public Event firstScan() {
        return createFirstScanEvent(EventType.Ec2_Security_Group_First_Scan);
    }

    @Override
    public Event initialise(Ec2SecurityGroup newResource) {
        return createEvent(null, newResource, EventType.Ec2_Security_Group_Found);
    }

    @Override
    public Event add(Ec2SecurityGroup newResource) {
        return createEvent(null, newResource, EventType.Ec2_Security_Group_Create);
    }

    @Override
    protected void update(List<Event> result, Ec2SecurityGroup oldResource, Ec2SecurityGroup newResource) {
        SecurityGroup oldSecurityGroup = oldResource.getResource();
        SecurityGroup newSecurityGroup = newResource.getResource();

        compareIpPermissions(result, oldSecurityGroup.getIpPermissions(), newSecurityGroup.getIpPermissions(), oldResource, newResource);
        compareIpPermissions(result, oldSecurityGroup.getIpPermissionsEgress(), newSecurityGroup.getIpPermissionsEgress(), oldResource, newResource);
        compareTags(result, oldSecurityGroup.getTags(), newSecurityGroup.getTags(), oldResource, newResource);
    }

    @Override
    public Event delete(Ec2SecurityGroup oldResource) {
        return createEvent(oldResource, null, EventType.Ec2_Security_Group_Delete);
    }

    private void compareIpPermissions(Collection<Event> result, List<IpPermission> oldPermissions, List<IpPermission> newPermissions,
            Ec2SecurityGroup oldResource, Ec2SecurityGroup newResource) {
        CompareResult<IpPermission> compareResult = resourceUtil.compare(oldPermissions, newPermissions);
        for (IpPermission ip : compareResult.getAdd()) {
            result.add(createEvent(oldResource, newResource, EventType.Ec2_Security_Group_Add_Rule, oldResource.getUniqueId()));
        }
        for (IpPermission ip : compareResult.getDelete()) {
            result.add(createEvent(oldResource, newResource, EventType.Ec2_Security_Group_Delete_Rule, oldResource.getUniqueId()));
        }
        for (Pair<IpPermission, IpPermission> pair : compareResult.getUpdate()) {
            log.error("not handled case");
            result.add(createEvent(oldResource, newResource, EventType.Unknown));
        }
    }
}
