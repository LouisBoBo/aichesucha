package com.car.scth.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.car.scth.app.MyApplication;
import com.car.scth.di.component.DaggerMileageStatisticsComponent;
import com.car.scth.mvp.contract.MileageStatisticsContract;
import com.car.scth.mvp.model.api.ModuleValueService;
import com.car.scth.mvp.model.bean.MileageStatisticsResultBean;
import com.car.scth.mvp.model.putbean.MileageStatisticsPutBean;
import com.car.scth.mvp.presenter.MileageStatisticsPresenter;

import com.car.scth.R;
import com.car.scth.mvp.utils.DateUtils;
import com.car.scth.mvp.utils.Utils;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 里程统计
 * <p>
 * Created by MVPArmsTemplate on 10/31/2020 08:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MileageStatisticsActivity extends BaseActivity<MileageStatisticsPresenter> implements MileageStatisticsContract.View {

    @BindView(R.id.tv_total_mileage)
    TextView tvMileageStatistics; // 总里程
    @BindView(R.id.tv_speed_number)
    TextView tvSpeedNumber; // 超速报警次数
    @BindView(R.id.tv_alarm_number)
    TextView tvAlarmNumber; // 报警次数
    @BindView(R.id.tv_start_time)
    TextView tvStartTime; // 开始时间
    @BindView(R.id.tv_end_time)
    TextView tvEndTime; // 结束时间

    private String startTime; // 开始时间
    private String endTime; // 结束时间
    private boolean isStartTime = true; // 是否是选择开始时间，否则就是选择结束时间
    private int selectStartTime = 0; // 选中的开始时间时间戳，用于判断
    private int selectEndTime = 0; // 选中的结束时间时间戳，用于判断

    private String mSimei; // 加密的imei号

    public static Intent newInstance() {
        return new Intent(MyApplication.getMyApp(), MileageStatisticsActivity.class);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMileageStatisticsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_mileage_statistics; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_function_mileage_statistics));
        mSimei = MyApplication.getMyApp().getSimei();
        setDataForTime();
        submitMileageStatistics();
    }

    /**
     * 初始化时间，首次进来
     */
    private void setDataForTime() {
        Calendar start = Calendar.getInstance();
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        startTime = Utils.convertCalendar2TimeString(start);
        endTime = DateUtils.getTodayDateTime();

        onSetDataForStartTime(startTime);
        onSetDataForEndTime(endTime);

        tvStartTime.setText(startTime);
        tvEndTime.setText(endTime);
    }

    /**
     * 计算开始时间
     *
     * @param time
     */
    private void onSetDataForStartTime(String time) {
        if (!TextUtils.isEmpty(time)) {
            selectStartTime = Integer.parseInt(DateUtils.data_2(time));
        }
    }

    /**
     * 计算结束时间
     *
     * @param time
     */
    private void onSetDataForEndTime(String time) {
        if (!TextUtils.isEmpty(time)) {
            selectEndTime = Integer.parseInt(DateUtils.data_2(time));
        }
    }

    @OnClick({R.id.ll_start_time, R.id.ll_end_time, R.id.btn_request})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_start_time:
                isStartTime = true;
                onSelectData();
                break;
            case R.id.ll_end_time:
                isStartTime = false;
                onSelectData();
                break;
            case R.id.btn_request:
                submitMileageStatistics();
                break;
        }
    }

    /**
     * 发起统计查询
     */
    private void submitMileageStatistics() {
        // 当前时间
        String currentTime = DateUtils.getTodayDateTime();
        // 当前时间的时间戳
        int currentTimeMillis = Integer.parseInt(DateUtils.data_2(currentTime));

        if (selectEndTime < selectStartTime) {
            ToastUtils.showShort(getString(R.string.txt_begin_gt_end));
            return;
        }

        if (selectStartTime > currentTimeMillis) {
            ToastUtils.showShort(getString(R.string.txt_start_gt_current));
            return;
        }

        if (selectEndTime > currentTimeMillis) {
            ToastUtils.showShort(getString(R.string.txt_end_gt_current));
            return;
        }

        int difference = selectEndTime - selectStartTime;
        int sevenDay = 60 * 60 * 24 * 7;
        if (difference > sevenDay) {
            ToastUtils.showShort(getString(R.string.txt_request_seven_data_error));
            return;
        }
        if (difference == 0) {
            ToastUtils.showShort(getString(R.string.txt_end_equals_start));
            return;
        }

        MileageStatisticsPutBean bean = new MileageStatisticsPutBean();

        MileageStatisticsPutBean.ParamsBean param = new MileageStatisticsPutBean.ParamsBean();
        param.setTime_begin(selectStartTime);
        param.setTime_end(selectEndTime);
        param.setSimei(mSimei);
        bean.setParams(param);
        bean.setFunc(ModuleValueService.Fuc_For_MileageStatistics_Name);
        bean.setModule(ModuleValueService.Module_For_MileageStatistics_Name);

        if (getPresenter() != null) {
            getPresenter().getMileageStatistics(bean);
        }

    }


    /**
     * 发起时间选择
     */
    private void onSelectData() {
        Calendar selectedDate = Calendar.getInstance();
        if (isStartTime) {
            //2020-10-20 00:00
            setCalendarTime(selectedDate, startTime);
        } else {
            setCalendarTime(selectedDate, endTime);
        }
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        int year = selectedDate.get(Calendar.YEAR);

        //正确设置方式 原因：注意事项有说明
        startDate.set(year, 0, 1);
        endDate.set(2050, 11, 1);

        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                if (isStartTime) {
                    startTime = getTime(date);
                    tvStartTime.setText(startTime);
                    onSetDataForStartTime(startTime);
                } else {
                    endTime = getTime(date);
                    tvEndTime.setText(endTime);
                    onSetDataForEndTime(endTime);
                }
            }
        })
                .setType(new boolean[]{false, true, true, true, true, true})// 默认全部显示
                .setCancelText(getString(R.string.txt_cancel))//取消按钮文字
                .setSubmitText(getString(R.string.txt_confirm))//确认按钮文字
                .setTitleSize(18)//标题文字大小
                .setTitleText(getString(R.string.txt_please_select_time))//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
//                .isCyclic(true)//是否循环滚动
                .setTitleColor(getResources().getColor(R.color.color_00A7FF))//标题文字颜色
                .setSubmitColor(getResources().getColor(R.color.color_00A7FF))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.color_333333))//取消按钮文字颜色
                .setTitleBgColor(getResources().getColor(R.color.color_FFFFFF))//标题背景颜色 Night mode
                .setBgColor(getResources().getColor(R.color.color_FFFFFF))//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
//                .setLabel("年","月","日","时","分","秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                .isDialog(true)//是否显示为对话框样式
                .setLineSpacingMultiplier(3)
                .setContentTextSize(15) //滚轮文字大小
                .setTextColorCenter(getResources().getColor(R.color.color_333333)) // 选中项文字颜色设置
                .setTextColorOut(getResources().getColor(R.color.color_999999)) // 未选中项文字颜色设置
                .build();

        pvTime.show();
    }

    private void setCalendarTime(Calendar selectedDate, String time) {
        String str = time.replace(":", "-").replace(" ", "-");
        String[] timeS = str.split("-");
        selectedDate.set(Integer.parseInt(timeS[0]), Integer.parseInt(timeS[1]) - 1,
                Integer.parseInt(timeS[2]), Integer.parseInt(timeS[3]), Integer.parseInt(timeS[4]), Integer.parseInt(timeS[5]));
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        return format.format(date);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getMileageStatisticsSuccess(MileageStatisticsResultBean bean) {
        // 总里程
        double mileage = ((float) bean.getDistance()) / 1000;
        tvMileageStatistics.setText(String.format(Locale.ENGLISH, "%.02f", mileage));
        // 报警次数
        int alarmNumber = bean.getTotal_alarm();
        // 超速次数
        int speedNumber = bean.getSpeed_alarm();
        tvAlarmNumber.setText(alarmNumber + getString(R.string.txt_next));
        tvSpeedNumber.setText(speedNumber + getString(R.string.txt_next));
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
}
