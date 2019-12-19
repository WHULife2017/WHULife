package com.example.WhuLife.subscription;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.WhuLife.R;

import java.util.ArrayList;
import java.util.List;

public class PreferenceActivity extends AppCompatActivity {
    private List<CheckBox>checkBoxList=new ArrayList<>();
    public static String[] attrbute={"lecture","sports","movie","supermarket","whunews","holiday","library","bulletin"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference_layout);
        Button button1=(Button)findViewById(R.id.button_commit);
        Button button2=(Button)findViewById(R.id.button_reset);
        Button button3=(Button)findViewById(R.id.button_selectall);
        InitCheckBox();
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavePreferenceToSP();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceSurfaceRest();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectAll();
            }
        });
    }

    private void InitCheckBox(){
        checkBoxList.add((CheckBox)findViewById(R.id.checkbox_lecture));
        checkBoxList.add((CheckBox)findViewById(R.id.checkbox_sports));
        checkBoxList.add((CheckBox)findViewById(R.id.checkbox_movie));
        checkBoxList.add((CheckBox)findViewById(R.id.checkbox_supermakert));
        checkBoxList.add((CheckBox)findViewById(R.id.checkbox_whunews));
        checkBoxList.add((CheckBox)findViewById(R.id.checkbox_holiday));
        checkBoxList.add((CheckBox)findViewById(R.id.checkbox_library));
        checkBoxList.add((CheckBox)findViewById(R.id.checkbox_bulletin));
    }
    /*处理用户的偏好设置 如果没选择则让用户进行重选或者不想选了就返回到上一级页面*/
    protected void SavePreferenceToSP(){
        /*flag确保至少有一个选项框被选择了*/
        boolean flag=false;
        for(CheckBox checkBox:checkBoxList){
            flag|=checkBox.isChecked();
            if(flag==true)
                break;
        }
        if(flag){
            SharedPreferences.Editor editor=getSharedPreferences("data",MODE_PRIVATE).edit();
            editor.putString("Status","edited");//方便后续进行判断有无设置完成
            int i=0;
            for(CheckBox checkBox:checkBoxList){
                editor.putBoolean(attrbute[i++],checkBox.isChecked());
            }
            editor.apply();
            //editor.commit();由于并发性原因被弃用的提交用法
            Toast.makeText(this,"你已经设置好了你的喜好，快来看看吧",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this, GetNewsActivity.class);
            startActivity(intent);
            /*确保后面返回不会返回这个界面了直接退出到最开始的界面*/
            this.finish();
        }/*否则一个选择框都没有选 我们弹出一个消息窗让其重选或退出*/
        else {
            AlertDialog.Builder dialog=new AlertDialog.Builder(PreferenceActivity.this);
            dialog.setTitle("错误提醒");
            dialog.setMessage("你没有选择任何选项就提交了哦");
            dialog.setCancelable(false);
            dialog.setPositiveButton("重新选择",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog,int which){

                }
            });
            dialog.setNegativeButton("返回上页", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dialog.show();
        }
    }

    protected void PreferenceSurfaceRest(){
        for(CheckBox checkbox:checkBoxList){
            checkbox.setChecked(false);
        }
    }
    protected void SelectAll(){
        for(CheckBox checkbox:checkBoxList){
            checkbox.setChecked(true);
        }
    }
}
