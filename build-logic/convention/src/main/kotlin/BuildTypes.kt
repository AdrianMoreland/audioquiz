
import com.android.build.api.dsl.ApkSigningConfig
import com.android.build.api.dsl.ApplicationBuildType
import com.android.build.api.dsl.TestBuildType
import org.gradle.api.NamedDomainObjectContainer



interface BuildType<T: com.android.build.api.dsl.BuildType> {
    val name: String

    fun create(
        buildTypeNamedDomainObjectContainer: NamedDomainObjectContainer<T>,
        signingConfigDomainObjectContainer: NamedDomainObjectContainer<out ApkSigningConfig>? = null,
    ): T

    companion object {
        const val DEBUG = "debug"
        const val RELEASE = "release"
        const val BENCHMARK = "benchmark"
    }
}

object BuildTypeDebug : BuildType<ApplicationBuildType> {
    override val name = BuildType.DEBUG

    override fun create(
        buildTypeNamedDomainObjectContainer: NamedDomainObjectContainer<ApplicationBuildType>,
        signingConfigDomainObjectContainer: NamedDomainObjectContainer<out ApkSigningConfig>?,
    ): ApplicationBuildType {
        return buildTypeNamedDomainObjectContainer.getByName(name) {
            isDefault
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true
            manifestPlaceholders["hostName"] = "debug"
            signingConfig = signingConfigDomainObjectContainer?.getByName("debug")
        }
    }
}

object BuildTypeRelease : BuildType<ApplicationBuildType> {
    override val name = BuildType.RELEASE

    override fun create(
        buildTypeNamedDomainObjectContainer: NamedDomainObjectContainer<ApplicationBuildType>,
        signingConfigDomainObjectContainer: NamedDomainObjectContainer<out ApkSigningConfig>?,
    ): ApplicationBuildType {
        return buildTypeNamedDomainObjectContainer.getByName(name) {
            isMinifyEnabled = true
            isShrinkResources = true
            manifestPlaceholders["hostName"] = "release"
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
            signingConfig = signingConfigDomainObjectContainer?.getByName("release")
        }
    }
}



/*
object BuildTypeBenchmark : BuildType<ApplicationBuildType> {
    override val name = BuildType.BENCHMARK

    override fun create(
        buildTypeNamedDomainObjectContainer: NamedDomainObjectContainer<ApplicationBuildType>,
        signingConfigDomainObjectContainer: NamedDomainObjectContainer<out ApkSigningConfig>?,
    ): ApplicationBuildType {
        return buildTypeNamedDomainObjectContainer.create(name) {
            initWith(buildTypeNamedDomainObjectContainer.getByName(BuildType.RELEASE))
            signingConfig = signingConfigDomainObjectContainer?.getByName(BuildType.DEBUG)
            matchingFallbacks.add(BuildType.RELEASE)
            proguardFiles("benchmark-rules.pro")
        }
    }
}

object TestBuildTypeBenchmark : BuildType<TestBuildType> {
    override val name = BuildType.BENCHMARK

    override fun create(
        buildTypeNamedDomainObjectContainer: NamedDomainObjectContainer<TestBuildType>,
        signingConfigDomainObjectContainer: NamedDomainObjectContainer<out ApkSigningConfig>?,
    ): TestBuildType {
        return buildTypeNamedDomainObjectContainer.create(name) {
            isDebuggable = true
            signingConfig = signingConfigDomainObjectContainer?.getByName(BuildType.DEBUG)
            matchingFallbacks += listOf(BuildType.RELEASE)
        }
    }
}
*/
