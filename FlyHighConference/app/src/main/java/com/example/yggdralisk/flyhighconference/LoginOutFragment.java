package com.example.yggdralisk.flyhighconference;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by yggdralisk on 02.03.16.
 */
public class LoginOutFragment extends Fragment{

    Button logOutbutton;
    ImageButton goBackButton;
    Context mContext;
    MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_out_layout,container,false);

        logOutbutton = (Button) view.findViewById(R.id.login_out_layout_button);
        goBackButton = (ImageButton) view.findViewById(R.id.login_out_layout_back_button);
        mContext = container.getContext();
        mainActivity = (MainActivity) getContext();

        setButtons();

        return view;
    }

    private void setButtons()
    {
        logOutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DataGetter.checkUserLogged(mContext)) {
                    DataGetter.toggleUserLogged(getContext(), "", -1);
                    displayToast("Wylogowano");
                }
                mainActivity.setLoggedNameOnDrawer("");
                mainActivity.changeZalogujWylogujOnDrawer();
            }
        });

        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setPreviousFragment();
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
