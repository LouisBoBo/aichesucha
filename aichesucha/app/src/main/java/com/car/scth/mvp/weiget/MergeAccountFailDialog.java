package com.car.scth.mvp.weiget;

import android.annotation.SuppressLint;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ScreenUtils;

import java.util.List;

import com.car.scth.R;
import com.car.scth.mvp.model.bean.MergeAccountResultBean;
import com.car.scth.mvp.ui.adapter.MergeAccountFailAdapter;

/**
 * 添加失败的设备
 */
public class MergeAccountFailDialog extends DialogFragment {

    private RecyclerView recyclerView;
    private Button btnCancel;
    private Button btnConfirm;
    private List<MergeAccountResultBean.FailItemBean> failItemsBeans;
    private MergeAccountFailAdapter mAdapter;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.CenterInDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ViewGroup viewGroup = (ViewGroup) View.inflate(getActivity(), R.layout.dialog_merge_account_fail, null);
        dialog.setContentView(viewGroup);
        setCancelable(false); // 点击屏幕或物理返回键，true：dialog消失，false：不消失
        dialog.setCanceledOnTouchOutside(false); // 点击屏幕，dialog不消失；点击物理返回键dialog消失
        initView(dialog);
        return dialog;
    }

    @SuppressLint("SetTextI18n")
    private void initView(Dialog dialog) {
        try {
            Window window = dialog.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = ScreenUtils.getScreenWidth() * 3 / 4;
            params.height = ScreenUtils.getScreenHeight() / 2;
            params.gravity = Gravity.CENTER;
            window.setAttributes(params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        recyclerView = dialog.findViewById(R.id.recyclerView);
        btnCancel = dialog.findViewById(R.id.btn_cancel);
        btnConfirm = dialog.findViewById(R.id.btn_confirm);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MergeAccountFailAdapter(R.layout.item_add_device_fail, failItemsBeans);
        recyclerView.setAdapter(mAdapter);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void show(FragmentManager manager, List<MergeAccountResultBean.FailItemBean> datas){
        if (isAdded()){
            dismiss();
        }
        this.failItemsBeans = datas;
        super.show(manager, "DeviceFailDialog");
    }

}
