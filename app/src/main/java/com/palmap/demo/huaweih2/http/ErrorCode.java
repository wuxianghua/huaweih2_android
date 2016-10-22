package com.palmap.demo.huaweih2.http;

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

}
