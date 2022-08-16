package com.car.scth.mvp.model.bean;

import com.google.gson.annotations.SerializedName;
import com.car.scth.mvp.model.entity.BaseBean;

import java.util.List;

/**
 * 添加设备
 */
public class AddDeviceResultBean extends BaseBean {

    private List<FailItemsBean> fail_items; // 添加失败的imei号
    private List<SucItemsBean> suc_items; // 添加成功的imei号

    public List<FailItemsBean> getFail_items() {
        return fail_items;
    }

    public void setFail_items(List<FailItemsBean> fail_items) {
        this.fail_items = fail_items;
    }

    public List<SucItemsBean> getSuc_items() {
        return suc_items;
    }

    public void setSuc_items(List<SucItemsBean> suc_items) {
        this.suc_items = suc_items;
    }

    public static class FailItemsBean {
        /**
         * error_message : 1dbead4d-1618-47bf-9634-835bdb247ed0
         * imei : 955032989
         */

        @SerializedName("error_message")
        private String error_messageX;
        private long imei;

        public String getError_messageX() {
            return error_messageX;
        }

        public void setError_messageX(String error_messageX) {
            this.error_messageX = error_messageX;
        }

        public long getImei() {
            return imei;
        }

        public void setImei(long imei) {
            this.imei = imei;
        }
    }

    public static class SucItemsBean {
        /**
         * imei : 955032989
         * simei : 734cc744-bd79-4f68-8db4-6755249d416a
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
