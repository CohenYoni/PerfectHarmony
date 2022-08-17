package com.synel.perfectharmony.models.api;

import com.google.gson.annotations.SerializedName;
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
public class EmployeeHeaderData {

    @SerializedName("Emp_no")
    private Integer employeeId;

    @SerializedName("Emp_name")
    private String employeeName;

    @SerializedName("Department")
    private String departmentNumber;

    @SerializedName("str_Department")
    private String departmentName;

    @SerializedName("Station")
    private String stationId;

    @SerializedName("str_station")
    private String stationName;

    @SerializedName("empGroup")
    private String employeeGroupId;

    @SerializedName("str_EmpGroup")
    private String employeeGroupName;

    @SerializedName("empAgreement")
    private String employeeAgreementId;

    @SerializedName("str_empAgreement")
    private String employeeAgreementName;

    @SerializedName("PatternNo")
    private Integer patternNo;

    @SerializedName("EmpListCount")
    private Integer employeeListCount;

    @SerializedName("StartScreen")
    private String startScreen;

    @SerializedName("EntityPermission")
    private String entityPermission;
}
