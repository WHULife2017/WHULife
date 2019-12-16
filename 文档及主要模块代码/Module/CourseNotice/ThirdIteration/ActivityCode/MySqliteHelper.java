package com.example.helloworld;
//数据库的类
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySqliteHelper extends SQLiteOpenHelper {
    public MySqliteHelper(Context context){
        super(context,"wujingjingdb",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        Log.i("tag","----------onCreate--------");
     //   String sql="create table courses(Sno varchar(20),Cno varchar(20),Cname varchar(30),Ctime datetime (8),CLongitude float(4),CLatitude float(4),Croom varchar(20) primary key(Sno,Cno))";
       String sql="create table if not exists courses_info(Cno varchar(20) primary key,Cname varchar(30),Type varchar(20),Dept varchar(20),Teacher varchar(10),Sco float(4),Length varchar(10),location varchar(30),day varchar(10),start_week number,end_week number,start_time number,end_time number,Latitude double,Longtitude double)";
//  String Cnos[]=new String[20];String Cname[]=new String[20];String type[]=new String[20];String dept[]=new String[20];String teacher[]=new String[20];String sco[]=new String[20];String length[]=new String[20];String location[]=new String[20];String day[]=new String [20];String start_week[]=new String[20];String end_week[]=new String[20];String start_time[]=new String[20];double Latitude[]=new double[20];double Longtitude[]=new double[20];
       db.execSQL(sql);
   //     String Cnos[];String Cname[];String type[];String dept[];String teacher[];float sco[];String length[];String loaction_time[];
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
       //db.execSQL("DROP TABLE IF EXISTS courses_info");
        //onCreate(db);

    }
}
