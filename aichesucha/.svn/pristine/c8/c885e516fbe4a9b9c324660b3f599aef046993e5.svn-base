package com.car.scth.mvp.model.putbean;

public class MileageStatisticsPutBean {
    /**
     * {
     *   "module": "summary",
     *   "func": "DeviceSimple",
     *   "params": {
     *     "time_end": 1011791002,
     *     "time_begin": 1011791002,
     *     "simei": "38d880ca-f1f7-434a-85dc-65608104ea0f" // 设备号登录 可不传
     *   }
     * }
     */

    private String module;
    private String func;
    private MileageStatisticsPutBean.ParamsBean params;

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
       private int time_begin;
       private int time_end;
        private String simei; // 设备号,如果设备号登入，可以不用填写

        public int getTime_begin() {
            return time_begin;
        }

        public void setTime_begin(int time_begin) {
            this.time_begin = time_begin;
        }

        public int getTime_end() {
            return time_end;
        }

        public void setTime_end(int time_end) {
            this.time_end = time_end;
        }

        public String getSimei() {
            return simei;
        }

        public void setSimei(String simei) {
            this.simei = simei;
        }
    }
}
