#include <jni.h>
#include <string>

extern "C"
JNIEXPORT void JNICALL
Java_com_example_ycl_ffmpeg_1pusher_utils_PushNative_startPush(JNIEnv *env, jobject instance,
                                                               jstring url_) {
    const char *url = env->GetStringUTFChars(url_, 0);


    env->ReleaseStringUTFChars(url_, url);
}extern "C"
JNIEXPORT void JNICALL
Java_com_example_ycl_ffmpeg_1pusher_utils_PushNative_stopPush(JNIEnv *env, jobject instance) {


}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_ycl_ffmpeg_1pusher_utils_PushNative_release(JNIEnv *env, jobject instance) {


}extern "C"
JNIEXPORT void JNICALL
Java_com_example_ycl_ffmpeg_1pusher_utils_PushNative_setVideoOptions(JNIEnv *env, jobject instance,
                                                                     jint width, jint height,
                                                                     jint bitrate, jint fps) {




}extern "C"
JNIEXPORT void JNICALL
Java_com_example_ycl_ffmpeg_1pusher_utils_PushNative_setAudioOptions(JNIEnv *env, jobject instance,
                                                                     jint sampleRateInHz,
                                                                     jint channel) {




}extern "C"
JNIEXPORT void JNICALL
Java_com_example_ycl_ffmpeg_1pusher_utils_PushNative_fireVideo(JNIEnv *env, jobject instance,
                                                               jbyteArray data_) {
    jbyte *data = env->GetByteArrayElements(data_, NULL);




    env->ReleaseByteArrayElements(data_, data, 0);
}extern "C"
JNIEXPORT void JNICALL
Java_com_example_ycl_ffmpeg_1pusher_utils_PushNative_fireAudio(JNIEnv *env, jobject instance,
                                                               jbyteArray data_, jint len) {
    jbyte *data = env->GetByteArrayElements(data_, NULL);




    env->ReleaseByteArrayElements(data_, data, 0);
}