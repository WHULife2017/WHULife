package com.example.WhuLife.subscription;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.WhuLife.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetNewsActivity extends AppCompatActivity{

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_news);
        progressBar = findViewById(R.id.process_bar);
        progressBar.setVisibility(View.VISIBLE);
        sendRequest("ss");
    }
    private void sendRequest(final String urlStr){
        Log.d("url",urlStr);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url("http://111.230.233.136:8888/").build();
                    Response response = client.newCall(request).execute();
                    String res = response.body().string();
                    showResponse(res);
                } catch (Exception e){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            AlertDialog.Builder dialog = new AlertDialog.Builder(GetNewsActivity.this);
                            dialog.setTitle("Error");
                            dialog.setMessage("The server is closed!");
                            dialog.setCancelable(false);
                            dialog.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                            dialog.show();
                        }
                    });
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private List<MNews> parseJSON(final String response) {
        Gson gson = new Gson();
        List<MNews> mNews = gson.fromJson(response,new TypeToken<List<MNews>>(){}.getType());
        for(MNews m : mNews){
            Log.d("M","url:"+ m.getUrl());
            Log.d("M","title:"+ m.getTitle());
        }
        return mNews;
    }

    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final List<MNews> newsList = parseJSON(response);
                RecyclerView recyclerView = findViewById(R.id.news_list);
                progressBar.setVisibility(View.GONE);
                LinearLayoutManager layoutManager = new LinearLayoutManager(GetNewsActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                NewsAdapter adapter = new NewsAdapter(newsList);
                recyclerView.setAdapter(adapter);

            }
        });
    }

}
