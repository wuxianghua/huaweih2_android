package com.palmap.indoor;

import android.os.Bundle;
import android.view.ViewGroup;

/**
 * Created by wtm on 2017/9/30.
 */

public interface IMapViewController {

    static interface Action{
        void onAction();
    }

    static interface onSingTapListener{
        void onAction(double x,double y);
    }

    void attachMap(ViewGroup root, Bundle savedInstanceState,Action action);

    void drawPlanarGraph(String mapDataPath);

    void resetNorth(long animTime);

    void selectFeature(double x,double y);

    void setOnSingTapListener(onSingTapListener l);

    void onStart();

    void onStop();

    void onResume();

    void onPause();

    void onSaveInstanceState(Bundle outState);

    void onDestroy();

    void onLowMemory();

}
