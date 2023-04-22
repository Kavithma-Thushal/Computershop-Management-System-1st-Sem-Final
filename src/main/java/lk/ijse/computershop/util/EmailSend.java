package lk.ijse.computershop.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSend {

    public static void mail() {

        // Sender's email address and password
        String senderEmail = "fightlife241@gmail.com";
        String senderPassword = "genbtmifwmmkcrsx";

        // Recipient's email address
        String recipientEmail = "kaviyabro9007@gmail.com";

        // Email properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Create a session with the sender's email and password
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Create a new email message
            Message message = new MimeMessage(session);

            // Set the sender, recipient, subject, and text of the email message
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Bashi Computer Shop");
            message.setText("your order placed successfully...!");

            // Send the email message
            Transport.send(message);

        } catch (MessagingException e) {
            System.out.println("Email Not Send");
        }
    }
}

