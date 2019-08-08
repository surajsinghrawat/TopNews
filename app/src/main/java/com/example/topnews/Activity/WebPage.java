package com.example.topnews.Activity;

import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.topnews.R;

public class WebPage extends AppCompatActivity {
    private WebView vistaWeb;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_page);

        setupActionBar("Top News");
         //set webView
        showWebPage();

    }
//Action Bar
    private void setupActionBar(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

//webView
    private void showWebPage()
    {

        progressBar=findViewById(R.id.progressBar);
        vistaWeb = findViewById(R.id.webView);

        vistaWeb.setVisibility(View.INVISIBLE);
        vistaWeb.clearCache(true);
        vistaWeb.getSettings().setJavaScriptEnabled(true);
        vistaWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        vistaWeb.setWebChromeClient(new WebChromeClient());
        vistaWeb.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
                vistaWeb.setVisibility(View.VISIBLE);

            }
        });
        vistaWeb.loadUrl(getIntent().getStringExtra("webPage"));
    }
}
