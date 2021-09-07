package com.forsys;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

public class Email {
	Logger logger = Logger.getLogger(Email.class);
	public static String toRecepients = "praveen.kothapalli@forsysinc.com,srikanth.bodipudi@forsysinc.com,pranita.kulkarni@forsysinc.com,polamma.katta@forsysinc.com,arun.ellangi@forsysinc.com";
	public static String from = "praveen.kothapalli@forsysinc.com";
	public static String errorToRecepients = "polamma.katta@forsysinc.com,praveen.kothapalli@forsysinc.com,srikanth.bodipudi@forsysinc.com,pranita.kulkarni@forsysinc.com,arun.ellangi@forsysinc.com";
	String subject=null; 
	String body = null; 
	String email_filepath = null;
	
	Email(){
		
	}
	
	Email(String subject, String body, String email_filepath){
		this.subject = subject;
		this.body = body;
		this.email_filepath = email_filepath;
	}

	public void sendEmail() throws IOException {

		Session session = mailConfiguration();
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toRecepients));
			message.setSubject(subject);
//			message.setText("The provided file was errored out. Please find the attachment for more details");
			BodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1
					.setText(body );

			MimeBodyPart messageBodyPart2 = new MimeBodyPart();
			DataSource source = new FileDataSource(email_filepath);
			messageBodyPart2.setDataHandler(new DataHandler(source));
			messageBodyPart2.setFileName(new File(email_filepath).getName());

//			MimeBodyPart messageBodyPart3 = new MimeBodyPart();
//			DataSource source3 = new FileDataSource(TestRunner.loggerFilePath);
//			messageBodyPart3.setDataHandler(new DataHandler(source3));
//			messageBodyPart3.setFileName(TestRunner.loggerFilePath);

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart1);
			multipart.addBodyPart(messageBodyPart2);
//			multipart.addBodyPart(messageBodyPart3);
			message.setContent(multipart);
			Transport.send(message);
			logger.info("\n mail sent successfully");
			System.out.println("Done");
		} catch (MessagingException ex) {
			throw new RuntimeException(ex);

		}

	}

	public void sendErrorEmail(String logmessage, String screenshot) throws IOException {

		Session session = mailConfiguration();
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(errorToRecepients));
			message.setSubject("Script Error out");
//			message.setText("The provided file was processed successfully. Please find the attachment");
			BodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setText(logmessage);

			MimeBodyPart messageBodyPart2 = new MimeBodyPart();
			DataSource source = new FileDataSource(screenshot);
			messageBodyPart2.setDataHandler(new DataHandler(source));
			messageBodyPart2.setFileName(new File(screenshot).getName());

//			MimeBodyPart messageBodyPart3 = new MimeBodyPart();
//			DataSource source3 = new FileDataSource(TestRunner.loggerFilePath);
//			messageBodyPart3.setDataHandler(new DataHandler(source3));
//			messageBodyPart3.setFileName(TestRunner.loggerFilePath);

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart1);
			multipart.addBodyPart(messageBodyPart2);
//			multipart.addBodyPart(messageBodyPart3);
			message.setContent(multipart);
			Transport.send(message);
			logger.info("\n mail sent successfully");
			System.out.println("Done");
		} catch (MessagingException ex) {
			throw new RuntimeException(ex);

		}

	}

	public static Session mailConfiguration() {
		Properties p = new Properties();
		p.put("mail.smtp.host", "smtp.gmail.com");
		p.put("mail.smtp.socketFactory.port", "465");
		p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		p.put("mail.smtp.auth", "true");
		p.put("mail.smtp.port", "465");
		Session session = Session.getDefaultInstance(p, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("noreply@forsysinc.com", "qr3*y5WL2");
			}
		});
		return session;
	}
	
	

}
