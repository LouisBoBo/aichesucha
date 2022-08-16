package com.car.scth.mvp.weiget;

import android.app.Dialog;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.ScreenUtils;
import com.car.scth.R;
import com.car.scth.mvp.utils.CharsFilters;
import com.car.scth.mvp.utils.ToastUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

/**
 * 添加分组
 */
public class GroupAddDialog extends DialogFragment {

    private EditText edtGroup;
    private Button btnCancel;
    private Button btnConfirm;
    private onGroupAddChange groupAddChange;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.CenterInDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ViewGroup viewGroup = (ViewGroup) View.inflate(getActivity(), R.layout.dialog_add_group, null);
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

        edtGroup = dialog.findViewById(R.id.edt_group);
        btnCancel = dialog.findViewById(R.id.btn_cancel);
        btnConfirm = dialog.findViewById(R.id.btn_confirm);
        edtGroup.setFilters(new InputFilter[]{new CharsFilters()});

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groupName = edtGroup.getText().toString().trim();
                if (TextUtils.isEmpty(groupName)){
                    ToastUtils.show(getString(R.string.txt_add_group_hint));
                    return;
                }
                if (groupAddChange != null){
                    groupAddChange.onGroupAdd(groupName);
                }
                dismiss();
            }
        });
    }

    public void show(FragmentManager manager, onGroupAddChange change){
        if (isAdded()){
            dismiss();
        }
        this.groupAddChange = change;
        super.show(manager, "GroupAddDialog");
    }

    public interface onGroupAddChange{

        /**
         * 添加分组
         * @param groupName 分组名称
         */
        void onGroupAdd(String groupName);

    }

}
