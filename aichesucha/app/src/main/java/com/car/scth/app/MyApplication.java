package com.car.scth.app;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import android.util.Log;

import com.baidu.lbsapi.BMapManager;
import com.baidu.lbsapi.MKGeneralListener;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.car.scth.mvp.model.api.Api;
import com.car.scth.mvp.utils.ConstantValue;
import com.jess.arms.base.BaseApplication;
import com.car.scth.R;
import com.car.scth.mvp.model.bean.AlarmScreenDeviceBean;
import com.car.scth.mvp.utils.CrashHandlerUtil;
import com.car.scth.mvp.utils.ManufacturerUtil;
import com.car.scth.mvp.utils.MyDisplayMetrics;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.vondear.rxtool.RxTool;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.ArrayList;
import java.util.List;


public class MyApplication extends BaseApplication {

    private static MyApplication myApp;

    private long system_time = 0; // 点击时间，用于处理快速点击
    private String mSimei; // 设备加密后的imei号
    private String mLocInfo; // 设备的一些定位数据信息
    private String deviceState; // 设备状态，在线，离线，休眠
    private long mImei; // 设备imei号
    private int carImageId; // 设备图标的id
    private String deviceMode; // 设备定位模式
    private int signal_rate; // 信号强度百分比值
    private int signa_val; // 卫星数量
    private int power = 0; // 电量
    private String latAndLon; // 经纬度(字符串格式：lat,lon)
    private String version; // 设备版本
    private String mIccid; // iccid
    private String carName; // 设备名称

    private boolean isBeforeAccount = false; // 当前登录账号是否和上一个登录账号相同
    private boolean isMergeAccount = false; // 是否需要合并账号

    private List<com.baidu.mapapi.model.LatLng> baiduListPoint; // 百度地图，添加中国区域范围值，构建多边形区域
    private List<com.baidu.mapapi.model.LatLng> taiWanListPoint; // 百度地图，添加台湾区域范围值，构建多边形区域

    // 百度街景使用
    public BMapManager mBMapManager = null;

    // 小米推送
    private static final String APP_ID = "2882303761520117923";
    private static final String APP_KEY = "5512011785923";
    // 此TAG在adb logcat中检索自己所需要的信息， 只需在命令行终端输入 adb logcat | grep
    public static final String TAG = "acscPush";


    public static MyApplication getMyApp() {
        return myApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;
        RxTool.init(this);
        Utils.init(this);

        // crash日志
        CrashHandlerUtil.getInstance().init(this);

        MyDisplayMetrics.init(this);

        // 友盟注册
        UMConfigure.preInit(this, "61935d36e0f9bb492b5e373b", "SlxkCheck");
        if (ConstantValue.getUmengInit()) {
            UMConfigure.init(this, "61935d36e0f9bb492b5e373b", "SlxkCheck", UMConfigure.DEVICE_TYPE_PHONE, "");
            // 选用AUTO页面采集模式
            MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        }
        // 判断用户是否同意了隐私政策协议
        if (ConstantValue.isAgreePrivacy()) {
            //在使用SDK各组件之前初始化context信息，传入ApplicationContext
            SDKInitializer.initialize(this);
            //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
            //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
            SDKInitializer.setCoordType(CoordType.GCJ02);

            initEngineManager(this);

            if (!ManufacturerUtil.isHuaWeiPush(this)) { // 非华为使用小米推送
                initForXiaoMiPush();
            }
        }
        onAddBaiduPoint();
        onTaiWanPoint();
        LogUtils.getConfig().setLogSwitch(!Api.isRelease); // LogUtils 的打印日志，发布包 关闭

    }


    /**
     * 初始化小米推送
     */
    private void initForXiaoMiPush() {
        //初始化push推送服务
        if (shouldInit()) {
            MiPushClient.registerPush(this, APP_ID, APP_KEY);
        }
        //打开Log
        LoggerInterface newLogger = new LoggerInterface() {

            @Override
            public void setTag(String tag) {
                // ignore
            }

            @SuppressLint("LogNotTimber")
            @Override
            public void log(String content, Throwable t) {
                Log.d(TAG, content, t);
            }

            @SuppressLint("LogNotTimber")
            @Override
            public void log(String content) {
                Log.d(TAG, content);
            }
        };
        Logger.setLogger(this, newLogger);
    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getApplicationInfo().processName;
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 初始化百度街景
     *
     * @param context
     */
    public void initEngineManager(Context context) {
        if (mBMapManager == null) {
            mBMapManager = new BMapManager(context);
        }

        if (!mBMapManager.init(new MyGeneralListener())) {
            ToastUtils.showShort(myApp.getString(R.string.txt_street_view_failed));
        }
    }

    // 常用事件监听，用来处理通常的网络错误，授权验证错误等
    public static class MyGeneralListener implements MKGeneralListener {

        @Override
        public void onGetPermissionState(int iError) {
            // 非零值表示key验证未通过
            if (iError != 0) {
                // 授权Key错误：
                ToastUtils.showShort(myApp.getString(R.string.txt_network_key_invalid) + iError);
            }
        }
    }

    /**
     * 获取点击保存的时间戳
     *
     * @return
     */
    public long getSystemTime() {
        return system_time;
    }

    /**
     * 设置当前点击的时间戳
     *
     * @param time
     */
    public void setSystemTime(long time) {
        system_time = time;
    }

    /**
     * 设备的加密的imei号
     *
     * @return
     */
    public String getSimei() {
        return mSimei;
    }

    /**
     * 赋值新的simei号
     *
     * @param imei
     */
    public void setSimei(String imei) {
        mSimei = imei;
    }

    /**
     * 获取用户iccid
     *
     * @return
     */
    public String getmIccid() {
        return mIccid;
    }

    public void setmIccid(String iccid) {
        this.mIccid = iccid;
    }

    /**
     * 设备名称
     *
     * @return
     */
    public String getCarName() {
        return carName;
    }

    public void setCarName(String name) {
        this.carName = name;
    }

    /**
     * 用户的一些定位数据，定位时间，定位类型等数据
     *
     * @return
     */
    public String getLocInfo() {
        return mLocInfo;
    }

    public void setLocInfo(String locInfo) {
        mLocInfo = locInfo;
    }

    /**
     * 设备状态
     *
     * @return
     */
    public String getDeviceState() {
        return deviceState;
    }

    public void setDeviceState(String state) {
        deviceState = state;
    }

    /**
     * 设备的imei号
     *
     * @return
     */
    public long getImei() {
        return mImei;
    }

    public void setImei(long imei) {
        mImei = imei;
    }

    /**
     * 设备定位模式
     *
     * @return
     */
    public String getDeviceMode() {
        return deviceMode;
    }

    public void setDeviceMode(String mode) {
        deviceMode = mode;
    }

    /**
     * 信号强度百分比值
     *
     * @return
     */
    public int getSignal_rate() {
        return signal_rate;
    }

    public void setSignal_rate(int value) {
        signal_rate = value;
    }

    /**
     * 卫星数量
     *
     * @return
     */
    public int getSigna_val() {
        return signa_val;
    }

    public void setSigna_val(int value) {
        signa_val = value;
    }

    /**
     * 电量值
     *
     * @return
     */
    public int getPower() {
        return power;
    }

    public void setPower(int value) {
        power = value;
    }

    /**
     * 设备的图标id
     *
     * @return
     */
    public int getCarImageId() {
        return carImageId;
    }

    public void setCarImageId(int imageId) {
        carImageId = imageId;
    }

    /**
     * 经纬度
     *
     * @return
     */
    public String getLatAndLon() {
        return latAndLon;
    }

    public void setLatAndLon(String latlon) {
        latAndLon = latlon;
    }

    /**
     * 设备版本
     *
     * @return
     */
    public String getVersion() {
        return version;
    }

    public void setVersion(String value) {
        version = value;
    }

    /**
     * 获取中国范围经纬度多边形
     *
     * @return
     */
    public List<LatLng> getBaiduListPoint() {
        return baiduListPoint;
    }

    /**
     * 获取台湾范围经纬度多边形
     *
     * @return
     */
    public List<com.baidu.mapapi.model.LatLng> getTaiWanListPoint() {
        return taiWanListPoint;
    }

    /**
     * 添加中国区域范围值，构建多边形区域
     */
    private void onAddBaiduPoint() {
        baiduListPoint = new ArrayList<>();
        baiduListPoint.add(new com.baidu.mapapi.model.LatLng(21.528655, 107.399393));
        baiduListPoint.add(new com.baidu.mapapi.model.LatLng(3.375179, 110.915018));
        baiduListPoint.add(new com.baidu.mapapi.model.LatLng(24.36105, 122.868143));
        baiduListPoint.add(new com.baidu.mapapi.model.LatLng(39.803422, 124.406229));
        baiduListPoint.add(new com.baidu.mapapi.model.LatLng(41.404783, 128.317362));
        baiduListPoint.add(new com.baidu.mapapi.model.LatLng(45.022246, 133.151346));
        baiduListPoint.add(new com.baidu.mapapi.model.LatLng(48.424784, 135.128885));
        baiduListPoint.add(new com.baidu.mapapi.model.LatLng(47.808684, 131.217752));
        baiduListPoint.add(new com.baidu.mapapi.model.LatLng(53.169129, 125.636698));
        baiduListPoint.add(new com.baidu.mapapi.model.LatLng(52.904887, 119.967752));
        baiduListPoint.add(new com.baidu.mapapi.model.LatLng(52.33295, 120.530802));
        baiduListPoint.add(new com.baidu.mapapi.model.LatLng(49.622413, 117.762247));
        baiduListPoint.add(new com.baidu.mapapi.model.LatLng(49.892116, 116.729532));
        baiduListPoint.add(new com.baidu.mapapi.model.LatLng(47.869521, 115.455118));
        baiduListPoint.add(new com.baidu.mapapi.model.LatLng(47.61834, 117.388712));
        baiduListPoint.add(new com.baidu.mapapi.model.LatLng(47.957884, 118.509317));
        baiduListPoint.add(new com.baidu.mapapi.model.LatLng(46.72212, 119.717813));
        baiduListPoint.add(new com.baidu.mapapi.model.LatLng(46.601481, 117.476603));
        baiduListPoint.add(new com.baidu.mapapi.model.LatLng(45.008654, 111.681961));
        baiduListPoint.add(new com.baidu.mapapi.model.LatLng(42.566385, 108.825515));
        baiduListPoint.add(new com.baidu.mapapi.model.LatLng(42.921389, 96.564773));
        baiduListPoint.add(new com.baidu.mapapi.model.LatLng(49.30825, 87.248367));
        baiduListPoint.add(new com.baidu.mapapi.model.LatLng(47.32128, 82.985672));
        baiduListPoint.add(new com.baidu.mapapi.model.LatLng(44.977577, 79.821609));
        baiduListPoint.add(new com.baidu.mapapi.model.LatLng(42.111624, 79.99739));
        baiduListPoint.add(new com.baidu.mapapi.model.LatLng(39.517976, 73.361648));
        baiduListPoint.add(new com.baidu.mapapi.model.LatLng(29.856311, 81.227859));
        baiduListPoint.add(new com.baidu.mapapi.model.LatLng(27.153442, 89.00618));
        baiduListPoint.add(new com.baidu.mapapi.model.LatLng(26.486741, 98.542312));
        baiduListPoint.add(new com.baidu.mapapi.model.LatLng(23.781767, 97.44368));
        baiduListPoint.add(new com.baidu.mapapi.model.LatLng(20.896219, 101.75032));
        baiduListPoint.add(new com.baidu.mapapi.model.LatLng(22.366781, 103.06868));
        baiduListPoint.add(new com.baidu.mapapi.model.LatLng(22.853592, 106.100906));
    }

    /**
     * 台湾多边形区域
     */
    private void onTaiWanPoint() {
        taiWanListPoint = new ArrayList<>();
        taiWanListPoint.add(new com.baidu.mapapi.model.LatLng(25.767311, 121.56077));
        taiWanListPoint.add(new com.baidu.mapapi.model.LatLng(23.218193, 118.528543));
        taiWanListPoint.add(new com.baidu.mapapi.model.LatLng(20.588635, 120.714823));
        taiWanListPoint.add(new com.baidu.mapapi.model.LatLng(22.621177, 122.912088));
        taiWanListPoint.add(new com.baidu.mapapi.model.LatLng(25.182142, 123.428446));
    }

    public String getAppId(){
        return APP_ID;
    }

    public String getAppKey(){
        return APP_KEY;
    }


    /**
     * 清理临时缓存数据
     */
    public void clearData() {
        mSimei = "";
        mLocInfo = "";
        deviceState = "";
        mImei = 0;
        deviceMode = "";
        signal_rate = 0;
        signa_val = 0;
        power = 0;
        carImageId = 0;
        latAndLon = "";
        version = "";
        mIccid = "";
        carName = "";
    }

    /**
     * 是否是上一个账号登录
     *
     * @param isAccount
     */
    public void setBeforeAccount(boolean isAccount) {
        this.isBeforeAccount = isAccount;
    }

    public boolean isBeforeAccount() {
        return isBeforeAccount;
    }

    /**
     * 是否需要合并账号
     *
     * @param mergeAccount
     */
    public void setMergeAccount(boolean mergeAccount) {
        this.isMergeAccount = mergeAccount;
    }

    public boolean isMergeAccount() {
        return isMergeAccount;
    }

}
