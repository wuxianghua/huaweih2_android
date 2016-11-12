package com.palmap.demo.huaweih2.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.palmap.demo.huaweih2.HuaWeiH2Application;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static android.widget.Toast.makeText;

/**
 * Created by eric3 on 2016/10/8.
 */

public class DialogUtils {
  public static void showShortToast(String msg){
    makeText(HuaWeiH2Application.instance, msg, Toast.LENGTH_SHORT).show();
  }

  public static void showShortToast(String msg,int locate){
    Toast toast = Toast.makeText(HuaWeiH2Application.instance, msg, Toast.LENGTH_SHORT);
    toast.setGravity(locate,0,-10);
    toast.show();
  }

  public static void showLongToast(String msg){
    makeText(HuaWeiH2Application.instance, msg, Toast.LENGTH_LONG).show();
  }

  // 定义一个显示消息的对话框,通过反射方式调用方法，目前只支持无参方法
  public static void showDialog(final Context context
      , String msg,final String methodNamePositive,final String methodNameNegative)
  {
    // 创建一个AlertDialog.Builder对象
    AlertDialog.Builder builder = new AlertDialog.Builder(context)
        .setMessage(msg).setCancelable(false);

    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
        try {
          Method f = context.getClass().getMethod(methodNameNegative);
          f.invoke(context);
        } catch (NoSuchMethodException e) {
          DialogUtils.showLongToast(methodNameNegative+"方法调用失败！");
          e.printStackTrace();
        } catch (InvocationTargetException e) {
          DialogUtils.showLongToast(methodNameNegative+"方法调用失败！");
          e.printStackTrace();
        } catch (IllegalAccessException e) {
          DialogUtils.showLongToast(methodNameNegative+"方法调用失败！");
          e.printStackTrace();
        }
      }
    });

    builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
    {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
        try {
          Method f = context.getClass().getMethod(methodNamePositive);
          f.invoke(context);
        } catch (NoSuchMethodException e) {
          DialogUtils.showLongToast(methodNamePositive+"方法调用失败！");
          e.printStackTrace();
        } catch (InvocationTargetException e) {
          DialogUtils.showLongToast(methodNamePositive+"方法调用失败！");
          e.printStackTrace();
        } catch (IllegalAccessException e) {
          DialogUtils.showLongToast(methodNamePositive+"方法调用失败！");
          e.printStackTrace();
        }


      }
    });


    builder.create().show();
  }

  // 定义一个显示消息的对话框,通过接口调用方法，目前只支持无参方法
  public static void showDialog(final Context context
      , String msg, final DialogCallBack callBack)
  {
    // 创建一个AlertDialog.Builder对象
    AlertDialog.Builder builder = new AlertDialog.Builder(context)
        .setMessage(msg).setCancelable(false);

    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
        callBack.onCancel();
      }
    });

    builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
    {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
        callBack.onComplete();
      }
    });


    builder.create().show();
  }

  public interface DialogCallBack {
    public void onComplete();
    public void onCancel();
  }
}
