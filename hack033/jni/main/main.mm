#include <jni.h>
#import <Foundation/Foundation.h>
#import <objc/runtime.h>
#import <TextFormatter.h>

extern "C"
{
jstring
Java_com_manning_androidhacks_hack033_TextFormatter_formatString(
	JNIEnv* env, jobject thiz, jstring text)
{
  jstring result = NULL;

  NSAutoreleasePool *pool = [NSAutoreleasePool new];
	const char *nativeText = env->GetStringUTFChars(text, 0);
  NSString *objcText = [NSString stringWithUTF8String:nativeText];
  env->ReleaseStringUTFChars(text, nativeText);

  NSString *formattedText = [TextFormatter format: objcText];
  result =  env->NewStringUTF([formattedText UTF8String]);

  [pool drain];

  return result;
}
}
