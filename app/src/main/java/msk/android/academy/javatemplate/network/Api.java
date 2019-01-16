package msk.android.academy.javatemplate.network;

import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class Api {

    private final String URL = "https://intern-f6251.firebaseio.com";
    private final int TIMEOUT_IN_SECONDS = 2;

    private final Endpoint stepsEndpoint;
    private static Api sRestApi;

    public static synchronized Api getInstance() {
        if (sRestApi == null) {
            sRestApi = new Api();
        }
        return sRestApi;
    }

    private Api() {
        final OkHttpClient httpClient = buildOkHttpClient();
        final Retrofit retrofit = buildRetrofitClient(httpClient);

        //init endpoints here. It's can be more then one stepsEndpoint
        stepsEndpoint = retrofit.create(Endpoint.class);
    }

    @NonNull
    private Retrofit buildRetrofitClient(@NonNull OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(URL)
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
//                .addInterceptor(ApiKeyInterceptor.create(API_KEY))
                .addInterceptor(networkLogInterceptor)
                .build();
    }

    public Endpoint stepsEndpoint() {
        return stepsEndpoint;
    }

}
