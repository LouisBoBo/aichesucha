package com.car.scth.mvp.ui.adapter;

import android.content.Context;
import android.widget.TextView;

import com.car.scth.R;
import com.car.scth.mvp.model.bean.ShakeValueBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 震动报警灵敏度
 */
public class ShakeSensitivityAdapter extends CommonAdapter<ShakeValueBean> {

    public ShakeSensitivityAdapter(Context context, int layoutId, List<ShakeValueBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, ShakeValueBean item, int position) {
        TextView tvValue = viewHolder.getView(R.id.tv_value);
        tvValue.setText(item.getSensitivityName());
        if (item.isSelect()) {
            tvValue.setBackground(mContext.getResources().getDrawable(R.drawable.bg_00a7ff_4));
            tvValue.setTextColor(mContext.getResources().getColor(R.color.color_FFFFFF));
        } else {
            tvValue.setBackgroundColor(mContext.getResources().getColor(R.color.color_F4F4F4));
            tvValue.setTextColor(mContext.getResources().getColor(R.color.color_333333));
        }
    }
}
