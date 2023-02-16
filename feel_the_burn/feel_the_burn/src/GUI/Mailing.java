
package GUI;
import  Tools.connexion;
//import static com.sun.org.glassfish.external.amx.AMXUtil.prop;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import Entities.Activite;

public class Mailing {
    private Connection con;
    private Statement ste;

    public Mailing() {
        con = connexion.getInstance().getCnx();
    }
    
    
    public static void mailing(String recipient) throws Exception
    {
         Properties prop = new Properties();
        final String moncompteEmail = "feel_the_burn@gmail.com";
        final String psw = "Burn123";
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        
        Session ses = Session.getInstance(prop, new javax.mail.Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(moncompteEmail, psw);
            }
        });
        try
      {
           Message mes= new MimeMessage(ses);
                       mes.setFrom(new InternetAddress(moncompteEmail));
            mes.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            mes.setSubject("information");
            mes.setContent("salut", "text/html");

            Transport.send(mes);
            

       }
        catch (MessagingException ex) {
            Logger.getLogger(Mailing.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        }
    
    public static void mailingValider(String recipient, String NomA) throws Exception
    {
          Properties prop = new Properties();
        final String moncompteEmail = "zeinebharaketi1@gmail.com";
        final String psw = "Zeineb/2021";
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

       Session ses = Session.getInstance(prop, new javax.mail.Authenticator(){
              protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(moncompteEmail, psw);
            }
       });
       
       try{
           Message msg = new MimeMessage(ses);
            msg.setFrom(new InternetAddress(moncompteEmail));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            msg.setSubject("Code de confirmation");
            msg.setText("Merci pour votre Interet à Feel the Burn , voici votre nom d'Activite:   "+NomA);
            
            Transport.send(msg);
           System.out.println(" Message envoyé avec succés !!! ");
       }
         catch (MessagingException ex) {
            Logger.getLogger(Mailing.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
    }

