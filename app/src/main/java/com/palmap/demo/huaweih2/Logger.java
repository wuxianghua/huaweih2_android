package com.palmap.demo.huaweih2;

import android.content.Context;
import android.content.Intent;

import static com.palmap.demo.huaweih2.LocateTimerService.ACTION_LocateTimerService;
import static com.palmap.demo.huaweih2.LocateTimerService.DATA_MSG;

/**
 * Created by 王天明 on 2017/5/26.
 */
public class Logger {
    public static void dumpLog(Context context, String msg){
//        Intent intent = new Intent();
//        intent.setAction(ACTION_LocateTimerService);
//        intent.putExtra(DATA_MSG, msg);
//        context.sendBroadcast(intent);
    }

    public static void dumpLog2(Context context, String msg){
        Intent intent = new Intent();
        intent.setAction(ACTION_LocateTimerService);
        intent.putExtra(DATA_MSG, msg);
        context.sendBroadcast(intent);
    }
}
