package com.example.yggdralisk.flyhighconference.BackEnd.RetrofitInterfaces;

import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Question;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by yggdralisk on 19.03.16.
 */
public interface QuestionToPresentationsInterface {
    @GET("api/questions?")
    Call<Question[]> load(@Query("presentation") int id);
}
