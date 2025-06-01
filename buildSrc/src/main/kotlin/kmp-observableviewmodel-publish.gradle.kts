@file:Suppress("UnstableApiUsage")

import com.vanniktech.maven.publish.SonatypeHost

plugins {
    id("com.vanniktech.maven.publish.base")
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.S01)
    signAllPublications()
    configureBasedOnAppliedPlugins()
    pom {
        name = "KMP-ObservableViewModel"
        description = "Library to share Kotlin ViewModels with SwiftUI"
        url = "https://github.com/rickclephas/KMP-ObservableViewModel"
        licenses {
            license {
                name = "MIT"
                url = "https://opensource.org/licenses/MIT"
            }
        }
        developers {
            developer {
                id = "rickclephas"
                name = "Rick Clephas"
                email = "rclephas@gmail.com"
            }
        }
        scm {
            url = "https://github.com/rickclephas/KMP-ObservableViewModel"
        }
    }
}
