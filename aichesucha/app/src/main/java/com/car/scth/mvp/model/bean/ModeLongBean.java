package com.car.scth.mvp.model.bean;

/**
 * 定位模式设置，省电模式，30或者60
 * Created by Administrator on 2019\5\17 0017.
 */

public class ModeLongBean {

    private int id;
    private int mode_long; // 省电时长
    private String showLong; // 显示时长

    public ModeLongBean(int id, int mode_long, String showLong){
        super();
        this.id = id;
        this.mode_long = mode_long;
        this.showLong = showLong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMode_long() {
        return mode_long;
    }

    public void setMode_long(int mode_long) {
        this.mode_long = mode_long;
    }

    public String getShowLong() {
        return showLong;
    }

    public void setShowLong(String showLong) {
        this.showLong = showLong;
    }
}
