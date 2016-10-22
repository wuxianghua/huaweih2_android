package com.palmap.demo.huaweih2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.palmap.demo.huaweih2.json.PictureModel;
import com.palmap.demo.huaweih2.view.SharePopView;
import com.palmap.demo.huaweih2.view.TitleBar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FootprintDetailsActivity extends AppCompatActivity {


    private PictureModel pictureModel;

    private ImageView imageView;
    private TextView  tvTime,tvDetails,tvLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footprint_details);
        TitleBar titleBar = (TitleBar) findViewById(R.id.titleBar);
        titleBar.setRightIcoImageRes(R.drawable.ico_star_full);

        titleBar.setRightIcoClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharePopView.ShareModel shareModel = new SharePopView.ShareModel();



                SharePopView.showSharePop(FootprintDetailsActivity.this,shareModel);
            }
        });

        titleBar.show(null,"足迹",null);
        titleBar.setOnTitleClickListener(new TitleBar.OnTitleClickListener() {
            @Override
            public void onLeft() {
                onBackPressed();
            }

            @Override
            public void onRight() {

            }
        });

        try {
            Intent intent = getIntent();
            pictureModel = intent.getExtras().getParcelable(PictureModel.class.getSimpleName());
            if (pictureModel != null) {
                bindView();
                Glide.with(this).load(pictureModel.getUrl()).into(imageView);
                SimpleDateFormat dateFormat = new SimpleDateFormat(
                        "yyyy年MM月dd日 HH:mm"
                );
                tvTime.setText(dateFormat.format(new Date(pictureModel.getTime())));
                tvDetails.setText(pictureModel.getDetails());
                tvLocation.setText(pictureModel.getLocationStr());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bindView() {
        imageView = (ImageView) findViewById(R.id.imageView);
        tvTime = (TextView) findViewById(R.id.tv_time);
        tvDetails = (TextView) findViewById(R.id.tv_details);
        tvLocation = (TextView) findViewById(R.id.tv_location);
    }
}
