package com.example.pc.vk;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends Activity {

    private String result = "";
    TextView form;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        form = (TextView) findViewById(R.id.text);
        try {
            MyAsyncTask mt = new MyAsyncTask();
            mt.execute(form);
        } catch(Exception e) {form.setText(e.toString());}

    }



    private class MyAsyncTask extends AsyncTask<TextView, Void, String> {

        TextView t;
        String result = "fail";

        @Override
        protected String doInBackground(TextView... params) {

            this.t = params[0];

            StringBuilder builder = new StringBuilder();
            try {
                URL url_point = new URL("https://api.vk.com/method/wall.get?owner_id=-27460410&count=5&v=5.37");
                HttpURLConnection urlConnection = (HttpURLConnection) url_point.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setReadTimeout(10000);
                urlConnection.connect();
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line = "";
                builder.append("test");
                while ((line = reader.readLine()) != null) {
                    builder.append(line + "\n");
                }
            } catch (Exception e) {}
            result = builder.toString();
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            t.setText(result);
        }

    }
}


