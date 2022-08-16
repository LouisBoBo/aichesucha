package com.car.scth.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.car.scth.R;
import com.car.scth.app.MyApplication;
import com.car.scth.di.component.DaggerOperationRecordComponent;
import com.car.scth.mvp.contract.OperationRecordContract;
import com.car.scth.mvp.model.api.ModuleValueService;
import com.car.scth.mvp.model.bean.AlarmScreenDeviceBean;
import com.car.scth.mvp.model.bean.OperationRecordResultBean;
import com.car.scth.mvp.model.putbean.OperationRecordPutBean;
import com.car.scth.mvp.presenter.OperationRecordPresenter;
import com.car.scth.mvp.ui.adapter.OperationAdapter;
import com.car.scth.mvp.ui.adapter.SearchDeviceAdapter;
import com.car.scth.mvp.ui.view.MyLoadMoreView;

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
 * Description: 操作记录
 * <p>
 * Created by MVPArmsTemplate on 10/30/2020 16:07
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class OperationRecordActivity extends BaseActivity<OperationRecordPresenter> implements OperationRecordContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.srl_view)
    SwipeRefreshLayout srlView;
    @BindView(R.id.tv_input_keyword)
    TextView tvInputKeyword; // 关键词搜索
    @BindView(R.id.edt_search_imei)
    EditText edtSearchImei;
    @BindView(R.id.tv_search_cancel)
    TextView tvSearchCancel;
    @BindView(R.id.recyclerView_search)
    RecyclerView recyclerViewSearch;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;

    private TextView tvPrompt; // 无数据布局
    private List<OperationRecordResultBean.ItemsBean> operationBeans;
    private OperationAdapter mAdapter;

    private long mLastTime = 0; // 获取到上一页日志的最后一条信息的时间戳
    private long mLastImei = 0; // 获取到上一页日志的最后一条信息的imei号
    private long mEndTime = 0; // 拉取日志的结束时间,精确到ms,可以不用填写,默认当前时间
    private long mBeginTime = 0; // 拉取日志的起始时间,精确到ms,可以不用填写
    private int limitSize = 20; // 每页最大加载条数
    private String mSimei;
    private List<String> simeiLists;


    public static Intent newInstance() {
        return new Intent(MyApplication.getMyApp(), OperationRecordActivity.class);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerOperationRecordComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_operation_record; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_operation_record));
        operationBeans = new ArrayList<>();
        simeiLists = new ArrayList<>();
        mSimei = MyApplication.getMyApp().getSimei();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        srlView.setColorSchemeResources(R.color.color_00A7FF, R.color.color_00A7FF);
        srlView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mLastImei = 0;
                mLastTime = 0;
                getOperationRecord(false, true);
            }
        });

        View statusView = LayoutInflater.from(this).inflate(R.layout.layout_status, recyclerView, false);
        tvPrompt = statusView.findViewById(R.id.txt_wrong_status);
        tvPrompt.setText(getString(R.string.error_no_data_operation_record));
        tvPrompt.setVisibility(View.GONE);

        mAdapter = new OperationAdapter(R.layout.item_operation_record, operationBeans);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setEmptyView(statusView);

        LoadMoreView loadMoreView = new MyLoadMoreView();
        mAdapter.setLoadMoreView(loadMoreView);
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getOperationRecord(false, false);
            }
        }, recyclerView);

        if (!TextUtils.isEmpty(mSimei)) {
            simeiLists.add(mSimei);
        }

        getOperationRecord(true, true);
    }


    /**
     * 获取操作记录数据
     *
     * @param isShow    是否弹出加载看
     * @param isRefresh 是否是刷新数据
     */
    private void getOperationRecord(boolean isShow, boolean isRefresh) {
        OperationRecordPutBean.ParamsBean paramsBean = new OperationRecordPutBean.ParamsBean();
        paramsBean.setLimit_size(limitSize);
        if (mLastTime != 0) {
            paramsBean.setLast_time(mLastTime);
        }
        if (mLastImei != 0) {
            paramsBean.setLast_imei(mLastImei);
        }
        if (mEndTime != 0) {
            paramsBean.setEnd_time(mEndTime);
        }
        if (mBeginTime != 0) {
            paramsBean.setBegin_time(mBeginTime);
        }
        if (simeiLists.size() > 0) {
            paramsBean.setSimeis(simeiLists);
        }
        OperationRecordPutBean bean = new OperationRecordPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Operation_Record);
        bean.setModule(ModuleValueService.Module_For_Operation_Record);

        if (getPresenter() != null) {
            getPresenter().getOperationRecord(bean, isShow, isRefresh);
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

    @Override
    public void getOperationRecordSuccess(OperationRecordResultBean operationRecordResultBean, boolean isRefresh) {
        if (isRefresh) {
            operationBeans.clear();
        }
        if (operationRecordResultBean.getItems() != null && operationRecordResultBean.getItems().size() > 0) {
            mLastTime = operationRecordResultBean.getLast_time();
            mLastImei = operationRecordResultBean.getLast_imei();
            operationBeans.addAll(operationRecordResultBean.getItems());
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

    /**
     * 显示无数据提示
     */
    private void onShowNoData() {
        if (operationBeans.size() > 0) {
            tvPrompt.setVisibility(View.GONE);
        } else {
            tvPrompt.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.tv_input_keyword, R.id.tv_search_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_input_keyword:
                llSearch.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_search_cancel:
                hideKeyboard(edtSearchImei);
                llSearch.setVisibility(View.GONE);
                edtSearchImei.setText("");
                break;
        }
    }
}
