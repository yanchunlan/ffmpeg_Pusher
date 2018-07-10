package com.example.ycl.ffmpeg_pusher.params;

/**
 * @Author: Ycl
 * @Date: 2018/7/9 12:02
 * @Desc:
 */
public class VideoParam {
    private int width;
    private int height;
    private int cameraId;

    private int bitrate = 480000;    // 码率480kbps
    private int fps = 25;// 帧频默认25帧/s


    public VideoParam(int width, int height, int cameraId) {
        this.width = width;
        this.height = height;
        this.cameraId = cameraId;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getCameraId() {
        return cameraId;
    }

    public void setCameraId(int cameraId) {
        this.cameraId = cameraId;
    }

    public int getBitrate() {
        return bitrate;
    }

    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }

    public int getFps() {
        return fps;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }
}
