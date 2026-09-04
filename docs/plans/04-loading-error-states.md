# P4 · A real loading/error state system

## Goal

Give the app a shared error state (there is currently none) and use it where failures are
silently swallowed today: `DetailScreen`'s blank-screen bailout and `BusScreen`'s missing
loading/error handling for the connections list.

## Why now

`theme/src/main/kotlin/de/heilsen/ganzhornfest/theme/component/` has `LoadingScreen.kt` and
`EmptyScreen.kt` but nothing for "this failed." `docs/TODO.md` calls this out directly: "Add a
reusable error state for failed DB/data loads instead of blank screens."

The concrete symptom: `map/src/main/kotlin/de/heilsen/ganzhornfest/detail/DetailScreen.kt:34`
reads
```kotlin
if (model !is DetailModel.Success) return
```
— when `model` is `DetailModel.Loading` (or any future failure state), the composable returns
**nothing**, rendering a blank white/black screen with no spinner, no message, no way back other
than the system back gesture. There's no `DetailModel.Error` variant to even represent a query
failure today (`DetailModel.kt` only has `Loading` and `Success`).

`BusScreen.kt:52` does handle `BusModel.Loading` correctly via `LoadingScreen()`, but
`BusModel` (in `bus-api`) has no `Error` variant either — if
`BusConnectionRepository.getBusConnection` throws (e.g. a malformed date string reaching
`Instant.parse` at `BusConnectionRepository.kt:51`), the `Flow` throws and the collecting
`collectAsState` call in `BusPresenter.kt:49` never delivers a value, silently freezing on
`BusModel.Loading` forever with no error surfaced.

## Worktree

```bash
/start-implement feat loading-error-states
```

## Files owned

- `theme/src/main/kotlin/de/heilsen/ganzhornfest/theme/component/ErrorScreen.kt` (new)
- `map/src/main/kotlin/de/heilsen/ganzhornfest/detail/DetailModel.kt`
- `map/src/main/kotlin/de/heilsen/ganzhornfest/detail/DetailScreen.kt`
- `map/src/main/kotlin/de/heilsen/ganzhornfest/detail/DetailPresenter.kt`
- `map/src/main/kotlin/de/heilsen/ganzhornfest/detail/GetClubDetailUseCase.kt`,
  `GetOfferDetailUseCase.kt` (wrap the flow to catch and map failures)
- `bus-api/src/main/kotlin/de/heilsen/ganzhornfest/bus/BusModel.kt`
- `bus-impl/src/main/kotlin/de/heilsen/ganzhornfest/bus/BusScreen.kt`,
  `BusPresenter.kt`

**Do not touch:** anything under `map/src/main/kotlin/de/heilsen/ganzhornfest/map/` (the `map`
package proper — `MapScreen.kt`, `MapPresenter.kt`, `MapModel.kt`, `MarkerUi.kt`, etc.) — that's
entirely owned by your `feat/festival-data-2026` branch. This plan only touches the sibling
`detail` package inside the same `:map` Gradle module, which your branch does not edit. Also do
not touch `feature/search-impl` (owned by P2) or `program/` (owned by P3) — each of those plans
adds its own loading/empty states inline as part of its rewrite.

## Steps

1. **Add `ErrorScreen`.** Mirror the shape of `theme/component/EmptyScreen.kt` and
   `LoadingScreen.kt`:
   ```kotlin
   @Composable
   fun ErrorScreen(
       modifier: Modifier = Modifier,
       message: String = "Etwas ist schiefgelaufen",
       onRetry: (() -> Unit)? = null,
   ) {
       Column(modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {
           Text(message, style = MaterialTheme.typography.titleMedium, textAlign = TextAlign.Center)
           if (onRetry != null) {
               Spacer(Modifier.height(8.dp))
               Button(onClick = onRetry) { Text("Erneut versuchen") }
           }
       }
   }
   ```
   Match `EmptyScreen`'s exact spacing/alignment constants so the two states feel consistent.
   *Verify:* add a `@PreviewDefault` for it, same as `GanzhornfestScaffoldPreview` in
   `GanzhornfestScaffold.kt`.

2. **Add `DetailModel.Error`.** In `DetailModel.kt`, add
   `data class Error(val message: String) : DetailModel` alongside `Loading`/`Success`. In
   `DetailScreen.kt`, replace the early-return `if (model !is DetailModel.Success) return` with
   an exhaustive `when`:
   ```kotlin
   when (model) {
       DetailModel.Loading -> LoadingScreen()
       is DetailModel.Error -> ErrorScreen(message = model.message)
       is DetailModel.Success -> { /* existing content */ }
   }
   ```
   *Verify:* Kotlin's exhaustiveness check on the sealed interface catches any missed branch at
   compile time — this is why `DetailModel` being `sealed interface` matters here.

3. **Surface failures from the use cases.** `GetClubDetailUseCase.invoke` and
   `GetOfferDetailUseCase` (mirror it) return `Flow<DetailModel.Success>` directly with no
   `catch`. Wrap with `.catch { emit(DetailModel.Error("...")) }` and widen the return type to
   `Flow<DetailModel>`. Update `DetailPresenter.present()` (`DetailPresenter.kt:19-27`)
   accordingly — it currently does `model ?: DetailModel.Loading` after `collectAsState(initial =
   null)`; once the flow itself never throws (errors become values), this simplifies slightly.
   *Verify:* a presenter test forcing the use case to emit `DetailModel.Error` and asserting it
   passes through unchanged.

4. **Add `BusModel.Error`.** In `bus-api/.../BusModel.kt`, add an `Error` variant. In
   `BusPresenter.present()` (`BusPresenter.kt:49-55`), wrap `getDepartures(...)` with `.catch { }`
   the same way, instead of relying on `collectAsState(initial = null)` + `?: return
   BusModel.Loading` which can't distinguish "still loading" from "failed and never emitted."
   In `BusScreen.kt`'s `when (busModel)` (line 52), add the `Error` branch rendering
   `ErrorScreen`.
   *Verify:* a turbine-based presenter test: make the fake `GetBusConnectionsUseCase` throw,
   assert the model transitions to `Error` rather than hanging on `Loading`.

## Tests

`bus-impl/build.gradle.kts` and `map/build.gradle.kts` — check both for existing
`kotest`/`mockk`/`turbine` deps before adding tests; if absent, add them following
`feature/search-impl/build.gradle.kts` as the template (per `CLAUDE.md`, inspect the
module's `build.gradle.kts` before editing it). Add:
- `theme` module: none needed beyond the preview, `ErrorScreen` has no logic to unit test.
- `map` module: a `DetailPresenterTest` (new, if no presenter tests exist there yet — check
  first) covering the loading → success and loading → error transitions.
- `bus-impl` module: extend or add a `BusPresenterTest` covering the same for `BusModel`.

## Done when

`./gradlew check` passes, then `/create-pr`.
