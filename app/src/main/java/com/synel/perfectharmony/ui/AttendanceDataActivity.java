package com.synel.perfectharmony.ui;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.preference.PreferenceManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textview.MaterialTextView;
import com.kal.rackmonthpicker.MonthAdapter;
import com.kal.rackmonthpicker.RackMonthPicker;
import com.synel.perfectharmony.R;
import com.synel.perfectharmony.databinding.ActivityAttendanceDataBinding;
import com.synel.perfectharmony.models.AttendanceCalculatedData;
import com.synel.perfectharmony.models.AuthData;
import com.synel.perfectharmony.models.EmployeeData;
import com.synel.perfectharmony.models.api.AttendanceDayData;
import com.synel.perfectharmony.models.api.AttendanceResponsePayload;
import com.synel.perfectharmony.models.api.GetAttendanceQueryParam;
import com.synel.perfectharmony.services.HarmonyApiClient;
import com.synel.perfectharmony.services.HarmonyAttendanceCalculator;
import com.synel.perfectharmony.utils.Constants;
import com.synel.perfectharmony.utils.ErrorUtils;
import com.synel.perfectharmony.utils.LocalTimeUtils;
import com.synel.perfectharmony.utils.SharedPreferencesUtil;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendanceDataActivity extends BaseMenuActivity {

    private SharedPreferences sharedPreferences;

    private Editor sharedPreferencesEditor;

    private CircularProgressIndicator fetchServerProgressIndicator;

    private MaterialTextView fetchServerProgressIndicatorText;

    private RackMonthPicker monthPicker;

    private AuthData authData;

    private int selectedMonth;

    private int selectedYear;

    ListView missingEntryListView;

    ListView nonApprovedDaysListView;

    ActivityAttendanceDataBinding binding;

    MaterialButton changeMonthButton;

    MonthAdapter monthAdapter;

    public AttendanceDataActivity() {

        this.monthAdapter = new MonthAdapter(this, null);
        monthAdapter.setLocale(Locale.getDefault());
    }

    private String getSelectedMonthText(int month, int year) {

        monthAdapter.setSelectedItem(month - 1);
        return monthAdapter.getShortMonth() + ", " + year;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AttendanceDataActivity.this);
        sharedPreferencesEditor = sharedPreferences.edit();
        YearMonth yearMonthNow = YearMonth.now();
        selectedYear = yearMonthNow.getYear();
        selectedMonth = yearMonthNow.getMonthValue();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_attendance_data);

        fetchServerProgressIndicator = findViewById(R.id.fetch_server_progress_bar);
        fetchServerProgressIndicatorText = findViewById(R.id.fetch_server_progress_bar_text);
        missingEntryListView = findViewById(R.id.missing_entry_days_list_view);
        nonApprovedDaysListView = findViewById(R.id.non_approved_days_list_view);
        changeMonthButton = findViewById(R.id.change_month_button);

        monthPicker = new RackMonthPicker(this)
            .setLocale(Locale.getDefault())
            .setPositiveText(getString(R.string.ok_button))
            .setNegativeText(getString(R.string.cancel_button))
            .setPositiveButton((month, startDate, endDate, year, monthLabel) -> {
                selectedMonth = month;
                selectedYear = year;
                binding.setSelectedMonthStr(monthLabel);
                binding.setAttendanceCalculatedData(null);
                updateArrayAdapters(Collections.emptyList(), Collections.emptyList());
                checkLoginAndGetAttendanceData();
            })
            .setNegativeButton(Dialog::cancel);
        changeMonthButton.setOnClickListener(view -> monthPicker.show());
        binding.setSelectedMonthStr(getSelectedMonthText(selectedMonth, selectedYear));

        if (!sharedPreferences.contains(Constants.EMPLOYEE_DATA_KEY)) {
            handleError(getText(R.string.employee_data_missing));
            goToLoginActivity();
            return;
        }
        EmployeeData employeeData = SharedPreferencesUtil.getObject(sharedPreferences,
                                                                    Constants.EMPLOYEE_DATA_KEY,
                                                                    null,
                                                                    EmployeeData.class);
        binding.setEmployeeData(employeeData);

        checkLoginAndGetAttendanceData();
    }

    private void toggleLoadingCircleBar(boolean turnOn) {

        fetchServerProgressIndicator.setVisibility(turnOn ? View.VISIBLE : View.GONE);
        fetchServerProgressIndicatorText.setVisibility(turnOn ? View.VISIBLE : View.GONE);
    }

    private void handleError(CharSequence errorMessage) {

        Toast.makeText(AttendanceDataActivity.this, errorMessage, Toast.LENGTH_LONG).show();
        toggleLoadingCircleBar(false);
        changeMonthButton.setClickable(true);
    }

    private void checkLoginAndGetAttendanceData() {

        if (!sharedPreferences.contains(Constants.AUTH_KEY)) {
            goToLoginActivity();
            return;
        }
        binding.setIsEmptyAttendanceData(false);
        authData = SharedPreferencesUtil.getObject(sharedPreferences, Constants.AUTH_KEY, null, AuthData.class);
        Call<Boolean> checkUserLoggedInCall = HarmonyApiClient.getApiInterface().checkUserLogin(authData.getSessionId());
        toggleLoadingCircleBar(true);
        changeMonthButton.setClickable(false);
        checkUserLoggedInCall.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response) {

                if (response.body() == null || !response.isSuccessful()) {
                    handleError(getText(R.string.check_user_login_error));
                    goToLoginActivity();
                    return;
                }
                if (!response.body()) {  // The user is not logged in
                    handleError(getText(R.string.login_expired));
                    goToLoginActivity();
                    return;
                }
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

        YearMonth selectedMonth = YearMonth.of(selectedYear, this.selectedMonth);
        GetAttendanceQueryParam attendanceQueryParam = GetAttendanceQueryParam.builder()
                                                                              .fromDate(selectedMonth.atDay(Constants.FIRST_DAY_OF_MONTH))
                                                                              .toDate(selectedMonth.atEndOfMonth())
                                                                              .withFuture(Constants.GET_ATTENDANCE_QUERY_PARAM_WITH_FUTURE)
                                                                              .employeeValue(Constants.GET_ATTENDANCE_QUERY_PARAM_EMPLOYEE_VALUE)
                                                                              .employedBy(Constants.GET_ATTENDANCE_QUERY_PARAM_EMPLOYEE_BY)
                                                                              .userNo(authData.getProfileCode())
                                                                              .employeeId(authData.getEmployeeId())
                                                                              .lPres(Constants.GET_ATTENDANCE_QUERY_PARAM_L_PRES)
                                                                              .dvcCodes(Constants.GET_ATTENDANCE_QUERY_PARAM_DVC_CODES)
                                                                              .groupLevel(Constants.GET_ATTENDANCE_QUERY_PARAM_GROUP_LEVEL)
                                                                              .groupCode(authData.getEmployeeId())
                                                                              .includeNonActiveEmployee(Constants.GET_ATTENDANCE_QUERY_PARAM_INCLUDE_NON_ACTIVE_EMPLOYEE)
                                                                              .filterState(Constants.GET_ATTENDANCE_QUERY_PARAM_FILTER_STATE)
                                                                              .isScheduleModule(Constants.GET_ATTENDANCE_QUERY_IS_SCHEDULE_MODULE)
                                                                              .isHebrew(Constants.GET_ATTENDANCE_QUERY_IS_HEBREW)
                                                                              .isGroupUpdate(Constants.GET_ATTENDANCE_QUERY_IS_GROUP_UPDATE)
                                                                              .isCheckMadan(Constants.GET_ATTENDANCE_QUERY_IS_CHECK_MADAN)
                                                                              .updateStatus(Constants.GET_ATTENDANCE_QUERY_UPDATE_STATUS)
                                                                              .gridType(Constants.GET_ATTENDANCE_QUERY_GRID_TYPE)
                                                                              .otherParamsXML(Constants.GET_ATTENDANCE_QUERY_OTHER_PARAMS_XML)
                                                                              .pageNo(Constants.GET_ATTENDANCE_QUERY_PAGE_NO)
                                                                              .pageLength(Constants.GET_ATTENDANCE_QUERY_PAGE_LENGTH)
                                                                              .filter(Constants.GET_ATTENDANCE_QUERY_FILTER)
                                                                              .build();
        Timestamp timestampNow = new Timestamp(System.currentTimeMillis());

        Call<AttendanceResponsePayload> attendanceDataCall = HarmonyApiClient.getApiInterface().getAttendanceData(authData.getSessionId(),
                                                                                                                  attendanceQueryParam,
                                                                                                                  Constants.ATTENDANCE_DATA_TAKE_PARAM,
                                                                                                                  Constants.ATTENDANCE_DATA_SKIP_PARAM,
                                                                                                                  Constants.ATTENDANCE_DATA_PAGE_PARAM,
                                                                                                                  Constants.ATTENDANCE_DATA_PAGE_SIZE_PARAM,
                                                                                                                  timestampNow.getTime());
        attendanceDataCall.enqueue(new Callback<AttendanceResponsePayload>() {
            @Override
            public void onResponse(@NonNull Call<AttendanceResponsePayload> call, @NonNull Response<AttendanceResponsePayload> response) {

                if (response.body() == null) {
                    handleError(getText(R.string.fetch_attendance_data_error));
                    goToLoginActivity();
                    return;
                }
                if (!response.isSuccessful()) {
                    String errorMessage = ErrorUtils.parseErrorJsonResponse(response.errorBody(),
                                                                            getString(R.string.server_exception_prefix),
                                                                            getString(R.string.fetch_attendance_data_error));
                    handleError(errorMessage);
                    goToLoginActivity();
                    return;
                }
                calculateAttendanceData(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<AttendanceResponsePayload> call, @NonNull Throwable t) {

                call.cancel();
                handleError(t.getMessage());
                goToLoginActivity();
            }
        });
    }

    private void calculateAttendanceData(AttendanceResponsePayload attendanceResponsePayload) {

        Optional<AttendanceDayData> lastDateOfMonth = attendanceResponsePayload.getAttendanceDaysData().stream()
                                                                               .max(Comparator.comparing(AttendanceDayData::getWorkingDate));
        if (!lastDateOfMonth.isPresent()) {
            binding.setIsEmptyAttendanceData(true);
            toggleLoadingCircleBar(false);
            changeMonthButton.setClickable(true);
            return;
        }
        binding.setIsEmptyAttendanceData(false);

        // TODO: 04/09/2022 Add alert on exit
        // TODO: 04/09/2022 colors (green?) + dark mode

        String maxHoursStr = sharedPreferences.getString(getString(R.string.max_hours_pref_key),
                                                         getString(R.string.max_hours_pref_default));
        LocalTime maxHours = LocalTime.parse(maxHoursStr, Constants.TIME_FORMATTER);

        HarmonyAttendanceCalculator attendanceCalculator = new HarmonyAttendanceCalculator(attendanceResponsePayload, maxHours);

        LocalDate selectedDate = lastDateOfMonth.get().getWorkingDate();
        YearMonth selectedMonth = YearMonth.of(selectedDate.getYear(), selectedDate.getMonth());
        YearMonth currentMonth = YearMonth.now();
        boolean isCurrentMonthSelected = selectedMonth.equals(currentMonth);
        LocalDate workingDate = isCurrentMonthSelected ? LocalDate.now() : selectedDate;

        String totalExpectedHours = LocalTimeUtils.secToHHmmStr(attendanceCalculator.getTotalNumOfExpectedWorkingHoursInSeconds(selectedDate));
        String totalActualHours = LocalTimeUtils.secToHHmmStr(attendanceCalculator.getTotalNumOfActualWorkingHoursInSeconds(selectedDate));
        int expectedHoursTodaySeconds = attendanceCalculator.getExpectedDayWorkingHours(workingDate);
        String expectedHoursToday = LocalTimeUtils.secToHHmmStr(expectedHoursTodaySeconds);
        int extraMissingSecToday = attendanceCalculator.calculateNumOfExtraMissingHoursInSeconds(workingDate);
        String extraMissingWord = extraMissingSecToday >= 0 ? getString(R.string.extra_hours_word) : getString(R.string.missing_hours_word);
        String extraMissingHoursToday = LocalTimeUtils.secToHHmmStr(Math.abs(extraMissingSecToday));
        String actualHoursTodaySentence = null;
        AttendanceDayData dayData = attendanceCalculator.getAttendanceDayData(workingDate).orElse(null);
        int needToWorkToday = attendanceCalculator.calculateNumOfWorkingHoursInDayOrMaxInSeconds(workingDate);
        String needToWorkTodayStr = LocalTimeUtils.secToHHmmStr(Math.max(needToWorkToday, 0));
        int needToWorkTodayWithoutMax = attendanceCalculator.calculateNumOfWorkingHoursInDayInSeconds(workingDate);
        String needToWorkTodayWithoutMaxStr = LocalTimeUtils.secToHHmmStr(Math.max(needToWorkTodayWithoutMax, 0));
        String maxHoursWarning = null;
        if (expectedHoursTodaySeconds == 0 || dayData == null) {
            actualHoursTodaySentence = getString(R.string.not_working_day);
        } else if (Objects.isNull(dayData.getActualStartTimeAW()) && Objects.isNull(dayData.getActualEndTimeAW())) {
            actualHoursTodaySentence = getString(R.string.need_to_work_x_hours_text, needToWorkTodayStr);
            if (needToWorkTodayWithoutMax > maxHours.toSecondOfDay()) {
                maxHoursWarning = getString(R.string.max_hours_warning_text, needToWorkTodayWithoutMaxStr, maxHoursStr);
            }
        } else if (Objects.nonNull(dayData.getActualStartTimeAW()) && Objects.isNull(dayData.getActualEndTimeAW())) {
            LocalTime exitHour = attendanceCalculator.calculateExitHourOfDayOrMaxInSeconds(workingDate);
            if (Objects.nonNull(exitHour)) {
                String enterHour = dayData.getActualStartTimeAW().format(Constants.TIME_FORMATTER);
                actualHoursTodaySentence = getString(R.string.need_to_exit_at_hour_text,
                                                     enterHour,
                                                     exitHour.format(Constants.TIME_FORMATTER),
                                                     needToWorkTodayStr);
                if (needToWorkTodayWithoutMax > maxHours.toSecondOfDay()) {
                    maxHoursWarning = getString(R.string.max_hours_warning_text, needToWorkTodayWithoutMaxStr, maxHoursStr);
                }
            }
        } else if (Objects.nonNull(dayData.getActualStartTimeAW()) && Objects.nonNull(dayData.getActualEndTimeAW())) {
            String actualHoursToday = LocalTimeUtils.secToHHmmStr(attendanceCalculator.calculateActualWorkingHoursTodayInSeconds(workingDate));
            actualHoursTodaySentence = getString(R.string.worked_x_hours_text, actualHoursToday);
        } else {
            actualHoursTodaySentence = getString(R.string.exit_without_enter);
        }
        AttendanceCalculatedData attendanceCalculatedData = AttendanceCalculatedData.builder()
                                                                                    .isCurrentMonthData(isCurrentMonthSelected)
                                                                                    .totalExpectedHoursUpToday(totalExpectedHours)
                                                                                    .totalActualHoursUpToday(totalActualHours)
                                                                                    .expectedHoursToday(expectedHoursToday)
                                                                                    .extraMissingWord(extraMissingWord)
                                                                                    .extraMissingHours(extraMissingHoursToday)
                                                                                    .actualHoursToday(actualHoursTodaySentence)
                                                                                    .maxHoursWarning(maxHoursWarning)
                                                                                    .build();

        binding.setAttendanceCalculatedData(attendanceCalculatedData);

        updateArrayAdapters(attendanceCalculator.getMissingEntryDays(selectedDate), attendanceCalculator.getNonApprovedDays(selectedDate));

        toggleLoadingCircleBar(false);
        changeMonthButton.setClickable(true);
    }

    private void updateArrayAdapters(List<AttendanceDayData> missingEntryDays, List<AttendanceDayData> nonApprovedDays) {

        List<String> missingEntryDaysStr = missingEntryDays.stream()
                                                           .map(day -> day.getWorkingDate().format(Constants.DATE_FORMATTER))
                                                           .collect(Collectors.toList());

        ArrayAdapter<String> missingEntryDaysArrayAdapter = new ArrayAdapter<>(AttendanceDataActivity.this,
                                                                               android.R.layout.simple_list_item_1,
                                                                               missingEntryDaysStr);
        missingEntryListView.setAdapter(missingEntryDaysArrayAdapter);
        missingEntryDaysArrayAdapter.notifyDataSetChanged();

        List<String> nonApprovedDaysStr = nonApprovedDays.stream()
                                                         .map(day -> day.getWorkingDate().format(Constants.DATE_FORMATTER))
                                                         .collect(Collectors.toList());

        ArrayAdapter<String> nonApprovedDaysArrayAdapter = new ArrayAdapter<>(AttendanceDataActivity.this,
                                                                              android.R.layout.simple_list_item_1,
                                                                              nonApprovedDaysStr);
        nonApprovedDaysListView.setAdapter(nonApprovedDaysArrayAdapter);
        nonApprovedDaysArrayAdapter.notifyDataSetChanged();
    }

    private void goToLoginActivity() {

        sharedPreferencesEditor.remove(Constants.AUTH_KEY);
        sharedPreferencesEditor.remove(Constants.EMPLOYEE_DATA_KEY);
        sharedPreferencesEditor.commit();
        Intent intent = new Intent(AttendanceDataActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }
}
