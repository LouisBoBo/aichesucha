package com.car.scth.mvp.model.putbean;

import java.io.Serializable;
import java.util.List;

/**
 * 设备详情信息，传递到设备详情页
 */
public class DeviceDetailIntentBean implements Serializable {

    private String device_number; // 设备名称
    private long imei; // 设备imei号
    private String simei; // 加密的imei号
    private String car_type; // 设备型号，需要自己分割
    private String state; // 设备状态
    private String last_pos; // 位置信息 json 格式如:{"Time":"2020-01-17 10:41:10","Lon":"114.03361124","Lat":"22.64252376","Addr":"114.033611,22.642524","Type":4,"Devspeed":"0.000","Direction":"196"}  注 Devspeed和Direction为可选字段
    private String iccid;
    private long start_dev_time; // 设备开通时间-时间戳
    private long end_dev_time; // 设备到期时间-时间戳
    private String bck_phone; // 车主电话（备用电话号码）
    private String user_name; // 车主姓名
    private String center_phone; // 中心号码
    private String bind_phone; // 绑定手机号,设置get_user时返回
    private List<String> bind_seller_name; // 销售商账号名称,设置get_user时返回

    public String getBind_phone() {
        return bind_phone;
    }

    public void setBind_phone(String bind_phone) {
        this.bind_phone = bind_phone;
    }

    public List<String> getBind_seller_name() {
        return bind_seller_name;
    }

    public void setBind_seller_name(List<String> bind_seller_name) {
        this.bind_seller_name = bind_seller_name;
    }

    public String getCenter_phone() {
        return center_phone;
    }

    public void setCenter_phone(String center_phone) {
        this.center_phone = center_phone;
    }

    public String getDevice_number() {
        return device_number;
    }

    public void setDevice_number(String device_number) {
        this.device_number = device_number;
    }

    public long getImei() {
        return imei;
    }

    public void setImei(long imei) {
        this.imei = imei;
    }

    public String getSimei() {
        return simei;
    }

    public void setSimei(String simei) {
        this.simei = simei;
    }

    public String getCar_type() {
        return car_type;
    }

    public void setCar_type(String car_type) {
        this.car_type = car_type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLast_pos() {
        return last_pos;
    }

    public void setLast_pos(String last_pos) {
        this.last_pos = last_pos;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public long getStart_dev_time() {
        return start_dev_time;
    }

    public void setStart_dev_time(long start_dev_time) {
        this.start_dev_time = start_dev_time;
    }

    public long getEnd_dev_time() {
        return end_dev_time;
    }

    public void setEnd_dev_time(long end_dev_time) {
        this.end_dev_time = end_dev_time;
    }

    public String getBck_phone() {
        return bck_phone;
    }

    public void setBck_phone(String bck_phone) {
        this.bck_phone = bck_phone;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
