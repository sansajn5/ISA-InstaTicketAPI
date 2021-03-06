package com.isa.instaticketapi.service;

import java.util.Locale;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import com.isa.instaticketapi.config.ApplicationProperties;
import com.isa.instaticketapi.domain.Offer;
import com.isa.instaticketapi.domain.User;


/**
 * Service class for handling with Mail server
 *
 * @author sansajn
 */
@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private static final String USER = "user";
    
    private static final String OFFER = "offer";

    private static final String BASE_URL = "baseUrl";

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private SpringTemplateEngine templateEngine;

    /**
     * Seding email to cilent
     *
     * @param to          to whom
     * @param subject     subject of email
     * @param content     conent of email
     * @param isMultipart representing does email contains img,etc...
     * @param isHtml      represeiting is it html type
     */
    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
                isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, CharEncoding.UTF_8);
            message.setTo(to);
            message.setFrom(applicationProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", to);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.warn("Email could not be sent to user '{}'", to, e);
            } else {
                log.warn("Email could not be sent to user '{}': {}", to, e.getMessage());
            }
        }
    }

    /**
     * Creating generic template for multiple cases
     *
     * @param user         user providing information about client
     * @param templateName template name for selecting email template
     * @param titleKey
     */
    @Async
    public void sendEmailFromTemplate(User user, String templateName, String titleKey) {
        Locale locale = Locale.forLanguageTag("en");
        Context context = new Context();
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, applicationProperties.getMail().getBaseUrl());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }
    
    
    @Async
    public void sendEmailFromTemplateItem(Offer offer, User user, String templateName, String titleKey) {
        Locale locale = Locale.forLanguageTag("en");
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(OFFER, offer);
        context.setVariable(BASE_URL, applicationProperties.getMail().getBaseUrl());
      
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    /**
     * Sending activation email to user
     *
     * @param user user providing information about client
     */
    @Async
    public void sendActivationEmail(User user) {
        log.debug("Sending activation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "activationEmail", "email.activation.title");
    }

    
    
    @Async
    public void sendItemAcceptedEmail(Offer offer, User user) {
    	
    	log.debug("Sending acepting item email to '{}'", user.getEmail());
    	
    	sendEmailFromTemplateItem(offer,user, "itemAcceptedEmail", "email.itemAccepting.title");
    }
    
    
    @Async
    public void sendItemRejectedEmail(Offer offer, User user) {
    	
    	log.debug("Sending rejecting item email to '{}'", user.getEmail());
    	
    	sendEmailFromTemplateItem(offer,user, "itemRejectedEmail", "email.itemRejecting.title");
    }
    
    
    
    /**
     * Sending creation email to user
     *
     * @param user user providing information about client
     */
    @Async
    public void sendCreationEmail(User user) {
        log.debug("Sending creation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "creationEmail", "email.activation.title");
    }

    /**
     * Sending password reset mail email to user
     *
     * @param user user providing information about client
     */
    @Async
    public void sendPasswordResetMail(User user) {
        log.debug("Sending password reset email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "passwordResetEmail", "email.reset.title");
    }


    public ApplicationProperties getApplicationProperties() {
        return applicationProperties;
    }

    public void setApplicationProperties(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public JavaMailSender getJavaMailSender() {
        return javaMailSender;
    }

    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public MessageSource getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public SpringTemplateEngine getTemplateEngine() {
        return templateEngine;
    }

    public void setTemplateEngine(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }
}
