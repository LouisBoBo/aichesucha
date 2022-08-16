package com.car.scth.mvp.ui.adapter;

import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import com.car.scth.R;
import com.car.scth.mvp.utils.ResultDataUtils;


/**
 * 登录账号列表
 */
public class LoginAccountListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public LoginAccountListAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        TextView tvAccount = helper.getView(R.id.tv_account);
        switch (item){
            case ResultDataUtils.Login_type_Account:
            case ResultDataUtils.Login_type_Phone_Account:
                tvAccount.setText(mContext.getString(R.string.txt_account_login));
                break;
            case ResultDataUtils.Login_type_Device:
            case ResultDataUtils.Login_type_Phone_Device:
                tvAccount.setText(mContext.getString(R.string.txt_device_login));
                break;
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
    }
}
