package com.palmap.demo.huaweih2.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.palmap.demo.huaweih2.LocateTimerService;
import com.palmap.demo.huaweih2.PoiInfoActivity;
import com.palmap.demo.huaweih2.R;
import com.palmap.demo.huaweih2.http.DataProviderCenter;
import com.palmap.demo.huaweih2.http.ErrorCode;
import com.palmap.demo.huaweih2.http.HttpDataCallBack;
import com.palmap.demo.huaweih2.json.PoiInfo;
import com.palmap.demo.huaweih2.other.Constant;
import com.palmap.demo.huaweih2.util.JsonUtils;
import com.palmap.demo.huaweih2.util.ShakeListenerUtils;
import com.palmap.demo.huaweih2.view.TitleBar;

import static com.palmap.demo.huaweih2.LocateTimerService.getCurrentLocationArea;


/**
 * Created by eric3 on 2016/10/18.
 * 摇一摇
 */

public class
FragmentShake extends BaseFragment {
    public static boolean needInit = true;
    private ShakeListenerUtils shakeUtils;
    private SensorManager mSensorManager; //定义sensor管理器, 注册监听器用
    LinearLayout shakeShow;
    LinearLayout shakeShowloc;
    TextView locName;
    ImageView mImgDn;
    ImageView mImgUp;

    ImageView result;
    TextView title;
    TextView text;
    PoiInfo poiInfo;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View fragmentView = inflater.inflate(R.layout.shake, container, false);

        locName = (TextView) fragmentView.findViewById(R.id.locName);
        shakeShowloc = (LinearLayout) fragmentView.findViewById(R.id.loca);
        String l = LocateTimerService.getCurrentLocationArea();
        if (Constant.ICS走廊.equals(l)) {
            locName.setText("ICS大楼");
        } else if ("".equals(l)) {
            shakeShowloc.setVisibility(View.GONE);
        } else {
            locName.setText(l);
        }
        shakeShow = (LinearLayout) fragmentView.findViewById(R.id.big);
        shakeShow.setVisibility(View.GONE);
        shakeShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getMainActivity(), PoiInfoActivity.class);
                intent.putExtra("poiinfo", poiInfo);
                intent.putExtra("type", 0);
                shakeShow.setVisibility(View.GONE);
                startActivity(intent);
            }
        });
        mImgUp = (ImageView) fragmentView.findViewById(R.id.img_up);
        mImgDn = (ImageView) fragmentView.findViewById(R.id.img_down);
        text = (TextView) fragmentView.findViewById(R.id.content);
        title = (TextView) fragmentView.findViewById(R.id.title);
        result = (ImageView) fragmentView.findViewById(R.id.img);

        getMainActivity().titleBar.setOnTitleClickListener(new TitleBar.OnTitleClickListener() {
            @Override
            public void onLeft() {
                stopShakeSensor();
                getMainActivity().showFragmentMap();
            }

            @Override
            public void onRight() {

            }
        });

        initShakeSensor();//初始化传感器

        return fragmentView;
    }

    private void startAnim() { // 定义摇一摇动画动画
        poiInfo = null;
//        AnimationSet animup = new AnimationSet(true);
//
//        TranslateAnimation mup0 = new TranslateAnimation(
//                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
//                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
//                +1.0f);
//        mup0.setDuration(1000);
//        TranslateAnimation mup1 = new TranslateAnimation(
//                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
//                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
//
//                -1.0f);
//        mup1.setDuration(1000);
//        //延迟执行1秒
//        mup1.setStartOffset(1000);
//        animup.addAnimation(mup0);
//        animup.addAnimation(mup1);
//
//        AnimationSet animdn = new AnimationSet(true);
//        TranslateAnimation mdn0 = new TranslateAnimation(
//                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
//                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
//                -1.0f);
//        mdn0.setDuration(1000);
//        TranslateAnimation mdn1 = new TranslateAnimation(
//                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
//                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
//                +1.0f);
//        mdn1.setDuration(1000);
//        //延迟执行1秒
//        mdn1.setStartOffset(1000);
//        animdn.addAnimation(mdn0);
//        animdn.addAnimation(mdn1);

//
//        animup.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                showPoiInfo();
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//
//        //上图片的动画效果的添加
//        mImgUp.startAnimation(animup);
//        //下图片动画效果的添加
//        mImgDn.startAnimation(animdn);

        int topHeight = mImgUp.getHeight();
        int buttomHeight = mImgDn.getHeight();

        int top = mImgUp.getTop();

        ObjectAnimator topAnim = ObjectAnimator.ofFloat(mImgUp, "translationY", top, top - topHeight, top);
        topAnim.setDuration(2000);
        ObjectAnimator bottomAnim = ObjectAnimator.ofFloat(mImgDn, "translationY", top, top + buttomHeight, top);
        bottomAnim.setDuration(2000);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(topAnim);
        animatorSet.play(bottomAnim);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                showPoiInfo();
            }
        });
        animatorSet.start();

    }

    /**
     * 显示poi信息UI
     */
    private void showPoiInfo() {
        if (poiInfo == null) {
            Toast.makeText(getActivity(), "没有信息,重试", Toast.LENGTH_SHORT).show();
            return;
        }
        shakeShow.setVisibility(View.VISIBLE);
        title.setText(poiInfo.getTitle());
        text.setText(poiInfo.getText());
        Glide.with(this).load(poiInfo.getImage()).into(result);
        ShakeListenerUtils.isTooShort = false;
    }

    public void initShakeSensor() {
        if (shakeUtils != null)
            return;

        shakeUtils = new ShakeListenerUtils(getActivity(), new ShakeListenerUtils.OnShakeListener() {
            @Override
            public void onShake() {
                startAnim();
                String js = JsonUtils.getShakeString(getCurrentLocationArea());
                DataProviderCenter.getInstance().getShake(js, new HttpDataCallBack() {
                    @Override
                    public void onError(int errorCode) {
                        ErrorCode.showError(errorCode);
                        ShakeListenerUtils.isTooShort = false;
                    }

                    @Override
                    public void onComplete(Object content) {
                        poiInfo = JsonUtils.getPoiInfo(content);
//                        if (poiInfo == null)
//                            return;
//
//                        shakeShow.setVisibility(View.VISIBLE);
//                        title.setText(poiInfo.getTitle());
//                        text.setText(poiInfo.getText());
//
//
//                        Thread thread = new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                try {
//                                    URL picUrl = new URL(poiInfo.getImage());
//                                    final Bitmap pngBM = BitmapFactory.decodeStream(picUrl.openStream());
//                                    getActivity().runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            result.setImageBitmap(pngBM);
//                                            ShakeListenerUtils.isTooShort = false;
//                                        }
//                                    });
//
//                                } catch (final Exception e) {
//                                    e.printStackTrace();
//                                    getActivity().runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            DialogUtils.showShortToast(e.getMessage());
//                                        }
//                                    });
//
//                                }
//                            }
//                        });
//                        thread.start();
                    }
                });
            }
        });
        //获取传感器管理服务
        mSensorManager = (SensorManager) getActivity()
                .getSystemService(Service.SENSOR_SERVICE);
    }

    public void stopShakeSensor() {
        shakeShow.setVisibility(View.GONE);
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(shakeUtils);
            shakeUtils = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(shakeUtils,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                //还有SENSOR_DELAY_UI、SENSOR_DELAY_FASTEST、SENSOR_DELAY_GAME等，
                //根据不同应用，需要的反应速率不同，具体根据实际情况设定
                SensorManager.SENSOR_DELAY_NORMAL);
        ShakeListenerUtils.isTooShort = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(shakeUtils);
    }


}
