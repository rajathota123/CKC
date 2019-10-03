package com.cisco.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

import com.cisco.base.BaseTest;


public class ExtractURLFromMail extends BaseTest{

//    public static void main(String[] args) {
//
//        ExtractURLFromMail gmail = new ExtractURLFromMail();
//        gmail.read();
//
//    }	
	public static String latestURL="";

    public static String read() {


        Properties props = new Properties();

        try {
            Properties properties = new Properties();
            properties.put("mail.smtp.host","smtp.gmail.com");
            properties.put("mail.smtp.socketFactory.port","465");
            properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
            properties.put("mail.smtp.auth","true");
            properties.put("mail.smtp.port","465");
            Session session = Session.getDefaultInstance(properties, null);

            Store store = session.getStore("imaps");
            store.connect("smtp.gmail.com", "rajeevk6cisco@gmail.com", "Cisco@321");

            Folder inbox = store.getFolder("inbox");
            inbox.open(Folder.READ_ONLY);
            int messageCount = inbox.getMessageCount();

            System.out.println("Total Messages:- " + messageCount);

            Message[] messages = inbox.getMessages();


            System.out.println("------------------------------");

            for (int i = 0; i < 1000; i++) {

                Message message=messages[messages.length-i-1];
                
if(setConfig("gmail").contains(message.getFrom()[0].toString())){
                System.out.println("---------------------------------");
                System.out.println("Mail Subject:- " + message.getSubject());
                //System.out.println("Subject: " + message.getSubject());
                //System.out.println("From: " + message.getFrom()[0]);
              System.out.println("Text: " + message.getContent().toString());

                List<String> urlList = new ArrayList<String>();
                urlList=ExtractURLFromMail.extractUrls(message.getContent().toString());

                for (int j=0;j < urlList.size();j++)
                {       
                        System.out.println("Number of URL's --> "+urlList.size());
                         System.out.println("URL is -->> "+urlList.get(j));
                         latestURL= urlList.get(j);
                }

                break;

                }
            }
           
            inbox.close(true);
            store.close();
           

        } catch (Exception e) {
            e.printStackTrace();
        }
        return latestURL;
		
    }


    public static List<String> extractUrls(String text)
    {
        List<String> containedUrls = new ArrayList<String>();
        String urlRegex =
"((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(text);

        while (urlMatcher.find())
        {
            containedUrls.add(text.substring(urlMatcher.start(0),
                    urlMatcher.end(0)));
        }

        return containedUrls;
    }
    
    public static String readmailcontent() {


        Properties props = new Properties();

        try {
            Properties properties = new Properties();
            properties.put("mail.smtp.host","smtp.gmail.com");
            properties.put("mail.smtp.socketFactory.port","465");
            properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
            properties.put("mail.smtp.auth","true");
            properties.put("mail.smtp.port","465");
            Session session = Session.getDefaultInstance(properties, null);

            Store store = session.getStore("imaps");
            store.connect("smtp.gmail.com", "rajeevk6cisco@gmail.com", "Cisco@321");

            Folder inbox = store.getFolder("inbox");
            inbox.open(Folder.READ_ONLY);
            int messageCount = inbox.getMessageCount();

            System.out.println("Total Messages:- " + messageCount);

            Message[] messages = inbox.getMessages();
            System.out.println("Messages:- " + messages.length);
            //System.out.println("Messages:- " + messages.length);
            


            System.out.println("------------------------------");

            for (int i = 0; i < 1; i++) {

                Message message=messages[messages.length-i-1];
                
              if(setConfig("Activitigmail").contains(message.getFrom()[0].toString())){
                System.out.println("---------------------------------");
                System.out.println("Mail Subject:- " + message.getSubject());
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);
              System.out.println("Text: " + message.getReceivedDate());
              latestURL=message.getSubject()+ " : "+message.getReceivedDate();
               
                }
            }
           
            inbox.close(true);
            store.close();
           

        } catch (Exception e) {
            e.printStackTrace();
        }
        return latestURL;
		
    }





}
