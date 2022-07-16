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
public class CompanyUserLoginRequestPayload {

    @SerializedName("CompanyId")
    private Integer companyId;

    @SerializedName("IsId")
    private Boolean isId;

    @SerializedName("EmpIdOrName")
    private String employeeIdOrName;

    @SerializedName("LanguageId")
    private String languageId;

    @SerializedName("CheckComp")
    private Boolean checkComp;
}
