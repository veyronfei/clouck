package com.clouck.util;

import java.util.Map;

import org.springframework.mail.SimpleMailMessage;

public interface EmailUtil {

    /**
     * Send a simple message based on a Velocity template.
     * @param msg the message to populate
     * @param templateName the Velocity template to use
     * @param model a map containing data within key/value pairs
     * @throws {@link CloudVersionException} if the template wasn't found or rendering failed
     */
    void send(SimpleMailMessage msg, EmailTemplate emailTemplate, Map model);

    /**
     * Send a simple message with pre-populated values.
     * You normally don't need to call this function.
     *
     * @param msg the message to send
     * @throws {@link CloudVersionException} when SMTP server is down, or authentication is not passed.
     */
    void send(SimpleMailMessage msg);
}
