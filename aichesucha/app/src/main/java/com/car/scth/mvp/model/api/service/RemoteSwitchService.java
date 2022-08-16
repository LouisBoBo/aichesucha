package com.car.scth.mvp.model.api.service;

import com.car.scth.mvp.model.api.Api;
import com.car.scth.mvp.model.bean.RemoteSwitchResultBean;
import com.car.scth.mvp.model.bean.TimeSwitchGetResultBean;
import com.car.scth.mvp.model.entity.BaseBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 远程开关机，定时开关机服务
 */
public interface RemoteSwitchService {

    /**
     * 设置远程开关机
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<RemoteSwitchResultBean> submitRemoteSwitch(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 获取定时开关机数据
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<TimeSwitchGetResultBean> getTimeSwitch(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 设置定时开关机
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<BaseBean> submitTimeSwitch(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 删除定时开关机
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<BaseBean> submitTimeSwitchDelete(@Query("sid") String sid, @Body RequestBody requestBody);


}
