package com.palmap.demo.huaweih2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.palmap.demo.huaweih2.adapter.ICSListViewAdapter;
import com.palmap.demo.huaweih2.functionActivity.CommentActivity;
import com.palmap.demo.huaweih2.functionActivity.FootPrintActivity;
import com.palmap.demo.huaweih2.functionActivity.PeripheryActivity;
import com.palmap.demo.huaweih2.functionActivity.ShakeActivity;
import com.palmap.demo.huaweih2.model.ICSModel;
import com.palmap.demo.huaweih2.other.Constant;
import com.palmap.demo.huaweih2.util.GpsUtils;
import com.palmap.demo.huaweih2.util.QQShareUtils;
import com.palmap.demo.huaweih2.util.WXShareUtils;
import com.palmap.huawei.FindCarActivity;
import com.palmap.huawei.view.FindCarNativeActivity;

import org.xq.com.xiaoqian.util.HUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.bingoogolapple.bgabanner.BGABanner;

public class H2MainActivity extends BaseActivity {

    private ListView listView;
    private BGABanner mContentBanner;
    ArrayList<ICSModel> models;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h2_main);
        WXShareUtils.regToWeChat(this);
        QQShareUtils.getInstance().regToQQ(this);
        FormatStrategy formatStrategy = CsvFormatStrategy.newBuilder()
                .tag("custom")
                .build();

        Logger.addLogAdapter(new DiskLogAdapter(formatStrategy));
        initView();
        checkFirstRun();

        if (Constant.openGps)
            GpsUtils.startGetLocation(this);

    }

    private void initBinner() {

//        List<View> views = new ArrayList<>();
//        views.add(BGABannerUtil.getItemImageView(this, R.drawable.btn_line_open));
//        views.add(BGABannerUtil.getItemImageView(this, R.drawable.btn_nav_cancle));
//        views.add(BGABannerUtil.getItemImageView(this, R.drawable.btn_nav_setstart));
        mContentBanner = (BGABanner) findViewById(R.id.banner_guide_content);
        mContentBanner.setData(R.drawable.content_5, R.drawable.content_2, R.drawable.content_3, R.drawable.content_4, R.drawable.content);
//        mContentBanner.setData(R.drawable.applogo,R.drawable.applogo,R.drawable.applogo);
        mContentBanner.startAutoPlay();

        mContentBanner.setDelegate(new BGABanner.Delegate<ImageView, String>() {
            @Override
            public void onBannerItemClick(BGABanner banner, ImageView itemView, String model, int position) {
//                Toast.makeText(banner.getContext(), "点击了" + position, Toast.LENGTH_SHORT).show();
                Intent intent;
                switch (position) {
                    case 0:
                        intent = new Intent(H2MainActivity.this, ActivityWeb.class);
                        intent.putExtra(ActivityWeb.URL, "http://mp.weixin.qq.com/s?__biz=MzAwNDA0MTY3Mg==&mid=2651629565&idx=1&sn=0f469d204fd95b04f71a04108cab5463&chksm=80c9f95ab7be704c11c018b9159eceb0b13bc9521833e99e9351af48e3dad72a45c6676b4bca&scene=21#wechat_redirect");
                        intent.putExtra(ActivityWeb.TITLE, "资讯详情");
                        startActivity(intent);

                        break;
                    case 1:
                        intent = new Intent(H2MainActivity.this, ActivityWeb.class);
                        intent.putExtra(ActivityWeb.URL, "http://mp.weixin.qq.com/s?__biz=MzAwNDA0MTY3Mg==&mid=2651629578&idx=1&sn=ffdd3ab20b68855d025d8c2459c705e4&chksm=80c9f6adb7be7fbb1e02c3bbf46c0c3630d211105789d1f8e4ee0928a3e2becd20ef04007b38&scene=21#wechat_redirect");
                        intent.putExtra(ActivityWeb.TITLE, "资讯详情");
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(H2MainActivity.this, ActivityWeb.class);
                        intent.putExtra(ActivityWeb.URL, "http://mp.weixin.qq.com/s?__biz=MzAwNDA0MTY3Mg==&mid=2651629584&idx=1&sn=e0f9def4a1c5186e75d7fdd59ac15a3b&chksm=80c9f6b7b7be7fa1f155041625d7083a30a1fc4e65bf806a91882451160b341b05eccb856661&scene=21#wechat_redirect");
                        intent.putExtra(ActivityWeb.TITLE, "资讯详情");
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(H2MainActivity.this, ActivityWeb.class);
                        intent.putExtra(ActivityWeb.URL, "http://mp.weixin.qq.com/s?__biz=MzAwNDA0MTY3Mg==&mid=2651629571&idx=1&sn=b8faf5199662860cf2c7e78119c3438d&chksm=80c9f6a4b7be7fb213c271ae031877e05dcb6a7931c9fda7160d19996181efd90ab1e060184c&scene=21#wechat_redirect");
                        intent.putExtra(ActivityWeb.TITLE, "资讯详情");
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(H2MainActivity.this, ICSInfoActivity.class);
                        intent.putExtra(ICSModel.class.getSimpleName(), models);
                        startActivity(intent);
                        break;
                }


            }
        });


    }

    private void checkFirstRun() {
        SharedPreferences setting = getSharedPreferences(Constant.IS_FIRST_RUN, 0);
        Boolean user_first = setting.getBoolean("FIRST", true);
        int time = setting.getInt("time", 2);
        if (time > 0) {
//            shouldShow2choose1 = true;
            setting.edit().putInt("time", (time - 1)).commit();//显示次数
        }

        if (user_first) {//第一次
            HuaWeiH2Application.firstRun = true;
            HuaWeiH2Application.startWelcomeAct = true;
            setting.edit().putBoolean("FIRST", false).commit();
            setting.edit().putInt("time", 2).commit();//显示次数

        } else {
//      //启动Android定时器，并且启动服务
//      if (Constant.openLocateService)
//        LocateTimerService.start(this);
        }
    }

    /**
     * 创建ics解决方案的数据源
     *
     * @return
     */
    private List<ICSModel> createICSModel() {
        models = new ArrayList<>();
        models.add(new ICSModel("室内增值服务解决方案", R.mipmap.ics1, "凭借商业咨询与网络规划设计能力、平滑演进易部署的数字化方案、E2E集成交付能力，实现人流地图和统计、精准营销消息推送、室内导航等增值服务，帮助运营商开放管道能力，使能室内数字化业务，实现业务转型。包括商业模式设计、网络层集成、平台层集成、应用层集成。"));
        models.add(new ICSModel("室内精准规划解决方案", R.mipmap.ics2, "华为基于室内外话务区分技术进行网络透析，精确识别室内价值热点；对已识别出来的室内批量价值热点进行多维度的评估,包括有覆盖、容量、话务、性能、收益、投诉等，同时给出多维评分排序结果；最后基于不同场景的特点与客户需求制定最合适的解决方案，并完成投资回报周期的预测，最终给出室内网络的投资和站点规划的合理化建议。帮助运营商有效投资，缩短投资回报周期。"));
        models.add(new ICSModel("场景化深度覆盖解决方案", R.mipmap.ics3, "凭借北京“鸟巢”等全球100+大型场馆建设经验，和400+大型赛事保障经验，积累了20+大型事件话务模型经验库，合理规划网络容量，有效应对大流量的冲击；构建业界领先的数字化场馆建模及仿真能力，结合专门的场馆天线，实现精确覆盖，有效控制场馆干扰。"));
        models.add(new ICSModel("室内网络性能提升解决方案", R.mipmap.ics4, "面向大事件体验提升的网络评估优化：华为凭借在巴西世界杯、北京鸟巢世锦赛等重大事件中丰富的保障经验，构建了话务预测和流量分析、容量和覆盖的评估和规划、应急紧急预案等方面的能力，并成功支撑了100+ 国际体育赛事；100+ 地区性传统节假日；80+ 国际展会；60+ 政治峰会及选举；30+ 大型宗教活动...，网络质量稳定。同时提供LampSite、SingleDAS、Micro以及无源器件等多样化的产品，快速满足不同场景、不同事件的差异化诉求。"));
        return models;
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.listView);

        final ICSListViewAdapter adapter = new ICSListViewAdapter(this, createICSModel());

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ICSModel model = adapter.getItem(position);
                Intent intent = new Intent(H2MainActivity.this, ICSInfoActivity.class);
                intent.putExtra(ICSModel.class.getSimpleName(), model);
                startActivity(intent);
            }
        });
        initBinner();
    }

    public void functionClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.layout_map: //点击地图
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("isTrip", false);
                startActivity(intent);
                break;

            case R.id.layout_trip: //点击行程
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("isTrip", true);
                startActivity(intent);
//                startActivity(new Intent(this,tripa));
                break;

            case R.id.layout_heat: //点击客流分析
                intent = new Intent(this, ActivityWeb.class);
                intent.putExtra(ActivityWeb.URL, "http://bi.palmap.cn/hw2");
                intent.putExtra(ActivityWeb.TITLE, "客流分析");
                startActivity(intent);
                break;
            case R.id.layout_shake:
                startActivity(new Intent(this, ShakeActivity.class));
                break;
            case R.id.layout_car:
                //startActivity(new Intent(this, FindCarActivity.class));
                startActivity(new Intent(this, FindCarNativeActivity.class));
                break;
            case R.id.layout_foot:
                startActivity(new Intent(this, FootPrintActivity.class));
                break;
            case R.id.layout_comment:
                startActivity(new Intent(this, CommentActivity.class));
                break;
            case R.id.layout_periphery://周边
                startActivity(new Intent(this, PeripheryActivity.class));
                break;
            case R.id.layout_ar://AR

//        HUtil.getIns().d = true;
                startActivity(new Intent(H2MainActivity.this, HUtil.getIns().GoAr()));

//        DialogUtils.showShortToast("敬请期待！", Gravity.CENTER);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (HuaWeiH2Application.startWelcomeAct) {
            HuaWeiH2Application.startWelcomeAct = false;
            startActivityForResult(new Intent(this, WelcomeActivity.class), Constant.startWelcome);
        } else {
            //启动Android定时器，并且启动定位查询服务
            if (Constant.useOldService) {
                if (Constant.openLocateService && LocateTimerService.getInstance() == null) {
                    LocateTimerService.start(this);
                }
            }else{
                LampSiteLocationService.start(this);
            }
        }
    }

    private boolean isExit = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Timer tExit = null;
            if (!isExit) {
                isExit = true; // 准备退出
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                tExit = new Timer();
                tExit.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        isExit = false; // 取消退出
                    }
                }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

            } else {
                if (MainActivity.instance != null && !MainActivity.instance.isFinishing()) {
                    MainActivity.instance.finish();
                }
                finish();
                System.exit(0);
            }
            return true;
        }
        return true;
    }
}
