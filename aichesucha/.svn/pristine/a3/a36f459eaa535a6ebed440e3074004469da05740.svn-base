package com.car.scth.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.car.scth.R;
import com.car.scth.app.MyApplication;
import com.car.scth.di.component.DaggerRemoteSwitchComponent;
import com.car.scth.mvp.contract.RemoteSwitchContract;
import com.car.scth.mvp.model.api.ModuleValueService;
import com.car.scth.mvp.model.bean.AlertBean;
import com.car.scth.mvp.model.bean.RemoteSwitchResultBean;
import com.car.scth.mvp.model.bean.TimeSwitchGetResultBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.RemoteSwitchSetPutBean;
import com.car.scth.mvp.model.putbean.TimeSwitchDeletePutBean;
import com.car.scth.mvp.model.putbean.TimeSwitchGetPutBean;
import com.car.scth.mvp.model.putbean.TimeSwitchSetPutBean;
import com.car.scth.mvp.presenter.RemoteSwitchPresenter;
import com.car.scth.mvp.utils.ResultDataUtils;
import com.car.scth.mvp.utils.ToastUtils;
import com.car.scth.mvp.weiget.AlertAppDialog;
import com.car.scth.mvp.weiget.TimeSwitchDataDialog;
import com.car.scth.mvp.weiget.TimeSwitchSelectDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 远程开关
 * <p>
 * Created by MVPArmsTemplate on 10/31/2020 17:40
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class RemoteSwitchActivity extends BaseActivity<RemoteSwitchPresenter> implements RemoteSwitchContract.View {

    @BindView(R.id.btn_boot_up)
    Button btnBootUp; // 远程开关-开机
    @BindView(R.id.btn_shutdown)
    Button btnShutdown; // 远程开关-关机
    @BindView(R.id.iv_on_off_timer)
    ImageView ivOnOffTimer; // 定时开关机
    @BindView(R.id.iv_every_day)
    ImageView ivEveryDay; // 每天
    @BindView(R.id.tv_every_day)
    TextView tvEveryDay; // 每天
    @BindView(R.id.iv_every_month)
    ImageView ivEveryMonth; // 每月
    @BindView(R.id.tv_every_month)
    TextView tvEveryMonth; // 每月
    @BindView(R.id.iv_current_month)
    ImageView ivCurrentMonth; // 当月
    @BindView(R.id.tv_current_month)
    TextView tvCurrentMonth; // 当月
    @BindView(R.id.tv_boot_time)
    TextView tvBootTime; // 开机时间
    @BindView(R.id.tv_shutdown_time)
    TextView tvShutdownTime; // 关机时间
    @BindView(R.id.btn_save)
    Button btnSave; // 定时开关机-保存
    @BindView(R.id.tv_data)
    TextView tvData; // 日期选择
    @BindView(R.id.ll_data)
    LinearLayout llData;
    @BindView(R.id.tv_data_hint)
    TextView tvDataHint; // 日期选择提示
    @BindView(R.id.ll_remote_switch)
    LinearLayout llRemoteSwitch; // 远程开关机布局
    @BindView(R.id.ll_time_switch)
    LinearLayout llTimeSwitch; // 定时开关机布局
    @BindView(R.id.rl_time_switch)
    RelativeLayout rlTimeSwitch; // 定时开关机底部布局
    @BindView(R.id.btn_save_off)
    Button btnSaveOff; // 保存按钮-定时开关机
    @BindView(R.id.ll_off_time)
    LinearLayout llOffTime; // 保存按钮-定时开关机

    private final static String Key_Remote_Switch = "RemoteSwitchActivity.remoteSwitch";
    private final static String Key_Time_Switch = "RemoteSwitchActivity.onTimeSwitch";

    private String mSimei;
    private String timeSwitchType; // 定时开关机日期类型
    private boolean isOpen = false; // true：定时开机，false：定时关机
    private boolean isAdd = true; // 是否是添加新的定时开关机
    private boolean switch_open = false;//远程开关网络状态
    private String mStrData = ""; // 已经选择的日期 - 展示使用
    private List<Integer> mDatas; // 已经选择的日期
    private List<TimeSwitchSetPutBean.ParamsBean.TimerBean> timerBeans; // 定时开关机时间
    // 用于对比开机时间和关机时间是否相同
    private int openHour = 0; // 开机时间 - 小时
    private int openMin = 0; // 开机时间 - 分钟
    private int closeHour = 0; // 关机时间 - 小时
    private int closeMin = 0; // 关机时间 - 分钟

    private int remoteSwitch; // 是否可远程开关机, -1不支持,1支持,0支持，但是不可用
    private int onTimeSwitch; // 是否有定时开关机功能, 0不支持,1支持

    public static Intent newInstance(int remoteswitch, int timeSwitch) {
        Intent intent = new Intent(MyApplication.getMyApp(), RemoteSwitchActivity.class);
        intent.putExtra(Key_Remote_Switch, remoteswitch);
        intent.putExtra(Key_Time_Switch, timeSwitch);
        return intent;
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRemoteSwitchComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_remote_switch; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_function_remote_switch));
        mSimei = MyApplication.getMyApp().getSimei();
        mDatas = new ArrayList<>();
        timerBeans = new ArrayList<>();
        remoteSwitch = getIntent().getIntExtra(Key_Remote_Switch, -1);
        onTimeSwitch = getIntent().getIntExtra(Key_Time_Switch, 0);

        if (remoteSwitch == -1) {
            llRemoteSwitch.setVisibility(View.GONE);
        } else {
            llRemoteSwitch.setVisibility(View.VISIBLE);
        }
        if (onTimeSwitch == 1) {
            llTimeSwitch.setVisibility(View.VISIBLE);
        } else {
            llTimeSwitch.setVisibility(View.GONE);
        }

        onTimeSwitchTypeShow(ResultDataUtils.Time_Switch_Every_Day);


        getTimeSwitch();
    }

    /**
     * 获取定时开关机数据
     */
    private void getTimeSwitch() {
        TimeSwitchGetPutBean.ParamsBean paramsBean = new TimeSwitchGetPutBean.ParamsBean();
        paramsBean.setSimei(mSimei);

        TimeSwitchGetPutBean bean = new TimeSwitchGetPutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Time_Switch_Get);
        bean.setModule(ModuleValueService.Module_For_Time_Switch_Get);
        bean.setParams(paramsBean);

        if (getPresenter() != null) {
            getPresenter().getTimeSwitch(bean);
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

    @OnClick({R.id.btn_boot_up, R.id.btn_shutdown, R.id.iv_on_off_timer, R.id.iv_every_day, R.id.tv_every_day, R.id.iv_every_month, R.id.tv_every_month,
            R.id.iv_current_month, R.id.tv_current_month, R.id.tv_boot_time, R.id.tv_shutdown_time, R.id.btn_save, R.id.ll_data, R.id.btn_save_off})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_boot_up: // 远程开关-开机
                onRemoteSwitchConfirm(ResultDataUtils.Remote_Switch_Open);
                break;
            case R.id.btn_shutdown: // 远程开关-关机
                onRemoteSwitchConfirm(ResultDataUtils.Remote_Switch_Close);
                break;
            case R.id.iv_on_off_timer: // 定时开关
                isOpen = !isOpen;
                onTimeSwitchShow();
                if (isOpen) {
                    onTimeSwitchTypeShow(ResultDataUtils.Time_Switch_Every_Day);
                }else {
                    //CHANGE_DO
                    if(switch_open) {
                        onOpenOrCloseConfirm();
                    }
                }
                break;
            case R.id.iv_every_day: // 每天
            case R.id.tv_every_day: // 每天
                onTimeSwitchTypeShow(ResultDataUtils.Time_Switch_Every_Day);
                break;
            case R.id.iv_every_month: // 每月
            case R.id.tv_every_month: // 每月
                onTimeSwitchTypeShow(ResultDataUtils.Time_Switch_Every_Month);
                break;
            case R.id.iv_current_month: // 当月
            case R.id.tv_current_month: // 当月
                onTimeSwitchTypeShow(ResultDataUtils.Time_Switch_Current_Month);
                break;
            case R.id.tv_boot_time: // 开机时间
                onSelectTime(1);
                break;
            case R.id.tv_shutdown_time: // 关机时间
                onSelectTime(2);
                break;
            case R.id.btn_save_off:
            case R.id.btn_save: // 保存
                onOpenOrCloseConfirm();
                break;
            case R.id.ll_data: // 日期选择
                onSelectDatas();
                break;
        }
    }

    /**
     * 确认开机或关机
     */
    private void onOpenOrCloseConfirm() {
        AlertBean bean = new AlertBean();
        bean.setType(0);
        if (isOpen) {
            bean.setAlert(getString(R.string.txt_open_switch_hint));
        } else {
            bean.setAlert(getString(R.string.txt_close_switch_hint));
        }
        AlertAppDialog dialog = new AlertAppDialog();
        dialog.show(getSupportFragmentManager(), bean, new AlertAppDialog.onAlertDialogChange() {
            @Override
            public void onConfirm() {
                submitTimeSwitch();
            }

            @Override
            public void onCancel() {
                if(switch_open) {
                    isOpen = !isOpen;
                    onTimeSwitchShow();
                    if (isOpen) {
                        onTimeSwitchTypeShow(ResultDataUtils.Time_Switch_Every_Day);
                    }
                }
            }
        });
    }

    /**
     * 提交开启 or 关闭 定时开关机
     */
    private void submitTimeSwitch() {
        if (isOpen) {
            if (!timeSwitchType.equals(ResultDataUtils.Time_Switch_Every_Day)) {
                if (mDatas.size() == 0) {
                    ToastUtils.show(getString(R.string.txt_select_day));
                    return;
                }
            }
            if (tvBootTime.getText().toString().equals(tvShutdownTime.getText().toString())) {
                ToastUtils.show(getString(R.string.txt_time_start_and_the_same_error));
                return;
            }
            timerBeans.clear();
            TimeSwitchSetPutBean.ParamsBean.TimerBean timerBean = new TimeSwitchSetPutBean.ParamsBean.TimerBean();
            timerBean.setStart(tvBootTime.getText().toString());
            timerBean.setClose(tvShutdownTime.getText().toString());
            timerBeans.add(timerBean);

            TimeSwitchSetPutBean.ParamsBean paramsBean = new TimeSwitchSetPutBean.ParamsBean();
            paramsBean.setTimer(timerBeans);
            if (!timeSwitchType.equals(ResultDataUtils.Time_Switch_Every_Day)) {
                paramsBean.setLoop_days(mDatas);
            }
            paramsBean.setLoc_type(timeSwitchType);
            paramsBean.setSimei(mSimei);

            TimeSwitchSetPutBean bean = new TimeSwitchSetPutBean();
            bean.setFunc(ModuleValueService.Fuc_For_Time_Switch_Set);
            bean.setModule(ModuleValueService.Module_For_Time_Switch_Set);
            bean.setParams(paramsBean);

            if (getPresenter() != null) {
                getPresenter().submitTimeSwitch(bean);
            }
        } else {
            TimeSwitchDeletePutBean.ParamsBean paramsBean = new TimeSwitchDeletePutBean.ParamsBean();
            paramsBean.setSimei(mSimei);

            TimeSwitchDeletePutBean bean = new TimeSwitchDeletePutBean();
            bean.setFunc(ModuleValueService.Fuc_For_Time_Switch_Delete);
            bean.setModule(ModuleValueService.Module_For_Time_Switch_Delete);
            bean.setParams(paramsBean);

            if (getPresenter() != null) {
                getPresenter().submitTimeSwitchDelete(bean);
            }
        }
    }

    /**
     * 选择日期
     */
    private void onSelectDatas() {
        TimeSwitchDataDialog dialog = new TimeSwitchDataDialog();
        dialog.show(getSupportFragmentManager(), timeSwitchType, mDatas, new TimeSwitchDataDialog.onTimeSwitchDataChange() {
            @Override
            public void onDataSelect(List<com.car.scth.mvp.ui.view.data.haibin.Calendar> calendarList) {
                onSetDataForSelectData(calendarList);
            }
        });
    }

    /**
     * 开始整合日期数据
     *
     * @param calendarList
     */
    private void onSetDataForSelectData(List<com.car.scth.mvp.ui.view.data.haibin.Calendar> calendarList) {
        mStrData = "";
        mDatas.clear();
        for (com.car.scth.mvp.ui.view.data.haibin.Calendar item : calendarList) {
            mDatas.add(item.getDay());
            if (TextUtils.isEmpty(mStrData)) {
                mStrData = item.getDay() + "";
            } else {
                mStrData = mStrData + "," + item.getDay();
            }
        }
        tvData.setText(mStrData);
    }

    /**
     * 选择时间
     *
     * @param type 1：选择开机时间，2：选择关机时间
     */
    private void onSelectTime(int type) {
        String time;
        int hour;
        int min;
        if (type == 1) {
            time = tvBootTime.getText().toString();
            hour = openHour;
            min = openMin;
        } else {
            time = tvShutdownTime.getText().toString();
            hour = closeHour;
            min = closeMin;
        }
        TimeSwitchSelectDialog dialog = new TimeSwitchSelectDialog();
        dialog.show(getSupportFragmentManager(), type, time, hour, min, new TimeSwitchSelectDialog.onTimeSwitchSelectChange() {
            @Override
            public void onTimeSelect(int type, String time, int hour, int min) {
                if (type == 1) {
                    tvBootTime.setText(time);
                    openHour = hour;
                    openMin = min;
                } else {
                    tvShutdownTime.setText(time);
                    closeHour = hour;
                    closeMin = min;
                }
            }
        });
    }

    /**
     * 定时开关机类型切换显示
     *
     * @param type e_loctype_eachday | e_loctype_eachmonth | e_loctype_currentmonth
     */
    private void onTimeSwitchTypeShow(String type) {
        timeSwitchType = type;
        ivEveryDay.setImageResource(R.mipmap.icon_unselected_small);
        ivEveryMonth.setImageResource(R.mipmap.icon_unselected_small);
        ivCurrentMonth.setImageResource(R.mipmap.icon_unselected_small);
        mStrData = "";
        mDatas.clear();
        switch (type) {
            case ResultDataUtils.Time_Switch_Every_Day:
                ivEveryDay.setImageResource(R.mipmap.icon_select_small);
                llData.setVisibility(View.GONE);
                break;
            case ResultDataUtils.Time_Switch_Every_Month:
                ivEveryMonth.setImageResource(R.mipmap.icon_select_small);
                llData.setVisibility(View.VISIBLE);
                tvDataHint.setText(getString(R.string.txt_every_month_data));
                tvData.setText(mStrData);
                break;
            case ResultDataUtils.Time_Switch_Current_Month:
                ivCurrentMonth.setImageResource(R.mipmap.icon_select_small);
                llData.setVisibility(View.VISIBLE);
                tvDataHint.setText(getString(R.string.txt_current_month_data));
                tvData.setText(mStrData);
                break;
        }
    }

    /**
     * 定时开关机-开关显示
     */
    private void onTimeSwitchShow() {
        ivOnOffTimer.setImageResource(isOpen ? R.mipmap.icon_on_message : R.mipmap.icon_off_message);
        rlTimeSwitch.setVisibility(isOpen ? View.VISIBLE : View.GONE);
//        llOffTime.setVisibility(isOpen ? View.GONE : View.VISIBLE);
    }

    /**
     * 远程开关机确认
     *
     * @param remoteSwitch 开机or关机
     */
    private void onRemoteSwitchConfirm(String remoteSwitch) {
        AlertBean bean = new AlertBean();
        bean.setTitle(getString(R.string.txt_tip));
        if (remoteSwitch.equals(ResultDataUtils.Remote_Switch_Open)) {
            bean.setAlert(getString(R.string.txt_open_confirm));
        } else {
            bean.setAlert(getString(R.string.txt_close_confirm));
        }
        bean.setType(0);
        AlertAppDialog dialog = new AlertAppDialog();
        dialog.show(getSupportFragmentManager(), bean, new AlertAppDialog.onAlertDialogChange() {
            @Override
            public void onConfirm() {
                submitRemoteSwitch(remoteSwitch);
            }

            @Override
            public void onCancel() {

            }
        });
    }

    /**
     * 远程开关机提交
     *
     * @param remoteSwitch 开机or关机
     */
    private void submitRemoteSwitch(String remoteSwitch) {
        RemoteSwitchSetPutBean.ParamsBean paramsBean = new RemoteSwitchSetPutBean.ParamsBean();
        paramsBean.setSimei(mSimei);
        paramsBean.setState(remoteSwitch);

        RemoteSwitchSetPutBean bean = new RemoteSwitchSetPutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Remote_Switch_Set);
        bean.setModule(ModuleValueService.Module_For_Remote_Switch_Set);
        bean.setParams(paramsBean);

        if (getPresenter() != null) {
            getPresenter().submitRemoteSwitch(bean);
        }
    }

    @Override
    public void submitRemoteSwitchSuccess(RemoteSwitchResultBean remoteSwitchResultBean) {
        ToastUtils.show(getString(R.string.txt_set_success));
    }

    @Override
    public void getTimeSwitchSuccess(TimeSwitchGetResultBean timeSwitchGetResultBean) {
        mDatas.clear();
        mStrData = "";
        if (!TextUtils.isEmpty(timeSwitchGetResultBean.getLoc_type())
                && !timeSwitchGetResultBean.getLoc_type().equals(ResultDataUtils.Time_Switch_Type_Close)) {
            onTimeSwitchTypeShow(timeSwitchGetResultBean.getLoc_type());
            isAdd = false;
            isOpen = true;
            switch_open = true;
            if (timeSwitchGetResultBean.getLoop_days() != null) {
                for (int i = 0; i < timeSwitchGetResultBean.getLoop_days().size(); i++) {
                    mDatas.add(timeSwitchGetResultBean.getLoop_days().get(i));
                    if (TextUtils.isEmpty(mStrData)) {
                        mStrData = timeSwitchGetResultBean.getLoop_days().get(i) + "";
                    } else {
                        mStrData = mStrData + "," + timeSwitchGetResultBean.getLoop_days().get(i);
                    }
                }
            }
            tvData.setText(mStrData);

            if (timeSwitchGetResultBean.getTimer() != null && timeSwitchGetResultBean.getTimer().size() > 0) {
                tvBootTime.setText(timeSwitchGetResultBean.getTimer().get(0).getStart());
                tvShutdownTime.setText(timeSwitchGetResultBean.getTimer().get(0).getClose());

                String[] strStart = timeSwitchGetResultBean.getTimer().get(0).getStart().split(":");
                openHour = Integer.parseInt(strStart[0]);
                openMin = Integer.parseInt(strStart[1]);

                String[] strEnd = timeSwitchGetResultBean.getTimer().get(0).getClose().split(":");
                closeHour = Integer.parseInt(strEnd[0]);
                closeMin = Integer.parseInt(strEnd[1]);
            }
        } else {
            isAdd = true;
            isOpen = false;
            switch_open = false;
            Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
            tvBootTime.setText(String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)));
            tvShutdownTime.setText(String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)));

            String[] strStart = tvBootTime.getText().toString().split(":");
            openHour = Integer.parseInt(strStart[0]);
            openMin = Integer.parseInt(strStart[1]);

            String[] strEnd = tvShutdownTime.getText().toString().split(":");
            closeHour = Integer.parseInt(strEnd[0]);
            closeMin = Integer.parseInt(strEnd[1]);
        }

        onTimeSwitchShow();
    }

    @Override
    public void submitTimeSwitchSuccess(BaseBean baseBean) {
        ToastUtils.show(getString(R.string.txt_successful_operation));
        switch_open = true;
    }

    @Override
    public void submitTimeSwitchDeleteSuccess(BaseBean baseBean) {
        ToastUtils.show(getString(R.string.txt_successful_operation));
        switch_open = false;
    }
}
