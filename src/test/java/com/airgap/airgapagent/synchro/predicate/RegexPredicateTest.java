package com.airgap.airgapagent.synchro.predicate;

import com.airgap.airgapagent.synchro.utils.PathInfo;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

/**
 * com.airgap.airgapagent.synchro.predicate
 * Created by Jacques Fontignie on 4/26/2020.
 */
class RegexPredicateTest {

    private final RegexPredicate regexPredicate = new RegexPredicate(
            List.of("pwd", "password"), false
    );

    @BeforeEach()
    public void setUp() {
        regexPredicate.init();
    }

    @AfterEach
    public void tearDown() {
        regexPredicate.close();
    }

    @Test
    void testSuccessful() throws IOException {
        Assertions.assertTrue(regexPredicate.call(PathInfo.of("src/test/resources/sample/sample.docx")));
        Assertions.assertTrue(regexPredicate.call(PathInfo.of("src/test/resources/sample/sample.htm")));
        Assertions.assertTrue(regexPredicate.call(PathInfo.of("src/test/resources/sample/sample.pdf")));
        Assertions.assertTrue(regexPredicate.call(PathInfo.of("src/test/resources/sample/sample.rtf")));
        Assertions.assertTrue(regexPredicate.call(PathInfo.of("src/test/resources/sample/sample.txt")));
        Assertions.assertTrue(regexPredicate.call(PathInfo.of("src/test/resources/sample/sample.xps")));
        Assertions.assertTrue(regexPredicate.call(PathInfo.of("src/test/resources/sample/sample.zip")));
        Assertions.assertTrue(regexPredicate.call(PathInfo.of("src/test/resources/sample/sample.7z")));
    }

    @Disabled
    @Test
    void testBigFiles() throws IOException {
        Assertions.assertTrue(regexPredicate.call(PathInfo.of("src/test/resources/sample/sample.eml")));
        Assertions.assertTrue(regexPredicate.call(PathInfo.of("src/test/resources/sample/sample.xlsx")));
    }

    @Test
    void testFolder() throws IOException {
        Assertions.assertTrue(regexPredicate.call(PathInfo.of("src/test/resources/sample")));
    }

    @Test
    void testCaseSensitive() throws IOException {
        RegexPredicate regexPredicate = new RegexPredicate(List.of("passWord"), true);
        regexPredicate.init();
        Assertions.assertFalse(regexPredicate.call(PathInfo.of("src/test/resources/sample/sample.docx")));
        Assertions.assertFalse(regexPredicate.call(PathInfo.of("src/test/resources/sample/sample.htm")));
        Assertions.assertFalse(regexPredicate.call(PathInfo.of("src/test/resources/sample/sample.pdf")));
        Assertions.assertFalse(regexPredicate.call(PathInfo.of("src/test/resources/sample/sample.rtf")));
        Assertions.assertFalse(regexPredicate.call(PathInfo.of("src/test/resources/sample/sample.txt")));
        Assertions.assertFalse(regexPredicate.call(PathInfo.of("src/test/resources/sample/sample.xps")));
        Assertions.assertFalse(regexPredicate.call(PathInfo.of("src/test/resources/sample/sample.zip")));
        Assertions.assertFalse(regexPredicate.call(PathInfo.of("src/test/resources/sample/sample.7z")));

    }

    @Test
    void testRegexCaseSensitive() {
        regexPredicate.setRegex(List.of("pwd", "password"));
        Assertions.assertEquals(2, regexPredicate.getRegex().size());
        regexPredicate.setCaseSensitive(true);
        regexPredicate.init();
        Pattern pattern = regexPredicate.getPattern();
        Assertions.assertFalse(pattern.matcher("Password").matches());
        Assertions.assertTrue(pattern.matcher("password").matches());

    }


    @Test
    void testRegexCaseInsensitive() {
        regexPredicate.setRegex(List.of("pwd", "password"));
        regexPredicate.setCaseSensitive(false);
        regexPredicate.init();
        Pattern pattern = regexPredicate.getPattern();

        Assertions.assertTrue(pattern.matcher("Password").matches());
        Assertions.assertTrue(pattern.matcher("password").matches());
    }
}
