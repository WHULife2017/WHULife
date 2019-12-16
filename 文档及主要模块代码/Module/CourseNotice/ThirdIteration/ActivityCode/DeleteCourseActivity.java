package com.example.helloworld;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DeleteCourseActivity extends AppCompatActivity {
    private EditText cno;
    private EditText cname;
    private Button commit;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_del_course);
        cno=(EditText)findViewById(R.id.cno);
        cname=(EditText)findViewById(R.id.cname);
        commit=(Button)findViewById(R.id.commit);
        MySqliteHelper dbhelper = new MySqliteHelper(this);
        SQLiteDatabase DB;
        DB=SQLiteDatabase.openOrCreateDatabase("data/data/com.example.helloworld/databases/wujingjingdb", null);

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cno.getText().toString().length()==0||cname.getText().toString().length()==0){
                    Toast.makeText(DeleteCourseActivity.this,"必要信息未填写",Toast.LENGTH_SHORT).show();
                }
                else{
                String sql = "delete  from courses_info where Cno="+cno.getText().toString()+" and "+" Cname "+" = "+"'"+cname.getText().toString()+"'"+";";
                DB.execSQL(sql);
               // Intent intent=new Intent(DeleteCourseActivity.this,EditTextActivity.class);
                //startActivity(intent);
                    DeleteCourseActivity.this.finish();
            }
            }
        });
    }
}
