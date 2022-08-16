package com.car.scth.mvp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.car.scth.R;
import com.car.scth.mvp.model.bean.MapTypeBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 地图类型选择
 */
public class MapTypeAdapter extends CommonAdapter<MapTypeBean> {

    public MapTypeAdapter(Context context, int layoutId, List<MapTypeBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, MapTypeBean item, int position) {
        viewHolder.setText(R.id.tv_map_name, item.getMapName());
    }
}
