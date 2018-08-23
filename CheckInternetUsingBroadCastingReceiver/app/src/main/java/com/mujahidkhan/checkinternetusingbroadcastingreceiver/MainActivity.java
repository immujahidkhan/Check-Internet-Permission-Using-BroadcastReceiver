package com.mujahidkhan.checkinternetusingbroadcastingreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView txt;
    private boolean isConnectionAvailable;
    public static final String NETWORK_SWITCH_FILTER = "com.mujahidkhan.checkinternetusingbroadcastingreceiver.NETWORK_SWITCH_FILTER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = findViewById(R.id.txt);
    }

    BroadcastReceiver netSwitchReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            isConnectionAvailable = intent.getExtras().getBoolean("is_connected");
            if (!isConnectionAvailable) {
                if (NetworkErrorActivity.isOptedToOffline()) {

                }
                {
                    Intent netWorkIntent = new Intent(MainActivity.this, NetworkErrorActivity.class);
                    startActivity(netWorkIntent);
                }
            } else {
                NetworkErrorActivity.setOptedToOffline(false);
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        try {
            registerReceiver(netSwitchReceiver, new IntentFilter(NETWORK_SWITCH_FILTER));
        } catch (Exception e) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(netSwitchReceiver);
        } catch (Exception e) {

        }
    }
}