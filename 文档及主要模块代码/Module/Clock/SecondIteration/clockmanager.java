package com.example.clock2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

public class clockmanager {
    public static AlarmManager am;
    public static PendingIntent pendingIntent;
    private Context context;
    private Calendar calendar;

    private clockmanager(Context context){
        this.context=context;
    }

    private static clockmanager instance=null;
    public static clockmanager getInstance(Context context){
        if(instance==null){
            synchronized(clockmanager.class){
                if(instance==null){
                    instance=new clockmanager(context);
                }
            }
        }
        return instance;
    }
    //初始化定义
    public void createAlarmManager(int id){
        am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context,AlarmReceiver.class);
        pendingIntent=PendingIntent.getBroadcast(context,id, intent,PendingIntent.FLAG_CANCEL_CURRENT);
    }
    //设置闹钟
    public void setAlarm(int year1,int month1,int date1,int hour1,int minute1,int second1) {

        calendar =Calendar.getInstance(Locale.getDefault());
        //calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.YEAR, year1);
        calendar.set(Calendar.MONTH, month1);
        calendar.set(Calendar.DATE, date1);
        calendar.set(Calendar.HOUR_OF_DAY, hour1);
        calendar.set(Calendar.MINUTE, minute1);
        calendar.set(Calendar.SECOND, second1);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            am.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }else{
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
        }
        Toast.makeText(context, "设置成功", Toast.LENGTH_LONG).show();

    }
    //取消闹钟
    public void cancelAlarm(int id) {
        Intent intent=new Intent(context,AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, id , intent, 0);
        am.cancel(pi);//取消闹钟
        Toast.makeText(context, "取消成功", Toast.LENGTH_LONG).show();
    }

}
