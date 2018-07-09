package com.example.ycl.ffmpeg_pusher.utils;

/**
 * @Author: Ycl
 * @Date: 2018/7/9 13:27
 * @Desc:
 */
public class PushNative {

    public native void startPush(String url);

    public native void stopPush();

    public native void release();

    // 设置参数
    public native void setVideoOptions(int width, int height, int bitrate, int fps);

    public native void setAudioOptions(int sampleRateInHz, int channel);

    // 传递数据
    public native void fireVideo(byte[] data);

    public native void fireAudio(byte[] data, int len);


    static {
        System.loadLibrary("native-lib");
    }

}
