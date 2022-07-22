package com.synel.perfectharmony.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textview.MaterialTextView;
import com.synel.perfectharmony.R;
import com.synel.perfectharmony.services.HarmonyApiClient;
import com.synel.perfectharmony.utils.Constants;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendanceDataActivity extends BaseMenuActivity {

    private SharedPreferences sharedPreferences;

    private Editor sharedPreferencesEditor;

    private CircularProgressIndicator fetchServerProgressIndicator;

    private MaterialTextView fetchServerProgressIndicatorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_data);

        fetchServerProgressIndicator = findViewById(R.id.fetch_server_progress_bar);
        fetchServerProgressIndicatorText = findViewById(R.id.fetch_server_progress_bar_text);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AttendanceDataActivity.this);
        sharedPreferencesEditor = sharedPreferences.edit();

        checkLoginAndGetAttendanceData();
    }

    private void toggleLoadingCircleBar(boolean turnOn) {

        fetchServerProgressIndicator.setVisibility(turnOn ? View.VISIBLE : View.GONE);
        fetchServerProgressIndicatorText.setVisibility(turnOn ? View.VISIBLE : View.GONE);
    }

    private void handleError(CharSequence errorMessage) {

        Toast.makeText(AttendanceDataActivity.this, errorMessage, Toast.LENGTH_LONG).show();
        toggleLoadingCircleBar(false);
    }

    private void checkLoginAndGetAttendanceData() {

        String sessionId = sharedPreferences.getString(Constants.SESSION_ID_PREF_KEY, null);
        if (Objects.isNull(sessionId)) {
            return;
        }
        Call<Boolean> checkUserLoggedInCall = HarmonyApiClient.getApiInterface().checkUserLogin(sessionId);
        toggleLoadingCircleBar(true);
        checkUserLoggedInCall.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response) {

                if (response.body() == null || !response.isSuccessful()) {
                    handleError(getText(R.string.check_user_login_error));
                    goToLoginActivity();
                    return;
                }
                if (!response.body()) {  // The user is not logged in
                    toggleLoadingCircleBar(false);
                    Toast.makeText(AttendanceDataActivity.this, R.string.login_expired, Toast.LENGTH_LONG).show();
                    goToLoginActivity();
                    return;
                }
                toggleLoadingCircleBar(false);
                fetchAttendanceData();
            }

            @Override
            public void onFailure(@NonNull Call<Boolean> call, @NonNull Throwable t) {

                call.cancel();
                handleError(t.getMessage());
                goToLoginActivity();
            }
        });
    }

    private void fetchAttendanceData() {
        // TODO: fetch the data and show it
    }

    private void goToLoginActivity() {

        sharedPreferencesEditor.remove(Constants.SESSION_ID_PREF_KEY);
        sharedPreferencesEditor.remove(Constants.USER_NO_PREF_KEY);
        sharedPreferencesEditor.commit();
        Intent intent = new Intent(AttendanceDataActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }
}
