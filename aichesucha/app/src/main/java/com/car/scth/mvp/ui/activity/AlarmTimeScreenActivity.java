package com.car.scth.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import com.car.scth.di.component.DaggerAlarmTimeScreenComponent;
import com.car.scth.mvp.contract.AlarmTimeScreenContract;
import com.car.scth.mvp.presenter.AlarmTimeScreenPresenter;

import com.car.scth.R;
import com.car.scth.mvp.utils.ConstantValue;
import com.car.scth.mvp.utils.DateUtils;


import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/20/2021 17:29
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class AlarmTimeScreenActivity extends BaseActivity<AlarmTimeScreenPresenter> implements AlarmTimeScreenContract.View {

    @BindView(R.id.tv_start_time)
    TextView tvStartTime; // 开始时间
    @BindView(R.id.ll_start_time)
    LinearLayout llStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime; // 结束时间
    @BindView(R.id.ll_end_time)
    LinearLayout llEndTime;

    private boolean isStartTime = true; // 是否是选择开始时间，否则就是选择结束时间
    private String startTime; // 开始时间
    private String endTime; // 结束时间
    private long selectStartTime = 0; // 选中的开始时间时间戳，用于判断
    private long selectEndTime = 0; // 选中的结束时间时间戳，用于判断
    private String currentTime; // 当前时间
    private long currentTimeMillis = 0; // 当前时间的时间戳


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAlarmTimeScreenComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_alarm_time_screen;//setContentView(id);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        SPUtils.getInstance().put(ConstantValue.ACTIVITY_STATUS, false);
        setTitle(getString(R.string.txt_alarm_time_select));
        setDataForTime();
    }

    /**
     * 初始化时间，首次进来
     */
    private void setDataForTime() {
        Calendar start = Calendar.getInstance();
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        startTime = DateUtils.convertCalendarToTimeStringMM(start);
        endTime = DateUtils.getTodayDateTimeMM();

        onSetDataForStartTime(startTime);
        onSetDataForEndTime(endTime);
        tvStartTime.setText(startTime);
        tvEndTime.setText(endTime);
    }

    @OnClick({R.id.ll_start_time, R.id.ll_end_time, R.id.btn_confirm})
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
            case R.id.btn_confirm:
                onConfirm();
                break;
        }
    }

    /**
     * 提交确认
     */
    private void onConfirm() {
        currentTime = DateUtils.getTodayDateTime();
        currentTimeMillis = Long.parseLong(DateUtils.data_2_MM(currentTime));

        if (TextUtils.isEmpty(startTime)) {
            ToastUtils.showShort(getString(R.string.txt_start_time_select));
            return;
        }
        if (TextUtils.isEmpty(endTime)) {
            ToastUtils.showShort(getString(R.string.txt_end_time_select));
            return;
        }

        if (selectStartTime >= selectEndTime){
            ToastUtils.showShort(getString(R.string.txt_end_equals_start));
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

        long difference = selectEndTime - selectStartTime;
        long sevenDay = 60 * 60 * 24 * 7;
        if (difference > sevenDay) {
            ToastUtils.showShort(getString(R.string.txt_request_seven_data_error));
            return;
        }

        Intent intent = new Intent();
        intent.putExtra("startTime", startTime);
        intent.putExtra("endTime", endTime);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    /**
     * 计算开始时间
     *
     * @param time
     */
    private void onSetDataForStartTime(String time) {
        if (!TextUtils.isEmpty(time)) {
            selectStartTime = Long.parseLong(DateUtils.data_2_MM(time));
        }
    }

    /**
     * 计算结束时间
     *
     * @param time
     */
    private void onSetDataForEndTime(String time) {
        if (!TextUtils.isEmpty(time)) {
            selectEndTime = Long.parseLong(DateUtils.data_2_MM(time));
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
        startDate.set(year - 1, 0, 1);
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
                .setType(new boolean[]{true, true, true, true, true, false})// 默认全部显示
                .setCancelText(getString(R.string.txt_cancel))//取消按钮文字
                .setSubmitText(getString(R.string.txt_confirm))//确认按钮文字
                .setTitleSize(18)//标题文字大小
                .setTitleText(getString(R.string.txt_alarm_time_select))//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
//                .isCyclic(true)//是否循环滚动
                .setTitleColor(getResources().getColor(R.color.color_108EE9))//标题文字颜色
                .setSubmitColor(getResources().getColor(R.color.color_108EE9))//确定按钮文字颜色
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
        String timeS[] = str.split("-");
        selectedDate.set(Integer.parseInt(timeS[0]), Integer.parseInt(timeS[1]) - 1,
                Integer.parseInt(timeS[2]), Integer.parseInt(timeS[3]), Integer.parseInt(timeS[4]));
    }

    @SuppressLint("SimpleDateFormat")
    private String getTime(Date date) {//可根据需要自行截取数据显示

        Log.d("getTime()", "choice date millis: " + date.getTime());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);

        return format.format(date);
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
