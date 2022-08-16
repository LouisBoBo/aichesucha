package com.car.scth.mvp.ui.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.car.scth.R;
import com.car.scth.mvp.model.bean.AccountListResultBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 用户管理，转移用户列表
 */
public class AccountRoleTransferAdapter extends BaseQuickAdapter<AccountListResultBean.InfoBean, BaseViewHolder> {

    public AccountRoleTransferAdapter(int layoutResId, @Nullable List<AccountListResultBean.InfoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, AccountListResultBean.InfoBean item) {
        helper.setText(R.id.tv_name, item.getAccount());
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
