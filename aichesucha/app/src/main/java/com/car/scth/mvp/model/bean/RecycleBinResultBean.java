package com.car.scth.mvp.model.bean;

import com.car.scth.mvp.model.entity.BaseBean;

import java.util.List;

/**
 * 回收站列表
 */
public class RecycleBinResultBean extends BaseBean {

    private List<ItemsBean> items;

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * car_number : cd92c157-a027-4fdb-82f6-b0eb37cd57ea
         * imei : 1556297579
         * recovery_account : 90c25e65-e3e6-415b-8526-27ce32997099
         * recovery_stat : 1556297579
         * sgid : 9726be56-4032-4e01-af26-c8a14cc267ec
         * simei : e95c66e0-6922-488f-9215-b9eaeff74445
         * version : 80dfc72b-8607-4469-a848-ca390aaab26b
         */

        private String car_number; // 车牌号
        private long imei; // 设备号 ,仅做展示
        private String recovery_account; // 执行冻结的操作帐号
        private long recovery_stat; // 回收时间戳
        private String sgid; // 车组号 ,后面请求需要
        private String simei; // 设备号 ,后面请求需要
        private String version; // default:1
        private boolean isSelect = false; // 是否选中

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getCar_number() {
            return car_number;
        }

        public void setCar_number(String car_number) {
            this.car_number = car_number;
        }

        public long getImei() {
            return imei;
        }

        public void setImei(long imei) {
            this.imei = imei;
        }

        public String getRecovery_account() {
            return recovery_account;
        }

        public void setRecovery_account(String recovery_account) {
            this.recovery_account = recovery_account;
        }

        public long getRecovery_stat() {
            return recovery_stat;
        }

        public void setRecovery_stat(long recovery_stat) {
            this.recovery_stat = recovery_stat;
        }

        public String getSgid() {
            return sgid;
        }

        public void setSgid(String sgid) {
            this.sgid = sgid;
        }

        public String getSimei() {
            return simei;
        }

        public void setSimei(String simei) {
            this.simei = simei;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
}
