package com.car.scth.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.utils.DistanceUtil;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.car.scth.R;
import com.car.scth.app.MyApplication;
import com.car.scth.di.component.DaggerRealTimeTrackBaiduComponent;
import com.car.scth.mvp.contract.RealTimeTrackBaiduContract;
import com.car.scth.mvp.model.api.ModuleValueService;
import com.car.scth.mvp.model.bean.AlertBean;
import com.car.scth.mvp.model.bean.DeviceListResultBean;
import com.car.scth.mvp.model.bean.DeviceLocationInfoBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.DeviceListPutBean;
import com.car.scth.mvp.model.putbean.RealTimeTrackPutBean;
import com.car.scth.mvp.presenter.RealTimeTrackBaiduPresenter;
import com.car.scth.mvp.utils.BitmapHelperBaidu;
import com.car.scth.mvp.utils.DeviceUtils;
import com.car.scth.mvp.utils.FunctionType;
import com.car.scth.mvp.utils.ResultDataUtils;
import com.car.scth.mvp.utils.ToastUtils;
import com.car.scth.mvp.utils.Utils;
import com.car.scth.mvp.weiget.AlertAppDialog;
import com.car.scth.mvp.weiget.RealTimeTrackInputDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/01/2021 09:15
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class RealTimeTrackBaiduActivity extends BaseActivity<RealTimeTrackBaiduPresenter> implements RealTimeTrackBaiduContract.View,
        BaiduMap.OnMapStatusChangeListener{

    @BindView(R.id.mapView)
    MapView mapView;

    private BaiduMap baiduMap;

    private ScheduledExecutorService scheduledThreadPool;
    private int size;
    private LatLng peo;
    private LatLng realLocation;
    private float zoom = 15; // 缩放层级
    private LatLngBounds.Builder mBuilder; // 所有设备可视化Builder
    private LocationClient mLocationClient;
    private MyLocationConfiguration.LocationMode mCurrentMode; // 定位模式 地图SDK支持三种定位模式：NORMAL（普通态）, FOLLOWING（跟随态）, COMPASS（罗盘态）
    private static final int accuracyCircleFillColor = 0x00FF00FF; // 自定义精度圈填充颜色
    private static final int accuracyCircleStrokeColor = 0x00FF00FF; // 自定义精度圈边框颜色

    //绘制轨迹线
    public List<LatLng> followLat;
    private Marker carMarker;
    private boolean isFirstGet = true; // 是否是首次请求，是的话，定位到设备在的位置
    private BitmapHelperBaidu bitmapHelper;

    private List<String> mSimeiLists;//实时请求imei号

    private long mImei;
    private String mSimei; // 设备imei号
    private String deviceNumber; // 设备名称
    private String deviceState; // 设备状态
    private String latAndLon = ""; // 设备经纬度
    private String deviceVersion; // 设备版本
    private int carImageId; // 设备图标id

    private int intervalTime = 5; // 跟踪控制的设备上报时间间隔 0-取消追踪，单位s
    private int effectiveTime = 1; // 位置跟踪有效期，单位min
    private int limitSizeForDevice = 10; // 最大获取设备列表数量限制
    private double mLatitude = 0; // 设备的经纬度
    private double mLongitude = 0; // 设备的经纬度
    private int mCountDown = 0; // 实时追踪倒计时指令
    private RealTimeTrackInputDialog trackInputDialog; // 时间填写dialog
    private boolean isFirstData = true; // 是否是第一次下发实时追踪指令

    public static Intent newInstance() {
        return new Intent(MyApplication.getMyApp(), RealTimeTrackBaiduActivity.class);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRealTimeTrackBaiduComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_real_time_track_baidu;//setContentView(id);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_function_realtime_tracking));
        carImageId = MyApplication.getMyApp().getCarImageId();

        followLat = new ArrayList<>();
        mImei = MyApplication.getMyApp().getImei();
        mSimei = MyApplication.getMyApp().getSimei();
        deviceState = MyApplication.getMyApp().getDeviceState();
        latAndLon = MyApplication.getMyApp().getLatAndLon();
        deviceVersion = MyApplication.getMyApp().getVersion();
        bitmapHelper = new BitmapHelperBaidu(this);

        if (TextUtils.isEmpty(MyApplication.getMyApp().getCarName())){
            if (String.valueOf(MyApplication.getMyApp().getImei()).length() > 6){
                deviceNumber = String.valueOf(MyApplication.getMyApp().getImei()).substring(String.valueOf(MyApplication.getMyApp().getImei()).length() - 6);
            }else{
                deviceNumber = String.valueOf(MyApplication.getMyApp().getImei());
            }
        }else{
            deviceNumber = MyApplication.getMyApp().getCarName();
        }

        mSimeiLists = new ArrayList<>();
        if (!TextUtils.isEmpty(mSimei)){
            mSimeiLists.add(mSimei);
        }

        baiduMap = mapView.getMap();
        // 设置地图缩放等级事件监听
        baiduMap.setOnMapStatusChangeListener(this);
        initMap();

        if (!TextUtils.isEmpty(latAndLon)){
            String[] location = latAndLon.split(",");
            mLatitude = (double) Long.parseLong(location[0]) / 1000000;
            mLongitude = (double) Long.parseLong(location[1]) / 1000000;
            realLocation = new LatLng(mLatitude, mLongitude);
            updateMapDisplay(realLocation, 0);
        }

        if (deviceVersion != null && FunctionType.C2.equals(deviceVersion.toUpperCase())) {
            effectiveTime = 1;
            mCountDown = effectiveTime * 60;
        } else {
            onInputTime();
        }
    }

    private void initMap() {
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        // 隐藏缩放控件
        mapView.showZoomControls(false);

        // 设置当前缩放等级
        mBuilder = new LatLngBounds.Builder();

        if (baiduMap != null) {
            baiduMap.setMyLocationEnabled(true);
            //定位初始化
            mLocationClient = new LocationClient(this);

            //通过LocationClientOption设置LocationClient相关参数
            LocationClientOption option = new LocationClientOption();
            option.setOpenGps(true); // 打开gps
            option.setCoorType("gcj02"); // 设置坐标类型
            option.setScanSpan(30000);
            //可选，设置是否需要地址信息，默认不需要
            option.setIsNeedAddress(true);
            //可选，设置是否需要地址描述
            option.setIsNeedLocationDescribe(true);

            //设置locationClientOption
            mLocationClient.setLocOption(option);

            // 自定义定位显示图标
            MyLocationConfiguration locationConfiguration = new MyLocationConfiguration(mCurrentMode,
                    false, bitmapHelper.getBitmapZoomForUserLocation(zoom), accuracyCircleFillColor, accuracyCircleStrokeColor);
            baiduMap.setMyLocationConfiguration(locationConfiguration);

            //注册LocationListener监听器
            MyLocationListener myLocationListener = new MyLocationListener();
            mLocationClient.registerLocationListener(myLocationListener);
        }
    }

    @Override
    public void submitTempTrackingSuccess(BaseBean baseBean) {
        if (isFirstData){
            ToastUtils.show(getString(R.string.txt_set_success));
        }
        isFirstData = false;
        if (!deviceVersion.toUpperCase().equals(FunctionType.C2)){
            handler.sendEmptyMessage(2);
//            getCycleData();
        }
        if (trackInputDialog != null && trackInputDialog.isAdded()){
            trackInputDialog.dismiss();
        }
    }

    @Override
    public void getDeviceListSuccess(DeviceListResultBean deviceListResultBean) {
        if (deviceListResultBean.getItems() != null && deviceListResultBean.getItems().size() > 0){
            DeviceListResultBean.ItemsBean itemsBean = deviceListResultBean.getItems().get(0);
            MyApplication.getMyApp().setSimei(itemsBean.getSimei());
            MyApplication.getMyApp().setLocInfo(itemsBean.getLast_pos());
            MyApplication.getMyApp().setDeviceState(itemsBean.getState());
            MyApplication.getMyApp().setImei(itemsBean.getImei());
            MyApplication.getMyApp().setPower(itemsBean.getPower());
            MyApplication.getMyApp().setVersion(itemsBean.getVersion());
            DeviceLocationInfoBean bean = DeviceUtils.parseDeviceData(itemsBean.getLast_pos());
            MyApplication.getMyApp().setLatAndLon(bean.getLat() + "," + bean.getLon());

            if (TextUtils.isEmpty(itemsBean.getCar_number())){
                if (String.valueOf(itemsBean.getImei()).length() > 6){
                    deviceNumber = String.valueOf(itemsBean.getImei()).substring(String.valueOf(itemsBean.getImei()).length() - 6);
                }else{
                    deviceNumber = String.valueOf(itemsBean.getImei());
                }
            }else{
                deviceNumber = itemsBean.getCar_number();
            }
            deviceVersion = itemsBean.getVersion();
            mLatitude = (double) bean.getLat() / 1000000;
            mLongitude = (double) bean.getLon() / 1000000;
            deviceState = itemsBean.getState();
            realLocation = new LatLng(mLatitude, mLongitude);
            DeviceLocationInfoBean infoBean = DeviceUtils.parseDeviceData(itemsBean.getLast_pos());
            updateMapDisplay(realLocation, infoBean.getDirection());
        }
    }

    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus) {
        zoom = mapStatus.zoom;
    }

    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

    }

    @Override
    public void onMapStatusChange(MapStatus mapStatus) {

    }

    @Override
    public void onMapStatusChangeFinish(MapStatus mapStatus) {

    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mapView == null) {
                return;
            }
            peo = new LatLng(location.getLatitude(), location.getLongitude());
            //计算p1、p2两点之间的直线距离，单位：米
            double distance = DistanceUtil.getDistance(peo, realLocation);
            if (isFirstData && peo != null && realLocation != null) {
                zoomMap(peo, realLocation);
            }

            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            baiduMap.setMyLocationData(locData);
        }
    }

    /**
     * 缩放地图
     */
    private void zoomMap(LatLng peoLatLng, LatLng carLatLng) {
        mBuilder.include(peoLatLng);
        mBuilder.include(carLatLng);
        if (baiduMap != null) {
            baiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLngBounds(mBuilder.build(), 600, 600));
            zoom = baiduMap.getMapStatus().zoom;
        }
    }

    @Override
    protected void onResume() {
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapView.onResume();
        //开启地图定位图层
        if (mLocationClient != null) {
            mLocationClient.start();
        }
        getCycleData();
        if (!deviceVersion.toUpperCase().equals(FunctionType.C2)){
            if (mCountDown > 0){
                handler.sendEmptyMessage(2);
            }
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapView.onPause();
        if (mLocationClient != null) {
            mLocationClient.stop();
        }
        //关闭定时线程池
        if (scheduledThreadPool != null) {
            scheduledThreadPool.shutdownNow();
        }
        scheduledThreadPool = null;

        handler.removeCallbacksAndMessages(null);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mLocationClient != null) {
            mLocationClient.stop();
        }
        //关闭定时线程池
        if (scheduledThreadPool != null) {
            scheduledThreadPool.shutdownNow();
        }
        scheduledThreadPool = null;

        if (trackInputDialog != null && trackInputDialog.isAdded()){
            trackInputDialog.dismiss();
        }

        handler.removeCallbacksAndMessages(null);
        if (mapView != null) {
            mapView.onDestroy();
        }
        mapView = null;
        super.onDestroy();
    }

    /**
     * 输入追踪时长
     */
    private void onInputTime(){
        trackInputDialog = new RealTimeTrackInputDialog();
        trackInputDialog.show(getSupportFragmentManager(), new RealTimeTrackInputDialog.onRealTimeTrackChange() {
            @Override
            public void onRealTimeTrack(int time) {
                effectiveTime = time;
                mCountDown = effectiveTime * 60;
                submitTempTracking();
            }

            @Override
            public void onCancel() {
                finish();
            }
        });
    }

    /**
     * 判断用户是否打开了GPS
     */
    private void gpsClosePrompt() {
        if (!Utils.isOPenGPS(this)) {
            AlertBean bean = new AlertBean();
            bean.setType(0);
            bean.setAlert(getString(R.string.txt_gps_author));
            AlertAppDialog dialog = new AlertAppDialog();
            dialog.show(getSupportFragmentManager(), bean, new AlertAppDialog.onAlertDialogChange() {
                @Override
                public void onConfirm() {
                    // 转到手机设置界面，用户设置GPS
                    Intent intent = new Intent(
                            Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(intent, 0); // 设置完成后返回到原来的界面
                }

                @Override
                public void onCancel() {

                }
            });
        }
    }

    /**
     * 周期线程池，刷新定位数据，下发定位指令
     */
    public void getCycleData() {
        if (scheduledThreadPool != null) {
            scheduledThreadPool.shutdownNow();
        }
        scheduledThreadPool = Executors.newScheduledThreadPool(5);
        // 定时5s执行一次
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            public void run() {
                handler.sendEmptyMessage(1);
            }
        }, 0, 5, TimeUnit.SECONDS);
        if (deviceVersion.toUpperCase().equals(FunctionType.C2)){
            scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
                public void run() {
                    handler.sendEmptyMessage(3);
                }
            }, 0, 60, TimeUnit.SECONDS);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                refreshDeviceLocation();
            } else if (msg.what == 2) {
                mCountDown--;
                if (mCountDown <= 0){
                    finish();
                }else{
                    handler.sendEmptyMessageDelayed(2, 1000);
                }
            }else if (msg.what == 3){
                submitTempTracking();
            }
        }
    };

    /**
     * 下发实时追踪指令
     */
    private void submitTempTracking(){
        RealTimeTrackPutBean.ParamsBean paramsBean = new RealTimeTrackPutBean.ParamsBean();
        paramsBean.setSimei(mSimei);
        paramsBean.setEffective_time(effectiveTime);
        paramsBean.setInterval_time(intervalTime);
        paramsBean.setIs_add_log(isFirstData);

        RealTimeTrackPutBean bean = new RealTimeTrackPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Real_Time_Track);
        bean.setModule(ModuleValueService.Module_For_Real_Time_Track);

        if (getPresenter() != null){
            getPresenter().submitTempTracking(bean);
        }
    }

    /**
     * 刷新定位数据
     */
    private void refreshDeviceLocation(){
        DeviceListPutBean.ParamsBean paramsBean = new DeviceListPutBean.ParamsBean();
        paramsBean.setLimit_size(limitSizeForDevice);
        if (mSimeiLists.size() > 0) {
            paramsBean.setSimei(mSimeiLists);
        }
        DeviceListPutBean bean = new DeviceListPutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Device_List);
        bean.setModule(ModuleValueService.Module_For_Device_List);
        bean.setParams(paramsBean);

        getPresenter().getDeviceList(bean);
    }

    /**
     * 刷新地图界面
     * @param latLngs 经纬度
     * @param direction 旋转角度
     */
    private void updateMapDisplay(LatLng latLngs, float direction) {
        // 2.汽车小标
        if (carMarker != null) {
            carMarker.setPosition(latLngs);
            carMarker.setIcon(getMarkerIcon(direction));
        } else {
            carMarker = (Marker) baiduMap.addOverlay(new MarkerOptions().zIndex(1)
                    .position(latLngs)
                    .draggable(false)
                    .icon(getMarkerIcon(direction)));
        }
        mBuilder.include(latLngs);
        baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().target(latLngs).zoom(zoom).build()));
        followLat.add(latLngs);
        drawPolyline();
    }

    /**
     * 绘制轨迹线
     */
    private void drawPolyline(){
        int color = Color.rgb(50, 205, 50);
        if (followLat.size() > 2) {
            baiduMap.addOverlay(new PolylineOptions().points(followLat).width(8).color(color));
        }
    }

    /**
     * 绘制设备图标等信息
     *
     * @return
     */
    private BitmapDescriptor getMarkerIcon(float direction) {
        View view = View.inflate(this, R.layout.layout_marker_item, null);
        TextView tvName = view.findViewById(R.id.tv_name);
        ImageView ivCar = view.findViewById(R.id.iv_car);
        tvName.setText(deviceNumber);
        ivCar.setImageResource(drawableId());
        if (carImageId == 0 || carImageId == 1 || carImageId == 2) {
            ivCar.setRotation(direction);//旋转
        }

        getDeviceState(tvName);
        return convertViewToBitmap(view);
    }

    private static BitmapDescriptor convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        return BitmapDescriptorFactory.fromBitmap(view.getDrawingCache());
    }

    private void getDeviceState(TextView tvView) {
        if (deviceState.equals(ResultDataUtils.Device_State_Line_On)) {
            tvView.setTextColor(getResources().getColor(R.color.color_73CA6C));
            tvView.setBackgroundResource(R.mipmap.icon_online_bg);
        } else if (deviceState.equals(ResultDataUtils.Device_State_Line_Sleep)) {
            tvView.setTextColor(getResources().getColor(R.color.color_F22E13));
            tvView.setBackgroundResource(R.mipmap.icon_static_bg);
        } else {
            tvView.setTextColor(getResources().getColor(R.color.color_AAAAAA));
            tvView.setBackgroundResource(R.mipmap.icon_offline_bg);
        }
    }

    /**
     * 显示对于的图标
     *
     * @return
     */
    private int drawableId() {
        int drawable = R.mipmap.icon_car_online;
        if (deviceState.equals(ResultDataUtils.Device_State_Line_On)) {
            switch (carImageId) {
                case 0:
                    drawable =  R.mipmap.icon_car_online;
                    break;
                case 1:
                    drawable = R.mipmap.icon_car_online_1;
                    break;
                case 2:
                    drawable =  R.mipmap.icon_car_online_2;
                    break;
                case 3:
                    drawable =  R.mipmap.icon_car_online_3;
                    break;
                case 4:
                    drawable =  R.mipmap.icon_car_online_4;
                    break;
            }
        } else if (deviceState.equals(ResultDataUtils.Device_State_Line_Sleep)) {
            switch (carImageId) {
                case 0:
                    drawable = R.mipmap.icon_car_sleep;
                    break;
                case 1:
                    drawable = R.mipmap.icon_car_sleep_1;
                    break;
                case 2:
                    drawable = R.mipmap.icon_car_sleep_2;
                    break;
                case 3:
                    drawable = R.mipmap.icon_car_sleep_3;
                    break;
                case 4:
                    drawable = R.mipmap.icon_car_sleep_4;
                    break;
            }
        } else {
            switch (carImageId) {
                case 0:
                    drawable = R.mipmap.icon_car_offline;
                    break;
                case 1:
                    drawable = R.mipmap.icon_car_offline_1;
                    break;
                case 2:
                    drawable = R.mipmap.icon_car_offline_2;
                    break;
                case 3:
                    drawable = R.mipmap.icon_car_offline_3;
                    break;
                case 4:
                    drawable = R.mipmap.icon_car_offline_4;
                    break;
            }
        }
        return drawable;
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
