//
//  AppDelegate.m
//  axemas
//
//  Copyright (c) 2013 AXANT. All rights reserved.
//

#import "AppDelegate.h"
#import "NavigationSectionsManager.h"
#import "NetworkAvailabilityDetector.h"
#import "IndexSectionController.h"

@interface AppDelegate ()

@property (nonatomic, strong) NetworkAvailabilityDetector *networkDetector;
@property (copy, nonatomic) FacebookLoginHandler facebookLoginHandler;

@end

@implementation AppDelegate

//TODO: Aggiungere Reachability per popup quando rete non disponibile

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    self.window = [[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
    self.networkDetector = [[NetworkAvailabilityDetector alloc] init];
    
    [NavigationSectionsManager registerController:[IndexSectionController class] forRoute:@"www/index.html"];

    
    self.rootController = [NavigationSectionsManager makeApplicationRootController:@[@{@"url":@"www/index.html",
                                                                                       @"title":@"Home",
                                                                                       @"toggleSidebarIcon":@"slide_icon"}]
                                                                       withSidebar:@{@"url":@"www/sidebar.html"}];

    self.window.rootViewController = self.rootController;
    [self.window makeKeyAndVisible];
    
    return YES;
}

- (void)applicationWillResignActive:(UIApplication *)application
{
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
}

/*
// Optional UITabBarControllerDelegate method.
- (void)tabBarController:(UITabBarController *)tabBarController didSelectViewController:(UIViewController *)viewController
{
}
*/

/*
// Optional UITabBarControllerDelegate method.
- (void)tabBarController:(UITabBarController *)tabBarController didEndCustomizingViewControllers:(NSArray *)viewControllers changed:(BOOL)changed
{
}
*/


#pragma mark - FACEBOOK

- (void)startFacebookLogin:(NSArray *)permissions withBlock:(void (^)(FBSession *session, FBSessionState state, NSError * error))facebookBlock{
    self.facebookLoginHandler=facebookBlock;
    // Open a session showing the user the login UI
    // You must ALWAYS ask for public_profile permissions when opening a session
    [FBSession openActiveSessionWithReadPermissions:permissions
                                       allowLoginUI:YES
                                  completionHandler:
     ^(FBSession *session, FBSessionState state, NSError *error) {
         
         // Retrieve the app delegate
         AppDelegate* appDelegate = [UIApplication sharedApplication].delegate;
         // Call the app delegate's sessionStateChanged:state:error method to handle session state changes
         [appDelegate sessionStateChanged:session state:state error:error];
     }];
}

- (void)sessionStateChanged:(FBSession *)session state:(FBSessionState) state error:(NSError *)error{
    if(self.facebookLoginHandler)
        self.facebookLoginHandler(session,state,error);
}

// During the Facebook login flow, your app passes control to the Facebook iOS app or Facebook in a mobile browser.
// After authentication, your app will be called back with the session information.
// Override application:openURL:sourceApplication:annotation to call the FBsession object that handles the incoming URL
- (BOOL)application:(UIApplication *)application
            openURL:(NSURL *)url
  sourceApplication:(NSString *)sourceApplication
         annotation:(id)annotation{
    return [FBSession.activeSession handleOpenURL:url];
}

@end
