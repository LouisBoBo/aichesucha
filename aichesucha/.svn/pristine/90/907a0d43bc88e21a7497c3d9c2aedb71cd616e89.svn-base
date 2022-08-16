package com.car.scth.mvp.model.api.service;

import com.car.scth.mvp.model.api.Api;
import com.car.scth.mvp.model.bean.AccountUserListResultBean;
import com.car.scth.mvp.model.bean.DeviceBaseResultBean;
import com.car.scth.mvp.model.bean.FamilyAddResultBean;
import com.car.scth.mvp.model.bean.GetTaskResultBean;
import com.car.scth.mvp.model.entity.BaseBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AccountService {


    /**
     * 获取账号列表
     * @param sid
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<AccountUserListResultBean> getAccountList(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 修改组织名称
     * @param sid
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<BaseBean> editFamilyName(@Query("sid") String sid,@Body RequestBody requestBody);


    /**
     * 删除组织
     * @param sid
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<DeviceBaseResultBean> deleteFamily(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 添加下一级
     * @param sid
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<FamilyAddResultBean> addFamily(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 删除组织
     * @param sid
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<GetTaskResultBean> getTaskResult(@Query("sid") String sid, @Body RequestBody requestBody);

}
