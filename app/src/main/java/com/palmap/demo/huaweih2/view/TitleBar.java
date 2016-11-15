package com.palmap.demo.huaweih2.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.palmap.demo.huaweih2.R;

/**
 * Created by eric3 on 2016/10/12.
 * 标题栏
 */

public class TitleBar extends RelativeLayout implements View.OnClickListener {
  private Context mContext;
  private TextView left;
  private TextView right;
  private TextView title;
  private ImageView im_back;
  private OnTitleClickListener onTitleClickListener;
  private boolean enableLeft =true;
  private boolean enableRight =true;
  private ImageView ico_right;

  private LinearLayout layout_extend_right;

  private void initView(){
    LayoutInflater.from(mContext).inflate(R.layout.layout_title_bar,this);

    left = (TextView) findViewById(R.id.title_left_tv);
    left.setOnClickListener(this);
    right = (TextView) findViewById(R.id.title_right_tv);
    right.setOnClickListener(this);
    title = (TextView) findViewById(R.id.title_center_tv);
    title.setOnClickListener(this);
    im_back = (ImageView)findViewById(R.id.title_bar_back);
    im_back.setOnClickListener(this);


    layout_extend_right = (LinearLayout) findViewById(R.id.layout_extend_right);

    ico_right = (ImageView) findViewById(R.id.ico_right);
  }

  public TitleBar(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.mContext = context;

    initView();
  }

  public TitleBar(Context context) {
    super(context);
    this.mContext = context;

    initView();
  }

  public void addRightExtendView(View v){
    if (null != layout_extend_right) {
      layout_extend_right.addView(v);
    }
  }

  public void setOnTitleClickListener(OnTitleClickListener onTitleClickListener){
    this.onTitleClickListener = onTitleClickListener;
  }
  public void setTitle(String s){
    title.setVisibility(VISIBLE);
    title.setText(s);
  }
  private void hideTitle(){
    title.setVisibility(View.GONE);
  }
  private void setLeft(String s){
    left.setVisibility(VISIBLE);
    im_back.setVisibility(View.GONE);
    left.setText(s);
  }
  private void hideLeft(){
    left.setVisibility(View.GONE);
  }
  private void setRight(String s){
    right.setVisibility(VISIBLE);
    right.setText(s);
  }
  private void hideRight(){
    right.setVisibility(View.GONE);
    ico_right.setVisibility(GONE);
  }
  private void setBackIcon(int i){
    left.setVisibility(View.GONE);
    im_back.setVisibility(i);
  }

  public void hide(){
    setVisibility(View.GONE);
  }

  public void show(String l,String t,String r){
    if (l==null) {
      setBackIcon(VISIBLE);
      left.setVisibility(GONE);
    }else {
      setLeft(l);
    }
    if (t==null)
      hideTitle();
    else
      setTitle(t);

    if (r==null)
      hideRight();
    else
      setRight(r);

    setVisibility(View.VISIBLE);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()){
      case R.id.title_bar_back:
        if (enableLeft)
        onTitleClickListener.onLeft();
        break;
      case R.id.title_left_tv:
        if (enableLeft)
        onTitleClickListener.onLeft();
        break;
      case R.id.title_right_tv:
        if (enableRight)
        onTitleClickListener.onRight();
        break;
      default:
        break;
    }
  }

  public void setRightIcoClickListener(View.OnClickListener onClickListener){
    ico_right.setOnClickListener(onClickListener);
  }

  public void setRightIcoImageRes(int res){
    ico_right.setVisibility(VISIBLE);
    ico_right.setImageResource(res);
  }


  public interface OnTitleClickListener {
    void onLeft();
    void onRight();
  }

  public void setEnableLeft(boolean enableLeft) {
    this.enableLeft = enableLeft;
  }

  public void setEnableRight(boolean enableRight) {
    this.enableRight = enableRight;
  }
}
