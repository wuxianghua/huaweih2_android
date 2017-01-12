package com.palmap.demo.huaweih2.fragment;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.palmap.demo.huaweih2.ActivityHall;
import com.palmap.demo.huaweih2.ActivityLab;
import com.palmap.demo.huaweih2.ActivityMeeting;
import com.palmap.demo.huaweih2.ActivityOffice;
import com.palmap.demo.huaweih2.HuaWeiH2Application;
import com.palmap.demo.huaweih2.LocateTimerService;
import com.palmap.demo.huaweih2.MainActivity;
import com.palmap.demo.huaweih2.PoiInfoActivity;
import com.palmap.demo.huaweih2.R;
import com.palmap.demo.huaweih2.adapter.SearchResultAdapter;
import com.palmap.demo.huaweih2.http.ErrorCode;
import com.palmap.demo.huaweih2.model.Floor;
import com.palmap.demo.huaweih2.model.ParkInfo;
import com.palmap.demo.huaweih2.model.PoiImgList;
import com.palmap.demo.huaweih2.model.RoutePoi;
import com.palmap.demo.huaweih2.other.Constant;
import com.palmap.demo.huaweih2.util.DialogUtils;
import com.palmap.demo.huaweih2.util.KeyBoardUtils;
import com.palmap.demo.huaweih2.util.LogUtils;
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
import com.palmaplus.nagrand.view.gestures.OnDoubleTapListener;
import com.palmaplus.nagrand.view.gestures.OnSingleTapListener;
import com.palmaplus.nagrand.view.gestures.OnZoomListener;
import com.palmaplus.nagrand.view.layer.FeatureLayer;

import java.math.BigDecimal;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

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


    public LocationMarkAnim locationMarkAnim;
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
    public LinearLayout mSearchBg;
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
    public String startName;
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
    public static long mCurrentFloor = -1; // 当前地图显示楼层id  0-B1  1-F1
    private int mCurrentPoiFilter = 0; // 当前poi过滤 0-无 1-电梯 2-厕所 3-出口 4-盥洗室
    public long mFloorId;

    private ImageView imPush;
    public FrameLayout mMapViewFrame;
    public MapView mMapView;

    public DataSource mDataSource;
    private MapOptions mMapOptions;
    private FeatureLayer featureLayer;
    public boolean isLoadingMap; // 正在加载地图,不添加显示定位点

    private Handler mHandler;
    public static boolean hasLocated = false;//有定位点
    public static boolean isSelectStartPoint = false;//是否正在选择起点模式，控制mapview单击响应
    public static boolean isNavigating = false;//是否正在导航，控制mapview单击响应
    public static boolean isShowFootPrint = false;//是否正在显示行程，控制地图显示
    public static boolean hasChoosenFootPrint = false;//是否显示过行程地图选择框
    public static boolean isSearchCar = false;//是否巡车页面
    public static boolean isNavigateCar = false;//是否反向巡车（规划路线）
    private boolean isShowSearchResult = false;//切换楼层是否显示搜索结果

    long searchResultID;

    private Mark startMark;
    private Mark endMark;
    public Mark mark;

    public ParkInfo parkInfo;//停车信息
    private double currentMapZoom;//当前缩放级别

    private boolean isSingleTapTooShort = false;//不让连续点击
    final int RIGHT = 0;
    final int LEFT = 1;
    final int UP = 2;
    final int DOWN = 3;
    private GestureDetector gestureDetector;

    RelativeLayout footInfo;
    ImageView footDown;
    LocationPagingList searchResultData;//搜索结果，必要时释放

    private GestureDetector.OnGestureListener onGestureListener =
            new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                       float velocityY) {
                    float x = e2.getX() - e1.getX();
                    float y = e2.getY() - e1.getY();

//          if (x > 0) {
//            doResult(RIGHT);
//          } else if (x < 0) {
//            doResult(LEFT);
//          } else
                    if (y > 0) {
                        doResult(DOWN);
                    } else if (y < 0) {
                        doResult(UP);
                    }
                    return true;
                }
            };
    private boolean canPush = true;//是否显示欢迎信息
    private int isSearch4Poi = -1;
    private boolean hasShowArriveEnd = false;//
    private boolean hasAddRedMark = true;//地图切行程是否添加红气泡
    private boolean isTrip;

    public void doResult(int action) {

        switch (action) {
            case RIGHT:
//        System.out.println("go right");
                break;

            case LEFT:
//        System.out.println("go left");
                break;
            case UP:
                break;
            case DOWN:
//                hideFootInfo();
                break;
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        mHandler = new Handler(Looper.getMainLooper());
        mContext = (MainActivity) getActivity();
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


        footDown = (ImageView) fragmentView.findViewById(R.id.foot_down);
        gestureDetector = new GestureDetector(mContext, onGestureListener);
        footDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideFootInfo();
            }
        });
        footInfo = (RelativeLayout) fragmentView.findViewById(R.id.foot_info);
        footInfo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
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
                mKeywords.setText("");
                if (searchResultData != null && searchResultData.getRef_Count() > 0)
                    searchResultData.drop();
            }
        });
        mSearch = (ImageView) fragmentView.findViewById(R.id.search);
        mSearch.setClickable(false);//防止欢迎页面点到
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNavigating) {
                    mContext.showTabMenu();
                    endNavigate();
                }

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


        mScale = (Scale) fragmentView.findViewById(R.id.scale);
        mScale.setMapView(mMapView);

//    mMapView.setLayerOffset(mMapContainer);
        //        mMapView.setOnMapViewStatusChangedListener(new MapView.OnMapViewStatusListener() {//不起作用
//            @Override
//            public void onMapViewStatus(MapView mapView, MapView.MapViewStatus mapViewStatus, MapView.MapViewStatus mapViewStatus1) {
//                switch (mapViewStatus1) {
//                    case Uninitializd:
//                        break;
//                    case Initialized:
//                        break;
//                    case Drawing:
//                        break;
//                    case Pausing:
//                        break;
//                    case Shutdown:
//                        break;
//                }
//            }
//        });
//    mMapContainer.setOnTouchListener(new View.OnTouchListener() {
//      @Override
//      public boolean onTouch(View v, MotionEvent event) {
//        return false;//优化地图手势操作
//      }
//    });


        mMapView.setOnSingleTapListener(new OnSingleTapListener() {
            @Override
            public void onSingleTap(MapView mapView, float x, float y) {

                if (isTapTooShort())
                    return;

                isSingleTapTooShort = true;
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isSingleTapTooShort = false;
                    }
                }, 500);


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
                LocationMarkAnim.currentMapDegree = BigDecimal.valueOf(mMapView.getRotate()).floatValue();
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

        mMapView.setOnDoubleTapListener(new OnDoubleTapListener() {
            @Override
            public boolean onDoubleTap(MapView mapView, float v, float v1) {
                if (mScale != null) {
                    mScale.postInvalidate();
                }
                return true;
            }
        });

        mMapView.setOnChangePlanarGraph(new MapView.OnChangePlanarGraph() {
            @Override
            public void onChangePlanarGraph(PlanarGraph oldPlanarGraph, PlanarGraph newPlanarGraph, long oldPlanarGraphId, long newPlanarGraphId) {
                if (isTrip){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setFootPrint();
//                            mContext.hidePoiInfoBar();
                            isTrip = false;

                        }
                    });
                }
            }
        });

        return fragmentView;
    }

    private void hideFootInfo() {

    /*
                AnimationSet相当于一个动画的集合，true表示使用Animation的interpolator
                false则是使用自己的。
                Interpolator 被用来修饰动画效果，定义动画的变化率，可以使存在的动画效果
                accelerated(加速)，decelerated(减速),repeated(重复),bounced(弹跳)等。
             */

        AnimationSet animationSet = new AnimationSet(true);
            /*
                    Animation还有几个方法
                    setFillAfter(boolean fillAfter)
                    如果fillAfter的值为真的话，动画结束后，控件停留在执行后的状态
                    setFillBefore(boolean fillBefore)
                    如果fillBefore的值为真的话，动画结束后，控件停留在动画开始的状态
                    setStartOffset(long startOffset)
                    设置动画控件执行动画之前等待的时间
                    setRepeatCount(int repeatCount)
                    设置动画重复执行的次数
             */

        TranslateAnimation translateAnimation = new TranslateAnimation(
                //X轴初始位置
                Animation.ABSOLUTE, 0.0f,
                //X轴移动的结束位置
                Animation.ABSOLUTE, 0.0f,
                //y轴开始位置
                Animation.ABSOLUTE, 0.0f,
                //y轴移动后的结束位置
                Animation.ABSOLUTE, footInfo.getHeight());

        //0.5秒完成动画
        translateAnimation.setDuration(500);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                footInfo.clearAnimation();
                footInfo.invalidate();

//        mContext.foot_up.setVisibility(View.VISIBLE);
                footInfo.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //如果fillAfter的值为真的话，动画结束后，控件停留在执行后的状态
        animationSet.setFillAfter(true);
        //将AlphaAnimation这个已经设置好的动画添加到 AnimationSet中
        animationSet.addAnimation(translateAnimation);
        //启动动画
        footInfo.startAnimation(animationSet);
    }

    public void showFootInfo() {
//    mContext.foot_up.setVisibility(View.GONE);
        if (footInfo.getVisibility() == View.VISIBLE)
            return;


        footInfo.setVisibility(View.VISIBLE);
    /*
                AnimationSet相当于一个动画的集合，true表示使用Animation的interpolator
                false则是使用自己的。
                Interpolator 被用来修饰动画效果，定义动画的变化率，可以使存在的动画效果
                accelerated(加速)，decelerated(减速),repeated(重复),bounced(弹跳)等。
             */

        AnimationSet animationSet = new AnimationSet(true);
            /*
                    Animation还有几个方法
                    setFillAfter(boolean fillAfter)
                    如果fillAfter的值为真的话，动画结束后，控件停留在执行后的状态
                    setFillBefore(boolean fillBefore)
                    如果fillBefore的值为真的话，动画结束后，控件停留在动画开始的状态
                    setStartOffset(long startOffset)
                    设置动画控件执行动画之前等待的时间
                    setRepeatCount(int repeatCount)
                    设置动画重复执行的次数
             */
        TranslateAnimation translateAnimation = new TranslateAnimation(
                //X轴初始位置
                Animation.ABSOLUTE, 0.0f,
                //X轴移动的结束位置
                Animation.ABSOLUTE, 0.0f,
                //y轴开始位置
                Animation.ABSOLUTE, footInfo.getHeight(),
                //y轴移动后的结束位置
                Animation.ABSOLUTE, 0.0f);

        //0.5秒完成动画
        translateAnimation.setDuration(500);
        //如果fillAfter的值为真的话，动画结束后，控件停留在执行后的状态
        animationSet.setFillAfter(false);
        //将AlphaAnimation这个已经设置好的动画添加到 AnimationSet中
        animationSet.addAnimation(translateAnimation);
        //启动动画
        footInfo.startAnimation(animationSet);
    }

    //显示顶条push
    public void showPush(String name) {
        if (Constant.H2大厅.equals(name)) {
            imPush.setBackgroundResource(R.drawable.infor_welcome);
        } else if (Constant.ICS实验室.equals(name)) {
            imPush.setBackgroundResource(R.drawable.infor_welcome_2);
        }

        if (imPush.getVisibility() == View.GONE && canPush) {
            imPush.setVisibility(View.VISIBLE);
            canPush = false;

            mHandler.postDelayed(new Runnable() {
                public void run() {
                    //execute the task
                    hidePush();
                }
            }, 10000);

        }

    }

    public void showRedPoiMark(String name, double x, double y) {
        if (currentRedMark != null) {
            if (currentRedMark.getName().equals(name)) {
                if (isShowFootPrint && !hasAddRedMark) {
                    hasAddRedMark = true;
                } else {
                    return;
                }
            } else
                mMapView.removeOverlay(currentRedMark);
        }

        PoiRedMark p = new PoiRedMark(mContext, name, new PoiRedMark.OnClickListenerForMark() {
            @Override
            public void onMarkSelect(PoiRedMark mark) {
                if (H2大厅.equals(mark.getName())) {
                    Intent intent = new Intent(mContext, PoiInfoActivity.class);
                    intent.putExtra("type", PoiInfoActivity.POI_HALL);
                    mContext.startActivity(intent);
                } else if (mark.getName().contains(ICS实验室)) {
                    Intent intent = new Intent(mContext, PoiInfoActivity.class);
                    intent.putExtra("type", PoiInfoActivity.POI_LAB);
                    mContext.startActivity(intent);
                } else if (mark.getName().contains(ICS办公区)) {
                    Intent intent = new Intent(mContext, PoiInfoActivity.class);
                    intent.putExtra("type", PoiInfoActivity.POI_OFFICE);
                    mContext.startActivity(intent);
                } else if (mark.getName().contains(会议室)) {
                    Intent intent = new Intent(mContext, PoiInfoActivity.class);
                    intent.putExtra("type", PoiInfoActivity.POI_MEETING);
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
        mHandler.postDelayed(new Runnable() {
            public void run() {
                canPush = true;
            }
        }, 20000);
    }

    //开始搜索
    public void initSearchView() {
        mCompass.setVisibility(View.GONE);
        mSearch.setVisibility(View.GONE);

    }

    //结束搜索
    public void hideSearchView() {
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

        mMapViewFrame = (FrameLayout) getActivity().findViewById(R.id.map_view);
        mMapOptions = new MapOptions(); // 该对象可设置一些地图手势操作
        mMapOptions.setSkewEnabled(false);//关闭俯仰
        mMapView = new MapView("default", mContext); //初始化MapView
        if (mMapViewFrame!=null)
          mMapViewFrame.addView(mMapView);

        mMapView.setMapOptions(mMapOptions);
        mMapView.setBackgroundColor(0xffebebeb);
        mMapView.initRatio(1.0F);
//    mMapView.setMaxZoomLevel(MAX_ZOOM);//不起作用
//    mMapView.setMinZoomLevel(MIN_ZOOM);//不起作用


        mMapView.setOverlayContainer(mContext.mMapContainer);
//    mMapView.setBackgroundResource(android.R.color.transparent);
        mMapView.setBackgroundResource(R.color.map_bg);
        mMapView.setMinAngle(45);

        featureLayer = new FeatureLayer("poi");
        mMapView.addLayer(featureLayer);
        mMapView.setLayerOffset(featureLayer);

        loadMap(FLOOR_ID_F1);
    }


    private boolean isTapTooShort() {//防止点太快闪退
        if (isSingleTapTooShort) {
            DialogUtils.showShortToast("点得太快了喔");
            return true;
        }

        isSingleTapTooShort = true;
        mHandler.postDelayed(new Runnable() {
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
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    DialogUtils.showShortToast("请在设置中开启相机权限", Gravity.CENTER);
                    return;
                }
                String lo = LocateTimerService.getCurrentLocationArea();
                if (lo.equals(Constant.其它)) {
                    DialogUtils.showShortToast("没有获取到当前位置，不能拍照", Gravity.CENTER);
                } else {
                    mContext.openCameraActivity();
                }
                break;
            case R.id.locate:
                if (hasLocated) {
                    if (mCurrentFloor == Constant.FLOOR_ID_F1) {
                        Types.Point point = mMapView.converToWorldCoordinate((double) locationMarkAnim.getX(), (double) locationMarkAnim.getY());
                        Coordinate coordinate = new Coordinate(point.x, point.y);
                        mMapView.moveToPoint(coordinate, true, 500);
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mMapView.getOverlayController().refresh();
                            }
                        }, 700);

                    } else
                        loadMap(Constant.FLOOR_ID_F1);
                } else {
                    DialogUtils.showShortToast("当前无定位点");
                }


//        initMapScale();


//        DataProviderCenter.getInstance().getPosition("", new HttpDataCallBack() {
//          @Override
//          public void onError(int errorCode) {
//            if (Constant.isDebug)
//              ErrorCode.showError(errorCode);
//            Log.e("", "errorCode：" + errorCode);
//          }
//
//          @Override
//          public void onComplete(Object content) {
//            try {
//
//              List<PositionJson> list = new ArrayList<PositionJson>(JSONArray.parseArray(content.toString(), PositionJson.class));
//              addLocationMark(list.get(0).getX(), list.get(0).getY());
//
//
////              JSONObject jo1 = JSON.parseObject(content.toString());
////              JSONObject jo2 = jo1.getJSONObject("geometry");
////      1        JSONArray jo3 = jo2.getJSONArray("coordinates");
////              double x = jo3.getDoubleValue(0);
////              double y = jo3.getDoubleValue(1);
//
////              Types.Point point = mMapView.converToScreenCoordinate(x,y);
////              addLocationMark(x,y);
////              addMark(list.get(0).getX(), list.get(0).getY());
//
//
//            } catch (IndexOutOfBoundsException e) {
//              DialogUtils.showShortToast(e.getMessage());
//            }
//
//          }
//        });
                break;
            case R.id.map_zoom_in:
                mMapView.zoomIn();
                if (mScale != null) {
                    mScale.postInvalidate();
                }
                break;
            case R.id.map_zoom_out:
                mMapView.zoomOut();
                if (mScale != null) {
                    mScale.postInvalidate();
                }
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
        mContext.showTabMenu();
        //判断切换楼层
        if (Constant.FLOOR_ID_F1 != mCurrentFloor) {
            isSearch4Poi = index;
            loadMap(Constant.FLOOR_ID_F1);
            return;
        }

//    addMark(x[index], y[index]);
//        Types.Point point = mMapView.converToScreenCoordinate(x[index], y[index]);
//        searchPOIByPoint((float) point.x, (float) point.y);

        //index 0

        switch (index) {

            case 0:
                Feature featureH2 = mMapView.selectFeature(1284129);
                if (featureH2 != null) {
                    addOverlaysByFeatures(featureH2);
                }
                break;

            case 1:
                List<Feature> featureMeetings = mMapView.searchFeature("Area", "name", new Value("会议室"));
                if (featureMeetings != null) {
                    addOverlaysByFeatures(featureMeetings);
                }
                break;
            case 2:
                Feature featureLaboratory1 = mMapView.selectFeature(1270043);
                if (featureLaboratory1 != null) {
                    addOverlaysByFeatures(featureLaboratory1);
                }
                Feature featureLaboratory2 = mMapView.selectFeature(1264378);
                if (featureLaboratory2 != null) {
                    addOverlaysByFeatures(featureLaboratory2);
                }
                break;

            case 3:
                Feature featureOffice = mMapView.selectFeature(1284130);
                if (featureOffice != null) {
                    addOverlaysByFeatures(featureOffice);
                }
                break;
        }

        initMapScale();
    }

    private void showSearch4Fliter(int index) {
        KeyBoardUtils.closeKeybord(mKeywords, mContext);
        mSearchBg.setVisibility(View.GONE);
        mCompass.setVisibility(View.VISIBLE);
        mSearch.setVisibility(View.VISIBLE);
        mContext.showTabMenu();
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
//      DialogUtils.showShortToast("电梯");
            mElevator.setBackgroundResource(R.drawable.btn_tab_elevator_sel);
            mWc.setBackgroundResource(R.drawable.btn_tab_toilet_nor);
            mExit.setBackgroundResource(R.drawable.btn_tab_entrance_nor);
            mWater.setBackgroundResource(R.drawable.btn_tab_water_nor);
        } else if (mCurrentPoiFilter == 2) {
//      DialogUtils.showShortToast("洗手间");
            mElevator.setBackgroundResource(R.drawable.btn_tab_elevator_nor);
            mWc.setBackgroundResource(R.drawable.btn_tab_toilet_sel);
            mExit.setBackgroundResource(R.drawable.btn_tab_entrance_nor);
            mWater.setBackgroundResource(R.drawable.btn_tab_water_nor);
        } else if (mCurrentPoiFilter == 3) {
//      DialogUtils.showShortToast("出入口");
            mElevator.setBackgroundResource(R.drawable.btn_tab_elevator_nor);
            mWc.setBackgroundResource(R.drawable.btn_tab_toilet_nor);
            mExit.setBackgroundResource(R.drawable.btn_tab_entrance_sel);
            mWater.setBackgroundResource(R.drawable.btn_tab_water_nor);
        } else if (mCurrentPoiFilter == 4) {
//      DialogUtils.showShortToast("茶水间");
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


    /**
     * @Author: eric3
     * @Description: 加载缓存地图
     * @Time 2016/11/7 18:22
     */
    private boolean checkCashAndLoad(final long floorID) {

        if (floorID == Constant.FLOOR_ID_F1) {
            if (HuaWeiH2Application.planarGraphF1 == null)
                return false;

            HuaWeiH2Application.planarGraphF1.obtain();
            LogUtils.d("加载内存缓存，floorId = " + floorID + " Ref_count = " + HuaWeiH2Application.planarGraphF1.getRef_Count());
            initPlanarGraph(HuaWeiH2Application.planarGraphF1, floorID);
            return true;
        }
        if (floorID == Constant.FLOOR_ID_B1) {
            if (HuaWeiH2Application.planarGraphB1 == null)
                return false;

            HuaWeiH2Application.planarGraphB1.obtain();
            LogUtils.d("加载内存缓存，floorId = " + floorID + " Ref_count = " + HuaWeiH2Application.planarGraphB1.getRef_Count());
            initPlanarGraph(HuaWeiH2Application.planarGraphB1, floorID);
            return true;
        }
        return false;
    }

    /**
     * @Author: eric3
     * @Description: 加载地图
     * @Time 2016/11/7 18:25
     */
    public void loadMap(final long floorId, boolean isShowProgress) {
        if (mCurrentFloor == floorId)
            return;

        if (isShowFootPrint && floorId == Constant.FLOOR_ID_B1) {//行程不能切换
            DialogUtils.showShortToast("请返回地图页面再切换楼层");
            return;
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

        if (isShowProgress)
            showProgress(mHandler, "提示", "地图加载中，请稍后...");

        Log.w(TAG, "开始加载地图，floorId = " + floorId);
        isLoadingMap = true;


        if (checkCashAndLoad(floorId))//有缓存
            return;


        mDataSource.requestPlanarGraph(floorId, new DataSource.OnRequestDataEventListener<PlanarGraph>() {
            @Override
            public void onRequestDataEvent(final DataSource.ResourceState resourceState, final PlanarGraph planarGraph) {

                if (resourceState == DataSource.ResourceState.ok ||
                        resourceState == DataSource.ResourceState.CACHE) {
                    Log.w(TAG, "resourceState = " + resourceState);
                    mSearch.setClickable(true);//防止欢迎页面点到

                    if (floorId == Constant.FLOOR_ID_F1) {//缓存
                        planarGraph.obtain();
                        HuaWeiH2Application.planarGraphF1 = planarGraph;
//            HuaWeiH2Application.planarGraphF1 = new MyPlanarGraph(PlanarGraph.getPtrAddress(planarGraph),false);

                    }
                    if (floorId == Constant.FLOOR_ID_B1) {
                        planarGraph.obtain();
                        HuaWeiH2Application.planarGraphB1 = planarGraph;
//            HuaWeiH2Application.planarGraphB1 = new MyPlanarGraph(PlanarGraph.getPtrAddress(planarGraph),false);
                    }


                    initPlanarGraph(planarGraph, floorId);


                } else {
                    closeProgress(mHandler);
                    mSearch.setClickable(true);//防止欢迎页面点到
                    isLoadingMap = false;
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            DialogUtils.showShortToast("floorID=" + floorId + "加载失败:" + resourceState);
                        }
                    });
                }
            }
        });
    }

    public void loadMap(final long floorId) {
        loadMap(floorId, true);
//    mContext.hidePoiInfoBar();
        mContext.showTabMenu();
    }

    /**
     * @Author: eric3
     * @Description: 开新线程渲染地图
     * @Time 2016/11/7 18:32
     */
    public void initPlanarGraph(final PlanarGraph planarGraph, final long floorID) {
        refeshPoiFilter(0);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                refeshFloorView(floorID);
                if (mScale != null) {
                    mScale.postInvalidate();
                }
            }
        });

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mMapView.drawPlanarGraph(planarGraph);
                mMapView.start();
                if (!isSearchCar || isNavigateCar)
                    closeProgress();

                if (isSearchCar && !isNavigateCar)
                    findCar(3000);

                if (mCurrentFloor == FLOOR_ID_F1) {
//          [_mapView visibleLayerFeature:@"Area_text" key:@"category" value:@(24002000) isVisible:false];
//          [_mapView visibleLayerFeature:@"Area_text" key:@"id" value:@(1284130) isVisible:true];

                    initMapScale();
                }


                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (!hasChoosenFootPrint) {
                            if (mContext.shouldShow2choose1) {
                                hasChoosenFootPrint = true;
                            }
                        }

                        if (isNavigating) {
                            if (!isShowFootPrint)
                            mContext.poiInfoBar.setVisibility(View.VISIBLE);

                            mContext.hideTabMenu();
                        }

                        if (mCompass != null) {
                            mCompass.setRotation(-BigDecimal.valueOf(mMapView.getRotate()).floatValue());
                            mCompass.invalidate();
                        }
                    }
                });


                if (isShowSearchResult) {
                    isShowSearchResult = false;
                    showSearchResult();
                }

                if (isSearch4Poi >= 0) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showSearch4Poi(isSearch4Poi);
                            isSearch4Poi = -1;
                        }
                    }, 1000);
                }


                isLoadingMap = false;
            }
        });
        thread.start();
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

        if (HuaWeiH2Application.planarGraphF1 != null) {//加载缓存
            HuaWeiH2Application.planarGraphF1.obtain();
            LogUtils.d("加载内存缓存，floorId = " + Constant.FLOOR_ID_F1 + " Ref_count = " + HuaWeiH2Application.planarGraphF1.getRef_Count());
            initFootPlanarGraph(HuaWeiH2Application.planarGraphF1);
            return;
        }


        isLoadingMap = true;
        mDataSource.requestPlanarGraph(FLOOR_ID_F1, new DataSource.OnRequestDataEventListener<PlanarGraph>() {
            @Override
            public void onRequestDataEvent(final DataSource.ResourceState resourceState, final PlanarGraph planarGraph) {

                if (resourceState == DataSource.ResourceState.ok ||
                        resourceState == DataSource.ResourceState.CACHE) {
                    Log.w(TAG, "resourceState = " + resourceState);
                    planarGraph.obtain();
                    HuaWeiH2Application.planarGraphF1 = planarGraph;

                    initFootPlanarGraph(planarGraph);

                } else {
                    closeProgress(mHandler);
                    isLoadingMap = false;
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            DialogUtils.showShortToast("floorID=" + floorId + "加载失败:" + resourceState);
                        }
                    });
                }

            }
        });
    }

    /**
     * @Author: eric3
     * @Description: 开新线程渲染行程地图
     * @Time 2016/11/7 18:42
     */
    public void initFootPlanarGraph(final PlanarGraph planarGraph) {
        refeshPoiFilter(0);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mMapView.drawPlanarGraph(planarGraph);
                mMapView.start();

                mMapView.visibleLayerFeature("Area_text", "display", new Value("ICS办公区"), false);
                isLoadingMap = false;

                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        refeshFloorView(FLOOR_ID_F1);
                        setFootPrint();
                    }
                });

            }
        });
        thread.start();
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
            searchPOIAndRefeshView(new long[]{Constant.男洗手间_ID, Constant.女洗手间_ID, Constant.残障洗手间_ID, Constant.洗手间_ID});
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


    //  /**
//  * @Author: eric3
//  * @Description: 获取自定义feature
//  * @Time 2016/11/8 21:15
//  */
//  private Feature getMyFeatureInfo(Feature ff){
//
//  }
    private boolean hasInfo(Feature feature) {//判断是否显示poi bar
        if (feature == null) {//没有搜索到feature
            return false;
        }
        String display = MapParamUtils.getDisplay(feature);
        if (display != null) {
            if (display.contains("办公楼"))
                return false;
        }
        long id = MapParamUtils.getId(feature);
        if (id == 1284127 || id == 1490708 || id == 1270023 || id == Constant.空地_POI_ID) {
            return false;
        }

        long cat = MapParamUtils.getCategoryId(feature);
        for (int i = 0; i < PoiImgList.getPoiImgList().size(); i++) {//自定义feature
            if (PoiImgList.getPoiImgList().get(i).getCat() == cat)
                return true;
        }


        String name = MapParamUtils.getName(feature);
        if (name == null) {
            return false;
        }

        return true;
    }

    private void searchPOIByPoint(float x, float y) {
//    List<Feature> features =mMapView.searchFeature(Constant.AREA_LAYER,"display",new Value("H114"));

        final Feature feature = mMapView.selectFeature(x, y);
        long poiID = 0;
        if (feature != null)
            poiID = MapParamUtils.getId(feature);
//        if (Constant.isDebug) {
//            DialogUtils.showShortToast("poiID=" + poiID);
//        }
        if (!hasInfo(feature)) {
            mContext.showTabMenu();
            return;
        }


        Types.Point point = mMapView.converToWorldCoordinate(x, y);
        endX = point.x;//可能
        endY = point.y;
        endName = MapParamUtils.getDisplay(feature);
        if (endName == null)
            endName = PoiImgList.getName(MapParamUtils.getCategoryId(feature));

        if ("办公室".equals(endName) || "会议室".equals(endName) || endName.contains("办公区")) {//加门牌号
            String addr = MapParamUtils.getAddress(feature) == null ? "" : MapParamUtils.getAddress(feature);
            endName = endName + addr;
        }

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
        mContext.showPoiInfoBar(MapParamUtils.getCategoryId(feature), endName);
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
        if (!hasInfo(feature)) {
            return;
        }


        mContext.setStartPoiInfo(feature);
        resetFeatureStyle(markFeatureID);//若切换楼层会闪退
        markFeatureID = MapParamUtils.getId(feature);
        startFeatureID = MapParamUtils.getId(feature);
        startFloorID = Feature.planar_graph.get(feature);
        startName = MapParamUtils.getName(feature);
        if (startName == null)
            startName = PoiImgList.getName(MapParamUtils.getCategoryId(feature));

        if ("办公室".equals(startName) || "会议室".equals(startName) || startName.contains("办公区")) {//加门牌号
            String addr = MapParamUtils.getAddress(feature) == null ? "" : MapParamUtils.getAddress(feature);
            startName = startName + addr;
        }
        Types.Point point = mMapView.converToWorldCoordinate(x, y);
        startX = point.x;
        startY = point.y;
        if (Constant.onSingleTap_changeColor) {
            setFeatureColor(feature, 0xffFFB5B5);
        }

        addMark(point.x, point.y, Mark.START);


    }


    public void resetFeatureStyle(long featureID) {
        mMapView.resetOriginStyle("Area", featureID);
    }

    public void resetFeatureStyle(String layerName, long featureID) {
        mMapView.resetOriginStyle(layerName, featureID);
    }

    private void setFeatureColor(Feature feature, int color) {
        if (feature != null) {
            mMapView.setRenderableColor("Area", LocationModel.id.get(feature), color);
            markFeatureID = MapParamUtils.getId(feature);
        }
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
            startMark = null;
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

    public void moveNavMark(double xs, double ys, double xe, double ye) {
        mMapView.removeOverlay(startMark);
        mMapView.removeOverlay(endMark);
        startMark = null;
        endMark = null;

        if (mCurrentFloor == startFloorID) {
            startMark = new Mark(mContext, Mark.START);
            startMark.init(new double[]{xs, ys});
            mMapView.addOverlay(startMark);
        }

        if (mCurrentFloor == toFloorID) {
            endMark = new Mark(mContext, Mark.END);
            endMark.init(new double[]{xe, ye});
            mMapView.addOverlay(endMark);
        }


        mMapView.getOverlayController().refresh();
    }

    public void addMark(double x, double y) {//wgs84坐标
//    if (Constant.isDebug)
//      DialogUtils.showLongToast("location:"+x+" "+y);

//    mMapView.removeAllOverlay();
        if (currentClickMark != null)
            mMapView.removeOverlay(currentClickMark);

        if (currentPoiMarks != null) {
            refeshPoiFilter(0);
            for (int i = 0; i < currentPoiMarks.size(); i++)
                mMapView.removeOverlay(currentPoiMarks.get(i));
        }
        Mark mark = new Mark(mContext);
        mark.init(new double[]{x, y});
        currentClickMark = mark;
        mMapView.addOverlay(mark);
        mMapView.getOverlayController().refresh();
    }

    public void addLocationMark(double x, double y) {//wgs84坐标
//    mMapView.removeAllOverlay();
        if (mCurrentFloor == Constant.FLOOR_ID_B1 || isLoadingMap)
            return;

        checkPoiPush(LocateTimerService.getCurrentLocationArea());
//    if (Constant.isDebug)
//      DialogUtils.showLongToast("location:" + x + " " + y);

//    LocationMark mark = new LocationMark(mContext);
//    mark.init(new double[]{x, y});
        //
//    mMapView.addOverlay(mark);
//    mMapView.getOverlayController().refresh();


        if (isNavigating && navigateManager != null && navigateManager.getRef_Count() > 0) {//将定位点放到导航线上
            Coordinate co = new Coordinate(x, y);
            navigateManager.switchPlanarGraph(mCurrentFloor);
            double dis = navigateManager.getMinDistanceByPoint(co);
            if (dis < 3.0) {
//      Types.Point point = mMapView.converToScreenCoordinate(x,y);
//      Feature feature = mMapView.selectFeature((float) point.x,(float) point.y);
//      if (feature!=null) {
                Coordinate coordinate = navigateManager.getPointOfIntersectioanByPoint(co);
                if (!Double.isNaN(coordinate.getX()) && !Double.isNaN(coordinate.getY())) {
                    x = coordinate.getX();
                    y = coordinate.getY();
                    LogUtils.d("addLocationMark->getPointOfIntersectioanByPoint x=" + x + " y=" + y);
                }


                if (!hasShowArriveEnd && calDistence(endX, endY, co.getX(), co.getY()) < Constant.NAV_MIN_DISTANCE) {
                    hasShowArriveEnd = true;
                    DialogUtils.showLongToast("您已到达终点附近");
                }
            }


//      }

        }

        if (locationMarkAnim != null) {
            locationMarkAnim.init(new double[]{x, y});
            locationMarkAnim.animTo(x, y);
        }

    }

    private double calDistence(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    private void checkPoiPush(String name) {
        if (isNavigating && (!isShowFootPrint))
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
//            if (!isShowFootPrint)
//                showPush(Constant.H2大厅);

            showRedPoiMark(Constant.会议室, x[1], y[1]);
        } else if (ICS办公区.equals(name)) {
            showRedPoiMark(Constant.ICS办公区, x[3], y[3]);
        } else {
            if (currentRedMark != null)
                mMapView.removeOverlay(currentRedMark);

            currentRedMark = null;
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

        mDataSource.search(keyWords, 1, 30, new long[]{FLOOR_ID_F1}, new long[]{}, new DataSource.OnRequestDataEventListener<LocationPagingList>() {
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

                searchResultData = data;
                //排序
                List<LocationModel> locationModels = sortSearchResult(data);

//        List<LocationModel> locationModels = new ArrayList<LocationModel>();
//        for (int i = 0; i < data.getSize(); i++) {
//          if (LocationModel.display.get(data.getPOI(i)) != null && !"".equals(LocationModel.display.get(data.getPOI(i)))) {
//            locationModels.add(data.getPOI(i));
//          }
//        }

                mSearchResultAdapter = new SearchResultAdapter(getActivity(), locationModels, new SearchResultAdapter.OnItemClickListener() {
                    @Override
                    public void onClicked(LocationModel locationModel) {
                        KeyBoardUtils.closeKeybord(mKeywords, mContext);
                        mSearchBg.setVisibility(View.GONE);
                        mKeywords.setText("");
                        if (searchResultData != null && searchResultData.getRef_Count() > 0)
                            searchResultData.drop();

//            Mark mark = new Mark(mContext);
                        searchResultID = LocationModel.id.get(locationModel);
                        Coordinate c = getCoordinate(locationModel);
                        if (c == null) {
                            //判断切换楼层
                            long floor = LocationModel.parent.get(locationModel);
                            if (floor != mCurrentFloor) {
                                isShowSearchResult = true;
                                loadMap(floor);
                            }
                            return;
                        }
                        showSearchResult();

//            initMapScale();
                    }
                });
                mSearchList.setAdapter(mSearchResultAdapter);
                mSearchList.setVisibility(View.VISIBLE);
                mSearchList.setFocusableInTouchMode(false);
                mSearchNull.setVisibility(View.GONE);
                mSearchDef.setVisibility(View.GONE);
                mSearchDef2.setVisibility(View.GONE);

            }
        });
    }

    private List<LocationModel> sortSearchResult(LocationPagingList data) {
        if (data == null)
            return null;


// Collator 类是用来执行区分语言环境的 String 比较的，这里选择使用CHINA
        Comparator comparator = Collator.getInstance(Locale.CHINESE);
//    List<String> arrStrings = new ArrayList<>();
        String[] sl = new String[data.getSize()];
        int size = 0;
        for (int i = 0; i < data.getSize(); i++) {
            if (LocationModel.display.get(data.getPOI(i)) != null && !"".equals(LocationModel.display.get(data.getPOI(i)))
                &&LocationModel.address.get(data.getPOI(i)) != null && !"".equals(LocationModel.address.get(data.getPOI(i)))) {
                sl[size++] = LocationModel.display.get(data.getPOI(i)) + LocationModel.address.get(data.getPOI(i));
            }
        }

        // 使根据指定比较器产生的顺序对指定对象数组进行排序。
        String[] sl_sort = Arrays.copyOf(sl,size);
        Arrays.sort(sl_sort, comparator);
        List<LocationModel> lol = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < data.getSize(); j++) {
                if (sl_sort[i].equals(LocationModel.display.get(data.getPOI(j)) + LocationModel.address.get(data.getPOI(j)))) {
                    lol.add(data.getPOI(j));
                    break;
                }
            }
        }

        return lol;
    }

    public void showSearchResult() {

        mContext.runOnUiThread(new Runnable() {
            @Override
            public void run() {
//        Coordinate c = getCoordinate(searchResultID);
                Feature feature = mMapView.selectFeature(searchResultID);

                endX = feature.getCentroid().getX();//point.x;//可能
                endY = feature.getCentroid().getY();//point.y;
                endName = MapParamUtils.getDisplay(feature);

                if (endName == null)
                    endName = PoiImgList.getName(MapParamUtils.getCategoryId(feature));

                if ("办公室".equals(endName) || "会议室".equals(endName) || endName.contains("办公区")) {//加门牌号
                    String addr = MapParamUtils.getAddress(feature) == null ? "" : MapParamUtils.getAddress(feature);
                    endName = endName + addr;
                }

                mContext.setPoiInfoBar(feature);
                mContext.showPoiInfoBar(MapParamUtils.getCategoryId(feature), endName);
                addMark(endX, endY);
                setFeatureColor(feature, 0xffFFB5B5);
                mCompass.setVisibility(View.VISIBLE);
                mSearch.setVisibility(View.VISIBLE);
                mMapView.moveToPoint(feature.getCentroid());
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mMapView.getOverlayController().refresh();
                    }
                }, 500);
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

//    private void addOverlaysByMarks(List<Mark> markList) {
//        mMapView.removeAllOverlay();
//        for (Mark mark : markList) {
//            mMapView.addOverlay(mark);
//        }
//    }


    private void addOverlaysByFeatures(List<Feature> featureList) {
        if (featureList == null)
            return;
//    Mark mark;
//    mMapView.removeAllOverlay();
//    Feature ff = featureList.get(0);
        for (Feature feature : featureList) {
            Mark mark = new Mark(mContext);
            mark.init(new double[]{feature.getCentroid().getX(), feature.getCentroid().getY()});
            mMapView.addOverlay(mark);
            currentPoiMarks.add(mark);
        }
//    Coordinate coordinate = new Coordinate(ff.getCentroid().getX(), ff.getCentroid().getY());
//    mMapView.moveToPoint(coordinate, true, 200);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMapView.getOverlayController().refresh();
            }
        }, 500);
    }

    private void addOverlaysByFeatures(Feature... featureList) {
        if (featureList == null)
            return;
        for (Feature feature : featureList) {
            Mark mark = new Mark(mContext);
            mark.init(new double[]{feature.getCentroid().getX(), feature.getCentroid().getY()});
            mMapView.addOverlay(mark);
            currentPoiMarks.add(mark);
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMapView.getOverlayController().refresh();
            }
        }, 500);
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
        //显示不相关功能
        mSearch.setVisibility(View.VISIBLE);
        mElevator.setVisibility(View.VISIBLE);
        mWc.setVisibility(View.VISIBLE);
        mWater.setVisibility(View.VISIBLE);
        mExit.setVisibility(View.VISIBLE);
        mShoot.setVisibility(View.VISIBLE);

        if (navigateLayer != null && navigateManager != null) {
            navigateLayer.clearFeatures();  //先把之前的导航线清理掉

            if (navigateManager.getRef_Count() > 0) {
                navigateManager.clear();
            }
            if (navigateLayer.getRef_Count() > 0) {
                mMapView.removeLayer(navigateLayer);
            }

//            navigateManager.drop();
//            navigateManager = null;
//            mMapView.removeLayer(navigateLayer);
            navigateLayer = null;
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

    /**
     * 导航的楼层ID
     */
    private long navigateFloorId;

    public void startNavigate() {//final Feature startFeatureID, final double toX, final double toY, final long toFloorID
        isNavigating = true;
        hasShowArriveEnd = false;
        mContext.titleBar.setTitle("导航路线");
        //隐藏不相关功能
        mSearch.setVisibility(View.GONE);
        mElevator.setVisibility(View.GONE);
        mWc.setVisibility(View.GONE);
        mWater.setVisibility(View.GONE);
        mExit.setVisibility(View.GONE);
        mShoot.setVisibility(View.GONE);
        if (navigateManager != null) {
            navigateManager.clear();
//            navigateManager.drop();
//            navigateManager = null;
        } else {
            navigateManager = new NavigateManager();
        }
        navigateLayer = new FeatureLayer("navigate");
        mMapView.addLayer(navigateLayer);
        mMapView.setLayerOffset(navigateLayer);


        navigateManager.setOnNavigateComplete(new NavigateManager.OnNavigateComplete() {
            @Override
            public void onNavigateComplete(final NavigateManager.NavigateState navigateState, FeatureCollection featureCollection) {

                if (navigateState == NavigateManager.NavigateState.ok
                        || navigateState == NavigateManager.NavigateState.CLIP_NAVIGATE_SUCCESS
                        || navigateState == NavigateManager.NavigateState.SWITCH_NAVIGATE_SUCCESS) {


                    if (navigateLayer != null && navigateLayer.getRef_Count() > 0) {
                        navigateLayer.clearFeatures();  //先把之前的导航线清理掉

                        navigateLayer.addFeatures(featureCollection); //重新添加新的导航线
                        Feature s = featureCollection.getFirstFeature();
                        Feature e = featureCollection.getEedFeature();
                        Coordinate firstCoordinate = navigateManager.getCoordinateByFeature(s, 0);
                        Coordinate endCoordinate = navigateManager.getCoordinateByFeature(e, navigateManager.getFeatureLength(e) - 1);

                        if (firstCoordinate != null && endCoordinate != null)
                            moveNavMark(firstCoordinate.getX(), firstCoordinate.getY(), endCoordinate.getX(), endCoordinate.getY());


                        mContext.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mContext.showNavigateInfoBar(startName, endName, (int) navigateManager.getTotalLineLength() + "");//显示导航详情
                            }
                        });
                    }

                } else {
                    mContext.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (navigateState == NavigateManager.NavigateState.NAVIGATE_NOT_FOUND) {
                                DialogUtils.showShortToast("该楼层没有导航线");
                            } else {
                                DialogUtils.showShortToast("导航线请求失败:" + navigateState);
                                mContext.showTabMenu();
                                endNavigate();
                            }

                        }
                    });

                }
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
                    navigateManager.switchPlanarGraph(newPlanarGraphId);
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
        navigateFloorId = mCurrentFloor;
        // TODO 请求导航线
//    navigateManager.setTimeout(1000L);
        navigateManager.navigation(startX, startY, startFloorID, endX, endY, toFloorID, navigateFloorId);

    }

    public void endNavigateInFootAndPark() {
        mSearch.setVisibility(View.VISIBLE);
        mShoot.setVisibility(View.VISIBLE);
        mF1.setVisibility(View.VISIBLE);
        mB1.setVisibility(View.VISIBLE);
        mLocation.setVisibility(View.VISIBLE);

        if (navigateLayer != null && navigateManager != null) {
            if (navigateManager.getRef_Count() > 0) {
                navigateManager.clear();
            }
            if (navigateLayer.getRef_Count() > 0) {
                navigateLayer.clearFeatures();  //先把之前的导航线清理掉
                mMapView.removeLayer(navigateLayer);
            }
        } else {
            navigateManager = new NavigateManager();
        }
        if (startFeatureID != -1) {
            resetFeatureStyle(startFeatureID);
            startFeatureID = -1;
        }
        mMapView.removeAllOverlay();

        mMapView.getOverlayController().refresh();

        isNavigating = false;

        startMark = null;
        endMark = null;
    }

    public void startNavigateInFoot() {//
        isNavigating = true;

        if (navigateLayer != null && navigateLayer.getRef_Count() > 0) {
            mMapView.removeLayer(navigateLayer);
        }
        if (navigateManager != null) {
            navigateManager.clear();
//            navigateManager.drop();
//            navigateManager = null;
        } else {
            navigateManager = new NavigateManager();
        }
        navigateLayer = new FeatureLayer("navigate");
        mMapView.addLayer(navigateLayer);
        mMapView.setLayerOffset(navigateLayer);

        navigateManager.setOnNavigateComplete(new NavigateManager.OnNavigateComplete() {
            @Override
            public void onNavigateComplete(final NavigateManager.NavigateState navigateState, FeatureCollection featureCollection) {

                if (navigateState == NavigateManager.NavigateState.ok
                        || navigateState == NavigateManager.NavigateState.CLIP_NAVIGATE_SUCCESS
                        || navigateState == NavigateManager.NavigateState.SWITCH_NAVIGATE_SUCCESS) {
                    navigateLayer.clearFeatures();  //先把之前的导航线清理掉

                    navigateLayer.addFeatures(featureCollection); //重新添加新的导航线
                    Feature s = featureCollection.getFirstFeature();
                    Feature e = featureCollection.getEedFeature();
                    Coordinate firstCoordinate = navigateManager.getCoordinateByFeature(s, 0);
                    Coordinate endCoordinate = navigateManager.getCoordinateByFeature(e, navigateManager.getFeatureLength(e) - 1);

                    if (firstCoordinate != null && endCoordinate != null)
                        moveNavMark(firstCoordinate.getX(), firstCoordinate.getY(), endCoordinate.getX(), endCoordinate.getY());

                } else {

                    mContext.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            DialogUtils.showShortToast("导航线请求失败:" + navigateState);
                            mContext.showTabMenu();
                            endNavigate();
                        }
                    });
                }
            }
        });

        startFloorID = Constant.FLOOR_ID_F1;
        toFloorID = Constant.FLOOR_ID_F1;
        navigateManager.navigation(12697160.01780d, 2588912.913410d, startFloorID, 12697093.00050d, 2588860.831650d, toFloorID); //

    }

    public void startNavigateInPark() {//
        isNavigating = true;


        if (navigateLayer != null && navigateLayer.getRef_Count() > 0) {
            mMapView.removeLayer(navigateLayer);
        }
        if (navigateManager != null) {
            navigateManager.clear();
        } else {
            navigateManager = new NavigateManager();
        }
        navigateLayer = new FeatureLayer("navigate");
        mMapView.addLayer(navigateLayer);
        mMapView.setLayerOffset(navigateLayer);

        navigateManager.setOnNavigateComplete(new NavigateManager.OnNavigateComplete() {
            @Override
            public void onNavigateComplete(final NavigateManager.NavigateState navigateState, final FeatureCollection featureCollection) {

                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (navigateState == NavigateManager.NavigateState.ok
                                || navigateState == NavigateManager.NavigateState.CLIP_NAVIGATE_SUCCESS
                                || navigateState == NavigateManager.NavigateState.SWITCH_NAVIGATE_SUCCESS) {

                            navigateLayer.clearFeatures();  //先把之前的导航线清理掉
                            navigateLayer.addFeatures(featureCollection); //重新添加新的导航线
                            Feature s = featureCollection.getFirstFeature();
                            Feature e = featureCollection.getEedFeature();
                            Coordinate firstCoordinate = navigateManager.getCoordinateByFeature(s, 0);
                            Coordinate endCoordinate = navigateManager.getCoordinateByFeature(e, navigateManager.getFeatureLength(e) - 1);

                            if (firstCoordinate != null && endCoordinate != null)
                                moveNavMark(firstCoordinate.getX(), firstCoordinate.getY(), endCoordinate.getX(), endCoordinate.getY());

                        } else {
                            DialogUtils.showShortToast("导航线请求失败:" + navigateState);
                            mContext.showTabMenu();
                            endNavigate();
                        }

                    }
                });
            }
        });

        startFloorID = Constant.FLOOR_ID_B1;
        toFloorID = Constant.FLOOR_ID_B1;
        navigateManager.navigation(12697134.854500, 2588907.366900, startFloorID, 12697112.698500, 2588866.7295000, toFloorID); //
    }

    public void initMapScale() {
//    mMapView.initRatio(1.0F);

//    Types.Point point = mMapView.converToScreenCoordinate(initX, initY);
//    mMapView.rotate(point, -mMapView.getRotate(),true,500);
//    Coordinate coordinate = new Coordinate(initX, initY);
//    mMapView.moveToPoint(coordinate, true, 500);


//    mMapView.zoom(initScale);


        mMapView.visibleLayerAllFeature("Area_text", false);
        mMapView.moveToRect(12697080.571, 2588843.728, 12697195.929, 2588958.557);//不能加动画，否则地图不见
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMapView.doCollisionDetection();
                mMapView.visibleLayerAllFeature("Area_text", true);
                mMapView.visibleLayerFeature("Area_text", "category", new Value(24002000L), false);
                mMapView.visibleLayerFeature("Area_text", "id", new Value(1284130L), true);
                mMapView.visibleLayerFeature("Area_text", "display", new Value("ICS办公区"), false);
//        mMapView.visibleLayerFeature("Area_text", "name", new Value("ICS办公区"), false);


                mMapView.getOverlayController().refresh();

            }
        }, 150);
    }

    private Types.Point getScreenCenter() {
        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;


        Types.Point point = new Types.Point(width / 2.0, height / 2.0);
        return point;
    }

    private void rotataToNorth() {
        mMapView.rotate(getScreenCenter(), -mMapView.getRotate(), true, 500);
        Log.d("rotate", "rotate :" + mMapView.getRotate());
        mHandler.postDelayed(new Runnable() {//旋转指北针
            public void run() {
                LocationMarkAnim.currentMapDegree = BigDecimal.valueOf(mMapView.getRotate()).floatValue();
                mCompass.setRotation(-BigDecimal.valueOf(mMapView.getRotate()).floatValue());
                mCompass.invalidate();
                if (mScale != null) {
                    mScale.postInvalidate();
                }

                mMapView.getOverlayController().refresh();
            }
        }, 700);
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

    public void hideSomeIcon() {
        if (mSearch != null && mShoot != null && mF1 != null && mB1 != null) {
            mSearch.setVisibility(View.GONE);
            mShoot.setVisibility(View.GONE);
            mF1.setVisibility(View.GONE);
            mB1.setVisibility(View.GONE);
        }
    }

    public void showSomeIcon() {
        if (mSearch != null && mShoot != null && mF1 != null && mB1 != null) {
            mSearch.setVisibility(View.VISIBLE);
            mShoot.setVisibility(View.VISIBLE);
            mF1.setVisibility(View.VISIBLE);
            mB1.setVisibility(View.VISIBLE);
        }
    }

    public void setFootPrint() {

        hasAddRedMark = false;
        isShowFootPrint = true;
        startNavigateInFoot();
//    mContext.foot_up.setVisibility(View.GONE);
        footInfo.setVisibility(View.VISIBLE);

        initMapScale();
        mContext.hideTabMenu();
        hideSomeIcon();
        mContext.titleBar.show(null, "行程规划", null);
        mContext.titleBar.setOnTitleClickListener(new TitleBar.OnTitleClickListener() {
            @Override
            public void onLeft() {
                resetFootPrint();
            }

            @Override
            public void onRight() {
            }
        });
//        mContext.titleBar.setRightIcoImageRes(R.drawable.ico_line_list);
//        mContext.titleBar.setEnableRight(true);
//        mContext.titleBar.setRightIcoClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showFootInfo();
//            }
//        });

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
        int foot_room = 0xff91db8b;
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

        checkPoiPush(LocateTimerService.getCurrentLocationArea());
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
                        Intent intent = new Intent(mContext, ActivityOffice.class);
                        intent.putExtra("isRoute", true);
                        mContext.startActivityForResult(intent, Constant.startOffice);
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
        if (mCurrentPoiFilter != 0)
            showSearch4Fliter(mCurrentPoiFilter);

        endNavigateInFootAndPark();
//    mContext.foot_up.setVisibility(View.GONE);
        footInfo.setVisibility(View.GONE);
        isShowFootPrint = false;
        mMapView.removeAllOverlay();

        showSomeIcon();
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
        if (p == null) {
            DialogUtils.showShortToast("无车位信息");
            return;
        }

        parkInfo = p;
        isSearchCar = true;
//    mF1.setVisibility(View.VISIBLE);
//    mB1.setVisibility(View.VISIBLE);
        if (mCurrentFloor == Constant.FLOOR_ID_B1) {
            if (isSearchCar) {
                findCar(1000);
            }
        } else {
            loadMap(Constant.FLOOR_ID_B1);
        }
//    mCurrentFloor = 0;


    }

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(findCarTask);
    }

    private Runnable findCarTask = new Runnable() {
        @Override
        public void run() {

            Coordinate c = new Coordinate(12697135.554500, 2588880.529500);
            mMapView.moveToPoint(c);//不能加动画否则converToScreenCoordinate不对

            mMapView.getOverlayController().refresh();

            Types.Point point = mMapView.converToScreenCoordinate(12697134.8545d, 2588907.3669d);


            final Feature feature = mMapView.selectFeature((float) point.x, (float) point.y);
            mContext.setPoiInfoBar(feature);
            mContext.showPoiInfoBar(MapParamUtils.getCategoryId(feature), endName);
            mSearch.setVisibility(View.GONE);
            mShoot.setVisibility(View.GONE);
            mF1.setVisibility(View.GONE);
            mB1.setVisibility(View.GONE);
            mLocation.setVisibility(View.GONE);

            startNavigateInPark();
            closeProgress();
        }
    };

    private void findCar(long delay) {

//        mMapView.setOnChangePlanarGraph(new MapView.OnChangePlanarGraph() {
//            @Override
//            public void onChangePlanarGraph(PlanarGraph planarGraph, PlanarGraph planarGraph1, long l, long l1) {
        mHandler.postDelayed(findCarTask, delay);
    }

    public void setIsTrip(boolean isTrip) {
        this.isTrip = isTrip;
    }

//        });
//    }
}
