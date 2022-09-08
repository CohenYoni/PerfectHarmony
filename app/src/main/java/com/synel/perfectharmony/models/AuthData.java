package com.synel.perfectharmony.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthData {

    private Integer employeeId;

    private String sessionId;

    private Integer profileCode;
}
