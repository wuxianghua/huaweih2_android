package com.palmap.demo.huaweih2;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.palmap.demo.huaweih2.adapter.ICSListViewAdapter;
import com.palmap.demo.huaweih2.functionActivity.CommentActivity;
import com.palmap.demo.huaweih2.functionActivity.FindCarActivity;
import com.palmap.demo.huaweih2.functionActivity.FootPrintActivity;
import com.palmap.demo.huaweih2.functionActivity.PeripheryActivity;
import com.palmap.demo.huaweih2.functionActivity.ShakeActivity;
import com.palmap.demo.huaweih2.model.ICSModel;
import com.palmap.demo.huaweih2.other.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class H2MainActivity extends BaseActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h2_main);
        initView();
    }

    /**
     * 创建ics解决方案的数据源
     *
     * @return
     */
    private List<ICSModel> createICSModel() {
        ArrayList<ICSModel> models = new ArrayList<>();
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
    }

    public void functionClick(View view) {
        switch (view.getId()) {
            case R.id.layout_map: //点击地图
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.layout_trip: //点击行程
                Intent intent = new Intent(this,MainActivity.class);
                intent.putExtra("isTrip", true);
                startActivity(intent);
//                startActivity(new Intent(this,tripa));
                break;
            case R.id.layout_shake:
                startActivity(new Intent(this, ShakeActivity.class));
                break;
            case R.id.layout_car:
                startActivity(new Intent(this, FindCarActivity.class));
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
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (HuaWeiH2Application.startWelcomeAct){
//            HuaWeiH2Application.startWelcomeAct = false;
//            startActivityForResult(new Intent(this, WelcomeActivity.class), Constant.startWelcome);
//        }else {
//            //启动Android定时器，并且启动定位查询服务
//            if (Constant.openLocateService&&LocateTimerService.getInstance()==null)
//                LocateTimerService.start(this);
//        }
        if (Constant.openLocateService&& LocateTimerService.getInstance()==null)
                LocateTimerService.start(this);
    }
    private boolean isExit = false;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
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
                if (MainActivity.instance != null) {
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
