package com.xuexue.lib.sdk;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.xuexue.lib.sdk.login.YangYangLoginResult;
import com.xuexue.lib.sdk.login.YangYangUserInfo;
import com.xuexue.lib.sdk.module.YangYangModuleInfo;
import com.xuexue.lib.sdk.pay.YangYangPayResult;
import com.xuexue.lib.sdk.purchase.YangYangPurchaseInfo;

public class Utils {

    static YangYangModuleInfo toYangYangModuleInfo(ReadableMap params) {
        YangYangModuleInfo info = new YangYangModuleInfo();
        info.statusCode = Double.valueOf(getDouble(params, "statusCode")).intValue();
        info.errorMessage = getString(params, "errorMessage");
        info.packageName = getString(params, "packageName");
        info.version = getString(params, "version");
        info.size = getString(params, "size");
        info.zipChecksum = getString(params, "zipChecksum");
        info.appChecksum = getString(params, "appChecksum");
        info.appName = getString(params, "appName");
        return info;
    }

    static WritableMap fromYangYangModuleInfo(YangYangModuleInfo info) {
        WritableMap result = Arguments.createMap();
        result.putInt("statusCode", info.statusCode);
        result.putString("errorMessage", info.errorMessage);
        result.putString("packageName", info.packageName);
        result.putString("version", info.version);
        result.putString("size", info.size);
        result.putString("zipChecksum", info.zipChecksum);
        result.putString("appChecksum", info.appChecksum);
        result.putString("appName", info.appName);
        return result;
    }

    static WritableMap fromYangYangPurchaseInfo(YangYangPurchaseInfo info) {
        WritableMap result = Arguments.createMap();
        result.putInt("statusCode", info.statusCode);
        result.putString("errorMessage", info.errorMessage);
        if (info.modules != null) {
            result.putArray("modules", Arguments.fromJavaArgs(info.modules));
        }
        return result;
    }

    static YangYangLoginResult toYangYangLoginResult(ReadableMap params) {
        YangYangLoginResult result = new YangYangLoginResult();
        YangYangUserInfo userInfo = toYangYangUserInfo(params);
        result.userId = userInfo.userId;
        result.accountId = userInfo.accountId;
        result.accountType = userInfo.accountType;
        return result;
    }

    static YangYangPayResult toYangYangPayResult(ReadableMap params) {
        YangYangPayResult result = new YangYangPayResult();
        result.requestId = getString(params, "requestId");
        result.time = Double.valueOf(getDouble(params, "time")).longValue();
        return result;
    }

    static YangYangUserInfo toYangYangUserInfo(ReadableMap params) {
        YangYangUserInfo result = new YangYangLoginResult();
        result.userId = getString(params, "userId");
        result.accountId = getString(params, "accountId");
        result.accountType = String.valueOf(getDouble(params, "accountType"));
        return result;
    }

    static String getString(ReadableMap params, String key) {
        return params.hasKey(key) ? params.getString(key) : null;
    }

    static double getDouble(ReadableMap params, String key) {
        return params.hasKey(key) ? params.getDouble(key) : -1;
    }

}
