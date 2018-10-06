package com.snpm.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.snpm.test.TestBase;

public class Util {
	static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	static DateFormat tf = new SimpleDateFormat("hh-mm-ss");

	private static final String date = df.format(new Date());
	private static final String time = tf.format(new Date());

	public Util() {

	}


	public static String getDate() {
		return date;
	}

	public static String getTime() {
		return time;
	}
	
	
	public static void sendEmail() {
		Properties props = new Properties();
		props.put("mail.smtp.host", TestBase.getProp().getProperty("mail.smtp.host"));
		props.put("mail.smtp.auth", TestBase.getProp().getProperty("mail.smtp.auth"));
		props.put("mail.smtp.port", TestBase.getProp().getProperty("mail.smtp.port"));

		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(TestBase.getProp().getProperty("mail.smtp.username"),"");
				}
			});


		Retry retry = new Retry();
		while (retry.shouldRetry()) {
		try {
		         // Create a default MimeMessage object.
		         Message message = new MimeMessage(session);

		         // Set From: header field of the header.
		         message.setFrom(new InternetAddress(TestBase.getProp().getProperty("mail.smtp.from")));

		         // Set To: header field of the header.
		         message.addRecipients(Message.RecipientType.TO,
		            InternetAddress.parse(TestBase.getProp().getProperty("mail.smtp.to")));

		         // Set Subject: header field
		         message.setSubject("Automation Report "+TestBase.getProp().getProperty("env"));

		         // Create the message part
		         BodyPart messageBodyPart = new MimeBodyPart();

		         // Now set the actual message
		         messageBodyPart.setText("Please find the attached automation report for "+TestBase.getProp().getProperty("env")+" environment");

		         // Create a multipar message
		         Multipart multipart = new MimeMultipart();

		         // Set text message part
		         multipart.addBodyPart(messageBodyPart);

		         // Part two is attachment
		         messageBodyPart = new MimeBodyPart();
		         String filepath = TestBase.workingDir+"\\testreport\\report_"  + Util.getDate() + "_" + Util.getTime()+ ".html";
		         DataSource source = new FileDataSource(filepath);
		         messageBodyPart.setDataHandler(new DataHandler(source));
		         messageBodyPart.setFileName("AutomationReport_"+TestBase.getProp().getProperty("env")+".html");
		         multipart.addBodyPart(messageBodyPart);

		         // Send the complete message parts
		         message.setContent(multipart);
		         
		         // Send message
		         Transport.send(message);

		         System.out.println("Sent message successfully....");
		         break;
		  
		      } catch (MessagingException e) {
		    	  try {
		    		  
		    		  	System.out.println("Oppss something goes wrong. Retrying sending email. Wait for some time...............");
						retry.errorOccured();
					} catch (Exception e1) {
						System.out.println("error occured while retrying");
						throw new RuntimeException("Runtime Exception", e1);
					}
		      }
		}
	}

}
