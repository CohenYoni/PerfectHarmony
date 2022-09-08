package com.synel.perfectharmony.utils;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.google.gson.Gson;
import java.util.Objects;

public class SharedPreferencesUtil {

    private static Gson gson = new Gson();

    private static void setGson(Gson gson) {

        SharedPreferencesUtil.gson = gson;
    }

    public static <T> T getObject(SharedPreferences sharedPreferences, String key, T defaultValue, Class<T> type) {

        String objectJson = sharedPreferences.getString(key, null);
        if (Objects.isNull(objectJson)) {
            return defaultValue;
        }
        return gson.fromJson(objectJson, type);
    }

    public static <T> Editor putObject(Editor sharedPreferencesEditor, String key, T value) {

        String objectJson = gson.toJson(value);
        sharedPreferencesEditor.putString(key, objectJson);
        return sharedPreferencesEditor;
    }
}
