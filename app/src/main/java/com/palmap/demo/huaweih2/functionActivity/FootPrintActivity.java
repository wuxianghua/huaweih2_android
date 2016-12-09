package com.palmap.demo.huaweih2.functionActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.palmap.demo.huaweih2.BaseActivity;
import com.palmap.demo.huaweih2.H2MainActivity;
import com.palmap.demo.huaweih2.LocateTimerService;
import com.palmap.demo.huaweih2.R;
import com.palmap.demo.huaweih2.UploadActivity;
import com.palmap.demo.huaweih2.fragment.FragmentFootPrint;
import com.palmap.demo.huaweih2.other.Constant;
import com.palmap.demo.huaweih2.util.BitMaputils;
import com.palmap.demo.huaweih2.util.DialogUtils;
import com.palmap.demo.huaweih2.util.QQShareUtils;
import com.palmap.demo.huaweih2.view.SharePopView;
import com.palmap.demo.huaweih2.view.TitleBar;

import java.io.File;

import static com.palmap.demo.huaweih2.other.Constant.startTakePic;
import static com.palmaplus.nagrand.position.ble.BeaconUtils.TAG;

public class FootPrintActivity extends BaseActivity {

    private TitleBar titleBar;
    FragmentFootPrint fragmentFootPrint;
    Bitmap temBitmap;
    Bitmap cobitmap;

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
                if (ActivityCompat.checkSelfPermission(FootPrintActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    DialogUtils.showShortToast("请在设置中开启相机权限", Gravity.CENTER);
                    return;
                }
                String lo = LocateTimerService.getCurrentLocationArea();
                if (lo.equals(Constant.其它)) {
                    DialogUtils.showShortToast("没有获取到当前位置，不能拍照", Gravity.CENTER);
                } else {
                    openCameraActivity();
                }

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
//                shareModel.urlBmp = BitmapFactory.decodeResource(getResources(), R.drawable.share_img);


                View coView = fragmentFootPrint.getBitMapView();
                coView.setDrawingCacheEnabled(true);
                cobitmap = coView.getDrawingCache();
                // 拷贝图片，否则在setDrawingCacheEnabled(false)以后该图片会被释放掉
                cobitmap = Bitmap.createBitmap(cobitmap);
                coView.setDrawingCacheEnabled(false);
                String bitmapPath = Environment.getExternalStorageDirectory() + File.separator
                    + "coBitmap";
                BitMaputils.saveBitmap(cobitmap,bitmapPath);

                shareModel.imgUrl = bitmapPath;
                shareModel.urlBmp = cobitmap;
                shareModel.text = "华为ICS实验室室内定位解决方案";
                shareModel.title = "华为ICS实验室";
                SharePopView.showSharePop(FootPrintActivity.this,shareModel, QQShareUtils.TYPE_LOCAL);
            }
        });
        titleBar.addRightExtendView(imageView);

    }


//    private Bitmap getScreenShoot(){
//        View coView = fragmentFootPrint.getBitMapView();
//        coView.setDrawingCacheEnabled(true);
//        cobitmap = coView.getDrawingCache();
//        // 拷贝图片，否则在setDrawingCacheEnabled(false)以后该图片会被释放掉
//        cobitmap = Bitmap.createBitmap(cobitmap);
//        coView.setDrawingCacheEnabled(false);
//        String bitmapPath = Environment.getExternalStorageDirectory() + File.separator
//            + "coBitmap";
//        BitMaputils.saveBitmap(cobitmap,bitmapPath);
//
//
////        //获取当前屏幕的大小
////        int width = getWindow().getDecorView().getRootView().getWidth();
////        int height = getWindow().getDecorView().getRootView().getHeight();
////        //生成相同大小的图片
////        temBitmap = Bitmap.createBitmap( width, height, Bitmap.Config.RGB_565);
////        //找到当前页面的跟布局
////        View view =  getWindow().getDecorView().getRootView();
////        //设置缓存
////        view.setDrawingCacheEnabled(true);
////        view.buildDrawingCache();
////        //从缓存中获取当前屏幕的图片
////        temBitmap = view.getDrawingCache();
//
////        //输出到sd卡
////        if (FileIOUtil.getExistStorage()) {
////            FileIOUtil.GetInstance().onFolderAnalysis(FileIOUtil.GetInstance().getFilePathAndName());
////            File file = new File(FileIOUtil.GetInstance().getFilePathAndName());
////            try {
////                if (!file.exists()) {
////                    file.createNewFile();
////                }
////                FileOutputStream foStream = new FileOutputStream(file);
////                temBitmap.compress(Bitmap.CompressFormat.PNG, 100, foStream);
////                foStream.flush();
////                foStream.close();
////            } catch (Exception e) {
////                Log.i("Show", e.toString());
////            }
////        }
//        return temBitmap;
//    }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (temBitmap!=null&&!temBitmap.isRecycled())
            temBitmap.recycle();
        if (cobitmap!=null&&!cobitmap.isRecycled())
            cobitmap.recycle();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, H2MainActivity.class));
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(this, H2MainActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
