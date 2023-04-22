package lk.ijse.computershop.util;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailSend {
    public static void mail() throws MessagingException{
        String senderEmail = "kaviyabro9007@gmail.com";
        String senderPassword = "eydlyipacjvbfmfx";
        String recipientEmail = "kavithmathushal451@gmail.com";
        String subject = "Bashi Computer Shop";
        String body = "Your order placed successfully...!";

        Session session = Session.getInstance(getEmailProperties(), getAuthenticator(senderEmail, senderPassword));

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
        message.setSubject(subject);

        // Create a multipart message to hold the text and attachment
        Multipart multipart = new MimeMultipart();

        // Add the message body as a text part
        BodyPart textPart = new MimeBodyPart();
        textPart.setText(body);
        multipart.addBodyPart(textPart);

        // Add the attachment
        /*String filePath = "/D:\\IJSE\\Workspace\\bashicomputershop\\src\\main\\resources\\assets\\img\\login.jpeg";
        File attachment = new File(filePath);
        if (attachment.exists()) {
            BodyPart attachmentPart = new MimeBodyPart();
            DataSource source = new FileDataSource(filePath);
            attachmentPart.setDataHandler(new DataHandler(source));
            attachmentPart.setFileName(attachment.getName());
            multipart.addBodyPart(attachmentPart);
        }*/

        // Set the message content to the multipart message
        message.setContent(multipart);

        // Send the email message
        Transport.send(message);
    }

    private static Properties getEmailProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        return props;
    }

    private static Authenticator getAuthenticator(String username, String password) {
        return new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };
    }
}
