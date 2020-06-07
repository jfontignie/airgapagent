package com.airgap.airgapagent.james;


import org.apache.james.core.MailAddress;
import org.apache.james.core.builder.MimeMessageBuilder;
import org.apache.mailet.Mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * some utilities for James unit testing
 */
public class MailUtil {

    public static final String SENDER = "test@james.apache.org";
    public static final String RECIPIENT = "test2@james.apache.org";
    private static int m_counter = 0;

    public static String newId() {
        m_counter++;
        return "MockMailUtil-ID-" + m_counter;
    }

    public static FakeMail createMockMail2Recipients() throws MessagingException {
        return FakeMail.builder()
                .name(newId())
                .recipients(new MailAddress("test@james.apache.org"), new MailAddress("test2@james.apache.org"))
                .build();
    }

    public static FakeMail createMockMail2Recipients(MimeMessage message) throws MessagingException {
        return FakeMail.builder()
                .name(newId())
                .mimeMessage(message)
                .recipients(new MailAddress("test@james.apache.org"), new MailAddress("test2@james.apache.org"))
                .build();
    }

    public static MimeMessage createMimeMessage() throws MessagingException {
        return createMimeMessage(null, null);
    }

    public static MimeMessage createMimeMessageWithSubject(String subject) throws MessagingException {
        return createMimeMessage(null, null, subject);
    }

    public static MimeMessage createMimeMessage(String headerName, String headerValue) throws MessagingException {
        return createMimeMessage(headerName, headerValue, "testmail");
    }

    private static MimeMessage createMimeMessage(String headerName, String headerValue, String subject) throws MessagingException {
        MimeMessageBuilder mimeMessageBuilder = MimeMessageBuilder.mimeMessageBuilder()
                .addToRecipient(RECIPIENT)
                .addFrom(SENDER)
                .setSubject(subject);
        if (headerName != null) {
            mimeMessageBuilder.addHeader(headerName, headerValue);
        }
        return mimeMessageBuilder.build();
    }

    public static String toString(Mail mail, String charset) throws IOException, MessagingException {
        ByteArrayOutputStream rawMessage = new ByteArrayOutputStream();
        mail.getMessage().writeTo(
                rawMessage,
                new String[]{"Bcc", "Content-Length", "Message-ID"});
        return rawMessage.toString(charset);
    }

}
