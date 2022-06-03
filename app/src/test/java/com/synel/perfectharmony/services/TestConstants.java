package com.synel.perfectharmony.services;

import com.synel.perfectharmony.utils.FileUtils;
import java.io.IOException;

public class TestConstants {

    public static String attendanceResponsePayloadLastDayWithoutEntries;

    public static String attendanceResponsePayloadLastDayJustEnter;

    public static String attendanceResponsePayloadLastDayAllEntries;

    public static String attendanceResponsePayloadOneDayDoubleShift;

    public static String attendanceResponsePayloadWithSick;

    public static String attendanceResponsePayload202112;

    public static String attendanceResponsePayload202201;

    public static String attendanceResponsePayload202202;

    public static String attendanceResponsePayload202203;

    public static String attendanceResponsePayload202204;

    public static String attendanceResponsePayload202205Partial;

    public static String attendanceResponsePayload202205;

    static {
        try {
            loadTestFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load all the test data from JSON files
     */
    public static void loadTestFiles() throws IOException {

        String baseFolder = "src/test/resources/";
        attendanceResponsePayloadLastDayWithoutEntries = FileUtils.readFile(
            baseFolder + "attendance_response_payload_last_day_without_entries.json");
        attendanceResponsePayloadLastDayJustEnter = FileUtils.readFile(baseFolder + "attendance_response_payload_last_day_just_enter.json");
        attendanceResponsePayloadLastDayAllEntries = FileUtils.readFile(baseFolder + "attendance_response_payload_all_entries.json");
        attendanceResponsePayloadOneDayDoubleShift = FileUtils.readFile(
            baseFolder + "attendance_response_payload_one_day_double_shifts.json");
        attendanceResponsePayloadWithSick = FileUtils.readFile(baseFolder + "attendance_response_payload_with_sick_days.json");
        attendanceResponsePayload202112 = FileUtils.readFile(baseFolder + "12.21.json");
        attendanceResponsePayload202201 = FileUtils.readFile(baseFolder + "01.22.json");
        attendanceResponsePayload202202 = FileUtils.readFile(baseFolder + "02.22.json");
        attendanceResponsePayload202203 = FileUtils.readFile(baseFolder + "03.22.json");
        attendanceResponsePayload202204 = FileUtils.readFile(baseFolder + "04.22.json");
        attendanceResponsePayload202205Partial = FileUtils.readFile(baseFolder + "01-28.05.22.json");
        attendanceResponsePayload202205 = FileUtils.readFile(baseFolder + "05.22.json");
    }
}
