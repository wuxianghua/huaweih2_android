package com.palmap.demo.huaweih2.model;

/**
 * Created by eric3 on 2016/10/24.
 */

public class PoiImg {
  int id;
  long cat;

  public PoiImg(int id, long cat) {
    this.id = id;
    this.cat = cat;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public long getCat() {
    return cat;
  }

  public void setCat(long cat) {
    this.cat = cat;
  }
}
