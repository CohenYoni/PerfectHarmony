package com.synel.perfectharmony.services;

import com.synel.perfectharmony.models.api.AttendanceDayData;
import com.synel.perfectharmony.models.api.AttendanceResponsePayload;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Test;

public class HarmonyAttendanceCalculatorTests {

    private final LocalTime maxDayWorkingHours = LocalTime.of(8, 20);

    @Test
    public void test202112Month() {

        AttendanceResponsePayload attendanceData = HarmonyParser.getInstance()
                                                                .deserializeAttendanceResponsePayloadJson(TestConstants.attendanceResponsePayload202112);
        HarmonyAttendanceCalculator attendanceCalculator = new HarmonyAttendanceCalculator(attendanceData, maxDayWorkingHours);
        Assert.assertEquals("Wrong working days!", 3, attendanceCalculator.getTotalNumOfWorkingDays(LocalDate.of(2021, 12, 28)));
        Assert.assertEquals("Wrong working days!", 4, attendanceCalculator.getTotalNumOfWorkingDays());

        Assert.assertEquals("Wrong expected working hours!", 77100,
                            attendanceCalculator.getTotalNumOfExpectedWorkingHoursInSeconds(LocalDate.of(2021, 12, 28)));
        Assert.assertEquals("Wrong expected working hours!", 105900,
                            attendanceCalculator.getTotalNumOfExpectedWorkingHoursInSeconds());

        Assert.assertEquals("Wrong actual working hours!",
                            TimeUnit.HOURS.toSeconds(24),
                            attendanceCalculator.getTotalNumOfActualWorkingHoursInSeconds(LocalDate.of(2021, 12, 28)));
        Assert.assertEquals("Wrong actual working hours!",
                            TimeUnit.HOURS.toSeconds(32),
                            attendanceCalculator.getTotalNumOfActualWorkingHoursInSeconds());

        Assert.assertEquals("Wrong extra hours!",
                            0,
                            attendanceCalculator.calculateNumOfExtraMissingHoursInSeconds(LocalDate.of(2021, 12, 27)));
        Assert.assertEquals("Wrong extra hours!", 9300, attendanceCalculator.calculateNumOfExtraMissingHoursInSeconds());

        Assert.assertEquals("Wrong working hours!",
                            28800,
                            attendanceCalculator.calculateNumOfWorkingHoursInDayInSeconds(LocalDate.of(2021, 12, 26)));
        Assert.assertEquals("Wrong working hours!",
                            19500,
                            attendanceCalculator.calculateNumOfWorkingHoursInDayInSeconds(LocalDate.of(2021, 12, 28)));
        Assert.assertEquals("Wrong working hours!",
                            19500,
                            attendanceCalculator.calculateNumOfWorkingHoursInDayInSeconds(LocalDate.of(2021, 12, 29)));

        Assert.assertEquals("Wrong exit hour!",
                            LocalTime.of(16, 0),
                            attendanceCalculator.calculateExitHourOfDayInSeconds(LocalDate.of(2021, 12, 26)));
        Assert.assertEquals("Wrong exit hour!",
                            LocalTime.of(13, 25),
                            attendanceCalculator.calculateExitHourOfDayInSeconds(LocalDate.of(2021, 12, 28)));
        Assert.assertEquals("Wrong exit hour!",
                            LocalTime.of(15, 25),
                            attendanceCalculator.calculateExitHourOfDayInSeconds(LocalDate.of(2021, 12, 29)));

        Assert.assertEquals("Wrong missing entries!",
                            Collections.emptyList(),
                            attendanceCalculator.getMissingEntryDays());

        Assert.assertEquals("Wrong last date!", LocalDate.of(2021, 12, 31), attendanceCalculator.getLastDateOfPayload());
    }

    @Test
    public void test202201Month() {

        AttendanceResponsePayload attendanceData = HarmonyParser.getInstance()
                                                                .deserializeAttendanceResponsePayloadJson(TestConstants.attendanceResponsePayload202201);
        HarmonyAttendanceCalculator attendanceCalculator = new HarmonyAttendanceCalculator(attendanceData, maxDayWorkingHours);
        Assert.assertEquals("Wrong working days!", 9, attendanceCalculator.getTotalNumOfWorkingDays(LocalDate.of(2022, 1, 16)));
        Assert.assertEquals("Wrong working days!", 18, attendanceCalculator.getTotalNumOfWorkingDays());

        Assert.assertEquals("Wrong expected working hours!", 240600,
                            attendanceCalculator.getTotalNumOfExpectedWorkingHoursInSeconds(LocalDate.of(2022, 1, 16)));
        Assert.assertEquals("Wrong expected working hours!", 479400,
                            attendanceCalculator.getTotalNumOfExpectedWorkingHoursInSeconds());

        Assert.assertEquals("Wrong actual working hours!", 268140,
                            attendanceCalculator.getTotalNumOfActualWorkingHoursInSeconds(LocalDate.of(2022, 1, 16)));
        Assert.assertEquals("Wrong actual working hours!", 538140,
                            attendanceCalculator.getTotalNumOfActualWorkingHoursInSeconds());

        Assert.assertEquals("Wrong extra hours!",
                            27540,
                            attendanceCalculator.calculateNumOfExtraMissingHoursInSeconds(LocalDate.of(2022, 1, 16)));
        Assert.assertEquals("Wrong extra hours!", 58740, attendanceCalculator.calculateNumOfExtraMissingHoursInSeconds());

        Assert.assertEquals("Wrong working hours!",
                            15240,
                            attendanceCalculator.calculateNumOfWorkingHoursInDayInSeconds(LocalDate.of(2022, 1, 10)));
        Assert.assertEquals("Wrong working hours!",
                            0,
                            attendanceCalculator.calculateNumOfWorkingHoursInDayInSeconds(LocalDate.of(2022, 1, 20)));
        Assert.assertEquals("Wrong working hours!",
                            -6420,
                            attendanceCalculator.calculateNumOfWorkingHoursInDayInSeconds(LocalDate.of(2022, 1, 23)));
        Assert.assertEquals("Wrong working hours!",
                            -27360,
                            attendanceCalculator.calculateNumOfWorkingHoursInDayInSeconds(LocalDate.of(2022, 1, 31)));

        Assert.assertEquals("Wrong exit hour!",
                            LocalTime.of(12, 15),
                            attendanceCalculator.calculateExitHourOfDayInSeconds(LocalDate.of(2022, 1, 10)));
        Assert.assertNull("Wrong exit hour!", attendanceCalculator.calculateExitHourOfDayInSeconds(LocalDate.of(2022, 1, 20)));
        Assert.assertEquals("Wrong exit hour!",
                            LocalTime.of(6, 14),
                            attendanceCalculator.calculateExitHourOfDayInSeconds(LocalDate.of(2022, 1, 23)));
        Assert.assertEquals("Wrong exit hour!",
                            LocalTime.of(0, 46),
                            attendanceCalculator.calculateExitHourOfDayInSeconds(LocalDate.of(2022, 1, 31)));

        List<AttendanceDayData> actualMissingEntries = attendanceCalculator.getMissingEntryDays();
        Assert.assertEquals("Wrong missing entries count!", 2, actualMissingEntries.size());
        Assert.assertEquals("Wrong missing entries dates!",
                            Arrays.asList(LocalDate.of(2022, 1, 18), LocalDate.of(2022, 1, 25)),
                            actualMissingEntries.stream().map(AttendanceDayData::getWorkingDate).collect(Collectors.toList()));

        Assert.assertEquals("Wrong last date!", LocalDate.of(2022, 1, 31), attendanceCalculator.getLastDateOfPayload());
    }

    @Test
    public void test202202Month() {

        AttendanceResponsePayload attendanceData = HarmonyParser.getInstance()
                                                                .deserializeAttendanceResponsePayloadJson(TestConstants.attendanceResponsePayload202202);
        HarmonyAttendanceCalculator attendanceCalculator = new HarmonyAttendanceCalculator(attendanceData, maxDayWorkingHours);
        Assert.assertEquals("Wrong working days!", 8, attendanceCalculator.getTotalNumOfWorkingDays(LocalDate.of(2022, 2, 14)));
        Assert.assertEquals("Wrong working days!", 16, attendanceCalculator.getTotalNumOfWorkingDays());

        Assert.assertEquals("Wrong expected working hours!", 208200,
                            attendanceCalculator.getTotalNumOfExpectedWorkingHoursInSeconds(LocalDate.of(2022, 2, 14)));
        Assert.assertEquals("Wrong expected working hours!", 416400,
                            attendanceCalculator.getTotalNumOfExpectedWorkingHoursInSeconds());

        Assert.assertEquals("Wrong actual working hours!", 236580,
                            attendanceCalculator.getTotalNumOfActualWorkingHoursInSeconds(LocalDate.of(2022, 2, 14)));
        Assert.assertEquals("Wrong actual working hours!", 482820,
                            attendanceCalculator.getTotalNumOfActualWorkingHoursInSeconds());

        Assert.assertEquals("Wrong extra hours!",
                            28380,
                            attendanceCalculator.calculateNumOfExtraMissingHoursInSeconds(LocalDate.of(2022, 2, 14)));
        Assert.assertEquals("Wrong extra hours!", 66420, attendanceCalculator.calculateNumOfExtraMissingHoursInSeconds());

        Assert.assertEquals("Wrong working hours!",
                            0,
                            attendanceCalculator.calculateNumOfWorkingHoursInDayInSeconds(LocalDate.of(2022, 2, 10)));
        Assert.assertEquals("Wrong working hours!",
                            -14580,
                            attendanceCalculator.calculateNumOfWorkingHoursInDayInSeconds(LocalDate.of(2022, 2, 20)));
        Assert.assertEquals("Wrong working hours!",
                            -36240,
                            attendanceCalculator.calculateNumOfWorkingHoursInDayInSeconds(LocalDate.of(2022, 2, 28)));

        Assert.assertNull("Wrong exit hour!", attendanceCalculator.calculateExitHourOfDayInSeconds(LocalDate.of(2022, 2, 10)));
        Assert.assertEquals("Wrong exit hour!",
                            LocalTime.of(4, 16),
                            attendanceCalculator.calculateExitHourOfDayInSeconds(LocalDate.of(2022, 2, 20)));
        Assert.assertEquals("Wrong exit hour!",
                            LocalTime.of(0, 1),
                            attendanceCalculator.calculateExitHourOfDayInSeconds(LocalDate.of(2022, 2, 28)));

        List<AttendanceDayData> actualMissingEntries = attendanceCalculator.getMissingEntryDays();
        Assert.assertEquals("Wrong missing entries count!", 0, actualMissingEntries.size());
        Assert.assertEquals("Wrong missing entries dates!",
                            Collections.emptyList(),
                            actualMissingEntries.stream().map(AttendanceDayData::getWorkingDate).collect(Collectors.toList()));

        Assert.assertEquals("Wrong last date!", LocalDate.of(2022, 2, 28), attendanceCalculator.getLastDateOfPayload());
    }

    @Test
    public void test202203Month() {

        AttendanceResponsePayload attendanceData = HarmonyParser.getInstance()
                                                                .deserializeAttendanceResponsePayloadJson(TestConstants.attendanceResponsePayload202203);
        HarmonyAttendanceCalculator attendanceCalculator = new HarmonyAttendanceCalculator(attendanceData, maxDayWorkingHours);
        Assert.assertEquals("Wrong working days!", 8, attendanceCalculator.getTotalNumOfWorkingDays(LocalDate.of(2022, 3, 14)));
        Assert.assertEquals("Wrong working days!", 18, attendanceCalculator.getTotalNumOfWorkingDays());

        Assert.assertEquals("Wrong expected working hours!", 208200,
                            attendanceCalculator.getTotalNumOfExpectedWorkingHoursInSeconds(LocalDate.of(2022, 3, 14)));
        Assert.assertEquals("Wrong expected working hours!", 464700,
                            attendanceCalculator.getTotalNumOfExpectedWorkingHoursInSeconds());

        Assert.assertEquals("Wrong actual working hours!", 225540,
                            attendanceCalculator.getTotalNumOfActualWorkingHoursInSeconds(LocalDate.of(2022, 3, 14)));
        Assert.assertEquals("Wrong actual working hours!", 539460,
                            attendanceCalculator.getTotalNumOfActualWorkingHoursInSeconds());

        Assert.assertEquals("Wrong extra hours!",
                            17340,
                            attendanceCalculator.calculateNumOfExtraMissingHoursInSeconds(LocalDate.of(2022, 3, 14)));
        Assert.assertEquals("Wrong extra hours!", 74760, attendanceCalculator.calculateNumOfExtraMissingHoursInSeconds());

        Assert.assertEquals("Wrong working hours!",
                            0,
                            attendanceCalculator.calculateNumOfWorkingHoursInDayInSeconds(LocalDate.of(2022, 3, 10)));
        Assert.assertEquals("Wrong working hours!",
                            -6360,
                            attendanceCalculator.calculateNumOfWorkingHoursInDayInSeconds(LocalDate.of(2022, 3, 20)));
        Assert.assertEquals("Wrong working hours!",
                            -40140,
                            attendanceCalculator.calculateNumOfWorkingHoursInDayInSeconds(LocalDate.of(2022, 3, 30)));

        Assert.assertNull("Wrong exit hour!", attendanceCalculator.calculateExitHourOfDayInSeconds(LocalDate.of(2022, 3, 10)));
        Assert.assertEquals("Wrong exit hour!",
                            LocalTime.of(8, 1),
                            attendanceCalculator.calculateExitHourOfDayInSeconds(LocalDate.of(2022, 3, 20)));
        Assert.assertEquals("Wrong exit hour!",
                            LocalTime.of(22, 13),
                            attendanceCalculator.calculateExitHourOfDayInSeconds(LocalDate.of(2022, 3, 30)));

        List<AttendanceDayData> actualMissingEntries = attendanceCalculator.getMissingEntryDays();
        Assert.assertEquals("Wrong missing entries count!", 0, actualMissingEntries.size());
        Assert.assertEquals("Wrong missing entries dates!",
                            Collections.emptyList(),
                            actualMissingEntries.stream().map(AttendanceDayData::getWorkingDate).collect(Collectors.toList()));

        Assert.assertEquals("Wrong last date!", LocalDate.of(2022, 3, 31), attendanceCalculator.getLastDateOfPayload());
    }

    @Test
    public void test202204Month() {

        AttendanceResponsePayload attendanceData = HarmonyParser.getInstance()
                                                                .deserializeAttendanceResponsePayloadJson(TestConstants.attendanceResponsePayload202204);
        HarmonyAttendanceCalculator attendanceCalculator = new HarmonyAttendanceCalculator(attendanceData, maxDayWorkingHours);
        Assert.assertEquals("Wrong working days!", 8, attendanceCalculator.getTotalNumOfWorkingDays(LocalDate.of(2022, 4, 14)));
        Assert.assertEquals("Wrong working days!", 16, attendanceCalculator.getTotalNumOfWorkingDays());

        Assert.assertEquals("Wrong expected working hours!", 208200,
                            attendanceCalculator.getTotalNumOfExpectedWorkingHoursInSeconds(LocalDate.of(2022, 4, 14)));
        Assert.assertEquals("Wrong expected working hours!", 416400,
                            attendanceCalculator.getTotalNumOfExpectedWorkingHoursInSeconds());

        Assert.assertEquals("Wrong actual working hours!", 211740,
                            attendanceCalculator.getTotalNumOfActualWorkingHoursInSeconds(LocalDate.of(2022, 4, 14)));
        Assert.assertEquals("Wrong actual working hours!", 419940,
                            attendanceCalculator.getTotalNumOfActualWorkingHoursInSeconds());

        Assert.assertEquals("Wrong extra hours!",
                            3540,
                            attendanceCalculator.calculateNumOfExtraMissingHoursInSeconds(LocalDate.of(2022, 4, 14)));
        Assert.assertEquals("Wrong extra hours!", 3540, attendanceCalculator.calculateNumOfExtraMissingHoursInSeconds());

        Assert.assertEquals("Wrong working hours!",
                            24600,
                            attendanceCalculator.calculateNumOfWorkingHoursInDayInSeconds(LocalDate.of(2022, 4, 10)));
        Assert.assertEquals("Wrong working hours!",
                            25260,
                            attendanceCalculator.calculateNumOfWorkingHoursInDayInSeconds(LocalDate.of(2022, 4, 20)));
        Assert.assertEquals("Wrong working hours!",
                            25260,
                            attendanceCalculator.calculateNumOfWorkingHoursInDayInSeconds(LocalDate.of(2022, 4, 27)));

        Assert.assertEquals("Wrong exit hour!",
                            LocalTime.of(16, 3),
                            attendanceCalculator.calculateExitHourOfDayInSeconds(LocalDate.of(2022, 4, 10)));
        Assert.assertNull("Wrong exit hour!", attendanceCalculator.calculateExitHourOfDayInSeconds(LocalDate.of(2022, 4, 20)));
        Assert.assertNull("Wrong exit hour!", attendanceCalculator.calculateExitHourOfDayInSeconds(LocalDate.of(2022, 4, 27)));

        List<AttendanceDayData> actualMissingEntries = attendanceCalculator.getMissingEntryDays();
        Assert.assertEquals("Wrong missing entries count!", 0, actualMissingEntries.size());
        Assert.assertEquals("Wrong missing entries dates!",
                            Collections.emptyList(),
                            actualMissingEntries.stream().map(AttendanceDayData::getWorkingDate).collect(Collectors.toList()));

        Assert.assertEquals("Wrong last date!", LocalDate.of(2022, 4, 30), attendanceCalculator.getLastDateOfPayload());
    }

    @Test
    public void test202205PartialMonth() {

        AttendanceResponsePayload attendanceData = HarmonyParser.getInstance()
                                                                .deserializeAttendanceResponsePayloadJson(TestConstants.attendanceResponsePayload202205Partial);
        HarmonyAttendanceCalculator attendanceCalculator = new HarmonyAttendanceCalculator(attendanceData, maxDayWorkingHours);
        Assert.assertEquals("Wrong working days!", 8, attendanceCalculator.getTotalNumOfWorkingDays(LocalDate.of(2022, 5, 14)));
        Assert.assertEquals("Wrong working days!", 16, attendanceCalculator.getTotalNumOfWorkingDays());

        Assert.assertEquals("Wrong expected working hours!", 201000,
                            attendanceCalculator.getTotalNumOfExpectedWorkingHoursInSeconds(LocalDate.of(2022, 5, 14)));
        Assert.assertEquals("Wrong expected working hours!", 409200,
                            attendanceCalculator.getTotalNumOfExpectedWorkingHoursInSeconds());

        Assert.assertEquals("Wrong actual working hours!", 204060,
                            attendanceCalculator.getTotalNumOfActualWorkingHoursInSeconds(LocalDate.of(2022, 5, 14)));
        Assert.assertEquals("Wrong actual working hours!", 415920,
                            attendanceCalculator.getTotalNumOfActualWorkingHoursInSeconds());

        Assert.assertEquals("Wrong extra hours!",
                            3060,
                            attendanceCalculator.calculateNumOfExtraMissingHoursInSeconds(LocalDate.of(2022, 5, 14)));
        Assert.assertEquals("Wrong extra hours!", 6720, attendanceCalculator.calculateNumOfExtraMissingHoursInSeconds());

        Assert.assertEquals("Wrong working hours!",
                            18840,
                            attendanceCalculator.calculateNumOfWorkingHoursInDayInSeconds(LocalDate.of(2022, 5, 10)));
        Assert.assertEquals("Wrong working hours!",
                            0,
                            attendanceCalculator.calculateNumOfWorkingHoursInDayInSeconds(LocalDate.of(2022, 5, 20)));
        Assert.assertEquals("Wrong working hours!",
                            22680,
                            attendanceCalculator.calculateNumOfWorkingHoursInDayInSeconds(LocalDate.of(2022, 5, 25)));

        Assert.assertEquals("Wrong exit hour!",
                            LocalTime.of(14, 36),
                            attendanceCalculator.calculateExitHourOfDayInSeconds(LocalDate.of(2022, 5, 10)));
        Assert.assertNull("Wrong exit hour!", attendanceCalculator.calculateExitHourOfDayInSeconds(LocalDate.of(2022, 5, 20)));
        Assert.assertEquals("Wrong exit hour!",
                            LocalTime.of(16, 33),
                            attendanceCalculator.calculateExitHourOfDayInSeconds(LocalDate.of(2022, 5, 25)));

        List<AttendanceDayData> actualMissingEntries = attendanceCalculator.getMissingEntryDays();
        Assert.assertEquals("Wrong missing entries count!", 0, actualMissingEntries.size());
        Assert.assertEquals("Wrong missing entries dates!",
                            Collections.emptyList(),
                            actualMissingEntries.stream().map(AttendanceDayData::getWorkingDate).collect(Collectors.toList()));

        Assert.assertEquals("Wrong last date!", LocalDate.of(2022, 5, 28), attendanceCalculator.getLastDateOfPayload());
    }

    @Test
    public void test202205Month() {

        AttendanceResponsePayload attendanceData = HarmonyParser.getInstance()
                                                                .deserializeAttendanceResponsePayloadJson(TestConstants.attendanceResponsePayload202205);
        HarmonyAttendanceCalculator attendanceCalculator = new HarmonyAttendanceCalculator(attendanceData, maxDayWorkingHours);
        Assert.assertEquals("Wrong working days!", 8, attendanceCalculator.getTotalNumOfWorkingDays(LocalDate.of(2022, 5, 14)));
        Assert.assertEquals("Wrong working days!", 19, attendanceCalculator.getTotalNumOfWorkingDays());

        Assert.assertEquals("Wrong expected working hours!", 201000,
                            attendanceCalculator.getTotalNumOfExpectedWorkingHoursInSeconds(LocalDate.of(2022, 5, 14)));
        Assert.assertEquals("Wrong expected working hours!", 484500,
                            attendanceCalculator.getTotalNumOfExpectedWorkingHoursInSeconds());

        Assert.assertEquals("Wrong actual working hours!", 204060,
                            attendanceCalculator.getTotalNumOfActualWorkingHoursInSeconds(LocalDate.of(2022, 5, 14)));
        Assert.assertEquals("Wrong actual working hours!", 487080,
                            attendanceCalculator.getTotalNumOfActualWorkingHoursInSeconds());

        Assert.assertEquals("Wrong extra hours!",
                            3060,
                            attendanceCalculator.calculateNumOfExtraMissingHoursInSeconds(LocalDate.of(2022, 5, 14)));
        Assert.assertEquals("Wrong extra hours!", 2580, attendanceCalculator.calculateNumOfExtraMissingHoursInSeconds());

        Assert.assertEquals("Wrong working hours!",
                            18840,
                            attendanceCalculator.calculateNumOfWorkingHoursInDayInSeconds(LocalDate.of(2022, 5, 10)));
        Assert.assertEquals("Wrong working hours!",
                            0,
                            attendanceCalculator.calculateNumOfWorkingHoursInDayInSeconds(LocalDate.of(2022, 5, 20)));
        Assert.assertEquals("Wrong working hours!",
                            17220,
                            attendanceCalculator.calculateNumOfWorkingHoursInDayInSeconds(LocalDate.of(2022, 5, 31)));

        Assert.assertEquals("Wrong exit hour!",
                            LocalTime.of(14, 36),
                            attendanceCalculator.calculateExitHourOfDayInSeconds(LocalDate.of(2022, 5, 10)));
        Assert.assertNull("Wrong exit hour!", attendanceCalculator.calculateExitHourOfDayInSeconds(LocalDate.of(2022, 5, 20)));
        Assert.assertEquals("Wrong exit hour!",
                            LocalTime.of(15, 40),
                            attendanceCalculator.calculateExitHourOfDayInSeconds(LocalDate.of(2022, 5, 31)));

        List<AttendanceDayData> actualMissingEntries = attendanceCalculator.getMissingEntryDays();
        Assert.assertEquals("Wrong missing entries count!", 0, actualMissingEntries.size());
        Assert.assertEquals("Wrong missing entries dates!",
                            Collections.emptyList(),
                            actualMissingEntries.stream().map(AttendanceDayData::getWorkingDate).collect(Collectors.toList()));

        Assert.assertEquals("Wrong last date!", LocalDate.of(2022, 5, 31), attendanceCalculator.getLastDateOfPayload());
    }

    @Test
    public void maxDayWorkingHoursTest() {

        HarmonyAttendanceCalculator attendanceCalculator = new HarmonyAttendanceCalculator(AttendanceResponsePayload.builder().build(),
                                                                                           maxDayWorkingHours);
        Assert.assertEquals("Wrong maximum day working hour initiation!",
                            maxDayWorkingHours,
                            attendanceCalculator.getMaxDayWorkingHours());

        LocalTime newMaxDayWorkingHours = LocalTime.of(8, 12);
        attendanceCalculator.setMaxDayWorkingHours(newMaxDayWorkingHours);
        Assert.assertEquals("Wrong maximum day working hour!", newMaxDayWorkingHours, attendanceCalculator.getMaxDayWorkingHours());
    }
}
