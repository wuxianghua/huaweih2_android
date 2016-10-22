package com.palmap.demo.huaweih2;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by eric3 on 2016/9/27.
 */
public class BaseActivity extends FragmentActivity {

  private ProgressDialog progressDialog = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Log.w("BaseActivity", "onCreate(Bundle savedInstanceState)");
    super.onCreate(savedInstanceState);
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
//    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //设置全屏
//    initStatusBar();
  }

  /*
  *  显示进度条
  * */
  protected void showProgress(String title, String msg){
    if (progressDialog == null){
      progressDialog = new ProgressDialog(this);
      progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
      progressDialog.setCanceledOnTouchOutside(false);
      progressDialog.setIndeterminate(false);
      progressDialog.setCancelable(true);
    }

    progressDialog.setTitle(title);
    progressDialog.setMessage(msg);

    if (!progressDialog.isShowing()){
      progressDialog.show();
    }
  }

  /*
  *  关闭进度条
  * */
  protected void closeProgress(){
    Log.e("exp", "BaseActivity->closeProgress()");
    if (progressDialog != null && progressDialog.isShowing()){
      progressDialog.dismiss();
    }
  }

  protected ProgressDialog getProgressDialog() {
    return progressDialog;
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    closeProgress();
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK){
      // 如果是返回键，动画结束该activity
      finish();
    }
    return super.onKeyDown(keyCode, event);
  }

  @TargetApi(19)
  private void setTranslucentStatus(boolean on){
    Window window =  getWindow();
    WindowManager.LayoutParams winParams = window.getAttributes();
    final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
    final int bits_nav = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
    if (on){
      winParams.flags |= bits;
      winParams.flags |= bits_nav;
    } else {
      winParams.flags &= ~bits;
      winParams.flags &= ~bits_nav;
    }
    window.setAttributes(winParams);
  }

}
