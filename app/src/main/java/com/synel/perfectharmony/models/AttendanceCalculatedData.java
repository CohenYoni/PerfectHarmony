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

    private boolean isCurrentMonthData;

    private String totalExpectedHoursUpToday;

    private String totalActualHoursUpToday;

    private String expectedHoursToday;

    private String extraMissingWord;

    private String extraMissingHours;

    private String actualHoursToday;

    private String maxHoursWarning;

    private List<String> missingEntryDays;

    private List<String> nonApprovedDays;
}
