package com.car.scth.mvp.model.entity;

import com.google.gson.annotations.SerializedName;
import com.car.scth.mvp.model.api.Api;

import java.io.Serializable;

public class BaseListBean<T extends ListBean>  implements Serializable {

    private T data;
    @SerializedName("errcode")
    private int code;
    @SerializedName("error_message")
    private String msg;

    public T getData() {
        return data;
    }

    public long getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    /**
     * 请求是否成功
     */
    public boolean isSuccess() {
        return code == Api.SUCCESS;
    }
}
