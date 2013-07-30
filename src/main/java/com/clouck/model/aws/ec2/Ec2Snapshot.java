package com.clouck.model.aws.ec2;
import static com.clouck.util.ResourceUtil.notEqual;
import static com.clouck.util.ResourceUtil.notEqualCollection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import com.amazonaws.services.ec2.model.CreateVolumePermission;
import com.amazonaws.services.ec2.model.ProductCode;
import com.amazonaws.services.ec2.model.Snapshot;
import com.clouck.model.Event;
import com.clouck.model.EventType;
import com.clouck.model.aws.AbstractResource;

@Document(collection = "ec2_snapshot")
@TypeAlias(value = "ec2_snapshot")
@SuppressWarnings("serial")
public class Ec2Snapshot extends AbstractResource<Snapshot> {

    private List<CreateVolumePermission> createVolumePermissions = new ArrayList<>();
    private List<ProductCode> productCodes = new ArrayList<>();

    @Override
    @SuppressWarnings("rawtypes")
    protected boolean isEqual(AbstractResource newResource) {
        Snapshot oldSnapshot = this.getResource();
        Ec2Snapshot newEc2Snapshot = (Ec2Snapshot) newResource;
        Snapshot newSnapshot = (Snapshot) newResource.getResource();

        if (notEqual(oldSnapshot.getSnapshotId(), newSnapshot.getSnapshotId())) return false;
        if (notEqual(oldSnapshot.getVolumeId(), newSnapshot.getVolumeId())) return false;
        if (notEqual(oldSnapshot.getState(), newSnapshot.getState())) return false;
        if (notEqual(oldSnapshot.getStartTime(), newSnapshot.getStartTime())) return false;
        if (notEqual(oldSnapshot.getProgress(), newSnapshot.getProgress())) return false;
        if (notEqual(oldSnapshot.getOwnerId(), newSnapshot.getOwnerId())) return false;
        if (notEqual(oldSnapshot.getDescription(), newSnapshot.getDescription())) return false;
        if (notEqual(oldSnapshot.getVolumeSize(), newSnapshot.getVolumeSize())) return false;
        if (notEqual(oldSnapshot.getOwnerAlias(), newSnapshot.getOwnerAlias())) return false;
        if (notEqualCollection(oldSnapshot.getTags(), newSnapshot.getTags())) return false;
        if (notEqualCollection(this.getCreateVolumePermissions(), newEc2Snapshot.getCreateVolumePermissions())) return false;
        if (notEqualCollection(this.getProductCodes(), newEc2Snapshot.getProductCodes())) return false;

        return true;
    }

    public List<CreateVolumePermission> getCreateVolumePermissions() {
        return createVolumePermissions;
    }

    public void setCreateVolumePermissions(List<CreateVolumePermission> createVolumePermissions) {
        this.createVolumePermissions = createVolumePermissions;
    }

    public List<ProductCode> getProductCodes() {
        return productCodes;
    }

    public void setProductCodes(List<ProductCode> productCodes) {
        this.productCodes = productCodes;
    }

    public Boolean isPublic() {
        for (CreateVolumePermission cvp : createVolumePermissions) {
            if (cvp.getGroup() != null) {
                if (cvp.getGroup().equals("all")) {
                    return true;
                } else {
                    return null;
                }
            }
        }
        return false;
    }
}