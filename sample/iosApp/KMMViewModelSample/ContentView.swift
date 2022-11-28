//
//  ContentView.swift
//  KMMViewModelSample
//
//  Created by Rick Clephas on 21/11/2022.
//

import SwiftUI
import KMMViewModelSwiftUI

struct ContentView: View {
    
    @StateViewModel var viewModel = TimeTravelViewModel()
    
    private var isFixedTimeBinding: Binding<Bool> {
        Binding { viewModel.isFixedTimeValue } set: { isFixedTime in
            if isFixedTime {
                viewModel.stopTime()
            } else {
                viewModel.startTime()
            }
        }
    }

    var body: some View {
        VStack{
            Spacer()
            Group {
                Text("Actual time:")
                Text(viewModel.actualTimeValue)
                    .font(.system(size: 20))
            }
            Group {
                Spacer().frame(height: 24)
                Text("Travel effect:")
                Text(viewModel.travelEffectValue?.description ?? "nil")
                    .font(.system(size: 20))
            }
            Group {
                Spacer().frame(height: 24)
                Text("Current time:")
                Text(viewModel.currentTimeValue)
                    .font(.system(size: 20))
            }
            Group {
                Spacer().frame(height: 24)
                HStack {
                    Toggle("", isOn: isFixedTimeBinding).labelsHidden()
                    Text("Fixed time")
                }
            }
            Group {
                Spacer().frame(height: 24)
                Button("Time travel") {
                    viewModel.timeTravel()
                }
            }
            Group {
                Spacer().frame(height: 24)
                Button("Reset") {
                    viewModel.resetTime()
                }.foregroundColor(viewModel.isResetDisabled ? Color.red : Color.green)
            }
            Spacer()
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
