package com.car.scth.mvp.ui.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.car.scth.R;
import com.car.scth.mvp.model.bean.LoopLocationModeResultBean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 周期定位列表
 */
public class LoopLocationModeAdapter extends BaseQuickAdapter<LoopLocationModeResultBean.ItemsBean, BaseViewHolder> {

    public LoopLocationModeAdapter(int layoutResId, @Nullable List<LoopLocationModeResultBean.ItemsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, LoopLocationModeResultBean.ItemsBean item) {
        helper.setText(R.id.tv_time, item.getInfo().getLoc_value());
        ImageView ivSwitch = helper.getView(R.id.iv_switch);
        ivSwitch.setImageResource(item.getInfo().isLoc_switch() ? R.mipmap.icon_on_message : R.mipmap.icon_off_message);
        List<String> dayDatas = new ArrayList<>();
        dayDatas.add(item.getInfo().getLoop_loc_name());
        if (item.getInfo().getRp_loop_loc() != null){
            if (item.getInfo().getRp_loop_loc().size() == 7){
                dayDatas.add(mContext.getString(R.string.txt_every_day));
            }else{
                for (int i = 0; i < item.getInfo().getRp_loop_loc().size(); i++){
                    switch (item.getInfo().getRp_loop_loc().get(i)){
                        case 0:
                            dayDatas.add(mContext.getString(R.string.txt_sunday));
                            break;
                        case 1:
                            dayDatas.add(mContext.getString(R.string.txt_monday));
                            break;
                        case 2:
                            dayDatas.add(mContext.getString(R.string.txt_tuesday));
                            break;
                        case 3:
                            dayDatas.add(mContext.getString(R.string.txt_wednesday));
                            break;
                        case 4:
                            dayDatas.add(mContext.getString(R.string.txt_thursday));
                            break;
                        case 5:
                            dayDatas.add(mContext.getString(R.string.txt_friday));
                            break;
                        case 6:
                            dayDatas.add(mContext.getString(R.string.txt_saturday));
                            break;
                    }
                }
            }
        }
        helper.setText(R.id.tv_data, dayDatas.toString().replace("[", "").replace("]", ""));
        helper.addOnClickListener(R.id.iv_switch);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
    }

}
