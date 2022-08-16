package com.car.scth.mvp.weiget;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.car.scth.R;
import com.car.scth.mvp.ui.view.data.haibin.Calendar;
import com.car.scth.mvp.ui.view.data.haibin.CalendarView;
import com.car.scth.mvp.utils.ResultDataUtils;
import com.car.scth.mvp.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

/**
 * 定时开关机 - 选择日期
 */
public class TimeSwitchDataDialog extends DialogFragment {

    private TextView tvData;
    private TextView tvConfirm;
    private TextView tvDismiss;
    private CalendarView calendarView;
    private int year; // 当前月份
    private int month; // 当前年份
    private List<Integer> mDatas; // 已经选择的日期
    private String timeSwitchType; // 定时开关机日期类型
    private onTimeSwitchDataChange dataChange;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ViewGroup viewGroup = (ViewGroup) View.inflate(getActivity(), R.layout.dialog_time_switch_data, null);
        dialog.setContentView(viewGroup);
        dialog.setCanceledOnTouchOutside(true);
        initView(dialog);
        return dialog;
    }

    private void initView(Dialog dialog) {
        try {
            Window window = dialog.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.MATCH_PARENT;
            params.gravity = Gravity.BOTTOM;
            window.setAttributes(params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        tvData = dialog.findViewById(R.id.tv_data);
        tvConfirm = dialog.findViewById(R.id.tv_confirm);
        tvDismiss = dialog.findViewById(R.id.tv_cancel);
        calendarView = dialog.findViewById(R.id.calendarView);

        calendarView.clearMultiSelect();
        year = calendarView.getCurYear();
        month = calendarView.getCurMonth();
        for (int i = 0; i < mDatas.size(); i++){
            calendarView.putMultiSelect(getCalendar(year, month, mDatas.get(i)));
        }

        calendarView.setOnMonthChangeListener(new CalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month) {

                tvData.setText(String.format("%04d-%02d-%02d", year, month, calendarView.getCurDay()));

                //月份改变  清除其他月份的选择
                List<Calendar> calendarList = calendarView.getMultiSelectCalendars();
                List<Calendar> calendarListTmp = new ArrayList<>();
                for (Calendar item : calendarList) {
                    if (item.getYear() != year || item.getMonth() != month) {
                        calendarListTmp.add(item);
                    }
                }

                for (Calendar item : calendarListTmp) {
                    calendarView.removeMultiSelect(item);
                }
            }
        });

        calendarView.setOnCalendarMultiSelectListener(new CalendarView.OnCalendarMultiSelectListener() {
            @Override
            public void onCalendarMultiSelectOutOfRange(Calendar calendar) {

            }

            @Override
            public void onMultiSelectOutOfSize(Calendar calendar, int maxSize) {

            }

            @Override
            public void onCalendarMultiSelect(Calendar calendar, int curSize, int maxSize) {
                if (timeSwitchType.equals(ResultDataUtils.Time_Switch_Current_Month)) {
                    int currentMonth = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH) + 1;
                    if (calendar.getMonth() != currentMonth) {
                        calendarView.removeMultiSelect(calendar);
                        ToastUtils.show(getString(R.string.txt_current_month_hint));
                        return;
                    }
                    // 今天的日期，判断当前选中的日期是否小于今天
                    int today = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH);
                    if (calendar.getDay() < today) {
                        calendarView.removeMultiSelect(calendar);
                        ToastUtils.show(getString(R.string.txt_current_day_hint));
                    }
                } else if (timeSwitchType.equals(ResultDataUtils.Time_Switch_Every_Day)) {
                    calendarView.removeMultiSelect(calendar);
                    ToastUtils.show(getString(R.string.txt_every_day_hint));
                }
            }
        });

        calendarView.scrollToCurrent();

        tvDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataChange != null){
                    dataChange.onDataSelect(calendarView.getMultiSelectCalendars());
                }
                dismiss();
            }
        });
    }

    private Calendar getCalendar(int year, int month, int day) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
//        calendar.setSchemeColor(getResources().getColor(R.color.colorBlue));
        return calendar;
    }

    /**
     *
     * @param manager
     * @param type 开关机类型
     * @param datas 已经选择的日期
     * @param change
     */
    public void show(FragmentManager manager, String type, List<Integer> datas, onTimeSwitchDataChange change){
        if (isAdded()){
            dismiss();
        }
        this.timeSwitchType = type;
        this.mDatas = datas;
        this.dataChange = change;
        super.show(manager, "TimeSwitchDataDialog");
    }

    public interface onTimeSwitchDataChange{

        void onDataSelect(List<Calendar> calendarList);

    }

}
