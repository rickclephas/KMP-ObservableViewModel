Pod::Spec.new do |s|
  s.name       = 'KMPObservableViewModelCoreObjC'
  s.version    = '1.0.0-BETA-11'
  s.summary    = 'Library to share Kotlin ViewModels with Swift'

  s.homepage   = 'https://github.com/rickclephas/KMP-ObservableViewModel'
  s.license    = 'MIT'
  s.authors    = 'Rick Clephas'

  s.source = {
    :git => 'https://github.com/rickclephas/KMP-ObservableViewModel.git',
    :tag => 'v' + s.version.to_s
  }

  s.ios.deployment_target = '13.0'
  s.osx.deployment_target = '10.15'
  s.watchos.deployment_target = '6.0'
  s.tvos.deployment_target = '13.0'

  s.source_files = 'KMPObservableViewModelCoreObjC/**/*.{h,m}'
end
