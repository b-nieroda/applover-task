package com.nieroda.networking;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

interface IRequestsInterface {


    @FormUrlEncoded
    @POST("/api/v1/login/")
    Call<Void> login(@Field("email") String email,
                         @Field("password") String password);
}
