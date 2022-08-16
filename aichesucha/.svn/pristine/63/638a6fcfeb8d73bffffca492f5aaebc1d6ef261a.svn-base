package com.car.scth.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.car.scth.R;
import com.car.scth.app.MyApplication;
import com.car.scth.di.component.DaggerGroupManagementComponent;
import com.car.scth.mvp.contract.GroupManagementContract;
import com.car.scth.mvp.model.api.ModuleValueService;
import com.car.scth.mvp.model.bean.AlertBean;
import com.car.scth.mvp.model.bean.DeviceBaseResultBean;
import com.car.scth.mvp.model.bean.DeviceDetailResultBean;
import com.car.scth.mvp.model.bean.DeviceGroupResultBean;
import com.car.scth.mvp.model.bean.FindDeviceResultBean;
import com.car.scth.mvp.model.bean.GroupAddResultBean;
import com.car.scth.mvp.model.bean.TaskProgressResultBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.DeviceDetailPutBean;
import com.car.scth.mvp.model.putbean.DeviceGroupPutBean;
import com.car.scth.mvp.model.putbean.FindDevicePutBean;
import com.car.scth.mvp.model.putbean.FreezeEquipmentPutBean;
import com.car.scth.mvp.model.putbean.GroupAddPutBean;
import com.car.scth.mvp.model.putbean.GroupDeletePutBean;
import com.car.scth.mvp.model.putbean.GroupEditPutBean;
import com.car.scth.mvp.model.putbean.RemoveDevicePutBean;
import com.car.scth.mvp.model.putbean.TaskProgressPubBean;
import com.car.scth.mvp.model.putbean.TransferDevicePutBean;
import com.car.scth.mvp.presenter.GroupManagementPresenter;
import com.car.scth.mvp.ui.adapter.GroupListManageAdapter;
import com.car.scth.mvp.ui.adapter.SearchDeviceAdapter;
import com.car.scth.mvp.ui.adapter.SearchDeviceNewAdapter;
import com.car.scth.mvp.ui.view.MyLoadMoreView;
import com.car.scth.mvp.utils.ConstantValue;
import com.car.scth.mvp.utils.ResultDataUtils;
import com.car.scth.mvp.utils.ToastUtils;
import com.car.scth.mvp.utils.Utils;
import com.car.scth.mvp.weiget.AlertAppDialog;
import com.car.scth.mvp.weiget.DeviceFailDialog;
import com.car.scth.mvp.weiget.GroupAddDialog;
import com.car.scth.mvp.weiget.GroupEditDialog;
import com.car.scth.mvp.weiget.GroupManagePopupwindow;
import com.car.scth.mvp.weiget.GroupTransferDeviceDialog;
import com.car.scth.mvp.weiget.TaskProgressDialog;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 设备分组管理界面
 * <p>
 * Created by MVPArmsTemplate on 01/08/2021 20:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class GroupManagementActivity extends BaseActivity<GroupManagementPresenter> implements GroupManagementContract.View {

    @BindView(R.id.iv_device_set)
    ImageView ivDeviceSet;
    @BindView(R.id.iv_device_add)
    ImageView ivAddDevice;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.srl_view)
    SwipeRefreshLayout srlView;
    @BindView(R.id.ll_search_layout)
    LinearLayout llSearch;
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.recyclerView_search)
    RecyclerView recyclerViewSearch;


    private TextView tvPrompt;
    private GroupListManageAdapter mAdapter;
    private List<DeviceGroupResultBean.GaragesBean> groupBeans; // 设备分组
    private String mLastGroupId; // 最后获取到的gid，没有可以为空
    private String mSid; // 分组id
    private String mGroupName; // 分组名称

    private GroupManagePopupwindow mPopupWindow; // 菜单栏
    private final static int Intent_Add_Device = 10; // 添加设备
    private final static int Intent_Device_Manager = 11; // 设备管理

    private static final int mCountDownTime = 2; // 倒计时固定时长
    private int mCountDown = mCountDownTime; // 倒计时递减时长
    private String taskId; // 任务id
    private TaskProgressDialog taskProgressDialog;

    private String familyAuth; // 用户权限

    private List<FindDeviceResultBean.ItemsBean> searchDeviceResultBeans; // 筛选结果设备列表
    private SearchDeviceNewAdapter mSearchAdapter; // 搜索
    private static final int mSearchCountDownTime = 1; // 倒计时固定时长
    private int mSearchCountDown = mSearchCountDownTime; // 倒计时递减时长
    private String strKeyword = ""; // 搜索关键词
    private long mSearchLastImei = 0; // 上一次调用该接口返回的最后一条imei
    private int mSearchLimitSize = 10; // 限制个数，默认10个，越多，速度越慢

    public static Intent newInstance() {
        return new Intent(MyApplication.getMyApp(), GroupManagementActivity.class);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerGroupManagementComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_group_management;//setContentView(id);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_group_manage));
        familyAuth = ConstantValue.getFamilyAuth();
        groupBeans = new ArrayList<>();
        searchDeviceResultBeans = new ArrayList<>();

        if (!TextUtils.isEmpty(familyAuth)) {
            if (familyAuth.contains(ResultDataUtils.Family_Auth_0) || familyAuth.contains(ResultDataUtils.Family_Auth_1)
                    || familyAuth.contains(ResultDataUtils.Family_Auth_10) || familyAuth.contains(ResultDataUtils.Family_Auth_13)
                    || familyAuth.contains(ResultDataUtils.Family_Auth_11) || familyAuth.contains(ResultDataUtils.Family_Auth_2)) {
            } else {
                ivDeviceSet.setVisibility(View.GONE);
                ivAddDevice.setVisibility(View.GONE);
            }
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        srlView.setColorSchemeResources(R.color.color_00A7FF, R.color.color_00A7FF);
        srlView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSid = "";
                mLastGroupId = "";
                mGroupName = "";
                getDeviceGroupList(false, true);
            }
        });

        View statusView = LayoutInflater.from(this).inflate(R.layout.layout_status, recyclerView, false);
        tvPrompt = statusView.findViewById(R.id.txt_wrong_status);
        tvPrompt.setText(getString(R.string.txt_group_no_data));
        tvPrompt.setVisibility(View.GONE);

        mAdapter = new GroupListManageAdapter(R.layout.item_group_manage, groupBeans);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setEmptyView(statusView);

        LoadMoreView loadMoreView = new MyLoadMoreView();
        mAdapter.setLoadMoreView(loadMoreView);
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getDeviceGroupList(false, false);
            }
        }, recyclerView);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                onDeviceManage(position);
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                for (DeviceGroupResultBean.GaragesBean bean : groupBeans) {
                    bean.setSelect(false);
                }
                groupBeans.get(position).setSelect(true);
                mAdapter.notifyDataSetChanged();
                mSid = groupBeans.get(position).getSid();
                mGroupName = groupBeans.get(position).getSname();
            }
        });

        onSearchDevice();
        getDeviceGroupList(true, false);
    }

    /**
     * 搜索设备
     */
    private void onSearchDevice() {
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(this));

        mSearchAdapter = new SearchDeviceNewAdapter(R.layout.item_search_device, searchDeviceResultBeans);
        recyclerViewSearch.setAdapter(mSearchAdapter);

        LoadMoreView loadMoreView = new MyLoadMoreView();
        mSearchAdapter.setLoadMoreView(loadMoreView);
        mSearchAdapter.setEnableLoadMore(true);
        mSearchAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (!TextUtils.isEmpty(strKeyword)) {
                    getFindDeviceList();
                }
            }
        }, recyclerViewSearch);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                strKeyword = s.toString();
                if (searchHandler.hasMessages(10)) {
                    searchHandler.removeMessages(10);
                    mSearchCountDown = mSearchCountDownTime;
                }
                searchHandler.sendEmptyMessageDelayed(10, 1000);
            }
        });
        mSearchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                hideKeyboard(edtSearch);
                long imei = searchDeviceResultBeans.get(position).getImei();
                String smei = searchDeviceResultBeans.get(position).getSimei();
                getDeviceHasLocation(imei, smei);
            }
        });
    }

    /**
     * 获取设备是否有有效定位数据
     *
     * @param imei  设备imei号
     * @param simei simei
     */
    private void getDeviceHasLocation(long imei, String simei) {
        DeviceDetailPutBean.ParamsBean paramsBean = new DeviceDetailPutBean.ParamsBean();
        paramsBean.setSimei(simei);

        DeviceDetailPutBean bean = new DeviceDetailPutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Device_Detail);
        bean.setModule(ModuleValueService.Module_For_Device_Detail);
        bean.setParams(paramsBean);

        showProgressDialog();

        getPresenter().getDeviceHasLocation(bean, imei, simei);
    }

    /**
     * 重置搜索列表数据
     */
    private void onResetSearchData() {
        edtSearch.setText("");
        searchDeviceResultBeans.clear();
        mSearchAdapter.notifyDataSetChanged();
    }

    /**
     * 显示搜索设备
     */
    private void onShowSearchDevice() {
        if (TextUtils.isEmpty(strKeyword)) {
            mSearchLastImei = 0;
            searchDeviceResultBeans.clear();
            mSearchAdapter.notifyDataSetChanged();
        } else {
            mSearchLastImei = 0;
            getFindDeviceList();
        }
    }

    /**
     * 搜索设备
     */
    private void getFindDeviceList() {
        if (TextUtils.isEmpty(strKeyword)) {
            return;
        }
        FindDevicePutBean.ParamsBean paramsBean = new FindDevicePutBean.ParamsBean();
        paramsBean.setImei(strKeyword);
        paramsBean.setLimit_size(mSearchLimitSize);
        if (mSearchLastImei != 0) {
            paramsBean.setLast_imei(mSearchLastImei);
        }
        FindDevicePutBean bean = new FindDevicePutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Find_Device);
        bean.setModule(ModuleValueService.Module_For_Find_Device);

        if (getPresenter() != null) {
            getPresenter().getFindDeviceData(bean);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler searchHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mSearchCountDown--;
            if (mSearchCountDown <= 0) {
                mSearchCountDown = mSearchCountDownTime;
                onShowSearchDevice();
            } else {
                searchHandler.sendEmptyMessageDelayed(10, 1000);
            }
        }
    };

    /**
     * 显示无数据提示
     */
    private void onShowNoData() {
        if (groupBeans.size() > 0) {
            tvPrompt.setVisibility(View.GONE);
        } else {
            tvPrompt.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 获取数据
     */
    private void getData() {
        mSid = "";
        mLastGroupId = "";
        mGroupName = "";
        getDeviceGroupList(false, true);
    }

    /**
     * 获取车组织列表和车组列表
     *
     * @param isShow    是否显示加载框
     * @param isRefresh 是否是刷新设备分组
     */
    private void getDeviceGroupList(boolean isShow, boolean isRefresh) {
        if (ConstantValue.isAccountLogin()) {
            DeviceGroupPutBean.ParamsBean paramsBean = new DeviceGroupPutBean.ParamsBean();
            paramsBean.setF_limit_size(0);
            // 限制分组获取数量
            int mLimitSize = 100;
            paramsBean.setG_limit_size(mLimitSize);
            paramsBean.setFamilyid(ConstantValue.getFamilySid());
            paramsBean.setGet_all(true);
            if (!TextUtils.isEmpty(mLastGroupId)) {
                paramsBean.setLast_gid(mLastGroupId);
            }
            DeviceGroupPutBean bean = new DeviceGroupPutBean();
            bean.setParams(paramsBean);
            bean.setFunc(ModuleValueService.Fuc_For_Device_Group_List);
            bean.setModule(ModuleValueService.Module_For_Device_Group_List);

            if (getPresenter() != null) {
                getPresenter().getDeviceGroupList(bean, isShow, isRefresh);
            }
        }
    }

    /**
     * 跳转设备管理
     */
    private void onDeviceManage(int position) {
        Intent intent = new Intent(this, DeviceListActivity.class);
        intent.putExtra("sid", groupBeans.get(position).getSid());
        intent.putExtra("title",groupBeans.get(position).getSname());
        startActivityForResult(intent, Intent_Device_Manager);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_device_set, R.id.iv_device_add, R.id.iv_back, R.id.ll_search, R.id.tv_search_cancel})
    public void onViewClicked(View view) {
        if (Utils.isButtonQuickClick(System.currentTimeMillis())) {
            switch (view.getId()) {
                case R.id.iv_back:
                    finish();
                    break;
                case R.id.iv_device_add:
                    addNewDevice();
                    break;
                case R.id.ll_search:
                    llSearch.setVisibility(View.VISIBLE);
                    edtSearch.setFocusable(true);
                    edtSearch.setFocusableInTouchMode(true);
                    edtSearch.requestFocus();
                    showKeyboard(edtSearch);
                    break;
                case R.id.tv_search_cancel:
                    llSearch.setVisibility(View.GONE);
                    hideKeyboard(edtSearch);
                    onResetSearchData();
                    break;
                case R.id.iv_device_set:
                    if (mPopupWindow != null && mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                    } else {
                        mPopupWindow = new GroupManagePopupwindow(this, 0);
                        mPopupWindow.setManageMenuChange(new GroupManagePopupwindow.onManageMenuChange() {
                            @Override
                            public void onMenuSelect(int id) {
                                // 0:转移，7：删除设备，2：删除分组，3：编辑分组，4：添加分组，5：添加设备，6：冻结设备
                                switch (id) {
                                    case 0:
                                        onTransferDevice();
                                        break;
                                    case 7:
                                        onDeleteDevice(0);
                                        break;
                                    case 2:
                                        onDeleteDevice(1);
                                        break;
                                    case 3:
                                        editGroupName();
                                        break;
                                    case 4:
                                        addNewGroup();
                                        break;
                                    case 5:
                                        addNewDevice();
                                        break;
                                    case 6:
                                        onFreezeEquipment();
                                        break;
                                }
                                mPopupWindow.dismiss();
                            }
                        });
                        mPopupWindow.showAsDropDown(view);
                    }
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Intent_Add_Device || requestCode == Intent_Device_Manager) {
                mSid = "";
                mLastGroupId = "";
                mGroupName = "";
                getDeviceGroupList(true, true);
                setResult(Activity.RESULT_OK);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 是否选择了分组
     *
     * @return
     */
    private boolean isGroupSelect() {
        if (TextUtils.isEmpty(mSid)) {
            ToastUtils.show(getString(R.string.txt_group_select_hint));
            return false;
        } else {
            return true;
        }
    }

    /**
     * 转移分组
     */
    private void onTransferDevice() {
        if (!isGroupSelect()) {
            return;
        }
        List<DeviceGroupResultBean.GaragesBean> groupBeansAll = new ArrayList<>(); // 设备分组
        groupBeansAll.addAll(groupBeans);
        DeviceGroupResultBean.GaragesBean beanAll = null; // 全部分组
            for (DeviceGroupResultBean.GaragesBean bean : groupBeansAll){
                if (bean.getSid().isEmpty()){
                    beanAll = bean;
                }
            }
        groupBeansAll.remove(beanAll);
        GroupTransferDeviceDialog dialog = new GroupTransferDeviceDialog();
        dialog.show(getSupportFragmentManager(), mSid, groupBeansAll, new GroupTransferDeviceDialog.onGroupTransferDeviceChange() {
            @Override
            public void onGroupTransferDevice(String sid) {
                submitTransferDevice(sid);
            }
        });
    }

    /**
     * 转移设备
     *
     * @param sid
     */
    private void submitTransferDevice(String sid) {
        TransferDevicePutBean.ParamsBean paramsBean = new TransferDevicePutBean.ParamsBean();
        paramsBean.setSgid(mSid);
        paramsBean.setSgid_new(sid);

        TransferDevicePutBean bean = new TransferDevicePutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Group_Transfer_Device);
        bean.setModule(ModuleValueService.Module_For_Group_Transfer_Device);

        if (getPresenter() != null) {
            getPresenter().submitTransferDevice(bean);
        }
    }

    /**
     * 冻结设备
     */
    private void onFreezeEquipment() {
        if (!isGroupSelect()) {
            return;
        }

        AlertBean bean = new AlertBean();
        bean.setTitle(getString(R.string.txt_tip));
        bean.setAlert(getString(R.string.txt_freeze_equipment_group));
        bean.setType(0);
        AlertAppDialog dialog = new AlertAppDialog();
        dialog.show(getSupportFragmentManager(), bean, new AlertAppDialog.onAlertDialogChange() {
            @Override
            public void onConfirm() {
                submitFreezeEquipment();
            }

            @Override
            public void onCancel() {

            }
        });
    }

    /**
     * 提交冻结设备
     */
    private void submitFreezeEquipment() {
        FreezeEquipmentPutBean.ParamsBean paramsBean = new FreezeEquipmentPutBean.ParamsBean();
        paramsBean.setSgid(mSid);

        FreezeEquipmentPutBean bean = new FreezeEquipmentPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Freeze_Equipment);
        bean.setModule(ModuleValueService.Module_For_Freeze_Equipment);

        if (getPresenter() != null) {
            getPresenter().submitFreezeEquipment(bean);
        }
    }

    /**
     * 删除分组
     *
     * @param type 删除类型，0：删除设备，1：删除分组
     */
    private void onDeleteDevice(int type) {
        if (!isGroupSelect()) {
            return;
        }

        AlertBean bean = new AlertBean();
        bean.setTitle(getString(R.string.txt_tip));
        if (type == 0) {
            bean.setAlert(getString(R.string.txt_device_delete_hint));
        } else {
            bean.setAlert(getString(R.string.txt_group_device_delete_hint));
        }
        bean.setType(0);
        AlertAppDialog dialog = new AlertAppDialog();
        dialog.show(getSupportFragmentManager(), bean, new AlertAppDialog.onAlertDialogChange() {
            @Override
            public void onConfirm() {
                if (type == 0) {
                    submitDeleteDevice();
                } else {
                    submitDeleteGroup();
                }
            }

            @Override
            public void onCancel() {

            }
        });
    }

    /**
     * 删除分组
     */
    private void submitDeleteGroup() {
        GroupDeletePutBean.ParamsBean paramsBean = new GroupDeletePutBean.ParamsBean();
        paramsBean.setGid(mSid);

        GroupDeletePutBean bean = new GroupDeletePutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Group_Delete);
        bean.setModule(ModuleValueService.Module_For_Group_Delete);

        if (getPresenter() != null) {
            getPresenter().submitDeleteGroup(bean);
        }
    }

    /**
     * 删除设备
     */
    private void submitDeleteDevice() {
        RemoveDevicePutBean.ParamsBean paramsBean = new RemoveDevicePutBean.ParamsBean();
        paramsBean.setSgid(mSid);
        paramsBean.setDel_type(ResultDataUtils.Device_Delete_Parent);

        RemoveDevicePutBean bean = new RemoveDevicePutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Device_Remove);
        bean.setModule(ModuleValueService.Module_For_Device_Remove);

        if (getPresenter() != null) {
            getPresenter().submitDeleteDevice(bean);
        }
    }

    /**
     * 编辑分组名称
     */
    private void editGroupName() {
        if (!isGroupSelect()) {
            return;
        }
        GroupEditDialog dialog = new GroupEditDialog();
        dialog.show(getSupportFragmentManager(), mSid, mGroupName, new GroupEditDialog.onGroupEditChange() {
            @Override
            public void onGroupEdit(String groupName, String gid) {
                submitGroupEdit(groupName, gid);
            }
        });
    }

    /**
     * 提交编辑分组
     *
     * @param groupName 分组名称
     * @param gid       分组id
     */
    private void submitGroupEdit(String groupName, String gid) {
        GroupEditPutBean.ParamsBean paramsBean = new GroupEditPutBean.ParamsBean();
        paramsBean.setGid(gid);
        paramsBean.setGroup_name(groupName);

        GroupEditPutBean bean = new GroupEditPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Group_Edit);
        bean.setModule(ModuleValueService.Module_For_Group_Edit);

        if (getPresenter() != null) {
            getPresenter().submitGroupEdit(bean);
        }
    }

    /**
     * 添加新的分组
     */
    private void addNewGroup() {
        GroupAddDialog dialog = new GroupAddDialog();
        dialog.show(getSupportFragmentManager(), new GroupAddDialog.onGroupAddChange() {
            @Override
            public void onGroupAdd(String groupName) {
                submitGroupAdd(groupName);
            }
        });
    }

    /**
     * 添加分组
     */
    private void submitGroupAdd(String groupName) {
        GroupAddPutBean.ParamsBean paramsBean = new GroupAddPutBean.ParamsBean();
        paramsBean.setGroup_name(groupName);
        paramsBean.setFamilyid(ConstantValue.getFamilySid());

        GroupAddPutBean bean = new GroupAddPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Group_Add);
        bean.setModule(ModuleValueService.Module_For_Group_Add);

        if (getPresenter() != null) {
            getPresenter().submitGroupAdd(bean);
        }
    }

    /**
     * 添加设备
     */
    private void addNewDevice() {
        if (!isGroupSelect()) {
            return;
        }

        Intent intent = new Intent(this, AddDeviceActivity.class);
        intent.putExtra("kay_sid", mSid);
        startActivityForResult(intent, Intent_Add_Device);
    }

    /**
     * 获取任务进度
     */
    private void getTaskProgress() {
        TaskProgressPubBean.ParamsBean paramsBean = new TaskProgressPubBean.ParamsBean();
        paramsBean.setTask_id(taskId);

        TaskProgressPubBean bean = new TaskProgressPubBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Task_Progress);
        bean.setModule(ModuleValueService.Module_For_Task_Progress);

        if (getPresenter() != null) {
            getPresenter().getTaskProgress(bean);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(@NonNull Message msg) {
            mCountDown--;
            if (mCountDown <= 0) {
                mCountDown = mCountDownTime;
                getTaskProgress();
            }
            handler.sendEmptyMessageDelayed(1, 1000);
        }
    };

    @Override
    public void getDeviceGroupListSuccess(DeviceGroupResultBean deviceGroupResultBean, boolean isRefresh) {
        if (isRefresh) {
            groupBeans.clear();
        }
        DeviceGroupResultBean.GaragesBean garagesBean = new DeviceGroupResultBean.GaragesBean();
        garagesBean.setImei_count(deviceGroupResultBean.getImei_count());
        garagesBean.setSid("");
        garagesBean.setSname(getString(R.string.txt_total_group));
        groupBeans.add(garagesBean);
        if (deviceGroupResultBean.getGarages() != null && deviceGroupResultBean.getGarages().size() > 0) {
            mLastGroupId = deviceGroupResultBean.getGarages().get(deviceGroupResultBean.getGarages().size() - 1).getSid();
            groupBeans.addAll(deviceGroupResultBean.getGarages());
        }
        mAdapter.notifyDataSetChanged();
        onShowNoData();
    }

    @Override
    public void finishRefresh() {
        srlView.setRefreshing(false);
    }

    @Override
    public void endLoadMore(int type) {
        if (type == 0)
            mAdapter.loadMoreComplete();
        else
            mSearchAdapter.loadMoreComplete();
    }

    @Override
    public void setNoMore(int type) {
        if (type == 0)
            mAdapter.loadMoreEnd();
        else
            mSearchAdapter.loadMoreEnd();
    }

    @Override
    public void endLoadFail(int type) {
        if (type == 0)
            mAdapter.loadMoreFail();
        else
            mSearchAdapter.loadMoreFail();
    }

    @Override
    public void submitGroupAddSuccess(GroupAddResultBean groupAddResultBean) {
        ToastUtils.show(getString(R.string.txt_add_group_success));
        getData();
    }

    @Override
    public void submitGroupEditSuccess(BaseBean baseBean, String name, String gid) {
        ToastUtils.show(getString(R.string.txt_edit_group_success));
        mGroupName = name;
        for (DeviceGroupResultBean.GaragesBean bean : groupBeans) {
            if (bean.getSid().equals(gid)) {
                bean.setSname(name);
                break;
            }
        }
        mAdapter.notifyDataSetChanged();
        setResult(Activity.RESULT_OK);
    }

    @Override
    public void submitTransferDeviceSuccess(DeviceBaseResultBean deviceBaseResultBean) {
        setResult(Activity.RESULT_OK);
        taskProgressDialog = null;
        taskId = "";
        if (!TextUtils.isEmpty(deviceBaseResultBean.getTask_id())) {
            taskId = deviceBaseResultBean.getTask_id();
            handler.sendEmptyMessage(1);
            if (taskProgressDialog == null) {
                taskProgressDialog = new TaskProgressDialog();
                taskProgressDialog.show(getSupportFragmentManager(), new TaskProgressDialog.onProgressBarChange() {
                    @Override
                    public void onTaskProgressFinish(String return_message, long return_code) {
                        handler.removeCallbacksAndMessages(null);

                        if (return_code == 0) {
                            ToastUtils.show(getString(R.string.txt_transfer_device_success));
                        } else {
                            ToastUtils.show(return_message);
                        }
                        ivDeviceSet.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getData();
                            }
                        }, 500);
                    }
                });
            }
        } else {
            ToastUtils.show(getString(R.string.txt_transfer_device_success));
            getData();

            if (deviceBaseResultBean.getFail_items() != null && deviceBaseResultBean.getFail_items().size() > 0) {
                DeviceFailDialog dialog = new DeviceFailDialog();
                dialog.show(getSupportFragmentManager(), deviceBaseResultBean.getFail_items(), 1);
            }
        }
    }

    @Override
    public void submitDeleteDeviceSuccess(DeviceBaseResultBean deviceBaseResultBean) {
        setResult(Activity.RESULT_OK);
        taskProgressDialog = null;
        taskId = "";
        if (!TextUtils.isEmpty(deviceBaseResultBean.getTask_id())) {
            taskId = deviceBaseResultBean.getTask_id();
            handler.sendEmptyMessage(1);
            if (taskProgressDialog == null) {
                taskProgressDialog = new TaskProgressDialog();
                taskProgressDialog.show(getSupportFragmentManager(), new TaskProgressDialog.onProgressBarChange() {
                    @Override
                    public void onTaskProgressFinish(String return_message, long return_code) {
                        handler.removeCallbacksAndMessages(null);

                        if (return_code == 0) {
                            ToastUtils.show(getString(R.string.txt_delete_success));
                        } else {
                            ToastUtils.show(return_message);
                        }
                        ivDeviceSet.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getData();
                            }
                        }, 500);
                    }
                });
            }
        } else {
            ToastUtils.show(getString(R.string.txt_delete_success));
            getData();

            if (deviceBaseResultBean.getFail_items() != null && deviceBaseResultBean.getFail_items().size() > 0) {
                DeviceFailDialog dialog = new DeviceFailDialog();
                dialog.show(getSupportFragmentManager(), deviceBaseResultBean.getFail_items(), 3);
            }
        }
    }

    @Override
    public void submitDeleteGroupSuccess(BaseBean baseBean) {
        setResult(Activity.RESULT_OK);
        ToastUtils.show(getString(R.string.txt_delete_success));
        getData();
    }

    @Override
    public void submitFreezeEquipmentSuccess(DeviceBaseResultBean deviceBaseResultBean) {
        setResult(Activity.RESULT_OK);
        taskProgressDialog = null;
        taskId = "";
        if (!TextUtils.isEmpty(deviceBaseResultBean.getTask_id())) {
            taskId = deviceBaseResultBean.getTask_id();
            handler.sendEmptyMessage(1);
            if (taskProgressDialog == null) {
                taskProgressDialog = new TaskProgressDialog();
                taskProgressDialog.show(getSupportFragmentManager(), new TaskProgressDialog.onProgressBarChange() {
                    @Override
                    public void onTaskProgressFinish(String return_message, long return_code) {
                        handler.removeCallbacksAndMessages(null);

                        if (return_code == 0) {
                            ToastUtils.show(getString(R.string.txt_successful_operation));
                        } else {
                            ToastUtils.show(return_message);
                        }
                        ivDeviceSet.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getData();
                            }
                        }, 500);
                    }
                });
            }
        } else {
            ToastUtils.show(getString(R.string.txt_successful_operation));
            getData();
            if (deviceBaseResultBean.getFail_items() != null && deviceBaseResultBean.getFail_items().size() > 0) {
                DeviceFailDialog dialog = new DeviceFailDialog();
                dialog.show(getSupportFragmentManager(), deviceBaseResultBean.getFail_items(), 4);
            }
        }
    }

    @Override
    public void getTaskProgressSuccess(TaskProgressResultBean taskProgressResultBean) {
        if (taskProgressDialog != null) {
            double progress = ((double) taskProgressResultBean.getCurrent_count() / taskProgressResultBean.getTotal()) * 100;
            int intProgress = (int) progress;
            if (intProgress > 100) {
                intProgress = 100;
            }
            taskProgressDialog.setProgressBar(intProgress, taskProgressResultBean.isIs_finish(),
                    taskProgressResultBean.getReturn_msg(), taskProgressResultBean.getReturn_code());
        }
    }

    @Override
    public void getFindDeviceDataSuccess(FindDeviceResultBean bean) {
        if (mSearchLastImei == 0) {
            searchDeviceResultBeans.clear();
        }
        if (bean.getItems() != null && bean.getItems().size() > 0) {
            mSearchLastImei = bean.getItems().get(bean.getItems().size() - 1).getImei();
            searchDeviceResultBeans.addAll(bean.getItems());
        }
        mSearchAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDismissProgress() {
        dismissProgressDialog();
    }

    @Override
    public void onRefreshData() {
        mSid = "";
        mLastGroupId = "";
        mGroupName = "";
        getDeviceGroupList(false, true);
    }

    @Override
    public void getDeviceHasLocationSuccess(DeviceDetailResultBean deviceDetailResultBean, long imei, String simei) {
        if (deviceDetailResultBean.getState_begin_time() != 0) {
            Intent intent = new Intent(GroupManagementActivity.this, MainActivity.class);
            intent.putExtra("imei", imei);
            intent.putExtra("smei", simei);
            intent.setAction(ConstantValue.DEVICE_ACTION);
            startActivity(intent);
            LocalBroadcastManager.getInstance(GroupManagementActivity.this).sendBroadcast(intent);
            finish();
        } else {
            ToastUtils.show(getString(R.string.txt_device_location_no_data));
        }
    }

    @Override
    public void onBackPressed() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        searchHandler.removeCallbacksAndMessages(null);
        searchHandler = null;
        super.onDestroy();
    }
}
