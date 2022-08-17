package com.synel.perfectharmony.models;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceCalculatedData {

    private String employeeName;

    private String employeeRole;

    private String selectedMonth;

    private String totalExpectedHoursUpToday;

    private String totalActualHoursUpToday;

    private String expectedHoursToday;

    private String extraMissingWord;

    private String extraMissingHours;

    private String actualHoursToday;

    private List<String> missingEntryDays;

    private List<String> nonApprovedDays;
}
