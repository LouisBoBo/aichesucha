package com.car.scth.mvp.model.bean;

import java.io.Serializable;

/**
 * 报警消息列表筛选条件-设备列表
 */
public class AlarmScreenDeviceBean implements Serializable {

    private long imei;
    private String strImei; // 设备号
    private String simei;
    private String name; // 设备名称
    private String version; // 设备版本号
    private String locInfo; // 设备定位信息

    public AlarmScreenDeviceBean(long imei, String strImei, String simei, String name, String version, String locInfo){
        super();
        this.imei = imei;
        this.strImei = strImei;
        this.simei = simei;
        this.name = name;
        this.version = version;
        this.locInfo = locInfo;
    }

    public String getStrImei() {
        return strImei;
    }

    public void setStrImei(String strImei) {
        this.strImei = strImei;
    }

    public long getImei() {
        return imei;
    }

    public void setImei(long imei) {
        this.imei = imei;
    }

    public String getSimei() {
        return simei;
    }

    public void setSimei(String simei) {
        this.simei = simei;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLocInfo() {
        return locInfo;
    }

    public void setLocInfo(String locInfo) {
        this.locInfo = locInfo;
    }
}
