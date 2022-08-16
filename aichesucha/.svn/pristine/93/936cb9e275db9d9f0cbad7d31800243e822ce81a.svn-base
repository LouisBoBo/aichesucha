package com.car.scth.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.route.RouteSearch;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.car.scth.R;
import com.car.scth.app.MyApplication;
import com.car.scth.di.component.DaggerRealTimeTrackAmapComponent;
import com.car.scth.mvp.contract.RealTimeTrackAmapContract;
import com.car.scth.mvp.model.api.ModuleValueService;
import com.car.scth.mvp.model.bean.AlertBean;
import com.car.scth.mvp.model.bean.DeviceListResultBean;
import com.car.scth.mvp.model.bean.DeviceLocationInfoBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.DeviceListPutBean;
import com.car.scth.mvp.model.putbean.RealTimeTrackPutBean;
import com.car.scth.mvp.presenter.RealTimeTrackAmapPresenter;
import com.car.scth.mvp.utils.BitmapHelperAmap;
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
 * Description: 实时追踪-高德地图
 * <p>
 * Created by MVPArmsTemplate on 11/05/2020 08:54
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class RealTimeTrackAmapActivity extends BaseActivity<RealTimeTrackAmapPresenter> implements RealTimeTrackAmapContract.View,
        AMapLocationListener, AMap.OnCameraChangeListener, LocationSource {

    @BindView(R.id.mapView)
    MapView mapView;

    private AMap aMap;
    private RouteSearch routeSearch;
    private Marker carMarker;
    private Marker myLocationMarker;
    private boolean firstzoom = true;
    private float mZoom = 16;

    private ScheduledExecutorService scheduledThreadPool;
    private int size;
    private LatLng peo;
    private LatLng realLocation;

    private LatLngBounds bounds;
    private List<String> mSimeiLists;//实时请求imei号

    //绘制轨迹线
    public List<LatLng> followLat = new ArrayList<>();
    private Polyline polyline;

    private long mImei;
    private String mSimei; // 设备imei号
    private String deviceNumber; // 设备名称
    private String deviceState; // 设备状态
    private String latAndLon = ""; // 设备经纬度
    private String deviceVersion; // 设备版本
    private int carImageId; // 设备图标id

    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private BitmapHelperAmap bitmapHelperAmap;

    private int intervalTime = 5; // 跟踪控制的设备上报时间间隔 0-取消追踪，单位s
    private int effectiveTime = 1; // 位置跟踪有效期，单位min
    private int limitSizeForDevice = 10; // 最大获取设备列表数量限制
    private double mLatitude = 0; // 设备的经纬度
    private double mLongitude = 0; // 设备的经纬度
    private int mCountDown = 0; // 实时追踪倒计时指令
    private RealTimeTrackInputDialog trackInputDialog; // 时间填写dialog
    private boolean isFirstData = true; // 是否是第一次下发实时追踪指令

    public static Intent newInstance() {
        return new Intent(MyApplication.getMyApp(), RealTimeTrackAmapActivity.class);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRealTimeTrackAmapComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_real_time_track_amap; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
        setTitle(getString(R.string.txt_function_realtime_tracking));
        carImageId = MyApplication.getMyApp().getCarImageId();

        mImei = MyApplication.getMyApp().getImei();
        mSimei = MyApplication.getMyApp().getSimei();
        deviceState = MyApplication.getMyApp().getDeviceState();
        latAndLon = MyApplication.getMyApp().getLatAndLon();
        deviceVersion = MyApplication.getMyApp().getVersion();
        bitmapHelperAmap = new BitmapHelperAmap(this);

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

        aMap = mapView.getMap();
        aMap.setLocationSource(this);
        aMap.setOnCameraChangeListener(this);
        aMap.setMyLocationEnabled(true);
        aMap.getUiSettings().setZoomControlsEnabled(false);//缩放按钮
        routeSearch = new RouteSearch(this);

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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
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
     */
    private void updateMapDisplay(LatLng latLngs, float direction) {
        // 2.汽车小标
        if (carMarker != null) {
            carMarker.setPosition(latLngs);
            carMarker.setIcon(getMarkerIcon(direction));
//            carMarker.setRotateAngle(360-direction);
        } else {
            carMarker = aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.7f)
                    .position(latLngs).icon(getMarkerIcon(direction)));
//            carMarker.setRotateAngle(360-direction);
        }
        aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngs, mZoom));
        followLat.add(latLngs);
        drawPolyline();
    }

    /**
     * 将我的位置的坐标添加进去
     *
     * @param location
     */
    private void addMyLocalMaker(LatLng location) {
        if (location != null) {
            if (myLocationMarker == null) {
                myLocationMarker = aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f).position(location).icon(bitmapHelperAmap.getBitmapZoomForUserLocation(mZoom)).zIndex(1));
            } else {
                myLocationMarker.setPosition(location);
            }
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
     * 计算地图缩放距离边距距离
     *
     * @param distance 两经纬度之间的距离
     */
    private void calcuteSize(float distance) {
        if (distance <= 5000) {
            size = 120;
        } else if (distance > 5000 && distance <= 10000) {
            size = 150;
        } else if (distance > 10000 && distance < 1000000) {
            size = 180;
        } else if (distance > 100000 && distance < 500000) {
            size = 200;
        } else if (distance > 500000 && distance < 1000000) {
            size = 250;
        } else if (distance > 1000000) {
            size = 300;
        }
    }

    /**
     * 缩放地图
     */
    private void zoomMap(LatLng peoLatLng, LatLng carLatLng) {
        bounds = new LatLngBounds.Builder()
                .include(peoLatLng)
                .include(carLatLng)
                .build();
        aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, size));
    }

    /**
     * 绘制关注线
     */
    public void drawPolyline() {
        int color = Color.rgb(50, 205, 50);
        if (followLat != null && followLat.size() >= 2) {
            if (polyline == null) {
                polyline = aMap.addPolyline(new PolylineOptions().addAll(followLat).width(8).color(color));
            } else {
                polyline.setPoints(followLat);
            }
        }
    }

    /**
     * 取消关注
     */
    public void cancelFollow() {
        followLat.clear();
        if (polyline != null) {
            polyline.remove();
        }
    }

    @Override
    protected void onResume() {
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapView.onResume();
//        gpsClosePrompt();
        getCycleData();
        if (!deviceVersion.toUpperCase().equals(FunctionType.C2)){
            if (mCountDown > 0){
                handler.sendEmptyMessage(2);
            }
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
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
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        if (mapView != null) {
            mapView.onDestroy();
        }
        cancelFollow();
        //销毁定位功能
        deactivate();

        //关闭定时线程池
        if (scheduledThreadPool != null) {
            scheduledThreadPool.shutdownNow();
        }
        scheduledThreadPool = null;

        if (routeSearch != null) {
            routeSearch.setRouteSearchListener(null);
            routeSearch = null;
        }
        if (trackInputDialog != null && trackInputDialog.isAdded()){
            trackInputDialog.dismiss();
        }

        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                if (aMapLocation.getLatitude() <= 0 || aMapLocation.getLongitude() <= 0) {
                    return;
                }
                peo = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                addMyLocalMaker(peo);

                // 计算量坐标点距离
                float distance = AMapUtils.calculateLineDistance(peo, new LatLng(mLatitude, mLongitude));
                calcuteSize(distance);

                if (peo != null && realLocation != null && firstzoom) {
                    zoomMap(peo, realLocation);
                    firstzoom = false;
                }
            } else {
                String errText = getString(R.string.txt_position_failed) + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        mZoom = cameraPosition.zoom;
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            //初始化定位
            mlocationClient = new AMapLocationClient(this);
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            mLocationOption.setHttpTimeOut(30);
            mLocationOption.setInterval(30 * 1000);
            //设置定位回调监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();//启动定位
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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

}
