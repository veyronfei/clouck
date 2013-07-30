package com.clouck.wrapper.spring;

import com.clouck.model.Event;
import com.clouck.model.EventType;

public interface MRWrapper {

    String getEventMessage(EventType et, Event event);

}
