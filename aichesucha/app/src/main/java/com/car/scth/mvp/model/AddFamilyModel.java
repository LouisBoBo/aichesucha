package com.car.scth.mvp.model;

import android.app.Application;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;
import javax.inject.Inject;

import com.car.scth.mvp.contract.AddFamilyContract;
import com.car.scth.mvp.model.api.service.AccountService;
import com.car.scth.mvp.model.bean.FamilyAddResultBean;
import com.car.scth.mvp.model.putbean.AddFamilyPutBean;
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
 * Created by MVPArmsTemplate on 01/11/2021 16:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class AddFamilyModel extends BaseModel implements AddFamilyContract.Model{
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public AddFamilyModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<FamilyAddResultBean> submitAddFamily(AddFamilyPutBean bean) {
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(bean));
        return Observable.just(mRepositoryManager.obtainRetrofitService(AccountService.class).addFamily(ConstantValue.getApiUrlSid(), requestBody))
                .flatMap(new Function<Observable<FamilyAddResultBean>, ObservableSource<FamilyAddResultBean>>() {
                    @Override
                    public ObservableSource<FamilyAddResultBean> apply(Observable<FamilyAddResultBean> beanObservable) throws Exception {
                        return mRepositoryManager.obtainRetrofitService(AccountService.class).addFamily(ConstantValue.getApiUrlSid(), requestBody);
                    }
                });
    }
}