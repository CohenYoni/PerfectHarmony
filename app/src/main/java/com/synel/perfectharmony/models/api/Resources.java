package com.synel.perfectharmony.models.api;

import com.google.gson.annotations.SerializedName;
import java.util.List;
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
public class Resources {

    @SerializedName("Dict")
    private List<Resource> resourcesDictionary;

    @SerializedName("DictMessage")
    private List<Resource> messagesDictionary;

    @SerializedName("Tooltips")
    private List<Tooltip> tooltips;

    @SerializedName("EntityPermission")
    private String entityPermission;
}
