package com.palmap.demo.huaweih2.util;

import android.content.Context;
import android.graphics.Bitmap;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;

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

  public static void sendTextToWeChat(String text){
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
  public static void sendToWeChat(Bitmap urlBmp,String text,int type){
    Bitmap bitmap = urlBmp;
    WXImageObject imageObject = new WXImageObject(bitmap);
    WXMediaMessage wxMediaMessage = new WXMediaMessage();
    wxMediaMessage.mediaObject = imageObject;
    wxMediaMessage.description = text;
    wxMediaMessage.title = "ICS足迹分享";

    Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap,50,50,true);
//    bitmap.recycle();//不能recycle
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    thumbBmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
    wxMediaMessage.thumbData = baos.toByteArray();

    SendMessageToWX.Req req = new SendMessageToWX.Req();

    req.transaction = String.valueOf(System.currentTimeMillis());
    req.message = wxMediaMessage;
    req.scene = type;

    weChatApi.sendReq(req);
  }

//  /**
//   * 根据图片的url路径获得Bitmap对象
//   * @param url
//   * @return
//   */
//  private static Bitmap getBitmap(String url) {
//    URL fileUrl = null;
//    Bitmap bitmap = null;
//
//    try {
//      fileUrl = new URL(url);
//    } catch (MalformedURLException e) {
//      e.printStackTrace();
//    }
//
//    try {
//      HttpURLConnection conn = (HttpURLConnection) fileUrl
//          .openConnection();
//      conn.setDoInput(true);
//      conn.connect();
//      InputStream is = conn.getInputStream();
//      bitmap = BitmapFactory.decodeStream(is);
//      is.close();
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//    return bitmap;
//
//  }

}
