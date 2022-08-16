package com.car.scth.mvp.weiget;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.car.scth.R;
import com.car.scth.mvp.utils.ToastUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

/**
 * 编辑分组名称
 */
public class GroupDeleteDialog extends DialogFragment {

    private TextView tvTitle;
    private TextView tvDeleteHint;
    private EditText edtGroup;
    private Button btnCancel;
    private Button btnConfirm;
    private onGroupDeleteChange groupDeleteChange;
    private String gid; // 分组id
    private int deleteType = 0; // 删除类型，0：删除设备，1：删除设备和分组

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.CenterInDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ViewGroup viewGroup = (ViewGroup) View.inflate(getActivity(), R.layout.dialog_delete_group, null);
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

        tvTitle = dialog.findViewById(R.id.tv_title);
        tvDeleteHint = dialog.findViewById(R.id.tv_delete_hint);
        edtGroup = dialog.findViewById(R.id.edt_group);
        btnCancel = dialog.findViewById(R.id.btn_cancel);
        btnConfirm = dialog.findViewById(R.id.btn_confirm);

        if (deleteType == 0){
            tvTitle.setText(getString(R.string.txt_manage_menu_1));
            tvDeleteHint.setVisibility(View.GONE);
        }else{
            tvTitle.setText(getString(R.string.txt_manage_menu_2));
            tvDeleteHint.setVisibility(View.VISIBLE);
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = edtGroup.getText().toString().trim();
                if (TextUtils.isEmpty(password)){
                    ToastUtils.show(getString(R.string.txt_password_hint));
                    return;
                }
                if (groupDeleteChange != null){
                    groupDeleteChange.onGroupDelete(password, gid, deleteType);
                }
                dismiss();
            }
        });
    }

    public void show(FragmentManager manager, String group_id, int type, onGroupDeleteChange change){
        if (isAdded()){
            dismiss();
        }
        this.gid = group_id;
        this.deleteType = type;
        this.groupDeleteChange = change;
        super.show(manager, "GroupDeleteDialog");
    }

    public interface onGroupDeleteChange{

        /**
         * 添加分组
         * @param password 登录密码
         * @param gid 分组id
         * @param type 删除类型，0：删除设备，1：删除设备和分组
         */
        void onGroupDelete(String password, String gid, int type);

    }

}
