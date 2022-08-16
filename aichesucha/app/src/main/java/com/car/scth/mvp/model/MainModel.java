package com.car.scth.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.car.scth.mvp.contract.MainContract;
import com.car.scth.mvp.model.api.service.PublicService;
import com.car.scth.mvp.model.api.service.UserService;
import com.car.scth.mvp.model.bean.CheckAppUpdateBean;
import com.car.scth.mvp.model.bean.UidInfoResultBean;
import com.car.scth.mvp.model.putbean.CheckAppUpdatePutBean;
import com.car.scth.mvp.model.putbean.UidInfoPutBean;
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
 * Created by MVPArmsTemplate on 10/14/2020 09:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class MainModel extends BaseModel implements MainContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MainModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<CheckAppUpdateBean> getAppUpdate(CheckAppUpdatePutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(PublicService.class).getAppUpdate(requestBody))
                .flatMap(new Function<Observable<CheckAppUpdateBean>, ObservableSource<CheckAppUpdateBean>>() {
                    @Override
                    public ObservableSource<CheckAppUpdateBean> apply(Observable<CheckAppUpdateBean> checkAppUpdateBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(PublicService.class).getAppUpdate(requestBody);
                    }
                });
    }

    @Override
    public Observable<UidInfoResultBean> getUidInfo(UidInfoPutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(UserService.class).getUidInfo(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<UidInfoResultBean>, ObservableSource<UidInfoResultBean>>() {
                    @Override
                    public ObservableSource<UidInfoResultBean> apply(Observable<UidInfoResultBean> uidInfoResultBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(UserService.class).getUidInfo(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }
}