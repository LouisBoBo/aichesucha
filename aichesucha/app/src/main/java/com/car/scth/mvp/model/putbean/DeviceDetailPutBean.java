package com.car.scth.mvp.model.putbean;

/**
 * 获取设备详细信息
 */
public class DeviceDetailPutBean {

    /**
     * module : device
     * func : GetDetail
     * params : {"simei":"abe0fece-6125-4ab5-beec-b83efcaa44dd"}
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
         * simei : abe0fece-6125-4ab5-beec-b83efcaa44dd
         */

        private String simei; // 加密的imei号

        public String getSimei() {
            return simei;
        }

        public void setSimei(String simei) {
            this.simei = simei;
        }
    }
}
