package com.car.scth.mvp.model.putbean;

/**
 * 版本更新
 */
public class CheckAppUpdatePutBean {

    /**
     * module : user
     * func : CheckVersion
     * params : {"version":1825469593}
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
         * version : 1825469593
         */

        private int version; // app版本号
        private String app_type;// app 区分代号

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public String getApp_type() {
            return app_type;
        }

        public void setApp_type(String app_type) {
            this.app_type = app_type;
        }
    }
}
