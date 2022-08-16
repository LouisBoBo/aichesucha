package com.car.scth.mvp.weiget;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.blankj.utilcode.util.ScreenUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.car.scth.R;
import com.car.scth.app.MyApplication;
import com.car.scth.mvp.model.bean.AlarmScreenDeviceBean;
import com.car.scth.mvp.ui.adapter.AlarmDeviceScreenAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 报警消息-设备筛选
 */
public class AlarmDeviceScreenDialog extends DialogFragment {

    private ImageView ivClose;
    private RecyclerView recyclerView;
    private List<AlarmScreenDeviceBean> screenDeviceBeans;
    private AlarmDeviceScreenAdapter mAdapter;
    private onAlarmDeviceScreenChange screenChange;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ViewGroup viewGroup = (ViewGroup) View.inflate(getActivity(), R.layout.dialog_alarm_device_screen, null);
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
            params.height = ScreenUtils.getScreenHeight() / 2;
            params.gravity = Gravity.BOTTOM;
            window.setAttributes(params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        screenDeviceBeans = new ArrayList<>();

        ivClose = dialog.findViewById(R.id.iv_close);
        recyclerView = dialog.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new AlarmDeviceScreenAdapter(R.layout.item_alarm_device_screen, screenDeviceBeans);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (screenChange != null){
                    screenChange.onDeviceScreen(screenDeviceBeans.get(position).getSimei());
                }
                dismiss();
            }
        });
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void show(FragmentManager manager, onAlarmDeviceScreenChange change){
        if (isAdded()){
            dismiss();
        }
        this.screenChange = change;
        super.show(manager, "AlarmDeviceScreenDialog");
    }

    public interface onAlarmDeviceScreenChange{

        /**
         * 选中的设备
         * @param simei
         */
        void onDeviceScreen(String simei);

    }

}
