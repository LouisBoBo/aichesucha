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

import com.blankj.utilcode.util.ScreenUtils;
import com.car.scth.R;
import com.car.scth.app.MyApplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;


/**
 * 故障自检dialog
 */
public class BreakdownExamineDialog extends DialogFragment {

    private TextView tvGSM;
    private TextView tvGPS;
    private TextView tvElectric;
    private TextView tvConfirm;
    
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.CenterInDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ViewGroup viewGroup = (ViewGroup) View.inflate(getActivity(), R.layout.dialog_breakdown_examine, null);
        dialog.setContentView(viewGroup);
        dialog.setCanceledOnTouchOutside(true);
        initView(dialog);
        return dialog;
    }

    @SuppressLint("SetTextI18n")
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

        tvGSM = dialog.findViewById(R.id.tv_gsm);
        tvGPS = dialog.findViewById(R.id.tv_gps);
        tvElectric = dialog.findViewById(R.id.tv_electric);
        tvConfirm = dialog.findViewById(R.id.tv_confirm);

        int singalRate = MyApplication.getMyApp().getSignal_rate();
        if(singalRate <= 14 ){
            tvGSM.setText(getString(R.string.txt_gsm) + "：" + getString(R.string.txt_gsm_lower));
        }else if(singalRate <= 23){
            tvGSM.setText(getString(R.string.txt_gsm) + "：" + getString(R.string.txt_gsm_middle));
        }else{
            tvGSM.setText(getString(R.string.txt_gsm) + "：" + getString(R.string.txt_gsm_high));
        }

        String latAndLog = MyApplication.getMyApp().getLatAndLon();
        String lat = "";
        String lng = "";
        if (!TextUtils.isEmpty(latAndLog)){
            String[] location = latAndLog.split(",");
            lat = location[0];
            lng = location[1];
        }
        if(TextUtils.isEmpty(lat) || TextUtils.isEmpty(lng) || lat.equals("0") || lng.equals("0")){
            tvGPS.setText(getString(R.string.txt_gps_no_position));
        }else{
            int signal = MyApplication.getMyApp().getSigna_val();
            if(signal == 0){
                tvGPS.setText(getString(R.string.txt_gps) + "：" + getString(R.string.txt_gps_no));
            } else if(signal <= 7 ){
                tvGPS.setText(getString(R.string.txt_gps) + "：" + getString(R.string.txt_gsm_lower));
            }else if(signal <= 10){
                tvGPS.setText(getString(R.string.txt_gps) + "：" + getString(R.string.txt_gsm_middle));
            }else{
                tvGPS.setText(getString(R.string.txt_gps) + "：" + getString(R.string.txt_gsm_high));
            }
        }

        tvElectric.setText(getString(R.string.txt_electric) + "：" + MyApplication.getMyApp().getPower() + "%");

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void show(FragmentManager manager){
        if (isAdded()){
            dismiss();
        }
        super.show(manager, "BreakdownExamineDialog");
    }

}
