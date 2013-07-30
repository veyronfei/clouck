package com.clouck.model.aws.ec2;

import static com.clouck.util.ResourceUtil.notEqual;
import static com.clouck.util.ResourceUtil.notEqualCollection;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import com.amazonaws.services.ec2.model.ProductCode;
import com.amazonaws.services.ec2.model.Volume;
import com.clouck.model.aws.AbstractResource;

@Document(collection = "ec2_volume")
@TypeAlias(value = "ec2_volume")
@SuppressWarnings("serial")
public class Ec2Volume extends AbstractResource<Volume> {
    private Boolean autoEnableIO;
    private List<ProductCode> productCodes = new ArrayList<>();

    @Override
    @SuppressWarnings("rawtypes")
    protected boolean isEqual(AbstractResource newResource) {
        Volume oldVolume = this.getResource();
        Ec2Volume newEc2Volume = (Ec2Volume) newResource;
        Volume newVolume = (Volume) newResource.getResource();

        if (notEqual(oldVolume.getVolumeId(), newVolume.getVolumeId())) return false;
        if (notEqual(oldVolume.getSize(), newVolume.getSize())) return false;
        if (notEqual(oldVolume.getSnapshotId(), newVolume.getSnapshotId())) return false;
        if (notEqual(oldVolume.getAvailabilityZone(), newVolume.getAvailabilityZone())) return false;
        if (notEqual(oldVolume.getState(), newVolume.getState())) return false;
        if (notEqual(oldVolume.getCreateTime(), newVolume.getCreateTime())) return false;
        if (notEqualCollection(oldVolume.getAttachments(), newVolume.getAttachments())) return false;
        if (notEqualCollection(oldVolume.getTags(), newVolume.getTags())) return false;
        if (notEqual(oldVolume.getVolumeType(), newVolume.getVolumeType())) return false;
        if (notEqual(oldVolume.getIops(), newVolume.getIops())) return false;
        if (notEqual(this.getAutoEnableIO(), newEc2Volume.getAutoEnableIO())) return false;
        if (notEqualCollection(this.getProductCodes(), newEc2Volume.getProductCodes())) return false;

        return true;
    }

    public Boolean getAutoEnableIO() {
        return autoEnableIO;
    }

    public void setAutoEnableIO(Boolean autoEnableIO) {
        this.autoEnableIO = autoEnableIO;
    }

    public List<ProductCode> getProductCodes() {
        return productCodes;
    }

    public void setProductCodes(List<ProductCode> productCodes) {
        this.productCodes = productCodes;
    }
}