package com.palmap.demo.huaweih2.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.palmap.demo.huaweih2.HuaWeiH2Application;
import com.palmap.demo.huaweih2.json.PictureModel;
import com.palmap.demo.huaweih2.json.PictureModelSum;
import com.palmap.demo.huaweih2.json.PoiInfo;

import java.util.List;

/**
 * Created by eric3 on 2016/10/21.
 */

public class JsonUtils {

  public static String getCommentsDown(String location,int start,int limits){
      return new String("{\"location\": \""+location+"\",\"start\": "+ start+",\"limits\": "+ limits+"}");
  }

  public static String getPostComment(String location,String text){
    return "{\"location\": \""+location+"\",\"comment\": \""+text+"\",\"userId\": \""+ HuaWeiH2Application.userIp+"\"}";
  }

  public static String getShakeString(String lo){
    return "{\"location\": \""+lo+"\",\"start\":0,\"limits\":1}";
  }
  public static String getDownloadImg(String location){
    return  "{\"location\": \""+location+"\",\"start\":0,\"limits\":1000}";
  }

  public static int getZanSum(Object content){
    if (content==null)
      return 0;

    JSONArray jsonArray = JSONArray.parseArray(content.toString());
    if (jsonArray.size()<=0)
      return 0;

    JSONObject jo = jsonArray.getJSONObject(0);
    int i = jo.getInteger("zanSum");
    return i;
  }

  public static PoiInfo getPoiInfo(Object content){
    if (content==null)
      return null;

    List<PoiInfo> js = JSONArray.parseArray(content.toString(),PoiInfo.class);
    if (js.size()<=0)
      return null;

    PoiInfo poiInfo = js.get(0);

    return poiInfo;
  }

  public static List<PictureModel> getPictureModel(Object content){
    if (content==null)
      return null;

    List<PictureModel> js = JSONArray.parseArray(content.toString(),PictureModel.class);
    if (js.size()<=0)
      return null;



    return js;
  }
  /*
  获取图片数量
   */
  public static List<PictureModelSum> getPictureModelSum(Object content){
    if (content==null)
      return null;

    List<PictureModelSum> js = JSONArray.parseArray(content.toString(),PictureModelSum.class);
    if (js.size()<=0)
      return null;



    return js;
  }
}
