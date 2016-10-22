package com.palmap.demo.huaweih2.json;

/**
 * Created by eric3 on 2016/10/17.
 * 评论上传
 */

public class CommentUp {
  String location;
  String comment;
  String userId;//ip地址
  public CommentUp(){

  }
  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

}
