package com.car.scth.mvp.model.putbean;

import java.util.List;

/**
 * 删除分组和设备
 */
public class DeleteDevicePutBean {

    /**
     * module : device
     * func : DelFromFamily
     * params : {"simei":["c6eff5d9-27e7-42de-a1b2-85e0a13e7bfd","a642ebdc-42e8-443c-b681-343921c9ec22"],"sgid":"87523803-6c6b-4abc-9899-b5b2b0e899b2","sfamilyid":"41a2fdab-d09b-4494-a6af-d9d2d27dc0aa","is_recycle":false,"is_del_gid":false}
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
         * simei : ["c6eff5d9-27e7-42de-a1b2-85e0a13e7bfd","a642ebdc-42e8-443c-b681-343921c9ec22"]
         * sgid : 87523803-6c6b-4abc-9899-b5b2b0e899b2
         * sfamilyid : 41a2fdab-d09b-4494-a6af-d9d2d27dc0aa
         * is_recycle : false
         * is_del_gid : false
         */

        private String sgid; // 需要删除指定分组下面全部设备，simei,sfamilyid,sgid三选一
        private String sfamilyid; // 需要删除指定车组织下面全部设备，simei,sfamilyid,sgid三选一
        private boolean is_recycle; // 如果是回收站的设备用sfamilyid或者sgid删除，这里需要设备为true，默认为false
        private boolean is_del_gid; // 如果用sgid删除设备，如果这里填写true，则最后会把gid也删除，默认为false，不删除gid
        private List<String> simei; // 设备号,限制数量1000，simei,sfamilyid,sgid三选一

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

        public boolean isIs_recycle() {
            return is_recycle;
        }

        public void setIs_recycle(boolean is_recycle) {
            this.is_recycle = is_recycle;
        }

        public boolean isIs_del_gid() {
            return is_del_gid;
        }

        public void setIs_del_gid(boolean is_del_gid) {
            this.is_del_gid = is_del_gid;
        }

        public List<String> getSimei() {
            return simei;
        }

        public void setSimei(List<String> simei) {
            this.simei = simei;
        }
    }
}
