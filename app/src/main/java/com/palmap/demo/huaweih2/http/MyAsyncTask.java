//package com.palmap.demo.huaweih2.http;
//
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.AsyncTask;
//import android.util.Log;
//
//import com.palmap.demo.huaweih2.other.Constant;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.HttpStatus;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.mime.FormBodyPart;
//import org.apache.http.entity.mime.MultipartEntity;
//import org.apache.http.entity.mime.content.ByteArrayBody;
//import org.apache.http.entity.mime.content.ContentBody;
//import org.apache.http.entity.mime.content.FileBody;
//import org.apache.http.entity.mime.content.InputStreamBody;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.protocol.HTTP;
//import org.apache.http.util.EntityUtils;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.UUID;
//
///**
// * Created by eric3 on 2016/10/23.
// */
//
//public class MyAsyncTask extends AsyncTask<String, Integer, String> {
//
//  String FORM_TABLE_NAME = "image";// 自己需要配置的表单
//  String filePath = Constant.PATH_PICTURE_UPLOAD;// 测试写的文件路径，转换成自己的文件路径
//  final String hostUrl = "http://10.0.10.227:8090/ICSData/insertPhoto";// 写成自己要上传的地址
//
//  @Override
//  protected String doInBackground(String... params) {
//    final String BOUNDARY = UUID.randomUUID().toString(); //边界标识 随机生成
//    HttpClient httpclient = null;
//    httpclient = new DefaultHttpClient();
//
//    final HttpPost httppost = new HttpPost(hostUrl);
//    httppost.setHeader("Content-Type", "multipart/form-data;boundary=" + BOUNDARY);
//    final File imageFile = new File(filePath);
//    final MultipartEntity multipartEntity = new MultipartEntity();
//
//    if (false) {
//      InputStream in = null;
//      try {
//        in = new FileInputStream(imageFile);
//      } catch (FileNotFoundException e) {
//        e.printStackTrace();
//      }
//      InputStreamBody inputStreamBody = new InputStreamBody(in,
//          "android_inputstream.jpg");
//      // FormBodyPart formBodyPart = new FormBodyPart(FORM_TABLE_NAME,
//      // contentBody);
//      multipartEntity.addPart(FORM_TABLE_NAME, inputStreamBody);
//    }
//    if (false) {
//      ContentBody contentBody = new FileBody(imageFile);
//      FormBodyPart formBodyPart = new FormBodyPart(FORM_TABLE_NAME,
//          contentBody);
//      multipartEntity.addPart(formBodyPart);
//    }
//    if (false) {
//      // FileBody fileBody = new FileBody(imageFile, "image/jpeg",
//      // "utf-8");
//      FileBody fileBody = new FileBody(imageFile);
//      multipartEntity.addPart(FORM_TABLE_NAME, fileBody);
//    }
//
//    if (true) {
//      Bitmap photoBM = BitmapFactory.decodeFile(filePath);
//      if (photoBM == null) {
//        return null;
//      }
//      ByteArrayOutputStream photoBao = new ByteArrayOutputStream();
//      boolean successCompress = photoBM.compress(Bitmap.CompressFormat.JPEG,
//          80, photoBao);
//      if (!successCompress) {
//        return null;
//      }
//      ByteArrayBody byteArrayBody = new ByteArrayBody(
//          photoBao.toByteArray(), System.currentTimeMillis()+".jpg");
//      photoBM.recycle();
//      // InputStreamBody inbody = new InputStreamBody(new InputStream,
//      // filename);
//      multipartEntity.addPart(FORM_TABLE_NAME, byteArrayBody);
//    }
//
//    httppost.setEntity(multipartEntity);
//
//    HttpResponse httpResponse;
//    try {
//      httpResponse = httpclient.execute(httppost);
//
//      final int statusCode = httpResponse.getStatusLine()
//          .getStatusCode();
//
//      String response = EntityUtils.toString(
//          httpResponse.getEntity(), HTTP.UTF_8);
//      Log.d("","got response:\n" + response);
//
//      if (statusCode == HttpStatus.SC_OK) {
//        return "success";
//      }
//
//    } catch (ClientProtocolException e) {
//      e.printStackTrace();
//    } catch (IOException e) {
//      e.printStackTrace();
//    } finally {
//      if (httpclient != null) {
//        httpclient.getConnectionManager().shutdown();
//        httpclient = null;
//      }
//    }
//    return null;
//
//  }
//
//  @Override
//  protected void onPostExecute(String result) {
//    super.onPostExecute(result);
//    if ("success".equals(result)) {
//      Log.d("","");
//    }
//  }
//
//}
