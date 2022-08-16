package com.car.scth.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.car.scth.mvp.contract.MineContract;
import com.car.scth.mvp.model.bean.CheckAppUpdateBean;
import com.car.scth.mvp.model.bean.LogoutAccountResultBean;
import com.car.scth.mvp.model.bean.UnbindPhoneResultBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.CheckAppUpdatePutBean;
import com.car.scth.mvp.model.putbean.LogoutAccountPutBean;
import com.car.scth.mvp.model.putbean.SignOutPutBean;
import com.car.scth.mvp.model.putbean.UnbindPhonePutBean;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/22/2020 16:55
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class MinePresenter extends BasePresenter<MineContract.Model, MineContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public MinePresenter(MineContract.Model model, MineContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 退出登录
     * @param bean
     */
    public void submitSignOut(SignOutPutBean bean){
        mModel.submitSignOut(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
//                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
//                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        mRootView.onDismissProgress();
                        if (baseBean.isSuccess()){
                            mRootView.submitSignOutSuccess(baseBean);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onDismissProgress();
                    }
                });
    }

    /**
     * 获取app版本更新信息
     */
    public void getAppUpdate(CheckAppUpdatePutBean bean){
        mModel.getAppUpdate(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
//                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
//                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<CheckAppUpdateBean>(mErrorHandler) {
                    @Override
                    public void onNext(CheckAppUpdateBean checkAppUpdateBean) {
                        mRootView.onDismissProgress();
                        if (checkAppUpdateBean.isSuccess()){
                            mRootView.getAppUpdateSuccess(checkAppUpdateBean);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onDismissProgress();
                    }

                });
    }

    /**
     * 注销账号
     * @param bean
     */
    public void submitLogoutAccount(LogoutAccountPutBean bean){
        mModel.submitLogoutAccount(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
//                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
//                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<LogoutAccountResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(LogoutAccountResultBean logoutAccountResultBean) {
                        mRootView.onDismissProgress();
                        if (logoutAccountResultBean.isSuccess()){
                            mRootView.submitLogoutAccountSuccess(logoutAccountResultBean);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onDismissProgress();
                    }
                });
    }

    /**
     * 解绑手机号码
     * @param bean
     */
    public void submitUnbindPhone(UnbindPhonePutBean bean){
        mModel.submitUnbindPhone(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
//                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
//                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<UnbindPhoneResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(UnbindPhoneResultBean unbindPhoneResultBean) {
                        mRootView.onDismissProgress();
                        mRootView.submitUnbindPhoneSuccess(unbindPhoneResultBean);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onDismissProgress();
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
