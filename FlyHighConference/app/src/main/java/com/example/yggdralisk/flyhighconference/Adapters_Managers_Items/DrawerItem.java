package com.example.yggdralisk.flyhighconference.Adapters_Managers_Items;

import android.support.v4.app.Fragment;

import com.example.yggdralisk.flyhighconference.Fragments.ConferenceListFragment;
import com.example.yggdralisk.flyhighconference.Fragments.LoginFragment;
import com.example.yggdralisk.flyhighconference.Fragments.LoginOutFragment;
import com.example.yggdralisk.flyhighconference.Fragments.NavigationFragment;
import com.example.yggdralisk.flyhighconference.Fragments.PartnersListFragment;
import com.example.yggdralisk.flyhighconference.Fragments.QuestionsListFragment;
import com.example.yggdralisk.flyhighconference.Fragments.SpeakersListFragment;

/**
 * Created by yggdralisk on 25.02.16.
 */
public class DrawerItem {
    int iconId;
    String text;
    Fragment fragment;

    public Fragment getFragment() {
        switch (text) {
            case "Plan konferencji":
                fragment = new ConferenceListFragment();
                break;
            case "Nawigacja":
                fragment = new NavigationFragment();
                break;
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

        return fragment;
    }

    public DrawerItem(String text, int iconId) {
        this.iconId = iconId;
        this.text = text;
    }
}
