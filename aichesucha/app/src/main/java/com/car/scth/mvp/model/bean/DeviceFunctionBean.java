package com.car.scth.mvp.model.bean;

/**
 * 设备功能表
 */
public class DeviceFunctionBean {

    // 功能id，0：流量卡，1：录音，2：一键设防，3：设备详情，4：导航，5：远程开关，6：定位模式，7：操作记录，8：里程统计，9：远程听音，10：油电控制，11：实时追踪，12：一键重启，
    // 13：一键休眠，14：故障自检，15：平台服务，16：寻找设备，17：一键唤醒
    private int id;
    private int drawableId; // 图标id地址
    private String functionName;

    public DeviceFunctionBean(int id, int drawableId, String functionName){
        super();
        this.id = id;
        this.drawableId = drawableId;
        this.functionName = functionName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }
}
