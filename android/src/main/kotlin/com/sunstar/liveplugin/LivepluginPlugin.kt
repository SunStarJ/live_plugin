package com.sunstar.liveplugin

import androidx.annotation.NonNull
import com.sunstar.liveplugin.view.live.VideoViewPlugin
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.shim.ShimPluginRegistry
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar

/** LivepluginPlugin */
public class LivepluginPlugin: FlutterPlugin, MethodCallHandler {
  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    val channel = MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "liveplugin")
    channel.setMethodCallHandler(LivepluginPlugin());
    var shimRegistry = ShimPluginRegistry(flutterPluginBinding.flutterEngine)
    var key = LivepluginPlugin::class.java.canonicalName
    if(shimRegistry.hasPlugin(key)) return
    var registrar = shimRegistry.registrarFor(key)
    VideoViewPlugin.registerWith(registrar)
  }

  companion object {
    @JvmStatic
    fun registerWith(registrar: Registrar) {
      VideoViewPlugin.registerWith(registrar)
      val channel = MethodChannel(registrar.messenger(), "liveplugin")
      channel.setMethodCallHandler(LivepluginPlugin())
    }
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    if (call.method == "getPlatformVersion") {
      result.success("Android ${android.os.Build.VERSION.RELEASE}")
    } else {
      result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
  }
}
