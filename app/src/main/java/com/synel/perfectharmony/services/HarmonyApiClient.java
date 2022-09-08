package com.synel.perfectharmony.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.synel.perfectharmony.utils.Constants;
import com.synel.perfectharmony.utils.GsonStringConverterFactory;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HarmonyApiClient {

    private static final int TIMEOUT_SEC = 60;

    private static volatile HarmonyApiInterface apiInterface;

    private static String baseUrl;

    private static String apiPathPrefix;

    private HarmonyApiClient() {}

    private static String cleanBaseUrl(String baseUrl) {

        if (baseUrl.endsWith(Constants.SLASH)) {
            baseUrl = baseUrl.replaceAll(String.format("%s+$", Constants.SLASH), "");
        }
        return baseUrl;
    }

    private static String cleanApiPathPrefix(String apiPathPrefix) {

        if (!apiPathPrefix.startsWith(Constants.SLASH)) {
            apiPathPrefix = Constants.SLASH + apiPathPrefix;
        }
        if (!apiPathPrefix.endsWith(Constants.SLASH)) {
            apiPathPrefix += Constants.SLASH;
        }
        return apiPathPrefix;
    }

    public static void setUrl(String baseUrl, String apiPathPrefix) {

        HarmonyApiClient.baseUrl = cleanBaseUrl(baseUrl);
        HarmonyApiClient.apiPathPrefix = cleanApiPathPrefix(apiPathPrefix);
    }

    private static Retrofit createClient(String initialCookies) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(Level.HEADERS);

        InMemoryCookieJar.setInitialCookies(baseUrl, initialCookies);

        OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addNetworkInterceptor(new HarmonyDefaultHeadersInterceptor(baseUrl))
            .cookieJar(new InMemoryCookieJar())
            .readTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
            .build();

        Gson gson = new GsonBuilder().serializeNulls().create();

        return new Retrofit.Builder()
            .baseUrl(baseUrl + apiPathPrefix)
            .addConverterFactory(new GsonStringConverterFactory(gson))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build();
    }

    public static HarmonyApiInterface getApiInterface(String initialCookies) {

        if (apiInterface == null) {
            synchronized (HarmonyApiClient.class) {
                if (apiInterface == null) {
                    apiInterface = createClient(initialCookies)
                        .create(HarmonyApiInterface.class);
                }
            }
        }
        return apiInterface;
    }

    public static HarmonyApiInterface getApiInterface() {

        if (Objects.isNull(baseUrl) || Objects.isNull(apiPathPrefix)) {
            throw new IllegalArgumentException("You must init the url parameters!");
        }
        return getApiInterface(null);
    }

    public synchronized static void resetApiInterface() {

        apiInterface = null;
    }
}
