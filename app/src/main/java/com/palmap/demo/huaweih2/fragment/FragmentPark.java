package com.palmap.demo.huaweih2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.palmap.demo.huaweih2.ActivityPay;
import com.palmap.demo.huaweih2.R;
import com.palmap.demo.huaweih2.adapter.ParkInfoAdapter;
import com.palmap.demo.huaweih2.model.ParkInfo;
import com.palmap.demo.huaweih2.model.ParkInfoList;
import com.palmap.demo.huaweih2.other.Constant;
import com.palmap.demo.huaweih2.util.DialogUtils;
import com.palmap.demo.huaweih2.util.KeyBoardUtils;
import com.palmap.demo.huaweih2.view.TitleBar;

import java.util.List;

import static com.palmap.demo.huaweih2.fragment.FragmentMap.isSearchCar;

/**
 * Created by eric3 on 2016/10/8.
 */
public class FragmentPark extends BaseFragment {
  public static boolean hasPay;//
   EditText carNum;
  TextView btnSearch;
  ParkInfoList parkInfos;
  List<ParkInfo> parkInfoList;
  ParkInfoAdapter parkInfoAdapter;
  public LinearLayout mainlayout;
  public static boolean isFindCarJumpF1 = false;
  String keyWord;
  ListView carNumList;
//  public LinearLayout floorlayout;

  @Override
  public View onCreateView(LayoutInflater inflater,
                           @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    View fragmentView = inflater.inflate(R.layout.park, container, false);

    parkInfos = new ParkInfoList();
    parkInfoList = parkInfos.getParkInfoList();
    carNum = (EditText) fragmentView.findViewById(R.id.tv_car_num);
    carNum.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        showCarList();
      }

      @Override
      public void afterTextChanged(Editable s) {
      }
    });
    btnSearch = (TextView) fragmentView.findViewById(R.id.btn_search);
    mainlayout = (LinearLayout)fragmentView.findViewById(R.id.park_main);
    carNumList = (ListView)fragmentView.findViewById(R.id.carNum_list);
    parkInfoAdapter = new ParkInfoAdapter(getActivity(), parkInfoList, new ParkInfoAdapter.OnItemClickListener() {
      @Override
      public void onClicked(ParkInfo parkInfo) {
        ParkInfo t=parkInfo;
        if (t==null){
          if ("".equals(carNum.getText())){
            DialogUtils.showShortToast("请输入车牌号信息");
          }else {
            DialogUtils.showShortToast("没有车牌号为" + parkInfo.getCarNum()+ "的停车信息");
          }

          return;
        }
        DialogUtils.showShortToast("正在为您跳转到车牌号为"+t.getCarNum()+"的停车位，请稍后...");
        KeyBoardUtils.closeKeybord(carNum,getActivity());
//        getMainActivity().hideTabMenu();
        showCarOnMap(t);
      }
    });
    carNumList.setAdapter(parkInfoAdapter);
//    floorlayout = (LinearLayout)fragmentView.findViewById(R.id.floor_park);
//    floorlayout.setVisibility(View.GONE);

    return fragmentView;
  }

  private void showCarList() {
    keyWord = carNum .getText().toString();
    if ("".equals(keyWord)){
      carNumList.setVisibility(View.GONE);
      return;
    }
    parkInfoList = parkInfos.getParkInfoListByKey(keyWord);
    parkInfoAdapter.notifyDataChanged(parkInfoList);
    carNumList.setVisibility(View.VISIBLE);
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    btnSearch.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ParkInfo t=parkInfos.getParkInfoByCarNum("粤B·"+carNum.getText());
        if (t==null){
          if ("".equals(carNum.getText())){
            DialogUtils.showShortToast("请输入车牌号信息");
          }else {
            DialogUtils.showShortToast("没有车牌号为" + carNum.getText()+ "的停车信息");
          }

          return;
        }
        DialogUtils.showShortToast("正在为您跳转到车牌号为"+"粤B·"+carNum.getText()+"的停车位，请稍后...");
        KeyBoardUtils.closeKeybord(carNum,getActivity());
//        getMainActivity().hideTabMenu();
        showCarOnMap(t);
      }
    });
    getMainActivity().titleBar.setOnTitleClickListener(new TitleBar.OnTitleClickListener() {
      @Override
      public void onLeft() {
        KeyBoardUtils.closeKeybord(carNum,getActivity());
        getMainActivity().showFragmentMap();
      }

      @Override
      public void onRight() {
      }
    });
  }

  private void showCarOnMap(final ParkInfo p) {
    mainlayout.setVisibility(View.GONE);
    carNumList.setVisibility(View.GONE);
//    floorlayout.setVisibility(View.VISIBLE);
    getMainActivity().showCarOnMap(p);
    getMainActivity().titleBar.show(null,"找车","缴费");
    getMainActivity().titleBar.setEnableRight(true);
    getMainActivity().titleBar.setOnTitleClickListener(new TitleBar.OnTitleClickListener() {
      @Override
      public void onLeft() {
        isSearchCar = false;
        getMainActivity().showFragmentPark();
        mainlayout.setVisibility(View.VISIBLE);

        getMainActivity().fragmentMap.endNavigateInFootAndPark();
        getMainActivity().fragmentMap.mMapView.removeAllOverlay();
//        getMainActivity().fragmentMap.resetFeatureStyle(getMainActivity().fragmentMap.markFeatureID);
        getMainActivity().fragmentMap.mMapView.getOverlayController().refresh();
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

        Intent intent = new Intent(getActivity(), ActivityPay.class);
        Bundle args = new Bundle();
        args.putParcelable("parkInfo", p);
        intent.putExtras(args);
        getActivity().startActivityForResult(intent, Constant.startPay);

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

  @Override
  public void onResume() {
    isFindCarJumpF1 = FragmentMap.mCurrentFloor==Constant.FLOOR_ID_F1;
    super.onResume();
  }
}
