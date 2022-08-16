package com.car.scth.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.car.scth.R;
import com.car.scth.di.component.DaggerUserInfoModifyComponent;
import com.car.scth.mvp.contract.UserInfoModifyContract;
import com.car.scth.mvp.model.api.ModuleValueService;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.ModifyPasswordPutBean;
import com.car.scth.mvp.presenter.UserInfoModifyPresenter;
import com.car.scth.mvp.ui.view.ClearEditText;
import com.car.scth.mvp.utils.ResultDataUtils;
import com.car.scth.mvp.utils.ToastUtils;
import com.car.scth.mvp.weiget.AuthoritySelectDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 修改用户信息
 * <p>
 * Created by MVPArmsTemplate on 03/12/2021 11:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class UserInfoModifyActivity extends BaseActivity<UserInfoModifyPresenter> implements UserInfoModifyContract.View {

    @BindView(R.id.tv_author)
    TextView tvAuthor; // 用户权限
    @BindView(R.id.edt_nike_name)
    ClearEditText edtNikeName; // 昵称
    @BindView(R.id.edt_email)
    ClearEditText edtEmail; // 优秀
    @BindView(R.id.btn_save)
    Button btnSave;

    private String mUid; // 用户id
    private String roleManage; // 用户权限
    private String nikeName; // 用户昵称
    private String mEmail; // 邮箱
    private boolean isMine; // 是否是当前用户

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerUserInfoModifyComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_user_info_modify;//setContentView(id);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_modify_user_info));

        mUid = getIntent().getStringExtra("uid");
        roleManage = getIntent().getStringExtra("role");
        nikeName = getIntent().getStringExtra("nike_name");
        mEmail = getIntent().getStringExtra("email");
        isMine = getIntent().getBooleanExtra("is_mine", false);

        onShowAuthority();
        if (!TextUtils.isEmpty(nikeName)){
            edtNikeName.setText(nikeName);
        }
        if (!TextUtils.isEmpty(mEmail)){
            edtEmail.setText(mEmail);
        }
    }

    /**
     * 显示权限
     */
    private void onShowAuthority(){
        if (!TextUtils.isEmpty(roleManage)){
            if (roleManage.equals(ResultDataUtils.Account_Type_Manager)){
                tvAuthor.setText(getString(R.string.txt_role_manager));
            }else{
                tvAuthor.setText(getString(R.string.txt_role_user));
            }
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

    @OnClick({R.id.tv_author, R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_author:
                onAuthoritySelect();
                break;
            case R.id.btn_save:
                onConfirm();
                break;
        }
    }

    /**
     * 权限选择
     */
    private void onAuthoritySelect(){
        if (isMine){
            ToastUtils.show(getString(R.string.txt_operating_authorization_not));
            return;
        }
        AuthoritySelectDialog dialog = new AuthoritySelectDialog();
        dialog.show(getSupportFragmentManager(), new AuthoritySelectDialog.onAuthoritySelectChange() {
            @Override
            public void onAuthoritySelect(String authority) {
                roleManage = authority;
                onShowAuthority();
            }
        });
    }

    /**
     * 确认提交
     */
    private void onConfirm(){
        nikeName = edtNikeName.getText().toString().trim();
        mEmail = edtEmail.getText().toString().trim();
        if (TextUtils.isEmpty(roleManage)){
            ToastUtils.show(getString(R.string.txt_user_author_select_hint));
            return;
        }

        ModifyPasswordPutBean.ParamsBean.InfoBean infoBean = new ModifyPasswordPutBean.ParamsBean.InfoBean();
        if (!isMine){
            infoBean.setRole(roleManage);
        }
        infoBean.setUid(mUid);
        if (!TextUtils.isEmpty(nikeName)){
            infoBean.setNick_name(nikeName);
        }
        if (!TextUtils.isEmpty(mEmail)){
            infoBean.setEmail(mEmail);
        }

        ModifyPasswordPutBean.ParamsBean paramsBean = new ModifyPasswordPutBean.ParamsBean();
        paramsBean.setInfo(infoBean);

        ModifyPasswordPutBean bean = new ModifyPasswordPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Set_Account);
        bean.setModule(ModuleValueService.Module_For_Set_Account);

        if (getPresenter() != null) {
            getPresenter().submitModifyUserInfo(bean);
        }
    }

    @Override
    public void submitModifyUserInfoSuccess(BaseBean baseBean) {
        ToastUtils.show(getString(R.string.txt_modify_success));
        setResult(Activity.RESULT_OK);
        finish();
    }
}
