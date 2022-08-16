package com.car.scth.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.car.scth.R;
import com.car.scth.di.component.DaggerAccountListComponent;
import com.car.scth.mvp.contract.AccountListContract;
import com.car.scth.mvp.model.api.ModuleValueService;
import com.car.scth.mvp.model.bean.AccountListResultBean;
import com.car.scth.mvp.model.bean.AlertBean;
import com.car.scth.mvp.model.bean.DeviceBaseResultBean;
import com.car.scth.mvp.model.bean.TaskProgressResultBean;
import com.car.scth.mvp.model.putbean.AccountDeletePutBean;
import com.car.scth.mvp.model.putbean.AccountListPutBean;
import com.car.scth.mvp.model.putbean.TaskProgressPubBean;
import com.car.scth.mvp.presenter.AccountListPresenter;
import com.car.scth.mvp.ui.adapter.AccountListAdapter;
import com.car.scth.mvp.ui.view.MyLoadMoreView;
import com.car.scth.mvp.utils.ConstantValue;
import com.car.scth.mvp.utils.ResultDataUtils;
import com.car.scth.mvp.utils.ToastUtils;
import com.car.scth.mvp.utils.Utils;
import com.car.scth.mvp.weiget.AlertAppDialog;
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
 * Created by MVPArmsTemplate on 03/12/2021 11:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class AccountListActivity extends BaseActivity<AccountListPresenter> implements AccountListContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.srl_view)
    SwipeRefreshLayout srlView;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight; // 添加用户

    private List<AccountListResultBean.InfoBean> familyBeans;
    private AccountListAdapter mAdapter;

    private String familyId; // 用户组织id
    private String lastUid; // 获取到的最后的用户uid
    private int limitSize = 50; // 限制获取数量
    private int familyLevel; // 组织层级，0为最上层(同级组织)，1为下级组织

    private static final int Intent_Add_Account = 10; // 添加账号
    private String familyAuth;

    private static final int mCountDownTime = 2; // 倒计时固定时长
    private int mCountDown = mCountDownTime; // 倒计时递减时长
    private String taskId; // 任务id
    private TaskProgressDialog taskProgressDialog;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAccountListComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_account_list;//setContentView(id);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_account_list));
        toolbarRight.setVisibility(View.VISIBLE);
        toolbarRight.setText(getString(R.string.txt_add_account));
        familyBeans = new ArrayList<>();
        familyId = getIntent().getStringExtra("familyId");
        familyLevel = getIntent().getIntExtra("level", 1);
        familyAuth = ConstantValue.getFamilyAuth();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        srlView.setColorSchemeResources(R.color.color_00A7FF, R.color.color_00A7FF);
        srlView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                lastUid = "";
                getAccountList(false, true);
            }
        });

        mAdapter = new AccountListAdapter(R.layout.item_account_list_next, familyBeans);
        recyclerView.setAdapter(mAdapter);

        LoadMoreView loadMoreView = new MyLoadMoreView();
        mAdapter.setLoadMoreView(loadMoreView);
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getAccountList(false, false);
            }
        }, recyclerView);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (Utils.isButtonQuickClick(System.currentTimeMillis())){
                    switch (view.getId()){
                        case R.id.iv_transfer: // 权限转移
                            onPermissionTransfer(familyBeans.get(position).getUid());
                            break;
                        case R.id.iv_edit: // 编辑用户信息
                            onModifyUserInfo(familyBeans.get(position));
                            break;
                        case R.id.iv_delete: // 删除用户
                            onDeleteAccount(familyBeans.get(position).getUid());
                            break;
                    }
                }
            }
        });

        getAccountList(true, true);
    }

    /**
     * 获取用户列表数据
     * @param isShow 加载框
     * @param isRefresh 是否刷新数据
     */
    private void getAccountList(boolean isShow, boolean isRefresh){
        AccountListPutBean.ParamsBean paramsBean = new AccountListPutBean.ParamsBean();
        paramsBean.setFamilyid(familyId);
        paramsBean.setLimit_size(limitSize);
        if (!TextUtils.isEmpty(lastUid)){
            paramsBean.setLast_uid(lastUid);
        }

        AccountListPutBean bean = new AccountListPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Account_List_For_Group);
        bean.setModule(ModuleValueService.Module_For_Account_List_For_Group);

        if (getPresenter() != null){
            getPresenter().getAccountListNext(bean, isShow, isRefresh);
        }
    }

    /**
     * 转移权限
     * @param uid 选择用户的uid
     */
    private void onPermissionTransfer(String uid){
        if (familyAuth.contains(ResultDataUtils.Family_Auth_21)){
            Intent intent = new Intent(this, PermissionTransferActivity.class);
            intent.putExtra("uid", uid);
            intent.putExtra("familyId", familyId);
            startActivityForResult(intent, Intent_Add_Account);
        }else{
            ToastUtils.show(getString(R.string.txt_operating_authorization_not));
        }
    }

    /**
     * 编辑用户信息
     * @param bean
     */
    private void onModifyUserInfo(AccountListResultBean.InfoBean bean){
        if (bean.isIs_mine() || familyAuth.contains(ResultDataUtils.Family_Auth_22)){
            Intent intent = new Intent(this, UserInfoModifyActivity.class);
            intent.putExtra("uid", bean.getUid());
            intent.putExtra("role", bean.getRole());
            intent.putExtra("nike_name", bean.getNick_name());
            intent.putExtra("email", bean.getEmail());
            intent.putExtra("is_mine", bean.isIs_mine());
            startActivityForResult(intent, Intent_Add_Account);
        }else{
            ToastUtils.show(getString(R.string.txt_operating_authorization_not));
        }
    }

    /**
     * 删除账号
     * @param uid 账号uid
     */
    private void onDeleteAccount(String uid){
        if (familyLevel == 0){
            if (!familyAuth.contains(ResultDataUtils.Family_Auth_18)){
                ToastUtils.show(getString(R.string.txt_operating_authorization_not));
                return;
            }
        }else{
            if (!familyAuth.contains(ResultDataUtils.Family_Auth_20)){
                ToastUtils.show(getString(R.string.txt_operating_authorization_not));
                return;
            }
        }

        AlertBean bean = new AlertBean();
        bean.setTitle(getString(R.string.txt_tip));
        bean.setAlert(getString(R.string.txt_account_delete_hint));
        bean.setType(0);
        AlertAppDialog dialog = new AlertAppDialog();
        dialog.show(getSupportFragmentManager(), bean, new AlertAppDialog.onAlertDialogChange() {
            @Override
            public void onConfirm() {
                submitAccountDelete(uid);
            }

            @Override
            public void onCancel() {

            }
        });
    }

    /**
     * 删除同级账号
     * @param uid
     */
    private void submitAccountDelete(String uid){
        AccountDeletePutBean.ParamsBean paramsBean = new AccountDeletePutBean.ParamsBean();
        paramsBean.setUid(uid);

        AccountDeletePutBean bean = new AccountDeletePutBean();
        bean.setParams(paramsBean);
        if (familyLevel == 0){
            bean.setFunc(ModuleValueService.Fuc_For_Delete_Account);
        }else{
            bean.setFunc(ModuleValueService.Fuc_For_Delete_Next_Account);
        }
        bean.setModule(ModuleValueService.Module_For_Delete_Account);

        if (getPresenter() != null){
            getPresenter().submitAccountDelete(bean);
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

    @OnClick(R.id.toolbar_right)
    public void onViewClicked() {
        if (Utils.isButtonQuickClick(System.currentTimeMillis())){
            if (familyAuth.contains(ResultDataUtils.Family_Auth_17)){
                Intent intent = new Intent(this, AddAccountActivity.class);
                intent.putExtra("familyId", familyId);
                startActivityForResult(intent, Intent_Add_Account);
            }else{
                ToastUtils.show(getString(R.string.txt_operating_authorization_not));
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == Intent_Add_Account){
                lastUid = "";
                getAccountList(false, true);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void getAccountListNextSuccess(AccountListResultBean accountListResultBean, boolean isRefresh) {
        if (isRefresh){
            familyBeans.clear();
        }
        if (accountListResultBean.getInfo() != null && accountListResultBean.getInfo().size() > 0){
            familyBeans.addAll(accountListResultBean.getInfo());
            lastUid = accountListResultBean.getInfo().get(accountListResultBean.getInfo().size() - 1).getUid();
        }
        mAdapter.notifyDataSetChanged();
        if (familyBeans.size() == 0){
            finish();
        }
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
    public void submitAccountDeleteSuccess(DeviceBaseResultBean deviceBaseResultBean, String uid) {
        taskProgressDialog = null;
        taskId = "";
        setResult(Activity.RESULT_OK);
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
                            ToastUtils.show(getString(R.string.txt_delete_success));
                        }else{
                            ToastUtils.show(return_message);
                        }
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (familyBeans.size() == 1){
                                    finish();
                                }else{
                                    onResetAccountData(uid);
                                }
                            }
                        }, 500);
                    }
                });
            }
        }else{
            ToastUtils.show(getString(R.string.txt_delete_success));
            if (familyBeans.size() == 1){
                finish();
            }else{
                onResetAccountData(uid);
            }
        }
    }

    /**
     * 判断是否还包含管理员用户
     */
    private void onResetAccountData(String uid){
        boolean isHasManage = false; // 是否还包含管理员用户
        for (AccountListResultBean.InfoBean bean : familyBeans){
            if (bean.getRole().equals(ResultDataUtils.Account_Type_Manager) && !bean.getUid().equals(uid)){
                isHasManage = true;
                break;
            }
        }
        if (isHasManage){
            lastUid = "";
            getAccountList(false, true);
        }else{
            finish();
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
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

}
