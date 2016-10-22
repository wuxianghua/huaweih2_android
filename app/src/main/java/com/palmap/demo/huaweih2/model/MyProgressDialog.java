package com.palmap.demo.huaweih2.model;

/**
 * Created by eric3 on 2016/10/8.
 */

import android.app.ProgressDialog;
import android.content.Context;

public class MyProgressDialog extends ProgressDialog {
  public MyProgressDialog(Context context, int theme) {
    super(context, theme);
  }

  public MyProgressDialog(Context context) {
    super(context);
  }
}