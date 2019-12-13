# **课表组时间接口第二次迭代**

### **代码**

代码有clockmanager.java，AlarmReceiver.java，RingActivity.java三个文件

#### **（1）clockmanager.java中的主要函数：**

public void createAlarmManager(int id)：初始化函数，定义AlarmManager类，提供对系统闹钟服务的访问接口；定义事件Intent和PendingIntent，这个类用于处理即将发生的事情，从系统取得一个用于向BroadcastReceiver的Intent广播的PendingIntent对象。由参数id 来区分不同的闹钟

```
 public void createAlarmManager(int id){
        am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context,AlarmReceiver.class);
        pendingIntent=PendingIntent.getBroadcast(context,id, intent,PendingIntent.FLAG_CANCEL_CURRENT);
    }
```

public void setAlarm(int year1,int month1,int date1,int hour1,int minute1,int second1)：设置闹钟函数，参数为年、月、日、时、分、秒，利用calendar.set()函数确定闹钟开始时间，根据API版本不同使用不同的方法设置闹钟。setExactAndAllowWhileIdle()和setExact()是一次性闹钟，setRepeating()是周期性闹钟，intervalTime为周期参数。

```
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
```

public void cancelAlarm(int id)：取消闹钟函数，需要重新intent和和PendingIntent，cancel(PendingIntent)取消闹钟。参数id确定要取消的闹钟。

```
 public void cancelAlarm(int id) {
        Intent intent=new Intent(context,AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, id , intent, 0);
        am.cancel(pi);//取消闹钟
        Toast.makeText(context, "取消成功", Toast.LENGTH_LONG).show();
    }
```

#### **（2）AlarmReceiver.java广播接收**

继承BroadcastReceiver类，闹钟时间到时调用，并用startActivity()函数启动RingActivity活动

```
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //String msg = intent.getStringExtra("msg");
        // Log.i("test1", "收到广播了");
        Toast.makeText(context, "收到广播了", Toast.LENGTH_LONG).show();
        //跳转到Activity
        Intent intent1 = new Intent(context, RingActivity.class);
        //给Intent设置标志位Flag
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
    }
}
```

#### **（3）RingActivity.java**

定义MediaPlayer实例，设置要播放的音乐，start()开始播放，stop()通知播放

NotificationCompat.Builder实例化通知栏构造器，设置参数；NotificationManager创建通知栏管理器，notify()发送通知

```
public class RingActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring);
        //播放音乐
        mediaPlayer = MediaPlayer.create(this, R.raw.nqmm);
        mediaPlayer.start();

        //显示通知栏
        NotificationCompat.Builder notificationCompat=new NotificationCompat.Builder(this,"default");
        //设置参数
        notificationCompat.setDefaults(NotificationCompat.DEFAULT_ALL);
        notificationCompat.setContentTitle("提醒");
        notificationCompat.setContentText("闹钟响了");
        notificationCompat.setSmallIcon(android.R.drawable.ic_lock_idle_alarm);
        Notification notification=notificationCompat.build();
        //通知管理器
        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //发送通知
        notificationManager.notify(0x101,notification);
    }
    public void close(View view){
        mediaPlayer.stop();
        finish();
    }
}

```

### **实例**

##### **使用条件：**

在res文件夹下新建raw文件夹，存入音乐（MP3）

##### **使用方法：**

（1）定义一个clockmanager类，并实例化，需要传入当前活动上下文

`clock=clockmanager.getInstance(this);`

（2）初始化函数，参数id区分不同的闹钟

`clock.createAlarmManager(0x101);`

（3）设置闹钟，参数为时间

`clock.setAlarm(2019, 11, 6, 10, 45, 00);`

（4）取消闹钟，参数为要取消的闹钟的id

` clock.cancelAlarm(0x101);`

##### **实例代码**

```
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

   private clockmanager clock;

   @Override

   protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        clock=clockmanager.getInstance(this);

        clock.createAlarmManager(0x101);

        Button button_1=findViewById(R.id.button_1);

        button_1.setOnClickListener(this);

        Button button_2=findViewById(R.id.button_2);

        button_2.setOnClickListener(this);

    }

    @Override

    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.button_1:

                 clock.setAlarm(2019, 11, 6, 10, 45, 00);

                 break;

            case R.id.button_2:

                clock.cancelAlarm(0x101);

                break;

      }

   }

}
```



#### **代码流程图**

![代码流程图](.\img\代码流程图.png)

### **UI界面**

开始界面：有设置闹钟和取消闹钟按钮

![界面1](.\img\界面1.png)

点击按钮后设置闹钟，下方弹出“设置成功”

![界面2](.\img\界面2.png)

在时间到后，收到广播，跳转到闹钟界面，并播放音乐

![界面3](.\img\界面3.png)

点击取消闹钟按钮，可以取消设置好的闹钟

![界面4](.\img\界面4.png)

#### **后续改进：**

确定不同闹钟的提醒方式（音乐、振动），与闹钟id的对应

根据数据设定闹钟时间的输入形式（年月日/第几周）

设置检测多个闹钟（id的设置/setAction()）和重复闹钟的呈现结果，修改代码

配合UI组设计界面

 

 

 

 

 