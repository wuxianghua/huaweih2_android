package com.palmap.demo.huaweih2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.palmap.demo.huaweih2.json.PoiInfo;
import com.palmap.demo.huaweih2.util.DialogUtils;

import java.net.URL;

public class PoiInfoActivity extends BaseActivity {
  public static final int YYY = 0;
  public static final int POI_HALL = 1;
  public static final int POI_LAB = 2;
  public static final int POI_FOOT = 3;//足迹
  ImageView close;
  ImageView imageView;
  TextView title;
  TextView detail;
  PoiInfo poiInfo;
  int type;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_poi_info);

    type = getIntent().getIntExtra("type", 1);
    poiInfo = (PoiInfo) getIntent().getSerializableExtra("poiinfo");

    detail = (TextView) findViewById(R.id.detail);
    title = (TextView) findViewById(R.id.title);
    imageView = (ImageView) findViewById(R.id.img);
    close = (ImageView) findViewById(R.id.close);

    close.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

    if (type == YYY) {
      title.setText(poiInfo.getTitle());
      detail.setText(poiInfo.getText());


      Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
          try {
            URL picUrl = new URL(poiInfo.getImage());

            final Bitmap pngBM = BitmapFactory.decodeStream(picUrl.openStream());

            runOnUiThread(new Runnable() {
              @Override
              public void run() {
                imageView.setImageBitmap(pngBM);
              }
            });


          } catch (final Exception e) {
            runOnUiThread(new Runnable() {
              @Override
              public void run() {
                DialogUtils.showShortToast(e.getMessage());
              }
            });

          }
        }
      });
      thread.start();


    } else if (type == POI_HALL) {
      title.setText("H2大厅");
      detail.setText("华为基地H区建立于2013年，目前有员工8000人，包括终端、企业、运营商3大BG的研发、销售、开发、产品等多个部门。");
      imageView.setImageResource(R.drawable.h2_foyer);
    } else if (type == POI_LAB) {
      title.setText("ICS实验室");
      detail.setText("华为ICS实验室成立于2010年，2016年完成改造升级。目前已搭建完善的测试环境主要用于设备集成验证、员工培训及客户接待参观。");
      imageView.setImageResource(R.drawable.laboratory_shake);
    } else if (type == POI_FOOT) {
      title.setVisibility(View.GONE);
      detail.setVisibility(View.GONE);
      imageView.setImageResource(R.drawable.c_footprint);
    }

  }
}
