package com.palmap.demo.huaweih2.fragment;

import android.app.Service;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.palmap.demo.huaweih2.R;
import com.palmap.demo.huaweih2.util.ShakeListenerUtils;
import com.palmap.demo.huaweih2.view.TitleBar;


/**
 * Created by eric3 on 2016/10/18.
 * 摇一摇
 */

public class FragmentShake extends BaseFragment {
  private ShakeListenerUtils shakeUtils;
  private SensorManager mSensorManager; //定义sensor管理器, 注册监听器用
  ImageView mImgDn;
  ImageView mImgUp;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    // TODO Auto-generated method stub

    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater,
                           @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    View fragmentView = inflater.inflate(R.layout.shake, container, false);

    mImgDn = (ImageView)fragmentView.findViewById(R.id.img_up);
    mImgUp = (ImageView)fragmentView.findViewById(R.id.img_down);

    getMainActivity().titleBar.setOnTitleClickListener(new TitleBar.OnTitleClickListener() {
      @Override
      public void onLeft() {
        getMainActivity().showFragmentMap();
      }

      @Override
      public void onRight() {

      }
    });
    return fragmentView;
  }

  private void startAnim() { // 定义摇一摇动画动画

    AnimationSet animup = new AnimationSet(true);

    TranslateAnimation mup0 = new TranslateAnimation(

        Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,

        Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,

        +1.0f);

    mup0.setDuration(1000);

    TranslateAnimation  mup1 = new TranslateAnimation(

        Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,

        Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,

        -1.0f);

    mup1.setDuration(1000);

//延迟执行1秒

    mup1.setStartOffset(1000);

    animup.addAnimation( mup0);

    animup.addAnimation( mup1);

//上图片的动画效果的添加

    mImgUp.startAnimation(animup);

    AnimationSet animdn = new AnimationSet(true);

    TranslateAnimation  mdn0 = new TranslateAnimation(

        Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,

        Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,

        -1.0f);

    mdn0.setDuration(1000);

    TranslateAnimation  mdn1 = new TranslateAnimation(

        Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,

        Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,

        +1.0f);

    mdn1.setDuration(1000);

//延迟执行1秒

    mdn1.setStartOffset(1000);

    animdn.addAnimation( mdn0);

    animdn.addAnimation( mdn1);

//下图片动画效果的添加

    mImgDn.startAnimation(animdn);

  }
  private void initSensor(){
    shakeUtils = new ShakeListenerUtils(getActivity(), new ShakeListenerUtils.OnShakeListener() {
      @Override
      public void onShake() {
        startAnim();
      }
    });
    //获取传感器管理服务
    mSensorManager = (SensorManager) getActivity()
        .getSystemService(Service.SENSOR_SERVICE);
    //加速度传感器
    mSensorManager.registerListener(shakeUtils,
        mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
        //还有SENSOR_DELAY_UI、SENSOR_DELAY_FASTEST、SENSOR_DELAY_GAME等，
        //根据不同应用，需要的反应速率不同，具体根据实际情况设定
        SensorManager.SENSOR_DELAY_NORMAL);
  }

  @Override
  public void onResume() {
    initSensor();
    super.onResume();
  }

  @Override
  public void onPause() {
    if (mSensorManager!=null)
    mSensorManager.unregisterListener(shakeUtils);
    super.onPause();
  }
}
