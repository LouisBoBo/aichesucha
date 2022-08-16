package com.car.scth.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.car.scth.R;
import com.car.scth.app.MyApplication;
import com.car.scth.di.component.DaggerLocationModeComponent;
import com.car.scth.mvp.contract.LocationModeContract;
import com.car.scth.mvp.model.api.ModuleValueService;
import com.car.scth.mvp.model.bean.AlertBean;
import com.car.scth.mvp.model.bean.LocationModeBean;
import com.car.scth.mvp.model.bean.LocationModeGetResultBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.DeviceModeSetPutBean;
import com.car.scth.mvp.model.putbean.LocationModeGetPutBean;
import com.car.scth.mvp.presenter.LocationModePresenter;
import com.car.scth.mvp.ui.adapter.LocationModeAdapter;
import com.car.scth.mvp.utils.ResultDataUtils;
import com.car.scth.mvp.utils.ToastUtils;
import com.car.scth.mvp.utils.Utils;
import com.car.scth.mvp.weiget.AlertAppDialog;
import com.car.scth.mvp.weiget.AlertAppRightDialog;
import com.car.scth.mvp.weiget.SelectModelLongDialog;
import com.car.scth.mvp.weiget.SelectTimeDialog;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/30/2020 17:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class LocationModeActivity extends BaseActivity<LocationModePresenter> implements LocationModeContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<LocationModeBean> modeBeans;
    private LocationModeAdapter mAdapter;
    private String mSimei; // 加密的imei号
    private String deviceMode; // 定位模式
    private int modeType = 0; // 值,例如点名模式 定位间隔 飞行模式开关 0-关闭 1-打开
    private String modeValue = ""; // 待机模式设置的时间 格式 HH:MM 飞行模式定位间隔
    private String mPowerSavingShowLong; // 省电模式时长，用于显示在UI上
    private List<LocationModeGetResultBean.SavePowerBean> savePowerBeans; // 省电模式下可选的时间

    private final static int Intent_Set_Mode = 10; // 设置飞行模式，周期定位模式回调

    public static Intent newInstance() {
        return new Intent(MyApplication.getMyApp(), LocationModeActivity.class);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLocationModeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_location_mode; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_function_location_mode));
        modeBeans = new ArrayList<>();
        savePowerBeans = new ArrayList<>();
        mSimei = MyApplication.getMyApp().getSimei();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new LocationModeAdapter(R.layout.item_location_mode, modeBeans);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (Utils.isButtonQuickClick(System.currentTimeMillis())) {
                    onLocationModeClick(position);
                }
            }
        });

        getLocationMode();
    }

    /**
     * 获取定位模式数据
     */
    private void getLocationMode() {
        LocationModeGetPutBean.ParamsBean paramsBean = new LocationModeGetPutBean.ParamsBean();
        paramsBean.setSimei(mSimei);

        LocationModeGetPutBean bean = new LocationModeGetPutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Location_Mode);
        bean.setModule(ModuleValueService.Module_For_Location_Mode);
        bean.setParams(paramsBean);

        if (getPresenter() != null) {
            getPresenter().getLocationMode(bean);
        }
    }

    /**
     * 设置定位模式
     *
     * @param mode       定位模式
     * @param mode_type  值,例如点名模式 定位间隔 飞行模式开关 0-关闭 1-打开
     * @param mode_value 待机模式设置的时间 格式 HH:MM 飞行模式定位间隔
     */
    private void submitLocationMode(String mode, int mode_type, String mode_value) {
        if (deviceMode.equals(mode) && !deviceMode.equals(ResultDataUtils.Device_Mode_Sleep) && !deviceMode.equals(ResultDataUtils.Device_Mode_Save_Power)) {
            return;
        }
        DeviceModeSetPutBean.ParamsBean paramsBean = new DeviceModeSetPutBean.ParamsBean();
        paramsBean.setSimei(mSimei);
        paramsBean.setMode(mode);
        if (mode_type != -1) {
            paramsBean.setMode_type(mode_type);
        }
        if (!TextUtils.isEmpty(mode_value)) {
            paramsBean.setSmode_value(mode_value);
        }
        DeviceModeSetPutBean bean = new DeviceModeSetPutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Location_Mode_Set);
        bean.setModule(ModuleValueService.Module_For_Location_Mode_Set);
        bean.setParams(paramsBean);

        if (getPresenter() != null) {
            getPresenter().submitLocationMode(bean);
        }
    }

    /**
     * 根据获取到的设备数据，设备类型，展示对应的
     */
    private void setLocationModeData() {
        for (LocationModeBean bean : modeBeans) {
            if (bean.getMode().equals(deviceMode)) {
                bean.setSelect(true);
            } else {
                bean.setSelect(false);
            }
            if (bean.getMode().equals(ResultDataUtils.Device_Mode_Save_Power)) {
                bean.setModeName(getString(R.string.txt_location_mode_7));
            } else if (bean.getMode().equals(ResultDataUtils.Device_Mode_Sleep)) {
                bean.setModeName(getString(R.string.txt_location_mode_6));
            }
        }

        if (deviceMode.equals(ResultDataUtils.Device_Mode_Save_Power)) {
            if (!TextUtils.isEmpty(mPowerSavingShowLong)) {
                for (LocationModeBean bean : modeBeans) {
                    if (bean.getMode().equals(ResultDataUtils.Device_Mode_Save_Power)) {
                        bean.setModeName(bean.getModeName() + " (" + mPowerSavingShowLong + ")");
                        break;
                    }
                }
            }
        } else if (deviceMode.equals(ResultDataUtils.Device_Mode_Sleep)) {
            if (!TextUtils.isEmpty(modeValue)) {
                for (LocationModeBean bean : modeBeans) {
                    if (bean.getMode().equals(ResultDataUtils.Device_Mode_Sleep)) {
                        bean.setModeName(bean.getModeName() + " (" + modeValue + ")");
                        break;
                    }
                }
            }
        }

        mAdapter.notifyDataSetChanged();
    }

    /**
     * 设置定位模式
     *
     * @param position
     */
    private void onLocationModeClick(int position) {
        switch (modeBeans.get(position).getMode()) {
            case ResultDataUtils.Device_Mode_Nomal:
                submitLocationMode(ResultDataUtils.Device_Mode_Nomal, -1, "");
                break;
            case ResultDataUtils.Device_Mode_Nomal_X7:
                submitLocationMode(ResultDataUtils.Device_Mode_Nomal_X7, -1, "");
                break;
            case ResultDataUtils.Device_Mode_Looploc:
                onSetLoopLocationMode();
                break;
            case ResultDataUtils.Device_Mode_Fly:
                onSetFlyMode();
                break;
            case ResultDataUtils.Device_Mode_Sup_Save_Power:
                onSetSupSavePowerMode();
                break;
            case ResultDataUtils.Device_Mode_Auto_Save_Power:
                break;
            case ResultDataUtils.Device_Mode_Sleep:
                setSleepMode();
                break;
            case ResultDataUtils.Device_Mode_Save_Power:
                setPowerSavingMode();
                break;
            case ResultDataUtils.Device_Mode_Call_One:
            case ResultDataUtils.Device_Mode_Sup_Save_Power_C2:
                onAlertForSetMode(ResultDataUtils.Device_Mode_Call_One, "");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            getLocationMode();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 设置飞行模式
     */
    private void onSetFlyMode() {
        Intent intent = new Intent(this, FlyModeActivity.class);
        if (deviceMode.equals(ResultDataUtils.Device_Mode_Fly)) {
            intent.putExtra("mode_type", modeType);
            intent.putExtra("mode_value", modeValue);
        }
        startActivityForResult(intent, Intent_Set_Mode);
    }

    /**
     * 设置周期定位模式
     */
    private void onSetLoopLocationMode() {
        Intent intent = new Intent(this, LoopLocationModeActivity.class);
        startActivityForResult(intent, Intent_Set_Mode);
    }

    /**
     * 开启超级省电模式(mode=4)
     */
    private void onSetSupSavePowerMode(){
        AlertBean bean = new AlertBean();
        bean.setTitle(getString(R.string.txt_tip));
        bean.setAlert(getString(R.string.txt_sup_save_power_hint));
        bean.setType(0);
        AlertAppDialog dialog = new AlertAppDialog();
        dialog.show(getSupportFragmentManager(), bean, new AlertAppDialog.onAlertDialogChange() {
            @Override
            public void onConfirm() {
                submitLocationMode(ResultDataUtils.Device_Mode_Sup_Save_Power, -1, "");
            }

            @Override
            public void onCancel() {

            }
        });
    }

    /**
     * 设置睡眠模式
     */
    private void setSleepMode() {
        SelectTimeDialog timeDialog = new SelectTimeDialog();
        timeDialog.show(getSupportFragmentManager(), modeValue, new SelectTimeDialog.onSelectTimeChange() {
            @Override
            public void onSelectTime(String time) {
                onAlertForSetMode(ResultDataUtils.Device_Mode_Sleep, time);
            }
        });
    }

    /**
     * 设置省电模式
     */
    private void setPowerSavingMode() {
        SelectModelLongDialog dialog = new SelectModelLongDialog();
        dialog.show(getSupportFragmentManager(), savePowerBeans, new SelectModelLongDialog.onSelectModeLongChange() {
            @Override
            public void onSelectModeLong(int mode_long, String showLong) {
                submitLocationMode(ResultDataUtils.Device_Mode_Save_Power, mode_long, "");
            }
        });
    }

    /**
     * 二次确认设置定位模式
     *
     * @param mode 定位模式
     * @param time 睡眠模式定位时间
     */
    private void onAlertForSetMode(String mode, String time) {
        AlertBean bean = new AlertBean();
        bean.setTitle(getString(R.string.txt_tip));
        if (mode.equals(ResultDataUtils.Device_Mode_Sleep)) {
            bean.setAlert(getString(R.string.txt_set_prompt));
        } else {
            bean.setAlert(getString(R.string.txt_super_call_the_roll_mode_prompt));
        }
        bean.setType(0);
        AlertAppRightDialog dialog = new AlertAppRightDialog();
        dialog.show(getSupportFragmentManager(), bean, new AlertAppRightDialog.onAlertDialogChange() {
            @Override
            public void onConfirm() {
                if (mode.equals(ResultDataUtils.Device_Mode_Sleep)) {
                    submitLocationMode(ResultDataUtils.Device_Mode_Sleep, -1, time);
                } else {
                    submitLocationMode(ResultDataUtils.Device_Mode_Call_One, -1, "");
                }
            }

            @Override
            public void onCancel() {

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

    @Override
    public void getLocationModeSuccess(LocationModeGetResultBean locationModeGetResultBean) {
        savePowerBeans.clear();
        if (locationModeGetResultBean.getSave_power() != null) {
            savePowerBeans.addAll(locationModeGetResultBean.getSave_power());
        }
        deviceMode = locationModeGetResultBean.getMode();
        MyApplication.getMyApp().setDeviceMode(deviceMode);
        modeValue = locationModeGetResultBean.getSmode_value();
        modeType = locationModeGetResultBean.getMode_type();
        mPowerSavingShowLong = "";
        if (deviceMode.equals(ResultDataUtils.Device_Mode_Save_Power)) {
            for (LocationModeGetResultBean.SavePowerBean bean : savePowerBeans) {
                if (bean.getTime() == modeType) {
                    mPowerSavingShowLong = bean.getName();
                    break;
                }
            }
        }

        modeBeans.clear();
        for (int i = 0; i < locationModeGetResultBean.getMode_fun().size(); i++) {
            switch (locationModeGetResultBean.getMode_fun().get(i)) {
                case ResultDataUtils.Device_Mode_Nomal:
                    modeBeans.add(new LocationModeBean(ResultDataUtils.Device_Mode_Nomal, getString(R.string.txt_location_mode_1), getString(R.string.location_mode_hint_1), false));
                    break;
                case ResultDataUtils.Device_Mode_Nomal_X7:
                    modeBeans.add(new LocationModeBean(ResultDataUtils.Device_Mode_Nomal_X7, getString(R.string.txt_location_mode_1), getString(R.string.location_mode_hint_1), false));
                    break;
                case ResultDataUtils.Device_Mode_Looploc:
                    modeBeans.add(new LocationModeBean(ResultDataUtils.Device_Mode_Looploc, getString(R.string.txt_location_mode_2), getString(R.string.location_mode_hint_2), false));
                    break;
                case ResultDataUtils.Device_Mode_Fly:
                    modeBeans.add(new LocationModeBean(ResultDataUtils.Device_Mode_Fly, getString(R.string.txt_location_mode_3), getString(R.string.location_mode_hint_3), false));
                    break;
                case ResultDataUtils.Device_Mode_Sup_Save_Power:
                    modeBeans.add(new LocationModeBean(ResultDataUtils.Device_Mode_Sup_Save_Power, getString(R.string.txt_location_mode_4), getString(R.string.location_mode_hint_4), false));
                    break;
                case ResultDataUtils.Device_Mode_Sleep:
                    modeBeans.add(new LocationModeBean(ResultDataUtils.Device_Mode_Sleep, getString(R.string.txt_location_mode_6), getString(R.string.location_mode_hint_6), false));
                    break;
                case ResultDataUtils.Device_Mode_Save_Power:
                    modeBeans.add(new LocationModeBean(ResultDataUtils.Device_Mode_Save_Power, getString(R.string.txt_location_mode_7), getString(R.string.location_mode_hint_7), false));
                    break;
                case ResultDataUtils.Device_Mode_Call_One:
                case ResultDataUtils.Device_Mode_Sup_Save_Power_C2:
                    modeBeans.add(new LocationModeBean(ResultDataUtils.Device_Mode_Call_One, getString(R.string.txt_location_mode_8), getString(R.string.location_mode_hint_8), false));
                    break;
            }
        }

        setLocationModeData();
    }

    @Override
    public void submitLocationModeSuccess(BaseBean baseBean, DeviceModeSetPutBean deviceModeSetPutBean) {
        ToastUtils.show(getString(R.string.txt_set_success));
        deviceMode = deviceModeSetPutBean.getParams().getMode();
        MyApplication.getMyApp().setDeviceMode(deviceMode);
        for (LocationModeBean bean : modeBeans) {
            if (bean.getMode().equals(deviceMode)) {
                bean.setSelect(true);
            } else {
                bean.setSelect(false);
            }
            if (bean.getMode().equals(ResultDataUtils.Device_Mode_Save_Power)) {
                bean.setModeName(getString(R.string.txt_location_mode_7));
            } else if (bean.getMode().equals(ResultDataUtils.Device_Mode_Sleep)) {
                bean.setModeName(getString(R.string.txt_location_mode_6));
            }
        }

        mPowerSavingShowLong = "";
        modeType = 0;
        modeValue = "";
        if (!TextUtils.isEmpty(deviceModeSetPutBean.getParams().getSmode_value())) {
            modeValue = deviceModeSetPutBean.getParams().getSmode_value();
        }
        if (deviceModeSetPutBean.getParams().getMode_type() != null) {
            modeType = deviceModeSetPutBean.getParams().getMode_type();
        }
        if (deviceMode.equals(ResultDataUtils.Device_Mode_Save_Power)) {
            for (LocationModeGetResultBean.SavePowerBean bean : savePowerBeans) {
                if (bean.getTime() == modeType) {
                    mPowerSavingShowLong = bean.getName();
                    break;
                }
            }
            if (!TextUtils.isEmpty(mPowerSavingShowLong)) {
                for (LocationModeBean bean : modeBeans) {
                    if (bean.getMode().equals(ResultDataUtils.Device_Mode_Save_Power)) {
                        bean.setModeName(bean.getModeName() + " (" + mPowerSavingShowLong + ")");
                        break;
                    }
                }
            }
        } else if (deviceMode.equals(ResultDataUtils.Device_Mode_Sleep)) {
            if (!TextUtils.isEmpty(modeValue)) {
                for (LocationModeBean bean : modeBeans) {
                    if (bean.getMode().equals(ResultDataUtils.Device_Mode_Sleep)) {
                        bean.setModeName(bean.getModeName() + " (" + modeValue + ")");
                        break;
                    }
                }
            }
        }

        mAdapter.notifyDataSetChanged();
    }

}
