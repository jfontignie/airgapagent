package com.airgap.airgapagent.utils;

import java.util.StringJoiner;

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
}
