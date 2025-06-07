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
- (void)setPropertyAccess:(void (^ _Nonnull)(NSObject * _Nonnull))propertyAccess;
- (void)setPropertyWillSet:(void (^ _Nonnull)(NSObject * _Nonnull))propertyWillSet;
- (void)setPropertyDidSet:(void (^ _Nonnull)(NSObject * _Nonnull))propertyDidSet;
@end

#endif /* KMPOVMViewModelScope_h */
