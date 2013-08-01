package com.clouck.comparator;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.ec2.model.Tag;
import com.clouck.model.Event;
import com.clouck.model.EventType;
import com.clouck.model.aws.AbstractResource;
import com.clouck.util.ResourceUtil;

public abstract class AbstractEc2Comparator<R extends AbstractResource<?>> implements Ec2Comparator<R> {

    @Autowired
    protected ResourceUtil resourceUtil;

    @SuppressWarnings("unchecked")
    @Override
    public Class<R> getType() {
        ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<R>) superclass.getActualTypeArguments()[0];
    }

    protected boolean notEqual(Object o1, Object o2) {
        return !Objects.equals(o1, o2);
    }

    protected boolean equal(Object o1, Object o2) {
        return Objects.equals(o1, o2);
    }

    @Override
    public abstract Event firstScan();

    @Override
    public abstract Event initialise(R newResource);

    @Override
    public abstract Event add(R newResource);

    @Override
    public abstract Event delete(R oldResource);

    @Override
    public List<Event> update(R oldResource, R newResource) {
        Validate.isTrue(!oldResource.equals(newResource));
        List<Event> result = new ArrayList<>();
        update(result, oldResource, newResource);
        if (result.size() == 0) {
            result.add(createEvent(oldResource, newResource, EventType.Unknown));
        }
        return result;
    }
    protected abstract void update(List<Event> result, R oldResource, R newResource);

    protected Event createFirstScanEvent(EventType eventType) {
        Event event = new Event();
        event.setEventType(eventType);
        return event;
    }

    protected Event createEvent(R oldResource, R newResource, EventType eventType, String... values) {
        Validate.isTrue(oldResource != null || newResource != null);
        Event e = new Event();
        e.setEventType(eventType);
        if (oldResource != null) {
            e.setUniqueId(oldResource.getUniqueId());
        } else {
            e.setUniqueId(newResource.getUniqueId());
        }
        if (oldResource != null) {
            e.setOldResourceId(oldResource.getId());
        }
        if (newResource != null) {
            e.setNewResourceId(newResource.getId());
        }
        Validate.isTrue(values.length < 5);
        int size = values.length;
        if (size > 0) {
            String s = values[0];
            e.setValue(s);
        }
        if (size > 1) {
            String s = values[1];
            e.setValue1(s);
        }
        if (size > 2) {
            String s = values[2];
            e.setValue2(s);
        }
        if (size > 3) {
            String s = values[3];
            e.setValue3(s);
        }
        return e;
    }

    protected void compareTags(Collection<Event> result, List<Tag> oldTags, List<Tag> newTags, R oldResource, R newResource) {
        CompareResult<Tag> compareResult = resourceUtil.compareTags(oldTags, newTags);
        for (Tag tag : compareResult.getAdd()) {
            result.add(createEvent(oldResource, newResource, EventType.Tag_Add, tag.getKey(), tag.getValue()));
        }
        for (Pair<Tag, Tag> pair : compareResult.getUpdate()) {
            result.add(createEvent(oldResource, newResource, EventType.Tag_Update, pair.getLeft().getKey(), pair.getLeft().getValue(), pair.getRight().getValue()));
        }
        for (Tag tag : compareResult.getDelete()) {
            result.add(createEvent(oldResource, newResource, EventType.Tag_Delete, tag.getKey(), tag.getValue()));
        }
    }
}