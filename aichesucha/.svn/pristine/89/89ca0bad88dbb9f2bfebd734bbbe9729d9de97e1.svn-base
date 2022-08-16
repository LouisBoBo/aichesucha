package com.car.scth.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.car.scth.mvp.contract.RecordContract;
import com.car.scth.mvp.model.bean.RecordConfigResultBean;
import com.car.scth.mvp.model.bean.RecordResultBean;
import com.car.scth.mvp.model.bean.RecordScheduleResultBean;
import com.car.scth.mvp.model.bean.RecordShortResultBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.RecordConfigPutBean;
import com.car.scth.mvp.model.putbean.RecordDeletePutBean;
import com.car.scth.mvp.model.putbean.RecordPutBean;
import com.car.scth.mvp.model.putbean.RecordSchedulePutBean;
import com.car.scth.mvp.model.putbean.RecordShortPutBean;
import com.car.scth.mvp.model.putbean.RecordShortResultPutBean;
import com.car.scth.mvp.model.putbean.RecordStopPutBean;
import com.car.scth.mvp.model.putbean.VoiceRecordingPutBean;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/25/2020 15:19
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class RecordPresenter extends BasePresenter<RecordContract.Model, RecordContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public RecordPresenter (RecordContract.Model model, RecordContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 获取录音配置参数
     * @param bean
     */
    public void getRecordConfig(RecordConfigPutBean bean){
        mModel.getRecordConfig(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
//                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
//                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<RecordConfigResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(RecordConfigResultBean recordConfigResultBean) {
                        mRootView.dismissDialog();
                        if (recordConfigResultBean.isSuccess()){
                            mRootView.getRecordConfigSuccess(recordConfigResultBean);
                        }
                    }
                });
    }

    /**
     * 获取录音数据
     * @param bean
     * @param isHistoryData 是否是获取历史录音数据
     */
    public void getRecordData(RecordPutBean bean, boolean isHistoryData){
        mModel.getRecordData(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
//                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
//                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<RecordResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(RecordResultBean recordResultBean) {
                        if (isHistoryData){
                            mRootView.finishRefresh();
                        }
                        if (recordResultBean.isSuccess()){
                            mRootView.getRecordDataSuccess(recordResultBean, isHistoryData);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        if (isHistoryData){
                            mRootView.finishRefresh();
                        }
                    }
                });
    }

    /**
     * 开启or关闭声控录音
     * @param bean
     */
    public void submitAutoRecordSwitch(VoiceRecordingPutBean bean){
        mModel.submitAutoRecordSwitch(bean)
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
                        mRootView.dismissDialog();
                        if (baseBean.isSuccess()){
                            mRootView.submitAutoRecordSwitchSuccess(baseBean, bean.getParams().isSwitchX());
                        }
                    }
                });
    }

    /**
     * 开始短录音
     * @param bean
     */
    public void submitShortRecord(RecordShortPutBean bean){
        mModel.submitShortRecord(bean)
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
                        mRootView.dismissDialog();
                        mRootView.submitShortRecordSuccess(baseBean);
                    }
                });
    }

    /**
     * 获取开始短录音指令下发结果
     * @param bean
     */
    public void getShortRecordResult(RecordShortResultPutBean bean){
        mModel.getShortRecordResult(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
//                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
//                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<RecordShortResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(RecordShortResultBean recordShortResultBean) {
                        if (recordShortResultBean.isSuccess()){
                            mRootView.getShortRecordResultSuccess(recordShortResultBean);
                        }
                    }
                });
    }

    /**
     * 获取短录音进度
     * @param bean
     */
    public void getRecordSchedule(RecordSchedulePutBean bean){
        mModel.getRecordSchedule(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
//                    mRootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
//                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<RecordScheduleResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(RecordScheduleResultBean recordScheduleResultBean) {
                        if (recordScheduleResultBean.isSuccess()){
                            mRootView.getRecordScheduleSuccess(recordScheduleResultBean);
                        }
                    }
                });
    }

    /**
     * 删除录音文件
     * @param bean
     */
    public void submitDeleteRecord(RecordDeletePutBean bean){
        mModel.submitDeleteRecord(bean)
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
                        mRootView.dismissDialog();
                        if (baseBean.isSuccess()){
                            mRootView.submitDeleteRecordSuccess(baseBean);
                        }
                    }
                });
    }

    /**
     * 停止录音
     * @param bean
     */
    public void submitRecordStop(RecordStopPutBean bean){
        mModel.submitRecordStop(bean)
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
                        mRootView.dismissDialog();
                        if (baseBean.isSuccess()){
                            mRootView.submitRecordStopSuccess(baseBean);
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
