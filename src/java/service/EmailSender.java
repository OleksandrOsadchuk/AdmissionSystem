/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author pear
 */
public class EmailSender {
    final String username="mcit.java@gmail.com";
final String password="zz123321";
    
    public String sendMail(String mailTo, String content) {

        Properties pps = new Properties();
        pps.put("mail.smtp.auth", "true");
        pps.put("mail.smtp.starttls.enable", "true");
        pps.put("mail.smtp.host", "smtp.gmail.com");
        pps.put("mail.smtp.port", "587");

        Authenticator ar = new Authenticator(){
                @Override
                protected PasswordAuthentication getPasswordAuthentication(){
                    return new PasswordAuthentication(username, password);
                } 
            };

        Session session = Session.getInstance(pps, ar);

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("StudentAdmissionSystem@system.com"));
            msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(mailTo));
            msg.setSubject("List from Admission System");
            msg.setContent(content, "text/html; charset=utf-8");
            //msg.setText(content);
            Transport.send(msg);

        }catch(MessagingException e) {
            throw new RuntimeException(e);
        }
        
        return "sent to: "+mailTo;
    }
    
}
