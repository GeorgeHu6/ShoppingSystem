import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.net.UnknownServiceException;
import java.util.Date;
import java.util.Properties;

public class MailSendTest {
    public static void main(String[] args) throws MessagingException, UnsupportedEncodingException {
        String myEmailAccount = "993506946@qq.com";
        String myEmailPassword = "tryzyjlhwwiwbfic";
        String smtpHost = "smtp.qq.com";

        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.host", smtpHost);
        properties.setProperty("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties);
        session.setDebug(true);

        MimeMessage message = createMimeMessage(session, myEmailAccount, "dhxxhch@163.com");
        Transport transport = session.getTransport();

        transport.connect(myEmailAccount, myEmailPassword);
        transport.sendMessage(message, message.getAllRecipients());

        transport.close();
    }

    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail) throws UnsupportedEncodingException, MessagingException {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sendMail));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiveMail));
        message.setSubject("Shopping System 验证码", "UTF-8");
        message.setContent("您本次请求的验证码为xxxxxx", "text/html;charset=UTF-8");
        message.setSentDate(new Date());
        return message;
    }
}
