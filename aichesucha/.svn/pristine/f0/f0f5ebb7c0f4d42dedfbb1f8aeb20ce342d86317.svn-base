package com.car.scth.mvp.model.putbean;

/**
 * 获取用户列表
 */
public class AccountListPutBean {

    /**
     * module : user
     * func : GetAccountList
     * params : {"familyid":"e46d473c-d78e-4a1b-a715-fb8b49589a83","last_uid":"e3e22089-c658-4d0b-9070-5af3eff8f15c","limit_size":704972224,"role":"e_role_user|e_role_manager"}
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
         * familyid : e46d473c-d78e-4a1b-a715-fb8b49589a83
         * last_uid : e3e22089-c658-4d0b-9070-5af3eff8f15c
         * limit_size : 704972224
         * role : e_role_user|e_role_manager
         */

        private String familyid; // 用户组织id
        private String last_uid; // 获取到的最后的用户uid
        private int limit_size; // 限制获取数量
        private String role; // 用户类型,e_role_user 普通用户权限,e_role_manager 管理员权限

        public String getFamilyid() {
            return familyid;
        }

        public void setFamilyid(String familyid) {
            this.familyid = familyid;
        }

        public String getLast_uid() {
            return last_uid;
        }

        public void setLast_uid(String last_uid) {
            this.last_uid = last_uid;
        }

        public int getLimit_size() {
            return limit_size;
        }

        public void setLimit_size(int limit_size) {
            this.limit_size = limit_size;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }
}
