package com.car.scth.mvp.utils;

import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.car.scth.app.MyApplication;
import com.car.scth.mvp.model.api.ModuleValueService;

/**
 * 全局常量
 * Created by Administrator on 2019\5\9 0009.
 */

public class ConstantValue {

    public static final String Is_Get_Update_App = "is_get_update_app"; // 是否已经请求过版本更新接口了，请求过了就不再请求
    public final static String IS_AGREE_PRIVACY = "is_agree_privacy"; // 是否同意用户隐私协议弹窗，false：不同意，true：同意
    public final static String ACCOUNT = "account"; // 登录的账号或者设备号(String)
    public final static String PASSWORD = "password"; // 登录的密码(String)
    public final static String IS_SAVE_PASSWORD = "is_save_password"; // 是否保存密码(boolean)
    public final static String Family_Sid = "family_sid"; // 登录获取的组织id，默认为当前用户的(String)
    public final static String Family_Sid_Login = "family_sid_login"; // 登录获取的组织id，默认为当前用户的(String)
    public final static String Family_Sname = "family_name"; // 登录获取的组织的名称
    public final static String Family_Sname_Login = "family_name_login"; // 登录获取的组织的名称
    public final static String Family_Auth = "family_auth"; // 权限列表， 格式化的json字符串
    public final static String Is_Need_Check = "is_need_check"; // 是否需要检查资料.true需要，表示未绑定手机号码，false不需要
    public final static String Is_Check_Phone = "is_check_phone"; // 是否绑定手机号的账户
    public final static String Is_Modify_Password = "is_modify_password"; // 未绑定手机号用户，是否需要修改密码 boolean
    public final static String Is_Bind_Mobile = "is_bind_mobile"; // 是否需要绑定手机号码 boolean
    public final static String Push_Switch = "push_switch"; // 此用户是否需要推送用户推送标志,true代表开 false代表关
    public final static String Push_Family = "push_family"; // 返回对应的组织id，用于绑定推送family_(familyid号)，如果是设备号登录,则绑定dev_(imei号)
    public final static String IS_USER_AGREEMENT = "is_user_agreement"; // 登录即同意用户隐私协议，false：不同意，true：同意
    public final static String Is_Auth_agree = "is_auth_agree"; // 是否授予手机授权，boolean
    public final static String Phone_Zone = "phone_zone"; // 如果是手机号绑定登录，返回手机号的区号 (int)

    public final static String USER_SID = "user_sid"; // 会话id(String)
    public final static String MAP_TYPE = "map_type"; // 地图类型，0：高德地图，1：百度地图，2：谷歌地图(int)
    public final static String USER_LOGIN_TYPE = "user_login_type"; // 用户登录类型，e_account_login 或 e_device_login

    public final static String Record_Last_Time = "record_last_time"; // 获取实时录音数据时使用，开始时间-startTime传值(long)
    public final static String Record_Before_Time = "record_before_time"; // 获取历史录音数据时使用，结束时间-endTime传值(long)

    public final static String APP_Service_Ip = "app_service_ip"; // 手动输入的域名
    public static final String ACTIVITY_STATUS = "activity_status"; // 是否处于地图页，true  or  false，用于下发点名模式指令(后台切换前台运行)
    public final static String Is_Refresh_Group_Data = "isRefreshGroup"; // 是否刷新首页分组数据
    public final static String DEVICE_ACTION = "com.car.scth.action.device"; // 设备列表选中
    public final static String FromCN = "from_cn";//是否是中国内地登录

    public final static String Is_Request_Authority = "is_request_authority"; // 是否请求了用户手机权限，每次打开APP只请求一次
    public final static String Push_mpm = "push_mpm"; // 推送类型
    public final static String HUAWEI_TOKEN = "huawei_token"; // 华为token

    public final static String Is_No_More_Reminders = "is_no_more_reminders"; // 是否不再弹出合并账号提示
    public final static String Is_JPush_Init_Success = "is_jpush_init"; // 极光推送是否初始化成功
    public final static String Umeng_Init = "umeng_init"; // 友盟是否同意隐私政策，true or false

    // 选中设备的信息
    public static final String Device_Imei = "device_imei"; // 选中设备imei号
    public static final String Device_Smei = "device_smei"; // 设备加密后的imei号
    public static final String Device_Type = "device_type"; // 选中设备的设备类型
    public static final String Device_Icon = "device_icon"; // 选中设备的图标
    public static final String Device_LocInfo = "device_locinfo"; // 设备的定位信息
    public static final String Device_Number = "device_number"; // 选中设备的设备名称
    public static final String Device_State = "device_state"; // 设备的状态(int)
    public static final String Device_Mode = "device_mode"; // 设备定位模式(int)
    public static final String Device_Iccid = "device_iccid"; // 设备的iccid
    public static final String Device_Version = "device_version"; // 设备当前版本信息
    public static final String Device_Signalrate = "device_signalrate"; // 信号强度百分比值(int)
    public static final String Device_Signal = "device_signal"; // 信号强弱(int)
    public static final String Device_Power = "device_power"; // 选中设备的电量

    /**
     * 是否请求了用户手机权限
     * @return
     */
    public static boolean isRequestAuthority(){
        return SPUtils.getInstance().getBoolean(Is_Request_Authority, false);
    }

    /**
     * 是否同意了隐私政策
     * @return
     */
    public static boolean isAgreePrivacy(){
        return SPUtils.getInstance().getBoolean(IS_AGREE_PRIVACY);
    }
    /**
     * 获取友盟隐私政策是否同意了
     * @return
     */
    public static boolean getUmengInit(){
        return SPUtils.getInstance().getBoolean(Umeng_Init, false);
    }

    /**
     * 获取推送类型
     * @return
     */
    public static String getPushMpm(){
        return SPUtils.getInstance().getString(Push_mpm, ResultDataUtils.Push_XiaoMi);
    }

    /**
     * 当前界面是否处于地图页
     * @return
     */
    public static boolean isActivityStatus(){
        return SPUtils.getInstance().getBoolean(ACTIVITY_STATUS, false);
    }

    /**
     * 是否刷新首页分组数据
     * @return
     */
    public static boolean isRefreshGroupData(){
        return SPUtils.getInstance().getBoolean(Is_Refresh_Group_Data, false);
    }

    /**
     * 获取用户权限
     * @return
     */
    public static String getFamilyAuth(){
        return SPUtils.getInstance().getString(Family_Auth, "");
    }

    /**
     * 是否登录
     * @return
     */
    public static boolean isLogin(){
        return !TextUtils.isEmpty(SPUtils.getInstance().getString(USER_SID));
    }

    /**
     * 获取推送开关状态
     * @return
     */
    public static boolean isPushSwitch(){
        return SPUtils.getInstance().getBoolean(Push_Switch, true);
    }

    /**
     * 获取推送绑定的推送id
     * @return
     */
    public static String getPushFamily(){
        return SPUtils.getInstance().getString(Push_Family);
    }

    /**
     * 是否保存密码
     * @return
     */
    public static boolean isSavePassword() {
        return SPUtils.getInstance().getBoolean(IS_SAVE_PASSWORD, false);
    }

    /**
     * 获取sid参数，拼接在接口地址url后面
     * @return
     */
    public static String getApiUrlSid(){
        return SPUtils.getInstance().getString(USER_SID,"");
    }

    /**
     * 获取登录账号
     * @return
     */
    public static String getAccount(){
        return SPUtils.getInstance().getString(ACCOUNT, "");
    }

    /**
     * 获取登录密码
     * @return
     */
    public static String getPassword(){
        return SPUtils.getInstance().getString(PASSWORD, "");
    }

    /**
     * 获取登录账号类型
     * @return
     */
    public static String getAccountType(){
        return SPUtils.getInstance().getString(USER_LOGIN_TYPE, "");
    }

    /**
     * 是否是账号登录
     * @return
     */
    public static boolean isAccountLogin(){
        boolean isAccount = false;
        if (getAccountType().equals(ResultDataUtils.Login_type_Account)
                || getAccountType().equals(ResultDataUtils.Login_type_Phone_Account)){
            isAccount = true;
        }
        return isAccount;
    }

    /**
     * 获取登录的地图类型
     * @return
     */
    public static int getMapType(){
        return SPUtils.getInstance().getInt(MAP_TYPE, 0);
    }

    /**
     * 获取登录的family_id
     * @return
     */
    public static String getFamilySid(){
        return SPUtils.getInstance().getString(Family_Sid, "");
    }

    /**
     * 获取登录的family_Sname
     * @return
     */
    public static String getFamilySName(){
        return SPUtils.getInstance().getString(Family_Sname, "");
    }

    /**
     * 是否需要检查资料.true需要，表示未绑定手机号码，false不需要
     * @return
     */
    public static boolean isNeedCheck(){
        return SPUtils.getInstance().getBoolean(Is_Need_Check, false);
    }

    /**
     * 获取手动输入的ip
     * @param isRelease 是否是生产服
     * @return
     */
    public static String getAPPServiceIp(boolean isRelease){
        String serviceIp = SPUtils.getInstance().getString(APP_Service_Ip, "");
        if (!TextUtils.isEmpty(serviceIp)){
            if (!serviceIp.contains("http://") && !serviceIp.contains("https://")){
                serviceIp = "http://" + serviceIp;
            }
        }
        if (TextUtils.isEmpty(serviceIp)){
            return isRelease ? "http://srt.gps006.com" : "http://csapp.cciot.cc"; // API域名
        }else{
            serviceIp = Utils.replaceBlank(serviceIp);
            return serviceIp.trim();
        }
    }

    /**
     * 获取充值需要的参数 - sim卡充值
     * @param sim Sim卡号
     * @param simType  Sim卡类型。fxft福信富通，gzwl公众物联，lm龙猫卡，yd移动，zdst中点尚通，ykpt优卡平台
     * @param iccid 设备的iccid
     * @return
     */
    public static String getPaySimRechargeValue(String sim, String simType, String iccid){
        return "?sid=" + SPUtils.getInstance().getString(USER_SID) + "&simei=" + MyApplication.getMyApp().getSimei()
                + "&simno=" + sim + "&simType=" + simType + "&iccid=" + iccid+"&appName=ac";
    }

    /**
     * 获取充值需要的参数 - 增值服务充值
     * @param rtype 取值为GetRecordConfig接口的type字段
     * @return
     */
    public static String getPayRecordRechargeValue(int rtype){
        return "?sid=" + SPUtils.getInstance().getString(USER_SID) + "&simei=" + MyApplication.getMyApp().getSimei() + "&imei="
                + MyApplication.getMyApp().getImei() + "&rtype=" + rtype;
    }

    /**
     * 是否是账号登录
     * @return
     */
    public static boolean isLoginForAccount(){
        boolean isAccount = false;
        if (getAccountType().equals(ResultDataUtils.Login_type_Account)
                || getAccountType().equals(ResultDataUtils.Login_type_Phone_Account)){
            isAccount = true;
        }
        return isAccount;
    }

}
