import { NativeEventEmitter, NativeModules, Platform } from 'react-native';

const { RNYangYangSdk } = NativeModules;
let EventEmitter;
if (Platform.OS === 'android') {
  EventEmitter = new NativeEventEmitter(RNYangYangSdk);
}

export const YangYangEvents = {
  LOGIN_REQUEST: 'YANGYANG_LOGIN_REQUEST',
  PLAY_REQUEST: 'YANGYANG_PLAY_REQUEST',
};

export type YangYangModuleInfo = {
  statusCode: number,
  errorMessage: string,
  packageName: string,
  version: string,
  size: string,
  zipChecksum: string,
  appChecksum: string,
  appName: string,
};

export type YangYangPurchaseInfo = {
  statusCode: number,
  errorMessage: string,
  modules: Array<string>,
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

const getModuleInfo = (moduleName: string): Promise<YangYangModuleInfo> => {
  return RNYangYangSdk.getModuleInfo(moduleName);
};

const isModulePartialDownloaded = (
  moduleInfo: YangYangModuleInfo
): Promise<boolean> => {
  return RNYangYangSdk.isModulePartialDownloaded(moduleInfo);
};

const showDownloadDialog = (moduleInfo: YangYangModuleInfo): Promise<void> => {
  return RNYangYangSdk.showDownloadDialog(moduleInfo);
};

const showOpenDialog = (moduleInfo: YangYangModuleInfo): Promise<void> => {
  return RNYangYangSdk.showOpenDialog(moduleInfo);
};

/**
 * 模块是否正在安装
 */
const isModuleInstalling = (
  moduleInfo: YangYangModuleInfo
): Promise<boolean> => {
  return RNYangYangSdk.isModuleInstalling(moduleInfo);
};

/**
 * 模块是否安装
 */
const isModuleInstalled = (
  moduleInfo: YangYangModuleInfo
): Promise<boolean> => {
  return RNYangYangSdk.isModuleInstalled(moduleInfo);
};

/**
 * 模块是否已下载
 * @param moduleName
 */
const isModuleDownloaded = (
  moduleInfo: YangYangModuleInfo
): Promise<boolean> => {
  return RNYangYangSdk.isModuleDownloaded(moduleInfo);
};

/**
 *
 * 解压模块
 * @param moduleName
 */
const unzipModule = (moduleInfo: YangYangModuleInfo): Promise<void> => {
  return RNYangYangSdk.unzipModule(moduleInfo);
};

/**
 *  下载模块
 * @param moduleName
 */
const downloadModule = (moduleInfo: YangYangModuleInfo): Promise<void> => {
  return RNYangYangSdk.downloadModule(moduleInfo);
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

const getPurchasedModules = (userId: string): Promise<YangYangPurchaseInfo> => {
  return RNYangYangSdk.getPurchasedModules(userId);
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

/**
 * 进入模块
 * @param moduleName
 */
const startModule = (
    moduleName: string,
    userInfo: ?YangYangUserInfo
): Promise<void> => {
  return RNYangYangSdk.startModule(moduleName, userInfo);
};

const setCanShowOpenDialog = (canShowOpenDialog: boolean) => {
  return RNYangYangSdk.setCanShowOpenDialog(canShowOpenDialog);
};

export default {
  getModuleInfo,
  isModulePartialDownloaded,
  showDownloadDialog,
  showOpenDialog,
  isModuleInstalling,
  isModuleInstalled,
  isModuleDownloaded,
  unzipModule,
  downloadModule,
  launchModule,
  onLoginCallback,
  onPayCallback,
  startNavigationActivity,
  setDebug,
  getPurchasedModules,
  addListener,
  removeListener,
  startModule,
  setCanShowOpenDialog,
};
