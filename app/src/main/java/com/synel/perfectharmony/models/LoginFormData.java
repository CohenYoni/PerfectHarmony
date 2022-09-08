package com.synel.perfectharmony.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginFormData {

    private Integer companyId;

    private Integer userId;

    private String password;
}
