/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordb.email;


import com.avbravo.jmoordb.util.JmoordbUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author avbravo
 */

public class JmoordbEmailSender implements Serializable{

    Boolean texthtml = false;

    /**
     * Creates a new instance of EnviarEmail
     */
    public JmoordbEmailSender() {
    }
// <editor-fold defaultstate="collapsed" desc="example()"> 

    private String example() {
        try {

            final String username = "myemail@gmail.com";
            final String password = "mypassword";

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("emisor@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("remitente@gmail.com"));
            message.setSubject("Alert");
            message.setText("Existe una plaga,"
                    + "\n\n No spam to my email, please!");

            Transport.send(message);

            System.out.println("Done");

        } catch (Exception ex) {
            JmoordbUtil.infoDialog("error", ex.getLocalizedMessage());
            System.out.println("error " + ex.getLocalizedMessage());
        }
        return null;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" send(String emaildestinatario, String titulo, String mensaje,    String emailremitente, String passwordremitente)"> 
    /**
     *
     * @param emaildestinatario
     * @param titulo
     * @param mensaje
     * @param emailremitente
     * @param passwordremitente
     * @return
     */
    public Boolean send(String emaildestinatario, String titulo, String mensaje,
            String emailremitente, String passwordremitente, Boolean... typehtml) {
        Boolean sending = false;
        try {

            Boolean texthtml = false;
            if (typehtml.length != 0) {
                texthtml = typehtml[0];

            }
            final String username = emailremitente;
            final String password = passwordremitente;
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailremitente));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(emaildestinatario));
            message.setSubject(titulo);
//            message.setText(mensaje);

            if (texthtml) {
                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                mimeBodyPart.setContent(mensaje, "text/html");
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(mimeBodyPart);
                message.setContent(multipart);
            } else {
                message.setText(mensaje);
            }

            Transport.send(message);
            sending = true;
        } catch (Exception ex) {
            JmoordbUtil.errorMessage("send() " + ex.getLocalizedMessage());
            System.out.println("send() " + ex.getLocalizedMessage());
        }
        return sending;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" send(String[] to, String[] cc, String[] bcc,, String titulo, String mensaje,    String emailremitente, String passwordremitente)"> 
    /**
     *
     * @param to
     * @param cc
     * @param bcc
     * @param titulo
     * @param mensaje
     * @param emailremitente
     * @param passwordremitente
     * @return
     */
    public Boolean send(String[] to, String[] cc, String[] bcc, String titulo, String mensaje,
            String emailremitente, String passwordremitente, Boolean... typehtml) {
        Boolean sending = false;
        try {
            Boolean texthtml = false;
            if (typehtml.length != 0) {
                texthtml = typehtml[0];

            }
            final String username = emailremitente;
            final String password = passwordremitente;
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            InternetAddress[] toAddress = new InternetAddress[to.length];
            // To get the array of toaddresses
            for (int i = 0; i < to.length; i++) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            InternetAddress[] ccAddress = new InternetAddress[cc.length];

            // To get the array of ccaddresses
            for (int i = 0; i < cc.length; i++) {
                ccAddress[i] = new InternetAddress(cc[i]);
            }
            InternetAddress[] bccAddress = new InternetAddress[bcc.length];

            // To get the array of bccaddresses
            for (int i = 0; i < bcc.length; i++) {
                bccAddress[i] = new InternetAddress(bcc[i]);
            }

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailremitente));
            // Set To: header field of the header.
            for (int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }
            // Set cc: header field of the header.
            for (int i = 0; i < ccAddress.length; i++) {
                message.addRecipient(Message.RecipientType.CC, ccAddress[i]);
            }
            // Set bcc: header field of the header.
            for (int i = 0; i < bccAddress.length; i++) {
                message.addRecipient(Message.RecipientType.BCC, bccAddress[i]);
            }

            message.setSubject(titulo);
//            message.setText(mensaje);
            message.setSentDate(new Date());

            if (texthtml) {
                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                mimeBodyPart.setContent(mensaje, "text/html");
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(mimeBodyPart);
                message.setContent(multipart);
            } else {
                message.setText(mensaje);
            }
            Transport.send(message);
            sending = true;
        } catch (Exception ex) {
            JmoordbUtil.errorMessage("send() " + ex.getLocalizedMessage());
            System.out.println("send() " + ex.getLocalizedMessage());
        }
        return sending;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean sendOutlook(String emaildestinatario, String titulo, String mensaje,           String emailremitente, String passwordremitente)"> 
    /**
     *
     * @param emaildestinatario
     * @param titulo
     * @param mensaje
     * @param emailremitente
     * @param passwordremitente
     * @return
     */
    public Boolean sendOutlook(String emaildestinatario, String titulo, String mensaje,
            String emailremitente, String passwordremitente, Boolean... typehtml) {
        Boolean sending = false;
        try {
            Boolean texthtml = false;
            if (typehtml.length != 0) {
                texthtml = typehtml[0];

            }
            final String username = emailremitente;
            final String password = passwordremitente;

//        
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.office365.com");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailremitente));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(emaildestinatario));
            message.setSubject(titulo);
//            message.setText(mensaje);
            message.setSentDate(new Date());

            if (texthtml) {
                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                mimeBodyPart.setContent(mensaje, "text/html");
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(mimeBodyPart);
                message.setContent(multipart);
            } else {
                message.setText(mensaje);
            }
            Transport.send(message);
            sending = true;
        } catch (Exception ex) {
            JmoordbUtil.errorMessage("send() " + ex.getLocalizedMessage());
            System.out.println("send() " + ex.getLocalizedMessage());
        }
        return sending;
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Boolean sendOutlook(String[] to, String[] cc, String[] bcc, String titulo, String mensaje,  String emailremitente, String passwordremitente)"> 

    /**
     * String[] to = { "xxxx@gmail.com" }; // list of recipient email addresses
     * String[] cc={ "xxxxt@gmail.com" }; String[] bcc={ "sxxxx@gmail.com" };
     *
     * @param to
     * @param cc
     * @param bcc
     * @param titulo
     * @param mensaje
     * @param emailremitente
     * @param passwordremitente
     * @return
     */
    public Boolean sendOutlook(String[] to, String[] cc, String[] bcc, String titulo, String mensaje,
            String emailremitente, String passwordremitente, Boolean... typehtml) {
        Boolean sending = false;
        try {
            Boolean texthtml = false;
            if (typehtml.length != 0) {
                texthtml = typehtml[0];

            }
            final String username = emailremitente;
            final String password = passwordremitente;
            //cc, bcc, to multiples
            InternetAddress[] toAddress = new InternetAddress[to.length];
            // To get the array of toaddresses
            for (int i = 0; i < to.length; i++) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            InternetAddress[] ccAddress = new InternetAddress[cc.length];

            // To get the array of ccaddresses
            for (int i = 0; i < cc.length; i++) {
                ccAddress[i] = new InternetAddress(cc[i]);
            }
            InternetAddress[] bccAddress = new InternetAddress[bcc.length];

            // To get the array of bccaddresses
            for (int i = 0; i < bcc.length; i++) {
                bccAddress[i] = new InternetAddress(bcc[i]);
            }

//        
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.office365.com");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailremitente));
            // Set To: header field of the header.
            for (int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }
            // Set cc: header field of the header.
            for (int i = 0; i < ccAddress.length; i++) {
                message.addRecipient(Message.RecipientType.CC, ccAddress[i]);
            }
            // Set bcc: header field of the header.
            for (int i = 0; i < bccAddress.length; i++) {
                message.addRecipient(Message.RecipientType.BCC, bccAddress[i]);
            }

            if (texthtml) {
                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                mimeBodyPart.setContent(mensaje, "text/html");
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(mimeBodyPart);
                message.setContent(multipart);
            } else {
                message.setText(mensaje);
            }
            message.setSubject(titulo);

            message.setSentDate(new Date());

            Transport.send(message);
            sending = true;
        } catch (Exception ex) {
            JmoordbUtil.errorMessage("send() " + ex.getLocalizedMessage());
            System.out.println("send() " + ex.getLocalizedMessage());
        }
        return sending;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean send(String emaildestinatario, String titulo, String mensaje,           String emailremitente, String passwordremitente,Properties props) "> 
    /**
     *
     * @param emaildestinatario
     * @param titulo
     * @param mensaje
     * @param emailremitente
     * @param passwordremitente
     * @param props
     * @return
     */
    public Boolean send(String emaildestinatario, String titulo, String mensaje,
            String emailremitente, String passwordremitente, Properties props, Boolean... typehtml) {
        Boolean sending = false;
        try {
            Boolean texthtml = false;
            if (typehtml.length != 0) {
                texthtml = typehtml[0];

            }
            final String username = emailremitente;
            final String password = passwordremitente;

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailremitente));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(emaildestinatario));
            message.setSubject(titulo);
//            message.setText(mensaje);

            if (texthtml) {
                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                mimeBodyPart.setContent(mensaje, "text/html");
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(mimeBodyPart);
                message.setContent(multipart);
            } else {
                message.setText(mensaje);
            }

            Transport.send(message);
            sending = true;
        } catch (Exception ex) {
            JmoordbUtil.errorMessage("send() " + ex.getLocalizedMessage());
            System.out.println("send() " + ex.getLocalizedMessage());
        }
        return sending;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" Boolean send(String emaildestinatario, String titulo, String mensaje,           String emailremitente, String passwordremitente,EmailSegurityProperties emailSegurityProperties)"> 
    /**
     *
     * @param emaildestinatario
     * @param titulo
     * @param mensaje
     * @param emailremitente
     * @param passwordremitente
     * @param props
     * @return
     */
    public Boolean send(String emaildestinatario, String titulo, String mensaje,
            String emailremitente, String passwordremitente, JmoordbEmailSegurityProperties emailSegurityProperties, Boolean... typehtml) {
        Boolean sending = false;
        try {
            Boolean texthtml = false;
            if (typehtml.length != 0) {
                texthtml = typehtml[0];

            }

            final String username = emailremitente;
            final String password = passwordremitente;

            Properties props = new Properties();
            props.put("mail.smtp.auth", emailSegurityProperties.getMailSmtpAuth());
            props.put("mail.smtp.starttls.enable", emailSegurityProperties.getMailSmtpStarttlsEnable());
            props.put("mail.smtp.host", emailSegurityProperties.getMailSmtpHost());
            props.put("mail.smtp.port", emailSegurityProperties.getMailSmtpPort());
            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailremitente));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(emaildestinatario));
            message.setSubject(titulo);
//            message.setText(mensaje);

            if (texthtml) {
                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                mimeBodyPart.setContent(mensaje, "text/html");
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(mimeBodyPart);
                message.setContent(multipart);
            } else {
                message.setText(mensaje);
            }

            Transport.send(message);
            sending = true;
        } catch (Exception ex) {
            JmoordbUtil.errorMessage("send() " + ex.getLocalizedMessage());
            System.out.println("send() " + ex.getLocalizedMessage());
        }
        return sending;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean getOutlook( String mtemail, String mypassword)"> 
    public Boolean getOutlook(String myemail, String mypassword) {
        Boolean sending = false;
        try {

//            final String username = "avbravo@gmail.com";
//            final String password = "javnet180denver$";
            final String username = myemail;
            final String password = mypassword;

//        
            Properties props = new Properties();
            props.put("mail.host", "outlook.office365.com");
            props.put("mail.store.protocol", "pop3s");
            props.put("mail.pop3s.auth", "true");
            props.put("mail.pop3s.port", "995");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Store store = session.getStore("pop3s");
            store.connect();
            Folder emailFolder = store.getFolder("INBOX");

            emailFolder.open(Folder.READ_ONLY);

            // retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();
            System.out.println("messages.length---" + messages.length);

            for (int i = 0, n = messages.length; i < n; i++) {
                Message message = messages[i];
                System.out.println("---------------------------------");
                System.out.println("Email Number " + (i + 1));
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);
                System.out.println("Fecha: " + message.getSentDate());
                System.out.println("Content: " + message.getContent());
            }

            //close the store and folder objects
            emailFolder.close(false);
            store.close();
            sending = true;
        } catch (Exception ex) {
            JmoordbUtil.errorMessage("send() " + ex.getLocalizedMessage());
            System.out.println("send() " + ex.getLocalizedMessage());
        }
        return sending;
    }
    // </editor-fold>

    
    

    
    
    // <editor-fold defaultstate="collapsed" desc=" send(String emaildestinatario, String titulo, String mensaje,    String emailremitente, String passwordremitente)"> 
    /**
     *
     * @param emaildestinatario
     * @param titulo
     * @param mensaje
     * @param emailremitente
     * @param passwordremitente
     * @return
     */
   public Future<String> sendAsync(String emaildestinatario, String titulo, String mensaje,
            String emailremitente, String passwordremitente, Boolean... isTextHtml) {
       texthtml=false;
        if (isTextHtml.length != 0) {
            texthtml = isTextHtml[0];

        }
        CompletableFuture<String> completableFuture
                = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {

         send(emaildestinatario,  titulo, mensaje,  emailremitente, passwordremitente, isTextHtml);
                completableFuture.complete("enviado");

                return null;
            }
        });

        return completableFuture;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" send(String[] to, String[] cc, String[] bcc,, String titulo, String mensaje,    String emailremitente, String passwordremitente)"> 
    /**
     *
     * @param to
     * @param cc
     * @param bcc
     * @param titulo
     * @param mensaje
     * @param emailremitente
     * @param passwordremitente
     * @return
     */
   public Future<String> sendAsync(String[] to, String[] cc, String[] bcc, String titulo, String mensaje,
            String emailremitente, String passwordremitente, Boolean... isTextHtml) {
       texthtml=false;
        if (isTextHtml.length != 0) {
            texthtml = isTextHtml[0];

        }
        CompletableFuture<String> completableFuture
                = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {

          send( to,  cc,  bcc, titulo,  mensaje,emailremitente,  passwordremitente,isTextHtml);
                completableFuture.complete("enviado");

                return null;
            }
        });

        return completableFuture;
    }// </editor-fold>

    
    // <editor-fold defaultstate="collapsed" desc="Boolean send(String emaildestinatario, String titulo, String mensaje,           String emailremitente, String passwordremitente,Properties props) "> 
    /**
     *
     * @param emaildestinatario
     * @param titulo
     * @param mensaje
     * @param emailremitente
     * @param passwordremitente
     * @param props
     * @return
     */
   public Future<String> sendAsync(String emaildestinatario, String titulo, String mensaje,
            String emailremitente, String passwordremitente, Properties props, Boolean... isTextHtml) {
       texthtml=false;
        if (isTextHtml.length != 0) {
            texthtml = isTextHtml[0];

        }
        CompletableFuture<String> completableFuture
                = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {

           send(emaildestinatario,  titulo, mensaje,
             emailremitente,  passwordremitente,props, isTextHtml) ;
                completableFuture.complete("enviado");

                return null;
            }
        });

        return completableFuture;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" Boolean send(String emaildestinatario, String titulo, String mensaje,           String emailremitente, String passwordremitente,EmailSegurityProperties emailSegurityProperties)"> 
    /**
     *
     * @param emaildestinatario
     * @param titulo
     * @param mensaje
     * @param emailremitente
     * @param passwordremitente
     * @param props
     * @return
     */
public Future<String>  sendAsync(String emaildestinatario, String titulo, String mensaje,
            String emailremitente, String passwordremitente, JmoordbEmailSegurityProperties emailSegurityProperties, Boolean... isTextHtml) {
      texthtml=false;
        if (isTextHtml.length != 0) {
            texthtml = isTextHtml[0];

        }
        CompletableFuture<String> completableFuture
                = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {

           send( emaildestinatario,  titulo,  mensaje,
            emailremitente,passwordremitente, emailSegurityProperties,isTextHtml) ;
                completableFuture.complete("enviado");

                return null;
            }
        });

        return completableFuture;
    }// </editor-fold>
 
// <editor-fold defaultstate="collapsed" desc="sendOutlookAsync(String emailreceptor, String titulo, String mensaje, String emailemisor, String passwordemisor)">
    /**
     * Email asincronico
     *
     * @param emailreceptor
     * @param titulo
     * @param mensaje
     * @param emailemisor
     * @param passwordemisor
     * @return
     * @throws InterruptedException
     */
    public Future<String> sendOutlookAsync(String emailreceptor, String titulo, String mensaje, String emailemisor, String passwordemisor,  Boolean... isTextHtml) throws InterruptedException {
        texthtml=false;
 if (isTextHtml.length != 0) {
            texthtml = isTextHtml[0];

        }
        CompletableFuture<String> completableFuture
                = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(new Callable<Object>() {

            @Override
            public Object call() throws Exception {

                sendOutlook(emailreceptor, titulo, mensaje, emailemisor, passwordemisor,texthtml);

                completableFuture.complete("enviado");

                return null;
            }
        });

        return completableFuture;
    }// </editor-fold>

    
    // <editor-fold defaultstate="collapsed" desc="sendOutlookAsync(String[] to, String[] cc, String[] bcc, String titulo, String mensaje, String emailemisor, String passwordemisor)">
    /**
     * Envia emails asincronicos
     *
     * @param to
     * @param cc
     * @param bcc
     * @param titulo
     * @param mensaje
     * @param emailemisor
     * @param passwordemisor
     * @return
     * @throws InterruptedException
     */
    public Future<String> sendOutlookAsync(String[] to, String[] cc, String[] bcc, String titulo, String mensaje, String emailemisor, String passwordemisor, Boolean... isTextHtml) throws InterruptedException {
texthtml=false;
        if (isTextHtml.length != 0) {
            texthtml = isTextHtml[0];

        }
        CompletableFuture<String> completableFuture
                = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {

                sendOutlook(to, cc, bcc, titulo, mensaje, emailemisor, passwordemisor, texthtml);
                completableFuture.complete("enviado");

                return null;
            }
        });

        return completableFuture;
    }// </editor-fold>
}
