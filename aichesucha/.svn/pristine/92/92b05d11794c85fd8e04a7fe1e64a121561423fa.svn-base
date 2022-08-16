package com.car.scth.mvp.ui.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.car.scth.R;
import com.car.scth.mvp.model.bean.RecycleBinResultBean;
import com.car.scth.mvp.utils.DateUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 回收站列表
 */
public class RecycleBinAdapter extends BaseQuickAdapter<RecycleBinResultBean.ItemsBean, BaseViewHolder> {

    public RecycleBinAdapter(int layoutResId, @Nullable List<RecycleBinResultBean.ItemsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, RecycleBinResultBean.ItemsBean item) {
        helper.setText(R.id.tv_imei, mContext.getString(R.string.txt_imei_two) + item.getImei());
        helper.setText(R.id.tv_time, mContext.getString(R.string.txt_freeze_equipment_time)
                + DateUtils.timeConversionDate_two(String.valueOf(item.getRecovery_stat())));
        ImageView ivSelect = helper.getView(R.id.iv_select);
        if (item.isSelect()){
            ivSelect.setImageResource(R.drawable.icon_select_big);
        }else{
            ivSelect.setImageResource(R.drawable.icon_unselect_big);
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
    }

}
