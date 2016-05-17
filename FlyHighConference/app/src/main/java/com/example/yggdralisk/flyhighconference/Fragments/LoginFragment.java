package com.example.yggdralisk.flyhighconference.Fragments;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yggdralisk.flyhighconference.BackEnd.AnalyticsApplication;
import com.example.yggdralisk.flyhighconference.BackEnd.DataGetter;
import com.example.yggdralisk.flyhighconference.BackEnd.MainActivity;
import com.example.yggdralisk.flyhighconference.R;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yggdralisk on 27.02.16.
 */
public class LoginFragment extends Fragment {

    @Bind(R.id.login_layout_email)
    EditText emailText;
    @Bind(R.id.login_layout_password)
    EditText passwordText;
    @Bind(R.id.login_layout_login_button)
    Button loginButton;
    @Bind(R.id.login_image)
    ImageView image;
    Context mContext;
    private Tracker mTracker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_layout, container, false);

        ButterKnife.bind(this, view);
        mContext = container.getContext();

        Glide.with(mContext).load(R.drawable.fly_high).placeholder(R.drawable.fly_high_temp).crossFade().fitCenter().into(image);

        loginButton.setOnClickListener(new loginListener());

        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();


        mTracker.setScreenName("Login Fragment");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());


        return view;
    }

    private boolean logIn(int id) {
        if (id != -1) {
            if (!DataGetter.checkUserLogged(mContext))
                DataGetter.toggleUserLogged(mContext, emailText.getText().toString(), id);

            String userName = emailText.getText().toString().substring(0, emailText.getText().toString().indexOf('@'));
            ((MainActivity) getContext()).setLoggedNameOnDrawer(userName);
            displayToast("Logged in as: " + userName);
            ((MainActivity) getContext()).changeLoginLogoutDrawer();


            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ((MainActivity) getContext()).setFragment(null, new LoginOutFragment(), null);
                    try {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
                }
            }, 750);
            return true;
        } else {
            displayToast(getString(R.string.login_error_messege));
            DataGetter.toggleUserLogged(mContext, "", -1);
            ((MainActivity) getContext()).setLoggedNameOnDrawer("");
            ((MainActivity) getContext()).changeLoginLogoutDrawer();
            return false;
        }
    }

    private void displayToast(String text) {
        Toast t = Toast.makeText(getContext(), "  " + text + "  ", Toast.LENGTH_LONG); //new Toast(mainActivity.getApplicationContext());
        TextView v = (TextView) t.getView().findViewById(android.R.id.message);
        if (v != null) {
            v.setGravity(Gravity.CENTER);
        }

        t.show();
    }

    private class loginListener implements View.OnClickListener {
        private static final String DATA_HOST_URL = "http://flyhigh.pwr.edu.pl/api/login";

        @Override
        public void onClick(View v) {
            String t1 = emailText.getText().toString();
            String t2 = passwordText.getText().toString();
            if (checkEmail() && checkPassword()) {
                String tempData = String.format("?email=%s&pass=%s", emailText.getText(), passwordText.getText());
                new getDataTask().execute(DATA_HOST_URL, tempData, emailText.getText().toString());
            }
        }

        private boolean checkEmail() {
            String regEx = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";//TODO:Sprawdzić poprawność dla dziwnych adresów email a@os.sos.pl

            Pattern pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(emailText.getText().toString());
            if (!matcher.matches())
                displayToast(mContext.getString(R.string.wrong_email));
            return matcher.matches();
        }

        private boolean checkPassword() {
            if (passwordText.getText().toString().isEmpty())//TODO:Czy potrzebujemy innego systemu sprawdzania haseł?
            {
                displayToast(mContext.getString(R.string.wrong_password));
                return false;
            }
            return true;
        }
    }

    private class getDataTask extends AsyncTask<String, Void, Void> {
        int tempId = -1;
        String tempName = "";

        @Override
        protected Void doInBackground(String... params) {

            tempName = params[2];
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(params[0] + params[1])
                    .build();
            try {
                Response res = client.newCall(request).execute();
                if (res.code() == 200) {
                    JSONObject temp = new JSONObject(res.body().string());
                    tempId = temp.getInt("id");
                }
            } catch (IOException | NumberFormatException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            logIn(tempId);
        }
    }

}
