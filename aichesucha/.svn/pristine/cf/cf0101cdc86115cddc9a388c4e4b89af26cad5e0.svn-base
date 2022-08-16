package com.car.scth.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.car.scth.R;
import com.car.scth.app.MyApplication;
import com.car.scth.di.component.DaggerLoopModeAddComponent;
import com.car.scth.mvp.contract.LoopModeAddContract;
import com.car.scth.mvp.model.api.ModuleValueService;
import com.car.scth.mvp.model.bean.LoopDayBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.LoopModeAddPutBean;
import com.car.scth.mvp.model.putbean.LoopModeModifyInfoPutBean;
import com.car.scth.mvp.model.putbean.LoopModeModifyPutBean;
import com.car.scth.mvp.presenter.LoopModeAddPresenter;
import com.car.scth.mvp.utils.DateUtils;
import com.car.scth.mvp.utils.ResultDataUtils;
import com.car.scth.mvp.utils.ToastUtils;
import com.car.scth.mvp.weiget.LoopDayDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 添加周期定位，修改周期定位
 * <p>
 * Created by MVPArmsTemplate on 12/12/2020 15:29
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class LoopModeAddActivity extends BaseActivity<LoopModeAddPresenter> implements LoopModeAddContract.View {

    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.time_picker)
    TimePicker timePicker;
    @BindView(R.id.tv_day)
    TextView tvDay;
    @BindView(R.id.edt_name)
    EditText edtName;

    private LoopModeModifyInfoPutBean mBean; // 为null是新增，不为null为修改
    private String tmpTime; // 周期定位时间
    private List<Integer> mLoopDatas; // 周期定位星期
    private String mSimei;
    private List<LoopDayBean> loopDayBeans;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoopModeAddComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_loop_mode_add;//setContentView(id);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbarRight.setVisibility(View.VISIBLE);
        toolbarRight.setText(getString(R.string.txt_save));
        mLoopDatas = new ArrayList<>();
        loopDayBeans = new ArrayList<>();
        mSimei = MyApplication.getMyApp().getSimei();

        loopDayBeans.add(new LoopDayBean(0, getString(R.string.txt_sunday), false));
        loopDayBeans.add(new LoopDayBean(1, getString(R.string.txt_monday), false));
        loopDayBeans.add(new LoopDayBean(2, getString(R.string.txt_tuesday), false));
        loopDayBeans.add(new LoopDayBean(3, getString(R.string.txt_wednesday), false));
        loopDayBeans.add(new LoopDayBean(4, getString(R.string.txt_thursday), false));
        loopDayBeans.add(new LoopDayBean(5, getString(R.string.txt_friday), false));
        loopDayBeans.add(new LoopDayBean(6, getString(R.string.txt_saturday), false));

        if (getIntent() != null){
            mBean = (LoopModeModifyInfoPutBean) getIntent().getSerializableExtra("bean");
        }

        timePicker.setIs24HourView(true);
        timePicker.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);
        if (mBean != null){
            setTitle(getString(R.string.txt_loop_location_mode_modify));
            mLoopDatas.addAll(mBean.getRp_loop_loc());
            edtName.setText(mBean.getLoop_loc_name());
            if (!TextUtils.isEmpty(mBean.getLoc_value())){
                String[] time = mBean.getLoc_value().split(":");
                timePicker.setCurrentHour(Integer.valueOf(time[0]));
                timePicker.setCurrentMinute(Integer.valueOf(time[1]));
            }
            List<String> dayDatas = new ArrayList<>();
            if (mBean.getRp_loop_loc().size() == 7){
                dayDatas.add(getString(R.string.txt_every_day));
            }else{
                for (int i = 0; i < mBean.getRp_loop_loc().size(); i++){
                    switch (mBean.getRp_loop_loc().get(i)){
                        case 0:
                            loopDayBeans.get(0).setSelect(true);
                            dayDatas.add(getString(R.string.txt_sunday));
                            break;
                        case 1:
                            loopDayBeans.get(1).setSelect(true);
                            dayDatas.add(getString(R.string.txt_monday));
                            break;
                        case 2:
                            loopDayBeans.get(2).setSelect(true);
                            dayDatas.add(getString(R.string.txt_tuesday));
                            break;
                        case 3:
                            loopDayBeans.get(3).setSelect(true);
                            dayDatas.add(getString(R.string.txt_wednesday));
                            break;
                        case 4:
                            loopDayBeans.get(4).setSelect(true);
                            dayDatas.add(getString(R.string.txt_thursday));
                            break;
                        case 5:
                            loopDayBeans.get(5).setSelect(true);
                            dayDatas.add(getString(R.string.txt_friday));
                            break;
                        case 6:
                            loopDayBeans.get(6).setSelect(true);
                            dayDatas.add(getString(R.string.txt_saturday));
                            break;
                    }
                }
            }
            tvDay.setText(dayDatas.toString().replace("[", "").replace("]", ""));
        }else{
            setTitle(getString(R.string.txt_loop_location_mode));
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

    @OnClick({R.id.toolbar_right, R.id.tv_day})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_right: // 保存
                if (mBean != null){
                    onLoopModeModify();
                }else{
                    onLoopModeAdd();
                }
                break;
            case R.id.tv_day: // 选择重复日期
                onLoopDaySelect();
                break;
        }
    }

    private void onLoopDaySelect(){
        LoopDayDialog dialog = new LoopDayDialog();
        dialog.show(getSupportFragmentManager(), loopDayBeans, new LoopDayDialog.onLoopDayChange() {
            @Override
            public void onLoopDaySelect(List<Integer> days, String strDays) {
                mLoopDatas.clear();
                mLoopDatas.addAll(days);
                tvDay.setText(strDays);
                for (LoopDayBean bean : loopDayBeans){
                    bean.setSelect(false);
                }
                for (int i = 0; i < mLoopDatas.size(); i++){
                    for (LoopDayBean bean : loopDayBeans){
                        if (bean.getDay() == mLoopDatas.get(i)){
                            bean.setSelect(true);
                            break;
                        }
                    }
                }
            }
        });
    }

    /**
     * 修改周期定位
     */
    @SuppressLint("DefaultLocale")
    private void onLoopModeModify(){
        String name = edtName.getText().toString().trim();
        if (mLoopDatas.size() == 0){
            ToastUtils.show(getString(R.string.txt_repeat_select_hint));
            return;
        }
        tmpTime = String.format("%02d:%02d", timePicker.getCurrentHour(), timePicker.getCurrentMinute());
        tmpTime = DateUtils.GetOthersStrFromENGLISHStr(tmpTime, "HH:mm", Locale.getDefault());

        LoopModeModifyPutBean.ParamsBean.ItemBean itemBean = new LoopModeModifyPutBean.ParamsBean.ItemBean();
        itemBean.setLoc_switch(mBean.isLoc_switch());
        itemBean.setLoc_value(tmpTime);
        itemBean.setLoop_loc_name(name);
        itemBean.setRp_loop_loc(mLoopDatas);

        LoopModeModifyPutBean.ParamsBean paramsBean = new LoopModeModifyPutBean.ParamsBean();
        paramsBean.setItem(itemBean);
        paramsBean.setLid(mBean.getLid());

        LoopModeModifyPutBean bean = new LoopModeModifyPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Loop_Location_Mode_Modify);
        bean.setModule(ModuleValueService.Module_For_Loop_Location_Mode_Modify);

        if (getPresenter() != null){
            getPresenter().submitLoopModify(bean);
        }
    }

    /**
     * 添加周期定位
     */
    @SuppressLint("DefaultLocale")
    private void onLoopModeAdd(){
        String name = edtName.getText().toString().trim();
        if (mLoopDatas.size() == 0){
            ToastUtils.show(getString(R.string.txt_repeat_select_hint));
            return;
        }
        tmpTime = String.format("%02d:%02d", timePicker.getCurrentHour(), timePicker.getCurrentMinute());
        tmpTime = DateUtils.GetOthersStrFromENGLISHStr(tmpTime, "HH:mm", Locale.getDefault());

        LoopModeAddPutBean.ParamsBean.ItemBean itemBean = new LoopModeAddPutBean.ParamsBean.ItemBean();
        itemBean.setLoc_switch(true);
        itemBean.setLoc_value(tmpTime);
        itemBean.setLoop_loc_name(name);
        itemBean.setRp_loop_loc(mLoopDatas);

        LoopModeAddPutBean.ParamsBean paramsBean = new LoopModeAddPutBean.ParamsBean();
        paramsBean.setItem(itemBean);
        paramsBean.setSimei(mSimei);

        LoopModeAddPutBean bean = new LoopModeAddPutBean();
        bean.setParams(paramsBean);
        bean.setFunc(ModuleValueService.Fuc_For_Loop_Location_Mode_Add);
        bean.setModule(ModuleValueService.Module_For_Loop_Location_Mode_Add);

        if (getPresenter() != null){
            getPresenter().submitLoopModeAdd(bean);
        }
    }

    @Override
    public void submitLoopModeSuccess(BaseBean baseBean) {
        ToastUtils.show(getString(R.string.txt_save_success));
        setResult(Activity.RESULT_OK);
        finish();
    }
}