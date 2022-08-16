package com.car.scth.mvp.model.api.service;

import com.car.scth.mvp.model.api.Api;
import com.car.scth.mvp.model.bean.RechargePackagesResultBean;
import com.car.scth.mvp.model.bean.SimDetailResultBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * sim卡相关服务
 */
public interface SimService {

    /**
     * 获取sim卡详细信息
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<SimDetailResultBean> getSimDetail(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 获取sim卡充值套餐
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<RechargePackagesResultBean> getRechargePackages(@Query("sid") String sid, @Body RequestBody requestBody);

}
