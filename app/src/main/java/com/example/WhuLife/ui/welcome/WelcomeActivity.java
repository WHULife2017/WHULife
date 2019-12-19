package com.example.WhuLife.ui.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.WhuLife.R;
import com.example.WhuLife.ui.Login.LoginActivity;

public class WelcomeActivity extends AppCompatActivity {
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE); //去掉标题栏
        setContentView(R.layout.welcome);

        new Thread() {
            public void run() {
                try {
                    //等待两秒钟
                    sleep(1000);
                    //跳转进入APP主页面
                    intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }.start();
    }
}
