import org.jlleitschuh.gradle.ktlint.KtlintExtension

// Shared convention for all Ganzhornfest modules.
// Today it wires ktlint into the `check` lifecycle task.
// It is intentionally general so more shared config can move here later.

pluginManager.apply("org.jlleitschuh.gradle.ktlint")

extensions.configure<KtlintExtension> {
    // Rule config lives in the repo root .editorconfig.
    filter {
        exclude { entry -> entry.file.path.contains("/build/") }
    }
}
