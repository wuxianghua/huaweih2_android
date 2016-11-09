package com.palmap.demo.huaweih2;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;
import com.palmap.demo.huaweih2.adapter.ImageAlbumAdapter;
import com.palmap.demo.huaweih2.http.DataProviderCenter;
import com.palmap.demo.huaweih2.http.ErrorCode;
import com.palmap.demo.huaweih2.http.HttpDataCallBack;
import com.palmap.demo.huaweih2.json.PictureModel;
import com.palmap.demo.huaweih2.other.Constant;
import com.palmap.demo.huaweih2.util.DialogUtils;
import com.palmap.demo.huaweih2.util.JsonUtils;
import com.palmap.demo.huaweih2.util.SystemBarTintManager;
import com.palmap.demo.huaweih2.view.TitleBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 图册activity
 *  Created by 王天明 on 2016/10/22
 */
public class ImageAlbumActivity extends BaseActivity implements PullToRefreshBase.OnRefreshListener2 {

    private PullToRefreshRecyclerView recyclerView;
    private ImageAlbumAdapter imageAlbumAdapter;
    private TitleBar titleBar;
    List<PictureModel> pictureModelList;
    ArrayList<String> urlData = new ArrayList<>();
    String location;
    int start = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_album);

        initStatusBar(R.color.black);

        location = getIntent().getStringExtra("location");
        recyclerView = (PullToRefreshRecyclerView) findViewById(R.id.recyclerView);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setReverseLayout(false);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.getRefreshableView().setLayoutManager(layoutManager);

        recyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        recyclerView.setOnRefreshListener(this);
        recyclerView.getRefreshableView().addItemDecoration(new SpacesItemDecoration(10));

       loadPictureModels();


        titleBar=(TitleBar)findViewById(R.id.title_bar);
        titleBar.show(null,location,null);
        titleBar.setOnTitleClickListener(new TitleBar.OnTitleClickListener() {
            @Override
            public void onLeft() {
                finish();
            }

            @Override
            public void onRight() {

            }
        });
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        refreshView.onRefreshComplete();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        ArrayList<String> urlData2 = new ArrayList<>();
        int current =start;
        for (; start< (Constant.EACH_TIME_PICTURE_NUM+current)&&start<pictureModelList.size(); start++) {
            urlData.add(pictureModelList.get(start).getPhoto());
        }
        imageAlbumAdapter.addAll(urlData2);
        refreshView.onRefreshComplete();
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
        }
    }

    private void loadPictureModels() {
        //加载图片库url
        String js = JsonUtils.getDownloadImg(location);
        DataProviderCenter.getInstance().getPictures(js, new HttpDataCallBack() {
            @Override
            public void onError(int errorCode) {
                ErrorCode.showError(errorCode);
            }

            @Override
            public void onComplete(Object content) {

                pictureModelList = JsonUtils.getPictureModel(content);
                if (pictureModelList==null||pictureModelList.size()<=0){
                    DialogUtils.showShortToast("没有照片");
                    return;
                }


                int current =start;
                for (; start< (Constant.EACH_TIME_PICTURE_NUM+current)&&start<pictureModelList.size(); start++) {
                    urlData.add(pictureModelList.get(start).getPhoto());
                }
                if (start>=pictureModelList.size()-1)
                    DialogUtils.showShortToast("没有更多");

                imageAlbumAdapter = new ImageAlbumAdapter(ImageAlbumActivity.this,urlData);
                recyclerView.getRefreshableView().setAdapter(imageAlbumAdapter);
                imageAlbumAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                Intent intent = new Intent(ImageAlbumActivity.this, ImageViewActivity.class);
//                intent.putStringArrayListExtra("imgList",  imageAlbumAdapter.getImageUrl());
//                intent.putExtra("itemIndex",
//                        position);

                        Intent intent = new Intent(ImageAlbumActivity.this, FootprintDetailsActivity.class);

                        PictureModel pictureModel = new PictureModel();
                        pictureModel = pictureModelList.get(position);
//                        pictureModel.setAppendix("测试测试测试描述");
//                        pictureModel.setUpdtime(System.currentTimeMillis());
//                        pictureModel.setLocation("ICS实验室");
//                        pictureModel.setPhoto("http://7xnnph.com1.z0.glb.clouddn.com/11385343fbf2b2112cf911efce8065380dd78eb8.jpg");

                        intent.putExtra(PictureModel.class.getSimpleName(),  pictureModel);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    /**
     * 设置状态栏颜色
     * API >=19
     *
     * @param colorId
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected void initStatusBar(int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 19
            Window window = getWindow();
            WindowManager.LayoutParams winParams = window.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            window.setAttributes(winParams);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setNavigationBarTintEnabled(true);
            tintManager.getConfig().getNavigationBarHeight();
            try {
                tintManager.setTintResource(colorId);
                int height = tintManager.getConfig().getStatusBarHeight();
                getRootView().setPadding(
                        getRootView().getLeft(),
                        getRootView().getPaddingTop() + height,
                        getRootView().getRight(),
                        getRootView().getBottom());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取root节点
     *
     * @return
     */
    public View getRootView() {
        return findViewById(android.R.id.content);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}
