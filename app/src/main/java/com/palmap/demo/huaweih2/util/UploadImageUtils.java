package com.palmap.demo.huaweih2.util;

import android.util.Log;

import com.palmap.demo.huaweih2.http.ErrorCode;
import com.palmap.demo.huaweih2.http.HttpDataCallBack;

import org.apache.http.protocol.HTTP;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

/**
 * Created by eric3 on 2016/10/21.
 */

public class UploadImageUtils {
  private static final String TAG = "uploadFile";
  private static final int TIME_OUT = 10*10000000; //超时时间
  private static final String CHARSET = "utf-8"; //设置编码
  /** * android上传文件到服务器
   * @param file 需要上传的文件
   * @param RequestURL 请求的rul
   * @return 返回响应的内容
   */
  public static void uploadFile(File file, String RequestURL, String jsonData, HttpDataCallBack callBack) {
    String BOUNDARY = UUID.randomUUID().toString(); //边界标识 随机生成
    String PREFIX = "--" , LINE_END = "\r\n";

    String CONTENT_TYPE = "multipart/form-data"; //内容类型

    // 判断数据是否为空
    if (jsonData == null || jsonData.length() <= 0){
      callBack.onError(ErrorCode.CODE_REQUEST_ERROR); // 网络请求错误
      return;
    }
    byte[] data = jsonData.getBytes();

    try {
      URL url = new URL(RequestURL);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setReadTimeout(TIME_OUT);
      conn.setConnectTimeout(TIME_OUT);
      conn.setDoInput(true); //允许输入流
      conn.setDoOutput(true); //允许输出流
      conn.setUseCaches(false); //不允许使用缓存
      conn.setRequestMethod("POST"); //请求方式
      conn.setRequestProperty("Charset", CHARSET);
      //设置编码
      conn.setRequestProperty("connection", "keep-alive");
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
        sb.append("Content-Disposition: form-data; name=\"img\"; filename=\""+file.getName()+"\""+LINE_END);
        sb.append("Content-Type: application/octet-stream; charset="+CHARSET+LINE_END);
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
        byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes();
        dos.write(end_data);

        dos.write(data, 0, data.length);

        dos.flush();
        dos.close();
        /**
         * 获取响应码 200=成功
         * 当响应成功，获取响应的流
         */
        int res = conn.getResponseCode();
        Log.e(TAG, "response code:"+res);
        if(res==200)
        {
          InputStream inputStream = conn.getInputStream();
          String result = changeInputStream(inputStream, HTTP.UTF_8);
          inputStream.close();
          callBack.onComplete(result);
        }
      }
    } catch (MalformedURLException e)
    { e.printStackTrace(); }
    catch (IOException e)
    { e.printStackTrace(); }
    callBack.onError(ErrorCode.CODE_EXCEPTION);
  }

  /*
  * 将一个输入流转换成指定编码的字符串
  * */
  private static String changeInputStream(InputStream inputStream, String encode){
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
}