package com.example.clock2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
        Button button_3=findViewById(R.id.button_3);
        button_3.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_1:
                clock.setAlarm1(2019, 11, 13, 11, 44, 00);
                Log.d("aaa", "teat4");
                break;
            case R.id.button_2:
                clock.setAlarm2(6, 11, 43, 00);
                Log.d("aaa", "teat111");
                break;
            case R.id.button_3:
                clock.cancelAlarm(0x101);
                Log.d("aaa", "teat000");
        }
    }
}
