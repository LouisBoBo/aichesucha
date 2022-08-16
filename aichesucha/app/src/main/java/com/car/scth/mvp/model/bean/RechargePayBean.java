package com.car.scth.mvp.model.bean;

/**
 * 支付类型bean
 */
public class RechargePayBean {

    private int id; // 0：微信支付，1：支付宝支付
    private String name;
    private int drawableId;
    private boolean isSelect; // 是否选中

    public RechargePayBean(int id, String name, int drawableId, boolean isSelect){
        super();
        this.id = id;
        this.name = name;
        this.drawableId = drawableId;
        this.isSelect = isSelect;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
