package com.palmap.demo.huaweih2.http;

import android.os.Handler;
import android.os.Looper;

import org.apache.http.NameValuePair;

import java.util.List;
import java.util.Map;

/**
 * Created by zhang on 2015/10/12.
 * 将接口和具体请求分离，使回调工作在主线程
 */
public class DataProvider {
  private Handler mHandler = new Handler(Looper.getMainLooper());

  /*
  * get请求
  * */
  public void getProvider(String url, Map<String, String> heads, final HttpDataCallBack<Object> callBack){
    AsyncHttp.getInstance().getRequest(url, heads, new HttpDataCallBack<Object>() {
      @Override
      public void onError(final int errorCode) {
        mHandler.post(new Runnable() {
          @Override
          public void run() {
            callBack.onError(errorCode);
          }
        });
      }

      @Override
      public void onComplete(final Object content) {
        mHandler.post(new Runnable() {
          @Override
          public void run() {
            callBack.onComplete(content);
          }
        });
      }
    });
  }

  /*
  * post请求
  * */
  public void postProvider(String url, Map<String, String> heads, List<NameValuePair> pairList, final HttpDataCallBack callBack){
    AsyncHttp.getInstance().postRequest(url, heads, pairList, new HttpDataCallBack<Object>() {
      @Override
      public void onError(final int errorCode) {
        mHandler.post(new Runnable() {
          @Override
          public void run() {
            callBack.onError(errorCode);
          }
        });
      }

      @Override
      public void onComplete(final Object content) {
        mHandler.post(new Runnable() {
          @Override
          public void run() {
            callBack.onComplete(content);
          }
        });
      }
    });
  }

  /*
  * post请求传递数据
  * */
  public void postDataProvider(String url, Map<String,String> heads, byte[] data, final HttpDataCallBack callBack){
    AsyncHttp.getInstance().sendDataByPost(url, data, heads, new HttpDataCallBack() {
      @Override
      public void onError(final int errorCode) {
        mHandler.post(new Runnable() {
          @Override
          public void run() {
            callBack.onError(errorCode);
          }
        });
      }

      @Override
      public void onComplete(final Object content) {
        mHandler.post(new Runnable() {
          @Override
          public void run() {
            callBack.onComplete(content);
          }
        });
      }
    });
  }
  /*
  * put请求传递数据
  * */
  public void putDataProvider(String url, Map<String,String> heads, byte[] data, final HttpDataCallBack callBack){
    AsyncHttp.getInstance().sendDataByPut(url, data, heads, new HttpDataCallBack() {
      @Override
      public void onError(final int errorCode) {
        mHandler.post(new Runnable() {
          @Override
          public void run() {
            callBack.onError(errorCode);
          }
        });
      }

      @Override
      public void onComplete(final Object content) {
        mHandler.post(new Runnable() {
          @Override
          public void run() {
            callBack.onComplete(content);
          }
        });
      }
    });
  }

  /*
  * delete请求
  * */
  public void deleteProvider(String url, Map<String,String> heads, final HttpDataCallBack callBack){
    AsyncHttp.getInstance().deleteRequest(url, heads, new HttpDataCallBack() {
      @Override
      public void onError(final int errorCode) {
        mHandler.post(new Runnable() {
          @Override
          public void run() {
            callBack.onError(errorCode);
          }
        });
      }

      @Override
      public void onComplete(final Object content) {
        mHandler.post(new Runnable() {
          @Override
          public void run() {
            callBack.onComplete(content);
          }
        });
      }
    });
  }

}