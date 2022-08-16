package com.car.scth.mvp.model.bean;

/**
 * 周期定位-日期选择
 */
public class LoopDayBean {

    private int day; // 01234567，对应周日 - 周六
    private String dayName; // 名称
    private boolean isSelect; // 是否选中

    public LoopDayBean(int day, String dayName, boolean select){
        super();
        this.day = day;
        this.dayName =dayName;
        this.isSelect = select;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
