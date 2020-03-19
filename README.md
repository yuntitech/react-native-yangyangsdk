
# react-native-yangyangsdk

## Getting started

`$ npm install react-native-yangyangsdk --save`

### Mostly automatic installation

`$ react-native link react-native-yangyangsdk`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-yangyangsdk` and add `RNYangyangsdk.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNYangyangsdk.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.xuexue.lib.sdk.RNYangyangsdkPackage;` to the imports at the top of the file
  - Add `new RNYangYangSdkPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-yangyangsdk'
  	project(':react-native-yangyangsdk').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-yangyangsdk/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-yangyangsdk')
  	```


## Usage
```javascript
import RNYangYangSdk from 'react-native-yangyangsdk';

// TODO: What to do with the module?
RNYangYangSdk;
```
  
