//
//  HomeSectionController.m
//  axemas
//
//  Created by Alessandro Molina on 4/10/14.
//  Copyright (c) 2014 AXANT. All rights reserved.
//

#import "IndexSectionController.h"
#import "WebViewJavascriptBridge.h"
#import "NavigationSectionsManager.h"

@implementation IndexSectionController

- (void)sectionWillLoad {
    [self.section.bridge registerHandler:@"open-camera" handler:^(id data, WVJBResponseCallback responseCallback) {
        
        UIImagePickerController *picker = [[UIImagePickerController alloc] init];
        picker.delegate = self;
        picker.allowsEditing = YES;
        
        if([UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerSourceTypeCamera])
            picker.sourceType = UIImagePickerControllerSourceTypeCamera;
        else
            picker.sourceType = UIImagePickerControllerSourceTypePhotoLibrary;
        
        [[NavigationSectionsManager activeNavigationController] presentViewController:picker animated:YES completion:NULL];
        
        if (responseCallback) {
            responseCallback(nil);
        }
    }];
}

- (void)imagePickerController:(UIImagePickerController *)picker didFinishPickingMediaWithInfo:(NSDictionary *)info {
    
    UIImage *chosenImage = info[UIImagePickerControllerEditedImage];
    NSString * fileName = [NSString stringWithFormat:@"/tmp/%@.jpg",[[NSUUID new] UUIDString]];
    NSString *jpgPath = [NSHomeDirectory() stringByAppendingPathComponent:fileName];
    [UIImageJPEGRepresentation(chosenImage, 1.0) writeToFile:jpgPath atomically:YES];

    [picker dismissViewControllerAnimated:YES completion:NULL];
    
    [self.section.bridge callHandler:@"send-path-to-js"
                                data:@{@"path": jpgPath}
                    responseCallback:^(id responseData) {
                        //empty
                    }];

}

- (void)imagePickerControllerDidCancel:(UIImagePickerController *)picker {
    [picker dismissViewControllerAnimated:YES completion:NULL];    
}

@end
