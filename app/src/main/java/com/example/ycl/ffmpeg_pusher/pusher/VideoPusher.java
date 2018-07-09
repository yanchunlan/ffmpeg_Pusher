package com.example.ycl.ffmpeg_pusher.pusher;

import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;

import com.example.ycl.ffmpeg_pusher.params.VideoParam;
import com.example.ycl.ffmpeg_pusher.utils.PushNative;

import java.io.IOException;


/**
 * @Author: Ycl
 * @Date: 2018/7/9 11:31
 * @Desc:
 */
public class VideoPusher extends Pusher implements SurfaceHolder.Callback, Camera.PreviewCallback {
    private static final String TAG = "VideoPusher";
    private SurfaceHolder surfaceHolder;
    private Camera mCamera;
    private VideoParam videoParams;
    private byte[] buffers;
    private boolean isPushing;
    private PushNative pushNative;
    private LivePusher livePusher;

    public VideoPusher(SurfaceHolder surfaceHolder, VideoParam videoParam, PushNative pushNative, LivePusher livePusher) {
        this.surfaceHolder = surfaceHolder;
        this.videoParams = videoParam;
        this.pushNative = pushNative;
        this.livePusher = livePusher;
        surfaceHolder.addCallback(this);
    }

    @Override
    public void startPush() {
        isPushing = true;
    }

    @Override
    public void stopPush() {
        isPushing = false;
    }

    @Override
    public void release() {
        stopPreview();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startPreview();
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        livePusher.stopPush();
        livePusher.release();
    }


    /**
     * 开始预览
     */
    private void startPreview() {
        try {
            //SurfaceView初始化完成，开始相机预览
            mCamera = Camera.open(videoParams.getCameraId());
            mCamera.setPreviewDisplay(surfaceHolder);

            // 获取预览图像数据  setPreviewCallbackWithBuffer 之前需要设置buffer
            buffers = new byte[videoParams.getWidth() * videoParams.getHeight() * 4];
            mCamera.addCallbackBuffer(buffers);
            mCamera.setPreviewCallbackWithBuffer(this); // 采集数据

            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "startPreview: ", e);
        }
    }

    /**
     * 停止预览
     */
    private void stopPreview() {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    /**
     * 切换摄像头
     */
    public void switchCamera() {
        if (videoParams.getCameraId() == Camera.CameraInfo.CAMERA_FACING_BACK) {
            videoParams.setCameraId(Camera.CameraInfo.CAMERA_FACING_FRONT);
        } else {
            videoParams.setCameraId(Camera.CameraInfo.CAMERA_FACING_BACK);
        }
        //重新预览
        stopPreview();
        startPreview();
    }


    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        Log.i(TAG, "onPreviewFrame: ");
        // 不断被调用 ,解决一个坑  当release的时候，一直在执行这个方法，所以可能为null
        if (mCamera != null) {
            mCamera.addCallbackBuffer(buffers);
        }

        if (isPushing) {
            //回调函数中获取图像数据，然后给Native代码编码
            pushNative.fireVideo(data);
        }
    }
}
