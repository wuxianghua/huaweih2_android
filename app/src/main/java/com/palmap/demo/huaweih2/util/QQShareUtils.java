package com.palmap.demo.huaweih2.util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;

import com.palmap.demo.huaweih2.other.Constant;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.io.File;

import static com.tencent.connect.share.QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT;

/**
 * Created by eric3 on 2016/10/18.
 */
public class QQShareUtils {
  private static final String APP_ID = "1105764370";
  private static Tencent mTencent;
  static QQShareUtils instance = new QQShareUtils();

  public static QQShareUtils getInstance() {
    return instance;
  }

  public void regToQQ(Context context){
    mTencent = Tencent.createInstance(APP_ID, context);
  }

  public void shareToQQ(Activity context)
  {
    String img = Environment.getExternalStorageDirectory() + File.separator + Constant.LUR_NAME+"/applogo.png";
    final Bundle params = new Bundle();
    params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
    params.putString(QQShare.SHARE_TO_QQ_TITLE, "我的分享");
    params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  "我正在华为ICS实验室参观");
    params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  "https://www.pgyer.com/nkee");
    params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,img);
    params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  "ICS室内定位");
//    params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,"其他附加功能");
    mTencent.shareToQQ(context, params, new BaseUiListener());
  }

  private void shareToQzone (Activity context) {
    //分享类型
    final Bundle params = new Bundle();
    params.putString(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,SHARE_TO_QZONE_TYPE_IMAGE_TEXT+"");
    params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "ICS室内定位");//必填
    params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "app下载链接");//选填
    params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "https://www.pgyer.com/nkee");//必填
//    params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, "图片链接ArrayList");
    mTencent.shareToQzone(context, params, new BaseUiListener());
  }

  private class BaseUiListener implements IUiListener {

    @Override
    public void onComplete(Object o) {
      DialogUtils.showShortToast("分享成功");
//      doComplete(response);
    }

    @Override
    public void onError(UiError e) {
      DialogUtils.showLongToast("onError: code:" + e.errorCode + ", msg:"
          + e.errorMessage + ", detail:" + e.errorDetail);
    }
    @Override
    public void onCancel() {
      DialogUtils.showLongToast("onCancel");
    }
  }
}
