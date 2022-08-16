package com.car.scth.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.car.scth.app.MyApplication;
import com.car.scth.di.component.DaggerLoopLocationModeComponent;
import com.car.scth.mvp.contract.LoopLocationModeContract;
import com.car.scth.mvp.model.api.ModuleValueService;
import com.car.scth.mvp.model.bean.AlertBean;
import com.car.scth.mvp.model.bean.LoopLocationModeResultBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.LoopLocationModeDeletePutBean;
import com.car.scth.mvp.model.putbean.LoopLocationModePutBean;
import com.car.scth.mvp.model.putbean.LoopModeModifyInfoPutBean;
import com.car.scth.mvp.model.putbean.LoopModeModifyPutBean;
import com.car.scth.mvp.presenter.LoopLocationModePresenter;
import com.car.scth.mvp.ui.adapter.LoopLocationModeAdapter;
import com.car.scth.mvp.ui.view.MyLoadMoreView;
import com.car.scth.mvp.utils.ToastUtils;
import com.car.scth.mvp.weiget.AlertAppDialog;

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
 * Description: 周期定位模式
 * <p>
 * Created by MVPArmsTemplate on 12/12/2020 10:59
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class LoopLocationModeActivity extends BaseActivity<LoopLocationModePresenter> implements LoopLocationModeContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.srl_view)
    SwipeRefreshLayout srlView;
    @BindView(R.id.toolbar_iv_right)
    ImageView toolbarIvRight; // 添加

    private TextView tvPrompt;
    private List<LoopLocationModeResultBean.ItemsBean> loopLocationBeans;
    private LoopLocationModeAdapter mAdapter;
    private int mLimitSize = 20; // 显示条数,非必填，默认20
    private String mLastSlid; // 瀑布流上一条数据id,非必填,默认从第一条数据开始拉取
    private String mSimei;
    private boolean isModeModify = false; // 是否有修改
    private final static int Intent_Loop_Add = 10; // 添加/修改周期定位

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoopLocationModeComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_loop_location_mode;//setContentView(id);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_loop_location_mode));
        toolbarIvRight.setVisibility(View.VISIBLE);
        toolbarIvRight.setImageResource(R.mipmap.icon_add_fence);
        loopLocationBeans = new ArrayList<>();
        mSimei = MyApplication.getMyApp().getSimei();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        srlView.setColorSchemeResources(R.color.color_00A7FF, R.color.color_00A7FF);
        srlView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mLastSlid = "";
                getLoopLocationMode(false, true);
            }
        });

        View statusView = LayoutInflater.from(this).inflate(R.layout.layout_status, recyclerView, false);
        tvPrompt = statusView.findViewById(R.id.txt_wrong_status);
        tvPrompt.setText(getString(R.string.txt_loop_location_mode_not_set));
        tvPrompt.setVisibility(View.GONE);

        mAdapter = new LoopLocationModeAdapter(R.layout.item_loop_location_mode, loopLocationBeans);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setEmptyView(statusView);

        LoadMoreView loadMoreView = new MyLoadMoreView();
        mAdapter.setLoadMoreView(loadMoreView);
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getLoopLocationMode(false, false);
            }
        }, recyclerView);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                submitLoopModify(position);
            }
        });
        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                onLoopDeleteConfirm(position);
                return false;
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                onLoopModeModify(position);
            }
        });

        getLoopLocationMode(true, true);
    }

    /**
     * 获取周期定位列表
     * @param isShow
     */
    private void getLoopLocationMode(boolean isShow, boolean isRefresh){
        LoopLocationModePutBean.ParamsBean paramsBean = new LoopLocationModePutBean.ParamsBean();
        paramsBean.setSimei(mSimei);
        paramsBean.setLimit_size(mLimitSize);
        if (!TextUtils.isEmpty(mLastSlid)){
            paramsBean.setLast_slid(mLastSlid);
        }

        LoopLocationModePutBean bean = new LoopLocationModePutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Loop_Location_Mode_Get);
        bean.setModule(ModuleValueService.Module_For_Loop_Location_Mode_Get);

        if (getPresenter() != null){
            getPresenter().getLoopLocationMode(bean, isShow, isRefresh);
        }
    }

    /**
     * 删除周期定位提示
     * @param position
     */
    private void onLoopDeleteConfirm(int position){
        AlertBean bean = new AlertBean();
        bean.setTitle(getString(R.string.txt_tip));
        bean.setAlert(getString(R.string.txt_loop_location_mode_delete));
        bean.setType(0);
        AlertAppDialog dialog = new AlertAppDialog();
        dialog.show(getSupportFragmentManager(), bean, new AlertAppDialog.onAlertDialogChange() {
            @Override
            public void onConfirm() {
                submitLoopDelete(position);
            }

            @Override
            public void onCancel() {

            }
        });
    }

    /**
     * 删除周期定位
     * @param position
     */
    private void submitLoopDelete(int position){
        LoopLocationModeDeletePutBean.ParamsBean paramsBean = new LoopLocationModeDeletePutBean.ParamsBean();
        paramsBean.setLid(loopLocationBeans.get(position).getLid());

        LoopLocationModeDeletePutBean bean = new LoopLocationModeDeletePutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Loop_Location_Mode_Delete);
        bean.setModule(ModuleValueService.Module_For_Loop_Location_Mode_Delete);

        if (getPresenter() != null){
            getPresenter().submitLoopDelete(bean, position);
        }
    }

    /**
     * 修改周期定位
     */
    private void submitLoopModify(int position){
        LoopModeModifyPutBean.ParamsBean.ItemBean itemBean = new LoopModeModifyPutBean.ParamsBean.ItemBean();
        itemBean.setLoc_switch(!loopLocationBeans.get(position).getInfo().isLoc_switch());
        LoopModeModifyPutBean.ParamsBean paramsBean = new LoopModeModifyPutBean.ParamsBean();
        paramsBean.setItem(itemBean);
        paramsBean.setLid(loopLocationBeans.get(position).getLid());

        LoopModeModifyPutBean bean = new LoopModeModifyPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Loop_Location_Mode_Modify);
        bean.setModule(ModuleValueService.Module_For_Loop_Location_Mode_Modify);

        if (getPresenter() != null){
            getPresenter().submitLoopModify(bean, position);
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
        if (loopLocationBeans.size() >= 4){
            ToastUtils.show(getString(R.string.txt_loop_location_mode_set_max));
            return;
        }
        Intent intent = new Intent(this, LoopModeAddActivity.class);
        startActivityForResult(intent, Intent_Loop_Add);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK){
            isModeModify = true;
            mLastSlid = "";
            getLoopLocationMode(true, true);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 修改周期定位信息
     * @param position
     */
    private void onLoopModeModify(int position){
        LoopLocationModeResultBean.ItemsBean itemsBean = loopLocationBeans.get(position);

        LoopModeModifyInfoPutBean bean = new LoopModeModifyInfoPutBean();
        bean.setLid(itemsBean.getLid());
        bean.setLoc_switch(itemsBean.getInfo().isLoc_switch());
        bean.setLoc_value(itemsBean.getInfo().getLoc_value());
        bean.setLoop_loc_name(itemsBean.getInfo().getLoop_loc_name());
        bean.setRp_loop_loc(itemsBean.getInfo().getRp_loop_loc());

        Intent intent = new Intent(this, LoopModeAddActivity.class);
        intent.putExtra("bean", bean);
        startActivityForResult(intent, Intent_Loop_Add);
    }

    @Override
    public void getLoopLocationModeSuccess(LoopLocationModeResultBean loopLocationModeResultBean, boolean isRefresh) {
        if (isRefresh){
            loopLocationBeans.clear();
        }
        if (loopLocationModeResultBean.getItems() != null){
            loopLocationBeans.addAll(loopLocationModeResultBean.getItems());
        }
        if (loopLocationBeans.size() > 0){
            mLastSlid = loopLocationBeans.get(loopLocationBeans.size() - 1).getLid();
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
    public void submitLoopDeleteSuccess(BaseBean baseBean, int position) {
        ToastUtils.show(getString(R.string.txt_delete_success));
        loopLocationBeans.remove(position);
        mAdapter.notifyDataSetChanged();
        isModeModify = true;
        onShowNoData();
    }

    @Override
    public void submitLoopModifySuccess(BaseBean baseBean, boolean loopSwitch, int position) {
        ToastUtils.show(getString(R.string.txt_modify_success));
        loopLocationBeans.get(position).getInfo().setLoc_switch(loopSwitch);
        mAdapter.notifyItemChanged(position);
        isModeModify = true;
    }

    @Override
    public void finish() {
        if (isModeModify){
            setResult(Activity.RESULT_OK);
        }
        super.finish();
    }

    /**
     * 显示无数据提示
     */
    private void onShowNoData(){
        if (loopLocationBeans.size() > 0){
            tvPrompt.setVisibility(View.GONE);
        }else{
            tvPrompt.setVisibility(View.VISIBLE);
        }
    }

}
