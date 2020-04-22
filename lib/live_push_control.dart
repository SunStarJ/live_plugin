import 'package:flutter/services.dart';
import 'package:liveplugin/type.dart';

/**
 *
 * @ProjectName:    live_plugin
 * @ClassName:      LivePushControl
 * @Description:    预览控制器
 * @Author:         孙浩
 * @QQ:             243280864
 * @CreateDate:     2020/4/9 14:01
 */
class LivePushControl {
  MethodChannel _methodChannel =
      MethodChannel("com.sunstar.liveplugin.viewControl");

  ///开始预览
  void startPreview() {
    _methodChannel.invokeMapMethod("preView");
  }
  ///销毁预览
  destroy() async {
    return _methodChannel.invokeMapMethod("destroy");
  }

  ///切换摄像头
  void switchCamera() {
    _methodChannel.invokeMapMethod("switchCamera");
  }

  ///修改滤镜
  void changeFilterType(String name) {
    FilterType type;
    switch (name) {
      case "无":
        type = FilterType.none;
        break;
      case "干净":
        type = FilterType.clean;
        break;
      case "童话":
        type = FilterType.fairytale;
        break;
      case "自然":
        type = FilterType.nature;
        break;
      case "健康":
        type = FilterType.healthy;
        break;
      case "温柔":
        type = FilterType.tender;
        break;
      case "美白":
        type = FilterType.whiten;
        break;
    }
    _methodChannel
        .invokeMapMethod("changeFilter", {"filterType": type.toString()});
  }

  ///修改滤镜等级
  void changeFilterLevel(double value) {
    _methodChannel.invokeMapMethod("changeFilterLevel", {"level": value});
  }

  ///修改美颜等级
  void changeBeauty(double value) {
    _methodChannel.invokeMapMethod("changeBeautyLevel", {"level": value});
  }

  ///添加水印月野兔样式
  void addWaterMarker() {
    _methodChannel.invokeMapMethod("addWaterMarker", {"x": 100, "y": 30});
  }

  ///开启背景音乐
  void startMusicPlay(String urlPath, bool isLoop) {
    _methodChannel.invokeMapMethod(
        "startPlayMusic", {"urlPath": urlPath, "isLoop": isLoop});
  }

}
