//package com.palmap.demo.huaweih2.view;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.LinearLayout;
//
//import com.palmap.demo.huaweih2.R;
//
///**
// * Created by eric3 on 2016/10/17.
// */
//
//public class FullScreenDialog extends Dialog implements View.OnClickListener{
//  @Override
//  public void onClick(View v) {
//    switch (v.getId()){
//      case R.id.btn_map:
//        dialogListener.btnMapClick();
//        break;
//      case R.id.btn_foot:
//        dialogListener.btnFootClick();
//        break;
//      default:
//        break;
//    }
//  }
//
//  //定义回调事件，用于dialog的点击事件
//  public interface OnDialogListener{
//     void btnMapClick();
//    void btnFootClick();
//  }
//
//  private OnDialogListener dialogListener;
//  LinearLayout btn_map;
//  LinearLayout btn_foot;
//
//  public FullScreenDialog(Context context, OnDialogListener dialogListener) {
//    super(context,R.style.dialog_full_screen);
//    this.dialogListener = dialogListener;
//  }
//
//  @Override
//  protected void onCreate(Bundle savedInstanceState) {
//    super.onCreate(savedInstanceState);
//    setContentView(R.layout.full_screen_dialog);
//
//    setCanceledOnTouchOutside(false);
//    btn_foot = (LinearLayout)findViewById(R.id.btn_foot);
//    btn_foot.setOnClickListener(this);
//    btn_map = (LinearLayout)findViewById(R.id.btn_map);
//    btn_map.setOnClickListener(this);
//  }
//
//  public void dismissDialog(){
//    this.dismiss();
//  }
//}
