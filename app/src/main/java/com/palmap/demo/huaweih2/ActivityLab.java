package com.palmap.demo.huaweih2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.palmap.demo.huaweih2.http.DataProviderCenter;
import com.palmap.demo.huaweih2.http.HttpDataCallBack;
import com.palmap.demo.huaweih2.json.CommentDown;
import com.palmap.demo.huaweih2.other.Constant;
import com.palmap.demo.huaweih2.util.DialogUtils;
import com.palmap.demo.huaweih2.util.JsonUtils;
import com.palmap.demo.huaweih2.view.TitleBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityLab extends BaseActivity {
//  PullToRefreshLayout pullToRefreshLayout;
  PullToRefreshScrollView refreshScrollView;
  RelativeLayout loadmore_view;
  TitleBar titleBar;
  LinearLayout commentList;
  int start = 0;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_lab);
    titleBar  = (TitleBar)findViewById(R.id.title_bar);
    commentList = (LinearLayout)findViewById(R.id.com_list);
    loadmore_view = (RelativeLayout)findViewById(R.id.loadmore_view);
    refreshScrollView = (PullToRefreshScrollView) findViewById(R.id.refreshScrollView);

    refreshScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);

    refreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
      @Override
      public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        refreshView.onRefreshComplete();
      }

      @Override
      public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        //加载评论

        String js = JsonUtils.getCommentsDown(Constant.ICS实验室,start,Constant.EACH_TIME_COMMENT_NUM);
        DataProviderCenter.getInstance().getComments(js, new HttpDataCallBack() {
          @Override
          public void onError(int errorCode) {
            DialogUtils.showLongToast(errorCode +"");
          }

          @Override
          public void onComplete(Object content) {
//            loadmore_view.setVisibility(View.INVISIBLE);
            Log.i("", content.toString());
            List<CommentDown> list = new ArrayList<>(JSONArray.parseArray(content.toString(), CommentDown.class));
            if (list.size()==0)
              return;


            for (int i = 0; i < list.size(); i++) {
              //显示评论
              // TODO 动态添加布局(xml方式)
              LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                  LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
              LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
              View view = inflater.inflate(R.layout.com_list_item, commentList,false);
              view.setLayoutParams(lp);
              TextView tn = (TextView) view.findViewById(R.id.com_name);
              tn.setText(list.get(i).getUserId());
              TextView tc = (TextView) view.findViewById(R.id.com_text);
              tc.setText(list.get(i).getComment());
              TextView tt = (TextView) view.findViewById(R.id.com_time);
              SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
              Date date = new Date(list.get(i).getComTime());
              tt.setText(sdf.format(date));

              start++;
              commentList.addView(view);
            }
          }
        });



        refreshView.onRefreshComplete();
      }
    });

//    pullToRefreshLayout = (PullToRefreshLayout)findViewById(R.id.container);
//    pullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
//      @Override
//      public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
//        Log.i("","onRefresh");
//      }
//
//      @Override
//      public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
//        //加载评论
//
//        String js = JsonUtils.getCommentsDown(Constant.ICS实验室,start,Constant.EACH_TIME_COMMENT_NUM);
//        DataProviderCenter.getInstance().getComments(js, new HttpDataCallBack() {
//          @Override
//          public void onError(int errorCode) {
//            DialogUtils.showLongToast(errorCode +"");
//          }
//
//          @Override
//          public void onComplete(Object content) {
////            loadmore_view.setVisibility(View.INVISIBLE);
//            Log.i("", content.toString());
//            List<CommentDown> list = new ArrayList<>(JSONArray.parseArray(content.toString(), CommentDown.class));
//            if (list.size()==0)
//              return;
//
//
//            for (int i = 0; i < list.size(); i++) {
//              //显示评论
//              // TODO 动态添加布局(xml方式)
//              LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                  LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//              LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
//              View view = inflater.inflate(R.layout.com_list_item, commentList,false);
//              view.setLayoutParams(lp);
//              TextView tn = (TextView) view.findViewById(R.id.com_name);
//              tn.setText(list.get(i).getUserId());
//              TextView tc = (TextView) view.findViewById(R.id.com_text);
//              tc.setText(list.get(i).getComment());
//              TextView tt = (TextView) view.findViewById(R.id.com_time);
//              SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//              Date date = new Date(list.get(i).getComTime());
//              tt.setText(sdf.format(date));
//
//              start++;
//              commentList.addView(view);
//            }
//          }
//        });
//
//
//      }
//    });

    titleBar.show(null,"ICS实验室",null);
    titleBar.setOnTitleClickListener(new TitleBar.OnTitleClickListener() {
      @Override
      public void onLeft() {
        finish();
      }
      @Override
      public void onRight() {

      }
    });
//    pullToRefreshLayout.autoLoad();
  }
}
