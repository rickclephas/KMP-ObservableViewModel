//
//  TimeTravelViewModel.swift
//  KMPObservableViewModelSample
//
//  Created by Rick Clephas on 28/11/2022.
//

import KMPObservableViewModelSampleShared

class TimeTravelViewModel: KMPObservableViewModelSampleShared.TimeTravelViewModel {
    
    @Published var isResetDisabled: Bool = false
    
    override func resetTime() {
        isResetDisabled = !isResetDisabled
        guard isResetDisabled else { return }
        super.resetTime()
    }
    
    override init() {
        super.init()
        print("init")
    }
    
    override func onCleared() {
        print("onCleared")
        DispatchQueue.main.async {
            GCKt.gcCollect()
            print("GC collected")
        }
    }
    
    deinit {
        print("deinit")
    }
}
