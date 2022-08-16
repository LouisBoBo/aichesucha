package com.car.scth.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
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
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.car.scth.R;
import com.car.scth.app.MyApplication;
import com.car.scth.di.component.DaggerAlarmRecordComponent;
import com.car.scth.mvp.contract.AlarmRecordContract;
import com.car.scth.mvp.model.api.ModuleValueService;
import com.car.scth.mvp.model.bean.AlarmRecordResultBean;
import com.car.scth.mvp.model.bean.AlertBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.AlarmDeletePutBean;
import com.car.scth.mvp.model.putbean.AlarmRecordPutBean;
import com.car.scth.mvp.presenter.AlarmRecordPresenter;
import com.car.scth.mvp.ui.adapter.AlarmRecordUserAdapter;
import com.car.scth.mvp.ui.view.MyLoadMoreView;
import com.car.scth.mvp.utils.ConstantValue;
import com.car.scth.mvp.utils.LocationAddress;
import com.car.scth.mvp.utils.ToastUtils;
import com.car.scth.mvp.utils.Utils;
import com.car.scth.mvp.weiget.AlarmDeviceScreenDialog;
import com.car.scth.mvp.weiget.AlertAppDialog;
import com.car.scth.mvp.weiget.AlertSelectDatePopupWindow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 报警记录列表
 * <p>
 * Created by MVPArmsTemplate on 10/26/2020 14:32
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class AlarmRecordActivity extends BaseActivity<AlarmRecordPresenter> implements AlarmRecordContract.View {

    @BindView(R.id.iv_setting)
    ImageView ivSetting; // 设置报警消息
    @BindView(R.id.iv_data)
    ImageView ivData; // 时间筛选
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.srl_view)
    SwipeRefreshLayout srlView;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.lp_head)
    RelativeLayout alarmHead;

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

    private static final int INTENT_TYPE = 11; // 报警类型筛选
    private int mAddressPosition; // 选中的item索引位置
    private AlertSelectDatePopupWindow mPopupWindow; // 日期选择弹框


    public static Intent newInstance() {
        return new Intent(MyApplication.getMyApp(), AlarmRecordActivity.class);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAlarmRecordComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_alarm;//setContentView(id);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        alarmRecordBeans = new ArrayList<>();
        mSimeiBeas = new ArrayList<>();
        screenTypeLists = new ArrayList<>();
        userFamilyId = ConstantValue.getFamilySid();
        mSimei = MyApplication.getMyApp().getSimei();
        if(!TextUtils.isEmpty(mSimei))
        mSimeiBeas.add(mSimei);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        srlView.setColorSchemeResources(R.color.color_00A7FF, R.color.color_00A7FF);
        srlView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                lastImei = 0;
                lastTime = 0;
                getAlarmRecord(false, true);
            }
        });

        View statusView = LayoutInflater.from(this).inflate(R.layout.layout_status, recyclerView, false);
        tvPrompt = statusView.findViewById(R.id.txt_wrong_status);
        tvPrompt.setText(getString(R.string.error_no_data_alarm));
        tvPrompt.setVisibility(View.GONE);

        mAdapter = new AlarmRecordUserAdapter(R.layout.item_alarm_list, alarmRecordBeans);
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
                if (Utils.isButtonQuickClick(System.currentTimeMillis())){
                    switch (view.getId()) {
                        case R.id.tv_address: // 查看经纬度
                            mAddressPosition = position;
                            // 如果是经纬度，点击解析详细地址
                            getAddressLocation(alarmRecordBeans.get(position).getAddr());
                            break;
                        case R.id.ll_msg_delete: // 删除报警消息
                            onDeleteAlarmConfirm(position);
                            break;
                    }
                }
            }
        });
//        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                mAddressPosition = position;
//                // 如果是经纬度，点击解析详细地址
//                getAddressLocation(alarmRecordBeans.get(position).getAddr());
//            }
//        });

        getAlarmRecord(true, true);
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
        if (ConstantValue.isLoginForAccount()) {
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
        dialog.show(getSupportFragmentManager(), bean, new AlertAppDialog.onAlertDialogChange() {
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
                new LocationAddress().Parsed(Double.parseDouble(location[0]), Double.parseDouble(location[1]))
                        .setAddressListener(str -> {
                            if (!TextUtils.isEmpty(str)) {
                                alarmRecordBeans.get(mAddressPosition).setAddr(str);
                                mAdapter.notifyItemChanged(mAddressPosition);
                            } else {
                                ToastUtils.show(getString(R.string.txt_address_error));
                            }
                        });
            }
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

    @OnClick({R.id.iv_setting, R.id.iv_data, R.id.iv_back})
    public void onViewClicked(View view) {
        if (Utils.isButtonQuickClick(System.currentTimeMillis())) {
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
                case R.id.iv_back:
                    finish();
                    break;
            }
        }
    }

    /**
     * 设备筛选
     */
    private void onAlarmDeviceScreen() {
        AlarmDeviceScreenDialog dialog = new AlarmDeviceScreenDialog();
        dialog.show(getSupportFragmentManager(), new AlarmDeviceScreenDialog.onAlarmDeviceScreenChange() {
            @Override
            public void onDeviceScreen(String simei) {
                mSimei = simei;
                lastImei = 0;
                lastTime = 0;
                getAlarmRecord(true, true);
            }
        });
    }

    /**
     * 日期选择弹框
     */
    private void showDateSelectPopupWindow() {
        mPopupWindow = new AlertSelectDatePopupWindow(this, startTime, endTime);
        mPopupWindow.showAsDropDown(alarmHead);
        mPopupWindow.setSelectTimeChange(new AlertSelectDatePopupWindow.onSelectTimeChange() {

            @Override
            public void onSelectTime(int start_Time, int end_Time) {
                //返回选择的开始时间 结束时间
                startTime = start_Time;
                endTime = end_Time;
                lastImei = 0;
                lastTime = 0;
                getAlarmRecord(true, true);
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
                    getAlarmRecord(true, true);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
