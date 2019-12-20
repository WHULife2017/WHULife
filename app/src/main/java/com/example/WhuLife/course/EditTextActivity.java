package com.example.WhuLife.course;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.location.AMapLocationClient;
import com.example.WhuLife.MainActivity;
import com.example.WhuLife.R;
import com.example.WhuLife.ui.Login.LoginActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditTextActivity extends AppCompatActivity {
    private Button update;
    private Button addition;
    private Button delete;
    private clockmanager clock;
    private Button bt1;
    private  Button bt2;
    private int week_n=0;//当前周
    static int id=0;
    private Button loc;
    private Calendar calendar;
    public OurLocation myLocation = new OurLocation(EditTextActivity.this);
    public AMapLocationClient mapLocationClient = null;

    course_display dis[]=new course_display[20];
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);
        bt1=(Button)findViewById(R.id.on);
        bt2=(Button)findViewById(R.id.off);
        update=(Button)findViewById(R.id.update);
        addition=(Button)findViewById(R.id.addition);
        delete=(Button)findViewById(R.id.delete);
        loc=(Button)findViewById(R.id.loc);
        mapLocationClient = new AMapLocationClient(this.getApplicationContext());
        //获取当前是周几
        Calendar calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_WEEK);
        final int hour=calendar.get(Calendar.HOUR_OF_DAY);
        final int minute=calendar.get(Calendar.MINUTE);
        switch (day){
            case Calendar.TUESDAY:
                System.out.println("周四");break;
            case Calendar.WEDNESDAY:
                System.out.println("周三");break;
            default:break;
        }
        //以下是计算当前周数
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1;Date date2;
        try{
            date1 = sdf.parse("2019-9-1");//学期第一天
            System.out.println(""+calcWeekOffset(date1));
            week_n=calcWeekOffset(date1);
        }catch(Exception e){
            e.printStackTrace();
        }
        //判断是否数据库中是否有这个表，如果有result=true
        SQLiteDatabase DB;
        try{
            DB=SQLiteDatabase.openOrCreateDatabase("data/data/com.example.WhuLife.course/databases/Coursesdb",null);
        }catch (Exception e){
            MySqliteHelper  dbhelper = new MySqliteHelper(this);
            DB = dbhelper.getWritableDatabase();
            dbhelper.onCreate(DB);
        }
        String sql = "select count(*) as c from sqlite_master where type ='table' and name ='courses_info';";
        Cursor cursor;
        boolean result=false;
        cursor = DB.rawQuery(sql, null);
        if(cursor.moveToNext()){
            int count = cursor.getInt(0);
            if(count>0){
                result = true;
            }
        }
        final course_display course_clock[]=new course_display[20];int index=0;
        //以下的查询可以用于课表显示以及生成提醒
        if(result) {
            cursor= DB.query("courses_info", null, null, null, null, null, null);
            int i = 0;
            int col = 0;
            int row = 0;//想法是根据周几和上课时间确定在哪一行哪一列
            //int start_time[]=new int[20];

            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String weekday = cursor.getString(cursor.getColumnIndex("day"));
                    int start_week = cursor.getInt(cursor.getColumnIndex("start_week"));
                    int end_week = cursor.getInt(cursor.getColumnIndex("end_week"));
                    double Latitude=cursor.getDouble(cursor.getColumnIndex("Latitude"));
                    double Longtitude=cursor.getDouble(cursor.getColumnIndex("Longtitude"));
                    String location=cursor.getString(cursor.getColumnIndex("location"));
                    System.out.println("day" + cursor.getString(cursor.getColumnIndex("day")));
                    if (week_n >= start_week && week_n <= end_week) {
                        System.out.println(week_n+"  "+ cursor.getString(cursor.getColumnIndex("Cname"))+weekday);
                        switch (weekday) {//根据周几显示
                            case "Sun":
                                col = 1;
                                row = cursor.getInt(cursor.getColumnIndex("start_time"));
                                break;
                            case "Mon":
                                col = 2;
                                row = cursor.getInt(cursor.getColumnIndex("start_time"));
                                break;
                            case "Tue":
                                col = 3;
                                row = cursor.getInt(cursor.getColumnIndex("start_time"));
                                break;
                            case "Wed":
                                col = 4;
                                row = cursor.getInt(cursor.getColumnIndex("start_time"));
                                break;
                            case "Thu":
                                col = 5;
                                row = cursor.getInt(cursor.getColumnIndex("start_time"));
                                break;
                            case "Fri":
                                col = 6;
                                row = cursor.getInt(cursor.getColumnIndex("start_time"));
                                break;
                            case "Sat":
                                col = 7;
                                row = cursor.getInt(cursor.getColumnIndex("start_time"));
                                break;
                            default:
                                break;
                        }
                        System.out.println("i"+row);
                        course_clock[index]=new course_display(weekday,start_week,end_week,row,Latitude,Longtitude,location);
                        index++;
                    }
                    i++;
                }

            }
            cursor.close();

            clock=clockmanager.getInstance(this);

            bt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int m=0;m<20;m++){
                        if(course_clock[m]!=null){
                            id++;
                            clock.createAlarmManager(id);
                            clock.setAlarm2(course_clock[m].getday(),course_clock[m].gethour(),course_clock[m].getminute(),00);

                            System.out.println(m);
                        }
                    }
                    System.out.println("id+"+id);
                    if(id==0){
                        Toast.makeText(EditTextActivity.this, "当前没有课程，无法打开闹钟", Toast.LENGTH_LONG).show();
                    }
                }
            });
            bt2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(id>0){
                        for(int m=1;m<=id;m++)
                            clock.cancelAlarm(m);
                    }
                    else{
                        Toast.makeText(EditTextActivity.this, "当前没有闹钟", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
        //   calendar =Calendar.getInstance(Locale.getDefault());

//由于不太了解UI如何设计的，我留了一个Button专门让用户确定是否打开地点提醒，如果不需要你们可以去掉
        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myLocation.initLocation(mapLocationClient);
                for(int m=0;m<20;m++){
                    int interval=0;
                    if(course_clock[m]!=null){
                        interval=(course_clock[m].gethour()-hour)*60+course_clock[m].getminute()-minute;//计算上课时间和当前时间的差值，
                        if(course_clock[m].getday()==day&&(interval<30&&interval>-30)){//如果时间之差在-30~30之间，进行定位
                            myLocation.setLocation(course_clock[m].getlatitude(),course_clock[m].getlongtitude());
                            myLocation.startLocation();
                            if(myLocation.isNear()){//如果接近，显示地点，UI可以设计一个好一的方式
                                Toast.makeText(EditTextActivity.this,course_clock[m].getlocation(),Toast.LENGTH_SHORT).show();
                                Intent intent1 = new Intent(EditTextActivity.this, RingActivity.class);
                                //给Intent设置标志位Flag
                                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent1.putExtra("location",course_clock[m].getlocation() );
                                EditTextActivity.this.startActivity(intent1);
                            }
                        }}
                }
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EditTextActivity.this,load.class);
                startActivity(intent);
            }
        });
        addition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EditTextActivity.this,AddCourseActivity.class);
                startActivity(intent);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EditTextActivity.this,DeleteCourseActivity.class);
                startActivity(intent);
            }
        });
    }
    public void clicktable(View v){
        Intent intent = new Intent(EditTextActivity.this, course_table.class);
        startActivity(intent);
    };
    @Override
    protected void onDestroy(){
        super.onDestroy();
        myLocation.destroyLocation();
    }
    public static int calcWeekOffset(Date startTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startTime);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        dayOfWeek = dayOfWeek - 1;
        if (dayOfWeek == 0) dayOfWeek = 7;

        int dayOffset = calcDayOffset(startTime);

        int weekOffset = dayOffset / 7;
        int a;
        if (dayOffset > 0) {
            a = (dayOffset % 7 + dayOfWeek > 7) ? 1 : 0;
        } else {
            a = (dayOfWeek + dayOffset % 7 < 1) ? -1 : 0;
        }
        weekOffset = weekOffset + a;
        return weekOffset;
    }
    public static int calcDayOffset(Date date1) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) {  //同一年
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {  //闰年
                    timeDistance += 366;
                } else {  //不是闰年

                    timeDistance += 365;
                }
            }
            return timeDistance + (day2 - day1);
        } else { //不同年
            return day2 - day1;
        }
    }

}


