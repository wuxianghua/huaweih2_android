package com.palmap.demo.huaweih2.http;

import android.util.Log;

import com.palmap.demo.huaweih2.HuaWeiH2Application;
import com.palmap.demo.huaweih2.util.PhoneUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;

import static com.vividsolutions.jts.geom.Location.BOUNDARY;


/**
 * Created by zhang on 2015/10/12.
 */
public class AsyncHttp {
  public static int HTTP_CONNECTION_TIMEOUT = 30; // 单位秒
  private static final String CHARSET = "utf-8"; //设置编码

  private static AsyncHttp instance = null;

  /*
  * 获取单实例对象
  * */
  public static AsyncHttp getInstance(){
    if (instance == null){
      instance = new AsyncHttp();
    }
    return instance;
  }

  /*
  * get数据请求
  * */
  public ClientConnectionManager getRequest(final String url, final Map<String, String> heads, final HttpDataCallBack callback){
    // 如果callback为null，则请求结果无法返回
    if (callback == null){
      return null;
    }

    // 检测网络是否连接
    if (!PhoneUtils.isNetWorkConnected(HuaWeiH2Application.instance)){
      callback.onError(ErrorCode.CODE_NO_INTERNET); // 无网络连接
      return null;
    }

    // 判断url是否为空
    if (url == null){
      callback.onError(ErrorCode.CODE_REQUEST_ERROR); // 网络请求错误
      return null;
    }
    Log.w("AsyncHttp", "url: " + url);

    // 创建http连接相关对象
    final HttpClient httpClient = new DefaultHttpClient();

    // 启用工作线进行网络请求
    Executors.newSingleThreadExecutor().execute(new Runnable() {
      @Override
      public void run() {
        // 设置参数
        HttpParams params = httpClient.getParams();
        params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, HTTP_CONNECTION_TIMEOUT * 1000); // 超时连接设置

        HttpGet httpGet = new HttpGet(url);

        // 设置head
        if (heads != null){
          Log.w("AsyncHttp","添加head中...");
          for (String key : heads.keySet()){
            Log.w("AsyncHttp","key: " + key);
//            Log.w("AsyncHttp","value: " + heads.get(key));
            httpGet.addHeader(key, heads.get(key));
          }
        }
        // 执行请求
        InputStream inputStream = null;
        try {
          HttpResponse response = httpClient.execute(httpGet);
          int returnCode = response.getStatusLine().getStatusCode();
          Log.w("AsyncHttp", "returnCode = " + returnCode);
          if (returnCode == HttpURLConnection.HTTP_OK){
            HttpEntity entity = response.getEntity();
            Log.w("AsyncHttp", "entity.getContentType().getValue(): " + entity.getContentType().getValue());
            if (entity != null){
              String contentType = entity.getContentType().getValue();
              if (contentType.contains("application/json")){
                String content = EntityUtils.toString(entity, HTTP.UTF_8);
                callback.onComplete(content);
              } else if (contentType.contains("application/x-protobuf")){
                inputStream = entity.getContent();
                int size = (int) entity.getContentLength();
                Log.w("AsyncHttp", "size = " + size);
                if (size < 0) {
                  callback.onError(ErrorCode.CODE_EXCEPTION);
                  return;
                }
                byte[] buffer = new byte[size];
                int curr = 0, read;
                while (curr < size){
                  read = inputStream.read(buffer, curr, size - curr);
                  if (read <= 0){
                    break;
                  }
                  curr += read;
                }
                callback.onComplete(buffer);
              } else { // 返回类型不明
                Log.w("AsyncHttp", "返回数据类型未知！");
                callback.onError(ErrorCode.CODE_REQUEST_ERROR);
              }
            } else {
              callback.onError(ErrorCode.CODE_REQUEST_ERROR);
            }
          } else {
            callback.onError(returnCode);
          }
        } catch (IOException e) {
          e.printStackTrace();
          Log.w("AsyncHttp", "执行httpClient.execute(httpGet)的异常！");
          callback.onError(ErrorCode.CODE_REQUEST_ERROR);
        } finally {
          httpClient.getConnectionManager().shutdown(); // 关闭请求连接
          if (inputStream != null){
            try {
              inputStream.close();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        }

      }
    });

    return httpClient.getConnectionManager();
  }

  /*
  * post数据请求
  * */
  public ClientConnectionManager postRequest(final String url, final Map<String, String> heads, final List<NameValuePair> pairList, final HttpDataCallBack callback){
    // 如果callback为null，则请求结果无法返回
    if (callback == null){
      return null;
    }

    // 检测网络是否连接
    if (!PhoneUtils.isNetWorkConnected(HuaWeiH2Application.instance)){
      callback.onError(ErrorCode.CODE_NO_INTERNET); // 无网络连接
      return null;
    }

    // 判断url是否为空
    if (url == null){
      callback.onError(ErrorCode.CODE_REQUEST_ERROR); // 网络请求错误
      return null;
    }
    Log.w("AsyncHttp", "url: " + url);

    // 创建http连接相关对象
    final DefaultHttpClient httpClient = new DefaultHttpClient();

    Executors.newSingleThreadExecutor().execute(new Runnable() {
      @Override
      public void run() {
        // 设置参数
        HttpParams params = httpClient.getParams();
        params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, HTTP_CONNECTION_TIMEOUT * 1000); // 超时连接设置

        HttpPost httpPost = new HttpPost(url);

        // 设置head
        if (heads != null){
          Log.w("AsyncHttp","添加head中...");
          for (String key : heads.keySet()){
            Log.w("AsyncHttp", "key: " + key);
//              Log.w("AsyncHttp", "value: " + heads.get(key));
            httpPost.addHeader(key, heads.get(key));
          }
        }

        InputStream inputStream = null;
        try {

          httpPost.setEntity(new UrlEncodedFormEntity(pairList, HTTP.UTF_8));

          HttpResponse response = httpClient.execute(httpPost);
          int returnCode = response.getStatusLine().getStatusCode();
          if (returnCode == HttpURLConnection.HTTP_OK){
            HttpEntity entity = response.getEntity();
            Log.w("AsyncHttp", "entity.getContentType().getValue(): " + entity.getContentType().getValue());

            if (entity != null){
              String contentType = entity.getContentType().getValue();
              if (contentType.contains("application/json")){
                String content = EntityUtils.toString(entity, HTTP.UTF_8);
                callback.onComplete(content);
              } else if (contentType.contains("application/x-protobuf")){
                inputStream = entity.getContent();
                int size = (int) entity.getContentLength();
                Log.w("AsyncHttp", "size = " + size);
                if (size < 0) {
                  callback.onError(ErrorCode.CODE_EXCEPTION);
                  return;
                }
                byte[] buffer = new byte[size];
                int curr = 0, read = 0;
                while (curr < size){
                  read = inputStream.read(buffer, curr, size - curr);
                  if (read <= 0){
                    break;
                  }
                  curr += read;
                }
                callback.onComplete(buffer);
              } else { // 返回类型不明
                Log.w("AsyncHttp", "返回数据类型未知！");
                callback.onError(ErrorCode.CODE_REQUEST_ERROR);
              }
            } else {
              callback.onError(ErrorCode.CODE_REQUEST_ERROR);
            }
          } else {
            callback.onError(returnCode);
          }

        } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
          Log.w("AsyncHttp", "new UrlEncodedFormEntity(pairList, HTTP.UTF_8)异常！");
          callback.onError(ErrorCode.CODE_REQUEST_ERROR);
        } catch (ClientProtocolException e) {
          e.printStackTrace();
          Log.w("AsyncHttp", "httpClient.execute(httpPost)异常->ClientProtocolException！");
          callback.onError(ErrorCode.CODE_REQUEST_ERROR);
        } catch (IOException e) {
          e.printStackTrace();
          Log.w("AsyncHttp", "httpClient.execute(httpPost)异常->IOException！");
          callback.onError(ErrorCode.CODE_REQUEST_ERROR);
        } finally {
          httpClient.getConnectionManager().shutdown();
          if (inputStream != null){
            try {
              inputStream.close();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        }

      }
    });

    return httpClient.getConnectionManager();
  }

  /**
   * Delete
   * @param url 发送请求的URL
   *
   */
  public void deleteRequest(final String url, final Map<String, String> heads, final HttpDataCallBack callback){
    // 如果callback为null，则请求结果无法返回
    if (callback == null){
      return;
    }

    // 检测网络是否连接
    if (!PhoneUtils.isNetWorkConnected(HuaWeiH2Application.instance)){
      callback.onError(ErrorCode.CODE_NO_INTERNET); // 无网络连接
      return;
    }

    // 判断url是否为空
    if (url == null){
      callback.onError(ErrorCode.CODE_REQUEST_ERROR); // 网络请求错误
      return;
    }

    Log.i("AsyncHttp", "url: " + url);

    Executors.newSingleThreadExecutor().execute(new Runnable() {
      @Override
      public void run() {
        try {
          URL httpUrl = new URL(url);
          HttpURLConnection urlConnection = (HttpURLConnection) httpUrl.openConnection();
          urlConnection.setConnectTimeout(HTTP_CONNECTION_TIMEOUT * 1000);
          urlConnection.setRequestMethod("DELETE");
          urlConnection.setDoInput(true); // 读取数据
          urlConnection.setDoOutput(false); // 向服务器写数据

          // 添加请求属性
          if (heads != null && !heads.isEmpty()){
            for (Object key : heads.keySet()){
              urlConnection.setRequestProperty(key.toString(), heads.get(key));
            }
          }
//          urlConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
//          Log.w("AsyncHttp", "data.length = " + data.length);

//          if (data!=null) {
//            // 获得输出流，向服务器输出内容
//            OutputStream outputStream = urlConnection.getOutputStream();
//            outputStream.write(data, 0, data.length);
//            outputStream.flush();
//            outputStream.close();
//          }

          // 获得服务器响应结果和状态码
          int responseCode = urlConnection.getResponseCode();
          Log.w("AsyncHttp", "sendData->responseCode = " + responseCode);
          if (responseCode == 200 || responseCode == 204 || responseCode == 201){//现在反204
            InputStream inputStream = urlConnection.getInputStream();
            String result = changeInputStream(inputStream, HTTP.UTF_8);
            inputStream.close();
            callback.onComplete(result);
          } else {
            callback.onError(responseCode);
          }

        } catch (MalformedURLException e) {
          e.printStackTrace();
          callback.onComplete(ErrorCode.CODE_EXCEPTION);
        } catch (IOException e) {
          e.printStackTrace();
          callback.onComplete(ErrorCode.CODE_EXCEPTION);
        }
      }
    });
  }
  /*
  * post请求，发送数据
  * */
  public void sendDataByPost(final String url, final byte[] data, final Map<String, String> heads, final HttpDataCallBack callback){
    // 如果callback为null，则请求结果无法返回
    if (callback == null){
      return;
    }

    // 检测网络是否连接
    if (!PhoneUtils.isNetWorkConnected(HuaWeiH2Application.instance)){
      callback.onError(ErrorCode.CODE_NO_INTERNET); // 无网络连接
      return;
    }

    // 判断url是否为空
    if (url == null){
      callback.onError(ErrorCode.CODE_REQUEST_ERROR); // 网络请求错误
      return;
    }

    // 判断数据是否为空
    if (data == null || data.length <= 0){
      callback.onError(ErrorCode.CODE_REQUEST_ERROR); // 网络请求错误
      return;
    }
    Log.i("AsyncHttp", "url: " + url);

    Executors.newSingleThreadExecutor().execute(new Runnable() {
      @Override
      public void run() {
        try {
          URL httpUrl = new URL(url);
          HttpURLConnection urlConnection = (HttpURLConnection) httpUrl.openConnection();
          urlConnection.setConnectTimeout(HTTP_CONNECTION_TIMEOUT * 1000);
          urlConnection.setRequestMethod("POST");
          urlConnection.setDoInput(true); // 读取数据
          urlConnection.setDoOutput(true); // 向服务器写数据

          // 添加请求属性
          if (heads != null && !heads.isEmpty()){
            for (Object key : heads.keySet()){
              urlConnection.setRequestProperty(key.toString(), heads.get(key));
            }
          }
//          urlConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
//          Log.w("AsyncHttp", "data.length = " + data.length);


          // 获得输出流，向服务器输出内容
          OutputStream outputStream = urlConnection.getOutputStream();
          outputStream.write(data, 0, data.length);
          outputStream.flush();
          outputStream.close();


          // 获得服务器响应结果和状态码
          int responseCode = urlConnection.getResponseCode();
          Log.w("AsyncHttp", "sendData->responseCode = " + responseCode);
          if (responseCode == 200 || responseCode == 204 || responseCode == 201){//现在反204
            InputStream inputStream = urlConnection.getInputStream();
            String result = changeInputStream(inputStream, HTTP.UTF_8);
            inputStream.close();
            callback.onComplete(result);
          } else {
            callback.onError(responseCode);
          }

        } catch (MalformedURLException e) {
//          DialogUtils.showLongToast(e.getMessage());
          callback.onError(ErrorCode.CODE_EXCEPTION);
        } catch (IOException e) {
//          DialogUtils.showLongToast(e.getMessage());
          callback.onError(ErrorCode.CODE_EXCEPTION);
        }
      }
    });

  }

  /*
  * post请求，发送数据
  * */
  public void sendDataByPut(final String url, final byte[] data, final Map<String, String> heads, final HttpDataCallBack callback){
    // 如果callback为null，则请求结果无法返回
    if (callback == null){
      return;
    }

    // 检测网络是否连接
    if (!PhoneUtils.isNetWorkConnected(HuaWeiH2Application.instance)){
      callback.onError(ErrorCode.CODE_NO_INTERNET); // 无网络连接
      return;
    }

    // 判断url是否为空
    if (url == null){
      callback.onError(ErrorCode.CODE_REQUEST_ERROR); // 网络请求错误
      return;
    }

    // 判断数据是否为空
    if (data == null || data.length <= 0){
      callback.onError(ErrorCode.CODE_REQUEST_ERROR); // 网络请求错误
      return;
    }
    Log.i("AsyncHttp", "url: " + url);

    Executors.newSingleThreadExecutor().execute(new Runnable() {
      @Override
      public void run() {
        try {
          URL httpUrl = new URL(url);
          HttpURLConnection urlConnection = (HttpURLConnection) httpUrl.openConnection();
          urlConnection.setConnectTimeout(HTTP_CONNECTION_TIMEOUT * 1000);
          urlConnection.setRequestMethod("PUT");
          urlConnection.setDoInput(true); // 读取数据
          urlConnection.setDoOutput(true); // 向服务器写数据

          // 添加请求属性
          if (heads != null && !heads.isEmpty()){
            for (Object key : heads.keySet()){
              urlConnection.setRequestProperty(key.toString(), heads.get(key));
            }
          }
//          urlConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
//          Log.w("AsyncHttp", "data.length = " + data.length);


          // 获得输出流，向服务器输出内容
          OutputStream outputStream = urlConnection.getOutputStream();
          outputStream.write(data, 0, data.length);
          outputStream.flush();
          outputStream.close();


          // 获得服务器响应结果和状态码
          int responseCode = urlConnection.getResponseCode();
          Log.w("AsyncHttp", "sendData->responseCode = " + responseCode);
          if (responseCode == 200 || responseCode == 204 || responseCode == 201){//现在反204
            InputStream inputStream = urlConnection.getInputStream();
            String result = changeInputStream(inputStream, HTTP.UTF_8);
            inputStream.close();
            callback.onComplete(result);
          } else {
            callback.onError(responseCode);
          }

        } catch (MalformedURLException e) {
          e.printStackTrace();
          callback.onComplete(ErrorCode.CODE_EXCEPTION);
        } catch (IOException e) {
          e.printStackTrace();
          callback.onComplete(ErrorCode.CODE_EXCEPTION);
        }
      }
    });

  }

  /*
  * 将一个输入流转换成指定编码的字符串
  * */
  private String changeInputStream(InputStream inputStream, String encode){
    String result = null;
    if (inputStream != null){
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      byte[] buffer = new byte[1024];
      int len = 0;
      try {
        while ((len = inputStream.read(buffer)) != -1){
          byteArrayOutputStream.write(buffer, 0, len);
        }
        result = new String(byteArrayOutputStream.toByteArray(), encode);
        byteArrayOutputStream.close(); // 关流
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return result;
  }

  private void sendFormData(final String requestURL) {
    try {
      URL url = new URL(requestURL);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setConnectTimeout(30 * 1000); //30秒连接超时
      connection.setReadTimeout(30 * 1000);   //30秒读取超时
      connection.setDoInput(true);  //允许文件输入流
      connection.setDoOutput(true); //允许文件输出流
      connection.setUseCaches(false);  //不允许使用缓存
      connection.setRequestMethod("POST");  //请求方式为POST

      connection.setRequestProperty("Charset", "utf-8");  //设置编码为utf-8
      connection.setRequestProperty("connection", "keep-alive"); //保持连接
      connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + BOUNDARY); //Content-Type必须为multipart/form-data








    } catch (Exception e) {
      e.printStackTrace();
    }

  }





    /** * android上传文件到服务器
     * @param file 需要上传的文
     * @return 返回响应的内容
     */
    public void uploadFile(final String url, final File file, final String jsonData, final HttpDataCallBack callBack) {
      // 如果callback为null，则请求结果无法返回
      if (callBack == null){
        return ;
      }

      // 检测网络是否连接
      if (!PhoneUtils.isNetWorkConnected(HuaWeiH2Application.instance)){
        callBack.onError(ErrorCode.CODE_NO_INTERNET); // 无网络连接
        return ;
      }

      final String BOUNDARY = UUID.randomUUID().toString(); //边界标识 随机生成
      final String PREFIX = "--" , LINE_END = "\r\n";

      final String CONTENT_TYPE = "multipart/form-data"; //内容类型

      // 判断数据是否为空
      if (jsonData == null || jsonData.length() <= 0){
        callBack.onError(ErrorCode.CODE_REQUEST_ERROR); // 网络请求错误
        return;
      }


      Executors.newSingleThreadExecutor().execute(new Runnable() {
        @Override
        public void run() {
          try {
            URL url1 = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            conn.setReadTimeout(HTTP_CONNECTION_TIMEOUT*1000);
            conn.setConnectTimeout(HTTP_CONNECTION_TIMEOUT*1000);
            conn.setDoInput(true); //   允许输入流
            conn.setDoOutput(true); //  允许输出流
            conn.setUseCaches(false); //不允许使用缓存
            conn.setRequestMethod("POST"); //请求方式
            conn.setRequestProperty("Charset", CHARSET);
            conn.setRequestProperty("Accept", "application/json");
            //设置编码
            conn.setRequestProperty("Connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);

//      // 获得输出流，向服务器输出内容
//      OutputStream outputStream = conn.getOutputStream();
//      outputStream.write(data, 0, data.length);
//      outputStream.flush();
//      outputStream.close();
            if(file!=null) {
              /** * 当文件不为空，把文件包装并且上传 */
              OutputStream outputSteam=conn.getOutputStream();
              DataOutputStream dos = new DataOutputStream(outputSteam);
              StringBuffer sb = new StringBuffer();
              sb.append(PREFIX);
              sb.append(BOUNDARY);
              sb.append(LINE_END);
              /**
               * 这里重点注意：
               * name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
               * filename是文件的名字，包含后缀名的 比如:abc.png
               */
              sb.append("Content-Disposition: form-data; name=\"file\"; filename=\""+file.getName()+"\""+LINE_END);
              sb.append("Content-Type: image/png; charset="+CHARSET+LINE_END);
              sb.append(LINE_END);
              dos.write(sb.toString().getBytes());
              InputStream is = new FileInputStream(file);
              byte[] bytes = new byte[1024];
              int len = 0;
              while((len=is.read(bytes))!=-1)
              {
                dos.write(bytes, 0, len);
              }
              is.close();
              dos.write(LINE_END.getBytes());

              String jsonString = PREFIX+BOUNDARY+LINE_END+"Content-Disposition: form-data; name=\"text\""+LINE_END+LINE_END+jsonData+LINE_END;
              dos.write(jsonString.getBytes());




              byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes();
              dos.write(end_data);
              Log.i("dos",dos.toString());
              dos.flush();
              dos.close();
              /**
               * 获取响应码 200=成功
               * 当响应成功，获取响应的流
               */
              int res = conn.getResponseCode();
              if(res==200)
              {
                InputStream inputStream = conn.getInputStream();
                String result = changeInputStream(inputStream, HTTP.UTF_8);
                inputStream.close();
                callBack.onComplete(result);
              }
            }
          } catch (MalformedURLException e){
            e.printStackTrace();
            callBack.onError(ErrorCode.CODE_EXCEPTION);
          }
          catch (IOException e){
            e.printStackTrace();
            callBack.onError(ErrorCode.CODE_EXCEPTION);
          }

        }
      });
//      Executors.newSingleThreadExecutor().execute(new Runnable() {
//        @Override
//        public void run() {
////          try {
////            URL url = new URL(URL_UPLOAD_IMAGE_TEST);
////            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
////            conn.setReadTimeout(TIME_OUT);
////            conn.setConnectTimeout(TIME_OUT);
////            conn.setDoInput(true); //   允许输入流
////            conn.setDoOutput(true); //  允许输出流
////            conn.setUseCaches(false); //不允许使用缓存
////            conn.setRequestMethod("POST"); //请求方式
////            conn.setRequestProperty("Charset", CHARSET);
////            conn.setRequestProperty("Accept", "application/json");
////            //设置编码
////            conn.setRequestProperty("connection", "keep-alive");
////            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
////
//////      // 获得输出流，向服务器输出内容
//////      OutputStream outputStream = conn.getOutputStream();
//////      outputStream.write(data, 0, data.length);
//////      outputStream.flush();
//////      outputStream.close();
////            if(file!=null) {
////              /** * 当文件不为空，把文件包装并且上传 */
////              OutputStream outputSteam=conn.getOutputStream();
////              DataOutputStream dos = new DataOutputStream(outputSteam);
////              StringBuffer sb = new StringBuffer();
////              sb.append(PREFIX);
////              sb.append(BOUNDARY);
////              sb.append(LINE_END);
////              /**
////               * 这里重点注意：
////               * name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
////               * filename是文件的名字，包含后缀名的 比如:abc.png
////               */
////              sb.append("Content-Disposition: form-data; name=\"img\"; filename=\""+file.getName()+"\""+LINE_END);
////              sb.append("Content-Type: application/octet-stream; charset="+CHARSET+LINE_END);
////              sb.append(LINE_END);
////              dos.write(sb.toString().getBytes());
////              InputStream is = new FileInputStream(file);
////              byte[] bytes = new byte[1024];
////              int len = 0;
////              while((len=is.read(bytes))!=-1)
////              {
////                dos.write(bytes, 0, len);
////              }
////              is.close();
////              dos.write(LINE_END.getBytes());
////              byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes();
////              dos.write(end_data);
////
////              dos.write(data, 0, data.length);
////
////              dos.flush();
////              dos.close();
////              /**
////               * 获取响应码 200=成功
////               * 当响应成功，获取响应的流
////               */
////              int res = conn.getResponseCode();
////              Log.e(TAG, "response code:"+res);
////              if(res==200)
////              {
////                InputStream inputStream = conn.getInputStream();
////                String result = changeInputStream(inputStream, HTTP.UTF_8);
////                inputStream.close();
////                callBack.onComplete(result);
////              }
////            }
////          } catch (MalformedURLException e){
////            e.printStackTrace();
////          }
////          catch (IOException e){
////            e.printStackTrace();
////          }
////          callBack.onError(ErrorCode.CODE_EXCEPTION);
//        }
//      });
//
    }



}
