#-------------------------------------------------------------------------------
# Copyright (c) 2012 Manning
# See the file license.txt for copying permission.
#-------------------------------------------------------------------------------
MODULE_PATH := $(call my-dir)
include $(CLEAR_VARS)

MODULE_NAME := main

MODULE_SRC_FILES := \
    JNIOnLoad.cpp \
    main.mm \

MODULE_C_INCLUDES += \
		$(MODULE_PATH)/../textformatter \

MODULE_SHARED_LIBRARIES += textformatter

include $(BUILD_SHARED_LIBRARY)
APP_SHARED_LIBRARIES += $(TARGET_ITOA_LIBRARIES)
