package com.synel.perfectharmony.utils;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.time.DurationFormatUtils;

/**
 * Utilities for LocalTime class.
 */
public class LocalTimeUtils {

    /**
     * Convert number of seconds to {@link LocalTime} object.
     *
     * @param numOfSeconds number of seconds to convert
     * @return {@link LocalTime} representation of the input seconds
     */
    public static LocalTime convertSecondsToLocalTime(int numOfSeconds) {

        int hours = Math.toIntExact(TimeUnit.SECONDS.toHours(numOfSeconds));
        int minutes = Math.toIntExact(TimeUnit.SECONDS.toMinutes(numOfSeconds) - TimeUnit.HOURS.toMinutes(hours));
        int seconds = Math.toIntExact(numOfSeconds - TimeUnit.MINUTES.toSeconds(minutes) - TimeUnit.HOURS.toSeconds(hours));
        return LocalTime.of(hours, minutes, seconds);
    }

    /**
     * Convert {@link LocalTime} object to seconds.
     *
     * @param time {@link LocalTime} object to convert
     * @return the total seconds of the time
     */
    public static int convertLocalTimeToSeconds(LocalTime time) {

        return Math.toIntExact(time.getSecond() + TimeUnit.HOURS.toSeconds(time.getHour()) + TimeUnit.MINUTES.toSeconds(time.getMinute()));
    }

    public static String secToHHmmStr(int numOfSeconds) {

        return DurationFormatUtils.formatDuration(numOfSeconds * Constants.MILLIS_IN_SEC, Constants.TIME_PATTERN, true);
    }
}
