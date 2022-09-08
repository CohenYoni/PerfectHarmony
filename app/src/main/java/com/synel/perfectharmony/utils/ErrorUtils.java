package com.synel.perfectharmony.utils;

import com.google.gson.Gson;
import com.synel.perfectharmony.models.api.ErrorResponse;
import java.util.Objects;
import okhttp3.ResponseBody;

public class ErrorUtils {

    private static final Gson gson = new Gson();

    public static String parseErrorJsonResponse(ResponseBody responseBody, String prefix, String fallbackMessage) {

        ErrorResponse errorResponse;
        try {
            errorResponse = gson.fromJson(responseBody.string(), ErrorResponse.class);
        } catch (Exception e) {
            return fallbackMessage;
        }
        String errorMessage = prefix + errorResponse.getErrorMessage();
        if (Objects.nonNull(errorResponse.getErrorMessageDetails())) {
            errorMessage += ": " + errorResponse.getErrorMessageDetails();
        }
        return errorMessage;
    }
}
