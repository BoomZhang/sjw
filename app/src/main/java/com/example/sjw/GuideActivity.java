package com.example.sjw;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 创建时间：2019/4/26
 * 作者：sjw
 * 描述：
 */
public class GuideActivity extends AppCompatActivity {

  private WebView mWebView;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_guide_layout);
    mWebView = findViewById(R.id.guide_webview);
    mWebView.setWebViewClient(new WebViewClient());
    mWebView.loadUrl("file:///android_asset/guide.html");
  }
}
