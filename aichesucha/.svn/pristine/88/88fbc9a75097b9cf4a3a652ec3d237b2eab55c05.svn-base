package com.car.scth.mvp.ui.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.car.scth.R;
import com.car.scth.mvp.model.bean.DataCenterBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 数据中心功能
 */
public class DataCenterAdapter extends BaseQuickAdapter<DataCenterBean, BaseViewHolder> {

    public DataCenterAdapter(int layoutResId, @Nullable List<DataCenterBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, DataCenterBean item) {
        ImageView ivPath = helper.getView(R.id.iv_path);
        ivPath.setImageResource(item.getDrawableId());
        helper.setText(R.id.tv_function, item.getFunction());
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
    }
}
