package com.palmap.demo.huaweih2.fragment;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
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
import com.palmap.demo.huaweih2.util.DialogUtils;
import com.palmap.demo.huaweih2.util.JsonUtils;
import com.palmap.demo.huaweih2.util.LogUtils;
import com.palmap.demo.huaweih2.util.ShakeListenerUtils;

import java.io.IOException;

import static com.palmap.demo.huaweih2.LocateTimerService.getCurrentLocationArea;


/**
 * Created by eric3 on 2016/10/18.
 * 摇一摇
 */

public class FragmentShake extends BaseFragment {
    public static boolean needInit = true;
    private ShakeListenerUtils shakeUtils;
    private SensorManager mSensorManager; //定义sensor管理器, 注册监听器用
    LinearLayout shakeShow;
    LinearLayout shakeShowloc;
    TextView locName;
    ImageView mImgDn;
    ImageView mImgUp;

    ImageView mImgFinish;
    MediaPlayer mediaPlayer;

    ImageView result;
    TextView title;
    TextView text;
    PoiInfo poiInfo;

    //如果第一次摇 并且在实验室
    boolean first = true;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View fragmentView = inflater.inflate(R.layout.shake, container, false);

        locName = (TextView) fragmentView.findViewById(R.id.locName);
        shakeShowloc = (LinearLayout) fragmentView.findViewById(R.id.loca);
        String l = LocateTimerService.getCurrentLocationArea();
        if (Constant.其它.equals(l)) {
            shakeShowloc.setVisibility(View.GONE);
        } else {
            locName.setText(l);
        }
        shakeShow = (LinearLayout) fragmentView.findViewById(R.id.big);
        shakeShow.setVisibility(View.GONE);
        shakeShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PoiInfoActivity.class);
                intent.putExtra("poiinfo", poiInfo);
                intent.putExtra("type", 0);
                shakeShow.setVisibility(View.GONE);
                startActivity(intent);
            }
        });
        mImgDn = (ImageView) fragmentView.findViewById(R.id.img_up);
        mImgUp = (ImageView) fragmentView.findViewById(R.id.img_down);

        mImgFinish = (ImageView) fragmentView.findViewById(R.id.img_finish);
        text = (TextView) fragmentView.findViewById(R.id.content);
        title = (TextView) fragmentView.findViewById(R.id.title);
        result = (ImageView) fragmentView.findViewById(R.id.img);

//    getMainActivity().titleBar.setOnTitleClickListener(new TitleBar.OnTitleClickListener() {
//      @Override
//      public void onLeft() {
//        LogUtils.i("onLeft->stopShakeSensor");
//        stopShakeSensor();
//        getMainActivity().showFragmentMap();
//      }
//
//      @Override
//      public void onRight() {
//
//      }
//    });

        LogUtils.i("onCreateView->initShakeSensor");
        initShakeSensor();//初始化传感器

        return fragmentView;
    }

    private void startAnim() { // 定义摇一摇动画
        LogUtils.i("FragmentShake->startAnim");


        first = false;

        poiInfo = null;
        AnimationSet animup = new AnimationSet(true);

        TranslateAnimation mup0 = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                +0.65f);
        mup0.setDuration(1000);
        TranslateAnimation mup1 = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,

                -0.65f);
        mup1.setDuration(1000);
        //延迟执行1秒
        mup1.setStartOffset(1000);
        animup.addAnimation(mup0);
        animup.addAnimation(mup1);

        AnimationSet animdn = new AnimationSet(true);
        TranslateAnimation mdn0 = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                -0.65f);
        mdn0.setDuration(1000);
        TranslateAnimation mdn1 = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                +0.65f);
        mdn1.setDuration(1000);
        //延迟执行1秒
        mdn1.setStartOffset(1000);
        animdn.addAnimation(mdn0);
        animdn.addAnimation(mdn1);


        animup.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mImgFinish.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                showPoiInfo();
                mImgUp.clearAnimation();
                mImgUp.invalidate();
                mImgDn.clearAnimation();
                mImgDn.invalidate();

                mImgFinish.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //上图片的动画效果的添加
        mImgUp.startAnimation(animup);
        //下图片动画效果的添加
        mImgDn.startAnimation(animdn);


//        int topHeight = mImgUp.getHeight();
//        int buttomHeight = mImgDn.getHeight();
//
//        int top = mImgUp.getTop();
//
//        ObjectAnimator topAnim = ObjectAnimator.ofFloat(mImgUp, "translationY", top, top - topHeight, top);
//        topAnim.setDuration(2000);
//        ObjectAnimator bottomAnim = ObjectAnimator.ofFloat(mImgDn, "translationY", top, top + buttomHeight, top);
//        bottomAnim.setDuration(2000);
//
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.play(topAnim);
//        animatorSet.play(bottomAnim);
//
//        animatorSet.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                showPoiInfo();
//            }
//        });
//        animatorSet.start();

    }

    /**
     * 显示poi信息UI
     */
    private void showPoiInfo() {
        if (poiInfo == null) {
            DialogUtils.showShortToast("没有获取到信息，再摇摇试试", Toast.LENGTH_SHORT);
            return;
        }
        shakeShow.setVisibility(View.VISIBLE);
        title.setText(poiInfo.getTitle());
        text.setText(poiInfo.getText());
        Glide.with(this).load(poiInfo.getImage()).centerCrop().into(result);
        ShakeListenerUtils.isTooShort = false;
    }

    public void initShakeSensor() {
        if (shakeUtils != null)
            return;

        shakeUtils = new ShakeListenerUtils(getActivity(), new ShakeListenerUtils.OnShakeListener() {
            @Override
            public void onShake() {

                if (first && LocateTimerService.getCurrentLocationArea().equals(Constant.ICS实验室)) {
                    shakeShow.setVisibility(View.GONE);
                    startAnim();
                    playMp3();
                    poiInfo = new PoiInfo();
                    poiInfo.setText("华为ICS实验室成立于2010年，2016年完成改造升级。整个实验室分成两个部分，外部为展厅，主要展示华为ICS方案；后侧为设备去，已搭建完善的测试环境，实现各种应用场景、各种移动应用业务、多厂商、多系统解决方案的端到端预集成及测试验证。");
                    poiInfo.setImage("http://106.75.7.212:9001/photo/laboratory@2x.png");
                    poiInfo.setTitle("ICS实验室");
                    LogUtils.e("直接加载实验室的摇一摇数据");
                    return;
                }

                LogUtils.i("ShakeListenerUtils->onShake");
                shakeShow.setVisibility(View.GONE);
                startAnim();
                playMp3();
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
                        System.out.println(1);
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
        //加速度传感器
        mSensorManager.registerListener(shakeUtils,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                //还有SENSOR_DELAY_UI、SENSOR_DELAY_FASTEST、SENSOR_DELAY_GAME等，
                //根据不同应用，需要的反应速率不同，具体根据实际情况设定
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stopShakeSensor() {
        shakeShow.setVisibility(View.GONE);
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(shakeUtils);
            shakeUtils = null;
        }

        mImgUp.clearAnimation();
        mImgUp.invalidate();
        mImgDn.clearAnimation();
        mImgDn.invalidate();
    }


    @Override
    public void onResume() {
        super.onResume();
//        mSensorManager.registerListener(shakeUtils,
//                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
//                //还有SENSOR_DELAY_UI、SENSOR_DELAY_FASTEST、SENSOR_DELAY_GAME等，
//                //根据不同应用，需要的反应速率不同，具体根据实际情况设定
//                SensorManager.SENSOR_DELAY_NORMAL);
//        ShakeListenerUtils.isTooShort = false;

        LogUtils.i("onResume->initShakeSensor");
        initShakeSensor();
        ShakeListenerUtils.isTooShort = false;
    }

    @Override
    public void onPause() {
        super.onPause();
//        mSensorManager.unregisterListener(shakeUtils);
        LogUtils.i("onPause->stopShakeSensor");
        stopShakeSensor();
    }

    private void playMp3() {

        boolean createState = false;
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(getActivity(), R.raw.shake);
            mediaPlayer.stop();
//        mediaPlayer=createLocalMp3();
            createState = true;
        }
        //当播放完音频资源时，会触发onCompletion事件，可以在该事件中释放音频资源，
        //以便其他应用程序可以使用该资源:
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();//释放音频资源
                mediaPlayer = null;
            }
        });
        try {
            //在播放音频资源之前，必须调用Prepare方法完成些准备工作
            if (createState) mediaPlayer.prepare();
            //开始播放音频
            mediaPlayer.start();

        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
