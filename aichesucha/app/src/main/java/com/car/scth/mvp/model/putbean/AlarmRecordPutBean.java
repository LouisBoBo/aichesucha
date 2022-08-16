package com.car.scth.mvp.model.putbean;

import java.util.List;

/**
 * 报警消息列表
 */
public class AlarmRecordPutBean {

    /**
     * module : alarm
     * func : Get
     * params : {"limit_size":100506007,"simei":["33b9ca97-251d-4d7e-b94c-f7e7d4861c8a","d3aafd67-537d-4d29-9d5a-4fa8d0e09ae7"],"sfamilyid":"4b4d3def-26cf-449b-9e30-220893410e8c","type":["e_alarm_min|e_alarm_shake|e_alarm_speed|e_alarm_illegal_open|e_alarm_illegal_start|e_alarm_out_fence|e_alarm_in_fence|e_alarm_light_off|e_alarm_lowpower|e_alarm_sensitivity|e_alarm_power_cut_off|e_alarm_acc_off|e_alarm_dismantle|e_alarm_line_dismantle|e_alarm_recovery_light|e_alarm_off_line|e_alarm_satellite|e_alarm_start_up|e_alarm_close_down|e_alarm_displacement|e_alarm_pseudo_base_station|e_alarm_speeding_end|e_alarm_start_dev|e_alarm_stop_dev|e_alarm_remove_lowpower|e_alarm_remove_out|e_alarm_sos|e_alarm_max","e_alarm_min|e_alarm_shake|e_alarm_speed|e_alarm_illegal_open|e_alarm_illegal_start|e_alarm_out_fence|e_alarm_in_fence|e_alarm_light_off|e_alarm_lowpower|e_alarm_sensitivity|e_alarm_power_cut_off|e_alarm_acc_off|e_alarm_dismantle|e_alarm_line_dismantle|e_alarm_recovery_light|e_alarm_off_line|e_alarm_satellite|e_alarm_start_up|e_alarm_close_down|e_alarm_displacement|e_alarm_pseudo_base_station|e_alarm_speeding_end|e_alarm_start_dev|e_alarm_stop_dev|e_alarm_remove_lowpower|e_alarm_remove_out|e_alarm_sos|e_alarm_max"],"start_time":100506007,"end_time":100506007,"last_imei":100506007,"last_time":100506007,"last_type":"e_alarm_min|e_alarm_shake|e_alarm_speed|e_alarm_illegal_open|e_alarm_illegal_start|e_alarm_out_fence|e_alarm_in_fence|e_alarm_light_off|e_alarm_lowpower|e_alarm_sensitivity|e_alarm_power_cut_off|e_alarm_acc_off|e_alarm_dismantle|e_alarm_line_dismantle|e_alarm_recovery_light|e_alarm_off_line|e_alarm_satellite|e_alarm_start_up|e_alarm_close_down|e_alarm_displacement|e_alarm_pseudo_base_station|e_alarm_speeding_end|e_alarm_start_dev|e_alarm_stop_dev|e_alarm_remove_lowpower|e_alarm_remove_out|e_alarm_sos|e_alarm_max"}
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
         * limit_size : 100506007
         * simei : ["33b9ca97-251d-4d7e-b94c-f7e7d4861c8a","d3aafd67-537d-4d29-9d5a-4fa8d0e09ae7"]
         * sfamilyid : 4b4d3def-26cf-449b-9e30-220893410e8c
         * type : ["e_alarm_min|e_alarm_shake|e_alarm_speed|e_alarm_illegal_open|e_alarm_illegal_start|e_alarm_out_fence|e_alarm_in_fence|e_alarm_light_off|e_alarm_lowpower|e_alarm_sensitivity|e_alarm_power_cut_off|e_alarm_acc_off|e_alarm_dismantle|e_alarm_line_dismantle|e_alarm_recovery_light|e_alarm_off_line|e_alarm_satellite|e_alarm_start_up|e_alarm_close_down|e_alarm_displacement|e_alarm_pseudo_base_station|e_alarm_speeding_end|e_alarm_start_dev|e_alarm_stop_dev|e_alarm_remove_lowpower|e_alarm_remove_out|e_alarm_sos|e_alarm_max","e_alarm_min|e_alarm_shake|e_alarm_speed|e_alarm_illegal_open|e_alarm_illegal_start|e_alarm_out_fence|e_alarm_in_fence|e_alarm_light_off|e_alarm_lowpower|e_alarm_sensitivity|e_alarm_power_cut_off|e_alarm_acc_off|e_alarm_dismantle|e_alarm_line_dismantle|e_alarm_recovery_light|e_alarm_off_line|e_alarm_satellite|e_alarm_start_up|e_alarm_close_down|e_alarm_displacement|e_alarm_pseudo_base_station|e_alarm_speeding_end|e_alarm_start_dev|e_alarm_stop_dev|e_alarm_remove_lowpower|e_alarm_remove_out|e_alarm_sos|e_alarm_max"]
         * start_time : 100506007
         * end_time : 100506007
         * last_imei : 100506007
         * last_time : 100506007
         * last_type : e_alarm_min|e_alarm_shake|e_alarm_speed|e_alarm_illegal_open|e_alarm_illegal_start|e_alarm_out_fence|e_alarm_in_fence|e_alarm_light_off|e_alarm_lowpower|e_alarm_sensitivity|e_alarm_power_cut_off|e_alarm_acc_off|e_alarm_dismantle|e_alarm_line_dismantle|e_alarm_recovery_light|e_alarm_off_line|e_alarm_satellite|e_alarm_start_up|e_alarm_close_down|e_alarm_displacement|e_alarm_pseudo_base_station|e_alarm_speeding_end|e_alarm_start_dev|e_alarm_stop_dev|e_alarm_remove_lowpower|e_alarm_remove_out|e_alarm_sos|e_alarm_max
         */

        private int limit_size; // 数据的限制条数，默认20条,最高不超过100
        private String sfamilyid; // 需要拉取的车组id,如果设备登入，不填
        private Integer start_time; // 筛选开始时间
        private Integer end_time; // 筛选结束时间
        private Long last_imei; // 上次请求返回的最后一个的imei，首次请求可以不填
        private Long last_time; // 上次请求返回的最后一个的时间，首次请求可以不填
        private String last_type; // 上次请求返回的最后一个的类型，首次请求可以不填
        private List<String> simei; // 设备列表,选填，如果设备登入不填,注意只能筛选同一个sfamilyid下的设备
        private List<String> type; // 需要过滤的类型，可以不用填写

        public int getLimit_size() {
            return limit_size;
        }

        public void setLimit_size(int limit_size) {
            this.limit_size = limit_size;
        }

        public String getSfamilyid() {
            return sfamilyid;
        }

        public void setSfamilyid(String sfamilyid) {
            this.sfamilyid = sfamilyid;
        }

        public Integer getStart_time() {
            return start_time;
        }

        public void setStart_time(Integer start_time) {
            this.start_time = start_time;
        }

        public Integer getEnd_time() {
            return end_time;
        }

        public void setEnd_time(Integer end_time) {
            this.end_time = end_time;
        }

        public Long getLast_imei() {
            return last_imei;
        }

        public void setLast_imei(Long last_imei) {
            this.last_imei = last_imei;
        }

        public Long getLast_time() {
            return last_time;
        }

        public void setLast_time(Long last_time) {
            this.last_time = last_time;
        }

        public String getLast_type() {
            return last_type;
        }

        public void setLast_type(String last_type) {
            this.last_type = last_type;
        }

        public List<String> getSimei() {
            return simei;
        }

        public void setSimei(List<String> simei) {
            this.simei = simei;
        }

        public List<String> getType() {
            return type;
        }

        public void setType(List<String> type) {
            this.type = type;
        }
    }
}
