# P7 · Revive CI, add static analysis — run this one first

## Goal

Get `./gradlew check` actually running on every PR again, add an assemble step so build
breakage (not just test/lint failures) is caught, and wire Detekt plus Compose lint checks
through the existing convention plugin so every module gets them for free.

## Why now — and why this lands before anything else

`.github/workflows/ci.yml:3-6`:
```yaml
on:
  push:
    branches: [master]
  pull_request:
    branches: [master]
```
The default branch is `main` (confirmed: `git symbolic-ref refs/remotes/origin/HEAD` →
`refs/remotes/origin/main`, and commit `bcd72f0`'s merge title is literally
"docs/main-branch-rename"). This workflow has not triggered on a single PR since that rename.
Confirmed directly: `gh run list` returns exactly **two** runs total, both timestamped
2026-07-19, both from the PR that originally added `ci.yml` back when `master` was still current
(titled "ci: add GitHub Actions workflow running gradle check"). Every PR merged since —
including `2f7bf63` and `da3beb4`, the two most recent commits on `main` — went in with zero
automated verification.

This is why P7 should land alone, first, before the other nine plans open PRs: without it,
none of their PRs get checked either.

`docs/TODO.md` separately asks for Detekt, Ktlint (already present via the `ganzhornfest`
convention plugin, see `build-logic/convention/src/main/kotlin/ganzhornfest.gradle.kts`), and
Compose lint checks. Compose lint (`compose-lint-checks`) is currently applied in exactly one
place, `app/build.gradle.kts:146` (`lintChecks(libs.compose.lint.checks)`) — every other Compose
module (`:map`, `:program`, `:bus-impl`, `:feature:search-impl`, `:feature:countdown`,
`:theme`) ships zero Compose-specific lint coverage.

## Worktree

```bash
/start-implement build ci-and-static-analysis
```

## Files owned

- `.github/workflows/ci.yml`
- `build-logic/convention/src/main/kotlin/ganzhornfest.gradle.kts`
- `build-logic/convention/build.gradle.kts` (if Detekt's plugin needs adding to
  `build-logic`'s own classpath)
- `gradle/libs.versions.toml`
- `.editorconfig` (Detekt config can live here or in a dedicated `detekt.yml`; check what
  `.editorconfig` already governs for ktlint before deciding)
- New `config/detekt/detekt.yml` (or similar), if a baseline config file is warranted
- `app/build.gradle.kts` — **remove** the now-redundant `lintChecks(libs.compose.lint.checks)`
  line once it's applied globally via the convention plugin, to avoid double-application

**Overlap:** `gradle/libs.versions.toml` is also touched by P1 (Firebase versions). Both are
additive to different `[versions]`/`[plugins]`/`[libraries]` keys — trivial merge either
direction, but land whichever merges second by doing a quick rebase rather than fighting it.

## Steps

1. **Fix the branch trigger.** Change `ci.yml`'s `branches: [master]` to `branches: [main]` in
   both the `push` and `pull_request` triggers.
   *Verify:* push this change on its own branch, open a draft PR, confirm the `CI` check
   actually appears and runs in the PR's checks tab (this is the one PR in this whole batch
   where visually confirming the Actions tab matters more than usual — do it manually, don't
   just trust the diff).

2. **Add an explicit assemble step.** `./gradlew check` runs tests/lint but by default does not
   guarantee `assembleDebug` succeeds for every module (lint runs against compiled sources but
   the full APK assembly path can still surface issues check alone won't, e.g. resource merging
   or manifest merging problems). Add a `./gradlew assembleDebug` step to `ci.yml` before or
   alongside `check`.
   *Verify:* the added step shows green in the same PR's Actions run.

3. **Add Detekt via the convention plugin.** In `libs.versions.toml`, add a `detekt` version and
   plugin entry. In `build-logic/convention/src/main/kotlin/ganzhornfest.gradle.kts`, alongside
   the existing `pluginManager.apply("org.jlleitschuh.gradle.ktlint")`
   (`ganzhornfest.gradle.kts:7`), add `pluginManager.apply("io.gitlab.arturbosch.detekt")` and a
   matching `extensions.configure<DetektExtension> { ... }` block — this is the whole point of
   the convention plugin per its own comment ("intentionally general so more shared config can
   move here later," `ganzhornfest.gradle.kts:5`) and matches your stated direction of sharing
   build config via convention plugins rather than `subprojects {}`/`allprojects {}`. You will
   likely need to add the Detekt Gradle plugin to `build-logic/convention/build.gradle.kts`'s
   own `dependencies` block (as a `compileOnly`/`implementation` on the plugin artifact) — check
   how `org.jlleitschuh.gradle.ktlint` is already wired there as the pattern to copy.
   *Verify:* `./gradlew detekt` (or however the task surfaces once `check` depends on it) runs
   across every included module, not just `:app` — spot check by running
   `./gradlew :feature:countdown:detekt` directly.

4. **Wire Detekt into `check`.** Confirm the plugin auto-attaches `detekt` to the `check`
   lifecycle task (it does by default), or add `tasks.check { dependsOn("detekt") }` in the
   convention plugin if not.
   *Verify:* `./gradlew check` fails if you temporarily introduce an obvious Detekt violation
   (e.g. a magic number or an empty catch block) in a scratch file, then confirm it passes again
   once removed.

5. **Apply Compose lint checks to every Compose module, once, centrally.** Move
   `lintChecks(libs.compose.lint.checks)` out of `app/build.gradle.kts` and into the
   `ganzhornfest` convention plugin, gated on the module having the Android Gradle Plugin's lint
   DSL available (i.e. only for `com.android.library`/`com.android.application` modules — check
   how the plugin currently detects Android vs. plain Kotlin modules, since not every module in
   this multi-module repo is an Android module). If a clean central application isn't feasible
   given the convention plugin's current generality, apply it individually to each Compose
   module's own `build.gradle.kts` instead (`:map`, `:program`, `:bus-impl`,
   `:feature:search-impl`, `:feature:countdown`, `:theme`) and say so explicitly in the PR
   description as the fallback taken.
   *Verify:* `./gradlew :program:lintDebug` (a module that never had Compose lint before) now
   reports Compose-specific findings if you temporarily introduce one (e.g. an unstable
   parameter on a `@Composable`), confirming the check is live there.

6. **Address what the new checks find.** Both Detekt and the newly-global Compose lint will
   likely surface pre-existing findings across the codebase (e.g. `MapScreen.kt`'s inline
   `Color(0xFFFF08F2)` literals, magic numbers throughout). Fix trivial ones directly. For
   anything non-trivial or touching files owned by other plans/branches in this batch, suppress
   with an inline `@Suppress` and a one-line comment pointing at the relevant TODO/plan, rather
   than expanding this PR's scope — per `CLAUDE.md`'s "surgical changes" rule, this PR's job is
   to turn the lights on, not to fix everything the lights reveal.
   *Verify:* `./gradlew check` is fully green with no suppressions left unexplained.

## Tests

No new unit tests — this plan is tooling/CI configuration. The verification *is* the tooling
running successfully, per steps above.

## Done when

A draft PR shows the `CI` check actually executing and passing in GitHub's UI (not just
`./gradlew check` passing locally — confirm the Actions tab, since the whole point of this plan
is that local success and CI execution have been silently decoupled), then merge this one before
opening any of the other nine PRs in this batch.
