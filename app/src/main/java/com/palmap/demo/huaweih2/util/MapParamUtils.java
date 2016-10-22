package com.palmap.demo.huaweih2.util;

import android.text.TextUtils;

import com.palmaplus.nagrand.data.DataElement;
import com.palmaplus.nagrand.data.Param;

/**
 * Created by wtm on 2016/6
 */

public class MapParamUtils {
  private static final Param<String> LOCATION_TYPE = new Param<>("location_type", String.class);
  private static final Param<Long> ID = new Param<>("id", Long.class);
  private static final Param<Long> OBJECT_ID = new Param<>("object_id", Long.class);
  private static final Param<Long> POI = new Param<>("poi", Long.class);
  private static final Param<String> NAME = new Param<>("name", String.class);
  private static final Param<String> DISPLAY = new Param<>("display", String.class);
  private static final Param<Long> categoryId = new Param<>("category", Long.class);
  private static final Param<String> address = new Param<>("address", String.class);
  private static final Param<String> english_name = new Param<>("englishName", String.class);
  private static final Param<String> english_name2 = new Param<>("english_name", String.class);
  //englishName
  //english_name
  public static long getId(DataElement dataElement) {
    return ID.get(dataElement);
  }
  public static long getObjectId(DataElement dataElement) {
    return OBJECT_ID.get(dataElement);
  }
  public static long getPOI(DataElement dataElement){
    return POI.get(dataElement);
  }
  public static long getCategoryId(DataElement dataElement){return categoryId.get(dataElement);}
  public static String getName(DataElement dataElement){
//    if (Config.language == Config.Language.ENGLISH) {
//      String name =  getEnglishName(dataElement);
//      if (!TextUtils.isEmpty(name)) {
//        return name;
//      } else {
////                return "EnglishName is null";
//        return "";
//      }
////            return getEnglishName(dataElement);
//    }
    return NAME.get(dataElement);
  }
  public static String getDisplay(DataElement dataElement){
    return DISPLAY.get(dataElement);
  }
  public static String getAddress(DataElement dataElement) {
    return address.get(dataElement);
  }
  public static String getEnglishName(DataElement dataElement) {
    String result = english_name.get(dataElement);
    if (TextUtils.isEmpty(result)) return english_name2.get(dataElement);
    return result;
  }
}
