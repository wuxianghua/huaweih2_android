package com.palmap.demo.huaweih2.util;

/**
 * Created by eric3 on 2016/10/21.
 */

public class JsonUtils {

  public static String getCommentsDown(String location,int start,int limits){
      return new String("{\"location\": \""+location+"\",\"start\": "+ start+",\"limits\": "+ limits+"}");
  }
}
