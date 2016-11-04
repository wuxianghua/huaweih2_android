package com.palmap.demo.huaweih2.json;

/**
 * Created by eric3 on 2016/10/21.
 */

public class CommentDown {
  String location;
  String comment;
  String userId;//ip地址
  long comTime;
  int id;

  public CommentDown(){

  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public long getComTime() {
    return comTime;
  }

  public void setComTime(long comTime) {
    this.comTime = comTime;
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
