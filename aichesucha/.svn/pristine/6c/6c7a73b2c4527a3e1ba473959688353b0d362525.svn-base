package com.car.scth.mvp.model.putbean;

/**
 * 回收站列表
 */
public class RecycleBinPutBean {

    /**
     * module : device
     * func : GetRecycle
     * params : {"last_sgid":"974ff126-1824-4464-a4b4-18fe027965b1","last_simei":"6ca1055b-d217-4747-838d-ad1c37962da5","limit_size":1392522321,"sfamily":"2285dc8d-3cfe-4117-b8dc-49b88da69498"}
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
         * last_sgid : 974ff126-1824-4464-a4b4-18fe027965b1
         * last_simei : 6ca1055b-d217-4747-838d-ad1c37962da5
         * limit_size : 1392522321
         * sfamily : 2285dc8d-3cfe-4117-b8dc-49b88da69498
         */

        private String last_sgid; // 上一次请求的最后一个sgid
        private String last_simei; // 上一次请求的最后一个simei
        private int limit_size; // 限制条数，不填默认20条
        private String sfamily; // 需要查询车组织的id，设备登入无法请求
        private Long last_recovery_stat; // 上一次请求的最后一个冻结时间

        public String getLast_sgid() {
            return last_sgid;
        }

        public void setLast_sgid(String last_sgid) {
            this.last_sgid = last_sgid;
        }

        public String getLast_simei() {
            return last_simei;
        }

        public void setLast_simei(String last_simei) {
            this.last_simei = last_simei;
        }

        public int getLimit_size() {
            return limit_size;
        }

        public void setLimit_size(int limit_size) {
            this.limit_size = limit_size;
        }

        public String getSfamily() {
            return sfamily;
        }

        public void setSfamily(String sfamily) {
            this.sfamily = sfamily;
        }

        public Long getLast_recovery_stat() {
            return last_recovery_stat;
        }

        public void setLast_recovery_stat(Long last_recovery_stat) {
            this.last_recovery_stat = last_recovery_stat;
        }
    }
}
