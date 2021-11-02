package com.airgap.airgapagent.utils.exceptions;

import java.util.StringJoiner;
import java.util.function.Consumer;

/**
 * com.airgap.airgapagent.utils
 * Created by Jacques Fontignie on 5/30/2020.
 */
public class ExceptionUtils {

    private ExceptionUtils() {
        //Nothing to do
    }

    public static String expand(Throwable throwable) {
        if (throwable == null) return "<noexception>";
        StringJoiner joiner = new StringJoiner("->");
        do {
            joiner.add(throwable.toString());
            throwable = throwable.getCause();
        } while (throwable != null);
        return joiner.toString();
    }

    public static <T extends Exception> void run(ThrowableStatement<T> statement, Consumer<T> consumer) {
        try {
            statement.run();
        } catch (Exception t) {
            //noinspection unchecked
            consumer.accept((T) t);
        }
    }

}
