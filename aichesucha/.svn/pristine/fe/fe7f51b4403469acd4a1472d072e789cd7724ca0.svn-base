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
import com.car.scth.di.component.DaggerRegisterComponent;
import com.car.scth.mvp.contract.RegisterContract;
import com.car.scth.mvp.model.api.Api;
import com.car.scth.mvp.model.api.ModuleValueService;
import com.car.scth.mvp.model.bean.PhoneAreaResultBean;
import com.car.scth.mvp.model.bean.PhoneCodeResultBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.PhoneAreaPutBean;
import com.car.scth.mvp.model.putbean.PhoneCodePutBean;
import com.car.scth.mvp.model.putbean.RegisterPutBean;
import com.car.scth.mvp.presenter.RegisterPresenter;
import com.car.scth.mvp.ui.view.ClearEditText;
import com.car.scth.mvp.utils.ConstantValue;
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

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/21/2020 10:54
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View {

    @BindView(R.id.edt_account)
    ClearEditText edtAccount; // 账号
    @BindView(R.id.edt_mobile)
    ClearEditText edtMobile; // 手机号码
    @BindView(R.id.edt_code)
    ClearEditText edtCode; // 手机验证码
    @BindView(R.id.tv_send_code)
    TextView tvSendCode; // 发送手机验证码
    @BindView(R.id.edt_email)
    ClearEditText edtEmail; // 邮箱
    @BindView(R.id.edt_password)
    ClearEditText edtPassword; // 密码
    @BindView(R.id.edt_password_confirm)
    ClearEditText edtPasswordConfirm; // 确认密码
    @BindView(R.id.btn_register)
    Button btnRegister; // 注册
    @BindView(R.id.tv_phone_area)
    TextView tvPhoneArea; // 手机号码区号
    @BindView(R.id.ll_mobile_area)
    LinearLayout llMobileArea; // 手机号码区号
    @BindView(R.id.ll_phone)
    LinearLayout llPhone; // 输入手机号
    @BindView(R.id.ll_phone_code)
    LinearLayout llPhoneCode; // 手机验证码
    @BindView(R.id.view_phone_code)
    View viewCode;
    @BindView(R.id.view_phone)
    View viewPhone;

    private String mPhoneZone = "86"; // 电话号码区号
    private List<PhoneAreaResultBean.ItemsBean> phoneAreaBeans; // 手机号码国际区号
    private Disposable disposable; // 验证码倒计时
    private boolean isCN ; // 是否国内用户

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRegisterComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_register;//setContentView(id);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_register_account));
        phoneAreaBeans = new ArrayList<>();
        tvPhoneArea.setText("+" + mPhoneZone);
        edtAccount.setHint(getString(R.string.txt_input_account_hint_cn));
        String mPhone = "";
        if (getIntent() != null) {
            if (getIntent().hasExtra("phone")) {
                mPhone = getIntent().getStringExtra("phone");
            }
        }
        if (!TextUtils.isEmpty(mPhone)) {
            edtMobile.setText(mPhone);
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

    @OnClick({R.id.tv_send_code, R.id.btn_register, R.id.ll_mobile_area})
    public void onViewClicked(View view) {
        if (Utils.isButtonQuickClick(System.currentTimeMillis())) {
            switch (view.getId()) {
                case R.id.tv_send_code: // 发送验证码
                    onSendPhoneCode();
                    break;
                case R.id.btn_register: // 注册
                    submitRegister();
                    break;
                case R.id.ll_mobile_area: // 选择手机号码国际区号
                    onPhoneAreaSelect();
                    break;
            }
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

        if (getPresenter() != null){
            getPresenter().getPhoneCode(bean);
        }
    }

    /**
     * 提交注册
     */
    private void submitRegister() {
        String account = edtAccount.getText().toString().trim().toLowerCase();
        String mobile = edtMobile.getText().toString().trim();
        String code = edtCode.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String passwordConfirm = edtPasswordConfirm.getText().toString().trim();

//        if (!isCN && TextUtils.isEmpty(account)) {
//            ToastUtils.show(getString(R.string.txt_input_account_hint));
//            return;
//        }
        if (TextUtils.isEmpty(mobile)) {
            ToastUtils.show(getString(R.string.txt_input_phone_hint));
            return;
        }
        if (TextUtils.isEmpty(code)) {
            ToastUtils.show(getString(R.string.txt_input_code_hint));
            return;
        }
        if (!TextUtils.isEmpty(email)) {
            if (!Utils.isEmail(email)) {
                ToastUtils.show(getString(R.string.txt_email_error));
                return;
            }
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtils.show(getString(R.string.txt_input_password));
            return;
        }
        if (TextUtils.isEmpty(passwordConfirm)) {
            ToastUtils.show(getString(R.string.txt_input_password_confirm));
            return;
        }
        if (!passwordConfirm.equals(password)) {
            ToastUtils.show(getString(R.string.txt_password_error_hint));
            return;
        }

        RegisterPutBean.ParamsBean.InfoBean infoBean = new RegisterPutBean.ParamsBean.InfoBean();
        if (!TextUtils.isEmpty(account)) {
            infoBean.setAccount(account);
        }
        infoBean.setPhone(mobile);
        infoBean.setPwd(password);
        if (!TextUtils.isEmpty(email)) {
            infoBean.setEmail(email);
        }

        RegisterPutBean.ParamsBean paramsBean = new RegisterPutBean.ParamsBean();
        paramsBean.setInfo(infoBean);
        paramsBean.setCode(code);
        paramsBean.setKey(Api.Mob_App_Key);
        paramsBean.setType(Api.App_Type);
        paramsBean.setZone(mPhoneZone);

        RegisterPutBean bean = new RegisterPutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Register);
        bean.setModule(ModuleValueService.Module_For_Register);
        bean.setParams(paramsBean);

        if (getPresenter() != null) {
            getPresenter().submitRegister(bean);
        }
    }

    @Override
    public void submitRegisterSuccess(BaseBean baseBean) {
        ToastUtils.show(getString(R.string.txt_register_success));
        Intent intent = new Intent();
        String account = edtAccount.getText().toString().trim();
        if (account.length() == 0) {
            intent.putExtra("account", edtMobile.getText().toString().trim());
        } else {
            intent.putExtra("account", account);
        }
        intent.putExtra("password", edtPassword.getText().toString().trim());
        setResult(Activity.RESULT_OK, intent);
        finish();
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
