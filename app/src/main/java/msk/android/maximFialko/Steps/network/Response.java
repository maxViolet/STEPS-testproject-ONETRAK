package msk.android.maximFialko.Steps.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {
    @SerializedName("")
    private List<StepsItemDTO> stepsItemDTO;

    public List<StepsItemDTO> getStepsDTO() {
        return stepsItemDTO;
    }
}
