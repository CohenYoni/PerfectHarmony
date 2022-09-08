package com.synel.perfectharmony.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.synel.perfectharmony.R;
import com.synel.perfectharmony.models.AuthData;
import com.synel.perfectharmony.models.EmployeeData;
import com.synel.perfectharmony.models.LoginFormData;
import com.synel.perfectharmony.models.api.CompanyUserLoginRequestPayload;
import com.synel.perfectharmony.models.api.ExceptionResponse;
import com.synel.perfectharmony.models.api.GetCompanyParamsPreferencesPermissionsResponse;
import com.synel.perfectharmony.models.api.GetEmployeeDataRequestParams;
import com.synel.perfectharmony.models.api.GetEmployeeDataResponsePayload;
import com.synel.perfectharmony.models.api.LoginRequestPayload;
import com.synel.perfectharmony.models.api.LoginResponsePayload;
import com.synel.perfectharmony.services.HarmonyApiClient;
import com.synel.perfectharmony.utils.Constants;
import com.synel.perfectharmony.utils.ErrorUtils;
import com.synel.perfectharmony.utils.SharedPreferencesUtil;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseMenuActivity {

    private TextInputEditText companyIdEditText;

    private TextInputEditText userIdEditText;

    private TextInputEditText passwordEditText;

    private Button loginButton;

    private SharedPreferences sharedPreferences;

    private Editor sharedPreferencesEditor;

    private CircularProgressIndicator loginProgressIndicator;

    private void initApiInterface() {

        String baseUrl = sharedPreferences.getString(getString(R.string.harmony_base_url_pref_key),
                                                     getString(R.string.harmony_base_url_pref_default));
        String apiPathPrefix = sharedPreferences.getString(getString(R.string.harmony_api_path_pref_key),
                                                           getString(R.string.harmony_api_path_pref_default));
        HarmonyApiClient.setUrl(baseUrl, apiPathPrefix);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        sharedPreferencesEditor = sharedPreferences.edit();

        initApiInterface();

        companyIdEditText = findViewById(R.id.company_id_edit_text);
        userIdEditText = findViewById(R.id.user_id_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        loginProgressIndicator = findViewById(R.id.login_loading_progress_bar);
        loginButton = findViewById(R.id.login_button);

        if (sharedPreferences.contains(Constants.LOGIN_FORM_KEY)) {
            LoginFormData loginFormData = SharedPreferencesUtil.getObject(sharedPreferences,
                                                                          Constants.LOGIN_FORM_KEY,
                                                                          null,
                                                                          LoginFormData.class);
            companyIdEditText.setText(String.valueOf(loginFormData.getCompanyId()));
            userIdEditText.setText(String.valueOf(loginFormData.getUserId()));
            passwordEditText.setText(loginFormData.getPassword());
        }

        loginButton.setOnClickListener(view -> getCompanyDataAndLogin());

        if (sharedPreferences.contains(Constants.AUTH_KEY)) {
            AuthData authData = SharedPreferencesUtil.getObject(sharedPreferences, Constants.AUTH_KEY, null, AuthData.class);
            fetchEmployeeData(authData);
        }
    }

    private Integer validateCompanyId() {

        Editable companyIdEditable;
        String companyIdString;
        if ((companyIdEditable = companyIdEditText.getText()) == null ||
            (companyIdString = companyIdEditable.toString()).trim().isEmpty()) {
            companyIdEditText.setError(getText(R.string.required_field_error));
            return null;
        }
        if (!companyIdString.matches("\\d+")) {
            companyIdEditText.setError(getText(R.string.only_digit_error));
            return null;
        }
        return Integer.valueOf(companyIdString);
    }

    private String validateUserId() {

        Editable userIdEditable;
        String userIdString;
        if ((userIdEditable = userIdEditText.getText()) == null || (userIdString = userIdEditable.toString()).trim().isEmpty()) {
            userIdEditText.setError(getText(R.string.required_field_error));
            return null;
        }
        if (!userIdString.matches("\\d+")) {
            companyIdEditText.setError(getText(R.string.only_digit_error));
            return null;
        }
        return userIdString;
    }

    private String validatePassword() {

        Editable passwordEditable;
        String passwordString;
        if ((passwordEditable = passwordEditText.getText()) == null || (passwordString = passwordEditable.toString()).trim().isEmpty()) {
            passwordEditText.setError(getText(R.string.required_field_error));
            return null;
        }
        return passwordString;
    }

    private void handleHttpError(CharSequence errorMessage) {

        new MaterialAlertDialogBuilder(MainActivity.this)
            .setMessage(errorMessage)
            .setPositiveButton(getText(R.string.ok_button), null)
            .show();
        toggleLoadingCircleBar(false);
    }

    private void toggleLoadingCircleBar(boolean turnOn) {

        loginProgressIndicator.setVisibility(turnOn ? View.VISIBLE : View.GONE);
        companyIdEditText.setEnabled(!turnOn);
        userIdEditText.setEnabled(!turnOn);
        passwordEditText.setEnabled(!turnOn);
        loginButton.setVisibility(turnOn ? View.GONE : View.VISIBLE);
        if (turnOn) {
            loginProgressIndicator.requestFocus();
        }
    }

    private void getCompanyDataAndLogin() {

        Integer companyId = validateCompanyId();
        String userId = validateUserId();
        String password = validatePassword();
        if (Objects.isNull(companyId) || Objects.isNull(userId) || Objects.isNull(password)) {
            return;
        }
        CompanyUserLoginRequestPayload companyUserLoginRequestPayload = CompanyUserLoginRequestPayload.builder()
                                                                                                      .companyId(companyId)
                                                                                                      .isId(Constants.COMPANY_USER_LOGIN_REQUEST_PAYLOAD_IS_ID)
                                                                                                      .employeeIdOrName(userId)
                                                                                                      .languageId(Constants.COMPANY_USER_LOGIN_REQUEST_PAYLOAD_LANGUAGE_ID)
                                                                                                      .checkComp(Constants.COMPANY_USER_LOGIN_REQUEST_PAYLOAD_CHECK_COMP)
                                                                                                      .build();

        Call<GetCompanyParamsPreferencesPermissionsResponse> companyParamsPreferencesPermissions = HarmonyApiClient.getApiInterface()
                                                                                                                   .getCompanyParamsPreferencesPermissions(
                                                                                                                       companyUserLoginRequestPayload);
        toggleLoadingCircleBar(true);
        companyParamsPreferencesPermissions.enqueue(new Callback<GetCompanyParamsPreferencesPermissionsResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetCompanyParamsPreferencesPermissionsResponse> call,
                                   @NonNull Response<GetCompanyParamsPreferencesPermissionsResponse> response) {

                if (response.body() == null || !response.isSuccessful() || response.body().isEmptyCompanyData()) {
                    handleHttpError(getText(R.string.fetch_company_data_error));
                    return;
                }
                login(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<GetCompanyParamsPreferencesPermissionsResponse> call, @NonNull Throwable t) {

                call.cancel();
                handleHttpError(t.getMessage());
            }
        });
    }

    private void login(@NonNull GetCompanyParamsPreferencesPermissionsResponse companyData) {

        Integer companyId = validateCompanyId();
        String userId = validateUserId();
        String password = validatePassword();
        if (companyId == null || userId == null || password == null) {
            toggleLoadingCircleBar(false);
            return;
        }
        LoginRequestPayload loginRequestPayload = LoginRequestPayload.builder()
                                                                     .employeeIdOrName(userId)
                                                                     .isId(Constants.LOGIN_REQUEST_PAYLOAD_IS_ID)
                                                                     .password(password)
                                                                     .otpCode(Constants.LOGIN_REQUEST_PAYLOAD_OPT_CODE)
                                                                     .otpPhone(Constants.LOGIN_REQUEST_PAYLOAD_OPT_PHONE)
                                                                     .languageId(Constants.LOGIN_REQUEST_PAYLOAD_LANGUAGE_ID)
                                                                     .companyId(companyId)
                                                                     .permissions(companyData.getCompanyPermissions())
                                                                     .companyParams(companyData.getCompanyParams())
                                                                     .companyPreferencesPermissions(companyData.getCompanyPreferencesPermissions())
                                                                     .companyPreferences(companyData.getCompanyPreferences())
                                                                     .loginByExistedRememberMe(Constants.LOGIN_REQUEST_PAYLOAD_LOGIN_BY_EXISTED_REMEMBER_ME)
                                                                     .rememberMe(Constants.LOGIN_REQUEST_PAYLOAD_REMEMBER_ME)
                                                                     .logoutHasOccurredByUser(Constants.LOGIN_REQUEST_PAYLOAD_LOGOUT_HAS_OCCURRED_BY_USER)
                                                                     .isFromMobileApp(Constants.LOGIN_REQUEST_PAYLOAD_IS_FROM_MOBILE_APP)
                                                                     .build();

        Call<LoginResponsePayload> login = HarmonyApiClient.getApiInterface().login(loginRequestPayload);
        login.enqueue(new Callback<LoginResponsePayload>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponsePayload> call,
                                   @NonNull Response<LoginResponsePayload> response) {

                if (!response.isSuccessful()) {
                    if (response.errorBody() == null) {
                        handleHttpError(getText(R.string.general_login_error));
                        return;
                    }
                    LoginResponsePayload errorResponse;
                    try {
                        errorResponse = new Gson().fromJson(response.errorBody().string(), LoginResponsePayload.class);
                    } catch (Exception e) {
                        handleHttpError(getText(R.string.general_login_error));
                        return;
                    }
                    if (Objects.nonNull(errorResponse.getExceptionType()) || errorResponse.getError() != 0) {
                        handleHttpError(getText(R.string.server_exception_prefix) + errorResponse.getErrorMessage());
                        return;
                    }
                    handleHttpError(getText(R.string.general_login_error));
                    return;
                }
                if (response.body() == null) {
                    handleHttpError(getText(R.string.general_login_error));
                    return;
                }
                if (Objects.nonNull(response.body().getExceptionType()) || response.body().getError() != 0) {
                    handleHttpError(response.body().getErrorMessage());
                    return;
                }

                LoginFormData loginFormData = LoginFormData.builder()
                                                           .companyId(companyId)
                                                           .userId(response.body().getEmployeeId())
                                                           .password(password)
                                                           .build();
                AuthData authData = AuthData.builder()
                                            .employeeId(response.body().getEmployeeId())
                                            .sessionId(response.body().getSessionId())
                                            .profileCode(response.body().getChosenProfile().getProfileCode())
                                            .build();
                SharedPreferencesUtil.putObject(sharedPreferencesEditor, Constants.LOGIN_FORM_KEY, loginFormData);
                SharedPreferencesUtil.putObject(sharedPreferencesEditor, Constants.AUTH_KEY, authData);
                sharedPreferencesEditor.commit();

                fetchEmployeeData(AuthData.builder()
                                          .sessionId(response.body().getSessionId())
                                          .employeeId(response.body().getEmployeeId())
                                          .profileCode(response.body().getChosenProfile().getProfileCode())
                                          .build());
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponsePayload> call, @NonNull Throwable t) {

                call.cancel();
                handleHttpError(t.getMessage());
            }
        });
    }

    private void fetchEmployeeData(AuthData authData) {

        if (sharedPreferences.contains(Constants.EMPLOYEE_DATA_KEY)) {
            goToAttendanceDataActivity();
            return;
        }
        GetEmployeeDataRequestParams employeeDataRequestParams = GetEmployeeDataRequestParams.builder()
                                                                                             .employeeId(authData.getEmployeeId())
                                                                                             .permGroup(authData.getProfileCode())
                                                                                             .build();
        Call<GetEmployeeDataResponsePayload> employeeDataCall = HarmonyApiClient.getApiInterface()
                                                                                .getEmployeeData(authData.getSessionId(),
                                                                                                 employeeDataRequestParams);
        toggleLoadingCircleBar(true);
        employeeDataCall.enqueue(new Callback<GetEmployeeDataResponsePayload>() {
            @Override
            public void onResponse(@NonNull Call<GetEmployeeDataResponsePayload> call,
                                   @NonNull Response<GetEmployeeDataResponsePayload> response) {

                if (!response.isSuccessful()) {
                    if (response.errorBody() == null) {
                        handleHttpError(getText(R.string.fetch_employee_data_error));
                        return;
                    }
                    if (response.code() == 400) {
                        try {
                            ExceptionResponse exceptionResponse = new Gson().fromJson(response.errorBody().string(),
                                                                                      ExceptionResponse.class);
                            handleHttpError(getText(R.string.server_exception_prefix) + exceptionResponse.getErrorMessage());
                        } catch (Exception e) {
                            handleHttpError(getText(R.string.fetch_employee_data_error));
                        }
                        return;
                    }
                    String errorMessage = ErrorUtils.parseErrorJsonResponse(response.errorBody(),
                                                                            getString(R.string.server_exception_prefix),
                                                                            getString(R.string.fetch_employee_data_error));
                    handleHttpError(errorMessage);
                    return;
                }
                if (response.body() == null) {
                    handleHttpError(getText(R.string.fetch_employee_data_error));
                    return;
                }
                EmployeeData employeeData = EmployeeData.builder()
                                                        .name(response.body().getEmployeeHeaderData().getEmployeeName())
                                                        .role(response.body().getEmployeeHeaderData().getDepartmentName())
                                                        .build();
                SharedPreferencesUtil.putObject(sharedPreferencesEditor, Constants.EMPLOYEE_DATA_KEY, employeeData);
                sharedPreferencesEditor.commit();
                toggleLoadingCircleBar(false);
                goToAttendanceDataActivity();
            }

            @Override
            public void onFailure(@NonNull Call<GetEmployeeDataResponsePayload> call, @NonNull Throwable t) {

                call.cancel();
                handleHttpError(t.getMessage());
            }
        });
    }

    private void goToAttendanceDataActivity() {

        Intent intent = new Intent(MainActivity.this, AttendanceDataActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }
}