# P6 · Edge-to-edge, insets, predictive back

## Goal

Make the app draw edge-to-edge with correct system-bar insets everywhere, resolve the
AppCompat/Compose theme conflict, and enable predictive back gestures — table-stakes on a
`targetSdk 37` app in 2026.

## Why now

- `app/src/main/kotlin/de/heilsen/ganzhornfest/main/MainActivity.kt` never calls
  `enableEdgeToEdge()`. On API 35+ edge-to-edge is enforced regardless, so right now the app is
  edge-to-edge *by OS default* but without deliberate inset handling, which is worse than either
  fully opting in or fully opting out.
- `app/src/main/AndroidManifest.xml:8` sets `android:theme="@style/Theme.AppCompat.DayNight.NoActionBar"`
  on an app whose `MainActivity` (`app/src/main/kotlin/.../main/MainActivity.kt`) is a plain
  `ComponentActivity` rendering pure Compose via `GanzhornfestTheme` (`theme/Theme.kt`). There is
  no `themes.xml` in this repo at all (`find app/src/main/res -iname "themes*.xml"` returns
  nothing) — the manifest points at a *library-default* AppCompat style that was never
  customized, while `GanzhornfestTheme` independently sets `MaterialTheme.colorScheme`. Two
  theming systems are active and not reconciled; this is exactly the kind of drift `docs/TODO.md`'s
  "Add edge-to-edge/insets handling" item is pointing at.
- `AndroidManifest.xml` has no `android:enableOnBackInvokedCallback="true"` on the
  `<application>` tag, so predictive back (standard since API 33, expected by API 35+ users) is
  not opted into.
- `theme/src/main/kotlin/de/heilsen/ganzhornfest/theme/component/GanzhornfestScaffold.kt:35-40`
  wraps its `content` slot in `Column(Modifier.padding(paddingValues))` — this consumes the
  `Scaffold`'s inner padding once, but nothing downstream re-exposes remaining insets (e.g. IME,
  display cutout) to children, meaning any per-screen content that wants to draw under/around a
  specific inset (like a bottom sheet respecting IME) has no path to do so. Note `MainScreen.kt`
  already got a partial fix for this in commit `2f7bf63` ("apply scaffold insets once on
  NavHost") — that fix is specific to `MainScreen`'s own `Scaffold`, not
  `GanzhornfestScaffold`, which every other screen (`BusScreen`, `ProgramScreen`, `DetailScreen`)
  uses via its own separate `Scaffold`. Confirm this nested-Scaffold double-inset situation
  still exists before and after your change (each of those screens' `GanzhornfestScaffold` is
  nested inside `MainScreen`'s own `Scaffold`/`NavHost`, so insets could double-apply — check
  carefully with the layout inspector or by reading padding values at runtime).

## Worktree

```bash
/start-implement feat edge-to-edge
```

## Files owned

- `app/src/main/kotlin/de/heilsen/ganzhornfest/main/MainActivity.kt`
- `app/src/main/AndroidManifest.xml`
- New `app/src/main/res/values/themes.xml` (and `values-night/themes.xml` if needed)
- `theme/src/main/kotlin/de/heilsen/ganzhornfest/theme/component/GanzhornfestScaffold.kt`

**Do not touch:** `app/src/main/kotlin/.../main/MainScreen.kt` — its inset handling was already
fixed in `2f7bf63`; re-touching it risks reverting that fix or conflicting with
`feat/festival-data-2026`, which also edits this file (adding `showPinEditorToggle` and a
`MarkerUiType.ATTRACTION` branch). This plan's `GanzhornfestScaffold` change is deliberately the
*other* half of the inset story — the one `MainScreen` reaching into `NavHost` doesn't cover.

## Steps

1. **Add `androidx.activity:activity-compose`'s `enableEdgeToEdge()`.** Already a transitive dep
   via `libs.bundles.androidx.compose` (`androidx-activity-compose` is in that bundle per
   `libs.versions.toml`). In `MainActivity.onCreate()`, call `enableEdgeToEdge()` before
   `setContent { MainScreen() }`.
   *Verify:* app still launches; status/nav bar backgrounds are now transparent instead of
   solid, confirmed visually.

2. **Add a proper `themes.xml`.** Create `app/src/main/res/values/themes.xml` with a Compose-first
   theme (`android:windowLightStatusBar`/`windowLightNavigationBar` should be omitted since
   `enableEdgeToEdge()` + `GanzhornfestTheme`'s dynamic color handles that at runtime via
   `WindowCompat`). Minimum needed: a theme that doesn't fight Compose — either
   `Theme.Material3.DayNight.NoActionBar` as the parent (closer to what the app actually is) or
   a deliberately minimal `Theme.SplashScreen`-less base. Point `AndroidManifest.xml:8` at it
   instead of `Theme.AppCompat.DayNight.NoActionBar`.
   *Verify:* app launches with no crash from a missing attribute (`android:theme` resolution
   failures show up immediately at launch); splash/cold-start background matches the app's
   actual color scheme instead of AppCompat defaults.

3. **Enable predictive back.** Add `android:enableOnBackInvokedCallback="true"` to the
   `<application>` tag in `AndroidManifest.xml`. Since `minSdk = 24`
   (`app/build.gradle.kts:19`) but predictive back only applies API 33+, this is a safe
   unconditional addition — it's a no-op below API 33.
   *Verify:* on an API 34+ emulator, the back gesture shows the predictive-back preview
   animation instead of an instant pop.

4. **Fix `GanzhornfestScaffold`'s inset handling.** Replace the blanket
   `Column(Modifier.padding(paddingValues))` with `Modifier.consumeWindowInsets(paddingValues)`
   composed onto the content, matching the pattern `MainScreen.kt` already uses for its own
   `NavHost` (`.padding(innerPadding).consumeWindowInsets(innerPadding)`, per `2f7bf63`). Check
   whether `GanzhornfestScaffold`'s own `Scaffold` needs `contentWindowInsets` set explicitly to
   avoid double-consuming insets already consumed by the parent `MainScreen` `Scaffold`/`NavHost`
   — since every `GanzhornfestScaffold` usage (`BusScreen`, `ProgramScreen`, `DetailScreen`) is
   nested inside `MainScreen`'s `NavHost` content, the outer `Scaffold` already consumed the
   system bar insets before this inner one runs. Setting
   `contentWindowInsets = WindowInsets(0, 0, 0, 0)` on the inner `Scaffold` is the likely fix —
   verify by testing on a device with a display cutout or gesture nav bar, not just a
   standard emulator skin.
   *Verify:* `BusScreen`'s bottom `Text("Angaben ohne Gewähr")` (`BusScreen.kt:56-64`, aligned
   `BottomEnd`) doesn't get clipped by or double-padded against the gesture nav bar.

## Tests

No new unit test surface — this is layout/manifest configuration. Verification is manual, on a
real device or an emulator image with gesture navigation and a display cutout enabled (the
default AVD skins with a notch work). If you have access to Compose UI testing infra already
wired in `:app` (`androidx-ui-test-junit4` is present per `app/build.gradle.kts:156`), a
lightweight semantics-based check that `GanzhornfestScaffold`'s content composes without
exception is reasonable, but don't over-invest in automated inset testing here.

## Done when

`./gradlew check` passes, manual verification on-device confirms no clipped content and a
working predictive-back gesture, then `/create-pr`.
