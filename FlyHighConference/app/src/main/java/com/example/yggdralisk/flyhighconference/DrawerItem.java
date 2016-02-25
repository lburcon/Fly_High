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
            case "Uczestnicy":
            case "Sponsorzy":
            case "Zaloguj/Wyloguj":
            default:
                fragment = new ConferenceListFragment();
        }
    }
}
