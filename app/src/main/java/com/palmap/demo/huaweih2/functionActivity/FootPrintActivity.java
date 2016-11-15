package com.palmap.demo.huaweih2.functionActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.palmap.demo.huaweih2.BaseActivity;
import com.palmap.demo.huaweih2.R;
import com.palmap.demo.huaweih2.UploadActivity;
import com.palmap.demo.huaweih2.fragment.FragmentFootPrint;
import com.palmap.demo.huaweih2.other.Constant;
import com.palmap.demo.huaweih2.util.QQShareUtils;
import com.palmap.demo.huaweih2.view.SharePopView;
import com.palmap.demo.huaweih2.view.TitleBar;

import java.io.File;

import static com.palmap.demo.huaweih2.other.Constant.startTakePic;
import static com.palmaplus.nagrand.position.ble.BeaconUtils.TAG;

public class FootPrintActivity extends BaseActivity {

    private TitleBar titleBar;
    FragmentFootPrint fragmentFootPrint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foot_print2);

        titleBar = (TitleBar) findViewById(R.id.titleBar);
        titleBar.show(null, "足迹", null);
        titleBar.setOnTitleClickListener(new TitleBar.OnTitleClickListener() {
            @Override
            public void onLeft() {
                onBackPressed();
            }

            @Override
            public void onRight() {
            }
        });
        titleBar.setRightIcoImageRes(R.drawable.ico_nav_camera);
        titleBar.setRightIcoClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCameraActivity();
            }
        });
        fragmentFootPrint = (FragmentFootPrint) getFragmentManager().findFragmentById(R.id.layout_fragment);


        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.ico_tab_share);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                WXShareUtils.sendToWeChat(BitmapFactory.decodeResource(getResources(), R.drawable.share_img),
//                        null,
//                        );
                SharePopView.ShareModel shareModel =  new SharePopView.ShareModel();
                shareModel.urlBmp = BitmapFactory.decodeResource(getResources(), R.drawable.share_img);
                shareModel.imgUrl = Environment.getExternalStorageDirectory()+"/"+Constant.LUR_NAME+"/share_img.jpg";
                shareModel.title = "华为ICS实验室室内定位解决方案";
                SharePopView.showSharePop(FootPrintActivity.this,shareModel, QQShareUtils.TYPE_LOCAL);
            }
        });
        titleBar.addRightExtendView(imageView);

    }

    public void openCameraActivity() {
        Intent intent = new Intent();
        // 指定开启系统相机的Action
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 400 * 200);
        //如果路径不存在，则创建
        // 创建文件夹
        File file = new File(Constant.DIR_PICTURE_UPLOAD);
        if (!file.exists()) {
            file.mkdirs();
//      file = new File(Constant.DIR_PICTURE_UPLOAD);
//      if (!file.exists()) {
//        file.mkdirs();
//      }
        }
        // 根据文件地址创建文件
        file = new File(Constant.PATH_PICTURE_UPLOAD);
        if (file.exists()) {
            file.delete();
        }
        // 把文件地址转换成Uri格式
        Uri uri = Uri.fromFile(file);
        // 设置系统相机拍摄照片完成后图片文件的存放地址
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, startTakePic);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constant.startTakePic:
                Log.i(TAG, "拍摄完成，resultCode=" + requestCode);
                Intent intent = new Intent(this, UploadActivity.class);
//    intent.putExtra()
                startActivityForResult(intent, Constant.startUploadPic);
                break;

            case Constant.startUploadPic:
                if (fragmentFootPrint != null)
                    fragmentFootPrint.loadPicNum();
                break;
//            case Constant.startUploadText:
//                if (fragmentFootPrint.commentList == null) {
//                    break;
//                }
//                if (resultCode == RESULT_OK) {
//                    fragmentFootPrint.commentList.removeAllViews();
//                    fragmentFootPrint.start = 0;
//                    fragmentFootPrint.loadComments();
//                }
//                break;
            default:
                break;
        }


        super.onActivityResult(requestCode, resultCode, data);

    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//
//
//            default:
//                break;
//        }
//
//
//        super.onActivityResult(requestCode, resultCode, data);
//
//    }
}
