package com.car.scth.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.car.scth.app.MyApplication;
import com.car.scth.di.component.DaggerSysSettingActivityComponent;
import com.car.scth.mvp.contract.SysSettingActivityContract;
import com.car.scth.mvp.model.api.ModuleValueService;
import com.car.scth.mvp.model.bean.AlertBean;
import com.car.scth.mvp.model.bean.DataCenterBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.OnKeyFunctionPutBean;
import com.car.scth.mvp.presenter.SysSettingActivityPresenter;

import com.car.scth.R;
import com.car.scth.mvp.ui.adapter.FunctionSetAdapter;
import com.car.scth.mvp.utils.ResultDataUtils;
import com.car.scth.mvp.utils.ToastUtils;
import com.car.scth.mvp.utils.Utils;
import com.car.scth.mvp.weiget.AlertAppDialog;
import com.car.scth.mvp.weiget.BreakdownExamineDialog;


import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/09/2021 15:55
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SysSettingActivityActivity extends BaseActivity<SysSettingActivityPresenter> implements SysSettingActivityContract.View {

    @BindView(R.id.sys_set_recyclerView)
    RecyclerView recyclerView;

    private List<DataCenterBean> functionBeans;
    private FunctionSetAdapter mAdapter;
    private int supportRemoteListen;
    private final static String Key_Remote_Listen = "remoteListen";
    private String mSimei; // simei号

    public static Intent newInstance(int remoteSupport) {
        Intent intent = new Intent(MyApplication.getMyApp(), SysSettingActivityActivity.class);
        intent.putExtra(Key_Remote_Listen, remoteSupport);
        return intent;
    }
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSysSettingActivityComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_sys_setting;//setContentView(id);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_sys_set));
        mSimei = MyApplication.getMyApp().getSimei();
        supportRemoteListen = getIntent().getIntExtra(Key_Remote_Listen,0);
        functionBeans = new ArrayList<>();
        functionBeans.add(new DataCenterBean(0, getString(R.string.txt_function_fault_self_check), R.mipmap.icon_guzhangzijian));
        if (supportRemoteListen == 1) {
            functionBeans.add(new DataCenterBean(1, getString(R.string.txt_function_remote_listening), R.mipmap.icon_yuanchengtingyin));
        }
        functionBeans.add(new DataCenterBean(2, getString(R.string.txt_switch_device_icon), R.mipmap.icon_qiehuantubiao));
        functionBeans.add(new DataCenterBean(3, getString(R.string.txt_reset_device), R.mipmap.icon_huifuchuchang));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new FunctionSetAdapter(R.layout.item_sys_function, functionBeans);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (Utils.isButtonQuickClick(System.currentTimeMillis())) {
                    onFunctionClick(functionBeans.get(position).getId());
                }
            }
        });
    }

    /**
     * 点击事件
     * @param id
     */
    private void onFunctionClick(int id){
        switch (id){
            case 0:
                BreakdownExamineDialog dialog = new BreakdownExamineDialog();
                dialog.show(getSupportFragmentManager());
                break;
            case 1:
                startActivity(new Intent(this, RemoteListeningActivity.class));
                break;
            case 2:
                Intent intent =  new Intent(this, IconCheckActivity.class);
                startActivity(intent);
                break;
            case 3:
                onConfirmOneKeyFunction(ResultDataUtils.Function_Reset);
                break;
        }
    }

    /**
     * 一键功能提交
     * @param key 功能值
     */
    private void onConfirmOneKeyFunction(String key){
        AlertBean bean = new AlertBean();
        bean.setTitle(getString(R.string.txt_tip));
        switch (key){
            case ResultDataUtils.Function_Restart:
                bean.setAlert(getString(R.string.txt_restart_hint));
                break;
            case ResultDataUtils.Function_Sleep:
                bean.setAlert(getString(R.string.txt_one_key_sleep));
                break;
            case ResultDataUtils.Function_Finddev:
                bean.setAlert(getString(R.string.txt_looking_for_equipment));
                break;
            case ResultDataUtils.Function_Wakeup:
                bean.setAlert(getString(R.string.txt_one_key_wakeup));
                break;
            case ResultDataUtils.Function_Reset:
                bean.setAlert(getString(R.string.txt_reset_device_hint));
                break;
        }
        bean.setType(0);
        AlertAppDialog dialog = new AlertAppDialog();
        dialog.show(getSupportFragmentManager(), bean, new AlertAppDialog.onAlertDialogChange() {
            @Override
            public void onConfirm() {
                submitOneKeyFunction(key);
            }

            @Override
            public void onCancel() {

            }
        });
    }

    /**
     * 一键功能提交
     * @param key 功能值
     */
    private void submitOneKeyFunction(String key){
        OnKeyFunctionPutBean.ParamsBean paramsBean = new OnKeyFunctionPutBean.ParamsBean();
        paramsBean.setType(key);
        paramsBean.setSimei(mSimei);

        OnKeyFunctionPutBean bean = new OnKeyFunctionPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_OnKey_Function);
        bean.setModule(ModuleValueService.Module_For_OnKey_Function);

        showProgressDialog();

        getPresenter().submitOneKeyFunction(bean);
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
    public void submitOneKeyFunctionSuccess(BaseBean baseBean) {
        ToastUtils.show(getString(R.string.txt_successful_operation));
    }

    @Override
    public void onDismissProgress() {
        dismissProgressDialog();
    }
}
