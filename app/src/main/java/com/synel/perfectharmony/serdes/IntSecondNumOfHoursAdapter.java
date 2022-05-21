package com.synel.perfectharmony.serdes;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.synel.perfectharmony.utils.Constants;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;

/**
 * {@link Integer} <-> {@link String} hours-minutes adapter.
 */
public class IntSecondNumOfHoursAdapter extends TypeAdapter<Integer> {

    /**
     * Serialize second counter to hours-minutes string representation.
     * For example: 5880 seconds => 1*60*60 + 38*60 => 01:38 hours.
     * Notice: negative numbers serialized with a leading minus sign.
     */
    @Override
    public void write(JsonWriter out, Integer value) throws IOException {

        if (Objects.isNull(value)) {
            out.value("");
            return;
        }
        boolean negative = value < 0;
        if (negative) {
            value *= -1;
        }
        int hours = Math.toIntExact(TimeUnit.SECONDS.toHours(value));
        int minutes = Math.toIntExact(TimeUnit.SECONDS.toMinutes(value) - TimeUnit.HOURS.toMinutes(hours));
        String numOfHours = LocalTime.of(hours, minutes).format(Constants.TIME_FORMATTER);
        if (negative) {
            numOfHours = "-" + numOfHours;
        }
        out.value(numOfHours);
    }

    /**
     * Deserialize hours counter from string to seconds.
     * For example: 01:38 hours => 1*60*60 + 38*60 => 5880 seconds.
     * Notice: negative number == missing hours, positive number == extra hours.
     */
    @Override
    public Integer read(JsonReader in) throws IOException {

        String numOfHoursStr = in.nextString();
        if (StringUtils.isBlank(numOfHoursStr)) {
            return null;
        }
        boolean negative = numOfHoursStr.startsWith("-");
        if (negative) {
            numOfHoursStr = numOfHoursStr.substring(1);
        }
        LocalTime numOfHours = LocalTime.parse(numOfHoursStr);
        int numOfSeconds = Math.toIntExact(
            numOfHours.getSecond() + TimeUnit.HOURS.toSeconds(numOfHours.getHour()) + TimeUnit.MINUTES.toSeconds(numOfHours.getMinute()));
        return negative ? -1 * numOfSeconds : numOfSeconds;
    }
}
