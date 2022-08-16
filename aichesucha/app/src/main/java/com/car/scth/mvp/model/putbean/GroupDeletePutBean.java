package com.car.scth.mvp.model.putbean;

/**
 * 删除分组
 */
public class GroupDeletePutBean {

    /**
     * module : family
     * func : DelDevGroup
     * params : {"gid":"3f3f0ac2-73fc-401b-b9c6-eb8f3b0fe795"}
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
         * gid : 3f3f0ac2-73fc-401b-b9c6-eb8f3b0fe795
         */

        private String gid;  // 车组id

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }
    }
}
