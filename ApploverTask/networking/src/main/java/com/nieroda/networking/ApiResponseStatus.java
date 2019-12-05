package com.nieroda.networking;

import android.util.Log;

public enum ApiResponseStatus {
    UNEXPECTED_ERROR(-1),
    UNAUTHORIZED(401),
    INTERNAL_SERVER_ERROR(500);

    private static final String TAG = ApiResponseStatus.class.getSimpleName();
    int errorCode;

    ApiResponseStatus(int i) {
        errorCode = i;
    }

    public static ApiResponseStatus getError(int code) {
        for (ApiResponseStatus response :
                ApiResponseStatus.values()) {
            if (code == response.errorCode)
                return response;
        }
        Log.e(TAG, "No such error code: " + code);
        return UNEXPECTED_ERROR;
    }
}
