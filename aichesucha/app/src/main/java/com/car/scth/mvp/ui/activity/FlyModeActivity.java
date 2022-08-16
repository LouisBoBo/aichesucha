package com.car.scth.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.car.scth.R;
import com.car.scth.app.MyApplication;
import com.car.scth.di.component.DaggerFlyModeComponent;
import com.car.scth.mvp.contract.FlyModeContract;
import com.car.scth.mvp.model.api.ModuleValueService;
import com.car.scth.mvp.model.bean.AlertBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.DeviceModeSetPutBean;
import com.car.scth.mvp.presenter.FlyModePresenter;
import com.car.scth.mvp.utils.ResultDataUtils;
import com.car.scth.mvp.utils.ToastUtils;
import com.car.scth.mvp.weiget.AlertAppDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 飞行模式设置
 * <p>
 * Created by MVPArmsTemplate on 12/12/2020 08:55
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class FlyModeActivity extends BaseActivity<FlyModePresenter> implements FlyModeContract.View {

    @BindView(R.id.edt_min)
    EditText edtMin; // 间隔时间
    @BindView(R.id.iv_switch)
    ImageView ivSwitch; // 飞行模式开关
    @BindView(R.id.btn_set)
    Button btnSet; // 设置

    private int modeType = 0; // 值,例如点名模式 定位间隔 飞行模式开关 0-关闭 1-打开
    private String modeValue = ""; // 待机模式设置的时间 格式 HH:MM 飞行模式定位间隔
    private String mSimei;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFlyModeComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_fly_mode;//setContentView(id);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_location_mode_3));
        mSimei = MyApplication.getMyApp().getSimei();

        if (getIntent() != null){
            modeType = getIntent().getIntExtra("mode_type", 0);
            modeValue = getIntent().getStringExtra("mode_value");
        }
        if (!TextUtils.isEmpty(modeValue)){
            edtMin.setText(modeValue);
            edtMin.setSelection(edtMin.getText().toString().length());
        }
        ivSwitch.setImageResource(modeType == 1 ? R.mipmap.icon_on_message : R.mipmap.icon_off_message);
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

    @OnClick({R.id.iv_switch, R.id.btn_set})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_switch:
                setFlySwitch();
                break;
            case R.id.btn_set:
                onFlyModeConfirm();
                break;
        }
    }

    /**
     * 飞行模式开关
     */
    private void setFlySwitch(){
        if (modeType == 0){
            modeType = 1;
        }else{
            modeType = 0;
        }
        ivSwitch.setImageResource(modeType == 1 ? R.mipmap.icon_on_message : R.mipmap.icon_off_message);
    }

    /**
     * 提交飞行模式
     */
    private void onFlyModeConfirm(){
        modeValue = edtMin.getText().toString().trim();
        if (TextUtils.isEmpty(modeValue)){
            ToastUtils.show(getString(R.string.txt_please_enter_the_positioning_interval));
            return;
        }
        int value = Integer.parseInt(modeValue);
        if (value < 10){
            ToastUtils.show(getString(R.string.txt_the_positioning_interval_is_not_less_than_10_minutes));
            return;
        }
        if (value > 1440){
            ToastUtils.show(getString(R.string.txt_the_positioning_interval_is_not_less_than_1440_minutes));
            return;
        }
        if (modeType == 1){
            submitFlyMode();

//            AlertBean bean = new AlertBean();
//            bean.setTitle(getString(R.string.txt_tip));
//            bean.setAlert(getString(R.string.txt_open_fly_mode_hint));
//            bean.setType(1);
//            AlertAppDialog dialog = new AlertAppDialog();
//            dialog.show(getSupportFragmentManager(), bean, new AlertAppDialog.onAlertDialogChange() {
//                @Override
//                public void onConfirm() {
//                    submitFlyMode();
//                }
//
//                @Override
//                public void onCancel() {
//                    modeType = 0;
//                    ivSwitch.setImageResource(R.drawable.icon_switch_off);
//                    submitFlyMode();
//                }
//            });
        }else{
            if (value >= 30){
                AlertBean bean = new AlertBean();
                bean.setTitle(getString(R.string.txt_tip));
                bean.setAlert(getString(R.string.txt_open_fly_mode_hint));
                bean.setType(1);
                AlertAppDialog dialog = new AlertAppDialog();
                dialog.show(getSupportFragmentManager(), bean, new AlertAppDialog.onAlertDialogChange() {
                    @Override
                    public void onConfirm() {
                        modeType = 1;
                        ivSwitch.setImageResource(R.mipmap.icon_on_message);
                        submitFlyMode();
                    }

                    @Override
                    public void onCancel() {
                        submitFlyMode();
                    }
                });
            }else{
                submitFlyMode();
            }
        }
    }

    /**
     * 设置定位模式
     */
    private void submitFlyMode(){
        DeviceModeSetPutBean.ParamsBean paramsBean = new DeviceModeSetPutBean.ParamsBean();
        paramsBean.setSimei(mSimei);
        paramsBean.setMode(ResultDataUtils.Device_Mode_Fly);
        paramsBean.setMode_type(modeType);
        paramsBean.setSmode_value(modeValue);
        DeviceModeSetPutBean bean = new DeviceModeSetPutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Location_Mode_Set);
        bean.setModule(ModuleValueService.Module_For_Location_Mode_Set);
        bean.setParams(paramsBean);

        if (getPresenter() != null){
            getPresenter().submitLocationMode(bean);
        }
    }

    @Override
    public void submitLocationModeSuccess(BaseBean baseBean) {
        ToastUtils.show(getString(R.string.txt_set_success));
        setResult(Activity.RESULT_OK);
        finish();
    }
}
