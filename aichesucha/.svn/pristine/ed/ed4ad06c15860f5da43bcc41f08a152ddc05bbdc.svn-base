package com.car.scth.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.blankj.utilcode.util.SPUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.car.scth.R;
import com.car.scth.app.MyApplication;
import com.car.scth.di.component.DaggerUserManagementComponent;
import com.car.scth.mvp.contract.UserManagementContract;
import com.car.scth.mvp.model.api.ModuleValueService;
import com.car.scth.mvp.model.bean.AlertBean;
import com.car.scth.mvp.model.bean.AlertCurrentBean;
import com.car.scth.mvp.model.bean.DeviceBaseResultBean;
import com.car.scth.mvp.model.bean.DeviceGroupResultBean;
import com.car.scth.mvp.model.bean.GetTaskResultBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.entity.TreeData;
import com.car.scth.mvp.model.putbean.DeleteFamilyPutBean;
import com.car.scth.mvp.model.putbean.DeviceGroupPutBean;
import com.car.scth.mvp.model.putbean.EditFamilyPutBean;
import com.car.scth.mvp.model.putbean.GetTaskPutBean;
import com.car.scth.mvp.presenter.UserManagementPresenter;
import com.car.scth.mvp.ui.adapter.TreeListAdapter;
import com.car.scth.mvp.utils.ConstantValue;
import com.car.scth.mvp.utils.PopupWindowUtil;
import com.car.scth.mvp.utils.ResultDataUtils;
import com.car.scth.mvp.utils.ToastUtils;
import com.car.scth.mvp.utils.Utils;
import com.car.scth.mvp.weiget.AlarmCurrentEditDialog;
import com.car.scth.mvp.weiget.AlertAppDialog;
import com.car.scth.mvp.weiget.DeviceFailDialog;
import com.car.scth.mvp.weiget.TaskProgressDialog;
import com.car.scth.mvp.weiget.UserManagePopupWindowDown;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 用户管理
 * <p>
 * Created by MVPArmsTemplate on 01/07/2021 10:27
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class UserManagementActivity extends BaseActivity<UserManagementPresenter> implements UserManagementContract.View {

    @BindView(R.id.tree_list)
    RecyclerView recyclerView;
    @BindView(R.id.srl_view)
    SwipeRefreshLayout srlView;

    private int mLimitSizeForAccount = 100; // 限定每页账号限制个数
    private boolean isRefresh;
    TreeListAdapter treeListAdapter;
    List<TreeData> treeData = new ArrayList<>();
    private String family_Sname;
    private String family_Sid;
    private String family_add_Sid; // 添加下级的 父级id
    private int parentIndex;
    private int parentLevel;
    private boolean isAddFamily;
    private String familyAddId;
    private String familyAddName;
    private TreeData treeParent;
    private UserManagePopupWindowDown userManagePopupWindowDown;//向下弹框

    private static final int mCountDownTime = 2; // 倒计时固定时长
    private int mCountDown = mCountDownTime; // 倒计时递减时长
    private String taskId; // 任务id
    private TaskProgressDialog taskProgressDialog;

    private PopupWindow mPopupWindow;
    private String familyAuth; // 功能

    private static final int Intent_Account_List = 12;

    public static Intent newInstance() {
        return new Intent(MyApplication.getMyApp(), UserManagementActivity.class);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerUserManagementComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_user_management;//setContentView(id);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_user_management));
        family_Sid = ConstantValue.getFamilySid();
        family_Sname = ConstantValue.getFamilySName();
        //首次获取一二级数据
        firstDefaultLevelData(true);
        familyAuth = ConstantValue.getFamilyAuth();

        treeListAdapter = new TreeListAdapter(this, treeData, new TreeListAdapter.FamilyAdapterListener() {
            @Override
            public void itemClick(String sid, int level) {
                getAccountList(false, sid, level);
            }

            @Override
            public void moreFamilyFunction(String sid, String name, View view, int level) {
                //更多功能
                showPopupWindow(sid, name, view, level);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        srlView.setColorSchemeResources(R.color.color_00A7FF, R.color.color_00A7FF);
        srlView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //首次获取一二级数据
                firstDefaultLevelData(true);
            }
        });

        recyclerView.setAdapter(treeListAdapter);

    }

    private void moreFunction(String sid, String name, View view, int level) {
        if (userManagePopupWindowDown != null && userManagePopupWindowDown.isShowing()) {
            userManagePopupWindowDown.dismiss();
        } else {
            userManagePopupWindowDown = new UserManagePopupWindowDown(this, level);
            userManagePopupWindowDown.setManageMenuChange(new UserManagePopupWindowDown.onManageMenuChange() {
                @Override
                public void onMenuSelect(int id) {
                    if (userManagePopupWindowDown != null && userManagePopupWindowDown.isShowing()) {
                        userManagePopupWindowDown.dismiss();
                    }
                    switch (id) {
                        case 0://查看用户
                            onIntentAccountList(sid, level);
                            break;
                        case 1://添加下级
                            family_add_Sid = sid;
                            //添加下一级
                            Intent intent = new Intent(UserManagementActivity.this, AddFamilyActivity.class);
                            intent.putExtra("familyId", sid);
                            startActivityForResult(intent, 201);
                            break;
                        case 2://绑定车组
                            ToastUtils.show("功能开发中，敬请期待...");
                            break;
                        case 3://编辑
                            edit(sid, name);
                            break;
                        case 4://删除
                            delete(sid, name);
                            break;
                    }

                }
            });
            userManagePopupWindowDown.showAsDropDown(view, 0, ArmsUtils.dip2px(this, -10));
        }

    }

    /**
     * 查看账号列表
     */
    private void onIntentAccountList(String sid, int level){
        Intent intent_account = new Intent(UserManagementActivity.this, AccountListActivity.class);
        intent_account.putExtra("familyId", sid);
        intent_account.putExtra("level", level);
        startActivityForResult(intent_account, Intent_Account_List);
    }

    /**
     * 弹出功能选择框
     * @param sid
     * @param name
     * @param view
     * @param level
     */
    private void showPopupWindow(String sid, String name, View view, int level) {
        View contentView = getPopupWindowContentView(sid, name, level);
        mPopupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        // 设置好参数之后再show
        int windowPos[] = PopupWindowUtil.calculatePopWindowPos(view, contentView);
        int xOff = 20; // 可以自己调整偏移
        windowPos[0] -= xOff;
        mPopupWindow.showAtLocation(view, Gravity.TOP | Gravity.START, windowPos[0], windowPos[1]);
    }

    /**
     * 功能选择
     * @param sid
     * @param name
     * @param level
     * @return
     */
    private View getPopupWindowContentView(String sid, String name, int level) {
        // 一个自定义的布局，作为显示的内容
        int layoutId = R.layout.layout_user_manager_function;   // 布局ID
        View contentView = LayoutInflater.from(this).inflate(layoutId, null);

        LinearLayout llAccount = contentView.findViewById(R.id.ll_account);
        LinearLayout llAddNextFamily = contentView.findViewById(R.id.ll_add_next_family);
        LinearLayout llEdit = contentView.findViewById(R.id.ll_edit);
        LinearLayout llDelete = contentView.findViewById(R.id.ll_delete);
        if (!familyAuth.contains(ResultDataUtils.Family_Auth_19)){
            llAddNextFamily.setVisibility(View.GONE);
        }
        if (familyAuth.contains(ResultDataUtils.Family_Auth_12)){
            llEdit.setVisibility(View.VISIBLE);
            if (level == 0){
                llDelete.setVisibility(View.GONE);
            }else{
                llDelete.setVisibility(View.VISIBLE);
            }
        }else{
            llEdit.setVisibility(View.GONE);
            llDelete.setVisibility(View.GONE);
        }
        llAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isButtonQuickClick(System.currentTimeMillis())){
                    // 查看用户
                    onIntentAccountList(sid, level);
                    if (mPopupWindow != null) {
                        mPopupWindow.dismiss();
                    }
                }
            }
        });
        llAddNextFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isButtonQuickClick(System.currentTimeMillis())){
                    // 添加下级
                    family_add_Sid = sid;
                    Intent intent = new Intent(UserManagementActivity.this, AddFamilyActivity.class);
                    intent.putExtra("familyId", sid);
                    startActivityForResult(intent, 201);
                    if (mPopupWindow != null) {
                        mPopupWindow.dismiss();
                    }
                }
            }
        });
        llEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isButtonQuickClick(System.currentTimeMillis())){
                    // 编辑
                    edit(sid, name);
                    if (mPopupWindow != null) {
                        mPopupWindow.dismiss();
                    }
                }
            }
        });
        llDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isButtonQuickClick(System.currentTimeMillis())){
                    // 删除
                    delete(sid, name);
                    if (mPopupWindow != null) {
                        mPopupWindow.dismiss();
                    }
                }
            }
        });
        return contentView;
    }

    //修改组织名称
    private void edit(String sid, String name) {
        AlertCurrentBean bean = new AlertCurrentBean();
        bean.setTitle(getString(R.string.txt_edit_family_name));
        bean.setHint(getString(R.string.txt_edit_family_name_hint));
        bean.setTextPassword(false);
        bean.setEditValue(name);
        final AlarmCurrentEditDialog dialog = new AlarmCurrentEditDialog();
        dialog.show(getSupportFragmentManager(), bean, new AlarmCurrentEditDialog.onAlartCurrentChange() {
            @Override
            public void onEditConfirm(String textValue) {
                submitEditFamilyName(sid, textValue);
            }

            @Override
            public void onEditCancel() {

            }
        });
    }

    private void submitEditFamilyName(String sid, String name) {
        EditFamilyPutBean.ParamsBean paramsBean = new EditFamilyPutBean.ParamsBean();
        paramsBean.setFamilyid(sid);
        paramsBean.setName(name);
        EditFamilyPutBean bean = new EditFamilyPutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Edit_Family_Name);
        bean.setModule(ModuleValueService.Module_For_Edit_Family_Name);
        bean.setParams(paramsBean);
        if (getPresenter() != null)
            getPresenter().submitEditFamilyName(bean);

    }

    //删除组织账号
    private void delete(String sid, String name) {
        AlertBean bean = new AlertBean();
        bean.setTitle(getString(R.string.txt_delete_family_tip) + name);
        bean.setAlert(getString(R.string.txt_delete_family_tip_confirm));
        bean.setType(0);
        AlertAppDialog dialog = new AlertAppDialog();
        dialog.show(getSupportFragmentManager(), bean, new AlertAppDialog.onAlertDialogChange() {
            @Override
            public void onConfirm() {
                submitDeleteFamily(sid);
            }

            @Override
            public void onCancel() {
            }
        });

    }

    private void submitDeleteFamily(String family_Sid) {
        DeleteFamilyPutBean.ParamsBean paramsBean = new DeleteFamilyPutBean.ParamsBean();
        paramsBean.setFamilyid(family_Sid);
        DeleteFamilyPutBean bean = new DeleteFamilyPutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Delete_Family_Name);
        bean.setModule(ModuleValueService.Module_For_Delete_Family_Name);
        bean.setParams(paramsBean);
        if (getPresenter() != null)
            getPresenter().submitDeleteFamily(bean);

    }

    private void getAccountList(boolean isRefresh, String sid, int level) {
        if (ConstantValue.isAccountLogin()) {
            DeviceGroupPutBean.ParamsBean paramsBean = new DeviceGroupPutBean.ParamsBean();
            paramsBean.setF_limit_size(mLimitSizeForAccount);
            paramsBean.setG_limit_size(0);
            paramsBean.setFamilyid(sid);
            DeviceGroupPutBean bean = new DeviceGroupPutBean();
            bean.setParams(paramsBean);
            bean.setFunc(ModuleValueService.Fuc_For_Device_Group_List);
            bean.setModule(ModuleValueService.Module_For_Device_Group_List);
            if (getPresenter() != null)
                getPresenter().getDeviceGroupList(bean, isRefresh, level);
        }
    }


    /**
     *
     * @param deviceGroupResultBean 返回的新增组织信息
     * @param isRefresh
     * @param sid   item点击组织的 family_sid
     * @param level item点击组织的所属 等级
     */
    //获取账号列表
    @Override
    public void getFamilyListSuccess(DeviceGroupResultBean deviceGroupResultBean, boolean isRefresh, String sid, int level) {
        if (deviceGroupResultBean != null) {
            List<DeviceGroupResultBean.FamilysBean> familyS = deviceGroupResultBean.getFamilys();
            //当前级别下总数
            int familySize = familyS.size();
            if (!isAddFamily) {
                int index = 0;
                if (familyS.size() > 0 && treeData.size() > 0) {
                    for (TreeData data : treeData) {
                        if (data.getFamilySid().equals(sid)) {
                            data.setHasChild(true); //当前sid 有下级
                            index = treeData.indexOf(data); //当前 sid 的下标
                            break;
                        }
                    }
                }
                if (level == 0) { // 0 级别时更新总数UI
                    treeData.clear();
                    treeData.add(0, new TreeData(family_Sname, family_Sid, 0, false, familySize));
                }
                for (int i = 0; i < familyS.size(); i++) {
                    DeviceGroupResultBean.FamilysBean bean = familyS.get(i);
                    treeData.add(index + 1 + i, new TreeData(bean.getSname(), bean.getSid(), level + 1, false, bean.getSub_count()));
                }
            } else {
                //添加成功后 更新UI
                isAddFamily = false;
                if (treeParent != null) {
                    int insertIndex = 0;//  新增组织的 同级组织最后一个下标
                    for (int i = 0; i < treeData.size(); i++) {
                        TreeData data = treeData.get(i);
                        if (data.getLevel() == treeParent.getLevel() + 1) {//找出父级下级最后一个组织的下标
                            insertIndex = i;
                        }
                    }
                    if (insertIndex == 0) { //未找到说明 父级下没有展示下一级 所以在父级下新增层级
                         insertIndex = parentIndex;
                    }
                    boolean isHasClick = treeParent.isClick();   //用户点击了展开下级组织就添加上去
                    if (isHasClick) {
                        boolean isHasChild = treeParent.getHasChild();
                        if (!isHasChild) treeParent.setHasChild(true);
                        treeData.add(insertIndex + 1, new TreeData(familyAddName, familyAddId, parentLevel + 1, false, 0));
                    }
                    if (familyS.size() > 0 && treeData.size() > 0) {//更新被添加下级的 组织个数
                        for (TreeData data : treeData) {
                            if (data.getFamilySid().equals(sid)) {
                                data.setFamilyCount(deviceGroupResultBean.getFamilys_total());
                                break;
                            }
                        }
                    }
                }
            }
        }
        treeListAdapter.listProcessing();
        treeListAdapter.notifyDataSetChanged();
    }


    @Override
    public void editFamilyNameSuccess(BaseBean bean, String sid, String name) {
        if (bean.isSuccess()) {
            ToastUtils.show(getString(R.string.txt_modify_success));
            for (TreeData treeData : treeData) {
                if (sid.equals(treeData.getFamilySid())) {
                    treeData.setFamilyName(name);
                    if (family_Sid.equals(sid)) { // 顶级用户名称修改 需要同时修改缓存数据
                        SPUtils.getInstance().put(ConstantValue.Family_Sname, name);
                    }
                    break;
                }
            }
            treeListAdapter.notifyDataSetChanged();
        } else {
            ToastUtils.show(bean.getError_message());
        }
    }

    @Override
    public void deleteFamilySuccess(DeviceBaseResultBean deviceBaseResultBean) {
        if (deviceBaseResultBean.isSuccess()) {
            taskProgressDialog = null;
            taskId = "";
            if (!TextUtils.isEmpty(deviceBaseResultBean.getTask_id())){
                taskId = deviceBaseResultBean.getTask_id();
                handler.sendEmptyMessage(1);
                if (taskProgressDialog == null){
                    taskProgressDialog = new TaskProgressDialog();
                    taskProgressDialog.show(getSupportFragmentManager(), new TaskProgressDialog.onProgressBarChange() {
                        @Override
                        public void onTaskProgressFinish(String return_message, long return_code) {
                            handler.removeCallbacksAndMessages(null);

                            if (return_code == 0) {
                                ToastUtils.show(getString(R.string.txt_delete_success));
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //删除成功 按首次进入该页面展示
                                        firstDefaultLevelData(false);
                                    }
                                }, 500);
                            } else {
                                ToastUtils.show(return_message);
                            }
                        }
                    });
                }
            }else{
                ToastUtils.show(getString(R.string.txt_delete_success));
                //删除成功 按首次进入该页面展示
                firstDefaultLevelData(false);

                if (deviceBaseResultBean.getFail_items() != null && deviceBaseResultBean.getFail_items().size() > 0){
                    DeviceFailDialog dialog = new DeviceFailDialog();
                    dialog.show(getSupportFragmentManager(), deviceBaseResultBean.getFail_items(), 6);
                }
            }
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){

        @Override
        public void handleMessage(@NonNull Message msg) {
            mCountDown--;
            if (mCountDown <= 0){
                mCountDown = mCountDownTime;
                getTaskProgress();
            }
            handler.sendEmptyMessageDelayed(1, 1000);
        }
    };

    /**
     * 获取删除处理进度
     */
    private void getTaskProgress(){
        GetTaskPutBean getTaskPutBean = new GetTaskPutBean();
        getTaskPutBean.setFunc(ModuleValueService.Fuc_For_Get_Task_Name);
        getTaskPutBean.setModule(ModuleValueService.Module_For_Get_Task_Name);
        GetTaskPutBean.ParamsBean paramsBean = new GetTaskPutBean.ParamsBean();
        paramsBean.setTask_id(taskId);
        getTaskPutBean.setParams(paramsBean);

        if (getPresenter() != null){
            getPresenter().getTaskResult(getTaskPutBean);
        }
    }

    @Override
    public void getTaskSuccess(GetTaskResultBean bean) {
        if (taskProgressDialog != null){
            double progress = ((double) bean.getCurrent_count() / bean.getTotal()) * 100;
            int intProgress = (int) progress;
            if (intProgress > 100){
                intProgress = 100;
            }
            taskProgressDialog.setProgressBar(intProgress, bean.isIs_finish(), bean.getReturn_msg(), bean.getReturn_code());
        }
    }

    /**
     * 1、当前级别未展开  --- 不更新UI
     * 2、当前级别已经展开  --- 更新UI
     * 3、更新UI  父级下 最后一个子级下标(再次请求接口返回总数量) 父index+子级总数 即插入位置
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 201 && resultCode == 200) {
            for (TreeData tree : treeData) {
                if (tree.getFamilySid().equals(family_add_Sid)) {
                    parentIndex = treeData.indexOf(tree); //当前 父级 的下标
                    parentLevel = tree.getLevel();
                    treeParent = tree;
                    break;
                }
            }
            if (parentIndex == 0) { //顶级时全部刷新数据
                firstDefaultLevelData(false);
            } else {
                isAddFamily = true;
                if (data != null) {
                    familyAddId = data.getStringExtra("familyId");
                    familyAddName = data.getStringExtra("familyName");
                }
                getAccountList(false, family_add_Sid, parentLevel); //获取总数
            }
        }else if (resultCode == Activity.RESULT_OK && requestCode == Intent_Account_List){
            //删除成功 按首次进入该页面展示
            firstDefaultLevelData(true);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 首次获取一二级数据
     */
    private void firstDefaultLevelData(boolean isRefresh) {
        if (treeListAdapter != null) {
            treeListAdapter.setShrinkIdListDefault();
        }
        treeData.clear();
        TreeData treeRoot = new TreeData(family_Sname, family_Sid, 0, false, 0);
        treeRoot.setClick(true); //默认展开一二级
        treeData.add(treeRoot);
        getAccountList(isRefresh, family_Sid, 0); // 第一次获取用户列表
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void finishRefresh() {
        srlView.setRefreshing(false);
    }

    @Override
    public void endLoadFail() {

    }

    @Override
    public void endLoadMore() {

    }

    @Override
    public void setNoMore() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
