package com.car.scth.mvp.ui.adapter;

import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.car.scth.R;
import com.car.scth.mvp.model.bean.DeviceBaseResultBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 添加设备-失败设备列表
 */
public class DeviceFailAdapter extends BaseQuickAdapter<DeviceBaseResultBean.FailItemsBean, BaseViewHolder> {

    public DeviceFailAdapter(int layoutResId, @Nullable List<DeviceBaseResultBean.FailItemsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, DeviceBaseResultBean.FailItemsBean item) {
        helper.setText(R.id.tv_imei, mContext.getString(R.string.txt_imei) + item.getImei());
        helper.setText(R.id.tv_reason, mContext.getString(R.string.txt_fail_reason) + item.getError_messageX());
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
    }

}
