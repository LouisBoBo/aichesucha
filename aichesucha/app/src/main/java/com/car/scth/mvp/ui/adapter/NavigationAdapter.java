package com.car.scth.mvp.ui.adapter;

import android.content.Context;

import com.car.scth.R;
import com.car.scth.mvp.model.bean.NavigationBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 导航地图选择
 */
public class NavigationAdapter extends CommonAdapter<NavigationBean> {

    public NavigationAdapter(Context context, int layoutId, List<NavigationBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, NavigationBean item, int position) {
        viewHolder.setText(R.id.tv_navigation, item.getName());
    }
}
