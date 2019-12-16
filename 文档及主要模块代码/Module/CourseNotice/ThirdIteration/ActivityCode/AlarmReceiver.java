package com.example.helloworld;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("wwwwwwwww");
        Log.d("aaaa","test1");
        // if ("com.example.alarm.Ring".equals(intent.getAction())) {
        String msg = intent.getStringExtra("msg");
        Log.i("test1", "收到广播了");
        //Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

        Toast.makeText(context, "收到广播了", Toast.LENGTH_LONG).show();
        //跳转到Activity
        Intent intent1 = new Intent(context, RingActivity.class);
        //给Intent设置标志位Flag
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
    }
}
