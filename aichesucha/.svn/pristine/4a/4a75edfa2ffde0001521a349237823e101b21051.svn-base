package com.car.scth.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.car.scth.R;
import com.car.scth.di.component.DaggerPermissionTransferComponent;
import com.car.scth.mvp.contract.PermissionTransferContract;
import com.car.scth.mvp.model.api.ModuleValueService;
import com.car.scth.mvp.model.bean.AccountListResultBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.AccountListPutBean;
import com.car.scth.mvp.model.putbean.RoleTransferPutBean;
import com.car.scth.mvp.presenter.PermissionTransferPresenter;
import com.car.scth.mvp.ui.adapter.AccountRoleTransferAdapter;
import com.car.scth.mvp.ui.view.MyLoadMoreView;
import com.car.scth.mvp.utils.ResultDataUtils;
import com.car.scth.mvp.utils.ToastUtils;

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
 * Description: 权限转移
 * <p>
 * Created by MVPArmsTemplate on 03/12/2021 14:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class PermissionTransferActivity extends BaseActivity<PermissionTransferPresenter> implements PermissionTransferContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.srl_view)
    SwipeRefreshLayout srlView;
    @BindView(R.id.btn_save)
    Button btnSave;

    private String familyId; // 组织id
    private String oldUid; // 上一个页面传过来的用户id
    private String roleUser = ResultDataUtils.Account_Type_User; // 用户类型,e_role_user 普通用户权限,e_role_manager 管理员权限

    private List<AccountListResultBean.InfoBean> familyBeans;
    private AccountRoleTransferAdapter mAdapter;

    private String lastUid; // 获取到的最后的用户uid
    private int limitSize = 50; // 限制获取数量

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPermissionTransferComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_permission_transfer;//setContentView(id);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_transfer));
        familyBeans = new ArrayList<>();
        familyId = getIntent().getStringExtra("familyId");
        oldUid = getIntent().getStringExtra("uid");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        srlView.setColorSchemeResources(R.color.color_00A7FF, R.color.color_00A7FF);
        srlView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                lastUid = "";
                getAccountList(false, true);
            }
        });

        mAdapter = new AccountRoleTransferAdapter(R.layout.item_account_role_transfer, familyBeans);
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
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                boolean isSelect = familyBeans.get(position).isSelect();
                for (AccountListResultBean.InfoBean bean : familyBeans){
                    bean.setSelect(false);
                }
                familyBeans.get(position).setSelect(!isSelect);
                mAdapter.notifyDataSetChanged();
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
        paramsBean.setRole(roleUser);
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

    @OnClick(R.id.btn_save)
    public void onViewClicked() {
        String uid = "";
        for (AccountListResultBean.InfoBean bean : familyBeans){
            if (bean.isSelect()){
                uid = bean.getUid();
                break;
            }
        }
        if (TextUtils.isEmpty(uid)){
            ToastUtils.show(getString(R.string.txt_account_select));
            return;
        }

        RoleTransferPutBean.ParamsBean paramsBean = new RoleTransferPutBean.ParamsBean();
        paramsBean.setOld_uid(oldUid);
        paramsBean.setUid(uid);

        RoleTransferPutBean bean = new RoleTransferPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Role_Transfer);
        bean.setModule(ModuleValueService.Module_For_Role_Transfer);

        if (getPresenter() != null){
            getPresenter().submitRoleTransfer(bean);
        }
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
    public void submitRoleTransferSuccess(BaseBean baseBean) {
        ToastUtils.show(getString(R.string.txt_successful_operation));
        setResult(Activity.RESULT_OK);
        finish();
    }

}
