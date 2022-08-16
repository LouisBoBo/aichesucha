package com.car.scth.mvp.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;


import com.car.scth.R;
import com.car.scth.mvp.model.bean.ListenMobileBean;

import java.util.List;

/**
 * 添加白名单 不能用复用控件
 */
public class ListenMobileAdapter extends BaseAdapter {

    private onListenMobileChange mobileChange;

    private Context mContext;
    List<ListenMobileBean> mobileBeans;
    public ListenMobileAdapter(Context mContext, List<ListenMobileBean> list){
        this.mContext = mContext;
        this.mobileBeans = list;

    }
    @Override
    public int getCount() {
        return mobileBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return mobileBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(mContext,  R.layout.item_listen_mobie, null);
        TextView tvMobile = convertView.findViewById(R.id.tv_mobile);
        ListenMobileBean item =mobileBeans.get(position);
        tvMobile.setText(mContext.getString(R.string.txt_mobile_hint) + item.getIndex() + ":");
        EditText edtMobile = convertView.findViewById(R.id.edt_mobile);
        if(!TextUtils.isEmpty(item.getMobile())) {
            edtMobile.setText(item.getMobile());
            edtMobile.setSelection(item.getMobile().length());
        }
        edtMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String mobile = s.toString().trim();
                if (mobileChange != null){
                    mobileChange.onAddMobile(position, mobile);
                }
            }
        });
        return convertView;
    }

    public void setListenMobileChange(onListenMobileChange change){
        this.mobileChange = change;
    }

    public interface onListenMobileChange{

        /**
         * 添加号码
         * @param position 索引
         * @param mobile 号码
         */
        void onAddMobile(int position, String mobile);

    }

}
