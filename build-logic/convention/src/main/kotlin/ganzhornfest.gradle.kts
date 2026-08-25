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
