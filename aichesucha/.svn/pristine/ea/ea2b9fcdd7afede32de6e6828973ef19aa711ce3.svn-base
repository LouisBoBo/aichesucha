package com.car.scth.mvp.weiget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.car.scth.R;
import com.car.scth.mvp.model.bean.GroupManageMenuBean;
import com.car.scth.mvp.ui.adapter.GroupManageMenuAdapter;
import com.car.scth.mvp.utils.ConstantValue;
import com.car.scth.mvp.utils.ResultDataUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 设备分组管理-菜单栏
 */
public class GroupManagePopupwindow extends PopupWindow {

    private Context mContext;
    private RecyclerView recyclerView;
    private LinearLayout llParent;
    private List<GroupManageMenuBean> menuBeans;
    private GroupManageMenuAdapter mAdapter;
    private onManageMenuChange menuChange;
    private int popupType = 0; // 触发类型，0：分组管理，1：设备管理

    public GroupManagePopupwindow(Context context, int type){
        super(context);
        this.mContext = context;
        this.popupType = type;
        View root = View.inflate(context, R.layout.popupwindow_group_manage, null);
        recyclerView = root.findViewById(R.id.recyclerView);
        llParent = root.findViewById(R.id.ll_parent);
        //设置SelectPicPopupWindow的View
        this.setContentView(root);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
//        setOutsideTouchable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        //this.setAnimationStyle(R.style.AnimBottom);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(new ColorDrawable(mContext.getResources().getColor(R.color.transparent)));

        initData();
    }

    private void initData(){
        String familyAuth = ConstantValue.getFamilyAuth();
        menuBeans = new ArrayList<>();
        if (!TextUtils.isEmpty(familyAuth)){
            if (popupType == 0){
                if (familyAuth.contains(ResultDataUtils.Family_Auth_0)){
                    menuBeans.add(new GroupManageMenuBean(0, mContext.getString(R.string.txt_manage_menu_0), R.drawable.icon_manage_menu_0));
                }
                if (familyAuth.contains(ResultDataUtils.Family_Auth_10)){
                    menuBeans.add(new GroupManageMenuBean(7, mContext.getString(R.string.txt_remove_device), R.drawable.icon_remove_device));
                }
                if (familyAuth.contains(ResultDataUtils.Family_Auth_13)){
                    menuBeans.add(new GroupManageMenuBean(2, mContext.getString(R.string.txt_manage_menu_2), R.drawable.icon_manage_menu_1));
                }
                if (familyAuth.contains(ResultDataUtils.Family_Auth_11)){
                    menuBeans.add(new GroupManageMenuBean(3, mContext.getString(R.string.txt_manage_menu_3), R.drawable.icon_manage_menu_2));
                    menuBeans.add(new GroupManageMenuBean(4, mContext.getString(R.string.txt_manage_menu_4), R.drawable.icon_manage_menu_3));
                }
            }else if (popupType == 1){
                if (familyAuth.contains(ResultDataUtils.Family_Auth_0)){
                    menuBeans.add(new GroupManageMenuBean(0, mContext.getString(R.string.txt_manage_menu_0), R.drawable.icon_manage_menu_0));
                }
                if (familyAuth.contains(ResultDataUtils.Family_Auth_10)){
                    menuBeans.add(new GroupManageMenuBean(7, mContext.getString(R.string.txt_remove_device), R.drawable.icon_remove_device));
                }
            }else if (popupType == 2){
                if (familyAuth.contains(ResultDataUtils.Family_Auth_5)){
                    menuBeans.add(new GroupManageMenuBean(21, mContext.getString(R.string.txt_restore_to_original_account), R.drawable.icon_restore_to_original_account));
                }
                if (familyAuth.contains(ResultDataUtils.Family_Auth_9)){
                    menuBeans.add(new GroupManageMenuBean(7, mContext.getString(R.string.txt_manage_menu_1_1), R.drawable.icon_remove_device));
                }
            }
        }
//        else{
//            if (popupType == 0){
//                menuBeans.add(new GroupManageMenuBean(0, mContext.getString(R.string.txt_manage_menu_0), R.drawable.icon_manage_menu_0));
//                menuBeans.add(new GroupManageMenuBean(6, mContext.getString(R.string.txt_freeze_equipment), R.drawable.icon_freeze_equipment));
//                menuBeans.add(new GroupManageMenuBean(7, mContext.getString(R.string.txt_remove_device), R.drawable.icon_remove_device));
//                menuBeans.add(new GroupManageMenuBean(2, mContext.getString(R.string.txt_manage_menu_2), R.drawable.icon_manage_menu_1));
//                menuBeans.add(new GroupManageMenuBean(3, mContext.getString(R.string.txt_manage_menu_3), R.drawable.icon_manage_menu_2));
//                menuBeans.add(new GroupManageMenuBean(4, mContext.getString(R.string.txt_manage_menu_4), R.drawable.icon_manage_menu_3));
//                menuBeans.add(new GroupManageMenuBean(5, mContext.getString(R.string.txt_manage_menu_5), R.drawable.icon_manage_menu_4));
//            }else if (popupType == 1){
//                menuBeans.add(new GroupManageMenuBean(0, mContext.getString(R.string.txt_manage_menu_0), R.drawable.icon_manage_menu_0));
//                menuBeans.add(new GroupManageMenuBean(6, mContext.getString(R.string.txt_freeze_equipment), R.drawable.icon_freeze_equipment));
//                menuBeans.add(new GroupManageMenuBean(7, mContext.getString(R.string.txt_remove_device), R.drawable.icon_remove_device));
//            }else if (popupType == 2){
//                menuBeans.add(new GroupManageMenuBean(21, mContext.getString(R.string.txt_restore_to_original_account), R.drawable.icon_restore_to_original_account));
//                menuBeans.add(new GroupManageMenuBean(7, mContext.getString(R.string.txt_remove), R.drawable.icon_remove_device));
//            }
//        }
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new GroupManageMenuAdapter(R.layout.item_group_manage_menu, menuBeans);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (menuChange != null){
                    menuChange.onMenuSelect(menuBeans.get(position).getId());
                }
            }
        });
        llParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    /**
     * 兼容 android 7.0之后设置showAsDropDown失效问题
     * @param anchor
     */
    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect visibleFrame = new Rect();
            anchor.getGlobalVisibleRect(visibleFrame);
            int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            setHeight(height);
        }
        super.showAsDropDown(anchor);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
            int[] a = new int[2];
            anchor.getLocationInWindow(a);
            showAtLocation(anchor, Gravity.NO_GRAVITY, xoff, a[1] + anchor.getHeight() + yoff);
        } else {
            super.showAsDropDown(anchor, xoff, yoff);
        }
    }

    public void setManageMenuChange(onManageMenuChange change){
        this.menuChange = change;
    }

    public interface onManageMenuChange{

        /**
         * 选择的功能
         * @param id 0:转移，1：删除，2：编辑分组，3：添加分组，4：添加设备
         */
        void onMenuSelect(int id);

    }

}
