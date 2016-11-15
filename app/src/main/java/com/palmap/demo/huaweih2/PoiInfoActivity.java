package com.palmap.demo.huaweih2;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.palmap.demo.huaweih2.json.PoiInfo;
import com.palmap.demo.huaweih2.util.BitMaputils;
import com.palmap.demo.huaweih2.util.QQShareUtils;
import com.palmap.demo.huaweih2.view.SharePopView;

import java.io.File;

public class PoiInfoActivity extends BaseActivity {
    public static final int YYY = 0;//摇一摇
    public static final int POI_HALL = 1;//大厅
    public static final int POI_LAB = 2;//实验室
    public static final int POI_FOOT = 3;//足迹
    public static final int POI_OFFICE = 4;//办公区
    public static final int POI_MEETING = 5;//会议室
    ImageView close;
    ImageView imageView;
    TextView title;
    TextView detail;
    PoiInfo poiInfo;
    int type;

    ImageView share;

    View coView;

    Bitmap cobitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poi_info);

        type = getIntent().getIntExtra("type", 1);
        poiInfo = (PoiInfo) getIntent().getSerializableExtra("poiinfo");

        coView = findViewById(R.id.co);


        detail = (TextView) findViewById(R.id.detail);
        title = (TextView) findViewById(R.id.title);
        imageView = (ImageView) findViewById(R.id.img);
        close = (ImageView) findViewById(R.id.close);
        share = (ImageView) findViewById(R.id.share);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                coView.setDrawingCacheEnabled(true);
                cobitmap = coView.getDrawingCache();
                // 拷贝图片，否则在setDrawingCacheEnabled(false)以后该图片会被释放掉
                cobitmap = Bitmap.createBitmap(cobitmap);
                coView.setDrawingCacheEnabled(false);

                String bitmapPath = Environment.getExternalStorageDirectory() + File.separator
                        + "coBitmap";

                BitMaputils.saveBitmap(cobitmap,bitmapPath);

                SharePopView.ShareModel shareModel = new SharePopView.ShareModel();
                shareModel.urlBmp = cobitmap;
                shareModel.text = "华为ICS实验室室内定位解决方案";
                shareModel.title = "华为ICS实验室";
                shareModel.imgUrl = bitmapPath;

                SharePopView.showSharePop(PoiInfoActivity.this, shareModel, QQShareUtils.TYPE_LOCAL);
            }
        });

        if (type == YYY) {
            title.setText(poiInfo.getTitle());
            detail.setText(poiInfo.getText());

            Glide.with(this).load(poiInfo.getImage()).into(imageView);

        } else if (type == POI_HALL) {
            title.setText("H2大厅");
            detail.setText("华为基地H区建立于2013年，目前有员工8000人，包括终端、企业、运营商3大BG的研发、销售、开发、产品等多个部门。");
            imageView.setImageResource(R.drawable.h2_foyer);
        } else if (type == POI_LAB) {
            title.setText("ICS实验室");
            detail.setText("华为ICS实验室成立于2010年，2016年完成改造升级。目前已搭建完善的测试环境主要用于设备集成验证、员工培训及客户接待参观。");
            imageView.setImageResource(R.drawable.laboratory_shake);
        } else if (type == POI_FOOT) {
            title.setVisibility(View.GONE);
            detail.setVisibility(View.GONE);

            imageView.post(new Runnable() {
                @Override
                public void run() {
                    ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                    layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    imageView.setLayoutParams(layoutParams);
                    imageView.setImageResource(R.drawable.c_footprint);
                }
            });


        } else if (type == POI_OFFICE) {
            title.setText("ICS办公区");
            detail.setText("华为ICS包括交付、开发、MKT、销售、产品等各领域在内一支经验丰富的专家队伍，总人数超过300人。");
            imageView.setImageResource(R.drawable.office_poi);
        } else if (type == POI_MEETING) {
            title.setText("会议室");
            detail.setText("用户可使用会议室预订管理系统，在基于位置的地图显示下，直观清晰的使每一个用户了解会议室预订情况");
            imageView.setImageResource(R.drawable.boardroo);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cobitmap != null && !cobitmap.isRecycled()) {
            cobitmap.recycle();
        }
    }
}
