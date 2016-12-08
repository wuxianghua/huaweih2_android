package com.palmap.demo.huaweih2.http;

import com.palmap.demo.huaweih2.util.LogUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by eric3 on 2016/11/22.
 */

public class OkHttpUtils {
  public final static long CONNECT_TIMEOUT =60;
  private static OkHttpClient mOkHttpClient;

  private static OkHttpClient getInstence(){
    if (mOkHttpClient==null){
      mOkHttpClient = new OkHttpClient().newBuilder()
          .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
          .build();
    }
    return mOkHttpClient;
  }

  public static void get(String url,final HttpDataCallBack callback){
    Headers headers = new Headers.Builder()
        .add("Accept", "application/json")
        .add("Content-Type", "application/json")
        .build();

    final Request request = new Request.Builder()
        .headers(headers)
        .url(url)
        .build();

    getInstence().newCall(request).enqueue(new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {

        callback.onError(ErrorCode.CODE_EXCEPTION);

        if (e!=null)
        LogUtils.e(e.getMessage());
      }

      @Override
      public void onResponse(Call call, Response response) throws IOException {
        int resCode = response.code();
        if (resCode == 200)
          callback.onComplete(response.body().string());
        else
          callback.onError(response.code());
      }
    });
  }

  public static void postFormData(String url, File file, String jsonData,final HttpDataCallBack callback){
    Headers headers = new Headers.Builder()
        .add("Accept", "application/json")
        .add("Content-Type", "application/json")
        .build();

    RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
    RequestBody requestBody = new MultipartBody.Builder()
        .setType(MultipartBody.FORM)
        .addFormDataPart("file", "temp.png", fileBody)
        .addFormDataPart("text", jsonData)
        .build();

    final Request request = new Request.Builder()
        .headers(headers)
        .post(requestBody)
        .url(url)
        .build();

    getInstence().newCall(request).enqueue(new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {

        callback.onError(ErrorCode.CODE_EXCEPTION);

        if (e!=null)
        LogUtils.e(e.getMessage());
      }

      @Override
      public void onResponse(Call call, Response response) throws IOException {
        int resCode = response.code();
        if (resCode == 200)
          callback.onComplete(response.body().string());
        else
          callback.onError(response.code());
      }
    });
  }



}
