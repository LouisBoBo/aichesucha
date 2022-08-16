package com.car.scth.mvp.model.api.service;

import com.car.scth.mvp.model.api.Api;
import com.car.scth.mvp.model.bean.CheckAppUpdateBean;
import com.car.scth.mvp.model.bean.LoginResultBean;
import com.car.scth.mvp.model.bean.PhoneAreaResultBean;
import com.car.scth.mvp.model.bean.PhoneCodeResultBean;
import com.car.scth.mvp.model.bean.TaskProgressResultBean;
import com.car.scth.mvp.model.entity.BaseBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 公共请求服务类
 */
public interface PublicService {

    /**
     * 登录
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<LoginResultBean> submitLogin(@Body RequestBody requestBody);

    /**
     * 注册
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<BaseBean> submitRegister(@Body RequestBody requestBody);

    /**
     * 获取手机号码国际区号
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<PhoneAreaResultBean> getPhoneArea(@Body RequestBody requestBody);

    /**
     * 获取手机验证码
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<PhoneCodeResultBean> getPhoneCode(@Body RequestBody requestBody);

    /**
     * 获取版本更新信息
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<CheckAppUpdateBean> getAppUpdate(@Body RequestBody requestBody);

    /**
     * 提交找回密码
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<BaseBean> submitForgetPassword(@Body RequestBody requestBody);

    /**
     * 获取任务进度
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<TaskProgressResultBean> getTaskProgress(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 获取邮箱验证码
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<BaseBean> getEmailCode(@Body RequestBody requestBody);
}
