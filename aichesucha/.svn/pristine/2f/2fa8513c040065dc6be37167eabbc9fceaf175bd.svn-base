package com.car.scth.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.geocoder.RegeocodeRoad;
import com.baidu.lbsapi.BMapManager;
import com.baidu.lbsapi.panoramaview.PanoramaView;
import com.baidu.lbsapi.panoramaview.PanoramaViewListener;
import com.baidu.lbsapi.tools.Point;
import com.blankj.utilcode.util.SPUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.car.scth.R;
import com.car.scth.app.MyApplication;
import com.car.scth.di.component.DaggerBaiduPanoramaComponent;
import com.car.scth.mvp.contract.BaiduPanoramaContract;
import com.car.scth.mvp.presenter.BaiduPanoramaPresenter;
import com.car.scth.mvp.utils.ConstantValue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.car.scth.mvp.utils.LocationAddress;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 街景地图
 * <p>
 * Created by MVPArmsTemplate on 02/22/2021 17:21
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class BaiduPanoramaActivity extends BaseActivity<BaiduPanoramaPresenter> implements BaiduPanoramaContract.View {

    @BindView(R.id.panoramaView)
    PanoramaView panoramaView;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_address)
    TextView tvAddress;

    private double deviceLatitude = 0; // 设备纬度
    private double deviceLongitude = 0; // 设备经度
    private String carName; // 设备名称
    private String mImei; // 设备imei号

    public static Intent newInstance() {
        return new Intent(MyApplication.getMyApp(), BaiduPanoramaActivity.class);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBaiduPanoramaComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_baidu_panorama;//setContentView(id);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        SPUtils.getInstance().put(ConstantValue.ACTIVITY_STATUS, false);
        setTitle(getString(R.string.txt_street_view));
        initBMapManager();
        carName = MyApplication.getMyApp().getCarName();
        mImei = MyApplication.getMyApp().getImei() + "";
        if (!TextUtils.isEmpty(MyApplication.getMyApp().getLatAndLon())){
            String[] location = MyApplication.getMyApp().getLatAndLon().split(",");
            deviceLatitude = (double) Long.parseLong(location[0]) / 1000000;
            deviceLongitude = (double) Long.parseLong(location[1]) / 1000000;
        }
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

        if (!TextUtils.isEmpty(carName)){
            tvName.setText(getString(R.string.txt_device_name) + carName);
        }else{
            tvName.setText(getString(R.string.txt_device_name) + mImei);
        }

        panoramaView.setShowTopoLink(true);

        // 测试回调函数,需要注意的是回调函数要在setPanorama()之前调用，否则回调函数可能执行异常
        panoramaView.setPanoramaViewListener(new PanoramaViewListener() {

            @Override
            public void onLoadPanoramaBegin() {

            }

            @Override
            public void onLoadPanoramaEnd(String json) {

            }

            @Override
            public void onLoadPanoramaError(String error) {

            }

            @Override
            public void onDescriptionLoadEnd(String json) {

            }

            @Override
            public void onMessage(String msgName, int msgType) {

            }

            @Override
            public void onCustomMarkerClick(String key) {

            }

            @Override
            public void onMoveStart() {

            }

            @Override
            public void onMoveEnd() {

            }
        });

        // 将84坐标转换成百度坐标
        Point sourcePoint = new Point(deviceLongitude, deviceLatitude);
//        Point resultPointLL = CoordinateConverter.converter(CoordinateConverter.COOR_TYPE.COOR_TYPE_WGS84, sourcePoint);

        panoramaView.setPanoramaImageLevel(PanoramaView.ImageDefinition.ImageDefinitionMiddle);
        panoramaView.setPanorama(sourcePoint.x, sourcePoint.y, PanoramaView.COORDTYPE_GCJ02);
    }

    private void initBMapManager() {
        MyApplication app = (MyApplication) this.getApplication();
        if (app.mBMapManager == null) {
            app.mBMapManager = new BMapManager(app);
            app.mBMapManager.init(new MyApplication.MyGeneralListener());
        }
    }

    @Override
    protected void onResume() {
        panoramaView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        panoramaView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        panoramaView.destroy();
        super.onDestroy();
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
