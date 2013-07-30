package com.clouck.model.aws.ec2;

import static com.clouck.util.ResourceUtil.notEqual;
import static com.clouck.util.ResourceUtil.notEqualCollection;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import com.amazonaws.services.ec2.model.NetworkInterface;
import com.clouck.model.aws.AbstractResource;

@Document(collection = "ec2_network_interface")
@TypeAlias(value = "ec2_network_interface")
@SuppressWarnings("serial")
public class Ec2NetworkInterface extends AbstractResource<NetworkInterface> {

    @Override
    @SuppressWarnings("rawtypes")
    protected boolean isEqual(AbstractResource newResource) {
        NetworkInterface oldNetworkInterface = this.getResource();
        Ec2NetworkInterface newEc2NetworkInterface = (Ec2NetworkInterface) newResource;
        NetworkInterface newNetworkInterface = newEc2NetworkInterface.getResource();
        
        if (notEqual(oldNetworkInterface.getNetworkInterfaceId(), newNetworkInterface.getNetworkInterfaceId())) return false;
        if (notEqual(oldNetworkInterface.getOwnerId(), newNetworkInterface.getOwnerId())) return false;
        if (notEqual(oldNetworkInterface.getRequesterId(), newNetworkInterface.getRequesterId())) return false;
        if (notEqual(oldNetworkInterface.getRequesterManaged(), newNetworkInterface.getRequesterManaged())) return false;
        if (notEqual(oldNetworkInterface.getStatus(), newNetworkInterface.getStatus())) return false;
        if (notEqual(oldNetworkInterface.getMacAddress(), newNetworkInterface.getMacAddress())) return false;
        if (notEqual(oldNetworkInterface.getPrivateIpAddress(), newNetworkInterface.getPrivateIpAddress())) return false;
        if (notEqual(oldNetworkInterface.getPrivateDnsName(), newNetworkInterface.getPrivateDnsName())) return false;
        if (notEqual(oldNetworkInterface.getSourceDestCheck(), newNetworkInterface.getSourceDestCheck())) return false;
        if (notEqualCollection(oldNetworkInterface.getGroups(), newNetworkInterface.getGroups())) return false;
        if (notEqual(oldNetworkInterface.getAttachment(), newNetworkInterface.getAttachment())) return false;
        if (notEqual(oldNetworkInterface.getAssociation(), newNetworkInterface.getAssociation())) return false;
        if (notEqualCollection(oldNetworkInterface.getTagSet(), newNetworkInterface.getTagSet())) return false;
        if (notEqualCollection(oldNetworkInterface.getPrivateIpAddresses(), newNetworkInterface.getPrivateIpAddresses())) return false;

        return true;
    }
}
