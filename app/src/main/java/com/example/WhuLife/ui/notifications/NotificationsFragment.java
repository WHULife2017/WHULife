package com.example.WhuLife.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.WhuLife.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;
import org.json.JSONStringer;
import org.jsoup.helper.HttpConnection;

import java.io.Closeable;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    //private List<String> subscriptionNoticeNum=new ArrayList<>();
    //JsonArray subscriptionNoticeNum=new JsonArray();
    /*小写的Json是google提供的 没有直接将list放入的函数*/
    JSONObject subscriptionNoticeNum=new JSONObject();
    List<String> noticenum=new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)  {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
//        final TextView textView = root.findViewById(R.id.text_notifications);
////        notificationsViewModel.getText().observe(this, new Observer<String>() {
////            @Override
////            public void onChanged(@Nullable String s) {
////                textView.setText(s);
////            }
////        });
        readNotification();

        return root;
    }
    private void readNotification(){
        SharedPreferences prefSubscribtion=getContext().getSharedPreferences("notice", Context.MODE_PRIVATE);
        Map<String,?> noticedata=prefSubscribtion.getAll();
//        Log.d("Data",String.valueOf(noticedata));
        //JsonObject test=new JsonObject();
        for(Map.Entry<String,?> tmp:noticedata.entrySet()){
            if(String.valueOf(tmp.getValue())=="true"){
                noticenum.add(tmp.getKey());
                //test.addProperty("Num",tmp.getKey());
                //subscriptionNoticeNum.add(test);
            }
            else
                continue;
        }
        try {
            subscriptionNoticeNum.put("Num",noticenum);
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            getNoticeInfo();
        }catch (Exception e){
            e.printStackTrace();
        }

        Log.d("DATA",String.valueOf(subscriptionNoticeNum));
        Log.d("DATA",String.valueOf(prefSubscribtion.getAll()));
    }
    private void getNoticeInfo() throws IOException {
        MediaType JSON=MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody=RequestBody.create(JSON,String.valueOf(subscriptionNoticeNum));

        OkHttpClient client=new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://111.230.233.136:8888/api/msg/")
                .post(requestBody)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response=client.newCall(request).execute();
        if(response.isSuccessful()){
            Log.d("REQUEST",String.valueOf(response));
        }else{
            Log.d("REQUEST",String.valueOf(response));
            throw new IOException("Unexpected code " + response);
        }


    }
}