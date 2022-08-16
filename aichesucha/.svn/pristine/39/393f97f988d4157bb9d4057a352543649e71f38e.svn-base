package com.car.scth.mvp.model.putbean;

/**
 * 删除报警消息
 */
public class AlarmDeletePutBean {

    /**
     * module : alarm
     * func : Del
     * params : {"type":"e_alarm_min|e_alarm_shake|e_alarm_speed|e_alarm_illegal_open|e_alarm_illegal_start|e_alarm_out_fence|e_alarm_in_fence|e_alarm_light_off|e_alarm_lowpower|e_alarm_sensitivity|e_alarm_power_cut_off|e_alarm_acc_off|e_alarm_dismantle|e_alarm_line_dismantle|e_alarm_recovery_light|e_alarm_off_line|e_alarm_satellite|e_alarm_start_up|e_alarm_close_down|e_alarm_displacement|e_alarm_pseudo_base_station|e_alarm_speeding_end|e_alarm_start_dev|e_alarm_stop_dev|e_alarm_remove_lowpower|e_alarm_remove_out|e_alarm_sos|e_alarm_max","time":416574198,"simei":"0b00c235-7b30-4442-a424-8f0cbbd5a01d"}
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
         * type : e_alarm_min|e_alarm_shake|e_alarm_speed|e_alarm_illegal_open|e_alarm_illegal_start|e_alarm_out_fence|e_alarm_in_fence|e_alarm_light_off|e_alarm_lowpower|e_alarm_sensitivity|e_alarm_power_cut_off|e_alarm_acc_off|e_alarm_dismantle|e_alarm_line_dismantle|e_alarm_recovery_light|e_alarm_off_line|e_alarm_satellite|e_alarm_start_up|e_alarm_close_down|e_alarm_displacement|e_alarm_pseudo_base_station|e_alarm_speeding_end|e_alarm_start_dev|e_alarm_stop_dev|e_alarm_remove_lowpower|e_alarm_remove_out|e_alarm_sos|e_alarm_max
         * time : 416574198
         * simei : 0b00c235-7b30-4442-a424-8f0cbbd5a01d
         */

        private String type; // 报警类型,注意，必填
        private Long time; // 需要删除的时间
        private String simei; // 设备simei号，如果设备登入，可以不填

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Long getTime() {
            return time;
        }

        public void setTime(Long time) {
            this.time = time;
        }

        public String getSimei() {
            return simei;
        }

        public void setSimei(String simei) {
            this.simei = simei;
        }
    }
}
