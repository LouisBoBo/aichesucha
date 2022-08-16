package com.car.scth.mvp.model.bean;

import com.car.scth.mvp.model.entity.BaseBean;

public class MileageStatisticsResultBean extends BaseBean {
    /**
     * {
     *   "distance": 1011791002,
     *   "error_message": "8d358705-f963-4c39-9273-d65477c24711",
     *   "speed_alarm": 1011791002,
     *   "total_alarm": 1011791002,
     *   "errcode": 0
     * }
     */

    private int distance;
    private int speed_alarm;
    private int total_alarm;


    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getSpeed_alarm() {
        return speed_alarm;
    }

    public void setSpeed_alarm(int speed_alarm) {
        this.speed_alarm = speed_alarm;
    }

    public int getTotal_alarm() {
        return total_alarm;
    }

    public void setTotal_alarm(int total_alarm) {
        this.total_alarm = total_alarm;
    }
}
