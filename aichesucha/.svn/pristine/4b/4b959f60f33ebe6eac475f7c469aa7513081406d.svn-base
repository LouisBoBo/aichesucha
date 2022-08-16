package com.car.scth.mvp.model.putbean;

/**
 * 定位模式设置bean
 */
public class DeviceModeSetPutBean {

    /**
     * module : loc
     * func : SetLocMode
     * params : {"simei":"9e50d772-be3b-443b-b99c-69494ed1aab7","mode_value":1956036983,"mode_type":1956036983,"mode":"e_mode_nomal|e_mode_looploc|e_mode_fly|e_mode_sup_save_power|e_mode_auto_save_power|e_mode_sleep|e_mode_save_power|e_mode_call_one|e_mode_sup_save_power_c2"}
     */

    private String module;
    private String func;
    private ParamsBean params;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getFunc() {
        return func;
    }

    public void setFunc(String func) {
        this.func = func;
    }

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public static class ParamsBean {
        /**
         * simei : 9e50d772-be3b-443b-b99c-69494ed1aab7
         * mode_value : 1956036983
         * mode_type : 1956036983
         * mode : e_mode_nomal|e_mode_looploc|e_mode_fly|e_mode_sup_save_power|e_mode_auto_save_power|e_mode_sleep|e_mode_save_power|e_mode_call_one|e_mode_sup_save_power_c2
         */

        private String simei; // 设备号,如果设备号登入，可以不用填写
        private String smode_value; // 待机模式设置的时间 格式 HH:MM 飞行模式定位间隔
        private Integer mode_type; // 值,例如点名模式 定位间隔 飞行模式开关 0-关闭 1-打开
        private String mode; // 定位模式

        public String getSimei() {
            return simei;
        }

        public void setSimei(String simei) {
            this.simei = simei;
        }

        public String getSmode_value() {
            return smode_value;
        }

        public void setSmode_value(String smode_value) {
            this.smode_value = smode_value;
        }

        public Integer getMode_type() {
            return mode_type;
        }

        public void setMode_type(Integer mode_type) {
            this.mode_type = mode_type;
        }

        public String getMode() {
            return mode;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }
    }
}
