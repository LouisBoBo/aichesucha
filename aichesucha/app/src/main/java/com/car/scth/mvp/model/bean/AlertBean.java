package com.car.scth.mvp.model.bean;

/**
 * 提示警告bean
 * Created by Administrator on 2019\6\21 0021.
 */

public class AlertBean {

    private String title = ""; // 标题
    private String alert = ""; // 提示警告语
    private String alertSmall = ""; // 副提示语，可选显示
    private int type = 0; // 提示类型，用于判断底部按钮显示或隐藏，按钮显示文字

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAlertSmall() {
        return alertSmall;
    }

    public void setAlertSmall(String alertSmall) {
        this.alertSmall = alertSmall;
    }

}
