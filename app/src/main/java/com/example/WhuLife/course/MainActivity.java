package com.example.WhuLife.course;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.WhuLife.R;


public class MainActivity extends AppCompatActivity {
    private TextView tv_1;
    private Button mBtnTextView;
    private Button mBtnEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_activity_main);
      mBtnEditText=(Button)findViewById(R.id.btn_course_edittext);
      mBtnEditText.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent=new Intent(MainActivity.this,EditTextActivity.class);
              startActivity(intent);
          }
      });

    }
}
