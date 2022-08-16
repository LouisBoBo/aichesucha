package com.car.scth.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.fence.GeoFence;
import com.amap.api.fence.GeoFenceClient;
import com.amap.api.fence.GeoFenceListener;
import com.amap.api.location.DPoint;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Circle;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Polygon;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.StringUtils;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.car.scth.R;
import com.car.scth.app.MyApplication;
import com.car.scth.di.component.DaggerFenceCreateBaiduComponent;
import com.car.scth.mvp.contract.FenceCreateBaiduContract;
import com.car.scth.mvp.model.api.ModuleValueService;
import com.car.scth.mvp.model.bean.DeviceBaseResultBean;
import com.car.scth.mvp.model.bean.DeviceLocationInfoBean;
import com.car.scth.mvp.model.bean.FenceAddResultBean;
import com.car.scth.mvp.model.bean.FenceAlarmTypeBean;
import com.car.scth.mvp.model.bean.FenceInfoModifyBean;
import com.car.scth.mvp.model.bean.FencePointBean;
import com.car.scth.mvp.model.bean.FenceResultBean;
import com.car.scth.mvp.model.bean.JsonBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.FenceAddPutBean;
import com.car.scth.mvp.model.putbean.FenceListPutBean;
import com.car.scth.mvp.model.putbean.FenceModifyPutBean;
import com.car.scth.mvp.presenter.FenceCreateBaiduPresenter;
import com.car.scth.mvp.ui.adapter.FenceAlarmTypeAdapter;
import com.car.scth.mvp.ui.view.VerticalSeekBar;
import com.car.scth.mvp.utils.BitmapHelperBaidu;
import com.car.scth.mvp.utils.CharsFilters;
import com.car.scth.mvp.utils.DeviceUtils;
import com.car.scth.mvp.utils.GetJsonDataUtil;
import com.car.scth.mvp.utils.ResultDataUtils;
import com.car.scth.mvp.utils.ToastUtils;
import com.car.scth.mvp.utils.Utils;
import com.car.scth.mvp.weiget.DeviceFailDialog;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 围栏-百度地图
 * <p>
 * Created by MVPArmsTemplate on 02/23/2021 16:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class FenceCreateBaiduActivity extends BaseActivity<FenceCreateBaiduPresenter> implements FenceCreateBaiduContract.View,
        BaiduMap.OnMapClickListener,
        BaiduMap.OnMapStatusChangeListener, GeoFenceListener {

    @BindView(R.id.seekBar_progress)
    VerticalSeekBar seekBarProgress;
    @BindView(R.id.tv_round)
    TextView tvRound; // 圆形围栏
    @BindView(R.id.view_round)
    View viewRound; // 圆形围栏
    @BindView(R.id.ll_round)
    LinearLayout llRound; // 圆形围栏
    @BindView(R.id.tv_polygonal)
    TextView tvPolygonal; // 多边形围栏
    @BindView(R.id.view_polygonal)
    View viewPolygonal; // 多边形围栏
    @BindView(R.id.ll_polygonal)
    LinearLayout llPolygonal; // 多边形围栏
    @BindView(R.id.edt_name)
    EditText edtName; // 名称
    @BindView(R.id.btn_save)
    Button btnSave; // 保存
    @BindView(R.id.tv_revoke)
    TextView tvRevoke; // 撤销
    @BindView(R.id.tv_clear)
    TextView tvClear; // 清楚
    @BindView(R.id.ll_clear_function)
    LinearLayout llClearFunction; // 撤销清除布局
    @BindView(R.id.tv_radius)
    TextView tvRadius; // 半径
    @BindView(R.id.mapView)
    MapView mapView; // 地图
    @BindView(R.id.ll_seekBar_progress)
    LinearLayout llSeekBarProgress; // 圆形围栏
    @BindView(R.id.iv_enter)
    ImageView ivEnter; // 进围栏
    @BindView(R.id.iv_out)
    ImageView ivOut; // 出围栏
    @BindView(R.id.iv_enter_out)
    ImageView ivEnterOut; // 进出围栏
    @BindView(R.id.iv_close)
    ImageView ivClose; // 关闭


    // 解析本地省市区数据
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private static boolean isLoaded = false; // 是否解析成功
    private ArrayList<JsonBean> cityJsonBean; // 省市列表总数据
    private ArrayList<ArrayList<String>> cityBeans; // 城市数据
    private ArrayList<ArrayList<ArrayList<String>>> areaShowBeans; // 区域数据，用于显示
    private ArrayList<ArrayList<ArrayList<JsonBean.CityBean.AreaBean>>> areaBeans; // 区域数据

    // 围栏报警类型
    private List<FenceAlarmTypeBean> alarmTypeBeans;

    private String mSimei; // 设备的imei号
    private int limitSize = 20; // 每页最大加载条数
    private DeviceLocationInfoBean infoBean;
    private String fenceType = ResultDataUtils.Fence_Round; // 围栏类型，圆形围栏，地级市围栏，多边形围栏
    private String fenceAlarmType = ResultDataUtils.Fence_Alarm_In_Out; // 围栏报警类型
    private FenceInfoModifyBean mBean; // 查看 or 修改围栏传递过来的参数
    // 是否是首次进来创建新的围栏 or 切换围栏类型
    private boolean isNullFence = true;
    private boolean isFirst = true; // 是否是首次进来
    private List<String> mSimeiList; // 设备号,如果设备号登入或者设置sgid，可以不用填写

    private BaiduMap mBaiduMap;

    //圆形围栏
    private Circle fenceCircle = null;
    private Marker carMarker = null;
    private LatLng carLatLng = null;
    private int mRadiusProcess = 500;

    //绘制多边形围栏
    private List<LatLng> polygon = new ArrayList<>();
    private List<Marker> listMarker = new ArrayList<>();
    private Marker marker = null;
    private Polyline polyline = null;//轨迹线

    // 区域地理围栏
    private List<LatLng> listLatLon = new ArrayList<>();
    private List<Polygon> listPolygon = new ArrayList<>();//区域线
    private List<Circle> listCircle = new ArrayList<>();//区域背景颜色
    private String cityFence = ""; // 需要上传的省份or城市名称
    private String districtFence = ""; // 需要上传的城市下的区域编码
    private GeoFenceClient fenceClient = null;
    private boolean isRepaint = false;//是否重绘

    // 设置一个默认经纬度，避免没有定位数据的设备登录，导致程序崩溃
    private double mLatitude = 39.90923; // 设备经纬度
    private double mLongitude = 116.397428;
    private LatLng centerPoint = new LatLng(39.90923, 116.397428);
    private float zoom = 17;
    private int carImageId; // 设备图标id
    private String deviceState; // 设备状态
    private int color = Color.rgb(47,217,167);

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFenceCreateBaiduComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_fence_create_baidu_new;//setContentView(id);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        cityJsonBean = new ArrayList<>();
        cityBeans = new ArrayList<>();
        areaBeans = new ArrayList<>();
        areaShowBeans = new ArrayList<>();
        alarmTypeBeans = new ArrayList<>();
        mSimeiList = new ArrayList<>();
        mSimei = MyApplication.getMyApp().getSimei();
        infoBean = DeviceUtils.parseDeviceData(MyApplication.getMyApp().getLocInfo());
        carImageId = MyApplication.getMyApp().getCarImageId();
        deviceState = MyApplication.getMyApp().getDeviceState();
//        // 开始解析
//        mHandler.sendEmptyMessage(MSG_LOAD_DATA);

        if (!TextUtils.isEmpty(mSimei)) {
            mSimeiList.add(mSimei);
        }
        if (getIntent() != null && getIntent().hasExtra("bean")) {
            mBean = (FenceInfoModifyBean) getIntent().getSerializableExtra("bean");
        }
        if (mBean != null) {
            fenceType = mBean.getType();
            fenceAlarmType = mBean.getFenceAlarmType();
        }

        fenceClient = new GeoFenceClient(getApplicationContext());// 初始化地理围栏

        fenceClient.setGeoFenceListener(this);
        fenceClient.setActivateAction(GeoFenceClient.GEOFENCE_IN);//设置地理围栏的触发行为,默认为进入

        onSeekBarProgress();
        initView();
        onCheckFenceType(fenceType);
        onCheckFenceAlarmType(fenceAlarmType);
    }


    /**
     * SeekBar属性及事件
     */
    private void onSeekBarProgress() {
        seekBarProgress.setThumbSizePx(100, 100);
        seekBarProgress.setProgress(500);
        seekBarProgress.setMaxProgress(100000);
        seekBarProgress.setOrientation(0);
        seekBarProgress.setOnSlideChangeListener(new VerticalSeekBar.SlideChangeListener() {
            @Override
            public void onStart(VerticalSeekBar slideView, int progress) {

            }

            @Override
            public void onProgress(VerticalSeekBar slideView, int progress) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onStop(VerticalSeekBar slideView, int progress) {
                mRadiusProcess = progress;
                if (mRadiusProcess < 500) {
                    seekBarProgress.setProgress(500);
                    mRadiusProcess = 500;
                }

                tvRadius.setText(mRadiusProcess + getString(R.string.txt_meter));
                if (fenceCircle != null) {
                    fenceCircle.setRadius(mRadiusProcess);
                    adjustCamera(fenceCircle.getCenter(), mRadiusProcess);
                }
            }
        });
    }


    @SuppressLint("SetTextI18n")
    private void initView() {
        // 设置过滤器，限制只能输入汉字,英文，数字
        edtName.setFilters(new InputFilter[]{new CharsFilters()});

        mLatitude = (double) infoBean.getLat() / 1000000;
        mLongitude = (double) infoBean.getLon() / 1000000;

        if (mBaiduMap == null) {
            mBaiduMap = mapView.getMap();
            mapView.showZoomControls(false);
            mBaiduMap.getUiSettings().setZoomGesturesEnabled(true);
            mBaiduMap.setMyLocationEnabled(true);
            mBaiduMap.setMaxAndMinZoomLevel(20, 4);

            mBaiduMap.setOnMapClickListener(this);
            mBaiduMap.setOnMapStatusChangeListener(this);
        }

        //小车
        carLatLng = new LatLng(mLatitude, mLongitude);

        if (mBean == null) {
            setTitle(getString(R.string.txt_create_fence));
            //没有这个围栏,说明是新增围栏
            isNullFence = true;
            updateCarLocation();
        } else {
            setTitle(getString(R.string.txt_modify_fence));
            //有这个围栏说明是修改围栏
            edtName.setText(mBean.getName());
            edtName.setSelection(edtName.getText().toString().trim().length());

            if (fenceType.equals(ResultDataUtils.Fence_Round)) {
                isNullFence = false;
                mRadiusProcess = mBean.getRadius();
                seekBarProgress.setProgress(mRadiusProcess);
                tvRadius.setText(mRadiusProcess + getString(R.string.txt_meter));
                zoom = BitmapHelperBaidu.getZoomLevel(mRadiusProcess);
                LatLng latLng;
                if (mBean.getLat() == 0 || mBean.getLon() == 0) {
                    ToastUtils.show(getString(R.string.txt_please_modify_fence));
                } else {
                    latLng = new LatLng(mBean.getLat(), mBean.getLon());
                    setDrawCircle(latLng, mRadiusProcess);//绘制圆形围栏
                }
            } else if (fenceType.equals(ResultDataUtils.Fence_City)) {
                zoom = 8;
                cityFence = mBean.getCityName();
                districtFence = mBean.getDistrictName();
//                tvProvinceCity.setText(cityFence);
                if (fenceClient != null) {
                    fenceClient.addGeoFence(StringUtils.null2Length0(districtFence), "");
                }
            } else {
                for (int i = 0; mBean.getPointBeans() != null && i < mBean.getPointBeans().size(); i++) {
                    FencePointBean bean = mBean.getPointBeans().get(i);
                    polygon.add(new LatLng(bean.getLat(), bean.getLon()));
                }
                setPolygonArrMarker(polygon);
                setDrawLine(polygon);
            }
        }

        if (carMarker == null) {
            carMarker = (Marker) mBaiduMap.addOverlay(new MarkerOptions().zIndex(100)
                    .position(carLatLng)
                    .anchor(0.5f, 0.5f)
                    .icon(BitmapDescriptorFactory.fromResource(drawableId())));
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

    @Override
    protected void onResume() {
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mapView != null) {
            mapView.onDestroy();
        }
        mapView = null;
        super.onDestroy();
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (fenceType.equals(ResultDataUtils.Fence_Round)) {
            int radius = seekBarProgress.getProgress();
            setDrawCircle(latLng, radius);//绘制圆形
        } else if (fenceType.equals(ResultDataUtils.Fence_Polygonal)) {
            if (polygon != null && polygon.size() >= 50) {
                ToastUtils.show(getString(R.string.txt_set_max_50_point));
                return;
            }
            polygon.add(latLng);
            setPolygonMarker(latLng);
            setDrawLine(polygon);
        }
    }

    @Override
    public void onMapPoiClick(MapPoi mapPoi) {

    }

    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus) {

    }

    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

    }

    @Override
    public void onMapStatusChange(MapStatus mapStatus) {

    }

    @Override
    public void onMapStatusChangeFinish(MapStatus mapStatus) {
        zoom = mapStatus.zoom;
        centerPoint = mapStatus.target;
    }

    @Override
    public void onGeoFenceCreateFinished(List<GeoFence> list, int i, String s) {
        try {
            if (i == GeoFence.ADDGEOFENCE_SUCCESS) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (GeoFence fence : list) {
                            drawFence(fence);
                        }
                    }
                }).start();
            } else {
                if (fenceClient != null && !isRepaint) {
                    isRepaint = true;
                    fenceClient.addGeoFence(StringUtils.null2Length0(districtFence), "");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void drawFence(GeoFence fence) {
        switch (fence.getType()) {
            case GeoFence.TYPE_ROUND:
            case GeoFence.TYPE_AMAPPOI:
                break;
            case GeoFence.TYPE_POLYGON:
            case GeoFence.TYPE_DISTRICT:
                drawPolygon(fence);
                break;
            default:
                break;
        }
    }

    /**
     * 绘制区域围栏线
     *
     * @param fence
     */
    private void drawPolygon(GeoFence fence) {
        final List<List<DPoint>> pointList = fence.getPointList();
        if (null == pointList || pointList.isEmpty()) {
            return;
        }
        listLatLon.clear();

        // 设置所有maker显示在当前可视区域地图中
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (List<DPoint> subList : pointList) {
            List<LatLng> lst = new ArrayList<>();

            for (DPoint point : subList) {
                lst.add(new LatLng(point.getLatitude(), point.getLongitude()));
                listLatLon.add(lst.get(lst.size() - 1));
                builder.include(new LatLng(point.getLatitude(), point.getLongitude()));
            }
        }

        mBaiduMap.addOverlay(new PolygonOptions().stroke(new Stroke(5, color))
                .points(listLatLon).fillColor(getResources().getColor(R.color.colorFill)));
        mBaiduMap.addOverlay(new MarkerOptions().anchor(0.5f, 0.5f).position(carLatLng).icon(BitmapDescriptorFactory.fromResource(drawableId())));
    }



    /**
     * 调整到设备视角
     */
    private void updateCarLocation() {
        carLatLng = new LatLng(mLatitude, mLongitude);
        //小车镜头
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().target(carLatLng).zoom(zoom).build()));

        if (carMarker != null) {
            carMarker.setPosition(carLatLng);
        } else {
            carMarker = (Marker) mBaiduMap.addOverlay(new MarkerOptions()
                    .zIndex(100)
                    .anchor(0.5f, 0.5f)
                    .position(carLatLng)
                    .icon(BitmapDescriptorFactory.fromResource(drawableId())));
        }
    }

    /**
     * 绘制圆形围栏
     *
     * @param location
     * @param radius
     */
    private void setDrawCircle(LatLng location, int radius) {
        if (fenceCircle != null) {
            fenceCircle.setCenter(location);
            fenceCircle.setRadius(radius);
        } else {
            fenceCircle = (Circle) mBaiduMap.addOverlay(new CircleOptions().
                    center(location).
                    radius(radius).
                    fillColor(Color.argb(0x33, 0, 0xa4, 0xe4)).
                    stroke(new Stroke(2, getResources().getColor(R.color.color_6EA8FF))));
        }
        adjustCamera(fenceCircle.getCenter(), radius);
    }

    /**
     * 绘制多边形Marker
     */
    private void setPolygonMarker(LatLng latLng) {
        marker = (Marker) mBaiduMap.addOverlay(new MarkerOptions().position(latLng).draggable(false).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_polygon)));
        listMarker.add(marker);
    }

    /**
     * 批量绘制多边形Marker
     */
    private void setPolygonArrMarker(List<LatLng> polygon) {
        for (int i = 0; i < polygon.size(); i++) {
            marker = (Marker) mBaiduMap.addOverlay(new MarkerOptions().position(polygon.get(i)).draggable(false)
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_polygon)));
            listMarker.add(marker);
        }
    }

    private void setDrawLine(List<LatLng> polygon) {
        if (polygon == null || polygon.size() < 2) {
            return;
        }

        final LatLngBounds.Builder bounds = new LatLngBounds.Builder();
        for (int i = 0; i < polygon.size(); i++) {
            bounds.include(polygon.get(i));
        }
        //调整可视范围
        llPolygonal.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLngBounds(bounds.build(), 800, 800));
                zoom = mBaiduMap.getMapStatus().zoom;
            }
        }, 500);

        if (polyline != null) {
            polyline.remove();
        }
        List<LatLng> tmpPoint = new ArrayList<>(polygon);
        tmpPoint.add(polygon.get(0));
        polyline = (Polyline) mBaiduMap.addOverlay(new PolylineOptions().points(tmpPoint).width(8).color(color));
    }

    private void adjustCamera(LatLng centerLatLng, int range) {
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().target(centerLatLng).zoom(BitmapHelperBaidu.getZoomLevel(range)).build()));
    }

    /**
     * 去掉多边围栏 marker  围栏线
     */
    private void getRidOfMarker(boolean isEmpty) {
        if (polyline != null) {
            polyline.remove();
        }
        for (int i = 0; i < listMarker.size(); i++) {
            listMarker.get(i).remove();
        }
        listMarker.clear();
        //是否清空所有数据  否就是撤销   删除最后一条数据
        if (isEmpty) {
            polygon.clear();
        } else {
            if (polygon != null && polygon.size() > 0) {
                polygon.remove(polygon.size() - 1);//删除最后一条数据
            }
        }
    }

    /**
     * 去掉区域围栏
     */
    private void clearData() {
        if (fenceClient != null) {
            if (listPolygon != null) {
                for (int i = 0; i < listPolygon.size(); i++) {
                    listPolygon.get(i).remove();
                }
                listPolygon.clear();
            }

            if (listCircle != null) {
                for (int i = 0; i < listCircle.size(); i++) {
                    listCircle.get(i).remove();
                }
                listCircle.clear();
            }
        }

        cityFence = "";
        districtFence = "";

        if (listLatLon != null) {
            listLatLon.clear();
        }
    }

    @OnClick({R.id.ll_round, R.id.ll_polygonal, R.id.btn_save, R.id.tv_revoke, R.id.tv_clear,
            R.id.iv_enter, R.id.tv_enter, R.id.iv_out, R.id.tv_out,
            R.id.iv_enter_out, R.id.tv_enter_out, R.id.iv_close, R.id.tv_close})
    public void onViewClicked(View view) {
        if (Utils.isButtonQuickClick(System.currentTimeMillis())) {
            switch (view.getId()) {
                case R.id.ll_round: // 圆形围栏
                    if (onModifyFence()) {
                        mBaiduMap.clear();
                        isNullFence = true;
                        onCheckFenceType(ResultDataUtils.Fence_Round);
                    }
                    break;
                case R.id.ll_polygonal: // 多边形围栏
                    if (onModifyFence()) {
                        mBaiduMap.clear();
                        isNullFence = true;
                        onCheckFenceType(ResultDataUtils.Fence_Polygonal);
                    }
                    break;
//                case R.id.ll_city: // 地级市围栏
//                    if (onModifyFence()) {
//                        mBaiduMap.clear();
//                        isNullFence = true;
//                        onCheckFenceType(ResultDataUtils.Fence_City);
//                    }
//                    break;
//                case R.id.tv_province_city: // 选择省市
//                    if (isLoaded) {
//                        hideKeyboard(edtName);
//                        onSelectCity();
//                    } else {
//                        ToastUtils.show(getString(R.string.txt_parse_city_data));
//                        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
//                    }
//                    break;
                case R.id.btn_save: // 保存
                    onSaveConfirm();
                    break;
                case R.id.tv_revoke: // 撤销
                    if (polygon != null && polygon.size() > 0) {
                        getRidOfMarker(false);//清空marker 线
                        if (polygon != null && polygon.size() > 0) {
                            setPolygonArrMarker(polygon);
                            setDrawLine(polygon);
                        }
                    } else {
                        ToastUtils.show(getString(R.string.txt_not_return));
                    }
                    break;
                case R.id.tv_clear: // 清除
                    getRidOfMarker(true);//清空marker 线
                    break;
                case R.id.iv_enter: // 进围栏
                case R.id.tv_enter: // 进围栏
                    onCheckFenceAlarmType(ResultDataUtils.Fence_Alarm_In);
                    break;
                case R.id.iv_out: // 出围栏
                case R.id.tv_out: // 出围栏
                    onCheckFenceAlarmType(ResultDataUtils.Fence_Alarm_Out);
                    break;
                case R.id.iv_enter_out: // 进出围栏
                case R.id.tv_enter_out: // 进出围栏
                    onCheckFenceAlarmType(ResultDataUtils.Fence_Alarm_In_Out);
                    break;
                case R.id.iv_close: // 关闭
                case R.id.tv_close: // 关闭
                    onCheckFenceAlarmType(ResultDataUtils.Fence_Alarm_Close);
                    break;
            }
        }
    }

    /**
     * 切换报警类型
     * @param type 围栏类型，0=进围栏 1=出围栏 2=进出围栏 3=关闭
     */
    private void onCheckFenceAlarmType(String type){
        fenceAlarmType = type;
        ivEnter.setImageResource(R.mipmap.icon_unselected_small);
        ivOut.setImageResource(R.mipmap.icon_unselected_small);
        ivEnterOut.setImageResource(R.mipmap.icon_unselected_small);
        ivClose.setImageResource(R.mipmap.icon_unselected_small);
        switch (fenceAlarmType){
            case ResultDataUtils.Fence_Alarm_In:
                ivEnter.setImageResource(R.mipmap.icon_select_small);
                break;
            case ResultDataUtils.Fence_Alarm_Out:
                ivOut.setImageResource(R.mipmap.icon_select_small);
                break;
            case ResultDataUtils.Fence_Alarm_In_Out:
                ivEnterOut.setImageResource(R.mipmap.icon_select_small);
                break;
            case ResultDataUtils.Fence_Alarm_Close:
                ivClose.setImageResource(R.mipmap.icon_select_small);
                break;
        }
    }

    /**
     * 保存提交
     */
    private void onSaveConfirm() {
        //区域围栏没有选择地方  返回
        if (fenceType.equals(ResultDataUtils.Fence_City)) {
            if (TextUtils.isEmpty(districtFence) && TextUtils.isEmpty(cityFence)) {
                ToastUtils.show(getString(R.string.txt_select_area));
                return;
            }
        } else if (fenceType.equals(ResultDataUtils.Fence_Polygonal)) {
            if (polygon == null) {
                ToastUtils.show(getString(R.string.txt_set_fence));
                return;
            } else if (polygon.size() <= 2) {
                ToastUtils.show(getString(R.string.txt_set_3_fence));
                return;
            } else if (polygon.size() > 50) {
                ToastUtils.show(getString(R.string.txt_set_max_50_point));
                return;
            }
        }

        if (TextUtils.isEmpty(edtName.getText().toString().trim())) {
            ToastUtils.show(getString(R.string.txt_please_fence_name));
            return;
        }

        getFenceList();
    }

    /**
     * 获取围栏列表数据
     */
    private void getFenceList() {
        FenceListPutBean.ParamsBean paramsBean = new FenceListPutBean.ParamsBean();
        paramsBean.setLimit_size(limitSize);
        paramsBean.setSimei(mSimei);

        FenceListPutBean bean = new FenceListPutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Fence_List);
        bean.setModule(ModuleValueService.Module_For_Fence_List);
        bean.setParams(paramsBean);

        if (getPresenter() != null) {
            getPresenter().getFenceList(bean);
        }
    }

    /**
     * 添加围栏
     */
    private void submitAddFence() {
        String fenceName = edtName.getText().toString().trim();

        FenceAddPutBean.ParamsBean paramsBean = new FenceAddPutBean.ParamsBean();
        paramsBean.setFence_switch(fenceAlarmType);
        paramsBean.setName(fenceName);
        if (mSimeiList.size() > 0) {
            paramsBean.setSimei(mSimeiList);
        }
        paramsBean.setType(fenceType);

        FenceAddPutBean.ParamsBean.OfenceBean ofenceBean = new FenceAddPutBean.ParamsBean.OfenceBean();
        // 圆形围栏
        FenceAddPutBean.ParamsBean.OfenceBean.CircleBean circleBean = new FenceAddPutBean.ParamsBean.OfenceBean.CircleBean();
        // 地级市围栏
        FenceAddPutBean.ParamsBean.OfenceBean.CityBean cityBean = new FenceAddPutBean.ParamsBean.OfenceBean.CityBean();
        // 多边形围栏
        FenceAddPutBean.ParamsBean.OfenceBean.PolygonBean polygonBean = new FenceAddPutBean.ParamsBean.OfenceBean.PolygonBean();

        if (fenceType.equals(ResultDataUtils.Fence_Round)) {
            LatLng center = fenceCircle.getCenter();
            long lat = (long) (Double.parseDouble(Utils.formatLatLng(center.latitude)) * 1000000);
            long lon = (long) (Double.parseDouble(Utils.formatLatLng(center.longitude)) * 1000000);
            circleBean.setRadius(mRadiusProcess);
            circleBean.setLat(lat);
            circleBean.setLon(lon);
            ofenceBean.setCircle(circleBean);
        } else if (fenceType.equals(ResultDataUtils.Fence_City)) {
            cityBean.setName(cityFence);
            if (!TextUtils.isEmpty(districtFence)) {
                cityBean.setDistrict(districtFence);
            }
            ofenceBean.setCity(cityBean);
        } else {
            ArrayList<FenceAddPutBean.ParamsBean.OfenceBean.PolygonBean.PoitBean> poitBeans = new ArrayList<>();
            for (int i = 0; i < polygon.size(); i++) {
                FenceAddPutBean.ParamsBean.OfenceBean.PolygonBean.PoitBean point = new FenceAddPutBean.ParamsBean.OfenceBean.PolygonBean.PoitBean();
                long lat = (long) (Double.parseDouble(Utils.formatLatLng(polygon.get(i).latitude)) * 1000000);
                long lon = (long) (Double.parseDouble(Utils.formatLatLng(polygon.get(i).longitude)) * 1000000);
                point.setLat(lat);
                point.setLon(lon);
                poitBeans.add(point);
            }
            polygonBean.setPoit(poitBeans);
            ofenceBean.setPolygon(polygonBean);
        }
        paramsBean.setOfence(ofenceBean);

        FenceAddPutBean bean = new FenceAddPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Fence_Add);
        bean.setModule(ModuleValueService.Module_For_Fence_Add);

        if (getPresenter() != null) {
            getPresenter().submitFenceAdd(bean);
        }
    }

    /**
     * 修改围栏数据
     */
    private void submitModifyFence() {
        String fenceName = edtName.getText().toString().trim();

        FenceModifyPutBean.ParamsBean paramsBean = new FenceModifyPutBean.ParamsBean();
        FenceModifyPutBean.ParamsBean.ItemBean itemBean = new FenceModifyPutBean.ParamsBean.ItemBean();
        itemBean.setFence_switch(fenceAlarmType);
        itemBean.setName(fenceName);
        itemBean.setType(fenceType);
        itemBean.setSfid(mBean.getSfid());

        FenceModifyPutBean.ParamsBean.ItemBean.OfenceBean ofenceBean = new FenceModifyPutBean.ParamsBean.ItemBean.OfenceBean();
        // 圆形围栏
        FenceModifyPutBean.ParamsBean.ItemBean.OfenceBean.CircleBean circleBean = new FenceModifyPutBean.ParamsBean.ItemBean.OfenceBean.CircleBean();
        // 地级市围栏
        FenceModifyPutBean.ParamsBean.ItemBean.OfenceBean.CityBean cityBean = new FenceModifyPutBean.ParamsBean.ItemBean.OfenceBean.CityBean();
        // 多边形围栏
        FenceModifyPutBean.ParamsBean.ItemBean.OfenceBean.PolygonBean polygonBean = new FenceModifyPutBean.ParamsBean.ItemBean.OfenceBean.PolygonBean();

        if (fenceType.equals(ResultDataUtils.Fence_Round)) {
            LatLng center = fenceCircle.getCenter();
            long lat = (long) (Double.parseDouble(Utils.formatLatLng(center.latitude)) * 1000000);
            long lon = (long) (Double.parseDouble(Utils.formatLatLng(center.longitude)) * 1000000);
            circleBean.setRadius(mRadiusProcess);
            circleBean.setLat(lat);
            circleBean.setLon(lon);
            ofenceBean.setCircle(circleBean);
        } else if (fenceType.equals(ResultDataUtils.Fence_City)) {
            cityBean.setName(cityFence);
            if (!TextUtils.isEmpty(districtFence)) {
                cityBean.setDistrict(districtFence);
            }
            ofenceBean.setCity(cityBean);
        } else {
            ArrayList<FenceModifyPutBean.ParamsBean.ItemBean.OfenceBean.PolygonBean.PoitBean> poitBeans = new ArrayList<>();
            for (int i = 0; i < polygon.size(); i++) {
                FenceModifyPutBean.ParamsBean.ItemBean.OfenceBean.PolygonBean.PoitBean point = new FenceModifyPutBean.ParamsBean.ItemBean.OfenceBean.PolygonBean.PoitBean();
                long lat = (long) (Double.parseDouble(Utils.formatLatLng(polygon.get(i).latitude)) * 1000000);
                long lon = (long) (Double.parseDouble(Utils.formatLatLng(polygon.get(i).longitude)) * 1000000);
                point.setLat(lat);
                point.setLon(lon);
                poitBeans.add(point);
            }
            polygonBean.setPoit(poitBeans);
            ofenceBean.setPolygon(polygonBean);
        }
        itemBean.setOfence(ofenceBean);
        paramsBean.setItem(itemBean);

        FenceModifyPutBean bean = new FenceModifyPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Fence_Modify);
        bean.setModule(ModuleValueService.Module_For_Fence_Modify);

        if (getPresenter() != null) {
            getPresenter().submitFenceModify(bean);
        }
    }

    /**
     * 修改围栏，只能修改当前类型围栏
     */
    private boolean onModifyFence() {
        if (mBean != null) {
            ToastUtils.show(getString(R.string.txt_modify_tip));
            return false;
        }
        return true;
    }

    /**
     * 切换围栏，圆形围栏，地级市围栏，多边形围栏
     *
     * @param type
     */
    @SuppressLint("SetTextI18n")
    private void onCheckFenceType(String type) {
        fenceType = type;
        tvRound.setTextColor(getResources().getColor(R.color.color_666666));
        tvRound.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        viewRound.setVisibility(View.INVISIBLE);
        tvPolygonal.setTextColor(getResources().getColor(R.color.color_666666));
        tvPolygonal.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        viewPolygonal.setVisibility(View.INVISIBLE);
        llClearFunction.setVisibility(View.GONE);
        llSeekBarProgress.setVisibility(View.GONE);
        tvRadius.setVisibility(View.GONE);
        switch (fenceType) {
            case ResultDataUtils.Fence_Round:
                llSeekBarProgress.setVisibility(View.VISIBLE);
                tvRound.setTextColor(getResources().getColor(R.color.color_333333));
                tvRound.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                viewRound.setVisibility(View.VISIBLE);
                tvRadius.setVisibility(View.VISIBLE);
                getRidOfMarker(true);
                clearData();
                if (isNullFence) {
                    mRadiusProcess = 500;
                    setDrawCircle(carLatLng, mRadiusProcess);//绘制圆形围栏
                    seekBarProgress.setProgress(mRadiusProcess);
                    tvRadius.setText(mRadiusProcess + getString(R.string.txt_meter));
                }
                districtFence = "";
                cityFence = "";
                break;
            case ResultDataUtils.Fence_Polygonal:
                tvPolygonal.setTextColor(getResources().getColor(R.color.color_333333));
                tvPolygonal.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                viewPolygonal.setVisibility(View.VISIBLE);
                llClearFunction.setVisibility(View.VISIBLE);
                if (fenceCircle != null) {
                    fenceCircle.remove();
                    fenceCircle = null;
                }
                clearData();
                districtFence = "";
                cityFence = "";
                break;
            case ResultDataUtils.Fence_City:
                getRidOfMarker(true);
                if (fenceCircle != null) {
                    fenceCircle.remove();
                    fenceCircle = null;
                }
                break;
        }

        mBaiduMap.addOverlay(new MarkerOptions().anchor(0.5f, 0.5f).position(carLatLng).icon(BitmapDescriptorFactory.fromResource(drawableId())));

        if (carLatLng != null) {
            if (!isFirst) {
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().target(carLatLng).zoom(zoom).build()));
            }
        }
        isFirst = false;
    }

    /**
     * 城市选择
     */
    private void onSelectCity() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
//                String opt1tx = cityJsonBean.size() > 0 ?
//                        cityJsonBean.get(options1).getPickerViewText() : "";

                cityFence = cityBeans.size() > 0
                        && cityBeans.get(options1).size() > 0 ?
                        cityBeans.get(options1).get(options2) : "";

                cityFence = cityFence + (areaShowBeans.size() > 0
                        && areaShowBeans.get(options1).size() > 0
                        && areaShowBeans.get(options1).get(options2).size() > 0 ?
                        areaShowBeans.get(options1).get(options2).get(options3) : "");

                districtFence = areaBeans.size() > 0
                        && areaBeans.get(options1).size() > 0
                        && areaBeans.get(options1).get(options2).size() > 0 ?
                        areaBeans.get(options1).get(options2).get(options3).getArea_code() : "";

                if (fenceClient != null) {
                    mBaiduMap.clear();
                    isRepaint = false;
                    fenceClient.addGeoFence(districtFence, "");
                }
            }
        })
                .setSubmitText(getString(R.string.txt_confirm))//确定按钮文字
                .setCancelText(getString(R.string.txt_cancel))//取消按钮文字
                .setTitleText(getString(R.string.txt_city_select)) //标题
                .setSubCalSize(16)//确定和取消文字大小
                .setTitleSize(18)//标题文字大小
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
                .setContentTextSize(16)//滚轮文字大小
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .build();

//        pvOptions.setPicker(cityJsonBean);//一级选择器
//        pvOptions.setPicker(cityJsonBean, cityBeans);//二级选择器
        pvOptions.setPicker(cityJsonBean, cityBeans, areaShowBeans);//三级选择器

        pvOptions.show();
    }

    @Override
    public void getFenceListSuccess(FenceResultBean fenceResultBean) {
        boolean isExist = false; // 是否有重名
        if (fenceResultBean.getItems() != null) {
            if (mBean == null) {
                if (fenceResultBean.getItems().size() >= 3) {
                    ToastUtils.show(getString(R.string.txt_fence_max_number));
                    return;
                }
                for (int i = 0; i < fenceResultBean.getItems().size(); i++) {
                    if (fenceResultBean.getItems().get(i).getName().trim().equals(edtName.getText().toString().trim())) {
                        isExist = true;
                        break;
                    }
                }
            } else {
                for (int i = 0; i < fenceResultBean.getItems().size(); i++) {
                    if (fenceResultBean.getItems().get(i).getName().trim().equals(edtName.getText().toString().trim())
                            && (!mBean.getSfid().equals(fenceResultBean.getItems().get(i).getSfid()))) {
                        isExist = true;
                        break;
                    }
                }
            }
        }

        if (isExist) {
            ToastUtils.show(getString(R.string.txt_please_fence_name_same));
        } else {
            if (mBean == null) {
                submitAddFence();
            } else {
                submitModifyFence();
            }
        }
    }

    @Override
    public void submitFenceAddSuccess(FenceAddResultBean fenceAddResultBean) {
        if (fenceAddResultBean.getFail_imei() != null && fenceAddResultBean.getFail_imei().size() > 0){
            List<DeviceBaseResultBean.FailItemsBean> failItemsBeans = new ArrayList<>();
            for (int i = 0; i < fenceAddResultBean.getFail_imei().size(); i++){
                DeviceBaseResultBean.FailItemsBean bean = new DeviceBaseResultBean.FailItemsBean();
                bean.setImei(fenceAddResultBean.getFail_imei().get(i).getImei());
                bean.setError_messageX(fenceAddResultBean.getFail_imei().get(i).getMessage());
                failItemsBeans.add(bean);
            }
            DeviceFailDialog dialog = new DeviceFailDialog();
            dialog.show(getSupportFragmentManager(), failItemsBeans, 2);
        }else{
            ToastUtils.show(getString(R.string.txt_successful_operation));
            setResult(Activity.RESULT_OK);
            finish();
        }
    }

    @Override
    public void submitFenceModifySuccess(BaseBean baseBean) {
        ToastUtils.show(getString(R.string.txt_successful_operation));
        setResult(Activity.RESULT_OK);
        finish();
    }

    // ------------------------- 分割线：解析省市区数据 -------------------------

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了
                        Log.e("kang", "开始解析本地省市区数据");
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    Log.e("kang", "解析完成");
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
                    Log.e("kang", "解析失败");
                    break;
            }
        }
    };

    /**
     * 解析省市数据
     */
    private void initJsonData() {
        cityJsonBean.clear();
        cityBeans.clear();
        areaShowBeans.clear();
        areaBeans.clear();
        //获取assets目录下的json文件数据
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");
        //用Gson 转成实体
        cityJsonBean.addAll(parseData(JsonData));
        for (int i = 0; i < cityJsonBean.size(); i++) {
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）
            ArrayList<ArrayList<JsonBean.CityBean.AreaBean>> province_AreaCode = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < cityJsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = cityJsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//添加城市

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                //该城市的所有地区列表
                ArrayList<String> city_AreaList = new ArrayList<>();
                ArrayList<JsonBean.CityBean.AreaBean> areaCodeArrayList = new ArrayList<>(cityJsonBean.get(i).getCityList().get(c).getArea());
                for (int h = 0; h < cityJsonBean.get(i).getCityList().get(c).getArea().size(); h++){
                    JsonBean.CityBean.AreaBean areaBean = cityJsonBean.get(i).getCityList().get(c).getArea().get(h);
                    city_AreaList.add(areaBean.getArea_name());
                }
                province_AreaList.add(city_AreaList);//添加该省所有地区数据
                province_AreaCode.add(areaCodeArrayList);
            }

            /**
             * 添加城市数据
             */
            cityBeans.add(cityList);

            /**
             * 添加地区数据
             */
            areaShowBeans.add(province_AreaList);
            areaBeans.add(province_AreaCode);
        }
        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);
    }

    /**
     * Gson 解析
     *
     * @param result
     * @return
     */
    private ArrayList<JsonBean> parseData(String result) {
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    /**
     * 显示对于的图标
     *
     * @return
     */
    private int drawableId() {
        int drawable =  R.mipmap.icon_car_online;
        switch (carImageId) {
            case 0:
                drawable =  R.mipmap.icon_car_online;
                break;
            case 1:
                drawable = R.mipmap.icon_car_online_1;
                break;
            case 2:
                drawable = R.mipmap.icon_car_online_2;
                break;
            case 3:
                drawable = R.mipmap.icon_car_online_3;
                break;
            case 4:
                drawable = R.mipmap.icon_car_online_4;
                break;
        }
        return drawable;
    }

}
