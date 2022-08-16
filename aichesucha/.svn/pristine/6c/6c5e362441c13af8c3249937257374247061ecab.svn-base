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
import com.car.scth.mvp.contract.RemoteSwitchContract;
import com.car.scth.mvp.model.bean.RemoteSwitchResultBean;
import com.car.scth.mvp.model.bean.TimeSwitchGetResultBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.RemoteSwitchSetPutBean;
import com.car.scth.mvp.model.putbean.TimeSwitchDeletePutBean;
import com.car.scth.mvp.model.putbean.TimeSwitchGetPutBean;
import com.car.scth.mvp.model.putbean.TimeSwitchSetPutBean;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/31/2020 17:40
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class RemoteSwitchPresenter extends BasePresenter<RemoteSwitchContract.Model, RemoteSwitchContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public RemoteSwitchPresenter(RemoteSwitchContract.Model model, RemoteSwitchContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 远程开关机设置
     * @param bean
     */
    public void submitRemoteSwitch(RemoteSwitchSetPutBean bean){
        mModel.submitRemoteSwitch(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<RemoteSwitchResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(RemoteSwitchResultBean remoteSwitchResultBean) {
                        if (remoteSwitchResultBean.isSuccess()){
                            mRootView.submitRemoteSwitchSuccess(remoteSwitchResultBean);
                        }
                    }
                });
    }

    /**
     * 获取定时开关机数据
     * @param bean
     */
    public void getTimeSwitch(TimeSwitchGetPutBean bean){
        mModel.getTimeSwitch(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<TimeSwitchGetResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(TimeSwitchGetResultBean timeSwitchGetResultBean) {
                        if (timeSwitchGetResultBean.isSuccess()){
                            mRootView.getTimeSwitchSuccess(timeSwitchGetResultBean);
                        }
                    }
                });
    }

    /**
     * 设置定时开关机
     * @param bean
     */
    public void submitTimeSwitch(TimeSwitchSetPutBean bean){
        mModel.submitTimeSwitch(bean)
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
                            mRootView.submitTimeSwitchSuccess(baseBean);
                        }
                    }
                });
    }

    /**
     * 删除定时开关机
     * @param bean
     */
    public void submitTimeSwitchDelete(TimeSwitchDeletePutBean bean){
        mModel.submitTimeSwitchDelete(bean)
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
                            mRootView.submitTimeSwitchDeleteSuccess(baseBean);
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
