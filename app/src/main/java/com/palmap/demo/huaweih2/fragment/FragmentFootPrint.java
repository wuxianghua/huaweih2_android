package com.palmap.demo.huaweih2.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.palmap.demo.huaweih2.R;
import com.palmap.demo.huaweih2.view.TitleBar;

/**
 * Created by eric3 on 2016/10/8.
 */

public class FragmentFootPrint extends BaseFragment implements View.OnClickListener{
  ImageView imageView;
  @Override
  public void onCreate(Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater,
                           @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    View fragmentView = inflater.inflate(R.layout.foot_print, container, false);
    imageView  = (ImageView)fragmentView.findViewById(R.id.image_foot_print);
    imageView.setOnClickListener(this);

    getMainActivity().titleBar.setOnTitleClickListener(new TitleBar.OnTitleClickListener() {
      @Override
      public void onLeft() {
        getMainActivity().showFragmentMap();
      }

      @Override
      public void onRight() {

      }
    });
    return fragmentView;
  }

  //从服务器获取某位置的所有图片列表
  private void getImageListByPosition(){

  }

  @Override
  public void onClick(View v) {
    switch (v.getId()){
      case R.id.image_foot_print:
//        startActivity(new Intent(getMainActivity(), ImageListActivity.class));
        break;

      default:
        break;
    }
  }
}
