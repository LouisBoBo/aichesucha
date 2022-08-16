package com.car.scth.mvp.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.car.scth.R;
import com.car.scth.mvp.model.bean.DeviceFunctionBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 设备功能表
 */
public class DeviceFunctionAdapter extends CommonAdapter<DeviceFunctionBean> {

    public DeviceFunctionAdapter(Context context, int layoutId, List<DeviceFunctionBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, DeviceFunctionBean item, int position) {
        ImageView ivPath = viewHolder.getView(R.id.iv_path);
        ivPath.setImageResource(item.getDrawableId());
        viewHolder.setText(R.id.tv_function_name, item.getFunctionName());
    }
}
