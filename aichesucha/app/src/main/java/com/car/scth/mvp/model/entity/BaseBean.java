package com.car.scth.mvp.model.entity;

import com.car.scth.mvp.model.api.Api;

import java.io.Serializable;

/**
 * Created by Administrator on 2019\5\9 0009.
 */

public class BaseBean implements Serializable {

    /**
     * error : 0000
     * error_description : success
     */

    private int errcode;
    private String error_message;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    /**
     * 请求是否成功
     */
    public boolean isSuccess() {
        return errcode == Api.SUCCESS;
    }

    /**
     * 特殊情况会使用200成功请求码
     */
    public boolean isSuccessFor200(){
        return errcode == Api.SUCCESS_200;
    }

}
