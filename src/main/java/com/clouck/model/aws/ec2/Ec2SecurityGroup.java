package com.clouck.model.aws.ec2;

import static com.clouck.util.ResourceUtil.notEqual;
import static com.clouck.util.ResourceUtil.notEqualCollection;

import java.util.Collections;
import java.util.List;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import com.amazonaws.services.ec2.model.IpPermission;
import com.amazonaws.services.ec2.model.SecurityGroup;
import com.clouck.model.aws.AbstractResource;
import com.clouck.model.aws.comparator.UserIdGroupPairComparator;

@Document(collection = "ec2_security_group")
@TypeAlias(value = "ec2_security_group")
@SuppressWarnings("serial")
public class Ec2SecurityGroup extends AbstractResource<SecurityGroup> {

    @Override
    @SuppressWarnings("rawtypes")
    protected boolean isEqual(AbstractResource newResource) {
        SecurityGroup oldSG = this.getResource();
        SecurityGroup newSG = (SecurityGroup) newResource.getResource();

        if (notEqual(oldSG.getOwnerId(), newSG.getOwnerId())) return false;
        if (notEqual(oldSG.getGroupName(), newSG.getGroupName())) return false;
        if (notEqual(oldSG.getGroupId(), newSG.getGroupId())) return false;
        if (notEqual(oldSG.getDescription(), newSG.getDescription())) return false;
        if (notEqualIpPermission(oldSG.getIpPermissions(), newSG.getIpPermissions())) return false;
        if (notEqualIpPermission(oldSG.getIpPermissionsEgress(), newSG.getIpPermissionsEgress())) return false;
        if (notEqual(oldSG.getVpcId(), newSG.getVpcId())) return false;
        if (notEqualCollection(oldSG.getTags(), newSG.getTags())) return false;

        return true;
    }

    private boolean notEqualIpPermission(List<IpPermission> l1, List<IpPermission> l2) {
        for (IpPermission i1 : l1) {
            Collections.sort(i1.getIpRanges());
            Collections.sort(i1.getUserIdGroupPairs(), new UserIdGroupPairComparator());
        }
        for (IpPermission i2 : l2) {
            Collections.sort(i2.getIpRanges());
            Collections.sort(i2.getUserIdGroupPairs(), new UserIdGroupPairComparator());
        }
        return notEqualCollection(l1, l2);
    }
}
