package com.example.ycl.ffmpeg_pusher;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ycl.ffmpeg_pusher.pusher.LivePusher;

public class VideoRecordActivity extends AppCompatActivity {
    private static final String TAG = "VideoRecordActivity";

    // 根据推流的 网页查看源代码，获取到src
    public static final String URL = "rtmp://112.74.96.116/live/jason";
    private SurfaceView mSurface;
    private LivePusher live;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_record);
        initView();

        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(VideoRecordActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(VideoRecordActivity.this, new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.INTERNET,
                }, 1);
            }
        } else {

            Log.i(TAG, "onCreate: ");
        }
    }

    private void initView() {
        mSurface = (SurfaceView) findViewById(R.id.surface);
        // 相机图像的预览
        live = new LivePusher(mSurface.getHolder());

    }

    /**
     * 开始直播
     */
    public void mStartLive(View view) {
        Button btn = (Button) view;
        if (btn.getText().equals("开始直播")) {
            live.startPush(URL);
            btn.setText("停止直播");
        } else {
            live.stopPush();
            btn.setText("开始直播");
        }
    }

    /**
     * 切换摄像头
     */
    public void mSwitchCamera(View btn) {
        live.switchCamera();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i(TAG, "onRequestPermissionsResult: requestCode" + requestCode + " permissions " + permissions.toString() + " grantResults " + grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "允许权限");
                } else {
                    Log.i(TAG, "拒绝权限");
                    Toast.makeText(this, "拒绝权限", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }


}
