package com.example.ycl.ffmpeg_pusher.pusher;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import com.example.ycl.ffmpeg_pusher.params.AudioParam;
import com.example.ycl.ffmpeg_pusher.utils.PushNative;

/**
 * @Author: Ycl
 * @Date: 2018/7/9 11:31
 * @Desc:
 */
public class AudioPusher extends Pusher {
    private static final String TAG = "AudioPusher";
    private AudioParam audioParam;
    private PushNative pushNative;
    private int minBufferSize;
    private AudioRecord audioRecord;
    private boolean isPushing;


    public AudioPusher(AudioParam audioParam, PushNative pushNative) {
        this.audioParam = audioParam;
        this.pushNative = pushNative;

        // 单声道  立体声
        int channelConfig = audioParam.getChannel() == 1 ?
                AudioFormat.CHANNEL_IN_MONO : AudioFormat.CHANNEL_IN_STEREO;
        minBufferSize = AudioRecord.getMinBufferSize(audioParam.getSampleRateInHz(),
                channelConfig,
                AudioFormat.ENCODING_PCM_16BIT);
        //  int audioSource, int sampleRateInHz, int channelConfig, int audioFormat, int bufferSizeInBytes
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                audioParam.getSampleRateInHz(),
                channelConfig,
                AudioFormat.ENCODING_PCM_16BIT,
                minBufferSize
        );
    }

    @Override
    public void startPush() {
        Log.i(TAG, "startPush: ");
        isPushing = true;
        //启动一个录音子线程
        new Thread(new AudioRecordRunable()).start();
    }

    @Override
    public void stopPush() {
        Log.i(TAG, "stopPush: ");
        isPushing = false;
        audioRecord.stop();
    }

    @Override
    public void release() {
        Log.i(TAG, "release: ");
        if (audioRecord != null) {
            audioRecord.release();
            audioRecord = null;
        }
    }


    class AudioRecordRunable implements Runnable {
        @Override
        public void run() {
            // 开始录音
            audioRecord.startRecording();

            while (isPushing) {
                //通过AudioRecord不断读取音频数据
                byte[] buffer = new byte[minBufferSize];
                int len = audioRecord.read(buffer, 0, buffer.length);
                if (len > 0) {
                    //                传给Native代码，进行音频编码
                    Log.i(TAG, "run: "+buffer+" len: "+len);
                    pushNative.fireAudio(buffer, len);
                }
            }
        }
    }
}

