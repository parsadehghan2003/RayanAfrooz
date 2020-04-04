package com.PechPech.pechpechprivate;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

public class AutoStart extends BroadcastReceiver {
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onReceive(final Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) G.context.getSystemService("connectivity");
        if (cm != null) {
            cm.addDefaultNetworkActiveListener(new ConnectivityManager.OnNetworkActiveListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                public void onNetworkActive() {
                    context.startForegroundService(new Intent(context, JobService.class));
                }
            });
        }
        Log.i("Autostart", "started");
    }
}