package msk.android.academy.javatemplate.network;

import com.google.gson.annotations.SerializedName;

public class StepsItemDTO {

    @SerializedName("date")
    private long date;

    @SerializedName("walk")
    private int walk;

    @SerializedName("aerobic")
    private int aerobic;

    @SerializedName("run")
    private int run;

    public long getDate() {
        return date;
    }

    public int getAerobic() {
        return aerobic;
    }

    public int getRun() {
        return run;
    }

    public int getWalk() {
        return walk;
    }

}
