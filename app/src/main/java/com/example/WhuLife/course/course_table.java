package com.example.WhuLife.course;

import com.example.WhuLife.R;
import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;

public class course_table extends Activity {
    LinearLayout weekPanels[]=new LinearLayout[7];;
    List courseData[]=new ArrayList[7];
    int itemHeight=0;
    int marTop=0,marLeft=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coursedisplay);
        //
        itemHeight=getResources().getDimensionPixelSize(R.dimen.weekItemHeight);
        marTop=getResources().getDimensionPixelSize(R.dimen.weekItemMarTop);
        marLeft=getResources().getDimensionPixelSize(R.dimen.weekItemMarLeft);
        //数据
        getData();

        for (int i = 0; i < weekPanels.length; i++) {
            weekPanels[i]=(LinearLayout) findViewById(R.id.weekPanel_1+i);
            initWeekPanel(weekPanels[i], courseData[i]);
        }

    }
    public void getData(){

        List<courseinfo>list1=new ArrayList<courseinfo>();
        List<courseinfo>list2=new ArrayList<courseinfo>();
        List<courseinfo>list3=new ArrayList<courseinfo>();
        List<courseinfo>list4=new ArrayList<courseinfo>();
        List<courseinfo>list5=new ArrayList<courseinfo>();
        List<courseinfo>list6=new ArrayList<courseinfo>();
        List<courseinfo>list7=new ArrayList<courseinfo>();
        int week_n=0;
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

            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {

                    String location=cursor.getString(cursor.getColumnIndex("location"));
                    String coursename=cursor.getString(cursor.getColumnIndex("Cname"));
                    String Teacher=cursor.getString(cursor.getColumnIndex("Teacher"));
                    String start_time1=cursor.getString(cursor.getColumnIndex("start_time"));
                    String end_time1=cursor.getString(cursor.getColumnIndex("end_time"));
                    String Cno=cursor.getString(cursor.getColumnIndex("Cno"));
                    String day=cursor.getString(cursor.getColumnIndex("day"));
                    int now_week=15;
                    int start_week=cursor.getInt(cursor.getColumnIndex("start_week"));
                    int end_week=cursor.getInt(cursor.getColumnIndex("end_week"));
                    if(start_time1!=null&&end_time1!=null&&start_week<=now_week&&end_week>=now_week){
                    int start_time=Integer.parseInt(start_time1);
                    int end_time=Integer.parseInt(end_time1);
                    int step=end_time-start_time+1;
                    courseinfo c=new courseinfo(coursename,location,start_time,step,Teacher,Cno);
                    // System.out.println("courses" + cursor.getString(cursor.getColumnIndex("Cname")));
                    if(day!=null){
                    switch(day) {
                        case "Mon":
                            list1.add(c);
                            break;
                        case "Tue":
                            list2.add(c);
                            break;
                        case "Wed":
                            list3.add(c);
                            break;
                        case "Thu":
                            list4.add(c);
                            break;
                        case "Fri":
                            list5.add(c);
                            break;
                        case "Sat":
                            list6.add(c);
                            break;
                        case "Sun":
                            list7.add(c);
                            break;
                    }}
                    }
                        index++;
                        courseData[0]=list1;
                        courseData[1]=list2;
                        courseData[2]=list3;
                        courseData[3]=list4;
                        courseData[4]=list5;
                        courseData[5]=list6;
                        courseData[6]=list7;
                }
            }
        }
            cursor.close();
    }
        /*List<courseinfo>list1=new ArrayList<courseinfo>();
        courseinfo c1 =new courseinfo("软件工程","A402", 1, 4, "典韦", "1002");
        list1.add(c1);
        list1.add(new courseinfo("C语言", "A101", 6, 3, "甘宁", "1001"));
        courseData[0]=list1;

        List<courseinfo>list2=new ArrayList<courseinfo>();
        list2.add(new courseinfo("计算机组成原理", "A106", 6, 3, "马超", "1001"));
        courseData[1]=list2;

        List<courseinfo>list3=new ArrayList<courseinfo>();
        list3.add(new courseinfo("数据库原理", "A105", 2, 3, "孙权", "1008"));
        list3.add(new courseinfo("计算机网络", "A405", 6, 2, "司马懿", "1009"));
        list3.add(new courseinfo("电影赏析", "A112", 9, 2, "诸葛亮", "1039"));
        courseData[2]=list3;

        List<courseinfo>list4=new ArrayList<courseinfo>();
        list4.add(new courseinfo("数据结构", "A223", 1, 3, "刘备", "1012"));
        list4.add(new courseinfo("操作系统", "A405", 6, 3, "曹操", "1014"));
        courseData[3]=list4;

        List<courseinfo>list5=new ArrayList<courseinfo>();
        list5.add(new courseinfo("Android开发","C120",1,4,"黄盖","1250"));
        list5.add(new courseinfo("游戏设计原理","C120",8,4,"陆逊","1251"));
        courseData[4]=list5;
    }*/

    public void initWeekPanel(LinearLayout ll,List<courseinfo>data){
        if(ll==null || data==null || data.size()<1)return;
        Log.i("Msg", "初始化面板");
        courseinfo pre=data.get(0);
        for (int i = 0; i < data.size(); i++) {
            courseinfo c =data.get(i);
            TextView tv =new TextView(this);
            LinearLayout.LayoutParams lp =new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT ,
                    itemHeight*c.getStep()+marTop*(c.getStep()-1));
            if(i>0){
                lp.setMargins(marLeft, (c.getStart()-(pre.getStart()+pre.getStep()))*(itemHeight+marTop)+marTop, 0, 0);
            }else{
                lp.setMargins(marLeft, (c.getStart()-1)*(itemHeight+marTop)+marTop, 0, 0);
            }
            tv.setLayoutParams(lp);
            tv.setGravity(Gravity.TOP);
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            tv.setTextSize(12);
            tv.setTextColor(getResources().getColor(R.color.courseTextColor));
            tv.setText(c.getName()+"\n"+c.getRoom()+"\n"+c.getTeach());
            tv.setBackgroundColor(getResources().getColor(R.color.classIndex));
            ll.addView(tv);
            pre=c;
        }
    }


}