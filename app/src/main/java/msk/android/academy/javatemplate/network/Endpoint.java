package msk.android.academy.javatemplate.network;

import android.support.annotation.NonNull;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface Endpoint {
    @NonNull
    @GET("/intern/metric.json")
    Single<FbResponse> getMetrics();
}
