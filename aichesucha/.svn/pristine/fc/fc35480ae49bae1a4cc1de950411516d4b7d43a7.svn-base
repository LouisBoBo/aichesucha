package com.car.scth.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.blankj.utilcode.util.SPUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.car.scth.R;
import com.car.scth.app.MyApplication;
import com.car.scth.di.component.DaggerNavigationAmapComponent;
import com.car.scth.mvp.contract.NavigationAmapContract;
import com.car.scth.mvp.model.bean.DeviceLocationInfoBean;
import com.car.scth.mvp.presenter.NavigationAmapPresenter;
import com.car.scth.mvp.utils.BitmapHelperAmap;
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
 * Description: 导航-高德地图
 * <p>
 * Created by MVPArmsTemplate on 11/04/2020 09:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class NavigationAmapActivity extends BaseActivity<NavigationAmapPresenter> implements NavigationAmapContract.View
        , AMap.OnCameraChangeListener, AMapLocationListener {

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

    private AMap aMap;
    private float mZoom = 15;
    private LatLng userLocation;
    private LatLng carLocation;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private MyLocationStyle myLocationStyle;
    private LatLngBounds bounds;
    private int size;
    private BitmapHelperAmap bitmapHelperAmap;
    private int carImageId; // 设备图标id
    private String mLocInfo; // 设备定位的一些信息
    private float mDirection = 0;// 方向

    public static Intent newInstance() {
        return new Intent(MyApplication.getMyApp(), NavigationAmapActivity.class);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerNavigationAmapComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_navigation_amap; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
        setTitle(getString(R.string.txt_function_navigation));
        carImageId = MyApplication.getMyApp().getCarImageId();

        bitmapHelperAmap = new BitmapHelperAmap(this);
        mImei = MyApplication.getMyApp().getImei();
        deviceState = MyApplication.getMyApp().getDeviceState();
        mLocInfo = MyApplication.getMyApp().getLocInfo();
        if (!TextUtils.isEmpty(mLocInfo)) {
            DeviceLocationInfoBean infoBean = DeviceUtils.parseDeviceData(mLocInfo);
            mDirection = infoBean.getDirection();
        }

        SPUtils.getInstance().put(ConstantValue.ACTIVITY_STATUS, false);

        if (!TextUtils.isEmpty(MyApplication.getMyApp().getLatAndLon())) {
            String[] location = MyApplication.getMyApp().getLatAndLon().split(",");
            deviceLatitude = (double) Long.parseLong(location[0]) / 1000000;
            deviceLongitude = (double) Long.parseLong(location[1]) / 1000000;
        }

        aMap = mapView.getMap();
        aMap.getUiSettings().setZoomControlsEnabled(false);
        aMap.setOnCameraChangeListener(this);
        aMap.setMyLocationEnabled(true);
        carLocation = new LatLng(deviceLatitude, deviceLongitude);
        // 添加设备图标
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(carLocation);
        markerOption.icon(getMarkerIcon());
        markerOption.anchor(0.5f, 1);
        markerOption.draggable(false);
        markerOption.visible(true);

        aMap.addMarker(markerOption);
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(carLocation, mZoom));
        onAMapShow();

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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
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

    /**
     * 初始化高德地图
     */
    private void onAMapShow() {
        mlocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(600000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除

        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(10000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。

        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位一次，且将视角移动到地图中心点。

        myLocationStyle.myLocationIcon(bitmapHelperAmap.getBitmapZoomForUserLocation(mZoom));//设置定位蓝点的icon图标方法，需要用到BitmapDescriptor类对象作为参数。
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
        // 设置定位监听
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationEnabled(true);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
//                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
//                aMapLocation.getLatitude();//获取纬度
//                aMapLocation.getLongitude();//获取经度
//                aMapLocation.getAccuracy();//获取精度信息

                userLocation = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                // 计算量坐标点距离
                float distance = AMapUtils.calculateLineDistance(userLocation, carLocation);
                calcuteSize(distance);

                if (userLocation != null && carLocation != null) {
                    zoomMap(userLocation, carLocation);
                }

                if (distance >= 1000) {
                    tvDistance.setText(Utils.formatValue(distance / 1000) + "km");
                } else {
                    tvDistance.setText(Utils.formatValue(distance) + getString(R.string.txt_meter));
                }

                if (mlocationClient != null) {
                    mlocationClient.stopLocation();
                }
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
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
        aMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, size));
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

    @OnClick(R.id.ll_navigation)
    public void onViewClicked() {
        NavigationDialog dialog = new NavigationDialog();
        dialog.show(getSupportFragmentManager(), String.valueOf(deviceLatitude), String.valueOf(deviceLongitude));
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
    protected void onResume() {
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapView.onResume();
        if (mlocationClient != null) {
            mlocationClient.startLocation();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
            mlocationClient = null;
        }
        if (mapView != null) {
            mapView.onDestroy();
        }
        super.onDestroy();
    }

}
