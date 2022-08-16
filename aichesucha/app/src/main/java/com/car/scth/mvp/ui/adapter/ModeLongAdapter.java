package com.car.scth.mvp.ui.adapter;

import android.content.Context;

import com.car.scth.R;
import com.car.scth.mvp.model.bean.LocationModeGetResultBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 设置定位模式-省电模式，选择间隔时长
 */
public class ModeLongAdapter extends CommonAdapter<LocationModeGetResultBean.SavePowerBean> {

    public ModeLongAdapter(Context context, int layoutId, List<LocationModeGetResultBean.SavePowerBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, LocationModeGetResultBean.SavePowerBean item, int position) {
        viewHolder.setText(R.id.tv_long, item.getName());
    }
}
