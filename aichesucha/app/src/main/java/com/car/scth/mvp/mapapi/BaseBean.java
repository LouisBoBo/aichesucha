package com.car.scth.mvp.mapapi;

import com.google.gson.annotations.SerializedName;

/**
 * @author Mr.zhuang on 2016/12/17.
 */
public class BaseBean {

    /**
     * timestamp : 1484363061216
     * httpCode : 200
     * msg : 请求成功
     */
    @SerializedName("error")
    private int httpCode = 0; // 返回编码
    private String errmsg; // 返回报文

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
