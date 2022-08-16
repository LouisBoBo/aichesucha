package com.car.scth.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.car.scth.mvp.contract.SimDetailContract;
import com.car.scth.mvp.model.api.service.SimService;
import com.car.scth.mvp.model.bean.SimDetailResultBean;
import com.car.scth.mvp.model.putbean.SimDetailPutBean;
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
 * Created by MVPArmsTemplate on 10/28/2020 16:54
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class SimDetailModel extends BaseModel implements SimDetailContract.Model{
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public SimDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<SimDetailResultBean> getSimDetail(SimDetailPutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(SimService.class).getSimDetail(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<SimDetailResultBean>, ObservableSource<SimDetailResultBean>>() {
                    @Override
                    public ObservableSource<SimDetailResultBean> apply(Observable<SimDetailResultBean> simDetailResultBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(SimService.class).getSimDetail(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }
}