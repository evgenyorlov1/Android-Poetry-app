package com.example.pc.vk;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.content.Intent;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    private MyBroadcastReceiver myBroadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intentService = new Intent(MainActivity.this, MyIntentService.class);
        myBroadcastReceiver = new MyBroadcastReceiver();

        IntentFilter intentFilter = new IntentFilter(MyIntentService.ACTION_MYINTENTSERVICE);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(myBroadcastReceiver, intentFilter);
        Toast.makeText(getApplicationContext(), "onCreate end", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);
        Toast.makeText(getApplicationContext(), "onDestroy", Toast.LENGTH_SHORT).show();
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {

        public static final String PROCESS_RESPONSE = "com.example.pc.vk.intent.action.PROCESS_RESPONSE";

        @Override
        public void onReceive(Context context, Intent intent) {
            String result  = intent.getStringExtra("Response");
            TextView form = (TextView) findViewById(R.id.text);
            Toast.makeText(getApplicationContext(), "Title", Toast.LENGTH_SHORT).show();
            form.setText(result);
        }
    }
}


