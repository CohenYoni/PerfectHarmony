package com.synel.perfectharmony.serdes;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.synel.perfectharmony.utils.Constants;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

/**
 * {@link LocalDate} <-> {@link String} adapter.
 */
public class LocalDateStringAdapter extends TypeAdapter<LocalDate> {

    /**
     * Serialize {@link LocalDate} object as {@link String}.
     */
    @Override
    public void write(JsonWriter out, LocalDate value) throws IOException {

        if (Objects.isNull(value)) {
            out.value("");
            return;
        }
        out.value(value.atStartOfDay().format(Constants.DATE_TIME_FORMATTER));
    }

    /**
     * Deserialize date from {@link String} to {@link LocalDate} object.
     */
    @Override
    public LocalDate read(JsonReader in) throws IOException {

        String localDateStr = in.nextString();
        return StringUtils.isBlank(localDateStr) ? null : LocalDate.parse(localDateStr, Constants.DATE_TIME_FORMATTER);
    }
}
