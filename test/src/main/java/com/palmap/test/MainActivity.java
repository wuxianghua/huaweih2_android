package com.palmap.test;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshRecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements PullToRefreshBase.OnRefreshListener2{

    private PullToRefreshRecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (PullToRefreshRecyclerView) findViewById(R.id.recyclerView);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setReverseLayout(false);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.getRefreshableView().setLayoutManager(layoutManager);

        recyclerView.setMode(PullToRefreshBase.Mode.BOTH);
        recyclerView.setOnRefreshListener(this);
        recyclerView.getRefreshableView().addItemDecoration(new SpacesItemDecoration(10));

        ArrayList<String> urlData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            urlData.add("http://7xnnph.com1.z0.glb.clouddn.com/11385343fbf2b2112cf911efce8065380dd78eb8.jpg");
            urlData.add("http://7xnnph.com1.z0.glb.clouddn.com/item1.jpg");
            urlData.add("http://7xnnph.com1.z0.glb.clouddn.com/048d22df9ae1da23f60a6578b1af0080");

        }
        recyclerView.getRefreshableView().setAdapter(new ImageAlbumAdapter(this,urlData));

    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        refreshView.onRefreshComplete();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        refreshView.onRefreshComplete();
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
        }
    }
}
