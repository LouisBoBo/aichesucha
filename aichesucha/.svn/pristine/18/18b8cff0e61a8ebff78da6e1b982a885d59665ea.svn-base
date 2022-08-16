package com.car.scth.mvp.ui.adapter;

import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.car.scth.R;
import com.car.scth.mvp.model.bean.OperationRecordResultBean;
import com.car.scth.mvp.utils.DateUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 操作记录
 */
public class OperationAdapter extends BaseQuickAdapter<OperationRecordResultBean.ItemsBean, BaseViewHolder> {

    public OperationAdapter(int layoutResId, @Nullable List<OperationRecordResultBean.ItemsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, OperationRecordResultBean.ItemsBean item) {
        helper.setText(R.id.tv_account, mContext.getString(R.string.txt_account) + item.getAccount());
        helper.setText(R.id.tv_name, mContext.getString(R.string.txt_name) + item.getImei());
        helper.setText(R.id.tv_time, mContext.getString(R.string.txt_time) + DateUtils.timeConversionDate_three(String.valueOf(item.getTime())));
        helper.setText(R.id.tv_remark, mContext.getString(R.string.txt_remark) + item.getRemark());
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
    }
}
