package com.car.scth.mvp.utils;

import android.content.Context;
import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

/**
 * 广播工具类
 */
public class BroadcastReceiverUtil {

    /**
     * 显示首页某个模块，请求某个功能
     *
     * @param context
     * @param page    功能类型
     */
    public static void showMainPage(Context context, int page) {
        Intent intent = new Intent();
        intent.setAction("main");
        intent.putExtra("page", page);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    /**
     * 显示首页报警模块，请求报警数据
     *
     * @param context
     * @param type    功能类型
     */
    public static void showAlarmMessagePage(Context context, int type) {
        Intent intent = new Intent();
        intent.setAction("alarm_message");
        intent.putExtra("type", type);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    /**
     * 定位页不可见的时候，发送广播，停止循环刷新设备数据
     *
     * @param context
     * @param type 类型，后续扩展
     */
    public static void onDeviceDetailRunStop(Context context, int type) {
        Intent intent = new Intent();
        intent.setAction("device_run");
        intent.putExtra("type", type);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    /**
     * 定位页不可见的时候，发送广播，停止循环刷新设备数据
     *
     * @param context
     */
    public static void showFunctionPage(Context context) {
        Intent intent = new Intent();
        intent.setAction("function");
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

}
