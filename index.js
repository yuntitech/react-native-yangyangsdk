import { NativeEventEmitter, NativeModules } from 'react-native';

const { RNYangYangSdk } = NativeModules;
const EventEmitter = new NativeEventEmitter(RNYangYangSdk);

export const YangYangEvents = {
  LOGIN_REQUEST: 'YANGYANG_LOGIN_REQUEST',
  PLAY_REQUEST: 'YANGYANG_PLAY_REQUEST',
};

export type YangYangUserInfo = {
  userId: ?string,
  accountId: ?string,
  accountType: ?string,
};

export type YangYangLoginResult = {
  statusCode: number,
  errorMessage: string,
  userId: string,
  accountType: number,
  accountId: number,
};

export type YangYangPayResult = {
  statusCode: number,
  errorMessage: string,
  requestId: string,
  time: number,
};

/**
 * 模块是否安装
 */
const isModuleInstalled = (moduleName: string): Promise<boolean> => {
  return RNYangYangSdk.isModuleInstalled(moduleName);
};

/**
 * 模块是否已下载
 * @param moduleName
 */
const isModuleDownloaded = (moduleName: string): Promise<boolean> => {
  return RNYangYangSdk.isModuleDownloaded(moduleName);
};

/**
 *
 * 解压模块
 * @param moduleName
 */
const unzipModule = (moduleName: string): Promise<void> => {
  return RNYangYangSdk.unzipModule(moduleName);
};

/**
 *  下载模块
 * @param moduleName
 */
const downloadModule = (moduleName: string): Promise<void> => {
  return RNYangYangSdk.downloadModule(moduleName);
};

/**
 * 启动模块
 * @param moduleName
 */
const launchModule = (
  moduleName: string,
  userInfo: ?YangYangUserInfo
): Promise<void> => {
  return RNYangYangSdk.launchModule(moduleName, userInfo);
};

/**
 * 书链登录后回调yangyangsdk
 * @param loginResult
 */
const onLoginCallback = (loginResult: YangYangLoginResult) => {
  RNYangYangSdk.onLoginCallback(loginResult);
};

/**
 *  书链支付后回调yangyangsdk
 * @param payResult
 */
const onPayCallback = (payResult: YangYangPayResult) => {
  RNYangYangSdk.onPayCallback(payResult);
};

const startNavigationActivity = () => {
  RNYangYangSdk.startNavigationActivity();
};

const setDebug = (debug: boolean) => {
  RNYangYangSdk.setDebug(debug);
};

const addListener = (
  eventName: string,
  callback: (data: { requestId?: string }) => void
) => {
  return EventEmitter.addListener(eventName, callback);
};

const removeListener = (
  eventName: string,
  callback: (data: { requestId?: string }) => void
) => {
  EventEmitter.removeListener(eventName, callback);
};

export default {
  isModuleInstalled,
  isModuleDownloaded,
  unzipModule,
  downloadModule,
  launchModule,
  onLoginCallback,
  onPayCallback,
  startNavigationActivity,
  setDebug,
  addListener,
  removeListener,
};
