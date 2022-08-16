package com.car.scth.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.car.scth.R;
import com.car.scth.di.component.DaggerDeviceManagementComponent;
import com.car.scth.mvp.contract.DeviceManagementContract;
import com.car.scth.mvp.model.api.ModuleValueService;
import com.car.scth.mvp.model.bean.AlertBean;
import com.car.scth.mvp.model.bean.DeviceBaseResultBean;
import com.car.scth.mvp.model.bean.DeviceGroupResultBean;
import com.car.scth.mvp.model.bean.DeviceListForManagerResultBean;
import com.car.scth.mvp.model.bean.TaskProgressResultBean;
import com.car.scth.mvp.model.putbean.DeviceGroupPutBean;
import com.car.scth.mvp.model.putbean.DeviceListForManagerPutBean;
import com.car.scth.mvp.model.putbean.FreezeEquipmentPutBean;
import com.car.scth.mvp.model.putbean.RemoveDevicePutBean;
import com.car.scth.mvp.model.putbean.TaskProgressPubBean;
import com.car.scth.mvp.model.putbean.TransferDevicePutBean;
import com.car.scth.mvp.presenter.DeviceManagementPresenter;
import com.car.scth.mvp.ui.adapter.DeviceListAdapter;
import com.car.scth.mvp.ui.view.MyLoadMoreView;
import com.car.scth.mvp.utils.ConstantValue;
import com.car.scth.mvp.utils.ResultDataUtils;
import com.car.scth.mvp.utils.ToastUtils;
import com.car.scth.mvp.utils.Utils;
import com.car.scth.mvp.weiget.AlertAppDialog;
import com.car.scth.mvp.weiget.DeviceFailDialog;
import com.car.scth.mvp.weiget.GroupManagePopupwindow;
import com.car.scth.mvp.weiget.GroupTransferDeviceDialog;
import com.car.scth.mvp.weiget.TaskProgressDialog;

import java.util.ArrayList;
import java.util.List;

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
 * Description: 设备管理界面
 * <p>
 * Created by MVPArmsTemplate on 11/06/2020 14:13
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class DeviceManagementActivity extends BaseActivity<DeviceManagementPresenter> implements DeviceManagementContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.srl_view)
    SwipeRefreshLayout srlView;
    @BindView(R.id.toolbar_iv_right)
    ImageView toolbarIvRight;
    @BindView(R.id.view)
    View view;

    private TextView tvPrompt;
    private String mSid; // 分组id
    private int mLimitSizeForDevice = 100; // 限定设备数量,默认100
    private String mLastSimeiForDevice; // 最后获取到的simei
    private String mLastSgidForDevice; // 上一次请求的最后一个sgidz

    // 获取分组列表数据
    private List<DeviceGroupResultBean.GaragesBean> groupBeans; // 设备分组
    private int mLimitSize = 100; // 限制分组获取数量

    private List<DeviceListForManagerResultBean.ItemsBean> deviceBeans;
    private DeviceListAdapter mAdapter;
    private GroupManagePopupwindow mPopupWindow; // 菜单栏
    private List<String> mSimeiBeans; // 选中的设备号,限制数量1000，simei,sfamilyid,sgid三选一
    private List<DeviceListForManagerResultBean.ItemsBean> deviceSelectBeans; // 选中的设备

    private static final int mCountDownTime = 2; // 倒计时固定时长
    private int mCountDown = mCountDownTime; // 倒计时递减时长
    private String taskId; // 任务id
    private TaskProgressDialog taskProgressDialog;

    private String familyAuth; // 用户权限

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerDeviceManagementComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_device_management; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_device_management));
        familyAuth = ConstantValue.getFamilyAuth();
        toolbarIvRight.setImageResource(R.drawable.icon_setting_manage);
        mSid = getIntent().getStringExtra("sid");
        deviceBeans = new ArrayList<>();
        mSimeiBeans = new ArrayList<>();
        deviceSelectBeans = new ArrayList<>();
        groupBeans = new ArrayList<>();

        if (!TextUtils.isEmpty(familyAuth)){
            if (familyAuth.contains(ResultDataUtils.Family_Auth_0) || familyAuth.contains(ResultDataUtils.Family_Auth_1)
                    || familyAuth.contains(ResultDataUtils.Family_Auth_10)){
                toolbarIvRight.setVisibility(View.VISIBLE);
            }
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        srlView.setColorSchemeResources(R.color.color_00A7FF, R.color.color_00A7FF);
        srlView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRefreshDeviceData();
            }
        });

        View statusView = LayoutInflater.from(this).inflate(R.layout.layout_status, recyclerView, false);
        tvPrompt = statusView.findViewById(R.id.txt_wrong_status);
        tvPrompt.setText(getString(R.string.txt_device_no_data));
        tvPrompt.setVisibility(View.GONE);

        mAdapter = new DeviceListAdapter(R.layout.item_group_manage, deviceBeans);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setEmptyView(statusView);

        LoadMoreView loadMoreView = new MyLoadMoreView();
        mAdapter.setLoadMoreView(loadMoreView);
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getDeviceListForGroup(false, false);
            }
        }, recyclerView);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                deviceBeans.get(position).setSelect(!deviceBeans.get(position).isSelect());
                mAdapter.notifyItemChanged(position);
            }
        });

        getDeviceListForGroup(true, false);
        getDeviceGroupList(false);
    }

    /**
     * 刷新设备列表数据
     */
    private void onRefreshDeviceData(){
        mLastSgidForDevice = "";
        mLastSimeiForDevice = "";
        getDeviceListForGroup(false, true);
    }

    /**
     * 获取分组下的设备列表
     *
     * @param isShow    是否显示加载框
     * @param isRefresh 是否刷新数据
     */
    private void getDeviceListForGroup(boolean isShow, boolean isRefresh) {
        DeviceListForManagerPutBean.ParamsBean paramsBean = new DeviceListForManagerPutBean.ParamsBean();
        paramsBean.setLimit_size(mLimitSizeForDevice);
        paramsBean.setGroupid(mSid);
        if (!TextUtils.isEmpty(mLastSgidForDevice)) {
            paramsBean.setLast_sgid(mLastSgidForDevice);
        }
        if (!TextUtils.isEmpty(mLastSimeiForDevice)) {
            paramsBean.setLast_simei(mLastSimeiForDevice);
        }

        DeviceListForManagerPutBean bean = new DeviceListForManagerPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Device_List_For_Group);
        bean.setModule(ModuleValueService.Module_For_Device_List_For_Group);

        if (getPresenter() != null) {
            getPresenter().getDeviceListForGroup(bean, isShow, isRefresh);
        }
    }

    /**
     * 获取车组织列表和车组列表
     * @param isShow 是否显示加载框
     */
    private void getDeviceGroupList(boolean isShow) {
        if (ConstantValue.isAccountLogin()) {
            DeviceGroupPutBean.ParamsBean paramsBean = new DeviceGroupPutBean.ParamsBean();
            paramsBean.setF_limit_size(0);
            paramsBean.setG_limit_size(mLimitSize);
            paramsBean.setFamilyid(ConstantValue.getFamilySid());
            DeviceGroupPutBean bean = new DeviceGroupPutBean();
            bean.setParams(paramsBean);
            bean.setFunc(ModuleValueService.Fuc_For_Device_Group_List);
            bean.setModule(ModuleValueService.Module_For_Device_Group_List);

            if (getPresenter() != null){
                getPresenter().getDeviceGroupList(bean, isShow);
            }
        }
    }

    /**
     * 显示无数据提示
     */
    private void onShowNoData(){
        if (deviceBeans.size() > 0){
            tvPrompt.setVisibility(View.GONE);
        }else{
            tvPrompt.setVisibility(View.VISIBLE);
        }
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

    @OnClick(R.id.toolbar_iv_right)
    public void onViewClicked() {
        if (Utils.isButtonQuickClick(System.currentTimeMillis())){
            if (mPopupWindow != null && mPopupWindow.isShowing()) {
                mPopupWindow.dismiss();
            } else {
                mPopupWindow = new GroupManagePopupwindow(this, 1);
                mPopupWindow.setManageMenuChange(new GroupManagePopupwindow.onManageMenuChange() {
                    @Override
                    public void onMenuSelect(int id) {
                        // 0:转移，6：冻结设备，7：移除设备
                        switch (id) {
                            case 0:
                                onTransferDevice();
                                break;
                            case 6:
                                onFreezeEquipment();
                                break;
                            case 7:
                                onDeleteDevice();
                                break;
                        }
                        mPopupWindow.dismiss();
                    }
                });
                mPopupWindow.showAsDropDown(view);
            }
        }
    }

    /**
     * 判断是否选中了设备
     */
    private boolean onSelectDevice(){
        deviceSelectBeans.clear();
        mSimeiBeans.clear();
        for (DeviceListForManagerResultBean.ItemsBean bean : deviceBeans){
            if (bean.isSelect()){
                deviceSelectBeans.add(bean);
                mSimeiBeans.add(bean.getSimei());
            }
        }
        return mSimeiBeans.size() != 0;
    }

    /**
     * 转移设备
     */
    private void onTransferDevice(){
        if (!onSelectDevice()){
            ToastUtils.show(getString(R.string.txt_transfer_device_hint));
            return;
        }

        GroupTransferDeviceDialog dialog = new GroupTransferDeviceDialog();
        dialog.show(getSupportFragmentManager(), mSid, groupBeans, new GroupTransferDeviceDialog.onGroupTransferDeviceChange() {
            @Override
            public void onGroupTransferDevice(String sid) {
                submitTransferDevice(sid);
            }
        });
    }

    /**
     * 转移设备
     * @param sid
     */
    private void submitTransferDevice(String sid){
        TransferDevicePutBean.ParamsBean paramsBean = new TransferDevicePutBean.ParamsBean();
        paramsBean.setSgid_new(sid);
        paramsBean.setSimei(mSimeiBeans);

        TransferDevicePutBean bean = new TransferDevicePutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Group_Transfer_Device);
        bean.setModule(ModuleValueService.Module_For_Group_Transfer_Device);

        if (getPresenter() != null){
            getPresenter().submitTransferDevice(bean);
        }
    }

    /**
     * 冻结设备
     */
    private void onFreezeEquipment(){
        if (!onSelectDevice()){
            ToastUtils.show(getString(R.string.txt_freeze_equipment_hint));
            return;
        }
        if (mSimeiBeans.size() > 1000){
            ToastUtils.show(getString(R.string.txt_freeze_equipment_max_number));
            return;
        }

        AlertBean bean = new AlertBean();
        bean.setTitle(getString(R.string.txt_tip));
        bean.setAlert(getString(R.string.txt_freeze_equipment_device) + mSimeiBeans.size() + getString(R.string.txt_freeze_equipment_device_2));
        bean.setType(0);
        AlertAppDialog dialog = new AlertAppDialog();
        dialog.show(getSupportFragmentManager(), bean, new AlertAppDialog.onAlertDialogChange() {
            @Override
            public void onConfirm() {
                submitFreezeEquipment();
            }

            @Override
            public void onCancel() {

            }
        });
    }

    /**
     * 提交冻结设备
     */
    private void submitFreezeEquipment(){
        FreezeEquipmentPutBean.ParamsBean paramsBean = new FreezeEquipmentPutBean.ParamsBean();
        paramsBean.setSimei(mSimeiBeans);

        FreezeEquipmentPutBean bean = new FreezeEquipmentPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Freeze_Equipment);
        bean.setModule(ModuleValueService.Module_For_Freeze_Equipment);

        if (getPresenter() != null){
            getPresenter().submitFreezeEquipment(bean);
        }
    }

    /**
     * 删除设备
     */
    private void onDeleteDevice(){
        if (!onSelectDevice()){
            ToastUtils.show(getString(R.string.txt_device_delete_select_hint));
            return;
        }

        AlertBean bean = new AlertBean();
        bean.setTitle(getString(R.string.txt_tip));
        bean.setAlert(getString(R.string.txt_device_delete_hint_two));
        bean.setType(0);
        AlertAppDialog dialog = new AlertAppDialog();
        dialog.show(getSupportFragmentManager(), bean, new AlertAppDialog.onAlertDialogChange() {
            @Override
            public void onConfirm() {
                submitDeleteDevice();
            }

            @Override
            public void onCancel() {

            }
        });
    }

    /**
     * 移除设备
     */
    private void submitDeleteDevice(){
        RemoveDevicePutBean.ParamsBean paramsBean = new RemoveDevicePutBean.ParamsBean();
        paramsBean.setSimei(mSimeiBeans);
        paramsBean.setDel_type(ResultDataUtils.Device_Delete_Parent);

        RemoveDevicePutBean bean = new RemoveDevicePutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Device_Remove);
        bean.setModule(ModuleValueService.Module_For_Device_Remove);

        if (getPresenter() != null){
            getPresenter().submitDeleteDevice(bean);
        }
    }

    /**
     * 获取任务进度
     */
    private void getTaskProgress(){
        TaskProgressPubBean.ParamsBean paramsBean = new TaskProgressPubBean.ParamsBean();
        paramsBean.setTask_id(taskId);

        TaskProgressPubBean bean = new TaskProgressPubBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Task_Progress);
        bean.setModule(ModuleValueService.Module_For_Task_Progress);

        if (getPresenter() != null){
            getPresenter().getTaskProgress(bean);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){

        @Override
        public void handleMessage(@NonNull Message msg) {
            mCountDown--;
            if (mCountDown <= 0){
                mCountDown = mCountDownTime;
                getTaskProgress();
            }
            handler.sendEmptyMessageDelayed(1, 1000);
        }
    };

    @Override
    public void getDeviceListForGroupSuccess(DeviceListForManagerResultBean deviceListForManagerResultBean, boolean isRefresh) {
        if (isRefresh) {
            deviceBeans.clear();
        }
        if (deviceListForManagerResultBean.getItems() != null && deviceListForManagerResultBean.getItems().size() > 0) {
            mLastSgidForDevice = deviceListForManagerResultBean.getItems().get(deviceListForManagerResultBean.getItems().size() - 1).getSgid();
            mLastSimeiForDevice = deviceListForManagerResultBean.getItems().get(deviceListForManagerResultBean.getItems().size() - 1).getSimei();
            deviceBeans.addAll(deviceListForManagerResultBean.getItems());
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
    public void getDeviceGroupListSuccess(DeviceGroupResultBean deviceGroupResultBean) {
        groupBeans.clear();
        if (deviceGroupResultBean.getGarages() != null && deviceGroupResultBean.getGarages().size() > 0) {
            groupBeans.addAll(deviceGroupResultBean.getGarages());
        }
    }

    @Override
    public void submitDeleteDeviceSuccess(DeviceBaseResultBean deviceBaseResultBean) {
        ToastUtils.show(getString(R.string.txt_delete_success));
        deviceBeans.removeAll(deviceSelectBeans);
        mAdapter.notifyDataSetChanged();
        mSimeiBeans.clear();
        deviceSelectBeans.clear();
        onShowNoData();
        setResult(Activity.RESULT_OK);
    }

    @Override
    public void submitTransferDeviceSuccess(DeviceBaseResultBean deviceBaseResultBean) {
        taskProgressDialog = null;
        taskId = "";
        if (!TextUtils.isEmpty(deviceBaseResultBean.getTask_id())){
            taskId = deviceBaseResultBean.getTask_id();
            handler.sendEmptyMessage(1);
            if (taskProgressDialog == null){
                taskProgressDialog = new TaskProgressDialog();
                taskProgressDialog.show(getSupportFragmentManager(), new TaskProgressDialog.onProgressBarChange() {
                    @Override
                    public void onTaskProgressFinish(String return_message, long return_code) {
                        handler.removeCallbacksAndMessages(null);

                        if (return_code == 0){
                            ToastUtils.show(getString(R.string.txt_successful_operation));
                        }else{
                            ToastUtils.show(return_message);
                        }
                        toolbarIvRight.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                onRefreshDeviceData();
                            }
                        }, 500);
                    }
                });
            }
        }else{
            ToastUtils.show(getString(R.string.txt_successful_operation));
            deviceBeans.removeAll(deviceSelectBeans);
            mAdapter.notifyDataSetChanged();
            mSimeiBeans.clear();
            deviceSelectBeans.clear();
            onShowNoData();
        }
        setResult(Activity.RESULT_OK);
    }

    @Override
    public void submitFreezeEquipmentSuccess(DeviceBaseResultBean deviceBaseResultBean) {
        taskProgressDialog = null;
        taskId = "";
        if (!TextUtils.isEmpty(deviceBaseResultBean.getTask_id())){
            taskId = deviceBaseResultBean.getTask_id();
            handler.sendEmptyMessage(1);
            if (taskProgressDialog == null){
                taskProgressDialog = new TaskProgressDialog();
                taskProgressDialog.show(getSupportFragmentManager(), new TaskProgressDialog.onProgressBarChange() {
                    @Override
                    public void onTaskProgressFinish(String return_message, long return_code) {
                        handler.removeCallbacksAndMessages(null);

                        if (return_code == 0){
                            ToastUtils.show(getString(R.string.txt_successful_operation));
                        }else{
                            ToastUtils.show(return_message);
                        }
                        toolbarIvRight.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                onRefreshDeviceData();
                            }
                        }, 500);
                    }
                });
            }
        }else{
            ToastUtils.show(getString(R.string.txt_successful_operation));
            onRefreshDeviceData();

            if (deviceBaseResultBean.getFail_items() != null && deviceBaseResultBean.getFail_items().size() > 0){
                DeviceFailDialog dialog = new DeviceFailDialog();
                dialog.show(getSupportFragmentManager(), deviceBaseResultBean.getFail_items(), 1);
            }
        }
        setResult(Activity.RESULT_OK);
    }

    @Override
    public void getTaskProgressSuccess(TaskProgressResultBean taskProgressResultBean) {
        if (taskProgressDialog != null){
            double progress = ((double) taskProgressResultBean.getCurrent_count() / taskProgressResultBean.getTotal()) * 100;
            int intProgress = (int) progress;
            if (intProgress > 100){
                intProgress = 100;
            }
            taskProgressDialog.setProgressBar(intProgress, taskProgressResultBean.isIs_finish(),
                    taskProgressResultBean.getReturn_msg(), taskProgressResultBean.getReturn_code());
        }
    }

    @Override
    public void onRefreshData() {
        mLastSgidForDevice = "";
        mLastSimeiForDevice = "";
        getDeviceListForGroup(false, true);
    }

    @Override
    public void onBackPressed() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

}
