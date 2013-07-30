package com.clouck.wrapper.spring;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.clouck.model.Event;
import com.clouck.model.EventType;

@Component
public class MRWrapperImpl implements MRWrapper {
    @Autowired
    private MessageSource ms;

    @Override
    public String getEventMessage(EventType et, Event event) {
        Object[] args = new Object[]{event.getUniqueId(), event.getValue(), event.getValue1(), event.getValue2(), event.getValue3()};
        if (et.equals(EventType.Ec2_Elastic_Ip_Associated) || et.equals(EventType.Ec2_Elastic_Ip_Disassociated)) {
            if (event.getValue1() != null) {
                return ms.getMessage("event.type.vpc." + et, args, Locale.getDefault());
            }
        }
        return ms.getMessage("event.type." + et, args, Locale.getDefault());
    }
}
