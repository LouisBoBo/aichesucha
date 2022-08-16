package com.car.scth.mvp.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.Selection;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.lbsapi.BMapManager;
import com.baidu.lbsapi.MKGeneralListener;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SPUtils;
import com.car.scth.mvp.utils.ScreenUtils;
import com.car.scth.mvp.weiget.MapTypeDialog;
import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.common.ApiException;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.car.scth.R;
import com.car.scth.app.MyApplication;
import com.car.scth.di.component.DaggerLoginComponent;
import com.car.scth.mvp.contract.LoginContract;
import com.car.scth.mvp.model.api.Api;
import com.car.scth.mvp.model.api.ModuleValueService;
import com.car.scth.mvp.model.bean.CheckAppUpdateBean;
import com.car.scth.mvp.model.bean.LoginResultBean;
import com.car.scth.mvp.model.putbean.CheckAppUpdatePutBean;
import com.car.scth.mvp.model.putbean.LoginPutBean;
import com.car.scth.mvp.presenter.LoginPresenter;
import com.car.scth.mvp.ui.view.ClearEditText;
import com.car.scth.mvp.utils.ConstantValue;
import com.car.scth.mvp.utils.IPUtils;
import com.car.scth.mvp.utils.MD5Utils;
import com.car.scth.mvp.utils.MacAddressUtils;
import com.car.scth.mvp.utils.ManufacturerUtil;
import com.car.scth.mvp.utils.ResultDataUtils;
import com.car.scth.mvp.utils.ToastUtils;
import com.car.scth.mvp.utils.Utils;
import com.car.scth.mvp.weiget.BindForeignDialog;
import com.car.scth.mvp.weiget.BindMobileDialog;
import com.car.scth.mvp.weiget.LoginAccountListDialog;
import com.car.scth.mvp.weiget.PrivacyPolicyDialog;
import com.car.scth.mvp.weiget.ServiceIPCheckDialog;
import com.car.scth.mvp.weiget.UploadAppDialog;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.car.scth.mvp.weiget.UserServicePolicyDialog;
import com.mob.MobSDK;
import com.umeng.commonsdk.UMConfigure;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 用户登录
 * <p>
 * Created by MVPArmsTemplate on 10/17/2020 17:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.edt_account)
    ClearEditText edtAccount; // 账号或设备号
    @BindView(R.id.edt_password)
    ClearEditText edtPassword; // 密码
    @BindView(R.id.iv_remember_password)
    ImageView ivRememberPassword; // 记住密码
    @BindView(R.id.btn_login)
    Button btnLogin; // 登录
    @BindView(R.id.tv_register)
    TextView tvRegister; // 注册
    @BindView(R.id.tv_agreement)
    TextView tvAgreement; // 查看用户协议
    @BindView(R.id.tv_version)
    TextView tvVersion; // 版本号
    @BindView(R.id.iv_sqr)
    ImageView ivSqr; // 扫码
    @BindView(R.id.iv_agreement)
    ImageView ivAgreement; // 隐私政策协议
    @BindView(R.id.cb_password_visible)
    AppCompatCheckBox cbPsdVisible;
    @BindView(R.id.rv_bg_login)
    RelativeLayout rvLogin;
    @BindView(R.id.tv_map)
    TextView tvMap;

    private static final int INTENT_SCAN_QR = 10; // 扫描二维码/条形码回调
    private static final int Intent_Register = 11; // 注册
    private static final int Intent_Bind_Mobile = 12; // 绑定手机
    private static final int PERMISSON_REQUESTCODE = 1; // 获取权限回调码
    // 判断是否需要检测权限，防止不停的弹框
    private boolean isNeedCheck = true;
    private boolean isAuth = true; // 权限不足弹窗只弹一次，防止重复弹出


    // 百度定位
    private LocationClient mLocationClient;
    private Double mLatitude = 0.0;
    private Double mLongitude = 0.0;
    private String mobileIpAddress; // 手机当前网络ip地址
    private String mobileCName; // wifi网络当前所属区域
    private String mobileImei; // 手机的imei号，唯一编码
    private String mobileIccid; // 获取手机的iccid
    private String mobileInfo = ""; // 手机信息，品牌，Android版本等等
    private String wifiMacAddress = ""; // 如果手机连接了wifi，获取路由器的mac地址

    private String mLoginInfo; // 登录信息，包含定位信息，手机信息，当前网络ip信息
    private String mLoginFlag = ""; // 登录标识:用户登录or设备号登录
    private String mPushMpm = ResultDataUtils.Push_XiaoMi; // 推送通道 0-极光(默认) 1-华为
    private boolean isAgreePrivacy = false; // 是否同意了用户隐私协议
    private boolean isSavePassword = false; // 是否保存密码
    private boolean isAgreement = false; // 是否勾选了同意用户隐私政策协议
    private String mPassword = ""; // 输入的密码
    private boolean isBeforeAccount = false; // 当前登录账号是否和上一个登录账号相同
    private boolean isMergeAccount = false; // 是否需要合并账号

    private MyLocationListener myLocationListener; // 定位监听

    // 版本更新
    private static final int INSTALL_PERMISS_CODE = 101; // 允许安装位置应用回调码
    private static final int INSTALL_APK_RESULT = 102; // apk安装情况，是否完成安装回调
    private String mFilePath; // 版本更新下载url地址
    private boolean isForceApp = false; // 是否需要强制更新App，false-不强制更新 true-强制更新
    private boolean isGetUpdateApp = false; // 是否请求过版本更新
    private int versionCode; // 当前版本标识
    private static final String Login_Key = "login.key";
    private int loginIntent = 0; // 0:默认跳转，1：启动页跳转，从哪个位置跳转过来的，如果是启动页，则判断请求版本更新接口
    private int mMapType = 0; // 地图类型，0：高德地图，1：百度地图，2：谷歌地图

    // 华为推送
    private String mHuaweiToken = ""; // 华为推送token
    private final static String CODELABS_ACTION = "com.car.scth.intent";
    private MyReceiver receiver;
    // 百度街景使用
    public BMapManager mBMapManager = null;


    public static Intent newInstance(int type) {
        Intent intent = new Intent(MyApplication.getMyApp(), LoginActivity.class);
        intent.putExtra(Login_Key, type);
        return intent;
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_login;//setContentView(id);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (rvLogin != null) { //带弧形的 按比例拉升
            ViewGroup.LayoutParams layoutParams = rvLogin.getLayoutParams();
            layoutParams.height = 25 * ScreenUtils.getScreenWidth(this) / 44;
            rvLogin.setLayoutParams(layoutParams);
        }
        tvAgreement.setText(Html.fromHtml(getString(R.string.txt_privacy_policy_login)));
        tvVersion.setText("V" + AppUtils.getAppVersionName());
        cbPsdVisible.setOnCheckedChangeListener(this);
        MyApplication.getMyApp().clearData();
        if (getIntent() != null) {
            loginIntent = getIntent().getIntExtra(Login_Key, 0);
        }

        versionCode = AppUtils.getAppVersionCode();
        isGetUpdateApp = SPUtils.getInstance().getBoolean(ConstantValue.Is_Get_Update_App, false);
        isAgreePrivacy = ConstantValue.isAgreePrivacy();
        isAgreement = SPUtils.getInstance().getBoolean(ConstantValue.IS_USER_AGREEMENT, false);
        mMapType = ConstantValue.getMapType();
        isSavePassword = ConstantValue.isSavePassword();
        mobileInfo = Utils.getMobilePackageInfo(this);
        edtAccount.setText(ConstantValue.getAccount());
        if (isSavePassword) {
            edtPassword.setText(ConstantValue.getPassword());
        }

        if (isAgreePrivacy) {
            //写权限通过之后再请求更新
            if (!isGetUpdateApp && loginIntent == 1) {
                checkAppUpdate();
            }
            onBaiduMapShow();
            getIPAddress();
            getPushRegisterId();
        } else {
            onShowPrivacyPolicy();
        }

        onCheckMapType(mMapType);
        onSaveAgreementClick();
        onSavePasswordClick();
    }

    //检查更新所需权限
    private boolean checkPermissionUpdate() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 用户协议使用说明告知
     */
    private void onShowUserServicePolicy() {
        UserServicePolicyDialog dialog = new UserServicePolicyDialog();
        dialog.show(getSupportFragmentManager(), new UserServicePolicyDialog.onPrivacyPolicyChange() {
            @Override
            public void onPrivacyPolicy(int type) {
                dialog.dismiss();
                if (type == 0) {
                    finish();
                } else {
                    btnLogin.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onShowPrivacyPolicy();
                        }
                    }, 500);
                }
            }
        });
    }

    /**
     * 隐私政策协议告知
     */
    private void onShowPrivacyPolicy() {
        final PrivacyPolicyDialog dialog = new PrivacyPolicyDialog();
        dialog.show(getSupportFragmentManager(), new PrivacyPolicyDialog.onPrivacyPolicyChange() {
            @Override
            public void onPrivacyPolicy(boolean isPrivacy) {
                SPUtils.getInstance().put(ConstantValue.IS_AGREE_PRIVACY, isPrivacy);
                dialog.dismiss();
                if (!isPrivacy) {
                    finish();
                } else {
                    initAppMapAndPush();
                    btnLogin.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getPushRegisterId();
                        }
                    }, 1000);

                    SPUtils.getInstance().put(ConstantValue.Umeng_Init, true);
                    // 友盟隐私政策提交
                    UMConfigure.submitPolicyGrantResult(getApplicationContext(), true);
                    // Mob的隐私政策提交
                    MobSDK.submitPolicyGrantResult(true, null);
                }
            }

            @Override
            public void onSeePrivacyPolicy() {
                onSeePrivacyAgreement();
            }
        });
    }

    /**
     * 初始化获取推送注册id
     */
    private void getPushRegisterId(){
        if (ManufacturerUtil.isHuaWeiPush(this)) {
            receiver = new MyReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(CODELABS_ACTION);
            registerReceiver(receiver, filter);
            mPushMpm = ResultDataUtils.Push_HuaWei;
            getToken();
        } else if (ManufacturerUtil.isXiaomi()) {
            mPushMpm = ResultDataUtils.Push_XiaoMi;
        }
    }

    /**
     * 查看用户隐私政策协议
     */
    private void onSeePrivacyAgreement() {
        String url = Api.Privacy_Policy;
        launchActivity(WebViewActivity.newInstance(getString(R.string.txt_privacy_policy_user), url));
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

        if (getPresenter() != null) {
            getPresenter().getAppUpdate(bean);
        }
    }

    /**
     * 获取手机imei号
     */
    @SuppressLint({"HardwareIds"})
    private void getMobileImei() {
        // 获取手机imei号
        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mobileImei = telephonyManager.getDeviceId();
        mobileIccid = telephonyManager.getSimSerialNumber();  //取出 ICCID
        if (TextUtils.isEmpty(mobileImei)) {
            mobileImei = Settings.System.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        }
    }

    /**
     * 初始化百度地图
     */
    private void onBaiduMapShow() {
        //定位初始化
        mLocationClient = new LocationClient(this);

        //通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("gcj02"); // 设置坐标类型
        option.setScanSpan(60000);
        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);
        //可选，设置是否需要地址描述
        option.setIsNeedLocationDescribe(true);

        //设置locationClientOption
        mLocationClient.setLocOption(option);

        //注册LocationListener监听器
        myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
        mLocationClient.start();
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null) {
                return;
            }

            mLatitude = location.getLatitude();
            mLongitude = location.getLongitude();

            mLocationClient.stop();
        }
    }

    @Override
    protected void onPause() {
        if (mLocationClient != null) {
            mLocationClient.stop();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mLocationClient != null) {
            mLocationClient.stop();
            mLocationClient.unRegisterLocationListener(myLocationListener);
        }
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_remember_password, R.id.tv_remember_password, R.id.btn_login,
            R.id.tv_register, R.id.tv_agreement, R.id.iv_sqr, R.id.iv_agreement, R.id.login_logo, R.id.tv_map})
    public void onViewClicked(View view) {
        if (Utils.isButtonQuickClick(System.currentTimeMillis())) {
            switch (view.getId()) {
                case R.id.iv_remember_password: // 记住密码
                case R.id.tv_remember_password: // 记住密码
                    isSavePassword = !isSavePassword;
                    onSavePasswordClick();
                    break;
                case R.id.btn_login: // 登录
                    hideKeyboard(edtAccount);
                    onLoginConfirm();
                    break;
                case R.id.tv_register: // 注册
                    hideKeyboard(edtAccount);
                    boolean cn = Utils.isFromCN(this, mobileCName);
                    SPUtils.getInstance().put(ConstantValue.FromCN, cn);
                    Intent intent = new Intent();
                    if (cn) {
                        intent.setClass(this, RegisterActivity.class);
                    } else {
                        intent.setClass(this, RegisterForeignActivity.class);
                    }
                    startActivityForResult(intent, Intent_Register);
                    break;
                case R.id.tv_agreement: // 查看用户协议
                    onSeePrivacyAgreement();
                    break;
                case R.id.login_logo:
                    onCheckServiceIP();
                    break;
                case R.id.iv_sqr:
                    Intent intent_qr = new Intent(this, HMSScanQrCodeActivity.class);
                    intent_qr.putExtra("type", 1);
                    startActivityForResult(intent_qr, INTENT_SCAN_QR);
                    break;
                case R.id.iv_agreement: // 是否同意隐私政策协议
                    isAgreement = !isAgreement;
                    onSaveAgreementClick();
                    break;
                case R.id.tv_map:
                    MapTypeDialog dialog = new MapTypeDialog();
                    dialog.show(getSupportFragmentManager(), new MapTypeDialog.onMapTypeChange() {
                        @Override
                        public void onMapTypeSelect(int id, String name) {
                            onCheckMapType(id);
                        }
                    });
                    break;

            }
        }
    }

    /**
     * 切换登录地图类型
     *
     * @param type 0：高德地图，1：百度地图，2：谷歌地图
     */
    private void onCheckMapType(int type) {
        mMapType = type;
        if (mMapType == 1) {
            tvMap.setText(getString(R.string.txt_map_baidu));
        } else {
            tvMap.setText(getString(R.string.txt_map_amap));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == INTENT_SCAN_QR) {
                String imei = data.getStringExtra("imei");
                if (!TextUtils.isEmpty(imei)) {
                    if (imei.length() == 11 || imei.length() == 15) {
                        edtAccount.setSelection(edtAccount.getText().toString().length());
                        edtAccount.setText(imei);
                    } else {
                        ToastUtils.show(getString(R.string.txt_scan_format_error));
                    }
                }
            } else if (requestCode == Intent_Register) {
                String account = data.getStringExtra("account");
                String password = data.getStringExtra("password");
                edtAccount.setText(account);
                edtPassword.setText(password);
            } else if (requestCode == Intent_Bind_Mobile) {
                String password = data.getStringExtra("password");
                if (!TextUtils.isEmpty(password))
                    edtPassword.setText(password);
            }
        }
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

    /**
     * 切换域名或ip
     */
    private void onCheckServiceIP() {
        ServiceIPCheckDialog dialog = new ServiceIPCheckDialog();
        dialog.show(getSupportFragmentManager(), new ServiceIPCheckDialog.onServiceIPCheckChange() {
            @Override
            public void onCheck() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        restartApplication();
                    }
                }, 500);
            }
        });
    }

    //重启应用
    private void restartApplication() {
        final Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        //杀掉以前进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 保存密码操作
     */
    private void onSavePasswordClick() {
        ivRememberPassword.setImageResource(isSavePassword ? R.drawable.icon_select : R.drawable.icon_unselect);
    }

    /**
     * 同意用户隐私政策协议
     */
    private void onSaveAgreementClick() {
        ivAgreement.setImageResource(isAgreement ? R.drawable.icon_select : R.drawable.icon_unselect);
    }

    /**
     * 确认登录
     */
    private void onLoginConfirm() {
        String account = edtAccount.getText().toString().trim().toLowerCase();
        mPassword = edtPassword.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            ToastUtils.show(getString(R.string.txt_edit_user_account));
            return;
        }
        if (TextUtils.isEmpty(mPassword)) {
            ToastUtils.show(getString(R.string.txt_password_hint));
            return;
        }
        if (!isAgreement) {
            ToastUtils.show(getString(R.string.txt_agreement_hint));
            return;
        }

        mLoginInfo = "IP:" + mobileIpAddress + ";IMEI:" + mobileImei + ";IMSI:null" + ";ICCID:" + mobileIccid
                + ";Address:" + mLatitude + "," + mLongitude + mobileInfo + ";WifiMac:" + wifiMacAddress;

        if (mPushMpm.equals(ResultDataUtils.Push_HuaWei) && TextUtils.isEmpty(mHuaweiToken)) {
            mPushMpm = ResultDataUtils.Push_XiaoMi;
        }

        LoginPutBean.ParamsBean paramsBean = new LoginPutBean.ParamsBean();
        paramsBean.setAccount(account);
        paramsBean.setPwd_md5(MD5Utils.getMD5Encryption(mPassword));
        paramsBean.setMpm(mPushMpm);
        if (mPushMpm.equals(ResultDataUtils.Push_HuaWei)) {
            paramsBean.setMpm_identify(mHuaweiToken);
        }
        paramsBean.setPlatform(ModuleValueService.Login_Platform_Type);
        paramsBean.setInfo(mLoginInfo);

        if (!TextUtils.isEmpty(mLoginFlag)) {
            paramsBean.setFlag(mLoginFlag);
        }
        paramsBean.setType(Api.App_Type);
        LoginPutBean bean = new LoginPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Login);
        bean.setModule(ModuleValueService.Module_For_Login);

        if (getPresenter() != null) {
            getPresenter().submitLogin(bean);
        }
    }

    /**
     * 绑定手机号码
     *
     * @param sid
     */
    private void onBindMobile(String sid, boolean isBindPhone) {
        Intent intent;
        if (isBindPhone) {
            intent = new Intent(this, BindMobileActivity.class);
        } else {
            intent = new Intent(this, BindForeignActivity.class); //设备号国外登录
        }
        intent.putExtra("sid", sid);
        intent.putExtra("password", mPassword);
        startActivityForResult(intent, Intent_Bind_Mobile);
    }

    @Override
    public void submitLoginSuccess(LoginResultBean loginResultBean) {
        if (loginResultBean.isSuccess()) {
            if (!TextUtils.isEmpty(loginResultBean.getSid())) {
                onLoginSuccess(loginResultBean);
            } else {
                onLoginAccountSelect(loginResultBean);
            }
        } else {
            mLoginFlag = "";
        }
    }

    private void onLoginAccountSelect(LoginResultBean loginResultBean) {
        if (loginResultBean.getFlag().size() >= 2) {
            int bindPhone = 0;
            for (String str : loginResultBean.getFlag()) {
                if (str.equals(ResultDataUtils.Login_type_Phone_Account)) {
                    bindPhone++;
                }
                if (str.equals(ResultDataUtils.Login_type_Phone_Device)) {
                    bindPhone++;
                }
            }
            isMergeAccount = bindPhone == 2; //是否符号合并要求
            LoginAccountListDialog dialog = new LoginAccountListDialog();
            dialog.show(getSupportFragmentManager(), loginResultBean.getFlag(), new LoginAccountListDialog.onLoginAccountChange() {
                @Override
                public void onAccountInfo(String accountInfo) {
                    mLoginFlag = accountInfo;
                    onLoginConfirm();
                }
            });
        } else {
            isMergeAccount = false;
            mLoginFlag = "";
            ToastUtils.show(loginResultBean.getError_message());
        }
    }

    private void onLoginSuccess(LoginResultBean loginResultBean) {
        mLoginFlag = loginResultBean.getFlag().get(0);
        SPUtils.getInstance().put(ConstantValue.USER_LOGIN_TYPE, mLoginFlag);
        SPUtils.getInstance().put(ConstantValue.HUAWEI_TOKEN, mHuaweiToken);
        SPUtils.getInstance().put(ConstantValue.Push_mpm, mPushMpm);
        boolean cn = Utils.isFromCN(this, mobileCName);
        SPUtils.getInstance().put(ConstantValue.FromCN, cn);
        boolean isModifyPassword = false; // 是否需要修改密码
        boolean isBindMobile = false; // 是否需要绑定手机号码
        if (loginResultBean.isIs_need_check()) {
            if (loginResultBean.getLack() != null) {
                for (String lack : loginResultBean.getLack()) {
                    if (lack.equals(ResultDataUtils.Lack_Password)) {
                        isModifyPassword = true;
                    }
                    if (lack.equals(ResultDataUtils.Lack_Phone)) {
                        isBindMobile = true;
                    }
                }
                SPUtils.getInstance().put(ConstantValue.Is_Modify_Password, isModifyPassword);
                SPUtils.getInstance().put(ConstantValue.Is_Bind_Mobile, isBindMobile);
            }
            if (!cn && mLoginFlag.equals(ResultDataUtils.Login_type_Device)) { //设备号、国外登录、缺少密码
                if (isModifyPassword) {
                    //提示修改密码
                    showModifyPsd(loginResultBean, false);
                } else {
                    //提示绑定手机号
                    BindMobileDialog dialog = new BindMobileDialog();
                    dialog.show(getSupportFragmentManager(), new BindMobileDialog.onBindMobileChange() {
                        @Override
                        public void onConfirm() {
                            mLoginFlag = "";
                            onBindMobile(loginResultBean.getSid(), true);
                        }

                        @Override
                        public void onCancel() {
                            mLoginFlag = "";
                        }
                    });
                }
            } else {
                if (!isBindMobile) { //已绑定手机号，密码为空
                    //提示修改密码
                    showModifyPsd(loginResultBean, true);
                } else {
                    BindMobileDialog dialog = new BindMobileDialog();
                    dialog.show(getSupportFragmentManager(), new BindMobileDialog.onBindMobileChange() {
                        @Override
                        public void onConfirm() {
                            mLoginFlag = "";
                            onBindMobile(loginResultBean.getSid(), true);
                        }

                        @Override
                        public void onCancel() {
                            mLoginFlag = "";
                        }
                    });
                }
            }
        } else {
            successToMain(loginResultBean);
        }
    }

    private void showModifyPsd(LoginResultBean loginResultBean, boolean bindPhone) {
        BindForeignDialog dialog = new BindForeignDialog();
        dialog.show(getSupportFragmentManager(), new BindForeignDialog.onBindMobileChange() {
            @Override
            public void onConfirm() {
                mLoginFlag = "";
                onBindMobile(loginResultBean.getSid(), bindPhone);
            }

            @Override
            public void onCancel() {
                mLoginFlag = "";
            }
        });
    }

    private void successToMain(LoginResultBean loginResultBean) {
        ToastUtils.show(getString(R.string.txt_login_success));
        if (!TextUtils.isEmpty(ConstantValue.getAccount()) && ConstantValue.getAccount().equals(edtAccount.getText().toString().trim())) {
            isBeforeAccount = true;
        }
        // 如果达到合并账号的条件，再判断是不是账号登录，判断账号是否有e_ua_add_dev
        if (isMergeAccount) {
            if (mLoginFlag.equals(ResultDataUtils.Login_type_Account)
                    || mLoginFlag.equals(ResultDataUtils.Login_type_Phone_Account)) {
                if (loginResultBean.getFamilys() != null && loginResultBean.getFamilys().size() > 0) {
                    isMergeAccount = loginResultBean.getFamilys().get(0).getAuth().toString().contains(ResultDataUtils.Family_Auth_2);
                } else {
                    isMergeAccount = false;
                }
                if (loginResultBean.getFamilys() != null && loginResultBean.getFamilys().size() > 0) {
                    isMergeAccount = loginResultBean.getFamilys().get(0).getType().equals(ResultDataUtils.Account_User);
                }
            }
        }
        SPUtils.getInstance().put(ConstantValue.MAP_TYPE, mMapType);
        SPUtils.getInstance().put(ConstantValue.Is_Modify_Password, false);
        SPUtils.getInstance().put(ConstantValue.Phone_Zone, loginResultBean.getZone());
        SPUtils.getInstance().put(ConstantValue.IS_USER_AGREEMENT, isAgreement);
        SPUtils.getInstance().put(ConstantValue.USER_SID, loginResultBean.getSid());
        SPUtils.getInstance().put(ConstantValue.ACCOUNT, edtAccount.getText().toString().trim());
        SPUtils.getInstance().put(ConstantValue.IS_SAVE_PASSWORD, isSavePassword);
        SPUtils.getInstance().put(ConstantValue.Is_Need_Check, loginResultBean.isIs_need_check());
        SPUtils.getInstance().put(ConstantValue.Push_Family, loginResultBean.getJpush());
        SPUtils.getInstance().put(ConstantValue.Is_Check_Phone, loginResultBean.isIs_check_phone());
        if (loginResultBean.getFamilys() != null && loginResultBean.getFamilys().size() > 0) {
            SPUtils.getInstance().put(ConstantValue.Family_Sid, loginResultBean.getFamilys().get(0).getSid());
            SPUtils.getInstance().put(ConstantValue.Family_Sid_Login, loginResultBean.getFamilys().get(0).getSid());
            SPUtils.getInstance().put(ConstantValue.Family_Sname, loginResultBean.getFamilys().get(0).getSname());
            SPUtils.getInstance().put(ConstantValue.Family_Sname_Login, loginResultBean.getFamilys().get(0).getSname());
            if (loginResultBean.getFamilys().get(0).getAuth() != null && loginResultBean.getFamilys().get(0).getAuth().size() > 0) {
                SPUtils.getInstance().put(ConstantValue.Family_Auth, loginResultBean.getFamilys().get(0).getAuth().toString());
            }
        }
        if (isSavePassword) {
            SPUtils.getInstance().put(ConstantValue.PASSWORD, edtPassword.getText().toString().trim());
        }
        MyApplication.getMyApp().setBeforeAccount(isBeforeAccount);
        MyApplication.getMyApp().setMergeAccount(isMergeAccount);
        launchActivity(MainActivity.newInstance());
        finish();
    }

    @Override
    public void getAppUpdateSuccess(CheckAppUpdateBean checkAppUpdateBean) {
        SPUtils.getInstance().put(ConstantValue.Is_Get_Update_App, true);
        if (!TextUtils.isEmpty(checkAppUpdateBean.getUrl())) {
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
                        onBackPressed();
                    } else {
                        dialog.dismiss();
                    }
                }
            });
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
    public void onBackPressed() {
        ActivityUtils.finishAllActivities();
        super.onBackPressed();
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
     * 检测是否有的权限都已经授权
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
            if (isAuth && isNeedCheck) {
                showMissingPermissionDialog();
            }
            isAuth = false;
            if (!isGetUpdateApp) {
                checkAppUpdate();
            }
        }
    }

    /**
     * 显示提示信息
     *
     * @since 2.5.0
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
     * 启动应用的设置
     *
     * @since 2.5.0
     */
    private void startAppSettings() {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    // ------------------------ 分割线：获取手机ip地址信息 ----------------------

    /**
     * 获取当前手机网络ip地址
     *
     * @return
     */
    private void getIPAddress() {
        new Thread(new Runnable() {
            @SuppressLint("ServiceCast")
            @Override
            public void run() {
                NetworkInfo info = ((ConnectivityManager) LoginActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                        try {
                            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                                NetworkInterface intf = en.nextElement();
                                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                                    InetAddress inetAddress = enumIpAddr.nextElement();
                                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                        mobileIpAddress = inetAddress.getHostAddress();
                                    }
                                }
                            }
                        } catch (SocketException e) {
                            e.printStackTrace();
                        }
                    } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                        wifiMacAddress = MacAddressUtils.getConnectedWifiMacAddress(MyApplication.getMyApp());
                        getMobileNetIp();
                    }
                } else {
                    //当前无网络连接,请在设置中打开网络
                }
            }
        }).start();
    }

    /**
     * 获取外网地址
     *
     * @return
     */
    private void getMobileNetIp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL infoUrl = null;
                InputStream inStream = null;
                try {
                    infoUrl = new URL(Api.App_GetIPUrl);
                    URLConnection connection = infoUrl.openConnection();
                    HttpURLConnection httpConnection = (HttpURLConnection) connection;
                    httpConnection.setReadTimeout(5000);//读取超时
                    httpConnection.setConnectTimeout(5000);//连接超时
                    httpConnection.setDoInput(true);
                    httpConnection.setUseCaches(false);

                    int responseCode = httpConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        inStream = httpConnection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
                        StringBuilder strber = new StringBuilder();
                        while ((mobileIpAddress = reader.readLine()) != null)
                            strber.append(mobileIpAddress + "\n");
                        inStream.close();
                        // 从反馈的结果中提取出IP地址
                        if (!TextUtils.isEmpty(strber.toString()) && strber.toString().contains("{") && strber.toString().contains("}")) {
                            int start = strber.indexOf("{");
                            int end = strber.indexOf("}");
                            String json = strber.substring(start, end + 1);
                            if (json != null) {
                                try {
                                    JSONObject jsonObject = new JSONObject(json);
                                    mobileIpAddress = jsonObject.optString("cip");
                                    mobileCName = jsonObject.optString("cname");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        if (TextUtils.isEmpty(mobileIpAddress)) {
                            mobileIpAddress = IPUtils.getWifiIPAddress(LoginActivity.this);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 获取华为推送token
     */
    private void getToken() {
        new Thread() {
            @SuppressLint("LogNotTimber")
            @Override
            public void run() {
                try {
                    // read from agconnect-services.json
//                    String appId = AGConnectServicesConfig.fromContext(LoginActivity.this).getString("client/app_id");
                    String appId = "103041235";
                    mHuaweiToken = HmsInstanceId.getInstance(LoginActivity.this).getToken(appId, "HCM");
                } catch (ApiException e) {
                    Log.e("kang", "get token failed, " + e);
                }
            }
        }.start();
    }


    /**
     * MyReceiver
     */
    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null && bundle.getString("msg") != null) {
                mHuaweiToken = bundle.getString("msg");
            }
        }
    }


    /**
     * 显示密码
     *
     * @param buttonView
     * @param isChecked
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            edtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            // 使光标始终在最后位置
            Editable etable = edtPassword.getText();
            Selection.setSelection(etable, etable.length());
        } else {
            edtPassword.setInputType(InputType.TYPE_CLASS_TEXT
                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            // 使光标始终在最后位置
            Editable editable = edtPassword.getText();
            Selection.setSelection(editable, editable.length());
        }
    }

    // --------------------------------------- 初始化推送及地图问题 ---------------------------------------

    private void initAppMapAndPush(){
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(MyApplication.getMyApp());
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.GCJ02);
        initEngineManager(MyApplication.getMyApp());
        if (!ManufacturerUtil.isHuaWeiPush(this)) { // 非华为使用小米推送
            initForXiaoMiPush();
        }
    }



    /**
     * 初始化百度街景
     *
     * @param context
     */
    public void initEngineManager(Context context) {
        if (mBMapManager == null) {
            mBMapManager = new BMapManager(context);
        }
        if (!mBMapManager.init(new LoginActivity.MyGeneralListener())) {
            ToastUtils.show(getString(R.string.txt_street_view_failed));
        }
    }

    // 常用事件监听，用来处理通常的网络错误，授权验证错误等
    public static class MyGeneralListener implements MKGeneralListener {

        @Override
        public void onGetPermissionState(int iError) {
            // 非零值表示key验证未通过
            if (iError != 0) {
                // 授权Key错误：
            }
        }
    }

    /**
     * 初始化小米推送
     */
    private void initForXiaoMiPush(){
        //初始化push推送服务
        if(shouldInit()) {
            MiPushClient.registerPush(MyApplication.getMyApp(), MyApplication.getMyApp().getAppId(), MyApplication.getMyApp().getAppKey());
        }
        //打开Log
        LoggerInterface newLogger = new LoggerInterface() {

            @Override
            public void setTag(String tag) {
                // ignore
            }

            @SuppressLint("LogNotTimber")
            @Override
            public void log(String content, Throwable t) {
                Log.d(TAG, content, t);
            }

            @SuppressLint("LogNotTimber")
            @Override
            public void log(String content) {
                Log.d(TAG, content);
            }
        };
        Logger.setLogger(this, newLogger);
    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getApplicationInfo().processName;
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

}
