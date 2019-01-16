package msk.android.academy.javatemplate.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FbResponse {

    @SerializedName("")
    private List<StepsItemDTO> stepsItemDTO;

    public List<StepsItemDTO> getStepsDTO() {
        return stepsItemDTO;
    }
}
