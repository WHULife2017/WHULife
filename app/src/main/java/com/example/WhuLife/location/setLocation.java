package com.example.WhuLife.location;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.WhuLife.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.qqtheme.framework.picker.SinglePicker;
import cn.qqtheme.framework.util.ConvertUtils;
import cn.qqtheme.framework.picker.TimePicker;

public class setLocation extends AppCompatActivity implements View.OnClickListener{

    private EditText editText,hourtext,typetext,editPackageId;
    private FloatingActionButton fab;
    private Location goal_location = new Location(0,0,"null");
    private String packageNumber;
    private int goal_h, goal_m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_location);
        editText=findViewById(R.id.edittext);
        editText.setFocusable(false);
        editText.setOnClickListener(this);
        hourtext=findViewById(R.id.hourtext);
        hourtext.setFocusable(false);
        hourtext.setOnClickListener(this);
        typetext=findViewById(R.id.type);
        typetext.setFocusable(false);
        typetext.setOnClickListener(this);
        editPackageId = findViewById(R.id.packageid);
        fab=findViewById(R.id.save);
        fab.setOnClickListener(this);
    }

    public void onTimePicker(View view, final EditText editText) {
        TimePicker picker = new TimePicker(this, TimePicker.HOUR_24);
        picker.setUseWeight(false);
        picker.setCycleDisable(false);
        picker.setRangeStart(0, 0);//00:00
        picker.setRangeEnd(23, 59);//23:59
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
        picker.setSelectedItem(currentHour, currentMinute);
        picker.setTopLineVisible(false);
        picker.setTextPadding(ConvertUtils.toPx(this, 15));
        picker.setOnTimePickListener(new TimePicker.OnTimePickListener() {
            @Override
            public void onTimePicked(String hour, String minute) {
                showToast(hour + ":" + minute);
                String text=hour+":"+minute;
                editText.setText(text);

            }
        });
        picker.show();
    }

    public void onSinglePicker(View view, final EditText editText) {
        List<Location> data = new ArrayList<>();
        data.add(new Location(30.539110423443365,114.3586011007117, "近邻宝"));
        data.add(new Location(30,114, "工学部菜市场"));
        SinglePicker<Location> picker = new SinglePicker<>(this, data);
        picker.setCanceledOnTouchOutside(false);
        picker.setSelectedIndex(1);
        picker.setCycleDisable(true);
        picker.setOnItemPickListener(new SinglePicker.OnItemPickListener<Location>() {
            @Override
            public void onItemPicked(int index, Location item) {
                goal_location.setfetchLocation(item.getfetchLocation());
                goal_location.setLongitude(item.getLongitude());
                goal_location.setLatitude(item.getLatitude());
                editText.setText(item.getfetchLocation());
            }
        });
        picker.show();
    }

    public void onPicker(View view, final EditText editText) {
        List<Company> data = new ArrayList<>();
        data.add(new Company("百世快递"));
        data.add(new Company("京东快递"));
        data.add(new Company("中通快递"));
        SinglePicker<Company> picker = new SinglePicker<>(this, data);
        picker.setCanceledOnTouchOutside(false);
        picker.setSelectedIndex(1);
        picker.setCycleDisable(true);
        picker.setOnItemPickListener(new SinglePicker.OnItemPickListener<Company>() {
            @Override
            public void onItemPicked(int index, Company item) {
                goal_location.setCompany(item.getCompany());
                editText.setText(item.getCompany());
            }
        });
        picker.show();
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void setInfo(){
        if(goal_location.getLatitude() != 0){
            Intent intent = new Intent();
            intent.putExtra("location", goal_location.getfetchLocation());
            intent.putExtra("longtitude", goal_location.getLongitude());
            intent.putExtra("latitude", goal_location.getLatitude());
            intent.putExtra("company", goal_location.getCompany());
            intent.putExtra("packageId", editPackageId.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.edittext:
                onSinglePicker(view,editText);
                break;
            case R.id.hourtext:
                onTimePicker(view,hourtext);
                break;
            case R.id.type:
                onPicker(view,typetext);
                break;
            case R.id.packageid:
                break;
            case R.id.save:
                setInfo();
                break;
        }
    }
}
