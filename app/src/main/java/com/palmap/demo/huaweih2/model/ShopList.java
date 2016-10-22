package com.palmap.demo.huaweih2.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eric3 on 2016/10/18.
 */

public class ShopList {
  List<Shop> shopList;

  public ShopList(){
    Shop shop1 = new Shop("常回家客家菜","中餐","龙岗区坂田居里夫人大道与隆平路交汇处（新天下侧门，神州电脑旁）","http://sz.meituan.com/shop/83457534?mtt=1.shops%2Fdefault.0.0.iu58rola",800);
    Shop shop2 = new Shop("汕头福合埕牛肉丸","中餐","龙岗区坂田街道黄金山坂兴路26号","http://sz.meituan.com/shop/4591086?acm=UmyulwbVm_koyls_bcmnils.0.0&mtt=1.shops%2Fdefault.0.0.iu59cuqj&cks=2646",800);
    Shop shop3 = new Shop("湘味羊骨","中餐","龙岗区坂田街道黄金山十三巷3号","https://www.nuomi.com/shop/54296461",600);
    Shop shop4 = new Shop("毛爹私房菜","中餐","龙岗区坂田万科城C区119号肯德基旁边(近KFC)","https://www.nuomi.com/shop/1702608",990);
    Shop shop5 = new Shop("LESSA主题餐厅","西餐","龙岗区坂田贝尔路2号绅士酒店二楼（华为基地H区南门）","http://sz.meituan.com/shop/69383546?acm=UmyulwbW18179831127834977209.41125777.1.41125777.1&mtt=1.deal%2Fdefault.0.0.iu59h9om&cks=54230",500);
    Shop shop6 = new Shop("华莱士","快餐","龙岗区坂田街道雪岗北路1152号（万盛百货一楼）","http://sz.meituan.com/shop/91071206?acm=UmyulwbVm_koyls_bcmnils.0.0W52013986944396515.36541928.1.36541928.1&mtt=1.deal%2Fdefault.0.0.iu59hwyu&cks=46491",700);
    Shop shop7 = new Shop("illy咖啡","咖啡店","龙岗区坂田华为基地培训中心员工广场","http://www.dianping.com/shop/5604581",200);
    Shop shop8 = new Shop("四云奶盖贡茶","奶茶店","龙岗区坂田吉华路696号","http://www.dianping.com/shop/67635952",100);
    Shop shop9 = new Shop("7-11便利店","便利店","龙岗区坂田街道（华为H2基地）","",180);

    shopList = new ArrayList<>();
    shopList.add(shop1);
    shopList.add(shop2);
    shopList.add(shop3);
    shopList.add(shop4);
    shopList.add(shop5);
    shopList.add(shop6);
    shopList.add(shop7);
    shopList.add(shop8);
    shopList.add(shop9);

  }

  public List<Shop> getShopList() {
    return shopList;
  }

  public void setShopList(List<Shop> shopList) {
    this.shopList = shopList;
  }
}
