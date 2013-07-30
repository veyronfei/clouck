package com.clouck.comparator;

import java.util.List;

import org.springframework.stereotype.Component;

import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.KeyPair;
import com.amazonaws.services.ec2.model.KeyPairInfo;
import com.clouck.model.Event;
import com.clouck.model.EventType;
import com.clouck.model.aws.ec2.Ec2KeyPair;

@Component
public class Ec2KeyPairComparator extends AbstractEc2Comparator<Ec2KeyPair> {

    @Override
    public Event firstScan() {
        return createFirstScanEvent(EventType.Ec2_Key_Pair_First_Scan);
    }

    @Override
    public Event initialise(Ec2KeyPair newResource) {
        return createEvent(null, newResource, EventType.Ec2_Key_Pair_Found);
    }

    @Override
    public Event add(Ec2KeyPair newResource) {
        return createEvent(null, newResource, EventType.Ec2_Key_Pair_Add);
    }

    @Override
    protected void update(List<Event> result, Ec2KeyPair oldResource, Ec2KeyPair newResource) {
        KeyPairInfo oldKeyPairInfo = oldResource.getResource();
        KeyPairInfo newKeyPairInfo = newResource.getResource();
        if (notEqual(oldKeyPairInfo.getKeyFingerprint(), newKeyPairInfo.getKeyFingerprint())) {
            result.add(createEvent(oldResource, newResource, EventType.Ec2_Key_Pair_Update));
        }
    }

    @Override
    public Event delete(Ec2KeyPair oldResource) {
        return createEvent(oldResource, null, EventType.Ec2_Key_Pair_Delete);
    }
}
