package com.bol.core.utils;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class SendEmailNotifications {
	
	
	public void sendSimpleMessage(String emailId,String subject,String content) throws UnsupportedEncodingException{
	final String username = "contact.bolstart@gmail.com";
	final String password = "BolStart@16";

	/*Properties props = new Properties();
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.smtp.host", "smtp.gmail.com");
	props.put("mail.smtp.port", "587");*/
	
	Properties props = new Properties();    
    props.put("mail.smtp.host", "smtp.gmail.com");    
    props.put("mail.smtp.socketFactory.port", "465");    
    props.put("mail.smtp.socketFactory.class",    
              "javax.net.ssl.SSLSocketFactory");    
    props.put("mail.smtp.auth", "true");    
    props.put("mail.smtp.port", "465");    

	Session session = Session.getInstance(props,
	  new javax.mail.Authenticator() {
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
		}
	  });

	try {

		Message message = new MimeMessage(session);
		
		MimeMessageHelper helper;
		message.setFrom(new InternetAddress(username,"BolStart Notifications"));
		message.setRecipients(Message.RecipientType.TO,
			InternetAddress.parse(emailId));
		message.setSubject(subject);
		//message.setContent(content,"text/html");
		message.setContent(StringEscapeUtils.unescapeJava(content), "text/html; charset=UTF-8");
		//message.setText(content);
		Transport.send(message);
	} catch (MessagingException e) {
		throw new RuntimeException(e);
	}
   }
	
}