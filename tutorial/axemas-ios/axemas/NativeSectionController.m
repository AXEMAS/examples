//
//  HomeSectionController.m
//  axemas
//
//  Created by Alessandro Molina on 4/10/14.
//  Copyright (c) 2014 AXANT. All rights reserved.
//

#import "NativeSectionController.h"
#import "WebViewJavascriptBridge.h"
#import "NavigationSectionsManager.h"

@implementation NativeSectionController

- (void)sectionWillLoad {
    [super sectionWillLoad];
    
    [self.section.bridge registerHandler:@"get-native-info" handler:^(id data, WVJBResponseCallback responseCallback) {
        
        NSNumber *stackSize = [NSNumber numberWithInteger:[NavigationSectionsManager getStackElements]];
        
        if (responseCallback) {
            responseCallback(@{@"items":stackSize,
                               @"device_name": [[UIDevice currentDevice] name]});
        }
    }];
    
    [self.section.bridge registerHandler:@"call-number" handler:^(id data, WVJBResponseCallback responseCallback) {
        NSDictionary *receivedData = (NSDictionary*)data;
        
        NSString *phoneNumber = [@"telprompt://" stringByAppendingString:[receivedData objectForKey:@"number"]];
        [[UIApplication sharedApplication] openURL:[NSURL URLWithString:phoneNumber]];
        
        NSLog(@"i would be calling now");
        
        if (responseCallback) {
            responseCallback(@{});
        }
    }];
    
    [NSTimer scheduledTimerWithTimeInterval:1.0 target:self selector:@selector(trigger:) userInfo:nil repeats:YES];

}


- (void)trigger:(NSTimer *)sender{
    NSDateFormatter *dateFormatter = [[NSDateFormatter alloc]init];
    [dateFormatter setDateFormat:[NSDateFormatter dateFormatFromTemplate:@"hh:mm:ss a" options:0 locale:[NSLocale currentLocale]]];
    NSString *theTime = [dateFormatter stringFromDate:[NSDate date]];
    
    [self.section.bridge callHandler:@"time-from-native" data:@{@"current_time":theTime}];
}

@end
