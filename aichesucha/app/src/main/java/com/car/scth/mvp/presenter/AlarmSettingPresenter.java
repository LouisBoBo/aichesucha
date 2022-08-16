package com.car.scth.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.jess.arms.utils.RxLifecycleUtils;
import com.car.scth.mvp.contract.AlarmSettingContract;
import com.car.scth.mvp.model.bean.DeviceConfigResultBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.DeviceConfigPutBean;
import com.car.scth.mvp.model.putbean.DeviceConfigSetPutBean;
import com.car.scth.mvp.model.putbean.ModifyPasswordPutBean;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/02/2020 16:07
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class AlarmSettingPresenter extends BasePresenter<AlarmSettingContract.Model, AlarmSettingContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public AlarmSettingPresenter (AlarmSettingContract.Model model, AlarmSettingContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 获取设备的配置信息，支持的功能等
     * @param bean
     */
    public void getDeviceConfig(DeviceConfigPutBean bean){
        mModel.getDeviceConfig(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<DeviceConfigResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(DeviceConfigResultBean deviceConfigResultBean) {
                        if (deviceConfigResultBean.isSuccess()){
                            mRootView.getDeviceConfigSuccess(deviceConfigResultBean);
                        }
                    }
                });
    }

    /**
     * 设置设备的配置信息
     * @param bean
     */
    public void setDeviceConfig(DeviceConfigSetPutBean bean){
        mModel.setDeviceConfig(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean.isSuccess()){
                            mRootView.setDeviceConfigSuccess(baseBean);
                        }
                    }
                });
    }

    /**
     * 设置推送开关
     * @param bean
     * @param isSetPushSwitch 是否是设置消息推送开关
     */
    public void submitPushSwitch(ModifyPasswordPutBean bean, boolean isSetPushSwitch){
        mModel.submitPushSwitch(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean.isSuccess()){
                            if (isSetPushSwitch){
                                mRootView.submitPushSwitchSuccess(bean.getParams().getPush_switch(), isSetPushSwitch);
                            }else{
                                mRootView.submitPushSwitchSuccess(false, isSetPushSwitch);
                            }
                        }
                    }
                });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
