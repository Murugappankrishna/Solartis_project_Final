package org.murugappan.service;


import org.murugappan.DAO.ProductsDAO;
import org.murugappan.DAO.ProductsImpl;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;
// Service Class Responsible For  Sending Mail
public class MailService {
	//objects declaration & initialization
    static Session newSession = null;
    MimeMessage mimeMessage = null;
    ProductsDAO productsImplementation = new ProductsImpl();
// Method To Setup Mail Properties
    private static void setupServerProperties() {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");
        newSession = Session.getDefaultInstance(properties, null);
    }
// Method To Send  Email
    void sendMail() {
        MailService mail = new MailService();
        MailService.setupServerProperties();
        mail.draftEmail();
        mail.sendEmail();
    }
//Method To Attach The Data 
    private void draftEmail() {
        String emailRecipients = "murugappan_m@solartis.com";
        String emailSubject = "List Of Products Out Of Stock";
        String lowStockProducts = productsImplementation.showLowStockItems();
        String emailBody = "<h1><b>The Following Products Are Running Out Of Stock </b></h1> <h2><i>Please Restock It!</i></h2>" + lowStockProducts + "";
        mimeMessage = new MimeMessage(newSession);
        try {
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailRecipients));
            mimeMessage.setSubject(emailSubject);
            MimeMultipart multipart = new MimeMultipart();
            MimeBodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(emailBody, "text/html");
            multipart.addBodyPart(bodyPart);
            mimeMessage.setContent(multipart);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
// Method To Send Email
    private void sendEmail() {
        String fromUser = "sriharishr105@gmail.com";
        String fromUserPassword = "czwympvajwawowbh";
        String emailHost = "smtp.gmail.com";
        Transport transport = null;
        try {
            transport = newSession.getTransport("smtp");
            transport.connect(emailHost, 587, fromUser, fromUserPassword);
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}

