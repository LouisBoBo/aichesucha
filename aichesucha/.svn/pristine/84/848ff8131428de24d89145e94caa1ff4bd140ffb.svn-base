package com.car.scth.mvp.model;

import android.app.Application;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;
import javax.inject.Inject;

import com.car.scth.mvp.contract.LocationBaiduContract;
import com.car.scth.mvp.model.api.service.DeviceService;
import com.car.scth.mvp.model.api.service.PublicService;
import com.car.scth.mvp.model.api.service.UserService;
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
import com.car.scth.mvp.model.putbean.PhoneCodePutBean;
import com.car.scth.mvp.utils.ConstantValue;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.RequestBody;


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
public class LocationBaiduModel extends BaseModel implements LocationBaiduContract.Model{
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public LocationBaiduModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<DeviceListResultBean> getDeviceList(DeviceListPutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(DeviceService.class).getDeviceList(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<DeviceListResultBean>, ObservableSource<DeviceListResultBean>>() {
                    @Override
                    public ObservableSource<DeviceListResultBean> apply(Observable<DeviceListResultBean> deviceListResultBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(DeviceService.class).getDeviceList(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }

    @Override
    public Observable<DeviceDetailResultBean> getDeviceDetailInfo(DeviceDetailPutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(DeviceService.class).getDeviceDetailInfo(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<DeviceDetailResultBean>, ObservableSource<DeviceDetailResultBean>>() {
                    @Override
                    public ObservableSource<DeviceDetailResultBean> apply(Observable<DeviceDetailResultBean> deviceDetailResultBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(DeviceService.class).getDeviceDetailInfo(ConstantValue.getApiUrlSid(), requestBody);
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
    public Observable<DeviceListForManagerResultBean> getDeviceListForGroup(DeviceListForManagerPutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(DeviceService.class).getDeviceListForGroup(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<DeviceListForManagerResultBean>, ObservableSource<DeviceListForManagerResultBean>>() {
                    @Override
                    public ObservableSource<DeviceListForManagerResultBean> apply(Observable<DeviceListForManagerResultBean> deviceListForManagerResultBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(DeviceService.class).getDeviceListForGroup(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }

    @Override
    public Observable<BaseBean> submitOneKeyFunction(OnKeyFunctionPutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(DeviceService.class).submitOneKeyFunction(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<BaseBean>, ObservableSource<BaseBean>>() {
                    @Override
                    public ObservableSource<BaseBean> apply(Observable<BaseBean> baseBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(DeviceService.class).submitOneKeyFunction(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }

    @Override
    public Observable<PhoneCodeResultBean> getPhoneCode(PhoneCodePutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(PublicService.class).getPhoneCode(requestBody))
                .flatMap(new Function<Observable<PhoneCodeResultBean>, ObservableSource<PhoneCodeResultBean>>() {
                    @Override
                    public ObservableSource<PhoneCodeResultBean> apply(Observable<PhoneCodeResultBean> phoneCodeResultBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(PublicService.class).getPhoneCode(requestBody);
                    }
                });
    }

    @Override
    public Observable<MergeAccountResultBean> submitMergeAccount(MergeAccountPutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(UserService.class).submitMergeAccount(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<MergeAccountResultBean>, ObservableSource<MergeAccountResultBean>>() {
                    @Override
                    public ObservableSource<MergeAccountResultBean> apply(Observable<MergeAccountResultBean> mergeAccountResultBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(UserService.class).submitMergeAccount(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }

}