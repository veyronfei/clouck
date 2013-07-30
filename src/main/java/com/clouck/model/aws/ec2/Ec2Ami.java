package com.clouck.model.aws.ec2;

import static com.clouck.util.ResourceUtil.notEqual;
import static com.clouck.util.ResourceUtil.notEqualCollection;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import com.amazonaws.services.ec2.model.Image;
import com.amazonaws.services.ec2.model.LaunchPermission;
import com.clouck.model.aws.AbstractResource;

@Document(collection = "ec2_ami")
@TypeAlias(value = "ec2_ami")
@SuppressWarnings("serial")
public class Ec2Ami extends AbstractResource<Image> {
    private List<LaunchPermission> launchPermissions = new ArrayList<>();

    @SuppressWarnings("rawtypes")
    @Override
    protected boolean isEqual(AbstractResource newResource) {
        Image oldImage = this.getResource();
        Ec2Ami newEc2Ami = (Ec2Ami) newResource;
        Image newImage = (Image) newEc2Ami.getResource();

        if (notEqual(oldImage.getImageId(), newImage.getImageId())) return false;
        if (notEqual(oldImage.getImageLocation(), newImage.getImageLocation())) return false;
        if (notEqual(oldImage.getState(), newImage.getState())) return false;
        if (notEqual(oldImage.getOwnerId(), newImage.getOwnerId())) return false;
        if (notEqual(oldImage.getPublic(), newImage.getPublic())) return false;
        if (notEqualCollection(oldImage.getProductCodes(), newImage.getProductCodes())) return false;
        if (notEqual(oldImage.getArchitecture(), newImage.getArchitecture())) return false;
        if (notEqual(oldImage.getImageType(), newImage.getImageType())) return false;
        if (notEqual(oldImage.getKernelId(), newImage.getKernelId())) return false;
        if (notEqual(oldImage.getRamdiskId(), newImage.getRamdiskId())) return false;
        if (notEqual(oldImage.getPlatform(), newImage.getPlatform())) return false;
        if (notEqual(oldImage.getStateReason(), newImage.getStateReason())) return false;
        if (notEqual(oldImage.getImageOwnerAlias(), newImage.getImageOwnerAlias())) return false;
        if (notEqual(oldImage.getName(), newImage.getName())) return false;
        if (notEqual(oldImage.getDescription(), newImage.getDescription())) return false;
        if (notEqual(oldImage.getRootDeviceType(), newImage.getRootDeviceType())) return false;
        if (notEqual(oldImage.getRootDeviceName(), newImage.getRootDeviceName())) return false;
        if (notEqualCollection(oldImage.getBlockDeviceMappings(), newImage.getBlockDeviceMappings())) return false;
        if (notEqual(oldImage.getVirtualizationType(), newImage.getVirtualizationType())) return false;
        if (notEqualCollection(oldImage.getTags(), newImage.getTags())) return false;
        if (notEqual(oldImage.getHypervisor(), newImage.getHypervisor())) return false;
        if (notEqualCollection(this.launchPermissions, newEc2Ami.getLaunchPermissions())) return false;

        return true;
    }

    public List<LaunchPermission> getLaunchPermissions() {
        return launchPermissions;
    }

    public void setLaunchPermissions(List<LaunchPermission> launchPermissions) {
        this.launchPermissions = launchPermissions;
    }
}