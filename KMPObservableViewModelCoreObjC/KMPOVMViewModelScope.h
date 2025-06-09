//
//  KMPOVMViewModelScope.h
//  KMPObservableViewModelCoreObjC
//
//  Created by Rick Clephas on 27/11/2022.
//

#ifndef KMPOVMViewModelScope_h
#define KMPOVMViewModelScope_h

#import <Foundation/Foundation.h>
#import "KMPOVMSubscriptionCount.h"

NS_ASSUME_NONNULL_BEGIN

__attribute__((swift_name("ViewModelScope")))
@protocol KMPOVMViewModelScope
@property (readonly) id<KMPOVMSubscriptionCount> subscriptionCount;

- (void)setSendObjectWillChange:(void (^ _Nonnull)(void))sendObjectWillChange;
@end

NS_ASSUME_NONNULL_END

#endif /* KMPOVMViewModelScope_h */
