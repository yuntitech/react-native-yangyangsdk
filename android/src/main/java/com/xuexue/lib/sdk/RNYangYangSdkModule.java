
package com.xuexue.lib.sdk;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.xuexue.lib.sdk.login.IYangYangLoginCallback;
import com.xuexue.lib.sdk.login.IYangYangLoginHandler;
import com.xuexue.lib.sdk.login.YangYangUserInfo;
import com.xuexue.lib.sdk.module.YangYangModuleCallback;
import com.xuexue.lib.sdk.module.YangYangModuleInfo;
import com.xuexue.lib.sdk.pay.IYangYangPayCallback;
import com.xuexue.lib.sdk.pay.IYangYangPayHandler;
import com.xuexue.lib.sdk.pay.YangYangPayRequest;
import com.xuexue.lib.sdk.purchase.YangYangPurchaseCallback;
import com.xuexue.lib.sdk.purchase.YangYangPurchaseInfo;


public class RNYangYangSdkModule extends ReactContextBaseJavaModule implements IYangYangLoginHandler,
        IYangYangPayHandler {

    private final ReactApplicationContext reactContext;
    private DeviceEventManagerModule.RCTDeviceEventEmitter eventEmitter;
    private YangYangAPI yyAPI;
    private IYangYangLoginCallback mIYangYangLoginCallback;
    private IYangYangPayCallback mIYangYangPayCallback;
    private static final String EVENT_YANGYANG_LOGIN_REQUEST = "YANGYANG_LOGIN_REQUEST";
    private static final String EVENT_YANGYANG_PAY_REQUEST = "YANGYANG_PLAY_REQUEST";
    private boolean mDebug = false;
    private boolean canShowOpenDialog = true;

    public RNYangYangSdkModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNYangYangSdk";
    }

    @Override
    public void initialize() {
        super.initialize();
        this.eventEmitter = reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class);
    }

    @ReactMethod
    public void getModuleInfo(String moduleName, final Promise promise) {
        createIfNeeded();
        if (checkValid(moduleName, promise)) {
            yyAPI.getModuleInfo(moduleName, new YangYangModuleCallback() {
                @Override
                public void onModuleCallback(YangYangModuleInfo yangYangModuleInfo) {
                    promise.resolve(Utils.fromYangYangModuleInfo(yangYangModuleInfo));
                }
            });
        }
    }

    @ReactMethod
    public void isModulePartialDownloaded(ReadableMap moduleInfo, Promise promise) {
        createIfNeeded();
        YangYangModuleInfo yangModuleInfo = Utils.toYangYangModuleInfo(moduleInfo);
        if (checkValid(yangModuleInfo.packageName, promise)) {
            promise.resolve(yyAPI.isModulePartialDownloaded(yangModuleInfo));
        }
    }

    @ReactMethod
    public void showDownloadDialog(ReadableMap moduleInfo, final Promise promise) {
        createIfNeeded();
        YangYangModuleInfo yangModuleInfo = Utils.toYangYangModuleInfo(moduleInfo);
        if (checkValid(yangModuleInfo.packageName, promise)) {
            yyAPI.showDownloadDialog(yangModuleInfo, new Runnable() {
                @Override
                public void run() {
                    promise.resolve(Arguments.createMap());
                }
            });
        }
    }

    @ReactMethod
    public void showOpenDialog(ReadableMap moduleInfo, final Promise promise) {
        createIfNeeded();
        YangYangModuleInfo yangModuleInfo = Utils.toYangYangModuleInfo(moduleInfo);
        if (checkValid(yangModuleInfo.packageName, promise)) {
            this.showOpenDialog(yangModuleInfo, new OpenAppDialog.OpenAppDialogDelegate() {
                @Override
                public void doCancel() {
                    promise.reject(new Exception("取消"));
                }

                @Override
                public void doConfirm() {
                    promise.resolve(Arguments.createMap());
                }
            });
        }
    }

    @ReactMethod
    public void setDebug(boolean debug) {
        Activity activity = getCurrentActivity();
        if (activity != null && mDebug != debug) {
            mDebug = debug;
            if (yyAPI != null) {
                yyAPI = null;
            }
            yyAPI = YangYangAPIFactory.createYangYangAPI(activity, debug);
            yyAPI.setLoginHandler(this);
            yyAPI.setPayHandler(this);
        }
    }

    @ReactMethod
    public void isModuleInstalling(ReadableMap moduleInfo, Promise promise) {
        createIfNeeded();
        YangYangModuleInfo yangModuleInfo = Utils.toYangYangModuleInfo(moduleInfo);
        if (checkValid(yangModuleInfo.packageName, promise)) {
            promise.resolve(yyAPI.isModuleInstalling(yangModuleInfo));
        }
    }

    @ReactMethod
    public void isModuleInstalled(ReadableMap moduleInfo, Promise promise) {
        createIfNeeded();
        YangYangModuleInfo yangModuleInfo = Utils.toYangYangModuleInfo(moduleInfo);
        if (checkValid(yangModuleInfo.packageName, promise)) {
            promise.resolve(yyAPI.isModuleInstalled(yangModuleInfo));
        }
    }

    @ReactMethod
    public void isModuleDownloaded(ReadableMap moduleInfo, Promise promise) {
        createIfNeeded();
        YangYangModuleInfo yangModuleInfo = Utils.toYangYangModuleInfo(moduleInfo);
        if (checkValid(yangModuleInfo.packageName, promise)) {
            promise.resolve(yyAPI.isModuleDownloaded(yangModuleInfo));
        }
    }

    @ReactMethod
    public void unzipModule(ReadableMap moduleInfo, final Promise promise) {
        createIfNeeded();
        YangYangModuleInfo yangModuleInfo = Utils.toYangYangModuleInfo(moduleInfo);
        if (checkValid(yangModuleInfo.packageName, promise)) {
            yyAPI.unzipModule(yangModuleInfo, new Runnable() {
                @Override
                public void run() {
                    promise.resolve(Arguments.createMap());
                }
            });
        }
    }

    @ReactMethod
    public void downloadModule(ReadableMap moduleInfo, final Promise promise) {
        createIfNeeded();
        YangYangModuleInfo yangModuleInfo = Utils.toYangYangModuleInfo(moduleInfo);
        if (checkValid(yangModuleInfo.packageName, promise)) {
            yyAPI.downloadModule(yangModuleInfo, new Runnable() {
                @Override
                public void run() {
                    promise.resolve(Arguments.createMap());
                }
            });
        }
    }

    @ReactMethod
    public void launchModule(String moduleName, ReadableMap userInfo, Promise promise) {
        createIfNeeded();
        if (checkValid(moduleName, promise)) {
            Activity activity = getCurrentActivity();
            if (activity != null) {
                Intent intent = new Intent(activity, DynamicGdxActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent.putExtra("app_id", moduleName);
                YangYangUserInfo yangUserInfo = userInfo != null ? Utils.toYangYangUserInfo(userInfo) : null;
                if (yangUserInfo != null) {
                    intent.putExtra("user_id", yangUserInfo.userId);
                }
                activity.startActivity(intent);
                promise.resolve(Arguments.createMap());
            } else {
                promise.reject(new Exception("activity is null"));
            }

        }
    }

    @ReactMethod
    public void startModule(final String modulePackageName, final ReadableMap userInfoMap, final Promise promise) {
        createIfNeeded();
        if (!checkValid(modulePackageName, promise)) {
            return;
        }
        yyAPI.getModuleInfo(modulePackageName, new YangYangModuleCallback() {
            public void onModuleCallback(final YangYangModuleInfo moduleInfo) {
                if (moduleInfo.statusCode == 0) {

                    if (yyAPI instanceof DefaultYangYangAPI) {
                        ((DefaultYangYangAPI) yyAPI).showModuleInstallProgress(moduleInfo);
                    }

                    if (yyAPI.isModuleInstalling(moduleInfo)) {
                        promise.reject(new Exception("module is installing"));
                        return;
                    }
                    final YangYangUserInfo userInfo = userInfoMap != null ? Utils.toYangYangUserInfo(userInfoMap) : null;
                    if (yyAPI.isModuleInstalled(moduleInfo)) {
                        startDynamicGdxActivity(modulePackageName, userInfo, promise);
                    } else if (yyAPI.isModuleDownloaded(moduleInfo)) {
                        yyAPI.unzipModule(moduleInfo, new Runnable() {
                            public void run() {
                                if (yyAPI != null && yyAPI instanceof DefaultYangYangAPI) {
                                    ((DefaultYangYangAPI) yyAPI).finishInstallModule(moduleInfo);
                                }
                                showOpenDialog(moduleInfo, new OpenAppDialog.OpenAppDialogDelegate() {
                                    @Override
                                    public void doCancel() {
                                        canShowOpenDialog = true;
                                    }

                                    @Override
                                    public void doConfirm() {
                                        startModule(modulePackageName, userInfoMap, promise);
                                    }
                                });
                            }
                        });
                    } else {
                        yyAPI.showDownloadDialog(moduleInfo, new Runnable() {
                            public void run() {
                                yyAPI.downloadModule(moduleInfo, new Runnable() {
                                    public void run() {
                                        startModule(modulePackageName, userInfoMap, promise);
                                    }
                                });
                            }
                        });
                    }
                } else {
                    Toast.makeText(getReactApplicationContext(), moduleInfo.errorMessage, Toast.LENGTH_LONG).show();
                    promise.reject(new Exception(moduleInfo.errorMessage));
                }

            }
        });
    }

    @ReactMethod
    public void onLoginCallback(ReadableMap params) {
        if (mIYangYangLoginCallback != null) {
            mIYangYangLoginCallback.onLoginCallback(Utils.toYangYangLoginResult(params));
        }
    }

    @ReactMethod
    public void onPayCallback(ReadableMap params) {
        if (mIYangYangPayCallback != null) {
            mIYangYangPayCallback.onPayCallback(Utils.toYangYangPayResult(params));
        }
    }


    @ReactMethod
    public void startNavigationActivity() {
        Activity activity = getCurrentActivity();
        if (activity == null) {
            return;
        }
        try {
            Class<?> clazz = Class.forName("cn.bookln.saas.MainActivity");
            Intent intent = new Intent(activity, clazz);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @ReactMethod
    public void getPurchasedModules(String userId, final Promise promise) {
        createIfNeeded();
        if (yyAPI == null) {
            promise.reject(new Exception("createYangYangAPI failed "));
            return;
        }
        yyAPI.getPurchasedModules(userId, new YangYangPurchaseCallback() {
            @Override
            public void onPurchaseCallback(YangYangPurchaseInfo yangYangPurchaseInfo) {
                promise.resolve(Utils.fromYangYangPurchaseInfo(yangYangPurchaseInfo));
            }
        });
    }

    @ReactMethod
    public void setCanShowOpenDialog(boolean canShowOpenDialog) {
        this.canShowOpenDialog = canShowOpenDialog;
    }

    private boolean checkValid(String moduleName, Promise promise) {
        if (TextUtils.isEmpty(moduleName)) {
            promise.reject(new Exception("moduleName is empty "));
            return false;
        }
        if (yyAPI == null) {
            promise.reject(new Exception("createYangYangAPI failed "));
            return false;
        }
        return true;
    }

    private boolean checkValid(Promise promise) {
        if (yyAPI == null) {
            promise.reject(new Exception("createYangYangAPI failed "));
            return false;
        }
        return true;
    }

    private void createIfNeeded() {
        Activity activity = getCurrentActivity();
        if (yyAPI == null && activity != null) {
            yyAPI = YangYangAPIFactory.createYangYangAPI(activity, mDebug);
            yyAPI.setLoginHandler(this);
            yyAPI.setPayHandler(this);
        }
    }

    @Override
    public void onLoginRequest(IYangYangLoginCallback iYangYangLoginCallback) {
        mIYangYangLoginCallback = iYangYangLoginCallback;
        eventEmitter.emit(EVENT_YANGYANG_LOGIN_REQUEST, Arguments.createMap());
    }

    @Override
    public void onPayRequest(YangYangPayRequest yangYangPayRequest, IYangYangPayCallback iYangYangPayCallback) {
        mIYangYangPayCallback = iYangYangPayCallback;
        WritableMap data = Arguments.createMap();
        data.putString("requestId", yangYangPayRequest.requestId);
        eventEmitter.emit(EVENT_YANGYANG_PAY_REQUEST, data);
    }

    private void startDynamicGdxActivity(String modulePackageName, YangYangUserInfo userInfo, Promise promise) {
        Activity activity = getCurrentActivity();
        if (activity != null) {
            Intent intent = new Intent(activity, DynamicGdxActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            intent.putExtra("app_id", modulePackageName);
            if (userInfo != null) {
                intent.putExtra("user_id", userInfo.userId);
            }
            activity.startActivity(intent);
            this.canShowOpenDialog = false;
            promise.resolve(Arguments.createMap());
        } else {
            promise.reject(new Exception("activity is null"));
        }
    }

    private void showOpenDialog(final YangYangModuleInfo moduleInfo,
                                final com.xuexue.lib.sdk.OpenAppDialog.OpenAppDialogDelegate delegate) {
        if (!this.canShowOpenDialog) {
            showMessageInMainThread("“" + moduleInfo.appName + "”已下载完成");
            return;
        }
        final Activity activity = getCurrentActivity();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!activity.isFinishing()) {
                        try {
                            OpenAppDialog dialog = new OpenAppDialog();
                            dialog.show(activity.getFragmentManager(), moduleInfo.appName, delegate);
                            canShowOpenDialog = false;
                        } catch (Throwable var2) {
                            showMessage("“" + moduleInfo.appName + "”已下载完成");
                        }
                    }
                }
            });
        } else {
            this.canShowOpenDialog = true;
        }
    }

    private void showMessage(String message) {
        if (yyAPI != null && yyAPI instanceof DefaultYangYangAPI) {
            ((DefaultYangYangAPI) yyAPI).showMessage(message);
        }
    }

    private void showMessageInMainThread(final String message) {
        final Activity activity = getCurrentActivity();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showMessage(message);
                }
            });
        }
    }

}
