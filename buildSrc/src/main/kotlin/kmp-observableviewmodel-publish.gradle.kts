plugins {
    `maven-publish`
    signing
}

ext["signing.keyId"] = null
ext["signing.password"] = null
ext["signing.secretKey"] = null
ext["signing.secretKeyRingFile"] = null
ext["ossrhUsername"] = null
ext["ossrhPassword"] = null
val localPropsFile = project.rootProject.file("local.properties")
if (localPropsFile.exists()) {
    localPropsFile.reader()
        .use { java.util.Properties().apply { load(it) } }
        .onEach { (name, value) -> ext[name.toString()] = value }
} else {
    ext["signing.keyId"] = System.getenv("SIGNING_KEY_ID")
    ext["signing.password"] = System.getenv("SIGNING_PASSWORD")
    ext["signing.secretKey"] = System.getenv("SIGNING_SECRET_KEY")
    ext["signing.secretKeyRingFile"] = System.getenv("SIGNING_SECRET_KEY_RING_FILE")
    ext["ossrhUsername"] = System.getenv("OSSRH_USERNAME")
    ext["ossrhPassword"] = System.getenv("OSSRH_PASSWORD")
}

fun getExtraString(name: String) = ext[name]?.toString()

val signPublications = getExtraString("signing.keyId") != null

publishing {
    repositories {
        maven {
            name = "sonatype"
            setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = getExtraString("ossrhUsername")
                password = getExtraString("ossrhPassword")
            }
        }
    }

    publications.withType<MavenPublication> {
        artifact(tasks.register("${name}JavadocJar", Jar::class) {
            archiveClassifier.set("javadoc")
            archiveAppendix.set(this@withType.name)
        })
        if (signPublications) signing.sign(this)

        pom {
            name.set("KMP-ObservableViewModel")
            description.set("Library to share Kotlin ViewModels with SwiftUI")
            url.set("https://github.com/rickclephas/KMM-ViewModel")
            licenses {
                license {
                    name.set("MIT")
                    url.set("https://opensource.org/licenses/MIT")
                }
            }
            developers {
                developer {
                    id.set("rickclephas")
                    name.set("Rick Clephas")
                    email.set("rclephas@gmail.com")
                }
            }
            scm {
                url.set("https://github.com/rickclephas/KMM-ViewModel")
            }
        }
    }
}

if (signPublications) {
    signing {
        getExtraString("signing.secretKey")?.let { secretKey ->
            useInMemoryPgpKeys(getExtraString("signing.keyId"), secretKey, getExtraString("signing.password"))
        }
    }
}
