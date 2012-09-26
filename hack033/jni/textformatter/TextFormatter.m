#import "TextFormatter.h"

@implementation TextFormatter

+ (NSString *)format:(NSString *)text {
	NSString *objc = @"Text from Objective-c";
	NSString *string = [NSString stringWithFormat:@"%@ with %@",
		objc, text];

  return string;
}

@end
