package com.car.scth.mvp.model.bean;

import com.car.scth.mvp.model.entity.BaseBean;

import java.util.List;

/**
 * 添加围栏结果
 */
public class FenceAddResultBean extends BaseBean {

    private List<FailImeiBean> fail_imei; // 失败列表
    private List<SucItemsBean> suc_items; // 添加成功的围栏列表

    public List<FailImeiBean> getFail_imei() {
        return fail_imei;
    }

    public void setFail_imei(List<FailImeiBean> fail_imei) {
        this.fail_imei = fail_imei;
    }

    public List<SucItemsBean> getSuc_items() {
        return suc_items;
    }

    public void setSuc_items(List<SucItemsBean> suc_items) {
        this.suc_items = suc_items;
    }

    public static class FailImeiBean {
        /**
         * imei : 2031776262
         * message : 6dd1e6b8-5a4e-4697-981b-e1caf62af336
         */

        private long imei;
        private String message;

        public long getImei() {
            return imei;
        }

        public void setImei(long imei) {
            this.imei = imei;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class SucItemsBean {
        /**
         * imei : 2031776262
         * sfid : 89748dd1-2d40-42e4-8092-8f45e544bc75
         */

        private long imei;
        private String sfid;

        public long getImei() {
            return imei;
        }

        public void setImei(long imei) {
            this.imei = imei;
        }

        public String getSfid() {
            return sfid;
        }

        public void setSfid(String sfid) {
            this.sfid = sfid;
        }
    }
}
