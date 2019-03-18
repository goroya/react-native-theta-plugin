package com.goroya.react.thetaplugin

import com.facebook.react.bridge.*
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.facebook.react.bridge.WritableMap
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactMethod
import com.theta360.pluginlibrary.values.ExitStatus
import com.theta360.pluginlibrary.activity.Constants
import android.content.Intent
import android.os.Build
import android.view.KeyEvent
import com.theta360.pluginlibrary.values.LedTarget
import com.theta360.pluginlibrary.values.LedColor
import com.theta360.pluginlibrary.callback.KeyCallback
import com.theta360.pluginlibrary.receiver.KeyReceiver
import android.content.IntentFilter
import com.theta360.pluginlibrary.UncaughtException
import android.content.pm.ActivityInfo

class RNThetaPluginModule(private val reactContext: ReactApplicationContext)
    : ReactContextBaseJavaModule(reactContext), LifecycleEventListener {

    companion object {
        @JvmField
        val TAG: String = RNThetaPluginModule::class.java.simpleName
    }
    private var startupFlag = false
    private var isCamera = false
    private var isAutoClose = true
    private var isClosed = false
    private var mUserOption: String? = null
    private var isApConnected = false

    private var mKeyCallback: KeyCallback? = null
    private var mKeyReceiver: KeyReceiver? = null

    private val onKeyReceiver = object : KeyReceiver.Callback {
        override fun onKeyDownCallback(keyCode: Int, event: KeyEvent?) {
            event?.also {
                if (event.keyCode == KeyReceiver.KEYCODE_MEDIA_RECORD && event.isLongPress) {
                    mKeyCallback?.onKeyLongPress(keyCode, event)
                    if (isAutoClose) {
                        close()
                    }
                } else {
                    mKeyCallback?.also {
                        if (event.repeatCount == 0) {
                            mKeyCallback?.onKeyDown(keyCode, event)
                        } else if (event.isLongPress) {
                            mKeyCallback?.onKeyLongPress(keyCode, event)
                        }
                    }
                }
            }
        }

        override fun onKeyUpCallback(keyCode: Int, event: KeyEvent?) {
            mKeyCallback?.onKeyUp(keyCode, event)
        }
    }

    init {
        reactContext.addLifecycleEventListener(this)
    }

    override fun getConstants(): MutableMap<String, Any> {
        val constants = HashMap<String, Any>()
        constants["DURATION_SHORT_KEY"] = "hogehoge"
        return constants
    }

    private fun sendEvent(reactContext: ReactContext,
                          eventName: String,
                          params: WritableMap?) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
                .emit(eventName, params)
    }

    private fun myFinishAndRemoveTask(){
        if (Build.VERSION.SDK_INT >= 21) {
            currentActivity?.finishAndRemoveTask()
        } else {
            currentActivity?.finish()
        }
    }

    override fun getName(): String {
        return "RNThetaPlugin"
    }

    private fun keyCodeToStr(keyCode: Int): String{
        var keyName = "UNKNOWN_KEY"
        when (keyCode) {
            KeyReceiver.KEYCODE_CAMERA -> {
                keyName = "CAMERA"
            }
            KeyReceiver.KEYCODE_MEDIA_RECORD -> {
                keyName = "MEDIA_RECORD"
            }
            KeyReceiver.KEYCODE_WLAN_ON_OFF -> {
                keyName = "WLAN_ON_OFF"
            }
        }
        return keyName
    }
    override fun onHostResume() {
        if(!startupFlag){
            startupFlag = true
            // Fix to be portrait
            currentActivity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            val uncaughtException = UncaughtException(reactApplicationContext){
                notificationError(it)
            }
            Thread.setDefaultUncaughtExceptionHandler(uncaughtException)

            setKeyCallback(object:KeyCallback {
                override fun onKeyDown(keyCode: Int, event: KeyEvent?) {
                    val keyName = keyCodeToStr(keyCode)
                    when (keyCode) {
                        KeyReceiver.KEYCODE_CAMERA -> {
                            sendEvent(reactContext, "CAMERA_BUTTON_PUSH_DOWN", null)
                        }
                        KeyReceiver.KEYCODE_MEDIA_RECORD -> {
                            sendEvent(reactContext, "MEDIA_RECORD_BUTTON_PUSH_DOWN", null)
                        }
                        KeyReceiver.KEYCODE_WLAN_ON_OFF -> {
                            sendEvent(reactContext, "WLAN_ON_OFF_BUTTON_PUSH_DOWN", null)
                        }
                    }
                    val data = Arguments.createMap()
                    data.putString("keyName", keyName)
                    data.putInt("keyCode", keyCode)
                    sendEvent(reactContext, "BUTTON_PUSH_DOWN", data)
                }

                override fun onKeyUp(keyCode: Int, event: KeyEvent?) {
                    val keyName = keyCodeToStr(keyCode)
                    when (keyCode) {
                        KeyReceiver.KEYCODE_CAMERA -> {
                            sendEvent(reactContext, "CAMERA_BUTTON_PUSH_UP", null)
                        }
                        KeyReceiver.KEYCODE_MEDIA_RECORD -> {
                            sendEvent(reactContext, "MEDIA_RECORD_BUTTON_PUSH_UP", null)
                        }
                        KeyReceiver.KEYCODE_WLAN_ON_OFF -> {
                            sendEvent(reactContext, "WLAN_ON_OFF_BUTTON_PUSH_UP", null)
                        }
                    }
                    val data = Arguments.createMap()
                    data.putString("keyName", keyName)
                    data.putInt("keyCode", keyCode)
                    sendEvent(reactContext, "BUTTON_PUSH_UP", data)
                }

                override fun onKeyLongPress(keyCode: Int, event: KeyEvent?) {
                    val keyName = keyCodeToStr(keyCode)
                    when (keyCode) {
                        KeyReceiver.KEYCODE_CAMERA -> {
                            sendEvent(reactContext, "CAMERA_BUTTON_LONG_PRESS", null)
                        }
                        KeyReceiver.KEYCODE_MEDIA_RECORD -> {
                            sendEvent(reactContext, "MEDIA_RECORD_BUTTON_LONG_PRESS", null)
                        }
                        KeyReceiver.KEYCODE_WLAN_ON_OFF -> {
                            sendEvent(reactContext, "WLAN_ON_OFF_BUTTON_LONG_PRESS", null)
                        }
                    }
                    val data = Arguments.createMap()
                    data.putString("keyName", keyName)
                    data.putInt("keyCode", keyCode)
                    sendEvent(reactContext, "BUTTON_LONG_PRESS", data)
                }
            })

        }
        val intent = currentActivity?.intent
        intent?.let {
            mUserOption = intent.getStringExtra(Constants.USER_OPTION)
            isApConnected = intent.getBooleanExtra(Constants.IS_AP_CONNECTED, false)
        }

        mKeyReceiver = KeyReceiver(onKeyReceiver)
        val keyFilter = IntentFilter()
        keyFilter.addAction(KeyReceiver.ACTION_KEY_DOWN)
        keyFilter.addAction(KeyReceiver.ACTION_KEY_UP)
        reactContext.registerReceiver(mKeyReceiver, keyFilter)
    }

    override fun onHostPause() {
        if (!isClosed && isAutoClose) {
            close()
        }
        reactContext.unregisterReceiver(mKeyReceiver);
    }

    override fun onHostDestroy() {
    }

    fun setKeyCallback(keyCallback: KeyCallback) {
        mKeyCallback = keyCallback
    }
    /**
     * Auto close setting
     *
     * @param autoClose true : auto close / false : not auto close
     */
    @ReactMethod
    fun setAutoClose(autoClose: Boolean, promise: Promise) {
        isAutoClose = autoClose
        promise.resolve()
    }

    @ReactMethod
    fun close(promise: Promise? = null) {
        isClosed = true
        if (isCamera) {
            notificationCameraOpen()
        }
        notificationSuccess()

        promise?.resolve(null)
    }

    @ReactMethod
    fun getUserOption(promise: Promise) {
        promise.resolve(mUserOption)
    }

    @ReactMethod
    fun isApConnected(promise: Promise) {
        promise.resolve(isApConnected)
    }

    @ReactMethod
    fun notificationCameraOpen(promise: Promise? = null) {
        isCamera = false
        reactContext.sendBroadcast(Intent(Constants.ACTION_MAIN_CAMERA_OPEN))

        promise?.resolve(null)
    }

    @ReactMethod
    fun notificationCameraClose(promise: Promise) {
        isCamera = true
        reactContext.sendBroadcast(Intent(Constants.ACTION_MAIN_CAMERA_CLOSE))

        promise.resolve(null)
    }

    /**
     * Sound of normal capture
     */
    @ReactMethod
    fun notificationAudioShutter(promise: Promise) {
        reactContext.sendBroadcast(Intent(Constants.ACTION_AUDIO_SHUTTER))

        promise.resolve(null)
    }

    /**
     * Sound of starting long exposure capture
     */
    @ReactMethod
    fun notificationAudioOpen(promise: Promise) {
        reactContext.sendBroadcast(Intent(Constants.ACTION_AUDIO_SH_OPEN))

        promise.resolve(null)
    }

    /**
     * Sound of ending long exposure capture
     */
    @ReactMethod
    fun notificationAudioClose(promise: Promise) {
        reactContext.sendBroadcast(Intent(Constants.ACTION_AUDIO_SH_CLOSE))

        promise.resolve(null)
    }

    /**
     * Sound of starting movie recording
     */
    @ReactMethod
    fun notificationAudioMovStart(promise: Promise) {
        reactContext.sendBroadcast(Intent(Constants.ACTION_AUDIO_MOVSTART))
        promise.resolve(null)
    }

    /**
     * Sound of stopping movie recording
     */
    @ReactMethod
    fun notificationAudioMovStop(promise: Promise) {
        reactContext.sendBroadcast(Intent(Constants.ACTION_AUDIO_MOVSTOP))
        promise.resolve(null)
    }

    /**
     * Sound of working self-timer
     */
    @ReactMethod
    fun notificationAudioSelf(promise: Promise) {
        reactContext.sendBroadcast(Intent(Constants.ACTION_AUDIO_SELF))
        promise.resolve(null)
    }

    /**
     * Sound of warning
     */
    @ReactMethod
    fun notificationAudioWarning(promise: Promise) {
        reactContext.sendBroadcast(Intent(Constants.ACTION_AUDIO_WARNING))
        promise.resolve(null)
    }

    /**
     * Turn on LED3 with color
     *
     */
    @ReactMethod
    fun notificationLed3Show(ledColorStr: String, promise: Promise) {
        val ledColor = LedColor.getValue(ledColorStr)

        val intent = Intent(Constants.ACTION_LED_SHOW)
        intent.putExtra(Constants.TARGET, LedTarget.LED3.toString())
        intent.putExtra(Constants.COLOR, ledColor.toString())
        reactContext.sendBroadcast(intent)

        promise.resolve(null)
    }

    private fun notificationLed3Show(ledColor: LedColor, promise: Promise? = null) {
        val intent = Intent(Constants.ACTION_LED_SHOW)
        intent.putExtra(Constants.TARGET, LedTarget.LED3.toString())
        intent.putExtra(Constants.COLOR, ledColor.toString())
        reactContext.sendBroadcast(intent)

        promise?.resolve(null)
    }

    /**
     * Turn on LED
     *
     * @param ledTarget target LED
     */
    @ReactMethod
    fun notificationLedShow(ledTargetStr: String, promise: Promise) {
        val ledTarget = LedTarget.getValue(ledTargetStr)

        if (ledTarget == LedTarget.LED3) {
            notificationLed3Show(LedColor.BLUE)
        } else {
            val intent = Intent(Constants.ACTION_LED_SHOW)
            intent.putExtra(Constants.TARGET, ledTarget.toString())
            reactContext.sendBroadcast(intent)
        }

        promise.resolve(null)
    }

    /**
     * Blink LED
     *
     * @param ledTarget target LED
     * @param ledColor color
     * @param period period 250-2000 (msec)
     */
    @ReactMethod
    fun notificationLedBlink(ledTargetStr: String, ledColorStr: String, period: Int, promise: Promise) {
        val ledTarget = LedTarget.getValue(ledTargetStr)
        var ledColor = LedColor.getValue(ledColorStr)

        if (ledColor == null) {
            ledColor = LedColor.BLUE
        }
        if (period < 250) {
            period = 250
        }
        if (period > 2000) {
            period = 2000
        }

        val intent = Intent(Constants.ACTION_LED_BLINK)
        intent.putExtra(Constants.TARGET, ledTarget.toString())
        intent.putExtra(Constants.COLOR, ledColor.toString())
        intent.putExtra(Constants.PERIOD, period)
        reactContext.sendBroadcast(intent)

        promise.resolve(null)
    }

    /**
     * Turn off LED
     *
     * @param ledTarget target LED
     */
    @ReactMethod
    fun notificationLedHide(ledTargetStr: String, promise: Promise) {
        val ledTarget = LedTarget.getValue(ledTargetStr)

        val intent = Intent(Constants.ACTION_LED_HIDE)
        intent.putExtra(Constants.TARGET, ledTarget.toString())
        reactContext.sendBroadcast(intent)

        promise.resolve(null)
    }

    @ReactMethod
    fun notificationWlanOff(promise: Promise) {
        reactContext.sendBroadcast(Intent(Constants.ACTION_WLAN_OFF))
        promise.resolve(null)
    }

    @ReactMethod
    fun notificationWlanAp(promise: Promise) {
        reactContext.sendBroadcast(Intent(Constants.ACTION_WLAN_AP))
        promise.resolve(null)
    }

    @ReactMethod
    fun notificationWlanCl(promise: Promise) {
        reactContext.sendBroadcast(Intent(Constants.ACTION_WLAN_CL))
        promise.resolve(null)
    }


    @ReactMethod
    fun notificationDatabaseUpdate(targetsJs: ReadableArray, promise: Promise) {
        val targets = arrayListOf<String?>()
        var i = 0
        while (i < targetsJs.size()){
            targets.add(targetsJs.getString(i))
            i++
        }

        val intent = Intent(Constants.ACTION_DATABASE_UPDATE)
        intent.putExtra(Constants.TARGETS, targets)
        reactContext.sendBroadcast(intent)

        promise.resolve(null)
    }

    /**
     * Notifying Completion of Plug-in when the plug-in ends normally
     */
    @ReactMethod
    fun notificationSuccess(promise: Promise? = null) {
        val intent = Intent(Constants.ACTION_FINISH_PLUGIN)
        intent.putExtra(Constants.PACKAGE_NAME, reactContext.packageName)
        intent.putExtra(Constants.EXIT_STATUS, ExitStatus.SUCCESS.toString())
        reactContext.sendBroadcast(intent)

        myFinishAndRemoveTask()

        promise?.resolve(null)
    }

    /**
     * Notifying Completion of Plug-in when the plug-in ends with error
     *
     * @param message error message
     */
    @ReactMethod
    fun notificationError(message: String, promise: Promise? = null) {
        val intent = Intent(Constants.ACTION_FINISH_PLUGIN)
        intent.putExtra(Constants.PACKAGE_NAME, reactContext.packageName)
        intent.putExtra(Constants.EXIT_STATUS, ExitStatus.FAILURE.toString())
        intent.putExtra(Constants.MESSAGE, message)
        reactContext.sendBroadcast(intent)

        myFinishAndRemoveTask()

        promise?.resolve(null)
    }

    /**
     * Notifying Occurrences of Errors
     */
    @ReactMethod
    fun notificationErrorOccured(promise: Promise) {
        reactContext.sendBroadcast(Intent(Constants.ACTION_ERROR_OCCURED))

        promise.resolve(null)
    }
}