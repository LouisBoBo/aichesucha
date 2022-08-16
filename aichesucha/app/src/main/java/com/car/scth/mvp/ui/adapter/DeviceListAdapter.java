package com.car.scth.mvp.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.car.scth.R;
import com.car.scth.mvp.model.bean.DeviceListForManagerResultBean;
import com.car.scth.mvp.utils.ResultDataUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 设备列表
 */
public class DeviceListAdapter extends BaseQuickAdapter<DeviceListForManagerResultBean.ItemsBean, BaseViewHolder> {

    private boolean isShowAll;//是否是展示账号下全部设备，默认false

    public DeviceListAdapter(int layoutResId, @Nullable List<DeviceListForManagerResultBean.ItemsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, DeviceListForManagerResultBean.ItemsBean item) {
        ImageView ivSelect = helper.getView(R.id.iv_select);
        TextView tvState = helper.getView(R.id.tv_device_state);
        TextView tvName = helper.getView(R.id.tv_name);
        if (TextUtils.isEmpty(item.getCar_number())) {
            tvName.setText(String.valueOf(item.getImei()));
        } else {
            tvName.setText(item.getCar_number());
        }
        if (!isShowAll) {
            ivSelect.setVisibility(View.VISIBLE);
            if (item.isSelect()) {
                ivSelect.setImageResource(R.mipmap.icon_device_list_select);
            } else {
                ivSelect.setImageResource(R.mipmap.icon_device_list_unselect);
            }
        } else {
            ivSelect.setVisibility(View.INVISIBLE);
        }
        tvState.setVisibility(View.VISIBLE);
        switch (item.getState()) {
            case ResultDataUtils.Device_State_Line_On:
                tvState.setText("[" + mContext.getString(R.string.txt_state_line_on) + "]");
                tvState.setTextColor(mContext.getResources().getColor(R.color.color_73CA6C));
                break;
            case ResultDataUtils.Device_State_Line_Sleep:
                tvState.setText("[" + mContext.getString(R.string.txt_state_line_sleep) + "]");
                tvState.setTextColor(mContext.getResources().getColor(R.color.color_F22E13));
                break;
            case ResultDataUtils.Device_State_Line_Down:
                tvState.setText("[" + mContext.getString(R.string.txt_state_line_down) + "]");
                tvState.setTextColor(mContext.getResources().getColor(R.color.color_AAAAAA));
                break;

        }
        helper.addOnClickListener(R.id.iv_select);
    }

    public void setShowAll(boolean isShowAll) {
        this.isShowAll = isShowAll;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
    }

}
