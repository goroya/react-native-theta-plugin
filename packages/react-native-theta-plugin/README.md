# React Native Theta Plugin

## Description

This is a wrapper for the [theta-plugin-library](https://github.com/ricohapi/theta-plugin-library) for react native.  

# Installation

```bash
$ npm install react-native-theta-plugin --save
$ react-native link react-native-theta-plugin
```
`$ npm install react-native-theta-plugin --save`

## Quick Start

```javascript
import ThetaPlugin from 'react-native-theta-plugin';

(async () => {
    // control LED
    await ThetaPlugin.notificationLed3Show('red');
    await ThetaPlugin.notificationLedShow("LED8");
    await ThetaPlugin.notificationLedBlink("LED7", "red", 1000);
    await ThetaPlugin.notificationLedHide("LED6");
    // control Sound
    await ThetaPlugin.notificationAudioShutter();
    await ThetaPlugin.notificationAudioOpen();
    await ThetaPlugin.notificationAudioClose();
    await ThetaPlugin.notificationAudioMovStart();
    await ThetaPlugin.notificationAudioMovStop();
    await ThetaPlugin.notificationAudioSelf();
    await ThetaPlugin.notificationAudioWarning();
    // control other
    await ThetaPlugin.notificationWlanOff();
    await ThetaPlugin.notificationWlanAp();
    await ThetaPlugin.notificationWlanCl();
    await ThetaPlugin.notificationDatabaseUpdate([]);
    await ThetaPlugin.notificationSuccess();
    await ThetaPlugin.notificationError('Error Hello');
    await ThetaPlugin.notificationErrorOccured();
    await ThetaPlugin.setAutoClose(false);
    await ThetaPlugin.close();
    await ThetaPlugin.notificationCameraOpen();
    await ThetaPlugin.notificationCameraClose();
})();

// Detect Button Push UP&Down
ThetaPlugin.addListener('BUTTON_LONG_PRESS', (e) => {
    console.log("BUTTON_LONG_PRESS", e);
});
ThetaPlugin.addListener('BUTTON_PUSH_UP', (e) => {
    console.log("BUTTON_PUSH_UP", e);
});
ThetaPlugin.addListener('BUTTON_PUSH_DOWN', (e) => {
    console.log("BUTTON_PUSH_DOWN", e);
});

ThetaPlugin.addListener('CAMERA_BUTTON_PUSH_DOWN', () => {
    console.log("CAMERA_BUTTON_PUSH_DOWN");
});
ThetaPlugin.addListener('MEDIA_RECORD_BUTTON_PUSH_DOWN', () => {
    console.log("MEDIA_RECORD_BUTTON_PUSH_DOWN");
});
ThetaPlugin.addListener('WLAN_ON_OFF_BUTTON_PUSH_DOWN', () => {
    console.log("WLAN_ON_OFF_BUTTON_PUSH_DOWN");
});

ThetaPlugin.addListener('CAMERA_BUTTON_PUSH_UP', () => {
    console.log("CAMERA_BUTTON_PUSH_UP");
});
ThetaPlugin.addListener('MEDIA_RECORD_BUTTON_PUSH_UP', () => {
    console.log("MEDIA_RECORD_BUTTON_PUSH_UP");
});
ThetaPlugin.addListener('WLAN_ON_OFF_BUTTON_PUSH_UP', () => {
    console.log("WLAN_ON_OFF_BUTTON_PUSH_UP");
});

ThetaPlugin.addListener('CAMERA_BUTTON_LONG_PRESS', () => {
    console.log("CAMERA_BUTTON_LONG_PRESS");
});
ThetaPlugin.addListener('MEDIA_RECORD_BUTTON_LONG_PRESS', () => {
    console.log("MEDIA_RECORD_BUTTON_LONG_PRESS");
});
ThetaPlugin.addListener('WLAN_ON_OFF_BUTTON_LONG_PRESS', () => {
    console.log("WLAN_ON_OFF_BUTTON_LONG_PRESS");
});
```

## API Reference 

TBD




# react-native-theta-plugin

**## Getting started

`$ npm install react-native-theta-plugin --save`

### Mostly automatic installation

`$ react-native link react-native-theta-plugin`**

### Manual installation


#### Android

1. Open up `android/app/src/main/java/[...]/MainApplication.java`
  - Add `import com.goroya.react.thetaplugin.RNThetaPluginPackage;` to the imports at the top of the file
  - Add `new RNThetaPluginPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-theta-plugin'
  	project(':react-native-theta-plugin').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-theta-plugin/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-theta-plugin')
  	```


## Usage
```javascript
import RNThetaPlugin from 'react-native-theta-plugin';

// TODO: What to do with the module?
RNThetaPlugin;
```
  