package com.car.scth.mvp.model.putbean;

import java.util.List;

/**
 * 设置定时开关机
 */
public class TimeSwitchSetPutBean {

    /**
     * module : timerswitch
     * func : Set
     * params : {"loc_type":"e_loctype_eachday|e_loctype_currentmonth|e_loctype_eachmonth","loop_days":[914708948,914708948,914708948],"simei":"ad026cfd-b9a7-4a87-8d3f-2903a082de46","timer":[{"close":"ecba2c3a-3580-4d4e-8806-d7dbc85f9de2","start":"19e99d74-379f-42a8-bcd4-bf0b7e5c6c70"},{"close":"e706a748-ea98-49ef-8acf-0ac46b5afa8b","start":"cde1b4a0-d2d1-4cb1-9726-61432a8620c4"},{"close":"fbd6a36d-226e-4377-bfa6-ae6d5e6f3416","start":"7e23e2ff-f3b5-4f58-9b13-121e27d03a22"},{"close":"8b338c69-4188-404c-8d31-4ba4ffb5cd83","start":"9448d9a4-1ee7-43ec-8a03-e7598dca2dea"}]}
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
         * loc_type : e_loctype_eachday|e_loctype_currentmonth|e_loctype_eachmonth
         * loop_days : [914708948,914708948,914708948]
         * simei : ad026cfd-b9a7-4a87-8d3f-2903a082de46
         * timer : [{"close":"ecba2c3a-3580-4d4e-8806-d7dbc85f9de2","start":"19e99d74-379f-42a8-bcd4-bf0b7e5c6c70"},{"close":"e706a748-ea98-49ef-8acf-0ac46b5afa8b","start":"cde1b4a0-d2d1-4cb1-9726-61432a8620c4"},{"close":"fbd6a36d-226e-4377-bfa6-ae6d5e6f3416","start":"7e23e2ff-f3b5-4f58-9b13-121e27d03a22"},{"close":"8b338c69-4188-404c-8d31-4ba4ffb5cd83","start":"9448d9a4-1ee7-43ec-8a03-e7598dca2dea"}]
         */

        //定时模式,如果是设置，就选填，如果是添加，就必填                                                                                            │
        //│                  │e_loctype_eachday                                                                                                                          │
        //│                  │e_loctype_currentmonth                                                                                                                     │
        //│                  │e_loctype_eachmonth
        private String loc_type;
        private String simei; // 设备号,如果设备号登入，可以不用填写
        private List<Integer> loop_days; // 模式,如果是设置，就选填，如果是添加，就必填,如果模式为e_loctype_eachmonth或e_loctype_currentmonth时,上传该参数 例如:1 2 13 25 31 范围(1-31)│
        private List<TimerBean> timer; // 定时开关时间对,如果是设置，就选填，如果是添加，就必填

        public String getLoc_type() {
            return loc_type;
        }

        public void setLoc_type(String loc_type) {
            this.loc_type = loc_type;
        }

        public String getSimei() {
            return simei;
        }

        public void setSimei(String simei) {
            this.simei = simei;
        }

        public List<Integer> getLoop_days() {
            return loop_days;
        }

        public void setLoop_days(List<Integer> loop_days) {
            this.loop_days = loop_days;
        }

        public List<TimerBean> getTimer() {
            return timer;
        }

        public void setTimer(List<TimerBean> timer) {
            this.timer = timer;
        }

        public static class TimerBean {
            /**
             * close : ecba2c3a-3580-4d4e-8806-d7dbc85f9de2
             * start : 19e99d74-379f-42a8-bcd4-bf0b7e5c6c70
             */

            private String close; // 关机时间，格式: HH:MM
            private String start; // 开机时间，格式为: HH:MM

            public String getClose() {
                return close;
            }

            public void setClose(String close) {
                this.close = close;
            }

            public String getStart() {
                return start;
            }

            public void setStart(String start) {
                this.start = start;
            }
        }
    }
}
