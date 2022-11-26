#import <Foundation/Foundation.h>

@protocol KMMVMObservableObjectScope
- (void)setSubscriptionCount:(NSInteger)subscriptionCount;
- (void)setSendObjectWillChange:(void (^ _Nonnull)(void))sendObjectWillChange;
- (void)callOnCleared;
@end
