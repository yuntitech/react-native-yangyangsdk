package com.xuexue.lib.sdk;

import com.facebook.react.bridge.ReadableMap;
import com.xuexue.lib.sdk.login.YangYangLoginResult;
import com.xuexue.lib.sdk.login.YangYangUserInfo;
import com.xuexue.lib.sdk.pay.YangYangPayResult;

public class Utils {


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
