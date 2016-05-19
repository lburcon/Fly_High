package com.example.yggdralisk.flyhighconference.BackEnd.RetrofitInterfaces;

import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Presentation;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Question;

/**
 * Created by yggdralisk on 19.05.16.
 */
public interface GetHarmonogramResultInterface {
        void onDownloadFinished(Presentation[] res);
}
