package com.bbs.mr.assignmentnetwork.Interface;


import com.bbs.mr.assignmentnetwork.Model.Account;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/* Created by MrBBS @ 2020
Email: 0331999bbs@gmail.com
Phone: 034 707 9556 */
public interface mAPI {
    @FormUrlEncoded
    @POST("login.php")
    Observable<String> login(@Field("username") String username, @Field("password") String password);

    @POST("register.php")
    @FormUrlEncoded
    Observable<String> register(@Field("username") String username, @Field("password") String password, @Field("name") String name, @Field("email") String email);

    @GET("getacc.php")
    @FormUrlEncoded
    public void getacc(Callback<List<Account>> response);
}
