package com.example.pc.vk;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

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
            MyAsyncTask mt = new MyAsyncTask(new AsyncResponse() {
                @Override
                public void processFinish(Object output) {
                    result = output.toString();
                }
            });
            mt.execute();
        } catch(Exception e) {form.setText(e.toString());}

    }



    private class MyAsyncTask extends AsyncTask<Object, Object, Object> {

        public AsyncResponse delegate = null;//Call back interface

        public MyAsyncTask(AsyncResponse asyncResponse) {
            delegate = asyncResponse;//Assigning call back interfacethrough constructor
        }

        @Override
        protected Object doInBackground(Object... params) {

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
            return builder;
        }

        @Override
        protected void onPostExecute(Object result) {
            delegate.processFinish(result);
        }

    }
}


