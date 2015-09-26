package com.example.pc.vk;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

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
            String description = groupsJson();
            String[] lyrics = wallJson();

            Object[] results = new Object[6];
            results[0] = groupsJson();
            try {
                for (int i=0; i<5; i++) {
                    results[i+1] = lyrics[i];
                }
            } catch(Exception e) {Log.d("myLogs", "for" + String.valueOf(e));}

            Log.d("myLogs", description);
            result = description;
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("myLogs", "on post execute");
            //String[] text = wallJson();
            t.setText(result);
        }


        protected String[] wallJson() {
            String wallGet = "https://api.vk.com/method/wall.get?owner_id=-27460410&count=5&v=5.37";
            StringBuilder builder = new StringBuilder();
            try {
                URL url_point = new URL(wallGet);
                HttpURLConnection urlConnection = (HttpURLConnection) url_point.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setReadTimeout(10000);
                urlConnection.connect();
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    builder.append(line + "\n");
                }
            } catch (Exception e) {Log.d("myLogs", String.valueOf(e));}

            String json = builder.toString();
            String[] lyrics = new String[5]; //count from wallGet
            try {
                Log.d("myLogs", "in wallJson");
                JSONObject jsonObject = new JSONObject(json);
                JSONObject response = jsonObject.getJSONObject("response");
                JSONArray items = response.getJSONArray("items");
                for(int i=0; i<items.length(); i++) {
                    JSONObject item = items.getJSONObject(i);
                    lyrics[i] = item.getString("text");
                }
            } catch (Exception e) {Log.d("myLogs", String.valueOf(e));}
            return lyrics;
        }


        protected String groupsJson() {
            String groupsGetById = "https://api.vk.com/method/groups.getById?group_ids=27460410&fields=description&v=5.37";
            StringBuilder builder = new StringBuilder();
            try {
                URL url_point = new URL(groupsGetById);
                HttpURLConnection urlConnection = (HttpURLConnection) url_point.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setReadTimeout(10000);
                urlConnection.connect();
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    builder.append(line + "\n");
                }
            } catch (Exception e) {Log.d("myLogs", String.valueOf(e));}

            String json = builder.toString();
            String description = "";
            try {
                Log.d("myLogs", "in wallJson");
                JSONObject jsonObject = new JSONObject(json);
                JSONArray response = jsonObject.getJSONArray("response");
                description = response.getJSONObject(0).getString("description");
            } catch (Exception e) {Log.d("myLogs", String.valueOf(e));}

            return description;
        }

    }
}


