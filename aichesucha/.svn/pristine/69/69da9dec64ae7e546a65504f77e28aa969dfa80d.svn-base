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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.car.scth.R;
import com.car.scth.mvp.model.bean.MapTypeBean;
import com.car.scth.mvp.ui.adapter.MapTypeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择使用地图
 */
public class MapTypeDialog extends DialogFragment {

    private ListView listView;
    private TextView tvCancel;
    private List<MapTypeBean> mapTypeBeans;
    private MapTypeAdapter mAdapter;
    private onMapTypeChange mapTypeChange;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ViewGroup viewGroup = (ViewGroup) View.inflate(getActivity(), R.layout.dialog_map_type, null);
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

        mapTypeBeans = new ArrayList<>();
        mapTypeBeans.add(new MapTypeBean(0, getString(R.string.txt_map_amap)));
        mapTypeBeans.add(new MapTypeBean(1, getString(R.string.txt_map_baidu)));
//        mapTypeBeans.add(new MapTypeBean(2, getString(R.string.txt_map_google)));

        listView = dialog.findViewById(R.id.listView);
        tvCancel = dialog.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mAdapter = new MapTypeAdapter(getContext(), R.layout.item_map_type, mapTypeBeans);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (mapTypeBeans.get(position).getId().equals("2")){
//                    if(!Utils.isPkgInstalled(MyApplication.getInstance(), "com.google.android.apps.maps")) {
//                        ToastUtils.show(getString(R.string.new_not_install_google_map));
//                        return;
//                    }
//
//                    if(!Utils.isInstallService(getActivity())){
//                        ToastUtils.show(getString(R.string.new_google_server));
//                        return;
//                    }
//                }
                if (mapTypeChange != null){
                    mapTypeChange.onMapTypeSelect(mapTypeBeans.get(position).getId(), mapTypeBeans.get(position).getMapName());
                }
                dismiss();
            }
        });
    }

    public void show(FragmentManager manager, onMapTypeChange change){
        if (isAdded()){
            dismiss();
        }
        this.mapTypeChange = change;
        super.show(manager, "MapTypeDialog");
    }

    public interface onMapTypeChange{

        /**
         * 选择的地图
         * @param id
         * @param name
         */
        void onMapTypeSelect(int id, String name);

    }

}
