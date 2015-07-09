#include "com_seerslab_ndktest_MainActivity.h"
#include "opencv2/core/core.hpp"
#include <android/log.h>


JNIEXPORT jstring JNICALL Java_com_seerslab_ndktest_MainActivity_getStringFromNative(JNIEnv *env, jobject obj) {
  cv::Mat();

  __android_log_print(ANDROID_LOG_DEBUG, "ss", "s");

  return env->NewStringUTF("hello jni11");
 }
