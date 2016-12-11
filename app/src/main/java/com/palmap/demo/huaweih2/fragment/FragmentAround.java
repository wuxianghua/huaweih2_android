package com.palmap.demo.huaweih2.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.palmap.demo.huaweih2.ActivityWeb;
import com.palmap.demo.huaweih2.R;
import com.palmap.demo.huaweih2.adapter.ShopListAdapter;
import com.palmap.demo.huaweih2.model.Shop;
import com.palmap.demo.huaweih2.model.ShopList;

/**
 * Created by eric3 on 2016/10/8.
 */

public class FragmentAround extends BaseFragment {
  ListView shopListView;

  @Override
  public View onCreateView(LayoutInflater inflater,
                           @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    View fragmentView = inflater.inflate(R.layout.around, container, false);
    shopListView = (ListView) fragmentView.findViewById(R.id.shop_list_view);
    ShopList shopList = new ShopList();
    ShopListAdapter shopListAdapter = new ShopListAdapter(getActivity(), shopList.getShopList(), new ShopListAdapter.OnItemClickListener() {
      @Override
      public void onClicked(Shop shop) {
        if (shop.getUrl()==null)
          return;

        Intent intent = new Intent(getActivity(), ActivityWeb.class);
        intent.putExtra(ActivityWeb.URL,shop.getUrl());
        intent.putExtra(ActivityWeb.TITLE,shop.getName());
        startActivity(intent);

//调用系统浏览器
//        final Uri uri = Uri.parse(shop.getUrl());
//        final Intent it = new Intent(Intent.ACTION_VIEW, uri);
//        startActivity(it);
      }
    });
    shopListView.setAdapter(shopListAdapter);
//    getMainActivity().titleBar.setOnTitleClickListener(new TitleBar.OnTitleClickListener() {
//      @Override
//      public void onLeft() {
//        getMainActivity().showFragmentMap();
//      }
//
//      @Override
//      public void onRight() {
//
//      }
//    });
    return fragmentView;
  }

}
