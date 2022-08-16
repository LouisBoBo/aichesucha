package com.car.scth.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.car.scth.mvp.contract.GroupManagementContract;
import com.car.scth.mvp.model.api.Api;
import com.car.scth.mvp.model.bean.DeviceBaseResultBean;
import com.car.scth.mvp.model.bean.DeviceDetailResultBean;
import com.car.scth.mvp.model.bean.DeviceGroupResultBean;
import com.car.scth.mvp.model.bean.FindDeviceResultBean;
import com.car.scth.mvp.model.bean.GroupAddResultBean;
import com.car.scth.mvp.model.bean.TaskProgressResultBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.DeviceDetailPutBean;
import com.car.scth.mvp.model.putbean.DeviceGroupPutBean;
import com.car.scth.mvp.model.putbean.FindDevicePutBean;
import com.car.scth.mvp.model.putbean.FreezeEquipmentPutBean;
import com.car.scth.mvp.model.putbean.GroupAddPutBean;
import com.car.scth.mvp.model.putbean.GroupDeletePutBean;
import com.car.scth.mvp.model.putbean.GroupEditPutBean;
import com.car.scth.mvp.model.putbean.RemoveDevicePutBean;
import com.car.scth.mvp.model.putbean.TaskProgressPubBean;
import com.car.scth.mvp.model.putbean.TransferDevicePutBean;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/08/2021 20:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class GroupManagementPresenter extends BasePresenter<GroupManagementContract.Model, GroupManagementContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public GroupManagementPresenter(GroupManagementContract.Model model, GroupManagementContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 获取车组织列表和车组列表
     * @param bean
     */
    public void getDeviceGroupList(DeviceGroupPutBean bean, boolean isShow, boolean isRefresh){
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
                        if (isRefresh){
                            mRootView.finishRefresh();
                        }
                        if (deviceGroupResultBean.isSuccess()){
                            mRootView.getDeviceGroupListSuccess(deviceGroupResultBean, isRefresh);
                            if (deviceGroupResultBean.getGarages() != null){
                                if (deviceGroupResultBean.getGarages().size() == 0 || deviceGroupResultBean.getGarages().size() < bean.getParams().getG_limit_size()) {
                                    mRootView.endLoadMore(0);//隐藏上拉加载更多的进度条
                                    mRootView.setNoMore(0);
                                }else{
                                    mRootView.endLoadMore(0);
                                }
                            }else{
                                mRootView.endLoadMore(0);//隐藏上拉加载更多的进度条
                                mRootView.setNoMore(0);
                            }
                        }else{
                            mRootView.endLoadFail(0);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.endLoadFail(0);
                    }

                });
    }

    /**
     * 添加分组
     * @param bean
     */
    public void submitGroupAdd(GroupAddPutBean bean){
        mModel.submitGroupAdd(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<GroupAddResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(GroupAddResultBean groupAddResultBean) {
                        if (groupAddResultBean.isSuccess()){
                            mRootView.submitGroupAddSuccess(groupAddResultBean);
                        }
                    }
                });
    }

    /**
     * 编辑分组名称
     * @param bean
     */
    public void submitGroupEdit(GroupEditPutBean bean){
        mModel.submitGroupEdit(bean)
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
                            mRootView.submitGroupEditSuccess(baseBean, bean.getParams().getGroup_name(), bean.getParams().getGid());
                        }else if (baseBean.getErrcode() == Api.Device_Freeze || baseBean.getErrcode() == Api.Data_Change){
                            mRootView.onRefreshData();
                        }
                    }
                });
    }

    /**
     * 转移设备
     * @param bean
     */
    public void submitTransferDevice(TransferDevicePutBean bean){
        mModel.submitTransferDevice(bean)
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
                    public void onNext(DeviceBaseResultBean transferDeviceResultBean) {
                        if (transferDeviceResultBean.isSuccess()){
                            mRootView.submitTransferDeviceSuccess(transferDeviceResultBean);
                        }else if (transferDeviceResultBean.getErrcode() == Api.Device_Freeze || transferDeviceResultBean.getErrcode() == Api.Data_Change){
                            mRootView.onRefreshData();
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
                        }else if (deviceBaseResultBean.getErrcode() == Api.Device_Freeze || deviceBaseResultBean.getErrcode() == Api.Data_Change){
                            mRootView.onRefreshData();
                        }
                    }
                });
    }

    /**
     * 删除分组
     * @param bean
     */
    public void submitDeleteGroup(GroupDeletePutBean bean){
        mModel.submitDeleteGroup(bean)
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
                            mRootView.submitDeleteGroupSuccess(baseBean);
                        }
                    }
                });
    }

    /**
     * 冻结设备
     * @param bean
     */
    public void submitFreezeEquipment(FreezeEquipmentPutBean bean){
        mModel.submitFreezeEquipment(bean)
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
                            mRootView.submitFreezeEquipmentSuccess(deviceBaseResultBean);
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
     * 模糊，精准搜索设备
     * @param bean
     * @return
     */
    public void getFindDeviceData(FindDevicePutBean bean){
        mModel.getFindDeviceData(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
//                    mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
//                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<FindDeviceResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(FindDeviceResultBean findDeviceResultBean) {
                        if (findDeviceResultBean.isSuccess()){
                            mRootView.getFindDeviceDataSuccess(findDeviceResultBean);
                            if (findDeviceResultBean.getItems() != null){
                                if (findDeviceResultBean.getItems().size() == 0 || findDeviceResultBean.getItems().size() < bean.getParams().getLimit_size()) {
                                    mRootView.endLoadMore(2);//隐藏上拉加载更多的进度条
                                    mRootView.setNoMore(2);
                                }else{
                                    mRootView.endLoadMore(2);
                                }
                            }else{
                                mRootView.endLoadMore(2);//隐藏上拉加载更多的进度条
                                mRootView.setNoMore(2);
                            }
                        }else{
                            mRootView.endLoadFail(2);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.endLoadFail(2);
                    }
                });
    }

    /**
     * 获取设备是否有有效定位数据
     *
     * @param bean
     */
    public void getDeviceHasLocation(DeviceDetailPutBean bean, long imei, String simei) {
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
                        mRootView.onDismissProgress();
                        if (deviceDetailResultBean.isSuccess()) {
                            mRootView.getDeviceHasLocationSuccess(deviceDetailResultBean, imei, simei);
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
