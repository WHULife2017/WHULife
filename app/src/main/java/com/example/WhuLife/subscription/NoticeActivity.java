package com.example.WhuLife.subscription;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.WhuLife.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NoticeActivity extends AppCompatActivity {

    private List<Check> checkList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_layout);
        sendRequest("TEST");//用于在服务器上url增加后缀以按照喜好进行查询操作


    }


    private void sendRequest(final String urlStr){
        Log.d("url",urlStr);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url("http://111.230.233.136:8888/api/msg/").build();
                    Response response = client.newCall(request).execute();
                    String res = response.body().string();
                    showResponse(res);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 调用notification显示的时候也需要使用
     * 故直接定义成静态的
     * @param response
     * @return
     */
    private List<MMessage> parseJSON(final String response) {
        Gson gson = new Gson();
        List<MMessage> msgList = gson.fromJson(response,new TypeToken<List<MMessage>>(){}.getType());
        for(MMessage m : msgList){
            Log.d("Msg","title:"+ m.getMid());
            Log.d("Msg","info:"+ m.getShortInfo());
            Log.d("Msg","time:"+ m.getTime());
            Log.d("Msg","place:"+ m.getLocation());
        }
        return msgList;
    }

    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final List<MMessage> msgList = parseJSON(response);
                Log.d("INFOMITION",msgList.toString());
                for(MMessage mMessage:msgList){
                    Check check=new Check(mMessage);
                    checkList.add(check);
                }
                for(Check check:checkList){
                    Log.d("INFOxxxxxxxxxxxxx",check.getEvent());
                }

                RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycleview_checkbox);
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(NoticeActivity.this);
                recyclerView.setLayoutManager(linearLayoutManager);
                final CheckBoxAdapter adapter=new CheckBoxAdapter(checkList);
                recyclerView.setAdapter(adapter);
                Button button1=(Button)findViewById(R.id.button_NoticeCommit);
                Button button2=(Button)findViewById(R.id.button_NoticeSelectAll);
                Button button3=(Button)findViewById(R.id.button_NoticeReset);
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean flag=false;
                        for (int i=0;i<checkList.size();i++){
                            flag|=adapter.map.get(i);
                            if(flag==true)
                                break;;
                        }
                        /*此意味着用户一个都没有选择*/
                        if(!flag){
                            AlertDialog.Builder dialog=new AlertDialog.Builder(NoticeActivity.this);
                            dialog.setTitle("错误提醒");
                            dialog.setMessage("你没有选择任何备忘事项就提交了哦");
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
                        else{
                            SharedPreferences isempty=getSharedPreferences("notice",MODE_PRIVATE);
                            if(isempty.getString("status","None").equals("edited")){
                                SharedPreferences.Editor editor=getSharedPreferences("notice",MODE_PRIVATE).edit();
                                int i=0;//前面使用了位置按序标号存储勾选状态 这里需要存储真正的ID号码
                                for(Check check:checkList){
                                    if(adapter.map.get(i)==true)
                                        editor.putBoolean(check.getNum(),adapter.map.get(i++));
                                    else
                                        i++;
                                }
                                editor.apply();
                                Toast.makeText(NoticeActivity.this,"新加备忘录已经为您添加完成",Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                SharedPreferences.Editor editor=getSharedPreferences("notice",MODE_PRIVATE).edit();
                                editor.putString("status","edited");
                                int i=0;//前面使用了位置按序标号存储勾选状态 这里需要存储真正的ID号码
                                for(Check check:checkList)
                                    editor.putBoolean(check.getNum(),adapter.map.get(i++));
                                editor.apply();
                                Toast.makeText(NoticeActivity.this,"备忘录已经为您设置完成",Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        }

                    }
                });
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.SelectAll();
                    }
                });
                button3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.ResetAll();
                    }
                });
                //TextView textView = findViewById(R.id.msg_text);
                //.setText(msgList.toString());

                //MsgAdapter adapter = new MsgAdapter(msgList);
                //recyclerView.setAdapter(adapter);
            }
        });
    }
}

