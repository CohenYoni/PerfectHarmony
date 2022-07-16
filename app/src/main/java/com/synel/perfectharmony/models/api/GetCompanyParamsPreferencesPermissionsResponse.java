package com.synel.perfectharmony.models.api;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
public class GetCompanyParamsPreferencesPermissionsResponse {

    @SerializedName("ErrorMsgId")
    private String errorMessageId;

    @SerializedName("ErrorMsgType")
    private Integer errorMessageType;

    @SerializedName("CompId")
    private String companyId;

    @SerializedName("CompName")
    private String companyName;

    @SerializedName("CompDbName")
    private String companyDatabaseName;

    @SerializedName("EncryptedDbName")
    private String encryptedDatabaseName;

    @SerializedName("IsExistsHelpFile")
    private Boolean isExistsHelpFile;

    @SerializedName("Resources")
    private Resources resources;

    @SerializedName("CompanyParams")
    private List<Param> companyParams;

    @SerializedName("CompanyPermissions")
    private List<Permission> companyPermissions;

    @SerializedName("CompanyPreferences")
    private List<Preference> companyPreferences;

    @SerializedName("CompanyPreferencesPermissions")
    private List<Permission> companyPreferencesPermissions;

    @SerializedName("FormsElementsShowTypeData")
    private Map<String, Object> formsElementsShowTypeData;

    @SerializedName("CompanySQLProcedureParams")
    private Object companySQLProcedureParams;

    @SerializedName("DateExpire")
    private String dateExpire;

    @SerializedName("ErrorMessageInLogin")
    private String errorMessageInLogin;

    @SerializedName("ExistSecureCookieOfUser")
    private Boolean existSecureCookieOfUser;

    @SerializedName("DateFormat")
    private String dateFormat;

    public boolean isEmptyCompanyData() {

        return Objects.isNull(companyId) || Objects.isNull(companyName) || Objects.isNull(companyParams) ||
            Objects.isNull(companyPermissions) || Objects.isNull(companyPreferences) || Objects.isNull(companyPreferencesPermissions);
    }
}
