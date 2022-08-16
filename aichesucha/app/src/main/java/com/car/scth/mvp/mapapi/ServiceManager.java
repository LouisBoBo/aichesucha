package com.car.scth.mvp.mapapi;

import retrofit2.Retrofit;
import com.car.scth.app.MyApplication;

/**
 * 网络服务管理器
 */
public class ServiceManager {

    /**
     * 获取地图类网络请求管理器
     * @return
     */
    private static Retrofit getNetworkMapManager(){
        return NetworkMapManager.getInstance(MyApplication.getMyApp());
    }


    public static MapService getMapService(){
        return getNetworkMapManager().create(MapService.class);
    }

}
