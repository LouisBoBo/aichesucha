package com.car.scth.mvp.model;

import android.app.Application;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;
import javax.inject.Inject;

import com.car.scth.mvp.contract.RealTimeModeContract;
import com.car.scth.mvp.model.api.service.LocationModeService;
import com.car.scth.mvp.model.bean.RealTimeModeResultBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.RealTimeModePutBean;
import com.car.scth.mvp.model.putbean.RealTimeModeSetPutBean;
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
 * Created by MVPArmsTemplate on 11/14/2020 17:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class RealTimeModeModel extends BaseModel implements RealTimeModeContract.Model{
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public RealTimeModeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<RealTimeModeResultBean> getRealTimeMode(RealTimeModePutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(LocationModeService.class).getRealTimeMode(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<RealTimeModeResultBean>, ObservableSource<RealTimeModeResultBean>>() {
                    @Override
                    public ObservableSource<RealTimeModeResultBean> apply(Observable<RealTimeModeResultBean> realTimeModeResultBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(LocationModeService.class).getRealTimeMode(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }

    @Override
    public Observable<BaseBean> submitRealTimeMode(RealTimeModeSetPutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(LocationModeService.class).submitRealTimeMode(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<BaseBean>, ObservableSource<BaseBean>>() {
                    @Override
                    public ObservableSource<BaseBean> apply(Observable<BaseBean> baseBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(LocationModeService.class).submitRealTimeMode(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }
}