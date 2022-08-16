package com.car.scth.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.car.scth.mvp.contract.RechargeContract;
import com.car.scth.mvp.model.api.service.SimService;
import com.car.scth.mvp.model.bean.RechargePackagesResultBean;
import com.car.scth.mvp.model.putbean.RechargePackagesPutBean;
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
 * Created by MVPArmsTemplate on 11/19/2020 18:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class RechargeModel extends BaseModel implements RechargeContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public RechargeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<RechargePackagesResultBean> getRechargePackages(RechargePackagesPutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(SimService.class).getRechargePackages(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<RechargePackagesResultBean>, ObservableSource<RechargePackagesResultBean>>() {
                    @Override
                    public ObservableSource<RechargePackagesResultBean> apply(Observable<RechargePackagesResultBean> rechargePackagesResultBeanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(SimService.class).getRechargePackages(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }
}