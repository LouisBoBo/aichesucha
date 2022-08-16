package com.car.scth.mvp.ui.adapter;

import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import com.car.scth.R;
import com.car.scth.mvp.model.bean.DataCenterBean;

/**
 * 设置中心功能
 */
public class FunctionSetAdapter extends BaseQuickAdapter<DataCenterBean, BaseViewHolder> {

    public FunctionSetAdapter(int layoutResId, @Nullable List<DataCenterBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, DataCenterBean item) {
        helper.setText(R.id.tv_menu, item.getFunction());
        helper.setImageResource(R.id.iv_menu,item.getDrawableId());
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
    }
}
