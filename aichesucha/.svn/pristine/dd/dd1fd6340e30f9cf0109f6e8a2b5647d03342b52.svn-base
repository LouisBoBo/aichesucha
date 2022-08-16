package com.car.scth.mvp.ui.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.car.scth.R;
import com.car.scth.mvp.model.bean.AlarmScreenBean;

import java.util.List;

/**
 * 报警消息筛选bean
 */
public class AlarmScreenAdapter extends BaseQuickAdapter<AlarmScreenBean, BaseViewHolder> {

    public AlarmScreenAdapter(int layoutResId, @Nullable List<AlarmScreenBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, AlarmScreenBean item) {
        helper.setText(R.id.tv_alarm, item.getAlarmName());
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
