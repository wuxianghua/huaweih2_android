package com.palmap.demo.huaweih2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.palmap.demo.huaweih2.http.DataProviderCenter;
import com.palmap.demo.huaweih2.http.ErrorCode;
import com.palmap.demo.huaweih2.http.HttpDataCallBack;
import com.palmap.demo.huaweih2.json.PositionJson;
import com.palmap.demo.huaweih2.other.Constant;
import com.palmap.demo.huaweih2.util.DialogUtils;
import com.palmap.demo.huaweih2.util.GpsUtils;
import com.palmap.demo.huaweih2.util.IpUtils;
import com.palmap.demo.huaweih2.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import static com.palmap.demo.huaweih2.fragment.FragmentMap.hasLocated;

/**
 * Created by eric3 on 2016/10/23.
 */

public class LocateTimerService extends Service {
    private boolean isReceived;//上次发送定位请求后是否收到返回
    private long timestampRec;//上次收到定位数据时间戳
    public static double curX;//当前定位点坐标，可能是gps和lampsite的结果
    public static double curY;
    public static long curFloorID;//当前定位点floor
    static Context mContext;
    static MainActivity mMainActivity;
    private boolean pushthread = false;
    private static LocateTimerService instance;
    private boolean isUseGpsLocation;//是否在室外，使用gps数据
    //地理围栏
    static double[] hall = new double[]{12697136.696, 12697172.696, 2588890.655, 2588917.384
    };//  x小,x大,y小,y大
    static double[] meeting = new double[]{12697125.262, 12697133.136, 2588900.784, 2588917.194
    };
    static double[] lab = new double[]{12697094.209, 12697102.759, 2588900.784, 2588912.914
    };
    static double[] office = new double[]{12697081.767, 12697093.989, 2588868.311, 2588890.293
    };
    static double[] h2 = new double[]{12697080.571, 12697195.929, 2588843.728, 2588958.557
    };
    static double[] h2_out = new double[]{12697149.692, 12697171.838, 2588912.244,2588957.663
    };

    //6个定位参测试点坐标
    double[] x = {
            12697093.187609216,
            12697093.172831079,
            12697093.154337427,
            12697093.132151147,
            12697093.1062875,
            12697092.882807275,
            12697092.651926538,
            12697092.611269495,
            12697092.387812158,
            12697092.179102441,
            12697092.160631677,
            12697092.145838281,
            12697092.138445398,
            12697092.119974634,
            12697092.105173608,
            12697092.090395471,
            12697092.064508935,
            12697092.042337915,
            12697092.016459009,
            12697092.184488794,
            12697092.158625146,
            12697092.33032467,
            12697092.311846277,
            12697092.293360254,
            12697092.278574487,
            12697092.267496606,
            12697092.256388208,
            12697092.245310327,
            12697092.420740625,
            12697092.402246973,
            12697092.581385156,
            12697092.570292016,
            12697092.559198877,
            12697092.548105737,
            12697092.540720483,
            12697092.529634973,
            12697092.518534204,
            12697092.503748437,
            12697092.477869531,
            12697092.455698511,
            12697092.437212488,
            12697092.42243435,
            12697092.407633325,
            12697092.392855188,
            12697092.374369165,
            12697092.542383691,
            12697092.710413476,
            12697092.684549829,
            12697092.66236355,
            12697092.83776333,
            12697092.819277307,
            12697092.808191797,
            12697092.800798913,
            12697092.789698144,
            12697092.786005517,
            12697092.774920007,
            12697092.767527124,
            12697092.76014187,
            12697092.756449243,
            12697092.752733728,
            12697092.749041101,
            12697092.741648218,
            12697092.737955591,
            12697092.734262964,
            12697092.730562707,
            12697092.72687008,
            12697092.723169824,
            12697092.719477197,
            12697092.715769311,
            12697092.905985376,
            12697093.286386987,
            12697093.476587793,
            12697093.856989404,
            12697094.237391016,
            12697095.188417932,
            12697096.13942959,
            12697097.470835229,
            12697098.615778467,
            12697099.947184106,
            12697101.46881344,
            12697102.990427515,
            12697104.702265283,
            12697106.604280969,
            12697108.69651272,
            12697110.788752099,
            12697112.880968591,
            12697114.592783472,
            12697116.685022851,
            12697118.587038537,
            12697120.492781183,
            12697122.204584619,
            12697124.300516626,
            12697126.392748376,
            12697128.298460504,
            12697130.0139909,
            12697131.539301416,
            12697132.870722314,
            12697134.202139398,
            12697135.723768732,
            12697137.245386621,
            12697138.957205316,
            12697140.292326471,
            12697141.43353512,
            12697142.764952203,
            12697143.715967676,
            12697144.864576837,
            12697145.81558468,
            12697146.766603967,
            12697148.101717493,
            12697149.623339197,
            12697150.954758188,
            12697152.286175271,
            12697153.617592355,
            12697154.568602106,
            12697155.519625207,
            12697156.280434152,
            12697157.231443902,
            12697158.182453653,
            12697159.327389261,
            12697160.278399011,
            12697161.039207956,
            12697161.80002453,
            12697162.560831567,
            12697163.321642419,
            12697163.892248651,
            12697164.462851068,
            12697165.033464929,
            12697165.604074975,
            12697166.174677392,
            12697166.745283624,
            12697167.315893671,
            12697168.076708337,
            12697168.647318384,
            12697169.408125421,
            12697170.359137079,
            12697170.92975094,
            12697171.500353357,
            12697172.261160394,
            12697172.641569635,
            12697173.212179681,
            12697173.592581293,
            12697174.163195154,
            12697174.543596765,
            12697174.924002191,
            12697175.494612237,
            12697175.875013849,
            12697176.255423089,
            12697176.635820886,
            12697177.016237756,
            12697177.396639368,
            12697177.777044794,
            12697178.157446405,
            12697178.537855646,
            12697179.108458063,
            12697179.298662683,
            12697179.488863489,
            12697179.8692651,
            12697180.439882776,
            12697180.820288202,
            12697181.200689813,
            12697181.581099054,
            12697181.961500665,
            12697182.341906091,
            12697182.722307703,
            12697182.912508508,
            12697183.102724573,
            12697183.483122369,
            12697183.859816095,
            12697184.05002453,
            12697184.430422327,
            12697184.810831567,
            12697185.001032373,
            12697185.191233179,
            12697185.187536737,
            12697185.180147668,
            12697185.1727586,
            12697185.165365716,
            12697185.154268762,
            12697184.956675073,
            12697184.759085199,
            12697184.561487695,
            12697184.367594263,
            12697184.170000574,
            12697184.162596246,
            12697183.96130993,
            12697183.756323358,
            12697183.543936273,
            12697183.32416012,
            12697182.914198419,
            12697182.69071438,
            12697182.467241785,
            12697182.240076562,
            12697182.01291134,
            12697181.793127557,
            12697181.569658777,
            12697181.349882623,
            12697181.126425287,
            12697180.906641504,
            12697180.877066156,
            12697180.85118725,
            12697180.821623346,
            12697180.79574444,
            12697180.769865533,
            12697180.743982812,
            12697180.714422723,
            12697180.688543817,
            12697180.662661096,
            12697181.017206689,
            12697180.99502041,
            12697181.170439264,
            12697181.345846673,
            12697181.331064722,
            12697181.506491205,
            12697181.681898614,
            12697181.853621027,
            12697182.025331995,
            12697182.010553857,
            12697182.182276269,
            12697182.354002496,
            12697182.529406091,
            12697182.510931512,
            12697182.49614956,
            12697182.477659723,
            12697182.459185144,
            12697182.444391748,
            12697182.433306238,
            12697182.61611416,
            12697182.608709833,
            12697182.787825128,
            12697182.780436059,
            12697182.963251611,
            12697182.959558984,
            12697183.146063348,
            12697183.332567712,
            12697183.328859827,
            12697183.519060632,
            12697183.515364191,
            12697183.705572626,
            12697183.701876184
    };
    double[] y = {
            2588848.4471992431,
            2588849.2080024658,
            2588850.1590217529,
            2588851.3002418457,
            2588852.6316627441,
            2588854.3397773681,
            2588856.4283241211,
            2588858.5205482421,
            2588860.228678125,
            2588861.1759895263,
            2588862.1269935546,
            2588862.8878120361,
            2588863.2682289062,
            2588864.2192329345,
            2588864.980051416,
            2588865.7408546386,
            2588867.0722755371,
            2588868.2134956298,
            2588869.5449012695,
            2588870.689829248,
            2588872.0212348877,
            2588872.9759468017,
            2588873.9269660888,
            2588874.8779701171,
            2588875.6387885986,
            2588876.2094062744,
            2588876.7800086914,
            2588877.3506111084,
            2588878.1151222168,
            2588879.0661415039,
            2588879.6404365478,
            2588880.2110542236,
            2588880.7816566406,
            2588881.3522743164,
            2588881.7326759277,
            2588882.3032783447,
            2588882.8738960205,
            2588883.6346992431,
            2588884.9661201416,
            2588886.1073402343,
            2588887.0583442627,
            2588887.8191627441,
            2588888.5799659668,
            2588889.3407844482,
            2588890.2917884765,
            2588891.4367011962,
            2588892.581613916,
            2588893.9130348144,
            2588895.0542549072,
            2588895.8187583862,
            2588896.7697776733,
            2588897.3403877197,
            2588897.720789331,
            2588898.291391748,
            2588898.4815925537,
            2588899.0522026001,
            2588899.4326118408,
            2588899.8130210815,
            2588900.0032218872,
            2588900.1934226928,
            2588900.3836234985,
            2588900.7640327392,
            2588900.9542335449,
            2588901.1444343505,
            2588901.3346351562,
            2588901.5248359619,
            2588901.7150443969,
            2588901.9052452026,
            2588902.0954536377,
            2588902.099153894,
            2588902.1065467773,
            2588902.1102394043,
            2588902.1176322876,
            2588902.1250251709,
            2588902.1435111938,
            2588902.1619895874,
            2588902.1878684936,
            2588902.0198387085,
            2588902.0457176147,
            2588902.0752891479,
            2588902.1048606811,
            2588902.1381324707,
            2588902.1750892578,
            2588902.2157539306,
            2588902.2564186035,
            2588902.2970832763,
            2588902.3303474365,
            2588902.3710121093,
            2588902.4079765258,
            2588902.2547401367,
            2588902.2880042968,
            2588902.1384605346,
            2588902.1791252075,
            2588902.0258888183,
            2588901.8689521728,
            2588901.7083229003,
            2588901.7341941772,
            2588901.7600730835,
            2588901.7896446167,
            2588901.8192161499,
            2588901.8524879394,
            2588901.6881584106,
            2588901.7103370605,
            2588901.7362159668,
            2588901.7546943603,
            2588901.3964713989,
            2588901.4149497924,
            2588901.4334358154,
            2588901.269113916,
            2588901.2986854492,
            2588901.324556726,
            2588901.3504356323,
            2588901.3763069091,
            2588901.3947929321,
            2588901.4132713256,
            2588901.4280647216,
            2588901.4465431152,
            2588901.4650215087,
            2588901.2970069824,
            2588901.3154853759,
            2588901.3302711425,
            2588901.3450569091,
            2588901.3598426757,
            2588901.3746284423,
            2588901.385721582,
            2588901.3968070922,
            2588901.4079002319,
            2588901.4189857421,
            2588901.4300788818,
            2588901.4411720214,
            2588901.4522575317,
            2588901.4670432983,
            2588901.4781364379,
            2588901.4929222045,
            2588901.5114005981,
            2588901.5224937377,
            2588901.533579248,
            2588901.5483650146,
            2588901.5557578979,
            2588901.5668510376,
            2588901.5742439209,
            2588901.5853370605,
            2588901.5927223144,
            2588901.6001228271,
            2588901.6112083374,
            2588901.6186012207,
            2588901.625994104,
            2588901.6333869873,
            2588901.6407798706,
            2588901.6481727539,
            2588901.6555656372,
            2588901.6629585205,
            2588901.6703514038,
            2588901.6814445434,
            2588901.6851371704,
            2588901.6888374267,
            2588901.69623031,
            2588901.7073158203,
            2588901.7147087036,
            2588901.7221015869,
            2588901.7294944702,
            2588901.7368873535,
            2588901.7442878662,
            2588901.7516731201,
            2588901.7553733764,
            2588901.7590736328,
            2588901.7664588867,
            2588901.964060205,
            2588901.967752832,
            2588901.9751457153,
            2588901.9825385986,
            2588901.9862312255,
            2588901.9899314819,
            2588902.1801322876,
            2588902.5605415283,
            2588902.940950769,
            2588903.3213523803,
            2588903.8919624267,
            2588904.2686714111,
            2588904.6453803955,
            2588905.0220893798,
            2588905.2085899292,
            2588905.5852989135,
            2588905.9657081543,
            2588906.5326179443,
            2588907.2897361694,
            2588908.4272483764,
            2588909.9451774536,
            2588911.4594062744,
            2588913.1675361572,
            2588914.87566604,
            2588916.7739890991,
            2588918.6723197876,
            2588920.1902412353,
            2588921.8983787475,
            2588923.4163001953,
            2588925.1244300781,
            2588926.6423591552,
            2588928.1639808593,
            2588929.495386499,
            2588931.0170158325,
            2588932.3484291015,
            2588933.67985,
            2588935.0112708984,
            2588936.5328926025,
            2588937.8643135009,
            2588939.1957343994,
            2588940.5345405517,
            2588941.6757606445,
            2588942.4402641235,
            2588943.2047752319,
            2588943.9655860839,
            2588944.7300895629,
            2588945.4946006713,
            2588946.449304956,
            2588947.4040168701,
            2588948.1648353515,
            2588949.1195396362,
            2588950.0742515502,
            2588950.8387550293,
            2588951.789766687,
            2588952.550577539,
            2588953.5015968261,
            2588954.4526008544,
            2588955.2134193359,
            2588955.7840217529,
            2588956.16813125,
            2588956.5485328613,
            2588957.1228355346,
            2588957.5032447753,
            2588957.887346643,
            2588958.0775474487,
            2588958.2714485107,
            2588958.4653495727,
            2588958.6555503784,
            2588958.6592506347,
            2588958.8494514404,
            2588958.8531440673,
            2588959.0433525024
    };
    int count = 0;

    public LocateTimerService() {
    }

    public static void setmMainActivity(MainActivity mMainActivity) {
        LocateTimerService.mMainActivity = mMainActivity;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("TimerService", "onBind");
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        instance = this;
        Log.d("TimerService", "onStartCommand");
        String ext = intent.getStringExtra("flags");
        if (ext == null) {
            DialogUtils.showLongToast("请重启定位服务");
        }
        if ("3".equals(ext)) {
            //判断当系统版本大于20，即超过Android5.0时，我们采用线程循环的方式请求。
            //当小于5.0时的系统则采用定时唤醒服务的方式执行循环
            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            if (currentapiVersion > 20) {
                getPushThread();
            } else {
                getHttp();
            }
        }
        return START_NOT_STICKY;
    }

    //循环请求的线程
    private void getPushThread() {
        pushthread = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (pushthread) {
                    try {
                        Thread.sleep(Constant.LOCATE_FRESH_TIME);
                        getHttp();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    //请求网络获取数据
    private void getHttp() {

        LogUtils.i(""+(System.currentTimeMillis()-timestampRec));
        if (!isReceived&&System.currentTimeMillis()-timestampRec<10000)
            return;

        isReceived = false;
        HuaWeiH2Application.userIp = IpUtils.getIpAddress();

        DataProviderCenter.getInstance().getPosition( new HttpDataCallBack() {
            @Override
            public void onError(int errorCode) {
                isReceived = true;
                timestampRec = System.currentTimeMillis();
                isUseGpsLocation = true;//没有lampsite数据，只能使用gps

                if (Constant.openLocateTest) {//测试代码，虚拟定位点
                    curX = x[count];
                    curY = y[count];
                    curFloorID = Constant.FLOOR_ID_F1;
                    hasLocated = true;
                    if (mMainActivity != null && mMainActivity.fragmentMap != null) {
                        mMainActivity.fragmentMap.addLocationMark(curX, curY);

                    }
                    count = ++count % (x.length);
                } else {

                    if (mMainActivity == null || mMainActivity.fragmentMap == null)
                        return;
                    //没有定位数据，查看是否有gps

                    curFloorID = Constant.FLOOR_ID_F1;
                    curX = GpsUtils.getCurX();
                    curY = GpsUtils.getCurY();

                    // TODO: 2016/12/29 室外hasLocated=true怎么办
                    hasLocated = false;

                    addLocationMark();
//                    if (mMainActivity.fragmentMap.mCurrentFloor == Constant.FLOOR_ID_F1) {
//                        mMainActivity.fragmentMap.addLocationMark(curX, curY);
//                        if (Constant.isDebug)
//                            ErrorCode.showError(ErrorCode.CODE_GPS);
//                    }
//                    ErrorCode.showError(CODE_NO_LOCATE_DATA);
                }
            }

            @Override
            public void onComplete(Object content) {
                isReceived = true;
                timestampRec = System.currentTimeMillis();
                if (mMainActivity == null || mMainActivity.fragmentMap == null)
                    return;
                try {
                    if (Constant.useTestServer) {
                        List<PositionJson> list = new ArrayList<PositionJson>(JSONArray.parseArray(content.toString(), PositionJson.class));
                        curY = list.get(0).getY();
                        curX = list.get(0).getX();
                        if (mMainActivity.fragmentMap.mCurrentFloor == Constant.FLOOR_ID_F1)
                            mMainActivity.fragmentMap.addLocationMark(curX, curY);
                    } else {
                        JSONObject jo1 = JSON.parseObject(content.toString());
                        JSONObject jo2 = jo1.getJSONObject("geometry");
                        JSONObject jo4 = jo1.getJSONObject("properties");
                        curFloorID = jo4.getLongValue("floor_id");
                        JSONArray jo3 = jo2.getJSONArray("coordinates");
                        curX = jo3.getDoubleValue(0);
                        curY = jo3.getDoubleValue(1);
                        hasLocated = true;

                        if (GpsUtils.hasExactGpsData||(curX > h2_out[0] && curX < h2_out[1] && curY > h2_out[2] && curY < h2_out[3])){
                            /*如果最近 EXP_SECONDS s内有精度小于18m的gps数据，就强制用gps
                               避免lampsite在室外也能获取不准确的定位点*/
                            isUseGpsLocation = true;
                            curX = GpsUtils.getCurX();
                            curY = GpsUtils.getCurY();
                            hasLocated = false;
                        }else {
                            isUseGpsLocation = false;//使用lampsite数据
                        }




                        addLocationMark();
//                        if (mMainActivity.fragmentMap.mCurrentFloor == Constant.FLOOR_ID_F1) {
//                            mMainActivity.fragmentMap.addLocationMark(curX, curY);
//                            if (Constant.isDebug)
//                                ErrorCode.showError(ErrorCode.CODE_HUAWEI);
//                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onDestroy() {
        pushthread = false;
        Log.d("TimerService", "onDestroy");
        super.onDestroy();
    }

    //启动服务和定时器
    public static void start(Context mContext) {
        LocateTimerService.mContext = mContext;
        try {
            Intent intent = new Intent(mContext, LocateTimerService.class);
            intent.putExtra("flags", "3");
            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            if (currentapiVersion > 20) {
                //一般的启动服务的方式
                mContext.startService(intent);
            } else {
                //定时唤醒服务的启动方式
                PendingIntent pIntent = PendingIntent.getService(mContext, 0,
                        intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) mContext
                        .getSystemService(Context.ALARM_SERVICE);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                        System.currentTimeMillis(), Constant.LOCATE_FRESH_TIME, pIntent);
            }
        } catch (Exception e) {
            DialogUtils.showShortToast(e.getMessage());
        }
    }

    public static LocateTimerService getInstance() {
        return instance;
    }

    //停止由AlarmManager启动的循环
    public static void stop(Context mContext) {
        instance = null;
        Intent intent = new Intent(mContext, LocateTimerService.class);
        PendingIntent pIntent = PendingIntent.getService(mContext, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) mContext
                .getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pIntent);
    }

    public static String getCurrentLocationArea() {

        if (mMainActivity == null || mMainActivity.fragmentMap == null || mMainActivity.fragmentMap.mMapView == null)
            return Constant.其它;

        String poiName = Constant.其它;


//    Types.Point point = mContext.fragmentMap.mMapView.converToScreenCoordinate(curX,curY);
//    poiName=mContext.fragmentMap.getPOINameByPoint((float) point.x,(float)point.y);

        if (curX > h2[0] && curX < h2[1] && curY > h2[2] && curY < h2[3])
            poiName = Constant.H2大楼;
        if (curX > hall[0] && curX < hall[1] && curY > hall[2] && curY < hall[3])
            poiName = Constant.H2大厅;
        if (curX > lab[0] && curX < lab[1] && curY > lab[2] && curY < lab[3])
            poiName = Constant.ICS实验室;
        if (curX > office[0] && curX < office[1] && curY > office[2] && curY < office[3])
            poiName = Constant.ICS办公区;
        if (curX > meeting[0] && curX < meeting[1] && curY > meeting[2] && curY < meeting[3])
            poiName = Constant.会议室;



        return poiName;
    }



    /**
    * @Author: eric3
    * @Description: 根据gps和lampsite状态选择有效的定位点，添加到地图
    * @Time 2016/12/29 12:14
    */
    private void addLocationMark(){

        if (mMainActivity.fragmentMap.mCurrentFloor == Constant.FLOOR_ID_F1)
            mMainActivity.fragmentMap.addLocationMark(curX, curY);

        if (Constant.isDebug){
            if (isUseGpsLocation){
                ErrorCode.showError(ErrorCode.CODE_GPS);
            }else{
                ErrorCode.showError(ErrorCode.CODE_HUAWEI);
            }
        }


    }
}