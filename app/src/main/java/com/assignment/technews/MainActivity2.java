package com.assignment.technews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    TextView titleTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        titleTxt = (TextView) findViewById(R.id.webTitle) ;

        Intent intent = getIntent();
        titleTxt.setText(intent.getStringExtra("title"));

        String urlWeb = intent.getStringExtra("url");
        WebView view = (WebView) findViewById(R.id.webView);
        view.getSettings().setJavaScriptEnabled(true);
        view.setWebViewClient(new WebViewClient());
        view.loadUrl(urlWeb);

    }
}