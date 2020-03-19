package com.xuexue.lib.sdk;

import com.facebook.react.bridge.ReadableMap;
import com.xuexue.lib.sdk.login.YangYangLoginResult;
import com.xuexue.lib.sdk.pay.YangYangPayResult;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

    private static final char[] HEX_DIGIT = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static YangYangLoginResult toYangYangLoginResult(ReadableMap params) {
        YangYangLoginResult result = new YangYangLoginResult();
        result.userId = Double.valueOf(params.getDouble("userId")).intValue();
        result.accountId = params.getString("accountId");
        return result;
    }

    public static YangYangPayResult toYangYangPayResult(ReadableMap params) {
        YangYangPayResult result = new YangYangPayResult();
        result.requestId = params.getString("requestId");
        result.time = System.currentTimeMillis();
        return result;
    }

    public static String MD5(String s) {
        if (s == null) {
            return null;
        } else {
            try {
                MessageDigest mdInst = MessageDigest.getInstance("MD5");
                mdInst.update(s.getBytes());
                byte[] md = mdInst.digest();
                char[] result = new char[32];
                int i = 0;

                for (int var5 = 0; i < 16; ++i) {
                    result[var5++] = HEX_DIGIT[md[i] >>> 4 & 15];
                    result[var5++] = HEX_DIGIT[md[i] & 15];
                }

                return new String(result);
            } catch (NoSuchAlgorithmException var6) {
                return "";
            }
        }
    }
}
