package com.isa.instaticketapi.service;

import com.isa.instaticketapi.InstaticketapiApplication;
import com.isa.instaticketapi.config.ApplicationProperties;
import com.isa.instaticketapi.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.io.ByteArrayOutputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = InstaticketapiApplication.class)
public class MailServiceTest {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Spy
    private JavaMailSenderImpl javaMailSender;

    @Captor
    private ArgumentCaptor messageCaptor;

    @Autowired
    private MailService mailService;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        doNothing().when(javaMailSender).send(any(MimeMessage.class));
        mailService = new MailService();
        mailService.setApplicationProperties(applicationProperties);
        mailService.setJavaMailSender(javaMailSender);
        mailService.setMessageSource(messageSource);
        mailService.setTemplateEngine(springTemplateEngine);
    }

    @Test
    public void testSendEmail() throws Exception {
        mailService.sendEmail("nemanja@test.com", "testSubject", "testCont", false, false);
        verify(javaMailSender).send((MimeMessage) messageCaptor.capture());
        MimeMessage message = (MimeMessage) messageCaptor.getValue();
        assertThat(message.getSubject()).isEqualTo("testSubject");
        assertThat(message.getContent().toString()).isEqualTo("testCont");
        assertThat(message.getAllRecipients()[0].toString()).isEqualTo("nemanja@test.com");
        assertThat(message.getFrom()[0].toString()).isEqualTo("InstaTicket");
        assertThat(message.getContent().getClass()).isEqualTo(String.class);
        assertThat(message.getDataHandler().getContentType()).isEqualTo("text/plain; charset=UTF-8");
    }

    @Test
    public void sendHtmlEmail() throws Exception {
        mailService.sendEmail("nemanja@test.com", "testSubject", "testCont", false, true);
        verify(javaMailSender).send((MimeMessage) messageCaptor.capture());
        MimeMessage message = (MimeMessage) messageCaptor.getValue();
        assertThat(message.getSubject()).isEqualTo("testSubject");
        assertThat(message.getContent().toString()).isEqualTo("testCont");
        assertThat(message.getAllRecipients()[0].toString()).isEqualTo("nemanja@test.com");
        assertThat(message.getFrom()[0].toString()).isEqualTo("InstaTicket");
        assertThat(message.getContent().getClass()).isEqualTo(String.class);
        assertThat(message.getDataHandler().getContentType()).isEqualTo("text/html;charset=UTF-8");
    }

    @Test
    public void testSendMultipartEmail() throws Exception {
        mailService.sendEmail("nemanja@test.com", "testSubject", "testCont", true, false);
        verify(javaMailSender).send((MimeMessage) messageCaptor.capture());
        MimeMessage message = (MimeMessage) messageCaptor.getValue();
        assertThat(message.getSubject()).isEqualTo("testSubject");
        assertThat(message.getAllRecipients()[0].toString()).isEqualTo("nemanja@test.com");
        assertThat(message.getFrom()[0].toString()).isEqualTo("InstaTicket");
        assertThat(message.getContent()).isInstanceOf(Multipart.class);
        MimeMultipart mp = (MimeMultipart) message.getContent();
        MimeBodyPart part = (MimeBodyPart) ((MimeMultipart) mp.getBodyPart(0).getContent()).getBodyPart(0);
        ByteArrayOutputStream aos = new ByteArrayOutputStream();
        part.writeTo(aos);
        assertThat(aos.toString()).isEqualTo("\r\ntestCont");
        assertThat(part.getDataHandler().getContentType()).isEqualTo("text/plain; charset=UTF-8");
    }
    //TODO check this
//    @Test
//    public void testCreationEmail() throws Exception {
//        User user = new User();
//        user.setUsername("TESTER");
//        user.setEmail("tester@test.com");
//        mailService.sendCreationEmail(user);
//        verify(javaMailSender).send((MimeMessage) messageCaptor.capture());
//        MimeMessage message = (MimeMessage) messageCaptor.getValue();
//        assertThat(message.getAllRecipients()[0].toString()).isEqualTo(user.getEmail());
//        assertThat(message.getFrom()[0].toString()).isEqualTo("InstaTickett");
//        assertThat(message.getContent().toString()).isNotEmpty();
//        assertThat(message.getDataHandler().getContentType()).isEqualTo("text/html; charset=UTF-8");
//    }


    //TODO Micko napisi test za HTML + MULTIPART
    //TODO Micko napisi test za aktivacioni email
    //TODO Milica napisi test za restartovanje sifre
    //TODO Milica napisi test za slanje maila kroz template


}
