package com.car.scth.mvp.model.bean;

import com.car.scth.mvp.model.entity.BaseBean;

import java.util.List;

/**
 * 获取定时开关机数据结果
 */
public class TimeSwitchGetResultBean extends BaseBean {

    /**
     * loc_type : e_loctype_eachday|e_loctype_currentmonth|e_loctype_eachmonth|e_loctype_close
     * loop_days : [469787060]
     * timer : [{"close":"68410112-087b-44a0-9c06-fca2605656f7","start":"bb564774-b13a-4b7e-9cf6-84a2e96a157f"}]
     */

    // 定时模式                    │
    //│             │e_loctype_eachday           │
    //│             │e_loctype_currentmonth      │
    //│             │e_loctype_eachmonth         │
    //│             │e_loctype_close
    private String loc_type;
    private List<Integer> loop_days; // 周期定位日期
    private List<TimerBean> timer; // 周期定位每日的时间

    public String getLoc_type() {
        return loc_type;
    }

    public void setLoc_type(String loc_type) {
        this.loc_type = loc_type;
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
         * close : 68410112-087b-44a0-9c06-fca2605656f7
         * start : bb564774-b13a-4b7e-9cf6-84a2e96a157f
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
