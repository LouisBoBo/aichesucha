package com.car.scth.mvp.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 围栏信息修改bean
 */
public class FenceInfoModifyBean implements Serializable {

    private String sfid; // 围栏id
    private String type; // 围栏类型
    private String fenceAlarmType; // 围栏报警类型，进围栏，出围栏，进出围栏，关闭
    private String name; // 围栏名称
    // 圆形围栏信息
    private int radius; // 半径
    private double lon; // 圆心的经度，原值*1000000
    private double lat; // 圆心的纬度，原值*1000000
    // 地级市围栏信息
    private String cityName; // 城市名称
    private String districtName; // 城市-区名称
    // 多边形围栏信息
    private List<FencePointBean> pointBeans; // 多边形围栏经纬度信息

    public String getSfid() {
        return sfid;
    }

    public void setSfid(String sfid) {
        this.sfid = sfid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFenceAlarmType() {
        return fenceAlarmType;
    }

    public void setFenceAlarmType(String fenceAlarmType) {
        this.fenceAlarmType = fenceAlarmType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public List<FencePointBean> getPointBeans() {
        return pointBeans;
    }

    public void setPointBeans(List<FencePointBean> pointBeans) {
        this.pointBeans = pointBeans;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
