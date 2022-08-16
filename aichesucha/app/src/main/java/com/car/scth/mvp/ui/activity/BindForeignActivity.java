package com.car.scth.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.OnClick;
import com.car.scth.di.component.DaggerBindForeignComponent;
import com.car.scth.mvp.contract.BindForeignContract;
import com.car.scth.mvp.model.api.Api;
import com.car.scth.mvp.model.api.ModuleValueService;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.ModifyPasswordPutBean;
import com.car.scth.mvp.presenter.BindForeignPresenter;

import com.car.scth.R;
import com.car.scth.mvp.ui.view.ClearEditText;
import com.car.scth.mvp.utils.ConstantValue;
import com.car.scth.mvp.utils.MD5Utils;
import com.car.scth.mvp.utils.ResultDataUtils;
import com.car.scth.mvp.utils.Utils;
import com.car.scth.mvp.weiget.PhoneHasBindDialog;


import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 设备号国外登录
 * <p>
 * Created by MVPArmsTemplate on 11/09/2021 16:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class BindForeignActivity extends BaseActivity<BindForeignPresenter> implements BindForeignContract.View {

    @BindView(R.id.edt_password)
    ClearEditText edtPassword;
    @BindView(R.id.edt_password_confirm)
    ClearEditText edtPasswordConfirm;

    private String mSid;
    private String mPassword; // 密码
    private String mLoginFlag = ""; // 登录标识:用户登录or设备号登录

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBindForeignComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_bind_foreign; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_modify_password));
        mSid = getIntent().getStringExtra("sid");
        mPassword = getIntent().getStringExtra("password");
        mLoginFlag = SPUtils.getInstance().getString(ConstantValue.USER_LOGIN_TYPE);
    }

    @OnClick({R.id.btn_bind})
    public void onViewClicked(View view) {
        if (Utils.isButtonQuickClick(System.currentTimeMillis())) {
            if (view.getId() == R.id.btn_bind) { // 绑定
                hideKeyboard(edtPassword);
                submitBindMobile(false);
            }
        }
    }

    /**
     * 提交绑定
     *
     * @param isChangeBind 是否更换手机号码绑定的imei号为当前imei号,默认false
     */
    private void submitBindMobile(boolean isChangeBind) {
        String password = edtPassword.getText().toString().trim();
        String passwordConfirm = edtPasswordConfirm.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showShort(getString(R.string.txt_input_new_password));
            return;
        }
        if (TextUtils.isEmpty(passwordConfirm)) {
            ToastUtils.showShort(getString(R.string.txt_input_new_password_confirm));
            return;
        }
        if (!passwordConfirm.equals(password)) {
            ToastUtils.showShort(getString(R.string.txt_password_error_hint));
            return;
        }

        ModifyPasswordPutBean.ParamsBean.InfoBean infoBean = new ModifyPasswordPutBean.ParamsBean.InfoBean();
        infoBean.setPwd(password);
        ModifyPasswordPutBean.ParamsBean paramsBean = new ModifyPasswordPutBean.ParamsBean();
        paramsBean.setInfo(infoBean);
        if (isChangeBind) {
            paramsBean.setChange_bind(true);
        }
        paramsBean.setPwd_md5(MD5Utils.getMD5Encryption(mPassword));
        paramsBean.setCheck_phone(false); // 国外设备号 登入传 false
        ModifyPasswordPutBean bean = new ModifyPasswordPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Set_Account);
        bean.setModule(ModuleValueService.Module_For_Set_Account);

        if (getPresenter() != null) {
            getPresenter().submitBindMobile(bean, mSid, isChangeBind);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
    public void submitBindMobileSuccess(BaseBean baseBean, boolean isChangeBind) {
        if (baseBean.isSuccess()) {
            SPUtils.getInstance().put(ConstantValue.Is_Need_Check, false);
            if (ConstantValue.isSavePassword()) {
                SPUtils.getInstance().put(ConstantValue.PASSWORD, edtPassword.getText().toString().trim());
            }
            if (isChangeBind) {
                ToastUtils.showShort(getString(R.string.txt_bind_phone_success_two));
            } else {
                ToastUtils.showShort(getString(R.string.txt_modify_success));
            }
            Intent intent = new Intent();
            intent.putExtra("password", edtPassword.getText().toString().trim());
            setResult(Activity.RESULT_OK, intent);
            finish();
        } else if (baseBean.getErrcode() == Api.Mobile_Bind_Used) {
            if (mLoginFlag.equals(ResultDataUtils.Login_type_Device) || mLoginFlag.equals(ResultDataUtils.Login_type_Phone_Device)) {
                onPhoneHasBinding();
            }
        } else {
            com.blankj.utilcode.util.ToastUtils.showShort(baseBean.getError_message());
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
        Intent intent = new Intent(this, RegisterForeignActivity.class);
        startActivity(intent);
        finish();
    }
}
