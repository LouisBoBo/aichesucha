package com.car.scth.mvp.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.car.scth.R;
import com.car.scth.mvp.model.bean.DeviceGroupResultBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 设备管理-分组列表
 */
public class GroupListManageAdapter extends BaseQuickAdapter<DeviceGroupResultBean.GaragesBean, BaseViewHolder> {

    public GroupListManageAdapter(int layoutResId, @Nullable List<DeviceGroupResultBean.GaragesBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, DeviceGroupResultBean.GaragesBean item) {
        ImageView ivSelect = helper.getView(R.id.iv_select);
        helper.setText(R.id.tv_name, item.getSname() + "(" + item.getImei_count() + mContext.getString(R.string.txt_device_unit) + ")");
        if (TextUtils.isEmpty(item.getSid())) {
            ivSelect.setVisibility(View.INVISIBLE);
        } else {
            ivSelect.setVisibility(View.VISIBLE);
            if (item.isSelect()) {
                ivSelect.setImageResource(R.mipmap.icon_device_list_select);
            } else {
                ivSelect.setImageResource(R.mipmap.icon_device_list_unselect);
            }
        }
        helper.addOnClickListener(R.id.iv_select);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
    }

}
