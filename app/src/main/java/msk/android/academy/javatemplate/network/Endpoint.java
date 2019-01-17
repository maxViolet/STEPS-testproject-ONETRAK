package msk.android.academy.javatemplate.network;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface Endpoint {
    @NonNull
    @GET("/intern/metric.json")
//    Single<Response> getSingleMetrics();
    Single<List<StepsItemDTO>> getMetrics();

}
