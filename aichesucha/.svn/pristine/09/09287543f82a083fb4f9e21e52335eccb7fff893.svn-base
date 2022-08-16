package com.car.scth.mvp.ui.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.car.scth.R;
import com.car.scth.mvp.model.bean.LoopDayBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 周期定位-重复周期
 */
public class LoopDayAdapter extends BaseQuickAdapter<LoopDayBean, BaseViewHolder> {

    public LoopDayAdapter(int layoutResId, @Nullable List<LoopDayBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, LoopDayBean item) {
        helper.setText(R.id.tv_day, item.getDayName());
        ImageView ivSelect = helper.getView(R.id.iv_select);
        ivSelect.setImageResource(item.isSelect() ? R.drawable.icon_select_big : R.drawable.icon_unselect_big);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
    }

}
