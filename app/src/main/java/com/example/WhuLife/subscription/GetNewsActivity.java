package com.example.WhuLife.subscription;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
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


public class GetNewsActivity extends AppCompatActivity{
    private List<String> selectedpref=new ArrayList<>();
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_news);
        getPref();
        sendRequest("all");

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.get_news_menu,menu);

        for(String name:selectedpref){
            switch (name){
                case "lecture":
                    menu.findItem(R.id.news_menu_item_lecture).setVisible(true);
                    break;
                case "sports":
                    menu.findItem(R.id.news_menu_item_sports).setVisible(true);
                    break;
                case "movie":
                    menu.findItem(R.id.news_menu_item_movie).setVisible(true);
                    break;
                case "supermarket":
                    menu.findItem(R.id.news_menu_item_supermarket).setVisible(true);
                    break;
                case "whunews":
                    menu.findItem(R.id.news_menu_item_whunews).setVisible(true);
                    break;
                case "holiday":
                    menu.findItem(R.id.news_menu_item_holiday).setVisible(true);
                    break;
                case "library":
                    menu.findItem(R.id.news_menu_item_library).setVisible(true);
                    break;
                case "bulletin":
                    menu.findItem(R.id.news_menu_item_bulletin).setVisible(true);
                    break;
                default:
            }
        }
        return true;
    }

    /**
     * 按照用户喜好设置menu的选择项
     * 以便使用户只关注自己感兴趣的模块
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.news_menu_item_lecture:
                sendRequest("lecture");
                break;
            case R.id.news_menu_item_sports:
                sendRequest("sports");
                break;
            case R.id.news_menu_item_movie:
                sendRequest("movie");
                break;
            case R.id.news_menu_item_supermarket:
                sendRequest("supermarket");
                break;
            case R.id.news_menu_item_whunews:
                sendRequest("whunews");
                break;
            case R.id.news_menu_item_holiday:
                sendRequest("holiday");
                break;
            case R.id.news_menu_item_library:
                sendRequest("library");
                break;
            case R.id.news_menu_item_bulletin:
                sendRequest("bulletin");
                break;
            default:
        }
        return true;
    }

    /**
     * 获取存储在sharedprefrences中的用户偏好
     */
    private void getPref(){
        SharedPreferences pref=getSharedPreferences("data",MODE_PRIVATE);
        for(int i=0;i<PreferenceActivity.attrbute.length;i++){
            if(pref.getBoolean(PreferenceActivity.attrbute[i],false)==true)
                selectedpref.add(PreferenceActivity.attrbute[i]);
            else
                continue;
        }
        for(int i=0;i<selectedpref.size();i++)
            Log.d("VALUES",String.valueOf(selectedpref.get(i)));
    }
    private void sendRequest(final String urlStr){
        progressBar = findViewById(R.id.process_bar);
        progressBar.setVisibility(View.VISIBLE);
        Log.d("url",urlStr);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url("http://111.230.233.136:8888/api/news/?"+urlStr).build();
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
