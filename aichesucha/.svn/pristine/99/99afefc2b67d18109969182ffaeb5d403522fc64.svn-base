package com.car.scth.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.utils.DistanceUtil;
import com.blankj.utilcode.util.SPUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.car.scth.R;
import com.car.scth.app.MyApplication;
import com.car.scth.di.component.DaggerNavigationBaiduComponent;
import com.car.scth.mvp.contract.NavigationBaiduContract;
import com.car.scth.mvp.model.bean.DeviceLocationInfoBean;
import com.car.scth.mvp.presenter.NavigationBaiduPresenter;
import com.car.scth.mvp.utils.BitmapHelperBaidu;
import com.car.scth.mvp.utils.ConstantValue;
import com.car.scth.mvp.utils.DeviceUtils;
import com.car.scth.mvp.utils.LocationAddress;
import com.car.scth.mvp.utils.ResultDataUtils;
import com.car.scth.mvp.utils.Utils;
import com.car.scth.mvp.weiget.NavigationDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 导航-百度地图
 * <p>
 * Created by MVPArmsTemplate on 02/23/2021 16:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class NavigationBaiduActivity extends BaseActivity<NavigationBaiduPresenter> implements NavigationBaiduContract.View {

    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.mapView)
    MapView mapView;

    private double deviceLatitude = 0; // 设备纬度
    private double deviceLongitude = 0; // 设备经度
    private String deviceState; // 设备状态
    private long mImei;

    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    private float mZoom = 15;
    private LatLng userLocation;
    private LatLng carLocation;
    private MyLocationConfiguration.LocationMode mCurrentMode; // 定位模式 地图SDK支持三种定位模式：NORMAL（普通态）, FOLLOWING（跟随态）, COMPASS（罗盘态）
    private static final int accuracyCircleFillColor = 0x00FF00FF; // 自定义精度圈填充颜色
    private static final int accuracyCircleStrokeColor = 0x00FF00FF; // 自定义精度圈边框颜色
    private BitmapHelperBaidu bitmapHelper;
    private LatLngBounds.Builder mBuilder; // 所有设备可视化Builder
    private int size;
    private int carImageId; // 设备图标id
    private String mLocInfo; // 设备定位的一些信息
    private float mDirection = 0;// 方向

    public static Intent newInstance() {
        return new Intent(MyApplication.getMyApp(), NavigationBaiduActivity.class);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerNavigationBaiduComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_navigation_baidu;//setContentView(id);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_function_navigation));
        carImageId = MyApplication.getMyApp().getCarImageId();

        bitmapHelper = new BitmapHelperBaidu(this);
        mImei = MyApplication.getMyApp().getImei();
        deviceState = MyApplication.getMyApp().getDeviceState();
        mLocInfo = MyApplication.getMyApp().getLocInfo();
        if (!TextUtils.isEmpty(mLocInfo)) {
            DeviceLocationInfoBean infoBean = DeviceUtils.parseDeviceData(mLocInfo);
            mDirection = infoBean.getDirection();
        }
        mBuilder = new LatLngBounds.Builder();
        SPUtils.getInstance().put(ConstantValue.ACTIVITY_STATUS, false);
        if (!TextUtils.isEmpty(MyApplication.getMyApp().getLatAndLon())) {
            String[] location = MyApplication.getMyApp().getLatAndLon().split(",");
            deviceLatitude = (double) Long.parseLong(location[0]) / 1000000;
            deviceLongitude = (double) Long.parseLong(location[1]) / 1000000;
        }

        carLocation = new LatLng(deviceLatitude, deviceLongitude);
        mBuilder.include(carLocation);

        mBaiduMap = mapView.getMap();
        mapView.showZoomControls(false);
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        onBaiduMapShow();
        new LocationAddress().Parsed(deviceLatitude, deviceLongitude)
                .setAddressListener(str -> {
                    if (tvAddress != null) {
                        if (!TextUtils.isEmpty(str)) {
                            tvAddress.setText(getString(R.string.txt_address) + str);
                        } else {
                            tvAddress.setText(getString(R.string.txt_address) + deviceLatitude + "," + deviceLongitude);
                        }
                    }
                });
    }

    /**
     * 初始化百度地图
     */
    private void onBaiduMapShow() {
        if (mBaiduMap != null) {
            mBaiduMap.setMyLocationEnabled(true);

            //定位初始化
            mLocationClient = new LocationClient(this);

            //通过LocationClientOption设置LocationClient相关参数
            LocationClientOption option = new LocationClientOption();
            option.setOpenGps(true); // 打开gps
            option.setCoorType("gcj02"); // 设置坐标类型
            option.setScanSpan(600000);
            //可选，设置是否需要地址信息，默认不需要
            option.setIsNeedAddress(true);
            //可选，设置是否需要地址描述
            option.setIsNeedLocationDescribe(true);

            //设置locationClientOption
            mLocationClient.setLocOption(option);

            // 自定义定位显示图标
            MyLocationConfiguration locationConfiguration = new MyLocationConfiguration(mCurrentMode,
                    false, bitmapHelper.getBitmapZoomForUserLocation(mZoom), accuracyCircleFillColor, accuracyCircleStrokeColor);
            mBaiduMap.setMyLocationConfiguration(locationConfiguration);

            //注册LocationListener监听器
            MyLocationListener myLocationListener = new MyLocationListener();
            mLocationClient.registerLocationListener(myLocationListener);

            MarkerOptions markerOption = new MarkerOptions();
            markerOption.position(carLocation);
            markerOption.icon(getMarkerIcon());
            markerOption.anchor(0.5f, 1);
            markerOption.draggable(false);
            markerOption.visible(true);

            mBaiduMap.addOverlay(markerOption);
        }
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @SuppressLint("SetTextI18n")
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mapView == null) {
                return;
            }
            userLocation = new LatLng(location.getLatitude(), location.getLongitude());
            mBuilder.include(userLocation);

            if (location.getLatitude() != 0 && location.getLongitude() != 0) {
                mLocationClient.stop();
            }

            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);

            if (userLocation != null && carLocation != null) {
                updateMapCenter();
            }

            //计算p1、p2两点之间的直线距离，单位：米
            double distance = DistanceUtil.getDistance(userLocation, carLocation);
            if (distance >= 1000) {
                tvDistance.setText(Utils.formatValue(distance / 1000) + "km");
            } else {
                tvDistance.setText(Utils.formatValue(distance) + getString(R.string.txt_meter));
            }
        }
    }

    /**
     * 缩放地图
     */
    private void updateMapCenter() {
        if (mBaiduMap != null) {
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLngBounds(mBuilder.build(), 600, 600));
            mZoom = mBaiduMap.getMapStatus().zoom;
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
        super.onResume();
    }

    @Override
    public void onPause() {
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapView.onPause();
        if (mLocationClient != null) {
            mLocationClient.stop();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mLocationClient != null) {
            mLocationClient.stop();
        }
        if (mapView != null) {
            mapView.onDestroy();
        }
        mapView = null;
        super.onDestroy();
    }

    /**
     * 绘制设备图标等信息
     *
     * @return
     */
    private BitmapDescriptor getMarkerIcon() {
        View view = View.inflate(this, R.layout.layout_marker_navigation, null);
        ImageView ivCar = view.findViewById(R.id.iv_car);
        getDeviceState(ivCar);
        return convertViewToBitmap(view);
    }

    private static BitmapDescriptor convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        return BitmapDescriptorFactory.fromBitmap(view.getDrawingCache());
    }

    private void getDeviceState(ImageView ivView) {
        if (ResultDataUtils.Device_State_Line_On.equals(deviceState)) {
            switch (carImageId) {
                case 0:
                    ivView.setImageResource(R.mipmap.icon_car_online);
                    break;
                case 1:
                    ivView.setImageResource(R.mipmap.icon_car_online_1);
                    break;
                case 2:
                    ivView.setImageResource(R.mipmap.icon_car_online_2);
                    break;
                case 3:
                    ivView.setImageResource(R.mipmap.icon_car_online_3);
                    break;
                case 4:
                    ivView.setImageResource(R.mipmap.icon_car_online_4);
                    break;
            }
        } else if (ResultDataUtils.Device_State_Line_Sleep.equals(deviceState)) {
            switch (carImageId) {
                case 0:
                    ivView.setImageResource(R.mipmap.icon_car_sleep);
                    break;
                case 1:
                    ivView.setImageResource(R.mipmap.icon_car_sleep_1);
                    break;
                case 2:
                    ivView.setImageResource(R.mipmap.icon_car_sleep_2);
                    break;
                case 3:
                    ivView.setImageResource(R.mipmap.icon_car_sleep_3);
                    break;
                case 4:
                    ivView.setImageResource(R.mipmap.icon_car_sleep_4);
                    break;
            }
        } else {
            switch (carImageId) {
                case 0:
                    ivView.setImageResource(R.mipmap.icon_car_offline);
                    break;
                case 1:
                    ivView.setImageResource(R.mipmap.icon_car_offline_1);
                    break;
                case 2:
                    ivView.setImageResource(R.mipmap.icon_car_offline_2);
                    break;
                case 3:
                    ivView.setImageResource(R.mipmap.icon_car_offline_3);
                    break;
                case 4:
                    ivView.setImageResource(R.mipmap.icon_car_offline_4);
                    break;
            }
        }
        if (carImageId == 0 || carImageId == 1 || carImageId == 2) {
            ivView.setRotation(mDirection);//旋转
        }
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

    @OnClick(R.id.ll_navigation)
    public void onViewClicked() {
        NavigationDialog dialog = new NavigationDialog();
        dialog.show(getSupportFragmentManager(), String.valueOf(deviceLatitude), String.valueOf(deviceLongitude));
    }
}
