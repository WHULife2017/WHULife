package com.example.WhuLife.course;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.WhuLife.R;

public class RingActivity extends AppCompatActivity {
    private TextView info;
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring);
        info=(TextView)findViewById(R.id.info);

        //播放音乐
        //mediaPlayer = MediaPlayer.create(this, R.raw.nqmm);
        //mediaPlayer.start();

        //显示通知栏
        Intent intent = getIntent();
        String String1 ="闹钟响了";
        String locString=intent.getStringExtra("location");
        if(locString!=null)
            info.setText(locString);
        else
            info.setText(String1);
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
        /*if(mediaPlayer!=null){
            if(mediaPlayer.isPlaying()){
                mediaPlayer.stop();
            }
            mediaPlayer.release();
        }*/
        finish();
    }
}
