package com.car.scth.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.car.scth.R;
import com.car.scth.app.MyApplication;
import com.car.scth.di.component.DaggerRealTimeModeComponent;
import com.car.scth.mvp.contract.RealTimeModeContract;
import com.car.scth.mvp.model.api.ModuleValueService;
import com.car.scth.mvp.model.bean.RealTimeModeResultBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.RealTimeModePutBean;
import com.car.scth.mvp.model.putbean.RealTimeModeSetPutBean;
import com.car.scth.mvp.presenter.RealTimeModePresenter;
import com.car.scth.mvp.utils.FunctionType;
import com.car.scth.mvp.utils.ResultDataUtils;
import com.car.scth.mvp.utils.ToastUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/14/2020 17:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class RealTimeModeActivity extends BaseActivity<RealTimeModePresenter> implements RealTimeModeContract.View {

    @BindView(R.id.iv_timing)
    ImageView ivTiming; // 定时
    @BindView(R.id.iv_fixed_distance)
    ImageView ivFixedDistance; // 定距
    @BindView(R.id.ll_distance)
    LinearLayout rlFixedDistance; // 定距
    @BindView(R.id.ll_time)
    LinearLayout rlTiming; // 定时
    @BindView(R.id.edt_value)
    EditText edtValue; // 值

    private String mSimei; // 加密的imei号
    private String realTimeMode; // 定位模式
    private String devType; // 设备型号

    public static Intent newInstance(){
        return new Intent(MyApplication.getMyApp(), RealTimeModeActivity.class);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRealTimeModeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_real_time_location; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_real_time_location));
        mSimei = MyApplication.getMyApp().getSimei();
        devType = MyApplication.getMyApp().getVersion();

        getRealTimeMode();
    }

    /**
     * 获取实时定位模式数据
     */
    public void getRealTimeMode(){
        RealTimeModePutBean.ParamsBean paramsBean = new RealTimeModePutBean.ParamsBean();
        paramsBean.setSimei(mSimei);

        RealTimeModePutBean bean = new RealTimeModePutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Real_Time_Mode_Get);
        bean.setModule(ModuleValueService.Module_For_Real_Time_Mode_Get);
        bean.setParams(paramsBean);

        if (getPresenter() != null){
            getPresenter().getRealTimeMode(bean);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_time, R.id.ll_distance, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_time: // 定时
                edtValue.setText("");
                onRealTimeModeShow(ResultDataUtils.Device_Mode_Real_Time_Time);
                break;
            case R.id.ll_distance: // 定距
                edtValue.setText("");
                onRealTimeModeShow(ResultDataUtils.Device_Mode_Real_Time_Distance);
                break;
            case R.id.btn_confirm: // 保存
                submitRealTimeMode();
                break;
        }
    }

    /**
     * 提交实时定位
     */
    private void submitRealTimeMode(){
        String value = edtValue.getText().toString();
        if (TextUtils.isEmpty(value)){
            if (realTimeMode.equals(ResultDataUtils.Device_Mode_Real_Time_Time)){
                ToastUtils.show(getString(R.string.txt_input_10_120));
            }else{
                ToastUtils.show(getString(R.string.txt_input_200_1000));
            }
            return;
        }

        int rtls_value = Integer.parseInt(value);
        if (realTimeMode.equals(ResultDataUtils.Device_Mode_Real_Time_Time)){
            if (rtls_value < 10 || rtls_value > 120){
                ToastUtils.show(getString(R.string.txt_input_10_120));
                return;
            }
        }else{
            if (rtls_value < 200 || rtls_value > 1000){
                ToastUtils.show(getString(R.string.txt_input_200_1000));
                return;
            }
        }

        RealTimeModeSetPutBean.ParamsBean paramsBean = new RealTimeModeSetPutBean.ParamsBean();
        paramsBean.setRtls_mode(realTimeMode);
        paramsBean.setRtls_value(rtls_value);
        paramsBean.setSimei(mSimei);

        RealTimeModeSetPutBean bean = new RealTimeModeSetPutBean();
        bean.setFunc(ModuleValueService.Fuc_For_Real_Time_Mode_Set);
        bean.setModule(ModuleValueService.Module_For_Real_Time_Mode_Set);
        bean.setParams(paramsBean);

        if (getPresenter() != null){
            getPresenter().submitRealTimeMode(bean);
        }
    }

    /**
     * 定位模式显示
     * @param mode 定位模式
     */
    private void onRealTimeModeShow(String mode){
        realTimeMode = mode;
        ivTiming.setImageResource(R.mipmap.icon_unselected_small);
        ivFixedDistance.setImageResource(R.mipmap.icon_unselected_small);
        if (mode.equals(ResultDataUtils.Device_Mode_Real_Time_Time)){
            ivTiming.setImageResource(R.mipmap.icon_save_password_select);
            edtValue.setHint(getString(R.string.txt_timing_hint));
        }else{
            ivFixedDistance.setImageResource(R.mipmap.icon_save_password_select);
            edtValue.setHint(getString(R.string.txt_fixed_distance_hint));
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getRealTimeModeSuccess(RealTimeModeResultBean realTimeModeResultBean) {
        realTimeMode = realTimeModeResultBean.getRtls_mode();
        if (realTimeModeResultBean.getRtls_value() == 0) {
            edtValue.setText("30");
        } else {
            edtValue.setText(realTimeModeResultBean.getRtls_value() + "");
        }
        if (realTimeModeResultBean.getIs_locspace() == 0) {
            rlFixedDistance.setVisibility(View.GONE);
        } else if (realTimeModeResultBean.getIs_realtimeposition() == 0) {
            rlTiming.setVisibility(View.GONE);
        }

        onRealTimeModeShow(realTimeMode);
    }

    @Override
    public void submitRealTimeModeSuccess(BaseBean baseBean) {
        ToastUtils.show(getString(R.string.txt_successful_operation));
    }
}
