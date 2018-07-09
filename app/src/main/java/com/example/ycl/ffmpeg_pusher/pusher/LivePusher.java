package com.example.ycl.ffmpeg_pusher.pusher;

import android.hardware.Camera;
import android.view.SurfaceHolder;

import com.example.ycl.ffmpeg_pusher.params.AudioParam;
import com.example.ycl.ffmpeg_pusher.params.VideoParam;
import com.example.ycl.ffmpeg_pusher.utils.PushNative;


/**
 * @Author: Ycl
 * @Date: 2018/7/9 11:30
 * @Desc:
 */
public class LivePusher {
    private SurfaceHolder surfaceHolder;
    private VideoPusher videoPusher;
    private AudioPusher audioPusher;
    private PushNative pushNative;

    public LivePusher(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        prepare();
    }

    private void prepare() {
        pushNative = new PushNative();

        //实例化视频推流器
        VideoParam videoParam = new VideoParam(480, 320, Camera.CameraInfo.CAMERA_FACING_BACK);
        videoPusher = new VideoPusher(surfaceHolder, videoParam, pushNative,this);

        //实例化音频推流器
        AudioParam audioParam = new AudioParam();
        audioPusher = new AudioPusher(audioParam, pushNative);
    }

    // 仅仅只是控制数据是否传递到底层
    public void startPush(String url) {
        videoPusher.startPush();
        audioPusher.startPush();
        pushNative.startPush(url);
    }


    public void stopPush() {
        videoPusher.stopPush();
        audioPusher.stopPush();
        pushNative.stopPush();
    }

    public void release() {
        videoPusher.release();
        audioPusher.release();
        pushNative.release();
    }


    public void switchCamera() {
        videoPusher.switchCamera();
    }
}
