//
//  KMPOVMViewModelScope.h
//  KMPObservableViewModelCoreObjC
//
//  Created by Rick Clephas on 27/11/2022.
//

#ifndef KMPOVMViewModelScope_h
#define KMPOVMViewModelScope_h

#import <Foundation/Foundation.h>

__attribute__((swift_name("ViewModelScope")))
@protocol KMPOVMViewModelScope
- (void)increaseSubscriptionCount;
- (void)decreaseSubscriptionCount;
- (void)setSendObjectWillChange:(void (^ _Nonnull)(void))sendObjectWillChange;
@end

#endif /* KMPOVMViewModelScope_h */
