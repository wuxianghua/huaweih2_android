package com.palmap.demo.huaweih2.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eric3 on 2016/10/18.
 */

public class ShopList {
  List<Shop> shopList;

  public ShopList(){
    Shop shop1 = new Shop("永和大王","中餐","坂田街道办五和南路82号","http://www.dianping.com/shop/4512378",672);
    Shop shop2 = new Shop("千味涮","中餐","坂田街道办五和南路82号","http://www.dianping.com/shop/4512378",768);
    Shop shop3 = new Shop("麦当劳","快餐"," 坂田五和南路82号阳光湾畔商场1楼","http://www.dianping.com/shop/2929849",597);
    Shop shop4 = new Shop("中影博亚影城(坂田店)","电影院","坂田街道五和大道118号和成世纪3F","http://www.dianping.com/shop/21244216",1015);
    Shop shop5 = new Shop("松哥油焖大虾","中餐","华为百草园对面马蹄山综合楼B栋1楼","http://www.dianping.com/shop/24005276",462);
    Shop shop6 = new Shop("华莱士","快餐","龙岗区坂田街道长发西路21-4号铺位","http://www.dianping.com/shop/27013286",789);
    Shop shop7 = new Shop("illy新天地咖啡吧","咖啡店","龙岗区坂田华为基地培训中心员工广场","http://www.dianping.com/shop/5604581",165);
    Shop shop8 = new Shop("四云奶盖贡茶","奶茶店","龙岗区坂田吉华路696号","http://www.dianping.com/shop/67635952",86);
    Shop shop9 = new Shop("韩客莱","中餐","龙岗区民治民康路民治华南物流站","http://www.dianping.com/shop/68938102",176);
    Shop shop10 = new Shop("7-11便利店","便利店","龙岗区坂田街道（华为H2基地）","",182);

    Shop shop11 = new Shop("电影院","","龙岗区坂田街道（华为H2基地）","",183);
    Shop shop22 = new Shop("便利店","","龙岗区坂田街道（华为H2基地）","",189);
    Shop shop33 = new Shop("美食","","龙岗区坂田街道（华为H2基地）","",213);

    shopList = new ArrayList<>();
    shopList.add(shop11);
    shopList.add(shop4);
    shopList.add(shop22);
    shopList.add(shop10);
    shopList.add(shop33);
    shopList.add(shop9);
    shopList.add(shop8);
    shopList.add(shop7);
    shopList.add(shop2);
    shopList.add(shop5);
    shopList.add(shop6);
    shopList.add(shop3);
    shopList.add(shop1);




  }

  public List<Shop> getShopList() {
    return shopList;
  }

  public void setShopList(List<Shop> shopList) {
    this.shopList = shopList;
  }
}
