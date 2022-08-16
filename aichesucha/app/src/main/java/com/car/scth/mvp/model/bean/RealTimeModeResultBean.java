package com.car.scth.mvp.model.bean;

import com.car.scth.mvp.model.entity.BaseBean;

/**
 * 实时定位模式结果bean
 */
public class RealTimeModeResultBean extends BaseBean {

    /**
     * is_locspace : 660240551
     * is_realtimeposition : 660240551
     * rtls_mode : e_rtls_time|e_rtls_space
     * rtls_value : 660240551
     */

    private int is_locspace; // 是否是实时定位的定距模式, 0不支持,1支持，2只读
    private int is_realtimeposition; // 是否是实时定位的定时模式, 0不支持,1支持，2只读
    private String rtls_mode; // e_rtls_time 定时 || e_rtls_space 定距
    private int rtls_value; // 0-10s~120s 1-200m~1000m

    public int getIs_locspace() {
        return is_locspace;
    }

    public void setIs_locspace(int is_locspace) {
        this.is_locspace = is_locspace;
    }

    public int getIs_realtimeposition() {
        return is_realtimeposition;
    }

    public void setIs_realtimeposition(int is_realtimeposition) {
        this.is_realtimeposition = is_realtimeposition;
    }

    public String getRtls_mode() {
        return rtls_mode;
    }

    public void setRtls_mode(String rtls_mode) {
        this.rtls_mode = rtls_mode;
    }

    public int getRtls_value() {
        return rtls_value;
    }

    public void setRtls_value(int rtls_value) {
        this.rtls_value = rtls_value;
    }
}

