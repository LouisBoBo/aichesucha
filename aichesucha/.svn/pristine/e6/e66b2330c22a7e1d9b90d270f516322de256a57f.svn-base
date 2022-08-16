package com.car.scth.mvp.model.api.service;

import com.car.scth.mvp.model.api.Api;
import com.car.scth.mvp.model.bean.DeviceBaseResultBean;
import com.car.scth.mvp.model.bean.DeviceConfigResultBean;
import com.car.scth.mvp.model.bean.DeviceDetailResultBean;
import com.car.scth.mvp.model.bean.DeviceGroupResultBean;
import com.car.scth.mvp.model.bean.DeviceListForManagerResultBean;
import com.car.scth.mvp.model.bean.DeviceListResultBean;
import com.car.scth.mvp.model.bean.FindDeviceResultBean;
import com.car.scth.mvp.model.bean.GroupAddResultBean;
import com.car.scth.mvp.model.bean.OperationRecordResultBean;
import com.car.scth.mvp.model.bean.RecycleBinResultBean;
import com.car.scth.mvp.model.bean.UnbindPhoneResultBean;
import com.car.scth.mvp.model.entity.BaseBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 设备相关请求
 */
public interface DeviceService {

    /**
     * 获取当前账号下设备列表，设备号登录同求
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<DeviceListResultBean> getDeviceList(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 获取设备详情
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<DeviceDetailResultBean> getDeviceDetailInfo(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 修改设备详细信息
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<BaseBean> submitDetailModify(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 获取操作记录数据
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<OperationRecordResultBean> getOperationRecord(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 获取设备的配置信息，支持的功能等
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<DeviceConfigResultBean> getDeviceConfig(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 设置设备的配置信息
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<BaseBean> setDeviceConfig(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 获取车组织列表和车组列表
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<DeviceGroupResultBean> getDeviceGroupList(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 获取分组下的设备列表
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<DeviceListForManagerResultBean> getDeviceListForGroup(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 添加分组
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<GroupAddResultBean> submitGroupAdd(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 编辑分组名称
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<BaseBean> submitGroupEdit(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 转移设备
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<DeviceBaseResultBean> submitTransferDevice(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 删除分组和设备
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<DeviceBaseResultBean> submitDeleteDevice(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 移除设备
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<DeviceBaseResultBean> submitRemoveDelete(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 添加设备
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<DeviceBaseResultBean> submitAddDevice(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 一键功能设置
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<BaseBean> submitOneKeyFunction(@Query("sid") String sid, @Body RequestBody requestBody);


    /**
     * 油电控制
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<BaseBean> submitOilElectricControl(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 删除分组
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<BaseBean> submitDeleteGroup(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 冻结设备
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<DeviceBaseResultBean> submitFreezeEquipment(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 获取回收站列表
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<RecycleBinResultBean> getRecycleBinData(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 回收站-恢复设置至原账号
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<DeviceBaseResultBean> submitRestoreToOriginalAccount(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 解绑手机号码
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<UnbindPhoneResultBean> submitUnbindPhone(@Query("sid") String sid, @Body RequestBody requestBody);

    /**
     * 模糊，精准搜索设备
     * @param sid 用户唯一id
     * @param requestBody
     * @return
     */
    @Headers({Api.HEADER_RELEASE_TYPE})
    @POST("/mapi")
    Observable<FindDeviceResultBean> getFindDeviceData(@Query("sid") String sid, @Body RequestBody requestBody);

}
