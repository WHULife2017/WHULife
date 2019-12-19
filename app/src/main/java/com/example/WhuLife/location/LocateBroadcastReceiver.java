package com.example.WhuLife.location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class LocateBroadcastReceiver extends BroadcastReceiver {
    
    @Override
    public void onReceive(Context context, Intent intent){
        Toast.makeText(context, "received", Toast.LENGTH_SHORT).show();
    }
}
