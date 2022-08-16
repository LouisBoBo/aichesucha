package com.car.scth.mvp.ui.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.lbsapi.model.BaiduPanoData;
import com.baidu.lbsapi.panoramaview.PanoramaRequest;
import com.baidu.lbsapi.tools.CoordinateConverter;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.utils.DistanceUtil;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.car.scth.R;
import com.car.scth.app.MyApplication;
import com.car.scth.di.component.DaggerLocationBaiduComponent;
import com.car.scth.mvp.contract.LocationBaiduContract;
import com.car.scth.mvp.model.api.Api;
import com.car.scth.mvp.model.api.ModuleValueService;
import com.car.scth.mvp.model.bean.AlertBean;
import com.car.scth.mvp.model.bean.DeviceDetailResultBean;
import com.car.scth.mvp.model.bean.DeviceGroupResultBean;
import com.car.scth.mvp.model.bean.DeviceListForManagerResultBean;
import com.car.scth.mvp.model.bean.DeviceListResultBean;
import com.car.scth.mvp.model.bean.DeviceLocationInfoBean;
import com.car.scth.mvp.model.bean.MergeAccountResultBean;
import com.car.scth.mvp.model.bean.PhoneCodeResultBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.DeviceDetailIntentBean;
import com.car.scth.mvp.model.putbean.DeviceDetailPutBean;
import com.car.scth.mvp.model.putbean.DeviceGroupPutBean;
import com.car.scth.mvp.model.putbean.DeviceListPutBean;
import com.car.scth.mvp.model.putbean.MergeAccountPutBean;
import com.car.scth.mvp.model.putbean.OnKeyFunctionPutBean;
import com.car.scth.mvp.model.putbean.PhoneCodePutBean;
import com.car.scth.mvp.presenter.LocationBaiduPresenter;
import com.car.scth.mvp.ui.activity.BaiduPanoramaActivity;
import com.car.scth.mvp.ui.activity.CustomerWebViewActivity;
import com.car.scth.mvp.ui.activity.DeviceDetailActivity;
import com.car.scth.mvp.ui.activity.GroupManagementActivity;
import com.car.scth.mvp.ui.activity.NavigationBaiduActivity;
import com.car.scth.mvp.ui.activity.TrackBaiduActivity;
import com.car.scth.mvp.utils.BitmapHelperBaidu;
import com.car.scth.mvp.utils.ConstantValue;
import com.car.scth.mvp.utils.DateUtils;
import com.car.scth.mvp.utils.DeviceUtils;
import com.car.scth.mvp.utils.FunctionType;
import com.car.scth.mvp.utils.LocationAddressParsed;
import com.car.scth.mvp.utils.ResultDataUtils;
import com.car.scth.mvp.utils.ToastUtils;
import com.car.scth.mvp.utils.Utils;
import com.car.scth.mvp.weiget.AlertAppDialog;
import com.umeng.analytics.MobclickAgent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import butterknife.BindView;
import butterknife.OnClick;
import com.car.scth.mvp.weiget.GroupSelectDialog;
import com.car.scth.mvp.weiget.MergeAccountDialog;
import com.car.scth.mvp.weiget.MergeAccountFailDialog;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * Description: 定位首页-百度地图
 */
public class LocationBaiduFragment extends BaseFragment<LocationBaiduPresenter> implements LocationBaiduContract.View,
        BaiduMap.OnMarkerClickListener, BaiduMap.OnMapStatusChangeListener, BaiduMap.OnMapLoadedCallback {

    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.iv_map_distance)
    ImageView ivRangingDistance; // 测距
    @BindView(R.id.iv_user_location)
    ImageView ivUserLocation; // 用户位置或设备的位置
    @BindView(R.id.tv_refresh_time)
    TextView tvRefresh; // 刷新数据倒计时
    @BindView(R.id.iv_refresh)
    ImageView ivRefresh; // 刷新数据
    @BindView(R.id.tv_distance)
    TextView tvDistance; // 测距距离
    @BindView(R.id.ll_location_now)
    LinearLayout llLocationNow; // 立即定位
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle; // 标题栏
    @BindView(R.id.tv_title_name)
    TextView tvTitleName; // 设备名称
    @BindView(R.id.tv_dump_energy)
    TextView tvDumpEnergy; // 剩余电量
    @BindView(R.id.iv_check_map)
    ImageView ivCheckMap; // 切换卫星地图
    @BindView(R.id.iv_traffic)
    ImageView ivTraffic; // 路况
    @BindView(R.id.iv_group_list)
    ImageView ivGroupList;// 分组列表
    @BindView(R.id.ll_distance)
    LinearLayout llDistance;
    @BindView(R.id.ll_device_location)
    LinearLayout llDeviceLocation;
    @BindView(R.id.ll_user_location)
    LinearLayout llUserLocation;

    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    private Overlay polylineDistance = null; // 用于测距连线
    private float mZoom = 17; // 缩放层级
    private LatLng centerPoint = new LatLng(39.90923, 116.397428);
    private double mLatitude = 0.0; // 定位获取到的纬度
    private double mLongitude = 0.0; // 定位获取到的经度
    private boolean isFirstLocation = true; // 是否是第一次定位，避免后续定位成功后，一直设置定位点为中心点
    private MyLocationConfiguration.LocationMode mCurrentMode; // 定位模式 地图SDK支持三种定位模式：NORMAL（普通态）, FOLLOWING（跟随态）, COMPASS（罗盘态）
    private static final int accuracyCircleFillColor = 0x00FF00FF; // 自定义精度圈填充颜色
    private static final int accuracyCircleStrokeColor = 0x00FF00FF; // 自定义精度圈边框颜色
    private BitmapHelperBaidu bitmapHelper;
    private LatLngBounds.Builder mBuilder; // 所有设备可视化Builder

    private HashMap<String, Marker> deviceMap; // 存储任务
    private LatLng mLatLngLocation; // 设备的经纬度
    private String selectImei; // 选中的设备的imei号(展示使用，用来做唯一匹配)
    private DeviceListResultBean.ItemsBean selectCarBean; // 选中的设备
    private DeviceDetailResultBean selectDeviceDetailBean; // 选中的设备的详细信息
    private Marker mMarker; // 选中的设备，仅用于选中手机定位位置使用
    private List<DeviceListResultBean.ItemsBean> deviceDataBeans; // 设备列表数据
    private List<DeviceListResultBean.ItemsBean> deviceSwitchBeans; // 设备左右切换列表数据
    private int switchPosition = 0; // 当前设备所在索引位置
    private boolean isSatelliteMap = false; // 是否是卫星地图
    private boolean isSwitchMapTraffic = false; // 是否开启了路况信息，false：否
    private boolean isClearMap = false; // 是否清理地图，用于设备列表选中设备时判断
    private boolean isDistance = false; // 是否开启测距
    private boolean isDeviceCenter = true; // 设备是否显示在地图中心，用于设备号登录判断
    private boolean isAutoFresh = false; //是否自动刷新

    // 测距使用
    private List<LatLng> distanceLatLng; // 专门用于测距
    private double mPolylineDistance = 0; // 测距距离
    private long refresh_time = 0; // 主动刷新数据间隔
    private boolean isFirstData = true; // 是否是第一次加载数据
    private boolean isOperationalRestrictions = false; // 是否是操作限制
    private String userFamilyId; // 用来请求设备数据的对应的组织id，默认指向当前登录用户
    // 获取设备列表数据
    private int limitSizeForDevice = 200; // 最大获取设备列表数量限制
    private boolean isAllDevice = true; // 是否拉取了账号下的所有设备
    private List<String> mSimeiLists; // 用于刷新数据的设备imei号

    private static final int mCountDownTime = 10; // 倒计时固定时长
    private int mCountDown = mCountDownTime; // 倒计时递减时长
    private boolean isShowInfoWindow = false; // 是否有选中设备，显示详细信息悬浮框
    private double selectLatForDevice; // 选中设备的纬度
    private double selectLonForDevice; // 选中设备的经度
    private String deviceVersion; // 选中设备的版本
    private List<String> mCallSimeis; // 需要下发位置查询的设备列表，如果非点名模式，将忽略这个参数
    private boolean isCallAllDev = true; // 如果设备处于点名模式，是否针对返回的设备集体下发一次位置查询，默认false，不下发;设置true，下发
    private int mCallTheRollMode = 0; // 当选中设备处于点名模式时，每30秒下发一次点名指令。
    private int mapStateChange; //地图变化情况
    private InfoWindow popInfoWindow;//气泡框

    private static class MapInfoWindow {
        private View viewInfoWindows;
        private TextView tvDeviceName; // 设备名称
        private TextView tvDeviceState; // 设备状态
        private TextView tvSpeed; // 速度
        private TextView tvDayMileage; // 日里程
        private TextView tvCommunicationTime; // 通讯时间
        private TextView tvLocationTime; // 定位时间
        private TextView tvAddress; // 地址
        private LinearLayout llHistoryTrack; // 历史轨迹
        private LinearLayout llDeviceDetail; // 设备详情
        private LinearLayout llNavigation; // 导航
        private ImageView ivLocationType; // 定位类型，gps，wifi，基站
        private ImageView ivSignal; // 信号强度
        private TextView tvSignalNumber; // 信号强度
        private TextView tvPower; // 剩余电量
        private TextView tvAcc; // acc信息
        private TextView tvLocationMode; // 定位模式
        private TextView tvStaticTime; // 静止时长
        private ImageView iv_power; //充电状态
//        private TextView tvSatelliteNumber; // gps卫星个数
//        private TextView tvGpsSatellite; // 字符"G"
//        private TextView tvBdSatelliteNumber; // 北斗个数
//        private TextView tvBdSatellite; // 字符 "B"
//        private ImageView ivSignalValue; // 卫星数量个数
        private TextView tvSignalValueNumber; // 卫星数量个数
    }

    private MapInfoWindow mapInfoWindow = new MapInfoWindow();

    private ChangePageReceiver receiver; // 注册广播接收器
    private DeviceSelectReceiver deviceReceiver;//注册设备选中接受器
    private int fragmentPosition = 0; // 当前切换的fragment位置，只有在首页的时候，才开启请求数据
    private String mPhoneZone = "86"; // 电话号码区号
    // 分组列表
    private String mLastGroupId; // 最后获取到的gid，没有可以为空
    private int mLimitSize = 100; // 限制分组获取数量
    private List<DeviceGroupResultBean.GaragesBean> groupDataBeans; // 设备分组
    private MergeAccountDialog mergeAccountDialog;

    public static LocationBaiduFragment newInstance() {
        LocationBaiduFragment fragment = new LocationBaiduFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerLocationBaiduComponent //如找不到该类,请编译一下项�?
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_location_baidu, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        deviceMap = new HashMap<>();
        deviceDataBeans = new ArrayList<>();
        mSimeiLists = new ArrayList<>();
        distanceLatLng = new ArrayList<>();
        userFamilyId = ConstantValue.getFamilySid();
        mCallSimeis = new ArrayList<>();
        deviceSwitchBeans = new ArrayList<>();
        groupDataBeans = new ArrayList<>();
        mPhoneZone = String.valueOf(SPUtils.getInstance().getInt(ConstantValue.Phone_Zone, 86));

        bitmapHelper = new BitmapHelperBaidu(getContext());

        mBaiduMap = mapView.getMap();
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        // marker点击事件
        mBaiduMap.setOnMarkerClickListener(this);
        //设置地图单击事件监听
        mBaiduMap.setOnMapClickListener(listener);
        // 设置地图缩放等级事件监听
        mBaiduMap.setOnMapStatusChangeListener(this);
        mBaiduMap.setOnMapLoadedCallback(this);
        // 隐藏缩放控件
        mapView.showZoomControls(false);

        llLocationNow.setVisibility(View.GONE);
        if (ConstantValue.isAccountLogin()) {
            ivGroupList.setVisibility(View.VISIBLE);
        } else {
            ivGroupList.setVisibility(View.GONE);
        }
        onLoadInfoWindow();
        initBaiduLbs();
        getDeviceList(true, true, false, false);

        // 注册一个广播接收器，用于接收从首页跳转到报警消息页面的广播
        IntentFilter filter = new IntentFilter();
        filter.addAction("device_run");
        receiver = new ChangePageReceiver();
        //注册切换页面广播接收者
        LocalBroadcastManager.getInstance(MyApplication.getMyApp()).registerReceiver(receiver, filter);

        // 注册一个设备广播接收器，用于接收从设备列表选中的设备
        IntentFilter filterDev = new IntentFilter();
        filterDev.addAction(ConstantValue.DEVICE_ACTION);
        deviceReceiver = new DeviceSelectReceiver();
        //注册切换页面广播接收者
        LocalBroadcastManager.getInstance(MyApplication.getMyApp()).registerReceiver(deviceReceiver, filterDev);

        onMergeAccount(); //账号合并
    }

    /**
     * 判断是否合并账号
     */
    private void onMergeAccount() {
        if (!MyApplication.getMyApp().isBeforeAccount() && MyApplication.getMyApp().isMergeAccount() && ConstantValue.isLoginForAccount()) {
            mergeAccountDialog = new MergeAccountDialog();
            mergeAccountDialog.show(getFragmentManager(), new MergeAccountDialog.onMergeAccountChange() {
                @Override
                public void onSendCode() {
                    onSendPhoneCode();
                }

                @Override
                public void onMergeAccount(String code) {
                    if (groupDataBeans.size() > 0) {
                        onMergeGroupSelect(code);
                        mergeAccountDialog.dismiss();
                    } else {
                        getDeviceGroupList(true, true, code);
                    }
                }
            });
        }
    }


    /**
     * 页面切换广播，广播接收器
     */
    private class ChangePageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                if (intent.hasExtra("type")) {
                    fragmentPosition = intent.getIntExtra("type", 0);
                    if (fragmentPosition != 0) {
                        handler.removeCallbacksAndMessages(null);
                    }
                }
            }
        }
    }

    /**
     * 设备列表选中广播，广播接收器
     */
    private class DeviceSelectReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                long imei = 0L;
                String smei = "";
                if (intent.hasExtra("imei")) {
                    imei = intent.getLongExtra("imei", 0L);
                }
                if (intent.hasExtra("smei")) {
                    smei = intent.getStringExtra("smei");
                }
                if (imei != 0L && smei != null && !smei.isEmpty())
                    deviceClickForGroup(imei, smei);
            }
        }
    }

    /**
     * 初始化百度地图
     */
    private void initBaiduLbs() {
        if (mBaiduMap != null) {
            mBaiduMap.setMyLocationEnabled(true);
            //定位初始化
            mLocationClient = new LocationClient(getActivity());
            //通过LocationClientOption设置LocationClient相关参数
            LocationClientOption option = new LocationClientOption();
            option.setOpenGps(true); // 打开gps
            option.setCoorType("gcj02"); // 设置坐标类型
            option.setScanSpan(20000); //定位时间间隔20s
            //设置locationClientOption
            mLocationClient.setLocOption(option);
            // 自定义定位显示图标
            MyLocationConfiguration locationConfiguration = new MyLocationConfiguration(mCurrentMode,
                    false, bitmapHelper.getBitmapZoomForUserLocation(mZoom), accuracyCircleFillColor, accuracyCircleStrokeColor);
            mBaiduMap.setMyLocationConfiguration(locationConfiguration);
            //注册LocationListener监听器
            MyLocationListener myLocationListener = new MyLocationListener();
            mLocationClient.registerLocationListener(myLocationListener);
            if (mLocationClient.isStarted()) {
                mLocationClient.restart();
            } else {
                mLocationClient.start();
            }
        }
    }
    /**
     * 开启/关闭卫星地图
     */
    private void onSwitchMapType() {
        if (isSatelliteMap) {
            ivCheckMap.setImageResource(R.mipmap.icon_weixing_map);
            mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
        } else {
            ivCheckMap.setImageResource(R.mipmap.icon_weixing_map_off);
            mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        }
    }
    /**
     * 查看全景地图
     */
    @SuppressLint("WrongConstant")
    private void onSeePanorama() {
        if (TextUtils.isEmpty(MyApplication.getMyApp().getImei() + "") || MyApplication.getMyApp().getImei() == 0) {
            ToastUtils.show(getString(R.string.txt_device_select_location));
            return;
        }
        if (!TextUtils.isEmpty(MyApplication.getMyApp().getLatAndLon())) {
            String[] location = MyApplication.getMyApp().getLatAndLon().split(",");
            boolean has = hasPanorama((double) Long.parseLong(location[0]) / 1000000, (double) Long.parseLong(location[1]) / 1000000);
            if (has) {
                launchActivity(BaiduPanoramaActivity.newInstance());
            } else {
                ToastUtils.show(getString(R.string.txt_no_panorama));
            }
        }
    }

    /**
     * 是否开启了路况
     */
    @SuppressLint("WrongConstant")
    private void onSwitchMapTraffic() {
        mBaiduMap.setTrafficEnabled(isSwitchMapTraffic);
        if (isSwitchMapTraffic) {
            ivTraffic.setImageResource(R.mipmap.icon_lukuang);
        } else {
            ivTraffic.setImageResource(R.mipmap.icon_lukuang_off);
        }
    }

    /**
     * 初始化气泡框
     */
    private void onLoadInfoWindow() {
        mapInfoWindow.viewInfoWindows = getLayoutInflater().inflate(R.layout.layout_custom_info_window, null);
        mapInfoWindow.tvDeviceState = mapInfoWindow.viewInfoWindows.findViewById(R.id.tv_device_state);
        mapInfoWindow.tvSpeed = mapInfoWindow.viewInfoWindows.findViewById(R.id.tv_device_speed);
        mapInfoWindow.tvDayMileage = mapInfoWindow.viewInfoWindows.findViewById(R.id.tv_day_distance);
        mapInfoWindow.tvCommunicationTime = mapInfoWindow.viewInfoWindows.findViewById(R.id.tv_communication_time);
        mapInfoWindow.tvLocationTime = mapInfoWindow.viewInfoWindows.findViewById(R.id.tv_location_time);
        mapInfoWindow.tvAddress = mapInfoWindow.viewInfoWindows.findViewById(R.id.tv_address);
        mapInfoWindow.llNavigation = mapInfoWindow.viewInfoWindows.findViewById(R.id.ll_navigation);
        mapInfoWindow.llHistoryTrack = mapInfoWindow.viewInfoWindows.findViewById(R.id.ll_history_track);
        mapInfoWindow.llDeviceDetail = mapInfoWindow.viewInfoWindows.findViewById(R.id.ll_device_detail);
        mapInfoWindow.ivLocationType = mapInfoWindow.viewInfoWindows.findViewById(R.id.iv_location_type);
        mapInfoWindow.ivSignal = mapInfoWindow.viewInfoWindows.findViewById(R.id.iv_signal);
        mapInfoWindow.tvSignalNumber = mapInfoWindow.viewInfoWindows.findViewById(R.id.tv_signal_number);
        mapInfoWindow.tvPower = mapInfoWindow.viewInfoWindows.findViewById(R.id.tv_electric);
        mapInfoWindow.tvAcc = mapInfoWindow.viewInfoWindows.findViewById(R.id.tv_acc);
        mapInfoWindow.tvLocationMode = mapInfoWindow.viewInfoWindows.findViewById(R.id.tv_location_mode);
        mapInfoWindow.tvStaticTime = mapInfoWindow.viewInfoWindows.findViewById(R.id.tv_static_time);
        mapInfoWindow.tvSignalValueNumber = mapInfoWindow.viewInfoWindows.findViewById(R.id.tv_satellite_number);
        mapInfoWindow.iv_power = mapInfoWindow.viewInfoWindows.findViewById(R.id.iv_power);

        mapInfoWindow.llDeviceDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isButtonQuickClick(System.currentTimeMillis())) {
                    if (selectCarBean != null && selectDeviceDetailBean != null) {
                        DeviceDetailIntentBean bean = new DeviceDetailIntentBean();
                        bean.setDevice_number(selectCarBean.getCar_number());
                        bean.setImei(selectCarBean.getImei());
                        bean.setSimei(selectCarBean.getSimei());
                        bean.setCar_type(selectCarBean.getVersion());
                        bean.setState(selectCarBean.getState());
                        bean.setLast_pos(selectCarBean.getLast_pos());
                        bean.setIccid(selectDeviceDetailBean.getIccid());
                        bean.setStart_dev_time(selectDeviceDetailBean.getDetail().getStart_dev_time());
                        bean.setEnd_dev_time(selectDeviceDetailBean.getDetail().getEnd_dev_time());
                        bean.setBck_phone(selectDeviceDetailBean.getDetail().getBck_phone());
                        bean.setUser_name(selectDeviceDetailBean.getDetail().getUser_name());
                        bean.setCenter_phone(selectDeviceDetailBean.getCenter_phone());
                        bean.setBind_phone(selectDeviceDetailBean.getBind_phone());

                        launchActivity(DeviceDetailActivity.newInstance(bean));
                    }
                }
            }
        });
        // 轨迹
        mapInfoWindow.llHistoryTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isButtonQuickClick(System.currentTimeMillis())) {
                    if (selectDeviceDetailBean != null) {
                        if (selectDeviceDetailBean.getDetail().getMode().equals(ResultDataUtils.Device_Mode_Call_One)
                                || selectDeviceDetailBean.getDetail().getMode().equals(ResultDataUtils.Device_Mode_Sup_Save_Power_C2)) {
                            AlertBean bean = new AlertBean();
                            bean.setTitle(getString(R.string.txt_tip));
                            bean.setAlert(getString(R.string.txt_quest_track_hint));
                            bean.setType(0);
                            AlertAppDialog dialog = new AlertAppDialog();
                            dialog.show(getFragmentManager(), bean, new AlertAppDialog.onAlertDialogChange() {
                                @Override
                                public void onConfirm() {
                                    launchActivity(TrackBaiduActivity.newInstance());
                                }

                                @Override
                                public void onCancel() {

                                }
                            });
                        } else {
                            launchActivity(TrackBaiduActivity.newInstance());
                        }
                    }
                }
            }
        });
        // 导航
        mapInfoWindow.llNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isButtonQuickClick(System.currentTimeMillis())) {
                    launchActivity(NavigationBaiduActivity.newInstance());
                }
            }
        });

    }

    /**
     * 获取设备列表
     *
     * @param isShow              是否弹出加载看
     * @param isInitiativeRefresh 是否主动刷新数据
     * @param clearMap            是否清理地图
     * @param isDataError         是否是数据错误，冻结设备，返回的是269877281，数据已变更,请刷新 返回的是269877251
     */
    private void getDeviceList(boolean isShow, boolean isInitiativeRefresh, boolean clearMap, boolean isDataError) {
        DeviceListPutBean.ParamsBean paramsBean = new DeviceListPutBean.ParamsBean();
        paramsBean.setLimit_size(limitSizeForDevice);
        if (isCallAllDev) {
            paramsBean.setCall_all_dev(true);
        }
        if (mCallTheRollMode >= 3) {
            mCallSimeis.clear();
            if (ConstantValue.getAccountType().equals(ModuleValueService.Login_Device)){
                if (deviceDataBeans.size() > 0 && !TextUtils.isEmpty(MyApplication.getMyApp().getDeviceMode())
                        && MyApplication.getMyApp().getDeviceMode().equals(ResultDataUtils.Device_Mode_Call_One)
                        && !TextUtils.isEmpty(MyApplication.getMyApp().getDeviceState())
                        && !MyApplication.getMyApp().getDeviceState().equals(ResultDataUtils.Device_State_Line_Down)) {
                    if (!TextUtils.isEmpty(deviceDataBeans.get(0).getSimei())) {
                        mCallSimeis.add(deviceDataBeans.get(0).getSimei());
                    } else {
                        mCallSimeis.add("0");
                    }
                    paramsBean.setCall_simei(mCallSimeis);
                }
            }else{
                if (selectCarBean != null && !TextUtils.isEmpty(MyApplication.getMyApp().getDeviceMode())
                        && MyApplication.getMyApp().getDeviceMode().equals(ResultDataUtils.Device_Mode_Call_One)
                        && !TextUtils.isEmpty(MyApplication.getMyApp().getDeviceState())
                        && !MyApplication.getMyApp().getDeviceState().equals(ResultDataUtils.Device_State_Line_Down)) {
                    if (!TextUtils.isEmpty(selectCarBean.getSimei())) {
                        mCallSimeis.add(selectCarBean.getSimei());
                    } else {
                        mCallSimeis.add("0");
                    }
                    paramsBean.setCall_simei(mCallSimeis);
                }
            }
            mCallTheRollMode = 0;
        }
        if (!isDataError && !isAllDevice && deviceDataBeans.size() == 1) {
            if (mSimeiLists.size() == 0) {
                for (int i = 0; i < deviceDataBeans.size(); i++) {
                    mSimeiLists.add(deviceDataBeans.get(i).getSimei());
                }
            }
        }
        if (mSimeiLists.size() > 0) {
            paramsBean.setSimei(mSimeiLists);
        } else {
            if (!TextUtils.isEmpty(userFamilyId)) {
                paramsBean.setSfamily(userFamilyId);
            }
        }
        DeviceListPutBean bean = new DeviceListPutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Device_List);
        bean.setModule(ModuleValueService.Module_For_Device_List);
        bean.setParams(paramsBean);

        if (isShow) {
            showProgressDialog();
        }

        getPresenter().getDeviceList(bean, isShow, isInitiativeRefresh, clearMap);
    }

    /**
     * 获取设备详细信息
     */
    private void getDeviceDetailInfo() {
        if (selectCarBean != null) {
            selectDeviceDetailBean = null;

            DeviceDetailPutBean.ParamsBean paramsBean = new DeviceDetailPutBean.ParamsBean();
            paramsBean.setSimei(selectCarBean.getSimei());

            DeviceDetailPutBean bean = new DeviceDetailPutBean();
            bean.setFunc(ModuleValueService.Fuc_For_Device_Detail);
            bean.setModule(ModuleValueService.Module_For_Device_Detail);
            bean.setParams(paramsBean);

            getPresenter().getDeviceDetailInfo(bean);
        }
    }

    /**
     * 用户从后台切换到前台，判断是否满足条件，满足的话下发定位模式指令
     */
    private void onSubmitCallTheRollMode() {
        if (ConstantValue.isActivityStatus()) {
            mCallTheRollMode = 3;
        }
        SPUtils.getInstance().put(ConstantValue.ACTIVITY_STATUS, true);
    }

    @Override
    public void onResume() {
        MobclickAgent.onPageStart("BaiduMapLocation");//统计页面("MainScreen"为页面名称，可自定义)
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        onSubmitCallTheRollMode();
        mapView.onResume();
        //开启地图定位图层
        if (mLocationClient != null) {
            mLocationClient.start();
        }
        if (fragmentPosition == 0 && mapView != null) {
            if (!isFirstData) {
                mCountDown = 0;
            }
            handler.sendEmptyMessage(1);
        }
        super.onResume();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && mapView != null) {
            if (!isFirstData) {
                mCountDown = 0;
            }
            handler.sendEmptyMessage(1);
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onPause() {
        MobclickAgent.onPageEnd("BaiduMapLocation");
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapView.onPause();
        if (mLocationClient != null) {
            mLocationClient.stop();
        }
        handler.removeCallbacksAndMessages(null);
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mLocationClient != null) {
            mLocationClient.stop();
        }
        if (polylineDistance != null) {
            polylineDistance.remove();
            polylineDistance = null;
        }
        mBaiduMap.setMyLocationEnabled(false);
        if (mapView != null) {
            mapView.onDestroy();
        }
        mapView = null;
        handler.removeCallbacksAndMessages(null);
        LocalBroadcastManager.getInstance(MyApplication.getMyApp()).unregisterReceiver(receiver);
        LocalBroadcastManager.getInstance(MyApplication.getMyApp()).unregisterReceiver(deviceReceiver);
    }

    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus) {

    }

    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus, int i) {
        mapStateChange = i;
    }

    @Override
    public void onMapStatusChange(MapStatus mapStatus) {

    }

    @Override
    public void onMapStatusChangeFinish(MapStatus mapStatus) {
        try {//用户手势触发导致的地图状态改变,比如双击、拖拽、滑动底图 REASON_GESTURE
            //SDK导致的地图状态改变, 比如点击缩放控件、指南针图标 用户缩放 REASON_API_ANIMATION
            if (mapStateChange == 1 || mapStateChange == 2) {
                mZoom = mapStatus.zoom;
                centerPoint = mapStatus.target;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public boolean onMarkerClick(Marker marker) {
        selectCarBean = null;
        selectImei = marker.getTitle();
        if (!TextUtils.isEmpty(selectImei)) {
            for (DeviceListResultBean.ItemsBean bean : deviceDataBeans) {
                if (selectImei.equals(bean.getImei() + "")) {
                    selectCarBean = bean;
                    break;
                }
            }
        }

        isDeviceCenter = true;
        onSelectPositionForSwitchList();
        isShowInfoWindow = true;
        onDeviceDetailInfo();
        return false;
    }

    private BaiduMap.OnMapClickListener listener = new BaiduMap.OnMapClickListener() {
        /**
         * 地图单击事件回调函数
         *
         * @param point 点击的地理坐标
         */
        @Override
        public void onMapClick(LatLng point) {
            isShowInfoWindow = false;
            if (!TextUtils.isEmpty(selectImei) || selectCarBean != null) {
                mBaiduMap.hideInfoWindow();
            }

            isDeviceCenter = false;
            if (ConstantValue.isLoginForAccount()) {
                selectCarBean = null;
                selectImei = "";
                clearData();
            }
            llLocationNow.setVisibility(View.GONE);
            if (isDistance) {
                isDistance = false;
                ivRangingDistance.setImageResource(R.mipmap.icon_juli_unselect);
                tvDistance.setVisibility(View.GONE);
                distanceLatLng.clear();
                if (polylineDistance != null) {
                    polylineDistance.remove();
                    polylineDistance = null;
                }
            }
            onSelectPositionForSwitchList();
        }

        @Override
        public void onMapPoiClick(MapPoi mapPoi) {

        }

    };

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mapView == null) {
                return;
            }
            mLatitude = location.getLatitude();
            mLongitude = location.getLongitude();

            isFirstLocation = false;
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isDistance && selectCarBean != null) {
                onPolylineDistance();
            }
        }


    }

    /**
     * 设置当前点位于地图中心
     */
    private void updateMapCenter() {
        if (mBaiduMap != null && selectCarBean != null) {
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().target(centerPoint).zoom(mZoom).build()));
            Marker marker = deviceMap.get(String.valueOf(selectCarBean.getImei()));
            if (marker!= null) {
                marker.setToTop();
            }
        }
    }

    /**
     * 清理临时缓存数据
     */
    private void clearData() {
        tvDumpEnergy.setText("");
        tvTitleName.setText("");
        MyApplication.getMyApp().clearData();
    }

    /**
     * 倒计时刷新数据
     */
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            mCountDown--;
            if (mCountDown <= 0) {
                mCallTheRollMode++;
                mCountDown = mCountDownTime;
                isAutoFresh = true;
                getDeviceList(false, false, false, false);
            }
            tvRefresh.setText(mCountDown + "S");
            handler.sendEmptyMessageDelayed(1, 1000);
        }
    };
    @Override
    public void setData(@Nullable Object data) {

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
    @SuppressLint("WrongConstant")
    @OnClick({R.id.iv_check_map, R.id.tv_check_map,
            R.id.iv_panorama, R.id.tv_panorama, R.id.iv_traffic, R.id.tv_traffic,
            R.id.iv_refresh,R.id.ll_location_now,R.id.iv_group_list,R.id.iv_kefu,R.id.ll_distance,R.id.ll_device_location,R.id.ll_user_location})
    public void onViewClicked(View view) {
        if (Utils.isButtonQuickClick(System.currentTimeMillis())) {
            switch (view.getId()) {
                case R.id.iv_refresh: // 刷新数据
                    if (System.currentTimeMillis() - refresh_time > 5000) {
                        refresh_time = System.currentTimeMillis();
                        getDeviceList(true, false, true, false);
                    } else {
                        ToastUtils.show(getString(R.string.txt_refresh_hint));
                    }
                    break;
                case R.id.ll_distance: // 测距
                    if (selectCarBean == null) {
                        ToastUtils.show(getString(R.string.txt_device_select_hint));
                        return;
                    }
                    isDistance = !isDistance;
                    onPolylineDistance();
                    break;
                case R.id.iv_check_map: // 切换卫星地图
                case R.id.tv_check_map:
                    isSatelliteMap = !isSatelliteMap;
                    onSwitchMapType();
                    break;
                case R.id.iv_panorama: // 街景
                case R.id.tv_panorama:
                    onSeePanorama();
                    break;
                case R.id.iv_traffic: // 路况
                case R.id.tv_traffic:
                    isSwitchMapTraffic = !isSwitchMapTraffic;
                    onSwitchMapTraffic();
                    break;
                case R.id.ll_user_location: // 用户位置
                    onCheckUserOrDeviceLocation(true);
                    break;
                case R.id.ll_device_location: // 设备位置
                    onCheckUserOrDeviceLocation(false);
                    break;
                case R.id.ll_location_now: // C2立即定位功能
                    onLocationC2Now();
                    break;
                case R.id.iv_group_list: // 分组列表
                    launchActivity(GroupManagementActivity.newInstance());
                    break;
                case R.id.iv_kefu: // 客服
                    onUserService();
                    break;
            }
        }
    }

    /**
     * 联系客服
     */
    private void onUserService() {
        Intent intent = new Intent(getContext(), CustomerWebViewActivity.class);
        intent.putExtra("title", getString(R.string.txt_app_user_service));
        intent.putExtra("url", Api.App_Service_Test);
        startActivity(intent);
    }

    /**
     * 切换到前一个设备
     */
    private void onCheckBeforeDevice() {
        switchPosition--;
        if (switchPosition < 0) {
            switchPosition = deviceSwitchBeans.size() - 1;
        }
        selectCarBean = deviceSwitchBeans.get(switchPosition);
        selectImei = selectCarBean.getImei() + "";
        isShowInfoWindow = true;
        onDeviceDetailInfo();
    }

    /**
     * 切换到后一个设备
     */
    private void onCheckNextDevice() {
        switchPosition++;
        if (switchPosition > deviceSwitchBeans.size() - 1) {
            switchPosition = 0;
        }
        selectCarBean = deviceSwitchBeans.get(switchPosition);
        selectImei = selectCarBean.getImei() + "";
        isShowInfoWindow = true;
        onDeviceDetailInfo();
    }

    /**
     * 判断选中设备在切换设备列表数据中的索引位置
     */
    private void onSelectPositionForSwitchList() {
        if (selectCarBean != null) {
            for (int i = 0; i < deviceSwitchBeans.size(); i++) {
                if (deviceSwitchBeans.get(i).getImei() == selectCarBean.getImei()) {
                    switchPosition = i;
                }
            }
        } else {
            switchPosition = -1;
        }
    }

    /**
     * C2设备类型下发定位模式指令
     */
    private void onLocationC2Now() {
        if (selectCarBean == null) {
            ToastUtils.show(getString(R.string.txt_device_select_hint));
            return;
        }

        if (selectCarBean.getState().equals(ResultDataUtils.Device_State_Line_Down)) {
            ToastUtils.show(getString(R.string.txt_location_off_line_hint));
            return;
        }

        AlertBean bean = new AlertBean();
        bean.setType(0);
        bean.setAlert(getString(R.string.txt_location_device_now));
        AlertAppDialog dialog = new AlertAppDialog();
        dialog.show(getFragmentManager(), bean, new AlertAppDialog.onAlertDialogChange() {
            @Override
            public void onConfirm() {
                submitLocationC2Now();
            }

            @Override
            public void onCancel() {

            }
        });
    }

    /**
     * 发起C2设备秒速定位
     */
    private void submitLocationC2Now() {
        if (selectCarBean.getState().equals(ResultDataUtils.Device_State_Line_On)) {
            submitOneKeyFunction(ResultDataUtils.Function_Query_Location);
        } else {
            showLoading();
            llLocationNow.postDelayed(new Runnable() {
                @Override
                public void run() {
                    hideLoading();
                    ToastUtils.show(getString(R.string.txt_location_device_success));
                    isShowInfoWindow = true;
                    onDeviceDetailInfo();
                }
            }, 1000);
        }
    }

    /**
     * 一键功能提交
     *
     * @param key 功能值
     */
    private void submitOneKeyFunction(String key) {
        OnKeyFunctionPutBean.ParamsBean paramsBean = new OnKeyFunctionPutBean.ParamsBean();
        paramsBean.setType(key);
        paramsBean.setSimei(selectCarBean.getSimei());

        OnKeyFunctionPutBean bean = new OnKeyFunctionPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_OnKey_Function);
        bean.setModule(ModuleValueService.Module_For_OnKey_Function);

        showProgressDialog();

        getPresenter().submitOneKeyFunction(bean);
    }

    /**
     * 测距
     */
    @SuppressLint("SetTextI18n")
    private void onPolylineDistance() {
        if (isDistance) {
            ivRangingDistance.setImageResource(R.mipmap.icon_juli);
            tvDistance.setVisibility(View.VISIBLE);
            distanceLatLng.clear();
            if (polylineDistance != null) {
                polylineDistance.remove();
                polylineDistance = null;
            }
            DeviceLocationInfoBean infoBean = DeviceUtils.parseDeviceData(selectCarBean.getLast_pos());
            LatLng device = new LatLng((double) infoBean.getLat() / 1000000, (double) infoBean.getLon() / 1000000);
            LatLng user = new LatLng(mLatitude, mLongitude);
            distanceLatLng.add(device);
            distanceLatLng.add(user);
            //计算p1、p2两点之间的直线距离，单位：米
            mPolylineDistance = DistanceUtil.getDistance(device, user);
            OverlayOptions mOverlayOptions = new PolylineOptions()
                    .width(8)
                    .color(getResources().getColor(R.color.colorPrimary))
                    .points(distanceLatLng);
            polylineDistance = mBaiduMap.addOverlay(mOverlayOptions);
            double distance = mPolylineDistance / 1000;
            tvDistance.setText(Utils.formatValue(distance) + "km");
        } else {
            ivRangingDistance.setImageResource(R.mipmap.icon_juli_unselect);
            tvDistance.setVisibility(View.GONE);
            distanceLatLng.clear();
            if (polylineDistance != null) {
                polylineDistance.remove();
                polylineDistance = null;
            }
        }
    }

    /**
     * 切换用户和设备位置
     *
     * @param isCheck 切换显示，true：用户位置，false：设备位置
     */
    private void onCheckUserOrDeviceLocation(boolean isCheck) {
        if (isCheck) {
            centerPoint = new LatLng(mLatitude, mLongitude);
            ToastUtils.show(getString(R.string.txt_check_location_man));
        } else {
            if (selectCarBean == null) {
                if (deviceSwitchBeans.size() > 0){
                    onLookAllDevice();
                }
                return;
            }
            centerPoint =  new LatLng(selectLatForDevice, selectLonForDevice);
            ToastUtils.show(getString(R.string.txt_check_location_car));
        }
        updateMapCenter();
    }

    /**
     * 查看所有设备
     */
    private void onLookAllDevice(){
        mBuilder = null;
        mBuilder = new LatLngBounds.Builder();
        for (int i = 0; i < deviceSwitchBeans.size(); i++) {
            DeviceLocationInfoBean infoBean = DeviceUtils.parseDeviceData(deviceSwitchBeans.get(i).getLast_pos());
            mBuilder.include(new LatLng((double) infoBean.getLat() / 1000000, (double) infoBean.getLon() / 1000000));
        }

        ivRefresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLngBounds(mBuilder.build(), 800, 800));
                mZoom = mBaiduMap.getMapStatus().zoom;

                ToastUtils.show(getString(R.string.txt_check_location_all_car));
            }
        }, 500);
    }

    /**
     * 重置一些参数
     */
    private void onResetValue() {
        isFirstData = false;
        selectImei = "";
        selectCarBean = null;
        clearData();
        mBaiduMap.clear();
        deviceMap.clear();
        ivRefresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mLocationClient != null) {
                    mLocationClient.start();
                }
            }
        }, 500);
    }

    @Override
    public void getDeviceListSuccess(DeviceListResultBean deviceListResultBean, boolean isInitiativeRefresh, boolean clearMap) {
        mCountDown = mCountDownTime;
        if (isFirstData) {
            isAllDevice = deviceListResultBean.isIs_all();
        }
        isCallAllDev = false;
        if (deviceListResultBean.getItems() == null) {
            if (isFirstData) {
                if (ConstantValue.isLoginForAccount()) {
                    ToastUtils.show(getString(R.string.txt_account_no_device));
                } else {
                    ToastUtils.show(getString(R.string.txt_device_location_no_data));
                }
            }
            onResetValue();
            return;
        }
        if (isFirstData) {
            if (deviceListResultBean.getItems().size() == 0) {
                ToastUtils.show(getString(R.string.txt_account_no_device));
                onResetValue();
                return;
            }
        }
        if (clearMap) {
            selectCarBean = null;
            clearData();
            mBaiduMap.clear();
            deviceMap.clear();
        }

        deviceSwitchBeans.clear();
        deviceDataBeans.clear();
        deviceDataBeans.addAll(deviceListResultBean.getItems());
        if (selectCarBean != null) {
            selectImei = selectCarBean.getImei() + "";
        }
        mBuilder = null;
        mBuilder = new LatLngBounds.Builder();

        for (int i = 0; i < deviceDataBeans.size(); i++) {
            DeviceListResultBean.ItemsBean bean = deviceDataBeans.get(i);
            DeviceLocationInfoBean infoBean = DeviceUtils.parseDeviceData(bean.getLast_pos());
            // 设备的定位信息
            if (TextUtils.isEmpty(bean.getLast_pos()) || infoBean.getLat() == 0 || infoBean.getLon() == 0) {
                if (isFirstData && deviceDataBeans.size() == 1) {
                    ToastUtils.show(getString(R.string.txt_device_location_no_data));
                }
                continue;
            }
            deviceSwitchBeans.add(bean);
            mLatLngLocation = new LatLng((double) infoBean.getLat() / 1000000, (double) infoBean.getLon() / 1000000);

            if (!TextUtils.isEmpty(selectImei) && selectImei.equals(bean.getImei() + "")) {
                selectCarBean = bean;
            }

            if (deviceMap.get(bean.getImei() + "") != null) {
                Objects.requireNonNull(deviceMap.get(bean.getImei() + "")).setPosition(mLatLngLocation);
                Objects.requireNonNull(deviceMap.get(bean.getImei() + "")).setIcon(getMarkerIcon(bean, infoBean.getDirection()));
            } else {
                MarkerOptions markerOption = new MarkerOptions();
                markerOption.position(mLatLngLocation);
                markerOption.title(bean.getImei() + "");
                markerOption.icon(getMarkerIcon(bean, infoBean.getDirection()));
                markerOption.anchor(0.5f, 0.8f);
                markerOption.draggable(false);
                markerOption.visible(true);
                Marker marker = (Marker) mBaiduMap.addOverlay(markerOption);
                deviceMap.put(bean.getImei() + "", marker);
            }
            mBuilder.include(mLatLngLocation);
        }

        if (isFirstData || isOperationalRestrictions) {
            if (deviceDataBeans.size() == 1) {
                mZoom = 17;
                DeviceLocationInfoBean location = DeviceUtils.parseDeviceData(deviceDataBeans.get(0).getLast_pos());
                if (location.getLat() != 0 && location.getLon() != 0) {
                    if (selectCarBean == null) {
                        selectCarBean = deviceDataBeans.get(0);
                    }
                }
            }
        }

        if (selectCarBean != null) {
            selectImei = selectCarBean.getImei() + "";
            isShowInfoWindow = true;
            onDeviceDetailInfo();
        }
        if (isInitiativeRefresh) {
            ivRefresh.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (deviceDataBeans.size() > 0) {
                        if (selectCarBean == null) {
                            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLngBounds(mBuilder.build(), 800, 800));
                            mZoom = mBaiduMap.getMapStatus().zoom;
                        }
                    }
                }
            }, 500);
        }
        isFirstData = false;
        isOperationalRestrictions = false;
    }

    @Override
    public void getDeviceListError(int errcode) {
        isFirstData = false;
        if (errcode == Api.Operational_Restrictions) {
            isOperationalRestrictions = true;
            mZoom = 17;
        } else if (errcode == Api.Device_Freeze || errcode == Api.Data_Change) {
            getDeviceDetailError();
        }
    }

    @Override
    public void getDeviceDetailError() {
        isFirstData = true;
        mCountDown = mCountDownTime;
        mSimeiLists.clear();
        getDeviceList(true, true, true, true);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getDeviceDetailInfoSuccess(DeviceDetailResultBean deviceDetailResultBean) {
        selectDeviceDetailBean = deviceDetailResultBean;
        setDeviceDetailData(deviceDetailResultBean);
    }

    //获得分组列表成功后，弹框提示合并到选择的分组 当中去
    @Override
    public void getDeviceGroupListSuccess(DeviceGroupResultBean deviceGroupResultBean, boolean isRefresh,String code) {
        if (isRefresh) {
            groupDataBeans.clear();
        }
        if (deviceGroupResultBean.getGarages() != null && deviceGroupResultBean.getGarages().size() > 0) {
            mLastGroupId = deviceGroupResultBean.getGarages().get(deviceGroupResultBean.getGarages().size() - 1).getSid();
            groupDataBeans.addAll(deviceGroupResultBean.getGarages());
            if (groupDataBeans.size() > 0) {
                groupDataBeans.get(0).setSelect(true);
            }
            mergeAccountDialog.dismiss();
            onMergeGroupSelect(code);
        }
    }

    @Override
    public void finishRefresh() {
    }

    @Override
    public void endLoadMore() {
    }

    @Override
    public void setNoMore() {
    }

    @Override
    public void endLoadFail() {
    }
    @Override
    public void getDeviceListForGroupSuccess(DeviceListForManagerResultBean deviceListForManagerResultBean, boolean isRefresh) {
    }
    @Override
    public void endLoadMoreForDevice() {
    }

    @Override
    public void setNoMoreForDevice() {
    }

    @Override
    public void endLoadFailForDevice() {
    }

    @Override
    public void submitOneKeyFunctionSuccess(BaseBean baseBean) {
        ToastUtils.show(getString(R.string.txt_location_device_success));
    }

    @Override
    public void onDismissProgress() {
        dismissProgressDialog();
    }

    @Override
    public void getPhoneCodeSuccess(PhoneCodeResultBean phoneCodeResultBean) {
        ToastUtils.show(getString(R.string.errcode_success));
    }

    @Override
    public void submitMergeAccountSuccess(MergeAccountResultBean mergeAccountResultBean) {
        if (mergeAccountResultBean.getErrcode() == Api.Mobile_Code_Error){
            onShowMergeAccount();
        }else if (mergeAccountResultBean.isSuccess()){
            if (mergeAccountResultBean.getFail_item() != null && mergeAccountResultBean.getFail_item().size() > 0) {
                MergeAccountFailDialog dialog = new MergeAccountFailDialog();
                dialog.show(getFragmentManager(), mergeAccountResultBean.getFail_item());
            } else {
                ToastUtils.show(getString(R.string.txt_merge_success));
                getDeviceList(true, true, true, false);
            }
        }
    }

    /**
     * 绘制设备图标等信息
     *
     * @param theDevice
     * @return
     */
    private BitmapDescriptor getMarkerIcon(DeviceListResultBean.ItemsBean theDevice, float direction) {
        View view = View.inflate(getContext(), R.layout.layout_marker_item, null);
        TextView tvName = view.findViewById(R.id.tv_name);
        ImageView ivCar = view.findViewById(R.id.iv_car);
        if (TextUtils.isEmpty(theDevice.getCar_number())) {
            if (String.valueOf(theDevice.getImei()).length() > 6) {
                tvName.setText(String.valueOf(theDevice.getImei()).substring(String.valueOf(theDevice.getImei()).length() - 6));
            } else {
                tvName.setText(String.valueOf(theDevice.getImei()));
            }
        } else {
            tvName.setText(theDevice.getCar_number());
        }

        getDeviceState(theDevice, ivCar, tvName, direction);
        return convertViewToBitmap(view);
    }

    private static BitmapDescriptor convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        return BitmapDescriptorFactory.fromBitmap(view.getDrawingCache());
    }

    private void getDeviceState(DeviceListResultBean.ItemsBean theDevice, ImageView ivView, TextView tvView, float direction) {
        if (theDevice.getState().equals(ResultDataUtils.Device_State_Line_On)) {
            tvView.setTextColor(getResources().getColor(R.color.color_73CA6C));
            tvView.setBackgroundResource(R.mipmap.icon_online_bg);
            switch (theDevice.getCar_image()) {
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
        } else if (theDevice.getState().equals(ResultDataUtils.Device_State_Line_Sleep)) {
            tvView.setTextColor(getResources().getColor(R.color.color_F22E13));
            tvView.setBackgroundResource(R.mipmap.icon_static_bg);
            switch (theDevice.getCar_image()) {
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
            tvView.setTextColor(getResources().getColor(R.color.color_AAAAAA));
            tvView.setBackgroundResource(R.mipmap.icon_offline_bg);
            switch (theDevice.getCar_image()) {
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
        if (theDevice.getCar_image() == 0 || theDevice.getCar_image() == 1 || theDevice.getCar_image() == 2) {
            ivView.setRotation(direction);//旋转
        }
    }

    /**
     * 获取设备详细信息
     */
    private void onDeviceDetailInfo(){
        // 获取设备详细信息
        getDeviceDetailInfo();
    }

    /**
     * 更新悬浮框数据
     */
    @SuppressLint({"SetTextI18n", "DefaultLocale", "SimpleDateFormat"})
    private void setDeviceDetailData(DeviceDetailResultBean deviceDetailResultBean) {
        if (isDistance) {
            distanceLatLng.clear();
            if (polylineDistance != null) {
                polylineDistance.remove();
                polylineDistance = null;
            }
        }
        if (selectCarBean != null) {
            MyApplication.getMyApp().setSimei(selectCarBean.getSimei());
            MyApplication.getMyApp().setLocInfo(selectCarBean.getLast_pos());
            MyApplication.getMyApp().setDeviceState(selectCarBean.getState());
            MyApplication.getMyApp().setImei(selectCarBean.getImei());
            MyApplication.getMyApp().setPower(selectCarBean.getPower());
            MyApplication.getMyApp().setVersion(selectCarBean.getVersion());
            MyApplication.getMyApp().setCarImageId(selectCarBean.getCar_image());
            MyApplication.getMyApp().setCarName(selectCarBean.getCar_number());
            DeviceLocationInfoBean bean = DeviceUtils.parseDeviceData(selectCarBean.getLast_pos());
            MyApplication.getMyApp().setLatAndLon(bean.getLat() + "," + bean.getLon());
            deviceVersion = selectCarBean.getVersion();
            if (selectCarBean.getVersion().equals(FunctionType.C2)) {
                llLocationNow.setVisibility(View.VISIBLE);
            } else {
                llLocationNow.setVisibility(View.GONE);
            }

            // 选中设备置于地图中间位置
            if (ConstantValue.isLoginForAccount()) {
                centerPoint = new LatLng((double) bean.getLat() / 1000000, (double) bean.getLon() / 1000000);
                if (isFirstData) {
                    mZoom = 17;
                }
                updateMapCenter();
            }else{
                if (isDeviceCenter){
                    centerPoint = new LatLng((double) bean.getLat() / 1000000, (double) bean.getLon() / 1000000);
                    if (isFirstData) {
                        mZoom = 17;
                    }
                    updateMapCenter();
                }
            }

            if (TextUtils.isEmpty(selectCarBean.getCar_number())) {
                tvTitleName.setText(String.valueOf(selectCarBean.getImei()));
            } else {
                tvTitleName.setText(selectCarBean.getCar_number());
            }
            tvDumpEnergy.setText(getString(R.string.txt_dump_energy) + selectCarBean.getPower() + "%");
            // 选中设备的状态
            String strState = "";
            switch (selectCarBean.getState()) {
                case ResultDataUtils.Device_State_Line_Down:
                    strState = getString(R.string.txt_status) + getString(R.string.txt_state_line_down);
                    break;
                case ResultDataUtils.Device_State_Line_On:
                    strState = getString(R.string.txt_status) + getString(R.string.txt_state_line_on);
                    break;
                case ResultDataUtils.Device_State_Line_Sleep:
                    strState = getString(R.string.txt_status) + getString(R.string.txt_state_line_static);
                    break;
            }
            mapInfoWindow.tvDeviceState.setText(strState);
            // 判断是否是C2设备
            if (FunctionType.C2.equals(selectCarBean.getVersion())) {
                if (ResultDataUtils.Device_State_Line_Sleep.equals(selectCarBean.getState())) {
                    mapInfoWindow.tvLocationTime.setText(getString(R.string.txt_location_time) + DateUtils.getTodayDateTime());
                } else {
                    mapInfoWindow.tvLocationTime.setText(getString(R.string.txt_location_time) + DateUtils.timeConversionDate_two(String.valueOf(bean.getTime())));
                }
            } else {
                mapInfoWindow.tvLocationTime.setText(getString(R.string.txt_location_time) + DateUtils.timeConversionDate_two(String.valueOf(bean.getTime())));
            }
            mapInfoWindow.tvSpeed.setText(((double) bean.getSpeed() / 10) + "km/h");
            mapInfoWindow.ivLocationType.setImageResource(ResultDataUtils.getLocationType(bean.getType()));

            // 判断是否是C2设备
            if (FunctionType.C2.equals(selectCarBean.getVersion())) {
                if (ResultDataUtils.Device_State_Line_Sleep.equals(selectCarBean.getState())) {
                    String mComTime = DateUtils.getTodayDateTime();
                    try {
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                        Date recrodEndTime = df.parse(DateUtils.getTodayDateTime());
                        mComTime = df.format(new Date(recrodEndTime.getTime() + 1000));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mapInfoWindow.tvCommunicationTime.setText(getString(R.string.txt_communication_time) + mComTime);
                } else {
                    mapInfoWindow.tvCommunicationTime.setText(getString(R.string.txt_communication_time)
                            + DateUtils.timeConversionDate_two(String.valueOf(deviceDetailResultBean.getLast_com_time())));
                }
            } else {
                mapInfoWindow.tvCommunicationTime.setText(getString(R.string.txt_communication_time)
                        + DateUtils.timeConversionDate_two(String.valueOf(deviceDetailResultBean.getLast_com_time())));
            }
            // 判断是否在充电
            if (ResultDataUtils.Acc_Power_Charge.equals(deviceDetailResultBean.getAcc_power())) {
                mapInfoWindow.iv_power.setVisibility(View.VISIBLE);
            } else {
                mapInfoWindow.iv_power.setVisibility(View.GONE);
            }
            mapInfoWindow.tvDayMileage.setText(Utils.formatValue(deviceDetailResultBean.getDay_distance() / 1000) + "km");
            String timeDes = "";
            if (selectCarBean.getState().equals(ResultDataUtils.Device_State_Line_On)) {
                timeDes = getString(R.string.txt_on_line_time) + "：";
            }
            if (selectCarBean.getState().equals(ResultDataUtils.Device_State_Line_Sleep)) {
                timeDes = getString(R.string.txt_static_time_total) + "：";
            }
            if (selectCarBean.getState().equals(ResultDataUtils.Device_State_Line_Down)) {
                timeDes = getString(R.string.txt_off_line_time) + "：";
            }
            mapInfoWindow.tvStaticTime.setText(timeDes+ResultDataUtils.convertTimemills(deviceDetailResultBean.getState_time()*1000, getContext()));

            mapInfoWindow.tvSignalValueNumber.setText(deviceDetailResultBean.getSigna_val() + "");
            mapInfoWindow.tvSignalNumber.setText(deviceDetailResultBean.getSignal_rate() + "");
            mapInfoWindow.ivSignal.setImageResource(ResultDataUtils.getSignal(deviceDetailResultBean.getSignal_rate()));
            ResultDataUtils.setElectricImageData(selectCarBean.getPower(),mapInfoWindow.tvPower);
            ResultDataUtils.setAccState(getContext(), deviceDetailResultBean.getAcc_state(), mapInfoWindow.tvAcc);
            mapInfoWindow.tvLocationMode.setText(getString(R.string.txt_function_location_mode) + "：" +ResultDataUtils.getLocationMode(getContext(), deviceDetailResultBean.getDetail().getMode(),
                    deviceVersion.toUpperCase(),deviceDetailResultBean.getMode_fun()));
            if (deviceDetailResultBean.getHistory() == 1) {
                mapInfoWindow.llHistoryTrack.setVisibility(View.VISIBLE);
            } else {
                mapInfoWindow.llHistoryTrack.setVisibility(View.GONE);
            }
            // 设备定位模式
            MyApplication.getMyApp().setDeviceMode(deviceDetailResultBean.getDetail().getMode());
            MyApplication.getMyApp().setSignal_rate(deviceDetailResultBean.getSignal_rate());
            MyApplication.getMyApp().setSigna_val(deviceDetailResultBean.getSigna_val());
            MyApplication.getMyApp().setmIccid(deviceDetailResultBean.getIccid());


            if (isDistance) {
                onPolylineDistance();
            }

            selectLatForDevice = (double) bean.getLat() / 1000000;
            selectLonForDevice = (double) bean.getLon() / 1000000;
            //显示地址
            LocationAddressParsed.getLocationParsedInstance().Parsed(selectLatForDevice,selectLonForDevice)
                    .setAddressListener(new LocationAddressParsed.getAddressListener() {
                        @Override
                        public void getAddress(String str) {
                            if (getContext() != null) {
                                if (!TextUtils.isEmpty(str)) {
                                    mapInfoWindow.tvAddress.setText(getString(R.string.txt_address) + str);
                                } else {
                                    mapInfoWindow.tvAddress.setText(getString(R.string.txt_address) + selectLatForDevice + "," + selectLonForDevice);
                                }
                            }
                        }
                    });


            if (popInfoWindow != null) {
                if (isAutoFresh) {
                    isAutoFresh = false;
                } else {
                    mBaiduMap.hideInfoWindow();
                }
                mBaiduMap.getAllInfoWindows().remove(popInfoWindow);
                popInfoWindow = null;
            }
            mapView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showInfoWindow();
                }
            }, 500);
        }
    }

    /**
     * 显示气泡框
     */
    private void showInfoWindow(){
        if (ConstantValue.isLoginForAccount()) {
            changeInfoWindow();
        }else{
            if (isDeviceCenter){
                changeInfoWindow();
            }
        }
    }

    private void changeInfoWindow() {
        if (selectCarBean.getImei() != 0 && deviceMap.get(selectCarBean.getImei() + "") != null) {
            if (isShowInfoWindow) {
                popInfoWindow = new InfoWindow(mapInfoWindow.viewInfoWindows, deviceMap.get(selectCarBean.getImei() + "").getPosition(), -150);
                mBaiduMap.showInfoWindow(popInfoWindow);
            } else {
                mBaiduMap.hideInfoWindow();
            }
        }
    }

    @Override
    public void onMapLoaded() {
        //重新设置地图中心点
        int density = (int) ScreenUtils.getScreenDensity();
        int heightY = ScreenUtils.getScreenHeight() / 2 + 45 * density;
        Point point = new Point(ScreenUtils.getScreenWidth() / 2,heightY);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().targetScreen(point).build()));
    }

    /**
     * 设备列表-选择设备
     *
     * @param imei  设备未加密的imei号
     * @param simei 设备加密的simei号
     */
    private void deviceClickForGroup(long imei, String simei) {
        selectImei = imei + "";
        if (isAllDevice) {
            selectCarBean = null;
            if (!TextUtils.isEmpty(selectImei)) {
                for (DeviceListResultBean.ItemsBean bean : deviceDataBeans) {
                    if (selectImei.equals(bean.getImei() + "")) {
                        selectCarBean = bean;
                        break;
                    }
                }
                onSelectPositionForSwitchList();
                if (selectCarBean != null) {
                    isShowInfoWindow = true;
//                    onDeviceDetailInfo();
                } else {
                    mSimeiLists.clear();
                    mSimeiLists.add(simei);
                    getDeviceList(true, true, true, false);
                }
            }
        } else {
            mSimeiLists.clear();
            mSimeiLists.add(simei);
            isClearMap = true; // 清理地图，如果设备存在于已加载的设备列表中，则不清理地图
            for (DeviceListResultBean.ItemsBean bean : deviceDataBeans) {
                if (imei == bean.getImei()) {
                    isClearMap = false;
                    break;
                }
            }
            getDeviceList(true, true, isClearMap, false);
        }
    }

    /**
     * 获取车组织列表和车组列表
     *
     * @param isRefresh 是否是刷新设备分组
     */
    private void getDeviceGroupList(boolean isRefresh, boolean isShow,String code) {
        if (ConstantValue.isAccountLogin()) {
            DeviceGroupPutBean.ParamsBean paramsBean = new DeviceGroupPutBean.ParamsBean();
            paramsBean.setF_limit_size(0);
            paramsBean.setG_limit_size(mLimitSize);
            paramsBean.setFamilyid(ConstantValue.getFamilySid());
            if (isRefresh){
                paramsBean.setGet_all(true);
            }
            if (!TextUtils.isEmpty(mLastGroupId)) {
                paramsBean.setLast_gid(mLastGroupId);
            }
            DeviceGroupPutBean bean = new DeviceGroupPutBean();
            bean.setParams(paramsBean);
            bean.setFunc(ModuleValueService.Fuc_For_Device_Group_List);
            bean.setModule(ModuleValueService.Module_For_Device_Group_List);

            getPresenter().getDeviceGroupList(bean, isRefresh,code);
        }
    }


    /**
     * 合并账号，选择分组合并
     *
     * @param code
     */
    private void onMergeGroupSelect(String code) {
        ivRangingDistance.postDelayed(new Runnable() {
            @Override
            public void run() {
                onShowMergeGroupSelect(code);
            }
        }, 300);
    }

    /**
     * 合并账号，选择分组合并
     *
     * @param code
     */
    private void onShowMergeGroupSelect(String code) {
        GroupSelectDialog dialog = new GroupSelectDialog();
        dialog.show(getFragmentManager(), groupDataBeans, new GroupSelectDialog.onGroupSelectferDeviceChange() {
            @Override
            public void onGroupSelect(String sid) {
                submitMergeAccount(code, sid);
            }

            @Override
            public void onCancel() {
                onShowMergeAccount();
            }
        });
    }


    /**
     * 发送验证码
     */
    private void onSendPhoneCode() {
        PhoneCodePutBean.ParamsBean paramsBean = new PhoneCodePutBean.ParamsBean();
        paramsBean.setCode(Api.Mob_Module_Code);
        paramsBean.setKey(Api.Mob_App_Key);
        paramsBean.setPhone(ConstantValue.getAccount());
        paramsBean.setZone(mPhoneZone);

        PhoneCodePutBean bean = new PhoneCodePutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Phone_Code);
        bean.setModule(ModuleValueService.Module_For_Phone_Code);

        getPresenter().getPhoneCode(bean);
    }

    /**
     * 提交合并账号
     *
     * @param code
     * @param gid
     */
    private void submitMergeAccount(String code, String gid) {
        MergeAccountPutBean.ParamsBean paramsBean = new MergeAccountPutBean.ParamsBean();
        paramsBean.setCode(code);
        paramsBean.setKey(Api.Mob_App_Key);
        paramsBean.setSgid(gid);

        MergeAccountPutBean bean = new MergeAccountPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Merge_Account);
        bean.setModule(ModuleValueService.Module_For_Merge_Account);

        getPresenter().submitMergeAccount(bean);
    }

    /**
     * 显示合并账号框
     */
    private void onShowMergeAccount(){
        ivRangingDistance.postDelayed(new Runnable() {
            @Override
            public void run() {
                onMergeAccount();
            }
        }, 300);
    }

    /**
     * 当前位置是否包含街景
     */
    private boolean hasPanorama(double lat, double lon) {
        com.baidu.lbsapi.tools.Point sourcePoint = new com.baidu.lbsapi.tools.Point(lon, lat);
        com.baidu.lbsapi.tools.Point resultPointLL = CoordinateConverter.converter(CoordinateConverter.COOR_TYPE.COOR_TYPE_GCJ02, sourcePoint);
        PanoramaRequest request = PanoramaRequest.getInstance(mContext);
        boolean hasFlag = false;
        try {
            BaiduPanoData locationPanoData = request.getPanoramaInfoByLatLon(resultPointLL.x, resultPointLL.y);
            //是否有外景(街景)
            hasFlag = locationPanoData.hasStreetPano();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasFlag;
    }
}
