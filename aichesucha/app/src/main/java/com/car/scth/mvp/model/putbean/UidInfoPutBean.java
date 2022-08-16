package com.car.scth.mvp.model.putbean;

/**
 * 获取单用户信息
 */
public class UidInfoPutBean {

    /**
     * module : user
     * func : GetUidInfo
     * params : {"uid":"56532443-8ccb-496a-ad74-ebcee92c4264"}
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
         * uid : 56532443-8ccb-496a-ad74-ebcee92c4264
         */

        private String uid; // 获取账号相关信息，如果是当前账号，可以不传

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
