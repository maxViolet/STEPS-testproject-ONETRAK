package msk.android.maximFialko.Steps.network;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface StepsApi {
    @NonNull
    @GET("/intern/metric.json")
    Single<List<StepsItemDTO>> getMetrics();
}
