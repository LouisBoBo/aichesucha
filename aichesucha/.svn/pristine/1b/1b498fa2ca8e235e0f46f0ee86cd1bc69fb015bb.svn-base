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

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

/**
 * 设置睡眠模式选择时间dialog
 */
public class SelectTimeDialog extends DialogFragment {

    private TextView tvDismiss;
    private TextView tvConfirm;
    private TimePicker timePicker;
    private String tmpTime; // 睡眠模式时间
    private onSelectTimeChange timeChange;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ViewGroup viewGroup = (ViewGroup) View.inflate(getActivity(), R.layout.dialog_select_time, null);
        dialog.setContentView(viewGroup);
        dialog.setCanceledOnTouchOutside(true);
        initView(dialog);
        return dialog;
    }

    private void initView(Dialog dialog){
        try{
            Window window = dialog.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.BOTTOM;
            window.setAttributes(params);
        }catch (Exception e){
            e.printStackTrace();
        }

        tvDismiss = dialog.findViewById(R.id.tv_cancel);
        timePicker = dialog.findViewById(R.id.time_picker);
        tvConfirm = dialog.findViewById(R.id.tv_confirm);

        timePicker.setIs24HourView(true);
        timePicker.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);
        if(!TextUtils.isEmpty(tmpTime)){
            String[] arrStr = tmpTime.split(":");
            timePicker.setCurrentHour(Integer.valueOf(arrStr[0]));
            timePicker.setCurrentMinute(Integer.valueOf(arrStr[1]));
        }

        tvDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View v) {
                tmpTime = String.format("%02d:%02d", timePicker.getCurrentHour(), timePicker.getCurrentMinute());
                tmpTime = DateUtils.GetOthersStrFromENGLISHStr(tmpTime, "HH:mm", Locale.getDefault());
                if (timeChange != null){
                    timeChange.onSelectTime(tmpTime);
                }
                dismiss();
            }
        });
    }

    public void show(FragmentManager manager, String time, onSelectTimeChange change){
        if (isAdded()){
            dismiss();
        }
        this.tmpTime = time;
        this.timeChange = change;
        super.show(manager, "SelectTimeDialog");
    }

    public interface onSelectTimeChange{

        /**
         * 提交选中的时间
         * @param time
         */
        void onSelectTime(String time);

    }


}
