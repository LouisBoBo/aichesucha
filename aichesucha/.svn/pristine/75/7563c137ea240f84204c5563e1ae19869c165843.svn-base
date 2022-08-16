package com.car.scth.mvp.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.car.scth.R;
import com.car.scth.mvp.model.bean.FenceAlarmTypeBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 围栏报警类型
 */
public class FenceAlarmTypeAdapter extends CommonAdapter<FenceAlarmTypeBean> {

    public FenceAlarmTypeAdapter(Context context, int layoutId, List<FenceAlarmTypeBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, FenceAlarmTypeBean item, int position) {
        viewHolder.setText(R.id.tv_fence_alarm_type, item.getName());
        ImageView ivSelect = viewHolder.getView(R.id.iv_select);
        if (item.isSelect()){
            ivSelect.setImageResource(R.drawable.icon_select_big);
        }else{
            ivSelect.setImageResource(R.drawable.icon_unselect_big);
        }
    }
}
