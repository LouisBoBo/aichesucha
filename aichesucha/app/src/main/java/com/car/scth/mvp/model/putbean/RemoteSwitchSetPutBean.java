package com.car.scth.mvp.model.putbean;

/**
 * 远程开关机设置
 */
public class RemoteSwitchSetPutBean {

    /**
     * params : {"simei":"34166d93-5bda-4d19-9fcf-d1f6ffa6487c","state":"e_remote_close|e_remote_open"}
     * func : RemoteSwitch
     * module : sim
     */

    private ParamsBean params;
    private String func;
    private String module;

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public String getFunc() {
        return func;
    }

    public void setFunc(String func) {
        this.func = func;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public static class ParamsBean {
        /**
         * simei : 34166d93-5bda-4d19-9fcf-d1f6ffa6487c
         * state : e_remote_close|e_remote_open
         */

        private String simei; // 加密的imei号
        private String state; // e_remote_close关机 e_remote_open开机

        public String getSimei() {
            return simei;
        }

        public void setSimei(String simei) {
            this.simei = simei;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
