package com.sunstar.liveplugin.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import com.netease.LSMediaCapture.lsLogUtil
import com.netease.LSMediaCapture.lsMediaCapture
import com.netease.LSMediaCapture.lsMessageHandler
import com.netease.vcloud.video.effect.VideoEffect
import com.netease.vcloud.video.render.NeteaseView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class LiveInstance private constructor() {
    private var lsMediaCapturePara: lsMediaCapture.LsMediaCapturePara? = null
    private var mediaCapture: lsMediaCapture? = null
    private var mLiveStreamingPara: lsMediaCapture.LiveStreamingPara? = null
    private var waterMarkerBitmap: Bitmap? = null

    companion object {
        val Instance: LiveInstance by lazy {
            LiveInstance()
        }
    }

    init {
        lsMediaCapturePara = lsMediaCapture.LsMediaCapturePara()
    }

    fun initLiveConfig(ctx: Context) {
        lsMediaCapturePara?.run {
            setContext(ctx)
            setMessageHandler { p0, p1 -> }
            setLogLevel(lsLogUtil.LogLevel.INFO)
            setUploadLog(false)
            mediaCapture = lsMediaCapture(this)
            mediaCapture?.setSourceType(lsMediaCapture.SourceType.CustomAV)
        }
        mLiveStreamingPara = lsMediaCapture.LiveStreamingPara()
    }

    fun initVideoStreamingPara(streamType: lsMediaCapture.StreamType): LiveInstance {
        mLiveStreamingPara?.run {
            setStreamType(streamType)
            setFormatType(lsMediaCapture.FormatType.RTMP_AND_MP4)
            isQosOn = true
            setQosEncodeMode(2)
        }
        return this
    }

    fun videoPreview(view: NeteaseView): LiveInstance {
        mediaCapture?.startVideoPreview(view, true, true, lsMediaCapture.VideoQuality.SUPER_HIGH, true)
        return this
    }

    fun onDestroy() {
        mediaCapture?.run {
            stopVideoPreview()
            destroyVideoPreview()
        }
        waterMarkerBitmap?.recycle()
        System.gc()
        waterMarkerBitmap = null
    }

    fun setBeauty() {
        mediaCapture?.run {
            setBeautyLevel(5)
            setFilterStrength(0.5f)
            setFilterType(VideoEffect.FilterType.clean)
        }
    }

    fun changeFilter(type: VideoEffect.FilterType) {
        mediaCapture?.setFilterType(type)
    }

    ///修改摄像头方向
    fun switchCamera() {
        mediaCapture?.switchCamera()
    }

    fun changeFilterLevel(value: Double) {
        mediaCapture?.setFilterStrength(value.toFloat())
    }

    fun changeBeautyLevel(argument: Double) {
        mediaCapture?.setBeautyLevel(argument.toInt())
    }

    fun addWorkMarker(ctx: Context, x: Int, y: Int) {
        Observable.just(1).map {
            var input = ctx.assets.open("logo.png")
            waterMarkerBitmap = BitmapFactory.decodeStream(input)
            waterMarkerBitmap
        }.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe {
            waterMarkerBitmap.run {
                mediaCapture?.setWaterMarkPara(this, VideoEffect.Rect.rightTop, x, y)
                mediaCapture?.setWaterPreview(true)
            }

        }

    }

    fun startPlayMusic(urlPath:String,isLoop:Boolean){
        mediaCapture?.startPlayMusic(urlPath,isLoop)
    }

    fun changeScreenConfig() {
        mediaCapture?.onConfigurationChanged()
    }

    fun startPush(pushPath: String) {
        mediaCapture?.run {
            initLiveStream(mLiveStreamingPara, pushPath)
            startLiveStreaming()
        }
    }

    fun stopLiveStreaming(){
        mediaCapture?.stopLiveStreaming()
    }

    fun pauseVideoLiveStream(){
        mediaCapture?.pauseVideoLiveStream()
    }

    fun resumeVideoLiveStream(){
        mediaCapture?.resumeVideoEncode()
    }

}