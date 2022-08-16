package com.car.scth.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.car.scth.di.component.DaggerAlarmScreenComponent;
import com.car.scth.mvp.contract.AlarmScreenContract;
import com.car.scth.mvp.model.bean.AlarmScreenBean;
import com.car.scth.mvp.presenter.AlarmScreenPresenter;

import com.car.scth.R;
import com.car.scth.mvp.ui.adapter.AlarmScreenAdapter;
import com.car.scth.mvp.utils.ToastUtils;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/14/2021 14:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class AlarmScreenActivity extends BaseActivity<AlarmScreenPresenter> implements AlarmScreenContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.btn_select_all)
    Button btnSelectAll; // 全选
    @BindView(R.id.btn_confirm)
    Button btnConfirm; // 确定

    private List<AlarmScreenBean> screenBeans;
    private AlarmScreenAdapter mAdapter;
    private boolean isAllSelect = false; // 是否全选

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAlarmScreenComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_alarm_screen;//setContentView(id);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(R.string.txt_screen_type_select);
        screenBeans = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addAlarmScreenData();

    }

    /**
     * 添加报警类型数据
     */
    private void addAlarmScreenData() {
        screenBeans.add(new AlarmScreenBean(getString(R.string.txt_alarm_shake), false, "e_alarm_shake"));
        screenBeans.add(new AlarmScreenBean(getString(R.string.txt_alarm_speed), false, "e_alarm_speed"));
        screenBeans.add(new AlarmScreenBean(getString(R.string.txt_alarm_speeding_end), false, "e_alarm_speeding_end"));
        screenBeans.add(new AlarmScreenBean(getString(R.string.txt_alarm_off_line), false, "e_alarm_off_line"));
        screenBeans.add(new AlarmScreenBean(getString(R.string.txt_shutdown_alarm), false, "e_alarm_close_down"));
        screenBeans.add(new AlarmScreenBean(getString(R.string.txt_reboot_alarm), false, "e_alarm_start_dev"));
        screenBeans.add(new AlarmScreenBean(getString(R.string.txt_fence_alarm_out), false, "e_alarm_out_fence"));
        screenBeans.add(new AlarmScreenBean(getString(R.string.txt_fence_alarm_in), false, "e_alarm_in_fence"));
        screenBeans.add(new AlarmScreenBean(getString(R.string.txt_low_power_alarm), false, "e_alarm_lowpower"));
        screenBeans.add(new AlarmScreenBean(getString(R.string.txt_remove_low_power_alarm), false, "e_alarm_remove_lowpower"));
        // 设备脱落报警和防拆报警合并，为了防止后续需求增加，以;号分割，不以,号分割
        screenBeans.add(new AlarmScreenBean(getString(R.string.txt_alarm_light_off), false, "e_alarm_light_off;e_alarm_dismantle"));
        screenBeans.add(new AlarmScreenBean(getString(R.string.txt_device_alarm), false, "e_alarm_recovery_light;e_alarm_remove_out"));
        screenBeans.add(new AlarmScreenBean(getString(R.string.txt_sos_alarm), false, "e_alarm_sos"));

        mAdapter = new AlarmScreenAdapter(R.layout.item_alarm_screen, screenBeans);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                boolean isSelect = !screenBeans.get(position).isSelect();
                screenBeans.get(position).setSelect(isSelect);
                mAdapter.notifyDataSetChanged();
                onSelectNumber();
            }
        });
    }

    /**
     * 报警数量
     */
    @SuppressLint("SetTextI18n")
    private void onSelectNumber() {
        // 选择的数量
        int selectNumber = 0;
        for (AlarmScreenBean bean : screenBeans) {
            if (bean.isSelect()) {
                selectNumber++;
            }
        }
        if (selectNumber > 0) {
            btnConfirm.setText(getString(R.string.txt_confirm) + "(" + selectNumber + ")");
        } else {
            btnConfirm.setText(getString(R.string.txt_confirm));
        }
        if (selectNumber == screenBeans.size()) {
            btnSelectAll.setText(getString(R.string.txt_select_all_cancel));
        } else {
            btnSelectAll.setText(getString(R.string.txt_select_all));
        }
    }

    @OnClick({R.id.btn_select_all, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_select_all:
                onSelectAll();
                break;
            case R.id.btn_confirm:
                onConfirm();
                break;
        }
    }

    /**
     * 全选
     */
    private void onSelectAll() {
        isAllSelect = !isAllSelect;
        for (AlarmScreenBean bean : screenBeans) {
            bean.setSelect(isAllSelect);
        }
        mAdapter.notifyDataSetChanged();
        onSelectNumber();
    }

    /**
     * 提交
     */
    private void onConfirm() {
        String type = "";
        for (AlarmScreenBean bean : screenBeans) {
            if (bean.isSelect()) {
                if (TextUtils.isEmpty(type)) {
                    type = type + bean.getAlertType();
                } else {
                    type = type + "," + bean.getAlertType();
                }
            }
        }
        if (TextUtils.isEmpty(type)) {
            ToastUtils.show(getString(R.string.txt_select_alarm_type_hint));
            return;
        } else {
            Intent intent = new Intent();
            intent.putExtra("type", type);
            setResult(Activity.RESULT_OK, intent);
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
