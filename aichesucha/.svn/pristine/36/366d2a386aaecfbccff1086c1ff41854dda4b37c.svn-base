package com.car.scth.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.car.scth.R;
import com.car.scth.app.MyApplication;
import com.car.scth.di.component.DaggerAddFamilyComponent;
import com.car.scth.mvp.contract.AddFamilyContract;
import com.car.scth.mvp.model.api.ModuleValueService;
import com.car.scth.mvp.model.bean.FamilyAddResultBean;
import com.car.scth.mvp.model.putbean.AddFamilyPutBean;
import com.car.scth.mvp.presenter.AddFamilyPresenter;
import com.car.scth.mvp.ui.view.ClearEditText;
import com.car.scth.mvp.utils.ToastUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/11/2021 16:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class AddFamilyActivity extends BaseActivity<AddFamilyPresenter> implements AddFamilyContract.View {

    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.add_family_account)
    ClearEditText addAccount; // 账号
    @BindView(R.id.add_family_name)
    ClearEditText addName; // 名称
    @BindView(R.id.add_family_email)
    ClearEditText addEmail; // 邮箱

    private String familyId = "";
    private String familyName = "";
    private String account = "";

    public static Intent newInstance() {
        return new Intent(MyApplication.getMyApp(), AddFamilyActivity.class);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAddFamilyComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_add_family;//setContentView(id);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_add_family));
        toolbarRight.setVisibility(View.VISIBLE);
        toolbarRight.setText(getString(R.string.txt_save));
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            familyId = intent.getStringExtra("familyId");
        }

    }

    @OnClick({R.id.toolbar_right})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.toolbar_right) {
            hideKeyboard(view);
            addFamily();
        }
    }

    private void addFamily() {
        account = addAccount.getText().toString().trim().toLowerCase();
        familyName = addName.getText().toString().trim();
        String email = addEmail.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            ToastUtils.show(getString(R.string.txt_add_family_account_hint));
            return;
        }
        AddFamilyPutBean bean = new AddFamilyPutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Add_Family_Name);
        bean.setModule(ModuleValueService.Module_For_Add_Family_Name);

        AddFamilyPutBean.ParamsBean paramsBean = new AddFamilyPutBean.ParamsBean();
        paramsBean.setParent_id(familyId);
        if (!TextUtils.isEmpty(familyName)){
            paramsBean.setFamily_name(familyName);
        }
        AddFamilyPutBean.ParamsBean.InfoBean info = new AddFamilyPutBean.ParamsBean.InfoBean();
        if (!TextUtils.isEmpty(email)) {
            info.setEmail(email);
        }
        info.setAccount(account);
        paramsBean.setInfo(info);
        bean.setParams(paramsBean);

        if (getPresenter() != null)
            getPresenter().submitAddFamily(bean);
    }

    @Override
    public void addFamilySuccess(FamilyAddResultBean bean) {
        if (bean.isSuccess()) {
            Intent intent = new Intent();
            if (!TextUtils.isEmpty(familyName)) {
                intent.putExtra("familyName", familyName);
            } else {
                intent.putExtra("familyName", account);
            }
            intent.putExtra("familyId", bean.getFid());
            setResult(200, intent);
            finish();
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
}
