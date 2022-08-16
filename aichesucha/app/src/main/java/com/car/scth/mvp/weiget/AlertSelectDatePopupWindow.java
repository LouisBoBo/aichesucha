package com.car.scth.mvp.weiget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.car.scth.R;
import com.car.scth.mvp.utils.DateUtils;
import com.car.scth.mvp.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * 报警时间选择弹框
 */
public class AlertSelectDatePopupWindow extends PopupWindow {

    private Context mContext;
    private onSelectTimeChange timeChange;
    private TextView tvStartTime, tvEndTime;
    private TextView tvCancel, tvConfirm;
    private LinearLayout ll_start_time, ll_end_time;
    private View view;

    private String startTime; // 开始时间
    private String endTime; // 结束时间
    private boolean isStartTime = true; // 是否是选择开始时间，否则就是选择结束时间
    private int selectStartTime = 0; // 选中的开始时间时间戳，用于判断
    private int selectEndTime = 0; // 选中的结束时间时间戳，用于判断

    private TimePickerView pvTime;

    /**
     * 初始化
     * @param context
     * @param oldStartTime 开始时间
     * @param oldEndTime 结束时间
     */
    public AlertSelectDatePopupWindow(Context context, int oldStartTime, int oldEndTime) {
        super(context);
        this.mContext = context;
        View root = View.inflate(context, R.layout.popupwindow_alert_date_select, null);
        tvStartTime = root.findViewById(R.id.tv_start_time);
        tvEndTime = root.findViewById(R.id.tv_end_time);
        ll_start_time = root.findViewById(R.id.ll_start_time);
        ll_end_time = root.findViewById(R.id.ll_end_time);
        tvCancel = root.findViewById(R.id.tv_select_date_cancel);
        tvConfirm = root.findViewById(R.id.tv_select_date_confirm);
        view = root.findViewById(R.id.view);

        //设置SelectPicPopupWindow的View
        this.setContentView(root);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ScreenUtils.getScreenWidth()); //fragment 获取的宽度ViewGroup.LayoutParams.MATCH_PARENT 异常
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(false);
//        setOutsideTouchable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        //this.setAnimationStyle(R.style.AnimBottom);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(new ColorDrawable(mContext.getResources().getColor(R.color.transparent)));

        setDataForTime(oldStartTime, oldEndTime);
        setListener();
    }

    /**
     * 初始化时间，首次进来
     */
    private void setDataForTime(int oldStartTime, int oldEndTime) {
        if (oldStartTime == 0 && oldEndTime == 0){
            Calendar start = Calendar.getInstance();
            start.set(Calendar.HOUR_OF_DAY, 0);
            start.set(Calendar.MINUTE, 0);
            start.set(Calendar.SECOND, 0);
            startTime = Utils.convertCalendar2TimeString(start);
            endTime = DateUtils.getTodayDateTime();
        }else{
            startTime = DateUtils.timeConversionDate_two(String.valueOf(oldStartTime));
            endTime = DateUtils.timeConversionDate_two(String.valueOf(oldEndTime));
        }

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

    private void setListener() {
        ll_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartTime = true;
                onSelectData();
            }
        });
        ll_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartTime = false;
                onSelectData();

            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 当前时间
                String currentTime = DateUtils.getTodayDateTime();
                // 当前时间的时间戳
                int currentTimeMillis = Integer.parseInt(DateUtils.data_2(currentTime));

                if (selectEndTime < selectStartTime) {
                    ToastUtils.showShort(mContext.getString(R.string.txt_begin_gt_end));
                    return;
                }

                if (selectStartTime > currentTimeMillis) {
                    ToastUtils.showShort(mContext.getString(R.string.txt_start_gt_current));
                    return;
                }

                if (selectEndTime > currentTimeMillis) {
                    ToastUtils.showShort(mContext.getString(R.string.txt_end_gt_current));
                    return;
                }

                int difference = selectEndTime - selectStartTime;
                int sevenDay = 60 * 60 * 24 * 7;
                if (difference > sevenDay) {
                    ToastUtils.showShort(mContext.getString(R.string.txt_request_seven_data_error));
                    return;
                }
                if (difference == 0) {
                    ToastUtils.showShort(mContext.getString(R.string.txt_end_equals_start));
                    return;
                }
                timeChange.onSelectTime(selectStartTime, selectEndTime);
                dismiss();
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    /**
     * 发起时间选择
     */
    private void onSelectData() {
        if (pvTime != null && pvTime.isShowing()) {
            pvTime.dismiss();
            return;
        }
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

        pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
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
                .setCancelText(mContext.getString(R.string.txt_cancel))//取消按钮文字
                .setSubmitText(mContext.getString(R.string.txt_confirm))//确认按钮文字
                .setTitleSize(18)//标题文字大小
                .setTitleText(mContext.getString(R.string.txt_please_select_time))//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
//                .isCyclic(true)//是否循环滚动
                .setTitleColor(mContext.getResources().getColor(R.color.color_00A7FF))//标题文字颜色
                .setSubmitColor(mContext.getResources().getColor(R.color.color_00A7FF))//确定按钮文字颜色
                .setCancelColor(mContext.getResources().getColor(R.color.color_333333))//取消按钮文字颜色
                .setTitleBgColor(mContext.getResources().getColor(R.color.color_FFFFFF))//标题背景颜色 Night mode
                .setBgColor(mContext.getResources().getColor(R.color.color_FFFFFF))//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
//                .setLabel("年","月","日","时","分","秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(true)//是否显示为对话框样式
                .setLineSpacingMultiplier(3)
                .setContentTextSize(15) //滚轮文字大小
                .setTextColorCenter(mContext.getResources().getColor(R.color.color_333333)) // 选中项文字颜色设置
                .setTextColorOut(mContext.getResources().getColor(R.color.color_999999)) // 未选中项文字颜色设置
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

    /**
     * 兼容 android 7.0之后设置showAsDropDown失效问题
     *
     * @param anchor
     */
    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect visibleFrame = new Rect();
            anchor.getGlobalVisibleRect(visibleFrame);
            int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            setHeight(height);
        }
        super.showAsDropDown(anchor);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
            int[] a = new int[2];
            anchor.getLocationInWindow(a);
            showAtLocation(anchor, Gravity.NO_GRAVITY, xoff, a[1] + anchor.getHeight() + yoff);
        } else {
            super.showAsDropDown(anchor, xoff, yoff);
        }
    }

    public void setSelectTimeChange(onSelectTimeChange change) {
        this.timeChange = change;
    }

    public interface onSelectTimeChange {

        /**
         * 选择的功能
         *
         * @param
         */
        void onSelectTime(int start_Time, int end_Time);

    }

}
