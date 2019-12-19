package com.example.WhuLife.course;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.TextView;
import android.widget.Toast;

import com.example.WhuLife.course.AlarmReceiver;

import java.util.Calendar;
import java.util.Locale;

public class clockmanager {
    public static AlarmManager am;
    public static PendingIntent pendingIntent;
    private Context context;
    private Calendar calendar1,calendar2,calendar3;

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
    //设置闹钟（年月日时分秒）
    public void setAlarm1(int year1,int month1,int date1,int hour1,int minute1,int second1) {

        calendar1 =Calendar.getInstance(Locale.getDefault());
        calendar2 =Calendar.getInstance(Locale.getDefault());
        calendar2.setTimeInMillis(System.currentTimeMillis());
        /*int year=calendar2.get(Calendar.YEAR);
        int month=calendar2.get(Calendar.MONTH);
        int date=calendar2.get(Calendar.DATE);
        int hour=calendar2.get(Calendar.HOUR_OF_DAY);
        int minute=calendar2.get(Calendar.MINUTE);
        int second=calendar2.get(Calendar.SECOND);
        time2.setText("Calendar获取当前日期"+year+"年"+month+"月"+day+"日"+hour+":"+minute+":"+second);*/
        calendar1.set(Calendar.YEAR, year1);
        calendar1.set(Calendar.MONTH, month1);
        calendar1.set(Calendar.DATE, date1);
        calendar1.set(Calendar.HOUR_OF_DAY, hour1);
        calendar1.set(Calendar.MINUTE, minute1);
        calendar1.set(Calendar.SECOND, second1);

        if (calendar2.getTimeInMillis()<calendar1.getTimeInMillis()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), pendingIntent);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                am.setExact(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), pendingIntent);
            } else {
                am.setRepeating(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
            }
            Toast.makeText(context, "设置成功", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(context, "设置时间已过", Toast.LENGTH_LONG).show();
        }

    }
    //设置闹钟（一年的第几周，一周的第几天）
    public void setAlarm2(int day1,int hour1,int minute1,int second1) {

        calendar1 =Calendar.getInstance(Locale.getDefault());
        calendar2 =Calendar.getInstance(Locale.getDefault());
        calendar3 =Calendar.getInstance(Locale.getDefault());
        calendar2.setTimeInMillis(System.currentTimeMillis());
        int day=calendar2.get(Calendar.DAY_OF_WEEK);
        int hour=calendar2.get(Calendar.HOUR_OF_DAY);
        int minute=calendar2.get(Calendar.MINUTE);
        int second=calendar2.get(Calendar.SECOND);
        calendar3.set(Calendar.DAY_OF_WEEK,day);
        calendar3.set(Calendar.HOUR_OF_DAY, hour);
        calendar3.set(Calendar.MINUTE, minute);
        calendar3.set(Calendar.SECOND, second);
      /*  if(day>day1){
            Toast.makeText(context, "设置时间已过", Toast.LENGTH_LONG).show();

        }else if(hour>hour){
            Toast.makeText(context, "设置时间已过", Toast.LENGTH_LONG).show();
        }*/
        //Calendar.WEEK_OF_YEAR:当前年中星期数
        //calendar.set(Calendar.WEEK_OF_YEAR,week1);
        //Calendar.DAY_OF_WEEK:当前星期的第几天(星期天表示第一天，星期六表示第七天)
        calendar1.set(Calendar.DAY_OF_WEEK,day1);
        calendar1.set(Calendar.HOUR_OF_DAY, hour1);
        calendar1.set(Calendar.MINUTE, minute1);
        calendar1.set(Calendar.SECOND, second1);

        if(calendar3.getTimeInMillis()<calendar1.getTimeInMillis()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), pendingIntent);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                am.setExact(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), pendingIntent);
            } else {
                am.setRepeating(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
            }
            Toast.makeText(context, "设置成功", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(context, "设置时间已过", Toast.LENGTH_LONG).show();
        }
    }

    //取消闹钟
    public void cancelAlarm(int id) {
        am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(context, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, id , intent, PendingIntent.FLAG_CANCEL_CURRENT);
        am.cancel(pi);//取消闹钟
        Toast.makeText(context, "取消成功", Toast.LENGTH_LONG).show();
    }

}
