package com.palmap.demo.huaweih2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.palmap.demo.huaweih2.view.CustomerViewPager;
import com.palmap.demo.huaweih2.view.MyProgressDialog;
import com.palmap.demo.huaweih2.view.RecyclableImageView;

import java.util.ArrayList;

/**
 * Created by 王天明 on 2016/10/22
 */
public class ImageViewActivity extends Activity {

    private ArrayList<String> imgList = new ArrayList<String>();
    private CustomerViewPager imgPager;
    private PagerAdapter mAdapter;
    Handler myHandler = new Handler();
    private MyProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_view);
        Intent intent = super.getIntent();
        imgList = intent.getStringArrayListExtra("imgList");
        int itemIndex = intent.getIntExtra("itemIndex", 0);

        imgPager = (CustomerViewPager) findViewById(R.id.imgPager);
        mAdapter = new ImageAdapter();
        imgPager.setAdapter(mAdapter);
        imgPager.setCurrentItem(itemIndex);
    }

    private class ImageAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return imgList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            String url = imgList.get(position);
            RecyclableImageView img = new RecyclableImageView(ImageViewActivity.this);
            getBM(url, img, container, position);
            return img;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        private void getBM(final String url, final RecyclableImageView img, final ViewGroup container, final int position) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    myHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            //开始加载的时候执行
                            starProgressDialog();

                            Glide.with(ImageViewActivity.this).load(url)
                                    .into(new SimpleTarget<GlideDrawable>() {
                                        @Override
                                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                            img.setImageDrawable(resource);
                                            closeProgressDialog();
                                        }

                                        @Override
                                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                            super.onLoadFailed(e, errorDrawable);
                                            closeProgressDialog();
                                        }
                                    });
                            img.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ImageViewActivity.this.finish();
                                }
                            });
                            container.addView(img, 0);
                        }
                    });
                    Looper.loop();
                }
            }).start();
        }

        private void starProgressDialog() {
            //开始加载的时候执行
            if (progressDialog == null || !progressDialog.isShowing()) {
                progressDialog = MyProgressDialog.createDialog(ImageViewActivity.this);
                progressDialog.show();
                progressDialog.setCancelable(true);
            }
        }

        private void closeProgressDialog() {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        }
    }
}
