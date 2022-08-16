package com.car.scth.mvp.weiget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
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
 * 用户管理-菜单栏
 */
public class UserManagePopupWindowDown extends PopupWindow {

    private Context mContext;
    private RecyclerView recyclerView;
    private LinearLayout llParent;
    private List<GroupManageMenuBean> menuBeans;
    private GroupManageMenuAdapter mAdapter;
    private onManageMenuChange menuChange;

    /**
     * 初始化
     * @param context
     * @param level 层级，0是最上级组织，其他是下级组织
     */
    public UserManagePopupWindowDown(Context context, int level){
        super(context);
        this.mContext = context;
        View root = View.inflate(context, R.layout.popupwindow_user_manage, null);
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

        initData(level);
    }

    private void initData(int level){
        String familyAuth = ConstantValue.getFamilyAuth();
        menuBeans = new ArrayList<>();
        menuBeans.add(new GroupManageMenuBean(0, mContext.getString(R.string.txt_family_view_user), R.drawable.icon_family_view_user));
        if (familyAuth.contains(ResultDataUtils.Family_Auth_19)){
            menuBeans.add(new GroupManageMenuBean(1, mContext.getString(R.string.txt_family_add_next), R.drawable.icon_family_add_next));
        }
//        menuBeans.add(new GroupManageMenuBean(2, mContext.getString(R.string.txt_family_bind_car), R.drawable.icon_family_bind_car));
        if (familyAuth.contains(ResultDataUtils.Family_Auth_12)){
            menuBeans.add(new GroupManageMenuBean(3, mContext.getString(R.string.txt_edit), R.drawable.icon_manage_menu_2));
            if (level != 0){
                menuBeans.add(new GroupManageMenuBean(4, mContext.getString(R.string.txt_delete), R.drawable.icon_manage_menu_1));
            }
        }

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
        if (Build.VERSION.SDK_INT >= 24) {
            Rect visibleFrame = new Rect();
            anchor.getGlobalVisibleRect(visibleFrame);
            int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            setHeight(height);
        }
        super.showAsDropDown(anchor, xoff, yoff);
    }

    public void setManageMenuChange(onManageMenuChange change){
        this.menuChange = change;
    }

    public interface onManageMenuChange{

        /**
         * 选择的功能
         * @param id 0:查看用户，1：添加下级，2：绑定车组，3：编辑，4：删除
         */
        void onMenuSelect(int id);

    }

}
