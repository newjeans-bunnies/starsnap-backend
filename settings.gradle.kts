plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "starsnap"

include("domain")
include("usecase")
include("adapter-infrastructure")
include("adapter-web")
include("config")
include("adapter-usecase")
include("exception")
include("adapter-lambda-s3")