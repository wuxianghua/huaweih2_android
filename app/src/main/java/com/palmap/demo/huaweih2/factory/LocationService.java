package com.palmap.demo.huaweih2.factory;

import com.palmap.demo.huaweih2.model.LocationInfoModel;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wtm on 2017/1/3.
 * 使用华为基站定位api
 */
public interface LocationService {

    @GET("https://api.ipalmap.com/pos")
    Observable<LocationInfoModel> requestLocation(
            @Query("appKey") String appKey,
            @Query("idType") String idType,
            @Query("idData") String idData);
}
