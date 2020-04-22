import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:liveplugin/live_push_view.dart';
import 'package:liveplugin/live_push_control.dart';

/**
 *
 * @ProjectName:    live_plugin
 * @ClassName:      video_view_page
 * @Description:    dart类作用描述
 * @Author:         孙浩
 * @QQ:             243280864
 * @CreateDate:     2020/4/9 14:33
 */

class VideoViewPage extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return _VideoViewPageState();
  }
}

class _VideoViewPageState extends State<VideoViewPage> {
  LivePushControl _control = LivePushControl();
  double strengthValue = 0;
  double beautyValue = 0;
  bool showWaterMarker = false;
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(),
      endDrawer: Container(
        width: 300,
        child: Column(
          children: <Widget>[
            Container(
              height: kToolbarHeight,
            ),
            Wrap(
              children: ["无", "干净", "童话", "自然", "健康", "温柔", "美白"]
                  .map((name) => MaterialButton(
                        onPressed: () {
                          _control.changeFilterType(name);
                        },
                        child: Text(name),
                        textColor: Colors.white,
                      ))
                  .toList(),
            ),
            SizedBox(
              height: 20,
            ),
            Text(
              "滤镜强度",
              style: TextStyle(color: Colors.white),
            ),
            SizedBox(
              height: 10,
            ),
            Slider(
              value: strengthValue,
              onChanged: (value) {
                setState(() {
                  _control.changeFilterLevel(value);
                  strengthValue = value;
                });

              },
              min: 0,
              max: 1,
            ),
            SizedBox(
              height: 20,
            ),
            Text(
              "磨皮强度",
              style: TextStyle(color: Colors.white),
            ),
            SizedBox(
              height: 10,
            ),
            Slider(
              value: beautyValue,
              onChanged: (value) {
                setState(() {
                  _control.changeFilterLevel(value);
                  beautyValue = value;
                });
              },
              divisions: 5,
              min: 0,
              max: 5,
            ),
            SizedBox(
              height: 20,
            ),
            Text(
              "显示水印",
              style: TextStyle(color: Colors.white),
            ),
            SizedBox(
              height: 10,
            ),
            CupertinoSwitch(value: showWaterMarker, onChanged: (value){
              if(showWaterMarker == false){
                showWaterMarker = value;
                _control.addWaterMarker();
              }
            })
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          _control.startPreview();
        },
        child: Icon(
          Icons.play_arrow,
          color: Colors.white,
        ),
      ),
      body: Stack(
        children: <Widget>[
          LivePushView(
            livePushControl: _control,
          ),
          GestureDetector(
            behavior: HitTestBehavior.translucent,
            child: Container(
              height: double.infinity,
              width: double.infinity,
            ),
            onDoubleTap: () {
              _control.switchCamera();
            },
          )
        ],
      ),
    );
  }

  @override
  void dispose() {
    _control.destroy();
    super.dispose();
  }
}
