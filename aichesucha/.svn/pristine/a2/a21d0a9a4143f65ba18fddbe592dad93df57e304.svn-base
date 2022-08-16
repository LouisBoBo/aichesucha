package com.car.scth.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.car.scth.mvp.contract.LocationBaiduContract;
import com.car.scth.mvp.model.api.Api;
import com.car.scth.mvp.model.bean.DeviceDetailResultBean;
import com.car.scth.mvp.model.bean.DeviceGroupResultBean;
import com.car.scth.mvp.model.bean.DeviceListForManagerResultBean;
import com.car.scth.mvp.model.bean.DeviceListResultBean;
import com.car.scth.mvp.model.bean.MergeAccountResultBean;
import com.car.scth.mvp.model.bean.PhoneCodeResultBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.DeviceDetailPutBean;
import com.car.scth.mvp.model.putbean.DeviceGroupPutBean;
import com.car.scth.mvp.model.putbean.DeviceListForManagerPutBean;
import com.car.scth.mvp.model.putbean.DeviceListPutBean;
import com.car.scth.mvp.model.putbean.MergeAccountPutBean;
import com.car.scth.mvp.model.putbean.OnKeyFunctionPutBean;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import com.car.scth.mvp.model.putbean.PhoneCodePutBean;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/26/2020 15:55
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class LocationBaiduPresenter extends BasePresenter<LocationBaiduContract.Model, LocationBaiduContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public LocationBaiduPresenter (LocationBaiduContract.Model model, LocationBaiduContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 获取当前账号下设备列表，设备号登录同求
     * @param bean
     * @param isShow
     */
    public void getDeviceList(DeviceListPutBean bean, boolean isShow, boolean isInitiativeRefresh, boolean clearMap){
        mModel.getDeviceList(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
//                    if (isShow){
//                        mRootView.showLoading();
//                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
//                    if (isShow){
//                        mRootView.hideLoading();
//                    }
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<DeviceListResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(DeviceListResultBean deviceListResultBean) {
                        if (isShow){
                            mRootView.onDismissProgress();
                        }
                        if (deviceListResultBean.isSuccess()){
                            mRootView.getDeviceListSuccess(deviceListResultBean, isInitiativeRefresh, clearMap);
                        }else{
                            mRootView.getDeviceListError(deviceListResultBean.getErrcode());
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
     * 获取设备详情
     * @param bean
     */
    public void getDeviceDetailInfo(DeviceDetailPutBean bean){
        mModel.getDeviceDetailInfo(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
//                    mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
//                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<DeviceDetailResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(DeviceDetailResultBean deviceDetailResultBean) {
                        if (deviceDetailResultBean.isSuccess()){
                            mRootView.getDeviceDetailInfoSuccess(deviceDetailResultBean);
                        }else if (deviceDetailResultBean.getErrcode() == Api.Device_Freeze || deviceDetailResultBean.getErrcode() == Api.Data_Change){
                            mRootView.getDeviceDetailError();
                        }
                    }
                });
    }

    /**
     * 获取车组织列表和车组列表
     * @param bean
     */
    public void getDeviceGroupList(DeviceGroupPutBean bean, boolean isRefresh ,String code){
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
                        if (isRefresh){
                            mRootView.finishRefresh();
                        }
                        if (deviceGroupResultBean.isSuccess()){
                            mRootView.getDeviceGroupListSuccess(deviceGroupResultBean, isRefresh,code);
                            if (deviceGroupResultBean.getGarages() != null){
                                if (deviceGroupResultBean.getGarages().size() == 0 || deviceGroupResultBean.getGarages().size() < bean.getParams().getG_limit_size()) {
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
     * 获取设备下的分组列表
     * @param bean
     * @param isRefresh
     */
    public void getDeviceListForGroup(DeviceListForManagerPutBean bean, boolean isRefresh){
        mModel.getDeviceListForGroup(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
//                    mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
//                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<DeviceListForManagerResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(DeviceListForManagerResultBean deviceListForManagerResultBean) {
                        if (deviceListForManagerResultBean.isSuccess()){
                            mRootView.getDeviceListForGroupSuccess(deviceListForManagerResultBean, isRefresh);
                            if (deviceListForManagerResultBean.getItems() != null){
                                if (deviceListForManagerResultBean.getItems().size() == 0 || deviceListForManagerResultBean.getItems().size() < bean.getParams().getLimit_size()) {
                                    mRootView.endLoadMoreForDevice();//隐藏上拉加载更多的进度条
                                    mRootView.setNoMoreForDevice();
                                }else{
                                    mRootView.endLoadMoreForDevice();
                                }
                            }else{
                                mRootView.endLoadMoreForDevice();//隐藏上拉加载更多的进度条
                                mRootView.setNoMoreForDevice();
                            }
                        }else{
                            mRootView.endLoadFailForDevice();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.endLoadFailForDevice();
                    }

                });
    }

    /**
     * 一键功能设置
     * @param bean
     */
    public void submitOneKeyFunction(OnKeyFunctionPutBean bean){
        mModel.submitOneKeyFunction(bean)
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
                            mRootView.submitOneKeyFunctionSuccess(baseBean);
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
     * 获取手机验证码
     *
     * @param bean
     */
    public void getPhoneCode(PhoneCodePutBean bean) {
        mModel.getPhoneCode(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
//                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
//                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<PhoneCodeResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(PhoneCodeResultBean phoneCodeResultBean) {
                        if (phoneCodeResultBean.isSuccess()) {
                            mRootView.getPhoneCodeSuccess(phoneCodeResultBean);
                        }
                    }
                });
    }

    /**
     * 合并账号
     * @param bean
     */
    public void submitMergeAccount(MergeAccountPutBean bean){
        mModel.submitMergeAccount(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<MergeAccountResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(MergeAccountResultBean mergeAccountResultBean) {
                        mRootView.submitMergeAccountSuccess(mergeAccountResultBean);
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
