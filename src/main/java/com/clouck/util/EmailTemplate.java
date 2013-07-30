package com.clouck.util;

public enum EmailTemplate {
    Task_Result("task-result.vm", "Task Result");

    private static final String emailTemplateFolder = "email-template";

    private String templateFile;
    private String subject;

    EmailTemplate(String templateFile, String subject) {
        this.templateFile = templateFile;
        this.subject = subject;
    }
    
    public String getFileName() {
        return emailTemplateFolder + "/" +templateFile;
    }
    
    public String getSubject() {
        return subject;
    }
}
