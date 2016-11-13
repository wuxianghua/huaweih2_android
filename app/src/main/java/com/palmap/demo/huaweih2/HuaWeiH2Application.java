package com.palmap.demo.huaweih2;

import android.app.Application;
import android.content.Intent;

import com.bugtags.library.Bugtags;
import com.bugtags.library.BugtagsOptions;
import com.bumptech.glide.Glide;
import com.palmap.demo.huaweih2.model.MyPlanarGraph;
import com.palmap.demo.huaweih2.other.Constant;
import com.palmap.demo.huaweih2.util.FileUtils;

/**
 * Created by eric3 on 2016/10/8.
 */

public class HuaWeiH2Application extends Application {
  public static boolean firstRun = false;//第一次启动
  public static boolean startWelcomeAct = false;//启动欢迎界面
  public static HuaWeiH2Application instance;
  public static String userIp = "00000000";

  public static MyPlanarGraph planarGraphF1 = null;//缓存楼层数据
  public static MyPlanarGraph planarGraphB1= null;

  @Override
  public void onCreate() {
    super.onCreate();

    instance = this;
    copyPalmapFile();







//    initImageLoader();
    if (BuildConfig.DEBUG) {
      new Thread(new Runnable() {
        @Override
        public void run() {
          Glide.get(HuaWeiH2Application.this).clearDiskCache();
        }
      }).start();
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

//  private void initImageLoader(){
////    ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
//
//
//    File cacheDir = StorageUtils.getCacheDirectory(this);  //缓存文件夹路径
//    ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
//        .memoryCacheExtraOptions(480, 800) // default = device screen dimensions 内存缓存文件的最大长宽
//        .tasksProcessingOrder(QueueProcessingType.FIFO) // default
//        .denyCacheImageMultipleSizesInMemory()
//        .memoryCache(new LruMemoryCache(2 * 1024 * 1024)) //可以通过自己的内存缓存实现
//        .memoryCacheSize(2 * 1024 * 1024)  // 内存缓存的最大值
//        .memoryCacheSizePercentage(13) // default
//        .diskCache(new UnlimitedDiscCache(cacheDir)) // default 可以自定义缓存路径
//        .diskCacheSize(100 * 1024 * 1024) // 100 Mb sd卡(本地)缓存的最大值
//        .diskCacheFileCount(100)  // 可以缓存的文件数量
//        // default为使用HASHCODE对UIL进行加密命名， 还可以用MD5(new Md5FileNameGenerator())加密
//        .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
//        .imageDownloader(new BaseImageDownloader(this)) // default
//        .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
//        .writeDebugLogs() // 打印debug log
//        .build(); //开始构建
//
//
//    ImageLoader.getInstance().init(config);
//  }


  @Override
  public void onTerminate() {
    super.onTerminate();

    if (LocateTimerService.getInstance()!=null) {
//      HuaWeiH2Application.firstRun=false;

      //停止由AlarmManager启动的循环
      LocateTimerService.stop(this);
      //停止由服务启动的循环
      Intent intent = new Intent(this, LocateTimerService.class);
      stopService(intent);
    }
  }
}
