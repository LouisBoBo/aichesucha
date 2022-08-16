package com.car.scth.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.car.scth.mvp.contract.UserManagementContract;
import com.car.scth.mvp.model.bean.AccountUserListResultBean;
import com.car.scth.mvp.model.bean.DeviceBaseResultBean;
import com.car.scth.mvp.model.bean.DeviceGroupResultBean;
import com.car.scth.mvp.model.bean.GetTaskResultBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.AccountUserInfoPutBean;
import com.car.scth.mvp.model.putbean.DeleteFamilyPutBean;
import com.car.scth.mvp.model.putbean.DeviceGroupPutBean;
import com.car.scth.mvp.model.putbean.EditFamilyPutBean;
import com.car.scth.mvp.model.putbean.GetTaskPutBean;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/07/2021 10:27
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class UserManagementPresenter extends BasePresenter<UserManagementContract.Model, UserManagementContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public UserManagementPresenter(UserManagementContract.Model model, UserManagementContract.View rootView) {
        super(model, rootView);
    }

    public void getAccountList(AccountUserInfoPutBean bean) {
        mModel.getAccountList(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<AccountUserListResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(AccountUserListResultBean accountUserListResultBean) {
                        if (accountUserListResultBean.isSuccess() || accountUserListResultBean.isSuccessFor200()) {

                        }
                    }
                });

    }


    /**
     * 获取车组织列表和车组列表
     *
     * @param bean
     */
    public void getDeviceGroupList(DeviceGroupPutBean bean, boolean isRefresh,int level) {
        mModel.getDeviceGroupList(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
//                    mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
//                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<DeviceGroupResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(DeviceGroupResultBean deviceGroupResultBean) {
                        if (isRefresh) {
                            mRootView.finishRefresh();
                        }
                        if (deviceGroupResultBean.isSuccess()) {
                            if (deviceGroupResultBean.getFamilys() != null) {
                                mRootView.getFamilyListSuccess(deviceGroupResultBean, isRefresh, bean.getParams().getFamilyid(), level);
                                if (deviceGroupResultBean.getFamilys().size() == 0 || deviceGroupResultBean.getFamilys().size() < bean.getParams().getF_limit_size()) {
                                    mRootView.endLoadMore();//隐藏上拉加载更多的进度条
                                    mRootView.setNoMore();
                                } else {
                                    mRootView.endLoadMore();
                                }
                            } else {
                                if (level == 0) { //只剩下最顶级的时候 需要返回状态
                                    mRootView.getFamilyListSuccess(null, isRefresh, bean.getParams().getFamilyid(), 0);
                                }
                                mRootView.endLoadMore();//隐藏上拉加载更多的进度条
                                mRootView.setNoMore();
                            }
                        } else {
                            mRootView.endLoadFail();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.endLoadFail();
                    }

                });
    }


    public void submitEditFamilyName(EditFamilyPutBean bean) {
        mModel.submitEditFamilyName(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean.isSuccess() || baseBean.isSuccessFor200()) {
                            mRootView.editFamilyNameSuccess(baseBean, bean.getParams().getFamilyid(),bean.getParams().getName());
                        }
                    }
                });

    }

    public void submitDeleteFamily(DeleteFamilyPutBean bean) {
        mModel.submitDeleteFamily(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<DeviceBaseResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(DeviceBaseResultBean baseBean) {
                        if (baseBean.isSuccess() || baseBean.isSuccessFor200()) {
                            mRootView.deleteFamilySuccess(baseBean);
                        }
                    }
                });

    }

    public void getTaskResult(GetTaskPutBean bean) {
        mModel.getTaskResult(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<GetTaskResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(GetTaskResultBean baseBean) {
                        if (baseBean.isSuccess()){
                            mRootView.getTaskSuccess(baseBean);
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
