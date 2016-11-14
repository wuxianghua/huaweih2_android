package com.palmap.demo.huaweih2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.palmap.demo.huaweih2.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eric3 on 2016/10/7.
 */

public class WelcomeActivity extends BaseActivity {
  private static final String TAG = "WelcomeActivity";
  private ViewPager viewpager = null;
  private List<View> list = null;
  private ImageView[] img = null;
  Button btn_ok;
  TextView jump;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_welcome);

    initView();

  }

  private void initView() {
    viewpager = (ViewPager) findViewById(R.id.viewpager_welcome);
    list = new ArrayList<View>();
    list.add(createImageView(R.drawable.welcome_tab1));
    list.add(createImageView(R.drawable.welcome_tab2));
    list.add(createImageView(R.drawable.welcome_tab3));

    btn_ok = (Button) findViewById(R.id.btn_ok);
    btn_ok.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    jump = (TextView) findViewById(R.id.jump);
    jump.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });


    img = new ImageView[list.size()];
    LinearLayout layout = (LinearLayout) findViewById(R.id.viewGroup);
    for (int i = 0; i < list.size(); i++) {
      img[i] = new ImageView(WelcomeActivity.this);
      if (0 == i) {
        img[i].setBackgroundResource(R.drawable.page_indicator_focused);
      } else {
        img[i].setBackgroundResource(R.drawable.page_indicator);
      }

      LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(23, 23);
      lp.setMargins(9, 0, 9, 0);
      img[i].setLayoutParams(lp);
      layout.addView(img[i]);
    }
    viewpager.setAdapter(new ViewPagerAdapter(list));
    viewpager.setOnPageChangeListener(new ViewPagerPageChangeListener());
  }

  private ImageView createImageView(int resId) {
    ImageView imageView = new ImageView(this.getApplication());
    Glide.with(this).load(resId).centerCrop().into(imageView);
    return imageView;
  }

  public void onOKClick(View view) {
    finish();
  }

  class ViewPagerPageChangeListener implements ViewPager.OnPageChangeListener {

    /*
     * state：网上通常说法：1的时候表示正在滑动，2的时候表示滑动完毕了，0的时候表示什么都没做，就是停在那；
     * 我的认为：1是按下时，0是松开，2则是新的标签页的是否滑动了
     * (例如：当前页是第一页，如果你向右滑不会打印出2，如果向左滑直到看到了第二页，那么就会打印出2了)；
     * 个人认为一般情况下是不会重写这个方法的
     */
    @Override
    public void onPageScrollStateChanged(int state) {
    }

    /*
     * page：看名称就看得出，当前页； positionOffset：位置偏移量，范围[0,1]；
     * positionoffsetPixels：位置像素，范围[0,屏幕宽度)； 个人认为一般情况下是不会重写这个方法的
     */
    @Override
    public void onPageScrolled(int page, float positionOffset,
                               int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int page) {
      if (page == 2)
        btn_ok.setVisibility(View.VISIBLE);
      else
        btn_ok.setVisibility(View.GONE);

      //更新图标
      for (int i = 0; i < list.size(); i++) {
        if (page == i) {
          img[i].setBackgroundResource(R.drawable.page_indicator_focused);
        } else {
          img[i].setBackgroundResource(R.drawable.page_indicator);
        }
      }
    }
  }
}
