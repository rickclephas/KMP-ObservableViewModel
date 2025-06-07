//
//  TimeTravelViewModel.swift
//  KMPObservableViewModelSample
//
//  Created by Rick Clephas on 28/11/2022.
//

import KMPObservableViewModelSampleShared
import KMPObservableViewModelCore

class TimeTravelViewModel: KMPObservableViewModelSampleShared.TimeTravelViewModel {
    
    @Published var isResetDisabled: Bool = false
    
    override func resetTime() {
        isResetDisabled = !isResetDisabled
        guard isResetDisabled else { return }
        super.resetTime()
    }
}

@available(iOS 17.0, *)
extension KMPObservableViewModelSampleShared.TimeTravelViewModel: Observable {
    public var observationRegistrar: ObservationRegistrar {
        \.actualTime
        \.travelEffect
        \.currentTime
        \.isFixedTime
    }
}
