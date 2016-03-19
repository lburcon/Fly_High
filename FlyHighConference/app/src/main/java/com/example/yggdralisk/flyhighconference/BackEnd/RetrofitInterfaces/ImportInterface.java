package com.example.yggdralisk.flyhighconference.BackEnd.RetrofitInterfaces;

import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Import;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by yggdralisk on 19.03.16. WTFAMIDOING
 */
public interface ImportInterface {
        @GET("api/import")
        Call<Import> load();
}
