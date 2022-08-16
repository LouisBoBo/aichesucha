package com.car.scth.mvp.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import java.lang.reflect.Method;

/**
 * 国内厂商
 */
public class ManufacturerUtil {

    public static final String MARK = Build.MANUFACTURER.toLowerCase();

    public static boolean isHuawei() {
        return MARK.contains("huawei");
    }

    public static boolean isXiaomi() {
        return MARK.contains("xiaomi") || MARK.contains("redmi");
    }

    public static boolean isOppo() {
        return MARK.contains("oppo");
    }

    public static boolean isVivo() {
        return MARK.contains("vivo");
    }

    public static boolean isMeizu() {
        return MARK.contains("meizu");
    }

    public static boolean isLeshi() {
        return MARK.contains("letv");
    }

    public static boolean isYijia() {
        return MARK.contains("oneplus");
    }

    /**
     * （判断emuiApiLevel>=9即可）
     *
     * @return
     */
    @SuppressLint("PrivateApi")
    public static boolean getEMUI() {
        int emuiApiLevel = 0;
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            Method method = cls.getDeclaredMethod("get", new Class[]{String.class});
            String level = (String) method.invoke(cls, new Object[]{"ro.build.hw_emui_api_level"});
            if (level != null && level.length() != 0)
                emuiApiLevel = Integer.parseInt(level);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emuiApiLevel >= 9;
    }

    /**
     * （判断hwid>=20401300即可）
     *
     * @param context
     * @return
     */
    public static boolean getHWID(Context context) {
        PackageManager pm = context.getPackageManager();
        int hwid = 0;
        try {
            PackageInfo pi = pm.getPackageInfo("com.huawei.hwid", 0);
            if (pi != null) {
                hwid = pi.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return hwid >= 20401300;
    }

    /**
     * 是否支持华为推送
     * @param context
     * @return true 华为推送  false 其他推送
     */
    public static boolean isHuaWeiPush(Context context) {
        return ManufacturerUtil.isHuawei() && ManufacturerUtil.getEMUI() && ManufacturerUtil.getHWID(context);
    }

}
