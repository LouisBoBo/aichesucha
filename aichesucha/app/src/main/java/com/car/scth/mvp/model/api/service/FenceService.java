package com.car.scth.mvp.model.api.service;

import retrofit2.http.GET;
import retrofit2.http.Url;
import com.car.scth.mvp.model.api.Api;
import com.car.scth.mvp.model.bean.BaiduMapAddressBean;
import com.car.scth.mvp.model.bean.FenceAddResultBean;
import com.car.scth.mvp.model.bean.FenceResultBean;
import com.car.scth.mvp.model.entity.BaseBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 围栏相关服务类
 */
public interface FenceService {

    /**
     * 获取围栏列表
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<FenceResultBean> getFenceList(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 围栏删除
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<BaseBean> submitFenceDelete(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 添加围栏
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<FenceAddResultBean> submitFenceAdd(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 修改围栏
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<BaseBean> submitFenceModify(@Query("sid") String sid, @Body RequestBody requestBody);

}
