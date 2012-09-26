LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    		:= hack042-native
LOCAL_LDLIBS			:= -llog

LOCAL_SRC_FILES := \
	main.cpp \
	sqlite3.c
	
include $(BUILD_SHARED_LIBRARY)
