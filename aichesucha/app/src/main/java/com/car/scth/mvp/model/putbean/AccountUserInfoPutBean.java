package com.car.scth.mvp.model.putbean;

/**
 * 获取用户账号下 账号列表
 */
public class AccountUserInfoPutBean {

    /**
     * {
     *   "module": "user",
     *   "func": "GetAccountList",
     *   "params": {
     *     "limit_size": 122753570,
     *     "last_uid": "3e9981c2-7b07-4eed-9322-6bb7720524ea",
     *     "familyid": "dd3cd4a1-0296-4d6e-a875-529789923bf8"
     *   }
     * }
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
         * limit_size : 1100977578
         * last_uid : 48833f35-0027-4222-b230-4277766669f1
         * familyid : 4e1d6ac9-2935-4306-b4e0-df93f32335d2
         */

        private int limit_size; // 限制获取数量
        private String last_gid; // 最后获取到的gid，没有可以为空
        private String last_uid; // 最后获取到的uid，没有可以为空
        private String familyid; // 用户组织id,为空表示最上一级

        public int getLimit_size() {
            return limit_size;
        }

        public void setLimit_size(int limit_size) {
            this.limit_size = limit_size;
        }

        public String getLast_gid() {
            return last_gid;
        }

        public void setLast_gid(String last_gid) {
            this.last_gid = last_gid;
        }

        public String getLast_uid() {
            return last_uid;
        }

        public void setLast_uid(String last_fid) {
            this.last_uid = last_uid;
        }

        public String getFamilyid() {
            return familyid;
        }

        public void setFamilyid(String familyid) {
            this.familyid = familyid;
        }
    }
}
