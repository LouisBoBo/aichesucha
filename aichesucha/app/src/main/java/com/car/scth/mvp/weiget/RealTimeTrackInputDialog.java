package com.car.scth.mvp.weiget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.car.scth.R;
import com.car.scth.app.MyApplication;
import com.car.scth.mvp.utils.ResultDataUtils;
import com.car.scth.mvp.utils.ToastUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

/**
 * 实时追踪输入追踪时间
 */
public class RealTimeTrackInputDialog extends DialogFragment {

    private EditText edtTime;
    private Button btnCancel;
    private Button btnConfirm;
    private TextView tvStateHint; // 设备状态提示
    private onRealTimeTrackChange realTimeTrackChange;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.CenterInDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ViewGroup viewGroup = (ViewGroup) View.inflate(getActivity(), R.layout.dialog_real_time_track_input, null);
        dialog.setContentView(viewGroup);
        setCancelable(false); // 点击屏幕或物理返回键，true：dialog消失，false：不消失
        dialog.setCanceledOnTouchOutside(false); // 点击屏幕，dialog不消失；点击物理返回键dialog消失
        initView(dialog);
        return dialog;
    }

    private void initView(Dialog dialog) {
        try {
            Window window = dialog.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = ScreenUtils.getScreenWidth() * 3 / 4;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.CENTER;
            window.setAttributes(params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        edtTime = dialog.findViewById(R.id.edt_time);
        btnCancel = dialog.findViewById(R.id.btn_cancel);
        btnConfirm = dialog.findViewById(R.id.btn_confirm);
        tvStateHint = dialog.findViewById(R.id.tv_state_hint);
        if (!TextUtils.isEmpty(MyApplication.getMyApp().getDeviceState())){
            switch (MyApplication.getMyApp().getDeviceState()){
                case ResultDataUtils.Device_State_Line_Down:
                    tvStateHint.setVisibility(View.VISIBLE);
                    tvStateHint.setText(getString(R.string.txt_real_time_track_off_hint));
                    break;
                case ResultDataUtils.Device_State_Line_Sleep:
                    tvStateHint.setVisibility(View.VISIBLE);
                    tvStateHint.setText(getString(R.string.txt_real_time_track_sleep_hint));
                    break;
                case ResultDataUtils.Device_State_Line_On:
                    tvStateHint.setVisibility(View.GONE);
                    break;
            }
        }else{
            tvStateHint.setVisibility(View.GONE);
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (realTimeTrackChange != null){
                    realTimeTrackChange.onCancel();
                }
                dismiss();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onConfirm();
            }
        });
    }

    /**
     * 确认提交
     */
    private void onConfirm(){
        String strTime = edtTime.getText().toString().trim();
        if (TextUtils.isEmpty(strTime)){
            ToastUtils.show(getString(R.string.txt_input_time_hint));
            return;
        }
        int time = Integer.parseInt(strTime);
        if (time == 0){
            ToastUtils.show(getString(R.string.txt_input_time_hint_2));
            return;
        }
        if (time > 1440){
            ToastUtils.show(getString(R.string.txt_input_time_hint_3));
            return;
        }
        hideKeyboard(edtTime);
        if (realTimeTrackChange != null){
            realTimeTrackChange.onRealTimeTrack(time);
        }
        dismiss();
    }

    /**
     * 收起软键盘
     *
     * @param view
     */
    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void show(FragmentManager manager, onRealTimeTrackChange change){
        if (isAdded()){
            dismiss();
        }
        this.realTimeTrackChange = change;
        super.show(manager, "RealTimeTrackInputDialog");
    }

    public interface onRealTimeTrackChange{

        /**
         * 实时追踪时间
         * @param time 分钟
         */
        void onRealTimeTrack(int time);

        /**
         * 取消
         */
        void onCancel();

    }

}
