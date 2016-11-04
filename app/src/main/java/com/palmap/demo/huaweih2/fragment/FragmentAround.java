package com.palmap.demo.huaweih2.fragment;


import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.palmap.demo.huaweih2.ActivityWeb;
import com.palmap.demo.huaweih2.MainActivity;
import com.palmap.demo.huaweih2.R;
import com.palmap.demo.huaweih2.adapter.ShopListAdapter;
import com.palmap.demo.huaweih2.model.Shop;
import com.palmap.demo.huaweih2.model.ShopList;
import com.palmap.demo.huaweih2.view.TitleBar;

/**
 * Created by eric3 on 2016/10/8.
 */

public class FragmentAround extends ListFragment {
//  ListView shopListView;
  @Override
  public void onCreate(Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater,
                           @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    View fragmentView = inflater.inflate(R.layout.around, container, false);
//    shopListView = (ListView) fragmentView.findViewById(R.id.list);
    ShopList shopList = new ShopList();
    ShopListAdapter shopListAdapter = new ShopListAdapter(getActivity(), shopList.getShopList(), new ShopListAdapter.OnItemClickListener() {
      @Override
      public void onClicked(Shop shop) {
        if (shop.getUrl()==null)
          return;

        Intent intent = new Intent(getActivity(), ActivityWeb.class);
        intent.putExtra("url",shop.getUrl());
        startActivity(intent);

//调用系统浏览器
//        final Uri uri = Uri.parse(shop.getUrl());
//        final Intent it = new Intent(Intent.ACTION_VIEW, uri);
//        startActivity(it);
      }
    });
    this.setListAdapter(shopListAdapter);
    getMainActivity().titleBar.setOnTitleClickListener(new TitleBar.OnTitleClickListener() {
      @Override
      public void onLeft() {
        getMainActivity().showFragmentMap();
      }

      @Override
      public void onRight() {

      }
    });
    return fragmentView;
  }

  private MainActivity getMainActivity() {
    return (MainActivity)getActivity();
  }
}
