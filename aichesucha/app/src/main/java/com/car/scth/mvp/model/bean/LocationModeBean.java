package com.car.scth.mvp.model.bean;

/**
 * 定位模式bean
 */
public class LocationModeBean {

    private String mode; // 定位模式:e_mode_nomal|e_mode_looploc|e_mode_fly|e_mode_sup_save_power|e_mode_auto_save_power|e_mode_sleep|e_mode_save_power|e_mode_call_one|e_mode_sup_save_power_c2
    private String modeName; // 模式名称
    private String description; // 定位模式说明
    private boolean isSelect; // 是否选中

    public LocationModeBean(String mode, String name, String description, boolean select){
        super();
        this.mode = mode;
        this.modeName = name;
        this.description = description;
        this.isSelect = select;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getModeName() {
        return modeName;
    }

    public void setModeName(String modeName) {
        this.modeName = modeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
