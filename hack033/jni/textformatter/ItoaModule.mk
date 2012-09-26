#-------------------------------------------------------------------------------
# Copyright (c) 2012 Manning
# See the file license.txt for copying permission.
#-------------------------------------------------------------------------------
MODULE_PATH := $(call my-dir)
include $(CLEAR_VARS)

MODULE_NAME := textformatter

MODULE_SRC_FILES := \
    TextFormatter.m
    

MODULE_C_INCLUDES += \
    $(MODULE_PATH) \

include $(BUILD_SHARED_LIBRARY)
