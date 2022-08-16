package com.car.scth.mvp.weiget;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.car.scth.R;
import com.car.scth.mvp.model.bean.LoopDayBean;
import com.car.scth.mvp.ui.adapter.LoopDayAdapter;
import com.car.scth.mvp.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 周期定位-重复周期
 */
public class LoopDayDialog extends DialogFragment {

    private RecyclerView recyclerView;
    private TextView tvConfirm;
    private TextView tvCancel;
    private TextView tvSelectAll; // 全选
    private List<LoopDayBean> loopDayBeans;
    private LoopDayAdapter mAdapter;
    private onLoopDayChange loopDayChange;
    private boolean isSelectAll = false;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ViewGroup viewGroup = (ViewGroup) View.inflate(getActivity(), R.layout.dialog_loop_day, null);
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
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.BOTTOM;
            window.setAttributes(params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        recyclerView = dialog.findViewById(R.id.recyclerView);
        tvConfirm = dialog.findViewById(R.id.tv_confirm);
        tvCancel = dialog.findViewById(R.id.tv_cancel);
        tvSelectAll = dialog.findViewById(R.id.tv_select_all);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new LoopDayAdapter(R.layout.item_loop_day, loopDayBeans);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                loopDayBeans.get(position).setSelect(!loopDayBeans.get(position).isSelect());
                mAdapter.notifyItemChanged(position);
                onLoopDaySelectAll();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onConfirm();
            }
        });
        tvSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelectAll = !isSelectAll;
                for (LoopDayBean bean : loopDayBeans){
                    bean.setSelect(isSelectAll);
                }
                mAdapter.notifyDataSetChanged();
                onLoopDaySelectAll();
            }
        });
        onLoopDaySelectAll();
    }

    /**
     * 是否选中了全部
     */
    private void onLoopDaySelectAll(){
        isSelectAll = true;
        for (LoopDayBean bean : loopDayBeans){
            if (!bean.isSelect()) {
                isSelectAll = false;
                break;
            }
        }
        if (isSelectAll){
            tvSelectAll.setText(getString(R.string.txt_select_all_cancel));
        }else{
            tvSelectAll.setText(getString(R.string.txt_select_all));
        }
    }

    /**
     * 确定选择
     */
    private void onConfirm(){
        List<Integer> days = new ArrayList<>();
        String strDays = "";
        for (LoopDayBean bean : loopDayBeans){
            if (bean.isSelect()){
                days.add(bean.getDay());
                if (TextUtils.isEmpty(strDays)){
                    strDays = bean.getDayName();
                }else{
                    strDays = strDays + "," + bean.getDayName();
                }
            }
        }
        if (days.size() == 0){
            ToastUtils.show(getString(R.string.txt_repeat_select_hint));
            return;
        }
        if (days.size() == 7){
            strDays = getString(R.string.txt_every_day);
        }
        if (loopDayChange != null){
            loopDayChange.onLoopDaySelect(days, strDays);
        }
        dismiss();
    }

    public void show(FragmentManager manager, List<LoopDayBean> days, onLoopDayChange change){
        if (isAdded()){
            dismiss();
        }
        this.loopDayBeans = days;
        this.loopDayChange = change;
        super.show(manager, "LoopDayDialog");
    }

    public interface onLoopDayChange{

        /**
         * 选择周期
         * @param days 已选择的周期id
         * @param strDays 已选择的周期文字显示
         */
        void onLoopDaySelect(List<Integer> days, String strDays);

    }

}
