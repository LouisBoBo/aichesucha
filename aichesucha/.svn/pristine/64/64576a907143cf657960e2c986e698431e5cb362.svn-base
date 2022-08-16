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
import com.car.scth.di.component.DaggerAddAccountComponent;
import com.car.scth.mvp.contract.AddAccountContract;
import com.car.scth.mvp.model.api.ModuleValueService;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.AddAccountPutBean;
import com.car.scth.mvp.presenter.AddAccountPresenter;
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
 * Description: 添加用户
 * <p>
 * Created by MVPArmsTemplate on 03/12/2021 14:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class AddAccountActivity extends BaseActivity<AddAccountPresenter> implements AddAccountContract.View {

    @BindView(R.id.edt_account)
    ClearEditText edtAccount; // 账号
    @BindView(R.id.tv_author)
    TextView tvAuthor; // 用户权限
    @BindView(R.id.edt_nike_name)
    ClearEditText edtNikeName; // 用户昵称
    @BindView(R.id.edt_email)
    ClearEditText edtEmail; // 邮箱
    @BindView(R.id.btn_save)
    Button btnSave; // 保存

    private String mFamilyId; // 管理员的组织id
    private String mRoteManage; // 用户权限

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAddAccountComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_add_account;//setContentView(id);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_add_account));
        mFamilyId = getIntent().getStringExtra("familyId");
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
            case R.id.tv_author: // 选择权限
                onAuthoritySelect();
                break;
            case R.id.btn_save: // 保存
                submitAddAccount();
                break;
        }
    }

    /**
     * 权限选择
     */
    private void onAuthoritySelect(){
        AuthoritySelectDialog dialog = new AuthoritySelectDialog();
        dialog.show(getSupportFragmentManager(), new AuthoritySelectDialog.onAuthoritySelectChange() {
            @Override
            public void onAuthoritySelect(String authority) {
                mRoteManage = authority;
                onShowAuthority();
            }
        });
    }

    /**
     * 显示权限
     */
    private void onShowAuthority(){
        if (mRoteManage.equals(ResultDataUtils.Account_Type_Manager)){
            tvAuthor.setText(getString(R.string.txt_role_manager));
        }else{
            tvAuthor.setText(getString(R.string.txt_role_user));
        }
    }

    /**
     * 提交添加用户
     */
    private void submitAddAccount(){
        String account = edtAccount.getText().toString().trim().toLowerCase();
        String nikeName = edtNikeName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        if (TextUtils.isEmpty(account)){
            ToastUtils.show(getString(R.string.txt_add_family_account_hint));
            return;
        }
        if (TextUtils.isEmpty(mRoteManage)){
            ToastUtils.show(getString(R.string.txt_user_author_select_hint));
            return;
        }

        AddAccountPutBean.ParamsBean.InfoBean infoBean = new AddAccountPutBean.ParamsBean.InfoBean();
        infoBean.setAccount(account);
        infoBean.setRole(mRoteManage);
        if (!TextUtils.isEmpty(email)){
            infoBean.setEmail(email);
        }
        if (!TextUtils.isEmpty(nikeName)){
            infoBean.setNick_name(nikeName);
        }

        AddAccountPutBean.ParamsBean paramsBean = new AddAccountPutBean.ParamsBean();
        paramsBean.setInfo(infoBean);
        paramsBean.setFamilyid(mFamilyId);

        AddAccountPutBean bean = new AddAccountPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Add_Account);
        bean.setModule(ModuleValueService.Module_For_Add_Account);

        if (getPresenter() != null){
            getPresenter().submitAddAccount(bean);
        }
    }

    @Override
    public void submitAddAccountSuccess(BaseBean baseBean) {
        ToastUtils.show(getString(R.string.txt_add_success));
        setResult(Activity.RESULT_OK);
        finish();
    }
}
