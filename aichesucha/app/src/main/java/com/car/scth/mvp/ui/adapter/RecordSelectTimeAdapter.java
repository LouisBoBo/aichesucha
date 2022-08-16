package com.car.scth.mvp.ui.adapter;

import android.content.Context;
import android.widget.TextView;

import com.car.scth.R;
import com.car.scth.mvp.model.bean.RecordConfigResultBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 选择录音时长
 */
public class RecordSelectTimeAdapter extends CommonAdapter<RecordConfigResultBean.DataBean> {

    public RecordSelectTimeAdapter(Context context, int layoutId, List<RecordConfigResultBean.DataBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, RecordConfigResultBean.DataBean item, int position) {
        TextView tvTime = viewHolder.getView(R.id.tv_time);
        tvTime.setText(item.getName());
        if (item.isIs_appreciation()){
            if (item.isIs_opt()){
                tvTime.setTextColor(mContext.getResources().getColor(R.color.color_000000));
            }else{
                tvTime.setTextColor(mContext.getResources().getColor(R.color.color_999999));
            }
        }else{
            tvTime.setTextColor(mContext.getResources().getColor(R.color.color_000000));
        }
    }
}
