//
//  BaseSectionController.m
//  Tutorial
//
//  Created by Andrei Neagu on 15/10/14.
//  Copyright (c) 2014 AXANT. All rights reserved.
//

#import "BaseSectionController.h"
#import "NavigationSectionsManager.h"
#import "ExampleViewController.h"

@implementation BaseSectionController

-(void)sectionWillLoad{
    [super sectionWillLoad];
    
    [self.section.bridge registerHandler:@"push-native-view" handler:^(id data, WVJBResponseCallback responseCallback) {
        NSDictionary *receivedData = (NSDictionary*)data;
        NSNumber * close = (NSNumber *)[receivedData objectForKey: @"close"];
        if([close boolValue] == YES )
            [[NavigationSectionsManager activeSidebarController] revealToggleAnimated:YES];
        [NavigationSectionsManager pushController:[[ExampleViewController alloc] init] animated:YES];
        
        if (responseCallback) {
            responseCallback(nil);
        }
    }];
    
}

@end
