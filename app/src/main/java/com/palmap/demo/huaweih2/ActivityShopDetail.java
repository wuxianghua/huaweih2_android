package com.palmap.demo.huaweih2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.palmap.demo.huaweih2.view.TitleBar;

public class ActivityShopDetail extends BaseActivity {
  RelativeLayout tv_phone;
  TitleBar titleBar;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_shop_detail);
    tv_phone = (RelativeLayout) findViewById(R.id.t2);
    tv_phone.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //用intent启动拨打电话
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+"12345678"));
        startActivity(intent);
      }
    });
    titleBar = (TitleBar)findViewById(R.id.title_bar);
    titleBar.show(null,"711便利店",null);
    titleBar.setOnTitleClickListener(new TitleBar.OnTitleClickListener() {
      @Override
      public void onLeft() {
        finish();
      }

      @Override
      public void onRight() {

      }
    });
  }


}
