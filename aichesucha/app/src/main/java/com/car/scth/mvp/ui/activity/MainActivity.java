package com.car.scth.mvp.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.xiaomi.mipush.sdk.MiPushClient;

import com.car.scth.R;
import com.car.scth.app.MyApplication;
import com.car.scth.di.component.DaggerMainComponent;
import com.car.scth.mvp.contract.MainContract;
import com.car.scth.mvp.model.api.ModuleValueService;
import com.car.scth.mvp.model.bean.CheckAppUpdateBean;
import com.car.scth.mvp.model.bean.UidInfoResultBean;
import com.car.scth.mvp.model.entity.TabEntity;
import com.car.scth.mvp.model.putbean.CheckAppUpdatePutBean;
import com.car.scth.mvp.model.putbean.UidInfoPutBean;
import com.car.scth.mvp.presenter.MainPresenter;
import com.car.scth.mvp.ui.fragment.FunctionUserFragment;
import com.car.scth.mvp.ui.fragment.LocationAmapFragment;
import com.car.scth.mvp.ui.fragment.LocationBaiduFragment;
import com.car.scth.mvp.ui.fragment.LocationGoogleFragment;
import com.car.scth.mvp.ui.fragment.MineFragment;
import com.car.scth.mvp.ui.view.CommonTabLayout;
import com.car.scth.mvp.utils.BroadcastReceiverUtil;
import com.car.scth.mvp.utils.ConstantValue;
import com.car.scth.mvp.utils.ResultDataUtils;
import com.car.scth.mvp.weiget.UploadAppDialog;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 个人版主页
 * <p>
 * Created by MVPArmsTemplate on 10/14/2020 09:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.frame_pager)
    FrameLayout mFramePager;
    @BindView(R.id.tl_common)
    CommonTabLayout mTlCommon;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private List<String> mTitles;
    private int[] mIconUnSelectIds = {R.mipmap.icon_main_location, R.mipmap.icon_main_fun, R.mipmap.icon_main_sys};
    private int[] mIconSelectIds = {R.mipmap.icon_main_location_select, R.mipmap.icon_main_fun_select, R.mipmap.icon_main_sys_select};

    private static final int PERMISSON_REQUESTCODE = 1; // 获取权限回调码
    // 判断是否需要检测权限，防止不停的弹框
    private boolean isNeedCheck = true;
    private boolean isAuth = true; // 权限不足弹窗只弹一次，防止重复弹出
    private long system_time; // 记录快速点击时间
    private int fragmentPosition = 0; // 点击fragment位置
    private Handler handler = new Handler();

    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA
    };

    // 版本更新
    private static final int INSTALL_PERMISS_CODE = 101; // 允许安装位置应用回调码
    private static final int INSTALL_APK_RESULT = 102; // apk安装情况，是否完成安装回调
    private String mFilePath; // 版本更新下载url地址
    private boolean isForceApp = false; // 是否需要强制更新App，false-不强制更新 true-强制更新
    private boolean isGetUpdateApp = false; // 是否请求过版本更新
    private int versionCode; // 当前版本标识

    private ChangePageReceiver receiver; // 注册广播接收器

    public static Intent newInstance(){
        return new Intent(MyApplication.getMyApp(), MainActivity.class);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main;//setContentView(id);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //Fragment重叠的问题处理
        outState.putParcelable("android:support:fragments", null);
        outState.putParcelable("android:fragments", null);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (!ConstantValue.isRequestAuthority()){
            checkPermissions(needPermissions);
            SPUtils.getInstance().put(ConstantValue.Is_Request_Authority, true);
        }
        // 地图类型,0：高德地图，1：百度地图，2：谷歌地图
        int mapType = ConstantValue.getMapType();
        mTitles = new ArrayList<>();
        if (getIntent() != null && getIntent().hasExtra("type")){
            fragmentPosition = getIntent().getIntExtra("type", 0);
        }

        versionCode = AppUtils.getAppVersionCode();
        isGetUpdateApp = SPUtils.getInstance().getBoolean(ConstantValue.Is_Get_Update_App, false);

        mTitles.add(getString(R.string.txt_location));
        mTitles.add(getString(R.string.txt_function));
        mTitles.add(getString(R.string.txt_mine));

        for (int i = 0; i < mTitles.size(); i++) {
            mTabEntities.add(new TabEntity(mTitles.get(i), mIconSelectIds[i], mIconUnSelectIds[i]));
            if (i == 0) {
                if (mapType == 2){
                    mFragments.add(LocationGoogleFragment.newInstance());
                }else if(mapType == 1){
                    mFragments.add(LocationBaiduFragment.newInstance());
                }else{
                    mFragments.add(LocationAmapFragment.newInstance());
                }
            } else if (i == 1) {
                mFragments.add(FunctionUserFragment.newInstance());
            } else if (i == 2) {
                mFragments.add(MineFragment.newInstance());
            }
        }
        mTlCommon.setTabData(mTabEntities, this, R.id.frame_pager, mFragments);
        mTlCommon.setOnTabSelectListener(new OnTabSelectListener() {

            @Override
            public void onTabSelect(int position) {
                if (fragmentPosition == position){
                    return;
                }
                fragmentPosition = position;
                mTlCommon.setCurrentTab(position);

                if (position == 1){
                    // 添加广播，如果点击的是功能模块，发送广播
                    BroadcastReceiverUtil.showFunctionPage(MainActivity.this);
                }else if (position == 2){
                    // 添加广播，如果点击的是报警消息模块，发送广播
//                    BroadcastReceiverUtil.showAlarmMessagePage(MainActivity.this, 0);
                }
                BroadcastReceiverUtil.onDeviceDetailRunStop(MainActivity.this, position);
                if (position == 0){
                    SPUtils.getInstance().put(ConstantValue.ACTIVITY_STATUS, true);
                }else{
                    SPUtils.getInstance().put(ConstantValue.ACTIVITY_STATUS, false);
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        int padding = SizeUtils.dp2px(3);
        mTlCommon.setMsgMargin(3, -padding, 0);

        // 注册一个广播接收器，用于接收从首页跳转到报警消息页面的广播
        IntentFilter filter = new IntentFilter();
        filter.addAction("main");
        receiver = new ChangePageReceiver();
        //注册切换页面广播接收者
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);

        //写权限通过之后再请求更新
        if (!isGetUpdateApp) {
            checkAppUpdate();
        }

        mTlCommon.postDelayed(new Runnable() {
            @Override
            public void run() {
                getUserInfo();
            }
        }, 1000);
    }

    /**
     * 获取单用户信息，包含推送开关，免打扰时间等
     */
    private void getUserInfo(){
        UidInfoPutBean bean = new UidInfoPutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Uid_Info);
        bean.setModule(ModuleValueService.Module_For_Uid_Info);

        String mSid = ConstantValue.getApiUrlSid();
        if (getPresenter() != null && mSid.length() > 0) {
            getPresenter().getUidInfo(bean);
        }
    }

    /**
     * 页面切换广播，广播接收器
     */
    private class ChangePageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                if (intent.hasExtra("page")) {
                    fragmentPosition = intent.getIntExtra("page", 0);
                    mTlCommon.setCurrentTab(fragmentPosition);
                }
            }
        }
    }

    /**
     * 检测app版本更新
     */
    private void checkAppUpdate() {
        CheckAppUpdatePutBean.ParamsBean paramsBean = new CheckAppUpdatePutBean.ParamsBean();
        paramsBean.setVersion(versionCode);
        paramsBean.setApp_type("ac");

        CheckAppUpdatePutBean bean = new CheckAppUpdatePutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_App_Version);
        bean.setModule(ModuleValueService.Module_For_App_Version);

        if (getPresenter() != null){
            getPresenter().getAppUpdate(bean);
        }
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

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - system_time > 2000) {
            ArmsUtils.snackbarText(getString(R.string.txt_exit_app));
            system_time = System.currentTimeMillis();
        } else {
            ActivityUtils.finishAllActivities();
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        handler = null;
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onDestroy();
    }

    // ------------------- 分割线：发起动态权限申请  ------------------------

    /**
     * @param permissions
     * @since 2.5.0
     */
    private void checkPermissions(String... permissions) {
        List<String> needRequestPermissonList = findDeniedPermissions(permissions);
        if (needRequestPermissonList.size() > 0) {
            ActivityCompat.requestPermissions(this,
                    needRequestPermissonList.toArray(
                            new String[needRequestPermissonList.size()]),
                    PERMISSON_REQUESTCODE);
        }
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     * @since 2.5.0
     */
    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<String>();
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this,
                    perm) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    this, perm)) {
                needRequestPermissonList.add(perm);
            }
        }
        return needRequestPermissonList;
    }

    /**
     * 检测是否说有的权限都已经授权
     *
     * @param grantResults
     * @return
     * @since 2.5.0
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NotNull String[] permissions, @NotNull int[] paramArrayOfInt) {
        if (requestCode == PERMISSON_REQUESTCODE) {
            isNeedCheck = !verifyPermissions(paramArrayOfInt);
            if (isAuth && isNeedCheck){
                showMissingPermissionDialog();
            }
            isAuth = false;
        }
    }

    /**
     * 显示提示信息
     *
     * @since 2.5.0
     *
     */
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.txt_tip);
        builder.setMessage(R.string.txt_notify_msg);

        // 拒绝, 退出应用
        builder.setNegativeButton(R.string.txt_cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.setPositiveButton(R.string.txt_setting,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                        dialog.dismiss();
                    }
                });

        builder.setCancelable(false);

        builder.show();
    }

    /**
     *  启动应用的设置
     *
     * @since 2.5.0
     *
     */
    private void startAppSettings() {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    @Override
    public void getAppUpdateSuccess(CheckAppUpdateBean checkAppUpdateBean) {
        SPUtils.getInstance().put(ConstantValue.Is_Get_Update_App, true);
        if (!TextUtils.isEmpty(checkAppUpdateBean.getUrl())){
            isForceApp = checkAppUpdateBean.isIs_force();
            UploadAppDialog dialog = new UploadAppDialog();
            dialog.show(getSupportFragmentManager(), checkAppUpdateBean, new UploadAppDialog.onAlertDialogChange() {
                @Override
                public void onAppDownLoad(String path) {
                    mFilePath = path;
                    applyInstallCheckApp();
                }

                @Override
                public void onThreeDownLoad() {
                    if (!TextUtils.isEmpty(checkAppUpdateBean.getUrl())) {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(checkAppUpdateBean.getUrl());
                        intent.setData(content_url);
                        startActivity(intent);
                    }
                    if (!isForceApp) {
                        dialog.dismiss();
                    }
                }

                @Override
                public void onCancel() {
                    if (isForceApp) {
                        ActivityUtils.finishAllActivities();
                        finish();
                    } else {
                        dialog.dismiss();
                    }
                }
            });
        }
    }

    @Override
    public void getUidInfoSuccess(UidInfoResultBean uidInfoResultBean) {
        SPUtils.getInstance().put(ConstantValue.Push_Switch, uidInfoResultBean.isPush_switch());
        if (uidInfoResultBean.isPush_switch()) {
            switch (ConstantValue.getPushMpm()) {
                case ResultDataUtils.Push_XiaoMi:
                    initXiaoMiPushAlias();
                    break;
            }
        }
    }

    /**
     * 设置小米推送的alias
     */
    private void initXiaoMiPushAlias(){
        String familyid = ConstantValue.getPushFamily();
        if (!TextUtils.isEmpty(familyid)) {
            MiPushClient.setAlias(this, familyid, null);
        }
    }

    /**
     * 检测是否有安装权限，判断当前安卓版本是否大于等于8.0，8.0以上系统设置安装未知来源权限
     */
    private void applyInstallCheckApp() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 是否设置了安装权限
            boolean isInstallPermission = getPackageManager().canRequestPackageInstalls();
            if (isInstallPermission) {
                installApk();
            } else {
                installApkSetting();
            }
        } else {
            installApk();
        }
    }

    /**
     * 跳转应用详情页打开安装权限
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void installApkSetting() {
        Uri packageUrl = Uri.parse("package:" + getPackageName());
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageUrl);
        startActivityForResult(intent, INSTALL_PERMISS_CODE);
    }

    /**
     * 下载完成，提示用户安装，获取安装App(支持7.0)的意图
     */
    private void installApk() {
        //apk文件的本地路径
        File file = new File(mFilePath);
        if (!file.exists()) {
            return;
        }
        try {
            Uri uri;
            //调用系统安装程序
            Intent intent = new Intent();
            intent.addCategory("android.intent.category.DEFAULT");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(this, "com.car.scth.fileprovider", file);
                intent.setAction(Intent.ACTION_INSTALL_PACKAGE);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//7.0以后，系统要求授予临时uri读取权限，安装完毕以后，系统会自动收回权限，该过程没有用户交互
            } else {
                uri = Uri.fromFile(file);
                intent.setAction("android.intent.action.VIEW");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            startActivityForResult(intent, INSTALL_APK_RESULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == INSTALL_PERMISS_CODE) {
            // 发起安装应用
            installApk();
        } else if (requestCode == INSTALL_APK_RESULT) {
            if (isForceApp) {
                onBackPressed();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
