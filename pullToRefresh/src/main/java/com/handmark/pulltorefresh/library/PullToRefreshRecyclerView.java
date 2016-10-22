package com.handmark.pulltorefresh.library;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by 王天明 on 2015/11/4 0004.
 * 瀑布流PullToRefreshRecyclerView
 */
public class PullToRefreshRecyclerView extends PullToRefreshBase<RecyclerView> {

    public PullToRefreshRecyclerView(Context context) {
        super(context);
    }

    public PullToRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    private int offset = 10;

    @Override
    public Orientation getPullToRefreshScrollDirection() {
        if (mRefreshableView != null) {
            RecyclerView.LayoutManager layoutManager = mRefreshableView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                int orientation = linearLayoutManager.getOrientation();
                if (orientation == LinearLayoutManager.HORIZONTAL) {
                    return Orientation.HORIZONTAL;
                } else {
                    return Orientation.VERTICAL;
                }
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                int orientation = staggeredGridLayoutManager.getOrientation();
                if (orientation == StaggeredGridLayoutManager.HORIZONTAL) {
                    return Orientation.HORIZONTAL;
                } else {
                    return Orientation.VERTICAL;
                }
            }
        }
        return Orientation.VERTICAL;
    }

    @Override
    protected RecyclerView createRefreshableView(Context context, AttributeSet attrs) {
        RecyclerView recyclerView = new RecyclerView(context, attrs);
        recyclerView.setId(R.id.pulltorefresh_recyclerview);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof StaggeredGridLayoutManager) {
                    dispatchStaggeredGridScrollEvent((StaggeredGridLayoutManager) layoutManager, recyclerView, dx, dy);
                } else if (layoutManager instanceof LinearLayoutManager) {
                    dispatchLinearLayoutrScrollEvent((LinearLayoutManager) layoutManager, recyclerView, dx, dy);
                } else {
                    Log.d("WTM", "the layoutManager is error");
                }
            }
        });

        return recyclerView;
    }

    private void dispatchLinearLayoutrScrollEvent(LinearLayoutManager layoutManager, RecyclerView recyclerView, int dx, int dy) {


    }


    private void dispatchStaggeredGridScrollEvent(StaggeredGridLayoutManager layoutManager, RecyclerView recyclerView, int dx, int dy) {
        if (layoutManager.getOrientation() == StaggeredGridLayoutManager.VERTICAL) {
//            int[] posEnd = layoutManager.findLastVisibleItemPositions(null);
//            int endPositions = posEnd[posEnd.length - 1];
 //           int dataCount = recyclerView.getAdapter().getItemCount();
/*            if (endPositions >= dataCount - 1) {
                View endChildView = layoutManager.findViewByPosition(dataCount - 1);
                if (endChildView != null) {
                    Log.d("WTM", "Y:" + ViewCompat.getY(endChildView));
                    Log.d("WTM", "H:" + endChildView.getHeight());
                    float endY = ViewCompat.getY(endChildView);
                    int endH = endChildView.getHeight();
                    float max = endY + endH;
                    ViewGroup viewGroup = (ViewGroup) endChildView.getParent();
                    int parentH = viewGroup.getHeight();
                    Log.d("WTM", "S:" + max);
                    isReadyEnd = max + offset == parentH;
//                    Log.d("WTM", "PraentH:" + viewGroup.getHeight());
//                    Log.d("WTM", "getScaleY:" + ViewCompat.getScaleY(mRefreshableView));
//                    isReadyEnd = (ViewCompat.getY(endChildView) + endChildView.getHeight()) == viewGroup.getHeight();
//                    Log.d("WTM", "b:" + isReadyEnd);
                    //isReadyEnd = ViewCompat.getY(endChildView) <= 0;
                }
            } else {
                isReadyEnd = false;
            }*/
            isReadyEnd = !getRefreshableView().canScrollVertically(1);
            isReadyTop = !getRefreshableView().canScrollVertically(-1);


//            int[] posFirst = layoutManager.findFirstVisibleItemPositions(null);
//            int firstPositions = posFirst[0];
//            if (firstPositions == 0) {
//                //获取第一个位置的子view 如果子view的Y为0 就表示真正滚动到了顶部！
//                View topChildView = layoutManager.getChildAt(0);
//                if (null != topChildView) {
//                    isReadyTop = 0 == ViewCompat.getY(topChildView);
//                }
//            } else {
//                isReadyTop = false;
//            }
//            if (firstPositions > endPositions) {//如果可见的大于不可见的 认为是已经到了底部了
//                isReadyEnd = true;
//            }
        }
    }

    private boolean isReadyTop = false;
    private boolean isReadyEnd = false;

    @Override
    protected boolean isReadyForPullEnd() {
//        float exactContentHeight = FloatMath.floor(mRefreshableView.getLayoutManager().getHeight());
//        return mRefreshableView.getScrollY() >= (exactContentHeight - mRefreshableView.getHeight());
        return isReadyEnd;
    }

    @Override
    protected boolean isReadyForPullStart() {
//        return mRefreshableView.getScrollY() == 0;
        return isReadyTop;
    }
}
