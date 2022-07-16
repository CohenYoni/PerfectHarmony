package com.synel.perfectharmony.services;

import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HarmonyApiClient {

    private static volatile Retrofit retrofitClient;

    private HarmonyApiClient() {}

    private static Retrofit createClient(String baseUrl, String apiPathPrefix, String initialCookies) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(Level.HEADERS);

        InMemoryCookieJar.setInitialCookies(baseUrl, initialCookies);

        OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addNetworkInterceptor(new HarmonyDefaultHeadersInterceptor(baseUrl))
            .cookieJar(new InMemoryCookieJar())
            .build();

        return new Retrofit.Builder()
            .baseUrl(baseUrl + apiPathPrefix)
            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()))
            .client(client)
            .build();
    }

    public static Retrofit getClient(String baseUrl, String apiPathPrefix, String initialCookies) {

        if (retrofitClient == null) {
            synchronized (HarmonyApiClient.class) {
                if (retrofitClient == null) {
                    retrofitClient = createClient(baseUrl, apiPathPrefix, initialCookies);
                }
            }
        }
        return retrofitClient;
    }
}
