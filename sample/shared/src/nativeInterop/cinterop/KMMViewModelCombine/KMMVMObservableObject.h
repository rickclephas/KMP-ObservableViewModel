#import <Foundation/Foundation.h>

@protocol KMMVMObservableObjectScope
- (void)setObjectWillChangeSubscriptionCount:(NSInteger)subscriptionCount;
- (void)setSendObjectWillChange:(void (^ _Nonnull)(void))sendObjectWillChange;
@end
