#import <Foundation/Foundation.h>

@protocol KMMVMViewModelScope
- (void)increaseSubscriptionCount;
- (void)decreaseSubscriptionCount;
- (void)setSendObjectWillChange:(void (^ _Nonnull)(void))sendObjectWillChange;
- (void)cancel;
@end
