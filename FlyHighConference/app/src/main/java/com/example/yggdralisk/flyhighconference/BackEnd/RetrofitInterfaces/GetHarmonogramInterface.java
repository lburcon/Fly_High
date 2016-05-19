package com.example.yggdralisk.flyhighconference.BackEnd.RetrofitInterfaces;

import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Import;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Presentation;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by yggdralisk on 19.05.16.
 */
public interface GetHarmonogramInterface {
    @GET("api/presentations")
    Call<Presentation[]> load(@Query("user")int userID);
}
