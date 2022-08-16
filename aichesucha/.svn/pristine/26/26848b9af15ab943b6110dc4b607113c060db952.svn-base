package com.car.scth.mvp.ui.adapter;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.car.scth.R;
import com.car.scth.mvp.model.bean.AlarmRecordResultBean;
import com.car.scth.mvp.utils.DateUtils;
import com.car.scth.mvp.utils.ResultDataUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 报警消息个人版
 */
public class AlarmRecordUserAdapter extends BaseQuickAdapter<AlarmRecordResultBean.ItemsBean, BaseViewHolder> {

    public AlarmRecordUserAdapter(int layoutResId, @Nullable List<AlarmRecordResultBean.ItemsBean> data) {
        super(layoutResId, data);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(@NonNull BaseViewHolder helper, AlarmRecordResultBean.ItemsBean item) {
        if (item.getType().equals(ResultDataUtils.Alarm_Out_Fence) || item.getType().equals(ResultDataUtils.Alarm_In_Fence)) {
            helper.setText(R.id.tv_alarm, ResultDataUtils.getAlarmName(item.getType()) + "(" + mContext.getString(R.string.txt_fence_name) + "："
                    + item.getFencename() + ")");
        } else if (item.getType().equals(ResultDataUtils.Alarm_Speed) || item.getType().equals(ResultDataUtils.Alarm_Speeding_End)) {
            helper.setText(R.id.tv_alarm, ResultDataUtils.getAlarmName(item.getType())
                    + "(" + ((double) item.getDevspeed() / 10) + "km/h" + ")");
        } else {
            helper.setText(R.id.tv_alarm, ResultDataUtils.getAlarmName(item.getType()));
        }
        helper.setText(R.id.tv_device_name, mContext.getString(R.string.txt_name) + (!TextUtils.isEmpty(item.getNumber()) ? item.getNumber() : item.getImei()));
        helper.setText(R.id.tv_time, mContext.getString(R.string.txt_time) + DateUtils.timeConversionDate_two(String.valueOf(item.getTime())));
        TextView tvAddress = helper.getView(R.id.tv_address);
        tvAddress.setText(mContext.getString(R.string.txt_address) + item.getAddr());
        String address = item.getAddr();
        if (!TextUtils.isEmpty(address)) {
            // 开始判断，如果是经纬度，则启动app自解析地址
            address = address.replace(".", "");
            address = address.replace(",", "");
            address = address.replace("-", "");
            address = address.replace(" ", "");
            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher isNum = pattern.matcher(address);
            if (!isNum.matches()) {
                tvAddress.setTextColor(mContext.getResources().getColor(R.color.color_565656));
                tvAddress.setText(mContext.getString(R.string.txt_address) + address);
            } else {
                tvAddress.setTextColor(mContext.getResources().getColor(R.color.color_00A7FF));
                tvAddress.setText(mContext.getString(R.string.txt_address) + item.getAddr() + "，" + mContext.getString(R.string.txt_alarm_details));
            }
        }
        helper.addOnClickListener(R.id.ll_msg_delete);
        helper.addOnClickListener(R.id.tv_address);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
    }
}
