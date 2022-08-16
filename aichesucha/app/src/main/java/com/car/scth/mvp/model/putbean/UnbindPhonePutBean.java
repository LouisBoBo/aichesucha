package com.car.scth.mvp.model.putbean;

import java.util.List;

/**
 * 解除绑定手机号码
 */
public class UnbindPhonePutBean {

    /**
     * module : device
     * func : UnBindPhone
     * params : {"simei":["2d762843-64a1-4dfe-8450-1e6e450c1c7a","c4fd60b4-1b86-4de0-9069-55543507fc8f"]}
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
        private List<String> simei;

        public List<String> getSimei() {
            return simei;
        }

        public void setSimei(List<String> simei) {
            this.simei = simei;
        }
    }
}
