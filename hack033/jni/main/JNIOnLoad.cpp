#-------------------------------------------------------------------------------
# Copyright (c) 2012 Manning
# See the file license.txt for copying permission.
#-------------------------------------------------------------------------------
#include <CoreFoundation/CFRuntime.h>
#include <jni.h>

extern "C"
{
jint JNI_OnLoad(JavaVM *vm, void *reserved) {

  // Initialize CoreFoundation
  _CFInitialize();

  // Load Objective-C classes
  extern void call_dyld_handlers();
  call_dyld_handlers();

  return JNI_VERSION_1_6;
}
}

