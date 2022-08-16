package com.car.scth.mvp.model.putbean;

public class OilSetPutBean {
    /**
     * {
     * "module": "device",
     * "func": "SetRelaySwitch",
     * "params": {
     * "simei": "3f5b607e-cca6-4482-9998-c104ca8d7470",
     * "replay_switch": false,
     * "relay_pwd_md5": "63756804-b968-4bb6-acab-51e2b3f4d4ac",
     * "new_relay_pwd": "1e3e18b2-c118-4b16-9ae4-ce406d8c6f0b"
     * }
     * }
     */

    private String module;
    private String func;
    private OilSetPutBean.ParamsBean params;

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
        private String simei;
        private boolean replay_switch;
        private String pwd_md5;

        public String getSimei() {
            return simei;
        }

        public void setSimei(String simei) {
            this.simei = simei;
        }

        public boolean isReplay_switch() {
            return replay_switch;
        }

        public void setReplay_switch(boolean replay_switch) {
            this.replay_switch = replay_switch;
        }

        public String getPwd_md5() {
            return pwd_md5;
        }

        public void setPwd_md5(String pwd_md5) {
            this.pwd_md5 = pwd_md5;
        }
    }


}
