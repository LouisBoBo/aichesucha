package com.car.scth.mvp.model;

import android.app.Application;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;
import javax.inject.Inject;

import com.car.scth.mvp.contract.FenceCreateAmapContract;
import com.car.scth.mvp.model.api.service.FenceService;
import com.car.scth.mvp.model.bean.FenceAddResultBean;
import com.car.scth.mvp.model.bean.FenceResultBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.putbean.FenceAddPutBean;
import com.car.scth.mvp.model.putbean.FenceListPutBean;
import com.car.scth.mvp.model.putbean.FenceModifyPutBean;
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
 * Created by MVPArmsTemplate on 11/03/2020 16:16
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class FenceCreateAmapModel extends BaseModel implements FenceCreateAmapContract.Model{
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public FenceCreateAmapModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<FenceResultBean> getFenceList(FenceListPutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(FenceService.class).getFenceList(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<FenceResultBean>, ObservableSource<FenceResultBean>>() {
                    @Override
                    public ObservableSource<FenceResultBean> apply(Observable<FenceResultBean> fenceResultBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(FenceService.class).getFenceList(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }

    @Override
    public Observable<FenceAddResultBean> submitFenceAdd(FenceAddPutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(FenceService.class).submitFenceAdd(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<FenceAddResultBean>, ObservableSource<FenceAddResultBean>>() {
                    @Override
                    public ObservableSource<FenceAddResultBean> apply(Observable<FenceAddResultBean> fenceAddResultBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(FenceService.class).submitFenceAdd(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }

    @Override
    public Observable<BaseBean> submitFenceModify(FenceModifyPutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(FenceService.class).submitFenceModify(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<BaseBean>, ObservableSource<BaseBean>>() {
                    @Override
                    public ObservableSource<BaseBean> apply(Observable<BaseBean> baseBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(FenceService.class).submitFenceModify(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }
}