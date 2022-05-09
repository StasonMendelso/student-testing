package com.stason.testing.controller.services;

import com.stason.testing.controller.exceptions.EmailException;
import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
/**
 * It is an email-sender class, which sends email to user's login
 * @author Stanislav Hlova
 * @version 1.0
 */
public class EmailSenderService {
    private static final Logger logger = Logger.getLogger(EmailSenderService.class.getName());

    private final String userName="glovastas2004@gmail.com";
    private final String password="dyhqlheeaastdurs";
    // Get system properties
    private final Properties properties = System.getProperties();
    // Sender's email ID needs to be mentioned
    private final String from = "glovastas2004@gmail.com";

    // Assuming you are sending email from through gmails smtp
    private String host = "smtp.gmail.com";
    public EmailSenderService() {
        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

    }

    /**
     * Sends an activation code to user's login
     * @param to a user's e-mail
     * @param activationCode an activation code
     */
    public void sendActivationCode(String to, String activationCode) {

        // Recipient's email ID needs to be mentioned.
        to = "glovastas2004@gmail.com";


        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }

        });

        // Used to debug SMTP issues
        session.setDebug(false);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Online Testing project");

            // Now set the actual message
            message.setContent(
                    "<h1>This is recovery letter.</h1>" +
                            "<h3>Your code activation:</h3>" +
                            "<h1>" + activationCode + "</h1>",
                    "text/html");

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            logger.warn("Can't send letter to email",mex);
            throw new EmailException("Sorry, can't send letter to email", mex);
        }
    }
    /**
     * Sends an activation link e to user's login
     * @param to a user's e-mail
     * @param activationLink an activation limk
     */
    public void sendActivationPasswordLink(String to, String activationLink) {

        // Recipient's email ID needs to be mentioned.
        to = "glovastas2004@gmail.com";


        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(userName, password);

            }

        });

        // Used to debug SMTP issues
        session.setDebug(false);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Online Testing project");

            // Now set the actual message
            message.setContent(
                    "<h1>This is letter for changing your password.</h1>" +
                            "<h3>Your activation link to change password:</h3>" +
                            "<a href=\""+activationLink+"\">" + activationLink + "</a>",
                    "text/html");

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            logger.warn("Can't send letter to email",mex);
            throw new EmailException("Sorry, can't send letter to email", mex);
        }
    }
}
