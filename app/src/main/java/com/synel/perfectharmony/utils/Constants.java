package com.synel.perfectharmony.utils;

import java.time.format.DateTimeFormatter;

public class Constants {

    public static final String DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(Constants.DATETIME_PATTERN);

    public static final String DATE_PATTERN = "yyyy-MM-dd";

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(Constants.DATE_PATTERN);

    public static final String TIME_PATTERN = "HH:mm";

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);

    public static final String LOGIN_FORM_KEY = "login_form";

    public static final String AUTH_KEY = "auth";

    public static final String EMPLOYEE_DATA_KEY = "employee_data";

    public static final String SLASH = "/";

    public static final int ATTENDANCE_DATA_TAKE_PARAM = 100;

    public static final int ATTENDANCE_DATA_SKIP_PARAM = 100;

    public static final int ATTENDANCE_DATA_PAGE_PARAM = 100;

    public static final int ATTENDANCE_DATA_PAGE_SIZE_PARAM = 100;

    public static final int ERROR_INT = -1;

    public static final boolean COMPANY_USER_LOGIN_REQUEST_PAYLOAD_IS_ID = true;

    public static final String COMPANY_USER_LOGIN_REQUEST_PAYLOAD_LANGUAGE_ID = "3";

    public static final boolean COMPANY_USER_LOGIN_REQUEST_PAYLOAD_CHECK_COMP = true;

    public static final boolean LOGIN_REQUEST_PAYLOAD_IS_ID = true;

    public static final String LOGIN_REQUEST_PAYLOAD_OPT_CODE = "";

    public static final String LOGIN_REQUEST_PAYLOAD_OPT_PHONE = "";

    public static final String LOGIN_REQUEST_PAYLOAD_LANGUAGE_ID = "3";

    public static final boolean LOGIN_REQUEST_PAYLOAD_LOGIN_BY_EXISTED_REMEMBER_ME = false;

    public static final boolean LOGIN_REQUEST_PAYLOAD_REMEMBER_ME = true;

    public static final boolean LOGIN_REQUEST_PAYLOAD_LOGOUT_HAS_OCCURRED_BY_USER = false;

    public static final boolean LOGIN_REQUEST_PAYLOAD_IS_FROM_MOBILE_APP = false;

    public static final int FIRST_DAY_OF_MONTH = 1;

    public static final int GET_ATTENDANCE_QUERY_PARAM_WITH_FUTURE = 1;

    public static final String GET_ATTENDANCE_QUERY_PARAM_EMPLOYEE_VALUE = "";

    public static final int GET_ATTENDANCE_QUERY_PARAM_EMPLOYEE_BY = 0;

    public static final int GET_ATTENDANCE_QUERY_PARAM_L_PRES = 1;

    public static final String GET_ATTENDANCE_QUERY_PARAM_DVC_CODES = "";

    public static final String GET_ATTENDANCE_QUERY_PARAM_GROUP_LEVEL = "6";

    public static final int GET_ATTENDANCE_QUERY_PARAM_INCLUDE_NON_ACTIVE_EMPLOYEE = 0;

    public static final String GET_ATTENDANCE_QUERY_PARAM_FILTER_STATE = "";

    public static final boolean GET_ATTENDANCE_QUERY_IS_SCHEDULE_MODULE = true;

    public static final boolean GET_ATTENDANCE_QUERY_IS_HEBREW = false;

    public static final boolean GET_ATTENDANCE_QUERY_IS_GROUP_UPDATE = false;

    public static final boolean GET_ATTENDANCE_QUERY_IS_CHECK_MADAN = true;

    public static final int GET_ATTENDANCE_QUERY_UPDATE_STATUS = -1;

    public static final int GET_ATTENDANCE_QUERY_GRID_TYPE = 0;

    public static final String GET_ATTENDANCE_QUERY_OTHER_PARAMS_XML = "";

    public static final int GET_ATTENDANCE_QUERY_PAGE_NO = 1;

    public static final int GET_ATTENDANCE_QUERY_PAGE_LENGTH = 100;

    public static final String GET_ATTENDANCE_QUERY_FILTER = null;

    public static final long MILLIS_IN_SEC = 1000;

    public static final int DAY_HOURS_APPROVED_STATUS_CODE = 1;

    public static final String NON_WORKING_DAY_CODE = "1282";
}
