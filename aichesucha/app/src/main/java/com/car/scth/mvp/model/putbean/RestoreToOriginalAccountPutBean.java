package com.car.scth.mvp.model.putbean;

import java.util.List;

/**
 * 回收站-恢复设备至原账号
 */
public class RestoreToOriginalAccountPutBean {

    /**
     * module : device
     * func : ReSumeRecycle
     * params : {"sfamilyid":"1fa0e59b-6d3b-4a9d-af5a-a2d36766a42a","simei":["d8f0d7a3-bbd7-44be-ad9b-77abdb1f16c1","a9dbff91-1286-44d3-9bee-db2291965791","9603f0b3-e4e7-47f9-b2c5-b4573d4fd168"]}
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
         * sfamilyid : 1fa0e59b-6d3b-4a9d-af5a-a2d36766a42a
         * simei : ["d8f0d7a3-bbd7-44be-ad9b-77abdb1f16c1","a9dbff91-1286-44d3-9bee-db2291965791","9603f0b3-e4e7-47f9-b2c5-b4573d4fd168"]
         */

        private String sfamilyid; // 需要恢复指定车组织下面全部设备，simei,sfamilyid二选一
        private List<String> simei; // 设备号,限制数量1000，simei,sfamilyid二选一
        private String sgid; // 需要恢复到指定的分组中,sgid与simei必须在同一个组织中

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

        public String getSgid() {
            return sgid;
        }

        public void setSgid(String sgid) {
            this.sgid = sgid;
        }
    }
}
