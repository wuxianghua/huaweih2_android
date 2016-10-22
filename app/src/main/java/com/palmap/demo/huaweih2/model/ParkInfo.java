package com.palmap.demo.huaweih2.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by eric3 on 2016/10/19.
 */

public class ParkInfo implements Parcelable {
  String carNum;//车牌号
  String carPosition;//停车位
  String hour;//停车时长
  String money;//收费
  String area;//对应位置编号

  public ParkInfo(String carNum, String carPosition, String area, String money, String hour) {
    this.carNum = carNum;
    this.carPosition = carPosition;
    this.area = area;
    this.money = money;
    this.hour = hour;
  }



  public static final Creator<ParkInfo> CREATOR = new Creator<ParkInfo>() {
    @Override
    public ParkInfo createFromParcel(Parcel in) {
      return new ParkInfo(in);
    }

    @Override
    public ParkInfo[] newArray(int size) {
      return new ParkInfo[size];
    }
  };

  public String getCarNum() {
    return carNum;
  }

  public void setCarNum(String carNum) {
    this.carNum = carNum;
  }

  public String getArea() {
    return area;
  }

  public void setArea(String area) {
    this.area = area;
  }

  public String getMoney() {
    return money;
  }

  public void setMoney(String money) {
    this.money = money;
  }

  public String getHour() {
    return hour;
  }

  public void setHour(String hour) {
    this.hour = hour;
  }

  public String getCarPosition() {
    return carPosition;
  }

  public void setCarPosition(String carPosition) {
    this.carPosition = carPosition;
  }


  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(getCarNum());
    dest.writeString(getCarPosition());
    dest.writeString(getArea());
    dest.writeString(getHour());
    dest.writeString(getMoney());
//    Bundle bundle = new Bundle();
//    bundle.putString("carNum", getCarNum());
//    bundle.putString("carPos", getCarPosition());
//    bundle.putString("carArea", getArea());
//    bundle.putString("carTime", getHour());
//    bundle.putString("carMoney", getMoney());
//    dest.writeBundle(bundle);
  }
  protected ParkInfo(Parcel in) {
    carNum = in.readString();
    carPosition = in.readString();
    area = in.readString();
    hour = in.readString();
    money = in.readString();

  }
}
