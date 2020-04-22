import 'dart:io';

import 'package:flutter/cupertino.dart';
import 'package:liveplugin/live_push_control.dart';

/**
 *
 * @ProjectName:    live_plugin
 * @ClassName:      LivePushView
 * @Description:    预览组件
 * @Author:         孙浩
 * @QQ:             243280864
 * @CreateDate:     2020/4/9 14:01
 */

class LivePushView extends StatefulWidget{
  LivePushControl livePushControl;

  LivePushView({@required this.livePushControl});

  @override
  State<StatefulWidget> createState() {
    return _LivePushViewState();
  }

}

class _LivePushViewState extends State<LivePushView>{
  @override
  Widget build(BuildContext context) {
    return initVideoView();
  }

  initVideoView()=>Platform.isAndroid?AndroidView(viewType: "com.sunstar.liveplugin.videoView"):Container();
}