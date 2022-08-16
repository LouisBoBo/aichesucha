package com.car.scth.mvp.weiget;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.blankj.utilcode.util.ScreenUtils;

import com.car.scth.R;

/**
 * 隐私政策说明协议
 */
public class UserServicePolicyDialog extends DialogFragment implements View.OnClickListener {

    private Button btnNonuse; // 暂不使用
    private Button btnAgree; // 同意
    private int mPrivacyType = 0; // 0：不同意，1：同意
    private onPrivacyPolicyChange policyChange;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.CenterInDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ViewGroup viewGroup = (ViewGroup) View.inflate(getActivity(), R.layout.dialog_user_service_policy, null);
        dialog.setContentView(viewGroup);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        initView(dialog);
        return dialog;
    }

    private void initView(Dialog dialog) {
        try {
            Window window = dialog.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            int width = ScreenUtils.getScreenWidth() * 3 / 4;
            params.width = width;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.CENTER;
            window.setAttributes(params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnNonuse = dialog.findViewById(R.id.btn_nonuse);
        btnAgree = dialog.findViewById(R.id.btn_agree);
        btnNonuse.setOnClickListener(this);
        btnAgree.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_nonuse:
                mPrivacyType = 0;
                if (policyChange != null){
                    policyChange.onPrivacyPolicy(mPrivacyType);
                }
                break;
            case R.id.btn_agree:
                mPrivacyType = 1;
                if (policyChange != null){
                    policyChange.onPrivacyPolicy(mPrivacyType);
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        if (mPrivacyType == 0){
            if (policyChange != null){
                policyChange.onPrivacyPolicy(mPrivacyType);
            }
        }
        super.onDestroy();
    }

    public void show(FragmentManager manager, onPrivacyPolicyChange change){
        if (isAdded()){
            dismiss();
        }
        this.policyChange = change;
        super.show(manager, "UserServicePolicyDialog");
    }

    public interface onPrivacyPolicyChange{

        /**
         * 选择
         * @param type 0：不同意，1：同意
         */
        void onPrivacyPolicy(int type);

    }

}
