//
//  KMPOVMPublisher.h
//  KMPObservableViewModelCoreObjC
//
//  Created by Rick Clephas on 09/06/2025.
//

#ifndef KMPOVMPublisher_h
#define KMPOVMPublisher_h

#import <Foundation/Foundation.h>
#import "KMPOVMProperty.h"

NS_ASSUME_NONNULL_BEGIN

__attribute__((swift_name("Publisher")))
@protocol KMPOVMPublisher
- (void)access:(id<KMPOVMProperty>)property;
- (void)willSet:(id<KMPOVMProperty>)property;
- (void)didSet:(id<KMPOVMProperty>)property;
@end

NS_ASSUME_NONNULL_END

#endif /* KMPOVMPublisher_h */
