package com.car.scth.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.car.scth.R;
import com.car.scth.app.MyApplication;
import com.car.scth.di.component.DaggerLaunchComponent;
import com.car.scth.mvp.contract.LaunchContract;
import com.car.scth.mvp.presenter.LaunchPresenter;
import com.car.scth.mvp.utils.ConstantValue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/17/2020 17:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class LaunchActivity extends BaseActivity<LaunchPresenter> implements LaunchContract.View {

    private int time = 2; // 启动页倒计时
    private String mSid;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLaunchComponent.builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_launch;//setContentView(id);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        setStatusBarSetting(true);

        mSid = SPUtils.getInstance().getString(ConstantValue.USER_SID, "");
        MyApplication.getMyApp().clearData();
        SPUtils.getInstance().put(ConstantValue.Is_Get_Update_App, false);
        SPUtils.getInstance().put(ConstantValue.Is_Request_Authority, false);

        handler.sendEmptyMessage(1);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            time--;
            if (time <= 0) {
                onLaunchIntent();
            } else {
                handler.sendEmptyMessageDelayed(1, 1000);
            }
        }
    };

    /**
     * 根据对应的值跳转不同的页面
     */
    private void onLaunchIntent() {
        if (!TextUtils.isEmpty(mSid)){
            launchActivity(MainActivity.newInstance());
        }else{
            launchActivity(LoginActivity.newInstance(1));
        }
        finish();
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
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        handler = null;
        super.onDestroy();
    }
}
