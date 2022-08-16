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
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.car.scth.R;
import com.car.scth.app.MyApplication;
import com.car.scth.di.component.DaggerFenceComponent;
import com.car.scth.mvp.contract.FenceContract;
import com.car.scth.mvp.model.api.Api;
import com.car.scth.mvp.model.api.ModuleValueService;
import com.car.scth.mvp.model.bean.AlertBean;
import com.car.scth.mvp.model.bean.FenceInfoModifyBean;
import com.car.scth.mvp.model.bean.FencePointBean;
import com.car.scth.mvp.model.bean.FenceResultBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.FenceDeletePutBean;
import com.car.scth.mvp.model.putbean.FenceListPutBean;
import com.car.scth.mvp.presenter.FencePresenter;
import com.car.scth.mvp.ui.adapter.FenceAdapter;
import com.car.scth.mvp.utils.ConstantValue;
import com.car.scth.mvp.utils.LocationAddress;
import com.car.scth.mvp.utils.ResultDataUtils;
import com.car.scth.mvp.utils.ToastUtils;
import com.car.scth.mvp.utils.Utils;
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
 * Description: 围栏列表
 * <p>
 * Created by MVPArmsTemplate on 10/31/2020 16:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class FenceActivity extends BaseActivity<FencePresenter> implements FenceContract.View {

    @BindView(R.id.toolbar_iv_right)
    ImageView toolbarIvRight;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.srl_view)
    SwipeRefreshLayout srlView;

    private TextView tvPrompt; // 无数据提示语
    private List<FenceResultBean.ItemsBean> fenceBeans;
    private FenceAdapter mAdapter;

    private String mSimei; // 加密的imei号
    private int limitSize = 20; // 每页最大加载条数
    private String lastSfid; // 瀑布流上一条数据id,非必填,默认从第一条数据开始拉取
    private int mPosition = 0; // 选中的索引位置

    private List<FencePointBean> pointBeans; // 多边形围栏经纬度信息，查看/修改围栏时传递数据使用
    private int mapType = 0; // 地图类型,0：高德地图，1：百度地图，2：谷歌地图
    private final static int INTENT_CREATE = 10; // 创建/修改围栏回调

    public static Intent newInstance() {
        return new Intent(MyApplication.getMyApp(), FenceActivity.class);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFenceComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_fence; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_electric_fence));
        fenceBeans = new ArrayList<>();
        pointBeans = new ArrayList<>();
        toolbarIvRight.setVisibility(View.VISIBLE);
        toolbarIvRight.setImageResource(R.mipmap.icon_add_fence);
        mSimei = MyApplication.getMyApp().getSimei();
        mapType = ConstantValue.getMapType();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        srlView.setColorSchemeResources(R.color.color_00A7FF, R.color.color_00A7FF);
        srlView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                lastSfid = "";
                getFenceList(false, true);
            }
        });

        View statusView = LayoutInflater.from(this).inflate(R.layout.layout_status, recyclerView, false);
        tvPrompt = statusView.findViewById(R.id.txt_wrong_status);
        tvPrompt.setText(getString(R.string.error_no_data_fence));
        tvPrompt.setVisibility(View.GONE);

        mAdapter = new FenceAdapter(R.layout.item_fence_new, fenceBeans);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setEmptyView(statusView);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                onFenceModify(position);
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                onFenceDeleteConfirm(position);
            }
        });

        getFenceList(true, true);
    }

    /**
     * 获取围栏列表数据
     * @param isShow 是否弹出加载看
     * @param isRefresh 是否是刷新数据
     */
    private void getFenceList(boolean isShow, boolean isRefresh){
        FenceListPutBean.ParamsBean paramsBean = new FenceListPutBean.ParamsBean();
        paramsBean.setLimit_size(limitSize);
        paramsBean.setSimei(mSimei);
        if (!TextUtils.isEmpty(lastSfid)){
            paramsBean.setLast_sfid(lastSfid);
        }

        FenceListPutBean bean = new FenceListPutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Fence_List);
        bean.setModule(ModuleValueService.Module_For_Fence_List);
        bean.setParams(paramsBean);

        if (getPresenter() != null){
            getPresenter().getFenceList(bean, isShow, isRefresh);
        }
    }

    /**
     * 删除围栏确认提示
     */
    private void onFenceDeleteConfirm(int position){
        AlertBean bean = new AlertBean();
        bean.setTitle(getString(R.string.txt_tip));
        bean.setAlert(getString(R.string.txt_fence_delete_hint));
        bean.setType(0);
        AlertAppDialog dialog = new AlertAppDialog();
        dialog.show(getSupportFragmentManager(), bean, new AlertAppDialog.onAlertDialogChange() {
            @Override
            public void onConfirm() {
                submitFenceDelete(position);
            }

            @Override
            public void onCancel() {

            }
        });
    }

    /**
     * 提交删除围栏
     * @param position
     */
    private void submitFenceDelete(int position){
        mPosition = position;
        FenceDeletePutBean.ParamsBean paramsBean = new FenceDeletePutBean.ParamsBean();
        paramsBean.setSfid(fenceBeans.get(position).getSfid());

        FenceDeletePutBean bean = new FenceDeletePutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Fence_Delete);
        bean.setModule(ModuleValueService.Module_For_Fence_Delete);
        bean.setParams(paramsBean);

        if (getPresenter() != null){
            getPresenter().submitFenceDelete(bean);
        }
    }

    /**
     * 查看，修改围栏
     * @param position
     */
    private void onFenceModify(int position){
        // 原始数据bean
        FenceResultBean.ItemsBean itemsBean = fenceBeans.get(position);
        // 传参数据bean
        FenceInfoModifyBean bean = new FenceInfoModifyBean();
        bean.setSfid(itemsBean.getSfid());
        bean.setType(itemsBean.getType());
        bean.setFenceAlarmType(itemsBean.getFence_switch());
        bean.setName(itemsBean.getName());
        if (itemsBean.getType().equals(ResultDataUtils.Fence_Round)){
            bean.setRadius(itemsBean.getOfence().getCircle().getRadius());
            bean.setLon(Utils.formatLatLngForDivisionOperation(itemsBean.getOfence().getCircle().getLon()));
            bean.setLat(Utils.formatLatLngForDivisionOperation(itemsBean.getOfence().getCircle().getLat()));
        }else if (itemsBean.getType().equals(ResultDataUtils.Fence_City)){
            ToastUtils.show(getString(R.string.txt_fence_not_support));
            return;
//            bean.setCityName(itemsBean.getOfence().getCity().getName());
//            bean.setDistrictName(itemsBean.getOfence().getCity().getDistrict());
        }else{
            pointBeans.clear();
            if (itemsBean.getOfence().getPolygon().getPoit() != null){
                for (int i = 0; i < itemsBean.getOfence().getPolygon().getPoit().size(); i++){
                    FencePointBean pointBean = new FencePointBean();
                    FenceResultBean.ItemsBean.OfenceBean.PolygonBean.PoitBean polygonBean = itemsBean.getOfence().getPolygon().getPoit().get(i);
                    pointBean.setLat(Utils.formatLatLngForDivisionOperation(polygonBean.getLat()));
                    pointBean.setLon(Utils.formatLatLngForDivisionOperation(polygonBean.getLon()));
                    pointBeans.add(pointBean);
                }
            }
            bean.setPointBeans(pointBeans);
        }

        Intent intent = new Intent();
        if (mapType == 2){
            // 谷歌地图
        }else if(mapType == 1){
            // 百度地图
            intent.setClass(FenceActivity.this, FenceCreateBaiduActivity.class);
        }else{
            // 高德地图
            intent.setClass(FenceActivity.this, FenceCreateAmapActivity.class);
        }
        intent.putExtra("bean", bean);
        startActivityForResult(intent, INTENT_CREATE);
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
        if (Utils.isButtonQuickClick(System.currentTimeMillis())) {
            if (fenceBeans.size() >= 3) {
                ToastUtils.show(getString(R.string.txt_fence_max_number));
                return;
            }
            Intent intent = new Intent();
            if (mapType == 2) {
                // 谷歌地图
            } else if (mapType == 1) {
                // 百度地图
                intent.setClass(FenceActivity.this, FenceCreateBaiduActivity.class);
            } else {
                // 高德地图
                intent.setClass(FenceActivity.this, FenceCreateAmapActivity.class);
            }
            startActivityForResult(intent, INTENT_CREATE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == INTENT_CREATE){
                lastSfid = "";
                getFenceList(true, true);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void getFenceListSuccess(FenceResultBean fenceResultBean, boolean isRefresh) {
        if (isRefresh){
            fenceBeans.clear();
        }
        if (fenceResultBean.getItems() != null && fenceResultBean.getItems().size() > 0){
            lastSfid = fenceResultBean.getItems().get(fenceResultBean.getItems().size() - 1).getSfid();
            fenceBeans.addAll(fenceResultBean.getItems());
        }
        mAdapter.notifyDataSetChanged();
        onShowNoData();
        for (int i = 0; i < fenceBeans.size(); i++) {
            FenceResultBean.ItemsBean itemsBean = fenceBeans.get(i);
            if (itemsBean.getType().equals(ResultDataUtils.Fence_Round)) {
                FenceResultBean.ItemsBean.OfenceBean.CircleBean bean = itemsBean.getOfence().getCircle();
                setBeanAddress(bean);
            }
        }
    }

    @Override
    public void finishRefresh() {
        srlView.setRefreshing(false);
    }

    @Override
    public void endLoadMore() {
//        mAdapter.loadMoreComplete();
    }

    @Override
    public void setNoMore() {
//        mAdapter.loadMoreEnd();
    }

    @Override
    public void endLoadFail() {
//        mAdapter.loadMoreFail();
    }

    @Override
    public void submitFenceDeleteSuccess(BaseBean baseBean) {
        ToastUtils.show(getString(R.string.txt_delete_success));
        fenceBeans.remove(mPosition);
        mAdapter.notifyDataSetChanged();
        onShowNoData();
    }

    private void setBeanAddress(FenceResultBean.ItemsBean.OfenceBean.CircleBean bean) {
        if (bean.getLon() != 0 && bean.getLat() != 0) {
            double lat = Utils.formatLatLngForDivisionOperation(bean.getLat());
            double lon = Utils.formatLatLngForDivisionOperation(bean.getLon());
            new LocationAddress().Parsed(lat, lon).
                    setAddressListener(new LocationAddress.getAddressListener() {
                        @Override
                        public void getAddress(String str) {
                            bean.setAddr(str);
                            mAdapter.notifyDataSetChanged();
                        }
                    });
        }
    }

    /**
     * 显示无数据提示
     */
    private void onShowNoData(){
        if (fenceBeans.size() > 0){
            tvPrompt.setVisibility(View.GONE);
        }else{
            tvPrompt.setVisibility(View.VISIBLE);
        }
    }

}
