package com.car.scth.mvp.ui.adapter;

import android.text.TextUtils;
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
 * 报警消息-设备筛选
 */
public class AlarmDeviceScreenAdapter extends BaseQuickAdapter<AlarmScreenDeviceBean, BaseViewHolder> {

    public AlarmDeviceScreenAdapter(int layoutResId, @Nullable List<AlarmScreenDeviceBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, AlarmScreenDeviceBean item) {
        helper.setText(R.id.tv_imei, !TextUtils.isEmpty(item.getName()) ? item.getName() : item.getImei() + "");
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
    }

}
