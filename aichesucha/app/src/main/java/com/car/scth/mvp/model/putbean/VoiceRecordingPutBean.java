package com.car.scth.mvp.model.putbean;

import com.google.gson.annotations.SerializedName;

/**
 * 开启/关闭声控录音
 */
public class VoiceRecordingPutBean {

    /**
     * module : record
     * func : SetVorSwitch
     * params : {"switch":true,"simei":"79cca755-114a-41f9-9bce-c57b7cf1a11a"}
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
         * switch : true
         * simei : 79cca755-114a-41f9-9bce-c57b7cf1a11a
         */

        @SerializedName("switch")
        private boolean switchX; // 开关
        private String simei;

        public boolean isSwitchX() {
            return switchX;
        }

        public void setSwitchX(boolean switchX) {
            this.switchX = switchX;
        }

        public String getSimei() {
            return simei;
        }

        public void setSimei(String simei) {
            this.simei = simei;
        }
    }
}
