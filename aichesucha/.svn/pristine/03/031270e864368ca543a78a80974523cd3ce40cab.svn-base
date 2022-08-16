package com.car.scth.mvp.ui.adapter;

import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.car.scth.R;
import com.car.scth.mvp.model.bean.DeviceGroupResultBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 首页-分组列表
 */
public class GroupListHomeAdapter extends BaseQuickAdapter<DeviceGroupResultBean.GaragesBean, BaseViewHolder> {

    public GroupListHomeAdapter(int layoutResId, @Nullable List<DeviceGroupResultBean.GaragesBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, DeviceGroupResultBean.GaragesBean item) {
        TextView tvGroupName = helper.getView(R.id.tv_group_name);
        tvGroupName.setText(item.getSname());
        helper.setText(R.id.tv_device_number, "(" + item.getImei_count() + mContext.getString(R.string.txt_device_unit) + ")");
        if (item.isSelect()){
            tvGroupName.setTextColor(mContext.getResources().getColor(R.color.color_00A7FF));
        }else{
            tvGroupName.setTextColor(mContext.getResources().getColor(R.color.color_333333));
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
    }

}
