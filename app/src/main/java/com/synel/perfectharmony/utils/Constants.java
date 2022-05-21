package com.synel.perfectharmony.utils;

import java.time.format.DateTimeFormatter;

public class Constants {

    public static final String DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(Constants.DATETIME_PATTERN);

    public static final String TIME_PATTERN = "HH:mm";

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);
}
