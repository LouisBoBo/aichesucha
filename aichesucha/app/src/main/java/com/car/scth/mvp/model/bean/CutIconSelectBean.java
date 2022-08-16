package com.car.scth.mvp.model.bean;

/**
 * 切换图标bean，存储单个设备选择切换的icon
 */
public class CutIconSelectBean {

    private long imei; // 用于匹配设备
    private String iconId; // 用于显示对于id的图标

    public CutIconSelectBean(long imei, String iconId){
        super();
        this.imei = imei;
        this.iconId = iconId;
    }

    public long getImei() {
        return imei;
    }

    public void setImei(long imei) {
        this.imei = imei;
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }
}
