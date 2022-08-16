package com.car.scth.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.car.scth.mvp.contract.UserManagementContract;
import com.car.scth.mvp.model.api.service.AccountService;
import com.car.scth.mvp.model.api.service.DeviceService;
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
 * Created by MVPArmsTemplate on 01/07/2021 10:27
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class UserManagementModel extends BaseModel implements UserManagementContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public UserManagementModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<AccountUserListResultBean> getAccountList(AccountUserInfoPutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(AccountService.class).getAccountList(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<AccountUserListResultBean>, ObservableSource<AccountUserListResultBean>>() {
                    @Override
                    public ObservableSource<AccountUserListResultBean> apply(Observable<AccountUserListResultBean> accountUserListResultBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(AccountService.class).getAccountList(ConstantValue.getApiUrlSid(), requestBody);
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

    @Override
    public Observable<BaseBean> submitEditFamilyName(EditFamilyPutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(AccountService.class).editFamilyName(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<BaseBean>, ObservableSource<BaseBean>>() {
                    @Override
                    public ObservableSource<BaseBean> apply(Observable<BaseBean> beanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(AccountService.class).editFamilyName(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }

    @Override
    public Observable<DeviceBaseResultBean> submitDeleteFamily(DeleteFamilyPutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(AccountService.class).deleteFamily(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<DeviceBaseResultBean>, ObservableSource<DeviceBaseResultBean>>() {
                    @Override
                    public ObservableSource<DeviceBaseResultBean> apply(Observable<DeviceBaseResultBean> beanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(AccountService.class).deleteFamily(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }

    @Override
    public Observable<GetTaskResultBean> getTaskResult(GetTaskPutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(AccountService.class).getTaskResult(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<GetTaskResultBean>, ObservableSource<GetTaskResultBean>>() {
                    @Override
                    public ObservableSource<GetTaskResultBean> apply(Observable<GetTaskResultBean> beanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(AccountService.class).getTaskResult(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }
}