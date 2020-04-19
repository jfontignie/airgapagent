package com.airgap.airgapagent.synchro;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * com.airgap.airgapagent.synchro
 * Created by Jacques Fontignie on 4/18/2020.
 */
class RegexTaskTest {


    @Test
    void test() throws IOException {
        RegexTask task = new RegexTask();
        task.setRegex(Arrays.asList("pwd", "password"));
        Task newTask = Mockito.mock(Task.class);
        task.setThen(Collections.singletonList(newTask));

        task.init();
        task.call(new PathInfo("src/test/resources", "src/test/resources/sample/sample.txt"));
        Mockito.verify(newTask, Mockito.only()).call(Mockito.any());
    }


    @Test
    void setRegex() {
        String regex = "(pwd)|(password)";
        Assertions.assertTrue(Pattern.matches(regex, "pwd"));
        Scanner scanner = new Scanner(new StringReader("this is a pwd"));
        String found = scanner.findWithinHorizon(regex, 0);
        Assertions.assertNotNull(found);
    }
}
