package com.car.scth.mvp.model.putbean;

/**
 * 获取手机号码国际区号
 */
public class PhoneAreaPutBean {

    /**
     * module : user
     * func : GetPhoneArea
     * params : {"last_country":"3fd2a299-5a6f-4c77-acca-bc85dc93604e"}
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
         * last_country : 3fd2a299-5a6f-4c77-acca-bc85dc93604e
         */

        private String last_country; // 上次获取到的最后的last_country

        public String getLast_country() {
            return last_country;
        }

        public void setLast_country(String last_country) {
            this.last_country = last_country;
        }
    }
}
