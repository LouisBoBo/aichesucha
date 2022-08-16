package com.car.scth.mvp.model.putbean;

import java.util.List;

/**
 * 冻结设备
 */
public class FreezeEquipmentPutBean {

    /**
     * module : device
     * func : Del
     * params : {"sfamilyid":"e2027d7b-c260-43f8-8601-41fd861c96d5","sgid":"00bdeafb-9ccf-45f3-aed9-c899518b3b31","simei":["98a6d4e8-618a-46ea-a6e1-e76acb3efae5","60c16d02-1524-430c-ad75-5a540f12ef0d"]}
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
         * sfamilyid : e2027d7b-c260-43f8-8601-41fd861c96d5
         * sgid : 00bdeafb-9ccf-45f3-aed9-c899518b3b31
         * simei : ["98a6d4e8-618a-46ea-a6e1-e76acb3efae5","60c16d02-1524-430c-ad75-5a540f12ef0d"]
         */

        private String sfamilyid; // 需要删除指定车组织下面全部设备，simei,sfamilyid,sgid三选一
        private String sgid; // 需要删除指定分组下面全部设备，simei,sfamilyid,sgid三选一
        private List<String> simei; // 设备号,限制数量1000，simei,sfamilyid,sgid三选一

        public String getSfamilyid() {
            return sfamilyid;
        }

        public void setSfamilyid(String sfamilyid) {
            this.sfamilyid = sfamilyid;
        }

        public String getSgid() {
            return sgid;
        }

        public void setSgid(String sgid) {
            this.sgid = sgid;
        }

        public List<String> getSimei() {
            return simei;
        }

        public void setSimei(List<String> simei) {
            this.simei = simei;
        }
    }
}
