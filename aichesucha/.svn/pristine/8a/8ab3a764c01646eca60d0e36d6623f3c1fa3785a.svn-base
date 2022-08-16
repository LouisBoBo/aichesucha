package com.car.scth.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.car.scth.R;
import com.car.scth.app.MyApplication;
import com.car.scth.di.component.DaggerIconCheckComponent;
import com.car.scth.mvp.contract.IconCheckContract;
import com.car.scth.mvp.model.api.ModuleValueService;
import com.car.scth.mvp.model.bean.IconCheckBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.DeviceDetailModifyPutBean;
import com.car.scth.mvp.presenter.IconCheckPresenter;
import com.car.scth.mvp.ui.adapter.IconCheckAdapter;
import com.car.scth.mvp.utils.BroadcastReceiverUtil;
import com.car.scth.mvp.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/02/2020 10:24
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class IconCheckActivity extends BaseActivity<IconCheckPresenter> implements IconCheckContract.View {

    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<IconCheckBean> iconCheckBeans;
    private IconCheckAdapter mAdapter;
    private int carImageId = 0; // 设备的图标id
    private String mSimei; // 设备的simei号

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerIconCheckComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_icon_check; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_device_icon));
        toolbarRight.setVisibility(View.VISIBLE);
        toolbarRight.setText(getString(R.string.txt_save));
        iconCheckBeans = new ArrayList<>();
        mSimei = MyApplication.getMyApp().getSimei();
        carImageId = MyApplication.getMyApp().getCarImageId();

        iconCheckBeans.add(new IconCheckBean(0, R.mipmap.icon_car_online, getString(R.string.txt_device_car_0), false));
        iconCheckBeans.add(new IconCheckBean(1, R.mipmap.icon_car_online_1, getString(R.string.txt_device_car_1), false));
        iconCheckBeans.add(new IconCheckBean(2, R.mipmap.icon_car_online_2,getString(R.string.txt_device_car_2),  false));
        iconCheckBeans.add(new IconCheckBean(3, R.mipmap.icon_car_online_3,getString(R.string.txt_device_car_3),  false));
        iconCheckBeans.add(new IconCheckBean(4, R.mipmap.icon_car_online_4,getString(R.string.txt_device_car_4),  false));
        for (IconCheckBean bean : iconCheckBeans){
            if (bean.getId() == carImageId){
                bean.setSelect(true);
            }
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new IconCheckAdapter(R.layout.item_icon_device, iconCheckBeans);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (IconCheckBean bean : iconCheckBeans){
                    bean.setSelect(false);
                }
                iconCheckBeans.get(position).setSelect(true);
                mAdapter.notifyDataSetChanged();
            }
        });
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

    @OnClick(R.id.toolbar_right)
    public void onViewClicked() {
        for (IconCheckBean bean : iconCheckBeans){
            if (bean.isSelect()){
                carImageId = bean.getId();
                break;
            }
        }

        DeviceDetailModifyPutBean.ParamsBean paramsBean = new DeviceDetailModifyPutBean.ParamsBean();
        paramsBean.setCar_image(carImageId);
        paramsBean.setSimei(mSimei);

        DeviceDetailModifyPutBean bean = new DeviceDetailModifyPutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Modify_Device_Detail);
        bean.setModule(ModuleValueService.Module_For_Modify_Device_Detail);
        bean.setParams(paramsBean);

        if (getPresenter() != null) {
            getPresenter().submitDetailModify(bean);
        }
    }

    @Override
    public void submitDetailModifySuccess(BaseBean baseBean) {
        MyApplication.getMyApp().setCarImageId(carImageId);
        ToastUtils.show(getString(R.string.txt_set_success));
        setResult(Activity.RESULT_OK);
        finish();
    }
}
