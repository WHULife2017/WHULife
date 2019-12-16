package com.example.WhuLife.course;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.WhuLife.R;

import java.util.regex.Pattern;

public class AddCourseActivity extends AppCompatActivity {
    private EditText cno;
    private EditText cname;
    private EditText time;
    private EditText location;
    private Button commit;
    private Button display;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        cno=(EditText)findViewById(R.id.cno);
        cname=(EditText)findViewById(R.id.cname);
        time=(EditText)findViewById(R.id.time);
        location=(EditText)findViewById(R.id.location);
        commit=(Button)findViewById(R.id.commit);
        display=(Button)findViewById(R.id.display);
        final MySqliteHelper dbhelper = new MySqliteHelper(this);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cno.getText().toString().length()==0||cname.getText().toString().length()==0||location.getText().toString().length()==0||time.getText().toString().length()==0){//||cname.getText().toString()==""||location.getText().toString()==""||time.getText().toString()==""
                    Toast.makeText(AddCourseActivity.this,"必要信息未填写",Toast.LENGTH_SHORT).show();
                }
                else{
                SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
                dbhelper.onCreate(sqLiteDatabase);
                ContentValues values = new ContentValues();
                String a=time.getText().toString();
                Pattern p = Pattern.compile("[-周节]");
                String[] r= p.split(a);
                values.put("Cno",cno.getText().toString());
                values.put("Cname",cname.getText().toString());
                values.put("start_week",r[0]);
                values.put("end_week",r[2]);
                values.put("day",r[4]);
                values.put("start_time",r[5]);
                values.put("end_time",r[6]);
                values.put("location",location.getText().toString());
                sqLiteDatabase.insert("courses_info", null, values);
                Toast.makeText(AddCourseActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
             }
            }
        });
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddCourseActivity.this,EditTextActivity.class);
                startActivity(intent);
            }
        });

    }
}
