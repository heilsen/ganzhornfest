import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension
import org.jlleitschuh.gradle.ktlint.KtlintExtension

// Shared convention for all Ganzhornfest modules.
// Wires ktlint into the `check` lifecycle task and adds Compose lint checks
// to every Android module.
// It is intentionally general so more shared config can move here later.

pluginManager.apply("org.jlleitschuh.gradle.ktlint")

extensions.configure<KtlintExtension> {
    // Rule config lives in the repo root .editorconfig.
    filter {
        exclude { entry -> entry.file.path.contains("/build/") }
    }
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

// com.android.application and com.android.library both apply com.android.base,
// so this fires exactly once per module regardless of plugin declaration order.
pluginManager.withPlugin("com.android.base") {
    dependencies.add("lintChecks", libs.findLibrary("compose-lint-checks").get())
}

// kotlinx.datetime.LocalDate and kotlin.time.Instant are unstable to Compose and sit in model
// classes across the feature modules, so presenters holding them never skip. The root config
// file marks them stable. Wired here rather than in each of the eleven Compose modules.
pluginManager.withPlugin("org.jetbrains.kotlin.plugin.compose") {
    extensions.configure<ComposeCompilerGradlePluginExtension> {
        stabilityConfigurationFiles.add(
            isolated.rootProject.projectDirectory.file("compose_compiler_config.conf"),
        )
    }
}
