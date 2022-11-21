//
//  ContentView.swift
//  KMMViewModelSample
//
//  Created by Rick Clephas on 21/11/2022.
//

import SwiftUI
import KMMViewModelSampleShared

struct ContentView: View {
    let greet = Greeting().greeting()

    var body: some View {
        Text(greet)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
