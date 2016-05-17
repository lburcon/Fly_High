package com.example.yggdralisk.flyhighconference.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yggdralisk.flyhighconference.BackEnd.DataGetter;
import com.example.yggdralisk.flyhighconference.BackEnd.MainActivity;
import com.example.yggdralisk.flyhighconference.R;
import com.google.android.gms.analytics.Tracker;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yggdralisk on 02.03.16.
 */
public class LoginOutFragment extends Fragment {

    @Bind(R.id.login_out_layout_button)
    Button logOutbutton;
    @Bind(R.id.login_out_image)
    ImageView image;
    Context mContext;
    private Tracker mTracker;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_out_layout, container, false);

        ButterKnife.bind(this, view);
        mContext = container.getContext();

        Glide.with(mContext).load(R.drawable.fly_high).placeholder(R.drawable.fly_high_temp).crossFade().fitCenter().into(image);

        setButtons();

        return view;
    }

    private void setButtons() {
        logOutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DataGetter.checkUserLogged(mContext)) {
                    DataGetter.toggleUserLogged(getContext(), "", -1);
                    displayToast(getString(R.string.logged_out));

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ((MainActivity) getContext()).setFragment(null, new LoginFragment(), null);
                        }
                    }, 750);
                }
                ((MainActivity) getContext()).setLoggedNameOnDrawer("");
                ((MainActivity) getContext()).changeLoginLogoutDrawer();
            }
        });
    }

    private void displayToast(String text) {
        Toast t = Toast.makeText(getContext(), "  " + text + "  ", Toast.LENGTH_LONG); //new Toast(mainActivity.getApplicationContext());
        TextView v = (TextView) t.getView().findViewById(android.R.id.message);
        if (v != null) {
            v.setGravity(Gravity.CENTER);
        }

        t.show();
    }


}
