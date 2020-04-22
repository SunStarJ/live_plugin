package com.sunstar.liveplugin.view.live

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.netease.LSMediaCapture.lsMediaCapture
import com.netease.vcloud.video.effect.VideoEffect
import com.netease.vcloud.video.render.NeteaseView
import com.sunstar.liveplugin.util.LiveInstance
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.PluginRegistry
import io.flutter.plugin.platform.PlatformView

class VideoView(private val registrar: PluginRegistry.Registrar, id: Int) : PlatformView, MethodChannel.MethodCallHandler {
    var videoView: NeteaseView? = null
    private val channel: MethodChannel

    init {
        addLifeCycle()
        channel = MethodChannel(registrar.messenger(), "com.sunstar.liveplugin.viewControl")
        channel.setMethodCallHandler(this)
        LiveInstance.Instance.initLiveConfig(registrar.activity().application.applicationContext)
    }

    private fun addLifeCycle() {
        registrar.activity().application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityPaused(p0: Activity?) {

            }

            override fun onActivityResumed(p0: Activity?) {
            }

            override fun onActivityStarted(p0: Activity?) {
            }

            override fun onActivityDestroyed(p0: Activity?) {
                LiveInstance.Instance.onDestroy()
            }

            override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {
            }

            override fun onActivityStopped(p0: Activity?) {
            }

            override fun onActivityCreated(p0: Activity?, p1: Bundle?) {
            }
        })
    }

    override fun getView(): View = initVideoView()

    private fun initVideoView(): View {
        if (videoView == null) {
            videoView = NeteaseView(registrar.activity())
        }
        return videoView!!
    }

    override fun dispose() {

    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            "preView" -> {
                registrar.activity().window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
                videoView?.run {
                    LiveInstance.Instance.initVideoStreamingPara(lsMediaCapture.StreamType.VIDEO).videoPreview(this)
                }
            }
            "destroy" -> {
                LiveInstance.Instance.onDestroy()
                registrar.activity().window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
            }
            "switchCamera" -> {
                LiveInstance.Instance.switchCamera()
            }
            "changeFilter" -> {
                var type: VideoEffect.FilterType? = null
                when (call.argument<String>("filterType")!!.toString()) {
                    "FilterType.none" -> type = VideoEffect.FilterType.none
                    "FilterType.clean" -> type = VideoEffect.FilterType.clean
                    "FilterType.fairytale" -> type = VideoEffect.FilterType.fairytale
                    "FilterType.nature" -> type = VideoEffect.FilterType.nature
                    "FilterType.healthy" -> type = VideoEffect.FilterType.healthy
                    "FilterType.tender" -> type = VideoEffect.FilterType.tender
                    "FilterType.whiten" -> type = VideoEffect.FilterType.whiten
                }
                type?.run {
                    LiveInstance.Instance.changeFilter(this)
                }
            }
            "changeFilterLevel" -> {
                LiveInstance.Instance.changeFilterLevel(call.argument<Double>("level")!!)
            }
            "changeBeautyLevel" -> {
                LiveInstance.Instance.changeBeautyLevel(call.argument<Double>("level")!!)
            }
            "addWaterMarker" -> {
                LiveInstance.Instance.addWorkMarker(registrar.context(), call.argument<Int>("x")!!, call.argument<Int>("y")!!)
            }
            "startPush" -> {
                LiveInstance.Instance.startPush(call.argument<String>("pushUrl")!!)
            }
            "changeScreenOrientation"->{
                LiveInstance.Instance.changeScreenConfig()
            }
            "startPlayMusic"->{
                LiveInstance.Instance.startPlayMusic(call.argument<String>("urlPath")!!,call.argument<Boolean>("isLoop")!!)
            }
        }
    }
}