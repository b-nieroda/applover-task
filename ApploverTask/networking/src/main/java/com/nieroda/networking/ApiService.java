package com.nieroda.networking;

import android.net.Uri;

import retrofit2.Call;
import retrofit2.Retrofit;

import static com.nieroda.networking.OkHttpClientUtil.createOkHttpClient;

public enum ApiService {
    INSTANCE;

    private final IRequestsInterface service;

    ApiService() {
        String serverUrl = BuildConfig.SERVER_URL;
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(serverUrl)
                .client(createOkHttpClient(Uri.parse(serverUrl)))
                .build();
        service = retrofit.create(IRequestsInterface.class);
    }

    public Call<Void> login(final String email, final String password, final HttpCallback<Void> callback) {
        Call<Void> call = service.login(email, password);
        call.enqueue(callback);
        return call;
    }


}
