package com.car.scth.mvp.model.bean;



import java.util.List;

import com.car.scth.mvp.model.entity.BaseBean;

/**
 * 模糊搜索/精准搜索
 */
public class FindDeviceResultBean extends BaseBean {

    private List<ItemsBean> items;

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * car_number : 1d49b12d-49cc-4fa8-a184-14aef7324335
         * imei : 909508334
         * is_recovery : true
         * sfid : 7560f733-d591-40ca-be2a-be963f44f63c
         * sgid : 69eaee9a-4863-4feb-9329-f8006d7c1c14
         * simei : 9e52883e-20da-4a34-b37d-553298020d8d
         * version : ede96d0e-f2d4-4248-92d0-00daffacf9ca
         */

        private String car_number; // 车牌号
        private long imei;
        private boolean is_recovery; // 是否回收设备，true是,false否
        private String sfid; // 组织id
        private String sgid; // 分组id
        private String simei;
        private String version; // 版本号

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

        public boolean isIs_recovery() {
            return is_recovery;
        }

        public void setIs_recovery(boolean is_recovery) {
            this.is_recovery = is_recovery;
        }

        public String getSfid() {
            return sfid;
        }

        public void setSfid(String sfid) {
            this.sfid = sfid;
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
