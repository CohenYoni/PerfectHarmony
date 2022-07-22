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
import com.synel.perfectharmony.models.api.CompanyUserLoginRequestPayload;
import com.synel.perfectharmony.models.api.GetCompanyParamsPreferencesPermissionsResponse;
import com.synel.perfectharmony.models.api.LoginRequestPayload;
import com.synel.perfectharmony.models.api.LoginResponsePayload;
import com.synel.perfectharmony.services.HarmonyApiClient;
import com.synel.perfectharmony.services.HarmonyApiInterface;
import com.synel.perfectharmony.utils.Constants;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseMenuActivity {

    private TextInputEditText companyIdEditText;

    private TextInputEditText userIdEditText;

    private TextInputEditText passwordEditText;

    private Button loginButton;

    SharedPreferences sharedPreferences;

    private Editor sharedPreferencesEditor;

    private CircularProgressIndicator loginProgressIndicator;

    private HarmonyApiInterface getApiInterface() {

        String baseUrl = sharedPreferences.getString(getString(R.string.harmony_base_url_pref_key),
                                                     getString(R.string.harmony_base_url_pref_default));
        String apiPathPrefix = sharedPreferences.getString(getString(R.string.harmony_api_path_pref_key),
                                                           getString(R.string.harmony_api_path_pref_default));
        return HarmonyApiClient.getApiInterface(baseUrl, apiPathPrefix);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        sharedPreferencesEditor = sharedPreferences.edit();

        if (sharedPreferences.contains(Constants.SESSION_ID_PREF_KEY)) {
            goToAttendanceDataActivity();
            return;
        }

        companyIdEditText = findViewById(R.id.company_id_edit_text);
        userIdEditText = findViewById(R.id.user_id_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        loginProgressIndicator = findViewById(R.id.login_loading_progress_bar);
        loginButton = findViewById(R.id.login_button);

        if (sharedPreferences.contains(Constants.COMPANY_ID_PREF_KEY)) {
            companyIdEditText.setText(String.valueOf(sharedPreferences.getInt(Constants.COMPANY_ID_PREF_KEY, 0)));
        }

        if (sharedPreferences.contains(Constants.EMPLOYEE_ID_PREF_KEY)) {
            userIdEditText.setText(String.valueOf(sharedPreferences.getInt(Constants.EMPLOYEE_ID_PREF_KEY, 0)));
        }

        if (sharedPreferences.contains(Constants.PASSWORD_PREF_KEY)) {
            passwordEditText.setText(sharedPreferences.getString(Constants.PASSWORD_PREF_KEY, ""));
        }

        loginButton.setOnClickListener(view -> getCompanyDataAndLogin());
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
                                                                                                      .isId(true)
                                                                                                      .employeeIdOrName(userId)
                                                                                                      .languageId("3")
                                                                                                      .checkComp(true)
                                                                                                      .build();

        Call<GetCompanyParamsPreferencesPermissionsResponse> companyParamsPreferencesPermissions = getApiInterface().getCompanyParamsPreferencesPermissions(
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
                                                                     .isId(true)
                                                                     .password(password)
                                                                     .otpCode("")
                                                                     .otpPhone("")
                                                                     .languageId("3")
                                                                     .companyId(companyId)
                                                                     .permissions(companyData.getCompanyPermissions())
                                                                     .companyParams(companyData.getCompanyParams())
                                                                     .companyPreferencesPermissions(companyData.getCompanyPreferencesPermissions())
                                                                     .companyPreferences(companyData.getCompanyPreferences())
                                                                     .loginByExistedRememberMe(false)
                                                                     .rememberMe(false)
                                                                     .logoutHasOccurredByUser(false)
                                                                     .isFromMobileApp(false)
                                                                     .build();

        Call<LoginResponsePayload> login = getApiInterface().login(loginRequestPayload);
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

                sharedPreferencesEditor.putString(Constants.SESSION_ID_PREF_KEY, response.body().getSessionId());
                sharedPreferencesEditor.putInt(Constants.COMPANY_ID_PREF_KEY, companyId);
                sharedPreferencesEditor.putInt(Constants.EMPLOYEE_ID_PREF_KEY, response.body().getEmployeeId());
                sharedPreferencesEditor.putString(Constants.PASSWORD_PREF_KEY, password);
                sharedPreferencesEditor.putInt(Constants.USER_NO_PREF_KEY, response.body().getChosenProfile().getProfileCode());
                sharedPreferencesEditor.commit();
                toggleLoadingCircleBar(false);
                goToAttendanceDataActivity();
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponsePayload> call, @NonNull Throwable t) {

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