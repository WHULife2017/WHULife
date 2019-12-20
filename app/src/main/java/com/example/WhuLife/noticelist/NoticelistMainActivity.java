package com.example.WhuLife.noticelist;

import androidx.annotation.UiThread;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.WhuLife.R;
import com.example.WhuLife.subscription.Check;
import com.example.WhuLife.subscription.CheckBoxAdapter;
import com.example.WhuLife.subscription.MMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NoticelistMainActivity extends AppCompatActivity {

    JSONObject subscriptionNoticeNum=new JSONObject();
    //JsonArray subscriptionNoticeNum=new JsonArray();
    /*小写的Json是google提供的 没有直接将list放入的函数*/
    List<String> noticenum=new ArrayList<>();
    private List<Check> checkList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_layout);
        readNotification();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void readNotification(){
        SharedPreferences prefSubscribtion=getSharedPreferences("notice", Context.MODE_PRIVATE);
        Map<String,?> noticedata=prefSubscribtion.getAll();
//        Log.d("Data",String.valueOf(noticedata));
        //JsonObject test=new JsonObject();
        for(Map.Entry<String,?> tmp:noticedata.entrySet()){
            if(String.valueOf(tmp.getValue())=="true"){
                noticenum.add(tmp.getKey());
                //test.addProperty("Num",tmp.getKey());
                //subscriptionNoticeNum.add(test);
            }
        }
        try {
            subscriptionNoticeNum.put("Num",noticenum);
        }catch (Exception e){
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    getNoticeInfo();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
        Log.d("DATA",String.valueOf(prefSubscribtion.getAll()));
    }
    private void getNoticeInfo() throws IOException {
        MediaType JSON=MediaType.parse("text/plain; charset=utf-8");
        //RequestBody requestBody = RequestBody.create(noticenum.toString(),JSON);
        RequestBody requestBody = RequestBody.create(subscriptionNoticeNum.toString(),JSON);
        Log.w("s",noticenum.toString());
        OkHttpClient client=new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://111.230.233.136:8888/api/msg/")
                .post(requestBody)
                .build();
        Log.w("sbbbbb",noticenum.toString());
        Response response=client.newCall(request).execute();
        Log.w("saaaaa",noticenum.toString());
        if(response.isSuccessful()){
            Log.d("REQUEST",String.valueOf(response));
            String res = response.body().string();
            Log.d("REQUEST",res);
            final List<MMessage> msgList = parseJSON(res);
            for(MMessage mMessage:msgList){
                Check check=new Check(mMessage);
                checkList.add(check);
            }
            for(Check check:checkList){
                Log.d("INFOxxxxxxxxxxxxx",check.getEvent());
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycleview_checkbox);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(NoticelistMainActivity.this);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    final CheckBoxAdapter adapter=new CheckBoxAdapter(checkList);
                    recyclerView.setAdapter(adapter);
                    Button button1=(Button)findViewById(R.id.button_NoticeCommit);
                    button1.setText("移除备忘录");
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
                                AlertDialog.Builder dialog=new AlertDialog.Builder(NoticelistMainActivity.this);
                                dialog.setTitle("错误提醒");
                                dialog.setMessage("你没有选择任何备忘事项就提交了哦");
                                dialog.setCancelable(false);
                                dialog.setPositiveButton("重新选择", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

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
                                SharedPreferences.Editor editor=getSharedPreferences("notice",MODE_PRIVATE).edit();
                                int i=0;//前面使用了位置按序标号存储勾选状态 这里需要存储真正的ID号码
                                for(Check check:checkList){
                                    if(adapter.map.get(i)==true)
                                        editor.putBoolean(check.getNum(),!adapter.map.get(i++));
                                    else
                                        i++;
                                }

                                editor.apply();
                                Toast.makeText(NoticelistMainActivity.this,"选择的备忘录已经为您移除",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getBaseContext(),NoticelistMainActivity.class);
                                startActivity(intent);
                                finish();
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
                }
            });

            Log.d("Message",msgList.toString());
            Log.d("REQUEST",String.valueOf(response));
        }else{
            Log.d("REQUEST",String.valueOf(response));
            throw new IOException("Unexpected code " + response);
        }


    }


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

}
