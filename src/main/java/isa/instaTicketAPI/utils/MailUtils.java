package isa.instaTicketAPI.utils;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.CharEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class MailUtils {

	@Autowired
	JavaMailSender javaMailService;

	@Value("${email.username}")
	private String username;

	@Autowired 
    private TemplateEngine templateEngine;

	public void sendVerificationEmail(String to, String emailVerificationCode) {
		
		String content = "Your verification code is: " + emailVerificationCode;
		Context context = new Context();
        context.setVariable("message", emailVerificationCode);
        String text = templateEngine.process("emailTemplate", context);
        String subject = "Verification your account";
		sendEmail(to, subject, text);
	}

	@Async
	public void sendEmail(String to, String subject, String content) {

		final MimeMessage mimeMessage = javaMailService.createMimeMessage();
		try {
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, CharEncoding.UTF_8);
			message.setTo(to);
			message.setFrom(username);
			message.setSubject(subject);
			message.setText(content, true);
			javaMailService.send(mimeMessage);
		} catch (Throwable e) {
		}
	}
	
}
