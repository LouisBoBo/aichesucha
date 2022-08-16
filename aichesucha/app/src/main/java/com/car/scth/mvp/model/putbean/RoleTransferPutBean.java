package com.car.scth.mvp.model.putbean;

/**
 * 转移权限
 */
public class RoleTransferPutBean {

    /**
     * module : user
     * func : SwitchAuth
     * params : {"old_uid":"f15de963-acef-4a7e-b8a6-7bb8e5e7dbbe","uid":"c6030975-f3e2-44e1-abd2-dd4df0c6acf3"}
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
         * old_uid : f15de963-acef-4a7e-b8a6-7bb8e5e7dbbe
         * uid : c6030975-f3e2-44e1-abd2-dd4df0c6acf3
         */

        private String old_uid; // 旧用户
        private String uid; // 新用户

        public String getOld_uid() {
            return old_uid;
        }

        public void setOld_uid(String old_uid) {
            this.old_uid = old_uid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
