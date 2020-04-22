package com.sunstar.liveplugin.view.live

import com.sunstar.liveplugin.view.live.VideoViewFactory
import io.flutter.plugin.common.PluginRegistry

class VideoViewPlugin {
    companion object {
        @JvmStatic
        fun registerWith(registrar: PluginRegistry.Registrar) {
            registrar.platformViewRegistry().registerViewFactory("com.sunstar.liveplugin.videoView", VideoViewFactory(registrar))
        }
    }
}