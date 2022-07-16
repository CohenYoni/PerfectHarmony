package com.synel.perfectharmony.serdes;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

/**
 * Empty {@link String} <-> null adapter.
 */
public class EmptyStringNullAdapter extends TypeAdapter<String> {

    /**
     * Serialize an empty string as null.
     */
    @Override
    public void write(JsonWriter out, String value) throws IOException {

        out.value(Objects.isNull(value) ? "" : value);
    }

    /**
     * Deserialize base64 string to string.
     */
    @Override
    public String read(JsonReader in) throws IOException {

        String readValue = in.nextString();
        return StringUtils.isEmpty(readValue) ? null : readValue;
    }
}
