package com.car.scth.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.SPUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.car.scth.R;
import com.car.scth.app.MyApplication;
import com.car.scth.di.component.DaggerAlarmSettingComponent;
import com.car.scth.mvp.contract.AlarmSettingContract;
import com.car.scth.mvp.model.api.ModuleValueService;
import com.car.scth.mvp.model.bean.DeviceConfigResultBean;
import com.car.scth.mvp.model.bean.ShakeValueBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.DeviceConfigPutBean;
import com.car.scth.mvp.model.putbean.DeviceConfigSetPutBean;
import com.car.scth.mvp.model.putbean.ModifyPasswordPutBean;
import com.car.scth.mvp.presenter.AlarmSettingPresenter;
import com.car.scth.mvp.ui.adapter.ShakeSensitivityAdapter;
import com.car.scth.mvp.utils.ConstantValue;
import com.car.scth.mvp.utils.ResultDataUtils;
import com.car.scth.mvp.utils.ToastUtils;
import com.car.scth.mvp.utils.Utils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 报警消息设置
 * <p>
 * Created by MVPArmsTemplate on 11/02/2020 16:07
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class AlarmSettingActivity extends BaseActivity<AlarmSettingPresenter> implements AlarmSettingContract.View {

    @BindView(R.id.iv_shake_alarm)
    ImageView ivShakeAlarm; // 震动报警
    @BindView(R.id.gridView_shake)
    GridView gridViewShake; // 震动报警
    @BindView(R.id.rl_shake_alarm)
    RelativeLayout rlShakeAlarm; // 震动报警
    @BindView(R.id.iv_speed_alarm)
    ImageView ivSpeedAlarm; // 超速报警
    @BindView(R.id.edt_speed)
    EditText edtSpeed; // 超速报警值
    @BindView(R.id.rl_over_speed_alarm)
    RelativeLayout rlOverSpeedAlarm; // 超速报警
    @BindView(R.id.iv_off_alarm)
    ImageView ivOffAlarm; // 脱落报警
    @BindView(R.id.rl_off_alarm)
    RelativeLayout rlOffAlarm; // 脱落报警
    @BindView(R.id.iv_low_power_alarm)
    ImageView ivLowPowerAlarm; // 低电报警
    @BindView(R.id.rl_low_power_alarm)
    RelativeLayout rlLowPowerAlarm; // 低电报警
    @BindView(R.id.iv_fence_alarm)
    ImageView ivFenceAlarm; // 进出围栏报警
    @BindView(R.id.rl_fence_alarm)
    RelativeLayout rlFenceAlarm; // 进出围栏报警
    @BindView(R.id.iv_sounds_witch)
    ImageView ivSoundsWitch; // 声音开关
    @BindView(R.id.rl_sounds_witch)
    RelativeLayout rlSoundsWitch; // 声音开关
    @BindView(R.id.iv_indicator_light_alarm)
    ImageView ivIndicatorLightAlarm; // 指示灯开关
    @BindView(R.id.rl_indicator_light_alarm)
    RelativeLayout rlIndicatorLightAlarm; // 指示灯开关
    @BindView(R.id.iv_offline_alarm)
    ImageView ivOfflineAlarm; // 离线报警
    @BindView(R.id.rl_offline_alarm)
    RelativeLayout rlOfflineAlarm; // 离线报警
    @BindView(R.id.iv_shutdown_alarm)
    ImageView ivShutdownAlarm; // 关机报警
    @BindView(R.id.rl_shutdown_alarm)
    RelativeLayout rlShutdownAlarm; // 关机报警
    @BindView(R.id.iv_reboot_alarm)
    ImageView ivRebootAlarm; // 启动报警
    @BindView(R.id.rl_reboot_alarm)
    RelativeLayout rlRebootAlarm; // 启动报警
    @BindView(R.id.btn_save)
    Button btnSave; // 保存
    @BindView(R.id.ll_speed_value)
    LinearLayout llSpeedValue; // 超速报警值
    @BindView(R.id.iv_push_message)
    ImageView ivPushMessage;// 推送开关

    private List<ShakeValueBean> shakeValueBeans;
    private ShakeSensitivityAdapter mAdapter;

    private String mSimei;
    private List<String> mSimeiBeas;

    // 获取到的报警开关信息
    private int mLowpower; // 低电报警,0关闭，1开启，-1不支持
    private int mIndicatorlight; // 指示灯 0关闭，1开启，-1不支持
    private int mSoundswitch; // 声音提示开关 0关闭，1开启，-1不支持
    private int mOpenswitch; // 开机报警 启动报警 0关闭，1开启，-1不支持
    private int mOffswitch; // 离线报警0关闭，1开启，-1不支持
    private int mCloseswitch; // 关机报警0关闭，1开启，-1不支持
    private int mDropalarm; // 脱落报警0关闭，1开启，-1不支持
    private int mFenceswitch; // 围栏开关 0关闭，1开启，-1不支持
    private int mShakealue; // 震动报警程度0-关闭,(18,28,48)开启，-1不支持,
    private int mSpeedValue; // 超速阈值 0关闭，>0开启，-1不支持

    // 用户设置的报警开关信息
    private int mLowpowerSet; // 低电报警,0关闭，1开启，-1不支持
    private int mIndicatorlightSet; // 指示灯 0关闭，1开启，-1不支持
    private int mSoundswitchSet; // 声音提示开关 0关闭，1开启，-1不支持
    private int mOpenswitchSet; // 开机报警 启动报警 0关闭，1开启，-1不支持
    private int mOffswitchSet; // 离线报警0关闭，1开启，-1不支持
    private int mCloseswitchSet; // 关机报警0关闭，1开启，-1不支持
    private int mDropalarmSet; // 脱落报警0关闭，1开启，-1不支持
    private int mFenceswitchSet; // 围栏开关 0关闭，1开启，-1不支持
    private int mShakealueSet; // 震动报警程度0-关闭,(18,28,48)开启，-1不支持,
    private int mSpeedValueSet; // 超速阈值 0关闭，>0开启，-1不支持
    private boolean isPushSwitch = false; // 极光推送开关

    public static Intent newInstance() {
        return new Intent(MyApplication.getMyApp(), AlarmSettingActivity.class);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAlarmSettingComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_alarm_setting; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_message_setting));
        shakeValueBeans = new ArrayList<>();
        mSimeiBeas = new ArrayList<>();
        mSimei = MyApplication.getMyApp().getSimei();
        isPushSwitch = SPUtils.getInstance().getBoolean(ConstantValue.Push_Switch, false);
        shakeValueBeans.add(new ShakeValueBean(18, getString(R.string.txt_higher), false));
        shakeValueBeans.add(new ShakeValueBean(28, getString(R.string.txt_medium), false));
        shakeValueBeans.add(new ShakeValueBean(48, getString(R.string.txt_inferior), false));
        mAdapter = new ShakeSensitivityAdapter(this, R.layout.item_shake_sensitivity, shakeValueBeans);
        gridViewShake.setAdapter(mAdapter);
        gridViewShake.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (ShakeValueBean bean : shakeValueBeans) {
                    bean.setSelect(false);
                }
                shakeValueBeans.get(position).setSelect(true);
                mAdapter.notifyDataSetChanged();
                mShakealueSet = shakeValueBeans.get(position).getSensitivity();
            }
        });

        if (!TextUtils.isEmpty(mSimei)){
            mSimeiBeas.add(mSimei);
        }
        onShowPushSwitch();
        getDeviceConfig();
    }

    /**
     * 获取设备的配置信息，支持的功能等
     */
    private void getDeviceConfig() {
        DeviceConfigPutBean.ParamsBean paramsBean = new DeviceConfigPutBean.ParamsBean();
        paramsBean.setSimei(mSimei);
        paramsBean.setType(ResultDataUtils.Config_Alarm);

        DeviceConfigPutBean bean = new DeviceConfigPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Config_Get);
        bean.setModule(ModuleValueService.Module_For_Config_Get);

        if (getPresenter() != null) {
            getPresenter().getDeviceConfig(bean);
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

    @OnClick({R.id.iv_push_message, R.id.iv_shake_alarm, R.id.iv_speed_alarm, R.id.iv_off_alarm, R.id.iv_low_power_alarm, R.id.iv_fence_alarm, R.id.iv_sounds_witch,
            R.id.iv_indicator_light_alarm, R.id.iv_offline_alarm, R.id.iv_shutdown_alarm, R.id.iv_reboot_alarm,
            R.id.btn_save})
    public void onViewClicked(View view) {
        if (Utils.isButtonQuickClick(System.currentTimeMillis())){
            switch (view.getId()) {
                case R.id.iv_push_message: // 推送开关
                    onPushSwitchSet();
                    break;
                case R.id.iv_shake_alarm: // 震动报警
                    setShakeValue();
                    break;
                case R.id.iv_speed_alarm: // 超速报警
                    setSpeedValue();
                    break;
                case R.id.iv_off_alarm: // 脱落报警
                    setOffAlarmValue();
                    break;
                case R.id.iv_low_power_alarm: // 低电报警
                    setLowPowerAlarmValue();
                    break;
                case R.id.iv_fence_alarm: // 进出围栏报警
                    setFenceAlarmValue();
                    break;
                case R.id.iv_sounds_witch: // 声音开关
                    setSoundsWitchValue();
                    break;
                case R.id.iv_indicator_light_alarm: // 指示灯开关
                    setIndicatorLightAlarm();
                    break;
                case R.id.iv_offline_alarm: // 离线报警
                    setOfflineAlarmValue();
                    break;
                case R.id.iv_shutdown_alarm: // 关机报警
                    setShutdownAlarmValue();
                    break;
                case R.id.iv_reboot_alarm: // 启动报警
                    setStartAlarmValue();
                    break;
                case R.id.btn_save: // 保存
                    onSetAlarmSave();
                    break;
            }
        }
    }

    /**
     * 极光推送开关状态
     */
    private void onShowPushSwitch() {
        ivPushMessage.setImageResource(isPushSwitch ? R.mipmap.icon_on_message : R.mipmap.icon_off_message);
    }


    /**
     * 保存报警消息设置
     */
    private void onSetAlarmSave() {
        String speed = edtSpeed.getText().toString().trim();
        if (TextUtils.isEmpty(speed)) {
            mSpeedValueSet = 0;
        } else {
            mSpeedValueSet = Integer.parseInt(speed);
        }

        DeviceConfigSetPutBean.ParamsBean.ConfigBean.CarSwitchBean carSwitchBean = new DeviceConfigSetPutBean.ParamsBean.ConfigBean.CarSwitchBean();
        if (mLowpowerSet != mLowpower) {
            carSwitchBean.setLowpower(mLowpowerSet);
        }
        if (mIndicatorlightSet != mIndicatorlight) {
            carSwitchBean.setIndicatorlight(mIndicatorlightSet);
        }
        if (mSoundswitchSet != mSoundswitch) {
            carSwitchBean.setSoundswitch(mSoundswitchSet);
        }
        if (mOpenswitchSet != mOpenswitch) {
            carSwitchBean.setOpenswitch(mOpenswitchSet);
        }
        if (mOffswitchSet != mOffswitch) {
            carSwitchBean.setOffswitch(mOffswitchSet);
        }
        if (mCloseswitchSet != mCloseswitch) {
            carSwitchBean.setCloseswitch(mCloseswitchSet);
        }
        if (mDropalarmSet != mDropalarm) {
            carSwitchBean.setDropalarm(mDropalarmSet);
        }
        if (mFenceswitchSet != mFenceswitch) {
            carSwitchBean.setFenceswitch(mFenceswitchSet);
        }

        DeviceConfigSetPutBean.ParamsBean.ConfigBean configBean = new DeviceConfigSetPutBean.ParamsBean.ConfigBean();
        configBean.setCar_switch(carSwitchBean);
        if (mShakealueSet != mShakealue) {
            configBean.setShake_value(mShakealueSet);
        }
        if (rlOverSpeedAlarm.getVisibility() == View.VISIBLE) {
            if (mSpeedValueSet != mSpeedValue) {
                configBean.setSpeed_value(mSpeedValueSet);
                if (mSpeedValueSet > 0){
                    if (mSpeedValueSet < 10 || mSpeedValueSet > 190) {
                        ToastUtils.show(getString(R.string.txt_over_speed_alarm_hint));
                        return;
                    }
                }
            }
        }

        DeviceConfigSetPutBean.ParamsBean paramsBean = new DeviceConfigSetPutBean.ParamsBean();
        paramsBean.setConfig(configBean);
        paramsBean.setSimei(mSimeiBeas);

        DeviceConfigSetPutBean bean = new DeviceConfigSetPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Config_Set);
        bean.setModule(ModuleValueService.Module_For_Config_Set);

        if (getPresenter() != null) {
            getPresenter().setDeviceConfig(bean);
        }
    }

    /**
     * 消息推送开关
     */
    private void onPushSwitchSet() {
        ModifyPasswordPutBean.ParamsBean paramsBean = new ModifyPasswordPutBean.ParamsBean();
        paramsBean.setPush_switch(!isPushSwitch);

        ModifyPasswordPutBean bean = new ModifyPasswordPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Set_Account);
        bean.setModule(ModuleValueService.Module_For_Set_Account);

        if (getPresenter() != null) {
            getPresenter().submitPushSwitch(bean, true);
        }
    }
    /**
     * 设置震动报警
     */
    private void setShakeValue() {
        //先复原都未选状态
        for (ShakeValueBean bean : shakeValueBeans) {
            bean.setSelect(false);
        }

        if (mShakealueSet > 0) {
            mShakealueSet = 0;
        } else {
            mShakealueSet = 28;
        }
        for (ShakeValueBean bean : shakeValueBeans) {
            if (bean.getSensitivity() == mShakealueSet) {
                bean.setSelect(true);
            }
        }
        mAdapter.notifyDataSetChanged();
        ivShakeAlarm.setImageResource(mShakealueSet > 0 ? R.mipmap.icon_on_message : R.mipmap.icon_off_message);
        gridViewShake.setVisibility(mShakealueSet > 0 ? View.VISIBLE : View.INVISIBLE);
    }

    /**
     * 设置超速报警
     */
    private void setSpeedValue() {
        String speed = edtSpeed.getText().toString().trim();
        if (TextUtils.isEmpty(speed)) {
            mSpeedValueSet = 0;
        } else {
            mSpeedValueSet = Integer.parseInt(speed);
        }
        if (mSpeedValueSet > 0) {
            mSpeedValueSet = 0;
        } else {
            mSpeedValueSet = 120;
        }
        if (mSpeedValueSet > 0) {
            edtSpeed.setText(String.valueOf(mSpeedValueSet));
        } else {
            edtSpeed.setText("");
        }
        ivSpeedAlarm.setImageResource(mSpeedValueSet > 0 ? R.mipmap.icon_on_message : R.mipmap.icon_off_message);
        llSpeedValue.setVisibility(mSpeedValueSet > 0 ? View.VISIBLE : View.INVISIBLE);
    }

    /**
     * 设置脱落报警
     */
    private void setOffAlarmValue() {
        if (mDropalarmSet > 0) {
            mDropalarmSet = 0;
        } else {
            mDropalarmSet = 1;
        }
        ivOffAlarm.setImageResource(mDropalarmSet > 0 ? R.mipmap.icon_on_message : R.mipmap.icon_off_message);
    }

    /**
     * 设置低电报警
     */
    private void setLowPowerAlarmValue() {
        if (mLowpowerSet > 0) {
            mLowpowerSet = 0;
        } else {
            mLowpowerSet = 1;
        }
        ivLowPowerAlarm.setImageResource(mLowpowerSet > 0 ? R.mipmap.icon_on_message : R.mipmap.icon_off_message);
    }

    /**
     * 设置进出围栏报警
     */
    private void setFenceAlarmValue() {
        if (mFenceswitchSet > 0) {
            mFenceswitchSet = 0;
        } else {
            mFenceswitchSet = 1;
        }
        ivFenceAlarm.setImageResource(mFenceswitchSet > 0 ? R.mipmap.icon_on_message : R.mipmap.icon_off_message);
    }

    /**
     * 设置声音开关报警
     */
    private void setSoundsWitchValue() {
        if (mSoundswitchSet > 0) {
            mSoundswitchSet = 0;
        } else {
            mSoundswitchSet = 1;
        }
        ivSoundsWitch.setImageResource(mSoundswitchSet > 0 ? R.mipmap.icon_on_message : R.mipmap.icon_off_message);
    }

    /**
     * 设置指示灯
     */
    private void setIndicatorLightAlarm() {
        if (mIndicatorlightSet > 0) {
            mIndicatorlightSet = 0;
        } else {
            mIndicatorlightSet = 1;
        }
        ivIndicatorLightAlarm.setImageResource(mIndicatorlightSet > 0 ? R.mipmap.icon_on_message : R.mipmap.icon_off_message);
    }

    /**
     * 设置离线报警
     */
    private void setOfflineAlarmValue() {
        if (mOffswitchSet > 0) {
            mOffswitchSet = 0;
        } else {
            mOffswitchSet = 1;
        }
        ivOfflineAlarm.setImageResource(mOffswitchSet > 0 ? R.mipmap.icon_on_message : R.mipmap.icon_off_message);
    }

    /**
     * 设置关机报警
     */
    private void setShutdownAlarmValue() {
        if (mCloseswitchSet > 0) {
            mCloseswitchSet = 0;
        } else {
            mCloseswitchSet = 1;
        }
        ivShutdownAlarm.setImageResource(mCloseswitchSet > 0 ? R.mipmap.icon_on_message : R.mipmap.icon_off_message);
    }

    /**
     * 设置开机报警
     */
    private void setStartAlarmValue() {
        if (mOpenswitchSet > 0) {
            mOpenswitchSet = 0;
        } else {
            mOpenswitchSet = 1;
        }
        ivRebootAlarm.setImageResource(mOpenswitchSet > 0 ? R.mipmap.icon_on_message : R.mipmap.icon_off_message);
    }

    @Override
    public void getDeviceConfigSuccess(DeviceConfigResultBean deviceConfigResultBean) {
        rlShakeAlarm.setVisibility(deviceConfigResultBean.getConfig().getShake_value() == -1 ? View.GONE : View.VISIBLE);
        // 超速报警
        rlOverSpeedAlarm.setVisibility(deviceConfigResultBean.getConfig().getSpeed_value() == -1 ? View.GONE : View.VISIBLE);
        // 脱落报警
        rlOffAlarm.setVisibility(deviceConfigResultBean.getConfig().getCar_switch().getDropalarm() == -1 ? View.GONE : View.VISIBLE);
        // 低电报警
        rlLowPowerAlarm.setVisibility(deviceConfigResultBean.getConfig().getCar_switch().getLowpower() == -1 ? View.GONE : View.VISIBLE);
        // 进出围栏报警
        rlFenceAlarm.setVisibility(deviceConfigResultBean.getConfig().getCar_switch().getFenceswitch() == -1 ? View.GONE : View.VISIBLE);
        // 声音开关
        rlSoundsWitch.setVisibility(deviceConfigResultBean.getConfig().getCar_switch().getSoundswitch() == -1 ? View.GONE : View.VISIBLE);
        // 指示灯开关
        rlIndicatorLightAlarm.setVisibility(deviceConfigResultBean.getConfig().getCar_switch().getIndicatorlight() == -1 ? View.GONE : View.VISIBLE);
        // 离线报警
        rlOfflineAlarm.setVisibility(deviceConfigResultBean.getConfig().getCar_switch().getOffswitch() == -1 ? View.GONE : View.VISIBLE);
        // 关机报警
        rlShutdownAlarm.setVisibility(deviceConfigResultBean.getConfig().getCar_switch().getCloseswitch() == -1 ? View.GONE : View.VISIBLE);
        // 开机报警
//        rlStartAlarm.setVisibility(deviceConfigResultBean.getConfig().getCar_switch().getOpenswitch() == -1 ? View.GONE : View.VISIBLE);
        // 启动报警
        rlRebootAlarm.setVisibility(deviceConfigResultBean.getConfig().getCar_switch().getOpenswitch() == -1 ? View.GONE : View.VISIBLE);

        mLowpower = deviceConfigResultBean.getConfig().getCar_switch().getLowpower(); // 低电报警,0关闭，1开启，-1不支持
        mIndicatorlight = deviceConfigResultBean.getConfig().getCar_switch().getIndicatorlight(); // 指示灯 0关闭，1开启，-1不支持
        mSoundswitch = deviceConfigResultBean.getConfig().getCar_switch().getSoundswitch(); // 声音提示开关 0关闭，1开启，-1不支持
        mOpenswitch = deviceConfigResultBean.getConfig().getCar_switch().getOpenswitch(); // 开机报警 启动报警 0关闭，1开启，-1不支持
        mOffswitch = deviceConfigResultBean.getConfig().getCar_switch().getOffswitch(); // 离线报警0关闭，1开启，-1不支持
        mCloseswitch = deviceConfigResultBean.getConfig().getCar_switch().getCloseswitch(); // 关机报警0关闭，1开启，-1不支持
        mDropalarm = deviceConfigResultBean.getConfig().getCar_switch().getDropalarm(); // 脱落报警0关闭，1开启，-1不支持
        mFenceswitch = deviceConfigResultBean.getConfig().getCar_switch().getFenceswitch(); // 围栏开关 0关闭，1开启，-1不支持
        mShakealue = deviceConfigResultBean.getConfig().getShake_value(); // 震动报警程度0-关闭,(18,28,48)开启，-1不支持,
        mSpeedValue = deviceConfigResultBean.getConfig().getSpeed_value(); // 超速阈值 0关闭，>0开启，-1不支持

        mLowpowerSet = mLowpower;
        mIndicatorlightSet = mIndicatorlight;
        mSoundswitchSet = mSoundswitch;
        mOpenswitchSet = mOpenswitch;
        mOffswitchSet = mOffswitch;
        mCloseswitchSet = mCloseswitch;
        mDropalarmSet = mDropalarm;
        mFenceswitchSet = mFenceswitch;
        mShakealueSet = mShakealue;
        mSpeedValueSet = mSpeedValue;

        onUpdateAlarmUI();
    }

    @Override
    public void setDeviceConfigSuccess(BaseBean baseBean) {
        ToastUtils.show(getString(R.string.txt_set_success));
        mLowpower = mLowpowerSet;
        mIndicatorlight = mIndicatorlightSet;
        mSoundswitch = mSoundswitchSet;
        mOpenswitch = mOpenswitchSet;
        mOffswitch = mOffswitchSet;
        mCloseswitch = mCloseswitchSet;
        mDropalarm = mDropalarmSet;
        mFenceswitch = mFenceswitchSet;
        mShakealue = mShakealueSet;
        mSpeedValue = mSpeedValueSet;

        onUpdateAlarmUI();
    }

    @Override
    public void submitPushSwitchSuccess(boolean pushSwitch, boolean isSetPushSwitch) {
        ToastUtils.show(getString(R.string.txt_set_success));
        if (isSetPushSwitch){
            isPushSwitch = pushSwitch;
            SPUtils.getInstance().put(ConstantValue.Push_Switch, isPushSwitch);
            onShowPushSwitch();
        }
    }

    /**
     * 更新UI
     */
    @SuppressLint("SetTextI18n")
    private void onUpdateAlarmUI() {
        // 震动报警
        for (ShakeValueBean bean : shakeValueBeans) {
            bean.setSelect(false);
        }
        ivShakeAlarm.setImageResource(mShakealue > 0 ? R.mipmap.icon_on_message : R.mipmap.icon_off_message);
        gridViewShake.setVisibility(mShakealueSet > 0 ? View.VISIBLE : View.INVISIBLE);
        if (mShakealue > 0) {
            for (ShakeValueBean bean : shakeValueBeans) {
                if (bean.getSensitivity() == mShakealue) {
                    bean.setSelect(true);
                }
            }
        }
        mAdapter.notifyDataSetChanged();
        // 超速报警
        ivSpeedAlarm.setImageResource(mSpeedValue > 0 ? R.mipmap.icon_on_message : R.mipmap.icon_off_message);
        llSpeedValue.setVisibility(mSpeedValueSet > 0 ? View.VISIBLE : View.INVISIBLE);
        if (mSpeedValue != -1) {
            if (mSpeedValue > 0) {
                edtSpeed.setText(String.valueOf(mSpeedValue));
            }
        }
        // 脱落报警
        ivOffAlarm.setImageResource(mDropalarm > 0 ? R.mipmap.icon_on_message : R.mipmap.icon_off_message);
        // 低电报警
        ivLowPowerAlarm.setImageResource(mLowpower > 0 ? R.mipmap.icon_on_message : R.mipmap.icon_off_message);
        // 进出围栏报警
        ivFenceAlarm.setImageResource(mFenceswitch > 0 ? R.mipmap.icon_on_message : R.mipmap.icon_off_message);
        // 声音开关
        ivSoundsWitch.setImageResource(mSoundswitch > 0 ? R.mipmap.icon_on_message : R.mipmap.icon_off_message);
        // 指示灯开关
        ivIndicatorLightAlarm.setImageResource(mIndicatorlight > 0 ? R.mipmap.icon_on_message : R.mipmap.icon_off_message);
        // 离线报警
        ivOfflineAlarm.setImageResource(mOffswitch > 0 ? R.mipmap.icon_on_message : R.mipmap.icon_off_message);
        // 关机报警
        ivShutdownAlarm.setImageResource(mCloseswitch > 0 ? R.mipmap.icon_on_message : R.mipmap.icon_off_message);
        // 启动报警
        ivRebootAlarm.setImageResource(mOpenswitch > 0 ? R.mipmap.icon_on_message : R.mipmap.icon_off_message);
    }

}
