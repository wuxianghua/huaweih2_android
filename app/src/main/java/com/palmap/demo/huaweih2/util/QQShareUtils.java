package com.palmap.demo.huaweih2.util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.palmap.demo.huaweih2.view.SharePopView;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

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

  public void shareToQQ(Activity context, SharePopView.ShareModel shareModel)
  {
//    String imgUrl = Environment.getExternalStorageDirectory() + File.separator + Constant.LUR_NAME+"/applogo.png";
    final Bundle params = new Bundle();
    params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
    params.putString(QQShare.SHARE_TO_QQ_TITLE, shareModel.title);
    params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  "华为ICS实验室");
    params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  "http://www.wandoujia.com/apps/com.palmap.demo.huaweih2");
    params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,shareModel.imgUrl);
    params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  "ICS室内定位");
    params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL,  shareModel.imgUrl);

//    params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,"其他附加功能");
    mTencent.shareToQQ(context, params, new BaseUiListener());
  }

  public void shareToQzone (Activity context,SharePopView.ShareModel shareModel) {
    //分享类型
    final Bundle params = new Bundle();
    params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
    params.putString(QQShare.SHARE_TO_QQ_TITLE, shareModel.title);
    params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  "华为ICS实验室");
    params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  "https://www.pgyer.com/nkee");
    params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,shareModel.imgUrl);
    params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  "ICS室内定位");

    params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,  QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
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
