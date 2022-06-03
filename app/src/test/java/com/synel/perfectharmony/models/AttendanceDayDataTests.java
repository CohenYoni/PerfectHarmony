package com.synel.perfectharmony.models;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.Assert;
import org.junit.Test;

public class AttendanceDayDataTests {

    @Test
    public void modelDataTest() {

        String employeeName = "Employee name";
        Integer employeeNumber = 1234;
        String mainBudgetId = "56";
        String mainBudgetDescription = "Main Budget";
        String subBudgetId = "78";
        String subBudgetDescription = "Sub Budget";
        String departmentCode = "9012345";
        String departmentDescription = "Department name";
        LocalDate workingDate = LocalDate.of(2022, 5, 20);
        String dayOfWeekName = "Friday";
        String attendanceDayType = "Regular Attendance";
        Integer absenceCode = 0;
        String absenceCodeName = "Regular Absence";
        Integer exceptionType = 3;
        String exceptionDescription = "Not an Exception Day";
        LocalTime shift1ExpectedStartTime = LocalTime.of(8, 0);
        LocalTime shift1ExpectedEndTime = LocalTime.of(12, 0);
        LocalTime shift2ExpectedStartTime = LocalTime.of(14, 0);
        LocalTime shift2ExpectedEndTime = LocalTime.of(18, 0);
        Integer expectedTimeSeconds = 8 * 60 * 60;
        String startEventType = "Mobile Enter";
        String endEventType = "Mobile Exit";
        LocalTime actualStartTimeA = LocalTime.of(7, 58);
        LocalTime actualEndTimeA = LocalTime.of(16, 41);
        LocalTime actualStartTimeAW = LocalTime.of(6, 17);
        LocalTime actualEndTimeAW = LocalTime.of(15, 36);
        Integer absenceTimeSeconds = 12;
        LocalTime startTimeMaxShiftActual = LocalTime.of(9, 1);
        LocalTime endTimeMinShiftActual = LocalTime.of(14, 17);
        Integer dayTotalTimeSecondsShiftRounded = 3 * 60 * 60;
        Integer dayMissingTimeSecondsShiftRounded = 5 * 60 * 60;
        AttendanceDayData testedObject = AttendanceDayData.builder()
                                                          .employeeName(employeeName)
                                                          .employeeNumber(employeeNumber)
                                                          .mainBudgetId(mainBudgetId)
                                                          .mainBudgetDescription(mainBudgetDescription)
                                                          .subBudgetId(subBudgetId)
                                                          .subBudgetDescription(subBudgetDescription)
                                                          .departmentCode(departmentCode)
                                                          .departmentDescription(departmentDescription)
                                                          .workingDate(workingDate)
                                                          .dayOfWeekName(dayOfWeekName)
                                                          .attendanceDayType(attendanceDayType)
                                                          .absenceCode(absenceCode)
                                                          .absenceCodeName(absenceCodeName)
                                                          .exceptionType(exceptionType)
                                                          .exceptionDescription(exceptionDescription)
                                                          .shift1ExpectedStartTime(shift1ExpectedStartTime)
                                                          .shift1ExpectedEndTime(shift1ExpectedEndTime)
                                                          .shift2ExpectedStartTime(shift2ExpectedStartTime)
                                                          .shift2ExpectedEndTime(shift2ExpectedEndTime)
                                                          .expectedTimeSeconds(expectedTimeSeconds)
                                                          .startEventType(startEventType)
                                                          .endEventType(endEventType)
                                                          .actualStartTimeA(actualStartTimeA)
                                                          .actualEndTimeA(actualEndTimeA)
                                                          .actualStartTimeAW(actualStartTimeAW)
                                                          .actualEndTimeAW(actualEndTimeAW)
                                                          .absenceTimeSeconds(absenceTimeSeconds)
                                                          .startTimeMaxShiftActual(startTimeMaxShiftActual)
                                                          .endTimeMinShiftActual(endTimeMinShiftActual)
                                                          .dayTotalTimeSecondsShiftRounded(dayTotalTimeSecondsShiftRounded)
                                                          .dayMissingTimeSecondsShiftRounded(dayMissingTimeSecondsShiftRounded)
                                                          .build();
        Assert.assertEquals("Wrong employee name!", employeeName, testedObject.getEmployeeName());
        Assert.assertEquals("Wrong employee number!", employeeNumber, testedObject.getEmployeeNumber());
        Assert.assertEquals("Wrong main budget ID!", mainBudgetId, testedObject.getMainBudgetId());
        Assert.assertEquals("Wrong main budget description!", mainBudgetDescription, testedObject.getMainBudgetDescription());
        Assert.assertEquals("Wrong sub budget ID!", subBudgetId, testedObject.getSubBudgetId());
        Assert.assertEquals("Wrong sub budget description!", subBudgetDescription, testedObject.getSubBudgetDescription());
        Assert.assertEquals("Wrong departure code!", departmentCode, testedObject.getDepartmentCode());
        Assert.assertEquals("Wrong departure description!", departmentDescription, testedObject.getDepartmentDescription());
        Assert.assertEquals("Wrong working date!", workingDate, testedObject.getWorkingDate());
        Assert.assertEquals("Wrong day of week name!", dayOfWeekName, testedObject.getDayOfWeekName());
        Assert.assertEquals("Wrong attendance day type!", attendanceDayType, testedObject.getAttendanceDayType());
        Assert.assertEquals("Wrong absence code!", absenceCode, testedObject.getAbsenceCode());
        Assert.assertEquals("Wrong absence code name!", absenceCodeName, testedObject.getAbsenceCodeName());
        Assert.assertEquals("Wrong exception type!", exceptionType, testedObject.getExceptionType());
        Assert.assertEquals("Wrong exception description!", exceptionDescription, testedObject.getExceptionDescription());
        Assert.assertEquals("Wrong shift 1 expected start time!", shift1ExpectedStartTime, testedObject.getShift1ExpectedStartTime());
        Assert.assertEquals("Wrong shift 1 expected end time!", shift1ExpectedEndTime, testedObject.getShift1ExpectedEndTime());
        Assert.assertEquals("Wrong shift 2 expected start time!", shift2ExpectedStartTime, testedObject.getShift2ExpectedStartTime());
        Assert.assertEquals("Wrong shift 2 expected end time!", shift2ExpectedEndTime, testedObject.getShift2ExpectedEndTime());
        Assert.assertEquals("Wrong Expected time seconds!", expectedTimeSeconds, testedObject.getExpectedTimeSeconds());
        Assert.assertEquals("Wrong start event type!", startEventType, testedObject.getStartEventType());
        Assert.assertEquals("Wrong end event type!", endEventType, testedObject.getEndEventType());
        Assert.assertEquals("Wrong actual start time A!", actualStartTimeA, testedObject.getActualStartTimeA());
        Assert.assertEquals("Wrong actual end time A!", actualEndTimeA, testedObject.getActualEndTimeA());
        Assert.assertEquals("Wrong actual start time AW!", actualStartTimeAW, testedObject.getActualStartTimeAW());
        Assert.assertEquals("Wrong actual end time AW!", actualEndTimeAW, testedObject.getActualEndTimeAW());
        Assert.assertEquals("Wrong absence time seconds!", absenceTimeSeconds, testedObject.getAbsenceTimeSeconds());
        Assert.assertEquals("Wrong start time max shift actual!", startTimeMaxShiftActual, testedObject.getStartTimeMaxShiftActual());
        Assert.assertEquals("Wrong end time min shift actual!", endTimeMinShiftActual, testedObject.getEndTimeMinShiftActual());
        Assert.assertEquals("Wrong day total seconds rounded!",
                            dayTotalTimeSecondsShiftRounded,
                            testedObject.getDayTotalTimeSecondsShiftRounded());
        Assert.assertEquals("Wrong day missing total seconds rounded!",
                            dayMissingTimeSecondsShiftRounded,
                            testedObject.getDayMissingTimeSecondsShiftRounded());
    }
}
