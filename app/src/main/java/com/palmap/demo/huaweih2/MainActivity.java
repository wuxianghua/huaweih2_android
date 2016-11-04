package com.palmap.demo.huaweih2;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.palmap.demo.huaweih2.model.PoiImg;
import com.palmap.demo.huaweih2.model.PoiImgList;
import com.palmap.demo.huaweih2.other.Constant;
import com.palmap.demo.huaweih2.util.DialogUtils;
import com.palmap.demo.huaweih2.util.IpUtils;
import com.palmap.demo.huaweih2.util.MapParamUtils;
import com.palmap.demo.huaweih2.util.QQShareUtils;
import com.palmap.demo.huaweih2.util.WXShareUtils;
import com.palmap.demo.huaweih2.view.Mark;
import com.palmap.demo.huaweih2.view.TitleBar;
import com.palmaplus.nagrand.data.Feature;
import com.palmaplus.nagrand.view.MapView;

import java.io.File;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.palmap.demo.huaweih2.fragment.FragmentMap.isNavigating;
import static com.palmap.demo.huaweih2.fragment.FragmentMap.isSearchCar;
import static com.palmap.demo.huaweih2.other.Constant.H2大厅;
import static com.palmap.demo.huaweih2.other.Constant.ICS办公区;
import static com.palmap.demo.huaweih2.other.Constant.ICS实验室;
import static com.palmap.demo.huaweih2.other.Constant.isDebug;
import static com.palmap.demo.huaweih2.other.Constant.startTakePic;
import static com.palmap.demo.huaweih2.other.Constant.会议室;
import static com.palmaplus.nagrand.position.ble.BeaconUtils.TAG;

public class MainActivity extends BaseActivity {
    //  public FullScreenDialog dialog;
    public RelativeLayout dialog;
    LinearLayout btn_map;
    LinearLayout btn_foot;
    public RelativeLayout mMapContainer; // 地图上覆盖物容器
    private RadioGroup tabMenu;
    RadioButton rout;
    RadioButton park;
    RelativeLayout poiInfoBar;
    FragmentAround fragmentAround;
    FragmentPark fragmentPark;
    public FragmentMap fragmentMap;
    FragmentFootPrint fragmentFootPrint;
    FragmentShake fragmentShake;

    public TextView im_poi;
    ImageView im_go;
    //  ImageView im_share;
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

        initStatusBar(R.color.black);
        //启动Android定时器，并且启动服务
        if (Constant.openLocateService)
            LocateTimerService.getLocation(this);

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
        switch (requestCode) {
            case Constant.startWelcome:
                initMapFragment();
                break;
            case Constant.startPay:
                if (resultCode == RESULT_OK)
                    fragmentPark.setPayed();
                break;
            case Constant.startOffice:
                if (data != null) {
                    String name = data.getStringExtra("peopleName");
                    if (name == null)
                        break;

                    fragmentMap.searchPeopleName(name);
                }
                break;
            case Constant.startTakePic:
                Log.i(TAG, "拍摄完成，resultCode=" + requestCode);
                Intent intent = new Intent(this, UploadActivity.class);
//    intent.putExtra()
                startActivityForResult(intent, Constant.startUploadPic);
                break;
            case Constant.startUploadText:
                if (fragmentFootPrint.commentList == null) {
                    break;
                }
                if (resultCode == RESULT_OK) {
                    fragmentFootPrint.commentList.removeAllViews();
                    fragmentFootPrint.start = 0;
                    fragmentFootPrint.loadComments();
                }
                break;
            case Constant.startUploadPic:
                if (fragmentFootPrint != null)
                    fragmentFootPrint.loadPicNum();
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

        dialog = (RelativeLayout) findViewById(R.id.dialog);
        btn_map = (LinearLayout) findViewById(R.id.btn_map);
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentMap.initMapScale();
                dialog.setVisibility(View.GONE);
            }
        });
        btn_foot = (LinearLayout) findViewById(R.id.btn_foot);
        btn_foot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragmentMap.mCurrentFloor == Constant.FLOOR_ID_B1) {
                    DialogUtils.showShortToast("请切换至F1再查看行程");
                    return;
                }
                fragmentMap.initMapScale();
                fragmentMap.setFootPrint();
                dialog.setVisibility(View.GONE);
            }
        });
        park = (RadioButton) findViewById(R.id.park);
        rout = (RadioButton) findViewById(R.id.route);
        rout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragmentMap.isShowFootPrint) {
                    fragmentMap.resetFootPrint();
                } else {
                    if (fragmentMap.mCurrentFloor == Constant.FLOOR_ID_B1) {//B1
                        DialogUtils.showShortToast("请切换至楼层F1后再点击行程");
                        return;
                    }
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
//    im_share = (ImageView) findViewById(R.id.share_im);
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
//            if (fragmentAround == null) {
                        fragmentAround = new FragmentAround();
                        transaction.add(R.id.main_content, fragmentAround);
//            } else {
//              transaction.show(fragmentAround);
//            }

                        break;
                    case R.id.park:
                        titleBar.show(null, "停车", null);
                        hideMap();
//            if (fragmentPark == null) {
                        fragmentPark = new FragmentPark();
                        transaction.add(R.id.main_content, fragmentPark);
//            } else {
//              transaction.show(fragmentPark);
//            }
                        break;
                    case R.id.footprint:
                        titleBar.show(null, "足迹", null);
                        hideMap();
//            if (fragmentFootPrint == null) {
                        fragmentFootPrint = new FragmentFootPrint();
                        transaction.add(R.id.main_content, fragmentFootPrint);
//            } else {
//              transaction.show(fragmentFootPrint);
//            }

                        break;
                    case R.id.shake:
                        hideMap();
                        titleBar.show(null, "摇一摇", null);
//            if (fragmentShake == null) {
                        fragmentShake = new FragmentShake();
                        transaction.add(R.id.main_content, fragmentShake);
//            } else {
//              fragmentShake.initShakeSensor();
//              transaction.show(fragmentShake);
//            }

                        break;
                    default:
                        break;
                }

                transaction.commit();
            }
        });


//    dialog = new FullScreenDialog(this, new FullScreenDialog.OnDialogListener() {
//      @Override
//      public void btnMapClick() {//室内地图
//        //dialog.dismissDialog();
//        dialog.dismiss();
//        fragmentMap.initMapScale();
//      }
//
//      @Override
//      public void btnFootClick() {//行程
//        fragmentMap.setFootPrint();
//        dialog.dismiss();
////        dialog.dismissDialog();
//        fragmentMap.initMapScale();
//      }
//    });
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

        //寻车不导航
        im_go.setVisibility(View.GONE);
    }

    public MapView getMapView() {
        return fragmentMap.mMapView;
    }


    private void hideMap() {
        tabMenu.setVisibility(View.GONE);
        fragmentMap.mMapView.setVisibility(View.GONE);
        mMapContainer.setVisibility(View.GONE);
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
        poiInfoBar.setVisibility(View.GONE);
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
    public void showPoiInfoBar(final long featureCategoryID) {
        isShowPoiInfoBar = true;
        tv_tip.setVisibility(View.GONE);
        poiInfoBar.setVisibility(View.VISIBLE);
        im_poi.setVisibility(View.VISIBLE);
        im_poi.setBackgroundResource(R.drawable.huaweilogo);


        List<PoiImg> poiImgs = PoiImgList.getPoiImgList();
        for (int i = 0; i < poiImgs.size(); i++) {
            if (featureCategoryID == poiImgs.get(i).getCat())
                im_poi.setBackgroundResource(poiImgs.get(i).getId());
        }


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
    public void setPoiInfoBar(final Feature feature) {
        final String name = MapParamUtils.getName(feature);
        if (isSearchCar) {
            tv_poi_name.setText(fragmentMap.parkInfo.getCarNum() + "停在" + (name == null ? "未知位置" : name));
        } else {
            tv_poi_name.setText(name == null ? "H2大楼" : name);
        }

        String address = MapParamUtils.getAddress(feature);
        tv_poi_address.setText(address == null ? fragmentMap.mCurrentFloor == Constant.FLOOR_ID_F1 ? "F1" : "B1" : address);

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
                fragmentMap.resetFeatureStyle(fragmentMap.markFeatureID);
//        fragmentMap.mMapView.removeOverlay(fragmentMap.mark);
//        fragmentMap.addMark(fragmentMap.xx,fragmentMap.yy);
                fragmentMap.addMark(fragmentMap.endX, fragmentMap.endY, Mark.END);
                if (fragmentMap.hasLocated) {//有定位点

                } else {//无定位点
                    fragmentMap.isSelectStartPoint = true;
                    fragmentMap.toFloorID = Feature.planar_graph.get(feature);

                    tv_poi_address.setVisibility(View.GONE);
                    tv_poi_moreinfo.setVisibility(View.GONE);
                    tv_poi_name.setVisibility(View.GONE);
                    im_poi.setVisibility(View.GONE);
                    im_go.setVisibility(View.GONE);
//          im_share.setVisibility(View.GONE);
                    tv_tip.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    //判断是否显示 查看详情
    private void checkShowMoreInfo(String name) {
        if (name == null) {
            tv_poi_moreinfo.setText("");
            tv_poi_moreinfo.setClickable(false);
            return;
        }
        //若为4个节点，显示详情
        if (H2大厅.equals(name) || ICS办公区.equals(name) || name.contains(ICS实验室) || 会议室.equals(name)) {
            tv_poi_moreinfo.setVisibility(View.VISIBLE);
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
        if (morePoiInfoName == null)
            return;

        if (H2大厅.equals(morePoiInfoName)) {
            startActivity(new Intent(this, ActivityHall.class));
        } else if (会议室.equals(morePoiInfoName)) {
            startActivity(new Intent(this, ActivityMeeting.class));
        } else if (ICS办公区.equals(morePoiInfoName)) {
            startActivityForResult(new Intent(this, ActivityOffice.class), Constant.startOffice);
        } else if (morePoiInfoName.contains(ICS实验室)) {
            startActivity(new Intent(this, ActivityLab.class));
        }
    }

    //隐藏导航栏，显示title
    public void hideTabMenu() {
        tabMenu.setVisibility(View.GONE);
    }

    //显示导航栏，隐藏title
    public void showTabMenu() {
        isShowPoiInfoBar = false;
        poiInfoBar.setVisibility(View.GONE);
//    im_share.setVisibility(View.VISIBLE);
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


    public void setStartPoiInfo(Feature feature) {
        poiInfoBar.setVisibility(View.VISIBLE);
        im_poi.setVisibility(View.VISIBLE);
        tv_poi_name.setVisibility(View.VISIBLE);
        tv_poi_address.setVisibility(View.VISIBLE);
        tabMenu.setVisibility(View.GONE);

        String name = MapParamUtils.getName(feature);

        tv_poi_name.setText(name == null ? "未知位置" : name);

        String address = MapParamUtils.getAddress(feature);

        tv_poi_address.setText(address == null ? fragmentMap.mCurrentFloor == 1 ? "F1" : "B1" : address);

//          tv_poi_moreinfo.setText(MapParamUtils.getEnglishName(locationModel));

        im_go.setVisibility(View.GONE);
//    im_share.setVisibility(View.GONE);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //停止由AlarmManager启动的循环
        LocateTimerService.stop(this);
        //停止由服务启动的循环
        Intent intent = new Intent(this, LocateTimerService.class);
        stopService(intent);
    }

    //  @Override
//  public void onBackPressed() {
//    exitBy2Click(); //调用双击退出函数
//    super.onBackPressed();
//  }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (fragmentShake != null && fragmentShake.isVisible()) {
                fragmentShake.stopShakeSensor();
            }


            if (tabMenu.getVisibility() == View.VISIBLE || (poiInfoBar.getVisibility() == View.VISIBLE && fragmentMap.isVisible() == true)) {//只有在首页才退出

                exitBy2Click(); //调用双击退出函数

            } else {
                if (isNavigating) {
                    showTabMenu();
                    fragmentMap.endNavigate();
                } else if (fragmentMap.isShowFootPrint) {
                    fragmentMap.resetFootPrint();
                } else if (isSearchCar) {
                    isSearchCar = false;
                    showFragmentPark();
                    fragmentPark.mainlayout.setVisibility(View.VISIBLE);
                    fragmentMap.mMapView.removeAllOverlay();
                    fragmentMap.resetFeatureStyle(fragmentMap.markFeatureID);
                    fragmentMap.mMapView.getOverlayController().refresh();
                } else {
                    showFragmentMap();
                }
            }
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


    public void openCameraActivity() {
        Intent intent = new Intent();
        // 指定开启系统相机的Action
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        //如果路径不存在，则创建
        // 创建文件夹
        File file = new File(Constant.DIR_PICTURE_UPLOAD);
        if (!file.exists()) {
            file.mkdirs();
//      file = new File(Constant.DIR_PICTURE_UPLOAD);
//      if (!file.exists()) {
//        file.mkdirs();
//      }
        }
        // 根据文件地址创建文件
        file = new File(Constant.PATH_PICTURE_UPLOAD);
        if (file.exists()) {
            file.delete();
        }
        // 把文件地址转换成Uri格式
        Uri uri = Uri.fromFile(file);
        // 设置系统相机拍摄照片完成后图片文件的存放地址
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, startTakePic);
    }

}
