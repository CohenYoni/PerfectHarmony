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
public class GetEmployeeDataResponsePayload {

    @SerializedName("EmployeeHeaderData")
    private EmployeeHeaderData employeeHeaderData;

    @SerializedName("EmpLookupRecordsCount")
    private Integer employeeLookupRecordsCount;

    @SerializedName("IsSecondMng")
    private Integer isSecondMng;
}
