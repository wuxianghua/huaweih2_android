package com.palmap.demo.huaweih2;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.alibaba.fastjson.JSONArray;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.palmap.demo.huaweih2.adapter.ImageListAdapter;
import com.palmap.demo.huaweih2.http.DataProviderCenter;
import com.palmap.demo.huaweih2.http.HttpDataCallBack;
import com.palmap.demo.huaweih2.json.PictureJson;
import com.palmap.demo.huaweih2.model.ImgCell;
import com.palmap.demo.huaweih2.util.DialogUtils;

import java.util.ArrayList;
import java.util.List;

public class ImageListActivity extends AppCompatActivity {
  ImageLoader imageLoader;
  ImageListAdapter imageListAdapter;
  ListView listView;
  List<ImgCell> imgCells = new ArrayList<>();
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_image_list);
    listView = (ListView)findViewById(R.id.img_listview);
    imageListAdapter = new ImageListAdapter(this, imgCells, new ImageListAdapter.OnItemClickListener() {
      @Override
      public void onClicked(ImgCell img) {

      }
    });

    listView.setAdapter(imageListAdapter);
    getImageCells();

  }

  /*
  一次获取20*3条
   */
  private void getImageCells(){
    String jsonString = "[{\"location\": \"\"}]";
    DataProviderCenter.getInstance().getImage(jsonString,new HttpDataCallBack() {
      @Override
      public void onError(int errorCode) {
        DialogUtils.showLongToast(errorCode+"");
      }

      @Override
      public void onComplete(Object content) {
        Log.i("",content.toString());
        List<PictureJson> list=new ArrayList<PictureJson>(JSONArray.parseArray(content.toString(),PictureJson.class));
        for (int i=0;i<list.size()/3;i++){
          ImgCell imgCell = new ImgCell();
          for (int j=0;j<3;j++){
            switch (j){
              case 0:
                imgCell.setB1(BitmapFactory.decodeByteArray(list.get(i*3+j).getPhoto(), 0, list.get(i*3+j).getPhoto().length));
                break;
              case 1:
                imgCell.setB2(BitmapFactory.decodeByteArray(list.get(i*3+j).getPhoto(), 0, list.get(i*3+j).getPhoto().length));
                break;
              case 2:
                imgCell.setB3(BitmapFactory.decodeByteArray(list.get(i*3+j).getPhoto(), 0, list.get(i*3+j).getPhoto().length));
                break;
            }
          }

          imgCells.add(imgCell);
        }

////            输出压缩图片
//        byte[] inputByte =null;
//        int length = 0;
//        DataInputStream dis = null;
//        FileOutputStream fos = null;
//        try {
//          try {
//            dis = new DataInputStream(new ByteArrayInputStream(list.get(0).getPhoto()));
//            fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory()+"/ggggggg.png"));
//            inputByte = new byte[1024];
//            System.out.println("开始接收数据...");
//            while ((length = dis.read(inputByte, 0, inputByte.length)) > 0) {
//              System.out.println(length);
//              fos.write(inputByte, 0, length);
//              fos.flush();
//            }
//            System.out.println("完成接收");
//          } finally {
//            if (fos != null)
//              fos.close();
//            if (dis != null)
//              dis.close();
//          }
//        } catch (Exception e) {
//        }



        imageListAdapter.notifyDataSetChanged();
      }
    });
  }

  private void init(){
//     imageLoader = ImageLoader.getInstance();
//    DisplayImageOptions options = new DisplayImageOptions.Builder()
//        .showImageOnLoading(R.drawable.ic_stub) // 设置图片下载期间显示的图片
//        .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
//        .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
//        .resetViewBeforeLoading(false)  // default 设置图片在加载前是否重置、复位
//        .delayBeforeLoading(1000)  // 下载前的延迟时间
//        .cacheInMemory(false) // default  设置下载的图片是否缓存在内存中
//        .cacheOnDisk(false) // default  设置下载的图片是否缓存在SD卡中
//        .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default 设置图片以如何的编码方式显示
//        .bitmapConfig(Bitmap.Config.ARGB_8888) // default 设置图片的解码类型
//        .build();
  }

  private void showOneImage(){
//    imageLoader.displayImage(uri, imageView);
//    imageLoader.displayImage(uri, imageView, options);
//    imageLoader.displayImage(uri, imageView, listener);
//    imageLoader.displayImage(uri, imageView, options, listener);
  }
}
