package com.airgap.airgapagent.james;

import com.airgap.airgapagent.algo.Matcher;
import com.airgap.airgapagent.algo.ahocorasick.AhoCorasickMatcher;
import com.airgap.airgapagent.service.ContentReaderService;
import com.airgap.airgapagent.utils.ConstantsTest;
import com.airgap.airgapagent.utils.DataReader;
import org.apache.james.core.builder.MimeMessageBuilder;
import org.apache.mailet.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.Map;
import java.util.Optional;

import static com.airgap.airgapagent.utils.ConstantsTest.CORPUS_SAMPLE_STRING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * com.airgap.airgapagent.james
 * Created by Jacques Fontignie on 6/7/2020.
 */
class ContentExactMatcherMailetTest {
    private final Mailet mailet = new ContentExactMatcherMailet();

    @Test
    void shouldIgnoreTheMessage() throws MessagingException {

        FakeMailetConfig mailetConfig = FakeMailetConfig.builder()
                .mailetName("Test")
                .setProperty("corpus", CORPUS_SAMPLE_STRING)
                .build();

        mailet.init(mailetConfig);

        Mail mail = FakeMail.builder()
                .name("mail")
                .mimeMessage(MimeMessageBuilder.mimeMessageBuilder()
                        .setSubject("one test"))
                .build();

        mailet.service(mail);

        Optional<Attribute> attribute = mail.getAttribute(ContentExactMatcherMailet.MATCH_NOT_FOUND.getName());
        assertThat(attribute).isPresent();
        assertThat(attribute.get()).isEqualTo(ContentExactMatcherMailet.MATCH_NOT_FOUND);
    }

    @Test
    void shouldFindPasswordInTheSubject() throws MessagingException {

        FakeMailetConfig mailetConfig = FakeMailetConfig.builder()
                .mailetName("Test")
                .setProperty(ContentExactMatcherMailet.CORPUS, CORPUS_SAMPLE_STRING)
                .setProperty(ContentExactMatcherMailet.MIN_HIT, "0")
                .build();

        mailet.init(mailetConfig);

        Mail mail = FakeMail.builder()
                .name("mail")
                .mimeMessage(MimeMessageBuilder.mimeMessageBuilder()
                        .setSubject("there is a password"))
                .build();

        mailet.service(mail);

        assertTrue(mail.getAttribute(AttributeName.of(ContentExactMatcherMailet.MATCHER_ATTRIBUTE)).isPresent());
        assertThat(mail.getAttribute(AttributeName.of(ContentExactMatcherMailet.MATCHER_ATTRIBUTE))
                .get().getValue()).isEqualTo(AttributeValue.of("HIT"));
    }


    @Test
    void shouldFindPasswordInTheSubjectDueToInsufficientHits() throws MessagingException {

        FakeMailetConfig mailetConfig = FakeMailetConfig.builder()
                .mailetName("Test")
                .setProperty(ContentExactMatcherMailet.CORPUS, CORPUS_SAMPLE_STRING)
                .setProperty(ContentExactMatcherMailet.MIN_HIT, "1")
                .build();

        mailet.init(mailetConfig);

        Mail mail = FakeMail.builder()
                .name("mail")
                .mimeMessage(MimeMessageBuilder.mimeMessageBuilder()
                        .setSubject("there is a password"))
                .build();

        mailet.service(mail);

        Optional<Attribute> attribute = mail.getAttribute(ContentExactMatcherMailet.MATCH_NOT_FOUND.getName());
        assertThat(attribute).isPresent();
        assertThat(attribute.get()).isEqualTo(ContentExactMatcherMailet.MATCH_NOT_FOUND);
    }

    @Test
    void shouldFindPasswordInMessage() throws MessagingException {

        FakeMailetConfig mailetConfig = FakeMailetConfig.builder()
                .mailetName("Test")
                .setProperty(ContentExactMatcherMailet.CORPUS, CORPUS_SAMPLE_STRING)
                .setProperty(ContentExactMatcherMailet.MIN_HIT, "1")
                .build();

        mailet.init(mailetConfig);

        Mail mail = FakeMail.builder()
                .name("mail")
                .mimeMessage(MimeMessageBuilder.mimeMessageBuilder()
                        .setSubject("there is a password")
                        .setText("And another password")
                )
                .build();

        mailet.service(mail);


        assertTrue(mail.getAttribute(AttributeName.of(ContentExactMatcherMailet.MATCHER_ATTRIBUTE)).isPresent());
        assertThat(mail.getAttribute(AttributeName.of(ContentExactMatcherMailet.MATCHER_ATTRIBUTE))
                .get().getValue()).isEqualTo(AttributeValue.of("HIT"));
    }


    @Test
    void shouldFindPasswordInAttachment() throws MessagingException, IOException {

        FakeMailetConfig mailetConfig = FakeMailetConfig.builder()
                .mailetName("Test")
                .setProperty(ContentExactMatcherMailet.CORPUS, CORPUS_SAMPLE_STRING)
                .setProperty(ContentExactMatcherMailet.MIN_HIT, "3")
                .build();

        mailet.init(mailetConfig);

        Mail mail = FakeMail.builder()
                .name("mail")
                .mimeMessage(MimeMessageBuilder.mimeMessageBuilder()
                        .setSubject("there is a password")
                        .setMultipartWithBodyParts(MimeMessageBuilder.bodyPartBuilder()
                                        .data("there is a password"),
                                createAttachmentBodyPart(ConstantsTest.CORPUS_SAMPLE)
                        ))
                .build();

        mailet.service(mail);


        assertTrue(mail.getAttribute(AttributeName.of(ContentExactMatcherMailet.MATCHER_ATTRIBUTE)).isPresent());
        assertThat(mail.getAttribute(AttributeName.of(ContentExactMatcherMailet.MATCHER_ATTRIBUTE))
                .get().getValue()).isEqualTo(AttributeValue.of("HIT"));
    }

    @Test
    void shouldFailDuetoInvalidParsing() throws MessagingException, IOException {

        Reader reader = Mockito.mock(Reader.class, invocation -> {
            throw new IOException("error expected");
        });
        ContentReaderService contentReaderService = Mockito.mock(ContentReaderService.class);
        Mockito.doReturn(new DataReader<>(new File(""), Map.of(), reader))
                .when(contentReaderService).getContent((MimeMessage) Mockito.any());

        ContentExactMatcherMailet mailet = new ContentExactMatcherMailet(contentReaderService);

        FakeMailetConfig mailetConfig = FakeMailetConfig.builder()
                .mailetName("Test")
                .setProperty(ContentExactMatcherMailet.CORPUS, CORPUS_SAMPLE_STRING)
                .setProperty(ContentExactMatcherMailet.MIN_HIT, "3")
                .build();
        mailet.init(mailetConfig);


        Mail mail = FakeMail.builder()
                .name("mail")
                .mimeMessage(MimeMessageBuilder.mimeMessageBuilder()
                        .setSubject("there is a password")
                        .setMultipartWithBodyParts(MimeMessageBuilder.bodyPartBuilder()
                                        .data("there is a password"),
                                createAttachmentBodyPart(ConstantsTest.CORPUS_SAMPLE)
                        ))
                .build();

        mailet.service(mail);


        assertTrue(mail.getAttribute(AttributeName.of(ContentExactMatcherMailet.MATCHER_ATTRIBUTE)).isPresent());
        assertThat(mail.getAttribute(AttributeName.of(ContentExactMatcherMailet.MATCHER_ATTRIBUTE))
                .get().getValue()).isEqualTo(AttributeValue.of("ERROR"));
    }

    @Test
    void shouldFailDueToInvalidBody() throws MessagingException, IOException {

        Matcher matcher = Mockito.mock(AhoCorasickMatcher.class);

        ContentReaderService contentReaderService = Mockito.mock(ContentReaderService.class);
        Mockito.doThrow(new IOException("throw"))
                .when(contentReaderService).getContent((MimeMessage) Mockito.any());

        ContentExactMatcherMailet mailet = new ContentExactMatcherMailet(contentReaderService);

        FakeMailetConfig mailetConfig = FakeMailetConfig.builder()
                .mailetName("Test")
                .setProperty(ContentExactMatcherMailet.CORPUS, CORPUS_SAMPLE_STRING)
                .setProperty(ContentExactMatcherMailet.MIN_HIT, "3")
                .build();
        mailet.init(mailetConfig);

        Mail mail = FakeMail.builder()
                .name("mail")
                .mimeMessage(MimeMessageBuilder.mimeMessageBuilder()
                        .setSubject("there is a password")
                        .setMultipartWithBodyParts(MimeMessageBuilder.bodyPartBuilder()
                                        .data("there is a password"),
                                createAttachmentBodyPart(ConstantsTest.CORPUS_SAMPLE)
                        ))
                .build();

        mailet.service(mail);


        assertTrue(mail.getAttribute(AttributeName.of(ContentExactMatcherMailet.MATCHER_ATTRIBUTE)).isPresent());
        assertThat(mail.getAttribute(AttributeName.of(ContentExactMatcherMailet.MATCHER_ATTRIBUTE))
                .get().getValue()).isEqualTo(AttributeValue.of("ERROR"));
    }


    private MimeMessageBuilder.BodyPartBuilder createAttachmentBodyPart(File file) throws IOException {
        byte[] array = Files.readAllBytes(file.toPath());
        return MimeMessageBuilder.bodyPartBuilder()
                .data(array)
                .disposition(MimeBodyPart.ATTACHMENT)
                .filename(file.getName());
    }

}
