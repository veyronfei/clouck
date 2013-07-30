package com.clouck.util;

import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.clouck.exception.CloudVersionException;

@Component
public class EmailUtilImpl implements EmailUtil {
    private static final Logger log = LoggerFactory.getLogger(EmailUtilImpl.class);

    @Autowired
    private MailSender mailSender;

    @Autowired
    private VelocityEngine velocityEngine;

    /**
     * {@inheritDoc}
     */
    //TODO: checkout http://static.springsource.org/spring/docs/3.0.x/spring-framework-reference/html/mail.html for html sending
    @Override
	public void send(SimpleMailMessage msg, EmailTemplate emailTemplate, Map model) {
        msg.setSubject(emailTemplate.getSubject());

        String result = null;

        try {
            result = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, emailTemplate.getFileName(), model);
        } catch (VelocityException ex) {
            throw new CloudVersionException(ex);
        }

        msg.setText(result);
        send(msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public void send(SimpleMailMessage msg) {
        try {
            mailSender.send(msg);
        } catch (MailException ex) {
            throw new CloudVersionException(ex);
        }
    }
//
//    /**
//     * Convenience method for sending messages with attachments.
//     *
//     * @param recipients array of e-mail addresses
//     * @param sender e-mail address of sender
//     * @param resource attachment from classpath
//     * @param bodyText text in e-mail
//     * @param subject subject of e-mail
//     * @param attachmentName name for attachment
//     * @throws MessagingException thrown when can't communicate with SMTP server
//     */
//    public void sendMessage(String[] recipients, String sender,
//                            ClassPathResource resource, String bodyText,
//                            String subject, String attachmentName)
//    throws MessagingException {
//        MimeMessage message = ((JavaMailSenderImpl) mailSender).createMimeMessage();
//
//        // use the true flag to indicate you need a multipart message
//        MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//        helper.setTo(recipients);
//
//        // use the default sending if no sender specified
//        if (sender == null) {
//            helper.setFrom(defaultFrom);
//        } else {
//           helper.setFrom(sender);
//        }
//
//        helper.setText(bodyText);
//        helper.setSubject(subject);
//
//        helper.addAttachment(attachmentName, resource);
//
//        ((JavaMailSenderImpl) mailSender).send(message);
//    }
}
