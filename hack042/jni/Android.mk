#-------------------------------------------------------------------------------
# Copyright (c) 2012 Manning
# See the file license.txt for copying permission.
#-------------------------------------------------------------------------------
LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    		:= hack042-native
LOCAL_LDLIBS			:= -llog

LOCAL_SRC_FILES := \
	main.cpp \
	sqlite3.c
	
include $(BUILD_SHARED_LIBRARY)
