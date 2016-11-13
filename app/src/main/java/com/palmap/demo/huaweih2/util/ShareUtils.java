package com.palmap.demo.huaweih2.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.io.File;

/**
 * Created by eric3 on 2016/11/13.
 */

public class ShareUtils {
  //分享文字
  public static void shareText(Context context,String text) {
    Intent shareIntent = new Intent();
    shareIntent.setAction(Intent.ACTION_SEND);
    shareIntent.putExtra(Intent.EXTRA_TEXT, text);
    shareIntent.setType("text/plain");

    //设置分享列表的标题，并且每次都显示分享列表
    context.startActivity(Intent.createChooser(shareIntent, "分享到"));
  }

  //分享单张图片
  public static void shareImage(Context context,String imagePath) {
    //由文件得到uri
    Uri imageUri = Uri.fromFile(new File(imagePath));
    Log.d("share", "uri:" + imageUri);  //输出：file:///storage/emulated/0/test.jpg

    Intent shareIntent = new Intent();
    shareIntent.setAction(Intent.ACTION_SEND);
    shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
    shareIntent.setType("image/*");
    context.startActivity(Intent.createChooser(shareIntent, "分享到"));
  }

//  //分享多张图片
//  public void shareMultipleImage(Context context,String imagePath) {
//    ArrayList<uri> uriList = new ArrayList<>();
//
//    String path = Environment.getExternalStorageDirectory() + File.separator;
//    uriList.add(Uri.fromFile(new File(path+"australia_1.jpg")));
//    uriList.add(Uri.fromFile(new File(path+"australia_2.jpg")));
//    uriList.add(Uri.fromFile(new File(path+"australia_3.jpg")));
//
//    Intent shareIntent = new Intent();
//    shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
//    shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList);
//    shareIntent.setType("image/*");
//    startActivity(Intent.createChooser(shareIntent, "分享到"));
//  }
}
