package com.car.scth.mvp.ui.adapter;

import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.car.scth.R;
import com.car.scth.app.MyApplication;
import com.car.scth.mvp.model.bean.DeviceListForManagerResultBean;
import com.car.scth.mvp.utils.ResultDataUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 首页，设备列表
 */
public class DeviceListHomeAdapter extends BaseQuickAdapter<DeviceListForManagerResultBean.ItemsBean, BaseViewHolder> {

    public DeviceListHomeAdapter(int layoutResId, @Nullable List<DeviceListForManagerResultBean.ItemsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, DeviceListForManagerResultBean.ItemsBean item) {
        helper.setText(R.id.tv_device_name, item.getImei() + "");
        TextView tvStatus = helper.getView(R.id.tv_device_state);
        tvStatus.setText(ResultDataUtils.getDeviceStatus(item.getState()));
        switch (item.getState()){
            case ResultDataUtils.Device_State_Line_On:
                tvStatus.setTextColor(mContext.getResources().getColor(R.color.color_2ABA5A));
                break;
            case ResultDataUtils.Device_State_Line_Sleep:
                tvStatus.setTextColor(mContext.getResources().getColor(R.color.color_FF2F25));
                break;
            case ResultDataUtils.Device_State_Line_Down:
                tvStatus.setTextColor(mContext.getResources().getColor(R.color.color_999999));
                break;
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
    }

}
