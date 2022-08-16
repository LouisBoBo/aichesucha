package com.car.scth.mvp.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.geocoder.RegeocodeRoad;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.car.scth.R;
import com.car.scth.app.MyApplication;
import com.car.scth.di.component.DaggerAlarmMessageUserComponent;
import com.car.scth.mvp.contract.AlarmMessageUserContract;
import com.car.scth.mvp.model.api.ModuleValueService;
import com.car.scth.mvp.model.bean.AlarmRecordResultBean;
import com.car.scth.mvp.model.bean.AlarmScreenDeviceBean;
import com.car.scth.mvp.model.bean.AlertBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.AlarmDeletePutBean;
import com.car.scth.mvp.model.putbean.AlarmRecordPutBean;
import com.car.scth.mvp.presenter.AlarmMessageUserPresenter;
import com.car.scth.mvp.ui.activity.AlarmScreenActivity;
import com.car.scth.mvp.ui.activity.AlarmSettingActivity;
import com.car.scth.mvp.ui.adapter.AlarmRecordUserAdapter;
import com.car.scth.mvp.ui.adapter.SearchDeviceAdapter;
import com.car.scth.mvp.ui.view.MyLoadMoreView;
import com.car.scth.mvp.utils.ConstantValue;
import com.car.scth.mvp.utils.ToastUtils;
import com.car.scth.mvp.utils.Utils;
import com.car.scth.mvp.weiget.AlertAppDialog;
import com.car.scth.mvp.weiget.AlertSelectDatePopupWindow;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 报警消息-个人版
 * <p>
 * Created by MVPArmsTemplate on 10/29/2020 09:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class AlarmMessageUserFragment extends BaseFragment<AlarmMessageUserPresenter> implements AlarmMessageUserContract.View {

    @BindView(R.id.iv_setting)
    ImageView ivSetting; // 设置报警消息
    @BindView(R.id.iv_data)
    ImageView ivData; // 时间筛选
    @BindView(R.id.tv_input_keyword)
    TextView tvInputKeyword; // 输入关键词搜索
    @BindView(R.id.iv_screen)
    ImageView ivScreen; // 筛选
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.srl_view)
    SwipeRefreshLayout srlView;
    @BindView(R.id.rl_alarm_head)
    RelativeLayout alarmHead;
    @BindView(R.id.edt_search_imei)
    EditText edtSearchImei; // 搜索
    @BindView(R.id.tv_search_cancel)
    TextView tvSearchCancel; // 搜索
    @BindView(R.id.recyclerView_search)
    RecyclerView recyclerViewSearch; // 搜索
    @BindView(R.id.ll_search)
    LinearLayout llSearch; // 搜索

    private TextView tvPrompt;
    private List<AlarmRecordResultBean.ItemsBean> alarmRecordBeans;
    private AlarmRecordUserAdapter mAdapter;
    private int mLimitSize = 20; // 数据的限制条数，默认20条,最高不超过100
    private int startTime = 0; // 筛选开始时间
    private int endTime = 0; // 筛选结束时间
    private long lastImei = 0; // 上次请求返回的最后一个的imei，首次请求可以不填
    private long lastTime = 0; // 上次请求返回的最后一个的时间，首次请求可以不填
    private String lastType; // 上次请求返回的最后一个的类型，首次请求可以不填
    private List<String> screenTypeLists; // 筛选的类型值
    private List<String> mSimeiBeas; // 设备列表,选填，如果设备登入不填,注意只能筛选同一个sfamilyid下的设备
    private int mPosition; // 选中设备索引位置
    private String userFamilyId; // 用户的familyId
    private String mSimei = ""; // 选中设备的simei号

    private ChangePageReceiver receiver; // 注册广播接收器
    private static final int INTENT_TYPE = 11; // 报警类型筛选
    private AlertSelectDatePopupWindow mPopupWindow; // 日期选择弹框
    private int mAddressPosition; // 选中的item索引位置

    // 高德地图通过经纬度获取地址信息
    private RegeocodeQuery query;
    private GeocodeSearch geocoderSearch; // 地理编码，通过经纬度获取地址信息


    public static AlarmMessageUserFragment newInstance() {
        AlarmMessageUserFragment fragment = new AlarmMessageUserFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerAlarmMessageUserComponent //如找不到该类,请编译一下项�?
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alarm_message_user, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        alarmRecordBeans = new ArrayList<>();
        mSimeiBeas = new ArrayList<>();
        screenTypeLists = new ArrayList<>();
        userFamilyId = ConstantValue.getFamilySid();

        geocoderSearch = new GeocodeSearch(getContext());
        geocoderSearch.setOnGeocodeSearchListener(aMaplistener);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        srlView.setColorSchemeResources(R.color.color_00A7FF, R.color.color_00A7FF);
        srlView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                lastImei = 0;
                lastTime = 0;
                getAlarmRecord(false, true);
            }
        });

        View statusView = LayoutInflater.from(getContext()).inflate(R.layout.layout_status, recyclerView, false);
        tvPrompt = statusView.findViewById(R.id.txt_wrong_status);
        tvPrompt.setText(getString(R.string.error_no_data_alarm));
        tvPrompt.setVisibility(View.GONE);

        mAdapter = new AlarmRecordUserAdapter(R.layout.item_alarm_record_for_user, alarmRecordBeans);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setEmptyView(statusView);

        LoadMoreView loadMoreView = new MyLoadMoreView();
        mAdapter.setLoadMoreView(loadMoreView);
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getAlarmRecord(false, false);
            }
        }, recyclerView);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                onDeleteAlarmConfirm(position);
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mAddressPosition = position;
                // 如果是经纬度，点击解析详细地址
                getAddressLocation(alarmRecordBeans.get(position).getAddr());
            }
        });

        // 注册一个广播接收器，用于接收从首页跳转到报警消息页面的广播
        IntentFilter filter = new IntentFilter();
        filter.addAction("alarm_message");
        receiver = new ChangePageReceiver();
        //注册切换页面广播接收者
        LocalBroadcastManager.getInstance(MyApplication.getMyApp()).registerReceiver(receiver, filter);
    }

    /**
     * 页面切换广播，广播接收器
     */
    private class ChangePageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (llSearch.getVisibility() == View.VISIBLE){
                llSearch.setVisibility(View.GONE);
                edtSearchImei.setText("");
            }
            mSimei = MyApplication.getMyApp().getSimei();
            onResetScreenData();
            onResetDeviceData(false);
            edtSearchImei.setText(mSimei);

        }
    }

    /**
     * 显示无数据提示
     */
    private void onShowNoData() {
        if (alarmRecordBeans.size() > 0) {
            tvPrompt.setVisibility(View.GONE);
        } else {
            tvPrompt.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 充值筛选条件值
     */
    private void onResetScreenData() {
        startTime = 0;
        endTime = 0;
        lastImei = 0;
        lastTime = 0;
        lastType = "";
        screenTypeLists.clear();
        mSimeiBeas.clear();
        if (!TextUtils.isEmpty(mSimei)) {
            mSimeiBeas.add(mSimei);
        }
    }

    /**
     * 重置相关请求信息
     */
    private void onResetDeviceData(boolean isShow) {
        getAlarmRecord(isShow, true);
    }

    /**
     * 获取报警消息列表
     *
     * @param isShow    是否弹出加载看
     * @param isRefresh 是否是刷新数据
     */
    private void getAlarmRecord(boolean isShow, boolean isRefresh) {
        AlarmRecordPutBean.ParamsBean paramsBean = new AlarmRecordPutBean.ParamsBean();
        paramsBean.setLimit_size(mLimitSize);
        if (lastImei != 0) {
            paramsBean.setLast_imei(lastImei);
        }
        if (lastTime != 0) {
            paramsBean.setLast_time(lastTime);
        }
        if (startTime != 0) {
            paramsBean.setStart_time(startTime);
        }
        if (endTime != 0) {
            paramsBean.setEnd_time(endTime);
        }
        if (!TextUtils.isEmpty(lastType)) {
            paramsBean.setLast_type(lastType);
        }
        if (ConstantValue.getAccountType().equals(ModuleValueService.Login_Account)) {
            paramsBean.setSfamilyid(userFamilyId);
        }
        if (mSimeiBeas.size() > 0) {
            paramsBean.setSimei(mSimeiBeas);
        }
        if (screenTypeLists.size() > 0) {
            paramsBean.setType(screenTypeLists);
        }
        AlarmRecordPutBean bean = new AlarmRecordPutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Alarm_Record);
        bean.setModule(ModuleValueService.Module_For_Alarm_Record);
        bean.setParams(paramsBean);

        if (isShow) {
            showProgressDialog();
        }

        getPresenter().getAlarmRecord(bean, isShow, isRefresh);
    }

    /**
     * 二次确认是否删除报警消息
     */
    private void onDeleteAlarmConfirm(int position) {
        AlertBean bean = new AlertBean();
        bean.setTitle(getString(R.string.txt_tip));
        bean.setAlert(getString(R.string.txt_alarm_delete_hint));
        bean.setType(0);
        AlertAppDialog dialog = new AlertAppDialog();
        dialog.show(getFragmentManager(), bean, new AlertAppDialog.onAlertDialogChange() {
            @Override
            public void onConfirm() {
                submitAlarmDelete(position);
            }

            @Override
            public void onCancel() {

            }
        });
    }

    /**
     * 删除报警消息
     *
     * @param position
     */
    private void submitAlarmDelete(int position) {
        mPosition = position;
        AlarmRecordResultBean.ItemsBean itemsBean = alarmRecordBeans.get(mPosition);

        AlarmDeletePutBean.ParamsBean paramsBean = new AlarmDeletePutBean.ParamsBean();
        paramsBean.setTime(itemsBean.getTime());
        paramsBean.setType(itemsBean.getType());
        if (!TextUtils.isEmpty(itemsBean.getSimei())) {
            paramsBean.setSimei(itemsBean.getSimei());
        }

        AlarmDeletePutBean bean = new AlarmDeletePutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Alarm_Delete);
        bean.setModule(ModuleValueService.Module_For_Alarm_Delete);
        bean.setParams(paramsBean);

        showProgressDialog();

        getPresenter().submitAlarmDelete(bean);
    }

    /**
     * 获取地址信息
     *
     * @param addressLocation
     */
    private void getAddressLocation(String addressLocation) {
        // 开始判断，如果是经纬度，则启动app自解析地址
        String address = addressLocation;
        address = address.replace(".", "");
        address = address.replace(",", "");
        address = address.replace("-", "");
        address = address.replace(" ", "");
        if (!TextUtils.isEmpty(address)) {
            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher isNum = pattern.matcher(address);
            if (isNum.matches()) {
                String[] location = addressLocation.split(",");
                LatLonPoint latLonPoint = new LatLonPoint(Double.parseDouble(location[0]), Double.parseDouble(location[1]));
                // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
                query = new RegeocodeQuery(latLonPoint, 500, GeocodeSearch.AMAP);
                query.setExtensions("all");
                geocoderSearch.getFromLocationAsyn(query);
            }
        }
    }

    /**
     * 通过此方法可以使 Fragment 能够与外界做一些交互和通信, 比如说外部的 Activity 想让自己持有的某�?Fragment 对象执行一些方�?
     * 建议在有多个需要与外界交互的方法时, 统一�?{@link Message}, 通过 what 字段来区分不同的方法, �?{@link #setData(Object)}
     * 方法中就可以 {@code switch} 做不同的操作, 这样就可以用统一的入口方法做多个不同的操�? 可以起到分发的作�?     * <p>
     * 调用此方法时请注意调用时 Fragment 的生命周�? 如果调用 {@link #setData(Object)} 方法�?{@link Fragment#onCreate(Bundle)} 还没执行
     * 但在 {@link #setData(Object)} 里却调用�?Presenter 的方�? 是会报空�? 因为 Dagger 注入是在 {@link Fragment#onCreate(Bundle)} 方法中执行的
     * 然后才创建的 Presenter, 如果要做一些初始化操作,可以不必让外部调�?{@link #setData(Object)}, �?{@link #initData(Bundle)} 中初始化就可以了
     * <p>
     * Example usage:
     * <pre>
     * public void setData(@Nullable Object data) {
     *     if (data != null && data instanceof Message) {
     *         switch (((Message) data).what) {
     *             case 0:
     *                 loadData(((Message) data).arg1);
     *                 break;
     *             case 1:
     *                 refreshUI();
     *                 break;
     *             default:
     *                 //do something
     *                 break;
     *         }
     *     }
     * }
     *
     * // call setData(Object):
     * Message data = new Message();
     * data.what = 0;
     * data.arg1 = 1;
     * fragment.setData(data);
     * </pre>
     *
     * @param data 当不需要参数时 {@code data} 可以�?{@code null}
     */
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

    @Override
    public void killMyself() {

    }

    @OnClick({R.id.iv_setting, R.id.iv_data, R.id.tv_input_keyword, R.id.iv_screen, R.id.tv_search_cancel})
    public void onViewClicked(View view) {
        if (Utils.isButtonQuickClick(System.currentTimeMillis())) {
            Intent intent = new Intent();
            switch (view.getId()) {
                case R.id.iv_setting:
                    if (TextUtils.isEmpty(MyApplication.getMyApp().getImei() + "") || MyApplication.getMyApp().getImei() == 0) {
                        ToastUtils.show(getString(R.string.txt_device_select_location));
                        return;
                    }
                    launchActivity(AlarmSettingActivity.newInstance());
                    break;
                case R.id.iv_data:
                    if (mPopupWindow != null && mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                    } else {
                        showDateSelectPopupWindow();
                    }
                    break;
                case R.id.tv_input_keyword: // 设备号筛选
                    llSearch.setVisibility(View.VISIBLE);
                    break;
                case R.id.iv_screen:
                    intent.setClass(getContext(), AlarmScreenActivity.class);
                    startActivityForResult(intent, INTENT_TYPE);
                    break;
                case R.id.tv_search_cancel:
                    hideKeyboard(edtSearchImei);
                    llSearch.setVisibility(View.GONE);
                    edtSearchImei.setText("");
                    break;
            }
        }
    }

    /**
     * 日期选择弹框
     */
    private void showDateSelectPopupWindow() {
        mPopupWindow = new AlertSelectDatePopupWindow(getContext(), startTime, endTime);
        mPopupWindow.showAsDropDown(alarmHead);
        mPopupWindow.setSelectTimeChange(new AlertSelectDatePopupWindow.onSelectTimeChange() {

            @Override
            public void onSelectTime(int start_Time, int end_Time) {
                //返回选择的开始时间 结束时间
                startTime = start_Time;
                endTime = end_Time;
                lastImei = 0;
                lastTime = 0;
                onResetDeviceData(true);
            }
        });
    }

    @Override
    public void getAlarmRecordSuccess(AlarmRecordResultBean alarmRecordResultBean, boolean isRefresh) {
        if (isRefresh) {
            alarmRecordBeans.clear();
        }
        lastImei = alarmRecordResultBean.getLast_imei();
        lastTime = alarmRecordResultBean.getLast_time();
        lastType = alarmRecordResultBean.getLast_type();
        if (alarmRecordResultBean.getItems() != null && alarmRecordResultBean.getItems().size() > 0) {
            alarmRecordBeans.addAll(alarmRecordResultBean.getItems());
        }
        mAdapter.notifyDataSetChanged();
        onShowNoData();
    }

    @Override
    public void finishRefresh() {
        srlView.setRefreshing(false);
    }

    @Override
    public void endLoadMore() {
        mAdapter.loadMoreComplete();
    }

    @Override
    public void setNoMore() {
        mAdapter.loadMoreEnd();
    }

    @Override
    public void endLoadFail() {
        mAdapter.loadMoreFail();
    }

    @Override
    public void submitAlarmDeleteSuccess(BaseBean baseBean) {
        ToastUtils.show(getString(R.string.txt_delete_success));
        alarmRecordBeans.remove(mPosition);
        mAdapter.notifyDataSetChanged();
        onShowNoData();
    }

    @Override
    public void onDismissProgress() {
        dismissProgressDialog();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == INTENT_TYPE) {
                String type = data.getStringExtra("type");
                if (type != null) {
                    screenTypeLists.clear();
                    lastImei = 0;
                    lastTime = 0;
                    String[] strType = type.split(",");
                    for (String typeString : strType) {
                        if (typeString.contains(";")) {
                            String[] lightOff = typeString.split(";");
                            screenTypeLists.addAll(Arrays.asList(lightOff));
                        } else {
                            screenTypeLists.add(typeString);
                        }
                    }
                    onResetDeviceData(true);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(MyApplication.getMyApp()).unregisterReceiver(receiver);
        super.onDestroy();
    }

    /**
     * 高德地图-地理编码检索监听器，逆地理编码，通过经纬度获取地址信息
     */
    private GeocodeSearch.OnGeocodeSearchListener aMaplistener = new GeocodeSearch.OnGeocodeSearchListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
            if (!TextUtils.isEmpty(regeocodeResult.getRegeocodeAddress().getFormatAddress())) {
                String address = regeocodeResult.getRegeocodeAddress().getFormatAddress().replace("(", "");
                address = address.replace(")", "");
                address = address.replace("null", "");
                // 判断是否获取到了方向和距离
                if (regeocodeResult.getRegeocodeAddress().getRoads() != null
                        && regeocodeResult.getRegeocodeAddress().getRoads().size() > 0) {
                    RegeocodeRoad road = regeocodeResult.getRegeocodeAddress().getRoads().get(0);
                    int distance = (int) road.getDistance();
                    address = address + "，" + road.getName() + road.getDirection() + distance
                            + getString(R.string.txt_meter);
                } else {
                    if (regeocodeResult.getRegeocodeAddress().getPois().size() > 0) {
                        PoiItem poiItem = regeocodeResult.getRegeocodeAddress().getPois().get(0);
                        if (!TextUtils.isEmpty(poiItem.getSnippet())) {
                            address = address + "，" + poiItem.getSnippet();
                        } else {
                            address = address + poiItem.getDirection() + poiItem.getDistance() + getString(R.string.txt_meter);
                        }
                    }
                }

                alarmRecordBeans.get(mAddressPosition).setAddr(address);
                mAdapter.notifyItemChanged(mAddressPosition);
            } else {
                ToastUtils.show(getString(R.string.txt_address_error));
            }
        }

        @Override
        public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

        }
    };

    @Override
    public void onResume() {
        MobclickAgent.onPageStart("Alarm");//统计页面("MainScreen"为页面名称，可自定义)
        super.onResume();
    }

    @Override
    public void onPause() {
        MobclickAgent.onPageEnd("Alarm");
        super.onPause();
    }
}
