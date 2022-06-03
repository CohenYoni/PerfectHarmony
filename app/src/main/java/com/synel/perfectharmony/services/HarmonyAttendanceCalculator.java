package com.synel.perfectharmony.services;

import com.synel.perfectharmony.models.AttendanceDayData;
import com.synel.perfectharmony.models.AttendanceResponsePayload;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Calculate attendance summaries based on Harmony raw data.
 */
public class HarmonyAttendanceCalculator {

    private static final int MISSING_ENTRY_TYPE = 3;

    private final AttendanceResponsePayload attendanceResponsePayload;

    private final LocalDate lastDateOfPayload;

    private LocalTime averageDayWorkingHours;

    /**
     * @param attendanceResponsePayload raw data of an attendance day
     * @param averageDayWorkingHours    the desired average working hours per day
     */
    public HarmonyAttendanceCalculator(AttendanceResponsePayload attendanceResponsePayload, LocalTime averageDayWorkingHours) {

        this.attendanceResponsePayload = attendanceResponsePayload;
        if (Objects.isNull(attendanceResponsePayload.getAttendanceDaysData())) {
            this.lastDateOfPayload = LocalDate.now();
        } else {
            this.lastDateOfPayload = Collections.max(attendanceResponsePayload.getAttendanceDaysData(),
                                                     Comparator.comparing(AttendanceDayData::getWorkingDate)).getWorkingDate();
        }
        this.averageDayWorkingHours = averageDayWorkingHours;
    }

    /**
     * @return the last available date of the month.
     */
    public LocalDate getLastDateOfPayload() {

        return lastDateOfPayload;
    }

    /**
     * @return the desired average working hours per day.
     */
    public LocalTime getAverageDayWorkingHours() {

        return averageDayWorkingHours;
    }

    /**
     * @param averageDayWorkingHours the new desired average working hours per day.
     */
    public void setAverageDayWorkingHours(LocalTime averageDayWorkingHours) {

        this.averageDayWorkingHours = averageDayWorkingHours;
    }

    /**
     * Calculate the number of working days in that payload up to the given date.
     *
     * @param upToDate the last date for inclusion in the calculation.
     * @return the number of working days in that payload
     */
    public int getTotalNumOfWorkingDays(LocalDate upToDate) {

        return Math.toIntExact(attendanceResponsePayload.getAttendanceDaysData().stream()
                                                        .filter(dayData -> dayData.getWorkingDate().isBefore(upToDate) ||
                                                            dayData.getWorkingDate().isEqual(upToDate))
                                                        .filter(dayData -> dayData.getExpectedTimeSeconds() > 0)
                                                        .count());
    }

    /**
     * Calculate the number of working days in that payload.
     *
     * @return the number of working days in that payload
     */
    public int getTotalNumOfWorkingDays() {

        return getTotalNumOfWorkingDays(lastDateOfPayload);
    }

    /**
     * Calculate the number of expected working hours in that payload up to the given date.
     *
     * @param upToDate the last date for inclusion in the calculation.
     * @return the number of expected working hours in that payload (in seconds)
     */
    public int getTotalNumOfExpectedWorkingHoursInSeconds(LocalDate upToDate) {

        return attendanceResponsePayload.getAttendanceDaysData().stream()
                                        .filter(dayData -> dayData.getWorkingDate().isBefore(upToDate) ||
                                            dayData.getWorkingDate().isEqual(upToDate))
                                        .map(AttendanceDayData::getExpectedTimeSeconds)
                                        .reduce(0, Integer::sum);
    }

    /**
     * Calculate the number of expected working hours in that payload.
     *
     * @return the number of expected working hours in that payload (in seconds)
     */
    public int getTotalNumOfExpectedWorkingHoursInSeconds() {

        return getTotalNumOfExpectedWorkingHoursInSeconds(lastDateOfPayload);
    }

    /**
     * Calculate the number of actual working hours in that payload up to the given date.
     *
     * @param upToDate the last date for inclusion in the calculation.
     * @return the number of actual working hours in that payload (in seconds)
     */
    public int getTotalNumOfActualWorkingHoursInSeconds(LocalDate upToDate) {

        int actualWorkingHours = attendanceResponsePayload.getAttendanceDaysData().stream()
                                                          .filter(dayData -> dayData.getWorkingDate().isBefore(upToDate) ||
                                                              dayData.getWorkingDate().isEqual(upToDate))
                                                          .filter(dayData -> Objects.nonNull(dayData.getActualStartTimeAW()) &&
                                                              Objects.nonNull(dayData.getActualEndTimeAW()))
                                                          .map(dayData -> dayData.getActualStartTimeAW()
                                                                                 .until(dayData.getActualEndTimeAW(), ChronoUnit.SECONDS))
                                                          .map(Math::toIntExact)
                                                          .reduce(0, Integer::sum);
        int approvedAbsenceHours = attendanceResponsePayload.getAttendanceDaysData().stream()
                                                            .filter(dayData -> dayData.getWorkingDate().isBefore(upToDate) ||
                                                                dayData.getWorkingDate().isEqual(upToDate))
                                                            .filter(dayData -> (Objects.isNull(dayData.getActualStartTimeAW()) ||
                                                                                    Objects.isNull(dayData.getActualEndTimeAW())) &&
                                                                Objects.nonNull(dayData.getAbsenceCode()) &&
                                                                dayData.getAbsenceCode() != 0)
                                                            .map(AttendanceDayData::getAbsenceTimeSeconds)
                                                            .reduce(0, Integer::sum);
        return actualWorkingHours + approvedAbsenceHours;
    }

    /**
     * Calculate the number of actual working hours in that payload.
     *
     * @return the number of actual working hours in that payload (in seconds)
     */
    public int getTotalNumOfActualWorkingHoursInSeconds() {

        return getTotalNumOfActualWorkingHoursInSeconds(lastDateOfPayload);
    }

    /**
     * Calculate the total number of extra/missing hours in that payload up to the given date.
     *
     * @param upToDate the last date for inclusion in the calculation.
     * @return the number of extra (positive number) / missing (negative number) hours in that payload (in seconds)
     */
    public int calculateNumOfExtraMissingHoursInSeconds(LocalDate upToDate) {

        LocalDate finalUpToDate = upToDate;
        Optional<AttendanceDayData> lastDayData = attendanceResponsePayload.getAttendanceDaysData().stream()
                                                                           .filter(dayData -> dayData.getWorkingDate()
                                                                                                     .atStartOfDay()
                                                                                                     .equals(finalUpToDate.atStartOfDay()))
                                                                           .findFirst();
        if (!lastDayData.isPresent()) {
            return 0;
        }
        if (Objects.isNull(lastDayData.get().getActualStartTimeAW()) || Objects.isNull(lastDayData.get().getActualEndTimeAW())) {
            upToDate = upToDate.minusDays(1);
        }
        return getTotalNumOfActualWorkingHoursInSeconds(upToDate) - getTotalNumOfExpectedWorkingHoursInSeconds(upToDate);
    }

    /**
     * Calculate the total number of extra/missing hours in that payload.
     *
     * @return the number of extra (positive number) / missing (negative number) hours in that payload (in seconds)
     */
    public int calculateNumOfExtraMissingHoursInSeconds() {

        return getTotalNumOfActualWorkingHoursInSeconds(lastDateOfPayload) - getTotalNumOfExpectedWorkingHoursInSeconds(lastDateOfPayload);
    }

    /**
     * Calculate the number of working hours of a working date, by adding the extra / missing hours to the expected hours.
     *
     * @param workingDate the required date.
     * @return the number of working hours of a working date (in seconds)
     */
    public int calculateNumOfWorkingHoursInDayInSeconds(LocalDate workingDate) {

        Optional<AttendanceDayData> requiredDayData = attendanceResponsePayload.getAttendanceDaysData().stream()
                                                                               .filter(dayData -> dayData.getWorkingDate()
                                                                                                         .atStartOfDay()
                                                                                                         .equals(workingDate.atStartOfDay()))
                                                                               .findFirst();
        if (!requiredDayData.isPresent() || requiredDayData.get().getExpectedTimeSeconds() == 0) {
            return 0;
        }
        return requiredDayData.get().getExpectedTimeSeconds() -
            calculateNumOfExtraMissingHoursInSeconds(requiredDayData.get().getWorkingDate().minusDays(1));
    }

    /**
     * Calculate the exit hour of a working date, by adding the number of the working hours to the enter hour.
     *
     * @param workingDate the required date.
     * @return the exit hour of a working date
     */
    public LocalTime calculateExitHourOfDayInSeconds(LocalDate workingDate) {

        Optional<AttendanceDayData> requiredDayData = attendanceResponsePayload.getAttendanceDaysData().stream()
                                                                               .filter(dayData -> dayData.getWorkingDate()
                                                                                                         .atStartOfDay()
                                                                                                         .equals(workingDate.atStartOfDay()))
                                                                               .findFirst();
        int requiredDayWorkingHours = calculateNumOfWorkingHoursInDayInSeconds(workingDate);
        if (!requiredDayData.isPresent() || requiredDayWorkingHours == 0 || Objects.isNull(requiredDayData.get().getActualStartTimeAW())) {
            return null;
        }
        return requiredDayData.get().getActualStartTimeAW().plusSeconds(requiredDayWorkingHours);
    }

    /**
     * Get the data of the missing entry days in that payload up to the given date.
     *
     * @param upToDate the last date for inclusion in the calculation.
     * @return a list of days without enter / exit entries
     */
    public List<AttendanceDayData> getMissingEntryDays(LocalDate upToDate) {

        return attendanceResponsePayload.getAttendanceDaysData().stream()
                                        .filter(dayData -> dayData.getWorkingDate().isBefore(upToDate) ||
                                            dayData.getWorkingDate().isEqual(upToDate))
                                        .filter(dayData -> dayData.getExceptionType() == MISSING_ENTRY_TYPE)
                                        .collect(Collectors.toList());
    }

    /**
     * Get the data of the missing entry days in that payload.
     *
     * @return a list of days without enter / exit entries
     */
    public List<AttendanceDayData> getMissingEntryDays() {

        return getMissingEntryDays(lastDateOfPayload);
    }
}
