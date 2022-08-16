package com.car.scth.mvp.model.putbean;

public class EditFamilyPutBean {
    /**
     * {
     * "module": "family",
     * "func": "SetFamilyName",
     * "params": {
     * "name": "6d97007f-1f2e-4f34-9594-e2e971fbbb23",
     * "familyid": "32181e06-2992-4fcc-afc8-848ee0832f77"
     * }
     * }
     */

    private String module;
    private String func;
    private EditFamilyPutBean.ParamsBean params;

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
        private String name;
        private String familyid;

        public ParamsBean() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFamilyid() {
            return familyid;
        }

        public void setFamilyid(String familyid) {
            this.familyid = familyid;
        }
    }

}
