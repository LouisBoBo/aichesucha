package com.car.scth.mvp.model.putbean;

/**
 * sim卡充值套餐获取
 */
public class RechargePackagesPutBean {

    /**
     * params : {"simei":"c6e49b33-237f-4e3f-a607-2e1c256fe75f"}
     * func : QueryPackages
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
         * simei : c6e49b33-237f-4e3f-a607-2e1c256fe75f
         */

        private String simei; // 设备的simei号
        private String iccid; // 设备的iccid号

        public String getSimei() {
            return simei;
        }

        public void setSimei(String simei) {
            this.simei = simei;
        }

        public String getIccid() {
            return iccid;
        }

        public void setIccid(String iccid) {
            this.iccid = iccid;
        }
    }
}
