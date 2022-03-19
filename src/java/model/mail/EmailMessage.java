	package model.mail;
	
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
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
import javax.mail.util.ByteArrayDataSource;



public class EmailMessage {
    public static final String host = "smtp.gmail.com";
    public static final String sender_email = "2022pfc.g5";
    public static final String sender_password = "2022pfc2022";

    private MimeMessage message;
    
//    private String recipient;
//    private String Subject;
//    private String body;
//    
//    private boolean hasFileAttach;
    

    public EmailMessage(String recipient,String Subject,String body) throws MessagingException {

//    	this.recipient = recipient;
//    	this.Subject = Subject;
//    	this.body = body;
//    	hasFileAttach=false;

    	Session session = authenticate();
        message = new MimeMessage(session);

        message.setFrom(new InternetAddress(sender_email));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        message.setSubject(Subject);
        message.setText(body);
    }


    public EmailMessage(String recipient,String Subject,String body,String filepath) throws MessagingException, IOException{

//    	this.recipient = recipient;
//    	this.Subject = Subject;
//    	this.body = body;
//    	hasFileAttach=true;
    	
    	Session session = authenticate();
        message = new MimeMessage(session);
        
        message.setFrom(new InternetAddress(sender_email));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        message.setSubject(Subject);

        Multipart multipart = new MimeMultipart();
        MimeBodyPart attachmentPart = new MimeBodyPart();
        MimeBodyPart textPart = new MimeBodyPart();

        File f =new File(filepath);

        attachmentPart.attachFile(f);
        textPart.setText(body);

        multipart.addBodyPart(textPart);
        multipart.addBodyPart(attachmentPart);

        message.setContent(multipart);        
    }

    public EmailMessage(String recipient,String Subject,String body,File file) throws MessagingException, IOException{

//    	this.recipient = recipient;
//    	this.Subject = Subject;
//    	this.body = body;
//    	hasFileAttach = true;
    	
    	Session session = authenticate();
        message = new MimeMessage(session);
        
        message.setFrom(new InternetAddress(sender_email));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        message.setSubject(Subject);

        Multipart multipart = new MimeMultipart();
        MimeBodyPart attachmentPart = new MimeBodyPart();
        MimeBodyPart textPart = new MimeBodyPart();


        attachmentPart.attachFile(file);
        textPart.setText(body);

        multipart.addBodyPart(textPart);
        multipart.addBodyPart(attachmentPart);
        
        message.setContent(multipart);        
    }
    
    public EmailMessage(String recipient,String Subject,String body,byte[] buffer) throws MessagingException, IOException{

    	Session session = authenticate();
        message = new MimeMessage(session);
        
        message.setFrom(new InternetAddress(sender_email));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        message.setSubject(Subject);

        Multipart multipart = new MimeMultipart();
        MimeBodyPart attachmentPart = new MimeBodyPart();
        MimeBodyPart textPart = new MimeBodyPart();

        // Pass PDF file (Buffer) as byte array 
        attachmentPart.setDataHandler(new DataHandler(new ByteArrayDataSource(buffer, "application/pdf")));
        attachmentPart.setFileName("Invoice.pdf");
        textPart.setText(body);

        multipart.addBodyPart(textPart);
        multipart.addBodyPart(attachmentPart);
        
        message.setContent(multipart);            
    }

   
    public void send() throws MessagingException {
        Transport.send(message);
    }

    
    private Session authenticate() {
        Properties properties = System.getProperties();

        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        return Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender_email,sender_password);
            }
        });
        
    }
    
}