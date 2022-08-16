package com.car.scth.mvp.ui.adapter;

import android.content.Context;

import com.car.scth.R;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 扫码二维码
 */
public class ScanQrCodeAdapter extends CommonAdapter<String> {

    public ScanQrCodeAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, String item, int position) {
        viewHolder.setText(R.id.tv_imei, item);
    }
}
