package com.car.scth.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.maps.utils.SpatialRelationUtil;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.geocoder.RegeocodeRoad;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.blankj.utilcode.util.SPUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.car.scth.R;
import com.car.scth.app.MyApplication;
import com.car.scth.di.component.DaggerTrackAmapComponent;
import com.car.scth.mvp.contract.TrackAmapContract;
import com.car.scth.mvp.model.api.ModuleValueService;
import com.car.scth.mvp.model.bean.AlertBean;
import com.car.scth.mvp.model.bean.RoutePoint;
import com.car.scth.mvp.model.bean.TrackHasDataResultBean;
import com.car.scth.mvp.model.bean.TrackListResultBean;
import com.car.scth.mvp.model.putbean.TrackHasDataPutBean;
import com.car.scth.mvp.model.putbean.TrackListPutBean;
import com.car.scth.mvp.presenter.TrackAmapPresenter;
import com.car.scth.mvp.ui.view.data.haibin.Calendar;
import com.car.scth.mvp.utils.BitmapHelperAmap;
import com.car.scth.mvp.utils.ConstantValue;
import com.car.scth.mvp.utils.DateUtils;
import com.car.scth.mvp.utils.LocationAddress;
import com.car.scth.mvp.utils.LocationAddressParsed;
import com.car.scth.mvp.utils.Play;
import com.car.scth.mvp.utils.ResultDataUtils;
import com.car.scth.mvp.utils.SmoothMarker;
import com.car.scth.mvp.utils.ToastUtils;
import com.car.scth.mvp.utils.Utils;
import com.car.scth.mvp.weiget.AlertAppDialog;
import com.car.scth.mvp.weiget.DateSelectPopupWindow;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/04/2020 11:44
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class TrackAmapActivity extends BaseActivity<TrackAmapPresenter> implements TrackAmapContract.View, AMap.InfoWindowAdapter,
        AMap.OnMapClickListener, AMap.OnMarkerClickListener, AMap.OnCameraChangeListener {

    @BindView(R.id.toolbar_iv_right)
    ImageView toolbarIvRight; // 日期筛选
    @BindView(R.id.mapView)
    MapView mapView; // 地图
    @BindView(R.id.iv_play)
    ImageView ivPlay; // 播放轨迹
    @BindView(R.id.seekbar_process)
    SeekBar seekbarProcess; // 进度
    @BindView(R.id.iv_play_speed)
    ImageView ivPlaySpeed; // 播放速度
    @BindView(R.id.tv_play_speed)
    TextView tvPlaySpeed; // 播放速度
    @BindView(R.id.tv_location_time)
    TextView tvLocationTime; // 定位点时间
    @BindView(R.id.tv_address)
    TextView tvAddress; // 定位点地址
    @BindView(R.id.iv_base_station)
    ImageView ivBaseStation; // 基站点
    @BindView(R.id.tv_before_day)
    TextView tvBeforeDay; // 前一天
    @BindView(R.id.tv_data)
    TextView tvData; // 日期
    @BindView(R.id.tv_after_day)
    TextView tvAfterDay; // 后一天
    @BindView(R.id.tv_speed)
    TextView tvSpeed; // 速度
    @BindView(R.id.ll_time)
    LinearLayout llTime;
    @BindView(R.id.view_title)
    View viewTitle;
    @BindView(R.id.ll_base_station)
    LinearLayout llBaseStation; // 基站

    private String mSimei; //
    private long mImei; // 设备的imei号
    private long startTimeForQuest; // 开始时间
    private long endTimeForQuest; // 结束时间
    private String startData; // 开始日期
    private String endData; // 结束日期
    private String startHour = " 00:00:00"; // 开始时间后缀
    private String endHour = " 23:59:59"; // 结束时间后缀
    private int mLimitSize = 2000; // 限制获取数量
    private long mLastTime = 0; // 最后时间
    private boolean isTrackDataComplete = false; // 是否获取完当前的数据
    private LatLngBounds mLatLngBounds; // 地图内可见经纬度

    private List<Long> trackDataBeans; // 轨迹日期数据

    private boolean isShowBase = false; // 是否显示基站
    private DateSelectPopupWindow dateSelectPopupWindow;

    private ArrayList<Calendar> trackDateList;
    private ArrayList<Calendar> selectTrackDateList;
    private java.util.Calendar currentSelectDay; // 请求的日期的第一个，包括多日期请求
    private Calendar currentDay; // 请求的日期的第一个，包括多日期请求，用于显示在日历上

    static class MapInfoWindow {
        public View viewInfoWindows;
        public TextView tvMarkerType; // 定位类型
        public TextView tvMarkerStartTime; // 开始时间
        public TextView tvMarkerEndTime; // 结束时间
        public TextView tvMarkerParkingTime; // 停留时长
        public TextView tvMarkerAddress; // 地址
        public TextView tvMarkerDistance; // 分段里程
        public TextView tvMarkerTime; // 当前点的
    }

    private MapInfoWindow mapInfoWindow = new MapInfoWindow();

    private AMap mAMap;
    private float mZoom = 16;

    private List<RoutePoint> jzRouteList;//基站点
    private List<RoutePoint> jzRouteListForSegmented;//基站点
    private List<RoutePoint> wifiRoutePointList; // wifi点
    private List<RoutePoint> wifiRoutePointListForSegmented; // wifi点
    private List<RoutePoint> playData; // 总数据
    private List<RoutePoint> playDataForSegmented; // 分段数据-用来计算总数据的
    private List<RoutePoint> arrowPointList; // 方向箭头
    private List<Marker> markerList;//保存地图中所有图标的列表
    private List<Marker> jzmarkerList;//保存地图中所有图标的列表
    private boolean isMakeAllVisual = true;//一旦有图标被隐藏,那么这个变量就是false,
    private Marker currentMarker, qiMarket, zhongMarket;//当前marker   起点   终点  停车点
    private Marker jzMarker;
    private BitmapHelperAmap bitmapHelper;
    private boolean isStartAndEnd = false; // 是否是起点或者终点
    private String mStartAndEnd = "_A"; // 起点或终点的后缀，用来区分起点或者终点与P点

    private SmoothMarker smoothMoveMarker; // 播放动画
    private boolean isPauseNow = false;//是否暂停动画
    boolean isColor = true;//颜色
    private String plate;//车牌
    private int playSpeed = 50; // 播放速度
    private int processPosition = 0; // 播放进度
    private int playType = 2; // 播放速度，1：快，播放速度为90，2：中，播放速度为50，3：慢，播放速度为10，默认快速

    private int playIndex = 0; // 播放进度条上的进度

    private boolean isGetSuccess = true; // 解析地址数据是否成功
    private double selectLatForPoint; // 选中点的纬度
    private double selectLonForPoint; // 选中点的经度

    private int drawableId = R.mipmap.icon_car_online; // 播放轨迹图标
    private int carImageId; // 设备图标id
    private double deviceLatitude = 0; // 设备纬度
    private double deviceLongitude = 0; // 设备经度
    private LatLng carLocation; // 设备位置，仅用于展示设备当前所在位置

    public static Intent newInstance() {
        return new Intent(MyApplication.getMyApp(), TrackAmapActivity.class);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTrackAmapComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_track_amap; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
        carImageId = MyApplication.getMyApp().getCarImageId();
        trackDataBeans = new ArrayList<>();
        trackDateList = new ArrayList<>();
        selectTrackDateList = new ArrayList<>();
        jzRouteList = new ArrayList<>();
        jzRouteListForSegmented = new ArrayList<>();
        markerList = new ArrayList<>();
        jzmarkerList = new ArrayList<>();
        playData = new ArrayList<>();
        playDataForSegmented = new ArrayList<>();
        wifiRoutePointList = new ArrayList<>();
        wifiRoutePointListForSegmented = new ArrayList<>();
        arrowPointList = new ArrayList<>();
        tvData.setText(DateUtils.getTodayDateTime_3());

        SPUtils.getInstance().put(ConstantValue.ACTIVITY_STATUS, false);

        mSimei = MyApplication.getMyApp().getSimei();
        mImei = MyApplication.getMyApp().getImei();
        if (!TextUtils.isEmpty(MyApplication.getMyApp().getCarName())) {
            setTitle(MyApplication.getMyApp().getCarName());
        } else {
            setTitle(String.valueOf(mImei));
        }
//        toolbarIvRight.setVisibility(View.VISIBLE);
//        toolbarIvRight.setImageResource(R.drawable.icon_data);

        currentSelectDay = java.util.Calendar.getInstance(Locale.ENGLISH);
        bitmapHelper = new BitmapHelperAmap(this);
        drawableId = drawableId();

        setDataForTrack("", "");
        initMap();
        loadMapInfoWindow();
        onSeekBarProcess();
        updateBaseStationSwitch();

        getTrackHasForData();
    }

    /**
     * 初始化高德地图
     */
    private void initMap() {
        if (mAMap == null) {
            mAMap = mapView.getMap();
            mAMap.getUiSettings().setScaleControlsEnabled(true);
            mAMap.getUiSettings().setRotateGesturesEnabled(false);
            mAMap.getUiSettings().setZoomControlsEnabled(false);
            mAMap.getUiSettings().setMyLocationButtonEnabled(false);

            mAMap.setInfoWindowAdapter(this);
            mAMap.setOnMarkerClickListener(this);
            mAMap.setOnMapClickListener(this);
            mAMap.setOnCameraChangeListener(this);
        }
        // 默认展示中心点为设备位置
        if (!TextUtils.isEmpty(MyApplication.getMyApp().getLatAndLon())) {
            String[] location = MyApplication.getMyApp().getLatAndLon().split(",");
            deviceLatitude = (double) Long.parseLong(location[0]) / 1000000;
            deviceLongitude = (double) Long.parseLong(location[1]) / 1000000;
        }
        carLocation = new LatLng(deviceLatitude, deviceLongitude);
        mAMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(carLocation, mZoom, 0, 0)));
    }

    /**
     * 初始化气泡框
     */
    @SuppressLint("InflateParams")
    private void loadMapInfoWindow() {
        mapInfoWindow.viewInfoWindows = getLayoutInflater().inflate(R.layout.layout_infowindow, null);
        mapInfoWindow.tvMarkerType = mapInfoWindow.viewInfoWindows.findViewById(R.id.tv_marker_type);
        mapInfoWindow.tvMarkerStartTime = mapInfoWindow.viewInfoWindows.findViewById(R.id.tv_marker_start_time);
        mapInfoWindow.tvMarkerEndTime = mapInfoWindow.viewInfoWindows.findViewById(R.id.tv_marker_end_time);
        mapInfoWindow.tvMarkerParkingTime = mapInfoWindow.viewInfoWindows.findViewById(R.id.tv_marker_parking_time);
        mapInfoWindow.tvMarkerAddress = mapInfoWindow.viewInfoWindows.findViewById(R.id.tv_marker_address);
        mapInfoWindow.tvMarkerDistance = mapInfoWindow.viewInfoWindows.findViewById(R.id.tv_marker_parking_distance);
        mapInfoWindow.tvMarkerTime = mapInfoWindow.viewInfoWindows.findViewById(R.id.tv_marker_time);
    }

    /**
     * 进度条事件监听
     */
    private void onSeekBarProcess() {
        seekbarProcess.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (playData != null && playData.size() > 3) {
                    playIndex = playData.size() * seekBar.getProgress() / 100;
                    if (playIndex >= playData.size()) {
                        playIndex = playData.size() - 1;
                    }
                    onSeekBarInfoShow();
                    generateSmoothMarker(playData, playIndex);
                    isPauseNow = true;
                    smoothMoveMarker.pauseMove();
                    ivPlay.setImageResource(R.mipmap.icon_track_play);
                }
            }
        });
    }

    /**
     * 拖动结束点的信息
     */
    @SuppressLint("SetTextI18n")
    private void onSeekBarInfoShow(){
        if (playIndex < playData.size()){
            RoutePoint currentPoint = playData.get(playIndex);
            setAddress(currentPoint);
            tvLocationTime.setText(getString(R.string.txt_time) + DateUtils.timedate(String.valueOf(currentPoint.getTime())));
            tvSpeed.setText(getString(R.string.txt_speed) + ((double) currentPoint.getSpeed() / 10) + "km/h");
        }
    }

    /**
     * 基站定位点显示开关
     */
    private void updateBaseStationSwitch() {
        ivBaseStation.setImageResource(isShowBase ? R.mipmap.icon_lbs_on : R.mipmap.icon_lbs_off);
        //加载基站信息
        if (jzmarkerList != null) {
            for (int i = 0; i < jzmarkerList.size(); ++i) {
                jzmarkerList.get(i).setVisible(isShowBase);
            }
        }
        //缩放地图到原始比例展示所有基站
        if(isShowBase){
            mAMap.animateCamera(CameraUpdateFactory.zoomTo(16));
        }
    }

    /**
     * 初始化时间，重置筛选时间
     *
     * @param start 开始日期
     * @param end   结束日期
     */
    private void setDataForTrack(String start, String end) {
        if (TextUtils.isEmpty(start) && TextUtils.isEmpty(end)) {
            startData = DateUtils.getTodayDateTime_3();
            endData = DateUtils.getTodayDateTime_3();
        } else {
            startData = start;
            endData = end;
        }
        startTimeForQuest = Long.parseLong(DateUtils.data_2(startData + startHour));
        endTimeForQuest = Long.parseLong(DateUtils.data_2(endData + endHour));
    }

    /**
     * 查询有轨迹的日期
     */
    private void getTrackHasForData() {
        TrackHasDataPutBean.ParamsBean paramsBean = new TrackHasDataPutBean.ParamsBean();
        paramsBean.setSimei(mSimei);

        TrackHasDataPutBean bean = new TrackHasDataPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Track_Data_Get);
        bean.setModule(ModuleValueService.Module_For_Track_Data_Get);

        if (getPresenter() != null) {
            getPresenter().getTrackHasForData(bean);
        }
    }

    /**
     * 获取轨迹列表
     *
     * @param isShow      是否显示加载框
     * @param isResetData 是否是请求新的一天轨迹数据，true:请求新的一天数据，false:请求同一天的轨迹数据，后续的数据
     */
    private void getTrackList(boolean isShow, boolean isResetData) {
        TrackListPutBean.ParamsBean paramsBean = new TrackListPutBean.ParamsBean();
        paramsBean.setSimei(mSimei);
        paramsBean.setTime_end(endTimeForQuest);
        paramsBean.setTime_begin(startTimeForQuest);
        paramsBean.setLimit_size(mLimitSize);
        if (mLastTime != 0) {
            paramsBean.setLast_time(mLastTime);
        }

        TrackListPutBean bean = new TrackListPutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Track_List_Data);
        bean.setModule(ModuleValueService.Module_For_Track_List_Data);
        bean.setParams(paramsBean);

        if (getPresenter() != null) {
            getPresenter().getTrackList(bean, isShow, isResetData);
        }
    }

    /**
     * 重置数据
     *
     * @param start 开始时间
     * @param end   结束时间
     */
    private void onResetData(String start, String end) {
        cancelSmoothMarker();
        onClearData();
        mLastTime = 0;
        isTrackDataComplete = false;
        setDataForTrack(start, end);
        getTrackList(true, true);
    }

    /**
     * 清除数据，恢复原来的数据
     */
    private void onClearData() {
        isPauseNow = false;
        playData.clear();
        mAMap.clear();
        processPosition = 0; // 播放进度
        if (seekbarProcess != null)
            seekbarProcess.setProgress(1);
        ivPlay.setImageResource(R.mipmap.icon_track_play);
        handler.removeCallbacksAndMessages(null);

        if (qiMarket != null) {
            qiMarket.remove();
            qiMarket = null;
        }

        if (zhongMarket != null) {
            zhongMarket.remove();
            zhongMarket = null;
        }

        if (currentMarker != null) {
            currentMarker.remove();
            currentMarker = null;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getInfoWindow(Marker marker) {
        // 是否是P点 + 当前点的时间 + 打点开始时间 + 打点结束时间 + 停留时长 + 分段里程 + 经度 + 纬度
        currentMarker = marker;

        String titleString = marker.getTitle();
        isStartAndEnd = titleString.contains(mStartAndEnd);
        titleString = titleString.replace(mStartAndEnd, "");
        String snippetString = marker.getSnippet();
        mapInfoWindow.tvMarkerType.setText(titleString);
        String[] detailInfo = snippetString.split(",");
        if (detailInfo.length == 8) {
            if (Integer.parseInt(detailInfo[0]) == 1 && !isStartAndEnd) {
                mapInfoWindow.tvMarkerTime.setVisibility(View.GONE);
                if (Long.parseLong(detailInfo[2]) != 0) {
                    mapInfoWindow.tvMarkerStartTime.setVisibility(View.VISIBLE);
                    mapInfoWindow.tvMarkerStartTime.setText(getString(R.string.txt_start_static_parking) + "："
                            + DateUtils.timeConversionDate_two(detailInfo[2]));
                } else {
                    mapInfoWindow.tvMarkerStartTime.setVisibility(View.GONE);
                }
                if (Long.parseLong(detailInfo[3]) != 0) {
                    mapInfoWindow.tvMarkerEndTime.setVisibility(View.VISIBLE);
                    mapInfoWindow.tvMarkerEndTime.setText(getString(R.string.txt_end_static_parking) + "："
                            + DateUtils.timeConversionDate_two(detailInfo[3]));
                } else {
                    mapInfoWindow.tvMarkerEndTime.setVisibility(View.GONE);
                }
                if (Integer.parseInt(detailInfo[4]) != 0) {
                    mapInfoWindow.tvMarkerParkingTime.setVisibility(View.VISIBLE);
                    mapInfoWindow.tvMarkerParkingTime.setText(getString(R.string.txt_duration_time) + "："
                            + Utils.getParkingTime(this, Integer.parseInt(detailInfo[4])));
                } else {
                    mapInfoWindow.tvMarkerParkingTime.setVisibility(View.GONE);
                }
                if (Integer.parseInt(detailInfo[5]) != 0) {
                    mapInfoWindow.tvMarkerDistance.setVisibility(View.VISIBLE);
                    mapInfoWindow.tvMarkerDistance.setText(getString(R.string.txt_section_mileage)
                            + Utils.formatValue((double) Integer.parseInt(detailInfo[5]) / 1000) + "km");
                } else {
                    mapInfoWindow.tvMarkerDistance.setVisibility(View.GONE);
                }
            } else {
                mapInfoWindow.tvMarkerStartTime.setVisibility(View.GONE);
                mapInfoWindow.tvMarkerEndTime.setVisibility(View.GONE);
                mapInfoWindow.tvMarkerParkingTime.setVisibility(View.GONE);
                mapInfoWindow.tvMarkerDistance.setVisibility(View.GONE);
                mapInfoWindow.tvMarkerTime.setVisibility(View.VISIBLE);
                if (Long.parseLong(detailInfo[1]) != 0) {
                    mapInfoWindow.tvMarkerTime.setText(getString(R.string.txt_track_location_time) + "：" + DateUtils.timeConversionDate_two(detailInfo[1]));
                }
            }
            selectLatForPoint = Double.parseDouble(detailInfo[6]);
            selectLonForPoint = Double.parseDouble(detailInfo[7]);
            new LocationAddress().Parsed(selectLatForPoint, selectLonForPoint)
                    .setAddressListener(str -> {
                        if (mapInfoWindow != null && mapInfoWindow.tvMarkerAddress != null) {
                            if (!TextUtils.isEmpty(str)) {
                                mapInfoWindow.tvMarkerAddress.setText(getString(R.string.txt_address) + str);
                            } else {
                                mapInfoWindow.tvMarkerAddress.setText(getString(R.string.txt_address) + selectLatForPoint + "," + selectLonForPoint);
                            }
                        }
                    });
        }
        return mapInfoWindow.viewInfoWindows;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        try {
            mZoom = cameraPosition.zoom;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (currentMarker != null) {
            currentMarker.hideInfoWindow();
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        try {
            if (smoothMoveMarker != null) {
                if (smoothMoveMarker.getMarker().equals(marker)) {
                    return true;
                }
            }
            marker.showInfoWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mapView != null) {
            mapView.onDestroy();
        }
        handler.removeCallbacksAndMessages(null);
        handler = null;
        super.onDestroy();
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

    @OnClick({R.id.tv_data, R.id.iv_play, R.id.iv_play_speed, R.id.tv_play_speed, R.id.ll_base_station, R.id.tv_before_day,
            R.id.tv_after_day})
    public void onViewClicked(View view) {
        if (Utils.isButtonQuickClick(System.currentTimeMillis())) {
            switch (view.getId()) {
                case R.id.tv_data: // 日期选择
                    onSelectData();
                    break;
                case R.id.iv_play: // 播放轨迹
                    playTrack();
                    break;
                case R.id.iv_play_speed: // 播放速度
                case R.id.tv_play_speed:
                    onPlaySpeed();
                    break;
                case R.id.ll_base_station: // 基站点
                    isShowBase = !isShowBase;
                    updateBaseStationSwitch();
                    break;
                case R.id.tv_before_day: // 前一天
                    onBeforeDayTrack();
                    break;
                case R.id.tv_after_day: // 后一天
                    onAfterDayTrack();
                    break;
            }
        }
    }

    /**
     * 选择日期
     */
    private void onSelectData() {
        if (currentDay == null) {
            currentDay = new Calendar();
        }
        currentDay.setYear(currentSelectDay.get(java.util.Calendar.YEAR));
        currentDay.setMonth(currentSelectDay.get(java.util.Calendar.MONTH) + 1);
        currentDay.setDay(currentSelectDay.get(java.util.Calendar.DAY_OF_MONTH));
        dateSelectPopupWindow = new DateSelectPopupWindow(this, trackDateList, currentDay);
        dateSelectPopupWindow.setCallbackAction(new DateSelectPopupWindow.CallbackAction() {
            @SuppressLint("SetTextI18n")
            @Override
            public void OnSelectDate(List<Calendar> calendarList) {
                if (!isTrackDataComplete) {
                    ToastUtils.show(getString(R.string.txt_track_data_get_hint));
                    return;
                }
                java.util.Calendar compareCalendar = java.util.Calendar.getInstance(Locale.ENGLISH);
                Calendar currentDate = new Calendar();
                currentDate.setDay(compareCalendar.get(java.util.Calendar.DAY_OF_MONTH));
                currentDate.setMonth(compareCalendar.get(java.util.Calendar.MONTH) + 1);
                currentDate.setYear(compareCalendar.get(java.util.Calendar.YEAR));
                boolean isRet = false;
                for (int i = 0; i < calendarList.size(); ++i) {
                    if (calendarList.get(i).compareTo(currentDate) > 0) {
                        isRet = true;
                        break;
                    }
                }

                if (isRet) {
                    ToastUtils.show(getString(R.string.txt_max_date));
                    return;
                }

                dateSelectPopupWindow.dismiss();
                selectTrackDateList.clear();
                selectTrackDateList.addAll(calendarList);

                if (selectTrackDateList.size() == 1) {
                    //单日
                    currentSelectDay.set(
                            selectTrackDateList.get(0).getYear(),
                            selectTrackDateList.get(0).getMonth() - 1,
                            selectTrackDateList.get(0).getDay(),
                            0,
                            0,
                            0);

                    String dataTime = selectTrackDateList.get(0).getYear() + "-" +
                            selectTrackDateList.get(0).getMonth() + "-" +
                            selectTrackDateList.get(0).getDay();

                    tvData.setText(dataTime);

                    onResetData(dataTime, dataTime);
                } else {
                    //多日
                    currentSelectDay.set(
                            selectTrackDateList.get(0).getYear(),
                            selectTrackDateList.get(0).getMonth() - 1,
                            selectTrackDateList.get(0).getDay(),
                            0,
                            0,
                            0);

                    String startTime = selectTrackDateList.get(0).getYear() + "-" +
                            selectTrackDateList.get(0).getMonth() + "-" +
                            selectTrackDateList.get(0).getDay();
                    String endTime = selectTrackDateList.get(selectTrackDateList.size() - 1).getYear() + "-" +
                            selectTrackDateList.get(selectTrackDateList.size() - 1).getMonth() + "-" +
                            selectTrackDateList.get(selectTrackDateList.size() - 1).getDay();
                    tvData.setText(startTime + "\n" + endTime);

                    onResetData(startTime, endTime);
                }
                seekbarProcess.setProgress(1);
            }
        });
        dateSelectPopupWindow.showAsDropDown(viewTitle, 0, 0);
    }

    /**
     * 前一天的数据
     */
    @SuppressLint("SetTextI18n")
    private void onBeforeDayTrack() {
        if (!isTrackDataComplete) {
            ToastUtils.show(getString(R.string.txt_track_data_get_hint));
            return;
        }

        if (currentSelectDay == null) {
            currentSelectDay = java.util.Calendar.getInstance(Locale.ENGLISH);
        }else {
            selectTrackDateList.clear();
        }

        currentSelectDay.add(java.util.Calendar.DAY_OF_MONTH, -1);

        String beforeTime = currentSelectDay.get(java.util.Calendar.YEAR) + "-" +
                (currentSelectDay.get(java.util.Calendar.MONTH) + 1) + "-" +
                (currentSelectDay.get(java.util.Calendar.DAY_OF_MONTH));

        tvData.setText(beforeTime);

        onResetData(beforeTime, beforeTime);
    }

    /**
     * 后一天的数据
     */
    @SuppressLint("SetTextI18n")
    private void onAfterDayTrack() {
        if (!isTrackDataComplete) {
            ToastUtils.show(getString(R.string.txt_track_data_get_hint));
            return;
        }
        if (currentSelectDay == null) {
            currentSelectDay = java.util.Calendar.getInstance(Locale.ENGLISH);
        }else {
            if(selectTrackDateList.size() > 0) {
                currentSelectDay.set(
                        selectTrackDateList.get(selectTrackDateList.size() - 1).getYear(),
                        selectTrackDateList.get(selectTrackDateList.size() - 1).getMonth() - 1,
                        selectTrackDateList.get(selectTrackDateList.size() - 1).getDay(),
                        0,
                        0,
                        0);
                selectTrackDateList.clear();
            }
        }

        currentSelectDay.add(java.util.Calendar.DAY_OF_MONTH, 1);
        java.util.Calendar tmpDate = java.util.Calendar.getInstance(Locale.ENGLISH);
        if (currentSelectDay.compareTo(tmpDate) > 0) {
            currentSelectDay.add(java.util.Calendar.DAY_OF_MONTH, -1);
            ToastUtils.show(getString(R.string.txt_max_track_date));
            return;
        }

        String afterTime = currentSelectDay.get(java.util.Calendar.YEAR) + "-" +
                (currentSelectDay.get(java.util.Calendar.MONTH) + 1) + "-" +
                (currentSelectDay.get(java.util.Calendar.DAY_OF_MONTH));

        tvData.setText(afterTime);

        onResetData(afterTime, afterTime);
    }

    /**
     * 快进播放，播放速度，1：快，播放速度为10，2：中，播放速度为6，3：慢，播放速度为2，默认快速
     */
    private void onPlaySpeed() {
        if (playType == 1) {
            playType = 2;
            playSpeed = 50;
            tvPlaySpeed.setText(getString(R.string.txt_medium_speed));
        } else if (playType == 2) {
            playType = 3;
            playSpeed = 10;
            tvPlaySpeed.setText(getString(R.string.txt_slow));
        } else {
            playType = 1;
            playSpeed = 90;
            tvPlaySpeed.setText(getString(R.string.txt_fast));
        }

        if (smoothMoveMarker == null) {
            smoothMoveMarker = new SmoothMarker(mAMap, carImageId);
        }
        int duration = 50 + (10 * (100 - playSpeed));
        smoothMoveMarker.setTotalDuration(duration);
    }

    /**
     * 格式化有轨迹数据的日期
     *
     * @param year  年
     * @param month 月
     * @param day   日
     * @return
     */
    private Calendar getCalendar(int year, int month, int day) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(getResources().getColor(R.color.color_2EB6B9));
        return calendar;
    }

    @Override
    public void getTrackHasForDataSuccess(TrackHasDataResultBean trackHasDataResultBean) {
        trackDataBeans.clear();
        if (trackDateList != null) {
            trackDateList.clear();
        }
        if (trackHasDataResultBean.getDate() != null) {
            trackDataBeans.addAll(trackHasDataResultBean.getDate());
        }
        boolean isHasTodayTrack = false; // 是否有当天的轨迹
        String today = DateUtils.getTodayDateTime_3();
        for (int i = 0; i < trackDataBeans.size(); i++) {
            String day = DateUtils.timedate_2(String.valueOf(trackDataBeans.get(i)));
            if (day.equals(today)) {
                isHasTodayTrack = true;
            }
            String[] dayDayas = day.split("-");
            trackDateList.add(getCalendar(Integer.parseInt(dayDayas[0]), Integer.parseInt(dayDayas[1]), Integer.parseInt(dayDayas[2])));
        }


        if (trackDataBeans.size() == 0) {
            onEndMoreData();
            currentSelectDay = java.util.Calendar.getInstance(Locale.ENGLISH);
            ToastUtils.show(getString(R.string.txt_no_trace_data));
        } else {
            if (isHasTodayTrack) {
                currentSelectDay = java.util.Calendar.getInstance(Locale.ENGLISH);
                onResetData(today, today);
            } else {
                AlertBean bean = new AlertBean();
                bean.setTitle(getString(R.string.txt_tip));
                bean.setAlert(getString(R.string.txt_no_trace_data_ex));
                bean.setType(0);
                AlertAppDialog dialog = new AlertAppDialog();
                dialog.show(getSupportFragmentManager(), bean, new AlertAppDialog.onAlertDialogChange() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onConfirm() {
                        String mostRecentDate = DateUtils.timedate_2(String.valueOf(trackDataBeans.get(trackDataBeans.size() - 1))); // 最近的日期
                        String[] dayDayas = mostRecentDate.split("-");
                        currentSelectDay = java.util.Calendar.getInstance(Locale.ENGLISH);
                        currentSelectDay.set(
                                Integer.parseInt(dayDayas[0]),
                                Integer.parseInt(dayDayas[1]) - 1,
                                Integer.parseInt(dayDayas[2]),
                                0,
                                0,
                                0);
                        tvData.setText(dayDayas[0] + "-" + dayDayas[1] + "-" + dayDayas[2]);
                        onResetData(mostRecentDate, mostRecentDate);
                    }

                    @Override
                    public void onCancel() {
                        onEndMoreData();
                        currentSelectDay = java.util.Calendar.getInstance(Locale.ENGLISH);
                    }
                });
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getTrackListSuccess(TrackListResultBean trackListResultBean, boolean isResetData) {
        if (isResetData) {
            jzRouteList.clear();
            playData.clear();
            wifiRoutePointList.clear();
            markerList.clear();
            jzmarkerList.clear();
            mLatLngBounds = null;
        }
        playDataForSegmented.clear();
        jzRouteListForSegmented.clear();
        wifiRoutePointListForSegmented.clear();
        arrowPointList.clear();

        if (trackListResultBean.getData() == null || (trackListResultBean.getData().size() == 0 && playData.size() == 0)) {
            onEndMoreData();
            ToastUtils.show(getString(R.string.txt_no_trace_data));
            tvAddress.setVisibility(View.GONE);
            llTime.setVisibility(View.GONE);
            return;
        }

        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
        for (int i = 0; i < trackListResultBean.getData().size(); i++) {
            TrackListResultBean.DataBean bean = trackListResultBean.getData().get(i);
            double lat = (double) bean.getLat() / 1000000;
            double lon = (double) bean.getLon() / 1000000;
            RoutePoint routePoint = new RoutePoint(new LatLng(lat, lon), bean.getType(), bean.getTime(), bean.getSpeed(),
                    lat, lon, bean.getDirection(), bean.getPtype(), bean.getStart_time(), bean.getDistance(), bean.getDuration(), bean.getEnd_time());
            if (bean.getType() == ResultDataUtils.Location_Base_Station_Track || bean.getType() == ResultDataUtils.Location_Static_Base_Station_Track) {
                jzRouteListForSegmented.add(routePoint);
            } else if (bean.getType() == ResultDataUtils.Location_GPS_Track || bean.getType() == ResultDataUtils.Location_Static_Gps_Track) {
                boundsBuilder.include(routePoint.getPoint());
                playDataForSegmented.add(routePoint);
            } else {
                wifiRoutePointListForSegmented.add(routePoint);
            }
        }
        if (isResetData) {
            if (playDataForSegmented.size() == 0){
                if (wifiRoutePointListForSegmented.size() > 0) {
                    for (int i = 0; i < wifiRoutePointListForSegmented.size(); i++) {
                        boundsBuilder.include(wifiRoutePointListForSegmented.get(i).getPoint());
                    }
                }else{
                    for (int i = 0; i < jzRouteListForSegmented.size(); i++) {
                        boundsBuilder.include(jzRouteListForSegmented.get(i).getPoint());
                    }
                }
            }
            mLatLngBounds = boundsBuilder.build();
            //调整角度
            mAMap.animateCamera(CameraUpdateFactory.newLatLngBounds(mLatLngBounds, 200));
            mZoom = mAMap.getCameraPosition().zoom;
        }

        if (arrowPointList.size() == 0){
            if (playDataForSegmented.size() > 0){
                arrowPointList.add(playDataForSegmented.get(0));
                for (int i = 1; i < playDataForSegmented.size(); i++){
                    RoutePoint lastPoint = arrowPointList.get(arrowPointList.size() - 1);
                    float tmpDistance = AMapUtils.calculateLineDistance(lastPoint.getPoint(), playDataForSegmented.get(i).getPoint());
                    if (tmpDistance > 500) {
                        arrowPointList.add(playDataForSegmented.get(i));
                    }
                }
            }
        }else{
            for (int i = 0; i < playDataForSegmented.size(); i++){
                RoutePoint lastPoint = arrowPointList.get(arrowPointList.size() - 1);
                float tmpDistance = AMapUtils.calculateLineDistance(lastPoint.getPoint(), playDataForSegmented.get(i).getPoint());
                if (tmpDistance > 500) {
                    arrowPointList.add(playDataForSegmented.get(i));
                }
            }
        }

        //绘制轨迹线
        List<LatLng> lineList = new ArrayList<>();
        int color = Color.rgb(50, 205, 50); //绘制轨迹线颜色
        if (playData.size() > 0) {
            playDataForSegmented.add(0, playData.get(playData.size() - 1));
        }
        if (playDataForSegmented.size() >= 2) {
            for (int i = 0; i < playDataForSegmented.size(); i++) {
                lineList.add(playDataForSegmented.get(i).getPoint());
            }
            mAMap.addPolyline(new PolylineOptions().addAll(lineList).width(12).color(color));
        }

        // 添加分段数据到总数据中 - 绘制轨迹数据
        playData.addAll(playDataForSegmented);
        // 添加分段数据到总数据中 - 基站点数据
        jzRouteList.addAll(jzRouteListForSegmented);
        // 添加分段数据到总数据中 - wifi点数据
        wifiRoutePointList.addAll(wifiRoutePointListForSegmented);

        // 展示第一个点的信息
        if (isResetData){
            if (playData.size() > 0){
                tvAddress.setVisibility(View.VISIBLE);
                llTime.setVisibility(View.VISIBLE);
                RoutePoint currentPoint = playData.get(0);
                setAddress(currentPoint);
                tvLocationTime.setText(getString(R.string.txt_time) + DateUtils.timedate(String.valueOf(currentPoint.getTime())));
                tvSpeed.setText(getString(R.string.txt_speed) + ((double) currentPoint.getSpeed() / 10) + "km/h");
            }
        }

        //3.绘制最后一个点   终点
        if (isTrackDataComplete) {
            // 如果是第一页就加载完了，则判断从总数据索引 1  -  size-1 中间段执行
            if (isResetData) {
                for (int i = 1; i < playData.size() - 1; i++) {
                    if (playData.get(i).getPtype() == 1) {
                        Marker rotateMarker;
                        String lastTitleString = getTitleStringByType(playData.get(i).type);
                        rotateMarker = mAMap.addMarker(new MarkerOptions().zIndex(1)
                                .position(playData.get(i).point)
                                .title(lastTitleString)
                                .snippet(getTrackDetailInfo(playData.get(i)))
                                .icon(bitmapHelper.getBitmapZoomParkingForGPS(mZoom)));
//                        rotateMarker.setToTop();
                        markerList.add(rotateMarker);
                    }
                }
            } else {
                // 如果是第二页++ 加载完成，则判断从当前页数据的索引  0  -   size-1  中间段执行
                for (int i = 0; i < playDataForSegmented.size() - 1; i++) {
                    if (playDataForSegmented.get(i).getPtype() == 1) {
                        Marker rotateMarker;
                        String lastTitleString = getTitleStringByType(playDataForSegmented.get(i).type);
                        rotateMarker = mAMap.addMarker(new MarkerOptions().zIndex(1)
                                .position(playDataForSegmented.get(i).point)
                                .title(lastTitleString)
                                .snippet(getTrackDetailInfo(playDataForSegmented.get(i)))
                                .icon(bitmapHelper.getBitmapZoomParkingForGPS(mZoom)));
//                        rotateMarker.setToTop();
                        markerList.add(rotateMarker);
                    }
                }
            }

            // 终点
            if (playData.size() > 1) {
                int endPosition = playData.size() - 1;
                int lastOneType = playData.get(endPosition).type;
                String lastTitleString = getTitleStringByType(lastOneType);
                zhongMarket = mAMap.addMarker(new MarkerOptions().zIndex(1)
                        .position(playData.get(endPosition).point)
                        .title(lastTitleString + mStartAndEnd)
                        .snippet(getTrackDetailInfo(playData.get(endPosition)))
                        .icon(bitmapHelper.getBitmapZoomForEnd(mZoom)));
                zhongMarket.setToTop();
                markerList.add(zhongMarket);
            }
        } else {
            if (isResetData) {
                for (int i = 1; i < playDataForSegmented.size(); i++) {
                    if (playDataForSegmented.get(i).getPtype() == 1) {
                        Marker rotateMarker;
                        String lastTitleString = getTitleStringByType(playDataForSegmented.get(i).type);
                        rotateMarker = mAMap.addMarker(new MarkerOptions().zIndex(1)
                                .position(playDataForSegmented.get(i).point)
                                .title(lastTitleString)
                                .snippet(getTrackDetailInfo(playDataForSegmented.get(i)))
                                .icon(bitmapHelper.getBitmapZoomParkingForGPS(mZoom)));
//                        rotateMarker.setToTop();
                        markerList.add(rotateMarker);
                    }
                }
            } else {
                for (int i = 0; i < playDataForSegmented.size(); i++) {
                    if (playDataForSegmented.get(i).getPtype() == 1) {
                        Marker rotateMarker;
                        String lastTitleString = getTitleStringByType(playDataForSegmented.get(i).type);
                        rotateMarker = mAMap.addMarker(new MarkerOptions().zIndex(1)
                                .position(playDataForSegmented.get(i).point)
                                .title(lastTitleString)
                                .snippet(getTrackDetailInfo(playDataForSegmented.get(i)))
                                .icon(bitmapHelper.getBitmapZoomParkingForGPS(mZoom)));
//                        rotateMarker.setToTop();
                        markerList.add(rotateMarker);
                    }
                }
            }
        }

        //2.绘制第一个点   起点
        if (isResetData) {
            if (playData.size() > 0) {
                int firstType = playData.get(0).type;
                String firstTitleString = getTitleStringByType(firstType);
                qiMarket = mAMap.addMarker(new MarkerOptions().zIndex(1)
                        .position(playData.get(0).point)
                        .title(firstTitleString + mStartAndEnd)
                        .snippet(getTrackDetailInfo(playData.get(0)))
                        .icon(bitmapHelper.getBitmapZoomForStart(mZoom)));
                qiMarket.setToTop();
                markerList.add(qiMarket);
            }
        }

        // 基站红点
        if (jzRouteListForSegmented.size() > 0) {
            RoutePoint routePointTmp;
            String titleStringTmp;
            for (int j = 0; j < jzRouteListForSegmented.size(); j++) {
                routePointTmp = jzRouteListForSegmented.get(j);
                titleStringTmp = getTitleStringByType(routePointTmp.getType());

                jzMarker = mAMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                        .position(routePointTmp.point)
                        .title(titleStringTmp)
                        .snippet(getTrackDetailInfo(routePointTmp))
                        .visible(isShowBase)
                        .icon(bitmapHelper.getBitmapZoomForBaseStation(mZoom)));
                jzmarkerList.add(jzMarker);
            }
        }

        // wifi蓝点
        if (wifiRoutePointListForSegmented.size() > 0) {
            RoutePoint routePointTmp;
            String titleStringTmp;
            for (int j = 0; j < wifiRoutePointListForSegmented.size(); j++) {
                routePointTmp = wifiRoutePointListForSegmented.get(j);
                titleStringTmp = getTitleStringByType(routePointTmp.getType());

                jzMarker = mAMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                        .position(routePointTmp.point)
                        .title(titleStringTmp)
                        .snippet(getTrackDetailInfo(routePointTmp))
                        .visible(true)
                        .icon(bitmapHelper.getBitmapZoomForWifi(mZoom)));
            }
        }

        //5.绘制角度
        if (arrowPointList.size() > 1) {
            Marker rotateMarker;
            RoutePoint routePoint;
            String titleString;
            LatLng pre;
            LatLng next;
            // 再循环
            for (int i = 1; i < arrowPointList.size(); i++) {
                routePoint = arrowPointList.get(i);
                pre = arrowPointList.get(i - 1).point;
                next = routePoint.point;
                titleString = getTitleStringByType(routePoint.type);
                rotateMarker = mAMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                        .position(next)
                        .title(titleString)
                        .snippet(getTrackDetailInfo(routePoint))
                        .icon(bitmapHelper.getBitmapZoomForDirection(mZoom)));

                float rotate = Utils.getRotate(pre, next);
                rotateMarker.setRotateAngle(360 - rotate + mAMap.getCameraPosition().bearing);
//                rotateMarker.setRotateAngle(routePoint.getDirection());
                markerList.add(rotateMarker);
            }
        }

        updateBaseStationSwitch();
        if (!isTrackDataComplete) {
            mLastTime = trackListResultBean.getData().get(trackListResultBean.getData().size() - 1).getTime();
            ivPlay.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getTrackList(false, false);
                }
            }, 100);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setAddress(RoutePoint currentPoint) {
        // 判断当前点的经纬度是否在中国范维内的经纬度多边形内，判断国内和海外
        boolean isChina = false; // 是否在国内
        selectLatForPoint = currentPoint.getLat();
        selectLonForPoint = currentPoint.getLon();
        com.baidu.mapapi.model.LatLng latLng = new com.baidu.mapapi.model.LatLng(selectLatForPoint, selectLonForPoint);
        if (com.baidu.mapapi.utils.SpatialRelationUtil.isPolygonContainsPoint(MyApplication.getMyApp().getBaiduListPoint(), latLng)) {
            isChina = true;
        }
        try {
            if (isChina) {
                LocationAddressParsed.getLocationParsedInstance().Parsed(selectLatForPoint, selectLonForPoint)
                        .setAddressListener(str -> {
                            isGetSuccess = true;
                            if (tvAddress != null) {
                                if (!TextUtils.isEmpty(str)) {
                                    tvAddress.setText(getString(R.string.txt_address) + str);
                                } else {
                                    tvAddress.setText(getString(R.string.txt_address) + selectLonForPoint + "," + selectLatForPoint);
                                }
                            }
                        });
            } else {
                isGetSuccess = true;
                tvAddress.setText(getString(R.string.txt_address) + selectLonForPoint + "," + selectLatForPoint);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEndMoreData() {
        isTrackDataComplete = true;
    }

    /**
     * 当前点类型
     *
     * @param type
     * @return
     */
    private String getTitleStringByType(int type) {
        switch (type) {
            case ResultDataUtils.Location_Base_Station_Track:
                return getString(R.string.txt_base_station);
            case ResultDataUtils.Location_Static_Base_Station_Track:
                return getString(R.string.txt_base_station_static);
            case ResultDataUtils.Location_GPS_Track:
                return getString(R.string.txt_location_gps);
            case ResultDataUtils.Location_Static_Gps_Track:
                return getString(R.string.txt_location_gps_static);
            case ResultDataUtils.Location_WIFI_Track:
                return getString(R.string.txt_location_wifi);
            case ResultDataUtils.Location_Static_WIFI_Track:
                return getString(R.string.txt_location_wifi_static);
        }
        return null;
    }

    /**
     * 轨迹点的详细信息
     *
     * @param routePoint
     * @return
     */
    private String getTrackDetailInfo(RoutePoint routePoint) {
        // 是否是P点 + 当前点的时间 + 打点开始时间 + 打点结束时间 + 停留时长 + 分段里程 + 经度 + 纬度
        return routePoint.getPtype() + "," + routePoint.getTime() + "," + routePoint.getStart_time() + "," + routePoint.getEnd_time() + ","
                + routePoint.getDuration() + "," + routePoint.getDistance() + "," + routePoint.getLat() + "," + routePoint.getLon();
    }

    /**
     * 播放动画
     */
    private void playTrack() {
        if (playData.size() < 3) {
            ToastUtils.show(getString(R.string.txt_not_play));
            return;
        }

        if (smoothMoveMarker == null) {
            ivPlay.setImageResource(R.mipmap.icon_track_pause);
            //txtPlay.setText(getString(R.string.txt_track_stop));
            generateSmoothMarker(playData, 0);
        } else {
            if (smoothMoveMarker.getRemainDistance() == 0.0f) {
                //已经播放了一遍,再播放一遍
                ivPlay.setImageResource(R.mipmap.icon_track_pause);
                //txtPlay.setText(getString(R.string.txt_track_stop));
                generateSmoothMarker(playData, 0);
                return;
            }

            if (!smoothMoveMarker.isPauseAnimationNow()) {
                if (!isPauseNow) {
                    //点击暂停
                    ivPlay.setImageResource(R.mipmap.icon_track_play);
                    //txtPlay.setText(getString(R.string.txt_track_play));
                    isPauseNow = true;
                    smoothMoveMarker.pauseMove();
                } else {
                    //暂停后恢复播放
                    ivPlay.setImageResource(R.mipmap.icon_track_pause);
                    //txtPlay.setText(getString(R.string.txt_track_stop));
                    isPauseNow = false;
                    //找到真正播放的索引
                    mAMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                            smoothMoveMarker.getPosition(),//先移动到点
                            mZoom, //新的缩放级别
                            0, //俯仰角0°~45°（垂直与地图时为0）
                            0  ////偏航角 0~360° (正北方为0)
                    )));
                    smoothMoveMarker.resumeMove();
                }
            }
        }
    }

    /***
     *  生成点平滑移动
     */
    private void generateSmoothMarker(List<RoutePoint> playData, int index) {

        try {
            if (playData == null || playData.size() == 0) {
                com.blankj.utilcode.util.ToastUtils.showShort(getString(R.string.txt_no_trace_data));
                return;
            }

            if (smoothMoveMarker != null) {
                smoothMoveMarker.destroy();
                smoothMoveMarker = null;
            }

            RoutePoint routePoint;
            //当前轨迹的播放列表
            List<LatLng> points = new ArrayList<>();
            for (int i = 0; i < playData.size(); i++) {
                routePoint = playData.get(i);
                points.add(routePoint.getPoint());
            }

            smoothMoveMarker = new SmoothMarker(mAMap, carImageId);
            smoothMoveMarker.setDescriptor(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), drawableId)));
            LatLng drivePoint = points.get(index);
            processPosition = index;
            Pair<Integer, LatLng> pair = SpatialRelationUtil.calShortestDistancePoint(points, drivePoint);
            points.set(pair.first, drivePoint);
            List<RoutePoint> subList = playData.subList(pair.first, points.size());
            // 设置滑动的轨迹左边点
            smoothMoveMarker.setPoints(subList, play);
            //CommonUtils.LogKevin("轨迹","总共长度:"+points.size(),TrackActivity.this);
            mAMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                    drivePoint,//先移动到点
                    mZoom, //新的缩放级别
                    0, //俯仰角0°~45°（垂直与地图时为0）
                    0  ////偏航角 0~360° (正北方为0)
            )));

            // 设置滑动的总时间
            int duration = 50 + (10 * (100 - playSpeed));
            smoothMoveMarker.setTotalDuration(duration);
            // 开始滑动
            smoothMoveMarker.setMoveListener(smoothListener);
            smoothMoveMarker.setFinishMoveListener(smoothFinishMoveListener);
            smoothMoveMarker.startSmoothMove();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 取消动画
     */
    private void cancelSmoothMarker() {
        if (smoothMoveMarker != null) {
            smoothMoveMarker.destroy();
            smoothMoveMarker = null;
            isPauseNow = false;
            ivPlay.setImageResource(R.mipmap.icon_track_play);
        }
    }

    /**
     * todo 监听动画结束的监听器
     */
    private SmoothMarker.SmoothFinishMoveListener smoothFinishMoveListener = new SmoothMarker.SmoothFinishMoveListener() {
        @Override
        public void onFinishMove() {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(0x0100);
                    isPauseNow = false;
                }
            });

        }
    };

    /**
     * 所有图标都可见
     */
    private void allMarkerVisual() {
        for (int i = 0; i < markerList.size(); i++) {
            markerList.get(i).setVisible(true);
        }
        isMakeAllVisual = true;
    }

    /**
     * 监听动画播放的过程
     */
    private SmoothMarker.SmoothMarkerMoveListener smoothListener = new SmoothMarker.SmoothMarkerMoveListener() {
        @Override
        public void move(final double v, final RoutePoint currentPoint, final RoutePoint nextPoint) {

            runOnUiThread(new Runnable() {
                @SuppressLint({"SetTextI18n", "DefaultLocale"})
                @Override
                public void run() {
                    try {
                        if (playData == null || playData.size() < 3) {
                            return;
                        }

                        if (smoothMoveMarker != null) {
                            int process = (processPosition + smoothMoveMarker.getIndex()) * 100 / playData.size();
                            seekbarProcess.setProgress(process);
                        }

                        if (!isMakeAllVisual) {
                            //如果有图标没有显示出来,让所有图标都显示
                            allMarkerVisual();
                        }

                        mAMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentPoint.getPoint().latitude, currentPoint.getPoint().longitude), mZoom));
                        if (isGetSuccess) {
                            isGetSuccess = false;
                            setAddress(currentPoint);
                        }
                        tvLocationTime.setText(getString(R.string.txt_time) + DateUtils.timedate(String.valueOf(currentPoint.getTime())));
                        tvSpeed.setText(getString(R.string.txt_speed) + ((double) currentPoint.getSpeed() / 10) + "km/h");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    /**
     * 播放完成回调
     */
    private Play play = new Play() {
        @Override
        public void complete(int state) {
            if (state == 0) {
                handler.sendEmptyMessage(0x0100);
                seekbarProcess.setProgress(100);
                isPauseNow = false;
            }
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x0100) {
                playIndex = playData.size() - 1;
                onSeekBarInfoShow();
                ivPlay.setImageResource(R.mipmap.icon_track_play);
                //txtPlay.setText(getString(R.string.txt_track_play));
            } else if (msg.what == 0x0200) {
                updateBaseStationSwitch();
            }
        }
    };

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