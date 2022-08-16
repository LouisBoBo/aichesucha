package com.car.scth.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.car.scth.R;
import com.car.scth.di.component.DaggerBindMobileComponent;
import com.car.scth.mvp.contract.BindMobileContract;
import com.car.scth.mvp.model.api.Api;
import com.car.scth.mvp.model.api.ModuleValueService;
import com.car.scth.mvp.model.bean.PhoneAreaResultBean;
import com.car.scth.mvp.model.bean.PhoneCodeResultBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.ModifyPasswordPutBean;
import com.car.scth.mvp.model.putbean.PhoneAreaPutBean;
import com.car.scth.mvp.model.putbean.PhoneCodePutBean;
import com.car.scth.mvp.presenter.BindMobilePresenter;
import com.car.scth.mvp.ui.view.ClearEditText;
import com.car.scth.mvp.utils.ConstantValue;
import com.car.scth.mvp.utils.MD5Utils;
import com.car.scth.mvp.utils.ResultDataUtils;
import com.car.scth.mvp.utils.ToastUtils;
import com.car.scth.mvp.utils.Utils;
import com.car.scth.mvp.weiget.PhoneAreaDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import com.car.scth.mvp.weiget.PhoneHasBindDialog;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 绑定手机号码
 * <p>
 * Created by MVPArmsTemplate on 02/25/2021 10:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class BindMobileActivity extends BaseActivity<BindMobilePresenter> implements BindMobileContract.View {

    @BindView(R.id.tv_phone_area)
    TextView tvPhoneArea;
    @BindView(R.id.ll_mobile_area)
    LinearLayout llMobileArea;
    @BindView(R.id.edt_mobile)
    ClearEditText edtMobile;
    @BindView(R.id.edt_code)
    ClearEditText edtCode;
    @BindView(R.id.tv_send_code)
    TextView tvSendCode;
    @BindView(R.id.btn_bind)
    Button btnBind;
    @BindView(R.id.edt_password)
    ClearEditText edtPassword;
    @BindView(R.id.edt_password_confirm)
    ClearEditText edtPasswordConfirm;
    @BindView(R.id.ll_password)
    LinearLayout llPassword;
    @BindView(R.id.view_password)
    View viewPassword;
    @BindView(R.id.ll_password_confirm)
    LinearLayout llPasswordConfirm;
    @BindView(R.id.view_password_confirm)
    View viewPasswordConfirm;
    @BindView(R.id.ll_mobile)
    LinearLayout llMobile;
    @BindView(R.id.ll_mobile_code)
    LinearLayout llMobileCode;
    @BindView(R.id.view_phone)
    View viewPhone;
    @BindView(R.id.view_phone_code)
    View viewPhoneCode;

    private String mPhoneZone = "86"; // 电话号码区号
    private List<PhoneAreaResultBean.ItemsBean> phoneAreaBeans; // 手机号码国际区号
    private Disposable disposable; // 验证码倒计时
    private String mSid;
    private String mPassword; // 密码
    private String mLoginFlag = ""; // 登录标识:用户登录or设备号登录
    private boolean isModifyPassword = false; // 是否需要修改密码
    private boolean isBindMobile = false; // 是否需要绑定手机号码

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBindMobileComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_bind_mobile;//setContentView(id);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_bind_mobile));
        phoneAreaBeans = new ArrayList<>();
        tvPhoneArea.setText("+" + mPhoneZone);
        mSid = getIntent().getStringExtra("sid");
        mPassword = getIntent().getStringExtra("password");
        mLoginFlag = SPUtils.getInstance().getString(ConstantValue.USER_LOGIN_TYPE);
        isModifyPassword = SPUtils.getInstance().getBoolean(ConstantValue.Is_Modify_Password);
        isBindMobile = SPUtils.getInstance().getBoolean(ConstantValue.Is_Bind_Mobile);

        if (isModifyPassword) {
            llPassword.setVisibility(View.VISIBLE);
            viewPassword.setVisibility(View.VISIBLE);
            llPasswordConfirm.setVisibility(View.VISIBLE);
            viewPasswordConfirm.setVisibility(View.VISIBLE);
        } else {
            llPassword.setVisibility(View.GONE);
            viewPassword.setVisibility(View.GONE);
            llPasswordConfirm.setVisibility(View.GONE);
            viewPasswordConfirm.setVisibility(View.GONE);
        }
        if (isBindMobile) {
            llMobile.setVisibility(View.VISIBLE);
            llMobileCode.setVisibility(View.VISIBLE);
        } else {
            llMobile.setVisibility(View.GONE);
            llMobileCode.setVisibility(View.GONE);
            viewPhone.setVisibility(View.GONE);
            viewPhoneCode.setVisibility(View.GONE);
        }

        if (!isBindMobile) {
            setTitle(getString(R.string.txt_modify_password));
            btnBind.setText(getText(R.string.txt_confirm));
        } else {
            setTitle(getString(R.string.txt_bind_mobile));
            btnBind.setText(getText(R.string.txt_bind_submit));
        }

        getPhoneArea();
    }

    /**
     * 获取手机号码国际区号
     */
    private void getPhoneArea() {
        PhoneAreaPutBean.ParamsBean paramsBean = new PhoneAreaPutBean.ParamsBean();
        PhoneAreaPutBean bean = new PhoneAreaPutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Phone_Area);
        bean.setModule(ModuleValueService.Module_For_Phone_Area);
        bean.setParams(paramsBean);

        if (getPresenter() != null) {
            getPresenter().getPhoneArea(bean);
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

    @OnClick({R.id.ll_mobile_area, R.id.tv_send_code, R.id.btn_bind})
    public void onViewClicked(View view) {
        if (Utils.isButtonQuickClick(System.currentTimeMillis())) {
            switch (view.getId()) {
                case R.id.ll_mobile_area: // 选择区号
                    hideKeyboard(edtMobile);
                    onPhoneAreaSelect();
                    break;
                case R.id.tv_send_code: // 发送验证码
                    hideKeyboard(edtCode);
                    onSendPhoneCode();
                    break;
                case R.id.btn_bind: // 绑定
                    hideKeyboard(edtMobile);
                    submitBindMobile(false);
                    break;
            }
        }
    }

    /**
     * 提交绑定
     *
     * @param isChangeBind 是否更换手机号码绑定的imei号为当前imei号,默认false
     */
    private void submitBindMobile(boolean isChangeBind) {
        String mobile = edtMobile.getText().toString().trim();
        String code = edtCode.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String passwordConfirm = edtPasswordConfirm.getText().toString().trim();
        if (isBindMobile) {
            if (TextUtils.isEmpty(mobile)) {
                ToastUtils.show(getString(R.string.txt_input_phone_hint));
                return;
            }
            if (TextUtils.isEmpty(code)) {
                ToastUtils.show(getString(R.string.txt_input_code_hint));
                return;
            }
        }
        if (isModifyPassword){
            if (TextUtils.isEmpty(password)) {
                ToastUtils.show(getString(R.string.txt_input_new_password));
                return;
            }
            if (TextUtils.isEmpty(passwordConfirm)) {
                ToastUtils.show(getString(R.string.txt_input_new_password_confirm));
                return;
            }
            if (!passwordConfirm.equals(password)) {
                ToastUtils.show(getString(R.string.txt_password_error_hint));
                return;
            }
        }

        ModifyPasswordPutBean.ParamsBean.InfoBean infoBean = new ModifyPasswordPutBean.ParamsBean.InfoBean();
        if (isBindMobile){
            infoBean.setPhone(mobile);
        }
        if (isModifyPassword){
            infoBean.setPwd(password);
        }

        ModifyPasswordPutBean.ParamsBean paramsBean = new ModifyPasswordPutBean.ParamsBean();
        paramsBean.setInfo(infoBean);
        if (isChangeBind) {
            paramsBean.setChange_bind(true);
        }
        if (isBindMobile){
            paramsBean.setCode(code);
            paramsBean.setZone(mPhoneZone);
            paramsBean.setKey(Api.Mob_App_Key);
        }
        paramsBean.setPwd_md5(MD5Utils.getMD5Encryption(mPassword));

        ModifyPasswordPutBean bean = new ModifyPasswordPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Set_Account);
        bean.setModule(ModuleValueService.Module_For_Set_Account);

        if (getPresenter() != null) {
            getPresenter().submitBindMobile(bean, mSid, isChangeBind);
        }
    }

    /**
     * 选择手机号码国际区号
     */
    private void onPhoneAreaSelect() {
        if (phoneAreaBeans.size() == 0) {
            getPhoneArea();
        } else {
            PhoneAreaDialog dialog = new PhoneAreaDialog();
            dialog.show(getSupportFragmentManager(), phoneAreaBeans, new PhoneAreaDialog.onPhoneAreaChange() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onPhoneArea(int zone) {
                    mPhoneZone = zone + "";
                    tvPhoneArea.setText("+" + mPhoneZone);
                }
            });
        }
    }

    /**
     * 发送验证码
     */
    private void onSendPhoneCode() {
        String mobile = edtMobile.getText().toString().trim();
        if (TextUtils.isEmpty(mobile)) {
            ToastUtils.show(getString(R.string.txt_input_phone_hint));
            return;
        }

        PhoneCodePutBean.ParamsBean paramsBean = new PhoneCodePutBean.ParamsBean();
        paramsBean.setCode(Api.Mob_Module_Code);
        paramsBean.setKey(Api.Mob_App_Key);
        paramsBean.setPhone(mobile);
        paramsBean.setZone(mPhoneZone);

        PhoneCodePutBean bean = new PhoneCodePutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Phone_Code);
        bean.setModule(ModuleValueService.Module_For_Phone_Code);

        if (getPresenter() != null) {
            getPresenter().getPhoneCode(bean);
        }
    }

    @Override
    public void getPhoneAreaSuccess(PhoneAreaResultBean phoneAreaResultBean) {
        phoneAreaBeans.clear();
        if (phoneAreaResultBean.getItems() != null)
            phoneAreaBeans.addAll(phoneAreaResultBean.getItems());
    }

    @Override
    public void getPhoneCodeSuccess(PhoneCodeResultBean phoneCodeResultBean) {
        ToastUtils.show(getString(R.string.errcode_success));
        countDownTime();
    }

    @Override
    public void submitBindMobileSuccess(BaseBean baseBean, boolean isChangeBind) {
        if (baseBean.isSuccess()) {
            SPUtils.getInstance().put(ConstantValue.Is_Need_Check, false);
            if (ConstantValue.isSavePassword()) {
                SPUtils.getInstance().put(ConstantValue.PASSWORD, edtPassword.getText().toString().trim());
            }
            if (isChangeBind){
                ToastUtils.show(getString(R.string.txt_bind_phone_success_two));
            }else{
                if (!isBindMobile) {
                    com.blankj.utilcode.util.ToastUtils.showShort(getString(R.string.txt_modify_success));
                } else {
                    com.blankj.utilcode.util.ToastUtils.showShort(getString(R.string.txt_bind_success));
                }
            }
            Intent intent = new Intent();
            if (isModifyPassword) {
                intent.putExtra("password", edtPassword.getText().toString().trim());
            } else {
                intent.putExtra("password", mPassword);
            }
            setResult(Activity.RESULT_OK, intent);
            finish();
        } else if (baseBean.getErrcode() == Api.Mobile_Bind_Used) {
            if (mLoginFlag.equals(ResultDataUtils.Login_type_Device) || mLoginFlag.equals(ResultDataUtils.Login_type_Phone_Device)) {
                onPhoneHasBinding();
            }
        }
    }

    /**
     * 该手机号码已经绑定了设备
     */
    private void onPhoneHasBinding() {
        PhoneHasBindDialog dialog = new PhoneHasBindDialog();
        dialog.show(getSupportFragmentManager(), new PhoneHasBindDialog.onPhoneHasBindChange() {
            @Override
            public void onRegister() {
                onToRegister();
            }

            @Override
            public void onUnbind() {
                submitBindMobile(true);
            }
        });
    }

    /**
     * 去注册
     */
    private void onToRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("phone", edtMobile.getText().toString().trim());
        intent.putExtra("password", edtPassword.getText().toString().trim());
        startActivity(intent);
        finish();
    }

    /**
     * 获取验证码倒计时
     */
    public void countDownTime() {
        final int count = 60;//倒计时10秒
        //ui线程中进行控件更新
        Observable<Long> countDownObservable = Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(count + 1)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return count - aLong;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//ui线程中进行控件更新
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        tvSendCode.setEnabled(false);
                    }
                });

        //回复原来初始状态
        disposable = countDownObservable.subscribe(new Consumer<Long>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void accept(Long aLong) throws Exception {
                tvSendCode.setText(aLong + "s");
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                //回复原来初始状态
                tvSendCode.setEnabled(true);
                tvSendCode.setText(getString(R.string.txt_send_code));
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (disposable != null) {
            disposable.dispose();
        }
        super.onDestroy();
    }

}
