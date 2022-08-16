package com.car.scth.mvp.ui.adapter;

import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.car.scth.R;
import com.car.scth.mvp.model.bean.AlarmScreenDeviceBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 搜索设备
 */
public class SearchDeviceAdapter extends BaseQuickAdapter<AlarmScreenDeviceBean, BaseViewHolder> {

    public SearchDeviceAdapter(int layoutResId, @Nullable List<AlarmScreenDeviceBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, AlarmScreenDeviceBean item) {
        helper.setText(R.id.tv_search_imei, mContext.getString(R.string.txt_imei) + item.getImei());
        helper.setText(R.id.tv_search_name, mContext.getString(R.string.txt_device_name) + item.getName());
        helper.setText(R.id.tv_search_version, mContext.getString(R.string.txt_device_type) + item.getVersion());
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
    }

}
