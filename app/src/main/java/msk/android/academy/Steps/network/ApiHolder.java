package msk.android.academy.Steps.network;

import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ApiHolder {
    private final String STEPS_URL = "https://intern-f6251.firebaseio.com";
    private final int TIMEOUT_IN_SECONDS = 2;

    private final StepsApi stepsEndpoint;
    private static ApiHolder sRestApi;

    public static synchronized ApiHolder getInstance() {
        if (sRestApi == null) {
            sRestApi = new ApiHolder();
        }
        return sRestApi;
    }

    private ApiHolder() {
        final OkHttpClient httpClient = buildOkHttpClient();
        final Retrofit retrofit = buildRetrofitClient(httpClient, STEPS_URL);
        stepsEndpoint = retrofit.create(StepsApi.class);
    }

    @NonNull
    private Retrofit buildRetrofitClient(@NonNull OkHttpClient client, String url) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @NonNull
    private OkHttpClient buildOkHttpClient() {
        final HttpLoggingInterceptor networkLogInterceptor = new HttpLoggingInterceptor();
        networkLogInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        return new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .addInterceptor(networkLogInterceptor)
                .build();
    }

    public StepsApi stepsApi() {
        return stepsEndpoint;
    }
}
