package com.example.ycl.ffmpeg_pusher.pusher;

/**
 * @Author: Ycl
 * @Date: 2018/7/9 11:31
 * @Desc:
 */
public abstract class Pusher {


    public abstract void startPush();

    public abstract void stopPush();

    public abstract void release();
}
