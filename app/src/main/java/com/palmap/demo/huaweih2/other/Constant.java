package com.palmap.demo.huaweih2.other;

import com.palmap.demo.huaweih2.HuaWeiH2Application;

/**
 * Created by eric3 on 2016/10/7.
 */

public class Constant {
//
//
  public static final String APP_KEY = "e8493b3ac8ee4ac9b99b98e63b8fcc8b";//h2统一
  // public static final String APP_KEY = "30497ca93a3b47bd841db8dced24878f";//h2
  public static final long MAP_ID = 1430L;
  public static final long FLOOR_ID_F1 = 1262934L;
  public static final long FLOOR_ID_B1 = 1261980L;

//  public static final String APP_KEY = "8793ae1b12d64ee7a58d446f9cd202c3";//图聚办公室的
//  public static final long FLOOR_ID_F1 = 1003497L;//21楼
//  public static final long FLOOR_ID_B1 = 1261980L;
//  public static final long MAP_ID = 6L;



//  public static final String APP_KEY = "543c0c5266fe4d30a561c3cc3d12ba97";//无锡海岸城
//  public static final long MAP_ID = 1444L;
//  public static final long FLOOR_ID_F1 = 1272847L;
//  public static final long FLOOR_ID_B1 = 0L;



  public static final String IS_FIRST_RUN = "isFirstRun";
  public static final String TAG_ACCESS_TOKEN = "access_token";

  public static final String LUR_NAME = "Nagrand/lua";
  public static final String FONT_NAME = "Nagrand/font";

//  public static final String APP_DATA_PATH = Environment.getExternalStorageDirectory()+"/huaweih2";
  public static final String APP_DATA_PATH = HuaWeiH2Application.instance.getExternalFilesDir(null)+"";
  public static final String DIR_PICTURE_UPLOAD = APP_DATA_PATH+"/temp";
  public static final String PATH_PICTURE_UPLOAD = DIR_PICTURE_UPLOAD+"/temp.jpg";
  public static final String PATH_MAP_CASH = APP_DATA_PATH+"/Nagrand/download/";


  //Environment.getExternalStorageDirectory()会有权限问题

//  public static final String DIR_PICTURE_UPLOAD = APP_DATA_PATH+"/temp";
//  public static final String PATH_PICTURE_UPLOAD = DIR_PICTURE_UPLOAD+"/temp.jpg";
//  public static final String PATH_MAP_CASH = APP_DATA_PATH+"/Nagrand/download/";

  public static String SERVER_URL = "https://api.ipalmap.com/";///huaweih2

  public static final long 电梯_ID = 24091000L;
  public static final long 无障碍电梯_ID = 24092000L;
  public static final long 男洗手间_ID = 23024000L;
  public static final long 女洗手间_ID = 23025000L;
  public static final long 残障洗手间_ID = 23059000L;
  public static final long 洗手间_ID = 23063000L;

  public static final long 安全出口_ID = 23061000L;
  public static final long 建筑物大门 = 23043000L;
  public static final long 门窗楼梯_ID = 13164000L;
  public static final long 盥洗室_ID = 23047000L;
  public static final long 茶水间_ID = 23040000L;//饮水处
  public static final long 商务办公_ID = 23027000L;//办公室、实验室

  public static final long 空地_POI_ID = 1262934L;
  //四个poi详情
  public static final String H2大厅 = "H2大厅";//poi详情
  public static final String 会议室 = "会议室";//poi详情
  public static final String ICS实验室 = "ICS实验室";//poi详情
  public static final String ICS办公区 = "ICS办公区";//poi详情
  public static final String H2大楼 = "H2大楼";//poi详情
  public static final String 其它 = "其它";//poi详情


  public static int   MAX_ZOOM = 5;
  public static int   MIN_ZOOM = 1;

  public static final String FACILITY_LAYER = "Facility";
  public static final String FACILITY_KEY_CAT = "category";
  public static final String FACILITY_KEY_NAME = "name";

  public static final String AREA_LAYER = "Area";
  public static final String AREA_KEY = "display";
  //设置开关
  //使用新平台接口与服务器通讯
  public static Boolean useAPIV2 = true;


  //是否开启地图缓存模式
  public static Boolean useCash = true;



  public static boolean onSingleTap_changeColor =true;//是否变色



  public static final int EACH_TIME_COMMENT_NUM = 5;
  public static final int EACH_TIME_PICTURE_NUM = 10;

  //startActivityForResult
  public static final int startTakePic = 0;
  public static final int startPay = 2;
  public static final int startWelcome = 1;
  public static final int startOffice=3;
  public static final int startUploadText=4;
  public static final int startUploadPic=5;

  //测试参数，控制
  public static Boolean isDebug = false;

  public static boolean openLocateService = true;//开启自动循环查询定位,本地测试也要开启

  public static boolean useTestServer = false;//是否使用内网测试服务器
  public static boolean openLocateTest = true;//开启本地定位测试

  public static boolean usePhoneIp = true;

  public static String testIp = "10.1.137.118";

  public static long LOCATE_FRESH_TIME = 2000;//定位间隔时间

  public static final double NAV_MIN_DISTANCE = 2.0;

  public static boolean useSystemShare = true;//是否使用系统分享
}
