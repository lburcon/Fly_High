package com.example.yggdralisk.flyhighconference.BackEnd.RetrofitInterfaces;

import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Question;

/**
 * Created by yggdralisk on 17.05.16.
 */
public interface GetQuestionsResultInterface {
    void onDownloadFinished(Question[] res);
}
