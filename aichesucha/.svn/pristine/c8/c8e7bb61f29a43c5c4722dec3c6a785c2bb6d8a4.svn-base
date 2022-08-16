package com.car.scth.mvp.model.bean;

import com.car.scth.mvp.model.entity.BaseBean;

import java.util.List;

/**
 * 转移设备
 */
public class TransferDeviceResultBean extends BaseBean {

    /**
     * suc_items : [{"imei":1304811208,"simei":"ac668b68-1b4c-4da0-8e3a-08c9af8c08ff"}]
     * task_id : 33eb73e8-ec68-48d7-b869-d6e1e894dfb1
     */

    private String task_id; // 如果数据量太多，触发异步操作，这里会返回任务id，如果没有返回，表示同步操作
    private List<SucItemsBean> suc_items; // 当没有触发异步操作时，有操作失败的imei号，通过这个字段返回

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public List<SucItemsBean> getSuc_items() {
        return suc_items;
    }

    public void setSuc_items(List<SucItemsBean> suc_items) {
        this.suc_items = suc_items;
    }

    public static class SucItemsBean {
        /**
         * imei : 1304811208
         * simei : ac668b68-1b4c-4da0-8e3a-08c9af8c08ff
         */

        private long imei; // 错误的imei号
        private String simei; // 错误的设备id

        public long getImei() {
            return imei;
        }

        public void setImei(long imei) {
            this.imei = imei;
        }

        public String getSimei() {
            return simei;
        }

        public void setSimei(String simei) {
            this.simei = simei;
        }
    }
}
