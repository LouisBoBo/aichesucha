package com.car.scth.mvp.model.putbean;

/**
 * 添加车组
 */
public class GroupAddPutBean {

    /**
     * module : family
     * func : AddDevGroup
     * params : {"group_name":"5780a1f8-e2df-4212-b268-9c36a580d75b","familyid":"06108698-6a1f-4d5e-bc06-2360942bb995"}
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
         * group_name : 5780a1f8-e2df-4212-b268-9c36a580d75b
         * familyid : 06108698-6a1f-4d5e-bc06-2360942bb995
         */

        private String group_name; // 车组名称
        private String familyid;

        public String getGroup_name() {
            return group_name;
        }

        public void setGroup_name(String group_name) {
            this.group_name = group_name;
        }

        public String getFamilyid() {
            return familyid;
        }

        public void setFamilyid(String familyid) {
            this.familyid = familyid;
        }
    }
}
