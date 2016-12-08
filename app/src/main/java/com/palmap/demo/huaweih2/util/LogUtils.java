package com.palmap.demo.huaweih2.util;

import android.util.Log;

/**
 * Created by eric3 on 2016/11/10.
 */

public class LogUtils {

  public static void i(String msg){
    if (msg!=null)
    Log.i("palmap",msg);
  }
  public static void d(String msg){
    if (msg!=null)
    Log.d("palmap",msg);
  }
  public static void e(String msg){
    if (msg!=null)
    Log.e("palmap",msg);
  }
}
