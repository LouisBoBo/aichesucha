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
import com.car.scth.di.component.DaggerRecycleBinComponent;
import com.car.scth.mvp.contract.RecycleBinContract;
import com.car.scth.mvp.model.api.ModuleValueService;
import com.car.scth.mvp.model.bean.AlertBean;
import com.car.scth.mvp.model.bean.DeviceBaseResultBean;
import com.car.scth.mvp.model.bean.DeviceGroupResultBean;
import com.car.scth.mvp.model.bean.RecycleBinResultBean;
import com.car.scth.mvp.model.bean.TaskProgressResultBean;
import com.car.scth.mvp.model.putbean.DeviceGroupPutBean;
import com.car.scth.mvp.model.putbean.RecycleBinPutBean;
import com.car.scth.mvp.model.putbean.RemoveDevicePutBean;
import com.car.scth.mvp.model.putbean.RestoreToOriginalAccountPutBean;
import com.car.scth.mvp.model.putbean.TaskProgressPubBean;
import com.car.scth.mvp.presenter.RecycleBinPresenter;
import com.car.scth.mvp.ui.adapter.RecycleBinAdapter;
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
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/05/2021 10:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class RecycleBinActivity extends BaseActivity<RecycleBinPresenter> implements RecycleBinContract.View {

    @BindView(R.id.toolbar_iv_right)
    ImageView toolbarIvRight;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.srl_view)
    SwipeRefreshLayout srlView;
    @BindView(R.id.view)
    View view;

    private TextView tvPrompt;

    private RecycleBinAdapter mAdapter;
    private List<RecycleBinResultBean.ItemsBean> resultBeans;

    private String mLastSgid; // 上一次请求的最后一个sgid
    private String mLastSimei; // 上一次请求的最后一个simei
    private long mLastRecoveryStat = 0; // 上一次请求的最后一个冻结时间
    private int mLimitSize = 20; // 限制条数，不填默认20条
    private String mFamilyId; // 需要查询车组织的id，设备登入无法请求

    private boolean isSelect;
    private GroupManagePopupwindow mPopupWindow; // 菜单栏
    private List<String> mSimeiBeans; // 选中的设备号,限制数量1000，simei,sfamilyid,sgid三选一

    private static final int mCountDownTime = 2; // 倒计时固定时长
    private int mCountDown = mCountDownTime; // 倒计时递减时长
    private String taskId; // 任务id
    private TaskProgressDialog taskProgressDialog;

    private List<DeviceGroupResultBean.GaragesBean> groupBeans; // 设备分组
    private int mLimitGroupSize = 100; // 限制条数，不填默认20条

    private String familyAuth; // 用户权限

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRecycleBinComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_recycle_bin;//setContentView(id);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_recycle));
        toolbarIvRight.setImageResource(R.drawable.icon_setting_manage);
        resultBeans = new ArrayList<>();
        mSimeiBeans = new ArrayList<>();
        groupBeans = new ArrayList<>();
        mFamilyId = ConstantValue.getFamilySid();
        familyAuth = ConstantValue.getFamilyAuth();

        if (!TextUtils.isEmpty(familyAuth)){
            if (familyAuth.contains(ResultDataUtils.Family_Auth_5) || familyAuth.contains(ResultDataUtils.Family_Auth_10)){
                toolbarIvRight.setVisibility(View.VISIBLE);
            }
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        srlView.setColorSchemeResources(R.color.color_00A7FF, R.color.color_00A7FF);
        srlView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRefreshRecycleBinData();
            }
        });

        View statusView = LayoutInflater.from(this).inflate(R.layout.layout_status, recyclerView, false);
        tvPrompt = statusView.findViewById(R.id.txt_wrong_status);
        tvPrompt.setText(getString(R.string.txt_device_no_data));
        tvPrompt.setVisibility(View.GONE);

        mAdapter = new RecycleBinAdapter(R.layout.item_recycle_bin, resultBeans);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setEmptyView(statusView);

        LoadMoreView loadMoreView = new MyLoadMoreView();
        mAdapter.setLoadMoreView(loadMoreView);
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getRecycleBinData(false, false);
            }
        }, recyclerView);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                isSelect = !resultBeans.get(position).isSelect();
                resultBeans.get(position).setSelect(isSelect);
                mAdapter.notifyItemChanged(position);
            }
        });

        getRecycleBinData(true, true);
        getDeviceGroupList(false);
    }

    /**
     * 刷新回收站数据
     */
    private void onRefreshRecycleBinData(){
        mLastSgid = "";
        mLastSimei = "";
        mLastRecoveryStat = 0;
        getRecycleBinData(false, true);
    }

    /**
     * 获取回收站列表
     *
     * @param isShow    是否弹出加载看
     * @param isRefresh 是否刷新数据
     */
    private void getRecycleBinData(boolean isShow, boolean isRefresh) {
        RecycleBinPutBean.ParamsBean paramsBean = new RecycleBinPutBean.ParamsBean();
        paramsBean.setSfamily(mFamilyId);
        paramsBean.setLimit_size(mLimitSize);
        if (!TextUtils.isEmpty(mLastSgid)) {
            paramsBean.setLast_sgid(mLastSgid);
        }
        if (!TextUtils.isEmpty(mLastSimei)) {
            paramsBean.setLast_simei(mLastSimei);
        }
        if (mLastRecoveryStat != 0){
            paramsBean.setLast_recovery_stat(mLastRecoveryStat);
        }

        RecycleBinPutBean bean = new RecycleBinPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Recycle_Bin);
        bean.setModule(ModuleValueService.Module_For_Recycle_Bin);

        if (getPresenter() != null) {
            getPresenter().getRecycleBinData(bean, isShow, isRefresh);
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
            paramsBean.setG_limit_size(mLimitGroupSize);
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
    private void onShowNoData() {
        if (resultBeans.size() > 0) {
            tvPrompt.setVisibility(View.GONE);
        } else {
            tvPrompt.setVisibility(View.VISIBLE);
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

    @OnClick(R.id.toolbar_iv_right)
    public void onViewClicked() {
        if (Utils.isButtonQuickClick(System.currentTimeMillis())){
            if (mPopupWindow != null && mPopupWindow.isShowing()) {
                mPopupWindow.dismiss();
            } else {
                mPopupWindow = new GroupManagePopupwindow(this, 2);
                mPopupWindow.setManageMenuChange(new GroupManagePopupwindow.onManageMenuChange() {
                    @Override
                    public void onMenuSelect(int id) {
                        // 21:恢复至原账号，7：移除设备
                        switch (id) {
                            case 21:
                                onRestoreToOriginalAccount();
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
        mSimeiBeans.clear();
        for (RecycleBinResultBean.ItemsBean bean : resultBeans){
            if (bean.isSelect()){
                mSimeiBeans.add(bean.getSimei());
            }
        }
        return mSimeiBeans.size() != 0;
    }

    /**
     * 恢复至原账号
     */
    private void onRestoreToOriginalAccount(){
        if (!onSelectDevice()){
            ToastUtils.show(getString(R.string.txt_restore_to_original_account_hint));
            return;
        }

        if (mSimeiBeans.size() > 1000){
            ToastUtils.show(getString(R.string.txt_restore_max_number));
            return;
        }

        if (groupBeans.size() == 0){
            getDeviceGroupList(true);
            return;
        }

        GroupTransferDeviceDialog dialog = new GroupTransferDeviceDialog();
        dialog.show(getSupportFragmentManager(), "", groupBeans, new GroupTransferDeviceDialog.onGroupTransferDeviceChange() {
            @Override
            public void onGroupTransferDevice(String sid) {
                submitRestoreToOriginalAccount(sid);
            }
        });
    }

    /**
     * 提交恢复至原账号
     * @param gid 分组id
     */
    private void submitRestoreToOriginalAccount(String gid){
        RestoreToOriginalAccountPutBean.ParamsBean paramsBean = new RestoreToOriginalAccountPutBean.ParamsBean();
        paramsBean.setSimei(mSimeiBeans);
        paramsBean.setSgid(gid);

        RestoreToOriginalAccountPutBean bean = new RestoreToOriginalAccountPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Recycle_RestoreToOriginalAccount);
        bean.setModule(ModuleValueService.Module_For_Recycle_RestoreToOriginalAccount);

        if (getPresenter() != null){
            getPresenter().submitRestoreToOriginalAccount(bean);
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

        if (mSimeiBeans.size() > 1000){
            ToastUtils.show(getString(R.string.txt_delete_max_number));
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
        paramsBean.setDel_type(ResultDataUtils.Device_Delete_Root);

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
    public void getRecycleBinDataSuccess(RecycleBinResultBean recycleBinResultBean, boolean isRefresh) {
        if (isRefresh) {
            resultBeans.clear();
        }
        if (recycleBinResultBean.getItems() != null && recycleBinResultBean.getItems().size() > 0) {
            resultBeans.addAll(recycleBinResultBean.getItems());
            mLastSgid = recycleBinResultBean.getItems().get(recycleBinResultBean.getItems().size() - 1).getSgid();
            mLastSimei = recycleBinResultBean.getItems().get(recycleBinResultBean.getItems().size() - 1).getSimei();
            mLastRecoveryStat = recycleBinResultBean.getItems().get(recycleBinResultBean.getItems().size() - 1).getRecovery_stat();
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
    public void submitRestoreToOriginalAccountSuccess(DeviceBaseResultBean deviceBaseResultBean) {
        setResult(Activity.RESULT_OK);
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
                                onRefreshRecycleBinData();
                            }
                        }, 500);
                    }
                });
            }
        }else{
            ToastUtils.show(getString(R.string.txt_successful_operation));
            onRefreshRecycleBinData();

            if (deviceBaseResultBean.getFail_items() != null && deviceBaseResultBean.getFail_items().size() > 0){
                DeviceFailDialog dialog = new DeviceFailDialog();
                dialog.show(getSupportFragmentManager(), deviceBaseResultBean.getFail_items(), 5);
            }
        }
    }

    @Override
    public void submitDeleteDeviceSuccess(DeviceBaseResultBean deviceBaseResultBean) {
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
                                onRefreshRecycleBinData();
                            }
                        }, 500);
                    }
                });
            }
        }else{
            ToastUtils.show(getString(R.string.txt_successful_operation));
            onRefreshRecycleBinData();

            if (deviceBaseResultBean.getFail_items() != null && deviceBaseResultBean.getFail_items().size() > 0){
                DeviceFailDialog dialog = new DeviceFailDialog();
                dialog.show(getSupportFragmentManager(), deviceBaseResultBean.getFail_items(), 3);
            }
        }
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
    public void getDeviceGroupListSuccess(DeviceGroupResultBean deviceGroupResultBean) {
        if (deviceGroupResultBean.getGarages() != null && deviceGroupResultBean.getGarages().size() > 0) {
            groupBeans.addAll(deviceGroupResultBean.getGarages());
        }
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

}
