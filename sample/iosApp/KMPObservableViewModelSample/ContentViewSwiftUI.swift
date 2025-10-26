//
//  ContentViewSwiftUI.swift
//  KMPObservableViewModelSample
//
//  Created by Rick Clephas on 21/11/2022.
//

import SwiftUI
import KMPObservableViewModelSwiftUI

struct ContentViewSwiftUIPublished: View {
    
    @StateViewModel var viewModel = TimeTravelViewModelPublished()
    
    var body: some View {
        ContentViewSwiftUI(viewModel: viewModel)
            .navigationTitle(Text("Published"))
            .navigationBarTitleDisplayMode(.inline)
    }
}

struct ContentViewSwiftUIPublishedObservable: View {
    
    @StateViewModel var viewModel = TimeTravelViewModelPublishedObservable()
    
    var body: some View {
        ContentViewSwiftUI(viewModel: viewModel)
            .navigationTitle(Text("Published + Observable"))
            .navigationBarTitleDisplayMode(.inline)
    }
}

@available(iOS 17.0, macOS 14.0, tvOS 17.0, watchOS 10.0, *)
struct ContentViewSwiftUIObservable: View {
    
    @StateViewModel var viewModel = TimeTravelViewModelObservable()
    
    var body: some View {
        ContentViewSwiftUI(viewModel: viewModel)
            .navigationTitle(Text("Observable"))
            .navigationBarTitleDisplayMode(.inline)
    }
}

private struct ContentViewSwiftUI: View {
    
    @ObservedViewModel var viewModel: TimeTravelViewModel
    @StateObject var counter = Counter()

    var body: some View {
        Spacer()
        ChangeCounter(counter.incrementAndGet()) {
            VStack(spacing: 24) {
                ActualTimeView(viewModel: viewModel)
                TravelEffectView(viewModel: viewModel)
                CurrentTimeView(viewModel: viewModel)
                IsFixedTimeView(viewModel: viewModel)
                ButtonsView(viewModel: viewModel)
            }.frame(minWidth: 0, maxWidth: .infinity)
        }.padding(.horizontal, 8)
        Spacer()
    }
}

private struct ActualTimeView: View {
    
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

private struct TravelEffectView: View {
    
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

private struct CurrentTimeView: View {
    
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

private struct IsFixedTimeView: View {
    
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

private struct ButtonsView: View {
    
    @ObservedViewModel var viewModel: TimeTravelViewModel
    @StateObject var counter = Counter()
    
    var body: some View {
        ChangeCounter(counter.incrementAndGet()) {
            VStack(spacing: 24) {
                Button("Time travel") {
                    viewModel.timeTravel()
                }
                Button("Reset") {
                    viewModel.resetTime()
                }.foregroundColor(viewModel.isResetDisabled ? Color.red : Color.green)
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentViewSwiftUIPublished()
    }
}
