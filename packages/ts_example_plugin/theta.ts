import { EventEmitter2 } from 'eventemitter2';
import {DeviceEventEmitter, NativeModules} from 'react-native';

const { RNThetaPlugin } = NativeModules;
const eventEmitter = new EventEmitter2();

RNThetaPlugin.addListener = (eventStr: string, callback: any) => {
    eventEmitter.on(eventStr, callback);
    return DeviceEventEmitter.addListener(eventStr, (e: Event) => {
        eventEmitter.emit(eventStr, e);
    });
};

RNThetaPlugin.removeAllListeners = () => {
    DeviceEventEmitter.removeAllListeners();
};

export default RNThetaPlugin;
