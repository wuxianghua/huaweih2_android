package com.palmap.huawei.rx;

import android.util.Log;

import com.mapbox.services.commons.geojson.Feature;
import com.mapbox.services.commons.geojson.FeatureCollection;
import com.palmap.demo.huaweih2.HuaWeiH2Application;
import com.palmap.huawei.http.GetCarParkingInfoService;
import com.palmap.huawei.config.Constant;
import com.palmap.huawei.mode.CarParkingInfo;
import com.palmap.huawei.mode.CarParkingInfos;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/10/10/010.
 */

public class RequestCarParkStatusOnSubscribe implements ObservableOnSubscribe<List<CarParkingInfo>> {

    private static final String TAG = RequestCarParkStatusOnSubscribe.class.getSimpleName();
    private GetCarParkingInfoService mGetCarParkingInfoService;

    public RequestCarParkStatusOnSubscribe(GetCarParkingInfoService getCarParkingInfoService) {
        mGetCarParkingInfoService = getCarParkingInfoService;
    }


    @Override
    public void subscribe(@NonNull final ObservableEmitter<List<CarParkingInfo>> e) throws Exception {
        Call<CarParkingInfos> carParkingStatus = mGetCarParkingInfoService.getCarParkingStatus(Constant.appkey, Constant.mapId, Constant.floorId);
        carParkingStatus.enqueue(new Callback<CarParkingInfos>() {
            @Override
            public void onResponse(Call<CarParkingInfos> call, Response<CarParkingInfos> response) {
                List<CarParkingInfo> carportInfos = response.body().carportInfos;
                if (carportInfos == null || carportInfos.size() == 0) return;
                e.onNext(carportInfos);
                e.onComplete();
            }

            @Override
            public void onFailure(Call<CarParkingInfos> call, Throwable t) {
                Log.e(TAG,"数据获取失败");
            }
        });
    }
}
