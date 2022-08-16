package com.car.scth.mvp.model.putbean;

/**
 * 下发实时追踪
 */
public class RealTimeTrackPutBean {

    /**
     * module : location
     * func : Tracking
     * params : {"simei":"f882155b-c922-42ad-81be-0dbd6aa2ea8c","interval_time":373611024,"effective_time":373611024}
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
         * simei : f882155b-c922-42ad-81be-0dbd6aa2ea8c
         * interval_time : 373611024
         * effective_time : 373611024
         */

        private String simei;
        private int interval_time; // 跟踪控制的设备上报时间间隔 0-取消追踪，单位s
        private int effective_time; // 位置跟踪有效期，单位min
        private boolean is_add_log; // 由于会循环调用，这里在首次进入页面的时候，设置为true，后面就设置为false，避免产生多条操作日志

        public String getSimei() {
            return simei;
        }

        public void setSimei(String simei) {
            this.simei = simei;
        }

        public int getInterval_time() {
            return interval_time;
        }

        public void setInterval_time(int interval_time) {
            this.interval_time = interval_time;
        }

        public int getEffective_time() {
            return effective_time;
        }

        public void setEffective_time(int effective_time) {
            this.effective_time = effective_time;
        }

        public boolean isIs_add_log() {
            return is_add_log;
        }

        public void setIs_add_log(boolean is_add_log) {
            this.is_add_log = is_add_log;
        }
    }
}
