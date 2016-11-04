package com.palmap.demo.huaweih2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.palmap.demo.huaweih2.http.DataProviderCenter;
import com.palmap.demo.huaweih2.http.ErrorCode;
import com.palmap.demo.huaweih2.http.HttpDataCallBack;
import com.palmap.demo.huaweih2.json.PictureJson;
import com.palmap.demo.huaweih2.other.Constant;
import com.palmap.demo.huaweih2.util.DialogUtils;
import com.palmap.demo.huaweih2.util.FileUtils;
import com.palmap.demo.huaweih2.view.TitleBar;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;

public class UploadActivity extends BaseActivity {

  TitleBar titleBar;
  EditText tv_message;
  ImageView im_upload;
  Bitmap bitmap;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_upload);
    
    initView();
  }

  private void initView() {
    titleBar = (TitleBar)findViewById(R.id.title_bar);
    tv_message = (EditText) findViewById(R.id.message);
    im_upload = (ImageView)findViewById(R.id.image_upload);

    File file = new File(Constant.PATH_PICTURE_UPLOAD);
    Uri uri = Uri.fromFile(file);
    im_upload.setImageURI(uri);

    // TODO: 2016/11/4 节省创建bitMap的内存
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inPreferredConfig=Bitmap.Config.RGB_565;
//    options.inDither=false;
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(Constant.PATH_PICTURE_UPLOAD, options);
    int outWidth = 400;//输出图片的宽度
    options.outHeight = options.outHeight * options.outWidth / outWidth;
    options.outWidth = outWidth;
    options.inJustDecodeBounds = false;
    bitmap= BitmapFactory.decodeFile(Constant.PATH_PICTURE_UPLOAD,options);

    if (bitmap==null) {
      finish();
      return;
    }


    bitmap = FileUtils.comp(bitmap);

    titleBar.show("取消","","上传");
    titleBar.setOnTitleClickListener(new TitleBar.OnTitleClickListener() {
      @Override
      public void onLeft() {
        finish();
      }

      @Override
      public void onRight() {
//        if("".equals(tv_message.getText().toString())){
//          DialogUtils.showShortToast("写点什么吧");
//          return;
//        }
//        uploadImage();
        uploadImageFormData();

      }
    });
  }

  private void uploadImageFormData(){
    File img = new File(Constant.PATH_PICTURE_UPLOAD);
    //若该文件存在
    if (!img.exists()) {
      DialogUtils.showShortToast(Constant.PATH_PICTURE_UPLOAD+"没有找到待上传的图片。");
      return;
    }

//    bitmap = comp(bitmap);//压缩
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

    //输出压缩图片
    byte[] inputByte = null;
    int length = 0;
    DataInputStream dis = null;
    FileOutputStream fos = null;
    try {
      try {
        dis = new DataInputStream(new ByteArrayInputStream(baos.toByteArray()));
        fos = new FileOutputStream(new File(Constant.PATH_PICTURE_UPLOAD));
        inputByte = new byte[1024];
        System.out.println("开始接收数据...");
        while ((length = dis.read(inputByte, 0, inputByte.length)) > 0) {
          System.out.println(length);
          fos.write(inputByte, 0, length);
          fos.flush();
        }
        System.out.println("完成接收");
      } finally {
        if (fos != null)
          fos.close();
        if (dis != null)
          dis.close();
      }
    } catch (Exception e) {
      DialogUtils.showShortToast(e.getMessage());
    }
    File imgUp = new File(Constant.PATH_PICTURE_UPLOAD);
    //若该文件存在
    if (!imgUp.exists()) {
      DialogUtils.showShortToast(Constant.PATH_PICTURE_UPLOAD+"没有找到待上传的图片。");
      return;
    }

    PictureJson pictureJson = new PictureJson();
    pictureJson.setAppendix(tv_message.getText().toString());
    pictureJson.setLocation(LocateTimerService.getCurrentLocationArea());
    String picJson = JSON.toJSONString(pictureJson);

//    MyAsyncTask myAsyncTask = new MyAsyncTask();
//    myAsyncTask.execute();

    showProgress("上传","正在上传图片。请稍后...");
    DataProviderCenter.getInstance().postFormDATA(imgUp, picJson, new HttpDataCallBack() {
      @Override
      public void onError(int errorCode) {
        ErrorCode.showError(errorCode);
        closeProgress();
        Log.e("error",errorCode+"");
      }

      @Override
      public void onComplete(Object content) {
        DialogUtils.showShortToast("上传成功");

        closeProgress();
        finish();
      }
    });
  }


  private void uploadImage(){
    File img = new File(Constant.PATH_PICTURE_UPLOAD);
    //若该文件存在
    if (!img.exists()) {
      DialogUtils.showShortToast(Constant.PATH_PICTURE_UPLOAD+"没有找到待上传的图片。");
      return;
    }

//    bitmap = comp(bitmap);//压缩
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);




//输出压缩图片
//    byte[] inputByte = null;
//    int length = 0;
//    DataInputStream dis = null;
//    FileOutputStream fos = null;
//    try {
//      try {
//        dis = new DataInputStream(new ByteArrayInputStream(baos.toByteArray()));
//        fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory()+"/hhhhhh.png"));
//        inputByte = new byte[1024];
//        System.out.println("开始接收数据...");
//        while ((length = dis.read(inputByte, 0, inputByte.length)) > 0) {
//          System.out.println(length);
//          fos.write(inputByte, 0, length);
//          fos.flush();
//        }
//        System.out.println("完成接收");
//      } finally {
//        if (fos != null)
//          fos.close();
//        if (dis != null)
//          dis.close();
//      }
//    } catch (Exception e) {
//    }



    PictureJson pictureJson = new PictureJson();
    pictureJson.setAppendix(tv_message.getText().toString());
    pictureJson.setLocation("H2大厅");
//    pictureJson.setPhoto(baos.toByteArray());
    String picJson = JSON.toJSONString(pictureJson);

    DataProviderCenter.getInstance().postImage(picJson, new HttpDataCallBack() {
      @Override
      public void onError(int errorCode) {
        DialogUtils.showLongToast("error:"+errorCode);
        finish();
      }

      @Override
      public void onComplete(Object content) {
        DialogUtils.showShortToast("照片评论上传成功。");
        finish();
      }
    });
  }

}
