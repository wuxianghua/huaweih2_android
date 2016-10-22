package com.palmap.demo.huaweih2;

import android.os.Bundle;

import com.palmap.demo.huaweih2.other.Constant;
import com.palmap.demo.huaweih2.view.TitleBar;

public class ActivityMeeting extends BaseActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_meeting);
    TitleBar titleBar = (TitleBar)findViewById(R.id.title_bar);
    titleBar.show(null, Constant.会议室,null);
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
