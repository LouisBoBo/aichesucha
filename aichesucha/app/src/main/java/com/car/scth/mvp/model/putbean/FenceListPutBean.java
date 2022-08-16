package com.car.scth.mvp.model.putbean;

/**
 * 获取围栏列表
 */
public class FenceListPutBean {

    /**
     * module : fence
     * func : Get
     * params : {"simei":"f73327ed-b8b8-4875-87f4-0965035c5af4","limit_size":1589498272,"last_sfid":"b57f87c0-7a54-409f-908a-89c1ff54e808"}
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
         * simei : f73327ed-b8b8-4875-87f4-0965035c5af4
         * limit_size : 1589498272
         * last_sfid : b57f87c0-7a54-409f-908a-89c1ff54e808
         */

        private String simei; // 设备号,如果设备号登入，可以不用填写
        private int limit_size; // 显示条数,非必填，默认20
        private String last_sfid; // 瀑布流上一条数据id,非必填,默认从第一条数据开始拉取

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

        public String getLast_sfid() {
            return last_sfid;
        }

        public void setLast_sfid(String last_sfid) {
            this.last_sfid = last_sfid;
        }
    }
}
