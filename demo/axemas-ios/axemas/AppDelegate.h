//
//  AppDelegate.h
//  axemas
//
//  Created by Alessandro Molina on 8/23/13.
//  Copyright (c) 2013 AXANT. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <FacebookSDK/FacebookSDK.h>

@interface AppDelegate : UIResponder <UIApplicationDelegate, UITabBarControllerDelegate>

typedef void (^FacebookLoginHandler)(FBSession *session, FBSessionState state, NSError * error);

@property (strong, nonatomic) UIWindow *window;
@property (strong, nonatomic) UIViewController *rootController;

- (void)sessionStateChanged:(FBSession *)session state:(FBSessionState) state error:(NSError *)error;
- (void)startFacebookLogin:(NSArray *)permissions withBlock:(void (^)(FBSession *session, FBSessionState state, NSError * error))facebookBlock;

@end
