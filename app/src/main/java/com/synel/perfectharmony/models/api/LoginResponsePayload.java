package com.synel.perfectharmony.models.api;

import com.google.gson.annotations.SerializedName;
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
public class LoginResponsePayload {

    @SerializedName("EmpId")
    private Integer employeeId;

    @SerializedName("Profiles")
    private List<Profile> profiles;

    @SerializedName("Permissions")
    private List<Permission> employeePermissions;

    @SerializedName("MngDashboardsPremissions")
    private List<Permission> managerDashboardsPermissions;

    @SerializedName("EmpDashboardsPremissions")
    private List<Permission> employeeDashboardsPermissions;

    @SerializedName("SessionId")
    private String sessionId;

    @SerializedName("Error")
    private Integer error;

    @SerializedName("ErrorMessage")
    private String errorMessage;

    @SerializedName("ErrorMessageId")
    private Integer errorMessageId;

    @SerializedName("CompanyParams")
    private List<Param> companyParams;

    @SerializedName("CompanyPermissions")
    private List<Permission> companyPermissions;

    @SerializedName("CompanyPreferences")
    private List<Preference> companyPreferences;

    @SerializedName("CompanyPreferencesPermissions")
    private List<Permission> companyPreferencesPermissions;

    @SerializedName("GridsLayouts")
    private List<GridLayout> gridsLayouts;

    @SerializedName("CurrentTaxYear")
    private Integer currentTaxYear;

    @SerializedName("ChosenProfile")
    private Profile chosenProfile;

    @SerializedName("ExType")
    private Integer exceptionType;

    @SerializedName("InnerException")
    private String innerException;
}
