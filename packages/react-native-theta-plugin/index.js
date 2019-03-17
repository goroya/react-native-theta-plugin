import { EventEmitter2 } from 'eventemitter2';
import {DeviceEventEmitter, NativeModules} from 'react-native';

const { RNThetaPlugin } = NativeModules;
const eventEmitter = new EventEmitter2();

RNThetaPlugin.addListener = (eventStr, callback) => {
    eventEmitter.on(eventStr, callback);
    return DeviceEventEmitter.addListener(eventStr, (e: Event) => {
        eventEmitter.emit(eventStr, e);
    });
};

RNThetaPlugin.removeListener = (id) => {
    DeviceEventEmitter.removeListener(id);
};

RNThetaPlugin.removeAllListeners = () => {
    DeviceEventEmitter.removeAllListeners();
};

export default RNThetaPlugin;
