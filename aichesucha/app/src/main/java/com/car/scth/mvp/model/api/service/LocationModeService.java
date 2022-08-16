package com.car.scth.mvp.model.api.service;

import com.car.scth.mvp.model.api.Api;
import com.car.scth.mvp.model.bean.LocationModeGetResultBean;
import com.car.scth.mvp.model.bean.LoopLocationModeResultBean;
import com.car.scth.mvp.model.bean.RealTimeModeResultBean;
import com.car.scth.mvp.model.entity.BaseBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 定位模式相关服务类
 */
public interface LocationModeService {

    /**
     * 获取定位模式
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<LocationModeGetResultBean> getLocationMode(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 设置定位模式
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<BaseBean> submitLocationMode(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 获取实时定位模式数据
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<RealTimeModeResultBean> getRealTimeMode(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 设置实时定位模式
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<BaseBean> submitRealTimeMode(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 获取周期定位列表
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<LoopLocationModeResultBean> getLoopLocationMode(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 删除周期定位
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<BaseBean> submitLoopDelete(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 修改周期定位
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<BaseBean> submitLoopModify(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 添加周期定位
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<BaseBean> submitLoopModeAdd(@Query("sid") String sid, @Body RequestBody requestBody);

}
