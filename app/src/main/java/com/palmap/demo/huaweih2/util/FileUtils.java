package com.palmap.demo.huaweih2.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by eric3 on 2016/10/8.
 */

public class FileUtils {
  private static final String DIR_NAME = "palmap";
  /*
 *  检测SDCard是否存在
 * */
  public static boolean checkoutSDCard() {
    return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
  }

  /*
  *  将asserts下一个指定文件夹中所有文件copy到SDCard中
  * */
  public static void copyDirToSDCardFromAsserts(Context context, String dirNameTo, String dirNameFromAssert) {
    try {
      AssetManager assetManager = context.getAssets();
      String[] fileList = assetManager.list(dirNameFromAssert);
      outputStr(dirNameFromAssert, fileList); // 输出dirName2中文件名
      String dir = Environment.getExternalStorageDirectory() + File.separator + dirNameTo;

      if (fileList != null && fileList.length > 0) {
        File file = null;

        // 创建文件夹
        file = new File(dir);
        if (!file.exists()) {
          file.mkdirs();
        } else {
          Log.w("FileUtilsTools", dir + "已存在.");
          deleteDirectory(dir);
          file.mkdirs();
        }

        // 创建文件
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        byte[] buffer = new byte[1024];
        int len = -1;
        for (int i = 0; i < fileList.length; i++) {
          file = new File(dir, fileList[i]);
          if (!file.exists()) {
            file.createNewFile();
          }
          inputStream = assetManager.open(dirNameFromAssert + File.separator + fileList[i]);
          outputStream = new FileOutputStream(file);
          while ((len = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
          }
          outputStream.flush();
        }

        // 关流
        if (inputStream != null) {
          inputStream.close();
        }
        if (outputStream != null) {
          outputStream.close();
        }
      }
    } catch (IOException e) {
      Log.e("FileUtilsTools", "IOException e");
      e.printStackTrace();
    }
  }

  public Bitmap getImage(String path){
    File mFile=new File(path);
    //若该文件存在
    if (mFile.exists()) {
      Bitmap bitmap= BitmapFactory.decodeFile(path);
      return bitmap;
    }else {
      DialogUtils.showShortToast("图片不存在。");
      return null;
    }
  }
  /*
  * 输出String[]中内容
  * 作用：输出文件夹中文件名
  * */
  public static void outputStr(String dirName, String[] listStr) {
    if (listStr != null) {
      if (listStr.length <= 0) {
        Log.w("FileUtilsTools", dirName + "文件为空");
      } else {
        Log.w("FileUtilsTools", dirName + "文件中有以下文件：");
        for (String str : listStr) {
          Log.w("FileUtilsTools", str);
        }
      }
    }
  }

  /*
  * 导出指定名称的数据库文件
  * */
  public static void outputDBFile(Context context, String dbName, String fileName){

    if (!checkoutSDCard()){
      Log.w("FileUtils","SDCard不存在");
      return;
    }

    File file = context.getDatabasePath(dbName);
    Log.w("FileUtils", "file path: " + file.getAbsolutePath());
    Log.w("FileUtils", "file name: " + file.getName());
    if (file.exists() && file.isFile()){
      String dirName = Environment.getExternalStorageDirectory().getPath() + File.separator + DIR_NAME;
      File dirFile = new File(dirName);
      if (!dirFile.exists()){
        dirFile.mkdirs();
      }

      if (fileName == null){
        fileName = file.getName();
      }
      File outputFile = new File(dirFile, fileName + ".db");
      if (outputFile.exists()){
        outputFile.delete();
      }

      FileOutputStream fos = null;
      FileInputStream fis = null;
      try {
        outputFile.createNewFile();
        fos = new FileOutputStream(outputFile);
        fis = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int length = -1;
        while ((length = fis.read(buffer)) > 0){
          fos.write(buffer, 0,length);
        }
        fos.flush();
        Log.w("FileUtils", "outputFile path: " + outputFile.getAbsolutePath());
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        try {
          if (fis != null){
            fis.close();
          }
          if (fos != null){
            fos.close();
          }
        }catch (Exception e){
          e.printStackTrace();
        }
      }

    } else {
      Log.w("FileUtils", "数据库文件不存在");
    }
  }
  /**
   * 删除单个文件
   * @param   filePath    被删除文件的文件名
   * @return 文件删除成功返回true，否则返回false
   */
  public static boolean deleteFile(String filePath) {
    File file = new File(filePath);
    if (file.isFile() && file.exists()) {
      return file.delete();
    }
    return false;
  }

  /**
   * 删除文件夹以及目录下的文件
   * @param   filePath 被删除目录的文件路径
   * @return  目录删除成功返回true，否则返回false
   */
  public static boolean deleteDirectory(String filePath) {
    boolean flag = false;
    //如果filePath不以文件分隔符结尾，自动添加文件分隔符
    if (!filePath.endsWith(File.separator)) {
      filePath = filePath + File.separator;
    }
    File dirFile = new File(filePath);
    if (!dirFile.exists() || !dirFile.isDirectory()) {
      return false;
    }
    flag = true;
    File[] files = dirFile.listFiles();
    //遍历删除文件夹下的所有文件(包括子目录)
    for (int i = 0; i < files.length; i++) {
      if (files[i].isFile()) {
        //删除子文件
        flag = deleteFile(files[i].getAbsolutePath());
        if (!flag) break;
      } else {
        //删除子目录
        flag = deleteDirectory(files[i].getAbsolutePath());
        if (!flag) break;
      }
    }
    if (!flag) return false;
    //删除当前空目录
    return dirFile.delete();
  }

  /**
   *  根据路径删除指定的目录或文件，无论存在与否
   *@param filePath  要删除的目录或文件
   *@return 删除成功返回 true，否则返回 false。
   */
  public static boolean DeleteFolder(String filePath) {
    File file = new File(filePath);
    if (!file.exists()) {
      return true;//文件不存在，返回删除成功
    } else {
      if (file.isFile()) {
        // 为文件时调用删除文件方法
        return deleteFile(filePath);
      } else {
        // 为目录时调用删除目录方法
        return deleteDirectory(filePath);
      }
    }
  }

  public static Bitmap comp(Bitmap image) {//压缩图片

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
    if( baos.toByteArray().length / 1024>1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
      baos.reset();//重置baos即清空baos
      image.compress(Bitmap.CompressFormat.JPEG, 40, baos);//这里压缩50%，把压缩后的数据存放到baos中
    }
    image.recycle();
    ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
    BitmapFactory.Options newOpts = new BitmapFactory.Options();
    //开始读入图片，此时把options.inJustDecodeBounds 设回true了
    newOpts.inJustDecodeBounds = true;
    Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
    newOpts.inJustDecodeBounds = false;
    int w = newOpts.outWidth;
    int h = newOpts.outHeight;
    //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
    float hh = 800f;//这里设置高度为800f
    float ww = 480f;//这里设置宽度为480f
    //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
    int be = 1;//be=1表示不缩放
    if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
      be = (int) (newOpts.outWidth / ww);
    } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
      be = (int) (newOpts.outHeight / hh);
    }
    if (be <= 0)
      be = 1;
    newOpts.inSampleSize = be;//设置缩放比例
    //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
    isBm = new ByteArrayInputStream(baos.toByteArray());
    bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
    return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
  }
  private static Bitmap compressImage(Bitmap image) {

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
    int options = 100;
    while ( baos.toByteArray().length / 1024>100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
      baos.reset();//重置baos即清空baos
      image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
      options -= 20;//每次都减少10
    }
    image.recycle();
    return BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()), null, null);//把ByteArrayInputStream数据生成图片
  }
}
