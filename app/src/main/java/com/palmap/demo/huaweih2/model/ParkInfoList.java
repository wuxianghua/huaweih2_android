package com.palmap.demo.huaweih2.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eric3 on 2016/10/19.
 */

public class ParkInfoList {
  private List<ParkInfo> parkInfoList;

  public ParkInfoList() {
    parkInfoList = new ArrayList<>();
    ParkInfo P1 = new ParkInfo("粤B·22376", "H152", "P1", "￥15", "2h");
    ParkInfo P2 = new ParkInfo("粤B·32398", "H152", "P2", "￥15", "4h");
    ParkInfo P3 = new ParkInfo("粤B·810HP", "H152", "P3", "￥15", "4h30min");
    ParkInfo P4 = new ParkInfo("粤B·570HS", "H152", "P4", "￥15", "10h");
    ParkInfo P5 = new ParkInfo("粤B·646FF", "H152", "P5", "￥15", "10h36min");

    parkInfoList.add(P1);
    parkInfoList.add(P2);
    parkInfoList.add(P3);
    parkInfoList.add(P4);
    parkInfoList.add(P5);
  }

  public ParkInfo getParkInfoByCarNum(String carNum) {
    for (ParkInfo parkInfo : parkInfoList) {
      if (parkInfo.getCarNum().equals(carNum))
        return parkInfo;
    }

    return null;
  }

  public List<ParkInfo> getParkInfoListByKey(String key) {
    List<ParkInfo> parkInfos = new ArrayList<>();
    for (ParkInfo parkInfo : parkInfoList) {
      if (parkInfo.getCarNum().contains(key))
        parkInfos.add(parkInfo);
    }

    return parkInfos;
  }
  public List<ParkInfo> getParkInfoList() {
    return parkInfoList;
  }

  public void setParkInfoList(List<ParkInfo> parkInfoList) {
    this.parkInfoList = parkInfoList;
  }
}
