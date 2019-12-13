package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class EditTextActivity extends AppCompatActivity {
    private Button update;
    private SQLiteDatabase DB;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);
        update=(Button)findViewById(R.id.update);
      /* Intent intent = getIntent() ;
        Bundle bundle = this.getIntent().getExtras();
        String Cnos[] =  bundle.getStringArray("DATA0") ;
        String Cname[] =  bundle.getStringArray("DATA1") ;
        String type[] =  bundle.getStringArray("DATA2") ;
        String dept[] =  bundle.getStringArray("DATA3") ;
        String teacher[] =  bundle.getStringArray("DATA4") ;
        String sco[] =  bundle.getStringArray("DATA5") ;
        String length[] =  bundle.getStringArray("DATA6") ;
        String location_time[] =  bundle.getStringArray("DATA7") ;*/
   /*     SQLiteDatabase dbRead = SQLiteDatabase.openOrCreateDatabase("data/data/com.example.helloworld/databases/wujingjingdb",null);

        String Cnos[]=new String[20];String Cname[]=new String[20];String type[]=new String[20];String dept[]=new String[20];String teacher[]=new String[20];String sco[]=new String[20];String length[]=new String[20];String loaction_time[]=new String[20];

        Cursor cursor = dbRead.query("courses",null,null,null,null,null,null);
        int i=0;
        if(cursor != null && cursor.getCount() >0){
            while(cursor.moveToNext()){
                String data = cursor.getString(cursor.getColumnIndex("Cno"));
                Cnos[i]=data;
                data = cursor.getString(cursor.getColumnIndex("Cname"));
                Cname[i]=data;
                data = cursor.getString(cursor.getColumnIndex("Type"));
                type[i]=data;
                data = cursor.getString(cursor.getColumnIndex("Dept"));
                dept[i]=data;
                data = cursor.getString(cursor.getColumnIndex("Teacher"));
                teacher[i]=data;
                data = cursor.getString(cursor.getColumnIndex("Sco"));
                sco[i]=data;
                data = cursor.getString(cursor.getColumnIndex("Length"));
                length[i]=data;
                data = cursor.getString(cursor.getColumnIndex("Location_time"));
                loaction_time[i]=data;
                i++;
            }
        }
        cursor.close();
        System.out.println("wwjj"+Cnos[0]);*/
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EditTextActivity.this,load.class);
                startActivity(intent);
                //以下的查询可以用于课表显示
                SQLiteDatabase DB;
                DB=SQLiteDatabase.openOrCreateDatabase("data/data/com.example.helloworld/databases/wujingjingdb", null);
                Cursor cursor = DB.query("courses_info", null, null, null, null, null, null);
                int i = 0;
                if (cursor != null && cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        String data = cursor.getString(cursor.getColumnIndex("Cno"));
                        System.out.println(data);
                        i++;
                    }
                }
                cursor.close();

            }
            });

    }
}

