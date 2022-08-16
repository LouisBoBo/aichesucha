package com.car.scth.mvp.model.bean;

/**
 * 切换设备图标
 */
public class IconCheckBean {

    private int id;
    private int drawableId;
    private String name;
    private boolean isSelect;

    public IconCheckBean(int id, int drawableId, String name, boolean select){
        super();
        this.id = id;
        this.drawableId = drawableId;
        this.name = name;
        this.isSelect = select;
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

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
