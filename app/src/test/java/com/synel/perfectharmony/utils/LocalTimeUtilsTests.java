package com.synel.perfectharmony.utils;

import java.time.LocalTime;
import org.junit.Assert;
import org.junit.Test;

public class LocalTimeUtilsTests {

    @Test
    public void convertSecondsToLocalTime() {

        Assert.assertEquals("Failed to convert seconds to time!", LocalTime.of(2, 23, 58), LocalTimeUtils.convertSecondsToLocalTime(8638));
    }

    @Test
    public void convertLocalTimeToSecondsTest() {

        Assert.assertEquals("Failed to convert time to seconds!", 8638, LocalTimeUtils.convertLocalTimeToSeconds(LocalTime.of(2, 23, 58)));
    }
}
