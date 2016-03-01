package com.example.yggdralisk.flyhighconference;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yggdralisk on 27.02.16.
 */
public class LoginFragment extends Fragment {

    EditText emailText;
    EditText passwordText;
    Button loginButton;
    MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_layout, container, false);

        emailText = (EditText) view.findViewById(R.id.login_layout_email);
        passwordText = (EditText) view.findViewById(R.id.login_layout_password);
        loginButton = (Button) view.findViewById(R.id.login_layout_button);
        mainActivity = (MainActivity)getContext();

        loginButton.setOnClickListener(new loginListener());

        return view;
    }

    private class loginListener implements View.OnClickListener {
        private static final String DATA_HOST_URL = "http://flyhigh.pwr.edu.pl/login";

        @Override
        public void onClick(View v) {
            String tempData = String.format("?email=%s&pass=%s", emailText.getText(), passwordText.getText());
            OkHttpClient client = new OkHttpClient();
            String str = new String();
            Request request = new Request.Builder()
                    .url(DATA_HOST_URL)
                    .build();
            try {
                Response res = client.newCall(request).execute();
                if (res.body() != null && res.body().string() != "null") {
                    DataGetter.toggleUserLogged(v.getContext(),emailText.getText().toString());
                    mainActivity.setLoggedNameDrawer(emailText.getText().toString().substring(0,emailText.getText().toString().indexOf('@')));
                }


            } catch (IOException e) {
                e.printStackTrace();
                Toast t = new Toast(v.getContext());
                t.setDuration(Toast.LENGTH_LONG);
                t.setText(R.string.login_error_messege);
                DataGetter.toggleUserLogged(v.getContext(), "");
                mainActivity.setLoggedNameDrawer("");
            }
        }
    }
}
