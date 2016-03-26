package com.example.yggdralisk.flyhighconference.BackEnd.RetrofitInterfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by yggdralisk on 25.03.16.
 */
public interface PostLikeInterface {
    @POST("api/like")
    Call<ResponseBody> load(@Query("question") int questionID,@Query("user") int userID);
}
