package com.car.scth.mvp.model.bean;

import com.car.scth.mvp.model.entity.BaseBean;

import java.util.List;

/**
 * 删除分组和设备
 */
public class DeleteDeviceResultBean extends BaseBean {

    /**
     * suc_items : [{"imei":570512007,"simei":"5fe1da04-35b1-4b60-8d79-9a75ac568a49"}]
     * task_id : 20c27e9d-dafd-4cca-baf8-815de5c80c91
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
         * imei : 570512007
         * simei : 5fe1da04-35b1-4b60-8d79-9a75ac568a49
         */

        private long imei;
        private String simei;

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
