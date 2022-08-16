package com.car.scth.mvp.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.car.scth.R;
import com.car.scth.mvp.model.bean.FenceResultBean;
import com.car.scth.mvp.utils.ResultDataUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 围栏列表
 */
public class FenceAdapter extends BaseQuickAdapter<FenceResultBean.ItemsBean, BaseViewHolder> {

    public FenceAdapter(int layoutResId, @Nullable List<FenceResultBean.ItemsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, FenceResultBean.ItemsBean item) {
        helper.setText(R.id.tv_fence_name, item.getName());
        helper.setText(R.id.tv_alarm_type, ResultDataUtils.getFenceAlarmSwitch(item.getFence_switch()));
        TextView tvAddress = helper.getView(R.id.tv_address);
        TextView tvRemark = helper.getView(R.id.tv_remark);
        if (item.getType().equals(ResultDataUtils.Fence_Round)) {
            if (item.getOfence().getCircle().getAddr() != null) {
                tvAddress.setVisibility(View.VISIBLE);
                tvRemark.setVisibility(View.VISIBLE);
                tvAddress.setText(item.getOfence().getCircle().getAddr());
            } else {
                tvAddress.setVisibility(View.GONE);
                tvRemark.setVisibility(View.GONE);
            }
        } else {
            tvAddress.setVisibility(View.GONE);
            tvRemark.setVisibility(View.GONE);
        }

        helper.addOnClickListener(R.id.iv_delete);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
    }
}
