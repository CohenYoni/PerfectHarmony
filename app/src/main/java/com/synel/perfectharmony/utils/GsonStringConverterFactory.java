package com.synel.perfectharmony.utils;

import androidx.annotation.NonNull;
import com.google.gson.Gson;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.http.Query;

public class GsonStringConverterFactory extends Converter.Factory {

    private final transient Gson gson;

    public GsonStringConverterFactory(Gson gson) {

        this.gson = gson;
    }

    @Override
    public Converter<?, String> stringConverter(@NonNull Type type,
                                                @NonNull Annotation[] annotations,
                                                @NonNull Retrofit retrofit) {

        boolean toJson = false;
        boolean needEncoding = false;
        for (Annotation annotation : annotations) {
            if (annotation instanceof ToJson) {
                toJson = true;
            }
            if (annotation instanceof Query) {
                needEncoding = ((Query) annotation).encoded();
            }
        }
        if (!toJson) {
            return super.stringConverter(type, annotations, retrofit);
        }
        boolean finalNeedEncoding = needEncoding;
        return value -> {
            String jsonStr = gson.toJson(value, type);
            return finalNeedEncoding ? URLEncoder.encode(jsonStr, StandardCharsets.UTF_8.toString()) : jsonStr;
        };
    }

    private boolean hasToJson(Annotation[] annotations) {

        for (Annotation annotation : annotations) {
            if (annotation instanceof ToJson) {
                return true;
            }
        }
        return false;
    }
}
