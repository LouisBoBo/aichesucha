package com.car.scth.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.car.scth.mvp.contract.RemoteSwitchContract;
import com.car.scth.mvp.model.api.service.RemoteSwitchService;
import com.car.scth.mvp.model.bean.RemoteSwitchResultBean;
import com.car.scth.mvp.model.bean.TimeSwitchGetResultBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.RemoteSwitchSetPutBean;
import com.car.scth.mvp.model.putbean.TimeSwitchDeletePutBean;
import com.car.scth.mvp.model.putbean.TimeSwitchGetPutBean;
import com.car.scth.mvp.model.putbean.TimeSwitchSetPutBean;
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
 * Created by MVPArmsTemplate on 10/31/2020 17:40
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class RemoteSwitchModel extends BaseModel implements RemoteSwitchContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public RemoteSwitchModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<RemoteSwitchResultBean> submitRemoteSwitch(RemoteSwitchSetPutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(RemoteSwitchService.class).submitRemoteSwitch(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<RemoteSwitchResultBean>, ObservableSource<RemoteSwitchResultBean>>() {
                    @Override
                    public ObservableSource<RemoteSwitchResultBean> apply(Observable<RemoteSwitchResultBean> remoteSwitchResultBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(RemoteSwitchService.class).submitRemoteSwitch(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }

    @Override
    public Observable<TimeSwitchGetResultBean> getTimeSwitch(TimeSwitchGetPutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(RemoteSwitchService.class).getTimeSwitch(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<TimeSwitchGetResultBean>, ObservableSource<TimeSwitchGetResultBean>>() {
                    @Override
                    public ObservableSource<TimeSwitchGetResultBean> apply(Observable<TimeSwitchGetResultBean> timeSwitchGetResultBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(RemoteSwitchService.class).getTimeSwitch(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }

    @Override
    public Observable<BaseBean> submitTimeSwitch(TimeSwitchSetPutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(RemoteSwitchService.class).submitTimeSwitch(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<BaseBean>, ObservableSource<BaseBean>>() {
                    @Override
                    public ObservableSource<BaseBean> apply(Observable<BaseBean> baseBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(RemoteSwitchService.class).submitTimeSwitch(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }

    @Override
    public Observable<BaseBean> submitTimeSwitchDelete(TimeSwitchDeletePutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(RemoteSwitchService.class).submitTimeSwitchDelete(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<BaseBean>, ObservableSource<BaseBean>>() {
                    @Override
                    public ObservableSource<BaseBean> apply(Observable<BaseBean> baseBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(RemoteSwitchService.class).submitTimeSwitchDelete(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }
}