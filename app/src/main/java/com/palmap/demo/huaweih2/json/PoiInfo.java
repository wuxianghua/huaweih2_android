package com.palmap.demo.huaweih2.json;

import java.io.Serializable;

/**
 * Created by eric3 on 2016/10/23.
 * 摇一摇
 */

public class PoiInfo implements Serializable{
  String image;
//  String title;
  String text;
  String title;

  public PoiInfo() {
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
