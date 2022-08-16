package com.car.scth.mvp.ui.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.car.scth.R;
import com.car.scth.mvp.model.bean.GroupManageMenuBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 分组管理-菜单栏
 */
public class GroupManageMenuAdapter extends BaseQuickAdapter<GroupManageMenuBean, BaseViewHolder> {

    public GroupManageMenuAdapter(int layoutResId, @Nullable List<GroupManageMenuBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, GroupManageMenuBean item) {
        helper.setText(R.id.tv_menu, item.getName());
        ImageView ivMenu = helper.getView(R.id.iv_menu);
        ivMenu.setImageResource(item.getDrawableId());
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
    }
}
