package com.airgap.airgapagent.synchro.predicate;

import java.time.Duration;
import java.util.Optional;
import java.util.function.LongFunction;

/**
 * com.airgap.airgapagent.synchro.predicate
 * Created by Jacques Fontignie on 5/10/2020.
 */
public class DurationHelper {

    private DurationHelper() {
        //Nothing to do
    }

    public static Duration simpleParse(String rawTime) {
        if (rawTime == null || rawTime.isEmpty())
            return null;
        if (!Character.isDigit(rawTime.charAt(0)))
            return null;

        String time = rawTime.toLowerCase();

        return tryParse(time, "s", Duration::ofSeconds)
                .orElseGet(() -> tryParse(time, "m", Duration::ofMinutes)
                        .orElseGet(() -> tryParse(time, "h", Duration::ofHours)
                                .orElseGet(() -> tryParse(time, "d", Duration::ofDays)
                                        .orElseGet(() -> tryParse(time, "w", l -> Duration.ofDays(l * 7))
                                                .orElse(null)))));
    }

    private static Optional<Duration> tryParse(String time, String unit,
                                               LongFunction<Duration> toDuration) {
        if (time.endsWith(unit)) {
            String trim = time.substring(0, time.lastIndexOf(unit)).trim();
            try {
                return Optional.of(toDuration.apply(Long.parseLong(trim)));
            } catch (NumberFormatException ignore) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
}

