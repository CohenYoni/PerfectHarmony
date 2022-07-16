package com.synel.perfectharmony.services;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import org.apache.commons.lang3.StringUtils;

public class InMemoryCookieJar implements CookieJar {

    private static final String COOKIES_SEPARATOR = ";";

    private static final Set<Cookie> COOKIES = new HashSet<>();

    public static void setInitialCookies(String initialCookiesUrl, String initialCookiesString) {

        Set<Cookie> initialCookies = parseCookiesFromString(initialCookiesUrl, initialCookiesString);
        COOKIES.addAll(initialCookies);
    }

    public static Set<Cookie> parseCookiesFromString(String cookiesUrl, String cookiesString) {

        HttpUrl initialCookiesHttpUrl;
        if (Objects.isNull(cookiesUrl) || StringUtils.isBlank(cookiesString) ||
            (initialCookiesHttpUrl = HttpUrl.parse(cookiesUrl)) == null) {
            return new HashSet<>();
        }
        return Arrays.stream(cookiesString.split(COOKIES_SEPARATOR))
                     .map(cookie -> Cookie.parse(initialCookiesHttpUrl, cookie))
                     .collect(Collectors.toSet());
    }

    @NonNull
    @Override
    public List<Cookie> loadForRequest(@NonNull HttpUrl httpUrl) {

        return new ArrayList<>(COOKIES);
    }

    @Override
    public void saveFromResponse(@NonNull HttpUrl httpUrl, @NonNull List<Cookie> cookiesList) {

        COOKIES.addAll(cookiesList);
    }
}
