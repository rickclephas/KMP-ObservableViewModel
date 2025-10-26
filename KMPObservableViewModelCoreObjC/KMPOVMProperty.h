//
//  KMPOVMProperty.h
//  KMPObservableViewModelCoreObjC
//
//  Created by Rick Clephas on 25/10/2025.
//

#ifndef KMPOVMProperty_h
#define KMPOVMProperty_h

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

__attribute__((swift_name("Property")))
@protocol KMPOVMProperty
@property (readonly) id _Nullable value;
@end

NS_ASSUME_NONNULL_END

#endif /* KMPOVMProperty_h */
