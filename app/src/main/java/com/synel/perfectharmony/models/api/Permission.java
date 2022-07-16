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
public class Permission {

    @SerializedName("Code")
    private String permissionCode;

    @SerializedName("AccessType")
    private Integer accessType;

    @SerializedName("ItemNo")
    private String itemNo;

    @SerializedName("Parent")
    private String parent;
}
