package com.synel.perfectharmony.models.api;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.synel.perfectharmony.serdes.LocalDateStringAdapter;
import java.time.LocalDate;
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
public class GetAttendanceQueryParam {

    @SerializedName("xFromDate")
    @JsonAdapter(LocalDateStringAdapter.class)
    private LocalDate fromDate;

    @SerializedName("xToDate")
    @JsonAdapter(LocalDateStringAdapter.class)
    private LocalDate toDate;

    @SerializedName("withFutur")
    private Integer withFuture;

    @SerializedName("xEmpVal")
    private String employeeValue;

    @SerializedName("xEmpBy")
    private Integer employedBy;

    @SerializedName("UserNo")
    private Integer userNo;

    @SerializedName("Emp_No")
    private Integer employeeId;

    @SerializedName("LPres")
    private Integer lPres;

    @SerializedName("DvcCodes")
    private String dvcCodes;

    @SerializedName("GroupLevel")
    private String groupLevel;

    @SerializedName("GroupCode")
    private Integer groupCode;

    @SerializedName("IncludeNonActiveEmp")
    private Integer includeNonActiveEmployee;

    @SerializedName("FilterState")
    private String filterState;

    @SerializedName("isSchedulModule")
    private boolean isScheduleModule;

    @SerializedName("xIsHeb")
    private boolean isHebrew;

    @SerializedName("IsGroupUpdate")
    private boolean isGroupUpdate;

    @SerializedName("isCheckMadan")
    private boolean isCheckMadan;

    @SerializedName("Updatestatus")
    private Integer updateStatus;

    @SerializedName("GridType")
    private Integer gridType;

    @SerializedName("OtherParamsXML")
    private String otherParamsXML;

    @SerializedName("PageNo")
    private Integer pageNo;

    @SerializedName("PageLength")
    private Integer pageLength;

    @SerializedName("Filter")
    private String filter;
}
