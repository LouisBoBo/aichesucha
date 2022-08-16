package com.car.scth.mvp.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.car.scth.R;
import com.car.scth.app.MyApplication;
import com.car.scth.di.component.DaggerRemoteListeningComponent;
import com.car.scth.mvp.contract.RemoteListeningContract;
import com.car.scth.mvp.presenter.RemoteListeningPresenter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 远程听音
 * <p>
 * Created by MVPArmsTemplate on 10/31/2020 17:24
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class RemoteListeningActivity extends BaseActivity<RemoteListeningPresenter> implements RemoteListeningContract.View {

    @BindView(R.id.toolbar_iv_right)
    ImageView toolbarIvRight;
    @BindView(R.id.edt_device_number)
    EditText edtDeviceNumber;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;

    private String remoteMobile; // 远程听音号码
    private long mImei;

    public static Intent newInstance() {
        return new Intent(MyApplication.getMyApp(), RemoteListeningActivity.class);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRemoteListeningComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_remote_listening; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.txt_function_remote_listening));
        toolbarIvRight.setVisibility(View.VISIBLE);
        toolbarIvRight.setImageResource(R.mipmap.icon_add_fence);
        mImei = MyApplication.getMyApp().getImei();

        remoteMobile = SPUtils.getInstance().getString("monitor_" + mImei, "");
        if (!TextUtils.isEmpty(remoteMobile)){
            edtDeviceNumber.setText(remoteMobile);
        }
        edtDeviceNumber.setCursorVisible(false);
        edtDeviceNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtDeviceNumber.setCursorVisible(true);
            }
        });
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

    @OnClick({R.id.toolbar_iv_right, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_iv_right:
                launchActivity(ListeningListActivity.newInstance());
                break;
            case R.id.btn_confirm:
                onConfirmMobile();
                break;
        }
    }

    /**
     * 拨打监听设备的号码
     */
    private void onConfirmMobile() {
        String mobile = edtDeviceNumber.getText().toString().trim();
        if (!TextUtils.isEmpty(mobile)) {
            SPUtils.getInstance().put("monitor_" + mImei, mobile);
            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri data = Uri.parse("tel:" + mobile);
            intent.setData(data);
            startActivity(intent);
        } else {
            ToastUtils.showShort(getString(R.string.txt_listen_in_mobile_hint));
        }

    }

}
