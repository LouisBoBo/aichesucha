package com.car.scth.mvp.model.bean;

import com.car.scth.mvp.model.entity.BaseBean;

public class GetTaskResultBean extends BaseBean {
    /**
     * {
     *   "errcode": 0,
     *   "current_count": 254111727,
     *   "error_message": "9be8a453-2d82-4a66-a5ee-2c7c6f14ed5a",
     *   "is_begin": false,
     *   "is_finish": false,
     *   "return_code": 254111727,
     *   "return_msg": "96efa531-39f4-4ada-a6b0-4bed7059c360",
     *   "total": 254111727
     * }
     */
      private int current_count;
      private boolean is_begin;
      private boolean is_finish;
      private int return_code;
      private String return_msg;
      private int total;

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

    public int getReturn_code() {
        return return_code;
    }

    public void setReturn_code(int return_code) {
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
