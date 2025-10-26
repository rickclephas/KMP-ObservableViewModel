//
//  TimeTravelViewModel.swift
//  KMPObservableViewModelSample
//
//  Created by Rick Clephas on 28/11/2022.
//

import KMPObservableViewModelSampleShared

class TimeTravelViewModel: KMPObservableViewModelSampleShared.TimeTravelViewModel {
    
    var isResetDisabled: Bool = false
    
    override func resetTime() {
        isResetDisabled = !isResetDisabled
        guard isResetDisabled else { return }
        super.resetTime()
    }
}

class TimeTravelViewModelPublished: TimeTravelViewModel {
    
    @Published private var _isResetDisabled: Bool = false
    
    override var isResetDisabled: Bool {
        get { _isResetDisabled }
        set { _isResetDisabled = newValue }
    }
}

class TimeTravelViewModelPublishedObservable: TimeTravelViewModel, Observable {
    
    @Published private var _isResetDisabled: Bool = false
    
    override var isResetDisabled: Bool {
        get { _isResetDisabled }
        set { _isResetDisabled = newValue }
    }
}

@Observable
@available(iOS 17.0, macOS 14.0, tvOS 17.0, watchOS 10.0, *)
class TimeTravelViewModelObservable: TimeTravelViewModel {
    
    private var _isResetDisabled: Bool = false
    
    override var isResetDisabled: Bool {
        get { _isResetDisabled }
        set { _isResetDisabled = newValue }
    }
}
