package com.car.scth.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.car.scth.R;
import com.car.scth.app.MyApplication;
import com.car.scth.di.component.DaggerModifyPasswordComponent;
import com.car.scth.mvp.contract.ModifyPasswordContract;
import com.car.scth.mvp.model.api.ModuleValueService;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.ModifyPasswordPutBean;
import com.car.scth.mvp.presenter.ModifyPasswordPresenter;
import com.car.scth.mvp.utils.ConstantValue;
import com.car.scth.mvp.utils.MD5Utils;
import com.car.scth.mvp.utils.ToastUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 修改密码
 * <p>
 * Created by MVPArmsTemplate on 01/18/2021 16:35
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ModifyPasswordActivity extends BaseActivity<ModifyPasswordPresenter> implements ModifyPasswordContract.View {

    @BindView(R.id.edt_old_password)
    EditText edtOldPassword;
    @BindView(R.id.edt_new_password)
    EditText edtNewPassword;
    @BindView(R.id.edt_new_password_confirm)
    EditText edtNewPasswordConfirm;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;

    public static Intent newInstance() {
        return new Intent(MyApplication.getMyApp(), ModifyPasswordActivity.class);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerModifyPasswordComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_modify_password;//setContentView(id);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_change_password));

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

    @OnClick(R.id.btn_confirm)
    public void onViewClicked() {
        hideKeyboard(edtNewPassword);
        String oldPassword = edtOldPassword.getText().toString().trim();
        String newPassword = edtNewPassword.getText().toString().trim();
        String newPasswordConfirm = edtNewPasswordConfirm.getText().toString().trim();
        if (TextUtils.isEmpty(oldPassword)){
            ToastUtils.show(getString(R.string.txt_input_old_password));
            return;
        }
        if (TextUtils.isEmpty(newPassword)){
            ToastUtils.show(getString(R.string.txt_input_new_password));
            return;
        }
        if (TextUtils.isEmpty(newPasswordConfirm)){
            ToastUtils.show(getString(R.string.txt_input_new_password_confirm));
            return;
        }
        if (!newPassword.equals(newPasswordConfirm)){
            ToastUtils.show(getString(R.string.txt_password_error_hint));
            return;
        }
        if (oldPassword.equals(newPassword)) {
            ToastUtils.show(getString(R.string.txt_edit_old_equals_new_password));
            return;
        }

        ModifyPasswordPutBean.ParamsBean.InfoBean infoBean = new ModifyPasswordPutBean.ParamsBean.InfoBean();
        infoBean.setPwd(newPassword);
        ModifyPasswordPutBean.ParamsBean paramsBean = new ModifyPasswordPutBean.ParamsBean();
        paramsBean.setInfo(infoBean);
        paramsBean.setPwd_md5(MD5Utils.getMD5Encryption(oldPassword));
        ModifyPasswordPutBean bean = new ModifyPasswordPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Set_Account);
        bean.setModule(ModuleValueService.Module_For_Set_Account);

        if (getPresenter() != null){
            getPresenter().submitModifyPassword(bean);
        }
    }

    @Override
    public void submitModifyPasswordSuccess(BaseBean baseBean) {
        ToastUtils.show(getString(R.string.txt_modify_success));
        if (!TextUtils.isEmpty(ConstantValue.getPassword())){
            SPUtils.getInstance().put(ConstantValue.PASSWORD, "");
        }
        ActivityUtils.finishAllActivities();
        launchActivity(LoginActivity.newInstance(0));
    }
}
