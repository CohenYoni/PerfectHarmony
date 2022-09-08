package com.synel.perfectharmony.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.synel.perfectharmony.models.api.AttendanceResponsePayload;
import java.util.Objects;

/**
 * Parse HTTP JSON responses of Harmony server.
 */
public class HarmonyParser {

    private static HarmonyParser instance;

    private final Gson gson;

    private HarmonyParser() {

        this.gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .create();
    }

    /**
     * @return a singleton instance of the parser.
     */
    public static HarmonyParser getInstance() {

        if (Objects.isNull(instance)) {
            instance = new HarmonyParser();
        }
        return instance;
    }

    /**
     * Serialize {@link AttendanceResponsePayload} to JSON.
     */
    public String serializeAttendanceResponsePayloadToJson(AttendanceResponsePayload attendanceResponsePayload) {

        return gson.toJson(attendanceResponsePayload);
    }

    /**
     * Deserialize JSON response of attendance data of an employee to {@link AttendanceResponsePayload}.
     */
    public AttendanceResponsePayload deserializeAttendanceResponsePayloadJson(String attendanceResponsePayloadJson) {

        return gson.fromJson(attendanceResponsePayloadJson, AttendanceResponsePayload.class);
    }
}
