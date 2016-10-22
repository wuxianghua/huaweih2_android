package com.palmap.demo.huaweih2.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.palmap.demo.huaweih2.impl.Pullable;

/**
 * Created by eric3 on 2016/10/21.
 */

public class PullableScrollView extends ScrollView implements Pullable
{

  public PullableScrollView(Context context)
  {
    super(context);
  }

  public PullableScrollView(Context context, AttributeSet attrs)
  {
    super(context, attrs);
  }

  public PullableScrollView(Context context, AttributeSet attrs, int defStyle)
  {
    super(context, attrs, defStyle);
  }

  @Override
  public boolean canPullDown()
  {
//    if (getScrollY() == 0)
//      return true;
//    else
      return false;
  }

  @Override
  public boolean canPullUp()
  {
    if (getScrollY() >= (getChildAt(0).getHeight() - getMeasuredHeight()))
      return true;
    else
      return false;
  }

}
