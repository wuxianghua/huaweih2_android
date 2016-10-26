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
    ParkInfo P1 = new ParkInfo("粤B-22222", "H135", "P1", "￥15", "2h");
    ParkInfo P2 = new ParkInfo("粤B-33333", "H148", "P2", "￥15", "4h");
    ParkInfo P3 = new ParkInfo("粤B-44444", "H251", "P3", "￥15", "4h30min");
    ParkInfo P4 = new ParkInfo("粤B-55555", "H199", "P4", "￥15", "10h");
    ParkInfo P5 = new ParkInfo("粤B-66666", "H237", "P5", "￥15", "10h36min");

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

  public List<ParkInfo> getParkInfoList() {
    return parkInfoList;
  }

  public void setParkInfoList(List<ParkInfo> parkInfoList) {
    this.parkInfoList = parkInfoList;
  }
}
