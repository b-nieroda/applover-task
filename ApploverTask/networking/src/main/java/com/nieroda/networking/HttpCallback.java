package com.nieroda.networking;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class HttpCallback<T> implements Callback<T> {
    public abstract void onSuccess();

    public abstract void onFailure(ApiResponseStatus failureReason);


    @Override
    public final void onResponse(Call<T> call, Response<T> response) {
        if (call.isCanceled()) {
            Log.d(HttpCallback.class.getSimpleName(), "call canceled");
            return;
        }
        if (response.isSuccessful()) {
            onSuccess();
        } else {
            onFailure(ApiResponseStatus.getError(response.code()));
        }
    }

    @Override
    public final void onFailure(Call<T> call, Throwable t) {
        if (call.isCanceled()) {
            Log.d(HttpCallback.class.getSimpleName(), "call canceled");
            return;
        }
        onFailure(ApiResponseStatus.UNEXPECTED_ERROR);
    }
}
