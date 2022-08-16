package com.car.scth.mvp.weiget;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.car.scth.R;
import com.car.scth.mvp.model.bean.RecordConfigResultBean;
import com.car.scth.mvp.ui.adapter.RecordSelectTimeAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;


/**
 * 录音时长选择
 */
public class RecordTimeSelectDialog extends DialogFragment {

    private ListView mListView;
    private TextView tvDismiss;
    // 录音时长
    private List<RecordConfigResultBean.DataBean> timeBeans;
    private RecordSelectTimeAdapter timeAdapter;
    private onRecordTimeChange timeChange;
    private boolean isExpire; // 是否过期-录音增值服务

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ViewGroup viewGroup = (ViewGroup) View.inflate(getActivity(), R.layout.dialog_record_time_select, null);
        dialog.setContentView(viewGroup);
        dialog.setCanceledOnTouchOutside(true);
        initView(dialog);
        return dialog;
    }

    private void initView(Dialog dialog) {
        try {
            Window window = dialog.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.BOTTOM;
            window.setAttributes(params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mListView = dialog.findViewById(R.id.listView);
        tvDismiss = dialog.findViewById(R.id.tv_cancel);

        timeAdapter = new RecordSelectTimeAdapter(getActivity(), R.layout.item_record_select_time, timeBeans);
        mListView.setAdapter(timeAdapter);

        tvDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RecordConfigResultBean.DataBean bean = timeBeans.get(position);
                if (bean.isIs_appreciation()){
                    if (isExpire){
                        if (timeChange != null){
                            timeChange.onRecordPayOpenSelect(bean.getType(), 1, bean.getTime());
                        }
                    }else{
                        if (bean.isIs_opt()){
                            if (timeChange != null){
                                timeChange.onRecordRimeSelect(bean.getTime());
                            }
                        }else{
                            if (timeChange != null){
                                timeChange.onRecordPayOpenSelect(bean.getType(), 0, bean.getTime());
                            }
                        }
                    }
                }else{
                    if (timeChange != null){
                        timeChange.onRecordRimeSelect(bean.getTime());
                    }
                }
                dismiss();
            }
        });
    }

    public void show(FragmentManager manager, List<RecordConfigResultBean.DataBean> datas, boolean expire, onRecordTimeChange change){
        if (isAdded()){
            dismiss();
        }
        this.timeBeans = datas;
        this.isExpire = expire;
        this.timeChange = change;
        super.show(manager, "RecordTimeSelectDialog");
    }

    public interface onRecordTimeChange{

        /**
         * 录音增值服务过期或未开通
         * @param id 录音id，可用作购买增值服务id
         * @param type  0：开通增值服务，1：续费
         * @param recordTime 录音时长
         */
        void onRecordPayOpenSelect(int id, int type, int recordTime);

        /**
         * 选择录音时间
         * @param recordTime 下发给服务器的录音时长
         */
        void onRecordRimeSelect(int recordTime);

    }

}
