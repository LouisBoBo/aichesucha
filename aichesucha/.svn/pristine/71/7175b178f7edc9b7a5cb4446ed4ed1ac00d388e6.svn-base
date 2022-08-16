package com.car.scth.mvp.model.putbean;

import java.util.List;

/**
 * 模糊，精准搜索设备
 */
public class FindDevicePutBean {

    /**
     * module : device
     * func : FindDev
     * params : {"imei":"61483c68-825b-4137-b72c-92dccf9ac066","imeis":["4f8728dd-8c15-4292-9e5a-6f72ebf1c6d4","246c9896-0ea9-43a0-9d7a-ba28d5508f20"],"last_imei":1694292985,"limit_size":1694292985,"recovery":false,"sfid":"94201e89-58c5-44b2-ae9a-eb25b1f7bbd9","sgid":"bd83883d-b2ad-43d7-b27c-f79b82af7fca"}
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
         * imei : 61483c68-825b-4137-b72c-92dccf9ac066
         * imeis : ["4f8728dd-8c15-4292-9e5a-6f72ebf1c6d4","246c9896-0ea9-43a0-9d7a-ba28d5508f20"]
         * last_imei : 1694292985
         * limit_size : 1694292985
         * recovery : false
         * sfid : 94201e89-58c5-44b2-ae9a-eb25b1f7bbd9
         * sgid : bd83883d-b2ad-43d7-b27c-f79b82af7fca
         */

        private String imei; // 模糊设备号/设备名称
        private Long last_imei; // 上一次调用该接口返回的最后一条imei
        private int limit_size; // 限制个数，默认10个，越多，速度越慢
        private Boolean recovery; // 只搜索回收站设备，默认fasle，搜索全部
        private String sfid; // 指定的组织id，也可以不指定，全范围搜索
        private String sgid; // 指定的分组id，也可以不指定，全范围搜索
        private List<String> imeis; // 指定查找的imei号，传入imei号列表

        public String getImei() {
            return imei;
        }

        public void setImei(String imei) {
            this.imei = imei;
        }

        public Long getLast_imei() {
            return last_imei;
        }

        public void setLast_imei(Long last_imei) {
            this.last_imei = last_imei;
        }

        public int getLimit_size() {
            return limit_size;
        }

        public void setLimit_size(int limit_size) {
            this.limit_size = limit_size;
        }

        public Boolean getRecovery() {
            return recovery;
        }

        public void setRecovery(Boolean recovery) {
            this.recovery = recovery;
        }

        public String getSfid() {
            return sfid;
        }

        public void setSfid(String sfid) {
            this.sfid = sfid;
        }

        public String getSgid() {
            return sgid;
        }

        public void setSgid(String sgid) {
            this.sgid = sgid;
        }

        public List<String> getImeis() {
            return imeis;
        }

        public void setImeis(List<String> imeis) {
            this.imeis = imeis;
        }
    }
}
