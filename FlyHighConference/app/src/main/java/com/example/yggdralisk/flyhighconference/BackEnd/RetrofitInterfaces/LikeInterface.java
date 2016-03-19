package com.example.yggdralisk.flyhighconference.BackEnd.RetrofitInterfaces;

import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Import;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Like;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by yggdralisk on 19.03.16.
 */
public interface LikeInterface {
    @GET("api/likes")
    Call<Like[]> load();
}
