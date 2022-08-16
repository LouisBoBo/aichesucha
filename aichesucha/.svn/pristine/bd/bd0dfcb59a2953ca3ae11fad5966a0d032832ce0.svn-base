package com.car.scth.mvp.model;

import android.app.Application;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;
import javax.inject.Inject;

import com.car.scth.mvp.contract.MileageStatisticsContract;
import com.car.scth.mvp.model.api.service.SummaryService;
import com.car.scth.mvp.model.bean.MileageStatisticsResultBean;
import com.car.scth.mvp.model.putbean.MileageStatisticsPutBean;
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
 * Created by MVPArmsTemplate on 12/26/2020 11:35
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class MileageStatisticsModel extends BaseModel implements MileageStatisticsContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MileageStatisticsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<MileageStatisticsResultBean> getMileageStatistics(MileageStatisticsPutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(SummaryService.class).getMileageStatistics(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<MileageStatisticsResultBean>, ObservableSource<MileageStatisticsResultBean>>() {
                    @Override
                    public ObservableSource<MileageStatisticsResultBean> apply(Observable<MileageStatisticsResultBean> beanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(SummaryService.class).getMileageStatistics(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }
}