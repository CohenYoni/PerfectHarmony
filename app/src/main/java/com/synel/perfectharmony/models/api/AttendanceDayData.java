package com.synel.perfectharmony.models.api;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.synel.perfectharmony.serdes.IntSecondNumOfHoursAdapter;
import com.synel.perfectharmony.serdes.IntStringAdapter;
import com.synel.perfectharmony.serdes.LocalDateStringAdapter;
import com.synel.perfectharmony.serdes.LocalTimeStringAdapter;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * POJO model for one day attendance data of an employee.
 */
@Setter
@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDayData {

    /* Employee details */

    @SerializedName("Emp_Name")
    private String employeeName;

    @SerializedName("Emp_noA")
    private Integer employeeNumber;

    /* Work details */

    @SerializedName("budgID_AW")
    private String mainBudgetId;

    @SerializedName("budgDescr_AW")
    private String mainBudgetDescription;

    @SerializedName("subBudgID_AW")
    private String subBudgetId;

    @SerializedName("subBudgDescr_AW")
    private String subBudgetDescription;

    @SerializedName("DepartmentCode")
    private String departmentCode;

    @SerializedName("DepartmentDescr")
    private String departmentDescription;

    /* Working day details */

    @SerializedName("WorkDateA")
    @JsonAdapter(LocalDateStringAdapter.class)
    private LocalDate workingDate;

    @SerializedName("DayName")
    private String dayOfWeekName;

    @SerializedName("Type")
    private String attendanceDayType;

    @SerializedName("AbsenceCodeAW")
    @JsonAdapter(IntStringAdapter.class)
    private Integer absenceCode;  // 0 - not absent, 2 - sick

    @SerializedName("NameAbsenceCodeAW")
    private String absenceCodeName;

    @SerializedName("Exception")
    private String exceptionCode;  // 1282 - not a working day, 1283 - missing day, 1285 - missing entry

    @SerializedName("ExceptType")
    private Integer exceptionType;  // 0 - no exception, 3 - missing day

    @SerializedName("lstExceptDict")
    private String exceptionDescription;

    /* Actual times */

    @SerializedName("shift1_start")
    @JsonAdapter(LocalTimeStringAdapter.class)
    private LocalTime shift1ExpectedStartTime;

    @SerializedName("shift1_end")
    @JsonAdapter(LocalTimeStringAdapter.class)
    private LocalTime shift1ExpectedEndTime;

    @SerializedName("shift2_start")
    @JsonAdapter(LocalTimeStringAdapter.class)
    private LocalTime shift2ExpectedStartTime;

    @SerializedName("shift2_end")
    @JsonAdapter(LocalTimeStringAdapter.class)
    private LocalTime shift2ExpectedEndTime;

    @SerializedName("ExpectedTimeAW")
    @JsonAdapter(IntSecondNumOfHoursAdapter.class)
    private Integer expectedTimeSeconds;

    @SerializedName("NameTrnFnc_startAW")
    private String startEventType;

    @SerializedName("NameTrnFnc_endAW")
    private String endEventType;

    @SerializedName("Time_startA")
    @JsonAdapter(LocalTimeStringAdapter.class)
    private LocalTime actualStartTimeA;

    @SerializedName("Time_endA")
    @JsonAdapter(LocalTimeStringAdapter.class)
    private LocalTime actualEndTimeA;

    @SerializedName("Time_startAW")
    @JsonAdapter(LocalTimeStringAdapter.class)
    private LocalTime actualStartTimeAW;

    @SerializedName("Time_endAW")
    @JsonAdapter(LocalTimeStringAdapter.class)
    private LocalTime actualEndTimeAW;

    @SerializedName("AbsenceTimeAW")
    @JsonAdapter(IntSecondNumOfHoursAdapter.class)
    private Integer absenceTimeSeconds;

    /* Rounded times */

    @SerializedName("Time_startA_RND")
    @JsonAdapter(LocalTimeStringAdapter.class)
    private LocalTime startTimeMaxShiftActual;

    @SerializedName("Time_endA_RND")
    @JsonAdapter(LocalTimeStringAdapter.class)
    private LocalTime endTimeMinShiftActual;

    @SerializedName("Day_TotalAW")
    @JsonAdapter(IntSecondNumOfHoursAdapter.class)
    private Integer dayTotalTimeSecondsShiftRounded;

    @SerializedName("MissAW")
    @JsonAdapter(IntSecondNumOfHoursAdapter.class)
    private Integer dayMissingTimeSecondsShiftRounded;  // can be negative (missing) or positive (extra)

    /* Approval data */

    @SerializedName("UpdateStatCodeAW")
    @JsonAdapter(IntStringAdapter.class)
    private Integer approvalStatusCodeAW;  // 1 - approved
}
