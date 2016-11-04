package com.palmap.demo.huaweih2.http;


import com.alibaba.fastjson.JSON;
import com.palmap.demo.huaweih2.json.IpList;
import com.palmap.demo.huaweih2.other.Constant;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
    ipList.setUserIP("****0ddf");

    String jsonString = JSON.toJSONString(ipList);
//    jsonString = "[" +jsonString + "]";
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

  /*
* 接口：上传评论
* */
  public void postFormDATA(File file, String jsonData, HttpDataCallBack callBack){

    mDataProvider.postFormDataProvider(URL_UPLOAD_IMAGE,file,jsonData==null?null:jsonData,callBack);
  }
}
