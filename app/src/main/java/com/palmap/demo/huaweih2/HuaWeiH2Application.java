package com.palmap.demo.huaweih2;

import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.palmap.demo.huaweih2.other.Constant;
import com.palmap.demo.huaweih2.util.DialogUtils;
import com.palmap.demo.huaweih2.util.FileUtils;

import java.io.File;

/**
 * Created by eric3 on 2016/10/8.
 */

public class HuaWeiH2Application extends Application {
  public static boolean firstRun = false;//第一次启动
  public static HuaWeiH2Application instance;
  public static String userIp = "00000000";

  @Override
  public void onCreate() {
    super.onCreate();

    instance = this;
    copyPalmapFile();

    initImageLoader();


  }

  private void copyPalmapFile(){
    // copy字体文件和lur配置文件
    if (FileUtils.checkoutSDCard()) {
      FileUtils.copyDirToSDCardFromAsserts(this, Constant.LUR_NAME, "lua");
//      FileUtils.copyDirToSDCardFromAsserts(this, Constant.LUR_NAME, "font");
//      FileUtils.copyDirToSDCardFromAsserts(this, Constant.LUR_NAME, Constant.LUR_NAME);
    } else {
      DialogUtils.showShortToast("未找到SDCard");
    }
  }

  private void initImageLoader(){
//    ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);


    File cacheDir = StorageUtils.getCacheDirectory(this);  //缓存文件夹路径
    ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
        .memoryCacheExtraOptions(480, 800) // default = device screen dimensions 内存缓存文件的最大长宽
        .tasksProcessingOrder(QueueProcessingType.FIFO) // default
        .denyCacheImageMultipleSizesInMemory()
        .memoryCache(new LruMemoryCache(2 * 1024 * 1024)) //可以通过自己的内存缓存实现
        .memoryCacheSize(2 * 1024 * 1024)  // 内存缓存的最大值
        .memoryCacheSizePercentage(13) // default
        .diskCache(new UnlimitedDiscCache(cacheDir)) // default 可以自定义缓存路径
        .diskCacheSize(100 * 1024 * 1024) // 100 Mb sd卡(本地)缓存的最大值
        .diskCacheFileCount(100)  // 可以缓存的文件数量
        // default为使用HASHCODE对UIL进行加密命名， 还可以用MD5(new Md5FileNameGenerator())加密
        .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
        .imageDownloader(new BaseImageDownloader(this)) // default
        .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
        .writeDebugLogs() // 打印debug log
        .build(); //开始构建


    ImageLoader.getInstance().init(config);
  }

}
