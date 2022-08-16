package com.car.scth.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.car.scth.mvp.contract.RecycleBinContract;
import com.car.scth.mvp.model.api.service.DeviceService;
import com.car.scth.mvp.model.api.service.PublicService;
import com.car.scth.mvp.model.bean.DeviceBaseResultBean;
import com.car.scth.mvp.model.bean.DeviceGroupResultBean;
import com.car.scth.mvp.model.bean.RecycleBinResultBean;
import com.car.scth.mvp.model.bean.TaskProgressResultBean;
import com.car.scth.mvp.model.putbean.DeviceGroupPutBean;
import com.car.scth.mvp.model.putbean.RecycleBinPutBean;
import com.car.scth.mvp.model.putbean.RemoveDevicePutBean;
import com.car.scth.mvp.model.putbean.RestoreToOriginalAccountPutBean;
import com.car.scth.mvp.model.putbean.TaskProgressPubBean;
import com.car.scth.mvp.utils.ConstantValue;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.RequestBody;


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
public class RecycleBinModel extends BaseModel implements RecycleBinContract.Model{
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public RecycleBinModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<RecycleBinResultBean> getRecycleBinData(RecycleBinPutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(DeviceService.class).getRecycleBinData(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<RecycleBinResultBean>, ObservableSource<RecycleBinResultBean>>() {
                    @Override
                    public ObservableSource<RecycleBinResultBean> apply(Observable<RecycleBinResultBean> recycleBinResultBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(DeviceService.class).getRecycleBinData(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }

    @Override
    public Observable<DeviceBaseResultBean> submitRestoreToOriginalAccount(RestoreToOriginalAccountPutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(DeviceService.class).submitRestoreToOriginalAccount(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<DeviceBaseResultBean>, ObservableSource<DeviceBaseResultBean>>() {
                    @Override
                    public ObservableSource<DeviceBaseResultBean> apply(Observable<DeviceBaseResultBean> restoreToOriginalAccountResultBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(DeviceService.class).submitRestoreToOriginalAccount(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }

    @Override
    public Observable<DeviceBaseResultBean> submitDeleteDevice(RemoveDevicePutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(DeviceService.class).submitRemoveDelete(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<DeviceBaseResultBean>, ObservableSource<DeviceBaseResultBean>>() {
                    @Override
                    public ObservableSource<DeviceBaseResultBean> apply(Observable<DeviceBaseResultBean> deleteDeviceResultBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(DeviceService.class).submitRemoveDelete(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }

    @Override
    public Observable<TaskProgressResultBean> getTaskProgress(TaskProgressPubBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(PublicService.class).getTaskProgress(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<TaskProgressResultBean>, ObservableSource<TaskProgressResultBean>>() {
                    @Override
                    public ObservableSource<TaskProgressResultBean> apply(Observable<TaskProgressResultBean> taskProgressResultBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(PublicService.class).getTaskProgress(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }

    @Override
    public Observable<DeviceGroupResultBean> getDeviceGroupList(DeviceGroupPutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(DeviceService.class).getDeviceGroupList(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<DeviceGroupResultBean>, ObservableSource<DeviceGroupResultBean>>() {
                    @Override
                    public ObservableSource<DeviceGroupResultBean> apply(Observable<DeviceGroupResultBean> deviceGroupResultBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(DeviceService.class).getDeviceGroupList(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }
}