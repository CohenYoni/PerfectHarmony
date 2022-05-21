package com.synel.perfectharmony.serdes;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

/**
 * {@link Integer} <-> {@link String} adapter.
 */
public class IntStringAdapter extends TypeAdapter<Integer> {

    /**
     * Serialize integers as string.
     */
    @Override
    public void write(JsonWriter out, Integer value) throws IOException {

        if (Objects.isNull(value)) {
            out.value("");
            return;
        }
        out.value(String.valueOf(value));
    }

    /**
     * Deserialize numbers from string to integer.
     */
    @Override
    public Integer read(JsonReader in) throws IOException {

        String number = in.nextString();
        return StringUtils.isBlank(number) ? null : Integer.valueOf(number);
    }
}
