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
import com.car.scth.mvp.model.bean.LocationModeGetResultBean;
import com.car.scth.mvp.ui.adapter.ModeLongAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

/**
 * 定位模式设置，设置省电模式dialog
 * Created by Administrator on 2019\5\17 0017.
 */

public class SelectModelLongDialog extends DialogFragment {

    private ListView mListView;
    private TextView tvDismiss;
    private List<LocationModeGetResultBean.SavePowerBean> modeLongBeans;
    private ModeLongAdapter mAdapter;
    private onSelectModeLongChange longChange;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ViewGroup viewGroup = (ViewGroup) View.inflate(getActivity(), R.layout.dialog_set_mode, null);
        dialog.setContentView(viewGroup);
        dialog.setCanceledOnTouchOutside(true);
        initView(dialog);
        return dialog;
    }

    private void initView(Dialog dialog){
        try{
            Window window = dialog.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.BOTTOM;
            window.setAttributes(params);
        }catch (Exception e){
            e.printStackTrace();
        }

        mListView = dialog.findViewById(R.id.listView);
        tvDismiss = dialog.findViewById(R.id.tv_cancel);
        mAdapter = new ModeLongAdapter(getActivity(), R.layout.item_mode_long, modeLongBeans);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int mode_long = modeLongBeans.get(position).getTime();
                String showLong = modeLongBeans.get(position).getName();
                if (longChange != null){
                    longChange.onSelectModeLong(mode_long, showLong);
                }
                dismiss();
            }
        });
        tvDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void show(FragmentManager manager, List<LocationModeGetResultBean.SavePowerBean> datas, onSelectModeLongChange change){
        if (isAdded()){
            dismiss();
        }
        this.modeLongBeans = datas;
        this.longChange = change;
        super.show(manager, "SelectModelLongDialog");
    }

    public interface onSelectModeLongChange{

        /**
         * 选择了省电模式
         * @param mode_long 时长
         * @param showLong 时长，用于显示
         */
        void onSelectModeLong(int mode_long, String showLong);

    }

}
