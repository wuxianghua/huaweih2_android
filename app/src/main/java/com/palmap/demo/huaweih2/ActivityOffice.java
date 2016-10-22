package com.palmap.demo.huaweih2;

import android.os.Bundle;

import com.palmap.demo.huaweih2.other.Constant;
import com.palmap.demo.huaweih2.view.TitleBar;

public class ActivityOffice extends BaseActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_office);

    TitleBar titleBar = (TitleBar)findViewById(R.id.title_bar);
    titleBar.show(null, Constant.ICS办公区,null);
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

  private void addPeople(){

  }
}
