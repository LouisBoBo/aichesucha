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

import com.blankj.utilcode.util.ScreenUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.car.scth.R;
import com.car.scth.mvp.model.bean.DeviceGroupResultBean;
import com.car.scth.mvp.ui.adapter.GroupListAdapter;
import com.car.scth.mvp.utils.ToastUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 转移设备
 */
public class GroupTransferDeviceDialog extends DialogFragment {

    private RecyclerView recyclerView;
    private Button btnCancel;
    private Button btnConfirm;
    private onGroupTransferDeviceChange transferDeviceChange;
    private List<DeviceGroupResultBean.GaragesBean> groupBeans; // 设备分组
    private GroupListAdapter mAdapter;
    private String mGid; // 需要转移设备的分组id

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.CenterInDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ViewGroup viewGroup = (ViewGroup) View.inflate(getActivity(), R.layout.dialog_transfer_device_group, null);
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
            params.height = ScreenUtils.getScreenHeight() / 2;
            params.gravity = Gravity.CENTER;
            window.setAttributes(params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DeviceGroupResultBean.GaragesBean selectBean = null; // 需要转移设备的分组
        if (!TextUtils.isEmpty(mGid)){
            for (DeviceGroupResultBean.GaragesBean bean : groupBeans){
                bean.setSelect(false);
                if (bean.getSid().equals(mGid)){
                    selectBean = bean;
                }
            }
        }
        if (selectBean != null){
            groupBeans.remove(selectBean);
        }

        recyclerView = dialog.findViewById(R.id.recyclerView);
        btnCancel = dialog.findViewById(R.id.btn_cancel);
        btnConfirm = dialog.findViewById(R.id.btn_confirm);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new GroupListAdapter(R.layout.item_group_list, groupBeans);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (DeviceGroupResultBean.GaragesBean bean : groupBeans){
                    bean.setSelect(false);
                }
                groupBeans.get(position).setSelect(true);
                mAdapter.notifyDataSetChanged();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        String sid = "";
        for (DeviceGroupResultBean.GaragesBean bean : groupBeans){
            if (bean.isSelect()){
                sid = bean.getSid();
                break;
            }
        }
        if (TextUtils.isEmpty(sid)){
            ToastUtils.show(getString(R.string.txt_group_select_hint));
            return;
        }

        if (transferDeviceChange != null){
            transferDeviceChange.onGroupTransferDevice(sid);
        }
        dismiss();
    }

    /**
     *
     * @param manager
     * @param gid 选中分组的gid
     * @param datas
     * @param change
     */
    public void show(FragmentManager manager, String gid, List<DeviceGroupResultBean.GaragesBean> datas, onGroupTransferDeviceChange change){
        if (isAdded()){
            dismiss();
        }
        this.mGid = gid;
        this.groupBeans = datas;
        this.transferDeviceChange = change;
        super.show(manager, "GroupTransferDeviceDialog");
    }

    public interface onGroupTransferDeviceChange{

        /**
         * 转移设备
         * @param sid 分组名称
         */
        void onGroupTransferDevice(String sid);

    }

}
