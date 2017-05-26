package com.palmap.demo.huaweih2.repo;


import com.palmap.demo.huaweih2.model.LocationInfoModel;

/**
 * Created by wtm on 2017/1/3.
 */

public interface LocationListener {

    void onComplete(LocationInfoModel locationInfoModel, long timeStamp);

    void onFailed(Exception ex, String msg);

}
