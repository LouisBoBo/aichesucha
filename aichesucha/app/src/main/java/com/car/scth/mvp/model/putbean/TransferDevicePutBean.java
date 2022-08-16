package com.car.scth.mvp.model.putbean;

import java.util.List;

/**
 * 转移设备
 */
public class TransferDevicePutBean {

    /**
     * module : device
     * func : MoveGarage
     * params : {"simei":["c7797e9f-5ecf-4a62-bbb3-46a63bc145df","efc7959a-7ec7-411c-8c6d-6b030fa9e948"],"sgid_new":"fd1fa8cd-14dc-4962-badc-79e35813228d","sgid":"5c93f525-a2b3-43d8-b9e4-0ded34cf6889","sfamilyid":"a6dfa917-aacf-4404-8763-1b4b98074430"}
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
         * simei : ["c7797e9f-5ecf-4a62-bbb3-46a63bc145df","efc7959a-7ec7-411c-8c6d-6b030fa9e948"]
         * sgid_new : fd1fa8cd-14dc-4962-badc-79e35813228d
         * sgid : 5c93f525-a2b3-43d8-b9e4-0ded34cf6889
         * sfamilyid : a6dfa917-aacf-4404-8763-1b4b98074430
         */

        private String sgid_new; // 指定的车组
        private String sgid; // 需要转移指定分组下面全部设备，simei,sfamilyid,sgid三选一
        private String sfamilyid; // 需要转移指定车组织下面全部设备，simei,sfamilyid,sgid三选一
        private List<String> simei; // 设备号

        public String getSgid_new() {
            return sgid_new;
        }

        public void setSgid_new(String sgid_new) {
            this.sgid_new = sgid_new;
        }

        public String getSgid() {
            return sgid;
        }

        public void setSgid(String sgid) {
            this.sgid = sgid;
        }

        public String getSfamilyid() {
            return sfamilyid;
        }

        public void setSfamilyid(String sfamilyid) {
            this.sfamilyid = sfamilyid;
        }

        public List<String> getSimei() {
            return simei;
        }

        public void setSimei(List<String> simei) {
            this.simei = simei;
        }
    }
}
