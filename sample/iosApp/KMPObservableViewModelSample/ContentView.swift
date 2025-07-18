//
//  ContentView.swift
//  KMPObservableViewModelSample
//
//  Created by Rick Clephas on 21/11/2022.
//

import SwiftUI
import KMPObservableViewModelSwiftUI

struct ContentView: View {
    
    @StateViewModel var viewModel = TimeTravelViewModel()
    @StateObject var counter = Counter()

    var body: some View {
        Spacer()
        ChangeCounter(counter.incrementAndGet()) {
            VStack {
                ActualTimeView(viewModel: viewModel)
                Spacer().frame(height: 24)
                TravelEffectView(viewModel: viewModel)
                Spacer().frame(height: 24)
                CurrentTimeView(viewModel: viewModel)
                Spacer().frame(height: 24)
                IsFixedTimeView(viewModel: viewModel)
                Spacer().frame(height: 24)
                Button("Time travel") {
                    viewModel.timeTravel()
                }
                Spacer().frame(height: 24)
                Button("Reset") {
                    viewModel.resetTime()
                }.foregroundColor(viewModel.isResetDisabled ? Color.red : Color.green)
            }.frame(minWidth: 0, maxWidth: .infinity)
        }.padding(.horizontal, 8)
        Spacer()
    }
}

struct ActualTimeView: View {
    
    @ObservedViewModel var viewModel: TimeTravelViewModel
    @StateObject var counter = Counter()
    
    var body: some View {
        ChangeCounter(counter.incrementAndGet()) {
            VStack {
                Text("Actual time:")
                Text(viewModel.actualTime)
                    .font(.system(size: 20))
            }
        }
    }
}

struct TravelEffectView: View {
    
    @ObservedViewModel var viewModel: TimeTravelViewModel
    @StateObject var counter = Counter()
    
    var body: some View {
        ChangeCounter(counter.incrementAndGet()) {
            VStack {
                Text("Travel effect:")
                Text(viewModel.travelEffect?.description ?? "nil")
                    .font(.system(size: 20))
            }
        }
    }
}

struct CurrentTimeView: View {
    
    @ObservedViewModel var viewModel: TimeTravelViewModel
    @StateObject var counter = Counter()
    
    var body: some View {
        ChangeCounter(counter.incrementAndGet()) {
            VStack {
                Text("Current time:")
                Text(viewModel.currentTime)
                    .font(.system(size: 20))
            }
        }
    }
}

struct IsFixedTimeView: View {
    
    @ObservedViewModel var viewModel: TimeTravelViewModel
    @StateObject var counter = Counter()
    
    private var isFixedTimeBinding: Binding<Bool> {
        Binding { viewModel.isFixedTime } set: { isFixedTime in
            if isFixedTime {
                viewModel.stopTime()
            } else {
                viewModel.startTime()
            }
        }
    }
    
    var body: some View {
        ChangeCounter(counter.incrementAndGet()) {
            HStack {
                Toggle("", isOn: isFixedTimeBinding).labelsHidden()
                Text("Fixed time")
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
