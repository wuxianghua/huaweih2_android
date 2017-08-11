package com.palmap.demo.huaweih2;

import android.content.Intent;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.palmap.demo.huaweih2.other.Constant;
import com.palmap.demo.huaweih2.util.FileUtils;
import com.palmaplus.nagrand.core.Engine;
import com.tencent.bugly.Bugly;
import com.tencent.smtt.sdk.QbSdk;

import org.xq.com.xiaoqian.application.XiaoqianApplication;

/**
 * Created by eric3 on 2016/10/8.
 */

public class HuaWeiH2Application extends XiaoqianApplication {
  public static boolean firstRun = false;//第一次启动
  public static boolean startWelcomeAct = false;//启动欢迎界面
  public static HuaWeiH2Application instance;
  public static String userIp = "00000000";

  //public static PlanarGraph planarGraphF1 = null;//缓存楼层数据
  //public static PlanarGraph planarGraphB1= null;

  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;
    copyPalmapFile();
    Bugly.init(getApplicationContext(), "d0fd96e1a6", false);
    Engine.getInstance();
    if (BuildConfig.DEBUG) {
      Stetho.initialize(
              Stetho.newInitializerBuilder(this)
                      .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                      .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                      .build());
    }
    QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

      @Override
      public void onViewInitFinished(boolean arg0) {
        // TODO Auto-generated method stub
        //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
        Log.d("app", " onViewInitFinished is " + arg0);
      }

      @Override
      public void onCoreInitFinished() {
        // TODO Auto-generated method stub
      }
    };
    //x5内核初始化接口
    QbSdk.initX5Environment(getApplicationContext(),  cb);
  }

  private void copyPalmapFile(){
    // copy字体文件和lur配置文件
//    if (FileUtils.checkoutSDCard()) {
      FileUtils.copyDirToSDCardFromAsserts(this, Constant.LUR_NAME, "lua");
    FileUtils.copyDirToSDCardFromAsserts(this, Constant.H, "H");
//      FileUtils.copyDirToSDCardFromAsserts(this, Constant.LUR_NAME, "font");
//      FileUtils.copyDirToSDCardFromAsserts(this, Constant.LUR_NAME, Constant.LUR_NAME);
//    } else {
//      DialogUtils.showShortToast("未找到存储器");
//    }
  }



  @Override
  public void onTerminate() {
    super.onTerminate();

    if (LocateTimerService.getInstance()!=null) {

      //停止由AlarmManager启动的循环
      LocateTimerService.stop(this);
      //停止由服务启动的循环
      Intent intent = new Intent(this, LocateTimerService.class);
      stopService(intent);
    }
  }
}
