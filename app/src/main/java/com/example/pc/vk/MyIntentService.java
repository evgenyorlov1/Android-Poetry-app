package com.example.pc.vk;

import android.app.IntentService;
import android.content.Intent;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.example.pc.vk.MainActivity.MyBroadcastReceiver;


public class MyIntentService extends IntentService {

    public static final String ACTION_MYINTENTSERVICE = "com.example.pc.vk.intentservice.RESPONSE";
    private int SUCCESS_TAG = 0;

    public MyIntentService() {
        super("VKwallGet");
    }

    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        String line = "";
        try {
            URL url_point = new URL("https://api.vk.com/method/wall.get?owner_id=-27460410&count=5&v=5.37");
            HttpURLConnection urlConnection = (HttpURLConnection) url_point.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.connect();
            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            InputStream input = new BufferedInputStream(urlConnection.getInputStream());
            while((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }
            SUCCESS_TAG = 1;
        } catch(Exception e) {}
        if(SUCCESS_TAG == 0) {
            Intent intentResponse = new Intent();
            intentResponse.setAction(MyBroadcastReceiver.PROCESS_RESPONSE);
            intentResponse.addCategory(Intent.CATEGORY_DEFAULT);
            intentResponse.putExtra("Response", "Error");
            sendBroadcast(intentResponse);
        } else {
            Intent intentResponse = new Intent();
            intentResponse.setAction(MyBroadcastReceiver.PROCESS_RESPONSE);
            intentResponse.addCategory(Intent.CATEGORY_DEFAULT);
            intentResponse.putExtra("Response", builder.toString());
            sendBroadcast(intentResponse);
        }
    }
}
