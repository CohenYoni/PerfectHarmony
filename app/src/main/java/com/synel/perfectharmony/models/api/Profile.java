package com.synel.perfectharmony.models.api;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    @SerializedName("Code")
    private Integer profileCode;

    @SerializedName("Descript")
    private String description;

    @SerializedName("EntityPermission")
    private String entityPermission;
}
