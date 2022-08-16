package com.car.scth.mvp.weiget;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.lbsapi.tools.CoordinateConverter;
import com.baidu.lbsapi.tools.Point;
import com.blankj.utilcode.util.ToastUtils;
import com.car.scth.R;
import com.car.scth.mvp.model.bean.NavigationBean;
import com.car.scth.mvp.ui.adapter.NavigationAdapter;
import com.car.scth.mvp.utils.Utils;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;


/**
 * 导航
 */
public class NavigationDialog extends DialogFragment {

    private ListView listView;
    private TextView tvCancel;
    private List<NavigationBean> navigationBeans; // 导航选择
    private NavigationAdapter mAdapter;
    private String deviceLatitude; // 设备纬度
    private String deviceLongitude; // 设备经度

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ViewGroup viewGroup = (ViewGroup) View.inflate(getActivity(), R.layout.dialog_navigation, null);
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

        listView = dialog.findViewById(R.id.listView);
        tvCancel = dialog.findViewById(R.id.tv_cancel);

        setNavigationMapData();

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NavigationBean bean = navigationBeans.get(position);
                if (bean.isHasApp()) {
                    onSelectNavigation(bean.getId());
                } else {
                    onSelectError(bean.getId());
                }
            }
        });
    }

    /**
     * 设置地图app数据
     */
    private void setNavigationMapData() {
        navigationBeans = new ArrayList<>();
        if (!Utils.isPkgInstalled(getActivity(), "com.autonavi.minimap")) {
            navigationBeans.add(new NavigationBean(0, getString(R.string.txt_amap_not_install), false));
        } else {
            navigationBeans.add(new NavigationBean(0, getString(R.string.txt_amap_map), true));
        }

        if (!Utils.isPkgInstalled(getActivity(), "com.baidu.BaiduMap")) {
            navigationBeans.add(new NavigationBean(1, getString(R.string.txt_baidu_not_install), false));
        } else {
            navigationBeans.add(new NavigationBean(1, getString(R.string.txt_baidu_map), true));
        }

        if (!Utils.isPkgInstalled(getActivity(), "com.tencent.map")) {
            navigationBeans.add(new NavigationBean(2, getString(R.string.txt_tencent_not_install), false));
        } else {
            navigationBeans.add(new NavigationBean(2, getString(R.string.txt_tencent_map), true));
        }

        mAdapter = new NavigationAdapter(getContext(), R.layout.item_navigation, navigationBeans);
        listView.setAdapter(mAdapter);
    }

    /**
     * 调起导航
     *
     * @param id
     */
    private void onSelectNavigation(int id) {
        Intent intent;
        if (id == 0) {
            try {
                //调用高德的外部导航
                intent = new Intent("android.intent.action.VIEW",
                        Uri.parse("androidamap://navi?sourceApplication="
                                + getString(R.string.app_name) + "&lat="
                                + deviceLatitude + "&lon="
                                + deviceLongitude + "&dev=0"));
                intent.setPackage("com.autonavi.minimap");
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (id == 1) {
            try {
              //调用百度SDK   将高德经纬度转换为百度经纬度   然后调用百度的外部导航
                Point sourcePoint = new Point(Double.parseDouble(deviceLongitude), Double.parseDouble(deviceLatitude));
                Point resultPointLL = CoordinateConverter.converter(CoordinateConverter.COOR_TYPE.COOR_TYPE_GCJ02, sourcePoint);
                intent = Intent.getIntent("intent://map/navi?location=" + resultPointLL.y + "," + resultPointLL.x + "&src=thirdapp.navi." + getString(R.string.app_name) + "#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                startActivity(intent); //启动调用
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            Uri uri = Uri.parse("qqmap://map/routeplan?type=drive&to="
                    + getString(R.string.txt_my_dst) + "&tocoord=" + deviceLatitude + "," + deviceLongitude);
            intent.setData(uri);
            intent.setPackage("com.tencent.map");
            startActivity(intent);
        }
        dismiss();
    }

    /**
     * 地图未安装，弹出提示
     *
     * @param id
     */
    private void onSelectError(int id) {
        switch (id) {
            case 0:
                ToastUtils.showShort(getString(R.string.txt_not_install_amap));
                break;
            case 1:
                ToastUtils.showShort(getString(R.string.txt_not_install_baidu));
                break;
            case 2:
                ToastUtils.showShort(getString(R.string.txt_not_install_tencent));
                break;
        }
    }

    public void show(FragmentManager manager, String latitude, String longitude) {
        if (isAdded()) {
            dismiss();
        }
        this.deviceLatitude = latitude;
        this.deviceLongitude = longitude;
        super.show(manager, "NavigationDialog");
    }

}
