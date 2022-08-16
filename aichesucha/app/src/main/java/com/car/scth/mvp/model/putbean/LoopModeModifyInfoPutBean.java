package com.car.scth.mvp.model.putbean;

import java.io.Serializable;
import java.util.List;

/**
 * 周期定位需要修改的信息
 */
public class LoopModeModifyInfoPutBean implements Serializable {

    private String lid; // 周期定位id
    private boolean loc_switch; // 周期定位开关
    private String loc_value; // 周期定位数值
    private String loop_loc_name; // 周期定位名称
    private List<Integer> rp_loop_loc; // 周期定位星期

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

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
