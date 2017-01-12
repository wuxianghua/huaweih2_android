package com.palmap.demo.huaweih2;

import android.content.Intent;

import com.bugtags.library.Bugtags;
import com.bugtags.library.BugtagsOptions;
import com.facebook.stetho.Stetho;
import com.palmap.demo.huaweih2.other.Constant;
import com.palmap.demo.huaweih2.util.FileUtils;
import com.palmaplus.nagrand.core.Engine;
import com.palmaplus.nagrand.data.PlanarGraph;

import org.xq.com.xiaoqian.application.XiaoqianApplication;

/**
 * Created by eric3 on 2016/10/8.
 */

public class HuaWeiH2Application extends XiaoqianApplication {
  public static boolean firstRun = false;//第一次启动
  public static boolean startWelcomeAct = false;//启动欢迎界面
  public static HuaWeiH2Application instance;
  public static String userIp = "00000000";

  public static PlanarGraph planarGraphF1 = null;//缓存楼层数据
  public static PlanarGraph planarGraphB1= null;

  @Override
  public void onCreate() {
    super.onCreate();

    instance = this;
    copyPalmapFile();

    Engine.getInstance();
    if (BuildConfig.DEBUG) {

      Stetho.initialize(
              Stetho.newInitializerBuilder(this)
                      .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                      .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                      .build());
    }

    //Bugtags在这里初始化
    BugtagsOptions options = new BugtagsOptions.Builder().
        trackingLocation(true).//是否获取位置，默认 true
        trackingCrashLog(true).//是否收集crash，默认 true
        trackingConsoleLog(true).//是否收集console log，默认 true
        trackingUserSteps(true).//是否收集用户操作步骤，默认 true
        trackingNetworkURLFilter("(.*)").//自定义网络请求跟踪的 text 规则，默认 null
        build();
    Bugtags.start("267921bb8c8c795df7f7ffa41ac5d307", this, Bugtags.BTGInvocationEventNone,options);

  }

  private void copyPalmapFile(){
    // copy字体文件和lur配置文件
//    if (FileUtils.checkoutSDCard()) {
      FileUtils.copyDirToSDCardFromAsserts(this, Constant.LUR_NAME, "lua");
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
