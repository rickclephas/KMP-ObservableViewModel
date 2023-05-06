Pod::Spec.new do |s|
  s.name       = 'KMMViewModelCoreObjC'
  s.version    = '1.0.0-ALPHA-8'
  s.summary    = 'Library to share Kotlin ViewModels with Swift'

  s.homepage   = 'https://github.com/rickclephas/KMM-ViewModel'
  s.license    = 'MIT'
  s.authors    = 'Rick Clephas'

  s.source = {
    :git => 'https://github.com/rickclephas/KMM-ViewModel.git',
    :tag => 'v' + s.version.to_s
  }

  s.ios.deployment_target = '13.0'
  s.osx.deployment_target = '10.15'
  s.watchos.deployment_target = '6.0'
  s.tvos.deployment_target = '13.0'

  s.source_files = 'KMMViewModelCoreObjC/**/*.{h,m}'
end
