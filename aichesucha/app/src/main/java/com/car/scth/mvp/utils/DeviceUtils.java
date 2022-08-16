package com.car.scth.mvp.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.car.scth.mvp.model.bean.DeviceLocationInfoBean;

/**
 * 设备数据解析工具类
 */
public class DeviceUtils {

    /**
     * 解析设备更多信息
     * @param locInfo
     * @return
     */
    public static DeviceLocationInfoBean parseDeviceData(String locInfo){
        DeviceLocationInfoBean bean;
        if (TextUtils.isEmpty(locInfo)){
            bean = new DeviceLocationInfoBean();
        }else{
            bean = new Gson().fromJson(locInfo,
                    DeviceLocationInfoBean.class);
        }
        return bean;
    }

}
