package com.palmap.demo.huaweih2;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bugtags.library.Bugtags;
import com.palmap.demo.huaweih2.util.SystemBarTintManager;

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

  @Override
  protected void onResume() {
    super.onResume();
    //注：Bugtags回调 1
    Bugtags.onResume(this);
  }

  @Override
  protected void onPause() {
    super.onPause();
    //注：Bugtags回调 2
    Bugtags.onPause(this);
  }

  @Override
  public boolean dispatchTouchEvent(MotionEvent ev) {
    //注：Bugtags回调 3
    Bugtags.onDispatchTouchEvent(this, ev);
    return super.dispatchTouchEvent(ev);
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


  /**
   * 设置状态栏颜色
   * API >=19
   *
   * @param colorId
   */
  @TargetApi(Build.VERSION_CODES.KITKAT)
  protected void initStatusBar(int colorId) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 19
      Window window = getWindow();
      WindowManager.LayoutParams winParams = window.getAttributes();
      final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
      winParams.flags |= bits;
      window.setAttributes(winParams);
      SystemBarTintManager tintManager = new SystemBarTintManager(this);
      tintManager.setStatusBarTintEnabled(true);
      tintManager.setNavigationBarTintEnabled(true);
      tintManager.getConfig().getNavigationBarHeight();
      try {
        tintManager.setTintResource(colorId);
        int height = tintManager.getConfig().getStatusBarHeight();
        getRootView().setPadding(
            getRootView().getLeft(),
            getRootView().getPaddingTop() + height,
            getRootView().getRight(),
            getRootView().getBottom());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 获取root节点
   *
   * @return
   */
  public View getRootView() {
    return findViewById(android.R.id.content);
  }

}
