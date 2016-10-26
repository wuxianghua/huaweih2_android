package com.palmap.demo.huaweih2.http;


import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.palmap.demo.huaweih2.json.IpList;
import com.palmap.demo.huaweih2.other.Constant;

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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by eric3 on 2016/10/13.
 */

public class DataProviderCenter {
  // 服务器地址
  private static final String URL_SERVER_TEST = "http://10.0.10.227:8090/ICSData/";
  private static final String URL_SERVER = "http://106.75.7.212:8090/ICSData/";

  private static final String URL_LOCATE =URL_SERVER+"queryLocation"; // 外网无锡海岸城

  private static final String URL_LOCATE_TEST = URL_SERVER_TEST+"queryLocation"; // 无锡海岸城定位
  private static final String URL_UPLOAD_IMAGE_TEST = URL_SERVER_TEST+"insertPhoto"; // 拍照上传
  private static final String URL_DOWNLOAD_IMAGE_TEST = URL_SERVER_TEST+"queryPhoto"; // 照片查询

  private static final String URL_DOWNLOAD_COMMENT_TEST = URL_SERVER_TEST+"queryComment"; // 评论查询

  private static final String URL_DOWNLOAD_CAR_TEST = URL_SERVER_TEST+"queryParkInfo"; // 车牌/车位信息表
  private static final String URL_DOWNLOAD_PUSHINFO_TEST = URL_SERVER_TEST+"queryPushMsg"; // 消息推送信息表
  private static final String URL_DOWNLOAD_POIINFO_TEST = URL_SERVER_TEST+"queryPoiInfo"; // （poi）详细信息
  private static final String URL_DOWNLOAD_TEAMINFO_TEST = URL_SERVER_TEST+"queryTeamInfo"; // 团队信息查询
  private static final String URL_DOWNLOAD_COMMENT = URL_SERVER+"queryComment"; // 评论查询
  private static final String URL_DOWNLOAD_IMAGE = URL_SERVER+"queryPhoto"; // 照片查询
  private static final String URL_UPLOAD_IMAGE = URL_SERVER+"insertPhoto"; // 拍照上传
  private static final String URL_UPLOAD_ZAN = URL_SERVER+"updDZInfo"; // 赞
  private static final String URL_UPLOAD_ZAN_TEST = URL_SERVER_TEST+"updDZInfo"; // 赞
  private static final String URL_DOWNLOAD_ZAN = URL_SERVER+"queryDZInfo"; // 赞
  private static final String URL_DOWNLOAD_ZAN_TEST = URL_SERVER_TEST+"queryDZInfo"; // 赞
  private static final String URL_DOWNLOAD_PICNUM_TEST = URL_SERVER_TEST+"queryPhotoSumByLoc"; // 图片总数
  private static final String URL_DOWNLOAD_PICNUM = URL_SERVER+"queryPhotoSumByLoc"; // 图片总数
  private static final String URL_DOWNLOAD_SHAKE_TEST = URL_SERVER_TEST+"queryWebChat"; // 摇一摇
  private static final String URL_DOWNLOAD_SHAKE = URL_SERVER+"queryWebChat"; // 摇一摇
  private static final String URL_UPLOAD_COMMENT_TEST = URL_SERVER_TEST+"insertCom"; // 评论上传
  private static final String URL_UPLOAD_COMMENT = URL_SERVER+"insertCom"; // 评论上传


  private static DataProviderCenter instance = null;
  private DataProvider mDataProvider;

  public DataProviderCenter(){
    mDataProvider = new DataProvider();
  }

  /*
  * 获取单实例对象
  * */
  public static DataProviderCenter getInstance(){
    if (instance == null){
      instance = new DataProviderCenter();
    }
    return instance;
  }


  /*
  * 接口：获取定位点
  * */
  public void getPosition(String IP,HttpDataCallBack callBack){
    Map<String, String> heads = new HashMap<String, String>();
    heads.put("Accept", "application/json");
    heads.put("Content-Type", "application/json");

    IpList ipList = new IpList();
    ipList.setUserIP("0ad1ce42");

    String jsonString = JSON.toJSONString(ipList);
    jsonString = "[" +jsonString + "]";
    //URL_LOCATE
//    String url = "http://location.palmap.cn:28099/comet/pos?idType=ip&idData=10.209.206.66";
    if (Constant.openLocateTest)
      mDataProvider.postDataProvider("", heads,jsonString==null?null:jsonString.getBytes(),callBack);
    else
      mDataProvider.postDataProvider(URL_LOCATE, heads,jsonString==null?null:jsonString.getBytes(),callBack);
//    mDataProvider.getProvider(url,heads,callBack);
  }

  /*
 * 接口：上传照片和评论
 * */
  public void postImage(String jsonData,HttpDataCallBack callBack){
    Map<String, String> heads = new HashMap<String, String>();
    heads.put("Accept", "application/json");
    heads.put("Content-Type", "application/json");

//    jsonData = "[" +jsonData + "]";
    mDataProvider.postDataProvider(URL_UPLOAD_IMAGE, heads,jsonData==null?null:jsonData.getBytes(),callBack);
  }

  /*
* 接口：上传赞
* */
  public void postZan(String jsonData,HttpDataCallBack callBack){
    Map<String, String> heads = new HashMap<String, String>();
    heads.put("Accept", "application/json");
    heads.put("Content-Type", "application/json");

//    jsonData = "[" +jsonData + "]";
    mDataProvider.postDataProvider(URL_UPLOAD_ZAN, heads,jsonData==null?null:jsonData.getBytes(),callBack);
  }
  /*
* 接口：下载赞
* */
  public void downloadZan(String jsonData,HttpDataCallBack callBack){
    Map<String, String> heads = new HashMap<String, String>();
    heads.put("Accept", "application/json");
    heads.put("Content-Type", "application/json");

//    jsonData = "[" +jsonData + "]";
    mDataProvider.postDataProvider(URL_DOWNLOAD_ZAN, heads,jsonData==null?null:jsonData.getBytes(),callBack);
  }

  /*
 * 接口：下载照片和评论
 * */
  public void getImage(String jsonData,HttpDataCallBack callBack){
    Map<String, String> heads = new HashMap<String, String>();
    heads.put("Accept", "application/json");
    heads.put("Content-Type", "application/json");

    mDataProvider.postDataProvider(URL_DOWNLOAD_IMAGE,heads,jsonData==null?null:jsonData.getBytes(),callBack);
  }

  /*
* 接口：下载评论
* */
  public void getComments(String jsonData,HttpDataCallBack callBack){
    Map<String, String> heads = new HashMap<String, String>();
    heads.put("Accept", "application/json");
    heads.put("Content-Type", "application/json");

    mDataProvider.postDataProvider(URL_DOWNLOAD_COMMENT,heads,jsonData==null?null:jsonData.getBytes(),callBack);
  }

  /*
* 接口：下载照片url评论
* */
  public void getPictures(String jsonData,HttpDataCallBack callBack){
    Map<String, String> heads = new HashMap<String, String>();
    heads.put("Accept", "application/json");
    heads.put("Content-Type", "application/json");

    mDataProvider.postDataProvider(URL_DOWNLOAD_IMAGE,heads,jsonData==null?null:jsonData.getBytes(),callBack);
  }

  /*
* 接口：下载评论
* */
  public void getShake(String jsonData,HttpDataCallBack callBack){
    Map<String, String> heads = new HashMap<String, String>();
    heads.put("Accept", "application/json");
    heads.put("Content-Type", "application/json");

    mDataProvider.postDataProvider(URL_DOWNLOAD_SHAKE,heads,jsonData==null?null:jsonData.getBytes(),callBack);
  }

  /*
* 接口：下载图片总数
* */
  public void getAllLocationPicNum(HttpDataCallBack callBack){
    Map<String, String> heads = new HashMap<String, String>();
    heads.put("Accept", "application/json");
    heads.put("Content-Type", "application/json");

    mDataProvider.getProvider(URL_DOWNLOAD_PICNUM,heads,callBack);
  }
  /*
* 接口：上传评论
* */
  public void postComments(String jsonData,HttpDataCallBack callBack){
    Map<String, String> heads = new HashMap<String, String>();
    heads.put("Accept", "application/json");
    heads.put("Content-Type", "application/json");

    mDataProvider.postDataProvider(URL_UPLOAD_COMMENT,heads,jsonData==null?null:jsonData.getBytes(),callBack);
  }

  public static class UploadImageUtils {
    private static final String TAG = "uploadFile";
    private static final int TIME_OUT = 10*10000000; //超时时间
    private static final String CHARSET = "utf-8"; //设置编码
    /** * android上传文件到服务器
     * @param file 需要上传的文
     * @return 返回响应的内容
     */
    public static void uploadFile(final File file, final String jsonData, final HttpDataCallBack callBack) {

      final String BOUNDARY = UUID.randomUUID().toString(); //边界标识 随机生成
      final String PREFIX = "--" , LINE_END = "\r\n";

      final String CONTENT_TYPE = "multipart/form-data"; //内容类型

      // 判断数据是否为空
      if (jsonData == null || jsonData.length() <= 0){
        callBack.onError(ErrorCode.CODE_REQUEST_ERROR); // 网络请求错误
        return;
      }
      final byte[] jsonDataBytes = jsonData.getBytes();


      Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
          try {
            URL url = new URL(URL_UPLOAD_IMAGE);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
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
              Log.e(TAG, "response code:"+res);
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
          }
          catch (IOException e){
            e.printStackTrace();
          }
          callBack.onError(ErrorCode.CODE_EXCEPTION);
        }
      });
      thread.start();
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
}
