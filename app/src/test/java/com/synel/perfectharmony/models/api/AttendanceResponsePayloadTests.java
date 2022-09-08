package com.synel.perfectharmony.models.api;

import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AttendanceResponsePayloadTests {

    @Mock
    private AttendanceDayData attendanceDayDataMock;

    @Test
    public void modelDataTest() {

        Integer totalCountOfDays = 1;
        List<AttendanceDayData> attendanceDaysData = Collections.singletonList(attendanceDayDataMock);
        AttendanceResponsePayload testedModel = new AttendanceResponsePayload(totalCountOfDays, attendanceDaysData);
        Assert.assertEquals("Different total count of days!", totalCountOfDays, testedModel.getTotalCountOfDays());
        Assert.assertEquals("Different attendance days data!", attendanceDaysData, testedModel.getAttendanceDaysData());
        Assert.assertEquals("Different days count in the list!", attendanceDaysData.size(), testedModel.getAttendanceDaysData().size());
    }
}
