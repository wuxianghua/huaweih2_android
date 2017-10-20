package com.palmap.huawei.rx;

import com.mapbox.services.commons.geojson.FeatureCollection;
import com.palmap.huawei.http.GetCarParkingInfoService;
import com.palmap.huawei.mode.CarParkingInfo;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/10/10/010.
 */

public class RxCarParkStatus {

    private static RequestCarParkStatusOnSubscribe requestCarParkStatusOnSubscribe;
    /**
     * 请求停车数据
     */
    public static Observable<List<CarParkingInfo>> requestCarParkStatus(GetCarParkingInfoService getCarParkingInfoService) {
        if (requestCarParkStatusOnSubscribe == null) {
            requestCarParkStatusOnSubscribe = new RequestCarParkStatusOnSubscribe(getCarParkingInfoService);
        }
        return Observable.create(requestCarParkStatusOnSubscribe);
    }
}
