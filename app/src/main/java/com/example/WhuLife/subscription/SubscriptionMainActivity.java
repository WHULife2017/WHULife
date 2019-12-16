package com.example.WhuLife.subscription;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.WhuLife.R;

public class SubscriptionMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subscription_activity_main);
        Button button1=(Button)findViewById(R.id.button_preference);
        Button button2=(Button)findViewById(R.id.button_changepreference);
        Button button3=(Button)findViewById(R.id.button_setnotice);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SubscriptionMainActivity.this, PreferenceActivity.class);
                Intent intent2=new Intent(SubscriptionMainActivity.this, GetNewsActivity.class);
                if(IsPreferenceEmpty())
                    startActivity(intent);
                else
                    startActivity(intent2);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SubscriptionMainActivity.this, PreferenceActivity.class);
                startActivity(intent);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SubscriptionMainActivity.this, NoticeActivity.class);
                startActivity(intent);
            }
        });
    }
    /*判断用户是否已经设置了自己的喜好信息，如果没设置的话则紧喜好设置，否则跳转进推送主页*/
    protected boolean IsPreferenceEmpty(){
        SharedPreferences pref=getSharedPreferences("data",MODE_PRIVATE);
        /*其实理论上如果还没有设置的话应该没有该文件 但也是返回true 为空*/
        if(pref.getString("Status","None").equals("edited"))
            return false;
        else
            return true;
//        FileInputStream in=null;
//        BufferedReader reader=null;
//        try{
//            in=openFileInput("data");
//            reader=new BufferedReader(new InputStreamReader(in));
//            /*read()读取的是字节数*/
//            if(reader.read()!=0)
//                return false;
//            else
//                return true;
//        }catch (IOException e){
//            e.printStackTrace();
//        }finally {
//            try{
//                if(reader!=null){
//                    reader.close();
//                }
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//        }
//        return true;//读取发生异常 很可能没有该文件需要重新设置

    }
}
