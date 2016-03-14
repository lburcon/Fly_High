package com.example.yggdralisk.flyhighconference;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yggdralisk on 27.02.16.
 */
public class LoginFragment extends Fragment {

    protected EditText emailText;
    protected EditText passwordText;
    protected Button loginButton;
    protected ImageButton backButton;
    MainActivity mainActivity;
    Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_layout, container, false);

        emailText = (EditText) view.findViewById(R.id.login_layout_email);
        passwordText = (EditText) view.findViewById(R.id.login_layout_password);
        loginButton = (Button) view.findViewById(R.id.login_layout_login_button);
        backButton = (ImageButton) view.findViewById(R.id.login_layout_back_button);
        mainActivity = (MainActivity) getContext();
        mContext = container.getContext();

        setButtons();


        return view;
    }

    private void setButtons() {
        loginButton.setOnClickListener(new loginListener());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setPreviousFragment();
            }
        });
    }

    private boolean logIn(int id) {
        if (id != -1) {
            if (!DataGetter.checkUserLogged(mContext))
                DataGetter.toggleUserLogged(mContext, emailText.getText().toString(), id);
            String userName = emailText.getText().toString().substring(0, emailText.getText().toString().indexOf('@'));
            mainActivity.setLoggedNameOnDrawer(userName);
            displayToast("Zalogowano jako: " + userName);
            mainActivity.changeLoginLogoutDrawer();


            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mainActivity.setFragment(null, new LoginOutFragment());
                }
            }, 2000);
            return true;
        } else {
            displayToast(getString(R.string.login_error_messege));
            DataGetter.toggleUserLogged(mContext, "", -1);
            mainActivity.setLoggedNameOnDrawer("");
            mainActivity.changeLoginLogoutDrawer();
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
                displayToast("Błędny format email");
            return matcher.matches();
        }

        private boolean checkPassword() {
            if (passwordText.getText().toString().isEmpty())//TODO:Czy potrzebujemy innego systemu sprawdzania haseł?
            {
                displayToast("Błędne hasło!");
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
