package com.car.scth.mvp.model.bean;

import com.car.scth.mvp.model.entity.BaseBean;

/**
 * 远程开关机结果
 */
public class RemoteSwitchResultBean extends BaseBean {

    /**
     * batch_no : 28912d4c-ea9b-42ec-8f3a-20a0a53b695a
     * msg : 24b51d6d-3299-4058-8291-dc1bbabd46eb
     * errcode : 0
     */

    private String batch_no; // 发送短信的批次号,公众物联不返回
    private String msg; // code不为0和400的结果说明

    public String getBatch_no() {
        return batch_no;
    }

    public void setBatch_no(String batch_no) {
        this.batch_no = batch_no;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
