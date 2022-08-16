package com.car.scth.mvp.model.bean;

import com.car.scth.mvp.model.entity.BaseBean;

import java.util.List;

/**
 * 获取设备定位模式结果
 */
public class LocationModeGetResultBean extends BaseBean {

    /**
     * mode : e_mode_nomal|e_mode_looploc|e_mode_fly|e_mode_sup_save_power|e_mode_auto_save_power|e_mode_sleep|e_mode_save_power|e_mode_call_one|e_mode_sup_save_power_c2
     * mode_fun : ["e_mode_nomal|e_mode_looploc|e_mode_fly|e_mode_sup_save_power|e_mode_auto_save_power|e_mode_sleep|e_mode_save_power|e_mode_call_one|e_mode_sup_save_power_c2","e_mode_nomal|e_mode_looploc|e_mode_fly|e_mode_sup_save_power|e_mode_auto_save_power|e_mode_sleep|e_mode_save_power|e_mode_call_one|e_mode_sup_save_power_c2","e_mode_nomal|e_mode_looploc|e_mode_fly|e_mode_sup_save_power|e_mode_auto_save_power|e_mode_sleep|e_mode_save_power|e_mode_call_one|e_mode_sup_save_power_c2"]
     * mode_type : 1184676163
     * save_power : [{"name":"7527c14e-b8c5-40a4-9af3-8a39c9f196eb","time":1184676163},{"name":"4a339a24-7e9f-4561-9219-598a94c778d0","time":1184676163}]
     * smode_value : bab61f5b-3b55-462c-b685-723af776c1b2
     */

    private String mode; // 定位模式
    private int mode_type; // 值,例如点名模式 定位间隔 飞行模式开关 0-关闭 1-打开
    private String smode_value; // 待机模式设置的时间 格式 HH:MM 飞行模式定位间隔
    private List<String> mode_fun; // 支持的定位模式
    private List<SavePowerBean> save_power; // 省电模式下可选的时间

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getMode_type() {
        return mode_type;
    }

    public void setMode_type(int mode_type) {
        this.mode_type = mode_type;
    }

    public String getSmode_value() {
        return smode_value;
    }

    public void setSmode_value(String smode_value) {
        this.smode_value = smode_value;
    }

    public List<String> getMode_fun() {
        return mode_fun;
    }

    public void setMode_fun(List<String> mode_fun) {
        this.mode_fun = mode_fun;
    }

    public List<SavePowerBean> getSave_power() {
        return save_power;
    }

    public void setSave_power(List<SavePowerBean> save_power) {
        this.save_power = save_power;
    }

    public static class SavePowerBean {
        /**
         * name : 7527c14e-b8c5-40a4-9af3-8a39c9f196eb
         * time : 1184676163
         */

        private String name; // 名称
        private int time; // 时间

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }
    }
}
