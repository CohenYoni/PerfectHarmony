package com.synel.perfectharmony.models.api;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * POJO model for HTTP attendance response of an employee.
 */
@Setter
@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceResponsePayload {

    @SerializedName("totalCount")
    private Integer totalCountOfDays;

    @SerializedName("results")
    private List<AttendanceDayData> attendanceDaysData;
}
