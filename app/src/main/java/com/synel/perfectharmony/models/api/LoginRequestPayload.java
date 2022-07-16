package com.synel.perfectharmony.models.api;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.synel.perfectharmony.serdes.Base64Adapter;
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
public class LoginRequestPayload {

    @SerializedName("EmpIdOrName")
    private String employeeIdOrName;

    @SerializedName("IsId")
    private boolean isId;

    @SerializedName("Password")
    @JsonAdapter(Base64Adapter.class)
    private String password;

    @SerializedName("OtpCode")
    private String otpCode;

    @SerializedName("OtpPhone")
    private String otpPhone;

    @SerializedName("LanguageId")
    private String languageId;

    @SerializedName("CompanyId")
    private Integer companyId;

    @SerializedName("CompanyPermissions")
    private List<Permission> permissions;

    @SerializedName("CompanyParams")
    private List<Param> companyParams;

    @SerializedName("CompanyPreferencesPermissions")
    private List<Permission> companyPreferencesPermissions;

    @SerializedName("CompanyPreferences")
    private List<Preference> companyPreferences;

    @SerializedName("LoginByExistedRememberMe")
    private boolean loginByExistedRememberMe;

    @SerializedName("RememberMe")
    private boolean rememberMe;

    @SerializedName("LogoutHasOccuredByUser")
    private boolean logoutHasOccurredByUser;

    @SerializedName("IsFromMobileApp")
    private boolean isFromMobileApp;
}
