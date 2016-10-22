package com.palmap.demo.huaweih2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.palmap.demo.huaweih2.ImageAlbumActivity;
import com.palmap.demo.huaweih2.R;
import com.palmap.demo.huaweih2.view.FootPrintItemView;
import com.palmap.demo.huaweih2.view.TitleBar;

/**
 * Created by eric3 on 2016/10/8.
 */

public class FragmentFootPrint extends BaseFragment implements View.OnClickListener {

    FootPrintItemView footprintView_office;
    FootPrintItemView footprintView_lab;
    FootPrintItemView footprintView_h2;
    FootPrintItemView footprintView_meetingRoom;
    FootPrintItemView footprintView_h2_hall;

    private PullToRefreshScrollView refreshScrollView;

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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
    }

    private void bindView(View view) {
        footprintView_office = (FootPrintItemView) view.findViewById(R.id.footprintView_office);
        footprintView_lab = (FootPrintItemView) view.findViewById(R.id.footprintView_lab);
        footprintView_h2 = (FootPrintItemView) view.findViewById(R.id.footprintView_h2);
        footprintView_meetingRoom = (FootPrintItemView) view.findViewById(R.id.footprintView_meetingRoom);
        footprintView_h2_hall = (FootPrintItemView) view.findViewById(R.id.footprintView_h2_hall);
        registerFootPrintItemViewClickEvent(footprintView_office
                , footprintView_lab
                , footprintView_h2
                , footprintView_meetingRoom
                , footprintView_h2_hall
        );

        refreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.refreshScrollView);

        refreshScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);

        refreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                refreshView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                //网络请求
                refreshView.onRefreshComplete();
            }
        });

    }

    private void registerFootPrintItemViewClickEvent(FootPrintItemView... views) {
        for (FootPrintItemView v : views) {
            v.setOnClickListener(new FootPrintItemViewClickEvent(v));
        }
    }

    //从服务器获取某位置的所有图片列表
    private void getImageListByPosition() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//      case R.id.image_foot_print:
//        startActivity(new Intent(getMainActivity(), ImageListActivity.class));
//        break;

            default:
                break;
        }
    }

    private class FootPrintItemViewClickEvent implements View.OnClickListener {
        FootPrintItemView view;

        FootPrintItemViewClickEvent(FootPrintItemView view) {
            this.view = view;
        }

        @Override
        public void onClick(View v) {
            if (v == null) return;
            startActivity(new Intent(getActivity(), ImageAlbumActivity.class));
        }
    }

}
