//
//  KMPOVMSubscriptionCount.h
//  KMPObservableViewModel
//
//  Created by Rick Clephas on 09/06/2025.
//

#ifndef KMPOVMSubscriptionCount_h
#define KMPOVMSubscriptionCount_h

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

__attribute__((swift_name("SubscriptionCount")))
@protocol KMPOVMSubscriptionCount
- (void)increase;
- (void)decrease;
@end

NS_ASSUME_NONNULL_END

#endif /* KMPOVMSubscriptionCount_h */
