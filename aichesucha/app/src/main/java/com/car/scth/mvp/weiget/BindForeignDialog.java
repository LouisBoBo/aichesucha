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
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.blankj.utilcode.util.ScreenUtils;

import com.car.scth.R;


/**
 * 国外登录 修改密码
 */
public class BindForeignDialog extends DialogFragment {

    private Button mBtnCancel; // 取消
    private Button mBtnConfirm; // 确定
    private onBindMobileChange bindMobileChange;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.CenterInDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ViewGroup viewGroup = (ViewGroup) View.inflate(getActivity(), R.layout.dialog_bind_foreign, null);
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

        mBtnCancel = dialog.findViewById(R.id.btn_cancel);
        mBtnConfirm = dialog.findViewById(R.id.btn_confirm);

        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bindMobileChange != null){
                    bindMobileChange.onCancel();
                }
                dismiss();
            }
        });
        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bindMobileChange != null){
                    bindMobileChange.onConfirm();
                }
                dismiss();
            }
        });
    }

    public void show(FragmentManager manager, onBindMobileChange change){
        if (isAdded()){
            dismiss();
        }
        this.bindMobileChange = change;
        super.show(manager, "BindMobileDialog");
    }

    public interface onBindMobileChange{

        void onConfirm();

        void onCancel();

    }

}
