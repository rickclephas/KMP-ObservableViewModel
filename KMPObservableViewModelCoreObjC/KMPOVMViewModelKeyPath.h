//
//  KMPOVMViewModelKeyPath.h
//  KMPObservableViewModelCoreObjC
//
//  Created by Rick Clephas on 11/06/2025.
//

#ifndef KMPOVMViewModelKeyPath_h
#define KMPOVMViewModelKeyPath_h

#import <Foundation/Foundation.h>
#import "KMPOVMPublisher.h"

NS_ASSUME_NONNULL_BEGIN

__attribute__((swift_name("ViewModelKeyPath")))
@protocol KMPOVMViewModelKeyPath
- (void)access:(id<KMPOVMPublisher>)publisher;
- (void)willSet:(id<KMPOVMPublisher>)publisher;
- (void)didSet:(id<KMPOVMPublisher>)publisher;
@end

NS_ASSUME_NONNULL_END

#endif /* KMPOVMViewModelKeyPath_h */
