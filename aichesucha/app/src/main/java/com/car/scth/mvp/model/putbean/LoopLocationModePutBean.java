package com.car.scth.mvp.model.putbean;

/**
 * 获取周期定位列表
 */
public class LoopLocationModePutBean {

    /**
     * module : loc
     * func : GetLoopLoc
     * params : {"simei":"e329986c-aad7-4068-8e3e-6b9dc63c2cd9","limit_size":1410271673,"last_slid":"877d217f-f82a-4118-989c-a0fb29fe75b4"}
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
         * simei : e329986c-aad7-4068-8e3e-6b9dc63c2cd9
         * limit_size : 1410271673
         * last_slid : 877d217f-f82a-4118-989c-a0fb29fe75b4
         */

        private String simei;
        private int limit_size; // 显示条数,非必填，默认20
        private String last_slid; // 瀑布流上一条数据id,非必填,默认从第一条数据开始拉取

        public String getSimei() {
            return simei;
        }

        public void setSimei(String simei) {
            this.simei = simei;
        }

        public int getLimit_size() {
            return limit_size;
        }

        public void setLimit_size(int limit_size) {
            this.limit_size = limit_size;
        }

        public String getLast_slid() {
            return last_slid;
        }

        public void setLast_slid(String last_slid) {
            this.last_slid = last_slid;
        }
    }
}
