package com.synel.perfectharmony.models.api;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {

    @SerializedName("ExType")
    private Integer exceptionType;

    @SerializedName("ErrorMessage")
    private String errorMessage;

    @SerializedName("InnerException")
    private String innerException;
}
