package com.clouck.model;
//package com.fleeio.model;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import javax.persistence.Table;
//
//@Entity
//@Table(name = "alert_conf")
//@SuppressWarnings("serial")
//public class AlertConf extends AbstractEntityBean {
//    
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private EventType eventType;
//    
//    @Column(name = "is_send_email")
//    private boolean isSendEmail = false;
//
//    public EventType getEventType() {
//        return eventType;
//    }
//
//    public void setEventType(EventType eventType) {
//        this.eventType = eventType;
//    }
//
//    public boolean isSendEmail() {
//        return isSendEmail;
//    }
//
//    public void setSendEmail(boolean isSendEmail) {
//        this.isSendEmail = isSendEmail;
//    }
//}