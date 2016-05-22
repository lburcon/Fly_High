package com.example.yggdralisk.flyhighconference.BackEnd.RetrofitInterfaces;

import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Like;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Presentation;

/**
 * Created by yggdralisk on 22.05.16.
 */
public interface GetLikesResultInterface {
        void onDownloadFinished(Like[] res);
}
