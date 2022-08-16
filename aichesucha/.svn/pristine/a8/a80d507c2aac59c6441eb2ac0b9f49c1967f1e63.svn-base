package com.car.scth.mvp.model.bean;

import com.car.scth.mvp.model.entity.BaseBean;

import java.util.List;

/**
 * 报警消息列表返回结果bean
 */
public class AlarmRecordResultBean extends BaseBean {

    /**
     * items : [{"addr":"9e848f5a-66be-4224-954f-2ab08a2abd67","devspeed":559853919,"fencename":"1ada7110-f469-4b32-b1af-fa97897c3757","imei":559853919,"number":"9f389935-0af3-4f68-b2fe-2701b87257f8","simei":"72033f45-babb-4b71-9323-f4ad95033868","time":559853919,"type":"e_alarm_min|e_alarm_shake|e_alarm_speed|e_alarm_illegal_open|e_alarm_illegal_start|e_alarm_out_fence|e_alarm_in_fence|e_alarm_light_off|e_alarm_lowpower|e_alarm_sensitivity|e_alarm_power_cut_off|e_alarm_acc_off|e_alarm_dismantle|e_alarm_line_dismantle|e_alarm_recovery_light|e_alarm_off_line|e_alarm_satellite|e_alarm_start_up|e_alarm_close_down|e_alarm_displacement|e_alarm_pseudo_base_station|e_alarm_speeding_end|e_alarm_start_dev|e_alarm_stop_dev|e_alarm_remove_lowpower|e_alarm_remove_out|e_alarm_sos|e_alarm_max"},{"addr":"cb2b718d-4485-4808-8fcd-cb1e5aa2ba13","devspeed":559853919,"fencename":"449c5be5-c9df-48e3-aee2-7e6c4c921ba0","imei":559853919,"number":"8fe4b6f0-06b1-4d64-9a17-770fa29b73fb","simei":"245c34db-1607-4b0d-8cc7-3440dcfe4727","time":559853919,"type":"e_alarm_min|e_alarm_shake|e_alarm_speed|e_alarm_illegal_open|e_alarm_illegal_start|e_alarm_out_fence|e_alarm_in_fence|e_alarm_light_off|e_alarm_lowpower|e_alarm_sensitivity|e_alarm_power_cut_off|e_alarm_acc_off|e_alarm_dismantle|e_alarm_line_dismantle|e_alarm_recovery_light|e_alarm_off_line|e_alarm_satellite|e_alarm_start_up|e_alarm_close_down|e_alarm_displacement|e_alarm_pseudo_base_station|e_alarm_speeding_end|e_alarm_start_dev|e_alarm_stop_dev|e_alarm_remove_lowpower|e_alarm_remove_out|e_alarm_sos|e_alarm_max"}]
     * last_imei : 559853919
     * last_time : 559853919
     * last_type : e_alarm_min|e_alarm_shake|e_alarm_speed|e_alarm_illegal_open|e_alarm_illegal_start|e_alarm_out_fence|e_alarm_in_fence|e_alarm_light_off|e_alarm_lowpower|e_alarm_sensitivity|e_alarm_power_cut_off|e_alarm_acc_off|e_alarm_dismantle|e_alarm_line_dismantle|e_alarm_recovery_light|e_alarm_off_line|e_alarm_satellite|e_alarm_start_up|e_alarm_close_down|e_alarm_displacement|e_alarm_pseudo_base_station|e_alarm_speeding_end|e_alarm_start_dev|e_alarm_stop_dev|e_alarm_remove_lowpower|e_alarm_remove_out|e_alarm_sos|e_alarm_max
     * errcode : 0
     */

    private long last_imei; // 本次请求返回的最后一个的imei，注意可能与items返回的最后一条数据不一样
    private long last_time; // 本次请求返回的最后一个的时间，注意可能与items返回的最后一条数据不一样
    private String last_type; // 本次请求返回的最后一个的类型，注意可能与items返回的最后一条数据不一样
    private List<ItemsBean> items;

    public long getLast_imei() {
        return last_imei;
    }

    public void setLast_imei(long last_imei) {
        this.last_imei = last_imei;
    }

    public long getLast_time() {
        return last_time;
    }

    public void setLast_time(long last_time) {
        this.last_time = last_time;
    }

    public String getLast_type() {
        return last_type;
    }

    public void setLast_type(String last_type) {
        this.last_type = last_type;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * addr : 9e848f5a-66be-4224-954f-2ab08a2abd67
         * devspeed : 559853919
         * fencename : 1ada7110-f469-4b32-b1af-fa97897c3757
         * imei : 559853919
         * number : 9f389935-0af3-4f68-b2fe-2701b87257f8
         * simei : 72033f45-babb-4b71-9323-f4ad95033868
         * time : 559853919
         * type : e_alarm_min|e_alarm_shake|e_alarm_speed|e_alarm_illegal_open|e_alarm_illegal_start|e_alarm_out_fence|e_alarm_in_fence|e_alarm_light_off|e_alarm_lowpower|e_alarm_sensitivity|e_alarm_power_cut_off|e_alarm_acc_off|e_alarm_dismantle|e_alarm_line_dismantle|e_alarm_recovery_light|e_alarm_off_line|e_alarm_satellite|e_alarm_start_up|e_alarm_close_down|e_alarm_displacement|e_alarm_pseudo_base_station|e_alarm_speeding_end|e_alarm_start_dev|e_alarm_stop_dev|e_alarm_remove_lowpower|e_alarm_remove_out|e_alarm_sos|e_alarm_max
         */

        private String addr; // 高德解析地址，<addr lat="123" lon="123"><ch time="222">地址</ch></addr>
        private int devspeed; // 速度
        private String fencename; // 围栏名称
        private long imei; // 发生报警的imei号,仅用作展示，后面的请求需要用到imei的请用simei
        private String number; // 车牌号
        private String simei; // 发生报警的imei号,后续的请求，可能会用到这个simei
        private long time; // 发生报警的时间戳高德解析地址，<addr lat="123" lon="123"><ch time="222">地址</ch></addr>
        private String type; // 报警类型

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public int getDevspeed() {
            return devspeed;
        }

        public void setDevspeed(int devspeed) {
            this.devspeed = devspeed;
        }

        public String getFencename() {
            return fencename;
        }

        public void setFencename(String fencename) {
            this.fencename = fencename;
        }

        public long getImei() {
            return imei;
        }

        public void setImei(long imei) {
            this.imei = imei;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getSimei() {
            return simei;
        }

        public void setSimei(String simei) {
            this.simei = simei;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
