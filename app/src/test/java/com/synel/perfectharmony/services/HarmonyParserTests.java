package com.synel.perfectharmony.services;

import com.synel.perfectharmony.models.AttendanceDayData;
import com.synel.perfectharmony.models.AttendanceResponsePayload;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import org.junit.Assert;
import org.junit.Test;

public class HarmonyParserTests {

    @Test
    public void serializeAttendanceResponseEmptyPayloadTest() {

        AttendanceResponsePayload payload = AttendanceResponsePayload.builder()
                                                                     .totalCountOfDays(0)
                                                                     .attendanceDaysData(Collections.emptyList())
                                                                     .build();
        String expectedJson = "{\n" +
            "  \"totalCount\": 0,\n" +
            "  \"results\": []\n" +
            "}";
        Assert.assertEquals("Failed to serialize empty payload!",
                            expectedJson,
                            HarmonyParser.getInstance().serializeAttendanceResponsePayloadToJson(payload));
    }

    @Test
    public void serializeAttendanceResponseTest() {

        AttendanceDayData attendanceDayData = AttendanceDayData.builder()
                                                               .employeeName("Employee Name")
                                                               .employeeNumber(111111118)
                                                               .mainBudgetId("Main budget ID")
                                                               .mainBudgetDescription("Main budget description")
                                                               .subBudgetId("Sub budget ID")
                                                               .subBudgetDescription("Sub budget description")
                                                               .departmentCode("Department code")
                                                               .departmentDescription("Department description")
                                                               .workingDate(LocalDate.of(2022, 5, 21))
                                                               .dayOfWeekName("Day of week name")
                                                               .attendanceDayType("Attendance day type")
                                                               .absenceCode(0)
                                                               .absenceCodeName("Absence code name")
                                                               .exceptionCode("1285")
                                                               .exceptionType(1)
                                                               .exceptionDescription("Exception description")
                                                               .shift1ExpectedStartTime(LocalTime.of(8, 0))
                                                               .shift1ExpectedEndTime(LocalTime.of(12, 0))
                                                               .shift2ExpectedStartTime(LocalTime.of(14, 0))
                                                               .shift2ExpectedEndTime(LocalTime.of(18, 0))
                                                               .expectedTimeSeconds(12720)
                                                               .startEventType("Start event type")
                                                               .endEventType("End event type")
                                                               .actualStartTimeA(LocalTime.of(8, 3))
                                                               .actualEndTimeA(LocalTime.of(14, 45))
                                                               .actualStartTimeAW(LocalTime.of(8, 7))
                                                               .actualEndTimeAW(LocalTime.of(15, 12))
                                                               .absenceTimeSeconds(85620)
                                                               .startTimeMaxShiftActual(LocalTime.of(9, 19))
                                                               .endTimeMinShiftActual(LocalTime.of(16, 29))
                                                               .dayTotalTimeSecondsShiftRounded(12660)
                                                               .dayMissingTimeSecondsShiftRounded(12780)
                                                               .build();

        AttendanceResponsePayload payload = AttendanceResponsePayload.builder()
                                                                     .totalCountOfDays(1)
                                                                     .attendanceDaysData(Collections.singletonList(attendanceDayData))
                                                                     .build();

        String expectedJson = "{\n" +
            "  \"totalCount\": 1,\n" +
            "  \"results\": [\n" +
            "    {\n" +
            "      \"Emp_Name\": \"Employee Name\",\n" +
            "      \"Emp_noA\": 111111118,\n" +
            "      \"budgID_AW\": \"Main budget ID\",\n" +
            "      \"budgDescr_AW\": \"Main budget description\",\n" +
            "      \"subBudgID_AW\": \"Sub budget ID\",\n" +
            "      \"subBudgDescr_AW\": \"Sub budget description\",\n" +
            "      \"DepartmentCode\": \"Department code\",\n" +
            "      \"DepartmentDescr\": \"Department description\",\n" +
            "      \"WorkDateA\": \"2022-05-21T00:00:00\",\n" +
            "      \"DayName\": \"Day of week name\",\n" +
            "      \"Type\": \"Attendance day type\",\n" +
            "      \"AbsenceCodeAW\": \"0\",\n" +
            "      \"NameAbsenceCodeAW\": \"Absence code name\",\n" +
            "      \"Exception\": \"1285\",\n" +
            "      \"ExceptType\": 1,\n" +
            "      \"lstExceptDict\": \"Exception description\",\n" +
            "      \"shift1_start\": \"08:00\",\n" +
            "      \"shift1_end\": \"12:00\",\n" +
            "      \"shift2_start\": \"14:00\",\n" +
            "      \"shift2_end\": \"18:00\",\n" +
            "      \"ExpectedTimeAW\": \"03:32\",\n" +
            "      \"NameTrnFnc_startAW\": \"Start event type\",\n" +
            "      \"NameTrnFnc_endAW\": \"End event type\",\n" +
            "      \"Time_startA\": \"08:03\",\n" +
            "      \"Time_endA\": \"14:45\",\n" +
            "      \"Time_startAW\": \"08:07\",\n" +
            "      \"Time_endAW\": \"15:12\",\n" +
            "      \"AbsenceTimeAW\": \"23:47\",\n" +
            "      \"Time_startA_RND\": \"09:19\",\n" +
            "      \"Time_endA_RND\": \"16:29\",\n" +
            "      \"Day_TotalAW\": \"03:31\",\n" +
            "      \"MissAW\": \"03:33\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";
        Assert.assertEquals("Failed to serialize payload!",
                            expectedJson,
                            HarmonyParser.getInstance().serializeAttendanceResponsePayloadToJson(payload));
    }

    @Test
    public void deserializeAttendanceResponseEmptyPayloadJson() {

        String attendanceResponsePayload = "{\n" +
            "  \"totalCount\": 1,\n" +
            "  \"results\": [\n" +
            "    {\n" +
            "      \"Emp_Name\": \"Employee Name\",\n" +
            "      \"Emp_noA\": 111111118,\n" +
            "      \"budgID_AW\": \"Main budget ID\",\n" +
            "      \"budgDescr_AW\": \"Main budget description\",\n" +
            "      \"subBudgID_AW\": \"Sub budget ID\",\n" +
            "      \"subBudgDescr_AW\": \"Sub budget description\",\n" +
            "      \"DepartmentCode\": \"Department code\",\n" +
            "      \"DepartmentDescr\": \"Department description\",\n" +
            "      \"WorkDateA\": \"2022-05-21T00:00:00\",\n" +
            "      \"DayName\": \"Day of week name\",\n" +
            "      \"Type\": \"Attendance day type\",\n" +
            "      \"AbsenceCodeAW\": \"0\",\n" +
            "      \"NameAbsenceCodeAW\": \"Absence code name\",\n" +
            "      \"ExceptType\": 1,\n" +
            "      \"lstExceptDict\": \"Exception description\",\n" +
            "      \"shift1_start\": \"08:00\",\n" +
            "      \"shift1_end\": \"12:00\",\n" +
            "      \"shift2_start\": \"14:00\",\n" +
            "      \"shift2_end\": \"18:00\",\n" +
            "      \"ExpectedTimeAW\": \"03:32\",\n" +
            "      \"NameTrnFnc_startAW\": \"Start event type\",\n" +
            "      \"NameTrnFnc_endAW\": \"End event type\",\n" +
            "      \"Time_startA\": \"08:03\",\n" +
            "      \"Time_endA\": \"14:45\",\n" +
            "      \"Time_startAW\": \"08:07\",\n" +
            "      \"Time_endAW\": \"15:12\",\n" +
            "      \"AbsenceTimeAW\": \"23:47\",\n" +
            "      \"Time_startA_RND\": \"09:19\",\n" +
            "      \"Time_endA_RND\": \"16:29\",\n" +
            "      \"Day_TotalAW\": \"03:31\",\n" +
            "      \"MissAW\": \"03:33\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";
        AttendanceDayData attendanceDayData = AttendanceDayData.builder()
                                                               .employeeName("Employee Name")
                                                               .employeeNumber(111111118)
                                                               .mainBudgetId("Main budget ID")
                                                               .mainBudgetDescription("Main budget description")
                                                               .subBudgetId("Sub budget ID")
                                                               .subBudgetDescription("Sub budget description")
                                                               .departmentCode("Department code")
                                                               .departmentDescription("Department description")
                                                               .workingDate(LocalDate.of(2022, 5, 21))
                                                               .dayOfWeekName("Day of week name")
                                                               .attendanceDayType("Attendance day type")
                                                               .absenceCode(0)
                                                               .absenceCodeName("Absence code name")
                                                               .exceptionType(1)
                                                               .exceptionDescription("Exception description")
                                                               .shift1ExpectedStartTime(LocalTime.of(8, 0))
                                                               .shift1ExpectedEndTime(LocalTime.of(12, 0))
                                                               .shift2ExpectedStartTime(LocalTime.of(14, 0))
                                                               .shift2ExpectedEndTime(LocalTime.of(18, 0))
                                                               .expectedTimeSeconds(12720)
                                                               .startEventType("Start event type")
                                                               .endEventType("End event type")
                                                               .actualStartTimeA(LocalTime.of(8, 3))
                                                               .actualEndTimeA(LocalTime.of(14, 45))
                                                               .actualStartTimeAW(LocalTime.of(8, 7))
                                                               .actualEndTimeAW(LocalTime.of(15, 12))
                                                               .absenceTimeSeconds(85620)
                                                               .startTimeMaxShiftActual(LocalTime.of(9, 19))
                                                               .endTimeMinShiftActual(LocalTime.of(16, 29))
                                                               .dayTotalTimeSecondsShiftRounded(12660)
                                                               .dayMissingTimeSecondsShiftRounded(12780)
                                                               .build();

        AttendanceResponsePayload expectedPayload = AttendanceResponsePayload.builder()
                                                                             .totalCountOfDays(1)
                                                                             .attendanceDaysData(Collections.singletonList(attendanceDayData))
                                                                             .build();
        Assert.assertEquals("Failed to deserialize payload!",
                            expectedPayload,
                            HarmonyParser.getInstance().deserializeAttendanceResponsePayloadJson(attendanceResponsePayload));
    }

    @Test
    public void deserializeAttendanceResponsePayloadJson() {

        String attendanceResponsePayload = "{\n" +
            "  \"totalCount\": 0,\n" +
            "  \"results\": []\n" +
            "}";
        AttendanceResponsePayload expectedPayload = AttendanceResponsePayload.builder()
                                                                             .totalCountOfDays(0)
                                                                             .attendanceDaysData(Collections.emptyList())
                                                                             .build();
        Assert.assertEquals("Failed to deserialize payload!",
                            expectedPayload,
                            HarmonyParser.getInstance().deserializeAttendanceResponsePayloadJson(attendanceResponsePayload));
    }

    @Test
    public void parseRealAttendanceResponsePayloadJsonWithoutExceptionsTest() {

        HarmonyParser parser = HarmonyParser.getInstance();

        AttendanceResponsePayload lastDayWithoutEntriesPayload = parser.deserializeAttendanceResponsePayloadJson(
            TestConstants.attendanceResponsePayloadLastDayWithoutEntries);
        Assert.assertEquals("Wrong days count in last day without entries!",
                            Integer.valueOf(18),
                            lastDayWithoutEntriesPayload.getTotalCountOfDays());
        AttendanceResponsePayload lastDayJustEnterPayload = parser.deserializeAttendanceResponsePayloadJson(
            TestConstants.attendanceResponsePayloadLastDayJustEnter);
        Assert.assertEquals("Wrong days count in last day just enter!", Integer.valueOf(18), lastDayJustEnterPayload.getTotalCountOfDays());
        AttendanceResponsePayload lastDayAllEntriesPayload = parser.deserializeAttendanceResponsePayloadJson(
            TestConstants.attendanceResponsePayloadLastDayAllEntries);
        Assert.assertEquals("Wrong days count in all entries!", Integer.valueOf(18), lastDayAllEntriesPayload.getTotalCountOfDays());
        AttendanceResponsePayload withSickDaysPayload = parser.deserializeAttendanceResponsePayloadJson(TestConstants.attendanceResponsePayloadWithSick);
        Assert.assertEquals("Wrong days count in sick days!", Integer.valueOf(30), withSickDaysPayload.getTotalCountOfDays());
        AttendanceResponsePayload dayWithDoubleShiftsPayload = parser.deserializeAttendanceResponsePayloadJson(
            TestConstants.attendanceResponsePayloadOneDayDoubleShift);
        Assert.assertEquals("Wrong days count in double shifts!", Integer.valueOf(37), dayWithDoubleShiftsPayload.getTotalCountOfDays());

        AttendanceResponsePayload attendanceData202112 = parser.deserializeAttendanceResponsePayloadJson(TestConstants.attendanceResponsePayload202112);
        Assert.assertEquals("Wrong days count of 2021-12!", Integer.valueOf(6), attendanceData202112.getTotalCountOfDays());
        AttendanceResponsePayload attendanceData202201 = parser.deserializeAttendanceResponsePayloadJson(TestConstants.attendanceResponsePayload202201);
        Assert.assertEquals("Wrong days count of 2022-01!", Integer.valueOf(37), attendanceData202201.getTotalCountOfDays());
        AttendanceResponsePayload attendanceData202202 = parser.deserializeAttendanceResponsePayloadJson(TestConstants.attendanceResponsePayload202202);
        Assert.assertEquals("Wrong days count of 2022-02!", Integer.valueOf(28), attendanceData202202.getTotalCountOfDays());
        AttendanceResponsePayload attendanceData202203 = parser.deserializeAttendanceResponsePayloadJson(TestConstants.attendanceResponsePayload202203);
        Assert.assertEquals("Wrong days count of 2022-03!", Integer.valueOf(31), attendanceData202203.getTotalCountOfDays());
        AttendanceResponsePayload attendanceData202204 = parser.deserializeAttendanceResponsePayloadJson(TestConstants.attendanceResponsePayload202204);
        Assert.assertEquals("Wrong days count of 2022-04!", Integer.valueOf(30), attendanceData202204.getTotalCountOfDays());
        AttendanceResponsePayload attendanceData202205 = parser.deserializeAttendanceResponsePayloadJson(TestConstants.attendanceResponsePayload202205Partial);
        Assert.assertEquals("Wrong days count of 2022-05!", Integer.valueOf(28), attendanceData202205.getTotalCountOfDays());
        String lastDayWithoutEntriesPayloadStr = parser.serializeAttendanceResponsePayloadToJson(lastDayWithoutEntriesPayload);
        String lastDayJustEnterPayloadStr = parser.serializeAttendanceResponsePayloadToJson(lastDayJustEnterPayload);
        String lastDayAllEntriesPayloadStr = parser.serializeAttendanceResponsePayloadToJson(lastDayAllEntriesPayload);
        String withSickDaysPayloadStr = parser.serializeAttendanceResponsePayloadToJson(withSickDaysPayload);
        String dayWithDoubleShiftsPayloadStr = parser.serializeAttendanceResponsePayloadToJson(dayWithDoubleShiftsPayload);
        String attendanceData202112Str = parser.serializeAttendanceResponsePayloadToJson(attendanceData202112);
        String attendanceData202201Str = parser.serializeAttendanceResponsePayloadToJson(attendanceData202201);
        String attendanceData202202Str = parser.serializeAttendanceResponsePayloadToJson(attendanceData202202);
        String attendanceData202203Str = parser.serializeAttendanceResponsePayloadToJson(attendanceData202203);
        String attendanceData202204Str = parser.serializeAttendanceResponsePayloadToJson(attendanceData202204);
        String attendanceData202205Str = parser.serializeAttendanceResponsePayloadToJson(attendanceData202205);
    }
}
