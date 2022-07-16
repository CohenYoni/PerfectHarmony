package com.synel.perfectharmony.services;

import androidx.annotation.NonNull;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HarmonyDefaultHeadersInterceptor implements Interceptor {

    private final String baseUrl;

    public HarmonyDefaultHeadersInterceptor(String baseUrl) {

        this.baseUrl = baseUrl;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        Request.Builder requestBuilder = chain.request().newBuilder();
        requestBuilder.header("accept", "application/json, text/javascript, */*; q=0.01");
        requestBuilder.header("accept-encoding", "gzip, deflate, br");
        requestBuilder.header("accept-language", "he-IL,he;q=0.9");
        requestBuilder.header("content-type", "application/json");
        requestBuilder.header("origin", baseUrl);
        requestBuilder.header("referer", baseUrl + "/eharmonynew");
        requestBuilder.header("user-agent",
                              "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.4951.67 Safari/537.36");
        requestBuilder.header("x-requested-with", "XMLHttpRequest");
        return chain.proceed(requestBuilder.build());
    }
}
