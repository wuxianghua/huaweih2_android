package com.palmap.demo.huaweih2.http;

import android.os.Looper;
import android.text.TextUtils;

import com.palmap.demo.huaweih2.HuaWeiH2Application;
import com.palmap.demo.huaweih2.util.DialogUtils;
import com.palmap.demo.huaweih2.util.IpUtils;
import com.palmap.demo.huaweih2.util.LogUtils;

/**
 * 错误定义类
 * Created by chuanchao.shao on 2014/5/5.
 */
public class ErrorCode {
    private static long timeStamp = 0;
//  private static int count =0;
    /**
     * 类型转换错误,返回的实际对象与想要得到的不符
     */
    public static final int CODE_CLASS_ERROR = 1001;
    /**
     * JSON类型转换错误，返回的实际对象与想要得到的不符
     */
    public static final int CODE_JSON_ERROR = 1002;

    /**
     * 网络请求错误
     */
    public static final int CODE_REQUEST_ERROR = 1003;

    /**
     * 无网络连接
     */
    public static final int CODE_NO_INTERNET = 1004;

    /*
    * Exception
    * */
    public static final int CODE_EXCEPTION = 1005;

    public static final int CODE_IP_EEEOR = 1006;


    public static void showError(final int code) {

        if (Thread.currentThread().getId() != Looper.getMainLooper().getThread().getId()) {
            return;
        }


        LogUtils.e("HTTP ErrorCode:" + code);
        long during = System.currentTimeMillis() - timeStamp;

        if (during < 20000)
            return;

        timeStamp = System.currentTimeMillis();

        if (code == CODE_NO_INTERNET) {
            DialogUtils.showShortToast("无网络连接");
            return;
        }
        if (code == CODE_EXCEPTION) {
            DialogUtils.showShortToast("网络错误");
            return;

        }
        if (code == CODE_REQUEST_ERROR) {
            DialogUtils.showShortToast("请求出错");
            return;

        }

        if (code == CODE_IP_EEEOR) {
            DialogUtils.showShortToast("当前不在移动4G环境下,无法体验定位功能");
            return;
        }

        if (code == 204) {
            //      count++;
            //      if (count>15) {
            //        count = 0;

            String ipType = IpUtils.getCurrentNetType(HuaWeiH2Application.instance);
            if (!TextUtils.isEmpty(ipType) && !"4g".equals(ipType) && !"中国移动".equals(IpUtils.getCurrentNetType(HuaWeiH2Application.instance))) {
//                DialogUtils.showShortToast("当前不在移动4G环境下,无法体验定位功能");
                DialogUtils.showShortToast("请在移动4G环境下体验");
            }else{
                DialogUtils.showShortToast(HuaWeiH2Application.userIp + "无定位数据");
            }
            return;
            //      }
        }
    }
}
