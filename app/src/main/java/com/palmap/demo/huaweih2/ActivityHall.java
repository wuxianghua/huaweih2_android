package com.palmap.demo.huaweih2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.palmap.demo.huaweih2.other.Constant;
import com.palmap.demo.huaweih2.view.TitleBar;

public class ActivityHall extends BaseActivity {
  ImageView btn_com;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_hall);

    TitleBar titleBar = (TitleBar)findViewById(R.id.title_bar);
    titleBar.show(null, Constant.H2大厅,null);
    titleBar.setOnTitleClickListener(new TitleBar.OnTitleClickListener() {
      @Override
      public void onLeft() {
        finish();
      }

      @Override
      public void onRight() {

      }
    });
    btn_com = (ImageView)findViewById(R.id.btn_com);
    btn_com.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
      }
    });
  }
}
