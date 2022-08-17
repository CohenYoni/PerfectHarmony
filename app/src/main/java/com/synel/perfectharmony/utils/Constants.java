package com.synel.perfectharmony.utils;

import java.time.format.DateTimeFormatter;

public class Constants {

    public static final String DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(Constants.DATETIME_PATTERN);

    public static final String TIME_PATTERN = "HH:mm";

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);

    public static final String SESSION_ID_PREF_KEY = "session_id";

    public static final String COMPANY_ID_PREF_KEY = "company_id";

    public static final String EMPLOYEE_ID_PREF_KEY = "employee_id";

    public static final String PASSWORD_PREF_KEY = "password";

    public static final String USER_NO_PREF_KEY = "user_no";

    public static final String SLASH = "/";

    public static final int ATTENDANCE_DATA_TAKE_PARAM = 100;

    public static final int ATTENDANCE_DATA_SKIP_PARAM = 100;

    public static final int ATTENDANCE_DATA_PAGE_PARAM = 100;

    public static final int ATTENDANCE_DATA_PAGE_SIZE_PARAM = 100;

    public static final int ERROR_INT = -1;
}
