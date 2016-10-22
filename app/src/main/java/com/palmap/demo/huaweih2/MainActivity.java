package com.palmap.demo.huaweih2;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.palmap.demo.huaweih2.fragment.FragmentAround;
import com.palmap.demo.huaweih2.fragment.FragmentFootPrint;
import com.palmap.demo.huaweih2.fragment.FragmentMap;
import com.palmap.demo.huaweih2.fragment.FragmentPark;
import com.palmap.demo.huaweih2.fragment.FragmentShake;
import com.palmap.demo.huaweih2.model.ParkInfo;
import com.palmap.demo.huaweih2.other.Constant;
import com.palmap.demo.huaweih2.util.DialogUtils;
import com.palmap.demo.huaweih2.util.IpUtils;
import com.palmap.demo.huaweih2.util.MapParamUtils;
import com.palmap.demo.huaweih2.util.QQShareUtils;
import com.palmap.demo.huaweih2.util.WXShareUtils;
import com.palmap.demo.huaweih2.view.FullScreenDialog;
import com.palmap.demo.huaweih2.view.Mark;
import com.palmap.demo.huaweih2.view.TitleBar;
import com.palmaplus.nagrand.data.Feature;
import com.palmaplus.nagrand.data.LocationModel;
import com.palmaplus.nagrand.view.MapView;

import java.util.Timer;
import java.util.TimerTask;

import static com.palmap.demo.huaweih2.fragment.FragmentMap.isSearchCar;
import static com.palmap.demo.huaweih2.other.Constant.H2大厅;
import static com.palmap.demo.huaweih2.other.Constant.ICS办公区;
import static com.palmap.demo.huaweih2.other.Constant.ICS实验室;
import static com.palmap.demo.huaweih2.other.Constant.isDebug;
import static com.palmap.demo.huaweih2.other.Constant.会议室;

public class MainActivity extends BaseActivity {
  public FullScreenDialog dialog;
  public RelativeLayout mMapContainer; // 地图上覆盖物容器
  private RadioGroup tabMenu;
  RadioButton rout;
  RadioButton park;
  RelativeLayout poiInfoBar;
  FragmentAround fragmentAround;
  FragmentPark fragmentPark;
  FragmentMap fragmentMap;
  FragmentFootPrint fragmentFootPrint;
  FragmentShake fragmentShake;

  TextView im_poi;
  ImageView im_go;
  ImageView im_share;
  //  TextView tv_nav_len;
  ImageView im_nav_start;
  ImageView im_nav_end;
  TextView tv_poi_name;
  TextView tv_poi_address;
  TextView tv_poi_moreinfo;
  TextView btn_set_start;
  TextView tv_tip;
  String morePoiInfoName;//查看详情的poi名字

  public TitleBar titleBar;
  //  final private String CURRENTFRAGMENT = "cf";
//  double toX;
//  double toY;
//  long toFloorID;//导航终点
  FragmentTransaction transaction;

  public boolean isShowPoiInfoBar = false;//是否显示poi详情栏

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);


    WXShareUtils.regToWeChat(this);
    QQShareUtils.getInstance().regToQQ(this);

    if (Constant.isDebug) {
      HuaWeiH2Application.firstRun = true;
      startActivityForResult(new Intent(MainActivity.this, WelcomeActivity.class), Constant.startWelcome);

    } else
      checkFirstRun();


    initView();
  }

  private void checkFirstRun() {
    SharedPreferences setting = getSharedPreferences(Constant.IS_FIRST_RUN, 0);
    Boolean user_first = setting.getBoolean("FIRST", true);
    if (user_first) {//第一次
      HuaWeiH2Application.firstRun = true;
      setting.edit().putBoolean("FIRST", false).commit();
      startActivityForResult(new Intent(MainActivity.this, WelcomeActivity.class), Constant.startWelcome);
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch (requestCode){
      case Constant.startWelcome:
        initMapFragment();
        break;
      case Constant.startPay:
        if (requestCode == RESULT_OK)
        fragmentPark.setPayed();
        break;
      default:
        break;
    }




    super.onActivityResult(requestCode, resultCode, data);

  }


  private void initMapFragment() {
    fragmentMap = new FragmentMap();//fang
    transaction = getFragmentManager().beginTransaction();
    transaction.replace(R.id.main_content, fragmentMap).commit();
  }

  public void initView() {
    if (!HuaWeiH2Application.firstRun) {
      fragmentMap = new FragmentMap();
      transaction = getFragmentManager().beginTransaction();
      transaction.replace(R.id.main_content, fragmentMap).commit();
    }
    poiInfoBar = (RelativeLayout) findViewById(R.id.poi_info);
    mMapContainer = (RelativeLayout) findViewById(R.id.map_container);

    park = (RadioButton) findViewById(R.id.park);
    rout = (RadioButton) findViewById(R.id.route);
    rout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (fragmentMap.isShowFootPrint) {
          fragmentMap.resetFootPrint();
        } else {
          fragmentMap.setFootPrint();
        }
      }
    });
    im_poi = (TextView) findViewById(R.id.poi_img);
    tv_tip = (TextView) findViewById(R.id.btn_tip);
    tv_poi_name = (TextView) findViewById(R.id.poi_name);
    tv_poi_address = (TextView) findViewById(R.id.poi_address);
    tv_poi_moreinfo = (TextView) findViewById(R.id.poi_moreinfo);
    tv_poi_moreinfo.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        shouMoreInfo();
      }
    });
    btn_set_start = (TextView) findViewById(R.id.btn_set_start);
    im_go = (ImageView) findViewById(R.id.go_im);
    im_share = (ImageView) findViewById(R.id.share_im);
    im_nav_end = (ImageView) findViewById(R.id.nav_end);
//    tv_nav_len = (TextView)findViewById(R.id.nav_len);
    im_nav_start = (ImageView) findViewById(R.id.nav_start);
    titleBar = (TitleBar) findViewById(R.id.title_bar);
    titleBar.hide();//
    tabMenu = (RadioGroup) findViewById(R.id.tab_menu);
    tabMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {


        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideAllFragments();
        titleBar.hide();
        transaction = getFragmentManager().beginTransaction();

        // TODO Auto-generated method stub
        switch (checkedId) {
          case R.id.route:
            showMap();
            if (fragmentMap == null) {
              fragmentMap = new FragmentMap();
              transaction.add(R.id.main_content, fragmentMap);
            } else {
//              fragmentMap.isShowFootPrint=true;
              transaction.show(fragmentMap);
            }
            break;
          case R.id.around:
            titleBar.show(null, "附近", null);
            hideMap();
            if (fragmentAround == null) {
              fragmentAround = new FragmentAround();
              transaction.add(R.id.main_content, fragmentAround);
            } else {
              transaction.show(fragmentAround);
            }

            break;
          case R.id.park:
            titleBar.show(null, "停车", null);
            hideMap();
            if (fragmentPark == null) {
              fragmentPark = new FragmentPark();
              transaction.add(R.id.main_content, fragmentPark);
            } else {
              transaction.show(fragmentPark);
            }
            break;
          case R.id.footprint:
            titleBar.show(null, "足迹", null);
            hideMap();
            if (fragmentFootPrint == null) {
              fragmentFootPrint = new FragmentFootPrint();
              transaction.add(R.id.main_content, fragmentFootPrint);
            } else {
              transaction.show(fragmentFootPrint);
            }

            break;
          case R.id.shake:
            hideMap();
            titleBar.show(null, "摇一摇", null);
            if (fragmentShake == null) {
              fragmentShake = new FragmentShake();
              transaction.add(R.id.main_content, fragmentShake);
            } else {
              transaction.show(fragmentShake);
            }

            break;
          default:
            break;
        }

        transaction.commit();
      }
    });


    dialog = new FullScreenDialog(this, new FullScreenDialog.OnDialogListener() {
      @Override
      public void btnMapClick() {//室内地图
        //dialog.dismissDialog();
        dialog.dismiss();
        fragmentMap.initMapScale();
      }

      @Override
      public void btnFootClick() {//行程
        fragmentMap.setFootPrint();
        dialog.dismiss();
//        dialog.dismissDialog();
        fragmentMap.initMapScale();
      }
    });
//    dialog.show();
  }

  public void showMap() {
    tabMenu.setVisibility(View.VISIBLE);
    fragmentMap.mMapView.setVisibility(View.VISIBLE);
    mMapContainer.setVisibility(View.VISIBLE);
    fragmentMap.mMapView.setOverlayContainer(mMapContainer);
  }

  public void showCarOnMap(ParkInfo parkInfo) {
//    showMap();
    fragmentMap.mMapView.setVisibility(View.VISIBLE);
    mMapContainer.setVisibility(View.VISIBLE);
    fragmentMap.mMapView.setOverlayContainer(mMapContainer);
    fragmentMap.moveToCar(parkInfo);
  }

  public MapView getMapView() {
      return fragmentMap.mMapView;
  }


  private void hideMap() {
    tabMenu.setVisibility(View.GONE);
    fragmentMap.mMapView.setVisibility(View.GONE);
    mMapContainer.setVisibility(View.GONE);
  }

  public void hideTabMenu() {
    tabMenu.setVisibility(View.GONE);
  }

  public void showFragment(Fragment fragment) {
    hideAllFragments();
    transaction = getFragmentManager().beginTransaction();
    transaction.replace(R.id.main_content, fragment);
    transaction.commit();

  }

  public void closeFragment(Fragment fragment) {
//    hideAllFragments();
    transaction = getFragmentManager().beginTransaction();
    transaction.remove(fragment);
    transaction.commit();

  }

  public void showFragmentPark() {
//    park.setChecked(true);//无效，因原本就是park
// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
    hideAllFragments();
    transaction = getFragmentManager().beginTransaction();

    hideMap();
    if (fragmentPark == null) {
      fragmentPark = new FragmentPark();
      transaction.add(R.id.main_content, fragmentPark);
    } else {
      transaction.show(fragmentPark);
    }
    transaction.commit();
    showTabMenu();
    tabMenu.setVisibility(View.GONE);
    titleBar.show(null, "停车", null);
    titleBar.setOnTitleClickListener(new TitleBar.OnTitleClickListener() {
      @Override
      public void onLeft() {
        showFragmentMap();
      }

      @Override
      public void onRight() {

      }
    });
  }

  public void showFragmentMap() {
    rout.setChecked(true);
//    // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
//    hideAllFragments();
//    titleBar.hide();
//    showMap();
//    transaction = getFragmentManager().beginTransaction();
//    if (fragmentMap == null) {
//      fragmentMap = new FragmentMap();
//      transaction.add(R.id.main_content, fragmentMap);
//    } else {
//      fragmentMap.isShowFootPrint=true;
//      transaction.show(fragmentMap);
//    }
  }

  //显示poi详情
  public void showPoiInfoBar(final Feature feature) {
    isShowPoiInfoBar = true;
    tv_tip.setVisibility(View.GONE);
    poiInfoBar.setVisibility(View.VISIBLE);
    im_poi.setVisibility(View.VISIBLE);
    im_poi.setBackgroundResource(R.drawable.btn_tab_camera);
    tv_poi_name.setVisibility(View.VISIBLE);
    tabMenu.setVisibility(View.GONE);
    tv_poi_address.setVisibility(View.VISIBLE);

  }

  //显示poi详情
  public void showPoiInfoBar() {
    poiInfoBar.setVisibility(View.VISIBLE);
  }

  //暂时隐藏poi详情
  public void hidePoiInfoBar() {
    poiInfoBar.setVisibility(View.GONE);
  }

  //显示导航详情
  public void showNavigateInfoBar(String startName, String endName, String len) {
    im_poi.setVisibility(View.GONE);
    btn_set_start.setVisibility(View.GONE);
    im_poi.setVisibility(View.VISIBLE);
    im_poi.setBackgroundResource(R.drawable.trans);
    im_poi.setText(len + "m");

    im_nav_end.setVisibility(View.VISIBLE);
    im_nav_start.setVisibility(View.VISIBLE);

    tv_poi_name.setVisibility(View.VISIBLE);//起点
    tv_poi_name.setText(startName);
    tv_poi_address.setVisibility(View.VISIBLE);//终点
    tv_poi_name.setText(endName);

  }

  //设置poi详情
  public void setPoiInfoBar(final LocationModel locationModel) {
    final String name = MapParamUtils.getName(locationModel);
    if (isSearchCar) {
      tv_poi_name.setText(fragmentMap.parkInfo.getCarNum() + "停在" + (name == null ? "未知位置" : name));
    } else {
      tv_poi_name.setText(name == null ? "未知位置" : name);
    }

    String address = MapParamUtils.getAddress(locationModel);
    tv_poi_address.setText(address == null ? fragmentMap.mCurrentFloor == 1 ? "F1" : "B1" : address);

    checkShowMoreInfo(name);


    btn_set_start.setVisibility(View.GONE);
    im_go.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //标题栏
        titleBar.show(null, "导航路线", null);
        titleBar.setOnTitleClickListener(new TitleBar.OnTitleClickListener() {
          @Override
          public void onLeft() {
            showTabMenu();
            fragmentMap.endNavigate();
          }

          @Override
          public void onRight() {
          }
        });
        //将POI mark图标改为终点
        fragmentMap.resetFeatureStyle(fragmentMap.markFeature);
//        fragmentMap.mMapView.removeOverlay(fragmentMap.mark);
//        fragmentMap.addMark(fragmentMap.xx,fragmentMap.yy);
        fragmentMap.addMark(fragmentMap.endX, fragmentMap.endY, Mark.END);
        if (fragmentMap.hasLocated) {//有定位点

        } else {//无定位点
          fragmentMap.isSelectStartPoint = true;
          fragmentMap.toFloorID = LocationModel.parent.get(locationModel);

          tv_poi_address.setVisibility(View.GONE);
          tv_poi_moreinfo.setVisibility(View.GONE);
          tv_poi_name.setVisibility(View.GONE);
          im_poi.setVisibility(View.GONE);
          im_go.setVisibility(View.GONE);
          im_share.setVisibility(View.GONE);
          tv_tip.setVisibility(View.VISIBLE);
        }
      }
    });
    im_share.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
//        WXShareUtils.sendToWeChat("我正在华为ICS实验室参观");
        QQShareUtils.getInstance().shareToQQ(MainActivity.this);
      }
    });
  }

  //判断是否显示 查看详情
  private void checkShowMoreInfo(String name) {
    //若为4个节点，显示详情
    if (name.equals(H2大厅) || name.equals(ICS办公区) || name.contains(ICS实验室) || name.equals(会议室)) {
      tv_poi_moreinfo.setText("查看详情>>");
      morePoiInfoName = name;
      tv_poi_moreinfo.setClickable(true);
    } else {
      tv_poi_moreinfo.setText("");
      tv_poi_moreinfo.setClickable(false);
    }


  }

  //查看更多
  private void shouMoreInfo() {
    if (H2大厅.equals(morePoiInfoName)) {
      startActivity(new Intent(this, ActivityHall.class));
    } else if (会议室.equals(morePoiInfoName)) {
      startActivity(new Intent(this, ActivityMeeting.class));
    } else if (ICS办公区.equals(morePoiInfoName)) {
      startActivity(new Intent(this, ActivityOffice.class));
    } else if (morePoiInfoName.contains(ICS实验室)) {
      startActivity(new Intent(this, ActivityLab.class));
    }
  }

  //显示导航栏
  public void showTabMenu() {
    isShowPoiInfoBar = false;
    poiInfoBar.setVisibility(View.GONE);
    im_share.setVisibility(View.VISIBLE);
    im_go.setVisibility(View.VISIBLE);
    tabMenu.setVisibility(View.VISIBLE);
    im_nav_end.setVisibility(View.GONE);
    im_nav_start.setVisibility(View.GONE);
//    tv_nav_len.setVisibility(View.GONE);
    titleBar.hide();
  }


  private void hideAllFragments() {
    transaction = getFragmentManager().beginTransaction();
    if (transaction == null)
      return;

    if (fragmentMap != null) {
      transaction.hide(fragmentMap);
    }
    if (fragmentFootPrint != null) {
      transaction.hide(fragmentFootPrint);
    }
    if (fragmentPark != null) {
      transaction.hide(fragmentPark);
    }
    if (fragmentAround != null) {
      transaction.hide(fragmentAround);
    }
    if (fragmentShake != null) {
      transaction.hide(fragmentShake);
    }

    transaction.commit();
  }


  public void setStartPoiInfo(LocationModel locationModel) {
    poiInfoBar.setVisibility(View.VISIBLE);
    im_poi.setVisibility(View.VISIBLE);
    tv_poi_name.setVisibility(View.VISIBLE);
    tv_poi_address.setVisibility(View.VISIBLE);
    tabMenu.setVisibility(View.GONE);

    String name = MapParamUtils.getName(locationModel);

    tv_poi_name.setText(name == null ? "未知位置" : name);

    String address = MapParamUtils.getAddress(locationModel);

    tv_poi_address.setText(address == null ? fragmentMap.mCurrentFloor == 1 ? "F1" : "B1" : address);

//          tv_poi_moreinfo.setText(MapParamUtils.getEnglishName(locationModel));

    im_go.setVisibility(View.GONE);
    im_share.setVisibility(View.GONE);
    tv_tip.setVisibility(View.GONE);
    btn_set_start.setVisibility(View.VISIBLE);
    btn_set_start.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        fragmentMap.isSelectStartPoint = false;
        fragmentMap.startNavigate();
      }
    });

  }

  @Override
  public void onStart() {
    super.onStart();

    HuaWeiH2Application.userIp = IpUtils.getIpAddress();
    if (isDebug)
      DialogUtils.showLongToast(HuaWeiH2Application.userIp);

  }


//  @Override
//  public void onBackPressed() {
//    exitBy2Click(); //调用双击退出函数
//    super.onBackPressed();
//  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
      exitBy2Click(); //调用双击退出函数
    }
    return true;


  }

  /**
   * 双击退出函数
   */
  private static Boolean isExit = false;

  private void exitBy2Click() {
    Timer tExit = null;
    if (isExit == false) {
      isExit = true; // 准备退出
      Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
      tExit = new Timer();
      tExit.schedule(new TimerTask() {
        @Override
        public void run() {
          isExit = false; // 取消退出
        }
      }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

    } else {
      finish();
      System.exit(0);
    }
  }
}
