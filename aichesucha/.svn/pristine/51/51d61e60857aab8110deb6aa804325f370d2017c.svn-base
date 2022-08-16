/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.car.scth.mvp.model.api.service;

import com.car.scth.mvp.model.api.Api;
import com.car.scth.mvp.model.bean.AccountListResultBean;
import com.car.scth.mvp.model.bean.AccountUserInfoResultBean;
import com.car.scth.mvp.model.bean.DeviceBaseResultBean;
import com.car.scth.mvp.model.bean.LogoutAccountResultBean;
import com.car.scth.mvp.model.bean.MergeAccountResultBean;
import com.car.scth.mvp.model.bean.UidInfoResultBean;
import com.car.scth.mvp.model.entity.BaseBean;
import com.car.scth.mvp.model.entity.User;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * ================================================
 * 展示 {@link Retrofit#create(Class)} 中需要传入的 ApiService 的使用方式
 * 存放关于用户的一些 API
 * <p>
 * Created by JessYan on 08/05/2016 12:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface UserService {
    String HEADER_API_VERSION = "Accept: application/vnd.github.v3+json";

    @Headers({HEADER_API_VERSION})
    @GET("/users")
    Observable<List<User>> getUsers(@Query("since") int lastIdQueried, @Query("per_page") int perPage);


    /**
     * 退出登录
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<BaseBean> submitSignOut(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 获取用户账号下设备分组列表
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<AccountUserInfoResultBean> getAccountUserInfo(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 修改密码
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<BaseBean> submitModifyPassword(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 注销账号
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<LogoutAccountResultBean> submitLogoutAccount(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 获取推送开关等信息
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<UidInfoResultBean> getUidInfo(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 获取当前组织的下级用户列表
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<AccountListResultBean> getAccountListNext(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 添加账号
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<BaseBean> submitAddAccount(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 删除账号
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<DeviceBaseResultBean> submitAccountDelete(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 转移权限
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<BaseBean> submitRoleTransfer(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 合并账号
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<MergeAccountResultBean> submitMergeAccount(@Query("sid") String sid, @Body RequestBody requestBody);

}
