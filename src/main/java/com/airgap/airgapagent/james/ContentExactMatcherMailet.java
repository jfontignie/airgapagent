package com.airgap.airgapagent.james;

import com.airgap.airgapagent.algo.MatchOption;
import com.airgap.airgapagent.algo.Matcher;
import com.airgap.airgapagent.algo.MatchingResult;
import com.airgap.airgapagent.algo.ahocorasick.AhoCorasickMatcher;
import com.airgap.airgapagent.algo.ahocorasick.Automaton;
import com.airgap.airgapagent.service.ContentReaderService;
import com.airgap.airgapagent.service.CorpusBuilderService;
import com.airgap.airgapagent.utils.DataReader;
import org.apache.mailet.Attribute;
import org.apache.mailet.Mail;
import org.apache.mailet.MailetException;
import org.apache.mailet.base.GenericMailet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * com.airgap.airgapagent.james
 * Created by Jacques Fontignie on 6/7/2020.
 */
public class ContentExactMatcherMailet extends GenericMailet {

    private static final Logger log = LoggerFactory.getLogger(ContentExactMatcherMailet.class);

    static final String MATCHER_ATTRIBUTE = "matcher";
    static final String MIN_HIT = "minHit";
    static final String CORPUS = "corpus";
    static final Attribute MATCH_HIT = Attribute.convertToAttribute(MATCHER_ATTRIBUTE, "HIT");
    static final Attribute MATCH_ERROR = Attribute.convertToAttribute(MATCHER_ATTRIBUTE, "ERROR");
    static final Attribute MATCH_NOT_FOUND = Attribute.convertToAttribute(MATCHER_ATTRIBUTE, "NotFound");

    private Matcher matcher;
    private final ContentReaderService contentReaderService;
    private int minHit;

    ContentExactMatcherMailet(ContentReaderService contentReaderService) {
        this.contentReaderService = contentReaderService;
    }

    public ContentExactMatcherMailet() {
        this(new ContentReaderService());
    }

    @Override
    public void init() throws MessagingException {
        minHit = Integer.parseInt(getInitParameter(MIN_HIT, String.valueOf(50)));
        String corpus = getInitParameter(CORPUS);
        if (corpus == null) {
            throw new MessagingException("Please configure the corpus location");
        }

        CorpusBuilderService corpusBuilderService = new CorpusBuilderService();
        Set<String> set;
        try {
            set = corpusBuilderService.buildSet(new File(corpus));
        } catch (IOException e) {
            throw new MessagingException("Impossible to build automaton", e);
        }
        Automaton automaton = new Automaton(Collections.singleton(MatchOption.CASE_INSENSITIVE), set);

        this.matcher = new AhoCorasickMatcher(automaton);

        log.info("Successfully loaded corpus with {} elements", set.size());
    }

    @Override
    public String getMailetInfo() {
        return "ExactMatch Mailet";
    }

    @Override
    public void service(Mail mail) throws MessagingException {
        Attribute attribute = MATCH_NOT_FOUND;
        AtomicInteger found = new AtomicInteger();
        Consumer<MatchingResult> consumer = matchingResult -> found.incrementAndGet();

        MimeMessage mimeMessage = getMessageFromMail(mail);

        try {
            matcher.match(mimeMessage.getSubject(), consumer);
        } catch (IOException e) {
            log.error("Impossible to read subject", e);
            attribute = MATCH_ERROR;
        }

        try {
            DataReader<MimeMessage> dataReader = contentReaderService.getContent(mimeMessage);
            matcher.match(dataReader.getReader(), consumer);
        } catch (IOException e) {
            log.error("Impossible to read content");
            attribute = MATCH_ERROR;
        }
        if (found.get() > minHit) {
            attribute = MATCH_HIT;
        }
        mail.setAttribute(attribute);

    }

    private MimeMessage getMessageFromMail(Mail mail) throws MailetException {
        try {
            return mail.getMessage();
        } catch (MessagingException e) {
            throw new MailetException("Could not retrieve message from Mail object", e);
        }
    }

}
