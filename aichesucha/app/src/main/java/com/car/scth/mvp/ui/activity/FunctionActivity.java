package com.car.scth.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.car.scth.R;
import com.car.scth.app.MyApplication;
import com.car.scth.di.component.DaggerFunctionComponent;
import com.car.scth.mvp.contract.FunctionContract;
import com.car.scth.mvp.model.bean.DeviceFunctionBean;
import com.car.scth.mvp.presenter.FunctionPresenter;
import com.car.scth.mvp.ui.adapter.DeviceFunctionAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 设备主要功能页
 * <p>
 * Created by MVPArmsTemplate on 10/26/2020 16:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class FunctionActivity extends BaseActivity<FunctionPresenter> implements FunctionContract.View {

    @BindView(R.id.gridView)
    GridView gridView;

    private List<DeviceFunctionBean> functionBeans;
    private DeviceFunctionAdapter mAdapter;

    private String mSimei; // 加密的imei号

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFunctionComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_function;//setContentView(id);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_main_function));
        functionBeans = new ArrayList<>();
        onAddFunctionData();
        mSimei = MyApplication.getMyApp().getSimei();

        mAdapter = new DeviceFunctionAdapter(this, R.layout.item_device_function, functionBeans);
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onFunctionClick(functionBeans.get(position).getId());
            }
        });
    }

    /**
     * 添加功能数据
     */
    private void onAddFunctionData(){
        functionBeans.add(new DeviceFunctionBean(0, R.drawable.icon_function_sim, getString(R.string.txt_function_sim)));
        functionBeans.add(new DeviceFunctionBean(1, R.drawable.icon_function_record, getString(R.string.txt_function_record)));
        functionBeans.add(new DeviceFunctionBean(2, R.drawable.icon_function_fence, getString(R.string.txt_function_fence)));
        functionBeans.add(new DeviceFunctionBean(3, R.drawable.icon_function_device_detail, getString(R.string.txt_function_detail)));
        functionBeans.add(new DeviceFunctionBean(4, R.drawable.icon_function_navigation, getString(R.string.txt_function_navigation)));
        functionBeans.add(new DeviceFunctionBean(5, R.drawable.icon_function_remote_switch, getString(R.string.txt_function_remote_switch)));
        functionBeans.add(new DeviceFunctionBean(6, R.drawable.icon_function_location_mode, getString(R.string.txt_function_location_mode)));
        functionBeans.add(new DeviceFunctionBean(7, R.drawable.icon_function_operation_record, getString(R.string.txt_function_operation_record)));
        functionBeans.add(new DeviceFunctionBean(8, R.drawable.icon_function_mileage_statistics, getString(R.string.txt_function_mileage_statistics)));
        functionBeans.add(new DeviceFunctionBean(9, R.drawable.icon_function_remote_listening, getString(R.string.txt_function_remote_listening)));
        functionBeans.add(new DeviceFunctionBean(10, R.drawable.icon_function_oil_and_electricity_control, getString(R.string.txt_function_oil_and_electricity_control)));
        functionBeans.add(new DeviceFunctionBean(11, R.drawable.icon_function_realtime_tracking, getString(R.string.txt_function_realtime_tracking)));
        functionBeans.add(new DeviceFunctionBean(12, R.drawable.icon_function_one_click_restart, getString(R.string.txt_function_one_click_restart)));
        functionBeans.add(new DeviceFunctionBean(13, R.drawable.icon_function_one_key_sleep, getString(R.string.txt_function_one_key_sleep)));
        functionBeans.add(new DeviceFunctionBean(14, R.drawable.icon_function_fault_self_check, getString(R.string.txt_function_fault_self_check)));
    }

    /**
     * 功能点击事件
     */
    private void onFunctionClick(int id){
        switch (id){
            case 0:
                launchActivity(SimDetailActivity.newInstance());
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
            case 11:
                break;
            case 12:
                break;
            case 13:
                break;
            case 14:
                break;
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
}
