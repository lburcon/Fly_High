package com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses;

/**
 * Created by yggdralisk on 19.03.16.
 */
public class SpeakerPresentationPair { //This class stores a pair of speaker and his presentation
    int speaker;//ORMSpeaker's id
    int presentation;//ORMPresentation's id

    public int getSpeaker() {
        return speaker;
    }

    public int getPresentation() {
        return presentation;
    }
}
