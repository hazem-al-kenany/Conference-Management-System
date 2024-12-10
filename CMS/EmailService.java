import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage;
import javax.mail.Session;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.io.File;

public class EmailService {
    private String senderEmail; //email of the sender
    private String senderPassword; //password of the sender's email

    //constructor to initialize sender email and password
    public EmailService(String senderEmail, String senderPassword) {
        this.senderEmail = senderEmail;
        this.senderPassword = senderPassword;
    }

    //send a simple email without attachments
    public void sendEmail(String recipientEmail, String subject, String body) {
        Properties properties = new Properties(); //email configuration
        properties.put("mail.smtp.auth", "true"); //enable authentication
        properties.put("mail.smtp.starttls.enable", "true"); //enable TLS encryption
        properties.put("mail.smtp.host", "smtp.gmail.com"); //gmail smtp server
        properties.put("mail.smtp.port", "587"); //gmail smtp port

        //authenticate using sender credentials
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session); //create email
            message.setFrom(new InternetAddress(senderEmail)); //set sender
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail)); //set recipient
            message.setSubject(subject); //set subject
            message.setText(body); //set body

            Transport.send(message); //send the email
            System.out.println("Email sent to: " + recipientEmail); //confirmation
        } catch (MessagingException e) {
            System.err.println("Error sending email: " + e.getMessage()); //error handling
        }
    }

    //send an email with an attachment
    public void sendEmailWithAttachment(String recipientEmail, String subject, String body, String attachmentPath) {
        Properties properties = new Properties(); //email configuration
        properties.put("mail.smtp.auth", "true"); //enable authentication
        properties.put("mail.smtp.starttls.enable", "true"); //enable TLS encryption
        properties.put("mail.smtp.host", "smtp.gmail.com"); //gmail smtp server
        properties.put("mail.smtp.port", "587"); //gmail smtp port

        //authenticate using sender credentials
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session); //create email
            message.setFrom(new InternetAddress(senderEmail)); //set sender
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail)); //set recipient
            message.setSubject(subject); //set subject

            //create email body
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(body); //set email body

            //attach file
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(attachmentPath); //attach file path

            //combine text and attachment
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart); //add text
            multipart.addBodyPart(attachmentPart); //add attachment

            message.setContent(multipart); //set content

            Transport.send(message); //send the email
            System.out.println("Email sent to: " + recipientEmail); //confirmation
        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage()); //error handling
        }
    }
}