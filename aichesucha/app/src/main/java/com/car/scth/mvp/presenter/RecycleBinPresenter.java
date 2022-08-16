package com.car.scth.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.car.scth.mvp.contract.RecycleBinContract;
import com.car.scth.mvp.model.bean.DeviceBaseResultBean;
import com.car.scth.mvp.model.bean.DeviceGroupResultBean;
import com.car.scth.mvp.model.bean.RecycleBinResultBean;
import com.car.scth.mvp.model.bean.TaskProgressResultBean;
import com.car.scth.mvp.model.putbean.DeviceGroupPutBean;
import com.car.scth.mvp.model.putbean.RecycleBinPutBean;
import com.car.scth.mvp.model.putbean.RemoveDevicePutBean;
import com.car.scth.mvp.model.putbean.RestoreToOriginalAccountPutBean;
import com.car.scth.mvp.model.putbean.TaskProgressPubBean;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/05/2021 10:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class RecycleBinPresenter extends BasePresenter<RecycleBinContract.Model, RecycleBinContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public RecycleBinPresenter (RecycleBinContract.Model model, RecycleBinContract.View rootView) {
        super(model, rootView);
    }

    public void getRecycleBinData(RecycleBinPutBean bean, boolean isShow, boolean isRefresh){
        mModel.getRecycleBinData(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    if (isShow){
                        mRootView.showLoading();
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (isShow){
                        mRootView.hideLoading();
                    }
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<RecycleBinResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(RecycleBinResultBean recycleBinResultBean) {
                        if (isRefresh){
                            mRootView.finishRefresh();
                        }
                        if (recycleBinResultBean.isSuccess()){
                            mRootView.getRecycleBinDataSuccess(recycleBinResultBean, isRefresh);
                            if (recycleBinResultBean.getItems() != null){
                                if (recycleBinResultBean.getItems().size() == 0 || recycleBinResultBean.getItems().size() < bean.getParams().getLimit_size()) {
                                    mRootView.endLoadMore();//隐藏上拉加载更多的进度条
                                    mRootView.setNoMore();
                                }else{
                                    mRootView.endLoadMore();
                                }
                            }else{
                                mRootView.endLoadMore();//隐藏上拉加载更多的进度条
                                mRootView.setNoMore();
                            }
                        }else{
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

    /**
     * 回收站-恢复设置至原账号
     * @param bean
     */
    public void submitRestoreToOriginalAccount(RestoreToOriginalAccountPutBean bean){
        mModel.submitRestoreToOriginalAccount(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<DeviceBaseResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(DeviceBaseResultBean deviceBaseResultBean) {
                        if (deviceBaseResultBean.isSuccess()){
                            mRootView.submitRestoreToOriginalAccountSuccess(deviceBaseResultBean);
                        }
                    }
                });
    }

    /**
     * 删除设备
     * @param bean
     */
    public void submitDeleteDevice(RemoveDevicePutBean bean){
        mModel.submitDeleteDevice(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<DeviceBaseResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(DeviceBaseResultBean deviceBaseResultBean) {
                        if (deviceBaseResultBean.isSuccess()){
                            mRootView.submitDeleteDeviceSuccess(deviceBaseResultBean);
                        }
                    }
                });
    }

    /**
     * 获取任务进度
     * @param bean
     */
    public void getTaskProgress(TaskProgressPubBean bean){
        mModel.getTaskProgress(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
//                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
//                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<TaskProgressResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(TaskProgressResultBean taskProgressResultBean) {
                        if (taskProgressResultBean.isSuccess()){
                            mRootView.getTaskProgressSuccess(taskProgressResultBean);
                        }
                    }
                });
    }

    /**
     * 获取车组织列表和车组列表
     * @param bean
     */
    public void getDeviceGroupList(DeviceGroupPutBean bean, boolean isShow){
        mModel.getDeviceGroupList(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    if (isShow){
                        mRootView.showLoading();
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (isShow){
                        mRootView.hideLoading();
                    }
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<DeviceGroupResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(DeviceGroupResultBean deviceGroupResultBean) {
                        if (deviceGroupResultBean.isSuccess()){
                            mRootView.getDeviceGroupListSuccess(deviceGroupResultBean);
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
