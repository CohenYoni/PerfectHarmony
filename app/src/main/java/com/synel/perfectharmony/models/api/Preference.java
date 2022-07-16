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
public class Preference {

    @SerializedName("AdditionalDetails")
    private String additionalDetails;

    @SerializedName("ModuleName")
    private String moduleName;

    @SerializedName("BehaviorName")
    private String behaviorName;

    @SerializedName("Value")
    private String value;
}
