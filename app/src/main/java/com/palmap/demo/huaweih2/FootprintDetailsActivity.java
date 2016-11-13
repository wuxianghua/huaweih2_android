package com.palmap.demo.huaweih2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.palmap.demo.huaweih2.json.PictureModel;
import com.palmap.demo.huaweih2.util.DialogUtils;
import com.palmap.demo.huaweih2.view.TitleBar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FootprintDetailsActivity extends BaseActivity {

    TitleBar titleBar;
    private PictureModel pictureModel;

    private ImageView imageView;
    private TextView  tvTime,tvDetails,tvLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footprint_details);

        try {
            Intent intent = getIntent();
            pictureModel = intent.getExtras().getParcelable(PictureModel.class.getSimpleName());
            if (pictureModel != null) {
                bindView();
                Glide.with(this).load(pictureModel.getPhoto()).into(imageView);
                SimpleDateFormat dateFormat = new SimpleDateFormat(
                        "yyyy年MM月dd日 HH:mm"
                );
                tvTime.setText(dateFormat.format(new Date(pictureModel.getUpdtime())));
                tvDetails.setText(pictureModel.getAppendix());
                tvLocation.setText(pictureModel.getLocation());

            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogUtils.showShortToast(e.getMessage());

        }
        titleBar = (TitleBar) findViewById(R.id.titleBar);
        titleBar.show(null,pictureModel.getLocation(),null);
//        titleBar.setRightIcoImageRes(R.drawable.ico_tab_share);
//
//        titleBar.setRightIcoClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                SharePopView.ShareModel shareModel = new SharePopView.ShareModel();
//
//                shareModel.imgUrl = pictureModel.getPhoto();
//                shareModel.title = "图片分享";
//                shareModel.text = pictureModel.getAppendix();
//
//                SharePopView.showSharePop(FootprintDetailsActivity.this,shareModel);
//            }
//        });


        titleBar.setOnTitleClickListener(new TitleBar.OnTitleClickListener() {
            @Override
            public void onLeft() {
                onBackPressed();
            }

            @Override
            public void onRight() {

            }
        });
    }

    private void bindView() {
        imageView = (ImageView) findViewById(R.id.imageView);
        tvTime = (TextView) findViewById(R.id.tv_time);
        tvDetails = (TextView) findViewById(R.id.tv_details);
        tvLocation = (TextView) findViewById(R.id.tv_location);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Glide.get(this).clearMemory();
        System.gc();
    }
}
