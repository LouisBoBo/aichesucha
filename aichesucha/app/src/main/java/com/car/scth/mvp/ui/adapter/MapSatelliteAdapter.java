package com.car.scth.mvp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.car.scth.R;
import com.car.scth.mvp.model.bean.MapSatelliteBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 2D地图，卫星地图
 */
public class MapSatelliteAdapter extends CommonAdapter<MapSatelliteBean> {

    public MapSatelliteAdapter(Context context, int layoutId, List<MapSatelliteBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, MapSatelliteBean item, int position) {
        ImageView ivPath = viewHolder.getView(R.id.iv_map_type);
        ImageView ivSelect = viewHolder.getView(R.id.iv_select);
        TextView tvName = viewHolder.getView(R.id.tv_name);
        tvName.setText(item.getName());
        if (item.getId() == 0){
            if (item.isSelect()){
                ivPath.setImageResource(R.drawable.icon_2d_map_show);
            }else{
                ivPath.setImageResource(R.drawable.icon_2d_map);
            }
        }else{
            ivPath.setImageResource(R.drawable.icon_satellite_map);
        }
        if (item.isSelect()){
            ivSelect.setVisibility(View.VISIBLE);
            tvName.setTextColor(mContext.getResources().getColor(R.color.color_00A7FF));
        }else{
            ivSelect.setVisibility(View.GONE);
            tvName.setTextColor(mContext.getResources().getColor(R.color.color_333333));
        }
    }
}
