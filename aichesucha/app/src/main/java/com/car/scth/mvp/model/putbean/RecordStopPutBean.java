package com.car.scth.mvp.model.putbean;

/**
 * 停止录音功能
 */
public class RecordStopPutBean {

    /**
     * module : record
     * func : StopRecord
     * params : {"simei":"3291ab16-9ae7-4df5-9211-b9c0efb99d11"}
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
         * simei : 3291ab16-9ae7-4df5-9211-b9c0efb99d11
         */

        private String simei;

        public String getSimei() {
            return simei;
        }

        public void setSimei(String simei) {
            this.simei = simei;
        }
    }
}
