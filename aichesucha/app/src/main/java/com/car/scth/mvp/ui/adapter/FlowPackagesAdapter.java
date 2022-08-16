package com.car.scth.mvp.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.car.scth.R;
import com.car.scth.mvp.model.bean.RechargePackagesResultBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * sim卡充值-流量套餐
 */
public class FlowPackagesAdapter extends CommonAdapter<RechargePackagesResultBean.ComeonBean> {

    public FlowPackagesAdapter(Context context, int layoutId, List<RechargePackagesResultBean.ComeonBean> datas) {
        super(context, layoutId, datas);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(ViewHolder viewHolder, RechargePackagesResultBean.ComeonBean item, int position) {
        LinearLayout llPackages = viewHolder.getView(R.id.ll_packages);
        TextView tvName = viewHolder.getView(R.id.tv_packages);
        TextView tvPrice = viewHolder.getView(R.id.tv_price);
        tvName.setText(item.getName());
        tvPrice.setText(item.getRenewals_price() + mContext.getString(R.string.txt_rmb));
        if (item.isSelect()){
            llPackages.setBackground(mContext.getResources().getDrawable(R.drawable.bg_00b4b7_6));
            tvName.setTextColor(mContext.getResources().getColor(R.color.color_00A7FF));
            tvPrice.setTextColor(mContext.getResources().getColor(R.color.color_00A7FF));
        }else{
            llPackages.setBackground(mContext.getResources().getDrawable(R.drawable.bg_ececec_6));
            tvName.setTextColor(mContext.getResources().getColor(R.color.color_333333));
            tvPrice.setTextColor(mContext.getResources().getColor(R.color.color_333333));
        }
    }
}
