package com.car.scth.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.car.scth.R;
import com.car.scth.app.MyApplication;
import com.car.scth.di.component.DaggerOilElectricityControlComponent;
import com.car.scth.mvp.contract.OilElectricityControlContract;
import com.car.scth.mvp.model.api.ModuleValueService;
import com.car.scth.mvp.model.bean.AlertCurrentBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.ModifyPasswordPutBean;
import com.car.scth.mvp.model.putbean.OilSetPutBean;
import com.car.scth.mvp.presenter.OilElectricityControlPresenter;
import com.car.scth.mvp.utils.MD5Utils;
import com.car.scth.mvp.utils.ToastUtils;
import com.car.scth.mvp.weiget.AlarmCurrentEditDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 油电控制
 * <p>
 * Created by MVPArmsTemplate on 10/31/2020 15:37
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class OilElectricityControlActivity extends BaseActivity<OilElectricityControlPresenter> implements OilElectricityControlContract.View {


    @BindView(R.id.oil_psd_set)
    LinearLayout oilPsdSet;
    @BindView(R.id.oil_regain)
    LinearLayout oilRegain;
    @BindView(R.id.oil_cut)
    LinearLayout oilCut;
    @BindView(R.id.tv_name)
    TextView tvName;

    private boolean isOilEleSwitch = false; // true：恢复油电，false：断开油电
    private String mSimei;

    public static Intent newInstance() {
        return new Intent(MyApplication.getMyApp(), OilElectricityControlActivity.class);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerOilElectricityControlComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_oil_electricity_control; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_function_oil_and_electricity_control));
        mSimei = MyApplication.getMyApp().getSimei();
        if (!TextUtils.isEmpty(MyApplication.getMyApp().getCarName())){
            tvName.setText(getString(R.string.txt_target_name) + MyApplication.getMyApp().getCarName());
        }else{
            tvName.setText(getString(R.string.txt_target_name) + MyApplication.getMyApp().getImei());
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @OnClick({R.id.oil_psd_set, R.id.oil_regain, R.id.oil_cut})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.oil_psd_set:
                onPasswordSet();
                break;
            case R.id.oil_regain:
                onOilElectricOpen();
                break;
            case R.id.oil_cut:
                onOilElectricClose();
                break;
        }
    }

    /**
     * 密码设置
     */
    private void onPasswordSet() {
        AlertCurrentBean bean = new AlertCurrentBean();
        bean.setTitle(getString(R.string.txt_password_set));
        bean.setTitleSmall(getString(R.string.txt_oil_password_set_hint));
        bean.setHint(getString(R.string.txt_password_hint));
        bean.setTextPassword(true);
        AlarmCurrentEditDialog dialog = new AlarmCurrentEditDialog();
        dialog.setDialogDismiss(false);
        dialog.show(getSupportFragmentManager(), bean, new AlarmCurrentEditDialog.onAlartCurrentChange() {
            @Override
            public void onEditConfirm(String textValue) {
                dialog.dismiss();
                submitOilModifyPassWord(textValue);

            }

            @Override
            public void onEditCancel() {

            }
        });
    }

    private void submitOilModifyPassWord(String text) {
        ModifyPasswordPutBean.ParamsBean paramsBean = new ModifyPasswordPutBean.ParamsBean();
        paramsBean.setRelay_pwd(text);
        ModifyPasswordPutBean bean = new ModifyPasswordPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Set_Account);
        bean.setModule(ModuleValueService.Module_For_Set_Account);
        if (getPresenter() != null) {
            getPresenter().submitModifyPassword(bean);
        }
    }


    /**
     * 恢复油电
     */
    private void onOilElectricOpen() {
        AlertCurrentBean bean = new AlertCurrentBean();
        bean.setTitle(getString(R.string.txt_oil_electric_open));
        bean.setTitleSmall(getString(R.string.txt_input_login_password));
        bean.setHint(getString(R.string.txt_input_login_password));
        bean.setTextPassword(true);
        AlarmCurrentEditDialog dialog = new AlarmCurrentEditDialog();
        dialog.show(getSupportFragmentManager(), bean, new AlarmCurrentEditDialog.onAlartCurrentChange() {
            @Override
            public void onEditConfirm(String textValue) {
                isOilEleSwitch = true;
                submitOilElectricControl(true, textValue);
            }

            @Override
            public void onEditCancel() {

            }
        });
    }

    /**
     * 断开油电
     */
    private void onOilElectricClose() {
        AlertCurrentBean bean = new AlertCurrentBean();
        bean.setTitle(getString(R.string.txt_oil_electric_close));
        bean.setTitleSmall(getString(R.string.txt_input_login_password));
        bean.setHint(getString(R.string.txt_input_login_password));
        bean.setTextPassword(true);
        AlarmCurrentEditDialog dialog = new AlarmCurrentEditDialog();
        dialog.show(getSupportFragmentManager(), bean, new AlarmCurrentEditDialog.onAlartCurrentChange() {
            @Override
            public void onEditConfirm(String textValue) {
                isOilEleSwitch = false;
                submitOilElectricControl(false, textValue);
            }

            @Override
            public void onEditCancel() {

            }
        });
    }


    /**
     * 提交油电控制开关设置
     *
     * @param switchState 0：关闭，1：开启
     * @param password
     */
    private void submitOilElectricControl(boolean switchState, String password) {

        OilSetPutBean bean = new OilSetPutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Oil_Name);
        bean.setModule(ModuleValueService.Module_For_Oil_Name);

        OilSetPutBean.ParamsBean param = new OilSetPutBean.ParamsBean();
        param.setReplay_switch(switchState);
        param.setPwd_md5(MD5Utils.getMD5Encryption(password));
        param.setSimei(mSimei);
        bean.setParams(param);
        if (getPresenter() != null)
            getPresenter().submitOilElectricControl(bean);
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void submitOilElectricControlSuccess(BaseBean baseBean) {
        if (baseBean.isSuccess()) {
            if (isOilEleSwitch) {
                ToastUtils.show(getString(R.string.txt_reset_success));
            } else {
                ToastUtils.show(getString(R.string.txt_shutdown_success));
            }
        }

    }

    @Override
    public void submitModifyPasswordSuccess(BaseBean baseBean) {
        if (baseBean.isSuccess()) {
            ToastUtils.show(getString(R.string.txt_modify_success));
        }
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
}
