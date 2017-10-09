package com.palmap.indoor;

import android.content.Context;

import com.palmap.indoor.impl.MapBoxMapViewController;

/**
 * Created by wtm on 2017/9/30.
 */

public final class MapViewControllerFactory {

    public static IMapViewController createByMapBox(Context context){
        return new MapBoxMapViewController(context);
    }
}
