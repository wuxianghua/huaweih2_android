package com.palmap.demo.huaweih2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.palmap.demo.huaweih2.http.DataProviderCenter;
import com.palmap.demo.huaweih2.http.HttpDataCallBack;
import com.palmap.demo.huaweih2.json.PictureJson;
import com.palmap.demo.huaweih2.other.Constant;
import com.palmap.demo.huaweih2.util.DialogUtils;
import com.palmap.demo.huaweih2.util.FileUtils;
import com.palmap.demo.huaweih2.view.TitleBar;

import java.io.ByteArrayOutputStream;
import java.io.File;

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
    bitmap= BitmapFactory.decodeFile(Constant.PATH_PICTURE_UPLOAD);
    if (bitmap==null) {
      finish();
      return;
    }


    bitmap = FileUtils.comp(bitmap);

    titleBar.show("取消","","保存");
    titleBar.setOnTitleClickListener(new TitleBar.OnTitleClickListener() {
      @Override
      public void onLeft() {
        finish();
      }

      @Override
      public void onRight() {
        uploadImage();
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
    pictureJson.setPhoto(baos.toByteArray());
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
