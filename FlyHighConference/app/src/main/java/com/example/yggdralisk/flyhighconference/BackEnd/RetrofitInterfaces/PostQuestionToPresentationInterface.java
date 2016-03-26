package com.example.yggdralisk.flyhighconference.BackEnd.RetrofitInterfaces;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by yggdralisk on 25.03.16.
 */
public interface PostQuestionToPresentationInterface {
    @POST("api/like?")
    Call<Boolean> load(@Query("presentation") int presentationID,@Query("user") int userID,@Query("content") String content);
}
