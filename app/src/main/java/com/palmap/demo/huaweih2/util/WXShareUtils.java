package com.palmap.demo.huaweih2.util;

import android.content.Context;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by eric3 on 2016/10/18.
 */

public class WXShareUtils {
private static final String APP_ID = "wx35765f080a854364";
private static IWXAPI weChatApi;

  public static void regToWeChat(Context context){

    weChatApi = WXAPIFactory.createWXAPI(context,APP_ID,true);
    weChatApi.registerApp(APP_ID);
  }

  public static void sendToWeChat(String text){
    WXTextObject textObject = new WXTextObject();
    textObject.text = text;

    WXMediaMessage msg = new WXMediaMessage();
    msg.mediaObject = textObject;
    msg.description = text;

    SendMessageToWX.Req req = new SendMessageToWX.Req();

    req.transaction = String.valueOf(System.currentTimeMillis());
    req.message = msg;

    weChatApi.sendReq(req);
  }

}
