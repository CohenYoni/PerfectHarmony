package com.synel.perfectharmony.serdes;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

/**
 * {@link String} <-> {@link String} base64 adapter.
 */
public class Base64Adapter extends TypeAdapter<String> {

    /**
     * Serialize a string to base64 string.
     */
    @Override
    public void write(JsonWriter out, String value) throws IOException {

        out.value(Objects.nonNull(value) ? Base64.getEncoder().encodeToString(value.getBytes()) : null);
    }

    /**
     * Deserialize base64 string to string.
     */
    @Override
    public String read(JsonReader in) throws IOException {

        String readValue = in.nextString();
        return Objects.nonNull(readValue) ? new String(Base64.getDecoder().decode(readValue)) : null;
    }
}
