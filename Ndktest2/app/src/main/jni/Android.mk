LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
OPENCV_CAMERA_MODULES:=off
OPENCV_INSTALL_MODULES:=on
OPENCV_LIB_TYPE:=SHARED
include C:\OpenCV-android-sdk\sdk\native\jni\OpenCV.MK

LOCAL_MODULE    := NDKTest
LOCAL_SRC_FILES := main.cpp
LOCAL_LDLIBS := -llog

include $(BUILD_SHARED_LIBRARY)

