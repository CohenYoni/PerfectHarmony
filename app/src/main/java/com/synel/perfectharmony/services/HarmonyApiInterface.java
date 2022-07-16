package com.synel.perfectharmony.services;

import com.synel.perfectharmony.models.AttendanceResponsePayload;
import com.synel.perfectharmony.models.api.CompanyUserLoginRequestPayload;
import com.synel.perfectharmony.models.api.GetAttendanceQueryParam;
import com.synel.perfectharmony.models.api.GetCompanyParamsPreferencesPermissionsResponse;
import com.synel.perfectharmony.models.api.LoginRequestPayload;
import com.synel.perfectharmony.models.api.LoginResponsePayload;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HarmonyApiInterface {

    @POST("NewLogin/GetCompanyParamsAndPreferencesAndPermissions")
    Call<GetCompanyParamsPreferencesPermissionsResponse> getCompanyParamsPreferencesPermissions(
        @Body CompanyUserLoginRequestPayload companyUserLoginRequestPayload);

    @POST("login/Login")
    Call<LoginResponsePayload> login(@Body LoginRequestPayload loginRequestPayload);

    @POST("Common/CheckUserLogin")
    Call<Boolean> checkUserLogin(@Header("sessionid") String sessionId);

    @GET("Attendance/GetAttendance?")
    Call<AttendanceResponsePayload> getAttendanceData(@Header("sessionid") String sessionId,
                                                      @Query(value = "query", encoded = true) GetAttendanceQueryParam attendanceQueryParam,
                                                      @Query(value = "take", encoded = true) Integer take,
                                                      @Query(value = "skip", encoded = true) Integer skip,
                                                      @Query(value = "page", encoded = true) Integer page,
                                                      @Query(value = "pageSize", encoded = true) Integer pageSize,
                                                      @Query(value = "_", encoded = true) Integer unknownParam);
}
