package com.car.scth.mvp.model.putbean;

import java.util.List;

/**
 * 添加周期定位
 */
public class LoopModeAddPutBean {

    /**
     * module : loc
     * func : AddLoopLoc
     * params : {"simei":"13c9c2bb-c264-4b6e-8a78-67cea85bbf49","item":{"rp_loop_loc":[1541298353,1541298353],"loop_loc_name":"47558113-7e77-414c-a97e-cb9c03d458dc","loc_value":"0e7ee6d4-b6b0-45fb-9078-81f36c895dfb","loc_type":"e_type_week|e_type_day","loc_switch":false}}
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
         * simei : 13c9c2bb-c264-4b6e-8a78-67cea85bbf49
         * item : {"rp_loop_loc":[1541298353,1541298353],"loop_loc_name":"47558113-7e77-414c-a97e-cb9c03d458dc","loc_value":"0e7ee6d4-b6b0-45fb-9078-81f36c895dfb","loc_type":"e_type_week|e_type_day","loc_switch":false}
         */

        private String simei;
        private ItemBean item;

        public String getSimei() {
            return simei;
        }

        public void setSimei(String simei) {
            this.simei = simei;
        }

        public ItemBean getItem() {
            return item;
        }

        public void setItem(ItemBean item) {
            this.item = item;
        }

        public static class ItemBean {
            /**
             * rp_loop_loc : [1541298353,1541298353]
             * loop_loc_name : 47558113-7e77-414c-a97e-cb9c03d458dc
             * loc_value : 0e7ee6d4-b6b0-45fb-9078-81f36c895dfb
             * loc_type : e_type_week|e_type_day
             * loc_switch : false
             */

            private String loop_loc_name;  // 周期定位名称
            private String loc_value; // 周期定位数值
            private boolean loc_switch; // 周期定位开关
            private List<Integer> rp_loop_loc; // 周期定位星期

            public String getLoop_loc_name() {
                return loop_loc_name;
            }

            public void setLoop_loc_name(String loop_loc_name) {
                this.loop_loc_name = loop_loc_name;
            }

            public String getLoc_value() {
                return loc_value;
            }

            public void setLoc_value(String loc_value) {
                this.loc_value = loc_value;
            }

            public boolean isLoc_switch() {
                return loc_switch;
            }

            public void setLoc_switch(boolean loc_switch) {
                this.loc_switch = loc_switch;
            }

            public List<Integer> getRp_loop_loc() {
                return rp_loop_loc;
            }

            public void setRp_loop_loc(List<Integer> rp_loop_loc) {
                this.rp_loop_loc = rp_loop_loc;
            }
        }
    }
}
