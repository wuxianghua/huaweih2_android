package com.palmap.demo.huaweih2.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.palmap.demo.huaweih2.ActivityHall;
import com.palmap.demo.huaweih2.ActivityLab;
import com.palmap.demo.huaweih2.ActivityMeeting;
import com.palmap.demo.huaweih2.ActivityOffice;
import com.palmap.demo.huaweih2.LocateTimerService;
import com.palmap.demo.huaweih2.MainActivity;
import com.palmap.demo.huaweih2.PoiInfoActivity;
import com.palmap.demo.huaweih2.R;
import com.palmap.demo.huaweih2.adapter.SearchResultAdapter;
import com.palmap.demo.huaweih2.http.DataProviderCenter;
import com.palmap.demo.huaweih2.http.ErrorCode;
import com.palmap.demo.huaweih2.http.HttpDataCallBack;
import com.palmap.demo.huaweih2.json.PositionJson;
import com.palmap.demo.huaweih2.model.Floor;
import com.palmap.demo.huaweih2.model.ParkInfo;
import com.palmap.demo.huaweih2.model.RoutePoi;
import com.palmap.demo.huaweih2.other.Constant;
import com.palmap.demo.huaweih2.util.DialogUtils;
import com.palmap.demo.huaweih2.util.KeyBoardUtils;
import com.palmap.demo.huaweih2.util.MapParamUtils;
import com.palmap.demo.huaweih2.view.LocationMarkAnim;
import com.palmap.demo.huaweih2.view.Mark;
import com.palmap.demo.huaweih2.view.PoiGreyMark;
import com.palmap.demo.huaweih2.view.PoiRedMark;
import com.palmap.demo.huaweih2.view.Scale;
import com.palmap.demo.huaweih2.view.TitleBar;
import com.palmaplus.nagrand.core.Engine;
import com.palmaplus.nagrand.core.Types;
import com.palmaplus.nagrand.core.Value;
import com.palmaplus.nagrand.data.BasicElement;
import com.palmaplus.nagrand.data.CategoryModel;
import com.palmaplus.nagrand.data.DataList;
import com.palmaplus.nagrand.data.DataSource;
import com.palmaplus.nagrand.data.Feature;
import com.palmaplus.nagrand.data.FeatureCollection;
import com.palmaplus.nagrand.data.LocationModel;
import com.palmaplus.nagrand.data.LocationPagingList;
import com.palmaplus.nagrand.data.MapElement;
import com.palmaplus.nagrand.data.PlanarGraph;
import com.palmaplus.nagrand.geos.Coordinate;
import com.palmaplus.nagrand.io.CacheAsyncHttpClient;
import com.palmaplus.nagrand.io.FileCacheMethod;
import com.palmaplus.nagrand.navigate.NavigateManager;
import com.palmaplus.nagrand.view.MapOptions;
import com.palmaplus.nagrand.view.MapView;
import com.palmaplus.nagrand.view.gestures.OnSingleTapListener;
import com.palmaplus.nagrand.view.gestures.OnZoomListener;
import com.palmaplus.nagrand.view.layer.FeatureLayer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.palmap.demo.huaweih2.R.id.search_null_tv;
import static com.palmap.demo.huaweih2.other.Constant.FLOOR_ID_F1;
import static com.palmap.demo.huaweih2.other.Constant.H2大厅;
import static com.palmap.demo.huaweih2.other.Constant.ICS办公区;
import static com.palmap.demo.huaweih2.other.Constant.ICS实验室;
import static com.palmap.demo.huaweih2.other.Constant.会议室;
import static com.palmaplus.nagrand.position.Location.floorId;
import static com.palmaplus.nagrand.position.ble.BeaconUtils.TAG;

/**
 * Created by eric3 on 2016/10/8.
 */

public class FragmentMap extends BaseFragment implements View.OnClickListener {
  //四节点气泡显示坐标
  double[] x = {12697150.114,
      12697129.354,
      12697098.453,
      12697087.313
  };
  double[] y = {
      2588909.271,
      2588909.813,
      2588907.851,
      2588875.184
  };
  //  //四节点行程选择坐标
//  long[] poi4huawei = new long[]{1284129    ,1284130 ,1270043, 1264378,
//        1264100, 1270042 ,1264420 ,1264107};
  double[] xxx = {12697150.096,
      12697132.168,
      12697127.299,
      12697130.157,
      12697129.987,
      12697098.491,
      12697097.793,
      12697086.776

  };
  double[] yyy = {
      2588906.247,
      2588906.713,
      2588912.343,
      2588908.131,
      2588903.792,
      2588910.819,
      2588904.872,
      2588877.236
  };


  LocationMarkAnim locationMarkAnim;
  //地图初始化
  private static final double initX = 1.2697134896049999E7;
  private static final double initY = 2588904.3841;
  private static final double initScale = 3;
  //  public double xx, yy;
  private TextView mF1;
  private TextView mB1;
  private TextView mLocation;
  private TextView mZoomIn;
  private TextView mZoomOut;
  private TextView mShoot;
  private TextView mElevator;
  private TextView mExit;
  private TextView mWater;
  private TextView mWc;
  private EditText mKeywords;
  private ImageView mCompass;
  private ImageView mSearch;
  private RelativeLayout mSearchBox;
  private ImageView mStartSearch;
  private TextView mCancelSearch;
  private ListView mSearchList;
  private LinearLayout mSearchBg;
  private LinearLayout mSearchDef;
  private LinearLayout mSearchDef2;
  private TextView mSearchNull;
  private PoiRedMark currentRedMark;//当前红色气泡
  private Mark currentClickMark;//当前点击位置poi
  private List<Mark> currentPoiMarks;//当前过滤poi mark 列表

  //搜索poi
  private LinearLayout btn_hall;
  private LinearLayout btn_meeting;
  private LinearLayout btn_lab;
  private LinearLayout btn_office;
  private LinearLayout btn_ele;
  private LinearLayout btn_wat;
  private LinearLayout btn_exit;
  private LinearLayout btn_tol;

  private SearchResultAdapter mSearchResultAdapter;
  public long markFeatureID;//用于变色后恢复
  public long startFeatureID;//导航开始
  public double startX;
  public double startY;
  //  public Feature endFeature;//导航结束
  private String startName;
  private String endName;
  public double endX;
  public double endY;
  public long startFloorID;
  public long toFloorID;
  private FeatureLayer navigateLayer;//
  FeatureLayer huaweiLayer;//poi结点变色
  private NavigateManager navigateManager;

  private MainActivity mContext;
  private Scale mScale;

  private List<Floor> mFloorListData;
  public long mCurrentFloor = -1; // 当前楼层id  0-B1  1-F1
  private int mCurrentPoiFilter = 0; // 当前poi过滤 0-无 1-电梯 2-厕所 3-出口 4-盥洗室
  public long mFloorId;

  private ImageView imPush;
  public FrameLayout mMapViewFrame;
  public MapView mMapView;

  public DataSource mDataSource;
  private MapOptions mMapOptions;
  private FeatureLayer featureLayer;
  private boolean isLoadingMap; // 正在加载地图

  private Handler mHandler;
  public static boolean hasLocated = false;//有定位点
  public static boolean isSelectStartPoint = false;//是否正在选择起点模式，控制mapview单击响应
  public static boolean isNavigating = false;//是否正在导航，控制mapview单击响应
  public static boolean isShowFootPrint = false;//是否正在显示行程，控制地图显示
  public static boolean hasChoosenFootPrint = false;//是否显示过行程地图选择框
  public static boolean isSearchCar = false;//是否巡车页面

  private Mark startMark;
  private Mark endMark;
  public Mark mark;

  public ParkInfo parkInfo;//停车信息
  private double currentMapZoom;//当前缩放级别

  private boolean isSingleTapTooShort = false;//不让连续点击

  @Override
  public void onCreate(Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    super.onCreate(savedInstanceState);

    mHandler = new Handler(Looper.getMainLooper());
    mContext = (MainActivity) getActivity();
//    startMark = new Mark(mContext,Mark.START);
    initEngine();
  }

  @Override
  public View onCreateView(LayoutInflater inflater,
                           @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    View fragmentView = inflater.inflate(R.layout.map, container, false);
    // 初始化view
    imPush = (ImageView) fragmentView.findViewById(R.id.push);
    imPush.setVisibility(View.GONE);
    mSearchNull = (TextView) fragmentView.findViewById(search_null_tv);
    btn_hall = (LinearLayout) fragmentView.findViewById(R.id.hall);
    btn_meeting = (LinearLayout) fragmentView.findViewById(R.id.meeting);
    btn_lab = (LinearLayout) fragmentView.findViewById(R.id.lab);
    btn_office = (LinearLayout) fragmentView.findViewById(R.id.office);
    btn_ele = (LinearLayout) fragmentView.findViewById(R.id.ele);
    btn_wat = (LinearLayout) fragmentView.findViewById(R.id.wat);
    btn_exit = (LinearLayout) fragmentView.findViewById(R.id.exi);
    btn_tol = (LinearLayout) fragmentView.findViewById(R.id.tol);
    btn_hall.setOnClickListener(this);
    btn_meeting.setOnClickListener(this);
    btn_office.setOnClickListener(this);
    btn_ele.setOnClickListener(this);
    btn_lab.setOnClickListener(this);
    btn_wat.setOnClickListener(this);
    btn_exit.setOnClickListener(this);
    btn_tol.setOnClickListener(this);

    mSearchDef = (LinearLayout) fragmentView.findViewById(R.id.search_default);
    mSearchDef2 = (LinearLayout) fragmentView.findViewById(R.id.search_default2);
    mSearchBg = (LinearLayout) fragmentView.findViewById(R.id.search_bg);
    mSearchList = (ListView) fragmentView.findViewById(R.id.search_list);
    mSearchBox = (RelativeLayout) fragmentView.findViewById(R.id.search_box);
    mStartSearch = (ImageView) fragmentView.findViewById(R.id.btn_search);
    mStartSearch.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startSearch();
      }
    });
    mCancelSearch = (TextView) fragmentView.findViewById(R.id.tv_cancel);
    mCancelSearch.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        KeyBoardUtils.closeKeybord(mKeywords, getActivity());
        mSearchBg.setVisibility(View.GONE);
        mCompass.setVisibility(View.VISIBLE);
        mSearch.setVisibility(View.VISIBLE);
        mContext.showTabMenu();
      }
    });
    mSearch = (ImageView) fragmentView.findViewById(R.id.search);
    mSearch.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mSearchBg.setVisibility(View.VISIBLE);
//        mSearchBg.bringToFront();
        mCompass.setVisibility(View.GONE);
        mSearch.setVisibility(View.GONE);

        KeyBoardUtils.openKeybord(mKeywords, mContext);
        mKeywords.requestFocus();
        mMapView.removeAllOverlay();
        if (mContext.isShowPoiInfoBar) {
          resetFeatureStyle(markFeatureID);

          if (currentClickMark != null)
            mMapView.removeOverlay(currentClickMark);

          mContext.hidePoiInfoBar();
        }
        mContext.hideTabMenu();
      }
    });
    mScale = (Scale) fragmentView.findViewById(R.id.scale);
    mKeywords = (EditText) fragmentView.findViewById(R.id.tv_keywords);
    mKeywords.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

        startSearch();
      }

      @Override
      public void afterTextChanged(Editable s) {
      }
    });
    mCompass = (ImageView) fragmentView.findViewById(R.id.compass);
    mCompass.setOnClickListener(this);
    mF1 = (TextView) fragmentView.findViewById(R.id.f1);
    mF1.setOnClickListener(this);
    mB1 = (TextView) fragmentView.findViewById(R.id.b1);
    mB1.setOnClickListener(this);
    mElevator = (TextView) fragmentView.findViewById(R.id.elevator);
    mElevator.setOnClickListener(this);
    mWc = (TextView) fragmentView.findViewById(R.id.wc);
    mWc.setOnClickListener(this);
    mLocation = (TextView) fragmentView.findViewById(R.id.locate);
    mLocation.setOnClickListener(this);
    mZoomIn = (TextView) fragmentView.findViewById(R.id.map_zoom_in);
    mZoomIn.setOnClickListener(this);
    mZoomOut = (TextView) fragmentView.findViewById(R.id.map_zoom_out);
    mZoomOut.setOnClickListener(this);
    mShoot = (TextView) fragmentView.findViewById(R.id.shoot);
    mShoot.setOnClickListener(this);

    mWater = (TextView) fragmentView.findViewById(R.id.water);
    mWater.setOnClickListener(this);
    mExit = (TextView) fragmentView.findViewById(R.id.exit);
    mExit.setOnClickListener(this);


    mMapViewFrame = (FrameLayout) getActivity().findViewById(R.id.map_view);
    mMapOptions = new MapOptions(); // 该对象可设置一些地图手势操作
    mMapOptions.setSkewEnabled(false);//关闭俯仰
    mMapView = new MapView("default", mContext); //初始化MapView
    mMapViewFrame.addView(mMapView);
    mMapView.setMapOptions(mMapOptions);
    mMapView.setBackgroundColor(0xffebebeb);
    mMapView.initRatio(1.0F);
//    mMapView.setMaxZoomLevel(MAX_ZOOM);//不起作用
//    mMapView.setMinZoomLevel(MIN_ZOOM);//不起作用
    mScale.setMapView(mMapView);

    mMapView.setOnMapViewStatusChangedListener(new MapView.OnMapViewStatusListener() {//不起作用
      @Override
      public void onMapViewStatus(MapView mapView, MapView.MapViewStatus mapViewStatus, MapView.MapViewStatus mapViewStatus1) {
        switch (mapViewStatus1) {
          case Uninitializd:
            break;
          case Initialized:
            break;
          case Drawing:
            break;
          case Pausing:
            break;
          case Shutdown:
            break;
        }
      }
    });
//    mMapContainer.setOnTouchListener(new View.OnTouchListener() {
//      @Override
//      public boolean onTouch(View v, MotionEvent event) {
//        return false;//优化地图手势操作
//      }
//    });
    mMapView.setOverlayContainer(mContext.mMapContainer);
//    mMapView.setBackgroundResource(android.R.color.transparent);
    mMapView.setBackgroundResource(R.color.map_bg);
    mMapView.setMinAngle(45);
//    mMapView.setLayerOffset(mMapContainer);

    featureLayer = new FeatureLayer("poi");
    mMapView.addLayer(featureLayer);
    mMapView.setLayerOffset(featureLayer);
    mMapView.setOnSingleTapListener(new OnSingleTapListener() {
      @Override
      public void onSingleTap(MapView mapView, float x, float y) {

        if (isTapTooShort())
          return;

        isSingleTapTooShort = true;
        new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {
            isSingleTapTooShort = false;
          }
        }, 1000);


        if ((isNavigating || isShowFootPrint || isSearchCar) && !isSelectStartPoint)
          return;

        if (isSelectStartPoint) {
          selectStartPoint(x, y);
        } else {
          if (mContext.isShowPoiInfoBar) {
            resetFeatureStyle(markFeatureID);

            if (currentClickMark != null)
              mMapView.removeOverlay(currentClickMark);

//            mContext.showTabMenu();
            searchPOIByPoint(x, y);
          } else {
            searchPOIByPoint(x, y);
          }
        }


      }
    });
    locationMarkAnim = new LocationMarkAnim(mContext, mMapView);
    // 地图缩放监听
    mMapView.setOnZoomListener(new OnZoomListener() {
      @Override
      public void preZoom(MapView mapView, float v, float v2) {

      }

      @Override
      public void onZoom(MapView mapView, boolean b) {
        //compass是一个带有指南针的ImageView，通过调用地图的getRotate方法来获取地图的旋转角度，
        //并且赋值给指南针的ImageView即可
        mCompass.setRotation(-BigDecimal.valueOf(mMapView.getRotate()).floatValue());
        mCompass.invalidate();
        if (mScale != null) {
          mScale.postInvalidate();
        }

      }

      @Override
      public void postZoom(MapView mapView, float v, float v2) {
        mMapView.getOverlayController().refresh();


        Log.e("", "level = " + mMapView.getZoomLevel());
//        if (mMapView.GetZoomLevel() < 1.0100) {
//          if (currentMapZoom >= 2.0000) {
//            DialogUtils.showShortToast("不能再放大");
//
//          }
//        }
//        if (mMapView.getZoomLevel() > 11.9990) {
//          if (currentMapZoom <= 11.0000) {
//            DialogUtils.showShortToast("不能再缩小");
//          }
//        }

        currentMapZoom = mMapView.getZoomLevel();
      }
    });


    return fragmentView;
  }

  //显示顶条push
  public void showPush(String name) {
    if (Constant.H2大厅.equals(name)) {
      imPush.setBackgroundResource(R.drawable.infor_welcome);
    } else if (Constant.ICS实验室.equals(name)) {
      imPush.setBackgroundResource(R.drawable.infor_welcome_2);
    }

    imPush.setVisibility(View.VISIBLE);

    new Handler().postDelayed(new Runnable() {
      public void run() {
        //execute the task
        hidePush();
      }
    }, 5000);
  }

  public void showRedPoiMark(String name, double x, double y) {
    if (currentRedMark != null)
      mMapView.removeOverlay(currentRedMark);

    PoiRedMark p = new PoiRedMark(mContext, name, new PoiRedMark.OnClickListenerForMark() {
      @Override
      public void onMarkSelect(PoiRedMark mark) {
        if (H2大厅.equals(mark.getName())) {
          Intent intent = new Intent(mContext, PoiInfoActivity.class);
          intent.putExtra("type", 1);
          mContext.startActivity(intent);
        } else if (mark.getName().contains(ICS实验室)) {
          Intent intent = new Intent(mContext, PoiInfoActivity.class);
          intent.putExtra("type", 2);
          mContext.startActivity(intent);
        }
      }
    });
    p.init(new double[]{x, y});
    currentRedMark = p;
    mMapView.addOverlay(p);
    mMapView.getOverlayController().refresh();
  }

  //
  private void hidePush() {
    imPush.setVisibility(View.GONE);
  }

  //开始搜索
  private void initSearchView() {
    mCompass.setVisibility(View.GONE);
    mSearch.setVisibility(View.GONE);

  }

  //结束搜索
  private void hideSearchView() {
    mCompass.setVisibility(View.VISIBLE);
    mSearch.setVisibility(View.VISIBLE);
  }

  //  @Override
//  public void onClick(View v) {
//    switch (v.getId()){
//      case R.id.dialog:
//        //什么都不做
//        break;
//      case R.id.btn_map:
//        initMapScale();
////        dialog.setVisibility(View.GONE);
//        break;
//      case R.id.btn_foot:
//        setFootPrint();
//        initMapScale();
////        dialog.setVisibility(View.GONE);
//        break;
//      default:
//        break;
//    }
//  }
  /*
*  根据appkey初始化引擎
* */
  private void initEngine() {
    Engine engine = Engine.getInstance();
    engine.startWithLicense(Constant.APP_KEY, mContext); // 设置验证license

    if (Constant.useCash) {
      //添加带缓存的DataSource
      CacheAsyncHttpClient cacheAsyncHttpClient = new CacheAsyncHttpClient(Constant.SERVER_URL);
      FileCacheMethod fileCacheMethod = new FileCacheMethod(Constant.PATH_MAP_CASH);
      cacheAsyncHttpClient.reset(fileCacheMethod);
      mDataSource = new DataSource(cacheAsyncHttpClient);
    } else {
      mDataSource = new DataSource(Constant.SERVER_URL);
    }

    loadMap(FLOOR_ID_F1);

  }


  private boolean isTapTooShort() {//防止点太快闪退
    if (isSingleTapTooShort) {
      DialogUtils.showShortToast("点得太快了喔");
      return true;
    }

    isSingleTapTooShort = true;
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        isSingleTapTooShort = false;
      }
    }, 400);

    return false;
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
//    if (requestCode == Constant.startTakePic) {
//      Log.i(TAG, "拍摄完成，resultCode=" + requestCode);
//      Intent intent = new Intent(mContext, UploadActivity.class);
////    intent.putExtra()
//      startActivity(intent);
//    }
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.compass:
        rotataToNorth();
        break;
      case R.id.shoot:
        mContext.openCameraActivity();
        break;
      case R.id.locate:
        DataProviderCenter.getInstance().getPosition("", new HttpDataCallBack() {
          @Override
          public void onError(int errorCode) {
            if (Constant.isDebug)
              ErrorCode.showError(errorCode);
            Log.e("", "errorCode：" + errorCode);
          }

          @Override
          public void onComplete(Object content) {
            try {

              List<PositionJson> list = new ArrayList<PositionJson>(JSONArray.parseArray(content.toString(), PositionJson.class));
              addLocationMark(list.get(0).getX(), list.get(0).getY());


//              JSONObject jo1 = JSON.parseObject(content.toString());
//              JSONObject jo2 = jo1.getJSONObject("geometry");
//              JSONArray jo3 = jo2.getJSONArray("coordinates");
//              double x = jo3.getDoubleValue(0);
//              double y = jo3.getDoubleValue(1);

//              Types.Point point = mMapView.converToScreenCoordinate(x,y);
//              addLocationMark(x,y);
//              addMark(list.get(0).getX(), list.get(0).getY());


            } catch (IndexOutOfBoundsException e) {
              DialogUtils.showShortToast(e.getMessage());
            }

          }
        });
        break;
      case R.id.map_zoom_in:
        mMapView.zoomIn();
        break;
      case R.id.map_zoom_out:
        mMapView.zoomOut();
        break;
      case R.id.elevator:
        refeshPoiFilter(1);
        break;
      case R.id.wc:
        refeshPoiFilter(2);
        break;
      case R.id.exit:
        refeshPoiFilter(3);
        break;
      case R.id.water:
        refeshPoiFilter(4);
        break;
      case R.id.f1:
        loadMap(FLOOR_ID_F1);
        break;
      case R.id.b1:
        loadMap(Constant.FLOOR_ID_B1);
        break;

      case R.id.hall:
        showSearch4Poi(0);
        break;
      case R.id.meeting:
        showSearch4Poi(1);
        break;
      case R.id.lab:
        showSearch4Poi(2);
        break;
      case R.id.office:
        showSearch4Poi(3);
        break;
      case R.id.ele:
        showSearch4Fliter(1);
        break;
      case R.id.wat:
        showSearch4Fliter(4);
        break;
      case R.id.exi:
        showSearch4Fliter(3);
        break;
      case R.id.tol:
        showSearch4Fliter(2);
        break;

      default:
        break;
    }
  }

  private void showSearch4Poi(int index) {
    KeyBoardUtils.closeKeybord(mKeywords, mContext);
    mSearchBg.setVisibility(View.GONE);
    mCompass.setVisibility(View.VISIBLE);
    mSearch.setVisibility(View.VISIBLE);
//    addMark(x[index], y[index]);
    Types.Point point = mMapView.converToScreenCoordinate(x[index], y[index]);
    searchPOIByPoint((float) point.x, (float) point.y);
    initMapScale();
  }

  private void showSearch4Fliter(int index) {
    KeyBoardUtils.closeKeybord(mKeywords, mContext);
    mSearchBg.setVisibility(View.GONE);
    mCompass.setVisibility(View.VISIBLE);
    mSearch.setVisibility(View.VISIBLE);
    refeshPoiFilter(index);
    initMapScale();
  }

  private void refeshPoiFilterView() {
    if (mCurrentPoiFilter == 0) {
      mElevator.setBackgroundResource(R.drawable.btn_tab_elevator_nor);
      mWc.setBackgroundResource(R.drawable.btn_tab_toilet_nor);
      mExit.setBackgroundResource(R.drawable.btn_tab_entrance_nor);
      mWater.setBackgroundResource(R.drawable.btn_tab_water_nor);
    } else if (mCurrentPoiFilter == 1) {
      mElevator.setBackgroundResource(R.drawable.btn_tab_elevator_sel);
      mWc.setBackgroundResource(R.drawable.btn_tab_toilet_nor);
      mExit.setBackgroundResource(R.drawable.btn_tab_entrance_nor);
      mWater.setBackgroundResource(R.drawable.btn_tab_water_nor);
    } else if (mCurrentPoiFilter == 2) {
      mElevator.setBackgroundResource(R.drawable.btn_tab_elevator_nor);
      mWc.setBackgroundResource(R.drawable.btn_tab_toilet_sel);
      mExit.setBackgroundResource(R.drawable.btn_tab_entrance_nor);
      mWater.setBackgroundResource(R.drawable.btn_tab_water_nor);
    } else if (mCurrentPoiFilter == 3) {
      mElevator.setBackgroundResource(R.drawable.btn_tab_elevator_nor);
      mWc.setBackgroundResource(R.drawable.btn_tab_toilet_nor);
      mExit.setBackgroundResource(R.drawable.btn_tab_entrance_sel);
      mWater.setBackgroundResource(R.drawable.btn_tab_water_nor);
    } else if (mCurrentPoiFilter == 4) {
      mElevator.setBackgroundResource(R.drawable.btn_tab_elevator_nor);
      mWc.setBackgroundResource(R.drawable.btn_tab_toilet_nor);
      mExit.setBackgroundResource(R.drawable.btn_tab_entrance_nor);
      mWater.setBackgroundResource(R.drawable.btn_tab_water_sel);
    }
  }

  private void refeshFloorView(long id) {
    mCurrentFloor = id;
    if (mCurrentFloor == Constant.FLOOR_ID_B1) {
      mF1.setBackgroundResource(R.drawable.btn_floor_f1);
      mB1.setBackgroundResource(R.drawable.btn_tab_b1_sel);
    } else if (mCurrentFloor == FLOOR_ID_F1) {
      mF1.setBackgroundResource(R.drawable.btn_floor_f1_sel);
      mB1.setBackgroundResource(R.drawable.btn_tab_b1);
    } else {
      mF1.setBackgroundResource(R.drawable.btn_floor_f1);
      mB1.setBackgroundResource(R.drawable.btn_tab_b1);
    }
  }

  /*
  * TODO 加载地图 - 根据floorId
  * */
  private void loadMap(final long floorId) {
    if (mCurrentFloor == floorId)
      return;

    if (isShowFootPrint && floorId == Constant.FLOOR_ID_B1) {//行程不能切换
      DialogUtils.showShortToast("请返回地图页面再切换楼层");
      return;
    }

    if (isNavigating){
      mContext.showTabMenu();
      endNavigate();
    }

    if (huaweiLayer != null && floorId == Constant.FLOOR_ID_B1) {
      huaweiLayer.clearFeatures();
      mMapView.removeLayer(huaweiLayer);
    }

    if (isSelectStartPoint) {//防止闪退
      resetFeatureStyle(markFeatureID);
      markFeatureID = -1;
    }
    if (mMapView != null)
      mMapView.removeAllOverlay();

    showProgress(mHandler, "提示", "地图加载中，请稍后...");

    Log.w(TAG, "开始加载地图，floorId = " + floorId);
    isLoadingMap = true;
    mDataSource.requestPlanarGraph(floorId, new DataSource.OnRequestDataEventListener<PlanarGraph>() {
      @Override
      public void onRequestDataEvent(final DataSource.ResourceState resourceState, final PlanarGraph planarGraph) {

        if (resourceState == DataSource.ResourceState.ok ||
            resourceState == DataSource.ResourceState.CACHE) {
          Log.w(TAG, "resourceState = " + resourceState);
          refeshPoiFilter(0);
          Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
              mContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                  refeshFloorView(floorId);
                }
              });

              mMapView.drawPlanarGraph(planarGraph);
              mMapView.start();
              if (!isSearchCar)
                closeProgress();

              if (isSearchCar)
                findCar();

              if (mCurrentFloor == FLOOR_ID_F1) {
                mMapView.visibleLayerFeature("Area_text", "display", new Value("ICS办公区"), false);
                initMapScale();
              }

              if (!hasChoosenFootPrint) {
                mContext.runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                    if (mContext.shouldShow2choose1) {
                      mContext.dialog.setVisibility(View.VISIBLE);
                      hasChoosenFootPrint = true;
                    }
                  }
                });
              }


            }
          });
          thread.start();

        } else {
          closeProgress(mHandler);
          mHandler.post(new Runnable() {
            @Override
            public void run() {
              DialogUtils.showShortToast("floorID=" + floorId + "加载失败:" + resourceState);
            }
          });
        }

        isLoadingMap = false;
      }
    });
  }

  /*
 * TODO 加载地图 - 根据floorId
 * */
  public void loadMapAndShowFoot() {


    if (isSelectStartPoint) {//防止闪退
      resetFeatureStyle(markFeatureID);
      markFeatureID = -1;
    }
    if (mMapView != null)
      mMapView.removeAllOverlay();

    showProgress(mHandler, "提示", "地图加载中，请稍后...");

    isLoadingMap = true;
    mDataSource.requestPlanarGraph(FLOOR_ID_F1, new DataSource.OnRequestDataEventListener<PlanarGraph>() {
      @Override
      public void onRequestDataEvent(final DataSource.ResourceState resourceState, final PlanarGraph planarGraph) {

        if (resourceState == DataSource.ResourceState.ok ||
            resourceState == DataSource.ResourceState.CACHE) {
          Log.w(TAG, "resourceState = " + resourceState);
          refeshPoiFilter(0);
          Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
              mContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                  refeshFloorView(FLOOR_ID_F1);
                }
              });

              mMapView.drawPlanarGraph(planarGraph);
              mMapView.start();

              mMapView.visibleLayerFeature("Area_text", "display", new Value("ICS办公区"), false);

              mContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                  setFootPrint();
                }
              });


            }
          });
          thread.start();

        } else {
          closeProgress(mHandler);
          mHandler.post(new Runnable() {
            @Override
            public void run() {
              DialogUtils.showShortToast("floorID=" + floorId + "加载失败:" + resourceState);
            }
          });
        }
        isLoadingMap = false;
      }
    });
  }


  private void requestPOI() {
    mDataSource.requestCategories(Constant.MAP_ID, mCurrentFloor, new DataSource.OnRequestDataEventListener<DataList<CategoryModel>>() {
      @Override
      public void onRequestDataEvent(DataSource.ResourceState state, DataList<CategoryModel> data) {
        if (state != DataSource.ResourceState.ok)
          return;
        if (data.getSize() == 0)
          return;

        CategoryModel poi = data.getPOI(0);
        Log.i(TAG, "requestCategories " + CategoryModel.id.get(poi));

        data.drop();//如果返回的数据不需要持久保存，请用这种方式释放内存
      }
    });
  }

  private void refeshPoiFilter(int choose) {
    if (mCurrentPoiFilter == choose) {
      mCurrentPoiFilter = 0;
    } else {
      mCurrentPoiFilter = choose;
    }


    mMapView.visibleLayerAllFeature(Constant.FACILITY_LAYER, true);
    if (currentPoiMarks != null) {
      for (int i = 0; i < currentPoiMarks.size(); i++)
        mMapView.removeOverlay(currentPoiMarks.get(i));
    }

    if (mCurrentPoiFilter == 0) {
      currentPoiMarks = null;
      currentPoiMarks = new ArrayList<>();
//      mMapView.removeAllOverlay();
      mMapView.getOverlayController().refresh();
    } else if (mCurrentPoiFilter == 1) {
      searchPOIAndRefeshView(new long[]{Constant.电梯_ID, Constant.无障碍电梯_ID});
    } else if (mCurrentPoiFilter == 2) {
      searchPOIAndRefeshView(new long[]{Constant.男洗手间_ID, Constant.女洗手间_ID, Constant.残障洗手间_ID});
    } else if (mCurrentPoiFilter == 3) {
      searchPOIAndRefeshView(new long[]{Constant.安全出口_ID, Constant.建筑物大门});
    } else if (mCurrentPoiFilter == 4) {
      searchPOIAndRefeshView(new long[]{Constant.茶水间_ID});
//      mMapView.visibleLayerAllFeature(Constant.FACILITY_LAYER, false);
//
//      addOverlaysByFeatures(mMapView.searchFeature(Constant.FACILITY_LAYER, Constant.FACILITY_KEY_NAME, new Value("茶水间")));
//      mMapView.visibleLayerFeature(Constant.FACILITY_LAYER, Constant.FACILITY_KEY_NAME, new Value("茶水间"), true);
    }

    refeshPoiFilterView();
  }


  private void searchPOIAndRefeshView(long[] categories) {
    mMapView.visibleLayerAllFeature(Constant.FACILITY_LAYER, false);

    for (int i = 0; i < categories.length; i++) {
      addOverlaysByFeatures(mMapView.searchFeature(Constant.FACILITY_LAYER, Constant.FACILITY_KEY_CAT, new Value(categories[i])));
      mMapView.visibleLayerFeature(Constant.FACILITY_LAYER, Constant.FACILITY_KEY_CAT, new Value(categories[i]), true);
    }

    initMapScale();
  }

  //  private Feature getPOIFeature(Feature feature){
//    for (int i = 0; i < xxx.length; i++) {
////      if (MapParamUtils.getId(feature)==)
//
//    }
//  }
  private boolean hasInfo(Feature feature) {//判断是否显示poi bar
    if (feature == null) {//没有搜索到feature
      return false;
    }
    long id = MapParamUtils.getId(feature);
    String display = MapParamUtils.getDisplay(feature);
    String name = MapParamUtils.getName(feature);

    if (id == 1263037 || id == 1270023 || id == Constant.空地_POI_ID) {
      return false;
    }
    if (display != null) {
      if (display.contains("办公楼"))
        return false;
    }
    if (name == null) {
      return false;
    }

    return true;
  }

  private void searchPOIByPoint(float x, float y) {
//    List<Feature> features =mMapView.searchFeature(Constant.AREA_LAYER,"display",new Value("H114"));

    final Feature feature = mMapView.selectFeature(x, y);
    if (!hasInfo(feature)) {
      mContext.showTabMenu();
      return;
    }

    Types.Point point = mMapView.converToWorldCoordinate(x, y);
    endX = point.x;//feature.getCentroid().getX();//可能
    endY = point.y;//feature.getCentroid().getY();
    endName = MapParamUtils.getDisplay(feature);
    long poiID = MapParamUtils.getId(feature);
    if (Constant.isDebug) {
      DialogUtils.showShortToast("poiID=" + poiID);
    }
//    mDataSource.requestPOI(poiID, new DataSource.OnRequestDataEventListener<LocationModel>() {
//      @Override
//      public void onRequestDataEvent(DataSource.ResourceState resourceState, final LocationModel locationModel) {
//        if (resourceState == DataSource.ResourceState.ok ||
//            resourceState == DataSource.ResourceState.CACHE) {
//          Log.w(TAG, "resourceState = " + resourceState);


//          if (name!=null)
    mContext.setPoiInfoBar(feature);
    markFeatureID = MapParamUtils.getId(feature);
    if (Constant.onSingleTap_changeColor) {
      setFeatureColor(feature, 0xffFFB5B5);
    }
//          xx = feature.getCentroid().getX();
//          yy = feature.getCentroid().getY();

    if (isSearchCar)//寻车页面对话框延迟消失
      closeProgress();

    addMark(point.x, point.y);

    mContext.showPoiInfoBar(MapParamUtils.getCategoryId(feature));
//        } else
//          DialogUtils.showShortToast("poi数据请求失败。");
//      }
//    });


  }

  //地理围栏需要
  public String getPOINameByPoint(float x, float y) {//屏幕坐标
//    List<Feature> features =mMapView.searchFeature(Constant.AREA_LAYER,"display",new Value("H114"));

    final Feature feature = mMapView.selectFeature(x, y);
    if (feature == null)
      return "";


    String name = MapParamUtils.getDisplay(feature);

    return name == null ? "" : name;
  }

  private void selectStartPoint(float x, float y) {

    final Feature feature = mMapView.selectFeature(x, y);
    if (feature == null || MapParamUtils.getName(feature) == null || MapParamUtils.getName(feature).contains("办公楼"))
      return;

//    long poiID = MapParamUtils.getId(feature);
//    mDataSource.requestPOI(poiID, new DataSource.OnRequestDataEventListener<LocationModel>() {
//      @Override
//      public void onRequestDataEvent(DataSource.ResourceState resourceState, LocationModel locationModel) {
//        if (resourceState == DataSource.ResourceState.ok ||
//            resourceState == DataSource.ResourceState.CACHE) {
//          Log.w(TAG, "resourceState = " + resourceState);

    mContext.setStartPoiInfo(feature);
    resetFeatureStyle(markFeatureID);//若切换楼层会闪退
    markFeatureID = MapParamUtils.getId(feature);
    startFeatureID = MapParamUtils.getId(feature);
    startFloorID = Feature.planar_graph.get(feature);
    startName = MapParamUtils.getName(feature);
    Types.Point point = mMapView.converToWorldCoordinate(x, y);
    startX = point.x;
    startY = point.y;
    if (Constant.onSingleTap_changeColor) {
      setFeatureColor(feature, 0xffFFB5B5);
    }


    addMark(point.x, point.y, Mark.START);

//        } else
//          DialogUtils.showShortToast("poi数据请求失败。");
//      }
//    });

  }


  public void resetFeatureStyle(long featureID) {
    mMapView.resetOriginStyle("Area", featureID);
  }

  public void resetFeatureStyle(String layerName, long featureID) {
    mMapView.resetOriginStyle(layerName, featureID);
  }

  private void setFeatureColor(Feature feature, int color) {
    mMapView.setRenderableColor("Area", LocationModel.id.get(feature), color);
  }

  private void setFeatureColor(long featureID, int color) {
    mMapView.setRenderableColor("Area", featureID, color);
  }

  private void setFeatureColor(String layerName, long featureID, int color) {
    mMapView.setRenderableColor(layerName, featureID, color);
  }

  public void addMark(double x, double y, int type) {
    if (isSelectStartPoint) {//添加起点时保留终点
      mMapView.removeOverlay(startMark);
      startMark=null;
    } else {
      mMapView.removeAllOverlay();
    }

    mMapView.getOverlayController().refresh();

    if (type == Mark.START) {
      startMark = new Mark(mContext, Mark.START);
      startMark.init(new double[]{x, y});
      mMapView.addOverlay(startMark);
    } else {
      endMark = new Mark(mContext, type);
      endMark.init(new double[]{x, y});
      mMapView.addOverlay(endMark);
    }

    mMapView.getOverlayController().refresh();
  }

  public void moveNavMark(double xs, double ys,double xe, double ye) {
    mMapView.removeOverlay(startMark);
    mMapView.removeOverlay(endMark);
    startMark = null;
    endMark = null;

      startMark = new Mark(mContext, Mark.START);
      startMark.init(new double[]{xs, ys});
      mMapView.addOverlay(startMark);

      endMark = new Mark(mContext, Mark.END);
      endMark.init(new double[]{xe, ye});
      mMapView.addOverlay(endMark);


    mMapView.getOverlayController().refresh();
  }

  public void addMark(double x, double y) {//wgs84坐标
//    if (Constant.isDebug)
//      DialogUtils.showLongToast("location:"+x+" "+y);

//    mMapView.removeAllOverlay();
    if (currentClickMark != null)
      mMapView.removeOverlay(currentClickMark);
    Mark mark = new Mark(mContext);
    mark.init(new double[]{x, y});
    currentClickMark = mark;
    mMapView.addOverlay(mark);
    mMapView.getOverlayController().refresh();
  }

  public void addLocationMark(double x, double y) {//wgs84坐标
//    mMapView.removeAllOverlay();

    checkPoiPush(LocateTimerService.getCurrentLocationArea());
//    if (Constant.isDebug)
//      DialogUtils.showLongToast("location:" + x + " " + y);

//    LocationMark mark = new LocationMark(mContext);
//    mark.init(new double[]{x, y});
    //
//    mMapView.addOverlay(mark);
//    mMapView.getOverlayController().refresh();


    locationMarkAnim.init(new double[]{x, y});
    locationMarkAnim.animTo(x, y);

  }

  private void checkPoiPush(String name) {
    if (isNavigating)
      return;

    if ("".equals(name) || name == null)
      return;

    if (H2大厅.equals(name)) {
      if (!isShowFootPrint)
        showPush(Constant.H2大厅);
      showRedPoiMark(Constant.H2大厅, x[0], y[0]);
    } else if (name.contains(ICS实验室)) {
      if (!isShowFootPrint)
        showPush(Constant.ICS实验室);
      showRedPoiMark(Constant.ICS实验室, x[2], y[2]);
    } else if (会议室.equals(name)) {
      showRedPoiMark(Constant.会议室, x[1], y[1]);
    } else if (ICS办公区.equals(name)) {
      showRedPoiMark(Constant.ICS办公区, x[3], y[3]);
    } else {
      if (currentRedMark != null)
        mMapView.removeOverlay(currentRedMark);
    }
  }

  //只显示参观线路
  private void showRoute() {
    long[] routeID = new long[]{123};
    for (int i = 0; i < routeID.length; i++) {
      mMapView.visibleLayerFeature(Constant.AREA_LAYER, Constant.AREA_KEY, new Value(routeID[i]), true);
    }
  }

  private void startSearch() {
    final String keyWords = mKeywords.getText().toString();
    if ("".equals(keyWords)) {
      mSearchList.setVisibility(View.GONE);
      mSearchDef.setVisibility(View.VISIBLE);
      mSearchDef2.setVisibility(View.VISIBLE);
      mSearchNull.setVisibility(View.GONE);
      return;
    }

//    List<Feature> features = mMapView.searchFeature("Area_text","name",new Value(keyWords));

    mDataSource.search(keyWords, 1, 10, new long[]{FLOOR_ID_F1}, new long[]{}, new DataSource.OnRequestDataEventListener<LocationPagingList>() {
      @Override
      public void onRequestDataEvent(DataSource.ResourceState state, LocationPagingList data) {
        if (state != DataSource.ResourceState.ok && state != DataSource.ResourceState.CACHE) {
          ErrorCode.showError(ErrorCode.CODE_NO_INTERNET);
          return;
        }
        if (data.getSize() == 0) {
          mSearchList.setVisibility(View.GONE);
          mSearchDef.setVisibility(View.GONE);
          mSearchDef2.setVisibility(View.GONE);
          mSearchNull.setVisibility(View.VISIBLE);
          return;
        }

        List<LocationModel> locationModels = new ArrayList<LocationModel>();
        for (int i = 0; i < data.getSize(); i++) {
          if (LocationModel.display.get(data.getPOI(i))!=null&&!"".equals(LocationModel.display.get(data.getPOI(i)))) {
            locationModels.add(data.getPOI(i));
          }


        }

        mSearchResultAdapter = new SearchResultAdapter(getActivity(), locationModels, new SearchResultAdapter.OnItemClickListener() {
          @Override
          public void onClicked(LocationModel locationModel) {
            KeyBoardUtils.closeKeybord(mKeywords, mContext);
            mSearchBg.setVisibility(View.GONE);
            mKeywords.setText("");

//            Mark mark = new Mark(mContext);
            Coordinate c = getCoordinate(locationModel);
            if (c == null)
              return;

            Feature feature = mMapView.selectFeature(MapParamUtils.getId(locationModel));
            mContext.showPoiInfoBar(MapParamUtils.getCategoryId(feature));
            addMark(c.getX(), c.getY());
            mCompass.setVisibility(View.VISIBLE);
            mSearch.setVisibility(View.VISIBLE);
            initMapScale();
          }
        });
        mSearchList.setAdapter(mSearchResultAdapter);
        mSearchList.setVisibility(View.VISIBLE);
        mSearchNull.setVisibility(View.GONE);
        mSearchDef.setVisibility(View.GONE);
        mSearchDef2.setVisibility(View.GONE);
//        data.drop();
      }
    });
  }

  public void searchPeopleName(String name) {
    mDataSource.search(name, 1, 1, new long[]{FLOOR_ID_F1}, new long[]{}, new DataSource.OnRequestDataEventListener<LocationPagingList>() {
      @Override
      public void onRequestDataEvent(DataSource.ResourceState state, LocationPagingList data) {
        if (state != DataSource.ResourceState.ok && state != DataSource.ResourceState.CACHE)
          return;
        if (data.getSize() == 0) {
          return;
        }
        mContext.showPoiInfoBar();
        Coordinate c = getCoordinate(data.getPOI(0));
        Types.Point point = mMapView.converToScreenCoordinate(c.getX(), c.getY());
        resetFeatureStyle(markFeatureID);

        searchPOIByPoint((float) point.x, (float) point.y);
//        addMark(c.getX(), c.getY());
      }
    });
  }

  private void addOverlaysByMarks(List<Mark> markList) {
    mMapView.removeAllOverlay();
    for (Mark mark : markList) {
      mMapView.addOverlay(mark);
    }
  }


  private void addOverlaysByFeatures(List<Feature> featureList) {
    if (featureList == null)
      return;
//    Mark mark;
//    mMapView.removeAllOverlay();
    for (Feature feature : featureList) {
      Mark mark = new Mark(mContext);
      mark.init(new double[]{feature.getCentroid().getX(), feature.getCentroid().getY()});
      mMapView.addOverlay(mark);
      currentPoiMarks.add(mark);
    }
    mMapView.getOverlayController().refresh();
  }


  private Coordinate getCoordinate(LocationModel locationModel) {
    if (locationModel == null)
      return null;

    Feature feature = mMapView.selectFeature(LocationModel.id.get(locationModel));
    if (feature != null)
      return feature.getCentroid();
    else
      return null;
  }

  public void endNavigate() {
    if (navigateLayer != null) {
      navigateLayer.clearFeatures();  //先把之前的导航线清理掉
      navigateManager.clear();
      navigateManager.drop();
      mMapView.removeLayer(navigateLayer);
    }
    if (startFeatureID != -1) {
      resetFeatureStyle(startFeatureID);
      startFeatureID = -1;
    }
    mMapView.removeAllOverlay();
    mMapView.getOverlayController().refresh();
    isNavigating = false;
    if (mContext.im_poi != null)
      mContext.im_poi.setText("");

    startMark = null;
    endMark = null;
    isSelectStartPoint = false;
  }

  public void startNavigate() {//final Feature startFeatureID, final double toX, final double toY, final long toFloorID
    isNavigating = true;
    navigateManager = new NavigateManager();
    navigateLayer = new FeatureLayer("navigate");
    mMapView.addLayer(navigateLayer);
    mMapView.setLayerOffset(navigateLayer);


    navigateManager.setOnNavigateComplete(new NavigateManager.OnNavigateComplete() {
      @Override
      public void onNavigateComplete(NavigateManager.NavigateState navigateState, FeatureCollection featureCollection) {
        navigateLayer.clearFeatures();  //先把之前的导航线清理掉
        navigateLayer.addFeatures(featureCollection); //重新添加新的导航线
        Feature s=featureCollection.getFirstFeature();
        Feature e=featureCollection.getEedFeature();
        Coordinate firstCoordinate = navigateManager.getCoordinateByFeature(s, 0);
        Coordinate endCoordinate = navigateManager.getCoordinateByFeature(e, navigateManager.getFeatureLength(e) - 1);

        moveNavMark(firstCoordinate.getX(),firstCoordinate.getY(),endCoordinate.getX(),endCoordinate.getY());


        mContext.runOnUiThread(new Runnable() {
          @Override
          public void run() {
            mContext.showNavigateInfoBar(startName, endName, (int) navigateManager.getTotalLineLength() + "");//显示导航详情
          }
        });
      }
    });
    mMapView.setOnChangePlanarGraph(new MapView.OnChangePlanarGraph() {
      @Override
      public void onChangePlanarGraph(PlanarGraph oldPlanarGraph, PlanarGraph newPlanarGraph, long oldPlanarGraphId, long newPlanarGraphId) {
//        long floorId = newPlanarGraphId;
        navigateLayer = new FeatureLayer("navigate");
        mMapView.addLayer(navigateLayer);
        mMapView.setLayerOffset(navigateLayer);

        if (navigateManager != null) {
          navigateManager.switchPlanarGraph(oldPlanarGraphId);
        }
      }
    });
//    long poiID = MapParamUtils.getId(startFeatureID);
//    mDataSource.requestPOI(poiID, new DataSource.OnRequestDataEventListener<LocationModel>() {
//      @Override
//      public void onRequestDataEvent(DataSource.ResourceState resourceState, LocationModel locationModel) {
//        if (resourceState == DataSource.ResourceState.ok ||
//            resourceState == DataSource.ResourceState.CACHE) {
//          Log.w(TAG, "resourceState = " + resourceState);

    resetFeatureStyle(startFeatureID);

//          long toFloorId = LocationModel.parent.get(locationModel);

    navigateManager.navigation(startX, startY, startFloorID, endX, endY, toFloorID); // TODO 请求导航线
//        }
//      }
//    });


  }

  public void initMapScale() {
//    mMapView.initRatio(1.0F);

//    Types.Point point = mMapView.converToScreenCoordinate(initX, initY);
//    mMapView.rotate(point, -mMapView.getRotate(),true,500);
//    Coordinate coordinate = new Coordinate(initX, initY);
//    mMapView.moveToPoint(coordinate, true, 500);


//    mMapView.zoom(initScale);

    mMapView.moveToRect(12697080.571, 2588843.728, 12697195.929, 2588958.557);
    mMapView.getOverlayController().refresh();
  }

  private void rotataToNorth() {

//    Coordinate coordinate = new Coordinate(initX,initY);
//    Types.Point point = new Types.Point(initX,initY);
    Types.Point point = mMapView.converToScreenCoordinate(initX, initY);
//    mMapView.moveToPoint(coordinate,true,500);
    mMapView.rotate(point, -mMapView.getRotate(), true, 500);

//    new Handler().postDelayed(new Runnable() {
//      @Override
//      public void run() {
//        initMapScale();
//      }
//    },550);
    new Handler().postDelayed(new Runnable() {//旋转指北针
      public void run() {
        mCompass.setRotation(-BigDecimal.valueOf(mMapView.getRotate()).floatValue());
        mCompass.invalidate();
        initMapScale();
        if (mScale != null) {
          mScale.postInvalidate();
        }
      }
    }, 700);


    mMapView.getOverlayController().refresh();
  }


  private void initHuaWeiLayer() {//初始化poi结点
//    if (huaweiLayer == null) {
    huaweiLayer = new FeatureLayer("poi"); //新建一个放置定位点的图层
    mMapView.addLayer(huaweiLayer);  //把这个图层添加至MapView中
    mMapView.setLayerOffset(huaweiLayer); //让这个图层获取到当前地图的坐标偏移
//    }

    for (int i = 0; i < xxx.length; i++) {
      Types.Point point1 = mMapView.converToScreenCoordinate(xxx[i], yyy[i]);
      Feature fea = mMapView.selectFeature((float) point1.x, (float) point1.y);
      MapElement mapElement = new MapElement(); //设置它的属性
      mapElement.addElement("id", new BasicElement((long) (i + 1 + 100))); //设置id
//      Geometry geometry = GeometryFactory.createLineString(fea.getGeometry().getCoordinates());

      Feature feature = new Feature(fea.getGeometry(), mapElement); //创建Feature
      huaweiLayer.addFeature(feature); //把这个Feature添加到FeatureLayer中
    }
  }

  public void setFootPrint() {
//    if (huaweiLayer == null)
//      initHuaWeiLayer();
    initMapScale();
    isShowFootPrint = true;
    mContext.hideTabMenu();
    mSearch.setVisibility(View.GONE);
    mF1.setVisibility(View.GONE);
    mB1.setVisibility(View.GONE);
    mContext.titleBar.show(null, "行程", null);
    mContext.titleBar.setOnTitleClickListener(new TitleBar.OnTitleClickListener() {
      @Override
      public void onLeft() {
        resetFootPrint();
      }

      @Override
      public void onRight() {

      }
    });


//    int foot_room = 0xff0196d0;
//    for (int i = 0; i < xxx.length; i++) {
//      setFeatureColor("poi",(long)(i + 1 + 100), foot_room);
//    }
//    mMapView.visibleLayerAllFeature("poi",true);

//    int foot_road = 0xff36bef0;
//    setFeatureColor("Area",1270023L, foot_road);
//    add4PoiMark();

//    showProgress(mHandler, "加载行程", "请稍后...");
//    Thread thread = new Thread(new Runnable() {
//      @Override
//      public void run() {
    //poiID  大厅1284129  1264643 tai  办公区1284130  ics实验室1270043 1264378
    // 会议室 1270042  1264107 1284127 1264105路1270023
//    setFeatureColor(1284129,R.color.foot_room);

    add4PoiMark();
    int foot_room = 0xff36b1f0;
    for (int i = 0; i < RoutePoi.pois.length; i++) {
      setFeatureColor(RoutePoi.pois[i], foot_room);
//          mMapView.visibleLayerFeature("Area", "id", new Value(RoutePoi.pois[i]), false);
    }

    setFeatureColor(1284130, foot_room);
    setFeatureColor(1264643, foot_room);

    setFeatureColor(1284129, foot_room);

    setFeatureColor(1270043, foot_room);
    setFeatureColor(1264378, foot_room);

    setFeatureColor(1270042, foot_room);
    setFeatureColor(1264107, foot_room);
    setFeatureColor(1284127, foot_room);
    setFeatureColor(1264105, foot_room);


    int foot_road = 0xff75d4ea;
    setFeatureColor(1270023, foot_road);
    closeProgress();
//      }
//    });
//    thread.start();


  }

  private void add4PoiMark() {

    String[] name = new String[]{Constant.H2大厅, Constant.会议室, Constant.ICS实验室, Constant.ICS办公区};
    for (int i = 0; i < x.length; i++) {
      Types.Point point = mMapView.converToScreenCoordinate(x[i], y[i]);
      PoiGreyMark p = new PoiGreyMark(mContext, name[i], new PoiGreyMark.OnClickListenerForMark() {
        @Override
        public void onMarkSelect(PoiGreyMark mark) {
          String name = mark.getName();
          if (name == null)
            return;

          if (H2大厅.equals(name)) {
            mContext.startActivity(new Intent(mContext, ActivityHall.class));
          } else if (会议室.equals(name)) {
            mContext.startActivity(new Intent(mContext, ActivityMeeting.class));
          } else if (ICS办公区.equals(name)) {
            mContext.startActivity(new Intent(mContext, ActivityOffice.class));
          } else if (name.contains(ICS实验室)) {
            mContext.startActivity(new Intent(mContext, ActivityLab.class));
          }
        }
      });
      p.init(new double[]{x[i], y[i]});
      mMapView.addOverlay(p);
    }


  }

  public void resetFootPrint() {
    isShowFootPrint = false;
    mMapView.removeAllOverlay();

    mSearch.setVisibility(View.VISIBLE);
    mF1.setVisibility(View.VISIBLE);
    mB1.setVisibility(View.VISIBLE);
    mContext.titleBar.hide();
    mContext.showTabMenu();

//    huaweiLayer.clearFeatures();
//    mMapView.removeLayer(huaweiLayer);

//    int t_color=0x00ffffff;
//    for (int i = 0; i < xxx.length; i++) {
//      setFeatureColor("poi",(long)(i + 1 + 100), t_color);
//    }


//    mMapView.visibleLayerAllFeature("poi",false);


//    huaweiLayer = null;
//    int nocolor = 0x00ffffff;
//    for (int i = 0; i < x.length; i++) {
//      setFeatureColor((long)(i+1+100),nocolor);
//    }
    resetFeatureStyle(1270023L);

//    showProgress(mHandler, "加载地图", "请稍后...");
//    Thread thread = new Thread(new Runnable() {
//      @Override
//      public void run() {
    //poiID  大厅1284129  1264643 tai  办公区1284130  ics实验室1270043 1264378
    // 会议室 1270042  1264107 1284127 1264105路1270023
    for (int i = 0; i < RoutePoi.pois.length; i++) {
      resetFeatureStyle(RoutePoi.pois[i]);
//          mMapView.visibleLayerFeature("Area", "id", new Value(RoutePoi.pois[i]), true);
    }
    resetFeatureStyle(1284129);
    resetFeatureStyle(1264643);

    resetFeatureStyle(1284130);

    resetFeatureStyle(1270043);
    resetFeatureStyle(1264378);

    resetFeatureStyle(1270042);
    resetFeatureStyle(1264107);
    resetFeatureStyle(1284127);
    resetFeatureStyle(1264105);


    resetFeatureStyle(1270023);

    closeProgress();
//      }
//    });
//    thread.start();
  }

  public void moveToCar(ParkInfo p) {
    isSearchCar = true;
    if (mCurrentFloor == Constant.FLOOR_ID_B1) {
      if (isSearchCar)
        findCar();
    } else {
      loadMap(Constant.FLOOR_ID_B1);
    }
//    mCurrentFloor = 0;
    parkInfo = p;

  }


  private void findCar() {
    List<Feature> features = mMapView.searchFeature(Constant.AREA_LAYER, "display", new Value(parkInfo.getCarPosition()));//

    if (features == null)
      return;

//    Mark mark = new Mark(mContext);
    final Coordinate c = features.get(0).getCentroid();
    mMapView.moveToPoint(c);//不能加动画否则converToScreenCoordinate不对
    mMapView.zoom(9);//放大
    mMapView.getOverlayController().refresh();

    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
      public void run() {
        Types.Point p = mMapView.converToScreenCoordinate(c.getX(), c.getY());
        searchPOIByPoint((float) p.x, (float) p.y);
      }
    }, 500);

//    addMark(c.getX(), c.getY());
  }

}
