package com.example.whulife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PushNewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pushnews_layout);
        WebView webView=(WebView)findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        Intent intent=getIntent();
        String url=intent.getStringExtra("URL");
        Log.d("URL",url);
        webView.loadUrl(url);
    }
}
