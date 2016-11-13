package com.palmap.demo.huaweih2.model;


import com.palmap.demo.huaweih2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eric3 on 2016/10/31.
 */

public class PoiImgList {


public static List<PoiImg> getPoiImgList(){
   List<PoiImg> poiImgs = new ArrayList<>();
  PoiImg img = new PoiImg(R.drawable.huaweilogo,23999000,"华为H2办公楼");
  PoiImg img1 = new PoiImg(R.drawable.elevator,24091000,"电梯");
  PoiImg img2 = new PoiImg(R.drawable.stairs,24097000,"楼梯");
  PoiImg img3= new PoiImg(R.drawable.tearoom,23040000,"茶水间");
  PoiImg img4 = new PoiImg(R.drawable.dtoilet,23059000,"残障洗手间");
  PoiImg img5 = new PoiImg(R.drawable.mtoilet,23024000,"男洗手间");
  PoiImg img18 = new PoiImg(R.drawable.toilet,23063000,"洗手间");
  PoiImg img6 = new PoiImg(R.drawable.wtoilet,23025000,"女洗手间");
  PoiImg img7 = new PoiImg(R.drawable.service,23018000,"服务台");
  PoiImg img8 = new PoiImg(R.drawable.lifeservice,15000000,"生活服务");
  PoiImg img9 = new PoiImg(R.drawable.park,17004000,"停车场");
  PoiImg img10 = new PoiImg(R.drawable.park,22001000,"停车位");
  PoiImg img11 = new PoiImg(R.drawable.laboratory,23027000,"实验室");
  PoiImg img12 = new PoiImg(R.drawable.meeting,23001000,"会议室");
  PoiImg img13 = new PoiImg(R.drawable.office,23027000,"办公室");
  PoiImg img14 = new PoiImg(R.drawable.office,24002000,"办公室");
  PoiImg img15 = new PoiImg(R.drawable.office,24005000,"办公室");
  PoiImg img16 = new PoiImg(R.drawable.office,24007000,"办公室");
  PoiImg img17 = new PoiImg(R.drawable.office,24008000,"办公室");
  poiImgs.add(img);
  poiImgs.add(img1);
  poiImgs.add(img2);
  poiImgs.add(img3);
  poiImgs.add(img4);
  poiImgs.add(img5);
  poiImgs.add(img6);
  poiImgs.add(img7);
  poiImgs.add(img8);
  poiImgs.add(img9);
  poiImgs.add(img10);
  poiImgs.add(img11);
  poiImgs.add(img12);
  poiImgs.add(img13);
  poiImgs.add(img14);
  poiImgs.add(img15);
  poiImgs.add(img16);
  poiImgs.add(img17);
  poiImgs.add(img18);

  return poiImgs;
}

  public static String getName(long cat){
    for (PoiImg p:getPoiImgList()){
      if (cat==p.getCat())
        return p.getDisplay();
    }
    return "";
  }

}
