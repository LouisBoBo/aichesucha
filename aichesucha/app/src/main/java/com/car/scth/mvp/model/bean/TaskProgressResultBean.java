package com.car.scth.mvp.model.bean;

import com.car.scth.mvp.model.entity.BaseBean;

/**
 * 任务进度
 */
public class TaskProgressResultBean extends BaseBean {

    /**
     * current_count : 254111727
     * is_begin : false
     * is_finish : false
     * return_code : 254111727
     * return_msg : 96efa531-39f4-4ada-a6b0-4bed7059c360
     * total : 254111727
     */

    private int current_count; // 当前完成任务数
    private boolean is_begin; // 任务是否已经开始
    private boolean is_finish; // 是否结束了
    private long return_code; // 当前完成之后的返回值
    private String return_msg; // 返回的提示信息，如果出错，返回错误信息
    private int total; // 待完成的总数

    public int getCurrent_count() {
        return current_count;
    }

    public void setCurrent_count(int current_count) {
        this.current_count = current_count;
    }

    public boolean isIs_begin() {
        return is_begin;
    }

    public void setIs_begin(boolean is_begin) {
        this.is_begin = is_begin;
    }

    public boolean isIs_finish() {
        return is_finish;
    }

    public void setIs_finish(boolean is_finish) {
        this.is_finish = is_finish;
    }

    public long getReturn_code() {
        return return_code;
    }

    public void setReturn_code(long return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
