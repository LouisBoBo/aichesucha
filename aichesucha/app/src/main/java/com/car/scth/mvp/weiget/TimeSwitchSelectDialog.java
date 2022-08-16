package com.car.scth.mvp.weiget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.TimePicker;

import com.car.scth.R;
import com.car.scth.mvp.utils.DateUtils;

import java.util.Calendar;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

/**
 * 定时开关机 - 选择时间
 */
public class TimeSwitchSelectDialog extends DialogFragment {

    private TimePicker timePicker;
    private TextView tvDismiss;
    private TextView tvConfirm;
    private int mType; // 1：选择开机时间，2：选择关机时间
    private String mTime = ""; // 传递过来的时间
    private onTimeSwitchSelectChange switchSelectChange;
    private int mHour; // 小时
    private int mMin; // 分钟

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ViewGroup viewGroup = (ViewGroup) View.inflate(getActivity(), R.layout.dialog_time_switch_select, null);
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
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.BOTTOM;
            window.setAttributes(params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        timePicker = dialog.findViewById(R.id.time_picker);
        tvDismiss = dialog.findViewById(R.id.tv_cancel);
        tvConfirm = dialog.findViewById(R.id.tv_confirm);

        timePicker.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);
        timePicker.setIs24HourView(true);

        if (TextUtils.isEmpty(mTime)){
            Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
            mTime = String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
        }
        String[] strStart = mTime.split(":");
        timePicker.setCurrentHour(Integer.valueOf(strStart[0]));
        timePicker.setCurrentMinute(Integer.valueOf(strStart[1]));

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            @SuppressLint("DefaultLocale")
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                mTime = String.format("%02d:%02d", timePicker.getCurrentHour(), timePicker.getCurrentMinute());
                mTime = DateUtils.GetOthersStrFromENGLISHStr(mTime, "HH:mm", Locale.getDefault());
                String[] strTime = mTime.split(":");
                if (strTime.length == 2){
                    mHour = Integer.parseInt(strTime[0]);
                    mMin = Integer.parseInt(strTime[1]);
                }else{
                    mHour = timePicker.getCurrentHour();
                    mMin = timePicker.getCurrentMinute();
                }
            }
        });

        tvDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchSelectChange != null){
                    switchSelectChange.onTimeSelect(mType, mTime, mHour, mMin);
                }
                dismiss();
            }
        });
    }

    /**
     *
     * @param manager
     * @param type 1：选择开机时间，2：选择关机时间
     * @param time 传递过来的时间
     * @param change
     */
    public void show(FragmentManager manager, int type, String time, int hour, int min, onTimeSwitchSelectChange change){
        if (isAdded()){
            dismiss();
        }
        this.mType = type;
        this.mTime = time;
        this.mHour = hour;
        this.mMin = min;
        this.switchSelectChange = change;
        super.show(manager, "TimeSwitchSelectDialog");
    }

    public interface onTimeSwitchSelectChange{

        /**
         * 选择的开机时间 or 关机时间
         * @param type 1：选择开机时间，2：选择关机时间
         * @param time 时分
         * @param hour 小时
         * @param min 分钟
         */
        void onTimeSelect(int type, String time, int hour, int min);

    }

}
