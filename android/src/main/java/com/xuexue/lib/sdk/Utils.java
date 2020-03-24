package com.xuexue.lib.sdk;

import com.facebook.react.bridge.ReadableMap;
import com.xuexue.lib.sdk.login.YangYangLoginResult;
import com.xuexue.lib.sdk.pay.YangYangPayResult;

public class Utils {


    static YangYangLoginResult toYangYangLoginResult(ReadableMap params) {
        YangYangLoginResult result = new YangYangLoginResult();
        result.userId = params.getString("userId");
        result.accountId = params.getString("accountId");
        return result;
    }

    static YangYangPayResult toYangYangPayResult(ReadableMap params) {
        YangYangPayResult result = new YangYangPayResult();
        result.requestId = params.getString("requestId");
        result.time = Double.valueOf(params.getDouble("time")).longValue();
        return result;
    }

}
