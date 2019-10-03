package com.cisco.util;
import java.security.Key;
import java.util.Date;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class SendEmail {
	String file= System.getProperty("user.dir") + "\\test-output\\"+"emailable-report.html";

//  public static void main(String[] args) {
	public void sentemail(String teststatus){
		System.out.println("file path " +file);
  final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
  // Get a Properties object
     Properties props = System.getProperties();
     props.setProperty("mail.smtp.host", "smtp.gmail.com");
     props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
     props.setProperty("mail.smtp.socketFactory.fallback", "false");
     props.setProperty("mail.smtp.port", "465");
     props.setProperty("mail.smtp.socketFactory.port", "465");
     props.put("mail.smtp.auth", "true");
     props.put("mail.debug", "true");
     props.put("mail.store.protocol", "pop3");
     props.put("mail.transport.protocol", "smtp");
     final String username = "rajeevcim2017@gmail.com";//give correct gmail id
     final String password = "Cisco_123";  //give correct password
     
     //encrypt password:
     
//     String key = "Cisco_123"; // 128 bit key
//     // Create key and cipher
//     Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
//     Cipher cipher = Cipher.getInstance("AES");
//     // encrypt the text
//     cipher.init(Cipher.ENCRYPT_MODE, aesKey);
//     byte[] encrypted = cipher.doFinal(text.getBytes());
//     System.err.println(new String(encrypted));
     try{
     Session session = Session.getDefaultInstance(props, 
                          new Authenticator(){
                             protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                             }});

   // -- Create a new message --
     Message msg = new MimeMessage(session);

  // -- Set the FROM and TO fields --
     msg.setFrom(new InternetAddress("rajeevcim2017@gmail.com"));
     msg.setRecipients(Message.RecipientType.TO, 
                      InternetAddress.parse("rajeevcim2017@gmail.com",false));
     msg.setSubject("SouthBoundServer_Result");
     msg.setText(teststatus);
     msg.setSentDate(new Date());
     Transport.send(msg);
     System.out.println("Message sent.");
  }catch (MessagingException e){ System.out.println("Erreur d'envoi, cause: " + e);}
  }
}