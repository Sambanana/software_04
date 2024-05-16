package com.example.mr_dankwah;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("sign_up.php")
    Call<ApiResponse> registerUser(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("sign_in.php")
    Call<ApiResponse> loginUser(
            @Field("username") String username,
            @Field("password") String password
    );
}
