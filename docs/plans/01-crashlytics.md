# P1 · Crash reporting with privacy handling

## Goal

Add Firebase Crashlytics so fatal crashes and important non-fatals surface with context
(selected festival day, bus destination, DB schema version), gated behind a collection-enabled
flag so nothing is sent without an explicit opt-in path. Wire it through the existing Timber
tree pattern rather than inventing a second logging surface.

## Why now

The repo has zero crash reporting today (`grep -rn "firebase\|crashlytics" .` returns nothing
outside `build/`). `docs/TODO.md` lists "Add crash reporting with explicit privacy handling"
under Quality/Release. The `GetMarkersUseCase` in `:map` currently calls `error(...)` on
unrecognised POI type data (see P9), which is exactly the kind of failure that should become a
recorded non-fatal instead of a hard crash — this plan is the prerequisite for that.

## Worktree

```bash
/start-implement feat crashlytics
```

## Files owned

- `gradle/libs.versions.toml` — add `firebase-bom`, `firebase-crashlytics`,
  `google-services` plugin, `firebase-crashlytics` Gradle plugin versions.
- `build.gradle.kts` (root) — apply `com.google.gms.google-services` and
  `com.google.firebase.crashlytics` plugins at the root with `apply false`.
- `app/build.gradle.kts` — apply both plugins, add `firebase-bom` platform +
  `firebase-crashlytics` dependency.
- `app/google-services.json` — new file, downloaded from the Firebase console. Not a secret,
  it ships inside every APK; commit it.
- `app/src/main/kotlin/de/heilsen/ganzhornfest/di/TimberBindings.kt` — add a
  `CrashlyticsTree : Timber.Tree` planted only in release builds.
- `app/src/main/kotlin/de/heilsen/ganzhornfest/GanzhornfestApplication.kt` — set
  `FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(...)` from stored consent.
- New `core-api/src/main/kotlin/de/heilsen/ganzhornfest/core/CrashReporter.kt` interface +
  `core-impl` binding, so feature modules record non-fatals without depending on Firebase
  directly (keeps `:map`, `:bus-impl` etc. Firebase-free, matches the existing `-api`/`-impl`
  split).
- `app/src/main/res/values/strings.xml` — any user-facing consent copy, if you add a settings
  toggle (optional, see Steps).

**Do not touch:** `app/src/main/kotlin/.../main/MainScreen.kt`, anything under `:map`,
`:program`, `:bus-impl` beyond adding the `CrashReporter` binding usage — those belong to other
plans or your `feat/festival-data-2026` branch.

**Known overlap:** `feat/festival-data-2026` also edits `app/build.gradle.kts` (a
`versionCode`/`versionName` bump). Your plugin/dependency additions land in a different region
of the file — trivial to merge either direction.

## Steps

1. **Create the Firebase project.** Console → Add project → Android app with package
   `de.heilsen.ganzhornfest`. Download `google-services.json` into `app/`.
   *Verify:* file exists, `applicationId` inside it matches `de.heilsen.ganzhornfest`.

2. **Add versions and plugins.** In `libs.versions.toml`:
   ```toml
   google-services = "4.4.4"
   firebase-bom = "34.5.0"
   ```
   (Check actual latest stable versions at implementation time — these are placeholders.)
   Add `[plugins]` entries for `google-services` and `firebase-crashlytics`, and a
   `firebase-crashlytics` library entry sourced from the BOM (no explicit version needed on the
   library itself once the BOM platform is applied).
   Apply both plugins with `apply false` in root `build.gradle.kts`, then `alias(...)` them in
   `app/build.gradle.kts`. Add `implementation(platform(libs.firebase.bom))` and
   `implementation(libs.firebase.crashlytics)`.
   *Verify:* `./gradlew :app:dependencies --configuration releaseRuntimeClasspath | grep firebase`
   shows resolved artifacts.

3. **Add the `CrashReporter` abstraction.**
   ```kotlin
   // core-api
   interface CrashReporter {
       fun recordNonFatal(throwable: Throwable, tag: String)
       fun setCustomKey(key: String, value: String)
   }
   ```
   Implement in `core-impl` (or `app` if you'd rather keep Firebase deps out of `core-impl` too
   — check `core-impl/build.gradle.kts` first; if it has no Android/Firebase deps currently,
   prefer implementing in `app` and exposing via the Metro graph, contributed with
   `@ContributesBinding(AppScope::class)`).
   *Verify:* a fake `CrashReporter` is trivial to provide in tests, since it's an interface.

4. **Wire the release Timber tree.** In `TimberBindings.kt`, add:
   ```kotlin
   class CrashlyticsTree : Timber.Tree() {
       override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
           if (priority < Log.INFO) return
           FirebaseCrashlytics.getInstance().log("$tag: $message")
           if (t != null) FirebaseCrashlytics.getInstance().recordException(t)
       }
   }
   ```
   Add it to the existing `@ElementsIntoSet` provider, gated on `!BuildConfig.DEBUG` (mirror the
   existing `if (BuildConfig.DEBUG) add(Timber.DebugTree())` pattern, inverted).
   *Verify:* release build's Timber tree set contains exactly `CrashlyticsTree`; debug contains
   exactly `DebugTree`. Write a quick unit test if `:app` test infra makes it easy, otherwise
   confirm by reading the assembled set.

5. **Consent gate.** Minimum viable: default `setCrashlyticsCollectionEnabled(true)` for a solo
   festival-info app with no PII in custom keys (day/destination/schema version only — no user
   identifiers). State this explicitly in the PR description as the privacy decision, since
   `docs/TODO.md` calls for "explicit privacy handling." If you want a real opt-out, add one
   `DataStore`-backed boolean read at `Application.onCreate()` — do not build a full settings
   screen for this alone, that's out of scope.
   *Verify:* toggling the flag (temporarily, in a debug build) changes whether a forced test
   crash appears in the Firebase console within a few minutes.

6. **Add custom keys where it's cheap.** In `BusPresenter.present()`, call
   `crashReporter.setCustomKey("bus_destination", destination)` when it changes. In
   `core-impl`'s DB provider or `DatabaseBindings.kt`, set `"db_schema_version"` to
   `GanzhornfestDb.Schema.version.toString()`.
   *Verify:* trigger a test crash, confirm the keys appear on the Crashlytics console event.

7. **CI secret.** `google-services.json` is committed, so CI needs no secret for it. Confirm
   `.github/workflows/ci.yml` (see P7) doesn't need changes for this — `./gradlew check` doesn't
   invoke `processReleaseGoogleServices` unless it assembles release, which `check` doesn't.
   *Verify:* `./gradlew check` still passes with no `GOOGLE_APPLICATION_CREDENTIALS` or similar
   set.

## Tests

`:app` already has `kotest`/`mockk`/`turbine` on the test classpath. If the `CrashReporter`
binding lives in `core-impl`, add those test deps there too (check
`core-impl/build.gradle.kts` first, per `CLAUDE.md`). A small unit test asserting the
`CrashlyticsTree` filters below `Log.INFO` is enough; do not attempt to test actual Firebase
network calls.

## Done when

`./gradlew check` passes, a manual debug build with a forced `throw` shows up in the Firebase
console, then run `/create-pr`.
