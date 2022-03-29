package dk.easv.BLL;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PostOffice {

    private static final String PROP_FILE = "src/emailInfo.txt";


    String userEmail;
    String userPassword;
    Session newSession = null;
    MimeMessage mimeMessage = null;

    public PostOffice() throws IOException {
        readPropfile();
    }

    private void readPropfile() throws IOException {
        Properties loginProp = new Properties();
        loginProp.load(new FileInputStream(PROP_FILE));
        userEmail = loginProp.getProperty("Email");
        userPassword = loginProp.getProperty("Password");
    }

    //Setup mail serverProperties
    private void setupServerProperties() {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.port", 587);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        newSession = Session.getDefaultInstance(properties, null);
        //Session var ikke sat i tut, måske en fejl
    }
//draft mail

    private MimeMessage draftEmail() throws MessagingException {
        String[] mailingList = {"vict657k@easv365.dk"}; //email list skal være en String array, måske??
        String subject = "subject placeholder";
        String body = "body placeholder";
        mimeMessage = new MimeMessage(newSession);

        for (String emailAddress : mailingList) {
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
        }
        mimeMessage.setSubject(subject);

        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(body, "html/text");

        MimeMultipart multipart = new MimeMultipart();
        multipart.addBodyPart(bodyPart);

        mimeMessage.setContent(multipart);
        return mimeMessage;
    }

    //send mail
    private void sendEmail() throws MessagingException {
        //replace med en proterty fil i stil med databse connector
        String fromUser = userEmail; //senderen skal være en gmail
        String fromUserPassword = userPassword;
        String emailHost = "smtp.gmail.com";
        Transport transport = newSession.getTransport("smtp");
        transport.connect(emailHost,fromUser,fromUserPassword);
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        transport.close();
        System.out.println("mail sendt");
    }

    public static void main(String[] args) throws MessagingException, IOException {
        PostOffice postOffice = new PostOffice();
        postOffice.setupServerProperties();
        postOffice.draftEmail();
        postOffice.sendEmail();
    }

}
