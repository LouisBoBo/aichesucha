package com.car.scth.mvp.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.car.scth.R;
import com.car.scth.mvp.model.bean.RechargePayBean;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 支付类型
 */
public class RechargePayAdapter extends CommonAdapter<RechargePayBean> {

    public RechargePayAdapter(Context context, int layoutId, List<RechargePayBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, RechargePayBean item, int position) {
        ImageView ivPay = viewHolder.getView(R.id.iv_pay);
        ImageView ivSelect = viewHolder.getView(R.id.iv_select);
        viewHolder.setText(R.id.tv_pay, item.getName());
        ivPay.setImageResource(item.getDrawableId());
        if (item.isSelect()){
            ivSelect.setImageResource(R.drawable.icon_select_big);
        }else{
            ivSelect.setImageResource(R.drawable.icon_unselect_big);
        }
    }
}
