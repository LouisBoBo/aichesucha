package com.car.scth.mvp.ui.adapter;

import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.car.scth.R;
import com.car.scth.mvp.model.bean.AddDeviceInfoBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 添加设备
 */
public class AddDeviceInfoAdapter extends BaseQuickAdapter<AddDeviceInfoBean, BaseViewHolder> {

    public AddDeviceInfoAdapter(int layoutResId, @Nullable List<AddDeviceInfoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, AddDeviceInfoBean item) {
        helper.setText(R.id.tv_imei, item.getImei());
//        helper.setText(R.id.tv_remark, item.getName() + "   " + item.getMobile() + "   " + item.getRemark());
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
    }
}
