package com.palmap.demo.huaweih2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.palmap.demo.huaweih2.ActivityPay;
import com.palmap.demo.huaweih2.R;
import com.palmap.demo.huaweih2.model.ParkInfo;
import com.palmap.demo.huaweih2.model.ParkInfoList;
import com.palmap.demo.huaweih2.other.Constant;
import com.palmap.demo.huaweih2.util.DialogUtils;
import com.palmap.demo.huaweih2.util.KeyBoardUtils;
import com.palmap.demo.huaweih2.view.TitleBar;

import static com.palmap.demo.huaweih2.fragment.FragmentMap.isSearchCar;

/**
 * Created by eric3 on 2016/10/8.
 */
public class FragmentPark extends BaseFragment {
  public static boolean hasPay;//
   EditText carNum;
  ImageView btnSearch;
  ParkInfoList parkInfoList;
  LinearLayout mainlayout;

  @Override
  public View onCreateView(LayoutInflater inflater,
                           @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    View fragmentView = inflater.inflate(R.layout.park, container, false);

    parkInfoList = new ParkInfoList();
    carNum = (EditText) fragmentView.findViewById(R.id.tv_car_num);
    btnSearch = (ImageView)fragmentView.findViewById(R.id.btn_search);
    mainlayout = (LinearLayout)fragmentView.findViewById(R.id.park_main);

    return fragmentView;
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    btnSearch.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ParkInfo t=parkInfoList.getParkInfoByCarNum("粤B-"+carNum.getText());
        if (t==null){
          DialogUtils.showShortToast("没有车牌号为"+"粤B-"+carNum.getText()+"的停车信息");
          return;
        }
        DialogUtils.showLongToast("正在为您跳转到车牌号为"+"粤B-"+carNum.getText()+"的停车位，请稍后...");
        KeyBoardUtils.closeKeybord(carNum,getActivity());
//        getMainActivity().hideTabMenu();
        showCarOnMap(t);
      }
    });
    getMainActivity().titleBar.setOnTitleClickListener(new TitleBar.OnTitleClickListener() {
      @Override
      public void onLeft() {
        getMainActivity().showFragmentMap();
      }

      @Override
      public void onRight() {

      }
    });
  }

  private void showCarOnMap(final ParkInfo p) {
    mainlayout.setVisibility(View.GONE);
    getMainActivity().showCarOnMap(p);
    getMainActivity().titleBar.show(null,"找车","缴费");
    getMainActivity().titleBar.setOnTitleClickListener(new TitleBar.OnTitleClickListener() {
      @Override
      public void onLeft() {
        isSearchCar = false;
        getMainActivity().showFragmentPark();
        mainlayout.setVisibility(View.VISIBLE);
      }

      @Override
      public void onRight() {
//        getMainActivity().getMapView().setVisibility(View.INVISIBLE);
//        getMainActivity().hidePoiInfoBar();
//        final FragmentPay fragmentPay = new FragmentPay();
//        Bundle args = new Bundle();
//        args.putParcelable("parkInfo", p);
//        fragmentPay.setArguments(args);
//        getMainActivity().showFragment(fragmentPay);

        Intent intent = new Intent(getMainActivity(), ActivityPay.class);
        Bundle args = new Bundle();
        args.putParcelable("parkInfo", p);
        intent.putExtras(args);
        startActivityForResult(intent, Constant.startPay);

//        getMainActivity().titleBar.show(null,"支付",null);
//        getMainActivity().titleBar.setOnTitleClickListener(new TitleBar.OnTitleClickListener() {
//          @Override
//          public void onLeft() {
////            getMainActivity().showPoiInfoBar();
////            getMainActivity().closeFragment(fragmentPay);
//
//          }
//          @Override
//          public void onRight() {
//          }
//        });

      }
    });
  }

  public void setPayed(){

    getMainActivity().titleBar.show(null,"找车","已缴费");
    getMainActivity().titleBar.setEnableRight(false);
  }
}
