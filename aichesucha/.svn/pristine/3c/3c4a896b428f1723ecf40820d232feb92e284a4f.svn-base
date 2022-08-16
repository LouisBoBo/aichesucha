package com.car.scth.mvp.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.car.scth.R;
import com.car.scth.mvp.model.bean.AccountListResultBean;
import com.car.scth.mvp.utils.ResultDataUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 用户管理，查看用户列表
 */
public class AccountListAdapter extends BaseQuickAdapter<AccountListResultBean.InfoBean, BaseViewHolder> {

    public AccountListAdapter(int layoutResId, @Nullable List<AccountListResultBean.InfoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, AccountListResultBean.InfoBean item) {
        helper.setText(R.id.tv_name, item.getAccount());
        ImageView ivTransfer = helper.getView(R.id.iv_transfer);
        ImageView ivDelete = helper.getView(R.id.iv_delete);
        if (item.getRole().equals(ResultDataUtils.Account_Type_Manager)){
            ivTransfer.setVisibility(View.VISIBLE);
        }else{
            ivTransfer.setVisibility(View.GONE);
        }
        if (item.isIs_mine()){
            ivDelete.setVisibility(View.GONE);
        }else{
            ivDelete.setVisibility(View.VISIBLE);
        }

        helper.addOnClickListener(R.id.iv_transfer);
        helper.addOnClickListener(R.id.iv_edit);
        helper.addOnClickListener(R.id.iv_delete);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
    }
}
