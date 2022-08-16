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
import com.car.scth.mvp.utils.ResultDataUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

/**
 * 添加用户-选择用户权限
 */
public class AuthoritySelectDialog extends DialogFragment implements View.OnClickListener {

    private TextView tvRoleManager; // 管理员
    private TextView tvRoleUser; // 普通用户
    private TextView tvCancel; // 取消
    private onAuthoritySelectChange authoritySelectChange;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ViewGroup viewGroup = (ViewGroup) View.inflate(getActivity(), R.layout.dialog_authority_select, null);
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
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.BOTTOM;
            window.setAttributes(params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        tvRoleManager = dialog.findViewById(R.id.tv_role_manager);
        tvRoleUser = dialog.findViewById(R.id.tv_role_user);
        tvCancel = dialog.findViewById(R.id.tv_cancel);
        tvRoleManager.setOnClickListener(this);
        tvRoleUser.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_role_manager:
                onAuthoritySelect(ResultDataUtils.Account_Type_Manager);
                break;
            case R.id.tv_role_user:
                onAuthoritySelect(ResultDataUtils.Account_Type_User);
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }

    /**
     * 选择权限
     * @param authority
     */
    private void onAuthoritySelect(String authority){
        if (authoritySelectChange != null){
            authoritySelectChange.onAuthoritySelect(authority);
        }
        dismiss();
    }

    public void show(FragmentManager manager, onAuthoritySelectChange change){
        if (isAdded()){
            dismiss();
        }
        this.authoritySelectChange = change;
        super.show(manager, "AuthoritySelectDialog");
    }

    public interface onAuthoritySelectChange{

        /**
         * 选择权限
         * @param authority 权限
         */
        void onAuthoritySelect(String authority);

    }

}
