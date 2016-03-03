package com.example.yggdralisk.flyhighconference;

import android.support.v4.app.Fragment;

/**
 * Created by yggdralisk on 25.02.16.
 */
public class DrawerItem {
    int iconId;
    String text;
    Fragment fragment;

    public DrawerItem(String text,int iconId) {
        this.iconId = iconId;
        this.text = text;

        switch (text) {
            case "Plan konderencji":
                fragment = new ConferenceListFragment();
                break;
            case "Nawigacja":
            case "Pytania":
                fragment = new QuestionsListFragment();
                break;
            case "Uczestnicy":
                fragment = new SpeakersListFragment();
                break;
            case "Sponsorzy":
                fragment = new PartnersListFragment();
                break;
            case "Zaloguj":
                fragment = new LoginFragment();
                break;
            case "Wyloguj":
                fragment = new LoginOutFragment();
                break;
            default:
                fragment = new ConferenceListFragment();
        }
    }
}
