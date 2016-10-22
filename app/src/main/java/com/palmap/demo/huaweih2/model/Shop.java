package com.palmap.demo.huaweih2.model;

/**
 * Created by eric3 on 2016/10/18.
 */

public class Shop {
  String name;//名称
  String type;//	类型
  String location;// 位置
  String url;// URL
  int distance;// 距离

  public Shop(String name, String type, String location, String url, int distance) {
    this.name = name;
    this.type = type;
    this.location = location;
    this.url = url;
    this.distance = distance;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public int getDistance() {
    return distance;
  }

  public void setDistance(int distance) {
    this.distance = distance;
  }
}
