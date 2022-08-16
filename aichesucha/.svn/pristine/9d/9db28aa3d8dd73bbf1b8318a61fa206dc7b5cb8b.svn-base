package com.car.scth.mvp.model.bean;

import com.car.scth.mvp.model.entity.BaseBean;

import java.util.List;

/**
 * 周期定位列表
 */
public class LoopLocationModeResultBean extends BaseBean {

    /**
     * items : [{"info":{"loc_switch":true,"loc_type":"e_type_week|e_type_day","loc_value":"c8f4ff1e-a2e7-48ab-abf5-8837e39f1533","loop_loc_name":"52a32980-2592-45ab-9e4f-8c300a63dcaf","rp_loop_loc":[1101480408]},"lid":"97e45596-4ff5-465b-8782-3fd46e1fe31e"},{"info":{"loc_switch":true,"loc_type":"e_type_week|e_type_day","loc_value":"6486e868-8e96-46ec-ae04-fa003b6bbbff","loop_loc_name":"18163587-ed4b-4138-8cd5-66d49a5fee6b","rp_loop_loc":[1101480408,1101480408,1101480408,1101480408]},"lid":"08ab7633-40d9-4b63-9cc7-5dc7168bfb29"},{"info":{"loc_switch":true,"loc_type":"e_type_week|e_type_day","loc_value":"c72a1960-50b1-47e5-a656-ebb52d2f50e7","loop_loc_name":"2e003f87-8402-489c-9930-7bec7c84b921","rp_loop_loc":[1101480408,1101480408]},"lid":"1fbecba1-b139-45d5-93dc-863cc7a66685"}]
     * total : 1101480408
     */

    private int total; // 符合条件的总条数
    private List<ItemsBean> items;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * info : {"loc_switch":true,"loc_type":"e_type_week|e_type_day","loc_value":"c8f4ff1e-a2e7-48ab-abf5-8837e39f1533","loop_loc_name":"52a32980-2592-45ab-9e4f-8c300a63dcaf","rp_loop_loc":[1101480408]}
         * lid : 97e45596-4ff5-465b-8782-3fd46e1fe31e
         */

        private InfoBean info;
        private String lid; // 周期定位id

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public String getLid() {
            return lid;
        }

        public void setLid(String lid) {
            this.lid = lid;
        }

        public static class InfoBean {
            /**
             * loc_switch : true
             * loc_type : e_type_week|e_type_day
             * loc_value : c8f4ff1e-a2e7-48ab-abf5-8837e39f1533
             * loop_loc_name : 52a32980-2592-45ab-9e4f-8c300a63dcaf
             * rp_loop_loc : [1,2,3,4,5,6,7]
             */

            private boolean loc_switch; // 周期定位开关
            private String loc_value; // 周期定位数值
            private String loop_loc_name; // 周期定位名称
            private List<Integer> rp_loop_loc; // 周期定位星期

            public boolean isLoc_switch() {
                return loc_switch;
            }

            public void setLoc_switch(boolean loc_switch) {
                this.loc_switch = loc_switch;
            }

            public String getLoc_value() {
                return loc_value;
            }

            public void setLoc_value(String loc_value) {
                this.loc_value = loc_value;
            }

            public String getLoop_loc_name() {
                return loop_loc_name;
            }

            public void setLoop_loc_name(String loop_loc_name) {
                this.loop_loc_name = loop_loc_name;
            }

            public List<Integer> getRp_loop_loc() {
                return rp_loop_loc;
            }

            public void setRp_loop_loc(List<Integer> rp_loop_loc) {
                this.rp_loop_loc = rp_loop_loc;
            }
        }
    }
}
