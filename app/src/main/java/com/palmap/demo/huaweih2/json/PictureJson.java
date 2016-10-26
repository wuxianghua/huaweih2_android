package com.palmap.demo.huaweih2.json;

/**
 * Created by eric3 on 2016/10/18.
 * 照片和评论报文
 */

public class PictureJson {
  String location;
//  byte[] photo;
  String appendix;

  public PictureJson() {
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

//  public byte[] getPhoto() {
//    return photo;
//  }

//  public void setPhoto(byte[] photo) {
//    this.photo = photo;
//  }

  public String getAppendix() {
    return appendix;
  }

  public void setAppendix(String appendix) {
    this.appendix = appendix;
  }
}
