package com.palmap.demo.huaweih2;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.palmap.demo.huaweih2.view.TitleBar;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class ActivityWeb extends BaseActivity {
  public static final String TITLE = "title";
  public static final String URL = "url";
  String url;
  String title;
  WebView webView ;
  TitleBar titleBar;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_web);


    url = getIntent().getStringExtra(URL);
    title = getIntent().getStringExtra(TITLE);
    titleBar = (TitleBar)findViewById(R.id.title_bar);

    titleBar.show(null, title ==null?"点餐": title,null);
    titleBar.setOnTitleClickListener(new TitleBar.OnTitleClickListener() {
      @Override
      public void onLeft() {
        finish();
      }

      @Override
      public void onRight() {

      }
    });

    if (title.equals("客流分析"))
      titleBar.setVisibility(View.GONE);

    webView = (WebView) findViewById(R.id.web_view);
    webView.setWebViewClient(new WebViewClient(){
      @Override
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
      }
    });
    WebSettings webSettings = webView.getSettings();
    webSettings.setSupportZoom(true);
    webSettings.setJavaScriptEnabled(true);
    webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
    webSettings.setBuiltInZoomControls(true);//support zoom/
    webSettings.setUseWideViewPort(true);// 适应手机
    webSettings.setLoadWithOverviewMode(true);
    webView.setWebViewClient(new WebViewClient(){

      @Override
      public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        showProgress("提示","加载中，请稍后");
      }

      @Override
      public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        closeProgress();
      }

      @Override
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
        //对网页中超链接按钮的响应
        if( url.startsWith("http:") || url.startsWith("https:") ) {
          return false;//webview自己处理
        }

        // Otherwise allow the OS to handle things like tel, mailto, etc.
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(text));
//        startActivity( intent );
        return true;//不处理
      }
    });
    if (url!=null)
      webView.loadUrl(url);
  }
}
