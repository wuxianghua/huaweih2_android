package com.palmap.demo.huaweih2.http;

import com.palmap.demo.huaweih2.util.DialogUtils;

/**
 * 错误定义类
 * Created by chuanchao.shao on 2014/5/5.
 */
public class ErrorCode {

  /**
   * 类型转换错误,返回的实际对象与想要得到的不符
   */
  public static final int CODE_CLASS_ERROR = 1001;
  /**
   * JSON类型转换错误，返回的实际对象与想要得到的不符
   */
  public static final int CODE_JSON_ERROR = 1002;

  /**
   * 网络请求错误
   */
  public static final int CODE_REQUEST_ERROR = 1003;

  /**
   * 无网络连接
   */
  public static final int CODE_NO_INTERNET = 1004;

  /*
  * Exception
  * */
  public static final int CODE_EXCEPTION = 1005;

  public static void showError(int code){
    if (code==CODE_NO_INTERNET)
      DialogUtils.showShortToast("无网络连接");
    if (code==CODE_EXCEPTION)
      DialogUtils.showShortToast("网络错误");
    if (code==CODE_REQUEST_ERROR)
      DialogUtils.showShortToast("请求出错");
  }
}
