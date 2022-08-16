package com.car.scth.mvp.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.car.scth.R;
import com.car.scth.mvp.model.bean.IconCheckBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 切换设备图标
 */
public class IconCheckAdapter extends BaseQuickAdapter<IconCheckBean, BaseViewHolder> {

    public IconCheckAdapter(int layoutResId, @Nullable List<IconCheckBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, IconCheckBean item) {
        ImageView ivPath = helper.getView(R.id.iv_icon);
        ImageView ivSelect = helper.getView(R.id.iv_select);
        ivPath.setImageResource(item.getDrawableId());
        helper.setText(R.id.tv_icon_name, item.getName());
        if (item.isSelect()){
            ivSelect.setVisibility(View.VISIBLE);
        }else{
            ivSelect.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
    }
}


