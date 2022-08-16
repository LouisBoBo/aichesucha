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
package com.car.scth.mvp.model.api;

import com.car.scth.mvp.utils.ConstantValue;

/**
 * ================================================
 * 存放一些与 API 有关的东西,如请求地址,请求码等
 * <p>
 * Created by JessYan on 08/05/2016 11:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface Api {

    boolean isRelease = false;//是否发布版本
    String APP_DOMAIN = ConstantValue.getAPPServiceIp(isRelease); // API域名
//    String APP_DOMAIN = isRelease ? "http://yjzx.8325.com" : "http://sl.8325.com"; // API域名

    // 返回成功码
    int SUCCESS = 0; // 成功
    int OUT_OF_DATE = 268697631; // 登录已过期
    int SUCCESS_200 = 200; // 成功，特殊情况
    int Device_Freeze = 269877281; // 设备已冻结
    int Data_Change = 269877251; // 数据已变更
    int Operational_Restrictions = 269877293; // 操作限制
    int Mobile_Bind_Used = 269877295; // 手机号已绑定其他账号或设备
    int Mobile_Code_Error = 269877302; // 验证码错误

    // 登录请求头
    String HEADER_RELEASE = "Content-Type:application/x-www-form-urlencoded";

    // 发布任务请求头
    String HEADER_RELEASE_TYPE = "Content-Type:application/json";

    // 获取手机IP地址信息
    String App_GetIPUrl = "http://pv.sohu.com/cityjson?ie=utf-8";

    // 帮助文档
    String Help_Center = "file:///android_asset/newhelpcenter.html";

    // 隐私政策
    String Privacy_Policy = "http://srt.gps006.com/privacy/privacyPolicy_ac.html";
    // 隐私政策-英文
    String Privacy_Policy_EN = "http://zbcar.8325.com/privacyPolicy_Llink_en.html";

    // mob短信
    String Mob_App_Key = "34cc1e267e924";
    // mob短信模板code
    String Mob_Module_Code = "10530953";
    // 用户登录类型
    int App_Type = 13;
    // sim卡充值
    String Pay_Sim_Recharge = isRelease?"http://srt.gps006.com/pay/" : "http://webcs.cciot.cc/pay/";
    //支付完成返回
    String Pay_Sim_Success_Return = isRelease?"http://srt.gps006.com/pay/return" : "http://webcs.cciot.cc/pay/return";
    //支付完成返回
    String Pay_Sim_Referer = isRelease?"http://srt.gps006.com" : "http://webcs.cciot.cc";
    // 增值服务充值
    String Pay_Record_Recharge = "http://zbcar.8325.com/pay_zb_v3/app_pay_record_zb.php";
    // 推送类型-极光推送
    String Push_JPush = "e_jpush";
    // 推送类型-华为推送
    String Push_HuaWei = "e_hpush";

    // 联系客服号码
    String Contact_Customer_Service = "8615914121092";

    // 百度地图解析地址API
    String Baidu_API_Code = "9A:26:C7:AF:7C:79:F1:E1:AA:41:8D:17:31:ED:B4:87:43:AE:18:8C;com.car.scth";
    String Baidu_API = "http://api.map.baidu.com/reverse_geocoding/v3/?ak=Qb2GQobp2ewRE7pPdK9q84Y5HBkXAfb2&output=json&coordtype=wgs84ll&location=";

    // 客服系统地址
    public static final String App_Service_Customer_Test = "http://114.215.190.173/service_test/customer.html"; // 用户交流页面
    public static final String App_Service_Test = "http://114.215.190.173/service_test/robot.html"; // 自动回复页面

    String HOST_MAP = "http://api.map.baidu.com"; //百度地图
}
