//
//  KMPOVMPublisher.h
//  KMPObservableViewModelCoreObjC
//
//  Created by Rick Clephas on 09/06/2025.
//

#ifndef KMPOVMPublisher_h
#define KMPOVMPublisher_h

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

__attribute__((swift_name("Publisher")))
@protocol KMPOVMPublisher
- (void)send;
@end

NS_ASSUME_NONNULL_END

#endif /* KMPOVMPublisher_h */
