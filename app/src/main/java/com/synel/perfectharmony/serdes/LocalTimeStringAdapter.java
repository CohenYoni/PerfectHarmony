package com.synel.perfectharmony.serdes;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.synel.perfectharmony.utils.Constants;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

/**
 * {@link LocalTime} <-> {@link String} adapter.
 */
public class LocalTimeStringAdapter extends TypeAdapter<LocalTime> {

    /**
     * Serialize {@link LocalTime} as {@link String}.
     */
    @Override
    public void write(JsonWriter out, LocalTime value) throws IOException {

        if (Objects.isNull(value)) {
            out.value("");
            return;
        }
        out.value(value.format(Constants.TIME_FORMATTER));
    }

    /**
     * Deserialize time (without date) from string to object.
     */
    @Override
    public LocalTime read(JsonReader in) throws IOException {

        String localTimeStr = in.nextString();
        return StringUtils.isBlank(localTimeStr) ? null : LocalTime.parse(localTimeStr);
    }
}
